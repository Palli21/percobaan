# Mapping Struktur Modul Apotek Online: RS2024 vs SIMRSMSG

## Ruang Lingkup

Dokumen ini memetakan struktur modul apotek online BPJS antara:

- `RS2024`
- `SIMRSMSG`

Fokus perbandingan:

- file inti `src/bridging/ApotekBPJS*.java`
- file desktop yang memanggil modul apotek online
- lapisan web `penyerahanresep` dan `penerimaanapotek`
- lapisan pasien `epasien` yang terkait penyerahan resep

## Ringkasan

- Inti bridging apotek online di dua repo sebagian besar sama.
- `RS2024` lebih lengkap di sisi dialog desktop BPJS apotek.
- `SIMRSMSG` lebih lengkap di sisi web, persetujuan pasien, dan iterasi resep.
- `RS2024` sudah punya referensi URL ke `penyerahanresep` dan `penerimaanapotek`, tetapi source `webapps`-nya tidak ada di repo.

## Mapping 1:1 File Inti

File berikut ada di kedua repo dan merupakan pasangan langsung:

| RS2024 | SIMRSMSG | Catatan |
| --- | --- | --- |
| `src/bridging/ApotekBPJSCekReferensiDPHO.java` | `src/bridging/ApotekBPJSCekReferensiDPHO.java` | Sama area referensi DPHO |
| `src/bridging/ApotekBPJSCekReferensiFaskes.java` | `src/bridging/ApotekBPJSCekReferensiFaskes.java` | Sama area referensi faskes |
| `src/bridging/ApotekBPJSCekReferensiObat.java` | `src/bridging/ApotekBPJSCekReferensiObat.java` | Sama area referensi obat |
| `src/bridging/ApotekBPJSCekReferensiPoli.java` | `src/bridging/ApotekBPJSCekReferensiPoli.java` | Sama area referensi poli |
| `src/bridging/ApotekBPJSCekReferensiSettingPPK.java` | `src/bridging/ApotekBPJSCekReferensiSettingPPK.java` | Sama area referensi setting PPK |
| `src/bridging/ApotekBPJSCekReferensiSpesialistik.java` | `src/bridging/ApotekBPJSCekReferensiSpesialistik.java` | Sama area referensi spesialistik |
| `src/bridging/ApotekBPJSDaftarPelayananObat.java` | `src/bridging/ApotekBPJSDaftarPelayananObat.java` | Sama area daftar pelayanan |
| `src/bridging/ApotekBPJSDaftarResepObat.java` | `src/bridging/ApotekBPJSDaftarResepObat.java` | Sama area daftar resep |
| `src/bridging/ApotekBPJSInputResepObat.java` | `src/bridging/ApotekBPJSInputResepObat.java` | Sama area input resep |
| `src/bridging/ApotekBPJSKunjunganSEP.java` | `src/bridging/ApotekBPJSKunjunganSEP.java` | Sama area kunjungan SEP |
| `src/bridging/ApotekBPJSMapingObat.java` | `src/bridging/ApotekBPJSMapingObat.java` | Sama area mapping obat |
| `src/bridging/ApotekBPJSMonitoringKlaim.java` | `src/bridging/ApotekBPJSMonitoringKlaim.java` | Sama area monitoring klaim |
| `src/bridging/ApotekBPJSPermintaanIterResep.java` | `src/bridging/ApotekBPJSPermintaanIterResep.java` | Sama area iterasi resep |
| `src/bridging/ApotekBPJSRekapPesertaPRB.java` | `src/bridging/ApotekBPJSRekapPesertaPRB.java` | Sama area rekap PRB |
| `src/bridging/ApotekBPJSResepObat.java` | `src/bridging/ApotekBPJSResepObat.java` | Sama area resep obat |
| `src/bridging/ApotekBPJSRiwayatPelayananObat.java` | `src/bridging/ApotekBPJSRiwayatPelayananObat.java` | Sama area riwayat pelayanan obat |

## Hanya Ada di RS2024

File berikut hanya ditemukan di `RS2024`:

- `src/bridging/ApotekBPJSDaftarPelayananObat2.java`
- `src/bridging/ApotekBPJSKirimObat.java`
- `src/bridging/ApotekBPJSKirimObatEditObat.java`
- `src/bridging/ApotekBPJSRiwayatObat.java`
- `src/bridging/ApotekBPJSRiwayatPelayananResep.java`

Catatan struktur:

