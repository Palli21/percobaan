# Tutorial Update SIMRS (Server-Client)

Dokumen ini menjelaskan cara menyiapkan server update, menyiapkan client, membuat paket `dataupdate.zip`, dan menjalankan update dari aplikasi.

## 1) Komponen yang Dibutuhkan
- `Update.jar`: aplikasi updater (dijalankan saat update).
- `settingupdate/config.xml`: konfigurasi alamat server update dan nama jar aplikasi utama.
- `settingupdate/version.xml`: versi aplikasi lokal.
- `version.html`: versi terbaru di server.
- `url.php`: penunjuk lokasi file `dataupdate.zip`.
- `dataupdate.zip`: paket file aplikasi yang akan di-update di client.

## 2) Persiapan di Server
1. Buat folder update di web server, contoh:
   - `http://IP/webapps/serverupdate/`
2. Buat `version.html` di folder tersebut:
```html
<html>
[version]3.19[/version]
[history]
- Perbaikan modul radiologi
- Penambahan menu update
[/history]
</html>
```
3. Buat `url.php` di folder tersebut:
```php
<?php
echo "[url]http://IP/webapps/serverupdate/dataupdate.zip[/url]";
?>
```
4. Upload `dataupdate.zip` ke folder tersebut.

## 3) Persiapan di Client
1. Pastikan file berikut ada di folder aplikasi:
   - `Update.jar`
   - `settingupdate/config.xml`
   - `settingupdate/version.xml`
2. Isi `settingupdate/config.xml`:
```xml
<entry key="URLSERVERUPDATE">IP/webapps/serverupdate</entry>
<entry key="DEFAULTAPLIKASIRUN">RS24.jar</entry>
```
3. Isi `settingupdate/version.xml` sesuai versi lokal:
```xml
<entry key="VERSION">3.18</entry>
```

## 4) Membuat Paket dataupdate.zip
Disarankan membuat dari folder `dist/` agar mengikuti hasil build aplikasi.

Contoh membuat paket:
```bash
cd /home/sim/SIM/RS2024/dist
zip -r ../dataupdate.zip RS24.jar lib report gambar
```

Catatan:
- Jangan masukkan file konfigurasi sensitif (misal `setting/database.xml`) jika tidak ingin ditimpa.
- Struktur dalam zip harus sama dengan struktur folder aplikasi di client.

## 5) Cara Menjalankan Update
1. Buka aplikasi SIMRS.
2. Klik menu **Update Aplikasi** pada menu bar.
3. Konfirmasi update.
4. Proses update berjalan, progres akan tampil (download, ekstrak, salin).
5. Setelah selesai, muncul notifikasi dan aplikasi akan dijalankan ulang otomatis.

Jika ingin menjalankan manual:
```bash
java -jar Update.jar
```

## 6) Catatan Versi
Update hanya berjalan jika versi server lebih baru daripada versi lokal.

- Versi server: `version.html` (tag `[version]...[/version]`)
- Versi lokal: `settingupdate/version.xml`

## 7) Troubleshooting
- **Updater tidak ditemukan**: pastikan `Update.jar` ada di folder aplikasi.
- **Tidak ada update terdeteksi**: pastikan versi di `version.html` lebih tinggi dari `version.xml`.
- **Gagal download**: cek akses `url.php` dari client (buka di browser).
- **Update berjalan tapi aplikasi tidak naik versi**: cek isi `dataupdate.zip` dan pastikan file yang benar di-update.
