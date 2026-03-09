-- Migrasi rekap sensus ranap ke skema baru (tanggal_sensus)
-- 1) Rename tabel lama jika ada
-- 2) Buat tabel ledger + snapshot baru

-- Rename tabel lama hanya jika ada dan legacy belum ada
SET @has_old = (
  SELECT COUNT(*) FROM information_schema.tables
  WHERE table_schema = DATABASE() AND table_name = 'rekap_sensus_ranap_harian'
);
SET @has_legacy = (
  SELECT COUNT(*) FROM information_schema.tables
  WHERE table_schema = DATABASE() AND table_name = 'rekap_sensus_ranap_harian_legacy'
);
SET @sql = IF(@has_old = 1 AND @has_legacy = 0,
  'RENAME TABLE rekap_sensus_ranap_harian TO rekap_sensus_ranap_harian_legacy',
  'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- DDL baru
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
