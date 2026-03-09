-- Snapshot harian sensus ranap per bangsal (jam 23:59) dengan MySQL Event Scheduler
-- Jalankan dengan user yang punya hak CREATE ROUTINE dan EVENT.
-- Pastikan event scheduler aktif:
--   SET GLOBAL event_scheduler = ON;

CREATE TABLE IF NOT EXISTS `rekap_sensus_ranap_harian` (
  `tanggal` date NOT NULL,
  `kd_bangsal` char(5) NOT NULL,
  `total_isi` int NOT NULL DEFAULT 0,
  `vvip` int NOT NULL DEFAULT 0,
  `vip` int NOT NULL DEFAULT 0,
  `kelas1` int NOT NULL DEFAULT 0,
  `kelas2` int NOT NULL DEFAULT 0,
  `kelas3` int NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`tanggal`,`kd_bangsal`),
  KEY `kd_bangsal` (`kd_bangsal`),
  CONSTRAINT `rekap_sensus_ranap_harian_ibfk_1`
    FOREIGN KEY (`kd_bangsal`) REFERENCES `bangsal` (`kd_bangsal`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP PROCEDURE IF EXISTS `sp_snapshot_sensus_ranap`;
DELIMITER //
CREATE PROCEDURE `sp_snapshot_sensus_ranap`(IN p_tanggal DATE)
BEGIN
  INSERT INTO rekap_sensus_ranap_harian
    (tanggal, kd_bangsal, total_isi, vvip, vip, kelas1, kelas2, kelas3)
  SELECT
    p_tanggal,
    kamar.kd_bangsal,
    COUNT(kamar.kd_kamar) AS total_isi,
    COUNT(DISTINCT CASE
      WHEN lower(kamar.kelas) like '%vvip%' THEN kamar.kd_kamar
    END) AS vvip,
    COUNT(DISTINCT CASE
      WHEN lower(kamar.kelas) like '%vip%' AND lower(kamar.kelas) not like '%vvip%' THEN kamar.kd_kamar
    END) AS vip,
    COUNT(DISTINCT CASE
      WHEN lower(kamar.kelas) like '%kelas 1%' OR lower(kamar.kelas) like '%kelas i%'
        OR lower(kamar.kelas)='i' OR lower(kamar.kelas)='1' THEN kamar.kd_kamar
    END) AS kelas1,
    COUNT(DISTINCT CASE
      WHEN lower(kamar.kelas) like '%kelas 2%' OR lower(kamar.kelas) like '%kelas ii%'
        OR lower(kamar.kelas)='ii' OR lower(kamar.kelas)='2' THEN kamar.kd_kamar
    END) AS kelas2,
    COUNT(DISTINCT CASE
      WHEN lower(kamar.kelas) like '%kelas 3%' OR lower(kamar.kelas) like '%kelas iii%'
        OR lower(kamar.kelas)='iii' OR lower(kamar.kelas)='3' THEN kamar.kd_kamar
    END) AS kelas3
  FROM kamar
  INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal
  WHERE kamar.statusdata='1' AND kamar.status='ISI'
  GROUP BY kamar.kd_bangsal
  ON DUPLICATE KEY UPDATE
    total_isi=VALUES(total_isi),
    vvip=VALUES(vvip),
    vip=VALUES(vip),
    kelas1=VALUES(kelas1),
    kelas2=VALUES(kelas2),
    kelas3=VALUES(kelas3),
    updated_at=CURRENT_TIMESTAMP;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS `sp_backfill_sensus_ranap`;
DELIMITER //
CREATE PROCEDURE `sp_backfill_sensus_ranap`(IN p_tgl_awal DATE, IN p_tgl_akhir DATE)
BEGIN
  DECLARE v_tgl DATE;
  SET v_tgl = p_tgl_awal;
  WHILE v_tgl <= p_tgl_akhir DO
    INSERT INTO rekap_sensus_ranap_harian
      (tanggal, kd_bangsal, total_isi, vvip, vip, kelas1, kelas2, kelas3)
    SELECT
      v_tgl,
      kamar.kd_bangsal,
      COUNT(DISTINCT kamar.kd_kamar) AS total_isi,
      COUNT(DISTINCT CASE
        WHEN lower(kamar.kelas) like '%vvip%' THEN kamar.kd_kamar
      END) AS vvip,
      COUNT(DISTINCT CASE
        WHEN lower(kamar.kelas) like '%vip%' AND lower(kamar.kelas) not like '%vvip%' THEN kamar.kd_kamar
      END) AS vip,
      COUNT(DISTINCT CASE
        WHEN lower(kamar.kelas) like '%kelas 1%' OR lower(kamar.kelas) like '%kelas i%'
          OR lower(kamar.kelas)='i' OR lower(kamar.kelas)='1' THEN kamar.kd_kamar
      END) AS kelas1,
      COUNT(DISTINCT CASE
        WHEN lower(kamar.kelas) like '%kelas 2%' OR lower(kamar.kelas) like '%kelas ii%'
          OR lower(kamar.kelas)='ii' OR lower(kamar.kelas)='2' THEN kamar.kd_kamar
      END) AS kelas2,
      COUNT(DISTINCT CASE
        WHEN lower(kamar.kelas) like '%kelas 3%' OR lower(kamar.kelas) like '%kelas iii%'
          OR lower(kamar.kelas)='iii' OR lower(kamar.kelas)='3' THEN kamar.kd_kamar
      END) AS kelas3
    FROM kamar_inap
    INNER JOIN kamar ON kamar_inap.kd_kamar=kamar.kd_kamar
    INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal
    WHERE kamar_inap.tgl_masuk<=v_tgl
      AND (kamar_inap.tgl_keluar='0000-00-00' OR kamar_inap.tgl_keluar IS NULL OR kamar_inap.tgl_keluar>v_tgl)
    GROUP BY kamar.kd_bangsal
    ON DUPLICATE KEY UPDATE
      total_isi=VALUES(total_isi),
      vvip=VALUES(vvip),
      vip=VALUES(vip),
      kelas1=VALUES(kelas1),
      kelas2=VALUES(kelas2),
      kelas3=VALUES(kelas3),
      updated_at=CURRENT_TIMESTAMP;
    SET v_tgl = DATE_ADD(v_tgl, INTERVAL 1 DAY);
  END WHILE;
END//
DELIMITER ;

DROP EVENT IF EXISTS `ev_snapshot_sensus_ranap`;
CREATE EVENT `ev_snapshot_sensus_ranap`
ON SCHEDULE EVERY 1 DAY
STARTS TIMESTAMP(CURRENT_DATE, '23:59:00')
DO
  CALL sp_snapshot_sensus_ranap(CURDATE());

-- Contoh backfill:
-- CALL sp_backfill_sensus_ranap('2024-01-01', CURDATE());
