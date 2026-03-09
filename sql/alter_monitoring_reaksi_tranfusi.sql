-- Tambahan kolom untuk monitoring_reaksi_tranfusi (golongan darah, rhesus, tgl kadaluarsa, HB, dan kebutuhan darah)
ALTER TABLE monitoring_reaksi_tranfusi
    ADD COLUMN golongan_darah VARCHAR(5) DEFAULT NULL AFTER produk_darah,
    ADD COLUMN rhesus VARCHAR(8) DEFAULT NULL AFTER golongan_darah,
    ADD COLUMN tgl_kadaluarsa VARCHAR(10) DEFAULT NULL AFTER rhesus,
    ADD COLUMN hb_pre VARCHAR(10) DEFAULT NULL AFTER lokasi_insersi,
    ADD COLUMN hb_post VARCHAR(10) DEFAULT NULL AFTER hb_pre,
    ADD COLUMN jml_bag VARCHAR(5) DEFAULT NULL AFTER hb_post,
    ADD COLUMN jml_cc VARCHAR(5) DEFAULT NULL AFTER jml_bag;
