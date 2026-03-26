# RS24

Basis kerja SIMRS Khanza yang dipakai untuk pengembangan RS24.

## Struktur Singkat

- `src/` berisi source code utama aplikasi desktop.
- `setting/` berisi konfigurasi aplikasi dan aset pendukung.
- `docs/` berisi catatan implementasi dan dokumentasi tambahan.
- `cache/` dipakai aplikasi untuk cache runtime lokal dan tidak perlu di-commit.

## Catatan Repo

- File lokal seperti `hs_err_pid*.log`, `*.code-workspace`, dan `cache/*.iyem` diabaikan oleh Git.
- Repo ini masih menyimpan beberapa aset biner besar. Jika nanti ada penambahan file besar baru, pertimbangkan Git LFS sebelum commit.
