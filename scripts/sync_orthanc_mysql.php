<?php

declare(strict_types=1);

set_time_limit(0);
ini_set('memory_limit', '1024M');

$rootDir = dirname(__DIR__);
$defaultConfigPath = $rootDir . '/orthanc/mysql.json';
$defaultBatchSize = (int) (getenv('SYNC_BATCH_SIZE') ?: 200);

$options = getopt('', [
    'config::',
    'batch-size::',
    'orthanc-url::',
    'orthanc-user::',
    'orthanc-pass::',
]);

$configPath = $options['config'] ?? $defaultConfigPath;
$batchSize = max(1, (int) ($options['batch-size'] ?? $defaultBatchSize));

$orthancUrl = rtrim((string) ($options['orthanc-url'] ?? getenv('ORTHANC_URL') ?: ''), '/');
$orthancUser = (string) ($options['orthanc-user'] ?? getenv('ORTHANC_USER') ?: '');
$orthancPass = (string) ($options['orthanc-pass'] ?? getenv('ORTHANC_PASS') ?: '');

if ($orthancUrl === '' || $orthancUser === '' || $orthancPass === '') {
    fwrite(STDERR, "ORTHANC_URL, ORTHANC_USER, dan ORTHANC_PASS wajib diisi.\n");
    exit(1);
}

$mysqlConfig = load_mysql_config($configPath);
$conn = connect_mysql($mysqlConfig);

$counts = [
    'patients' => 0,
    'studies' => 0,
    'series' => 0,
    'instances' => 0,
];

$patients = orthanc_get('/patients', $orthancUrl, $orthancUser, $orthancPass);
if (!is_array($patients)) {
    fwrite(STDERR, "Gagal mengambil data pasien dari Orthanc.\n");
    exit(1);
}

$batchPatients = [];
$batchStudies = [];
$batchSeries = [];
$batchInstances = [];

foreach ($patients as $orthancPatientId) {
    $patient = orthanc_get('/patients/' . rawurlencode((string) $orthancPatientId), $orthancUrl, $orthancUser, $orthancPass);
    if (!is_object($patient)) {
        continue;
    }

    $p = $patient->MainDicomTags ?? new stdClass();
    $patientId = safe_string($p->PatientID ?? null);
    $batchPatients[] = [
        'orthanc_patient_id' => safe_string($orthancPatientId),
        'patient_id' => $patientId,
        'nama' => safe_string($p->PatientName ?? null),
        'kelamin' => safe_string($p->PatientSex ?? null),
        'tgl_lahir' => dicom_date($p->PatientBirthDate ?? null),
    ];
    $counts['patients']++;

    if (count($batchPatients) >= $batchSize) {
        batch_insert($conn, 'pasien_orthanc', ['orthanc_patient_id', 'patient_id', 'nama', 'kelamin', 'tgl_lahir'], $batchPatients);
        log_message('pasien', count($batchPatients));
        $batchPatients = [];
    }

    foreach (($patient->Studies ?? []) as $orthancStudyId) {
        $study = orthanc_get('/studies/' . rawurlencode((string) $orthancStudyId), $orthancUrl, $orthancUser, $orthancPass);
        if (!is_object($study)) {
            continue;
        }

        $s = $study->MainDicomTags ?? new stdClass();
        $studyUid = safe_string($s->StudyInstanceUID ?? null);
        if ($studyUid === '') {
            continue;
        }

        $batchStudies[] = [
            'patient_id' => $patientId,
            'orthanc_study_id' => safe_string($orthancStudyId),
            'study_uid' => $studyUid,
            'description' => safe_string($s->StudyDescription ?? null),
            'tanggal' => dicom_date($s->StudyDate ?? null),
            'institution' => safe_string($s->InstitutionName ?? null),
        ];
        $counts['studies']++;

        if (count($batchStudies) >= $batchSize) {
            batch_insert($conn, 'study_orthanc', ['patient_id', 'orthanc_study_id', 'study_uid', 'description', 'tanggal', 'institution'], $batchStudies);
            log_message('study', count($batchStudies));
            $batchStudies = [];
        }

        foreach (($study->Series ?? []) as $orthancSeriesId) {
            $series = orthanc_get('/series/' . rawurlencode((string) $orthancSeriesId), $orthancUrl, $orthancUser, $orthancPass);
            if (!is_object($series)) {
                continue;
            }

            $ser = $series->MainDicomTags ?? new stdClass();
            $seriesUid = safe_string($ser->SeriesInstanceUID ?? null);
            if ($seriesUid === '') {
                continue;
            }

            $batchSeries[] = [
                'study_uid' => $studyUid,
                'orthanc_series_id' => safe_string($orthancSeriesId),
                'series_uid' => $seriesUid,
                'body_part' => safe_string($ser->BodyPartExamined ?? null),
                'modality' => safe_string($ser->Modality ?? null),
                'station_name' => safe_string($ser->StationName ?? null),
            ];
            $counts['series']++;

            if (count($batchSeries) >= $batchSize) {
                batch_insert($conn, 'series_orthanc', ['study_uid', 'orthanc_series_id', 'series_uid', 'body_part', 'modality', 'station_name'], $batchSeries);
                log_message('series', count($batchSeries));
                $batchSeries = [];
            }

            foreach (($series->Instances ?? []) as $orthancInstanceId) {
                $instance = orthanc_get('/instances/' . rawurlencode((string) $orthancInstanceId), $orthancUrl, $orthancUser, $orthancPass);
                if (!is_object($instance)) {
                    continue;
                }

                $ins = $instance->MainDicomTags ?? new stdClass();
                $sopUid = safe_string($ins->SOPInstanceUID ?? null);
                if ($sopUid === '') {
                    continue;
                }

                $imageType = '';
                if (isset($ins->ImageType)) {
                    $imageType = is_array($ins->ImageType) ? implode('\\', $ins->ImageType) : safe_string($ins->ImageType);
                }

                $batchInstances[] = [
                    'series_uid' => $seriesUid,
                    'orthanc_instance_id' => safe_string($orthancInstanceId),
                    'sop_uid' => $sopUid,
                    'sop_class' => safe_string($ins->SOPClassUID ?? null),
                    'image_type' => $imageType,
                ];
                $counts['instances']++;

                if (count($batchInstances) >= $batchSize) {
                    batch_insert($conn, 'instance_orthanc', ['series_uid', 'orthanc_instance_id', 'sop_uid', 'sop_class', 'image_type'], $batchInstances);
                    log_message('instance', count($batchInstances));
                    $batchInstances = [];
                }
            }
        }
    }
}

