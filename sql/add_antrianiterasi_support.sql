CREATE TABLE IF NOT EXISTS `antrianiterasi` (
  `no_resep` varchar(14) NOT NULL,
  `status` enum('1. Iterasi 1x','2. Iterasi 2x') DEFAULT NULL,
  PRIMARY KEY (`no_resep`),
  CONSTRAINT `antrianiterasi_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_obat` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
