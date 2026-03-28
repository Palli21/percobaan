-- Schema untuk form RMPenilaianAwalKeperawatanBayiIGD
-- Jalankan di database yang sama dengan tabel `reg_periksa` dan `petugas`
-- Default Khanza/SIMRSMSG menggunakan database `sik`

USE sik;

DROP TABLE IF EXISTS `penilaian_awal_keperawatan_bayi_igd_rencana`;
DROP TABLE IF EXISTS `penilaian_awal_keperawatan_bayi_igd_masalah`;
DROP TABLE IF EXISTS `penilaian_awal_keperawatan_bayi_igd`;

CREATE TABLE `penilaian_awal_keperawatan_bayi_igd` (
  `no_rawat`                                     varchar(17) NOT NULL,
  `tanggal`                                      datetime NOT NULL,
  `kategori_triage`                              varchar(5) NOT NULL DEFAULT '',
  `keluhan_saat_ini`                             text NOT NULL,
  `alergi_tidak_ada`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Ya',
  `alergi_keterangan`                            varchar(200) NOT NULL DEFAULT '',

  `airway_bebas`                                enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_gargling`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_stridor`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_wheezing`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_ronchi`                               enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_terintubasi`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',

  `breathing_spontan`                           enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_dispneu`                           enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_ventilasi_mekanik`                 enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_tachipneu`                         enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_apneu`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_memakai_ventilator`                enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',

  `nadi_kualitas`                               varchar(20) NOT NULL DEFAULT '',
  `crt`                                         varchar(20) NOT NULL DEFAULT '',
  `warna_kulit`                                 varchar(20) NOT NULL DEFAULT '',
  `perdarahan`                                  varchar(30) NOT NULL DEFAULT '',
  `turgor_kulit`                                varchar(20) NOT NULL DEFAULT '',
  `respirasi`                                   varchar(20) NOT NULL DEFAULT '',
  `tekanan_darah`                               varchar(20) NOT NULL DEFAULT '',
  `respon`                                      varchar(20) NOT NULL DEFAULT '',
  `refleks`                                     varchar(100) NOT NULL DEFAULT '',
  `gcs`                                         varchar(20) NOT NULL DEFAULT '',
  `nadi_neurologi`                              varchar(20) NOT NULL DEFAULT '',
  `suhu`                                        varchar(10) NOT NULL DEFAULT '',

  `skrining_gizi1`                              varchar(150) NOT NULL DEFAULT '',
  `nilai_gizi1`                                 tinyint(4) NOT NULL DEFAULT 0,
  `skrining_gizi2`                              varchar(150) NOT NULL DEFAULT '',
  `nilai_gizi2`                                 tinyint(4) NOT NULL DEFAULT 0,
  `total_skor_gizi`                             tinyint(4) NOT NULL DEFAULT 0,
  `keterangan_gizi`                             varchar(100) NOT NULL DEFAULT '',
  `dietisien_diberitahu`                        enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `jam_lapor_dietisien`                         varchar(5) NOT NULL DEFAULT '',

  `fungsional_tidak`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `fungsional_tongkat`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `fungsional_kursi_roda`                       enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `fungsional_lain`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `fungsional_lain_keterangan`                  varchar(200) NOT NULL DEFAULT '',
  `cacat_tubuh`                                 varchar(200) NOT NULL DEFAULT '',

  `humpty_skala1`                               varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai1`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala2`                               varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai2`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala3`                               varchar(200) NOT NULL DEFAULT '',
  `humpty_nilai3`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala4`                               varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai4`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala5`                               varchar(200) NOT NULL DEFAULT '',
  `humpty_nilai5`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala6`                               varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai6`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala7`                               varchar(200) NOT NULL DEFAULT '',
  `humpty_nilai7`                               tinyint(4) NOT NULL DEFAULT 0,
  `humpty_total`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_keterangan`                           varchar(80) NOT NULL DEFAULT '',

  `nips_skala1`                                 varchar(200) NOT NULL DEFAULT '',
  `nips_nilai1`                                 tinyint(4) NOT NULL DEFAULT 0,
  `nips_skala2`                                 varchar(200) NOT NULL DEFAULT '',
  `nips_nilai2`                                 tinyint(4) NOT NULL DEFAULT 0,
  `nips_skala3`                                 varchar(200) NOT NULL DEFAULT '',
  `nips_nilai3`                                 tinyint(4) NOT NULL DEFAULT 0,
  `nips_skala4`                                 varchar(200) NOT NULL DEFAULT '',
  `nips_nilai4`                                 tinyint(4) NOT NULL DEFAULT 0,
  `nips_skala5`                                 varchar(200) NOT NULL DEFAULT '',
  `nips_nilai5`                                 tinyint(4) NOT NULL DEFAULT 0,
  `nips_total`                                  tinyint(4) NOT NULL DEFAULT 0,
  `nips_keterangan`                             varchar(80) NOT NULL DEFAULT '',

  `penilaian_kondisi`                           text NOT NULL,
  `discharge_rawat_gabung`                      enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_diselimuti_hangat`                 enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_kirim_ke_ruang_bayi`               enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_vitamin_k`                         enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_observasi_suhu`                    enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_pemantauan_glukosa`                enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_edukasi_keluarga`                  enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `discharge_lainnya`                           text NOT NULL,

  `diagnosis_bersihan_jalan_napas_tidak_efektif` enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_pola_napas_tidak_efektif`          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_gangguan_pertukaran_gas`           enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_termoregulasi_tidak_efektif`       enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_ketidakseimbangan_nutrisi`         enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_ikterik_berhubungan_menyusui`      enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_kurang_volume_cairan`              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_diare`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_perfusi_jaringan_gastrointestinal` enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_kesiapan_peningkatan_nutrisi`      enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_menyusui_tidak_efektif`            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_risiko_infeksi`                    enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_risiko_aspirasi`                   enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `diagnosis_risiko_kerusakan_integritas_kulit` enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',

  `psikologis_cemas`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_takut`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_marah`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_sedih`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `mental_orientasi_baik`                       enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `mental_masalah_perilaku`                     enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `mental_masalah_perilaku_keterangan`          varchar(200) NOT NULL DEFAULT '',
  `mental_perilaku_kekerasan`                   enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `status_sosial`                               varchar(100) NOT NULL DEFAULT '',
  `tempat_tinggal`                              varchar(100) NOT NULL DEFAULT '',
  `hubungan_keluarga`                           varchar(200) NOT NULL DEFAULT '',
  `orang_tua_wali_bayi`                         varchar(120) NOT NULL DEFAULT '',
  `petugas_display`                             varchar(120) NOT NULL DEFAULT '',
  `nip`                                         varchar(20) DEFAULT NULL,

  PRIMARY KEY (`no_rawat`),
  KEY `idx_penilaian_awal_keperawatan_bayi_igd_tanggal` (`tanggal`),
  KEY `idx_penilaian_awal_keperawatan_bayi_igd_nip` (`nip`),
  CONSTRAINT `penilaian_awal_keperawatan_bayi_igd_ibfk_1`
    FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_awal_keperawatan_bayi_igd_ibfk_2`
    FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE `penilaian_awal_keperawatan_bayi_igd_masalah` (
  `no_rawat` varchar(17) NOT NULL,
  `kode_masalah` varchar(3) NOT NULL,
  PRIMARY KEY (`no_rawat`,`kode_masalah`),
  KEY `kode_masalah` (`kode_masalah`),
  CONSTRAINT `penilaian_awal_keperawatan_bayi_igd_masalah_ibfk_1`
    FOREIGN KEY (`no_rawat`) REFERENCES `penilaian_awal_keperawatan_bayi_igd` (`no_rawat`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_awal_keperawatan_bayi_igd_masalah_ibfk_2`
    FOREIGN KEY (`kode_masalah`) REFERENCES `master_masalah_keperawatan_anak` (`kode_masalah`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE `penilaian_awal_keperawatan_bayi_igd_rencana` (
  `no_rawat` varchar(17) NOT NULL,
  `kode_rencana` varchar(3) NOT NULL,
  PRIMARY KEY (`no_rawat`,`kode_rencana`),
  KEY `kode_rencana` (`kode_rencana`),
  CONSTRAINT `penilaian_awal_keperawatan_bayi_igd_rencana_ibfk_1`
    FOREIGN KEY (`no_rawat`) REFERENCES `penilaian_awal_keperawatan_bayi_igd` (`no_rawat`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_awal_keperawatan_bayi_igd_rencana_ibfk_2`
    FOREIGN KEY (`kode_rencana`) REFERENCES `master_rencana_keperawatan_anak` (`kode_rencana`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Schema untuk form RMPenilaianAwalKeperawatanAnakIGD
DROP TABLE IF EXISTS `penilaian_awal_keperawatan_anak_igd`;

CREATE TABLE `penilaian_awal_keperawatan_anak_igd` (
  `no_rawat`                                     varchar(17) NOT NULL,
  `tanggal`                                      datetime NOT NULL,
  `kategori_triage`                              varchar(5) NOT NULL DEFAULT '',
  `keluhan_saat_ini`                             text NOT NULL,
  `alergi_tidak_ada`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Ya',
  `alergi_keterangan`                            varchar(200) NOT NULL DEFAULT '',

  `airway_bebas`                                 enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_gargling`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_stridor`                               enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_wheezing`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_ronchi`                                enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `airway_terintubasi`                           enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',

  `breathing_spontan`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_dispneu`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_ventilasi_mekanik`                  enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_tachipneu`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_apneu`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `breathing_memakai_ventilator`                 enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',

  `nadi_kualitas`                                varchar(20) NOT NULL DEFAULT '',
  `crt`                                          varchar(20) NOT NULL DEFAULT '',
  `warna_kulit`                                  varchar(20) NOT NULL DEFAULT '',
  `perdarahan`                                   varchar(30) NOT NULL DEFAULT '',
  `turgor_kulit`                                 varchar(20) NOT NULL DEFAULT '',
  `respirasi`                                    varchar(20) NOT NULL DEFAULT '',
  `tekanan_darah`                                varchar(20) NOT NULL DEFAULT '',
  `respon`                                       varchar(20) NOT NULL DEFAULT '',
  `pupil`                                        varchar(20) NOT NULL DEFAULT '',
  `refleks`                                      varchar(100) NOT NULL DEFAULT '',
  `gcs_e`                                        varchar(5) NOT NULL DEFAULT '',
  `gcs_v`                                        varchar(5) NOT NULL DEFAULT '',
  `gcs_m`                                        varchar(5) NOT NULL DEFAULT '',
  `nadi_neurologi`                               varchar(20) NOT NULL DEFAULT '',
  `suhu`                                         varchar(10) NOT NULL DEFAULT '',

  `skrining_gizi1`                               varchar(150) NOT NULL DEFAULT '',
  `nilai_gizi1`                                  tinyint(4) NOT NULL DEFAULT 0,
  `skrining_gizi2`                               varchar(150) NOT NULL DEFAULT '',
  `nilai_gizi2`                                  tinyint(4) NOT NULL DEFAULT 0,
  `total_skor_gizi`                              tinyint(4) NOT NULL DEFAULT 0,
  `keterangan_gizi`                              varchar(100) NOT NULL DEFAULT '',
  `gizi_status_dietisien`                        varchar(50) NOT NULL DEFAULT '',
  `gizi_lapor`                                   varchar(50) NOT NULL DEFAULT '',
  `gizi_tanggal`                                 varchar(10) NOT NULL DEFAULT '',
  `gizi_jam`                                     varchar(5) NOT NULL DEFAULT '',

  `alat_bantu_tidak`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `alat_bantu_tongkat`                           enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `alat_bantu_kruk`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `alat_bantu_kursi_roda`                        enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `alat_bantu_lain`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `alat_bantu_lain_keterangan`                   varchar(200) NOT NULL DEFAULT '',
  `cacat_tubuh_status`                           varchar(30) NOT NULL DEFAULT '',
  `cacat_tubuh_keterangan`                       varchar(200) NOT NULL DEFAULT '',

  `perawatan_minimal_1`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_minimal_2`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_minimal_3`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_minimal_4`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_minimal_5`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_parsial_1`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_parsial_2`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_parsial_3`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_parsial_4`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_parsial_5`                          enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_total_1`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_total_2`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_total_3`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_total_4`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `perawatan_total_5`                            enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `klasifikasi_pasien`                           varchar(30) NOT NULL DEFAULT '',
  `aktivitas_pasien`                             varchar(50) NOT NULL DEFAULT '',

  `humpty_skala1`                                varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai1`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala2`                                varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai2`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala3`                                varchar(200) NOT NULL DEFAULT '',
  `humpty_nilai3`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala4`                                varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai4`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala5`                                varchar(200) NOT NULL DEFAULT '',
  `humpty_nilai5`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala6`                                varchar(150) NOT NULL DEFAULT '',
  `humpty_nilai6`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_skala7`                                varchar(200) NOT NULL DEFAULT '',
  `humpty_nilai7`                                tinyint(4) NOT NULL DEFAULT 0,
  `humpty_total`                                 tinyint(4) NOT NULL DEFAULT 0,
  `humpty_keterangan`                            varchar(80) NOT NULL DEFAULT '',

  `nrs`                                          varchar(20) NOT NULL DEFAULT '',
  `wong_baker`                                   varchar(50) NOT NULL DEFAULT '',
  `flacc_skala1`                                 varchar(200) NOT NULL DEFAULT '',
  `flacc_nilai1`                                 varchar(5) NOT NULL DEFAULT '',
  `flacc_skala2`                                 varchar(200) NOT NULL DEFAULT '',
  `flacc_nilai2`                                 varchar(5) NOT NULL DEFAULT '',
  `flacc_skala3`                                 varchar(200) NOT NULL DEFAULT '',
  `flacc_nilai3`                                 varchar(5) NOT NULL DEFAULT '',
  `flacc_skala4`                                 varchar(200) NOT NULL DEFAULT '',
  `flacc_nilai4`                                 varchar(5) NOT NULL DEFAULT '',
  `flacc_skala5`                                 varchar(200) NOT NULL DEFAULT '',
  `flacc_nilai5`                                 varchar(5) NOT NULL DEFAULT '',
  `flacc_total`                                  varchar(5) NOT NULL DEFAULT '',
  `nyeri_frekuensi`                              varchar(50) NOT NULL DEFAULT '',
  `nyeri_lama`                                   varchar(100) NOT NULL DEFAULT '',
  `nyeri_menjalar`                               varchar(20) NOT NULL DEFAULT '',
  `nyeri_menjalar_ke`                            varchar(100) NOT NULL DEFAULT '',
  `nyeri_kualitas`                               varchar(50) NOT NULL DEFAULT '',
  `nyeri_pencetus`                               text NOT NULL,
  `nyeri_pengurang`                              text NOT NULL,
  `lokasi_nyeri`                                 text NOT NULL,
  `tindak_lanjut_edukasi`                        enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `tindak_lanjut_intervensi`                     enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `tindak_lanjut_konsul`                         enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `tindak_lanjut_konsul_ke`                      varchar(120) NOT NULL DEFAULT '',

  `psikologis_cemas`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_takut`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_marah`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_sedih`                             enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_lain`                              enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `psikologis_lain_keterangan`                   varchar(200) NOT NULL DEFAULT '',
  `mental_orientasi_baik`                        enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `mental_masalah_perilaku`                      enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `mental_masalah_perilaku_keterangan`           varchar(200) NOT NULL DEFAULT '',
  `mental_perilaku_kekerasan`                    enum('Ya','Tidak') NOT NULL DEFAULT 'Tidak',
  `mental_perilaku_kekerasan_keterangan`         varchar(200) NOT NULL DEFAULT '',
  `status_sosial`                                varchar(100) NOT NULL DEFAULT '',
  `tempat_tinggal`                               varchar(100) NOT NULL DEFAULT '',
  `tempat_tinggal_keterangan`                    varchar(200) NOT NULL DEFAULT '',
  `hubungan_keluarga`                            varchar(200) NOT NULL DEFAULT '',
  `ibadah_teratur`                               varchar(20) NOT NULL DEFAULT '',
  `tempat_ttd`                                   varchar(120) NOT NULL DEFAULT '',
  `pasien_keluarga_ttd`                          varchar(120) NOT NULL DEFAULT '',
  `petugas_display`                              varchar(120) NOT NULL DEFAULT '',
  `nip`                                          varchar(20) DEFAULT NULL,

  PRIMARY KEY (`no_rawat`),
  KEY `idx_penilaian_awal_keperawatan_anak_igd_tanggal` (`tanggal`),
  KEY `idx_penilaian_awal_keperawatan_anak_igd_nip` (`nip`),
  CONSTRAINT `penilaian_awal_keperawatan_anak_igd_ibfk_1`
    FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_awal_keperawatan_anak_igd_ibfk_2`
    FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
