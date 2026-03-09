<?php
if (php_sapi_name() !== 'cli') {
    echo "Run script ini via CLI.\n";
    exit(1);
}

// Pastikan conf.php bisa di-include di CLI.
if (!isset($_SERVER['REQUEST_URI'])) {
    $_SERVER['REQUEST_URI'] = '';
}

require_once __DIR__ . '/../webapps/conf/conf.php';

date_default_timezone_set('Asia/Jakarta');

$usage = "Usage: php scripts/rebuild_sensus_ranap.php YYYY-MM-DD YYYY-MM-DD [kd_bangsal|ALL] [kd_dokter|ALL]\n";
if ($argc < 3) {
    echo $usage;
    exit(1);
}

$startDateInput = trim($argv[1]);
$endDateInput = trim($argv[2]);
$bangsalInput = isset($argv[3]) ? trim($argv[3]) : '';
$dokterInput = isset($argv[4]) ? trim($argv[4]) : '';

function normalizeDate($dateStr)
{
    $dt = DateTime::createFromFormat('Y-m-d', $dateStr);
    if (!$dt || $dt->format('Y-m-d') !== $dateStr) {
        return null;
    }
    return $dt;
}

$startDate = normalizeDate($startDateInput);
$endDate = normalizeDate($endDateInput);
if (!$startDate || !$endDate) {
    echo "Tanggal tidak valid. Gunakan format YYYY-MM-DD.\n";
    echo $usage;
    exit(1);
}
if ($startDate > $endDate) {
    echo "start_date harus <= end_date.\n";
    exit(1);
}

$kdBangsal = '';
if ($bangsalInput !== '' && strtoupper($bangsalInput) !== 'ALL') {
    $kdBangsal = $bangsalInput;
}

$kdDokter = '';
if ($dokterInput !== '' && strtoupper($dokterInput) !== 'ALL') {
    $kdDokter = $dokterInput;
}

// Konfigurasi mapping stts_pulang (silakan sesuaikan)
$sttsMap = array(
    'pindah' => array('Pindah Kamar'),
    'meninggal' => array('Meninggal'),
    'rujuk' => array('Rujuk'),
    'paps' => array('APS', 'Pulang Paksa', 'Atas Permintaan Sendiri', 'PAPS'),
    'pulang_lain' => array('Sehat', 'Sembuh', 'Membaik', 'Kontrol', 'Pulang', 'Lain-lain')
);

// Toleransi menit untuk deteksi pindahan
$toleranceMinutes = 10;

function listToLower($list)
{
    $out = array();
    foreach ($list as $val) {
        $val = trim($val);
        if ($val !== '') {
            $out[] = strtolower($val);
        }
    }
    return $out;
}

$sttsMapLower = array(
    'pindah' => listToLower($sttsMap['pindah']),
    'meninggal' => listToLower($sttsMap['meninggal']),
    'rujuk' => listToLower($sttsMap['rujuk']),
    'paps' => listToLower($sttsMap['paps']),
    'pulang_lain' => listToLower($sttsMap['pulang_lain'])
);

function equalsAny($value, $list)
{
    foreach ($list as $item) {
        if ($value === $item) {
            return true;
        }
    }
    return false;
}

function containsAny($value, $list)
{
    foreach ($list as $item) {
        if ($item !== '' && stripos($value, $item) !== false) {
            return true;
        }
    }
    return false;
}

function categorizeStts($stts, $map)
{
    $s = strtolower(trim($stts));
    if ($s === '') {
        return '';
    }
    if (equalsAny($s, $map['meninggal']) || containsAny($s, $map['meninggal']) || strpos($s, 'meninggal') !== false) {
        return 'meninggal';
    }
    if (equalsAny($s, $map['pindah'])) {
        return 'pindah';
    }
    if (containsAny($s, $map['paps'])) {
        return 'paps';
    }
    if (containsAny($s, $map['rujuk']) || strpos($s, 'rujuk') !== false) {
        return 'rujuk';
    }
    if (containsAny($s, $map['pulang_lain'])) {
        return 'pulang';
    }
    return '';
}

mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
$mysqli = new mysqli($db_hostname, $db_username, $db_password, $db_name);
$mysqli->set_charset('utf8');

$endPlusOne = (clone $endDate)->modify('+1 day');

$segmentsSql = "SELECT
        ki.no_rawat,
        ki.kd_kamar,
        k.kd_bangsal,
        ki.tgl_masuk,
        ki.jam_masuk,
        ki.tgl_keluar,
        ki.jam_keluar,
        ki.stts_pulang,
        TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00')) AS masuk_at,
        CASE WHEN ki.tgl_keluar IS NULL OR ki.tgl_keluar='0000-00-00'
             THEN NULL
             ELSE TIMESTAMP(ki.tgl_keluar, IFNULL(ki.jam_keluar,'00:00:00')) END AS keluar_at,
        EXISTS (
            SELECT 1 FROM kamar_inap prev
            INNER JOIN kamar prevk ON prev.kd_kamar=prevk.kd_kamar
            WHERE prev.no_rawat=ki.no_rawat
              AND prev.stts_pulang='Pindah Kamar'
              AND prev.tgl_keluar IS NOT NULL AND prev.tgl_keluar<>'0000-00-00'
              AND prevk.kd_bangsal<>k.kd_bangsal
              AND TIMESTAMP(prev.tgl_keluar, IFNULL(prev.jam_keluar,'00:00:00')) BETWEEN
                  DATE_SUB(TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00')), INTERVAL " . intval($toleranceMinutes) . " MINUTE)
                  AND TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00'))
        ) AS is_pindahan_in,
        EXISTS (
            SELECT 1 FROM kamar_inap next_ki
            INNER JOIN kamar nextk ON next_ki.kd_kamar=nextk.kd_kamar
            WHERE next_ki.no_rawat=ki.no_rawat
              AND ki.stts_pulang='Pindah Kamar'
              AND ki.tgl_keluar IS NOT NULL AND ki.tgl_keluar<>'0000-00-00'
              AND nextk.kd_bangsal<>k.kd_bangsal
              AND TIMESTAMP(next_ki.tgl_masuk, IFNULL(next_ki.jam_masuk,'00:00:00')) BETWEEN
                  TIMESTAMP(ki.tgl_keluar, IFNULL(ki.jam_keluar,'00:00:00'))
                  AND DATE_ADD(TIMESTAMP(ki.tgl_keluar, IFNULL(ki.jam_keluar,'00:00:00')), INTERVAL " . intval($toleranceMinutes) . " MINUTE)
        ) AS is_pindahan_out,
        (SELECT MIN(TIMESTAMP(ki2.tgl_masuk, IFNULL(ki2.jam_masuk,'00:00:00')))
         FROM kamar_inap ki2
         WHERE ki2.no_rawat=ki.no_rawat) AS admission_first_at
    FROM kamar_inap ki
    INNER JOIN kamar k ON ki.kd_kamar=k.kd_kamar
    INNER JOIN bangsal b ON k.kd_bangsal=b.kd_bangsal
    WHERE TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00')) < ?
      AND (ki.tgl_keluar IS NULL OR ki.tgl_keluar='0000-00-00' OR TIMESTAMP(ki.tgl_keluar, IFNULL(ki.jam_keluar,'00:00:00')) >= ?)
";
if ($kdBangsal !== '') {
    $segmentsSql .= " AND k.kd_bangsal = ?";
}

$insertLedgerSql = "INSERT INTO census_ranap_ledger
    (tanggal_sensus, no_rawat, kd_bangsal, kd_kamar, masuk_at, keluar_at,
     flag_awal, flag_masuk, flag_pindahan_masuk, flag_keluar_pulang, flag_keluar_rujuk,
     flag_keluar_paps, flag_keluar_pindah, flag_mati_kurang_48, flag_mati_lebih_sama_48)
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    ON DUPLICATE KEY UPDATE
      kd_kamar=VALUES(kd_kamar),
      masuk_at=VALUES(masuk_at),
      keluar_at=VALUES(keluar_at),
      flag_awal=VALUES(flag_awal),
      flag_masuk=VALUES(flag_masuk),
      flag_pindahan_masuk=VALUES(flag_pindahan_masuk),
      flag_keluar_pulang=VALUES(flag_keluar_pulang),
      flag_keluar_rujuk=VALUES(flag_keluar_rujuk),
      flag_keluar_paps=VALUES(flag_keluar_paps),
      flag_keluar_pindah=VALUES(flag_keluar_pindah),
      flag_mati_kurang_48=VALUES(flag_mati_kurang_48),
      flag_mati_lebih_sama_48=VALUES(flag_mati_lebih_sama_48)";

$deleteLedgerSql = "DELETE FROM census_ranap_ledger WHERE tanggal_sensus = ?";
$deleteRekapSql = "DELETE FROM rekap_sensus_ranap_harian WHERE tanggal_sensus = ?";
$deleteDpjpSql = "DELETE FROM rekap_sensus_ranap_harian_dpjp WHERE tanggal_sensus = ?";
if ($kdBangsal !== '') {
    $deleteLedgerSql .= " AND kd_bangsal = ?";
    $deleteRekapSql .= " AND kd_bangsal = ?";
    $deleteDpjpSql .= " AND kd_bangsal = ?";
}
if ($kdDokter !== '') {
    $deleteDpjpSql .= " AND kd_dokter = ?";
}

$insertRekapSql = "INSERT INTO rekap_sensus_ranap_harian
    (tanggal_sensus, kd_bangsal, pasien_awal, masuk, pindahan_masuk,
     keluar_pulang, keluar_rujuk, keluar_paps, keluar_pindah,
     mati_kurang_48, mati_lebih_sama_48, pasien_sisa, generated_at)
    SELECT
      ?, kd_bangsal,
      IFNULL(COUNT(DISTINCT CASE WHEN flag_awal=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_masuk=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_pindahan_masuk=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_keluar_pulang=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_keluar_rujuk=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_keluar_paps=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_keluar_pindah=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_mati_kurang_48=1 THEN no_rawat END),0),
      IFNULL(COUNT(DISTINCT CASE WHEN flag_mati_lebih_sama_48=1 THEN no_rawat END),0),
      0,
      CURRENT_TIMESTAMP
    FROM census_ranap_ledger
    WHERE tanggal_sensus = ?";
if ($kdBangsal !== '') {
    $insertRekapSql .= " AND kd_bangsal = ?";
}
$insertRekapSql .= " GROUP BY kd_bangsal
    ON DUPLICATE KEY UPDATE
      pasien_awal=VALUES(pasien_awal),
      masuk=VALUES(masuk),
      pindahan_masuk=VALUES(pindahan_masuk),
      keluar_pulang=VALUES(keluar_pulang),
      keluar_rujuk=VALUES(keluar_rujuk),
      keluar_paps=VALUES(keluar_paps),
      keluar_pindah=VALUES(keluar_pindah),
      mati_kurang_48=VALUES(mati_kurang_48),
      mati_lebih_sama_48=VALUES(mati_lebih_sama_48),
      generated_at=CURRENT_TIMESTAMP";

$insertDpjpSql = "INSERT INTO rekap_sensus_ranap_harian_dpjp
    (tanggal_sensus, kd_bangsal, kd_dokter, pasien_awal, pasien_sisa, total_pasien_window, generated_at)
    SELECT
      ?, l.kd_bangsal, d.kd_dokter,
      COUNT(DISTINCT CASE WHEN l.flag_awal=1 THEN l.no_rawat END) AS pasien_awal,
      0 AS pasien_sisa,
      COUNT(DISTINCT CASE WHEN (l.flag_awal=1 OR l.flag_masuk=1) THEN l.no_rawat END) AS total_pasien_window,
      CURRENT_TIMESTAMP
    FROM census_ranap_ledger l
    INNER JOIN dpjp_ranap d ON d.no_rawat=l.no_rawat
    WHERE l.tanggal_sensus = ?";
if ($kdBangsal !== '') {
    $insertDpjpSql .= " AND l.kd_bangsal = ?";
}
if ($kdDokter !== '') {
    $insertDpjpSql .= " AND d.kd_dokter = ?";
}
$insertDpjpSql .= " GROUP BY l.kd_bangsal, d.kd_dokter
    ON DUPLICATE KEY UPDATE
      pasien_awal=VALUES(pasien_awal),
      total_pasien_window=VALUES(total_pasien_window),
      generated_at=CURRENT_TIMESTAMP";

echo "Rebuild sensus ranap: {$startDateInput} s.d. {$endDateInput}";
if ($kdBangsal !== '') {
    echo " | bangsal={$kdBangsal}";
}
if ($kdDokter !== '') {
    echo " | dokter={$kdDokter}";
}
echo "\n";

$insertLedgerStmt = $mysqli->prepare($insertLedgerSql);

for ($date = clone $startDate; $date <= $endPlusOne; $date->modify('+1 day')) {
    $tanggalSensus = $date->format('Y-m-d');
    $nextDate = (clone $date)->modify('+1 day');
    $cutoffStart = $tanggalSensus . ' 07:00:00';
    $cutoffEnd = $nextDate->format('Y-m-d') . ' 06:59:59';
    $cutoffNext = $nextDate->format('Y-m-d') . ' 07:00:00';

    $cutoffStartDt = new DateTime($cutoffStart);
    $cutoffEndDt = new DateTime($cutoffEnd);

    $mysqli->begin_transaction();
    try {
        // Bersihkan data lama per tanggal (dan filter jika ada)
        $delLedgerStmt = $mysqli->prepare($deleteLedgerSql);
        if ($kdBangsal !== '') {
            $delLedgerStmt->bind_param('ss', $tanggalSensus, $kdBangsal);
        } else {
            $delLedgerStmt->bind_param('s', $tanggalSensus);
        }
        $delLedgerStmt->execute();
        $delLedgerStmt->close();

        $ledgerRows = array();

        $segStmt = $mysqli->prepare($segmentsSql);
        if ($kdBangsal !== '') {
            $segStmt->bind_param('sss', $cutoffNext, $cutoffStart, $kdBangsal);
        } else {
            $segStmt->bind_param('ss', $cutoffNext, $cutoffStart);
        }
        $segStmt->execute();
        $result = $segStmt->get_result();

        while ($row = $result->fetch_assoc()) {
            $masukAtStr = $row['masuk_at'];
            $keluarAtStr = $row['keluar_at'];

            $masukAtDt = new DateTime($masukAtStr);
            $keluarAtDt = null;
            if ($keluarAtStr !== null && $keluarAtStr !== '') {
                $keluarAtDt = new DateTime($keluarAtStr);
            }

            $flagAwal = ($masukAtDt < $cutoffStartDt && ($keluarAtDt === null || $keluarAtDt >= $cutoffStartDt)) ? 1 : 0;
            $flagMasuk = ($masukAtDt >= $cutoffStartDt && $masukAtDt <= $cutoffEndDt) ? 1 : 0;
            $flagPindahanMasuk = ($flagMasuk === 1 && intval($row['is_pindahan_in']) === 1) ? 1 : 0;
            if ($flagPindahanMasuk === 1) {
                // Pindahan masuk tidak dihitung sebagai "masuk" baru
                $flagMasuk = 0;
            }

            $flagKeluar = ($keluarAtDt !== null && $keluarAtDt >= $cutoffStartDt && $keluarAtDt <= $cutoffEndDt) ? 1 : 0;
            $kategoriKeluar = '';
            if ($flagKeluar === 1) {
                $kategoriKeluar = categorizeStts($row['stts_pulang'], $sttsMapLower);
            }

            $flagKeluarPulang = ($flagKeluar === 1 && $kategoriKeluar === 'pulang') ? 1 : 0;
            $flagKeluarRujuk = ($flagKeluar === 1 && $kategoriKeluar === 'rujuk') ? 1 : 0;
            $flagKeluarPaps = ($flagKeluar === 1 && $kategoriKeluar === 'paps') ? 1 : 0;
            $flagKeluarPindah = ($flagKeluar === 1 && $kategoriKeluar === 'pindah' && intval($row['is_pindahan_out']) === 1) ? 1 : 0;

            $flagMatiKurang48 = 0;
            $flagMatiLebihSama48 = 0;
            if ($flagKeluar === 1 && $kategoriKeluar === 'meninggal') {
                $admFirstAtStr = $row['admission_first_at'];
                $admFirstAtDt = $admFirstAtStr ? new DateTime($admFirstAtStr) : $masukAtDt;
                $diffSeconds = $keluarAtDt->getTimestamp() - $admFirstAtDt->getTimestamp();
                $diffHours = intdiv(max($diffSeconds, 0), 3600);
                if ($diffHours < 48) {
                    $flagMatiKurang48 = 1;
                } else {
                    $flagMatiLebihSama48 = 1;
                }
            }

            $key = $row['no_rawat'] . '|' . $row['kd_bangsal'];
            if (!isset($ledgerRows[$key])) {
                $ledgerRows[$key] = array(
                    'no_rawat' => $row['no_rawat'],
                    'kd_bangsal' => $row['kd_bangsal'],
                    'kd_kamar' => $row['kd_kamar'],
                    'masuk_at' => $masukAtStr,
                    'keluar_at' => $keluarAtStr,
                    'flag_awal' => $flagAwal,
                    'flag_masuk' => $flagMasuk,
                    'flag_pindahan_masuk' => $flagPindahanMasuk,
                    'flag_keluar_pulang' => $flagKeluarPulang,
                    'flag_keluar_rujuk' => $flagKeluarRujuk,
                    'flag_keluar_paps' => $flagKeluarPaps,
                    'flag_keluar_pindah' => $flagKeluarPindah,
                    'flag_mati_kurang_48' => $flagMatiKurang48,
                    'flag_mati_lebih_sama_48' => $flagMatiLebihSama48
                );
            } else {
                // Gabungkan jika ada beberapa segmen untuk no_rawat + bangsal di hari yang sama
                $existing = $ledgerRows[$key];

                if ($masukAtStr < $existing['masuk_at']) {
                    $existing['masuk_at'] = $masukAtStr;
                    $existing['kd_kamar'] = $row['kd_kamar'];
                }
                if ($existing['kd_kamar'] === '' || $existing['kd_kamar'] === null) {
                    $existing['kd_kamar'] = $row['kd_kamar'];
                }
                if ($existing['keluar_at'] === null || $existing['keluar_at'] === '') {
                    $existing['keluar_at'] = $keluarAtStr;
                } elseif ($keluarAtStr !== null && $keluarAtStr > $existing['keluar_at']) {
                    $existing['keluar_at'] = $keluarAtStr;
                }

                $existing['flag_awal'] = max($existing['flag_awal'], $flagAwal);
                $existing['flag_masuk'] = max($existing['flag_masuk'], $flagMasuk);
                $existing['flag_pindahan_masuk'] = max($existing['flag_pindahan_masuk'], $flagPindahanMasuk);
                $existing['flag_keluar_pulang'] = max($existing['flag_keluar_pulang'], $flagKeluarPulang);
                $existing['flag_keluar_rujuk'] = max($existing['flag_keluar_rujuk'], $flagKeluarRujuk);
                $existing['flag_keluar_paps'] = max($existing['flag_keluar_paps'], $flagKeluarPaps);
                $existing['flag_keluar_pindah'] = max($existing['flag_keluar_pindah'], $flagKeluarPindah);
                $existing['flag_mati_kurang_48'] = max($existing['flag_mati_kurang_48'], $flagMatiKurang48);
                $existing['flag_mati_lebih_sama_48'] = max($existing['flag_mati_lebih_sama_48'], $flagMatiLebihSama48);

                if ($existing['flag_awal'] === 1) {
                    // Jika sudah termasuk pasien awal di bangsal ini, jangan hitung sebagai "masuk"
                    $existing['flag_masuk'] = 0;
                }

                $ledgerRows[$key] = $existing;
            }
        }

        $result->free();
        $segStmt->close();

        // Insert ledger
        foreach ($ledgerRows as $row) {
            $noRawat = $row['no_rawat'];
            $kdBangsalRow = $row['kd_bangsal'];
            $kdKamarRow = $row['kd_kamar'];
            $masukAt = $row['masuk_at'];
            $keluarAt = $row['keluar_at'];
            $flagAwal = $row['flag_awal'];
            $flagMasuk = $row['flag_masuk'];
            $flagPindahanMasuk = $row['flag_pindahan_masuk'];
            $flagKeluarPulang = $row['flag_keluar_pulang'];
            $flagKeluarRujuk = $row['flag_keluar_rujuk'];
            $flagKeluarPaps = $row['flag_keluar_paps'];
            $flagKeluarPindah = $row['flag_keluar_pindah'];
            $flagMatiKurang48 = $row['flag_mati_kurang_48'];
            $flagMatiLebihSama48 = $row['flag_mati_lebih_sama_48'];

            $insertLedgerStmt->bind_param(
                'ssssssiiiiiiiii',
                $tanggalSensus,
                $noRawat,
                $kdBangsalRow,
                $kdKamarRow,
                $masukAt,
                $keluarAt,
                $flagAwal,
                $flagMasuk,
                $flagPindahanMasuk,
                $flagKeluarPulang,
                $flagKeluarRujuk,
                $flagKeluarPaps,
                $flagKeluarPindah,
                $flagMatiKurang48,
                $flagMatiLebihSama48
            );
            $insertLedgerStmt->execute();
        }

        // Refresh rekap harian
        $delRekapStmt = $mysqli->prepare($deleteRekapSql);
        if ($kdBangsal !== '') {
            $delRekapStmt->bind_param('ss', $tanggalSensus, $kdBangsal);
        } else {
            $delRekapStmt->bind_param('s', $tanggalSensus);
        }
        $delRekapStmt->execute();
        $delRekapStmt->close();

        $rekapStmt = $mysqli->prepare($insertRekapSql);
        if ($kdBangsal !== '') {
            $rekapStmt->bind_param('sss', $tanggalSensus, $tanggalSensus, $kdBangsal);
        } else {
            $rekapStmt->bind_param('ss', $tanggalSensus, $tanggalSensus);
        }
        $rekapStmt->execute();
        $rekapStmt->close();

        // Refresh rekap DPJP
        $delDpjpStmt = $mysqli->prepare($deleteDpjpSql);
        if ($kdBangsal !== '' && $kdDokter !== '') {
            $delDpjpStmt->bind_param('sss', $tanggalSensus, $kdBangsal, $kdDokter);
        } elseif ($kdBangsal !== '') {
            $delDpjpStmt->bind_param('ss', $tanggalSensus, $kdBangsal);
        } elseif ($kdDokter !== '') {
            $delDpjpStmt->bind_param('ss', $tanggalSensus, $kdDokter);
        } else {
            $delDpjpStmt->bind_param('s', $tanggalSensus);
        }
        $delDpjpStmt->execute();
        $delDpjpStmt->close();

        $dpjpStmt = $mysqli->prepare($insertDpjpSql);
        if ($kdBangsal !== '' && $kdDokter !== '') {
            $dpjpStmt->bind_param('ssss', $tanggalSensus, $tanggalSensus, $kdBangsal, $kdDokter);
        } elseif ($kdBangsal !== '') {
            $dpjpStmt->bind_param('sss', $tanggalSensus, $tanggalSensus, $kdBangsal);
        } elseif ($kdDokter !== '') {
            $dpjpStmt->bind_param('sss', $tanggalSensus, $tanggalSensus, $kdDokter);
        } else {
            $dpjpStmt->bind_param('ss', $tanggalSensus, $tanggalSensus);
        }
        $dpjpStmt->execute();
        $dpjpStmt->close();

        $mysqli->commit();

        $countLedger = count($ledgerRows);
        echo "[$tanggalSensus] ledger={$countLedger}\n";
    } catch (Exception $e) {
        $mysqli->rollback();
        throw $e;
    }
}

// Update pasien_sisa = pasien_awal D+1
$updateRekapSql = "UPDATE rekap_sensus_ranap_harian r
    SET pasien_sisa = IFNULL((
        SELECT r2.pasien_awal
        FROM rekap_sensus_ranap_harian r2
        WHERE r2.tanggal_sensus = DATE_ADD(r.tanggal_sensus, INTERVAL 1 DAY)
          AND r2.kd_bangsal = r.kd_bangsal
    ), 0)
    WHERE r.tanggal_sensus BETWEEN ? AND ?";
if ($kdBangsal !== '') {
    $updateRekapSql .= " AND r.kd_bangsal = ?";
}

$updateDpjpSql = "UPDATE rekap_sensus_ranap_harian_dpjp r
    SET pasien_sisa = IFNULL((
        SELECT r2.pasien_awal
        FROM rekap_sensus_ranap_harian_dpjp r2
        WHERE r2.tanggal_sensus = DATE_ADD(r.tanggal_sensus, INTERVAL 1 DAY)
          AND r2.kd_bangsal = r.kd_bangsal
          AND r2.kd_dokter = r.kd_dokter
    ), 0)
    WHERE r.tanggal_sensus BETWEEN ? AND ?";
if ($kdBangsal !== '') {
    $updateDpjpSql .= " AND r.kd_bangsal = ?";
}
if ($kdDokter !== '') {
    $updateDpjpSql .= " AND r.kd_dokter = ?";
}

$updateRekapStmt = $mysqli->prepare($updateRekapSql);
if ($kdBangsal !== '') {
    $startStr = $startDate->format('Y-m-d');
    $endStr = $endDate->format('Y-m-d');
    $updateRekapStmt->bind_param('sss', $startStr, $endStr, $kdBangsal);
} else {
    $startStr = $startDate->format('Y-m-d');
    $endStr = $endDate->format('Y-m-d');
    $updateRekapStmt->bind_param('ss', $startStr, $endStr);
}
$updateRekapStmt->execute();
$updateRekapStmt->close();

$updateDpjpStmt = $mysqli->prepare($updateDpjpSql);
$startStr = $startDate->format('Y-m-d');
$endStr = $endDate->format('Y-m-d');
if ($kdBangsal !== '' && $kdDokter !== '') {
    $updateDpjpStmt->bind_param('ssss', $startStr, $endStr, $kdBangsal, $kdDokter);
} elseif ($kdBangsal !== '') {
    $updateDpjpStmt->bind_param('sss', $startStr, $endStr, $kdBangsal);
} elseif ($kdDokter !== '') {
    $updateDpjpStmt->bind_param('sss', $startStr, $endStr, $kdDokter);
} else {
    $updateDpjpStmt->bind_param('ss', $startStr, $endStr);
}
$updateDpjpStmt->execute();
$updateDpjpStmt->close();

$insertLedgerStmt->close();
$mysqli->close();

echo "Selesai. pasien_sisa sudah disinkron dari pasien_awal (D+1).\n";
