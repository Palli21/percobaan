# Analisis Monitoring Verifikasi Klaim BPJS

## Ringkasan

Modul "Monitoring Verifikasi Klaim BPJS" adalah dialog Swing yang:

1. Memanggil API VClaim BPJS endpoint monitoring klaim per tanggal.
2. Mencocokkan hasil API berdasarkan `noSEP` ke data lokal `bridging_sep` atau `bridging_sep_internal`.
3. Menggabungkan umpan balik klaim BPJS dengan komponen biaya lokal SIMRS.
4. Menampilkan hasil ke grid dan dapat dicetak ke report Jasper.

Modul ini tidak membaca data utama dari tabel cache lokal `inacbg_klaim_umpanbalik`. Cache itu hanya diisi oleh tombol "Ambil Ke Lokal".

## File Utama

- `src/bridging/BPJSMonitoringKlaim.java`
- `report/rptBridgingMonitoringSEP.jrxml`
- `src/simrskhanza/frmUtama.java`
- `setting/database.xml`

## Posisi Menu

Menu utama menampilkan tombol:

- `Monitoring Verifikasi Klaim BPJS`

Tombol ini membuka dialog `BPJSMonitoringKlaim`.

## Sumber Konfigurasi API

Konfigurasi diambil dari `setting/database.xml` dengan key:

- `URLAPIBPJS`
- `CONSIDAPIBPJS`
- `SECRETKEYAPIBPJS`
- `USERKEYAPIBPJS`

Endpoint yang dipanggil:

- `/Monitoring/Klaim/Tanggal/{tanggal}/JnsPelayanan/{jenis}/Status/{status}`

## Alur Data

### 1. Filter tanggal lokal

Metode `tampil()` mengambil daftar tanggal unik dari:

- `bridging_sep`
- `bridging_sep_internal`

Rentang tanggal mengikuti filter `TglSEP1` sampai `TglSEP2`.

### 2. Panggil API BPJS

Untuk setiap tanggal, modul memanggil API monitoring per kombinasi:

- Jenis pelayanan: `1` ranap, `2` ralan
- Status: `1` proses verifikasi, `2` pending verifikasi, `3` klaim

Jika filter di layar dipilih "Semua", seluruh kombinasi dipanggil.

### 3. Cocokkan ke data lokal

Setelah respons BPJS diterima, setiap item `klaim` dicocokkan ke:

- `bridging_sep` berdasarkan `no_sep`
- `bridging_sep_internal` berdasarkan `no_sep`

Artinya, record yang tampil hanya klaim yang juga punya SEP lokal.

### 4. Tambah komponen biaya lokal

Sebelum dimasukkan ke grid, modul menghitung komponen biaya lokal per `no_rawat`:

- obat
- tindakan
- radiologi
- lab
- operasi

Semua dihitung dari tabel transaksi SIMRS, bukan dari respons BPJS.

### 5. Cetak report

Saat tombol cetak dipakai:

1. Isi grid dipindah ke tabel `temporary`
2. Jasper `rptBridgingMonitoringSEP.jasper` dipanggil dengan query `temporary`

## Struktur Grid dan Asal Nilai

### Identitas SEP dan pasien

Kolom berikut berasal dari `bridging_sep` atau `bridging_sep_internal`:

- No.SEP
- No.Rawat
- No.RM
- Nama Pasien
- Tanggal SEP
- Tanggal Rujukan
- No.Rujukan
- Kode/Nama PPK Rujukan
- Kode/Nama PPK Pelayanan
- Kode/Nama Diagnosa
- Kode/Nama Poli
- Kelas Rawat
- User Input
- Tgl.Lahir
- Peserta
- J.Kel
- No.Kartu
- Tanggal Pulang
- Asal Rujukan
- Eksekutif
- COB
- Penjamin
- No.Telp
- Kode DPJP
- Nama DPJP

### Umpan balik klaim BPJS

Kolom berikut berasal dari respons API BPJS:

- INACBG
- Status
- No.FPK
- Pengajuan
- Disetujui
- Tarif Gruper
- Tarif RS
- Topup

Field biaya dibaca dari node:

- `biaya.byPengajuan`
- `biaya.bySetujui`
- `biaya.byTarifGruper`
- `biaya.byTarifRS`
- `biaya.byTopup`

### Komponen biaya lokal SIMRS

Kolom berikut dihitung dari transaksi lokal per `no_rawat`:

- Obat
- Tindakan
- Radiologi
- Lab
- Operasi

## Rumus Penting

### Untung/Rugi yang dipakai modul ini

Rumus saat ini:

`Untung/Rugi = Disetujui - Topup - Tarif RS`

Catatan:

- Ini adalah rumus berbasis umpan balik BPJS.
- Rumus ini tidak memakai komponen biaya lokal seperti obat, lab, radiologi, operasi, dan tindakan.
- Jadi hasilnya bukan margin riil RS terhadap biaya layanan, melainkan selisih administratif dari nilai BPJS.

## Mapping Data ke Jasper Report

Report cetak hanya membawa ringkasan dari grid:

