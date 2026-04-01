package rekammedis;

import fungsi.akses;
import fungsi.koneksiDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import widget.Button;
import widget.ComboBox;
import widget.InternalFrame;
import widget.Label;
import widget.ScrollPane;
import widget.Table;
import widget.Tanggal;
import widget.TextArea;
import widget.TextBox;
import widget.panelisi;

/**
 * Scaffold form baru untuk Asesmen Awal Rawat Inap Medis.
 * Form ini disiapkan sebagai basis pengembangan lanjutan dari dokumen PDF
 * dan belum dihubungkan ke database/menu utama.
 */
public final class AsesmenAwalRawatInapMedis extends javax.swing.JDialog {

    private static final String NAMA_TABEL = "asesmen_awal_rawat_inap_medis";
    private static final Color WARNA_FORM = new Color(246, 249, 244);
    private static final Color WARNA_PANEL = new Color(255, 255, 255);
    private static final Color WARNA_PANEL_AKSESEN = new Color(250, 252, 248);
    private static final Color WARNA_BORDER = new Color(214, 222, 214);
    private static final Color WARNA_JUDUL = new Color(62, 83, 62);
    private static final Color WARNA_READONLY = new Color(242, 245, 239);
    private static final int LEBAR_LABEL = 170;
    private static final int TINGGI_FIELD = 28;
    private static final int LEBAR_FIELD_KECIL = 120;
    private static final int LEBAR_FIELD_SEDANG = 170;
    private static final int LEBAR_FIELD_BESAR = 230;
    private final DefaultTableModel tabMode;
    private final SimpleDateFormat tanggalTabel = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat jamSekarang = new SimpleDateFormat("HH:mm:ss");
    private final Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private final DlgCariDokter dokter = new DlgCariDokter(null, false);

    private InternalFrame internalFrame1;
    private panelisi panelGlass8;
    private javax.swing.JTabbedPane TabRawat;
    private InternalFrame internalFrame2;
    private ScrollPane scrollInput;
    private JPanel FormInput;
    private InternalFrame internalFrame3;
    private ScrollPane ScrollData;
    private Table tbData;
    private panelisi panelGlass9;
    private Tanggal DTPCari1;
    private Tanggal DTPCari2;
    private TextBox TCari;
    private Button BtnCari;
    private Button BtnAll;
    private Label LCount;
    private Button BtnSimpan;
    private Button BtnBatal;
    private Button BtnHapus;
    private Button BtnEdit;
    private Button BtnPrint;
    private Button BtnKeluar;

    private TextBox TNoRw;
    private TextBox TNoRM;
    private TextBox TPasien;
    private TextBox TglLahir;
    private TextBox Jk;
    private Tanggal TglAsesmen;
    private TextBox JamAsesmen;
    private TextBox KdDokter;
    private TextBox NmDokter;
    private TextBox SumberRujukan;
    private TextBox DiagnosaRujukan;
    private ComboBox Anamnesis;
    private TextBox Hubungan;
    private TextBox CaraDatang;
    private TextArea KeluhanUtama;
    private TextArea RiwayatPenyakitSekarang;
    private TextArea RiwayatPenyakitDahulu;
    private TextArea RiwayatPengobatan;
    private TextArea RiwayatPenyakitKeluarga;
    private TextBox RiwayatAlergiObat;
    private ComboBox Kesadaran;
    private TextBox BeratBadan;
    private TextBox TinggiBadan;
    private TextBox TekananDarah;
    private TextBox Nadi;
    private TextBox Respirasi;
    private TextBox Suhu;
    private TextBox SpO2;
    private TextArea PemeriksaanMata;
    private TextArea SistemSaraf;
    private TextArea PemeriksaanTHT;
    private TextArea PemeriksaanThoraks;
    private TextArea PemeriksaanAbdomen;
    private TextArea PemeriksaanEkstremitas;
    private TextArea StatusLokalisasi;
    private TextArea HasilLaboratorium;
    private TextArea HasilEKG;
    private TextArea HasilRadiologi;
    private TextArea DiagnosisBanding;
    private TextArea RencanaKerjaTerapi;
    private TextArea CatatanPenting;
    private TextBox DokterPengkaji;
    private TextBox DokterDPJP;
    private TextBox InfoTanggalJamPengkajian;
    private JCheckBox RujukanORS;
    private JCheckBox RujukanPuskesmas;
    private JCheckBox RujukanPraktek;
    private JCheckBox RujukanLainnya;
    private JCheckBox CaraDatangSendiri;
    private JCheckBox CaraDatangDiantar;
    private JCheckBox RiwayatHipertensi;
    private JCheckBox RiwayatDiabetes;
    private JCheckBox RiwayatJantungKoroner;
    private JCheckBox RiwayatAsma;
    private JCheckBox RiwayatStroke;
    private JCheckBox RiwayatAlergiObatCheck;
    private JCheckBox RiwayatLiver;
    private JCheckBox RiwayatGinjal;
    private JCheckBox RiwayatTBParu;
    private JCheckBox RiwayatLainnyaCheck;
    private TextBox RiwayatDahuluLainnya;
    private JCheckBox PernahDirawatTidak;
    private JCheckBox PernahDirawatYa;
    private TextBox PernahDirawatKapan;
    private TextBox PernahDirawatDimana;
    private final TextBox[] ObatNama = new TextBox[4];
    private final TextBox[] ObatDosis = new TextBox[4];
    private final TextBox[] ObatWaktu = new TextBox[4];
    private JCheckBox KeluargaHipertensi;
    private JCheckBox KeluargaDiabetes;
    private JCheckBox KeluargaJantung;
    private JCheckBox KeluargaAsma;
    private JCheckBox KeluargaLainnyaCheck;
    private TextBox KeluargaLainnya;
    private TextBox DiagnosisBanding1;
    private TextBox DiagnosisBanding2;
    private TextBox DiagnosisBanding3;
    private TextBox DiagnosisBanding4;

    public AsesmenAwalRawatInapMedis(Frame parent, boolean modal) {
        super(parent, modal);
        tabMode = new DefaultTableModel(
            new Object[]{
                "No.Rawat", "No.RM", "Nama Pasien", "Tanggal Asesmen",
                "Dokter", "Keluhan Utama", "Diagnosis"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        initComponents();
        buildInputLayout();
        initTable();
        emptTeks();
        isCek();
        ensureTableExists();
        tampil();

        setLocation(8, 1);
        setSize(1100, 720);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setLayout(new BorderLayout(1, 1));

        internalFrame1 = new InternalFrame();
        internalFrame1.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(240, 245, 235)),
            "::[ Asesmen Awal Rawat Inap Medis ]::",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Tahoma", 0, 11),
            new Color(50, 50, 50)
        ));
        internalFrame1.setLayout(new BorderLayout(1, 1));

