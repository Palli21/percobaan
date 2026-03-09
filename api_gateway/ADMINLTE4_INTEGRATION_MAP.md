# Peta Integrasi AdminLTE 4 ke `api_gateway`

## 1) Kondisi Saat Ini (Baseline)

- Layout aktif masih `SB Admin 2` + Bootstrap 3.
- Referensi CSS lama ada di:
  - `application/modules/catalog/views/master_header.php`
- Referensi JS lama ada di:
  - `application/modules/catalog/views/master_footer.php`
- Struktur halaman utama katalog:
  - `application/modules/catalog/views/master_navigation.php`
  - `application/modules/catalog/views/home/index.php`

Implikasi teknis:
- Jangan langsung mengganti aset lama (`assets/sb2/*`) karena class lama seperti `.panel`, `.navbar-default`, `.timeline`, dan plugin `metisMenu` masih dipakai.
- Strategi aman: jalankan AdminLTE 4 secara paralel dulu.

## 2) Sumber AdminLTE 4 (yang sudah siap pakai)

Sumber build:
- `/home/pallicks/Dokumen/AdminLTE-4.0.0-rc4/dist`

File inti yang dibutuhkan:
- `dist/css/adminlte.min.css`
- `dist/js/adminlte.min.js`
- `dist/assets/img/*`

Dependensi tambahan yang dipakai template resmi:
- Bootstrap 5 JS + Popper
- Bootstrap Icons
- OverlayScrollbars CSS/JS

Catatan:
- Template AdminLTE 4 resmi default memakai CDN untuk dependensi tambahan di komponen head/scripts.
- Untuk jaringan internal RS, disarankan vendor local (tanpa CDN).

## 3) Mapping Folder Aset (Disarankan)

Tujuan di project `api_gateway`:

- `assets/adminlte4/css/adminlte.min.css`
- `assets/adminlte4/js/adminlte.min.js`
- `assets/adminlte4/assets/img/*`
- `assets/adminlte4/vendor/bootstrap5/*`
- `assets/adminlte4/vendor/bootstrap-icons/*`
- `assets/adminlte4/vendor/overlayscrollbars/*`

Contoh sinkronisasi awal (manual):

```bash
mkdir -p /home/pallicks/Simrs/RS2024/api_gateway/assets/adminlte4/{css,js,assets}
cp /home/pallicks/Dokumen/AdminLTE-4.0.0-rc4/dist/css/adminlte.min.css /home/pallicks/Simrs/RS2024/api_gateway/assets/adminlte4/css/
cp /home/pallicks/Dokumen/AdminLTE-4.0.0-rc4/dist/js/adminlte.min.js /home/pallicks/Simrs/RS2024/api_gateway/assets/adminlte4/js/
cp -r /home/pallicks/Dokumen/AdminLTE-4.0.0-rc4/dist/assets/* /home/pallicks/Simrs/RS2024/api_gateway/assets/adminlte4/assets/
```

## 4) Mapping View Lama -> View Baru (Paralel)

Pertahankan file lama tetap aktif:
- `master_header.php`
- `master_navigation.php`
- `master_footer.php`

Buat varian baru khusus AdminLTE 4:
- `master_header_adminlte4.php`
- `master_navigation_adminlte4.php`
- `master_footer_adminlte4.php`
- `home/index_adminlte4.php` (pilot page)

Tujuan:
- Route lama tetap aman.
- Route baru untuk validasi UI/UX dan kompatibilitas tanpa ganggu produksi.

## 5) Mapping Controller (Pilot)

Controller saat ini:
- `application/modules/catalog/controllers/Catalog.php`

Method lama (`index`) tetap:
- load view SB Admin 2.

Tambahkan method pilot, misalnya:
- `adminlte4()`

Isi method pilot:
- load `master_header_adminlte4`
- load `master_navigation_adminlte4`
- load `home/index_adminlte4`
- load `master_footer_adminlte4`

## 6) Risiko Kompatibilitas Utama

1. Bootstrap 3 vs Bootstrap 5 conflict
- Jangan mix CSS/JS keduanya dalam satu halaman.

2. jQuery dependency lama
- AdminLTE 4 core tidak butuh jQuery, tapi halaman lama `sb2` masih butuh.

3. Komponen markup berbeda
- Komponen lama `.panel` perlu migrasi bertahap ke `.card`.
- Navbar/sidebar lama perlu refactor class ke struktur AdminLTE 4.

4. Ikon
- Lama: Font Awesome 4 (`fa ...`)
- Baru: Bootstrap Icons (`bi ...`) atau Font Awesome versi baru.

## 7) Rencana Rollout Aman

1. Tambah aset `assets/adminlte4/*` tanpa menyentuh `assets/sb2/*`.
2. Buat view/layout AdminLTE 4 paralel.
3. Buat 1 route pilot (`catalog/adminlte4`) untuk verifikasi.
4. Migrasi halaman bertahap per modul.
5. Setelah stabil, baru deprecate layout lama.

## 8) Checklist Uji Minimum

- Halaman pilot load tanpa error 404 aset.
- Sidebar toggle bekerja.
- Layout responsif desktop/mobile.
- Tidak ada conflict JS di console browser.
- Route lama (`catalog/index`) tetap normal.

