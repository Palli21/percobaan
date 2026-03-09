/*
 * Kontribusi dari M. Syukur RS. Jiwa Prov Sultra
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMTransferPasienAntarRuang2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMTransferPasienAntarRuang2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal Masuk","Tanggal Pindah",
            "Indikasi Pindah",
            "Keterangan Indikasi Pindah",
            "Asal Ruang Rawat / Poliklinik",
            "Ruang Rawat Selanjutnya",
            "Metode Pemindahan",
            "Diagnosa Utama",
            "Diagnosa Sekunder",
            "Diagnosa Keperawatan",
            "Peralatan Yang Menyertai",
            "Keterangan Peralatan Menyertai",
            "Menyetujui Pemindahan",
            "Nama Keluarga/Penanggung Jawab",
            "Hubungan",
            "Keluhan Utama",
            "Keadaan Umum",
            "TD",
            "Nadi",
            "RR",
            "Suhu",
            "Pendamping Pasien",
            "PDR",
            "Status Generlis",
            "Status Lokalis",
            "Aktivitas Ditempat Tidur",
            "Aktivitas Kemandirian",
            "Hp",
            "HP_Kemandirian",
            "Berpakaian",
            "Berpakaian Kemandirian",
            "Pergerakan",
            "Pergerakan Kemandirian",
            "Makan",
            "Pemeriksaan Penunjang Yang Sudah Dilakukan",
            "Intervensi/Tindakan Yang Sudah Dilakukan ",
            "Diet",
            "Rencana Perawatan Selanjutnya",
            "Obat Yang Telah Diberikan",
            "NIP Menyerahkan","Petugas Yang Menyerahkan","NIP Menerima","Petugas Yang Menerima"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 46; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(190);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(108);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(160);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setPreferredWidth(230);
            }else if(i==16){
                column.setPreferredWidth(236);
            }else if(i==17){
                column.setPreferredWidth(134);
            }else if(i==18){
                column.setPreferredWidth(165);
            }else if(i==19){
                column.setPreferredWidth(127);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(107);
            }else if(i==23){
                column.setPreferredWidth(50);
            }else if(i==24){
                column.setPreferredWidth(50);
            }else if(i==25){
                column.setPreferredWidth(45);
            }else if(i==26){
                column.setPreferredWidth(50);
            }else if(i==27){
                column.setPreferredWidth(200);
            }else if(i==28){
                column.setPreferredWidth(107);
            }else if(i==29){
                column.setPreferredWidth(50);
            }else if(i==30){
                column.setPreferredWidth(50);
            }else if(i==31){
                column.setPreferredWidth(45);
            }else if(i==32){
                column.setPreferredWidth(50);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(95);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(95);
            }else if(i==37){
                column.setPreferredWidth(80);
            }else if(i==38){
                column.setPreferredWidth(143);
            }else if(i==39){
                column.setPreferredWidth(80);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(132);
            }else if(i==42){
                column.setPreferredWidth(120);
            }else if(i==43){
                column.setPreferredWidth(100);
            }else if(i==44){
                column.setPreferredWidth(125);
            }else if(i==45){
                column.setPreferredWidth(143);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        AsalRuang.setDocument(new batasInput((byte)30).getKata(AsalRuang));
        RuangSelanjutnya.setDocument(new batasInput((byte)30).getKata(RuangSelanjutnya));
        DiagnosaUtama.setDocument(new batasInput((int)50).getKata(DiagnosaUtama));
        DiagnosaSekunder.setDocument(new batasInput((int)100).getKata(DiagnosaSekunder));
        KeteranganIndikasiPindahRuang.setDocument(new batasInput((int)50).getKata(KeteranganIndikasiPindahRuang));
        PendampingPasien.setDocument(new batasInput((int)50).getKata(PendampingPasien));
        PemeriksaanPenunjang.setDocument(new batasInput((int)500).getKata(PemeriksaanPenunjang));
        NamaMenyetujui.setDocument(new batasInput((int)50).getKata(NamaMenyetujui));
        KeluhanUtama.setDocument(new batasInput((int)200).getKata(KeluhanUtama));
        TD.setDocument(new batasInput((int)7).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        SG.setDocument(new batasInput((int)100).getKata(SG));
        SL.setDocument(new batasInput((int)100).getKata(SL));
        TindakanYangSudahDilakukan.setDocument(new batasInput((int)100).getKata(TindakanYangSudahDilakukan));
        Diet.setDocument(new batasInput((int)100).getKata(Diet));
        PerawatanSelanjutnya.setDocument(new batasInput((int)100).getKata(PerawatanSelanjutnya));
        ObatYangDiberikan.setDocument(new batasInput((int)800).getKata(ObatYangDiberikan));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenyerahkan.requestFocus();
                    }else{
                        KdPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenerima.requestFocus();
                    }
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
        
        ChkAccor.setSelected(false);
        isPhoto();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jSeparator14 = new javax.swing.JSeparator();
        MenyetujuiPemindahan = new widget.ComboBox();
        jLabel57 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TanggalMasuk = new widget.Tanggal();
        label12 = new widget.Label();
        TanggalPindah = new widget.Tanggal();
        KeteranganIndikasiPindahRuang = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        PendampingPasien = new widget.TextBox();
        RuangSelanjutnya = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        PeralatanMenyertai = new widget.ComboBox();
        jLabel16 = new widget.Label();
        DiagnosaUtama = new widget.TextBox();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel30 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        DiagnosaSekunder = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        ObatYangDiberikan = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel34 = new widget.Label();
        IndikasiPindah = new widget.ComboBox();
        AsalRuang = new widget.TextBox();
        jLabel35 = new widget.Label();
        MetodePemindahan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        NamaMenyetujui = new widget.TextBox();
        jLabel38 = new widget.Label();
        HubunganMenyetujui = new widget.ComboBox();
        jLabel39 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel42 = new widget.Label();
        KeadaanUmum = new widget.ComboBox();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel24 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel44 = new widget.Label();
        ATTempatTidur = new widget.ComboBox();
        label14 = new widget.Label();
        KdPetugasMenyerahkan = new widget.TextBox();
        NmPetugasMenyerahkan = new widget.TextBox();
        label15 = new widget.Label();
        KdPetugasMenerima = new widget.TextBox();
        NmPetugasMenerima = new widget.TextBox();
        label16 = new widget.Label();
        jLabel53 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        DiagnosaKeperawatan = new widget.TextArea();
        KeteranganPeralatan = new widget.TextBox();
        jLabel55 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        SG = new widget.TextArea();
        jLabel56 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        SL = new widget.TextArea();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        PDR = new widget.ComboBox();
        jLabel61 = new widget.Label();
        ATTempatTidur1 = new widget.ComboBox();
        ATTempatTidurKemandirian = new widget.ComboBox();
        ATTempatTidur3 = new widget.ComboBox();
        BtnDokter = new widget.Button();
        HP = new widget.ComboBox();
        jLabel62 = new widget.Label();
        ATTempatTidur4 = new widget.ComboBox();
        HPKemandirian = new widget.ComboBox();
        ATTempatTidur5 = new widget.ComboBox();
        Berpakaian = new widget.ComboBox();
        jLabel63 = new widget.Label();
        ATTempatTidur6 = new widget.ComboBox();
        BerpakaianKemandirian = new widget.ComboBox();
        ATTempatTidur7 = new widget.ComboBox();
        jSeparator8 = new javax.swing.JSeparator();
        Pergerakan = new widget.ComboBox();
        jLabel64 = new widget.Label();
        ATTempatTidur8 = new widget.ComboBox();
        PergerakanKemandirian = new widget.ComboBox();
        ATTempatTidur9 = new widget.ComboBox();
        jLabel65 = new widget.Label();
        Makan = new widget.ComboBox();
        jLabel66 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        TindakanYangSudahDilakukan = new widget.TextArea();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        Diet = new widget.TextArea();
        jLabel69 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        PerawatanSelanjutnya = new widget.TextArea();
        jSeparator9 = new javax.swing.JSeparator();
        BtnMenerima = new widget.Button();
        BtnPetugasPendamping = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transfer Pasien Antar Ruang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahsave.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                BtnSimpanMouseWheelMoved(evt);
            }
        });
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahtambah.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahhapus.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahganti.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahprint.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahkaca.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahkeluar.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 800));

        FormInput.setBackground(new java.awt.Color(204, 204, 204));
        FormInput.setBorder(null);
        FormInput.setForeground(new java.awt.Color(51, 51, 51));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1163));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        MenyetujuiPemindahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MenyetujuiPemindahan.setName("MenyetujuiPemindahan"); // NOI18N
        MenyetujuiPemindahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyetujuiPemindahanKeyPressed(evt);
            }
        });
        FormInput.add(MenyetujuiPemindahan);
        MenyetujuiPemindahan.setBounds(780, 230, 80, 23);

        jLabel57.setText("Indikasi Pindah :");
        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(390, 40, 92, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(570, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(630, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(760, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Masuk :");
        label11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(720, 10, 30, 23);

        TanggalMasuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2025 08:57:54" }));
        TanggalMasuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        TanggalMasuk.setOpaque(false);
        TanggalMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMasukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalMasuk);
        TanggalMasuk.setBounds(74, 40, 130, 23);

        label12.setText("Pindah :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(201, 40, 55, 23);

        TanggalPindah.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2025 08:57:54" }));
        TanggalPindah.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPindah.setName("TanggalPindah"); // NOI18N
        TanggalPindah.setOpaque(false);
        TanggalPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPindahKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPindah);
        TanggalPindah.setBounds(260, 40, 130, 23);

        KeteranganIndikasiPindahRuang.setHighlighter(null);
        KeteranganIndikasiPindahRuang.setName("KeteranganIndikasiPindahRuang"); // NOI18N
        KeteranganIndikasiPindahRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeteranganIndikasiPindahRuangActionPerformed(evt);
            }
        });
        KeteranganIndikasiPindahRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganIndikasiPindahRuangKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganIndikasiPindahRuang);
        KeteranganIndikasiPindahRuang.setBounds(724, 40, 130, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        PendampingPasien.setHighlighter(null);
        PendampingPasien.setName("PendampingPasien"); // NOI18N
        PendampingPasien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                PendampingPasienAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                PendampingPasienAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        PendampingPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendampingPasienActionPerformed(evt);
            }
        });
        PendampingPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendampingPasienKeyPressed(evt);
            }
        });
        FormInput.add(PendampingPasien);
        PendampingPasien.setBounds(240, 410, 220, 23);

        RuangSelanjutnya.setHighlighter(null);
        RuangSelanjutnya.setName("RuangSelanjutnya"); // NOI18N
        RuangSelanjutnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RuangSelanjutnyaKeyPressed(evt);
            }
        });
        FormInput.add(RuangSelanjutnya);
        RuangSelanjutnya.setBounds(458, 80, 160, 23);

        jLabel14.setText("Metode Pemindahan :");
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(620, 80, 110, 23);

        jLabel15.setText("Ruang Rawat Selanjutnya :");
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(320, 80, 130, 23);

        PeralatanMenyertai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oksigen Portable", "Infus", "NGT", "Syringe Pump", "Suction", "Kateter Urin", "Tidak Ada" }));
        PeralatanMenyertai.setName("PeralatanMenyertai"); // NOI18N
        PeralatanMenyertai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeralatanMenyertaiKeyPressed(evt);
            }
        });
        FormInput.add(PeralatanMenyertai);
        PeralatanMenyertai.setBounds(150, 230, 135, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Diagnosa Utama   :");
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(15, 120, 120, 23);

        DiagnosaUtama.setHighlighter(null);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaUtama);
        DiagnosaUtama.setBounds(125, 120, 730, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Asal Ruang Rawat / Poliklinik");
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(15, 80, 149, 23);

        jLabel20.setText(":");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(155, 80, 10, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Diagnosa Sekunder :");
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(15, 150, 170, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        DiagnosaSekunder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaSekunder.setColumns(20);
        DiagnosaSekunder.setRows(5);
        DiagnosaSekunder.setName("DiagnosaSekunder"); // NOI18N
        DiagnosaSekunder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunderKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(DiagnosaSekunder);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(15, 170, 410, 43);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 110, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(10, 870, 880, 1);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Obat Yang Telah Diberikan :");
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(10, 880, 640, 20);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText(" Sudah Dilakukan (EKH,LAB,DLL)  :");
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(540, 580, 180, 10);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        ObatYangDiberikan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ObatYangDiberikan.setColumns(20);
        ObatYangDiberikan.setRows(5);
        ObatYangDiberikan.setName("ObatYangDiberikan"); // NOI18N
        ObatYangDiberikan.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ObatYangDiberikanAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        ObatYangDiberikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatYangDiberikanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(ObatYangDiberikan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(10, 900, 880, 140);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(540, 600, 310, 73);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 220, 880, 1);

        jLabel34.setText("Pasien/Keluarga Mengetahui & Menyetujui Alasan Pemindahan :");
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(450, 230, 320, 23);

        IndikasiPindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kondisi Pasien Stabil", "Kondisi Pasien Tidak Ada Perubahan", "Kondisi Pasien Memburuk", "Fasilitas Kurang Memadai", "Fasilitas Butuh Lebih Baik", "Tenaga Membutuhkan Yang Lebih Ahli", "Tenaga Kurang", "Lain-lain" }));
        IndikasiPindah.setName("IndikasiPindah"); // NOI18N
        IndikasiPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiPindahKeyPressed(evt);
            }
        });
        FormInput.add(IndikasiPindah);
        IndikasiPindah.setBounds(486, 40, 235, 23);

        AsalRuang.setHighlighter(null);
        AsalRuang.setName("AsalRuang"); // NOI18N
        AsalRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRuangKeyPressed(evt);
            }
        });
        FormInput.add(AsalRuang);
        AsalRuang.setBounds(169, 80, 150, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(130, 230, 15, 23);

        MetodePemindahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kursi Roda", "Tempat Tidur", "Brankar", "Jalan Sendiri", "-" }));
        MetodePemindahan.setName("MetodePemindahan"); // NOI18N
        MetodePemindahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MetodePemindahanKeyPressed(evt);
            }
        });
        FormInput.add(MetodePemindahan);
        MetodePemindahan.setBounds(739, 80, 115, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Peralatan Yang Menyertai");
        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(20, 230, 130, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(380, 260, 20, 23);

        NamaMenyetujui.setHighlighter(null);
        NamaMenyetujui.setName("NamaMenyetujui"); // NOI18N
        NamaMenyetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaMenyetujuiKeyPressed(evt);
            }
        });
        FormInput.add(NamaMenyetujui);
        NamaMenyetujui.setBounds(400, 260, 230, 23);

        jLabel38.setText("Hubungan :");
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(620, 260, 80, 23);

        HubunganMenyetujui.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kakak", "Adik", "Saudara", "Keluarga", "Kakek", "Nenek", "Orang Tua", "Suami", "Istri", "Penanggung Jawab", "Menantu", "Ipar", "Mertua", "-" }));
        HubunganMenyetujui.setName("HubunganMenyetujui"); // NOI18N
        HubunganMenyetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganMenyetujuiKeyPressed(evt);
            }
        });
        FormInput.add(HubunganMenyetujui);
        HubunganMenyetujui.setBounds(710, 260, 150, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Bila Pemberi Persetujuan Adalah Keluarga/Penanggung Jawab Pasien, Nama");
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(20, 260, 380, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 290, 880, 1);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Keadaan Umum Pasien :");
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(10, 290, 320, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Keluhan Utama :");
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(440, 310, 170, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(440, 330, 410, 63);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Keadaan            :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(50, 310, 90, 23);

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Gelisah", "Delirium", "Koma" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(140, 310, 130, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(320, 310, 60, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(380, 310, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(50, 340, 90, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("x/menit");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(200, 340, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(140, 340, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(280, 340, 30, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(320, 340, 60, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(380, 340, 50, 23);

        jLabel24.setText("Suhu :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(50, 370, 90, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(140, 370, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("°C");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(200, 370, 30, 23);

        jLabel28.setText("TD :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(280, 310, 30, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 400, 880, 1);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Potensial Dilakukan Rehabilitas : ");
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(490, 410, 190, 23);

        ATTempatTidur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur.setName("ATTempatTidur"); // NOI18N
        ATTempatTidur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidurKeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur);
        ATTempatTidur.setBounds(170, 600, 170, 23);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Petugas / Perawat :");
        label14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(40, 1070, 130, 23);

        KdPetugasMenyerahkan.setEditable(false);
        KdPetugasMenyerahkan.setName("KdPetugasMenyerahkan"); // NOI18N
        KdPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugasMenyerahkan.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                KdPetugasMenyerahkanAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        KdPetugasMenyerahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPetugasMenyerahkanActionPerformed(evt);
            }
        });
        FormInput.add(KdPetugasMenyerahkan);
        KdPetugasMenyerahkan.setBounds(290, 1090, 100, 23);

        NmPetugasMenyerahkan.setEditable(false);
        NmPetugasMenyerahkan.setName("NmPetugasMenyerahkan"); // NOI18N
        NmPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(207, 23));
        NmPetugasMenyerahkan.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                NmPetugasMenyerahkanAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        NmPetugasMenyerahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugasMenyerahkanActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugasMenyerahkan);
        NmPetugasMenyerahkan.setBounds(390, 1090, 180, 23);

        label15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label15.setText("PJ UNIT MENERIMA                     : ");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(110, 1130, 180, 23);

        KdPetugasMenerima.setEditable(false);
        KdPetugasMenerima.setName("KdPetugasMenerima"); // NOI18N
        KdPetugasMenerima.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugasMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPetugasMenerimaActionPerformed(evt);
            }
        });
        FormInput.add(KdPetugasMenerima);
        KdPetugasMenerima.setBounds(290, 1130, 100, 23);

        NmPetugasMenerima.setEditable(false);
        NmPetugasMenerima.setName("NmPetugasMenerima"); // NOI18N
        NmPetugasMenerima.setPreferredSize(new java.awt.Dimension(207, 23));
        NmPetugasMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugasMenerimaActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugasMenerima);
        NmPetugasMenerima.setBounds(390, 1130, 180, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("PJ UNIT YANG MENYERAHKAN   :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(110, 1090, 180, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Diagnosa Keperawatan :");
        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(440, 150, 170, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        DiagnosaKeperawatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaKeperawatan.setColumns(20);
        DiagnosaKeperawatan.setRows(5);
        DiagnosaKeperawatan.setName("DiagnosaKeperawatan"); // NOI18N
        DiagnosaKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeperawatanKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(DiagnosaKeperawatan);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(440, 170, 410, 43);

        KeteranganPeralatan.setHighlighter(null);
        KeteranganPeralatan.setName("KeteranganPeralatan"); // NOI18N
        KeteranganPeralatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPeralatanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPeralatan);
        KeteranganPeralatan.setBounds(290, 230, 160, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Aktivitas DItempat Tidur : ");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(30, 600, 160, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        SG.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        SG.setColumns(20);
        SG.setRows(5);
        SG.setName("SG"); // NOI18N
        SG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SGKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(SG);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(20, 470, 410, 73);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Status Lokalias ( Temuan Yang Siknifikan) : ");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(440, 450, 240, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        SL.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        SL.setColumns(20);
        SL.setRows(5);
        SL.setName("SL"); // NOI18N
        SL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SLKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(SL);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(440, 470, 410, 73);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Status Generalis (Temuan Yang Siknifikan) : ");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(20, 450, 240, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Petugas Pendamping Pasien Saat Pindah :");
        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 410, 230, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Pemeriksaan Fisik : ");
        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(10, 430, 170, 23);

        PDR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Kurang" }));
        PDR.setName("PDR"); // NOI18N
        PDR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PDRKeyPressed(evt);
            }
        });
        FormInput.add(PDR);
        PDR.setBounds(680, 410, 170, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Status Kemandirian : ");
        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(10, 560, 120, 23);

        ATTempatTidur1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur1.setName("ATTempatTidur1"); // NOI18N
        ATTempatTidur1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur1KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur1);
        ATTempatTidur1.setBounds(170, 600, 170, 23);

        ATTempatTidurKemandirian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Butuh Bantuan", "Tidak Dapat Melakukan" }));
        ATTempatTidurKemandirian.setName("ATTempatTidurKemandirian"); // NOI18N
        ATTempatTidurKemandirian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidurKemandirianKeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidurKemandirian);
        ATTempatTidurKemandirian.setBounds(350, 600, 170, 23);

        ATTempatTidur3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur3.setName("ATTempatTidur3"); // NOI18N
        ATTempatTidur3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur3KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur3);
        ATTempatTidur3.setBounds(350, 600, 170, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                BtnDokterAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                BtnDokterAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                BtnDokterAncestorRemoved(evt);
            }
        });
        BtnDokter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                BtnDokterStateChanged(evt);
            }
        });
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        BtnDokter.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                BtnDokterVetoableChange(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(570, 1090, 28, 23);

        HP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wajah Rambut Tangan", "Batang Tubuh Parineum", "Ekstremitas Bawah", "Traktus Digestivus", "Traktus Urinarius" }));
        HP.setName("HP"); // NOI18N
        HP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HPKeyPressed(evt);
            }
        });
        FormInput.add(HP);
        HP.setBounds(170, 630, 170, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Hygiene Pribadi               : ");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(30, 630, 160, 23);

        ATTempatTidur4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur4.setName("ATTempatTidur4"); // NOI18N
        ATTempatTidur4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur4KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur4);
        ATTempatTidur4.setBounds(170, 630, 170, 23);

        HPKemandirian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Butuh Bantuan", "Tidak Dapat Melakukan" }));
        HPKemandirian.setName("HPKemandirian"); // NOI18N
        HPKemandirian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HPKemandirianKeyPressed(evt);
            }
        });
        FormInput.add(HPKemandirian);
        HPKemandirian.setBounds(350, 630, 170, 23);

        ATTempatTidur5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur5.setName("ATTempatTidur5"); // NOI18N
        ATTempatTidur5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur5KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur5);
        ATTempatTidur5.setBounds(350, 630, 170, 23);

        Berpakaian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ekstremitas Atas", "Batang Tubuh Parineum", "Ekstremitas Bawah" }));
        Berpakaian.setName("Berpakaian"); // NOI18N
        Berpakaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BerpakaianKeyPressed(evt);
            }
        });
        FormInput.add(Berpakaian);
        Berpakaian.setBounds(170, 660, 170, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Berpakaian                       : ");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(30, 660, 160, 23);

        ATTempatTidur6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur6.setName("ATTempatTidur6"); // NOI18N
        ATTempatTidur6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur6KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur6);
        ATTempatTidur6.setBounds(170, 660, 170, 23);

        BerpakaianKemandirian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Butuh Bantuan", "Tidak Dapat Melakukan" }));
        BerpakaianKemandirian.setName("BerpakaianKemandirian"); // NOI18N
        BerpakaianKemandirian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BerpakaianKemandirianKeyPressed(evt);
            }
        });
        FormInput.add(BerpakaianKemandirian);
        BerpakaianKemandirian.setBounds(350, 660, 170, 23);

        ATTempatTidur7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur7.setName("ATTempatTidur7"); // NOI18N
        ATTempatTidur7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur7KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur7);
        ATTempatTidur7.setBounds(350, 660, 170, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 550, 880, 1);

        Pergerakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jalan Kaki", "Kursi Roda" }));
        Pergerakan.setName("Pergerakan"); // NOI18N
        Pergerakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PergerakanKeyPressed(evt);
            }
        });
        FormInput.add(Pergerakan);
        Pergerakan.setBounds(170, 690, 170, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Pergerakan                      : ");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(30, 690, 160, 23);

        ATTempatTidur8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur8.setName("ATTempatTidur8"); // NOI18N
        ATTempatTidur8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur8KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur8);
        ATTempatTidur8.setBounds(170, 690, 170, 23);

        PergerakanKemandirian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Butuh Bantuan", "Tidak Dapat Melakukan" }));
        PergerakanKemandirian.setName("PergerakanKemandirian"); // NOI18N
        PergerakanKemandirian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PergerakanKemandirianKeyPressed(evt);
            }
        });
        FormInput.add(PergerakanKemandirian);
        PergerakanKemandirian.setBounds(350, 690, 170, 23);

        ATTempatTidur9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berguling", "Duduk" }));
        ATTempatTidur9.setName("ATTempatTidur9"); // NOI18N
        ATTempatTidur9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATTempatTidur9KeyPressed(evt);
            }
        });
        FormInput.add(ATTempatTidur9);
        ATTempatTidur9.setBounds(350, 690, 170, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Makan                              : ");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(30, 720, 160, 23);

        Makan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Butuh Bantuan", "Tidak Dapat Melakukan" }));
        Makan.setName("Makan"); // NOI18N
        Makan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MakanKeyPressed(evt);
            }
        });
        FormInput.add(Makan);
        Makan.setBounds(170, 720, 170, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Intervensi atau Tidakan Yang Sudah Dilakukan :");
        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(540, 680, 250, 20);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        TindakanYangSudahDilakukan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakanYangSudahDilakukan.setColumns(20);
        TindakanYangSudahDilakukan.setRows(5);
        TindakanYangSudahDilakukan.setName("TindakanYangSudahDilakukan"); // NOI18N
        TindakanYangSudahDilakukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanYangSudahDilakukanKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(TindakanYangSudahDilakukan);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(540, 700, 320, 73);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Pemeriksaan Penunjang Diagnostik yang ");
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(540, 560, 250, 14);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Diet :");
        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(20, 770, 250, 20);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Diet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diet.setColumns(20);
        Diet.setRows(5);
        Diet.setName("Diet"); // NOI18N
        Diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DietKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Diet);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(20, 790, 320, 73);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Rencana Perawatan Selanjutnya  :");
        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(360, 770, 250, 20);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PerawatanSelanjutnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PerawatanSelanjutnya.setColumns(20);
        PerawatanSelanjutnya.setRows(5);
        PerawatanSelanjutnya.setName("PerawatanSelanjutnya"); // NOI18N
        PerawatanSelanjutnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanSelanjutnyaKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(PerawatanSelanjutnya);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(360, 790, 320, 73);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(10, 1070, 880, 1);

        BtnMenerima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMenerima.setMnemonic('2');
        BtnMenerima.setToolTipText("Alt+2");
        BtnMenerima.setName("BtnMenerima"); // NOI18N
        BtnMenerima.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMenerima.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                BtnMenerimaAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        BtnMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenerimaActionPerformed(evt);
            }
        });
        BtnMenerima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMenerimaKeyPressed(evt);
            }
        });
        FormInput.add(BtnMenerima);
        BtnMenerima.setBounds(570, 1130, 28, 23);

        BtnPetugasPendamping.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasPendamping.setMnemonic('2');
        BtnPetugasPendamping.setToolTipText("Alt+2");
        BtnPetugasPendamping.setName("BtnPetugasPendamping"); // NOI18N
        BtnPetugasPendamping.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasPendamping.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                BtnPetugasPendampingAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                BtnPetugasPendampingAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                BtnPetugasPendampingAncestorRemoved(evt);
            }
        });
        BtnPetugasPendamping.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                BtnPetugasPendampingStateChanged(evt);
            }
        });
        BtnPetugasPendamping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasPendampingActionPerformed(evt);
            }
        });
        BtnPetugasPendamping.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasPendampingKeyPressed(evt);
            }
        });
        BtnPetugasPendamping.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                BtnPetugasPendampingVetoableChange(evt);
            }
        });
        FormInput.add(BtnPetugasPendamping);
        BtnPetugasPendamping.setBounds(460, 410, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Transfer Pasien", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Pindah :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(197, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahceklis.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengambilan Persetujuan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahtambah1.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Transfer Pasien", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugasMenyerahkan.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menyerahkan");
        }else if(NmPetugasMenerima.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(AsalRuang,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(AsalRuang,"Asal Ruang");
        }else if(RuangSelanjutnya.getText().trim().equals("")){
            Valid.textKosong(RuangSelanjutnya,"Ruang Selanjutnya");
        }else if(DiagnosaUtama.getText().trim().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
       }else if(ObatYangDiberikan.getText().trim().equals("")){
            Valid.textKosong(ObatYangDiberikan,"Obat Yang Diberikan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else {
                if(akses.getkode().equals(KdPetugasMenerima.getText())||akses.getkode().equals(KdPetugasMenyerahkan.getText())){
                    simpan();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnMenerima,BtnBatal);
        }
        
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }             
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRM.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugasMenyerahkan.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menyerahkan");
        }else if(NmPetugasMenerima.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(AsalRuang,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(AsalRuang,"Asal Ruang");
        }else if(RuangSelanjutnya.getText().trim().equals("")){
            Valid.textKosong(RuangSelanjutnya,"Ruang Selanjutnya");
        }else if(DiagnosaUtama.getText().trim().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else if(DiagnosaSekunder.getText().trim().equals("")){
            Valid.textKosong(DiagnosaSekunder,"Diagnosa Sekunder");
        }else if(DiagnosaKeperawatan.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKeperawatan,"Diagnos Keperawatan");
        }else if(KeteranganPeralatan.getText().trim().equals("")){
            Valid.textKosong(KeteranganPeralatan,"Keterangan Peralatan");
        }else if(NamaMenyetujui.getText().trim().equals("")){
            Valid.textKosong(NamaMenyetujui,"NamaMenyetujui"); 
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR ");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama"); 
        }else if(ObatYangDiberikan.getText().trim().equals("")){
            Valid.textKosong(ObatYangDiberikan,"Obat Yang Diberikan");    

        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang2.tanggal_masuk, "+
                            "transfer_pasien_antar_ruang2.tanggal_pindah,"+
                            "transfer_pasien_antar_ruang2.indikasi_pindah_ruang,"+ 
                            "transfer_pasien_antar_ruang2.keterangan_indikasi_pindah_ruang,"+  
                            "transfer_pasien_antar_ruang2.asal_ruang,"+
                            "transfer_pasien_antar_ruang2.ruang_selanjutnya, "+
                            "transfer_pasien_antar_ruang2.metode_pemindahan_pasien,"+         
                            "transfer_pasien_antar_ruang2.diagnosa_utama,"+ 
                            "transfer_pasien_antar_ruang2.diagnosa_sekunder,"+
                            "transfer_pasien_antar_ruang2.Diagnosa_Keperawatan,"+       
                            "transfer_pasien_antar_ruang2.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang2.keterangan_peralatan_yang_menyertai,"+ 
                            "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang2.pasien_keluarga_menyetujui, "+ 
                            "transfer_pasien_antar_ruang2.nama_menyetujui, "+ 
                            "transfer_pasien_antar_ruang2.hubungan_menyetujui,"+
                            "transfer_pasien_antar_ruang2.keluhan_utama,"+        
                            "transfer_pasien_antar_ruang2.keadaan_umum,"+
                            "transfer_pasien_antar_ruang2.td, "+ 
                            "transfer_pasien_antar_ruang2.nadi,"+ 
                            "transfer_pasien_antar_ruang2.rr,"+
                            "transfer_pasien_antar_ruang2.suhu,"+       
                            "transfer_pasien_antar_ruang2.Pendamping_Pasien,"+
                            "transfer_pasien_antar_ruang2.PDR,"+
                            "transfer_pasien_antar_ruang2.SG,"+
                            "transfer_pasien_antar_ruang2.SL,"+
                            "transfer_pasien_antar_ruang2.ATTempat_Tidur,"+
                            "transfer_pasien_antar_ruang2.ATTempat_Tidur_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.HP,"+
                            "transfer_pasien_antar_ruang2.HP_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.Berpakaian,"+
                            "transfer_pasien_antar_ruang2.Berpakaian_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.Pergerakan,"+
                            "transfer_pasien_antar_ruang2.Pergerakan_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.Makan,"+
                            "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang2.Tindakan_Yang_Sudah_Dilakukan,"+
                            "transfer_pasien_antar_ruang2.Diet,"+
                            "transfer_pasien_antar_ruang2.Perawatan_Selanjutnya,"+
                            "transfer_pasien_antar_ruang2.obat_yang_telah_diberikan,"+     
                            " transfer_pasien_antar_ruang2.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan, transfer_pasien_antar_ruang2  .nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join  transfer_pasien_antar_ruang2   on reg_periksa.no_rawat= transfer_pasien_antar_ruang2  .no_rawat "+
                            "inner join petugas as petugasmenyerahkan on  transfer_pasien_antar_ruang2  .nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenerima on  transfer_pasien_antar_ruang2  .nip_menerima=petugasmenerima.nip where "+
                            " transfer_pasien_antar_ruang2  .tanggal_pindah between ? and ? order by  transfer_pasien_antar_ruang2  .tanggal_pindah");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang2.tanggal_masuk, "+
                            "transfer_pasien_antar_ruang2.tanggal_pindah,"+
                            "transfer_pasien_antar_ruang2.indikasi_pindah_ruang,"+ 
                            "transfer_pasien_antar_ruang2.keterangan_indikasi_pindah_ruang,"+  
                            "transfer_pasien_antar_ruang2.asal_ruang,"+
                            "transfer_pasien_antar_ruang2.ruang_selanjutnya, "+
                            "transfer_pasien_antar_ruang2.metode_pemindahan_pasien,"+         
                            "transfer_pasien_antar_ruang2.diagnosa_utama,"+ 
                            "transfer_pasien_antar_ruang2.diagnosa_sekunder,"+
                            "transfer_pasien_antar_ruang2.Diagnosa_Keperawatan,"+       
                            "transfer_pasien_antar_ruang2.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang2.keterangan_peralatan_yang_menyertai,"+ 
                            "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang2.pasien_keluarga_menyetujui, "+ 
                            "transfer_pasien_antar_ruang2.nama_menyetujui, "+ 
                            "transfer_pasien_antar_ruang2.hubungan_menyetujui,"+
                            "transfer_pasien_antar_ruang2.keluhan_utama, "+        
                            "transfer_pasien_antar_ruang2.keadaan_umum,"+
                            "transfer_pasien_antar_ruang2.td, "+ 
                            "transfer_pasien_antar_ruang2.nadi,"+ 
                            "transfer_pasien_antar_ruang2.rr,"+
                            "transfer_pasien_antar_ruang2.suhu,"+ 
                            "transfer_pasien_antar_ruang2.Pendamping_Pasien,"+
                            "transfer_pasien_antar_ruang2.PDR,"+
                            "transfer_pasien_antar_ruang2.SG,"+
                            "transfer_pasien_antar_ruang2.SL,"+
                            "transfer_pasien_antar_ruang2.ATTempat_Tidur,"+
                            "transfer_pasien_antar_ruang2.ATTempat_Tidur_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.HP,"+
                            "transfer_pasien_antar_ruang2.HP_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.Berpakaian,"+
                            "transfer_pasien_antar_ruang2.Berpakaian_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.Pergerakan,"+
                            "transfer_pasien_antar_ruang2.Pergerakan_Kemandirian,"+
                            "transfer_pasien_antar_ruang2.Makan,"+
                            "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang2.Tindakan_Yang_Sudah_Dilakukan,"+
                            "transfer_pasien_antar_ruang2.Diet,"+
                            "transfer_pasien_antar_ruang2.Perawatan_Selanjutnya,"+
                            "transfer_pasien_antar_ruang2.obat_yang_telah_diberikan,"+    
                            " transfer_pasien_antar_ruang2.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan, transfer_pasien_antar_ruang2  .nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join  transfer_pasien_antar_ruang2   on reg_periksa.no_rawat= transfer_pasien_antar_ruang2  .no_rawat "+
                            "inner join petugas as petugasmenyerahkan on  transfer_pasien_antar_ruang2  .nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenerima on  transfer_pasien_antar_ruang2  .nip_menerima=petugasmenerima.nip where "+
                            " transfer_pasien_antar_ruang2  .tanggal_pindah between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            " transfer_pasien_antar_ruang2  .nip_menyerahkan like ? or petugasmenyerahkan.nama like ?) order by  transfer_pasien_antar_ruang2  .tanggal_pindah");
                }

                try {
                    if(TCari.getText().trim().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                    }
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                           "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Indikasi Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Indikasi Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asal Ruang Rawat / Poliklinik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ruang Rawat Selanjutnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Metode Pemindahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Sekunder</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Keperawatan</b></td>"+   
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perlatan Yang Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Peralatan Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Menyetujui Pemindahan</b></td>"+ 
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Keluarga/Penanggung Jawab</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+   
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pendamping Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>PDR</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Generlis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Lokalis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Aktivitas Ditempat Tidur</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Aktivitas kemandirian</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HP</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HP Kemandirian</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Berpakaian</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Berpakaian Kemandirian</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pergerakan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pergerakan Kemandirian</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Makan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Penunjang Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Intervensi/Tindakan Yang Sudah DIlakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diet</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencan Perawatan Selanjutnya</b></td>"+                         
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Yang Telah Diberikan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menerima</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menerima</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_masuk")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_pindah")+"</td>"+
                               "<td valign='top'>"+rs.getString("indikasi_pindah_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_indikasi_pindah_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("asal_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("ruang_selanjutnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("metode_pemindahan_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_sekunder")+"</td>"+
                               "<td valign='top'>"+rs.getString("Diagnosa_Keperawatan")+"</td>"+
                               "<td valign='top'>"+rs.getString("peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("pasien_keluarga_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama_menyetujui")+"</td>"+        
                               "<td valign='top'>"+rs.getString("hubungan_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum")+"</td>"+ 
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("Pendamping_Pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("PDR")+"</td>"+
                               "<td valign='top'>"+rs.getString("SG")+"</td>"+
                               "<td valign='top'>"+rs.getString("SL")+"</td>"+
                               "<td valign='top'>"+rs.getString("ATTempat_Tidur")+"</td>"+  
                               "<td valign='top'>"+rs.getString("ATTempat_Tidur_Kemandirian")+"</td>"+
                               "<td valign='top'>"+rs.getString("HP")+"</td>"+ 
                               "<td valign='top'>"+rs.getString("HP_Kemandirian")+"</td>"+
                               "<td valign='top'>"+rs.getString("Berpakaian")+"</td>"+
                               "<td valign='top'>"+rs.getString("Berpakaian_Kemandirian")+"</td>"+
                               "<td valign='top'>"+rs.getString("Pergerakan")+"</td>"+         
                               "<td valign='top'>"+rs.getString("Pergerakan_Kemandirian")+"</td>"+
                               "<td valign='top'>"+rs.getString("Makan")+"</td>"+ 
                               "<td valign='top'>"+rs.getString("pemeriksaan_penunjang_yang_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("Tindakan_Yang_Sudah_Dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("Diet")+"</td>"+
                               "<td valign='top'>"+rs.getString("Perawatan_Selanjutnya")+"</td>"+  
                               "<td valign='top'>"+rs.getString("obat_yang_telah_diberikan")+"</td>"+       
                               "<td valign='top'>"+rs.getString("nip_menyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menerima")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenerima")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4000' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>"
                    );

                    File g = new File("file2.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                    );
                    bg.close();

                    File f = new File("TransferPasienAntarRuang.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA TRANSFER PASIEN ANTAR RUANG<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MenyetujuiPemindahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyetujuiPemindahanKeyPressed
        Valid.pindah(evt,PendampingPasien,NamaMenyetujui);
    }//GEN-LAST:event_MenyetujuiPemindahanKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMasukKeyPressed
        Valid.pindah2(evt,TNoRw,TanggalPindah);
    }//GEN-LAST:event_TanggalMasukKeyPressed

    private void TanggalPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPindahKeyPressed
        Valid.pindah2(evt,TanggalMasuk,IndikasiPindah);
    }//GEN-LAST:event_TanggalPindahKeyPressed

    private void KeteranganIndikasiPindahRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganIndikasiPindahRuangKeyPressed
        Valid.pindah(evt,IndikasiPindah,AsalRuang);
    }//GEN-LAST:event_KeteranganIndikasiPindahRuangKeyPressed

    private void PendampingPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendampingPasienKeyPressed
       Valid.pindah(evt,KeluhanUtama,PDR);
    }//GEN-LAST:event_PendampingPasienKeyPressed

    private void RuangSelanjutnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RuangSelanjutnyaKeyPressed
        Valid.pindah(evt,AsalRuang,MetodePemindahan);
    }//GEN-LAST:event_RuangSelanjutnyaKeyPressed

    private void PeralatanMenyertaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeralatanMenyertaiKeyPressed
        Valid.pindah(evt,DiagnosaKeperawatan,KeteranganPeralatan);
    }//GEN-LAST:event_PeralatanMenyertaiKeyPressed

    private void DiagnosaSekunderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunderKeyPressed
             
    }//GEN-LAST:event_DiagnosaSekunderKeyPressed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
        Valid.pindah(evt,MetodePemindahan,DiagnosaSekunder);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void ObatYangDiberikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatYangDiberikanKeyPressed
        
    }//GEN-LAST:event_ObatYangDiberikanKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
       
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void IndikasiPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiPindahKeyPressed
        Valid.pindah(evt,TanggalPindah,KeteranganIndikasiPindahRuang);
    }//GEN-LAST:event_IndikasiPindahKeyPressed

    private void AsalRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRuangKeyPressed
        Valid.pindah(evt,KeteranganIndikasiPindahRuang,RuangSelanjutnya);
    }//GEN-LAST:event_AsalRuangKeyPressed

    private void MetodePemindahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MetodePemindahanKeyPressed
        Valid.pindah(evt,RuangSelanjutnya,DiagnosaUtama);
    }//GEN-LAST:event_MetodePemindahanKeyPressed

    private void NamaMenyetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaMenyetujuiKeyPressed
        Valid.pindah(evt,MenyetujuiPemindahan,HubunganMenyetujui);
    }//GEN-LAST:event_NamaMenyetujuiKeyPressed

    private void HubunganMenyetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganMenyetujuiKeyPressed
        Valid.pindah(evt,NamaMenyetujui,KeadaanUmum);
    }//GEN-LAST:event_HubunganMenyetujuiKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
       
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,HubunganMenyetujui,TD);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,KeadaanUmum,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,KeluhanUtama);
    }//GEN-LAST:event_SuhuKeyPressed

    private void ATTempatTidurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidurKeyPressed
        Valid.pindah(evt,SL,ATTempatTidurKemandirian); 
    }//GEN-LAST:event_ATTempatTidurKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

//button ambil
    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("delete from antripersetujuantransferantarruang");
                Sequel.queryu("insert into antripersetujuantransferantarruang values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"')");
                Sequel.queryu("delete from bukti_persetujuan_ transfer_pasien_antar_ruang2   where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and tanggal_masuk='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void DiagnosaKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeperawatanKeyPressed
        
    }//GEN-LAST:event_DiagnosaKeperawatanKeyPressed

    private void KeteranganPeralatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPeralatanKeyPressed
       Valid.pindah(evt,PeralatanMenyertai,MenyetujuiPemindahan);
    }//GEN-LAST:event_KeteranganPeralatanKeyPressed

    private void SGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SGKeyPressed
        
    }//GEN-LAST:event_SGKeyPressed

    private void SLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SLKeyPressed
        
    }//GEN-LAST:event_SLKeyPressed

    private void NmPetugasMenyerahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugasMenyerahkanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasMenyerahkanActionPerformed

    private void PDRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PDRKeyPressed
       Valid.pindah(evt,PendampingPasien,SG);
    }//GEN-LAST:event_PDRKeyPressed

    private void ATTempatTidur1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur1KeyPressed

    private void ATTempatTidurKemandirianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidurKemandirianKeyPressed
       Valid.pindah(evt,ATTempatTidur,HP);
    }//GEN-LAST:event_ATTempatTidurKemandirianKeyPressed

    private void ATTempatTidur3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur3KeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        pilihan=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void HPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HPKeyPressed
        Valid.pindah(evt,ATTempatTidurKemandirian,HPKemandirian);
    }//GEN-LAST:event_HPKeyPressed

    private void ATTempatTidur4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur4KeyPressed

    private void HPKemandirianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HPKemandirianKeyPressed
       Valid.pindah(evt,HP,Berpakaian);
    }//GEN-LAST:event_HPKemandirianKeyPressed

    private void ATTempatTidur5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur5KeyPressed

    private void BerpakaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BerpakaianKeyPressed
        Valid.pindah(evt,HPKemandirian,BerpakaianKemandirian);
    }//GEN-LAST:event_BerpakaianKeyPressed

    private void ATTempatTidur6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur6KeyPressed

    private void BerpakaianKemandirianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BerpakaianKemandirianKeyPressed
        Valid.pindah(evt,Berpakaian,Pergerakan);
    }//GEN-LAST:event_BerpakaianKemandirianKeyPressed

    private void ATTempatTidur7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur7KeyPressed

    private void PergerakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PergerakanKeyPressed
       Valid.pindah(evt,BerpakaianKemandirian,PergerakanKemandirian);
    }//GEN-LAST:event_PergerakanKeyPressed

    private void ATTempatTidur8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur8KeyPressed

    private void PergerakanKemandirianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PergerakanKemandirianKeyPressed
        Valid.pindah(evt,Pergerakan,Makan);
    }//GEN-LAST:event_PergerakanKemandirianKeyPressed

    private void ATTempatTidur9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATTempatTidur9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATTempatTidur9KeyPressed

    private void MakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MakanKeyPressed
        Valid.pindah(evt,PergerakanKemandirian,PemeriksaanPenunjang);
    }//GEN-LAST:event_MakanKeyPressed

    private void TindakanYangSudahDilakukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanYangSudahDilakukanKeyPressed
        Valid.pindah(evt,PemeriksaanPenunjang,Diet);
    }//GEN-LAST:event_TindakanYangSudahDilakukanKeyPressed

    private void DietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DietKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DietKeyPressed

    private void PerawatanSelanjutnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanSelanjutnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanSelanjutnyaKeyPressed

    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
        pilihan=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMenerimaActionPerformed

    private void BtnMenerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenerimaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMenerimaKeyPressed

    private void NmPetugasMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugasMenerimaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasMenerimaActionPerformed

    private void KdPetugasMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPetugasMenerimaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasMenerimaActionPerformed

    private void KdPetugasMenyerahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPetugasMenyerahkanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasMenyerahkanActionPerformed

    private void PendampingPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendampingPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendampingPasienActionPerformed

    private void BtnMenerimaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnMenerimaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMenerimaAncestorAdded

    private void PendampingPasienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_PendampingPasienAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_PendampingPasienAncestorAdded

    private void BtnDokterAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnDokterAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokterAncestorAdded

    private void BtnDokterAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnDokterAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokterAncestorMoved

    private void BtnDokterAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnDokterAncestorRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokterAncestorRemoved

    private void BtnDokterStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_BtnDokterStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokterStateChanged

    private void BtnDokterVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_BtnDokterVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokterVetoableChange

    private void NmPetugasMenyerahkanAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_NmPetugasMenyerahkanAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasMenyerahkanAncestorAdded

    private void PendampingPasienAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_PendampingPasienAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_PendampingPasienAncestorMoved

    private void KdPetugasMenyerahkanAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_KdPetugasMenyerahkanAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasMenyerahkanAncestorAdded

    private void ObatYangDiberikanAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ObatYangDiberikanAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatYangDiberikanAncestorAdded

    private void KeteranganIndikasiPindahRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeteranganIndikasiPindahRuangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganIndikasiPindahRuangActionPerformed

    private void BtnPetugasPendampingAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnPetugasPendampingAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasPendampingAncestorAdded

    private void BtnPetugasPendampingAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnPetugasPendampingAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasPendampingAncestorMoved

    private void BtnPetugasPendampingAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_BtnPetugasPendampingAncestorRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasPendampingAncestorRemoved

    private void BtnPetugasPendampingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_BtnPetugasPendampingStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasPendampingStateChanged

    private void BtnPetugasPendampingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasPendampingActionPerformed
        pilihan=3;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasPendampingActionPerformed

    private void BtnPetugasPendampingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasPendampingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasPendampingKeyPressed

    private void BtnPetugasPendampingVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_BtnPetugasPendampingVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasPendampingVetoableChange

    private void BtnSimpanMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_BtnSimpanMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpanMouseWheelMoved

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTransferPasienAntarRuang2 dialog = new RMTransferPasienAntarRuang2(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.ComboBox ATTempatTidur;
    private widget.ComboBox ATTempatTidur1;
    private widget.ComboBox ATTempatTidur3;
    private widget.ComboBox ATTempatTidur4;
    private widget.ComboBox ATTempatTidur5;
    private widget.ComboBox ATTempatTidur6;
    private widget.ComboBox ATTempatTidur7;
    private widget.ComboBox ATTempatTidur8;
    private widget.ComboBox ATTempatTidur9;
    private widget.ComboBox ATTempatTidurKemandirian;
    private widget.TextBox AsalRuang;
    private widget.ComboBox Berpakaian;
    private widget.ComboBox BerpakaianKemandirian;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenerima;
    private widget.Button BtnPetugasPendamping;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DiagnosaKeperawatan;
    private widget.TextArea DiagnosaSekunder;
    private widget.TextBox DiagnosaUtama;
    private widget.TextArea Diet;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox HP;
    private widget.ComboBox HPKemandirian;
    private widget.ComboBox HubunganMenyetujui;
    private widget.ComboBox IndikasiPindah;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugasMenerima;
    private widget.TextBox KdPetugasMenyerahkan;
    private widget.ComboBox KeadaanUmum;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KeteranganIndikasiPindahRuang;
    private widget.TextBox KeteranganPeralatan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ComboBox Makan;
    private widget.ComboBox MenyetujuiPemindahan;
    private widget.ComboBox MetodePemindahan;
    private widget.TextBox Nadi;
    private widget.TextBox NamaMenyetujui;
    private widget.TextBox NmPetugasMenerima;
    private widget.TextBox NmPetugasMenyerahkan;
    private widget.TextArea ObatYangDiberikan;
    private widget.ComboBox PDR;
    private widget.PanelBiasa PanelAccor;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.TextBox PendampingPasien;
    private widget.ComboBox PeralatanMenyertai;
    private widget.TextArea PerawatanSelanjutnya;
    private widget.ComboBox Pergerakan;
    private widget.ComboBox PergerakanKemandirian;
    private widget.TextBox RR;
    private widget.TextBox RuangSelanjutnya;
    private widget.TextArea SG;
    private widget.TextArea SL;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalMasuk;
    private widget.Tanggal TanggalPindah;
    private widget.TextBox TglLahir;
    private widget.TextArea TindakanYangSudahDilakukan;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel30;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel44;
    private widget.Label jLabel53;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                         "transfer_pasien_antar_ruang2.tanggal_masuk, "+
                        "transfer_pasien_antar_ruang2.tanggal_pindah,"+
                        "transfer_pasien_antar_ruang2.indikasi_pindah_ruang,"+ 
                        "transfer_pasien_antar_ruang2.keterangan_indikasi_pindah_ruang,"+  
                        "transfer_pasien_antar_ruang2.asal_ruang,"+
                        "transfer_pasien_antar_ruang2.ruang_selanjutnya, "+
                        "transfer_pasien_antar_ruang2.metode_pemindahan_pasien,"+         
                        "transfer_pasien_antar_ruang2.diagnosa_utama,"+ 
                        "transfer_pasien_antar_ruang2.diagnosa_sekunder,"+
                        "transfer_pasien_antar_ruang2.Diagnosa_Keperawatan,"+       
                        "transfer_pasien_antar_ruang2.peralatan_yang_menyertai,"+
                        "transfer_pasien_antar_ruang2.keterangan_peralatan_yang_menyertai,"+ 
                        "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang2.pasien_keluarga_menyetujui, "+ 
                        "transfer_pasien_antar_ruang2.nama_menyetujui, "+ 
                        "transfer_pasien_antar_ruang2.hubungan_menyetujui,"+
                        "transfer_pasien_antar_ruang2.keluhan_utama,"+        
                        "transfer_pasien_antar_ruang2.keadaan_umum,"+
                        "transfer_pasien_antar_ruang2.td, "+ 
                        "transfer_pasien_antar_ruang2.nadi,"+ 
                        "transfer_pasien_antar_ruang2.rr,"+
                        "transfer_pasien_antar_ruang2.suhu,"+ 
                        "transfer_pasien_antar_ruang2.Pendamping_Pasien,"+
                        "transfer_pasien_antar_ruang2.PDR,"+
                        "transfer_pasien_antar_ruang2.SG,"+
                        "transfer_pasien_antar_ruang2.SL,"+
                        "transfer_pasien_antar_ruang2.ATTempat_Tidur,"+
                        "transfer_pasien_antar_ruang2.ATTempat_Tidur_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.HP,"+
                        "transfer_pasien_antar_ruang2.HP_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.Berpakaian,"+
                        "transfer_pasien_antar_ruang2.Berpakaian_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.Pergerakan,"+
                        "transfer_pasien_antar_ruang2.Pergerakan_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.Makan,"+
                        "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang2.Tindakan_Yang_Sudah_Dilakukan,"+
                        "transfer_pasien_antar_ruang2.Diet,"+
                        "transfer_pasien_antar_ruang2.Perawatan_Selanjutnya,"+
                        "transfer_pasien_antar_ruang2.obat_yang_telah_diberikan,"+        
                        "transfer_pasien_antar_ruang2.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan, "+ 
                        "transfer_pasien_antar_ruang2.nip_menerima,"+
                        "petugasmenerima.nama as petugasmenerima "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join  transfer_pasien_antar_ruang2   on reg_periksa.no_rawat= transfer_pasien_antar_ruang2  .no_rawat "+
                        "inner join petugas as petugasmenyerahkan on  transfer_pasien_antar_ruang2  .nip_menyerahkan=petugasmenyerahkan.nip "+
                        "inner join petugas as petugasmenerima on  transfer_pasien_antar_ruang2  .nip_menerima=petugasmenerima.nip where "+
                        " transfer_pasien_antar_ruang2  .tanggal_pindah between ? and ? order by  transfer_pasien_antar_ruang2  .tanggal_pindah");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                        "transfer_pasien_antar_ruang2.tanggal_masuk, "+
                        "transfer_pasien_antar_ruang2.tanggal_pindah,"+
                        "transfer_pasien_antar_ruang2.indikasi_pindah_ruang,"+ 
                        "transfer_pasien_antar_ruang2.keterangan_indikasi_pindah_ruang,"+  
                        "transfer_pasien_antar_ruang2.asal_ruang,"+
                        "transfer_pasien_antar_ruang2.ruang_selanjutnya, "+
                        "transfer_pasien_antar_ruang2.metode_pemindahan_pasien,"+         
                        "transfer_pasien_antar_ruang2.diagnosa_utama,"+ 
                        "transfer_pasien_antar_ruang2.diagnosa_sekunder,"+
                        "transfer_pasien_antar_ruang2.Diagnosa_Keperawatan,"+       
                        "transfer_pasien_antar_ruang2.peralatan_yang_menyertai,"+
                        "transfer_pasien_antar_ruang2.keterangan_peralatan_yang_menyertai,"+ 
                        "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang2.pasien_keluarga_menyetujui, "+ 
                        "transfer_pasien_antar_ruang2.nama_menyetujui, "+ 
                        "transfer_pasien_antar_ruang2.hubungan_menyetujui,"+
                        "transfer_pasien_antar_ruang2.keluhan_utama, "+         
                        "transfer_pasien_antar_ruang2.keadaan_umum,"+
                        "transfer_pasien_antar_ruang2.td, "+ 
                        "transfer_pasien_antar_ruang2.nadi,"+ 
                        "transfer_pasien_antar_ruang2.rr,"+
                        "transfer_pasien_antar_ruang2.suhu,"+   
                        "transfer_pasien_antar_ruang2.Pendamping_Pasien,"+
                        "transfer_pasien_antar_ruang2.PDR,"+
                        "transfer_pasien_antar_ruang2.SG,"+
                        "transfer_pasien_antar_ruang2.SL,"+
                        "transfer_pasien_antar_ruang2.ATTempat_Tidur,"+
                        "transfer_pasien_antar_ruang2.ATTempat_Tidur_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.HP,"+
                        "transfer_pasien_antar_ruang2.HP_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.Berpakaian,"+
                        "transfer_pasien_antar_ruang2.Berpakaian_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.Pergerakan,"+
                        "transfer_pasien_antar_ruang2.Pergerakan_Kemandirian,"+
                        "transfer_pasien_antar_ruang2.Makan,"+
                        "transfer_pasien_antar_ruang2.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang2.Tindakan_Yang_Sudah_Dilakukan,"+
                        "transfer_pasien_antar_ruang2.Diet,"+
                        "transfer_pasien_antar_ruang2.Perawatan_Selanjutnya,"+
                        "transfer_pasien_antar_ruang2.obat_yang_telah_diberikan,"+     
                        "transfer_pasien_antar_ruang2.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan, transfer_pasien_antar_ruang2  .nip_menerima,"+
                        "petugasmenerima.nama as petugasmenerima "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join  transfer_pasien_antar_ruang2   on reg_periksa.no_rawat= transfer_pasien_antar_ruang2  .no_rawat "+
                        "inner join petugas as petugasmenyerahkan on  transfer_pasien_antar_ruang2  .nip_menyerahkan=petugasmenyerahkan.nip "+
                        "inner join petugas as petugasmenerima on  transfer_pasien_antar_ruang2  .nip_menerima=petugasmenerima.nip where "+
                        " transfer_pasien_antar_ruang2.tanggal_pindah between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        " transfer_pasien_antar_ruang2.nip_menyerahkan like ? or petugasmenyerahkan.nama like ?) order by  transfer_pasien_antar_ruang2  .tanggal_pindah");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("tgl_lahir"),
                        rs.getString("jk"),
                        rs.getString("tanggal_masuk"),
                        rs.getString("tanggal_pindah"),
                        rs.getString("indikasi_pindah_ruang"),
                        rs.getString("keterangan_indikasi_pindah_ruang"),
                        rs.getString("asal_ruang"),
                        rs.getString("ruang_selanjutnya"),
                        rs.getString("metode_pemindahan_pasien"),
                        rs.getString("diagnosa_utama"),
                        rs.getString("diagnosa_sekunder"),
                        rs.getString("Diagnosa_Keperawatan"),
                        rs.getString("peralatan_yang_menyertai"),
                        rs.getString("keterangan_peralatan_yang_menyertai"),
                        rs.getString("pasien_keluarga_menyetujui"),
                        rs.getString("nama_menyetujui"),
                        rs.getString("hubungan_menyetujui"),
                        rs.getString("keluhan_utama"),
                        rs.getString("keadaan_umum"),
                        rs.getString("td"),
                        rs.getString("nadi"),
                        rs.getString("rr"),
                        rs.getString("suhu"),
                        rs.getString("Pendamping_Pasien"),
                        rs.getString("PDR"),
                        rs.getString("SG"),
                        rs.getString("SL"),
                        rs.getString("ATTempat_Tidur"),
                        rs.getString("ATTempat_Tidur_Kemandirian"),
                        rs.getString("HP"),
                        rs.getString("HP_Kemandirian"),
                        rs.getString("Berpakaian"),
                        rs.getString("Berpakaian_Kemandirian"),
                        rs.getString("Pergerakan"),
                        rs.getString("Pergerakan_Kemandirian"),
                        rs.getString("Makan"),
                        rs.getString("pemeriksaan_penunjang_yang_dilakukan"),
                        rs.getString("Tindakan_Yang_Sudah_Dilakukan"),
                        rs.getString("Diet"),
                        rs.getString("Perawatan_Selanjutnya"),
                        rs.getString("obat_yang_telah_diberikan"),
                        rs.getString("nip_menyerahkan"),
                        rs.getString("petugasmenyerahkan"),
                        rs.getString("nip_menerima"),
                        rs.getString("petugasmenerima"),
                           
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TanggalMasuk.setDate(new Date());
        TanggalPindah.setDate(new Date());
        IndikasiPindah.setSelectedIndex(0);
        KeteranganIndikasiPindahRuang.setText("");
        AsalRuang.setText("");
        RuangSelanjutnya.setText("");
        MetodePemindahan.setSelectedIndex(0);
        DiagnosaUtama.setText("");
        DiagnosaSekunder.setText("");
        DiagnosaKeperawatan.setText("");
        KeteranganPeralatan.setText("");                                                                                                                                                                                                                                                                                                                                                                            
        ObatYangDiberikan.setText("");
        PeralatanMenyertai.setSelectedIndex(0);
        PendampingPasien.setText("");
        PDR.setSelectedIndex(0);
        SG.setText("");
        SL.setText("");
        ATTempatTidur.setSelectedIndex(0);
        ATTempatTidurKemandirian.setSelectedIndex(0);
        HP.setSelectedIndex(0);
        HPKemandirian.setSelectedIndex(0);
        Berpakaian.setSelectedIndex(0);
        BerpakaianKemandirian.setSelectedIndex(0);
        Pergerakan.setSelectedIndex(0);
        PergerakanKemandirian.setSelectedIndex(0);   
        Makan.setSelectedIndex(0);        
        PemeriksaanPenunjang.setText("");
        TindakanYangSudahDilakukan.setText("");
        Diet.setText("");
        PerawatanSelanjutnya.setText("");
        MenyetujuiPemindahan.setSelectedIndex(0);                                                                                                           
        NamaMenyetujui.setText("");                                                                                                                         
        HubunganMenyetujui.setSelectedIndex(0);
        KeadaanUmum.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        KeluhanUtama.setText("");
        ATTempatTidur.setSelectedIndex(0);
        TabRawat.setSelectedIndex(0);
        IndikasiPindah.requestFocus();
        KdPetugasMenyerahkan.setText("");
        NmPetugasMenyerahkan.setText("");
        KdPetugasMenerima.setText("");
        NmPetugasMenerima.setText("");
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            IndikasiPindah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            KeteranganIndikasiPindahRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            AsalRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            RuangSelanjutnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            MetodePemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            DiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            DiagnosaSekunder.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            ObatYangDiberikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            PeralatanMenyertai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            PendampingPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            MenyetujuiPemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            NamaMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            HubunganMenyetujui.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeadaanUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString()); 
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()); 
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            ATTempatTidur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KdPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString()); 
            NmPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()); 
            KdPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString()); 
            NmPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()); 
            Valid.SetTgl2(TanggalMasuk,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Valid.SetTgl2(TanggalPindah,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        if(Sequel.cariInteger("select count(no_rawat) from pemeriksaan_ralan where no_rawat='"+TNoRw.getText()+"' ")>0){ 
            KeluhanUtama.setText(Sequel.cariIsi("select keluhan from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            Suhu.setText(Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ralan where no_rawat=?",TNoRw.getText())); 
            TD.setText(Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText())); 
            Nadi.setText(Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText())); 
            RR.setText(Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText())); 
        }else{
    }
        }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettransfer_pasien_antar_ruang ());
        BtnHapus.setEnabled(akses.gettransfer_pasien_antar_ruang ());
        BtnEdit.setEnabled(akses.gettransfer_pasien_antar_ruang ());
        BtnPrint.setEnabled(akses.gettransfer_pasien_antar_ruang ());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from  transfer_pasien_antar_ruang2   where no_rawat=? and tanggal_masuk=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("transfer_pasien_antar_ruang2","no_rawat=? and tanggal_masuk=?","no_rawat=?,tanggal_masuk=?,tanggal_pindah=?,"+
                 "indikasi_pindah_ruang=?,"+ 
                 "keterangan_indikasi_pindah_ruang=?,"+  
                 "asal_ruang=?,"+
                 "ruang_selanjutnya=?, "+
                 "metode_pemindahan_pasien=?,"+         
                 "diagnosa_utama=?,"+ 
                 "diagnosa_sekunder=?,"+
                 "Diagnosa_Keperawatan=?,"+       
                 "peralatan_yang_menyertai=?,"+
                 "keterangan_peralatan_yang_menyertai=?"+ 
                 "pemeriksaan_penunjang_yang_dilakukan=?,"+
                 "pasien_keluarga_menyetujui=?,"+ 
                 "nama_menyetujui=?,"+ 
                 "hubungan_menyetujui=?,"+
                 "keluhan_utama=?,"+         
                 "keadaan_umum=?,"+
                 "td=?, "+ 
                 "nadi=?,"+ 
                 "rr=?,"+
                 "suhu=?,"+   
                 "Pendamping_Pasien=?,"+
                 "PDR=?,"+
                 "SG=?,"+
                 "SL=?,"+
                 "ATTempat_Tidur=?,"+
                 "ATTempat_Tidur_Kemandirian=?,"+
                 "HP=?,"+
                 "HP_Kemandirian=?,"+
                 "Berpakaian=?,"+
                 "Berpakaian_Kemandirian=?,"+
                 "Pergerakan=?,"+
                 "Pergerakan_Kemandirian=?,"+
                 "Makan=?,"+
                 "pemeriksaan_penunjang_yang_dilakukan=?,"+
                 "Tindakan_Yang_Sudah_Dilakukan=?,"+
                 "Diet=?,"+
                 "Perawatan_Selanjutnya=?,"+
                 "obat_yang_telah_diberikan=?,"+         
                "nip_menyerahkan=?,nip_menerima=?",46,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),AsalRuang.getText(), 
                RuangSelanjutnya.getText(),DiagnosaUtama.getText(),DiagnosaSekunder.getText(),IndikasiPindah.getSelectedItem().toString(),KeteranganIndikasiPindahRuang.getText(), 
                ObatYangDiberikan.getText(),MetodePemindahan.getSelectedItem().toString(),PeralatanMenyertai.getSelectedItem().toString(),
                PendampingPasien.getText(),PemeriksaanPenunjang.getText(),MenyetujuiPemindahan.getSelectedItem().toString(),NamaMenyetujui.getText(),
                HubunganMenyetujui.getSelectedItem().toString(),KeluhanUtama.getText(),KeadaanUmum.getSelectedItem().toString(),
                TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),
                KdPetugasMenyerahkan.getText(),KdPetugasMenerima.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(IndikasiPindah.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(KeteranganIndikasiPindahRuang.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(AsalRuang.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(RuangSelanjutnya.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(MetodePemindahan.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(DiagnosaUtama.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(DiagnosaSekunder.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(ObatYangDiberikan.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(PemeriksaanPenunjang.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(PeralatanMenyertai.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(PendampingPasien.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(MenyetujuiPemindahan.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(NamaMenyetujui.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(HubunganMenyetujui.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(KeadaanUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(ATTempatTidur.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(KdPetugasMenyerahkan.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(NmPetugasMenyerahkan.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(KdPetugasMenerima.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(NmPetugasMenerima.getText(),tbObat.getSelectedRow(),37);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf(" transfer_pasien_antar_ruang2  ","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",42,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),
                IndikasiPindah.getSelectedItem().toString(),
                KeteranganIndikasiPindahRuang.getText(), 
                AsalRuang.getText(),
                RuangSelanjutnya.getText(),
                MetodePemindahan.getSelectedItem().toString(),
                DiagnosaUtama.getText(),
                DiagnosaSekunder.getText(),
                DiagnosaKeperawatan.getText(),
                PeralatanMenyertai.getSelectedItem().toString(),
                KeteranganPeralatan.getText(),
                MenyetujuiPemindahan.getSelectedItem().toString(),
                NamaMenyetujui.getText(),
                HubunganMenyetujui.getSelectedItem().toString(),
                KeluhanUtama.getText(),
                KeadaanUmum.getSelectedItem().toString(),
                TD.getText(),
                Nadi.getText(),
                RR.getText(),
                Suhu.getText(),
                PendampingPasien.getText(),
                PDR.getSelectedItem().toString(),
                SG.getText(),
                SL.getText(),
                ATTempatTidur.getSelectedItem().toString(),
                ATTempatTidurKemandirian.getSelectedItem().toString(),
                HP.getSelectedItem().toString(),
                HPKemandirian.getSelectedItem().toString(),
                Berpakaian.getSelectedItem().toString(),
                BerpakaianKemandirian.getSelectedItem().toString(),
                Pergerakan.getSelectedItem().toString(),
                PergerakanKemandirian.getSelectedItem().toString(),
                Makan.getSelectedItem().toString(),
                PemeriksaanPenunjang.getText(),
                TindakanYangSudahDilakukan.getText(),
                Diet.getText(),
                PerawatanSelanjutnya.getText(),
                ObatYangDiberikan.getText(),
                KdPetugasMenyerahkan.getText(),
                KdPetugasMenerima.getText()
            })==true){
                emptTeks();
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(480,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select bukti_persetujuan_transfer_pasien_antar_ruang2.photo from bukti_persetujuan_transfer_pasien_antar_ruang2   where bukti_persetujuan_transfer_pasien_antar_ruang2.no_rawat=? and bukti_persetujuan_transfer_pasien_antar_ruang2.tanggal_masuk=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/persetujuantransferruang/"+rs.getString("photo")+"' alt='photo' width='500' height='500'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
    }
}