        panelGlass8 = new panelisi();
        panelGlass8.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 9));
        panelGlass8.setPreferredSize(new Dimension(44, 54));

        BtnSimpan = createButton("Simpan", "/picture/save-16x16.png");
        BtnSimpan.addActionListener(this::BtnSimpanActionPerformed);
        panelGlass8.add(BtnSimpan);

        BtnBatal = createButton("Baru", "/picture/Cancel-2-16x16.png");
        BtnBatal.addActionListener(this::BtnBatalActionPerformed);
        panelGlass8.add(BtnBatal);

        BtnHapus = createButton("Hapus", "/picture/stop_f2.png");
        BtnHapus.addActionListener(this::BtnHapusActionPerformed);
        panelGlass8.add(BtnHapus);

        BtnEdit = createButton("Ganti", "/picture/inventaris.png");
        BtnEdit.addActionListener(this::BtnEditActionPerformed);
        panelGlass8.add(BtnEdit);

        BtnPrint = createButton("Cetak", "/picture/b_print.png");
        BtnPrint.addActionListener(this::BtnPrintActionPerformed);
        panelGlass8.add(BtnPrint);

        BtnKeluar = createButton("Keluar", "/picture/exit.png");
        BtnKeluar.addActionListener(this::BtnKeluarActionPerformed);
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, BorderLayout.PAGE_END);

        TabRawat = new javax.swing.JTabbedPane();

        internalFrame2 = new InternalFrame();
        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new BorderLayout(1, 1));
        internalFrame2.setBackground(WARNA_FORM);

        scrollInput = new ScrollPane();
        scrollInput.setName("scrollInput");
        scrollInput.getVerticalScrollBar().setUnitIncrement(18);

        FormInput = new JPanel();
        FormInput.setBackground(WARNA_FORM);
        FormInput.setBorder(null);
        FormInput.setPreferredSize(new Dimension(980, 2450));

        scrollInput.setViewportView(FormInput);
        internalFrame2.add(scrollInput, BorderLayout.CENTER);
        TabRawat.addTab("Input", internalFrame2);

        internalFrame3 = new InternalFrame();
        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new BorderLayout(1, 1));
        internalFrame3.setBackground(WARNA_FORM);

        panelGlass9 = new panelisi();
        panelGlass9.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 9));

        Label lblTgl1 = new Label();
        lblTgl1.setText("Tgl. Awal :");
        panelGlass9.add(lblTgl1);

        DTPCari1 = new Tanggal();
        DTPCari1.setPreferredSize(new Dimension(140, 23));
        panelGlass9.add(DTPCari1);

        Label lblTgl2 = new Label();
        lblTgl2.setText("Tgl. Akhir :");
        panelGlass9.add(lblTgl2);

        DTPCari2 = new Tanggal();
        DTPCari2.setPreferredSize(new Dimension(140, 23));
        panelGlass9.add(DTPCari2);

        Label lblCari = new Label();
        lblCari.setText("Cari :");
        panelGlass9.add(lblCari);

        TCari = new TextBox();
        TCari.setPreferredSize(new Dimension(220, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    BtnCariActionPerformed(null);
                }
            }
        });
        panelGlass9.add(TCari);

        BtnCari = createButton("Cari", "/picture/accept.png");
        BtnCari.setPreferredSize(new Dimension(90, 30));
        BtnCari.addActionListener(this::BtnCariActionPerformed);
        panelGlass9.add(BtnCari);

        BtnAll = createButton("Semua", "/picture/Search-16x16.png");
        BtnAll.setPreferredSize(new Dimension(90, 30));
        BtnAll.addActionListener(this::BtnAllActionPerformed);
        panelGlass9.add(BtnAll);

        Label lblCountTitle = new Label();
        lblCountTitle.setText("Record :");
        panelGlass9.add(lblCountTitle);

        LCount = new Label();
        LCount.setHorizontalAlignment(SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new Dimension(80, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, BorderLayout.PAGE_START);

        ScrollData = new ScrollPane();
        ScrollData.getVerticalScrollBar().setUnitIncrement(18);
        tbData = new Table();
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tbData.getSelectedRow() > -1) {
                    loadRecord(tbData.getValueAt(tbData.convertRowIndexToModel(tbData.getSelectedRow()), 0).toString());
                }
                if (evt.getClickCount() == 2) {
                    TabRawat.setSelectedIndex(0);
                }
            }
        });
        ScrollData.setViewportView(tbData);
        internalFrame3.add(ScrollData, BorderLayout.CENTER);
        TabRawat.addTab("Data", internalFrame3);

        internalFrame1.add(TabRawat, BorderLayout.CENTER);
        add(internalFrame1, BorderLayout.CENTER);
    }

    private Button createButton(String text, String iconPath) {
        Button button = new Button();
        button.setText(text);
        button.setPreferredSize(new Dimension(100, 30));
        if (getClass().getResource(iconPath) != null) {
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        }
        return button;
    }

    private void buildInputLayout() {
        FormInput.removeAll();
        FormInput.setLayout(new javax.swing.BoxLayout(FormInput, javax.swing.BoxLayout.Y_AXIS));
        FormInput.setBorder(BorderFactory.createEmptyBorder(14, 14, 16, 14));

        FormInput.add(buildHeaderPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildIdentitasPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildRujukanPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildRiwayatPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildTandaVitalPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildPemeriksaanFisikPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildPenunjangPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildDiagnosisPanel());
        FormInput.add(Box.createVerticalStrut(10));
        FormInput.add(buildPengkajianPanel());
        FormInput.add(Box.createVerticalGlue());

        FormInput.revalidate();
        FormInput.repaint();
    }

    private JPanel buildHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(WARNA_PANEL_AKSESEN);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNA_BORDER),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JLabel title = new JLabel("Asesmen Awal Rawat Inap Medis");
        title.setFont(new java.awt.Font("Tahoma", 1, 14));
        title.setForeground(WARNA_JUDUL);
        panel.add(title, BorderLayout.NORTH);

        JLabel subtitle = new JLabel("Lengkapi identitas, riwayat, pemeriksaan fisik, penunjang, diagnosis, dan pengkajian pasien.");
        subtitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        subtitle.setForeground(new Color(90, 98, 90));
        panel.add(subtitle, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildIdentitasPanel() {
        JPanel panel = createSectionPanel("I. Identitas Pasien");

        TNoRw = createField(LEBAR_FIELD_KECIL, false);
        TNoRM = createField(LEBAR_FIELD_KECIL, true);
        TPasien = createField(LEBAR_FIELD_BESAR, true);
        TglLahir = createField(LEBAR_FIELD_SEDANG, true);
        Jk = createField(LEBAR_FIELD_KECIL, true);
        TglAsesmen = createDateField(LEBAR_FIELD_SEDANG);
        JamAsesmen = createField(LEBAR_FIELD_KECIL, false);
        KdDokter = createField(LEBAR_FIELD_KECIL, false);
        NmDokter = createField(LEBAR_FIELD_BESAR, true);

        addThree(panel, 0, "No.Rawat :", TNoRw, "No.RM :", TNoRM, "Tgl. Asesmen :", TglAsesmen);
        addThree(panel, 1, "Nama Pasien :", TPasien, "Tgl. Lahir :", TglLahir, "Jam :", JamAsesmen);
        addThree(panel, 2, "Jenis Kelamin :", Jk, "Kode Dokter :", KdDokter, "Dokter Pengkaji :", NmDokter);

        return panel;
    }

    private JPanel buildRujukanPanel() {
        JPanel panel = createSectionPanel("II. Rujukan dan Anamnesis");

        SumberRujukan = createField(260, false);
        DiagnosaRujukan = createField(LEBAR_FIELD_BESAR, false);
        Anamnesis = createComboField(LEBAR_FIELD_SEDANG);
        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Autoanamnesis", "Alloanamnesis"}));
        Hubungan = createField(LEBAR_FIELD_KECIL, false);
        CaraDatang = createField(LEBAR_FIELD_SEDANG, false);
        KeluhanUtama = createArea(4);
        RiwayatPenyakitSekarang = createArea(4);
        RujukanORS = createCheck("ORS");
        RujukanPuskesmas = createCheck("Puskesmas");
        RujukanPraktek = createCheck("Praktek");
        RujukanLainnya = createCheck("Lainnya");
        CaraDatangSendiri = createCheck("Datang Sendiri");
        CaraDatangDiantar = createCheck("Diantar");

        addCardPair(panel, 0,
            createFieldGroupCard(
                "Rujukan",
                createOptionLine(RujukanORS, RujukanPuskesmas, RujukanPraktek, RujukanLainnya),
                createLabeledLine("Keterangan", SumberRujukan)
            ),
            createFieldGroupCard(
                "Cara Datang",
                createOptionLine(CaraDatangSendiri, CaraDatangDiantar),
                createLabeledLine("Hubungan dengan pasien", Hubungan)
            )
        );
        addCardPair(panel, 1,
            createFieldCard("Diagnosa Rujukan", DiagnosaRujukan),
            createFieldGroupCard(
                "Anamnesis",
                createLabeledLine("Jenis", Anamnesis),
                createInfoLabel("Tanggal dan jam asesmen mengikuti identitas di bagian atas.")
            )
        );
        addCardPair(panel, 2,
            createAreaCard("Keluhan Utama", KeluhanUtama, 190),
            createAreaCard("Riwayat Penyakit Sekarang", RiwayatPenyakitSekarang, 190)
        );

        return panel;
    }

    private JPanel buildRiwayatPanel() {
        JPanel panel = createSectionPanel("III. Riwayat Penyakit dan Pengobatan");

        RiwayatPenyakitDahulu = createArea(4);
        RiwayatPengobatan = createArea(5);
        RiwayatPenyakitKeluarga = createArea(4);
        RiwayatAlergiObat = createField(LEBAR_FIELD_BESAR, false);
        RiwayatHipertensi = createCheck("Hipertensi");
        RiwayatDiabetes = createCheck("Diabetes Melitus");
        RiwayatJantungKoroner = createCheck("Penyakit Jantung Koroner");
        RiwayatAsma = createCheck("Asma");
        RiwayatStroke = createCheck("Stroke");
        RiwayatAlergiObatCheck = createCheck("Riwayat Alergi Obat");
        RiwayatLiver = createCheck("Liver");
        RiwayatGinjal = createCheck("Ginjal");
        RiwayatTBParu = createCheck("Tb Paru");
        RiwayatLainnyaCheck = createCheck("Lain-lain");
        RiwayatDahuluLainnya = createField(220, false);
        PernahDirawatTidak = createCheck("Tidak");
        PernahDirawatYa = createCheck("Ya");
        PernahDirawatKapan = createField(140, false);
        PernahDirawatDimana = createField(180, false);
        KeluargaHipertensi = createCheck("Hipertensi");
        KeluargaDiabetes = createCheck("Diabetes Melitus");
        KeluargaJantung = createCheck("Jantung");
        KeluargaAsma = createCheck("Asma");
        KeluargaLainnyaCheck = createCheck("Lainnya");
        KeluargaLainnya = createField(180, false);

        addCardFull(panel, 0, createFieldGroupCard(
            "Riwayat Penyakit Dahulu",
            createOptionLine(RiwayatHipertensi, RiwayatDiabetes, RiwayatJantungKoroner),
            createOptionLine(RiwayatAsma, RiwayatStroke, RiwayatAlergiObatCheck),
            createOptionLine(RiwayatLiver, RiwayatGinjal, RiwayatTBParu, RiwayatLainnyaCheck),
            createLabeledLine("Lain-lain", RiwayatDahuluLainnya),
            createInlinePanel(
                new JLabel("Pernah dirawat:"),
                PernahDirawatTidak,
                PernahDirawatYa,
                new JLabel("Kapan"),
                PernahDirawatKapan,
                new JLabel("Di mana"),
                PernahDirawatDimana
            )
        ));
        addCardFull(panel, 1, createMedicationTableCard());
        addCardFull(panel, 2, createFieldGroupCard(
            "Riwayat Penyakit Keluarga",
            createOptionLine(KeluargaHipertensi, KeluargaDiabetes, KeluargaJantung, KeluargaAsma, KeluargaLainnyaCheck),
            createLabeledLine("Lainnya", KeluargaLainnya)
        ));
        addCardFull(panel, 3, createFieldCard("Keterangan Riwayat Alergi Obat", RiwayatAlergiObat));

        return panel;
    }

    private JPanel buildTandaVitalPanel() {
        JPanel panel = createSectionPanel("IV. Tanda Vital");

        Kesadaran = createComboField(LEBAR_FIELD_SEDANG);
        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma"
        }));
        BeratBadan = createField(LEBAR_FIELD_KECIL, false);
        TinggiBadan = createField(LEBAR_FIELD_KECIL, false);
        TekananDarah = createField(LEBAR_FIELD_KECIL, false);
        Nadi = createField(LEBAR_FIELD_KECIL, false);
        Respirasi = createField(LEBAR_FIELD_KECIL, false);
        Suhu = createField(LEBAR_FIELD_KECIL, false);
        SpO2 = createField(LEBAR_FIELD_KECIL, false);

        addThree(panel, 0, "Kesadaran :", Kesadaran, "Berat Badan :", BeratBadan, "Tinggi Badan :", TinggiBadan);
        addFour(panel, 1, "TD :", TekananDarah, "Nadi :", Nadi, "RR :", Respirasi, "Suhu :", Suhu);
        addFull(panel, 2, "SpO2 :", SpO2);

        return panel;
    }

    private JPanel buildPemeriksaanFisikPanel() {
        JPanel panel = createSectionPanel("V. Pemeriksaan Fisik");

        PemeriksaanMata = createArea(4);
        SistemSaraf = createArea(4);
        PemeriksaanTHT = createArea(4);
        PemeriksaanThoraks = createArea(4);
        PemeriksaanAbdomen = createArea(5);
        PemeriksaanEkstremitas = createArea(4);
        StatusLokalisasi = createArea(4);

        addCardPair(panel, 0,
            createAreaCard("Mata", PemeriksaanMata, 110),
            createAreaCard("Sistem Saraf", SistemSaraf, 110)
        );
        addCardPair(panel, 1,
            createAreaCard("THT", PemeriksaanTHT, 110),
            createAreaCard("Thoraks / Cor / Pulmo", PemeriksaanThoraks, 110)
        );
        addCardPair(panel, 2,
            createAreaCard("Abdomen", PemeriksaanAbdomen, 125),
            createAreaCard("Ekstremitas", PemeriksaanEkstremitas, 125)
        );
        addCardFull(panel, 3, createAreaCard("Status Lokalisasi", StatusLokalisasi, 110));

        return panel;
    }

    private JPanel buildPenunjangPanel() {
        JPanel panel = createSectionPanel("VI. Hasil Pemeriksaan Penunjang");

        HasilLaboratorium = createArea(5);
        HasilEKG = createArea(5);
        HasilRadiologi = createArea(5);

        addCardTriple(panel, 0,
            createAreaCard("Laboratorium", HasilLaboratorium, 145),
            createAreaCard("EKG", HasilEKG, 145),
            createAreaCard("Radiologi", HasilRadiologi, 145)
        );

        return panel;
    }

    private JPanel buildDiagnosisPanel() {
        JPanel panel = createSectionPanel("VII. Diagnosis dan Rencana");

        DiagnosisBanding = createArea(5);
        RencanaKerjaTerapi = createArea(5);
        CatatanPenting = createArea(5);
        DiagnosisBanding1 = createField(560, false);
        DiagnosisBanding2 = createField(560, false);
        DiagnosisBanding3 = createField(560, false);
        DiagnosisBanding4 = createField(560, false);

        addCardFull(panel, 0, createDiagnosisBandingCard());
        addCardFull(panel, 1, createAreaCard("Rencana Kerja dan Terapi", RencanaKerjaTerapi, 130));
        addCardFull(panel, 2, createAreaCard("Catatan Penting", CatatanPenting, 120));

        return panel;
    }

    private JPanel buildPengkajianPanel() {
        JPanel panel = createSectionPanel("VIII. Pengkajian");

        DokterPengkaji = createField(LEBAR_FIELD_BESAR, false);
        DokterDPJP = createField(LEBAR_FIELD_BESAR, false);
        InfoTanggalJamPengkajian = createField(260, true);
        refreshInfoTanggalJamPengkajian();

        addCardPair(panel, 0,
            createFieldGroupCard(
                "Yang Melakukan Pengkajian",
                createLabeledLine("Nama", DokterPengkaji),
                createLabeledLine("Tanggal / Jam", InfoTanggalJamPengkajian)
            ),
            createFieldGroupCard(
                "Dokter DPJP",
                createLabeledLine("Nama", DokterDPJP),
                createInfoLabel("Area ini disiapkan untuk menyesuaikan blok tanda tangan di PDF.")
            )
        );
        return panel;
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WARNA_PANEL);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(WARNA_BORDER),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new java.awt.Font("Tahoma", 1, 11),
                WARNA_JUDUL
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));
        return panel;
    }

    private TextArea createArea(int rows) {
        TextArea area = new TextArea();
        area.setRows(rows);
        area.setColumns(20);
        area.setBackground(WARNA_PANEL);
        return area;
    }

    private ScrollPane wrap(TextArea area, int height) {
        ScrollPane pane = new ScrollPane();
        pane.setBorder(BorderFactory.createLineBorder(WARNA_BORDER));
        pane.setPreferredSize(new Dimension(200, height));
        pane.getViewport().setBackground(WARNA_PANEL);
        pane.setViewportView(area);
        return pane;
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        return c;
    }

    private Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setForeground(WARNA_JUDUL);
        label.setPreferredSize(new Dimension(LEBAR_LABEL, TINGGI_FIELD));
        return label;
    }

    private TextBox createField(int width, boolean readOnly) {
        TextBox field = new TextBox();
        field.setPreferredSize(new Dimension(width, TINGGI_FIELD));
        if (readOnly) {
            field.setEditable(false);
            field.setBackground(WARNA_READONLY);
        }
        return field;
    }

    private ComboBox createComboField(int width) {
        ComboBox combo = new ComboBox();
        combo.setPreferredSize(new Dimension(width, TINGGI_FIELD));
        return combo;
    }

    private Tanggal createDateField(int width) {
        Tanggal tanggal = new Tanggal();
        tanggal.setPreferredSize(new Dimension(width, TINGGI_FIELD));
        return tanggal;
    }

    private JCheckBox createCheck(String text) {
        JCheckBox check = new JCheckBox(text);
        check.setOpaque(false);
        check.setFont(new java.awt.Font("Tahoma", 0, 11));
        check.setForeground(new Color(70, 78, 70));
        return check;
    }

    private JPanel createInlinePanel(JComponent... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        panel.setOpaque(false);
        for (JComponent component : components) {
            panel.add(component);
        }
        return panel;
    }

    private JPanel createOptionLine(JComponent... components) {
        return createInlinePanel(components);
    }

    private JPanel createLabeledLine(String labelText, JComponent field) {
        JLabel label = new JLabel(labelText + " :");
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setForeground(WARNA_JUDUL);
        return createInlinePanel(label, field);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new java.awt.Font("Tahoma", 0, 11));
        label.setForeground(new Color(102, 110, 102));
        return label;
    }

    private JPanel createFieldGroupCard(String title, JComponent... contents) {
        JPanel card = createCardShell(title);
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new javax.swing.BoxLayout(body, javax.swing.BoxLayout.Y_AXIS));
        for (int i = 0; i < contents.length; i++) {
            body.add(contents[i]);
            if (i < contents.length - 1) {
                body.add(Box.createVerticalStrut(6));
            }
        }
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel createMedicationTableCard() {
        JPanel card = createCardShell("Riwayat Pengobatan (obat yang sedang dikonsumsi)");
        JPanel body = new JPanel(new GridBagLayout());
        body.setOpaque(false);

        addMedicationHeader(body, 0, "No");
        addMedicationHeader(body, 1, "Nama Obat");
        addMedicationHeader(body, 2, "Dosis");
        addMedicationHeader(body, 3, "Waktu Penggunaan Terakhir");

        for (int i = 0; i < 4; i++) {
            ObatNama[i] = createField(220, false);
            ObatDosis[i] = createField(160, false);
            ObatWaktu[i] = createField(220, false);

            GridBagConstraints c = gbc(0, i + 1);
            c.weightx = 0;
            JLabel nomor = new JLabel(String.valueOf(i + 1) + ".");
            nomor.setForeground(WARNA_JUDUL);
            body.add(nomor, c);

            c = gbc(1, i + 1);
            c.weightx = 0.4;
            body.add(ObatNama[i], c);

            c = gbc(2, i + 1);
            c.weightx = 0.25;
            body.add(ObatDosis[i], c);

            c = gbc(3, i + 1);
            c.weightx = 0.35;
            body.add(ObatWaktu[i], c);
        }

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private void addMedicationHeader(JPanel panel, int x, String text) {
        GridBagConstraints c = gbc(x, 0);
        c.weightx = x == 0 ? 0 : 0.33;
        JLabel label = new JLabel(text);
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setForeground(WARNA_JUDUL);
        panel.add(label, c);
    }

    private JPanel createDiagnosisBandingCard() {
        JPanel card = createCardShell("Diagnosa / Diagnosis Banding");
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new javax.swing.BoxLayout(body, javax.swing.BoxLayout.Y_AXIS));
        body.add(createNumberedDiagnosisLine("1.", DiagnosisBanding1));
        body.add(Box.createVerticalStrut(6));
        body.add(createNumberedDiagnosisLine("2.", DiagnosisBanding2));
        body.add(Box.createVerticalStrut(6));
        body.add(createNumberedDiagnosisLine("3.", DiagnosisBanding3));
        body.add(Box.createVerticalStrut(6));
        body.add(createNumberedDiagnosisLine("4.", DiagnosisBanding4));
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel createNumberedDiagnosisLine(String number, TextBox field) {
        JLabel label = new JLabel(number);
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setForeground(WARNA_JUDUL);
        return createInlinePanel(label, field);
    }

    private void refreshInfoTanggalJamPengkajian() {
        if (InfoTanggalJamPengkajian == null) {
            return;
        }
        String tanggal = TglAsesmen == null || TglAsesmen.getDate() == null ? tanggalTabel.format(new Date()) : tanggalTabel.format(TglAsesmen.getDate());
        String jam = JamAsesmen == null || JamAsesmen.getText().trim().isEmpty() ? jamSekarang.format(new Date()) : JamAsesmen.getText().trim();
        InfoTanggalJamPengkajian.setText(tanggal + " " + jam);
    }

    private JPanel createAreaCard(String title, TextArea area, int height) {
        JPanel card = createCardShell(title);
        card.add(wrap(area, height), BorderLayout.CENTER);
        return card;
    }

    private JPanel createFieldCard(String title, JComponent field) {
        JPanel card = createCardShell(title);
        card.add(field, BorderLayout.CENTER);
        return card;
    }

    private JPanel createCardShell(String title) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(WARNA_PANEL_AKSESEN);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNA_BORDER),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel label = new JLabel(title);
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setForeground(WARNA_JUDUL);
        card.add(label, BorderLayout.NORTH);

        return card;
    }

    private void addTwo(JPanel panel, int row, String label1, JComponent field1, String label2, JComponent field2) {
        GridBagConstraints c = gbc(0, row);
        c.weightx = 0;
        panel.add(createLabel(label1), c);

        c = gbc(1, row);
        c.weightx = 0.5;
        panel.add(field1, c);

        c = gbc(2, row);
        c.weightx = 0;
        panel.add(createLabel(label2), c);

        c = gbc(3, row);
        c.weightx = 0.5;
        panel.add(field2, c);
    }

    private void addThree(JPanel panel, int row, String label1, JComponent field1, String label2, JComponent field2, String label3, JComponent field3) {
        GridBagConstraints c = gbc(0, row);
        c.weightx = 0;
        panel.add(createLabel(label1), c);

        c = gbc(1, row);
        c.weightx = 0.34;
        panel.add(field1, c);

        c = gbc(2, row);
        c.weightx = 0;
        panel.add(createLabel(label2), c);

        c = gbc(3, row);
        c.weightx = 0.33;
        panel.add(field2, c);

        c = gbc(4, row);
        c.weightx = 0;
        panel.add(createLabel(label3), c);

        c = gbc(5, row);
        c.weightx = 0.33;
        panel.add(field3, c);
    }

    private void addFour(JPanel panel, int row, String label1, JComponent field1, String label2, JComponent field2, String label3, JComponent field3, String label4, JComponent field4) {
        GridBagConstraints c = gbc(0, row);
        c.weightx = 0;
        panel.add(createLabel(label1), c);

        c = gbc(1, row);
        c.weightx = 0.25;
        panel.add(field1, c);

        c = gbc(2, row);
        c.weightx = 0;
        panel.add(createLabel(label2), c);

        c = gbc(3, row);
        c.weightx = 0.25;
        panel.add(field2, c);

        c = gbc(4, row);
        c.weightx = 0;
        panel.add(createLabel(label3), c);

        c = gbc(5, row);
        c.weightx = 0.25;
        panel.add(field3, c);

        c = gbc(6, row);
        c.weightx = 0;
        panel.add(createLabel(label4), c);

        c = gbc(7, row);
        c.weightx = 0.25;
        panel.add(field4, c);
    }

    private void addFull(JPanel panel, int row, String label, JComponent field) {
        GridBagConstraints c = gbc(0, row);
        c.weightx = 0;
        panel.add(createLabel(label), c);

        c = gbc(1, row);
        c.gridwidth = 5;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(field, c);
    }

    private void addCardPair(JPanel panel, int row, JComponent left, JComponent right) {
        GridBagConstraints c = gbc(0, row);
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.BOTH;
        panel.add(left, c);

        c = gbc(3, row);
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.BOTH;
        panel.add(right, c);
    }

    private void addCardTriple(JPanel panel, int row, JComponent first, JComponent second, JComponent third) {
        GridBagConstraints c = gbc(0, row);
        c.gridwidth = 2;
        c.weightx = 0.33;
        c.fill = GridBagConstraints.BOTH;
        panel.add(first, c);

        c = gbc(2, row);
        c.gridwidth = 2;
        c.weightx = 0.33;
        c.fill = GridBagConstraints.BOTH;
        panel.add(second, c);

        c = gbc(4, row);
        c.gridwidth = 2;
        c.weightx = 0.34;
        c.fill = GridBagConstraints.BOTH;
        panel.add(third, c);
    }

    private void addCardFull(JPanel panel, int row, JComponent card) {
        GridBagConstraints c = gbc(0, row);
        c.gridwidth = 6;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(card, c);
    }

    private void initTable() {
        tbData.setModel(tabMode);
        tbData.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        int[] widths = new int[]{110, 90, 220, 140, 180, 250, 220};
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
        updateCount();
    }

    private void updateCount() {
        LCount.setText(String.valueOf(tabMode.getRowCount()));
    }

    public void emptTeks() {
        Date sekarang = new Date();

        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TglLahir.setText("");
        Jk.setText("");
        TglAsesmen.setDate(sekarang);
        DTPCari1.setDate(sekarang);
        DTPCari2.setDate(sekarang);
        JamAsesmen.setText(jamSekarang.format(sekarang));
        KdDokter.setText("");
        NmDokter.setText("");
        SumberRujukan.setText("");
        DiagnosaRujukan.setText("");
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        CaraDatang.setText("");
        KeluhanUtama.setText("");
        RiwayatPenyakitSekarang.setText("");
        RiwayatPenyakitDahulu.setText("");
        RiwayatPengobatan.setText("");
        RiwayatPenyakitKeluarga.setText("");
        RiwayatAlergiObat.setText("");
        Kesadaran.setSelectedIndex(0);
        BeratBadan.setText("");
        TinggiBadan.setText("");
        TekananDarah.setText("");
        Nadi.setText("");
        Respirasi.setText("");
        Suhu.setText("");
        SpO2.setText("");
        PemeriksaanMata.setText("");
        SistemSaraf.setText("");
        PemeriksaanTHT.setText("");
        PemeriksaanThoraks.setText("");
        PemeriksaanAbdomen.setText("");
        PemeriksaanEkstremitas.setText("");
        StatusLokalisasi.setText("");
        HasilLaboratorium.setText("");
        HasilEKG.setText("");
        HasilRadiologi.setText("");
        DiagnosisBanding.setText("");
        RencanaKerjaTerapi.setText("");
        CatatanPenting.setText("");
        DokterPengkaji.setText("");
        DokterDPJP.setText("");
        resetCompositeFields();
        refreshInfoTanggalJamPengkajian();
        if (akses.getjml2() >= 1) {
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            DokterPengkaji.setText(NmDokter.getText());
        }
        TNoRw.requestFocus();
    }

    public void setNoRm(String noRawat, Date tanggalCari) {
        emptTeks();
        TNoRw.setText(noRawat);
        TCari.setText(noRawat);
        DTPCari1.setDate(tanggalCari);
        DTPCari2.setDate(tanggalCari);
        loadIdentitasPasien();
        if (dataSudahAda(noRawat)) {
            loadRecord(noRawat);
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(true);
        BtnBatal.setEnabled(true);
        BtnHapus.setEnabled(true);
        BtnEdit.setEnabled(true);
        BtnPrint.setEnabled(true);
        BtnKeluar.setEnabled(true);
        if (akses.getjml2() >= 1) {
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            DokterPengkaji.setText(NmDokter.getText());
        }
    }

    private void BtnSimpanActionPerformed(ActionEvent evt) {
        if (validasiSimpan()) {
            if (dataSudahAda(TNoRw.getText().trim())) {
                gantiData();
            } else {
                simpanData();
            }
        }
    }

    private void BtnBatalActionPerformed(ActionEvent evt) {
        emptTeks();
    }

    private void BtnHapusActionPerformed(ActionEvent evt) {
        hapusData();
    }

    private void BtnEditActionPerformed(ActionEvent evt) {
        if (validasiSimpan()) {
            gantiData();
        }
    }

    private void BtnPrintActionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(
            this,
            "Template cetak untuk form baru ini belum dihubungkan.\nSilakan lanjutkan ke report/Jasper saat siap integrasi.",
            "Info",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void BtnKeluarActionPerformed(ActionEvent evt) {
        dispose();
    }

    private void BtnCariActionPerformed(ActionEvent evt) {
        tampil();
    }

    private void BtnAllActionPerformed(ActionEvent evt) {
        TCari.setText("");
        tampil();
    }

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
        tampil();
    }

    private void resetCompositeFields() {
        setCheck(RujukanORS, false);
        setCheck(RujukanPuskesmas, false);
        setCheck(RujukanPraktek, false);
        setCheck(RujukanLainnya, false);
        setCheck(CaraDatangSendiri, false);
        setCheck(CaraDatangDiantar, false);
        setCheck(RiwayatHipertensi, false);
        setCheck(RiwayatDiabetes, false);
        setCheck(RiwayatJantungKoroner, false);
        setCheck(RiwayatAsma, false);
        setCheck(RiwayatStroke, false);
        setCheck(RiwayatAlergiObatCheck, false);
        setCheck(RiwayatLiver, false);
        setCheck(RiwayatGinjal, false);
        setCheck(RiwayatTBParu, false);
        setCheck(RiwayatLainnyaCheck, false);
        setCheck(PernahDirawatTidak, false);
        setCheck(PernahDirawatYa, false);
        setCheck(KeluargaHipertensi, false);
        setCheck(KeluargaDiabetes, false);
        setCheck(KeluargaJantung, false);
        setCheck(KeluargaAsma, false);
        setCheck(KeluargaLainnyaCheck, false);
        setTextIfReady(RiwayatDahuluLainnya, "");
        setTextIfReady(PernahDirawatKapan, "");
        setTextIfReady(PernahDirawatDimana, "");
        setTextIfReady(KeluargaLainnya, "");
        setTextIfReady(DiagnosisBanding1, "");
        setTextIfReady(DiagnosisBanding2, "");
        setTextIfReady(DiagnosisBanding3, "");
        setTextIfReady(DiagnosisBanding4, "");
        for (int i = 0; i < ObatNama.length; i++) {
            setTextIfReady(ObatNama[i], "");
            setTextIfReady(ObatDosis[i], "");
            setTextIfReady(ObatWaktu[i], "");
        }
    }

    private void setCheck(JCheckBox checkBox, boolean selected) {
        if (checkBox != null) {
            checkBox.setSelected(selected);
        }
    }

    private void setTextIfReady(TextBox field, String value) {
        if (field != null) {
            field.setText(value);
        }
    }

    private void sinkCompositeFields() {
        SumberRujukan.setText(serializeRujukan());
        CaraDatang.setText(serializeCaraDatang());
        RiwayatPenyakitDahulu.setText(serializeRiwayatPenyakitDahulu());
        RiwayatPengobatan.setText(serializeRiwayatPengobatan());
        RiwayatPenyakitKeluarga.setText(serializeRiwayatPenyakitKeluarga());
        DiagnosisBanding.setText(serializeDiagnosisBanding());
    }

    private void applyCompositeFields() {
        applyRujukan(SumberRujukan.getText());
        applyCaraDatang(CaraDatang.getText());
        applyRiwayatPenyakitDahulu(RiwayatPenyakitDahulu.getText());
        applyRiwayatPengobatan(RiwayatPengobatan.getText());
        applyRiwayatPenyakitKeluarga(RiwayatPenyakitKeluarga.getText());
        applyDiagnosisBanding(DiagnosisBanding.getText());
    }

    private String serializeRujukan() {
        List<String> pilihan = new ArrayList<String>();
        addIfSelected(pilihan, RujukanORS, "ORS");
        addIfSelected(pilihan, RujukanPuskesmas, "Puskesmas");
        addIfSelected(pilihan, RujukanPraktek, "Praktek");
        addIfSelected(pilihan, RujukanLainnya, "Lainnya");
        String detail = SumberRujukan.getText().trim();
        if (pilihan.isEmpty() && detail.isEmpty()) {
            return "";
        }
        return "Pilihan=" + joinList(pilihan) + ";Detail=" + detail;
    }

    private void applyRujukan(String value) {
        setCheck(RujukanORS, false);
        setCheck(RujukanPuskesmas, false);
        setCheck(RujukanPraktek, false);
        setCheck(RujukanLainnya, false);
        SumberRujukan.setText("");
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        if (!value.contains("Pilihan=")) {
            SumberRujukan.setText(value);
            return;
        }
        String[] parts = value.split(";Detail=", 2);
        String pilihan = parts[0].replace("Pilihan=", "").trim();
        String detail = parts.length > 1 ? parts[1] : "";
        applyLabelsToChecks(pilihan, RujukanORS, RujukanPuskesmas, RujukanPraktek, RujukanLainnya);
        SumberRujukan.setText(detail);
    }

    private String serializeCaraDatang() {
        List<String> pilihan = new ArrayList<String>();
        addIfSelected(pilihan, CaraDatangSendiri, "Datang Sendiri");
        addIfSelected(pilihan, CaraDatangDiantar, "Diantar");
        return joinList(pilihan);
    }

    private void applyCaraDatang(String value) {
        setCheck(CaraDatangSendiri, false);
        setCheck(CaraDatangDiantar, false);
        applyLabelsToChecks(value, CaraDatangSendiri, CaraDatangDiantar);
    }

    private String serializeRiwayatPenyakitDahulu() {
        List<String> penyakit = new ArrayList<String>();
        addIfSelected(penyakit, RiwayatHipertensi, "Hipertensi");
        addIfSelected(penyakit, RiwayatDiabetes, "Diabetes Melitus");
        addIfSelected(penyakit, RiwayatJantungKoroner, "Penyakit Jantung Koroner");
        addIfSelected(penyakit, RiwayatAsma, "Asma");
        addIfSelected(penyakit, RiwayatStroke, "Stroke");
        addIfSelected(penyakit, RiwayatAlergiObatCheck, "Riwayat Alergi Obat");
        addIfSelected(penyakit, RiwayatLiver, "Liver");
        addIfSelected(penyakit, RiwayatGinjal, "Ginjal");
        addIfSelected(penyakit, RiwayatTBParu, "Tb Paru");
        addIfSelected(penyakit, RiwayatLainnyaCheck, "Lain-lain");
        return "Penyakit=" + joinList(penyakit) +
            ";Lainnya=" + RiwayatDahuluLainnya.getText().trim() +
            ";PernahDirawat=" + serializePernahDirawat() +
            ";Kapan=" + PernahDirawatKapan.getText().trim() +
            ";DiMana=" + PernahDirawatDimana.getText().trim();
    }

    private String serializePernahDirawat() {
        List<String> pilihan = new ArrayList<String>();
        addIfSelected(pilihan, PernahDirawatTidak, "Tidak");
        addIfSelected(pilihan, PernahDirawatYa, "Ya");
        return joinList(pilihan);
    }

    private void applyRiwayatPenyakitDahulu(String value) {
        resetRiwayatPenyakitDahuluChecks();
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        if (!value.contains("Penyakit=")) {
            RiwayatDahuluLainnya.setText(value);
            return;
        }
        String[] segments = value.split(";");
        for (String segment : segments) {
            String[] kv = segment.split("=", 2);
            if (kv.length < 2) {
                continue;
            }
            String key = kv[0].trim();
            String val = kv[1].trim();
            if (key.equals("Penyakit")) {
                applyLabelsToChecks(val,
                    RiwayatHipertensi, RiwayatDiabetes, RiwayatJantungKoroner, RiwayatAsma,
                    RiwayatStroke, RiwayatAlergiObatCheck, RiwayatLiver, RiwayatGinjal,
                    RiwayatTBParu, RiwayatLainnyaCheck
                );
            } else if (key.equals("Lainnya")) {
                RiwayatDahuluLainnya.setText(val);
            } else if (key.equals("PernahDirawat")) {
                applyLabelsToChecks(val, PernahDirawatTidak, PernahDirawatYa);
            } else if (key.equals("Kapan")) {
                PernahDirawatKapan.setText(val);
            } else if (key.equals("DiMana")) {
                PernahDirawatDimana.setText(val);
            }
        }
    }

    private void resetRiwayatPenyakitDahuluChecks() {
        setCheck(RiwayatHipertensi, false);
        setCheck(RiwayatDiabetes, false);
        setCheck(RiwayatJantungKoroner, false);
        setCheck(RiwayatAsma, false);
        setCheck(RiwayatStroke, false);
        setCheck(RiwayatAlergiObatCheck, false);
        setCheck(RiwayatLiver, false);
        setCheck(RiwayatGinjal, false);
        setCheck(RiwayatTBParu, false);
        setCheck(RiwayatLainnyaCheck, false);
        setCheck(PernahDirawatTidak, false);
        setCheck(PernahDirawatYa, false);
        RiwayatDahuluLainnya.setText("");
        PernahDirawatKapan.setText("");
        PernahDirawatDimana.setText("");
    }

    private String serializeRiwayatPengobatan() {
        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < ObatNama.length; i++) {
            String nama = ObatNama[i] == null ? "" : ObatNama[i].getText().trim();
            String dosis = ObatDosis[i] == null ? "" : ObatDosis[i].getText().trim();
            String waktu = ObatWaktu[i] == null ? "" : ObatWaktu[i].getText().trim();
            if (!nama.isEmpty() || !dosis.isEmpty() || !waktu.isEmpty()) {
                lines.add(nama + "|" + dosis + "|" + waktu);
            }
        }
        return joinWithNewLine(lines);
    }

    private void applyRiwayatPengobatan(String value) {
        for (int i = 0; i < ObatNama.length; i++) {
            setTextIfReady(ObatNama[i], "");
            setTextIfReady(ObatDosis[i], "");
            setTextIfReady(ObatWaktu[i], "");
        }
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        String[] lines = value.split("\\n");
        for (int i = 0; i < lines.length && i < ObatNama.length; i++) {
            String[] parts = lines[i].split("\\|", -1);
            setTextIfReady(ObatNama[i], parts.length > 0 ? parts[0] : "");
            setTextIfReady(ObatDosis[i], parts.length > 1 ? parts[1] : "");
            setTextIfReady(ObatWaktu[i], parts.length > 2 ? parts[2] : "");
        }
    }

    private String serializeRiwayatPenyakitKeluarga() {
        List<String> penyakit = new ArrayList<String>();
        addIfSelected(penyakit, KeluargaHipertensi, "Hipertensi");
        addIfSelected(penyakit, KeluargaDiabetes, "Diabetes Melitus");
        addIfSelected(penyakit, KeluargaJantung, "Jantung");
        addIfSelected(penyakit, KeluargaAsma, "Asma");
        addIfSelected(penyakit, KeluargaLainnyaCheck, "Lainnya");
        return "Penyakit=" + joinList(penyakit) + ";Lainnya=" + KeluargaLainnya.getText().trim();
    }

    private void applyRiwayatPenyakitKeluarga(String value) {
        setCheck(KeluargaHipertensi, false);
        setCheck(KeluargaDiabetes, false);
        setCheck(KeluargaJantung, false);
        setCheck(KeluargaAsma, false);
        setCheck(KeluargaLainnyaCheck, false);
        KeluargaLainnya.setText("");
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        if (!value.contains("Penyakit=")) {
            KeluargaLainnya.setText(value);
            return;
        }
        String[] segments = value.split(";");
        for (String segment : segments) {
            String[] kv = segment.split("=", 2);
            if (kv.length < 2) {
                continue;
            }
            String key = kv[0].trim();
            String val = kv[1].trim();
            if (key.equals("Penyakit")) {
                applyLabelsToChecks(val, KeluargaHipertensi, KeluargaDiabetes, KeluargaJantung, KeluargaAsma, KeluargaLainnyaCheck);
            } else if (key.equals("Lainnya")) {
                KeluargaLainnya.setText(val);
            }
        }
    }

    private String serializeDiagnosisBanding() {
        List<String> lines = new ArrayList<String>();
        addIfFilled(lines, DiagnosisBanding1);
        addIfFilled(lines, DiagnosisBanding2);
        addIfFilled(lines, DiagnosisBanding3);
        addIfFilled(lines, DiagnosisBanding4);
        return joinWithNewLine(lines);
    }

    private void applyDiagnosisBanding(String value) {
        setTextIfReady(DiagnosisBanding1, "");
        setTextIfReady(DiagnosisBanding2, "");
        setTextIfReady(DiagnosisBanding3, "");
        setTextIfReady(DiagnosisBanding4, "");
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        String[] lines = value.split("\\n");
        if (lines.length > 0) {
            DiagnosisBanding1.setText(lines[0]);
        }
        if (lines.length > 1) {
            DiagnosisBanding2.setText(lines[1]);
        }
        if (lines.length > 2) {
            DiagnosisBanding3.setText(lines[2]);
        }
        if (lines.length > 3) {
            DiagnosisBanding4.setText(lines[3]);
        }
    }

    private void addIfSelected(List<String> values, JCheckBox checkBox, String label) {
        if (checkBox != null && checkBox.isSelected()) {
            values.add(label);
        }
    }

    private void addIfFilled(List<String> values, TextBox field) {
        if (field != null && !field.getText().trim().isEmpty()) {
            values.add(field.getText().trim());
        }
    }

    private String joinList(List<String> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(values.get(i));
        }
        return builder.toString();
    }

    private String joinWithNewLine(List<String> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append("\n");
            }
            builder.append(values.get(i));
        }
        return builder.toString();
    }

    private void applyLabelsToChecks(String csv, JCheckBox... checkBoxes) {
        if (csv == null || csv.trim().isEmpty()) {
            return;
        }
        String[] values = csv.split(",");
        for (String value : values) {
            String cleaned = value.trim();
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox != null && checkBox.getText().equalsIgnoreCase(cleaned)) {
                    checkBox.setSelected(true);
                }
            }
        }
    }

    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    private boolean validasiSimpan() {
        if (TNoRw.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No.Rawat masih kosong.");
            TNoRw.requestFocus();
            return false;
        }
        if (TPasien.getText().trim().isEmpty()) {
            loadIdentitasPasien();
        }
        if (TPasien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama pasien masih kosong.");
            TPasien.requestFocus();
            return false;
        }
        if (KeluhanUtama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keluhan utama masih kosong.");
            KeluhanUtama.requestFocus();
            return false;
        }
        return true;
    }

    private void ensureTableExists() {
        try {
            ps = koneksi.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + NAMA_TABEL + " (" +
                "no_rawat VARCHAR(17) NOT NULL," +
                "tanggal DATETIME," +
                "kd_dokter VARCHAR(20)," +
                "sumber_rujukan VARCHAR(120)," +
                "diagnosa_rujukan TEXT," +
                "anamnesis VARCHAR(30)," +
                "hubungan VARCHAR(50)," +
                "cara_datang VARCHAR(60)," +
                "keluhan_utama TEXT," +
                "riwayat_penyakit_sekarang TEXT," +
                "riwayat_penyakit_dahulu TEXT," +
                "riwayat_pengobatan TEXT," +
                "riwayat_penyakit_keluarga TEXT," +
                "riwayat_alergi_obat TEXT," +
                "kesadaran VARCHAR(30)," +
                "berat_badan VARCHAR(15)," +
                "tinggi_badan VARCHAR(15)," +
                "tekanan_darah VARCHAR(20)," +
                "nadi VARCHAR(15)," +
                "respirasi VARCHAR(15)," +
                "suhu VARCHAR(15)," +
                "spo2 VARCHAR(15)," +
                "pemeriksaan_mata TEXT," +
                "sistem_saraf TEXT," +
                "pemeriksaan_tht TEXT," +
                "pemeriksaan_thoraks TEXT," +
                "pemeriksaan_abdomen TEXT," +
                "pemeriksaan_ekstremitas TEXT," +
                "status_lokalisasi TEXT," +
                "hasil_laboratorium TEXT," +
                "hasil_ekg TEXT," +
                "hasil_radiologi TEXT," +
                "diagnosis_banding TEXT," +
                "rencana_kerja_terapi TEXT," +
                "catatan_penting TEXT," +
                "dokter_pengkaji VARCHAR(120)," +
                "dokter_dpjp VARCHAR(120)," +
                "PRIMARY KEY (no_rawat)," +
                "KEY kd_dokter (kd_dokter)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1"
            );
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyiapkan tabel " + NAMA_TABEL + " : " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    private void tampil() {
        tabMode.setRowCount(0);
        try {
            String sql =
                "SELECT rp.no_rawat, p.no_rkm_medis, p.nm_pasien, a.tanggal, " +
                "COALESCE(d.nm_dokter, a.dokter_pengkaji, a.dokter_dpjp, '') AS dokter_pemeriksa, " +
                "a.keluhan_utama, a.diagnosis_banding " +
                "FROM " + NAMA_TABEL + " a " +
                "INNER JOIN reg_periksa rp ON rp.no_rawat = a.no_rawat " +
                "INNER JOIN pasien p ON p.no_rkm_medis = rp.no_rkm_medis " +
                "LEFT JOIN dokter d ON d.kd_dokter = a.kd_dokter " +
                "WHERE a.tanggal BETWEEN ? AND ? ";
            if (!TCari.getText().trim().isEmpty()) {
                sql += "AND (rp.no_rawat LIKE ? OR p.no_rkm_medis LIKE ? OR p.nm_pasien LIKE ? " +
                       "OR COALESCE(d.nm_dokter, a.dokter_pengkaji, a.dokter_dpjp, '') LIKE ?) ";
            }
            sql += "ORDER BY a.tanggal DESC";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, tanggalTabel.format(DTPCari1.getDate() == null ? new Date() : DTPCari1.getDate()) + " 00:00:00");
            ps.setString(2, tanggalTabel.format(DTPCari2.getDate() == null ? new Date() : DTPCari2.getDate()) + " 23:59:59");
            if (!TCari.getText().trim().isEmpty()) {
                String keyword = "%" + TCari.getText().trim() + "%";
                ps.setString(3, keyword);
                ps.setString(4, keyword);
                ps.setString(5, keyword);
                ps.setString(6, keyword);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("no_rawat"),
                    rs.getString("no_rkm_medis"),
                    rs.getString("nm_pasien"),
                    rs.getString("tanggal"),
                    rs.getString("dokter_pemeriksa"),
                    truncate(rs.getString("keluhan_utama"), 80),
                    truncate(rs.getString("diagnosis_banding"), 80)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data asesmen: " + e.getMessage());
        } finally {
            closeResultSet();
            closeStatement();
            updateCount();
        }
    }

    private void loadIdentitasPasien() {
        if (TNoRw.getText().trim().isEmpty()) {
            return;
        }
        try {
            ps = koneksi.prepareStatement(
                "SELECT rp.no_rkm_medis, p.nm_pasien, p.tgl_lahir, IF(p.jk='L','Laki-Laki','Perempuan') AS jk " +
                "FROM reg_periksa rp INNER JOIN pasien p ON p.no_rkm_medis = rp.no_rkm_medis " +
                "WHERE rp.no_rawat = ?"
            );
            ps.setString(1, TNoRw.getText().trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                TNoRM.setText(rs.getString("no_rkm_medis"));
                TPasien.setText(rs.getString("nm_pasien"));
                TglLahir.setText(rs.getString("tgl_lahir"));
                Jk.setText(rs.getString("jk"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data pasien: " + e.getMessage());
        } finally {
            closeResultSet();
            closeStatement();
        }
    }

    private void loadRecord(String noRawat) {
        try {
            loadIdentitasPasien();
            ps = koneksi.prepareStatement("SELECT * FROM " + NAMA_TABEL + " WHERE no_rawat = ?");
            ps.setString(1, noRawat);
            rs = ps.executeQuery();
            if (rs.next()) {
                Date tanggal = rs.getTimestamp("tanggal");
                if (tanggal != null) {
                    TglAsesmen.setDate(tanggal);
                    JamAsesmen.setText(jamSekarang.format(tanggal));
                }
                refreshInfoTanggalJamPengkajian();
                KdDokter.setText(rs.getString("kd_dokter"));
                if (KdDokter.getText() != null && !KdDokter.getText().trim().isEmpty()) {
                    NmDokter.setText(dokter.tampil3(KdDokter.getText().trim()));
                } else {
                    NmDokter.setText(rs.getString("dokter_pengkaji"));
                }
                SumberRujukan.setText(rs.getString("sumber_rujukan"));
                DiagnosaRujukan.setText(rs.getString("diagnosa_rujukan"));
                Anamnesis.setSelectedItem(rs.getString("anamnesis"));
                Hubungan.setText(rs.getString("hubungan"));
                CaraDatang.setText(rs.getString("cara_datang"));
                KeluhanUtama.setText(rs.getString("keluhan_utama"));
                RiwayatPenyakitSekarang.setText(rs.getString("riwayat_penyakit_sekarang"));
                RiwayatPenyakitDahulu.setText(rs.getString("riwayat_penyakit_dahulu"));
                RiwayatPengobatan.setText(rs.getString("riwayat_pengobatan"));
                RiwayatPenyakitKeluarga.setText(rs.getString("riwayat_penyakit_keluarga"));
                RiwayatAlergiObat.setText(rs.getString("riwayat_alergi_obat"));
                Kesadaran.setSelectedItem(rs.getString("kesadaran"));
                BeratBadan.setText(rs.getString("berat_badan"));
                TinggiBadan.setText(rs.getString("tinggi_badan"));
                TekananDarah.setText(rs.getString("tekanan_darah"));
                Nadi.setText(rs.getString("nadi"));
                Respirasi.setText(rs.getString("respirasi"));
                Suhu.setText(rs.getString("suhu"));
                SpO2.setText(rs.getString("spo2"));
                PemeriksaanMata.setText(rs.getString("pemeriksaan_mata"));
                SistemSaraf.setText(rs.getString("sistem_saraf"));
                PemeriksaanTHT.setText(rs.getString("pemeriksaan_tht"));
                PemeriksaanThoraks.setText(rs.getString("pemeriksaan_thoraks"));
                PemeriksaanAbdomen.setText(rs.getString("pemeriksaan_abdomen"));
                PemeriksaanEkstremitas.setText(rs.getString("pemeriksaan_ekstremitas"));
                StatusLokalisasi.setText(rs.getString("status_lokalisasi"));
                HasilLaboratorium.setText(rs.getString("hasil_laboratorium"));
                HasilEKG.setText(rs.getString("hasil_ekg"));
                HasilRadiologi.setText(rs.getString("hasil_radiologi"));
                DiagnosisBanding.setText(rs.getString("diagnosis_banding"));
                RencanaKerjaTerapi.setText(rs.getString("rencana_kerja_terapi"));
                CatatanPenting.setText(rs.getString("catatan_penting"));
                DokterPengkaji.setText(rs.getString("dokter_pengkaji"));
                DokterDPJP.setText(rs.getString("dokter_dpjp"));
                applyCompositeFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data asesmen: " + e.getMessage());
        } finally {
            closeResultSet();
            closeStatement();
        }
    }

    private boolean dataSudahAda(String noRawat) {
        boolean ada = false;
        try {
            ps = koneksi.prepareStatement("SELECT no_rawat FROM " + NAMA_TABEL + " WHERE no_rawat = ?");
            ps.setString(1, noRawat);
            rs = ps.executeQuery();
            ada = rs.next();
        } catch (Exception e) {
            ada = false;
        } finally {
            closeResultSet();
            closeStatement();
        }
        return ada;
    }

    private void simpanData() {
        sinkCompositeFields();
        normalisasiDokter();
        try {
            ps = koneksi.prepareStatement(
                "INSERT INTO " + NAMA_TABEL + " (" +
                "no_rawat, tanggal, kd_dokter, sumber_rujukan, diagnosa_rujukan, anamnesis, hubungan, cara_datang, " +
                "keluhan_utama, riwayat_penyakit_sekarang, riwayat_penyakit_dahulu, riwayat_pengobatan, riwayat_penyakit_keluarga, riwayat_alergi_obat, " +
                "kesadaran, berat_badan, tinggi_badan, tekanan_darah, nadi, respirasi, suhu, spo2, " +
                "pemeriksaan_mata, sistem_saraf, pemeriksaan_tht, pemeriksaan_thoraks, pemeriksaan_abdomen, pemeriksaan_ekstremitas, status_lokalisasi, " +
                "hasil_laboratorium, hasil_ekg, hasil_radiologi, diagnosis_banding, rencana_kerja_terapi, catatan_penting, dokter_pengkaji, dokter_dpjp" +
                ") VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ")"
            );
            bindFormToStatement(ps);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Asesmen awal rawat inap medis berhasil disimpan.");
            tampil();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan asesmen: " + e.getMessage());
        } finally {
            applyCompositeFields();
            closeStatement();
        }
    }

    private void gantiData() {
        sinkCompositeFields();
        normalisasiDokter();
        if (!dataSudahAda(TNoRw.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Data belum ada. Gunakan tombol Simpan.");
            return;
        }
        try {
            ps = koneksi.prepareStatement(
                "UPDATE " + NAMA_TABEL + " SET " +
                "tanggal=?, kd_dokter=?, sumber_rujukan=?, diagnosa_rujukan=?, anamnesis=?, hubungan=?, cara_datang=?, " +
                "keluhan_utama=?, riwayat_penyakit_sekarang=?, riwayat_penyakit_dahulu=?, riwayat_pengobatan=?, riwayat_penyakit_keluarga=?, riwayat_alergi_obat=?, " +
                "kesadaran=?, berat_badan=?, tinggi_badan=?, tekanan_darah=?, nadi=?, respirasi=?, suhu=?, spo2=?, " +
                "pemeriksaan_mata=?, sistem_saraf=?, pemeriksaan_tht=?, pemeriksaan_thoraks=?, pemeriksaan_abdomen=?, pemeriksaan_ekstremitas=?, status_lokalisasi=?, " +
                "hasil_laboratorium=?, hasil_ekg=?, hasil_radiologi=?, diagnosis_banding=?, rencana_kerja_terapi=?, catatan_penting=?, dokter_pengkaji=?, dokter_dpjp=? " +
                "WHERE no_rawat=?"
            );
            bindFormToStatementForUpdate(ps);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Asesmen awal rawat inap medis berhasil diperbarui.");
            tampil();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui asesmen: " + e.getMessage());
        } finally {
            applyCompositeFields();
            closeStatement();
        }
    }

    private void hapusData() {
        String noRawat = TNoRw.getText().trim();
        if (noRawat.isEmpty() && tbData.getSelectedRow() > -1) {
            noRawat = tbData.getValueAt(tbData.convertRowIndexToModel(tbData.getSelectedRow()), 0).toString();
        }
        if (noRawat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data atau isi No.Rawat yang akan dihapus.");
            return;
        }
        try {
            ps = koneksi.prepareStatement("DELETE FROM " + NAMA_TABEL + " WHERE no_rawat = ?");
            ps.setString(1, noRawat);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Asesmen berhasil dihapus.");
            emptTeks();
            tampil();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus asesmen: " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    private void bindFormToStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, TNoRw.getText().trim());
        statement.setString(2, getTanggalJamAsesmen());
        statement.setString(3, KdDokter.getText().trim());
        statement.setString(4, SumberRujukan.getText().trim());
        statement.setString(5, DiagnosaRujukan.getText().trim());
        statement.setString(6, String.valueOf(Anamnesis.getSelectedItem()));
        statement.setString(7, Hubungan.getText().trim());
        statement.setString(8, CaraDatang.getText().trim());
        statement.setString(9, KeluhanUtama.getText().trim());
        statement.setString(10, RiwayatPenyakitSekarang.getText().trim());
        statement.setString(11, RiwayatPenyakitDahulu.getText().trim());
        statement.setString(12, RiwayatPengobatan.getText().trim());
        statement.setString(13, RiwayatPenyakitKeluarga.getText().trim());
        statement.setString(14, RiwayatAlergiObat.getText().trim());
        statement.setString(15, String.valueOf(Kesadaran.getSelectedItem()));
        statement.setString(16, BeratBadan.getText().trim());
        statement.setString(17, TinggiBadan.getText().trim());
        statement.setString(18, TekananDarah.getText().trim());
        statement.setString(19, Nadi.getText().trim());
        statement.setString(20, Respirasi.getText().trim());
        statement.setString(21, Suhu.getText().trim());
        statement.setString(22, SpO2.getText().trim());
        statement.setString(23, PemeriksaanMata.getText().trim());
        statement.setString(24, SistemSaraf.getText().trim());
        statement.setString(25, PemeriksaanTHT.getText().trim());
        statement.setString(26, PemeriksaanThoraks.getText().trim());
        statement.setString(27, PemeriksaanAbdomen.getText().trim());
        statement.setString(28, PemeriksaanEkstremitas.getText().trim());
        statement.setString(29, StatusLokalisasi.getText().trim());
        statement.setString(30, HasilLaboratorium.getText().trim());
        statement.setString(31, HasilEKG.getText().trim());
        statement.setString(32, HasilRadiologi.getText().trim());
        statement.setString(33, DiagnosisBanding.getText().trim());
        statement.setString(34, RencanaKerjaTerapi.getText().trim());
        statement.setString(35, CatatanPenting.getText().trim());
        statement.setString(36, DokterPengkaji.getText().trim());
        statement.setString(37, DokterDPJP.getText().trim());
    }

    private void bindFormToStatementForUpdate(PreparedStatement statement) throws Exception {
        statement.setString(1, getTanggalJamAsesmen());
        statement.setString(2, KdDokter.getText().trim());
        statement.setString(3, SumberRujukan.getText().trim());
        statement.setString(4, DiagnosaRujukan.getText().trim());
        statement.setString(5, String.valueOf(Anamnesis.getSelectedItem()));
        statement.setString(6, Hubungan.getText().trim());
        statement.setString(7, CaraDatang.getText().trim());
        statement.setString(8, KeluhanUtama.getText().trim());
        statement.setString(9, RiwayatPenyakitSekarang.getText().trim());
        statement.setString(10, RiwayatPenyakitDahulu.getText().trim());
        statement.setString(11, RiwayatPengobatan.getText().trim());
        statement.setString(12, RiwayatPenyakitKeluarga.getText().trim());
        statement.setString(13, RiwayatAlergiObat.getText().trim());
        statement.setString(14, String.valueOf(Kesadaran.getSelectedItem()));
        statement.setString(15, BeratBadan.getText().trim());
        statement.setString(16, TinggiBadan.getText().trim());
        statement.setString(17, TekananDarah.getText().trim());
        statement.setString(18, Nadi.getText().trim());
        statement.setString(19, Respirasi.getText().trim());
        statement.setString(20, Suhu.getText().trim());
        statement.setString(21, SpO2.getText().trim());
        statement.setString(22, PemeriksaanMata.getText().trim());
        statement.setString(23, SistemSaraf.getText().trim());
        statement.setString(24, PemeriksaanTHT.getText().trim());
        statement.setString(25, PemeriksaanThoraks.getText().trim());
        statement.setString(26, PemeriksaanAbdomen.getText().trim());
        statement.setString(27, PemeriksaanEkstremitas.getText().trim());
        statement.setString(28, StatusLokalisasi.getText().trim());
        statement.setString(29, HasilLaboratorium.getText().trim());
        statement.setString(30, HasilEKG.getText().trim());
        statement.setString(31, HasilRadiologi.getText().trim());
        statement.setString(32, DiagnosisBanding.getText().trim());
        statement.setString(33, RencanaKerjaTerapi.getText().trim());
        statement.setString(34, CatatanPenting.getText().trim());
        statement.setString(35, DokterPengkaji.getText().trim());
        statement.setString(36, DokterDPJP.getText().trim());
        statement.setString(37, TNoRw.getText().trim());
    }

    private String getTanggalJamAsesmen() {
        Date tanggal = TglAsesmen.getDate() == null ? new Date() : TglAsesmen.getDate();
        String jam = JamAsesmen.getText().trim().isEmpty() ? jamSekarang.format(new Date()) : JamAsesmen.getText().trim();
        return tanggalTabel.format(tanggal) + " " + jam;
    }

    private void normalisasiDokter() {
        if (!KdDokter.getText().trim().isEmpty() && NmDokter.getText().trim().isEmpty()) {
            NmDokter.setText(dokter.tampil3(KdDokter.getText().trim()));
        }
        if (DokterPengkaji.getText().trim().isEmpty()) {
            DokterPengkaji.setText(NmDokter.getText().trim());
        }
        if (DokterDPJP.getText().trim().isEmpty()) {
            DokterDPJP.setText(NmDokter.getText().trim());
        }
    }

    private void closeStatement() {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            ps = null;
        }
    }

    private void closeResultSet() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            rs = null;
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            AsesmenAwalRawatInapMedis dialog = new AsesmenAwalRawatInapMedis(new javax.swing.JFrame(), true);
            dialog.setVisible(true);
        });
    }
}
