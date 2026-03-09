-- Draft migrasi integrasi Bridging Apotek BPJS
-- Tanggal: 2026-02-28
-- Catatan:
-- 1) Jalankan di database aplikasi yang benar, setelah backup.
-- 2) Script ini idempotent sebisa mungkin.
-- 3) Ini DRAFT, review dulu di staging.

START TRANSACTION;

-- ------------------------------------------------------------------
-- 1) Pastikan kolom konfigurasi apotek BPJS ada di tabel setting
-- ------------------------------------------------------------------
SET @db := DATABASE();

SELECT COUNT(*) INTO @has_kode_apotek
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA=@db AND TABLE_NAME='setting' AND COLUMN_NAME='kode_apotek';

SET @sql := IF(
    @has_kode_apotek=0,
    'ALTER TABLE setting ADD COLUMN kode_apotek VARCHAR(20) NULL AFTER aktifkanbatch',
    'SELECT ''skip setting.kode_apotek'''
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- ------------------------------------------------------------------
-- 2) Extend mapping obat apotek bpjs (harga/retriksi)
-- ------------------------------------------------------------------
SELECT COUNT(*) INTO @has_harga
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA=@db AND TABLE_NAME='maping_obat_apotek_bpjs' AND COLUMN_NAME='harga';

SET @sql := IF(
    @has_harga=0,
    'ALTER TABLE maping_obat_apotek_bpjs ADD COLUMN harga VARCHAR(30) NULL AFTER nama_brng_apotek_bpjs',
    'SELECT ''skip maping_obat_apotek_bpjs.harga'''
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT COUNT(*) INTO @has_retriksi
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA=@db AND TABLE_NAME='maping_obat_apotek_bpjs' AND COLUMN_NAME='retriksi';

SET @sql := IF(
    @has_retriksi=0,
    'ALTER TABLE maping_obat_apotek_bpjs ADD COLUMN retriksi VARCHAR(255) NULL AFTER harga',
    'SELECT ''skip maping_obat_apotek_bpjs.retriksi'''
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- ------------------------------------------------------------------
-- 3) Tabel header/detail bridging apotek bpjs (sesuai modul baru)
-- ------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS bridging_apotek_bpjs (
    no_sep        VARCHAR(40)  NOT NULL,
    no_apotek     VARCHAR(40)  NOT NULL,
    no_resep      VARCHAR(40)  NOT NULL,
    tgl_resep     DATETIME     NULL,
    tgl_pelayanan DATETIME     NULL,
    jenis_obat    VARCHAR(3)   NULL,
    iterasi       VARCHAR(3)   NULL,
    kd_poli       VARCHAR(20)  NULL,
    nm_poli       VARCHAR(100) NULL,
    kd_dpjp       VARCHAR(20)  NULL,
    nm_dpjp       VARCHAR(100) NULL,
    id_user_sep   VARCHAR(50)  NULL,
    PRIMARY KEY (no_sep),
    KEY idx_bridging_apotek_bpjs_no_apotek (no_apotek),
    KEY idx_bridging_apotek_bpjs_no_resep (no_resep),
    CONSTRAINT fk_bridging_apotek_bpjs_sep
      FOREIGN KEY (no_sep)
      REFERENCES bridging_sep (no_sep)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS bridging_apotek_bpjs_obat (
    no_sep   VARCHAR(40)  NOT NULL,
    no_resep VARCHAR(40)  NOT NULL,
    kd_obat  VARCHAR(30)  NOT NULL,
    nm_obat  VARCHAR(255) NULL,
    jumlah   DOUBLE       NULL,
    signa1   VARCHAR(10)  NULL,
    signa2   VARCHAR(10)  NULL,
    racikan  CHAR(1)      NOT NULL DEFAULT '0',
    PRIMARY KEY (no_sep, no_resep, kd_obat, racikan),
    KEY idx_bridging_apotek_bpjs_obat_kd_obat (kd_obat),
    CONSTRAINT fk_bridging_apotek_bpjs_obat_hdr
      FOREIGN KEY (no_sep)
      REFERENCES bridging_apotek_bpjs (no_sep)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ------------------------------------------------------------------
-- 4) Tabel lokal transaksi edit obat apotek bpjs
--    Disamakan dengan struktur tabel asli yang sudah ada.
-- ------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS detail_pemberian_obat_apotekbpjs LIKE detail_pemberian_obat;
CREATE TABLE IF NOT EXISTS obat_racikan_apotekbpjs LIKE obat_racikan;
CREATE TABLE IF NOT EXISTS detail_obat_racikan_apotekbpjs LIKE detail_obat_racikan;

COMMIT;