- `ApotekBPJSKirimObat.java` memanggil `ApotekBPJSDaftarPelayananObat2.java`.
- `ApotekBPJSKirimObatEditObat.java` menjadi pasangan edit untuk alur kirim obat.
- `ApotekBPJSRiwayatObat.java` dan `ApotekBPJSRiwayatPelayananResep.java` menambah area penelusuran riwayat yang tidak ada di `SIMRSMSG`.
- Dari pencarian referensi di source, file-file unik ini belum tampak terhubung ke `frmUtama.java`, jadi kemungkinan masih dipanggil dari dialog lain atau belum diekspos di menu utama.

## Hanya Ada di SIMRSMSG

File desktop unik:

- `src/bridging/ApotekBPJSDaftarPermintaanResepIterasi.java`

Lapisan web unik:

- `webapps/penyerahanresep/`
- `webapps/penerimaanapotek/`

Lapisan pasien unik:

- `epasien/pages/ambilpersetujuanpenyerahanresepralan.php`
- `epasien/pages/listhasilpersetujuanpenyerahanresepralan.php`
- `epasien/pages/listriwayatpersetujuanpenyerahanresepralan.php`

Titik integrasi tambahan:

- `webapps/index.php`
- `epasien/conf/command.php`
- `src/simrskhanza/frmUtama.java`

Catatan struktur:

- `ApotekBPJSDaftarPermintaanResepIterasi.java` sudah terhubung ke menu utama melalui `frmUtama.java`.
- `penyerahanresep` menangani foto/bukti penyerahan resep.
- `penerimaanapotek` menangani foto/bukti penerimaan barang/faktur apotek.
- `epasien` dipakai untuk persetujuan pasien terhadap penyerahan resep rawat jalan.

## File Pendukung Bersama yang Perlu Dicek Saat Backport

Walaupun file-file berikut ada di kedua repo, mereka tetap perlu dicek karena menjadi titik sambung antara desktop dan web:

- `src/inventory/DlgDaftarPermintaanResep.java`
- `src/inventory/DlgResepObat.java`
- `src/inventory/DlgCariPemesanan.java`
- `src/keuangan/KeuanganTagihanObatBHP.java`
- `src/keuangan/KeuanganHutangObatBelumLunas.java`
- `setting/database.xml`

Hal yang sudah tampak dari struktur:

- `DlgResepObat.java` di kedua repo memuat foto bukti dari jalur `penyerahanresep`.
- `DlgCariPemesanan.java` di kedua repo memanggil login ke `penerimaanapotek`.
- `KeuanganTagihanObatBHP.java` dan `KeuanganHutangObatBelumLunas.java` di kedua repo juga menampilkan foto dari `penerimaanapotek`.
- Artinya, walaupun `RS2024` tidak menyimpan source `webapps`, desktop-nya sudah didesain untuk berinteraksi dengan web hybrid tersebut.

## Mapping Backport yang Disarankan

### Jika targetnya melengkapi RS2024 dengan flow web seperti SIMRSMSG

Minimal yang perlu dibawa dari `SIMRSMSG`:

- `src/bridging/ApotekBPJSDaftarPermintaanResepIterasi.java`
- `webapps/penyerahanresep/`
- `webapps/penerimaanapotek/`
- `epasien/pages/ambilpersetujuanpenyerahanresepralan.php`
- `epasien/pages/listhasilpersetujuanpenyerahanresepralan.php`
- `epasien/pages/listriwayatpersetujuanpenyerahanresepralan.php`
- penyesuaian `epasien/conf/command.php`
- penyesuaian `webapps/index.php`
- hook menu di `src/simrskhanza/frmUtama.java`

Tambahan yang perlu diverifikasi:

- tabel `bukti_penyerahan_resep_obat`
- folder upload web
- konfigurasi hybrid web di `setting/database.xml`

### Jika targetnya melengkapi SIMRSMSG dengan fitur desktop dari RS2024

Minimal yang perlu dibawa dari `RS2024`:

- `src/bridging/ApotekBPJSDaftarPelayananObat2.java`
- `src/bridging/ApotekBPJSKirimObat.java`
- `src/bridging/ApotekBPJSKirimObatEditObat.java`
- `src/bridging/ApotekBPJSRiwayatObat.java`
- `src/bridging/ApotekBPJSRiwayatPelayananResep.java`

Tambahan yang perlu diverifikasi:

- apakah dialog-dialog tersebut perlu menu baru di `frmUtama.java`
- apakah ada report `.jasper` pendukung yang juga harus ikut
- apakah ada schema tabel tambahan yang diasumsikan oleh file-file tersebut