if ($batchPatients !== []) {
    batch_insert($conn, 'pasien_orthanc', ['orthanc_patient_id', 'patient_id', 'nama', 'kelamin', 'tgl_lahir'], $batchPatients);
}
if ($batchStudies !== []) {
    batch_insert($conn, 'study_orthanc', ['patient_id', 'orthanc_study_id', 'study_uid', 'description', 'tanggal', 'institution'], $batchStudies);
}
if ($batchSeries !== []) {
    batch_insert($conn, 'series_orthanc', ['study_uid', 'orthanc_series_id', 'series_uid', 'body_part', 'modality', 'station_name'], $batchSeries);
}
if ($batchInstances !== []) {
    batch_insert($conn, 'instance_orthanc', ['series_uid', 'orthanc_instance_id', 'sop_uid', 'sop_class', 'image_type'], $batchInstances);
}

$conn->close();

echo sprintf(
    "[%s] Sync selesai. patients=%d studies=%d series=%d instances=%d\n",
    date('Y-m-d H:i:s'),
    $counts['patients'],
    $counts['studies'],
    $counts['series'],
    $counts['instances']
);

function load_mysql_config(string $configPath): array
{
    if (!is_file($configPath)) {
        throw new RuntimeException("Config MySQL tidak ditemukan: {$configPath}");
    }

    $json = json_decode((string) file_get_contents($configPath), true);
    if (!is_array($json) || !isset($json['MySQL']) || !is_array($json['MySQL'])) {
        throw new RuntimeException("Format config MySQL tidak valid: {$configPath}");
    }

    $cfg = $json['MySQL'];

    return [
        'host' => (string) ($cfg['Host'] ?? 'localhost'),
        'port' => (int) ($cfg['Port'] ?? 3306),
        'socket' => (string) ($cfg['UnixSocket'] ?? ''),
        'database' => (string) ($cfg['Database'] ?? ''),
        'user' => (string) ($cfg['Username'] ?? ''),
        'pass' => (string) ($cfg['Password'] ?? ''),
    ];
}

function connect_mysql(array $cfg): mysqli
{
    $socket = $cfg['socket'] !== '' ? $cfg['socket'] : null;
    $conn = @new mysqli($cfg['host'], $cfg['user'], $cfg['pass'], $cfg['database'], $cfg['port'], $socket);
    if ($conn->connect_error) {
        throw new RuntimeException('Koneksi MySQL gagal: ' . $conn->connect_error);
    }

    $conn->set_charset('utf8mb4');
    return $conn;
}

function orthanc_get(string $endpoint, string $orthancUrl, string $orthancUser, string $orthancPass)
{
    $ch = curl_init($orthancUrl . $endpoint);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_USERPWD, $orthancUser . ':' . $orthancPass);
    curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($ch, CURLOPT_TIMEOUT, 60);

    $response = curl_exec($ch);
    $status = (int) curl_getinfo($ch, CURLINFO_HTTP_CODE);
    $error = curl_error($ch);
    curl_close($ch);

    if ($response === false || $status !== 200) {
        fwrite(STDERR, sprintf("[%s] Gagal ambil %s HTTP=%d Error=%s\n", date('Y-m-d H:i:s'), $endpoint, $status, $error));
        return null;
    }

    return json_decode($response);
}

function dicom_date($value): ?string
{
    $date = safe_string($value);
    if (strlen($date) !== 8) {
        return null;
    }

    return substr($date, 0, 4) . '-' . substr($date, 4, 2) . '-' . substr($date, 6, 2);
}

function safe_string($value): string
{
    if ($value === null) {
        return '';
    }

    return trim((string) $value);
}

function batch_insert(mysqli $conn, string $table, array $columns, array $rows): void
{
    if ($rows === []) {
        return;
    }

    $values = [];
    foreach ($rows as $row) {
        $escaped = [];
        foreach ($columns as $column) {
            $value = $row[$column] ?? null;
            if ($value === null || $value === '') {
                $escaped[] = 'NULL';
            } else {
                $escaped[] = "'" . $conn->real_escape_string((string) $value) . "'";
            }
        }
        $values[] = '(' . implode(',', $escaped) . ')';
    }

    $updates = [];
    foreach ($columns as $column) {
        $updates[] = $column . '=VALUES(' . $column . ')';
    }

    $sql = 'INSERT INTO ' . $table .
        ' (' . implode(',', $columns) . ') VALUES ' . implode(',', $values) .
        ' ON DUPLICATE KEY UPDATE ' . implode(', ', $updates);

    if (!$conn->query($sql)) {
        throw new RuntimeException('Gagal batch insert ke ' . $table . ': ' . $conn->error);
    }
}

function log_message(string $label, int $count): void
{
    echo sprintf("[%s] Batch %s %d selesai\n", date('Y-m-d H:i:s'), $label, $count);
}
