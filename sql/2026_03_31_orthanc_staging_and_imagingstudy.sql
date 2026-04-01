SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `pasien_orthanc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orthanc_patient_id` varchar(100) NOT NULL,
  `patient_id` varchar(50) DEFAULT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `kelamin` varchar(10) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pasien_orthanc_orthanc_patient_id` (`orthanc_patient_id`),
  KEY `idx_pasien_orthanc_patient_id` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `study_orthanc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(50) DEFAULT NULL,
  `orthanc_study_id` varchar(100) DEFAULT NULL,
  `study_uid` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_study_orthanc_study_uid` (`study_uid`),
  UNIQUE KEY `uk_study_orthanc_orthanc_study_id` (`orthanc_study_id`),
  KEY `idx_study_orthanc_patient_tanggal` (`patient_id`,`tanggal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `series_orthanc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `study_uid` varchar(255) DEFAULT NULL,
  `orthanc_series_id` varchar(100) DEFAULT NULL,
  `series_uid` varchar(255) NOT NULL,
  `body_part` varchar(100) DEFAULT NULL,
  `modality` varchar(50) DEFAULT NULL,
  `station_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_series_orthanc_series_uid` (`series_uid`),
  UNIQUE KEY `uk_series_orthanc_orthanc_series_id` (`orthanc_series_id`),
  KEY `idx_series_orthanc_study_uid` (`study_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `instance_orthanc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `series_uid` varchar(255) DEFAULT NULL,
  `orthanc_instance_id` varchar(100) DEFAULT NULL,
  `sop_uid` varchar(255) NOT NULL,
  `sop_class` varchar(100) DEFAULT NULL,
  `image_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_instance_orthanc_sop_uid` (`sop_uid`),
  UNIQUE KEY `uk_instance_orthanc_orthanc_instance_id` (`orthanc_instance_id`),
  KEY `idx_instance_orthanc_series_uid` (`series_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `satu_sehat_imagingstudy_radiologi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `noorder` varchar(30) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_servicerequest` varchar(100) DEFAULT NULL,
  `study_uid` varchar(255) NOT NULL,
  `id_imagingstudy` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_satu_sehat_imagingstudy_radiologi` (`noorder`,`kd_jenis_prw`,`study_uid`),
  KEY `idx_satu_sehat_imagingstudy_servicerequest` (`id_servicerequest`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