- `temp1` = No.SEP
- `temp2` = No.Rawat
- `temp3` = Nama Pasien
- `temp4` = Tanggal SEP
- `temp5` = Jenis Pelayanan
- `temp6` = Pengajuan
- `temp7` = Tarif Gruper
- `temp8` = Tarif RS
- `temp9` = Untung/Rugi

### Ketidaksesuaian label report

Di Jasper, header `temp6` diberi label:

- `Tagihan`

Padahal nilai yang dikirim ke `temp6` adalah:

- `Pengajuan`

Ini adalah mismatch label dan bisa menyesatkan pembaca report.

## Tabel Lokal yang Terlibat

### Tabel SEP

- `bridging_sep`
- `bridging_sep_internal`

Kedua tabel menyimpan metadata SEP yang menjadi dasar pencocokan dengan respons BPJS.

### Tabel cache umpan balik

- `inacbg_klaim_umpanbalik`

Diisi oleh tombol "Ambil Ke Lokal" untuk 7 bulan terakhir, dengan field:

- `no_sep`
- `kode`
- `nama`
- `bypengajuan`
- `bydisetujui`
- `bytarifgrouper`
- `bytarifrs`
- `bytopup`
- `nofpk`
- `status`

Saat ini tabel ini belum dipakai oleh tampilan utama `tampil()`.

### Tabel biaya lokal yang dihitung

Komponen biaya lokal ditarik dari beberapa tabel, antara lain:

- `detail_pemberian_obat`
- `tagihan_obat_langsung`
- `beri_obat_operasi`
- `rawat_jl_dr`
- `rawat_jl_pr`
- `rawat_jl_drpr`
- `rawat_inap_dr`
- `rawat_inap_pr`
- `rawat_inap_drpr`
- `periksa_radiologi`
- `periksa_lab`
- `detail_periksa_lab`
- `operasi`

## Kelemahan Desain Saat Ini

### 1. Sangat bergantung pada API real-time

Setiap pencarian akan:

- loop per tanggal
- loop per jenis pelayanan
- loop per status
- memanggil API berulang

Untuk rentang tanggal panjang, proses bisa lambat.

### 2. Data lokal cache belum dimanfaatkan

Tombol "Ambil Ke Lokal" sudah menyiapkan cache 7 bulan terakhir, tetapi pencarian utama tetap mengambil data dari API. Ini membuat manfaat cache belum terasa.

### 3. Report cetak hanya ringkasan

Grid menyimpan banyak kolom, tetapi Jasper hanya mencetak sedikit kolom ringkasan. Informasi penting seperti status verifikasi detail, diagnosa, poli, DPJP, dan komponen biaya lokal tidak ikut tercetak.

### 4. Label "Tagihan" tidak akurat

Header report memakai istilah `Tagihan`, padahal datanya adalah `Pengajuan`.

### 5. Rumus untung/rugi berpotensi disalahartikan

Karena rumus tidak membandingkan terhadap total komponen biaya lokal, angka "untung/rugi" bisa dianggap sebagai margin operasional padahal bukan.

### 6. Ada duplikasi proses SEP dan SEP internal

Metode `Monitor()` dan `MonitorInternal()` memanggil endpoint API yang sama, hanya beda sumber pencocokan lokal. Ini menambah beban proses.

## Opsi Perbaikan yang Paling Aman

### Opsi 1. Perbaikan label report

Perubahan kecil dan aman:

- ubah label `Tagihan` menjadi `Pengajuan`

Ini aman karena tidak mengubah logika data.

### Opsi 2. Gunakan cache lokal sebagai sumber baca utama

Alur yang lebih ringan:

1. tombol "Ambil Ke Lokal" atau job background mengisi `inacbg_klaim_umpanbalik`
2. layar monitoring membaca join lokal antara:
   - `bridging_sep`
   - `bridging_sep_internal`
   - `inacbg_klaim_umpanbalik`
3. API dipakai hanya untuk refresh data yang belum ada

Keuntungan:

- pencarian lebih cepat
- beban API turun
- report lebih stabil saat koneksi BPJS lambat

### Opsi 3. Pisahkan dua definisi nilai

Supaya tidak rancu:

- `Selisih BPJS = Disetujui - Topup - Tarif RS`
- `Margin Operasional = Disetujui - Total Biaya Lokal`

Dengan begitu pengguna bisa membedakan angka administratif dan angka biaya layanan nyata.

### Opsi 4. Kurangi panggilan API per tanggal

Jika tetap memakai API real-time:

- perkecil rentang tanggal default
- tampilkan progress
- pertimbangkan pagination atau batching jika API mendukung

## Kesimpulan

Secara fungsi, modul ini sudah berhasil menggabungkan umpan balik klaim BPJS dengan data lokal SEP dan biaya SIMRS. Masalah utamanya bukan pada konsep, tetapi pada efisiensi dan kejelasan makna data yang ditampilkan.

Prioritas pembenahan yang paling aman:

1. Benahi label report `Tagihan` menjadi `Pengajuan`
2. Dokumentasikan arti `Untung/Rugi`
3. Pindahkan sumber baca utama ke cache lokal `inacbg_klaim_umpanbalik`

