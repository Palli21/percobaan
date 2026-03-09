-- Tambahan kolom untuk menyimpan 3 entri Bagian / Diminta Jam / Datang Jam pada penilaian_medis_igd
ALTER TABLE penilaian_medis_igd
    ADD COLUMN bagian1 VARCHAR(150) NOT NULL DEFAULT '' AFTER ket_cara_datang,
    ADD COLUMN diminta_jam1 VARCHAR(20) NOT NULL DEFAULT '' AFTER bagian1,
    ADD COLUMN datang_jam1 VARCHAR(20) NOT NULL DEFAULT '' AFTER diminta_jam1,
    ADD COLUMN bagian2 VARCHAR(150) NOT NULL DEFAULT '' AFTER datang_jam1,
    ADD COLUMN diminta_jam2 VARCHAR(20) NOT NULL DEFAULT '' AFTER bagian2,
    ADD COLUMN datang_jam2 VARCHAR(20) NOT NULL DEFAULT '' AFTER diminta_jam2,
    ADD COLUMN bagian3 VARCHAR(150) NOT NULL DEFAULT '' AFTER datang_jam2,
    ADD COLUMN diminta_jam3 VARCHAR(20) NOT NULL DEFAULT '' AFTER bagian3,
    ADD COLUMN datang_jam3 VARCHAR(20) NOT NULL DEFAULT '' AFTER diminta_jam3;
