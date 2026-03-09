# Panduan Sinkronisasi NetBeans Designer

## Status Saat Ini

**File Timestamps:**
- `AsesmenKhususKebidananIbuNifas.java`: **01:39** ✅ (ter-update dengan perubahan helper methods)
- `AsesmenKhususKebidananIbuNifas.form`: **00:42** ⚠️ (belum ter-update dari Designer)

## Yang Sudah Ada di File .java

✅ Spacing constants (line 112-122)
✅ Color palette (line 124-133)
✅ Helper methods: `createSectionHeader()`, `createGroupPanel()`, `createSeparator()`, `applySectionBackground()`
✅ Section headers untuk A, B, C
✅ Separators antar sections

## Langkah Sinkronisasi

### Di NetBeans:

1. **Pastikan file terbuka:**
   - Buka `AsesmenKhususKebidananIbuNifas.java` di NetBeans

2. **Switch ke tab Design:**
   - Klik tab **"Design"** di bagian atas editor
   - Lihat visual designer

3. **Save perubahan:**
   - Tekan **Ctrl+S** atau **File → Save**
   - File `.form` akan otomatis di-generate/update

4. **Refresh jika perlu:**
   - Klik kanan pada project → **Refresh**
   - Atau close dan reopen file

### Verifikasi:

Setelah save, jalankan:
```bash
stat -c '%y' /home/sim/SIM/RS2024/src/rekammedis/AsesmenKhususKebidananIbuNifas.form
```

Timestamp harus lebih baru dari 00:42.

---

## Jika Ada Konflik

Jika NetBeans Designer menunjukkan error atau komponen hilang:

1. **Close NetBeans**
2. **Backup file:**
   ```bash
   cp AsesmenKhususKebidananIbuNifas.java AsesmenKhususKebidananIbuNifas.java.backup
   cp AsesmenKhususKebidananIbuNifas.form AsesmenKhususKebidananIbuNifas.form.backup
   ```

3. **Reopen NetBeans**
4. **Rebuild project**

---

## Catatan Penting

⚠️ **Komponen yang ditambahkan via code (`initSubjektifSection()`) TIDAK akan muncul di NetBeans Designer** karena:
- Section headers dibuat secara programmatic
- Separators ditambahkan di runtime
- Helper methods dipanggil setelah `initComponents()`

**Ini NORMAL** - komponen dinamis tidak visible di Designer, tapi akan muncul saat aplikasi dijalankan.

---

## Next Steps

Silakan:
1. Save file di NetBeans (Ctrl+S)
2. Beritahu saya jika sudah
3. Atau screenshot jika ada error
