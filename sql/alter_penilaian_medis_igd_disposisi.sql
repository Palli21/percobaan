-- Tambahan kolom disposisi pada penilaian_medis_igd
ALTER TABLE penilaian_medis_igd
    ADD COLUMN observasi ENUM('Ya','Tidak') NOT NULL DEFAULT 'Tidak' AFTER datang_jam3,
    ADD COLUMN dipulangkan ENUM('Ya','Tidak') NOT NULL DEFAULT 'Tidak' AFTER observasi,
    ADD COLUMN paps_dirujuk ENUM('Ya','Tidak') NOT NULL DEFAULT 'Tidak' AFTER dipulangkan,
    ADD COLUMN dirujuk ENUM('Ya','Tidak') NOT NULL DEFAULT 'Tidak' AFTER paps_dirujuk,
    ADD COLUMN dirujuk_ke VARCHAR(150) NOT NULL DEFAULT '' AFTER dirujuk,
    ADD COLUMN rawat_inap ENUM('Ya','Tidak') NOT NULL DEFAULT 'Tidak' AFTER dirujuk_ke,
    ADD COLUMN rawat_inap_ke VARCHAR(150) NOT NULL DEFAULT '' AFTER rawat_inap;
