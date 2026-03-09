-- Ledger harian sensus ranap (cutoff 07:00) + snapshot agregat harian
-- Catatan: tabel rekap_sensus_ranap_harian di beberapa sistem mungkin sudah ada.
-- Jika struktur lama berbeda, lakukan backup/rename lalu buat ulang sesuai kebutuhan ini.

CREATE TABLE IF NOT EXISTS `census_ranap_ledger` (
  `tanggal_sensus` DATE NOT NULL,
  `no_rawat` VARCHAR(17) NOT NULL,
  `kd_bangsal` VARCHAR(10) NOT NULL,
  `kd_kamar` VARCHAR(15) NOT NULL,
  `masuk_at` DATETIME NOT NULL,
  `keluar_at` DATETIME NULL,
  `flag_awal` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_masuk` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_pindahan_masuk` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_keluar_pulang` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_keluar_rujuk` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_keluar_paps` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_keluar_pindah` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_mati_kurang_48` TINYINT(1) NOT NULL DEFAULT 0,
  `flag_mati_lebih_sama_48` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uniq_ledger` (`tanggal_sensus`, `no_rawat`, `kd_bangsal`),
  KEY `idx_ledger_tgl_bangsal` (`tanggal_sensus`, `kd_bangsal`),
  KEY `idx_ledger_no_rawat` (`no_rawat`),
  KEY `idx_ledger_kamar` (`kd_kamar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `rekap_sensus_ranap_harian` (
  `tanggal_sensus` DATE NOT NULL,
  `kd_bangsal` VARCHAR(10) NOT NULL,
  `pasien_awal` INT NOT NULL DEFAULT 0,
  `masuk` INT NOT NULL DEFAULT 0,
  `pindahan_masuk` INT NOT NULL DEFAULT 0,
  `keluar_pulang` INT NOT NULL DEFAULT 0,
  `keluar_rujuk` INT NOT NULL DEFAULT 0,
  `keluar_paps` INT NOT NULL DEFAULT 0,
  `keluar_pindah` INT NOT NULL DEFAULT 0,
  `mati_kurang_48` INT NOT NULL DEFAULT 0,
  `mati_lebih_sama_48` INT NOT NULL DEFAULT 0,
  `pasien_sisa` INT NOT NULL DEFAULT 0,
  `generated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tanggal_sensus`, `kd_bangsal`),
  KEY `idx_rekap_bangsal` (`kd_bangsal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `rekap_sensus_ranap_harian_dpjp` (
  `tanggal_sensus` DATE NOT NULL,
  `kd_bangsal` VARCHAR(10) NOT NULL,
  `kd_dokter` VARCHAR(20) NOT NULL,
  `pasien_awal` INT NOT NULL DEFAULT 0,
  `pasien_sisa` INT NOT NULL DEFAULT 0,
  `total_pasien_window` INT NOT NULL DEFAULT 0,
  `generated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tanggal_sensus`, `kd_bangsal`, `kd_dokter`),
  KEY `idx_rekap_dpjp_dokter` (`kd_dokter`, `tanggal_sensus`),
  KEY `idx_rekap_dpjp_bangsal` (`kd_bangsal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Query inti (contoh) ========================================================
-- 1) Segmen aktif pada window cutoff_start/cutoff_next
--   :cutoff_start = 'YYYY-MM-DD 07:00:00'
--   :cutoff_next  = 'YYYY-MM-DD 07:00:00' (hari berikutnya)
--
-- SELECT ki.no_rawat, k.kd_bangsal,
--        TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00')) AS masuk_at,
--        CASE WHEN ki.tgl_keluar IS NULL OR ki.tgl_keluar='0000-00-00'
--             THEN NULL
--             ELSE TIMESTAMP(ki.tgl_keluar, IFNULL(ki.jam_keluar,'00:00:00')) END AS keluar_at
-- FROM kamar_inap ki
-- JOIN kamar k ON ki.kd_kamar=k.kd_kamar
-- WHERE TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00')) < :cutoff_next
--   AND (ki.tgl_keluar IS NULL OR ki.tgl_keluar='0000-00-00'
--        OR TIMESTAMP(ki.tgl_keluar, IFNULL(ki.jam_keluar,'00:00:00')) >= :cutoff_start);
--
-- 2) Deteksi pindahan_masuk (toleransi menit)
--   :tol = 10
--
-- SELECT ki.no_rawat, ki.kd_kamar,
--        EXISTS (
--          SELECT 1 FROM kamar_inap prev
--          WHERE prev.no_rawat=ki.no_rawat
--            AND prev.stts_pulang='Pindah Kamar'
--            AND prev.tgl_keluar IS NOT NULL AND prev.tgl_keluar<>'0000-00-00'
--            AND TIMESTAMP(prev.tgl_keluar, IFNULL(prev.jam_keluar,'00:00:00'))
--                BETWEEN DATE_SUB(TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00')), INTERVAL :tol MINUTE)
--                    AND TIMESTAMP(ki.tgl_masuk, IFNULL(ki.jam_masuk,'00:00:00'))
--        ) AS is_pindahan
-- FROM kamar_inap ki;
--
-- 3) admission_first_at per no_rawat
--
-- SELECT no_rawat, MIN(TIMESTAMP(tgl_masuk, IFNULL(jam_masuk,'00:00:00'))) AS admission_first_at
-- FROM kamar_inap
-- GROUP BY no_rawat;
