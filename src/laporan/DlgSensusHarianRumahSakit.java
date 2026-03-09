package laporan;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public final class DlgSensusHarianRumahSakit extends javax.swing.JDialog {
    private final Connection koneksi = koneksiDB.condb();
    private final sekuel Sequel = new sekuel();
    private final validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DefaultTableModel tabMasuk;
    private DefaultTableModel tabTransfer;
    private DefaultTableModel tabPindah;
    private DefaultTableModel tabKeluar;
    private DefaultTableModel tabPenyakitRalan;
    private DefaultTableModel tabPenyakitRanap;
    private DefaultTableModel tabStatusPengunjungMasuk;
    private DefaultTableModel tabStatusPengunjungKeluar;
    private DefaultTableModel tabDokterMasuk;
    private DefaultTableModel tabDokterKeluar;

    public DlgSensusHarianRumahSakit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocation(8, 1);
        setSize(900, 700);
        initTableModels();
        initFilterDefaults();
    }

    public void tampil() {
        clearTable(tabMasuk);
        clearTable(tabTransfer);
        clearTable(tabPindah);
        clearTable(tabKeluar);
        clearTable(tabPenyakitRalan);
        clearTable(tabPenyakitRanap);
        clearTable(tabStatusPengunjungMasuk);
        clearTable(tabStatusPengunjungKeluar);
        clearTable(tabDokterMasuk);
        clearTable(tabDokterKeluar);
        loadPasienMasuk();
        loadPasienTransfer();
        loadPasienPindah();
        loadPasienKeluar();
        loadPenyakitRalan();
        loadPenyakitRanap();
        loadStatusPengunjungMasuk();
        loadStatusPengunjungKeluar();
        loadDokterMasuk();
        loadDokterKeluar();
        updateTabTitles();
        txtBedTersedia.setText(Sequel.cariIsi("select count(kd_kamar) from kamar where statusdata='1' and status like '%KOSONG%'"));
    }

    private void initFilterDefaults() {
        txtBedTersedia.setEditable(false);
        txtBedTersedia.setText("0");
        txtTotalMasuk.setEditable(false);
        txtTotalTransfer.setEditable(false);
        txtTotalPindah.setEditable(false);
        txtTotalKeluar.setEditable(false);
        txtTotalMasuk.setText("0");
        txtTotalTransfer.setText("0");
        txtTotalPindah.setText("0");
        txtTotalKeluar.setText("0");
        txtJaminan.setText("");
        txtKelas.setText("");
        txtKamar.setText("");
        txtDokter.setText("");
        cmbHari.setSelectedIndex(0);
        if (TglFilter.getDate() != null) {
            TglFilter2.setDate(TglFilter.getDate());
        }
        cmbJK.setSelectedIndex(0);
        cmbJamAwal.setSelectedIndex(0);
        cmbMenitAwal.setSelectedIndex(0);
        cmbJamAkhir.setSelectedIndex(23);
        cmbMenitAkhir.setSelectedIndex(59);
    }

    private void initTableModels() {
        tabMasuk = new DefaultTableModel(null, new String[]{
                "No.", "Nama Pasien", "No. RM", "Jaminan", "Kelas", "Kamar", "Jenis Kelamin", "Dokter"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbMasuk.setModel(tabMasuk);
        tbMasuk.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbMasuk.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbMasuk, new int[]{40, 180, 80, 120, 60, 140, 90, 160});
        tbMasuk.setDefaultRenderer(Object.class, new WarnaTable());

        tabTransfer = new DefaultTableModel(null, new String[]{
                "No.", "Nama Pasien", "No. RM", "Jaminan", "Kelas", "Dari Unit"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbTransfer.setModel(tabTransfer);
        tbTransfer.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTransfer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbTransfer, new int[]{40, 180, 80, 120, 60, 140});
        tbTransfer.setDefaultRenderer(Object.class, new WarnaTable());

        tabPindah = new DefaultTableModel(null, new String[]{
                "No.", "Nama Pasien", "No. RM", "Jaminan", "Kelas", "Tanggal Masuk",
                "Lama Rawat", "Ke Unit"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbPindah.setModel(tabPindah);
        tbPindah.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPindah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbPindah, new int[]{40, 180, 80, 120, 60, 90, 80, 140});
        tbPindah.setDefaultRenderer(Object.class, new WarnaTable());

        tabKeluar = new DefaultTableModel(null, new String[]{
                "No.", "Nama Pasien", "No. RM", "Jaminan", "Kelas", "Kamar", "Jenis Kelamin", "Dokter",
                "Tanggal Masuk", "Lama Rawat", "Pulang", "Dirujuk", "Permintaan Sendiri", "Mati <24 Jam",
                "Mati >24 Jam"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbKeluar.setModel(tabKeluar);
        tbKeluar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKeluar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbKeluar, new int[]{40, 180, 80, 120, 60, 140, 90, 160, 90, 80, 70, 70, 120, 90, 90});
        tbKeluar.setDefaultRenderer(Object.class, new WarnaTable());

        tabPenyakitRalan = new DefaultTableModel(null, new String[]{
                "No.", "Kode", "Nama Penyakit", "Jumlah"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbPenyakitRalan.setModel(tabPenyakitRalan);
        tbPenyakitRalan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPenyakitRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbPenyakitRalan, new int[]{40, 80, 320, 80});
        tbPenyakitRalan.setDefaultRenderer(Object.class, new WarnaTable());

        tabPenyakitRanap = new DefaultTableModel(null, new String[]{
                "No.", "Kode", "Nama Penyakit", "Jumlah"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbPenyakitRanap.setModel(tabPenyakitRanap);
        tbPenyakitRanap.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPenyakitRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbPenyakitRanap, new int[]{40, 80, 320, 80});
        tbPenyakitRanap.setDefaultRenderer(Object.class, new WarnaTable());

        tabStatusPengunjungMasuk = new DefaultTableModel(null, new String[]{
                "Status", "Jumlah"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbStatusPengunjungMasuk.setModel(tabStatusPengunjungMasuk);
        tbStatusPengunjungMasuk.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbStatusPengunjungMasuk.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbStatusPengunjungMasuk, new int[]{220, 80});
        tbStatusPengunjungMasuk.setDefaultRenderer(Object.class, new WarnaTable());

        tabStatusPengunjungKeluar = new DefaultTableModel(null, new String[]{
                "Status", "Jumlah"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbStatusPengunjungKeluar.setModel(tabStatusPengunjungKeluar);
        tbStatusPengunjungKeluar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbStatusPengunjungKeluar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbStatusPengunjungKeluar, new int[]{220, 80});
        tbStatusPengunjungKeluar.setDefaultRenderer(Object.class, new WarnaTable());

        tabDokterMasuk = new DefaultTableModel(null, new String[]{
                "Dokter", "Jumlah"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbDokterMasuk.setModel(tabDokterMasuk);
        tbDokterMasuk.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDokterMasuk.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbDokterMasuk, new int[]{220, 80});
        tbDokterMasuk.setDefaultRenderer(Object.class, new WarnaTable());

        tabDokterKeluar = new DefaultTableModel(null, new String[]{
                "Dokter", "Jumlah"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbDokterKeluar.setModel(tabDokterKeluar);
        tbDokterKeluar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDokterKeluar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnWidths(tbDokterKeluar, new int[]{220, 80});
        tbDokterKeluar.setDefaultRenderer(Object.class, new WarnaTable());
    }

    private void clearTable(DefaultTableModel model) {
        if (model.getRowCount() > 0) {
            model.setRowCount(0);
        }
    }

    private void updateTabTitles() {
        if (TabSensus == null || TabSensus.getTabCount() < 5) {
            return;
        }
        TabSensus.setTitleAt(0, "Pasien Masuk (" + tabMasuk.getRowCount() + ")");
        TabSensus.setTitleAt(1, "Pasien Transfer (" + tabTransfer.getRowCount() + ")");
        TabSensus.setTitleAt(2, "Pasien Pindah (" + tabPindah.getRowCount() + ")");
        TabSensus.setTitleAt(3, "Pasien Keluar (" + tabKeluar.getRowCount() + ")");
        TabSensus.setTitleAt(4, "Penyakit");
    }

    private void setColumnWidths(JTable table, int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }

    private String[] buildNumberOptions(int max) {
        String[] values = new String[max + 1];
        for (int i = 0; i <= max; i++) {
            values[i] = (i < 10 ? "0" : "") + i;
        }
        return values;
    }

    private String getFilterLike(widget.TextBox field) {
        return "%" + field.getText().trim() + "%";
    }

    private String getJKFilter() {
        Object pilihan = cmbJK.getSelectedItem();
        if (pilihan == null) {
            return "%";
        }
        String jk = pilihan.toString();
        if ("Semua".equals(jk)) {
            return "%";
        }
        return jk;
    }

    private int getHariIndex() {
        Object pilihan = cmbHari.getSelectedItem();
        if (pilihan == null) {
            return 0;
        }
        String hari = pilihan.toString();
        if ("Minggu".equals(hari)) {
            return 1;
        }
        if ("Senin".equals(hari)) {
            return 2;
        }
        if ("Selasa".equals(hari)) {
            return 3;
        }
        if ("Rabu".equals(hari)) {
            return 4;
        }
        if ("Kamis".equals(hari)) {
            return 5;
        }
        if ("Jumat".equals(hari)) {
            return 6;
        }
        if ("Sabtu".equals(hari)) {
            return 7;
        }
        return 0;
    }

    private String[] getRentangWaktu() {
        String tanggalAwal = Valid.SetTgl(TglFilter.getSelectedItem() + "");
        String tanggalAkhir = Valid.SetTgl(TglFilter2.getSelectedItem() + "");
        String mulai = tanggalAwal + " " + cmbJamAwal.getSelectedItem() + ":" + cmbMenitAwal.getSelectedItem() + ":00";
        String selesai = tanggalAkhir + " " + cmbJamAkhir.getSelectedItem() + ":" + cmbMenitAkhir.getSelectedItem() + ":59";
        if (mulai.compareTo(selesai) > 0) {
            return new String[]{selesai, mulai};
        }
        return new String[]{mulai, selesai};
    }

    private String getWaktuMulai() {
        return getRentangWaktu()[0];
    }

    private String getWaktuSelesai() {
        return getRentangWaktu()[1];
    }

    private void loadPasienMasuk() {
        txtTotalMasuk.setText("0");
        int hariIndex = getHariIndex();
        String sql = "select pasien.nm_pasien,reg_periksa.no_rkm_medis,penjab.png_jawab,kamar.kelas," +
                "concat(kamar.kd_kamar,' ',bangsal.nm_bangsal) as kamar,pasien.jk," +
                "ifnull(dpjp.dokter_dpjp,'') as dokter " +
                "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join penjab " +
                "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal " +
                "left join (select dpjp_ranap.no_rawat," +
                "group_concat(distinct dokter.nm_dokter order by dokter.nm_dokter separator ', ') as dokter_dpjp " +
                "from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter " +
                "group by dpjp_ranap.no_rawat) dpjp on dpjp.no_rawat=kamar_inap.no_rawat " +
                "where concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk) between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(kamar_inap.tgl_masuk)=? " : "") +
                "and penjab.png_jawab like ? and kamar.kelas like ? " +
                "and concat(kamar.kd_kamar,' ',bangsal.nm_bangsal) like ? and pasien.jk like ? " +
                "and ifnull(dpjp.dokter_dpjp,'') like ? " +
                "group by kamar_inap.no_rawat order by kamar_inap.tgl_masuk,kamar_inap.jam_masuk";
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            ps.setString(paramIndex++, getFilterLike(txtJaminan));
            ps.setString(paramIndex++, getFilterLike(txtKelas));
            ps.setString(paramIndex++, getFilterLike(txtKamar));
            ps.setString(paramIndex++, getJKFilter());
            ps.setString(paramIndex++, getFilterLike(txtDokter));
            rs = ps.executeQuery();
            int nomor = 1;
            while (rs.next()) {
                tabMasuk.addRow(new Object[]{
                        nomor++,
                        rs.getString("nm_pasien"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("png_jawab"),
                        rs.getString("kelas"),
                        rs.getString("kamar"),
                        rs.getString("jk"),
                        rs.getString("dokter")
                });
            }
            txtTotalMasuk.setText(String.valueOf(tabMasuk.getRowCount()));
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    private void loadPasienTransfer() {
        txtTotalTransfer.setText("0");
        int hariIndex = getHariIndex();
        String sql = "select pasien.nm_pasien,reg_periksa.no_rkm_medis,penjab.png_jawab,kamar.kelas,bangsal.nm_bangsal " +
                "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join penjab " +
                "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal " +
                "left join (select dpjp_ranap.no_rawat," +
                "group_concat(distinct dokter.nm_dokter order by dokter.nm_dokter separator ', ') as dokter_dpjp " +
                "from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter " +
                "group by dpjp_ranap.no_rawat) dpjp on dpjp.no_rawat=kamar_inap.no_rawat " +
                "where kamar_inap.stts_pulang='Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' " +
                "and concat(kamar_inap.tgl_keluar,' ',kamar_inap.jam_keluar) between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(kamar_inap.tgl_keluar)=? " : "") +
                "and ifnull(dpjp.dokter_dpjp,'') like ? " +
                "order by kamar_inap.tgl_keluar,kamar_inap.jam_keluar";
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            ps.setString(paramIndex++, getFilterLike(txtDokter));
            rs = ps.executeQuery();
            int nomor = 1;
            while (rs.next()) {
                tabTransfer.addRow(new Object[]{
                        nomor++,
                        rs.getString("nm_pasien"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("png_jawab"),
                        rs.getString("kelas"),
                        rs.getString("nm_bangsal")
                });
            }
            txtTotalTransfer.setText(String.valueOf(tabTransfer.getRowCount()));
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    private void loadPasienPindah() {
        txtTotalPindah.setText("0");
        int hariIndex = getHariIndex();
        String sql = "select pasien.nm_pasien,reg_periksa.no_rkm_medis,penjab.png_jawab,kamar_to.kelas,ki_to.tgl_masuk," +
                "if(ki_to.stts_pulang='Pindah Kamar',(" +
                "ifnull(to_days(concat(ki_to.tgl_keluar,' ',ki_to.jam_keluar))-to_days(concat(ki_to.tgl_masuk,' ',ki_to.jam_masuk))," +
                "to_days(now())-to_days(concat(ki_to.tgl_masuk,' ',ki_to.jam_masuk))))," +
                "(ifnull(to_days(concat(ki_to.tgl_keluar,' ',ki_to.jam_keluar))-to_days(concat(ki_to.tgl_masuk,' ',ki_to.jam_masuk))," +
                "to_days(now())-to_days(concat(ki_to.tgl_masuk,' ',ki_to.jam_masuk)))+1)) as lama_rawat," +
                "bangsal_to.nm_bangsal " +
                "from kamar_inap ki_to inner join kamar_inap ki_from " +
                "on ki_from.no_rawat=ki_to.no_rawat and ki_from.stts_pulang='Pindah Kamar' " +
                "and concat(ki_from.tgl_keluar,' ',ki_from.jam_keluar)=(" +
                "select max(concat(ki2.tgl_keluar,' ',ki2.jam_keluar)) from kamar_inap ki2 " +
                "where ki2.no_rawat=ki_to.no_rawat and ki2.stts_pulang='Pindah Kamar' " +
                "and concat(ki2.tgl_keluar,' ',ki2.jam_keluar) <= concat(ki_to.tgl_masuk,' ',ki_to.jam_masuk)" +
                ") " +
                "inner join reg_periksa inner join pasien inner join penjab inner join kamar kamar_to inner join bangsal bangsal_to " +
                "on ki_to.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "and reg_periksa.kd_pj=penjab.kd_pj and ki_to.kd_kamar=kamar_to.kd_kamar and kamar_to.kd_bangsal=bangsal_to.kd_bangsal " +
                "left join (select dpjp_ranap.no_rawat," +
                "group_concat(distinct dokter.nm_dokter order by dokter.nm_dokter separator ', ') as dokter_dpjp " +
                "from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter " +
                "group by dpjp_ranap.no_rawat) dpjp on dpjp.no_rawat=ki_to.no_rawat " +
                "where concat(ki_to.tgl_masuk,' ',ki_to.jam_masuk) between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(ki_to.tgl_masuk)=? " : "") +
                "and ifnull(dpjp.dokter_dpjp,'') like ? " +
                "order by ki_to.tgl_masuk,ki_to.jam_masuk";
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            ps.setString(paramIndex++, getFilterLike(txtDokter));
            rs = ps.executeQuery();
            int nomor = 1;
            while (rs.next()) {
                tabPindah.addRow(new Object[]{
                        nomor++,
                        rs.getString("nm_pasien"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("png_jawab"),
                        rs.getString("kelas"),
                        rs.getString("tgl_masuk"),
                        rs.getString("lama_rawat"),
                        rs.getString("nm_bangsal")
                });
            }
            txtTotalPindah.setText(String.valueOf(tabPindah.getRowCount()));
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    private void loadPasienKeluar() {
        txtTotalKeluar.setText("0");
        int hariIndex = getHariIndex();
        String sttsNorm = "lower(replace(kamar_inap.stts_pulang,' ',''))";
        String sql = "select pasien.nm_pasien,reg_periksa.no_rkm_medis,penjab.png_jawab,kamar.kelas," +
                "concat(kamar.kd_kamar,' ',bangsal.nm_bangsal) as kamar,pasien.jk," +
                "ifnull(dpjp.dokter_dpjp,'') as dokter,kamar_inap.tgl_masuk," +
                "kamar_inap.lama,kamar_inap.stts_pulang," +
                "case when kamar_inap.stts_pulang='Rujuk' then 'V' else '' end as dirujuk," +
                "case when kamar_inap.stts_pulang in ('APS','Pulang Paksa','Atas Permintaan Sendiri') then 'V' else '' end as permintaan," +
                "case when " + sttsNorm + " like '%<24%' or " + sttsNorm + " like '%24<%' then 'V' else '' end as mati_kurang_24," +
                "case when " + sttsNorm + " like '%>24%' or " + sttsNorm + " like '%24>%' then 'V' else '' end as mati_lebih_24," +
                "case when kamar_inap.stts_pulang not in ('Rujuk','APS','Pulang Paksa','Atas Permintaan Sendiri') " +
                "and lower(kamar_inap.stts_pulang) not like '%meninggal%' " +
                "and lower(kamar_inap.stts_pulang) not like '%mati%' " +
                "and " + sttsNorm + " not like '%<24%' and " + sttsNorm + " not like '%24<%' " +
                "and " + sttsNorm + " not like '%>24%' and " + sttsNorm + " not like '%24>%' then 'V' else '' end as pulang " +
                "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join penjab " +
                "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal " +
                "left join (select dpjp_ranap.no_rawat," +
                "group_concat(distinct dokter.nm_dokter order by dokter.nm_dokter separator ', ') as dokter_dpjp " +
                "from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter " +
                "group by dpjp_ranap.no_rawat) dpjp on dpjp.no_rawat=kamar_inap.no_rawat " +
                "where kamar_inap.tgl_keluar<>'0000-00-00' and kamar_inap.stts_pulang<>'Pindah Kamar' " +
                "and concat(kamar_inap.tgl_keluar,' ',kamar_inap.jam_keluar) between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(kamar_inap.tgl_keluar)=? " : "") +
                "and penjab.png_jawab like ? and kamar.kelas like ? " +
                "and concat(kamar.kd_kamar,' ',bangsal.nm_bangsal) like ? and pasien.jk like ? " +
                "and ifnull(dpjp.dokter_dpjp,'') like ? " +
                "order by kamar_inap.tgl_keluar,kamar_inap.jam_keluar";
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            ps.setString(paramIndex++, getFilterLike(txtJaminan));
            ps.setString(paramIndex++, getFilterLike(txtKelas));
            ps.setString(paramIndex++, getFilterLike(txtKamar));
            ps.setString(paramIndex++, getJKFilter());
            ps.setString(paramIndex++, getFilterLike(txtDokter));
            rs = ps.executeQuery();
            int nomor = 1;
            while (rs.next()) {
                tabKeluar.addRow(new Object[]{
                        nomor++,
                        rs.getString("nm_pasien"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("png_jawab"),
                        rs.getString("kelas"),
                        rs.getString("kamar"),
                        rs.getString("jk"),
                        rs.getString("dokter"),
                        rs.getString("tgl_masuk"),
                        rs.getString("lama"),
                        rs.getString("pulang"),
                        rs.getString("dirujuk"),
                        rs.getString("permintaan"),
                        rs.getString("mati_kurang_24"),
                        rs.getString("mati_lebih_24")
                });
            }
            txtTotalKeluar.setText(String.valueOf(tabKeluar.getRowCount()));
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    private void loadPenyakitRalan() {
        loadPenyakit("Ralan", tabPenyakitRalan);
    }

    private void loadPenyakitRanap() {
        loadPenyakit("Ranap", tabPenyakitRanap);
    }

    private void loadPenyakit(String status, DefaultTableModel model) {
        clearTable(model);
        int hariIndex = getHariIndex();
        String sql = "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,count(diagnosa_pasien.no_rawat) as jumlah " +
                "from diagnosa_pasien inner join penyakit inner join reg_periksa " +
                "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit and diagnosa_pasien.no_rawat=reg_periksa.no_rawat " +
                "where diagnosa_pasien.status=? and diagnosa_pasien.prioritas='1' and reg_periksa.status_lanjut=? " +
                "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(reg_periksa.tgl_registrasi)=? " : "") +
                "group by diagnosa_pasien.kd_penyakit order by jumlah desc,penyakit.nm_penyakit limit 10";
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, status);
            ps.setString(paramIndex++, status);
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            rs = ps.executeQuery();
            int nomor = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                        nomor++,
                        rs.getString("kd_penyakit"),
                        rs.getString("nm_penyakit"),
                        rs.getString("jumlah")
                });
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    private void loadStatusPengunjungMasuk() {
        loadStatusPengunjung(tabStatusPengunjungMasuk, labelStatusPengunjungMasuk);
    }

    private void loadStatusPengunjungKeluar() {
        loadStatusPengunjung(tabStatusPengunjungKeluar, labelStatusPengunjungKeluar);
    }

    private void loadDokterMasuk() {
        loadDokter(tabDokterMasuk, labelDokterMasuk, true);
    }

    private void loadDokterKeluar() {
        loadDokter(tabDokterKeluar, labelDokterKeluar, false);
    }

    private void loadDokter(DefaultTableModel model, widget.Label label, boolean pasienMasuk) {
        clearTable(model);
        int hariIndex = getHariIndex();
        String waktuField = pasienMasuk
                ? "concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk)"
                : "concat(kamar_inap.tgl_keluar,' ',kamar_inap.jam_keluar)";
        String hariField = pasienMasuk ? "kamar_inap.tgl_masuk" : "kamar_inap.tgl_keluar";
        String filterKeluar = pasienMasuk
                ? ""
                : "and kamar_inap.tgl_keluar<>'0000-00-00' and kamar_inap.stts_pulang<>'Pindah Kamar' ";
        String sql = "select dokter.nm_dokter as nama_dokter," +
                "count(distinct kamar_inap.no_rawat) as jumlah " +
                "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join penjab " +
                "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal " +
                "inner join dpjp_ranap on kamar_inap.no_rawat=dpjp_ranap.no_rawat " +
                "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter " +
                "where " + waktuField + " between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(" + hariField + ")=? " : "") +
                filterKeluar +
                "and penjab.png_jawab like ? and kamar.kelas like ? " +
                "and concat(kamar.kd_kamar,' ',bangsal.nm_bangsal) like ? and pasien.jk like ? " +
                "and dokter.nm_dokter like ? " +
                "group by dokter.nm_dokter order by jumlah desc, dokter.nm_dokter";
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            ps.setString(paramIndex++, getFilterLike(txtJaminan));
            ps.setString(paramIndex++, getFilterLike(txtKelas));
            ps.setString(paramIndex++, getFilterLike(txtKamar));
            ps.setString(paramIndex++, getJKFilter());
            ps.setString(paramIndex++, getFilterLike(txtDokter));
            rs = ps.executeQuery();
            int total = 0;
            while (rs.next()) {
                total++;
                model.addRow(new Object[]{
                        rs.getString("nama_dokter"),
                        rs.getString("jumlah")
                });
            }
            if (label != null) {
                label.setText("Dokter (Total: " + total + ")");
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    private void loadStatusPengunjung(DefaultTableModel model, widget.Label label) {
        clearTable(model);
        int hariIndex = getHariIndex();
        String sql = "select pasien.jk,reg_periksa.stts_daftar,reg_periksa.status_poli," +
                "(select perujuk from rujuk_masuk where no_rawat=reg_periksa.no_rawat limit 1) as perujuk " +
                "from reg_periksa inner join pasien inner join penjab " +
                "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj " +
                "where reg_periksa.stts<>'Batal' and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? " +
                (hariIndex > 0 ? "and dayofweek(reg_periksa.tgl_registrasi)=? " : "") +
                "and penjab.png_jawab like ? and pasien.jk like ? " +
                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg";
        int total = 0;
        int jkl = 0;
        int jkp = 0;
        int pengunjungbaru = 0;
        int pengunjunglama = 0;
        int statuspolibaru = 0;
        int statuspolilama = 0;
        int rujukan = 0;
        try {
            ps = koneksi.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, getWaktuMulai());
            ps.setString(paramIndex++, getWaktuSelesai());
            if (hariIndex > 0) {
                ps.setInt(paramIndex++, hariIndex);
            }
            ps.setString(paramIndex++, getFilterLike(txtJaminan));
            ps.setString(paramIndex++, getJKFilter());
            rs = ps.executeQuery();
            while (rs.next()) {
                total++;
                String jk = rs.getString("jk");
                if ("L".equals(jk)) {
                    jkl++;
                } else if ("P".equals(jk)) {
                    jkp++;
                }

                if ("Lama".equals(rs.getString("stts_daftar"))) {
                    pengunjunglama++;
                } else {
                    pengunjungbaru++;
                }

                if ("Lama".equals(rs.getString("status_poli"))) {
                    statuspolilama++;
                } else {
                    statuspolibaru++;
                }

                String perujuk = rs.getString("perujuk");
                if (perujuk == null || perujuk.trim().equals("")) {
                    rujukan++;
                }
            }
            model.addRow(new Object[]{"Laki-Laki", jkl});
            model.addRow(new Object[]{"Perempuan", jkp});
            model.addRow(new Object[]{"Pengunjung Baru", pengunjungbaru});
            model.addRow(new Object[]{"Pengunjung Lama", pengunjunglama});
            model.addRow(new Object[]{"Jenis Kunjungan Baru", statuspolibaru});
            model.addRow(new Object[]{"Jenis Kunjungan Lama", statuspolilama});
            model.addRow(new Object[]{"Rujukan", rujukan});
            if (label != null) {
                label.setText("Status Pengunjung (Total: " + total + ")");
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (Exception e) { /* ignore */ }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        internalFrame1 = new widget.InternalFrame();
        panelFilter = new widget.panelisi();
        labelHari = new widget.Label();
        cmbHari = new widget.ComboBox();
        labelTanggal = new widget.Label();
        TglFilter = new widget.Tanggal();
        labelTanggalSampai = new widget.Label();
        TglFilter2 = new widget.Tanggal();
        labelJam = new widget.Label();
        cmbJamAwal = new widget.ComboBox();
        labelJamSeparator = new widget.Label();
        cmbMenitAwal = new widget.ComboBox();
        labelJamSampai = new widget.Label();
        cmbJamAkhir = new widget.ComboBox();
        labelJamSeparatorAkhir = new widget.Label();
        cmbMenitAkhir = new widget.ComboBox();
        labelJaminan = new widget.Label();
        txtJaminan = new widget.TextBox();
        labelKelas = new widget.Label();
        txtKelas = new widget.TextBox();
        labelKamar = new widget.Label();
        txtKamar = new widget.TextBox();
        labelDokter = new widget.Label();
        txtDokter = new widget.TextBox();
        labelJK = new widget.Label();
        cmbJK = new widget.ComboBox();
        labelBedTersedia = new widget.Label();
        txtBedTersedia = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        TabSensus = new javax.swing.JTabbedPane();
        internalFrameMasuk = new widget.InternalFrame();
        ScrollMasuk = new widget.ScrollPane();
        tbMasuk = new widget.Table();
        panelMasukContainer = new widget.panelisi();
        panelMasukData = new widget.panelisi();
        panelMasukRingkasan = new widget.panelisi();
        panelStatusMasuk = new widget.panelisi();
        labelStatusPengunjungMasuk = new widget.Label();
        ScrollStatusMasuk = new widget.ScrollPane();
        tbStatusPengunjungMasuk = new widget.Table();
        panelDokterMasuk = new widget.panelisi();
        labelDokterMasuk = new widget.Label();
        ScrollDokterMasuk = new widget.ScrollPane();
        tbDokterMasuk = new widget.Table();
        panelTotalMasuk = new widget.panelisi();
        labelTotalMasuk = new widget.Label();
        txtTotalMasuk = new widget.TextBox();
        internalFrameTransfer = new widget.InternalFrame();
        ScrollTransfer = new widget.ScrollPane();
        tbTransfer = new widget.Table();
        panelTotalTransfer = new widget.panelisi();
        labelTotalTransfer = new widget.Label();
        txtTotalTransfer = new widget.TextBox();
        internalFramePindah = new widget.InternalFrame();
        ScrollPindah = new widget.ScrollPane();
        tbPindah = new widget.Table();
        panelTotalPindah = new widget.panelisi();
        labelTotalPindah = new widget.Label();
        txtTotalPindah = new widget.TextBox();
        internalFrameKeluar = new widget.InternalFrame();
        ScrollKeluar = new widget.ScrollPane();
        tbKeluar = new widget.Table();
        panelKeluarContainer = new widget.panelisi();
        panelKeluarData = new widget.panelisi();
        panelKeluarRingkasan = new widget.panelisi();
        panelStatusKeluar = new widget.panelisi();
        labelStatusPengunjungKeluar = new widget.Label();
        ScrollStatusKeluar = new widget.ScrollPane();
        tbStatusPengunjungKeluar = new widget.Table();
        panelDokterKeluar = new widget.panelisi();
        labelDokterKeluar = new widget.Label();
        ScrollDokterKeluar = new widget.ScrollPane();
        tbDokterKeluar = new widget.Table();
        panelTotalKeluar = new widget.panelisi();
        labelTotalKeluar = new widget.Label();
        txtTotalKeluar = new widget.TextBox();
        internalFramePenyakit = new widget.InternalFrame();
        panelPenyakitContainer = new widget.panelisi();
        panelPenyakitRalan = new widget.panelisi();
        labelPenyakitRalan = new widget.Label();
        ScrollPenyakitRalan = new widget.ScrollPane();
        tbPenyakitRalan = new widget.Table();
        panelPenyakitRanap = new widget.panelisi();
        labelPenyakitRanap = new widget.Label();
        ScrollPenyakitRanap = new widget.ScrollPane();
        tbPenyakitRanap = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Sensus Harian Rumah Sakit ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50)
        ));
        internalFrame1.setLayout(new BorderLayout(1, 1));

        panelFilter.setPreferredSize(new Dimension(55, 120));
        panelFilter.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        labelHari.setText("Hari :");
        labelHari.setPreferredSize(new Dimension(45, 23));
        panelFilter.add(labelHari);

        cmbHari.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
                "Semua", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"
        }));
        cmbHari.setPreferredSize(new Dimension(90, 23));
        panelFilter.add(cmbHari);

        labelTanggal.setText("Periode :");
        labelTanggal.setPreferredSize(new Dimension(65, 23));
        panelFilter.add(labelTanggal);

        TglFilter.setDisplayFormat("dd-MM-yyyy");
        TglFilter.setPreferredSize(new Dimension(90, 23));
        panelFilter.add(TglFilter);

        labelTanggalSampai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTanggalSampai.setText("s/d");
        labelTanggalSampai.setPreferredSize(new Dimension(25, 23));
        panelFilter.add(labelTanggalSampai);

        TglFilter2.setDisplayFormat("dd-MM-yyyy");
        TglFilter2.setPreferredSize(new Dimension(90, 23));
        panelFilter.add(TglFilter2);

        labelJam.setText("Jam :");
        labelJam.setPreferredSize(new Dimension(40, 23));
        panelFilter.add(labelJam);

        cmbJamAwal.setModel(new javax.swing.DefaultComboBoxModel(buildNumberOptions(23)));
        cmbJamAwal.setPreferredSize(new Dimension(45, 23));
        panelFilter.add(cmbJamAwal);

        labelJamSeparator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJamSeparator.setText(":");
        labelJamSeparator.setPreferredSize(new Dimension(5, 23));
        panelFilter.add(labelJamSeparator);

        cmbMenitAwal.setModel(new javax.swing.DefaultComboBoxModel(buildNumberOptions(59)));
        cmbMenitAwal.setPreferredSize(new Dimension(45, 23));
        panelFilter.add(cmbMenitAwal);

        labelJamSampai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJamSampai.setText("s/d");
        labelJamSampai.setPreferredSize(new Dimension(25, 23));
        panelFilter.add(labelJamSampai);

        cmbJamAkhir.setModel(new javax.swing.DefaultComboBoxModel(buildNumberOptions(23)));
        cmbJamAkhir.setPreferredSize(new Dimension(45, 23));
        panelFilter.add(cmbJamAkhir);

        labelJamSeparatorAkhir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJamSeparatorAkhir.setText(":");
        labelJamSeparatorAkhir.setPreferredSize(new Dimension(5, 23));
        panelFilter.add(labelJamSeparatorAkhir);

        cmbMenitAkhir.setModel(new javax.swing.DefaultComboBoxModel(buildNumberOptions(59)));
        cmbMenitAkhir.setPreferredSize(new Dimension(45, 23));
        panelFilter.add(cmbMenitAkhir);

        labelJaminan.setText("Jaminan :");
        labelJaminan.setPreferredSize(new Dimension(65, 23));
        panelFilter.add(labelJaminan);

        txtJaminan.setPreferredSize(new Dimension(120, 23));
        panelFilter.add(txtJaminan);

        labelKelas.setText("Kelas :");
        labelKelas.setPreferredSize(new Dimension(50, 23));
        panelFilter.add(labelKelas);

        txtKelas.setPreferredSize(new Dimension(80, 23));
        panelFilter.add(txtKelas);

        labelKamar.setText("Kamar :");
        labelKamar.setPreferredSize(new Dimension(60, 23));
        panelFilter.add(labelKamar);

        txtKamar.setPreferredSize(new Dimension(120, 23));
        panelFilter.add(txtKamar);

        labelDokter.setText("Dokter :");
        labelDokter.setPreferredSize(new Dimension(60, 23));
        panelFilter.add(labelDokter);

        txtDokter.setPreferredSize(new Dimension(140, 23));
        panelFilter.add(txtDokter);

        labelJK.setText("J.K. :");
        labelJK.setPreferredSize(new Dimension(40, 23));
        panelFilter.add(labelJK);

        cmbJK.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
                "Semua", "L", "P"
        }));
        cmbJK.setPreferredSize(new Dimension(70, 23));
        panelFilter.add(cmbJK);

        labelBedTersedia.setText("TT tersedia :");
        labelBedTersedia.setPreferredSize(new Dimension(80, 23));
        panelFilter.add(labelBedTersedia);

        txtBedTersedia.setPreferredSize(new Dimension(60, 23));
        panelFilter.add(txtBedTersedia);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setToolTipText("Tampilkan");
        BtnCari.setPreferredSize(new Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        panelFilter.add(BtnCari);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setToolTipText("Keluar");
        BtnKeluar.setPreferredSize(new Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelFilter.add(BtnKeluar);

        internalFrame1.add(panelFilter, BorderLayout.PAGE_START);

        internalFrameMasuk.setLayout(new BorderLayout(1, 1));
        panelMasukContainer.setLayout(new BorderLayout(1, 1));
        panelMasukData.setLayout(new BorderLayout(1, 1));
        ScrollMasuk.setViewportView(tbMasuk);
        panelMasukData.add(ScrollMasuk, BorderLayout.CENTER);
        panelTotalMasuk.setPreferredSize(new Dimension(100, 44));
        panelTotalMasuk.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 9));
        labelTotalMasuk.setText("Jumlah :");
        labelTotalMasuk.setPreferredSize(new Dimension(60, 23));
        panelTotalMasuk.add(labelTotalMasuk);
        txtTotalMasuk.setPreferredSize(new Dimension(60, 23));
        panelTotalMasuk.add(txtTotalMasuk);
        panelMasukData.add(panelTotalMasuk, BorderLayout.PAGE_END);

        panelStatusMasuk.setLayout(new BorderLayout(1, 1));
        labelStatusPengunjungMasuk.setText("Status Pengunjung (Total: 0)");
        labelStatusPengunjungMasuk.setPreferredSize(new Dimension(200, 23));
        panelStatusMasuk.add(labelStatusPengunjungMasuk, BorderLayout.PAGE_START);
        ScrollStatusMasuk.setViewportView(tbStatusPengunjungMasuk);
        panelStatusMasuk.add(ScrollStatusMasuk, BorderLayout.CENTER);
        panelStatusMasuk.setPreferredSize(new Dimension(100, 160));

        panelDokterMasuk.setLayout(new BorderLayout(1, 1));
        labelDokterMasuk.setText("Dokter (Total: 0)");
        labelDokterMasuk.setPreferredSize(new Dimension(200, 23));
        panelDokterMasuk.add(labelDokterMasuk, BorderLayout.PAGE_START);
        ScrollDokterMasuk.setViewportView(tbDokterMasuk);
        panelDokterMasuk.add(ScrollDokterMasuk, BorderLayout.CENTER);

        panelMasukRingkasan.setLayout(new GridLayout(1, 2, 1, 1));
        panelMasukRingkasan.add(panelStatusMasuk);
        panelMasukRingkasan.add(panelDokterMasuk);
        panelMasukRingkasan.setPreferredSize(new Dimension(100, 160));

        panelMasukContainer.add(panelMasukData, BorderLayout.CENTER);
        panelMasukContainer.add(panelMasukRingkasan, BorderLayout.PAGE_END);
        internalFrameMasuk.add(panelMasukContainer, BorderLayout.CENTER);
        TabSensus.addTab("Pasien Masuk", internalFrameMasuk);

        internalFrameTransfer.setLayout(new BorderLayout(1, 1));
        ScrollTransfer.setViewportView(tbTransfer);
        internalFrameTransfer.add(ScrollTransfer, BorderLayout.CENTER);
        panelTotalTransfer.setPreferredSize(new Dimension(100, 44));
        panelTotalTransfer.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 9));
        labelTotalTransfer.setText("Jumlah :");
        labelTotalTransfer.setPreferredSize(new Dimension(60, 23));
        panelTotalTransfer.add(labelTotalTransfer);
        txtTotalTransfer.setPreferredSize(new Dimension(60, 23));
        panelTotalTransfer.add(txtTotalTransfer);
        internalFrameTransfer.add(panelTotalTransfer, BorderLayout.PAGE_END);
        TabSensus.addTab("Pasien Transfer", internalFrameTransfer);

        internalFramePindah.setLayout(new BorderLayout(1, 1));
        ScrollPindah.setViewportView(tbPindah);
        internalFramePindah.add(ScrollPindah, BorderLayout.CENTER);
        panelTotalPindah.setPreferredSize(new Dimension(100, 44));
        panelTotalPindah.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 9));
        labelTotalPindah.setText("Jumlah :");
        labelTotalPindah.setPreferredSize(new Dimension(60, 23));
        panelTotalPindah.add(labelTotalPindah);
        txtTotalPindah.setPreferredSize(new Dimension(60, 23));
        panelTotalPindah.add(txtTotalPindah);
        internalFramePindah.add(panelTotalPindah, BorderLayout.PAGE_END);
        TabSensus.addTab("Pasien Pindah", internalFramePindah);

        internalFrameKeluar.setLayout(new BorderLayout(1, 1));
        panelKeluarContainer.setLayout(new BorderLayout(1, 1));
        panelKeluarData.setLayout(new BorderLayout(1, 1));
        ScrollKeluar.setViewportView(tbKeluar);
        panelKeluarData.add(ScrollKeluar, BorderLayout.CENTER);
        panelTotalKeluar.setPreferredSize(new Dimension(100, 44));
        panelTotalKeluar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 9));
        labelTotalKeluar.setText("Jumlah :");
        labelTotalKeluar.setPreferredSize(new Dimension(60, 23));
        panelTotalKeluar.add(labelTotalKeluar);
        txtTotalKeluar.setPreferredSize(new Dimension(60, 23));
        panelTotalKeluar.add(txtTotalKeluar);
        panelKeluarData.add(panelTotalKeluar, BorderLayout.PAGE_END);

        panelStatusKeluar.setLayout(new BorderLayout(1, 1));
        labelStatusPengunjungKeluar.setText("Status Pengunjung (Total: 0)");
        labelStatusPengunjungKeluar.setPreferredSize(new Dimension(200, 23));
        panelStatusKeluar.add(labelStatusPengunjungKeluar, BorderLayout.PAGE_START);
        ScrollStatusKeluar.setViewportView(tbStatusPengunjungKeluar);
        panelStatusKeluar.add(ScrollStatusKeluar, BorderLayout.CENTER);
        panelStatusKeluar.setPreferredSize(new Dimension(100, 160));

        panelDokterKeluar.setLayout(new BorderLayout(1, 1));
        labelDokterKeluar.setText("Dokter (Total: 0)");
        labelDokterKeluar.setPreferredSize(new Dimension(200, 23));
        panelDokterKeluar.add(labelDokterKeluar, BorderLayout.PAGE_START);
        ScrollDokterKeluar.setViewportView(tbDokterKeluar);
        panelDokterKeluar.add(ScrollDokterKeluar, BorderLayout.CENTER);

        panelKeluarRingkasan.setLayout(new GridLayout(1, 2, 1, 1));
        panelKeluarRingkasan.add(panelStatusKeluar);
        panelKeluarRingkasan.add(panelDokterKeluar);
        panelKeluarRingkasan.setPreferredSize(new Dimension(100, 160));

        panelKeluarContainer.add(panelKeluarData, BorderLayout.CENTER);
        panelKeluarContainer.add(panelKeluarRingkasan, BorderLayout.PAGE_END);
        internalFrameKeluar.add(panelKeluarContainer, BorderLayout.CENTER);
        TabSensus.addTab("Pasien Keluar", internalFrameKeluar);

        internalFramePenyakit.setLayout(new BorderLayout(1, 1));
        panelPenyakitContainer.setLayout(new GridLayout(2, 1, 1, 1));

        panelPenyakitRalan.setLayout(new BorderLayout(1, 1));
        labelPenyakitRalan.setText("10 Penyakit Terbanyak Rawat Jalan");
        labelPenyakitRalan.setPreferredSize(new Dimension(250, 23));
        panelPenyakitRalan.add(labelPenyakitRalan, BorderLayout.PAGE_START);
        ScrollPenyakitRalan.setViewportView(tbPenyakitRalan);
        panelPenyakitRalan.add(ScrollPenyakitRalan, BorderLayout.CENTER);

        panelPenyakitRanap.setLayout(new BorderLayout(1, 1));
        labelPenyakitRanap.setText("10 Penyakit Terbanyak Rawat Inap");
        labelPenyakitRanap.setPreferredSize(new Dimension(250, 23));
        panelPenyakitRanap.add(labelPenyakitRanap, BorderLayout.PAGE_START);
        ScrollPenyakitRanap.setViewportView(tbPenyakitRanap);
        panelPenyakitRanap.add(ScrollPenyakitRanap, BorderLayout.CENTER);

        panelPenyakitContainer.add(panelPenyakitRalan);
        panelPenyakitContainer.add(panelPenyakitRanap);
        internalFramePenyakit.add(panelPenyakitContainer, BorderLayout.CENTER);
        TabSensus.addTab("Penyakit", internalFramePenyakit);

        internalFrame1.add(TabSensus, BorderLayout.CENTER);

        getContentPane().add(internalFrame1, BorderLayout.CENTER);
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        tampil();
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.ScrollPane ScrollDokterKeluar;
    private widget.ScrollPane ScrollDokterMasuk;
    private widget.ScrollPane ScrollKeluar;
    private widget.ScrollPane ScrollMasuk;
    private widget.ScrollPane ScrollPenyakitRalan;
    private widget.ScrollPane ScrollPenyakitRanap;
    private widget.ScrollPane ScrollStatusKeluar;
    private widget.ScrollPane ScrollStatusMasuk;
    private widget.ScrollPane ScrollPindah;
    private widget.ScrollPane ScrollTransfer;
    private javax.swing.JTabbedPane TabSensus;
    private widget.Tanggal TglFilter;
    private widget.Tanggal TglFilter2;
    private widget.ComboBox cmbHari;
    private widget.ComboBox cmbJK;
    private widget.ComboBox cmbJamAkhir;
    private widget.ComboBox cmbJamAwal;
    private widget.ComboBox cmbMenitAkhir;
    private widget.ComboBox cmbMenitAwal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrameKeluar;
    private widget.InternalFrame internalFrameMasuk;
    private widget.InternalFrame internalFramePenyakit;
    private widget.InternalFrame internalFramePindah;
    private widget.InternalFrame internalFrameTransfer;
    private widget.Label labelBedTersedia;
    private widget.Label labelDokter;
    private widget.Label labelDokterKeluar;
    private widget.Label labelDokterMasuk;
    private widget.Label labelHari;
    private widget.Label labelJK;
    private widget.Label labelJam;
    private widget.Label labelJamSampai;
    private widget.Label labelJamSeparator;
    private widget.Label labelJamSeparatorAkhir;
    private widget.Label labelJaminan;
    private widget.Label labelKamar;
    private widget.Label labelKelas;
    private widget.Label labelPenyakitRalan;
    private widget.Label labelPenyakitRanap;
    private widget.Label labelStatusPengunjungKeluar;
    private widget.Label labelStatusPengunjungMasuk;
    private widget.Label labelTanggal;
    private widget.Label labelTanggalSampai;
    private widget.Label labelTotalKeluar;
    private widget.Label labelTotalMasuk;
    private widget.Label labelTotalPindah;
    private widget.Label labelTotalTransfer;
    private widget.panelisi panelFilter;
    private widget.panelisi panelDokterKeluar;
    private widget.panelisi panelDokterMasuk;
    private widget.panelisi panelKeluarContainer;
    private widget.panelisi panelKeluarData;
    private widget.panelisi panelKeluarRingkasan;
    private widget.panelisi panelMasukContainer;
    private widget.panelisi panelMasukData;
    private widget.panelisi panelMasukRingkasan;
    private widget.panelisi panelPenyakitContainer;
    private widget.panelisi panelPenyakitRalan;
    private widget.panelisi panelPenyakitRanap;
    private widget.panelisi panelStatusKeluar;
    private widget.panelisi panelStatusMasuk;
    private widget.panelisi panelTotalKeluar;
    private widget.panelisi panelTotalMasuk;
    private widget.panelisi panelTotalPindah;
    private widget.panelisi panelTotalTransfer;
    private widget.Table tbKeluar;
    private widget.Table tbMasuk;
    private widget.Table tbDokterKeluar;
    private widget.Table tbDokterMasuk;
    private widget.Table tbPenyakitRalan;
    private widget.Table tbPenyakitRanap;
    private widget.Table tbStatusPengunjungKeluar;
    private widget.Table tbStatusPengunjungMasuk;
    private widget.Table tbPindah;
    private widget.Table tbTransfer;
    private widget.TextBox txtBedTersedia;
    private widget.TextBox txtDokter;
    private widget.TextBox txtJaminan;
    private widget.TextBox txtKamar;
    private widget.TextBox txtKelas;
    private widget.TextBox txtTotalKeluar;
    private widget.TextBox txtTotalMasuk;
    private widget.TextBox txtTotalPindah;
    private widget.TextBox txtTotalTransfer;
}