## Checklist Sinkronisasi File Pendukung

Hasil cek file pendukung apotek online yang sudah dibandingkan:

- `src/inventory/DlgDaftarPermintaanResep.java`
- `src/inventory/DlgResepObat.java`
- `src/inventory/DlgCariPemesanan.java`
- `src/keuangan/KeuanganTagihanObatBHP.java`
- `src/keuangan/KeuanganHutangObatBelumLunas.java`
- `setting/database.xml`

Status sinkronisasi:

- `DlgResepObat.java`: hook `penyerahanresep` identik di dua repo.
- `DlgCariPemesanan.java`: hook login `penerimaanapotek` dan load foto identik di dua repo.
- `KeuanganTagihanObatBHP.java`: load foto `penerimaanapotek` identik di dua repo.
- `KeuanganHutangObatBelumLunas.java`: load foto `penerimaanapotek` identik di dua repo.
- `setting/database.xml`: nilai `HOSTHYBRIDWEB`, `USERHYBRIDWEB`, `PASHYBRIDWEB`, `HYBRIDWEB`, `PORTWEB`, `URLAPIAPOTEKBPJS`, `SECRETKEYAPIAPOTEKBPJS`, `CONSIDAPIAPOTEKBPJS`, `USERKEYAPIAPOTEKBPJS`, `JADIKANPIUTANGAPOTEKBPJS`, dan `KODEPPKAPOTEKBPJS` sama di dua repo.
- `DlgDaftarPermintaanResep.java`: hook apotek online hampir sama, tetapi `RS2024` punya tambahan update `tgl_penyerahan` dan `jam_penyerahan` pada `resep_obat` saat penyerahan resep.
- `setting/database.xml`: `SIMRSMSG` punya `AKTIFKANRESEPITERDOKTER=yes`, sedangkan `RS2024` belum punya entry itu.

Checklist patch yang disarankan:

### Patch A: Keputusan soal timestamp penyerahan resep

- Tentukan apakah perilaku `RS2024` akan dijadikan standar.
- Jika iya, pertahankan logika update:
  - `update resep_obat set tgl_penyerahan=now(), jam_penyerahan=current_time()`
- Jika ingin `SIMRSMSG` disamakan, patch targetnya:
  - `SIMRSMSG/src/inventory/DlgDaftarPermintaanResep.java`

### Patch B: Membawa toggle iterasi resep dokter ke RS2024

File yang perlu disentuh:

- `RS2024/setting/database.xml`
- `RS2024/src/fungsi/koneksiDB.java`
- `RS2024/src/simrskhanza/DlgRawatJalan.java`

Langkah teknis:

- Tambahkan entry `AKTIFKANRESEPITERDOKTER` ke `setting/database.xml`
- Tambahkan getter `AKTIFKANRESEPITERDOKTER()` ke `fungsi/koneksiDB.java`
- Port gating dari `SIMRSMSG/src/simrskhanza/DlgRawatJalan.java` yang memeriksa:
  - `if(koneksiDB.AKTIFKANRESEPITERDOKTER().equals("yes"))`
- Jika memang ingin fitur iterasi resep dari sisi rawat jalan ikut aktif, port juga komponen berikut dari `SIMRSMSG` ke `RS2024`:
  - deklarasi `BtnResepIterasiBPJS`
  - inisialisasi tombol
  - `ActionPerformed`
  - `FormMenu.add(BtnResepIterasiBPJS)`
  - logika `setVisible(...)`

Catatan implementasi:

- `RS2024` sekarang sudah punya menu utama `frmUtama.java` untuk membuka `ApotekBPJSDaftarPermintaanResepIterasi`.
- Tetapi `RS2024` belum punya tombol `BtnResepIterasiBPJS` di `DlgRawatJalan.java`, jadi toggle `AKTIFKANRESEPITERDOKTER` belum akan berguna penuh kalau hanya menambah entry di `database.xml`.

## Kesimpulan Praktis

- Untuk inti BPJS apotek, `RS2024` bisa dianggap basis desktop yang lebih lengkap.
- Untuk alur hybrid web dan pasien, `SIMRSMSG` adalah basis yang lebih lengkap.
- Jika ingin repo gabungan yang utuh, pendekatan paling aman adalah:
  - ambil fitur desktop unik dari `RS2024`
  - ambil flow web dan epasien unik dari `SIMRSMSG`
  - lalu verifikasi titik sambung di `DlgDaftarPermintaanResep`, `DlgResepObat`, `DlgCariPemesanan`, dan `frmUtama`
