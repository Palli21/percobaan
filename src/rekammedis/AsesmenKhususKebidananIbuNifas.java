/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class AsesmenKhususKebidananIbuNifas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private StringBuilder htmlContent;
    private String finger = "";
    // Legacy fields referenced by handlers but not present in the .form.
    private widget.ComboBox JenisPersalinan;
    private widget.ComboBox KeadaanAnak;
    private widget.ComboBox JenisKelamin;
    private widget.ComboBox KetubanPecah;
    private widget.ComboBox Episiotomi;
    private widget.ComboBox Pereneum;
    private widget.TextBox SCAI;
    private widget.TextBox BB1;
    private widget.TextBox PB;
    private widget.TextBox wita;
    private widget.TextBox jam1;
    private widget.TextBox mnt1;
    private widget.TextBox jam2;
    private widget.TextBox mnt2;
    private widget.TextBox jam3;
    private widget.TextBox mnt3;
    private widget.TextBox jam4;
    private widget.TextBox mnt4;
    private widget.TextBox jamtotal;
    private widget.TextBox mnttotal;
    private widget.TextBox darahan1;
    private widget.TextBox darahan2;
    private widget.TextBox darahan3;
    private widget.TextBox darahan4;
    private widget.TextBox darahantotal;
    private widget.Label jLabel27;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel37;
    private widget.Label jLabel43;
    private widget.Label jLabel48;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
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
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;

    // Spacing & Layout Constants for consistent UI
    private static final int SECTION_MARGIN_TOP = 15;
    private static final int SECTION_MARGIN_LEFT = 10;
    private static final int LABEL_WIDTH_SMALL = 70;
    private static final int LABEL_WIDTH_MEDIUM = 120;
    private static final int FIELD_HEIGHT = 23;
    private static final int FIELD_PADDING = 10;
    private static final int CHECKBOX_WIDTH = 80;
    private static final int TEXTBOX_WIDTH_SMALL = 30;
    private static final int TEXTBOX_WIDTH_MEDIUM = 80;
    private static final int TEXTBOX_WIDTH_LARGE = 150;

    // Color Palette for Visual Hierarchy
    private static final java.awt.Color COLOR_SECTION_HEADER_BG = new java.awt.Color(230, 240, 250); // Light blue
    private static final java.awt.Color COLOR_SECTION_HEADER_BORDER = new java.awt.Color(180, 200, 220);
    private static final java.awt.Color COLOR_SECTION_HEADER_TEXT = new java.awt.Color(50, 50, 100);
    private static final java.awt.Color COLOR_SUBJEKTIF_BG = new java.awt.Color(248, 248, 230); // Light yellow
    private static final java.awt.Color COLOR_OBJEKTIF_BG = new java.awt.Color(240, 248, 255); // Alice blue
    private static final java.awt.Color COLOR_ASSESSMENT_BG = new java.awt.Color(245, 255, 245); // Honeydew
    private static final java.awt.Color COLOR_SEPARATOR = new java.awt.Color(208, 221, 229);
    private static final java.awt.Color COLOR_PANEL_BG = new java.awt.Color(252, 252, 252);
    private static final java.awt.Color COLOR_PANEL_BORDER = new java.awt.Color(200, 200, 200);

    /**
     * Creates new form DlgRujuk
     * 
     * @param parent
     * @param modal
     */
    public AsesmenKhususKebidananIbuNifas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // Keep GUI Builder ( .form ) layout; disable runtime layout overrides.
        initSubjektifPlaceholders();
        hideSubjektifSection();

        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Kode Dokter", "Nama Dokter", "Tanggal",
                "Keluhan Utama", "Riwayat Keluhan Saat Ini", "Riwayat Kesehatan", "Riwayat Kesehatan Lain",
                "G", "P", "A", "HPHT", "HPL", "Imunisasi", "Opname", "Opname Sakit", "Opname RS", "Operasi",
                "Operasi Ket",
                "Pengobatan", "Pengobatan Ket", "Riwayat Alergi", "Riwayat Alergi Ket",
                "PF Mata", "PF Mata Lain", "PF Thyroid", "PF KGB", "PF Mammae", "PF Mammae Lain", "PF Puting",
                "PF Colostrum",
                "Kesadaran", "G-C-S", "TB (cm)", "BB (kg)", "Suhu (c)", "TD", "Nadi", "RR", "SpO2", "Wajah", "Mata",
                "Mulut", "Payudara",
                "Fundus Uteri", "Kontraksi Uterus", "Kandung Kemih", "Luka Operasi", "Lokhea", "Luka Perinerum",
                "Foley Catheter", "Ekstremitas",
                "Hb", "Ht", "Leukosit", "Trombosit", "Glukosa", "Urine", "Protein", "Keton", "Rontgen", "USG",
                "Analisa", "Penatalaksana", "Masalah/Diagnosa", "Tujuan/Target", "Kode Perawat", "Nama Perawat"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] columnWidths = {
                150, 150, 150, 150, 80, 100, 150, 140, 100, 100, 80, 80, 80, 80, 80, 80, 80, 100, 100, 100, 100,
                120, 120, 120, 120, 120, 120, 120, 120, 80, 80, 80, 80, 80, 80, 80, 80, 120, 120,
                150, 150, 150, 150, 120, 150
        };
        for (i = 0; i < columnWidths.length; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        GCS.setDocument(new batasInput((int) 4).getKata(GCS));
        TD.setDocument(new batasInput((int) 10).getKata(TD));
        Nadi.setDocument(new batasInput((int) 10).getKata(Nadi));
        RR.setDocument(new batasInput((int) 10).getKata(RR));
        Suhu.setDocument(new batasInput((int) 4).getKata(Suhu));
        SPO.setDocument(new batasInput((int) 10).getKata(SPO));
        BB.setDocument(new batasInput((int) 4).getKata(BB));
        TB.setDocument(new batasInput((int) 4).getKata(TB));
        Rontgen.setDocument(new batasInput((int) 100).getKata(Rontgen));
        USG.setDocument(new batasInput((int) 100).getKata(USG));
        Hb.setDocument(new batasInput((int) 4).getKata(Hb));
        Ht.setDocument(new batasInput((int) 4).getKata(Ht));
        Leukosit.setDocument(new batasInput((int) 4).getKata(Leukosit));
        Trombosit.setDocument(new batasInput((int) 4).getKata(Trombosit));
        Glukosa.setDocument(new batasInput((int) 4).getKata(Glukosa));
        Urine.setDocument(new batasInput((int) 4).getKata(Urine));
        Protein.setDocument(new batasInput((int) 4).getKata(Protein));
        Keton.setDocument(new batasInput((int) 4).getKata(Keton));
        KeluhanUtama.setDocument(new batasInput((int) 1000).getKata(KeluhanUtama));
        RiwayatKeluhan.setDocument(new batasInput((int) 1000).getKata(RiwayatKeluhan));
        RiwayatKesehatanLain.setDocument(new batasInput((int) 100).getKata(RiwayatKesehatanLain));
        Gravida.setDocument(new batasInput((int) 5).getKata(Gravida));
        Para.setDocument(new batasInput((int) 5).getKata(Para));
        Abortus.setDocument(new batasInput((int) 5).getKata(Abortus));
        HPHT.setDocument(new batasInput((int) 10).getKata(HPHT));
        HPL.setDocument(new batasInput((int) 10).getKata(HPL));
        Imunisasi.setDocument(new batasInput((int) 100).getKata(Imunisasi));
        OpnameSakit.setDocument(new batasInput((int) 100).getKata(OpnameSakit));
        OpnameRS.setDocument(new batasInput((int) 100).getKata(OpnameRS));
        OperasiKet.setDocument(new batasInput((int) 100).getKata(OperasiKet));
        PengobatanKet.setDocument(new batasInput((int) 100).getKata(PengobatanKet));
        AlergiKet.setDocument(new batasInput((int) 100).getKata(AlergiKet));
        MataLain.setDocument(new batasInput((int) 100).getKata(MataLain));
        MammaeLain.setDocument(new batasInput((int) 100).getKata(MammaeLain));
        Tujuan.setDocument(new batasInput((int) 1000).getKata(Tujuan));
        Masalah.setDocument(new batasInput((int) 1000).getKata(Masalah));
        Penatalaksana.setDocument(new batasInput((int) 1000).getKata(Penatalaksana));
        Analisa.setDocument(new batasInput((int) 1000).getKata(Analisa));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                KdPetugas.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KdDokter.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        +
                        ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}" +
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        +
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        +
                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}" +
                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}" +
                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}" +
                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}" +
                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }

    private void initSubjektifPlaceholders() {
        if (JenisPersalinan == null) {
            JenisPersalinan = new widget.ComboBox();
            JenisPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "E. Vaccum/Forceps" }));
        }
        if (SCAI == null)
            SCAI = new widget.TextBox();
        if (KeadaanAnak == null) {
            KeadaanAnak = new widget.ComboBox();
            KeadaanAnak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hidup", "Mati" }));
        }
        if (JenisKelamin == null) {
            JenisKelamin = new widget.ComboBox();
            JenisKelamin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-Laki", "Perempuan" }));
        }
        if (BB1 == null)
            BB1 = new widget.TextBox();
        if (PB == null)
            PB = new widget.TextBox();
        if (KetubanPecah == null) {
            KetubanPecah = new widget.ComboBox();
            KetubanPecah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Amniotomi" }));
        }
        if (wita == null)
            wita = new widget.TextBox();
        if (Episiotomi == null) {
            Episiotomi = new widget.ComboBox();
            Episiotomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dilakukan", "Tidak Dilakukan" }));
        }
        if (Pereneum == null) {
            Pereneum = new widget.ComboBox();
            Pereneum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Utuh", "Ruptur" }));
        }
        if (jam1 == null)
            jam1 = new widget.TextBox();
        if (mnt1 == null)
            mnt1 = new widget.TextBox();
        if (jam2 == null)
            jam2 = new widget.TextBox();
        if (mnt2 == null)
            mnt2 = new widget.TextBox();
        if (jam3 == null)
            jam3 = new widget.TextBox();
        if (mnt3 == null)
            mnt3 = new widget.TextBox();
        if (jam4 == null)
            jam4 = new widget.TextBox();
        if (mnt4 == null)
            mnt4 = new widget.TextBox();
        if (jamtotal == null)
            jamtotal = new widget.TextBox();
        if (mnttotal == null)
            mnttotal = new widget.TextBox();
        if (darahan1 == null)
            darahan1 = new widget.TextBox();
        if (darahan2 == null)
            darahan2 = new widget.TextBox();
        if (darahan3 == null)
            darahan3 = new widget.TextBox();
        if (darahan4 == null)
            darahan4 = new widget.TextBox();
        if (darahantotal == null)
            darahantotal = new widget.TextBox();
        if (jLabel27 == null)
            jLabel27 = new widget.Label();
        if (jLabel30 == null)
            jLabel30 = new widget.Label();
        if (jLabel31 == null)
            jLabel31 = new widget.Label();
        if (jLabel32 == null)
            jLabel32 = new widget.Label();
        if (jLabel37 == null)
            jLabel37 = new widget.Label();
        if (jLabel43 == null)
            jLabel43 = new widget.Label();
        if (jLabel48 == null)
            jLabel48 = new widget.Label();
        if (jLabel54 == null)
            jLabel54 = new widget.Label();
        if (jLabel55 == null)
            jLabel55 = new widget.Label();
        if (jLabel56 == null)
            jLabel56 = new widget.Label();
        if (jLabel60 == null)
            jLabel60 = new widget.Label();
        if (jLabel61 == null)
            jLabel61 = new widget.Label();
        if (jLabel62 == null)
            jLabel62 = new widget.Label();
        if (jLabel63 == null)
            jLabel63 = new widget.Label();
        if (jLabel64 == null)
            jLabel64 = new widget.Label();
        if (jLabel65 == null)
            jLabel65 = new widget.Label();
        if (jLabel66 == null)
            jLabel66 = new widget.Label();
        if (jLabel67 == null)
            jLabel67 = new widget.Label();
        if (jLabel68 == null)
            jLabel68 = new widget.Label();
        if (jLabel69 == null)
            jLabel69 = new widget.Label();
        if (jLabel70 == null)
            jLabel70 = new widget.Label();
        if (jLabel71 == null)
            jLabel71 = new widget.Label();
        if (jLabel72 == null)
            jLabel72 = new widget.Label();
        if (jLabel73 == null)
            jLabel73 = new widget.Label();
        if (jLabel74 == null)
            jLabel74 = new widget.Label();
        if (jLabel75 == null)
            jLabel75 = new widget.Label();
        if (jLabel76 == null)
            jLabel76 = new widget.Label();
        if (jLabel77 == null)
            jLabel77 = new widget.Label();
        if (jLabel78 == null)
            jLabel78 = new widget.Label();
        if (jLabel79 == null)
            jLabel79 = new widget.Label();
    }

    private void initSubjektifSection() {
        if (FormInput == null) {
            return;
        }
        final int subjOffset = 25;

        // Add Section A Header with visual improvement
        widget.PanelBiasa headerA = createSectionHeader("A. ALASAN MASUK", SECTION_MARGIN_LEFT, 70, 850);
        FormInput.add(headerA);

        jLabelSubjKeluhan = initLabel(jLabelSubjKeluhan, "jLabelSubjKeluhan", "1. Keluhan Utama:");
        addToForm(jLabelSubjKeluhan, SECTION_MARGIN_LEFT, 110, 110, FIELD_HEIGHT);
        jLabelSubjRiwayatKeluhan = initLabel(jLabelSubjRiwayatKeluhan, "jLabelSubjRiwayatKeluhan",
                "2. Riwayat Keluhan:");
        addToForm(jLabelSubjRiwayatKeluhan, 420, 80 + subjOffset, 120, 23);
        jLabelSubjRiwayatKesehatan = initLabel(jLabelSubjRiwayatKesehatan, "jLabelSubjRiwayatKesehatan",
                "B. Riwayat Kesehatan");
        addToForm(jLabelSubjRiwayatKesehatan, 10, 130 + subjOffset, 120, 23);
        jLabelSubjGPA = initLabel(jLabelSubjGPA, "jLabelSubjGPA", "G/P/A :");
        addToForm(jLabelSubjGPA, 10, 160 + subjOffset, 50, 23);
        jLabelSubjP = initLabel(jLabelSubjP, "jLabelSubjP", "P");
        addToForm(jLabelSubjP, 100, 160 + subjOffset, 10, 23);
        jLabelSubjA = initLabel(jLabelSubjA, "jLabelSubjA", "A");
        addToForm(jLabelSubjA, 155, 160 + subjOffset, 10, 23);
        jLabelSubjHPHT = initLabel(jLabelSubjHPHT, "jLabelSubjHPHT", "HPHT :");
        addToForm(jLabelSubjHPHT, 210, 160 + subjOffset, 45, 23);
        jLabelSubjHPL = initLabel(jLabelSubjHPL, "jLabelSubjHPL", "HPL :");
        addToForm(jLabelSubjHPL, 340, 160 + subjOffset, 35, 23);
        jLabelSubjImunisasi = initLabel(jLabelSubjImunisasi, "jLabelSubjImunisasi", "Imunisasi :");
        addToForm(jLabelSubjImunisasi, 460, 160 + subjOffset, 70, 23);
        jLabelSubjOpname = initLabel(jLabelSubjOpname, "jLabelSubjOpname", "Opname :");
        addToForm(jLabelSubjOpname, 10, 190 + subjOffset, 60, 23);
        jLabelSubjOpnameSakit = initLabel(jLabelSubjOpnameSakit, "jLabelSubjOpnameSakit", "Sakit :");
        addToForm(jLabelSubjOpnameSakit, 210, 190 + subjOffset, 50, 23);
        jLabelSubjOpnameRS = initLabel(jLabelSubjOpnameRS, "jLabelSubjOpnameRS", "RS :");
        addToForm(jLabelSubjOpnameRS, 390, 190 + subjOffset, 30, 23);
        jLabelSubjOperasi = initLabel(jLabelSubjOperasi, "jLabelSubjOperasi", "Operasi :");
        addToForm(jLabelSubjOperasi, 10, 220 + subjOffset, 60, 23);
        jLabelSubjOperasiKet = initLabel(jLabelSubjOperasiKet, "jLabelSubjOperasiKet", "Ket :");
        addToForm(jLabelSubjOperasiKet, 210, 220 + subjOffset, 40, 23);
        jLabelSubjPengobatan = initLabel(jLabelSubjPengobatan, "jLabelSubjPengobatan", "Pengobatan :");
        addToForm(jLabelSubjPengobatan, 430, 220 + subjOffset, 90, 23);
        jLabelSubjPengobatanKet = initLabel(jLabelSubjPengobatanKet, "jLabelSubjPengobatanKet", "Ket :");
        addToForm(jLabelSubjPengobatanKet, 660, 220 + subjOffset, 40, 23);
        jLabelSubjAlergi = initLabel(jLabelSubjAlergi, "jLabelSubjAlergi", "C. Alergi :");
        addToForm(jLabelSubjAlergi, 10, 250 + subjOffset, 70, 23);
        jLabelSubjMata = initLabel(jLabelSubjMata, "jLabelSubjMata", "Mata :");
        addToForm(jLabelSubjMata, 420, 250 + subjOffset, 40, 23);
        jLabelSubjThyroid = initLabel(jLabelSubjThyroid, "jLabelSubjThyroid", "Thyroid :");
        addToForm(jLabelSubjThyroid, 10, 280 + subjOffset, 60, 23);
        jLabelSubjKGB = initLabel(jLabelSubjKGB, "jLabelSubjKGB", "KGB :");
        addToForm(jLabelSubjKGB, 250, 280 + subjOffset, 40, 23);
        jLabelSubjMammae = initLabel(jLabelSubjMammae, "jLabelSubjMammae", "Mammae :");
        addToForm(jLabelSubjMammae, 10, 310 + subjOffset, 60, 23);
        jLabelSubjPuting = initLabel(jLabelSubjPuting, "jLabelSubjPuting", "Puting :");
        addToForm(jLabelSubjPuting, 10, 340 + subjOffset, 60, 23);
        jLabelSubjColostrum = initLabel(jLabelSubjColostrum, "jLabelSubjColostrum", "Colostrum :");
        addToForm(jLabelSubjColostrum, 270, 340 + subjOffset, 80, 23);

        scrollPaneKeluhan = initScrollPane(scrollPaneKeluhan, "scrollPaneKeluhan");
        KeluhanUtama = initTextArea(KeluhanUtama, "KeluhanUtama");
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPaneKeluhan.setViewportView(KeluhanUtama);
        addToForm(scrollPaneKeluhan, 120, 80 + subjOffset, 280, 45);

        scrollPaneRiwayatKeluhan = initScrollPane(scrollPaneRiwayatKeluhan, "scrollPaneRiwayatKeluhan");
        RiwayatKeluhan = initTextArea(RiwayatKeluhan, "RiwayatKeluhan");
        scrollPaneRiwayatKeluhan.setViewportView(RiwayatKeluhan);
        addToForm(scrollPaneRiwayatKeluhan, 540, 80 + subjOffset, 300, 45);

        // Add separator after section A
        javax.swing.JSeparator sepA = createSeparator(165, 860);
        FormInput.add(sepA);

        // Add Section B Header with visual improvement
        widget.PanelBiasa headerB = createSectionHeader("B. RIWAYAT KESEHATAN", SECTION_MARGIN_LEFT, 175, 850);
        FormInput.add(headerB);

        // Adjust Y positioning for section B content
        int sectionBStartY = 215;
        ChkDM = initCheckBox(ChkDM, "ChkDM", "DM");
        addToForm(ChkDM, 130, 130 + subjOffset, 45, 23);
        ChkHT = initCheckBox(ChkHT, "ChkHT", "HT");
        addToForm(ChkHT, 90, sectionBStartY, 45, FIELD_HEIGHT);
        ChkTBC = initCheckBox(ChkTBC, "ChkTBC", "TBC");
        addToForm(ChkTBC, 140, sectionBStartY, 50, FIELD_HEIGHT);
        ChkAsma = initCheckBox(ChkAsma, "ChkAsma", "Asma");
        addToForm(ChkAsma, 200, sectionBStartY, 55, FIELD_HEIGHT);
        ChkGinjal = initCheckBox(ChkGinjal, "ChkGinjal", "Ginjal");
        addToForm(ChkGinjal, 260, sectionBStartY, 60, FIELD_HEIGHT);
        ChkHepar = initCheckBox(ChkHepar, "ChkHepar", "Hepar");
        addToForm(ChkHepar, 330, sectionBStartY, 60, FIELD_HEIGHT);
        ChkLain = initCheckBox(ChkLain, "ChkLain", "Lain-lain");
        addToForm(ChkLain, 400, sectionBStartY, 80, FIELD_HEIGHT);
        RiwayatKesehatanLain = initTextBox(RiwayatKesehatanLain, "RiwayatKesehatanLain");
        addToForm(RiwayatKesehatanLain, 490, sectionBStartY, 260, FIELD_HEIGHT);

        Gravida = initTextBox(Gravida, "Gravida");
        addToForm(Gravida, 60, 160 + subjOffset, 30, 23);
        Para = initTextBox(Para, "Para");
        addToForm(Para, 115, 160 + subjOffset, 30, 23);
        Abortus = initTextBox(Abortus, "Abortus");
        addToForm(Abortus, 170, 160 + subjOffset, 30, 23);
        HPHT = initTextBox(HPHT, "HPHT");
        addToForm(HPHT, 255, 160 + subjOffset, 80, 23);
        HPL = initTextBox(HPL, "HPL");
        addToForm(HPL, 375, 160 + subjOffset, 80, 23);
        Imunisasi = initTextBox(Imunisasi, "Imunisasi");
        addToForm(Imunisasi, 530, 160 + subjOffset, 150, 23);

        ChkOpnamePernah = initCheckBox(ChkOpnamePernah, "ChkOpnamePernah", "Pernah");
        addToForm(ChkOpnamePernah, 70, 190 + subjOffset, 70, 23);
        ChkOpnameTidak = initCheckBox(ChkOpnameTidak, "ChkOpnameTidak", "Tidak");
        addToForm(ChkOpnameTidak, 140, 190 + subjOffset, 60, 23);
        OpnameSakit = initTextBox(OpnameSakit, "OpnameSakit");
        addToForm(OpnameSakit, 260, 190 + subjOffset, 120, 23);
        OpnameRS = initTextBox(OpnameRS, "OpnameRS");
        addToForm(OpnameRS, 420, 190 + subjOffset, 120, 23);

        ChkOperasiPernah = initCheckBox(ChkOperasiPernah, "ChkOperasiPernah", "Pernah");
        addToForm(ChkOperasiPernah, 70, 220 + subjOffset, 70, 23);
        ChkOperasiTidak = initCheckBox(ChkOperasiTidak, "ChkOperasiTidak", "Tidak");
        addToForm(ChkOperasiTidak, 140, 220 + subjOffset, 60, 23);
        OperasiKet = initTextBox(OperasiKet, "OperasiKet");
        addToForm(OperasiKet, 250, 220 + subjOffset, 170, 23);

        ChkPengobatanPernah = initCheckBox(ChkPengobatanPernah, "ChkPengobatanPernah", "Pernah");
        addToForm(ChkPengobatanPernah, 520, 220 + subjOffset, 70, 23);
        ChkPengobatanTidak = initCheckBox(ChkPengobatanTidak, "ChkPengobatanTidak", "Tidak");
        addToForm(ChkPengobatanTidak, 590, 220 + subjOffset, 60, 23);
        PengobatanKet = initTextBox(PengobatanKet, "PengobatanKet");
        addToForm(PengobatanKet, 700, 220 + subjOffset, 130, 23);

        ChkAlergiAda = initCheckBox(ChkAlergiAda, "ChkAlergiAda", "Ada");
        addToForm(ChkAlergiAda, 90, 250 + subjOffset, 50, 23);
        ChkAlergiTidak = initCheckBox(ChkAlergiTidak, "ChkAlergiTidak", "Tidak");
        addToForm(ChkAlergiTidak, 150, 250 + subjOffset, 60, 23);
        AlergiKet = initTextBox(AlergiKet, "AlergiKet");
        addToForm(AlergiKet, 220, 250 + subjOffset, 190, 23);

        ChkMataAnemia = initCheckBox(ChkMataAnemia, "ChkMataAnemia", "Anemia");
        addToForm(ChkMataAnemia, 470, 250 + subjOffset, 70, 23);
        ChkMataIcterus = initCheckBox(ChkMataIcterus, "ChkMataIcterus", "Icterus");
        addToForm(ChkMataIcterus, 540, 250 + subjOffset, 70, 23);
        ChkMataTAK = initCheckBox(ChkMataTAK, "ChkMataTAK", "t.a.k");
        addToForm(ChkMataTAK, 630, 250 + subjOffset, 60, 23);
        ChkMataLain = initCheckBox(ChkMataLain, "ChkMataLain", "Lain");
        addToForm(ChkMataLain, 700, 250 + subjOffset, 50, 23);
        MataLain = initTextBox(MataLain, "MataLain");
        addToForm(MataLain, 760, 250 + subjOffset, 90, 23);

        ChkThyroidMembesar = initCheckBox(ChkThyroidMembesar, "ChkThyroidMembesar", "Membesar");
        addToForm(ChkThyroidMembesar, 80, 280 + subjOffset, 80, 23);
        ChkThyroidTidak = initCheckBox(ChkThyroidTidak, "ChkThyroidTidak", "Tidak");
        addToForm(ChkThyroidTidak, 160, 280 + subjOffset, 60, 23);
        ChkKGBMembesar = initCheckBox(ChkKGBMembesar, "ChkKGBMembesar", "Membesar");
        addToForm(ChkKGBMembesar, 300, 280 + subjOffset, 80, 23);
        ChkKGBTidak = initCheckBox(ChkKGBTidak, "ChkKGBTidak", "Tidak");
        addToForm(ChkKGBTidak, 380, 280 + subjOffset, 60, 23);

        ChkMammaeLembek = initCheckBox(ChkMammaeLembek, "ChkMammaeLembek", "Lembek");
        addToForm(ChkMammaeLembek, 80, 310 + subjOffset, 70, 23);
        ChkMammaeKeras = initCheckBox(ChkMammaeKeras, "ChkMammaeKeras", "Keras");
        addToForm(ChkMammaeKeras, 150, 310 + subjOffset, 60, 23);
        ChkMammaePanas = initCheckBox(ChkMammaePanas, "ChkMammaePanas", "Panas");
        addToForm(ChkMammaePanas, 220, 310 + subjOffset, 60, 23);
        ChkMammaeKemerahan = initCheckBox(ChkMammaeKemerahan, "ChkMammaeKemerahan", "Kemerahan");
        addToForm(ChkMammaeKemerahan, 290, 310 + subjOffset, 90, 23);
        ChkMammaeBenjolan = initCheckBox(ChkMammaeBenjolan, "ChkMammaeBenjolan", "Benjolan");
        addToForm(ChkMammaeBenjolan, 380, 310 + subjOffset, 80, 23);
        ChkMammaeLain = initCheckBox(ChkMammaeLain, "ChkMammaeLain", "Lain");
        addToForm(ChkMammaeLain, 470, 310 + subjOffset, 50, 23);
        MammaeLain = initTextBox(MammaeLain, "MammaeLain");
        addToForm(MammaeLain, 520, 310 + subjOffset, 120, 23);

        ChkPutingMenonjol = initCheckBox(ChkPutingMenonjol, "ChkPutingMenonjol", "Menonjol");
        addToForm(ChkPutingMenonjol, 70, 340 + subjOffset, 90, 23);
        ChkPutingDatar = initCheckBox(ChkPutingDatar, "ChkPutingDatar", "Datar");
        addToForm(ChkPutingDatar, 170, 340 + subjOffset, 70, 23);
        ChkColostrumLancar = initCheckBox(ChkColostrumLancar, "ChkColostrumLancar", "Lancar");
        addToForm(ChkColostrumLancar, 360, 340 + subjOffset, 70, 23);
        ChkColostrumBelum = initCheckBox(ChkColostrumBelum, "ChkColostrumBelum", "Belum Ada");
        addToForm(ChkColostrumBelum, 460, 340 + subjOffset, 90, 23);

        // Add separator and header for Section C
        javax.swing.JSeparator sepC = createSeparator(338, 860);
        FormInput.add(sepC);

        widget.PanelBiasa headerC = createSectionHeader("C. PEMERIKSAAN FISIK AWAL", SECTION_MARGIN_LEFT, 348, 850);
        FormInput.add(headerC);

        // Update section label texts for D and E with better styling
        if (jLabel83 != null) {
            jLabel83.setText("D. PEMERIKSAAN UMUM");
            jLabel83.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
        }
        if (jLabel85 != null) {
            jLabel85.setText("E. PEMERIKSAAN FISIK");
            jLabel85.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
        }
    }

    private void initSubjektifFtoNSection() {
        if (FormInput == null) {
            return;
        }
        if (jLabel58 != null) {
            jLabel58.setText("K. Pemeriksaan Penunjang");
        }
        if (label3 != null) {
            label3.setText("L. Rumusan Masalah");
        }
        jLabelFGenitalia = initLabel(jLabelFGenitalia, "jLabelFGenitalia", "F. Genitalia");
        addToForm(jLabelFGenitalia, 20, 970, 90, 23);
        jLabelFGenitaliaLokia = initLabel(jLabelFGenitaliaLokia, "jLabelFGenitaliaLokia", "Lokia :");
        addToForm(jLabelFGenitaliaLokia, 40, 995, 45, 23);
        jLabelFGenitaliaJenis = initLabel(jLabelFGenitaliaJenis, "jLabelFGenitaliaJenis", "Jenis");
        addToForm(jLabelFGenitaliaJenis, 90, 995, 40, 23);
        LokiaJenis = initTextBox(LokiaJenis, "LokiaJenis");
        addToForm(LokiaJenis, 135, 995, 80, 23);
        jLabelFGenitaliaWarna = initLabel(jLabelFGenitaliaWarna, "jLabelFGenitaliaWarna", "Warna");
        addToForm(jLabelFGenitaliaWarna, 225, 995, 45, 23);
        LokiaWarna = initTextBox(LokiaWarna, "LokiaWarna");
        addToForm(LokiaWarna, 275, 995, 80, 23);
        jLabelFGenitaliaBanyak = initLabel(jLabelFGenitaliaBanyak, "jLabelFGenitaliaBanyak", "Banyak");
        addToForm(jLabelFGenitaliaBanyak, 365, 995, 50, 23);
        LokiaBanyak = initTextBox(LokiaBanyak, "LokiaBanyak");
        addToForm(LokiaBanyak, 420, 995, 60, 23);
        jLabelFGenitaliaBau = initLabel(jLabelFGenitaliaBau, "jLabelFGenitaliaBau", "Bau :");
        addToForm(jLabelFGenitaliaBau, 490, 995, 35, 23);
        ChkLokiaBauPos = initCheckBox(ChkLokiaBauPos, "ChkLokiaBauPos", "Positif");
        addToForm(ChkLokiaBauPos, 530, 995, 65, 23);
        ChkLokiaBauNeg = initCheckBox(ChkLokiaBauNeg, "ChkLokiaBauNeg", "Negatif");
        addToForm(ChkLokiaBauNeg, 600, 995, 70, 23);

        jLabelFGenitaliaPerineum = initLabel(jLabelFGenitaliaPerineum, "jLabelFGenitaliaPerineum", "Perineum :");
        addToForm(jLabelFGenitaliaPerineum, 40, 1020, 70, 23);
        ChkPerineumUtuh = initCheckBox(ChkPerineumUtuh, "ChkPerineumUtuh", "Utuh");
        addToForm(ChkPerineumUtuh, 120, 1020, 55, 23);
        ChkPerineumRuptur = initCheckBox(ChkPerineumRuptur, "ChkPerineumRuptur", "Ruptur");
        addToForm(ChkPerineumRuptur, 180, 1020, 70, 23);
        jLabelFGenitaliaVulva = initLabel(jLabelFGenitaliaVulva, "jLabelFGenitaliaVulva", "Vulva :");
        addToForm(jLabelFGenitaliaVulva, 260, 1020, 50, 23);
        ChkVulvaVarises = initCheckBox(ChkVulvaVarises, "ChkVulvaVarises", "Varises");
        addToForm(ChkVulvaVarises, 320, 1020, 70, 23);
        ChkVulvaEdema = initCheckBox(ChkVulvaEdema, "ChkVulvaEdema", "Edema");
        addToForm(ChkVulvaEdema, 395, 1020, 65, 23);
        ChkVulvaCondyloma = initCheckBox(ChkVulvaCondyloma, "ChkVulvaCondyloma", "Condyloma");
        addToForm(ChkVulvaCondyloma, 465, 1020, 90, 23);
        jLabelFGenitaliaBartholini = initLabel(jLabelFGenitaliaBartholini, "jLabelFGenitaliaBartholini",
                "Bartholini :");
        addToForm(jLabelFGenitaliaBartholini, 570, 1020, 80, 23);
        ChkBartholiniMembesar = initCheckBox(ChkBartholiniMembesar, "ChkBartholiniMembesar", "Membesar");
        addToForm(ChkBartholiniMembesar, 655, 1020, 90, 23);
        ChkBartholiniTidak = initCheckBox(ChkBartholiniTidak, "ChkBartholiniTidak", "Tidak");
        addToForm(ChkBartholiniTidak, 745, 1020, 60, 23);

        jLabelFGenitaliaVagina = initLabel(jLabelFGenitaliaVagina, "jLabelFGenitaliaVagina", "Vagina :");
        addToForm(jLabelFGenitaliaVagina, 40, 1045, 55, 23);
        ChkVaginaCairan = initCheckBox(ChkVaginaCairan, "ChkVaginaCairan", "Cairan");
        addToForm(ChkVaginaCairan, 105, 1045, 65, 23);
        jLabelFGenitaliaVaginaBau = initLabel(jLabelFGenitaliaVaginaBau, "jLabelFGenitaliaVaginaBau", "Bau");
        addToForm(jLabelFGenitaliaVaginaBau, 175, 1045, 35, 23);
        ChkVaginaBauPos = initCheckBox(ChkVaginaBauPos, "ChkVaginaBauPos", "+");
        addToForm(ChkVaginaBauPos, 215, 1045, 35, 23);
        ChkVaginaBauNeg = initCheckBox(ChkVaginaBauNeg, "ChkVaginaBauNeg", "-");
        addToForm(ChkVaginaBauNeg, 255, 1045, 35, 23);
        jLabelFGenitaliaVaginaWarna = initLabel(jLabelFGenitaliaVaginaWarna, "jLabelFGenitaliaVaginaWarna", "Warna");
        addToForm(jLabelFGenitaliaVaginaWarna, 300, 1045, 45, 23);
        VaginaWarna = initTextBox(VaginaWarna, "VaginaWarna");
        addToForm(VaginaWarna, 350, 1045, 80, 23);
        ChkVaginaProlapsus = initCheckBox(ChkVaginaProlapsus, "ChkVaginaProlapsus", "Prolapsus");
        addToForm(ChkVaginaProlapsus, 440, 1045, 80, 23);
        ChkVaginaTAK = initCheckBox(ChkVaginaTAK, "ChkVaginaTAK", "t.a.k");
        addToForm(ChkVaginaTAK, 525, 1045, 60, 23);

        jLabelGAnus = initLabel(jLabelGAnus, "jLabelGAnus", "G. Anus");
        addToForm(jLabelGAnus, 20, 1070, 60, 23);
        ChkAnusHemoroid = initCheckBox(ChkAnusHemoroid, "ChkAnusHemoroid", "Haemoroid");
        addToForm(ChkAnusHemoroid, 120, 1070, 90, 23);
        ChkAnusTAK = initCheckBox(ChkAnusTAK, "ChkAnusTAK", "t.a.k");
        addToForm(ChkAnusTAK, 215, 1070, 60, 23);

        jLabelHEkstremitas = initLabel(jLabelHEkstremitas, "jLabelHEkstremitas", "H. Ekstremitas");
        addToForm(jLabelHEkstremitas, 20, 1095, 95, 23);
        ChkEkstOedema = initCheckBox(ChkEkstOedema, "ChkEkstOedema", "Oedema");
        addToForm(ChkEkstOedema, 130, 1095, 75, 23);
        ChkEkstVarises = initCheckBox(ChkEkstVarises, "ChkEkstVarises", "Varises");
        addToForm(ChkEkstVarises, 210, 1095, 70, 23);
        ChkEkstRefleks = initCheckBox(ChkEkstRefleks, "ChkEkstRefleks", "Refleks +/-");
        addToForm(ChkEkstRefleks, 290, 1095, 90, 23);
        ChkEkstParalise = initCheckBox(ChkEkstParalise, "ChkEkstParalise", "Paralise di");
        addToForm(ChkEkstParalise, 390, 1095, 80, 23);
        EkstParaliseKet = initTextBox(EkstParaliseKet, "EkstParaliseKet");
        addToForm(EkstParaliseKet, 470, 1095, 90, 23);

        jLabelIHoman = initLabel(jLabelIHoman, "jLabelIHoman", "I. Homan Sign");
        addToForm(jLabelIHoman, 20, 1120, 90, 23);
        ChkHomanYa = initCheckBox(ChkHomanYa, "ChkHomanYa", "Ya");
        addToForm(ChkHomanYa, 130, 1120, 40, 23);
        ChkHomanTidak = initCheckBox(ChkHomanTidak, "ChkHomanTidak", "Tidak");
        addToForm(ChkHomanTidak, 175, 1120, 60, 23);

        jLabelJPola = initLabel(jLabelJPola, "jLabelJPola", "J. Pola Kebiasaan");
        addToForm(jLabelJPola, 20, 1145, 130, 23);
        jLabelJPolaNutrisi = initLabel(jLabelJPolaNutrisi, "jLabelJPolaNutrisi", "1. Nutrisi");
        addToForm(jLabelJPolaNutrisi, 40, 1170, 70, 23);
        ChkNutrisiMual = initCheckBox(ChkNutrisiMual, "ChkNutrisiMual", "Mual");
        addToForm(ChkNutrisiMual, 120, 1170, 55, 23);
        ChkNutrisiMuntah = initCheckBox(ChkNutrisiMuntah, "ChkNutrisiMuntah", "Muntah");
        addToForm(ChkNutrisiMuntah, 180, 1170, 65, 23);
        ChkNutrisiTidakNafsu = initCheckBox(ChkNutrisiTidakNafsu, "ChkNutrisiTidakNafsu", "Tidak Nafsu Makan");
        addToForm(ChkNutrisiTidakNafsu, 250, 1170, 140, 23);
        ChkNutrisiPuasa = initCheckBox(ChkNutrisiPuasa, "ChkNutrisiPuasa", "Puasa");
        addToForm(ChkNutrisiPuasa, 395, 1170, 60, 23);
        ChkNutrisiTAK = initCheckBox(ChkNutrisiTAK, "ChkNutrisiTAK", "t.a.k");
        addToForm(ChkNutrisiTAK, 460, 1170, 60, 23);

        jLabelJPolaEliminasi = initLabel(jLabelJPolaEliminasi, "jLabelJPolaEliminasi", "2. Eliminasi");
        addToForm(jLabelJPolaEliminasi, 40, 1195, 80, 23);
        jLabelJPolaBab = initLabel(jLabelJPolaBab, "jLabelJPolaBab", "BAB :");
        addToForm(jLabelJPolaBab, 120, 1195, 40, 23);
        ChkBabKonstipasi = initCheckBox(ChkBabKonstipasi, "ChkBabKonstipasi", "Konstipasi");
        addToForm(ChkBabKonstipasi, 165, 1195, 80, 23);
        ChkBabDiare = initCheckBox(ChkBabDiare, "ChkBabDiare", "Diare");
        addToForm(ChkBabDiare, 250, 1195, 55, 23);
        ChkBabTAK = initCheckBox(ChkBabTAK, "ChkBabTAK", "t.a.k");
        addToForm(ChkBabTAK, 310, 1195, 60, 23);
        jLabelJPolaBak = initLabel(jLabelJPolaBak, "jLabelJPolaBak", "BAK :");
        addToForm(jLabelJPolaBak, 380, 1195, 40, 23);
        ChkBakRetensi = initCheckBox(ChkBakRetensi, "ChkBakRetensi", "Retensi Urine");
        addToForm(ChkBakRetensi, 425, 1195, 100, 23);
        ChkBakKateter = initCheckBox(ChkBakKateter, "ChkBakKateter", "Kateter");
        addToForm(ChkBakKateter, 530, 1195, 70, 23);
        ChkBakOliguri = initCheckBox(ChkBakOliguri, "ChkBakOliguri", "Oliguri");
        addToForm(ChkBakOliguri, 605, 1195, 70, 23);
        ChkBakTAK = initCheckBox(ChkBakTAK, "ChkBakTAK", "t.a.k");
        addToForm(ChkBakTAK, 680, 1195, 60, 23);

        jLabelJPolaIstirahat = initLabel(jLabelJPolaIstirahat, "jLabelJPolaIstirahat", "3. Istirahat Tidur");
        addToForm(jLabelJPolaIstirahat, 40, 1220, 120, 23);
        ChkIstirahatInsomnia = initCheckBox(ChkIstirahatInsomnia, "ChkIstirahatInsomnia", "Insomnia");
        addToForm(ChkIstirahatInsomnia, 170, 1220, 70, 23);
        ChkIstirahatTidakAda = initCheckBox(ChkIstirahatTidakAda, "ChkIstirahatTidakAda", "Tidak ada keluhan");
        addToForm(ChkIstirahatTidakAda, 245, 1220, 130, 23);
        ChkIstirahatLain = initCheckBox(ChkIstirahatLain, "ChkIstirahatLain", "Lain-lain");
        addToForm(ChkIstirahatLain, 380, 1220, 80, 23);

        jLabelJPolaAktivitas = initLabel(jLabelJPolaAktivitas, "jLabelJPolaAktivitas", "4. Aktivitas");
        addToForm(jLabelJPolaAktivitas, 40, 1245, 80, 23);
        ChkAktivitasMandiri = initCheckBox(ChkAktivitasMandiri, "ChkAktivitasMandiri", "Mandiri");
        addToForm(ChkAktivitasMandiri, 170, 1245, 70, 23);
        ChkAktivitasBergantung = initCheckBox(ChkAktivitasBergantung, "ChkAktivitasBergantung", "Bergantung sebagian");
        addToForm(ChkAktivitasBergantung, 245, 1245, 130, 23);
        ChkAktivitasPenuh = initCheckBox(ChkAktivitasPenuh, "ChkAktivitasPenuh", "Penuh");
        addToForm(ChkAktivitasPenuh, 380, 1245, 60, 23);

        jLabelJPolaPsikologis = initLabel(jLabelJPolaPsikologis, "jLabelJPolaPsikologis", "5. Psikologis");
        addToForm(jLabelJPolaPsikologis, 40, 1270, 90, 23);
        ChkPsikoGembira = initCheckBox(ChkPsikoGembira, "ChkPsikoGembira", "Gembira");
        addToForm(ChkPsikoGembira, 170, 1270, 70, 23);
        ChkPsikoTenang = initCheckBox(ChkPsikoTenang, "ChkPsikoTenang", "Tenang");
        addToForm(ChkPsikoTenang, 245, 1270, 60, 23);
        ChkPsikoCemas = initCheckBox(ChkPsikoCemas, "ChkPsikoCemas", "Cemas");
        addToForm(ChkPsikoCemas, 310, 1270, 60, 23);
        ChkPsikoMenangis = initCheckBox(ChkPsikoMenangis, "ChkPsikoMenangis", "Menangis");
        addToForm(ChkPsikoMenangis, 375, 1270, 70, 23);
        ChkPsikoLain = initCheckBox(ChkPsikoLain, "ChkPsikoLain", "Lain-lain");
        addToForm(ChkPsikoLain, 450, 1270, 80, 23);

        jLabelJPolaSupport = initLabel(jLabelJPolaSupport, "jLabelJPolaSupport", "6. Support Sistem");
        addToForm(jLabelJPolaSupport, 40, 1295, 110, 23);
        ChkSupportKeluarga = initCheckBox(ChkSupportKeluarga, "ChkSupportKeluarga", "Keluarga");
        addToForm(ChkSupportKeluarga, 170, 1295, 80, 23);
        ChkSupportTeman = initCheckBox(ChkSupportTeman, "ChkSupportTeman", "Teman");
        addToForm(ChkSupportTeman, 255, 1295, 60, 23);
        ChkSupportLain = initCheckBox(ChkSupportLain, "ChkSupportLain", "Lain-lain");
        addToForm(ChkSupportLain, 320, 1295, 80, 23);

        ChkPenunjangLab = initCheckBox(ChkPenunjangLab, "ChkPenunjangLab", "Laboratorium");
        addToForm(ChkPenunjangLab, 170, 1345, 100, 23);
        ChkPenunjangRontgen = initCheckBox(ChkPenunjangRontgen, "ChkPenunjangRontgen", "Rontgen");
        addToForm(ChkPenunjangRontgen, 275, 1345, 80, 23);
        ChkPenunjangUSG = initCheckBox(ChkPenunjangUSG, "ChkPenunjangUSG", "USG");
        addToForm(ChkPenunjangUSG, 360, 1345, 60, 23);
        ChkPenunjangEKG = initCheckBox(ChkPenunjangEKG, "ChkPenunjangEKG", "EKG");
        addToForm(ChkPenunjangEKG, 425, 1345, 60, 23);
        ChkPenunjangLain = initCheckBox(ChkPenunjangLain, "ChkPenunjangLain", "Lain-lain");
        addToForm(ChkPenunjangLain, 490, 1345, 80, 23);

        jLabelMPenyuluhan = initLabel(jLabelMPenyuluhan, "jLabelMPenyuluhan", "M. Penyuluhan");
        addToForm(jLabelMPenyuluhan, 20, 1800, 120, 23);
        ChkPenyuluhanPeraturan = initCheckBox(ChkPenyuluhanPeraturan, "ChkPenyuluhanPeraturan",
                "Peraturan Rumah Sakit");
        addToForm(ChkPenyuluhanPeraturan, 40, 1825, 170, 23);
        ChkPenyuluhanHak = initCheckBox(ChkPenyuluhanHak, "ChkPenyuluhanHak", "Hak dan Kewajiban Klien");
        addToForm(ChkPenyuluhanHak, 220, 1825, 180, 23);
        ChkPenyuluhanPerawatan = initCheckBox(ChkPenyuluhanPerawatan, "ChkPenyuluhanPerawatan", "Perawatan diri di RS");
        addToForm(ChkPenyuluhanPerawatan, 410, 1825, 150, 23);
        jLabelMPengetahuan = initLabel(jLabelMPengetahuan, "jLabelMPengetahuan", "Pengetahuan tentang :");
        addToForm(jLabelMPengetahuan, 40, 1850, 130, 23);
        ChkPengetahuanASI = initCheckBox(ChkPengetahuanASI, "ChkPengetahuanASI", "ASI");
        addToForm(ChkPengetahuanASI, 180, 1850, 50, 23);
        ChkPengetahuanKB = initCheckBox(ChkPengetahuanKB, "ChkPengetahuanKB", "KB");
        addToForm(ChkPengetahuanKB, 240, 1850, 45, 23);
        ChkPengetahuanRawatTaliPusat = initCheckBox(ChkPengetahuanRawatTaliPusat, "ChkPengetahuanRawatTaliPusat",
                "Rawat Tali Pusat");
        addToForm(ChkPengetahuanRawatTaliPusat, 295, 1850, 130, 23);
        ChkPengetahuanPijatBayi = initCheckBox(ChkPengetahuanPijatBayi, "ChkPengetahuanPijatBayi", "Pijat Bayi");
        addToForm(ChkPengetahuanPijatBayi, 430, 1850, 80, 23);
        ChkPengetahuanLain = initCheckBox(ChkPengetahuanLain, "ChkPengetahuanLain", "Lain-lain");
        addToForm(ChkPengetahuanLain, 515, 1850, 80, 23);

        jLabelNDischarge = initLabel(jLabelNDischarge, "jLabelNDischarge", "N. Discharge Planning");
        addToForm(jLabelNDischarge, 20, 1880, 160, 23);
        ChkDPMinumObat = initCheckBox(ChkDPMinumObat, "ChkDPMinumObat", "Jadwal minum obat di rumah");
        addToForm(ChkDPMinumObat, 40, 1905, 200, 23);
        ChkDPKonsul = initCheckBox(ChkDPKonsul, "ChkDPKonsul", "Jadwal konsul");
        addToForm(ChkDPKonsul, 245, 1905, 110, 23);
        ChkDPImunisasi = initCheckBox(ChkDPImunisasi, "ChkDPImunisasi", "Jadwal Imunisasi Bayi");
        addToForm(ChkDPImunisasi, 360, 1905, 170, 23);
        ChkDPMobilisasi = initCheckBox(ChkDPMobilisasi, "ChkDPMobilisasi", "Mobilisasi di rumah");
        addToForm(ChkDPMobilisasi, 40, 1930, 140, 23);
        ChkDPDiet = initCheckBox(ChkDPDiet, "ChkDPDiet", "Diet di rumah");
        addToForm(ChkDPDiet, 185, 1930, 100, 23);
        ChkDPLain = initCheckBox(ChkDPLain, "ChkDPLain", "Lain-lain");
        addToForm(ChkDPLain, 290, 1930, 80, 23);
    }

    private widget.Label initLabel(widget.Label label, String name, String text) {
        if (label == null) {
            label = new widget.Label();
            label.setName(name);
            label.setText(text);
        }
        return label;
    }

    private widget.CekBox initCheckBox(widget.CekBox cek, String name, String text) {
        if (cek == null) {
            cek = new widget.CekBox();
            cek.setName(name);
            cek.setText(text);
        }
        return cek;
    }

    private widget.TextBox initTextBox(widget.TextBox box, String name) {
        if (box == null) {
            box = new widget.TextBox();
            box.setName(name);
        }
        return box;
    }

    private widget.TextArea initTextArea(widget.TextArea area, String name) {
        if (area == null) {
            area = new widget.TextArea();
            area.setName(name);
            area.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            area.setColumns(20);
            area.setRows(5);
        }
        return area;
    }

    private widget.ScrollPane initScrollPane(widget.ScrollPane pane, String name) {
        if (pane == null) {
            pane = new widget.ScrollPane();
            pane.setName(name);
            pane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        }
        return pane;
    }

    private void addToForm(java.awt.Component component, int x, int y, int width, int height) {
        if (component.getParent() != FormInput) {
            FormInput.add(component);
        }
        component.setBounds(x, y, width, height);
    }

    /**
     * Creates a section header panel with background color and border
     * 
     * @param title The section title text
     * @return Configured panel with header styling
     */
    private widget.PanelBiasa createSectionHeader(String title, int x, int y, int width) {
        widget.PanelBiasa panel = new widget.PanelBiasa();
        panel.setBackground(COLOR_SECTION_HEADER_BG);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(COLOR_SECTION_HEADER_BORDER, 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        widget.Label label = new widget.Label();
        label.setText(title);
        label.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
        label.setForeground(COLOR_SECTION_HEADER_TEXT);
        label.setName(title.replaceAll("[^a-zA-Z0-9]", "") + "Header");

        panel.add(label);
        panel.setBounds(x, y, width, 30);
        return panel;
    }

    /**
     * Creates a group panel with titled border for organizing related fields
     * 
     * @param title  The panel title
     * @param x      X position
     * @param y      Y position
     * @param width  Panel width
     * @param height Panel height
     * @return Configured group panel
     */
    private widget.PanelBiasa createGroupPanel(String title, int x, int y, int width, int height) {
        widget.PanelBiasa panel = new widget.PanelBiasa();
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(COLOR_PANEL_BORDER, 1),
                title,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11),
                new java.awt.Color(80, 80, 80)));
        panel.setBackground(COLOR_PANEL_BG);
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setName(title.replaceAll("[^a-zA-Z0-9]", "") + "Panel");
        return panel;
    }

    /**
     * Creates a horizontal separator line
     * 
     * @param y     Y position
     * @param width Separator width
     * @return Configured separator
     */
    private javax.swing.JSeparator createSeparator(int y, int width) {
        javax.swing.JSeparator separator = new javax.swing.JSeparator();
        separator.setBackground(COLOR_SEPARATOR);
        separator.setForeground(COLOR_SEPARATOR);
        separator.setBorder(javax.swing.BorderFactory.createLineBorder(COLOR_SEPARATOR));
        separator.setBounds(0, y, width, 1);
        return separator;
    }

    /**
     * Applies background color to panel based on section type
     * 
     * @param panel       The panel to style
     * @param sectionType Type: "subjektif", "objektif", or "assessment"
     */
    private void applySectionBackground(widget.PanelBiasa panel, String sectionType) {
        if (panel == null)
            return;

        switch (sectionType.toLowerCase()) {
            case "subjektif":
                panel.setBackground(COLOR_SUBJEKTIF_BG);
                break;
            case "objektif":
                panel.setBackground(COLOR_OBJEKTIF_BG);
                break;
            case "assessment":
                panel.setBackground(COLOR_ASSESSMENT_BG);
                break;
            default:
                panel.setBackground(COLOR_PANEL_BG);
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelSubjAlasanMasuk = new widget.Label();
        jLabelSubjKeluhan = new widget.Label();
        scrollPaneKeluhan = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabelSubjRiwayatKeluhan = new widget.Label();
        scrollPaneRiwayatKeluhan = new widget.ScrollPane();
        RiwayatKeluhan = new widget.TextArea();
        jLabelSubjRiwayatKesehatan = new widget.Label();
        ChkDM = new widget.CekBox();
        ChkHT = new widget.CekBox();
        ChkTBC = new widget.CekBox();
        ChkAsma = new widget.CekBox();
        ChkGinjal = new widget.CekBox();
        ChkHepar = new widget.CekBox();
        ChkLain = new widget.CekBox();
        RiwayatKesehatanLain = new widget.TextBox();
        jLabelSubjGPA = new widget.Label();
        Gravida = new widget.TextBox();
        jLabelSubjP = new widget.Label();
        Para = new widget.TextBox();
        jLabelSubjA = new widget.Label();
        Abortus = new widget.TextBox();
        jLabelSubjHPHT = new widget.Label();
        HPHT = new widget.TextBox();
        jLabelSubjHPL = new widget.Label();
        HPL = new widget.TextBox();
        jLabelSubjImunisasi = new widget.Label();
        Imunisasi = new widget.TextBox();
        jLabelSubjOpname = new widget.Label();
        ChkOpnamePernah = new widget.CekBox();
        ChkOpnameTidak = new widget.CekBox();
        jLabelSubjOpnameSakit = new widget.Label();
        OpnameSakit = new widget.TextBox();
        jLabelSubjOpnameRS = new widget.Label();
        OpnameRS = new widget.TextBox();
        jLabelSubjOperasi = new widget.Label();
        ChkOperasiPernah = new widget.CekBox();
        ChkOperasiTidak = new widget.CekBox();
        jLabelSubjOperasiKet = new widget.Label();
        OperasiKet = new widget.TextBox();
        jLabelSubjPengobatan = new widget.Label();
        ChkPengobatanPernah = new widget.CekBox();
        ChkPengobatanTidak = new widget.CekBox();
        jLabelSubjPengobatanKet = new widget.Label();
        PengobatanKet = new widget.TextBox();
        jLabelSubjAlergi = new widget.Label();
        ChkAlergiAda = new widget.CekBox();
        ChkAlergiTidak = new widget.CekBox();
        AlergiKet = new widget.TextBox();
        jLabelSubjMata = new widget.Label();
        ChkMataAnemia = new widget.CekBox();
        ChkMataIcterus = new widget.CekBox();
        ChkMataTAK = new widget.CekBox();
        ChkMataLain = new widget.CekBox();
        MataLain = new widget.TextBox();
        jLabelSubjThyroid = new widget.Label();
        ChkThyroidMembesar = new widget.CekBox();
        ChkThyroidTidak = new widget.CekBox();
        jLabelSubjKGB = new widget.Label();
        ChkKGBMembesar = new widget.CekBox();
        ChkKGBTidak = new widget.CekBox();
        jLabelSubjMammae = new widget.Label();
        ChkMammaeLembek = new widget.CekBox();
        ChkMammaeKeras = new widget.CekBox();
        ChkMammaePanas = new widget.CekBox();
        ChkMammaeKemerahan = new widget.CekBox();
        ChkMammaeBenjolan = new widget.CekBox();
        ChkMammaeLain = new widget.CekBox();
        MammaeLain = new widget.TextBox();
        jLabelSubjPuting = new widget.Label();
        ChkPutingMenonjol = new widget.CekBox();
        ChkPutingDatar = new widget.CekBox();
        jLabelSubjColostrum = new widget.Label();
        ChkColostrumLancar = new widget.CekBox();
        ChkColostrumBelum = new widget.CekBox();
        jLabel33 = new widget.Label();
        jLabel39 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel29 = new widget.Label();
        SPO = new widget.TextBox();
        jLabel35 = new widget.Label();
        Wajah = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Mulut = new widget.ComboBox();
        jLabel45 = new widget.Label();
        Payudara = new widget.ComboBox();
        jLabel46 = new widget.Label();
        FundusUteri = new widget.ComboBox();
        jLabel49 = new widget.Label();
        LukaOperasi = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Lokhea = new widget.ComboBox();
        jLabel51 = new widget.Label();
        LukaPerineum = new widget.ComboBox();
        jLabel52 = new widget.Label();
        FoleyCatheter = new widget.ComboBox();
        scrollPane12 = new widget.ScrollPane();
        Tujuan = new widget.TextArea();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel42 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel47 = new widget.Label();
        KontraksiUterus = new widget.ComboBox();
        jLabel53 = new widget.Label();
        KandungKemih = new widget.ComboBox();
        jLabel36 = new widget.Label();
        jLabelFGenitalia = new widget.Label();
        jLabelFGenitaliaLokia = new widget.Label();
        jLabelFGenitaliaJenis = new widget.Label();
        LokiaJenis = new widget.TextBox();
        jLabelFGenitaliaWarna = new widget.Label();
        LokiaWarna = new widget.TextBox();
        jLabelFGenitaliaBanyak = new widget.Label();
        LokiaBanyak = new widget.TextBox();
        jLabelFGenitaliaBau = new widget.Label();
        ChkLokiaBauPos = new widget.CekBox();
        ChkLokiaBauNeg = new widget.CekBox();
        jLabelFGenitaliaPerineum = new widget.Label();
        ChkPerineumUtuh = new widget.CekBox();
        ChkPerineumRuptur = new widget.CekBox();
        jLabelFGenitaliaVulva = new widget.Label();
        ChkVulvaVarises = new widget.CekBox();
        ChkVulvaEdema = new widget.CekBox();
        ChkVulvaCondyloma = new widget.CekBox();
        jLabelFGenitaliaBartholini = new widget.Label();
        ChkBartholiniMembesar = new widget.CekBox();
        ChkBartholiniTidak = new widget.CekBox();
        jLabelFGenitaliaVagina = new widget.Label();
        ChkVaginaCairan = new widget.CekBox();
        jLabelFGenitaliaVaginaBau = new widget.Label();
        ChkVaginaBauPos = new widget.CekBox();
        ChkVaginaBauNeg = new widget.CekBox();
        jLabelFGenitaliaVaginaWarna = new widget.Label();
        VaginaWarna = new widget.TextBox();
        ChkVaginaProlapsus = new widget.CekBox();
        ChkVaginaTAK = new widget.CekBox();
        jLabelGAnus = new widget.Label();
        ChkAnusHemoroid = new widget.CekBox();
        ChkAnusTAK = new widget.CekBox();
        jLabelHEkstremitas = new widget.Label();
        ChkEkstOedema = new widget.CekBox();
        ChkEkstVarises = new widget.CekBox();
        ChkEkstRefleks = new widget.CekBox();
        ChkEkstParalise = new widget.CekBox();
        EkstParaliseKet = new widget.TextBox();
        jLabelIHoman = new widget.Label();
        ChkHomanYa = new widget.CekBox();
        ChkHomanTidak = new widget.CekBox();
        jLabelJPola = new widget.Label();
        jLabelJPolaNutrisi = new widget.Label();
        ChkNutrisiMual = new widget.CekBox();
        ChkNutrisiMuntah = new widget.CekBox();
        ChkNutrisiTidakNafsu = new widget.CekBox();
        ChkNutrisiPuasa = new widget.CekBox();
        ChkNutrisiTAK = new widget.CekBox();
        jLabelJPolaEliminasi = new widget.Label();
        jLabelJPolaBab = new widget.Label();
        ChkBabKonstipasi = new widget.CekBox();
        ChkBabDiare = new widget.CekBox();
        ChkBabTAK = new widget.CekBox();
        jLabelJPolaBak = new widget.Label();
        ChkBakRetensi = new widget.CekBox();
        ChkBakKateter = new widget.CekBox();
        ChkBakOliguri = new widget.CekBox();
        ChkBakTAK = new widget.CekBox();
        jLabelJPolaIstirahat = new widget.Label();
        ChkIstirahatInsomnia = new widget.CekBox();
        ChkIstirahatTidakAda = new widget.CekBox();
        ChkIstirahatLain = new widget.CekBox();
        jLabelJPolaAktivitas = new widget.Label();
        ChkAktivitasMandiri = new widget.CekBox();
        ChkAktivitasBergantung = new widget.CekBox();
        ChkAktivitasPenuh = new widget.CekBox();
        jLabelJPolaPsikologis = new widget.Label();
        ChkPsikoGembira = new widget.CekBox();
        ChkPsikoTenang = new widget.CekBox();
        ChkPsikoCemas = new widget.CekBox();
        ChkPsikoMenangis = new widget.CekBox();
        ChkPsikoLain = new widget.CekBox();
        jLabelJPolaSupport = new widget.Label();
        ChkSupportKeluarga = new widget.CekBox();
        ChkSupportTeman = new widget.CekBox();
        ChkSupportLain = new widget.CekBox();
        ChkPenunjangLab = new widget.CekBox();
        ChkPenunjangRontgen = new widget.CekBox();
        ChkPenunjangUSG = new widget.CekBox();
        ChkPenunjangEKG = new widget.CekBox();
        ChkPenunjangLain = new widget.CekBox();
        jLabelMPenyuluhan = new widget.Label();
        ChkPenyuluhanPeraturan = new widget.CekBox();
        ChkPenyuluhanHak = new widget.CekBox();
        ChkPenyuluhanPerawatan = new widget.CekBox();
        jLabelMPengetahuan = new widget.Label();
        ChkPengetahuanASI = new widget.CekBox();
        ChkPengetahuanKB = new widget.CekBox();
        ChkPengetahuanRawatTaliPusat = new widget.CekBox();
        ChkPengetahuanPijatBayi = new widget.CekBox();
        ChkPengetahuanLain = new widget.CekBox();
        jLabelNDischarge = new widget.Label();
        ChkDPMinumObat = new widget.CekBox();
        ChkDPKonsul = new widget.CekBox();
        ChkDPImunisasi = new widget.CekBox();
        ChkDPMobilisasi = new widget.CekBox();
        ChkDPDiet = new widget.CekBox();
        ChkDPLain = new widget.CekBox();
        jLabel58 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel86 = new widget.Label();
        USG = new widget.TextBox();
        jLabel88 = new widget.Label();
        Leukosit = new widget.TextBox();
        jLabel90 = new widget.Label();
        Trombosit = new widget.TextBox();
        jLabel57 = new widget.Label();
        jLabel91 = new widget.Label();
        Glukosa = new widget.TextBox();
        Urine = new widget.TextBox();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        Protein = new widget.TextBox();
        jLabel95 = new widget.Label();
        Keton = new widget.TextBox();
        Ht = new widget.TextBox();
        jLabel87 = new widget.Label();
        Hb = new widget.TextBox();
        jLabel89 = new widget.Label();
        Rontgen = new widget.TextBox();
        jLabel97 = new widget.Label();
        label1 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Analisa = new widget.TextArea();
        scrollPane14 = new widget.ScrollPane();
        Penatalaksana = new widget.TextArea();
        scrollPane15 = new widget.ScrollPane();
        Masalah = new widget.TextArea();
        label2 = new widget.Label();
        label3 = new widget.Label();
        label15 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        btnPetugas = new widget.Button();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Medis");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Asesmen Khusus Kebidanan Pada Ibu Nifas ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
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

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
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
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(248, 248, 230));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(954, 1517));
        FormInput.setLayout(null);

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

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 60, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(140, 40, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
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
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(320, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(500, 410, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(530, 410, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(580, 410, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(410, 410, 45, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(380, 410, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(330, 440, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(280, 440, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(240, 440, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(600, 410, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(650, 410, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(-40, 440, 127, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(90, 440, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(700, 410, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(170, 440, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(460, 410, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(460, 440, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(410, 440, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(370, 440, 40, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(230, 410, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(300, 410, 60, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 1430, 880, 1);

        jLabelSubjAlasanMasuk.setText("A. Alasan Masuk");
        jLabelSubjAlasanMasuk.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelSubjAlasanMasuk.setName("jLabelSubjAlasanMasuk"); // NOI18N
        FormInput.add(jLabelSubjAlasanMasuk);
        jLabelSubjAlasanMasuk.setBounds(10, 80, 120, 23);

        jLabelSubjKeluhan.setText("Keluhan Utama :");
        jLabelSubjKeluhan.setName("jLabelSubjKeluhan"); // NOI18N
        FormInput.add(jLabelSubjKeluhan);
        jLabelSubjKeluhan.setBounds(10, 105, 110, 23);

        scrollPaneKeluhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPaneKeluhan.setName("scrollPaneKeluhan"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPaneKeluhan.setViewportView(KeluhanUtama);

        FormInput.add(scrollPaneKeluhan);
        scrollPaneKeluhan.setBounds(120, 105, 280, 45);

        jLabelSubjRiwayatKeluhan.setText("Riwayat Keluhan :");
        jLabelSubjRiwayatKeluhan.setName("jLabelSubjRiwayatKeluhan"); // NOI18N
        FormInput.add(jLabelSubjRiwayatKeluhan);
        jLabelSubjRiwayatKeluhan.setBounds(420, 105, 120, 23);

        scrollPaneRiwayatKeluhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPaneRiwayatKeluhan.setName("scrollPaneRiwayatKeluhan"); // NOI18N

        RiwayatKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatKeluhan.setColumns(20);
        RiwayatKeluhan.setRows(5);
        RiwayatKeluhan.setName("RiwayatKeluhan"); // NOI18N
        scrollPaneRiwayatKeluhan.setViewportView(RiwayatKeluhan);

        FormInput.add(scrollPaneRiwayatKeluhan);
        scrollPaneRiwayatKeluhan.setBounds(540, 105, 300, 45);

        jLabelSubjRiwayatKesehatan.setText("B. Riwayat Kesehatan :");
        jLabelSubjRiwayatKesehatan.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelSubjRiwayatKesehatan.setName("jLabelSubjRiwayatKesehatan"); // NOI18N
        FormInput.add(jLabelSubjRiwayatKesehatan);
        jLabelSubjRiwayatKesehatan.setBounds(30, 160, 130, 23);

        ChkDM.setText("DM");
        ChkDM.setName("ChkDM"); // NOI18N
        FormInput.add(ChkDM);
        ChkDM.setBounds(40, 180, 45, 23);

        ChkHT.setText("HT");
        ChkHT.setName("ChkHT"); // NOI18N
        FormInput.add(ChkHT);
        ChkHT.setBounds(90, 180, 45, 23);

        ChkTBC.setText("TBC");
        ChkTBC.setName("ChkTBC"); // NOI18N
        FormInput.add(ChkTBC);
        ChkTBC.setBounds(140, 180, 50, 23);

        ChkAsma.setText("Asma");
        ChkAsma.setName("ChkAsma"); // NOI18N
        FormInput.add(ChkAsma);
        ChkAsma.setBounds(200, 180, 55, 23);

        ChkGinjal.setText("Ginjal");
        ChkGinjal.setName("ChkGinjal"); // NOI18N
        FormInput.add(ChkGinjal);
        ChkGinjal.setBounds(260, 180, 60, 23);

        ChkHepar.setText("Hepar");
        ChkHepar.setName("ChkHepar"); // NOI18N
        FormInput.add(ChkHepar);
        ChkHepar.setBounds(330, 180, 60, 23);

        ChkLain.setText("Lain-lain");
        ChkLain.setName("ChkLain"); // NOI18N
        FormInput.add(ChkLain);
        ChkLain.setBounds(400, 180, 80, 23);

        RiwayatKesehatanLain.setName("RiwayatKesehatanLain"); // NOI18N
        FormInput.add(RiwayatKesehatanLain);
        RiwayatKesehatanLain.setBounds(490, 180, 260, 23);

        jLabelSubjGPA.setText("G :");
        jLabelSubjGPA.setName("jLabelSubjGPA"); // NOI18N
        FormInput.add(jLabelSubjGPA);
        jLabelSubjGPA.setBounds(10, 210, 50, 23);

        Gravida.setName("Gravida"); // NOI18N
        FormInput.add(Gravida);
        Gravida.setBounds(60, 210, 30, 23);

        jLabelSubjP.setText("P");
        jLabelSubjP.setName("jLabelSubjP"); // NOI18N
        FormInput.add(jLabelSubjP);
        jLabelSubjP.setBounds(100, 210, 10, 23);

        Para.setName("Para"); // NOI18N
        FormInput.add(Para);
        Para.setBounds(120, 210, 30, 23);

        jLabelSubjA.setText("A");
        jLabelSubjA.setName("jLabelSubjA"); // NOI18N
        FormInput.add(jLabelSubjA);
        jLabelSubjA.setBounds(160, 210, 10, 23);

        Abortus.setName("Abortus"); // NOI18N
        FormInput.add(Abortus);
        Abortus.setBounds(170, 210, 30, 23);

        jLabelSubjHPHT.setText("HPHT :");
        jLabelSubjHPHT.setName("jLabelSubjHPHT"); // NOI18N
        FormInput.add(jLabelSubjHPHT);
        jLabelSubjHPHT.setBounds(210, 210, 45, 23);

        HPHT.setName("HPHT"); // NOI18N
        FormInput.add(HPHT);
        HPHT.setBounds(260, 210, 80, 23);

        jLabelSubjHPL.setText("HPL :");
        jLabelSubjHPL.setName("jLabelSubjHPL"); // NOI18N
        FormInput.add(jLabelSubjHPL);
        jLabelSubjHPL.setBounds(340, 210, 35, 23);

        HPL.setName("HPL"); // NOI18N
        FormInput.add(HPL);
        HPL.setBounds(380, 210, 80, 23);

        jLabelSubjImunisasi.setText("Imunisasi :");
        jLabelSubjImunisasi.setName("jLabelSubjImunisasi"); // NOI18N
        FormInput.add(jLabelSubjImunisasi);
        jLabelSubjImunisasi.setBounds(460, 210, 70, 23);

        Imunisasi.setName("Imunisasi"); // NOI18N
        FormInput.add(Imunisasi);
        Imunisasi.setBounds(530, 210, 150, 23);

        jLabelSubjOpname.setText("Opname :");
        jLabelSubjOpname.setName("jLabelSubjOpname"); // NOI18N
        FormInput.add(jLabelSubjOpname);
        jLabelSubjOpname.setBounds(20, 240, 60, 23);

        ChkOpnamePernah.setText("Pernah");
        ChkOpnamePernah.setName("ChkOpnamePernah"); // NOI18N
        FormInput.add(ChkOpnamePernah);
        ChkOpnamePernah.setBounds(80, 240, 70, 23);

        ChkOpnameTidak.setText("Tidak");
        ChkOpnameTidak.setName("ChkOpnameTidak"); // NOI18N
        FormInput.add(ChkOpnameTidak);
        ChkOpnameTidak.setBounds(150, 240, 60, 23);

        jLabelSubjOpnameSakit.setText("Sakit :");
        jLabelSubjOpnameSakit.setName("jLabelSubjOpnameSakit"); // NOI18N
        FormInput.add(jLabelSubjOpnameSakit);
        jLabelSubjOpnameSakit.setBounds(200, 240, 50, 23);

        OpnameSakit.setName("OpnameSakit"); // NOI18N
        FormInput.add(OpnameSakit);
        OpnameSakit.setBounds(260, 240, 120, 23);

        jLabelSubjOpnameRS.setText("RS :");
        jLabelSubjOpnameRS.setName("jLabelSubjOpnameRS"); // NOI18N
        FormInput.add(jLabelSubjOpnameRS);
        jLabelSubjOpnameRS.setBounds(390, 240, 30, 23);

        OpnameRS.setName("OpnameRS"); // NOI18N
        FormInput.add(OpnameRS);
        OpnameRS.setBounds(430, 240, 120, 23);

        jLabelSubjOperasi.setText("Operasi :");
        jLabelSubjOperasi.setName("jLabelSubjOperasi"); // NOI18N
        FormInput.add(jLabelSubjOperasi);
        jLabelSubjOperasi.setBounds(20, 270, 60, 23);

        ChkOperasiPernah.setText("Pernah");
        ChkOperasiPernah.setName("ChkOperasiPernah"); // NOI18N
        FormInput.add(ChkOperasiPernah);
        ChkOperasiPernah.setBounds(80, 270, 70, 23);

        ChkOperasiTidak.setText("Tidak");
        ChkOperasiTidak.setName("ChkOperasiTidak"); // NOI18N
        FormInput.add(ChkOperasiTidak);
        ChkOperasiTidak.setBounds(150, 270, 60, 23);

        jLabelSubjOperasiKet.setText("Ket :");
        jLabelSubjOperasiKet.setName("jLabelSubjOperasiKet"); // NOI18N
        FormInput.add(jLabelSubjOperasiKet);
        jLabelSubjOperasiKet.setBounds(200, 270, 40, 23);

        OperasiKet.setName("OperasiKet"); // NOI18N
        FormInput.add(OperasiKet);
        OperasiKet.setBounds(260, 270, 170, 23);

        jLabelSubjPengobatan.setText("Pengobatan :");
        jLabelSubjPengobatan.setName("jLabelSubjPengobatan"); // NOI18N
        FormInput.add(jLabelSubjPengobatan);
        jLabelSubjPengobatan.setBounds(0, 300, 90, 23);

        ChkPengobatanPernah.setText("Pernah");
        ChkPengobatanPernah.setName("ChkPengobatanPernah"); // NOI18N
        FormInput.add(ChkPengobatanPernah);
        ChkPengobatanPernah.setBounds(80, 300, 70, 23);

        ChkPengobatanTidak.setText("Tidak");
        ChkPengobatanTidak.setName("ChkPengobatanTidak"); // NOI18N
        FormInput.add(ChkPengobatanTidak);
        ChkPengobatanTidak.setBounds(150, 300, 60, 23);

        jLabelSubjPengobatanKet.setText("Ket :");
        jLabelSubjPengobatanKet.setName("jLabelSubjPengobatanKet"); // NOI18N
        FormInput.add(jLabelSubjPengobatanKet);
        jLabelSubjPengobatanKet.setBounds(200, 300, 40, 23);

        PengobatanKet.setName("PengobatanKet"); // NOI18N
        FormInput.add(PengobatanKet);
        PengobatanKet.setBounds(260, 300, 170, 23);

        jLabelSubjAlergi.setText("C. Riwayat Alergi :");
        jLabelSubjAlergi.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelSubjAlergi.setName("jLabelSubjAlergi"); // NOI18N
        FormInput.add(jLabelSubjAlergi);
        jLabelSubjAlergi.setBounds(20, 340, 100, 23);

        ChkAlergiAda.setText("Ada");
        ChkAlergiAda.setName("ChkAlergiAda"); // NOI18N
        FormInput.add(ChkAlergiAda);
        ChkAlergiAda.setBounds(40, 360, 50, 23);

        ChkAlergiTidak.setText("Tidak");
        ChkAlergiTidak.setName("ChkAlergiTidak"); // NOI18N
        FormInput.add(ChkAlergiTidak);
        ChkAlergiTidak.setBounds(100, 360, 60, 23);

        AlergiKet.setName("AlergiKet"); // NOI18N
        FormInput.add(AlergiKet);
        AlergiKet.setBounds(170, 360, 190, 23);

        jLabelSubjMata.setText("Mata :");
        jLabelSubjMata.setName("jLabelSubjMata"); // NOI18N
        FormInput.add(jLabelSubjMata);
        jLabelSubjMata.setBounds(20, 520, 40, 23);

        ChkMataAnemia.setText("Anemia");
        ChkMataAnemia.setName("ChkMataAnemia"); // NOI18N
        FormInput.add(ChkMataAnemia);
        ChkMataAnemia.setBounds(70, 520, 70, 23);

        ChkMataIcterus.setText("Icterus");
        ChkMataIcterus.setName("ChkMataIcterus"); // NOI18N
        FormInput.add(ChkMataIcterus);
        ChkMataIcterus.setBounds(140, 520, 70, 23);

        ChkMataTAK.setText("t.a.k");
        ChkMataTAK.setName("ChkMataTAK"); // NOI18N
        FormInput.add(ChkMataTAK);
        ChkMataTAK.setBounds(230, 520, 60, 23);

        ChkMataLain.setText("Lain");
        ChkMataLain.setName("ChkMataLain"); // NOI18N
        FormInput.add(ChkMataLain);
        ChkMataLain.setBounds(300, 520, 50, 23);

        MataLain.setName("MataLain"); // NOI18N
        FormInput.add(MataLain);
        MataLain.setBounds(360, 520, 90, 23);

        jLabelSubjThyroid.setText("Thyroid :");
        jLabelSubjThyroid.setName("jLabelSubjThyroid"); // NOI18N
        FormInput.add(jLabelSubjThyroid);
        jLabelSubjThyroid.setBounds(10, 550, 60, 23);

        ChkThyroidMembesar.setText("Membesar");
        ChkThyroidMembesar.setName("ChkThyroidMembesar"); // NOI18N
        FormInput.add(ChkThyroidMembesar);
        ChkThyroidMembesar.setBounds(80, 550, 80, 23);

        ChkThyroidTidak.setText("Tidak");
        ChkThyroidTidak.setName("ChkThyroidTidak"); // NOI18N
        FormInput.add(ChkThyroidTidak);
        ChkThyroidTidak.setBounds(160, 550, 60, 23);

        jLabelSubjKGB.setText("KGB :");
        jLabelSubjKGB.setName("jLabelSubjKGB"); // NOI18N
        FormInput.add(jLabelSubjKGB);
        jLabelSubjKGB.setBounds(220, 550, 40, 23);

        ChkKGBMembesar.setText("Membesar");
        ChkKGBMembesar.setName("ChkKGBMembesar"); // NOI18N
        FormInput.add(ChkKGBMembesar);
        ChkKGBMembesar.setBounds(280, 550, 80, 23);

        ChkKGBTidak.setText("Tidak");
        ChkKGBTidak.setName("ChkKGBTidak"); // NOI18N
        FormInput.add(ChkKGBTidak);
        ChkKGBTidak.setBounds(380, 550, 60, 23);

        jLabelSubjMammae.setText("Mammae :");
        jLabelSubjMammae.setName("jLabelSubjMammae"); // NOI18N
        FormInput.add(jLabelSubjMammae);
        jLabelSubjMammae.setBounds(10, 570, 60, 23);

        ChkMammaeLembek.setText("Lembek");
        ChkMammaeLembek.setName("ChkMammaeLembek"); // NOI18N
        FormInput.add(ChkMammaeLembek);
        ChkMammaeLembek.setBounds(80, 570, 70, 23);

        ChkMammaeKeras.setText("Keras");
        ChkMammaeKeras.setName("ChkMammaeKeras"); // NOI18N
        FormInput.add(ChkMammaeKeras);
        ChkMammaeKeras.setBounds(160, 570, 60, 23);

        ChkMammaePanas.setText("Panas");
        ChkMammaePanas.setName("ChkMammaePanas"); // NOI18N
        FormInput.add(ChkMammaePanas);
        ChkMammaePanas.setBounds(220, 570, 60, 23);

        ChkMammaeKemerahan.setText("Kemerahan");
        ChkMammaeKemerahan.setName("ChkMammaeKemerahan"); // NOI18N
        FormInput.add(ChkMammaeKemerahan);
        ChkMammaeKemerahan.setBounds(280, 570, 90, 23);

        ChkMammaeBenjolan.setText("Benjolan");
        ChkMammaeBenjolan.setName("ChkMammaeBenjolan"); // NOI18N
        FormInput.add(ChkMammaeBenjolan);
        ChkMammaeBenjolan.setBounds(380, 570, 80, 23);

        ChkMammaeLain.setText("Lain");
        ChkMammaeLain.setName("ChkMammaeLain"); // NOI18N
        FormInput.add(ChkMammaeLain);
        ChkMammaeLain.setBounds(460, 570, 50, 23);

        MammaeLain.setName("MammaeLain"); // NOI18N
        FormInput.add(MammaeLain);
        MammaeLain.setBounds(520, 570, 120, 23);

        jLabelSubjPuting.setText("Puting :");
        jLabelSubjPuting.setName("jLabelSubjPuting"); // NOI18N
        FormInput.add(jLabelSubjPuting);
        jLabelSubjPuting.setBounds(10, 590, 60, 23);

        ChkPutingMenonjol.setText("Menonjol");
        ChkPutingMenonjol.setName("ChkPutingMenonjol"); // NOI18N
        FormInput.add(ChkPutingMenonjol);
        ChkPutingMenonjol.setBounds(80, 590, 70, 23);

        ChkPutingDatar.setText("Datar");
        ChkPutingDatar.setName("ChkPutingDatar"); // NOI18N
        FormInput.add(ChkPutingDatar);
        ChkPutingDatar.setBounds(160, 590, 70, 23);

        jLabelSubjColostrum.setText("Colostrum/Asi :");
        jLabelSubjColostrum.setName("jLabelSubjColostrum"); // NOI18N
        FormInput.add(jLabelSubjColostrum);
        jLabelSubjColostrum.setBounds(70, 610, 80, 23);

        ChkColostrumLancar.setText("Lancar");
        ChkColostrumLancar.setName("ChkColostrumLancar"); // NOI18N
        FormInput.add(ChkColostrumLancar);
        ChkColostrumLancar.setBounds(160, 610, 60, 23);

        ChkColostrumBelum.setText("Belum Ada");
        ChkColostrumBelum.setName("ChkColostrumBelum"); // NOI18N
        FormInput.add(ChkColostrumBelum);
        ChkColostrumBelum.setBounds(220, 610, 90, 23);

        jLabel33.setText("Riwayat Persalinan Sekarang :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(500, 2160, 150, 23);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(20, 410, 70, 23);

        Ekstremitas.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tungkai Edema", "Refleks Patella" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(600, 1800, 130, 23);

        jLabel40.setText("Wajah :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(220, 1650, 127, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Compos Mentis", "Apatis", "Delirium", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(90, 410, 130, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(500, 440, 40, 23);

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });
        FormInput.add(SPO);
        SPO.setBounds(540, 440, 45, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(590, 440, 30, 23);

        Wajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oedema", "Pucat", "Normal/Simetris" }));
        Wajah.setName("Wajah"); // NOI18N
        Wajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WajahKeyPressed(evt);
            }
        });
        FormInput.add(Wajah);
        Wajah.setBounds(360, 1650, 128, 23);

        jLabel44.setText("Mulut :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(230, 1710, 127, 23);

        Mulut.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Bibir Pucat", "Bibir Kemerahan", "Bibir Lembab", "Bibir Kering", "Caries Gigi" }));
        Mulut.setName("Mulut"); // NOI18N
        Mulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MulutKeyPressed(evt);
            }
        });
        FormInput.add(Mulut);
        Mulut.setBounds(360, 1710, 128, 23);

        jLabel45.setText("Payudara :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(230, 1740, 127, 23);

        Payudara.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Pengeluaran ASI", "Putting datar/tenggelam", "Puting susu menonjol  (Kiri / Kanan)" }));
        Payudara.setName("Payudara"); // NOI18N
        Payudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PayudaraKeyPressed(evt);
            }
        });
        FormInput.add(Payudara);
        Payudara.setBounds(360, 1740, 128, 23);

        jLabel46.setText("Fundus Uteri :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(230, 1770, 127, 23);

        FundusUteri.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Setinggi Pusat", "1 jari bwh pusat", "1/2 simfisis – pusat" }));
        FundusUteri.setName("FundusUteri"); // NOI18N
        FundusUteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FundusUteriKeyPressed(evt);
            }
        });
        FormInput.add(FundusUteri);
        FundusUteri.setBounds(360, 1770, 128, 23);

        jLabel49.setText("Luka Operasi :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(500, 1680, 95, 23);

        LukaOperasi.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Redness/merah", "Edema", "Discharge/nanah", "Tidak Ada Masalah" }));
        LukaOperasi.setName("LukaOperasi"); // NOI18N
        LukaOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LukaOperasiKeyPressed(evt);
            }
        });
        FormInput.add(LukaOperasi);
        LukaOperasi.setBounds(600, 1680, 128, 23);

        jLabel50.setText("Lokhea :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(500, 1710, 95, 23);

        Lokhea.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Rubra", "Sanguilenta", "Serosa", "Air-air" }));
        Lokhea.setName("Lokhea"); // NOI18N
        Lokhea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokheaKeyPressed(evt);
            }
        });
        FormInput.add(Lokhea);
        Lokhea.setBounds(600, 1710, 128, 23);

        jLabel51.setText("Luka Perineum :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(500, 1740, 95, 23);

        LukaPerineum.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Redness/merah", "Edema", "Discharge/nanah", "Tidak Ada Masalah" }));
        LukaPerineum.setName("LukaPerineum"); // NOI18N
        LukaPerineum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LukaPerineumKeyPressed(evt);
            }
        });
        FormInput.add(LukaPerineum);
        LukaPerineum.setBounds(600, 1740, 128, 23);

        jLabel52.setText("Ekstremitas :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(500, 1800, 95, 23);

        FoleyCatheter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FoleyCatheter.setName("FoleyCatheter"); // NOI18N
        FoleyCatheter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FoleyCatheterKeyPressed(evt);
            }
        });
        FormInput.add(FoleyCatheter);
        FoleyCatheter.setBounds(600, 1770, 128, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Tujuan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tujuan.setColumns(20);
        Tujuan.setRows(3);
        Tujuan.setName("Tujuan"); // NOI18N
        Tujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Tujuan);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(460, 1850, 370, 120);

        label11.setText("Tgl :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(692, 40, 30, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-01-2026 23:02:48" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(730, 40, 130, 23);

        jLabel42.setText("Mata :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(230, 1680, 127, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Conjuctiva Merah", "Conjuctiva Pucat", "Sklera Ikteric", "Pandangan Kabur" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(360, 1680, 128, 23);

        jLabel47.setText("Kontraksi Uterus :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(230, 1800, 127, 23);

        KontraksiUterus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keras", "Sedang", "Lembek" }));
        KontraksiUterus.setName("KontraksiUterus"); // NOI18N
        KontraksiUterus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraksiUterusKeyPressed(evt);
            }
        });
        FormInput.add(KontraksiUterus);
        KontraksiUterus.setBounds(360, 1800, 128, 23);

        jLabel53.setText("Kandung Kemih :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(500, 1650, 95, 23);

        KandungKemih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Penuh", "Kosong" }));
        KandungKemih.setName("KandungKemih"); // NOI18N
        KandungKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KandungKemihKeyPressed(evt);
            }
        });
        FormInput.add(KandungKemih);
        KandungKemih.setBounds(600, 1650, 128, 23);

        jLabel36.setText("Pukul:");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(450, 2160, 30, 23);

        jLabelFGenitalia.setText("F. Genitalia");
        jLabelFGenitalia.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelFGenitalia.setName("jLabelFGenitalia"); // NOI18N
        FormInput.add(jLabelFGenitalia);
        jLabelFGenitalia.setBounds(0, 640, 90, 23);

        jLabelFGenitaliaLokia.setText("Lokia :");
        jLabelFGenitaliaLokia.setName("jLabelFGenitaliaLokia"); // NOI18N
        FormInput.add(jLabelFGenitaliaLokia);
        jLabelFGenitaliaLokia.setBounds(30, 670, 45, 23);

        jLabelFGenitaliaJenis.setText("Jenis");
        jLabelFGenitaliaJenis.setName("jLabelFGenitaliaJenis"); // NOI18N
        FormInput.add(jLabelFGenitaliaJenis);
        jLabelFGenitaliaJenis.setBounds(80, 670, 40, 23);

        LokiaJenis.setName("LokiaJenis"); // NOI18N
        FormInput.add(LokiaJenis);
        LokiaJenis.setBounds(150, 670, 80, 23);

        jLabelFGenitaliaWarna.setText("Warna");
        jLabelFGenitaliaWarna.setName("jLabelFGenitaliaWarna"); // NOI18N
        FormInput.add(jLabelFGenitaliaWarna);
        jLabelFGenitaliaWarna.setBounds(240, 670, 45, 23);

        LokiaWarna.setName("LokiaWarna"); // NOI18N
        FormInput.add(LokiaWarna);
        LokiaWarna.setBounds(290, 670, 80, 23);

        jLabelFGenitaliaBanyak.setText("Banyak");
        jLabelFGenitaliaBanyak.setName("jLabelFGenitaliaBanyak"); // NOI18N
        FormInput.add(jLabelFGenitaliaBanyak);
        jLabelFGenitaliaBanyak.setBounds(380, 670, 50, 23);

        LokiaBanyak.setName("LokiaBanyak"); // NOI18N
        FormInput.add(LokiaBanyak);
        LokiaBanyak.setBounds(440, 670, 60, 23);

        jLabelFGenitaliaBau.setText("Bau :");
        jLabelFGenitaliaBau.setName("jLabelFGenitaliaBau"); // NOI18N
        FormInput.add(jLabelFGenitaliaBau);
        jLabelFGenitaliaBau.setBounds(530, 670, 35, 23);

        ChkLokiaBauPos.setText("Positif");
        ChkLokiaBauPos.setName("ChkLokiaBauPos"); // NOI18N
        FormInput.add(ChkLokiaBauPos);
        ChkLokiaBauPos.setBounds(570, 670, 65, 23);

        ChkLokiaBauNeg.setText("Negatif");
        ChkLokiaBauNeg.setName("ChkLokiaBauNeg"); // NOI18N
        FormInput.add(ChkLokiaBauNeg);
        ChkLokiaBauNeg.setBounds(640, 670, 70, 23);

        jLabelFGenitaliaPerineum.setText("Perineum :");
        jLabelFGenitaliaPerineum.setName("jLabelFGenitaliaPerineum"); // NOI18N
        FormInput.add(jLabelFGenitaliaPerineum);
        jLabelFGenitaliaPerineum.setBounds(10, 700, 60, 23);

        ChkPerineumUtuh.setText("Utuh");
        ChkPerineumUtuh.setName("ChkPerineumUtuh"); // NOI18N
        FormInput.add(ChkPerineumUtuh);
        ChkPerineumUtuh.setBounds(80, 700, 55, 23);

        ChkPerineumRuptur.setText("Ruptur");
        ChkPerineumRuptur.setName("ChkPerineumRuptur"); // NOI18N
        FormInput.add(ChkPerineumRuptur);
        ChkPerineumRuptur.setBounds(150, 700, 70, 23);

        jLabelFGenitaliaVulva.setText("Vulva :");
        jLabelFGenitaliaVulva.setName("jLabelFGenitaliaVulva"); // NOI18N
        FormInput.add(jLabelFGenitaliaVulva);
        jLabelFGenitaliaVulva.setBounds(20, 730, 50, 23);

        ChkVulvaVarises.setText("Varises");
        ChkVulvaVarises.setName("ChkVulvaVarises"); // NOI18N
        FormInput.add(ChkVulvaVarises);
        ChkVulvaVarises.setBounds(80, 730, 70, 23);

        ChkVulvaEdema.setText("Edema");
        ChkVulvaEdema.setName("ChkVulvaEdema"); // NOI18N
        FormInput.add(ChkVulvaEdema);
        ChkVulvaEdema.setBounds(150, 730, 65, 23);

        ChkVulvaCondyloma.setText("Condyloma");
        ChkVulvaCondyloma.setName("ChkVulvaCondyloma"); // NOI18N
        FormInput.add(ChkVulvaCondyloma);
        ChkVulvaCondyloma.setBounds(220, 730, 90, 23);

        jLabelFGenitaliaBartholini.setText("Kelenjar Bartholini :");
        jLabelFGenitaliaBartholini.setName("jLabelFGenitaliaBartholini"); // NOI18N
        FormInput.add(jLabelFGenitaliaBartholini);
        jLabelFGenitaliaBartholini.setBounds(10, 760, 100, 23);

        ChkBartholiniMembesar.setText("Membesar");
        ChkBartholiniMembesar.setName("ChkBartholiniMembesar"); // NOI18N
        FormInput.add(ChkBartholiniMembesar);
        ChkBartholiniMembesar.setBounds(150, 760, 90, 23);

        ChkBartholiniTidak.setText("Tidak");
        ChkBartholiniTidak.setName("ChkBartholiniTidak"); // NOI18N
        FormInput.add(ChkBartholiniTidak);
        ChkBartholiniTidak.setBounds(250, 760, 60, 23);

        jLabelFGenitaliaVagina.setText("Vagina :");
        jLabelFGenitaliaVagina.setName("jLabelFGenitaliaVagina"); // NOI18N
        FormInput.add(jLabelFGenitaliaVagina);
        jLabelFGenitaliaVagina.setBounds(20, 790, 55, 23);

        ChkVaginaCairan.setText("Cairan");
        ChkVaginaCairan.setName("ChkVaginaCairan"); // NOI18N
        FormInput.add(ChkVaginaCairan);
        ChkVaginaCairan.setBounds(80, 790, 65, 23);

        jLabelFGenitaliaVaginaBau.setText("Bau");
        jLabelFGenitaliaVaginaBau.setName("jLabelFGenitaliaVaginaBau"); // NOI18N
        FormInput.add(jLabelFGenitaliaVaginaBau);
        jLabelFGenitaliaVaginaBau.setBounds(140, 790, 35, 23);

        ChkVaginaBauPos.setText("+");
        ChkVaginaBauPos.setName("ChkVaginaBauPos"); // NOI18N
        FormInput.add(ChkVaginaBauPos);
        ChkVaginaBauPos.setBounds(180, 790, 35, 23);

        ChkVaginaBauNeg.setText("-");
        ChkVaginaBauNeg.setName("ChkVaginaBauNeg"); // NOI18N
        FormInput.add(ChkVaginaBauNeg);
        ChkVaginaBauNeg.setBounds(220, 790, 35, 23);

        jLabelFGenitaliaVaginaWarna.setText("Warna");
        jLabelFGenitaliaVaginaWarna.setName("jLabelFGenitaliaVaginaWarna"); // NOI18N
        FormInput.add(jLabelFGenitaliaVaginaWarna);
        jLabelFGenitaliaVaginaWarna.setBounds(260, 790, 45, 23);

        VaginaWarna.setName("VaginaWarna"); // NOI18N
        FormInput.add(VaginaWarna);
        VaginaWarna.setBounds(310, 790, 80, 23);

        ChkVaginaProlapsus.setText("Prolapsus");
        ChkVaginaProlapsus.setName("ChkVaginaProlapsus"); // NOI18N
        FormInput.add(ChkVaginaProlapsus);
        ChkVaginaProlapsus.setBounds(400, 790, 80, 23);

        ChkVaginaTAK.setText("t.a.k");
        ChkVaginaTAK.setName("ChkVaginaTAK"); // NOI18N
        FormInput.add(ChkVaginaTAK);
        ChkVaginaTAK.setBounds(490, 790, 60, 23);

        jLabelGAnus.setText("G. Anus");
        jLabelGAnus.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelGAnus.setName("jLabelGAnus"); // NOI18N
        FormInput.add(jLabelGAnus);
        jLabelGAnus.setBounds(10, 830, 60, 23);

        ChkAnusHemoroid.setText("Haemoroid");
        ChkAnusHemoroid.setName("ChkAnusHemoroid"); // NOI18N
        FormInput.add(ChkAnusHemoroid);
        ChkAnusHemoroid.setBounds(80, 830, 90, 23);

        ChkAnusTAK.setText("t.a.k");
        ChkAnusTAK.setName("ChkAnusTAK"); // NOI18N
        FormInput.add(ChkAnusTAK);
        ChkAnusTAK.setBounds(180, 830, 60, 23);

        jLabelHEkstremitas.setText("H. Ekstremitas");
        jLabelHEkstremitas.setName("jLabelHEkstremitas"); // NOI18N
        FormInput.add(jLabelHEkstremitas);
        jLabelHEkstremitas.setBounds(0, 850, 95, 23);

        ChkEkstOedema.setText("Oedema");
        ChkEkstOedema.setName("ChkEkstOedema"); // NOI18N
        FormInput.add(ChkEkstOedema);
        ChkEkstOedema.setBounds(110, 850, 75, 23);

        ChkEkstVarises.setText("Varises");
        ChkEkstVarises.setName("ChkEkstVarises"); // NOI18N
        FormInput.add(ChkEkstVarises);
        ChkEkstVarises.setBounds(190, 850, 70, 23);

        ChkEkstRefleks.setText("Refleks +/-");
        ChkEkstRefleks.setName("ChkEkstRefleks"); // NOI18N
        FormInput.add(ChkEkstRefleks);
        ChkEkstRefleks.setBounds(270, 850, 90, 23);

        ChkEkstParalise.setText("Paralise di");
        ChkEkstParalise.setName("ChkEkstParalise"); // NOI18N
        FormInput.add(ChkEkstParalise);
        ChkEkstParalise.setBounds(370, 850, 80, 23);

        EkstParaliseKet.setName("EkstParaliseKet"); // NOI18N
        FormInput.add(EkstParaliseKet);
        EkstParaliseKet.setBounds(450, 850, 90, 23);

        jLabelIHoman.setText("I. Homan Sign");
        jLabelIHoman.setName("jLabelIHoman"); // NOI18N
        FormInput.add(jLabelIHoman);
        jLabelIHoman.setBounds(10, 870, 90, 23);

        ChkHomanYa.setText("Ya");
        ChkHomanYa.setName("ChkHomanYa"); // NOI18N
        FormInput.add(ChkHomanYa);
        ChkHomanYa.setBounds(110, 870, 40, 23);

        ChkHomanTidak.setText("Tidak");
        ChkHomanTidak.setName("ChkHomanTidak"); // NOI18N
        FormInput.add(ChkHomanTidak);
        ChkHomanTidak.setBounds(160, 870, 60, 23);

        jLabelJPola.setText("J. Pola Kebiasaan");
        jLabelJPola.setName("jLabelJPola"); // NOI18N
        FormInput.add(jLabelJPola);
        jLabelJPola.setBounds(0, 890, 110, 23);

        jLabelJPolaNutrisi.setText("1. Nutrisi");
        jLabelJPolaNutrisi.setName("jLabelJPolaNutrisi"); // NOI18N
        FormInput.add(jLabelJPolaNutrisi);
        jLabelJPolaNutrisi.setBounds(0, 910, 70, 23);

        ChkNutrisiMual.setText("Mual");
        ChkNutrisiMual.setName("ChkNutrisiMual"); // NOI18N
        FormInput.add(ChkNutrisiMual);
        ChkNutrisiMual.setBounds(80, 910, 55, 23);

        ChkNutrisiMuntah.setText("Muntah");
        ChkNutrisiMuntah.setName("ChkNutrisiMuntah"); // NOI18N
        FormInput.add(ChkNutrisiMuntah);
        ChkNutrisiMuntah.setBounds(140, 910, 65, 23);

        ChkNutrisiTidakNafsu.setText("Tidak Nafsu Makan");
        ChkNutrisiTidakNafsu.setName("ChkNutrisiTidakNafsu"); // NOI18N
        FormInput.add(ChkNutrisiTidakNafsu);
        ChkNutrisiTidakNafsu.setBounds(210, 910, 140, 23);

        ChkNutrisiPuasa.setText("Puasa");
        ChkNutrisiPuasa.setName("ChkNutrisiPuasa"); // NOI18N
        FormInput.add(ChkNutrisiPuasa);
        ChkNutrisiPuasa.setBounds(360, 910, 60, 23);

        ChkNutrisiTAK.setText("t.a.k");
        ChkNutrisiTAK.setName("ChkNutrisiTAK"); // NOI18N
        FormInput.add(ChkNutrisiTAK);
        ChkNutrisiTAK.setBounds(420, 910, 60, 23);

        jLabelJPolaEliminasi.setText("2. Eliminasi");
        jLabelJPolaEliminasi.setName("jLabelJPolaEliminasi"); // NOI18N
        FormInput.add(jLabelJPolaEliminasi);
        jLabelJPolaEliminasi.setBounds(0, 930, 80, 23);

        jLabelJPolaBab.setText("BAB :");
        jLabelJPolaBab.setName("jLabelJPolaBab"); // NOI18N
        FormInput.add(jLabelJPolaBab);
        jLabelJPolaBab.setBounds(80, 930, 40, 23);

        ChkBabKonstipasi.setText("Konstipasi");
        ChkBabKonstipasi.setName("ChkBabKonstipasi"); // NOI18N
        FormInput.add(ChkBabKonstipasi);
        ChkBabKonstipasi.setBounds(130, 930, 80, 23);

        ChkBabDiare.setText("Diare");
        ChkBabDiare.setName("ChkBabDiare"); // NOI18N
        FormInput.add(ChkBabDiare);
        ChkBabDiare.setBounds(210, 930, 55, 23);

        ChkBabTAK.setText("t.a.k");
        ChkBabTAK.setName("ChkBabTAK"); // NOI18N
        FormInput.add(ChkBabTAK);
        ChkBabTAK.setBounds(270, 930, 60, 23);

        jLabelJPolaBak.setText("BAK :");
        jLabelJPolaBak.setName("jLabelJPolaBak"); // NOI18N
        FormInput.add(jLabelJPolaBak);
        jLabelJPolaBak.setBounds(340, 930, 40, 23);

        ChkBakRetensi.setText("Retensi Urine");
        ChkBakRetensi.setName("ChkBakRetensi"); // NOI18N
        FormInput.add(ChkBakRetensi);
        ChkBakRetensi.setBounds(390, 930, 100, 23);

        ChkBakKateter.setText("Kateter");
        ChkBakKateter.setName("ChkBakKateter"); // NOI18N
        FormInput.add(ChkBakKateter);
        ChkBakKateter.setBounds(490, 930, 70, 23);

        ChkBakOliguri.setText("Oliguri");
        ChkBakOliguri.setName("ChkBakOliguri"); // NOI18N
        FormInput.add(ChkBakOliguri);
        ChkBakOliguri.setBounds(570, 930, 70, 23);

        ChkBakTAK.setText("t.a.k");
        ChkBakTAK.setName("ChkBakTAK"); // NOI18N
        FormInput.add(ChkBakTAK);
        ChkBakTAK.setBounds(640, 930, 60, 23);

        jLabelJPolaIstirahat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelJPolaIstirahat.setText("3. Istirahat Tidur");
        jLabelJPolaIstirahat.setName("jLabelJPolaIstirahat"); // NOI18N
        FormInput.add(jLabelJPolaIstirahat);
        jLabelJPolaIstirahat.setBounds(30, 950, 80, 23);

        ChkIstirahatInsomnia.setText("Insomnia");
        ChkIstirahatInsomnia.setName("ChkIstirahatInsomnia"); // NOI18N
        FormInput.add(ChkIstirahatInsomnia);
        ChkIstirahatInsomnia.setBounds(130, 950, 70, 23);

        ChkIstirahatTidakAda.setText("Tidak ada keluhan");
        ChkIstirahatTidakAda.setName("ChkIstirahatTidakAda"); // NOI18N
        FormInput.add(ChkIstirahatTidakAda);
        ChkIstirahatTidakAda.setBounds(210, 950, 130, 23);

        ChkIstirahatLain.setText("Lain-lain");
        ChkIstirahatLain.setName("ChkIstirahatLain"); // NOI18N
        FormInput.add(ChkIstirahatLain);
        ChkIstirahatLain.setBounds(340, 950, 80, 23);

        jLabelJPolaAktivitas.setText("4. Aktivitas");
        jLabelJPolaAktivitas.setName("jLabelJPolaAktivitas"); // NOI18N
        FormInput.add(jLabelJPolaAktivitas);
        jLabelJPolaAktivitas.setBounds(0, 970, 80, 23);

        ChkAktivitasMandiri.setText("Mandiri");
        ChkAktivitasMandiri.setName("ChkAktivitasMandiri"); // NOI18N
        FormInput.add(ChkAktivitasMandiri);
        ChkAktivitasMandiri.setBounds(130, 970, 70, 23);

        ChkAktivitasBergantung.setText("Bergantung sebagian");
        ChkAktivitasBergantung.setName("ChkAktivitasBergantung"); // NOI18N
        FormInput.add(ChkAktivitasBergantung);
        ChkAktivitasBergantung.setBounds(210, 970, 130, 23);

        ChkAktivitasPenuh.setText("Penuh");
        ChkAktivitasPenuh.setName("ChkAktivitasPenuh"); // NOI18N
        FormInput.add(ChkAktivitasPenuh);
        ChkAktivitasPenuh.setBounds(340, 970, 60, 23);

        jLabelJPolaPsikologis.setText("5. Psikologis");
        jLabelJPolaPsikologis.setName("jLabelJPolaPsikologis"); // NOI18N
        FormInput.add(jLabelJPolaPsikologis);
        jLabelJPolaPsikologis.setBounds(0, 990, 90, 23);

        ChkPsikoGembira.setText("Gembira");
        ChkPsikoGembira.setName("ChkPsikoGembira"); // NOI18N
        FormInput.add(ChkPsikoGembira);
        ChkPsikoGembira.setBounds(130, 990, 70, 23);

        ChkPsikoTenang.setText("Tenang");
        ChkPsikoTenang.setName("ChkPsikoTenang"); // NOI18N
        FormInput.add(ChkPsikoTenang);
        ChkPsikoTenang.setBounds(210, 990, 60, 23);

        ChkPsikoCemas.setText("Cemas");
        ChkPsikoCemas.setName("ChkPsikoCemas"); // NOI18N
        FormInput.add(ChkPsikoCemas);
        ChkPsikoCemas.setBounds(280, 990, 60, 23);

        ChkPsikoMenangis.setText("Menangis");
        ChkPsikoMenangis.setName("ChkPsikoMenangis"); // NOI18N
        FormInput.add(ChkPsikoMenangis);
        ChkPsikoMenangis.setBounds(350, 990, 70, 23);

        ChkPsikoLain.setText("Lain-lain");
        ChkPsikoLain.setName("ChkPsikoLain"); // NOI18N
        FormInput.add(ChkPsikoLain);
        ChkPsikoLain.setBounds(430, 990, 80, 23);

        jLabelJPolaSupport.setText("6. Support Sistem");
        jLabelJPolaSupport.setName("jLabelJPolaSupport"); // NOI18N
        FormInput.add(jLabelJPolaSupport);
        jLabelJPolaSupport.setBounds(0, 1010, 110, 23);

        ChkSupportKeluarga.setText("Keluarga");
        ChkSupportKeluarga.setName("ChkSupportKeluarga"); // NOI18N
        FormInput.add(ChkSupportKeluarga);
        ChkSupportKeluarga.setBounds(130, 1010, 80, 23);

        ChkSupportTeman.setText("Teman");
        ChkSupportTeman.setName("ChkSupportTeman"); // NOI18N
        FormInput.add(ChkSupportTeman);
        ChkSupportTeman.setBounds(210, 1010, 60, 23);

        ChkSupportLain.setText("Lain-lain");
        ChkSupportLain.setName("ChkSupportLain"); // NOI18N
        FormInput.add(ChkSupportLain);
        ChkSupportLain.setBounds(280, 1010, 80, 23);

        ChkPenunjangLab.setText("Laboratorium");
        ChkPenunjangLab.setName("ChkPenunjangLab"); // NOI18N
        FormInput.add(ChkPenunjangLab);
        ChkPenunjangLab.setBounds(30, 1080, 100, 23);

        ChkPenunjangRontgen.setText("Rontgen");
        ChkPenunjangRontgen.setName("ChkPenunjangRontgen"); // NOI18N
        FormInput.add(ChkPenunjangRontgen);
        ChkPenunjangRontgen.setBounds(140, 1080, 80, 23);

        ChkPenunjangUSG.setText("USG");
        ChkPenunjangUSG.setName("ChkPenunjangUSG"); // NOI18N
        FormInput.add(ChkPenunjangUSG);
        ChkPenunjangUSG.setBounds(220, 1080, 60, 23);

        ChkPenunjangEKG.setText("EKG");
        ChkPenunjangEKG.setName("ChkPenunjangEKG"); // NOI18N
        FormInput.add(ChkPenunjangEKG);
        ChkPenunjangEKG.setBounds(290, 1080, 60, 23);

        ChkPenunjangLain.setText("Lain-lain");
        ChkPenunjangLain.setName("ChkPenunjangLain"); // NOI18N
        FormInput.add(ChkPenunjangLain);
        ChkPenunjangLain.setBounds(350, 1080, 80, 23);

        jLabelMPenyuluhan.setText("M. Penyuluhan");
        jLabelMPenyuluhan.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelMPenyuluhan.setName("jLabelMPenyuluhan"); // NOI18N
        FormInput.add(jLabelMPenyuluhan);
        jLabelMPenyuluhan.setBounds(10, 1350, 90, 23);

        ChkPenyuluhanPeraturan.setText("Peraturan Rumah Sakit");
        ChkPenyuluhanPeraturan.setName("ChkPenyuluhanPeraturan"); // NOI18N
        FormInput.add(ChkPenyuluhanPeraturan);
        ChkPenyuluhanPeraturan.setBounds(30, 1380, 170, 23);

        ChkPenyuluhanHak.setText("Hak dan Kewajiban Klien");
        ChkPenyuluhanHak.setName("ChkPenyuluhanHak"); // NOI18N
        FormInput.add(ChkPenyuluhanHak);
        ChkPenyuluhanHak.setBounds(210, 1380, 180, 23);

        ChkPenyuluhanPerawatan.setText("Perawatan diri di RS");
        ChkPenyuluhanPerawatan.setName("ChkPenyuluhanPerawatan"); // NOI18N
        FormInput.add(ChkPenyuluhanPerawatan);
        ChkPenyuluhanPerawatan.setBounds(400, 1380, 150, 23);

        jLabelMPengetahuan.setText("Pengetahuan tentang :");
        jLabelMPengetahuan.setName("jLabelMPengetahuan"); // NOI18N
        FormInput.add(jLabelMPengetahuan);
        jLabelMPengetahuan.setBounds(30, 1400, 130, 23);

        ChkPengetahuanASI.setText("ASI");
        ChkPengetahuanASI.setName("ChkPengetahuanASI"); // NOI18N
        FormInput.add(ChkPengetahuanASI);
        ChkPengetahuanASI.setBounds(170, 1400, 50, 23);

        ChkPengetahuanKB.setText("KB");
        ChkPengetahuanKB.setName("ChkPengetahuanKB"); // NOI18N
        FormInput.add(ChkPengetahuanKB);
        ChkPengetahuanKB.setBounds(230, 1400, 45, 23);

        ChkPengetahuanRawatTaliPusat.setText("Rawat Tali Pusat");
        ChkPengetahuanRawatTaliPusat.setName("ChkPengetahuanRawatTaliPusat"); // NOI18N
        FormInput.add(ChkPengetahuanRawatTaliPusat);
        ChkPengetahuanRawatTaliPusat.setBounds(280, 1400, 130, 23);

        ChkPengetahuanPijatBayi.setText("Pijat Bayi");
        ChkPengetahuanPijatBayi.setName("ChkPengetahuanPijatBayi"); // NOI18N
        FormInput.add(ChkPengetahuanPijatBayi);
        ChkPengetahuanPijatBayi.setBounds(420, 1400, 80, 23);

        ChkPengetahuanLain.setText("Lain-lain");
        ChkPengetahuanLain.setName("ChkPengetahuanLain"); // NOI18N
        FormInput.add(ChkPengetahuanLain);
        ChkPengetahuanLain.setBounds(500, 1400, 80, 23);

        jLabelNDischarge.setText("N. Discharge Planning");
        jLabelNDischarge.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabelNDischarge.setName("jLabelNDischarge"); // NOI18N
        FormInput.add(jLabelNDischarge);
        jLabelNDischarge.setBounds(20, 1440, 120, 23);

        ChkDPMinumObat.setText("Jadwal minum obat di rumah");
        ChkDPMinumObat.setName("ChkDPMinumObat"); // NOI18N
        FormInput.add(ChkDPMinumObat);
        ChkDPMinumObat.setBounds(30, 1460, 200, 23);

        ChkDPKonsul.setText("Jadwal konsul");
        ChkDPKonsul.setName("ChkDPKonsul"); // NOI18N
        FormInput.add(ChkDPKonsul);
        ChkDPKonsul.setBounds(230, 1460, 110, 23);

        ChkDPImunisasi.setText("Jadwal Imunisasi Bayi");
        ChkDPImunisasi.setName("ChkDPImunisasi"); // NOI18N
        FormInput.add(ChkDPImunisasi);
        ChkDPImunisasi.setBounds(350, 1460, 170, 23);

        ChkDPMobilisasi.setText("Mobilisasi di rumah");
        ChkDPMobilisasi.setName("ChkDPMobilisasi"); // NOI18N
        FormInput.add(ChkDPMobilisasi);
        ChkDPMobilisasi.setBounds(30, 1480, 140, 23);

        ChkDPDiet.setText("Diet di rumah");
        ChkDPDiet.setName("ChkDPDiet"); // NOI18N
        FormInput.add(ChkDPDiet);
        ChkDPDiet.setBounds(170, 1480, 100, 23);

        ChkDPLain.setText("Lain-lain");
        ChkDPLain.setName("ChkDPLain"); // NOI18N
        FormInput.add(ChkDPLain);
        ChkDPLain.setBounds(280, 1480, 80, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("K. Pemeriksaan Penunjang");
        jLabel58.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(20, 1060, 160, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("D. Pemeriksaan Umum");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(30, 390, 127, 23);

        jLabel84.setText("Foley Catheter :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(500, 1770, 95, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("E. Pemeriksaan Fisik");
        jLabel85.setFont(new java.awt.Font("Liberation Sans", 1, 11)); // NOI18N
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(20, 490, 127, 23);

        jLabel41.setText("Hb :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(80, 1110, 19, 23);

        jLabel86.setText("Ht :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(170, 1110, 20, 23);

        USG.setFocusTraversalPolicyProvider(true);
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(100, 1170, 430, 23);

        jLabel88.setText("Leukosit :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(240, 1110, 50, 23);

        Leukosit.setFocusTraversalPolicyProvider(true);
        Leukosit.setName("Leukosit"); // NOI18N
        Leukosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeukositKeyPressed(evt);
            }
        });
        FormInput.add(Leukosit);
        Leukosit.setBounds(290, 1110, 45, 23);

        jLabel90.setText("Trombosit :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(340, 1110, 60, 23);

        Trombosit.setFocusTraversalPolicyProvider(true);
        Trombosit.setName("Trombosit"); // NOI18N
        Trombosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TrombositKeyPressed(evt);
            }
        });
        FormInput.add(Trombosit);
        Trombosit.setBounds(400, 1110, 45, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("C. USG");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(30, 1170, 40, 23);

        jLabel91.setText("Glukosa :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(450, 1110, 50, 23);

        Glukosa.setFocusTraversalPolicyProvider(true);
        Glukosa.setName("Glukosa"); // NOI18N
        Glukosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GlukosaKeyPressed(evt);
            }
        });
        FormInput.add(Glukosa);
        Glukosa.setBounds(500, 1110, 45, 23);

        Urine.setFocusTraversalPolicyProvider(true);
        Urine.setName("Urine"); // NOI18N
        Urine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineKeyPressed(evt);
            }
        });
        FormInput.add(Urine);
        Urine.setBounds(590, 1110, 45, 23);

        jLabel92.setText("Urine :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(550, 1110, 40, 23);

        jLabel93.setText("Protein :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(640, 1110, 50, 23);

        Protein.setFocusTraversalPolicyProvider(true);
        Protein.setName("Protein"); // NOI18N
        Protein.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProteinKeyPressed(evt);
            }
        });
        FormInput.add(Protein);
        Protein.setBounds(690, 1110, 45, 23);

        jLabel95.setText("Keton :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(740, 1110, 40, 23);

        Keton.setFocusTraversalPolicyProvider(true);
        Keton.setName("Keton"); // NOI18N
        Keton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetonKeyPressed(evt);
            }
        });
        FormInput.add(Keton);
        Keton.setBounds(780, 1110, 45, 23);

        Ht.setFocusTraversalPolicyProvider(true);
        Ht.setName("Ht"); // NOI18N
        Ht.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HtKeyPressed(evt);
            }
        });
        FormInput.add(Ht);
        Ht.setBounds(190, 1110, 45, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("A. Darah");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(30, 1110, 50, 23);

        Hb.setFocusTraversalPolicyProvider(true);
        Hb.setName("Hb"); // NOI18N
        Hb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HbKeyPressed(evt);
            }
        });
        FormInput.add(Hb);
        Hb.setBounds(120, 1110, 45, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("B. Rontgen");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(30, 1140, 60, 23);

        Rontgen.setFocusTraversalPolicyProvider(true);
        Rontgen.setName("Rontgen"); // NOI18N
        Rontgen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RontgenKeyPressed(evt);
            }
        });
        FormInput.add(Rontgen);
        Rontgen.setBounds(100, 1140, 430, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("III. ANALISA");
        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(10, 1990, 180, 23);

        label1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label1.setText("TUJUAN/TARGET YANG INGIN DICAPAI");
        label1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(460, 1830, 230, 20);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Analisa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Analisa.setColumns(20);
        Analisa.setRows(3);
        Analisa.setName("Analisa"); // NOI18N
        Analisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnalisaKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Analisa);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(30, 2020, 810, 43);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Penatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penatalaksana.setColumns(20);
        Penatalaksana.setRows(3);
        Penatalaksana.setName("Penatalaksana"); // NOI18N
        Penatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenatalaksanaKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Penatalaksana);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(30, 2100, 810, 43);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Masalah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Masalah.setColumns(20);
        Masalah.setRows(3);
        Masalah.setName("Masalah"); // NOI18N
        Masalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasalahKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Masalah);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(30, 1220, 770, 120);

        label2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label2.setText("IV. PENATALAKSANAAN");
        label2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label2.setName("label2"); // NOI18N
        FormInput.add(label2);
        label2.setBounds(10, 2080, 140, 20);

        label3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label3.setText("L. Rumusan Masalah");
        label3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label3.setName("label3"); // NOI18N
        FormInput.add(label3);
        label3.setBounds(30, 1200, 150, 20);

        label15.setText("Perawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(330, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(410, 40, 60, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(480, 40, 190, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(670, 40, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(350, 2150, 28, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 160, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 330, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 480, 880, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 640, 880, 1);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 820, 880, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 1040, 880, 1);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 1350, 880, 1);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-01-2026" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, BtnDokter);
        }
    }// GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(KdPetugas, "Perawat");
        } else {
            if (Sequel.menyimpantf(
                    "asesmen_khusus_kebidanan_ibu_nifas(no_rawat,tanggal,kd_dokter,keluhan_utama,riwayat_keluhan,riwayat_kesehatan,riwayat_kesehatan_lain,"
                            +
                            "g,p,a,hpht,hpl,imunisasi,opname,opname_sakit,opname_rs,operasi,operasi_ket,pengobatan,pengobatan_ket,alergi,alergi_ket,"
                            +
                            "pf_mata,pf_mata_lain,pf_thyroid,pf_kgb,pf_mammae,pf_mammae_lain,pf_puting,pf_colostrum,jenis_persalinan,indikasi_sc,anak,jenis_kelamin,bb,pb,"
                            +
                            "ketuban_pecah,jam_pecah,episiotomi,pereneum,jam_kala1,menit_kala1,darahan_kala1,jam_kala2,menit_kala2,darahan_kala2,jam_kala3,menit_kala3,"
                            +
                            "darahan_kala3,jam_kala4,menit_kala4,darahan_kala4,ttljamkala,ttlmenitkala,ttldarahan,kesadaran,gcs,tb,bb_umum,suhu,td,nadi,rr,spo2,"
                            +
                            "wajah,mata,mulut,payudara,fundus_uteri,kontraksi_uterus,kandung_kemih,luka_operasi,lokhea,luka_perineum,foley_catheter,ekstremitas,hb,ht,leukosit,trombosit,"
                            +
                            "glukosa,urine,protein,keton,rontgen,usg,analisa,penatalaksana,masalah,tujuan,nip)",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "", 91, new String[] {
                            TNoRw.getText(),
                            Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " "
                                    + TglAsuhan.getSelectedItem().toString().substring(11, 19),
                            KdDokter.getText(),
                            KeluhanUtama.getText(), RiwayatKeluhan.getText(), buildRiwayatKesehatan(),
                            RiwayatKesehatanLain.getText(),
                            Gravida.getText(), Para.getText(), Abortus.getText(), HPHT.getText(), HPL.getText(),
                            Imunisasi.getText(),
                            getPilihan(ChkOpnamePernah, "Pernah", ChkOpnameTidak, "Tidak Pernah"),
                            OpnameSakit.getText(), OpnameRS.getText(),
                            getPilihan(ChkOperasiPernah, "Pernah", ChkOperasiTidak, "Tidak Pernah"),
                            OperasiKet.getText(),
                            getPilihan(ChkPengobatanPernah, "Pernah", ChkPengobatanTidak, "Tidak"),
                            PengobatanKet.getText(),
                            getPilihan(ChkAlergiAda, "Ada", ChkAlergiTidak, "Tidak Ada"), AlergiKet.getText(),
                            buildPemeriksaanMata(), MataLain.getText(),
                            getPilihan(ChkThyroidMembesar, "Membesar", ChkThyroidTidak, "Tidak"),
                            getPilihan(ChkKGBMembesar, "Membesar", ChkKGBTidak, "Tidak"), buildPemeriksaanMammae(),
                            MammaeLain.getText(),
                            getPilihan(ChkPutingMenonjol, "Menonjol", ChkPutingDatar, "Datar"),
                            getPilihan(ChkColostrumLancar, "Lancar", ChkColostrumBelum, "Belum Ada"),
                            JenisPersalinan.getSelectedItem().toString(), SCAI.getText(),
                            KeadaanAnak.getSelectedItem().toString(), JenisKelamin.getSelectedItem().toString(),
                            BB1.getText(), PB.getText(), KetubanPecah.getSelectedItem().toString(), wita.getText(),
                            Episiotomi.getSelectedItem().toString(), Pereneum.getSelectedItem().toString(),
                            jam1.getText(), mnt1.getText(), darahan1.getText(), jam2.getText(), mnt2.getText(),
                            darahan2.getText(), jam3.getText(), mnt3.getText(), darahan3.getText(), jam4.getText(),
                            mnt4.getText(), darahan4.getText(), jamtotal.getText(), mnttotal.getText(),
                            darahantotal.getText(),
                            Kesadaran.getSelectedItem().toString(), GCS.getText(), TB.getText(), BB.getText(),
                            Suhu.getText(), TD.getText(), Nadi.getText(), RR.getText(), SPO.getText(),
                            Wajah.getSelectedItem().toString(), Mata.getSelectedItem().toString(),
                            Mulut.getSelectedItem().toString(), Payudara.getSelectedItem().toString(),
                            FundusUteri.getSelectedItem().toString(), KontraksiUterus.getSelectedItem().toString(),
                            KandungKemih.getSelectedItem().toString(), LukaOperasi.getSelectedItem().toString(),
                            Lokhea.getSelectedItem().toString(), LukaPerineum.getSelectedItem().toString(),
                            FoleyCatheter.getSelectedItem().toString(), Ekstremitas.getSelectedItem().toString(),
                            Hb.getText(), Ht.getText(), Leukosit.getText(), Trombosit.getText(), Glukosa.getText(),
                            Urine.getText(), Protein.getText(), Keton.getText(), Rontgen.getText(), USG.getText(),
                            Analisa.getText(), Penatalaksana.getText(), Masalah.getText(), Tujuan.getText(),
                            KdPetugas.getText()
                    }) == true) {
                emptTeks();
            }
        }

    }// GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        }
    }// GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }// GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }// GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 70).toString())) {
                    hapus();
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }

    }// GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }// GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnEditActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(KdPetugas, "Petugas");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 70).toString())) {
                        ganti();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }// GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }// GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
    }// GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
    }// GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            try {
                String filterPrint = (TCari.getText().trim().equals("") ? ""
                        : " and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or asesmen_khusus_kebidanan_ibu_nifas.nip like ? or "
                                +
                                "petugas.nama like ? or asesmen_khusus_kebidanan_ibu_nifas.kd_dokter like ? or dokter.nm_dokter like ?)");

                ps = koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.tanggal,asesmen_khusus_kebidanan_ibu_nifas.kd_dokter,dokter.nm_dokter,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.kesadaran,asesmen_khusus_kebidanan_ibu_nifas.gcs,asesmen_khusus_kebidanan_ibu_nifas.tb,asesmen_khusus_kebidanan_ibu_nifas.bb_umum,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.suhu,asesmen_khusus_kebidanan_ibu_nifas.td,asesmen_khusus_kebidanan_ibu_nifas.nadi,asesmen_khusus_kebidanan_ibu_nifas.rr,asesmen_khusus_kebidanan_ibu_nifas.spo2,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.wajah,asesmen_khusus_kebidanan_ibu_nifas.mata,asesmen_khusus_kebidanan_ibu_nifas.mulut,asesmen_khusus_kebidanan_ibu_nifas.payudara,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.fundus_uteri,asesmen_khusus_kebidanan_ibu_nifas.kontraksi_uterus,asesmen_khusus_kebidanan_ibu_nifas.kandung_kemih,asesmen_khusus_kebidanan_ibu_nifas.luka_operasi,asesmen_khusus_kebidanan_ibu_nifas.lokhea,asesmen_khusus_kebidanan_ibu_nifas.luka_perineum,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.foley_catheter,asesmen_khusus_kebidanan_ibu_nifas.ekstremitas,asesmen_khusus_kebidanan_ibu_nifas.hb,asesmen_khusus_kebidanan_ibu_nifas.ht,asesmen_khusus_kebidanan_ibu_nifas.leukosit,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.trombosit,asesmen_khusus_kebidanan_ibu_nifas.glukosa,asesmen_khusus_kebidanan_ibu_nifas.urine,asesmen_khusus_kebidanan_ibu_nifas.protein,asesmen_khusus_kebidanan_ibu_nifas.keton,asesmen_khusus_kebidanan_ibu_nifas.rontgen,asesmen_khusus_kebidanan_ibu_nifas.usg,"
                                +
                                "asesmen_khusus_kebidanan_ibu_nifas.analisa,asesmen_khusus_kebidanan_ibu_nifas.penatalaksana,asesmen_khusus_kebidanan_ibu_nifas.masalah,asesmen_khusus_kebidanan_ibu_nifas.tujuan,asesmen_khusus_kebidanan_ibu_nifas.nip,petugas.nama "
                                +
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                                "inner join asesmen_khusus_kebidanan_ibu_nifas on reg_periksa.no_rawat=asesmen_khusus_kebidanan_ibu_nifas.no_rawat "
                                +
                                "inner join petugas on asesmen_khusus_kebidanan_ibu_nifas.nip=petugas.nip " +
                                "inner join dokter on asesmen_khusus_kebidanan_ibu_nifas.kd_dokter=dokter.kd_dokter " +
                                "where asesmen_khusus_kebidanan_ibu_nifas.tanggal between ? and ?" + filterPrint
                                + " order by asesmen_khusus_kebidanan_ibu_nifas.tanggal");

                try {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    if (!TCari.getText().trim().equals("")) {
                        ps.setString(3, "%" + TCari.getText() + "%");
                        ps.setString(4, "%" + TCari.getText() + "%");
                        ps.setString(5, "%" + TCari.getText() + "%");
                        ps.setString(6, "%" + TCari.getText() + "%");
                        ps.setString(7, "%" + TCari.getText() + "%");
                        ps.setString(8, "%" + TCari.getText() + "%");
                        ps.setString(9, "%" + TCari.getText() + "%");
                    }
                    rs = ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(
                            "<tr class='isi'>" +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kode Dokter</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kesadaran</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>G-C-S</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>TB</b></td>" +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>BB</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>Suhu</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>TD</b></td>" +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>Nadi</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>RR</b></td>" +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>SpO2</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Wajah</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Mata</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Mulut</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Payudara</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Fundus Uteri</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Kontraksi Uterus</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Kandung Kemih</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Luka Operasi</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Lokhea</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Luka Perinerum</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Foley Catheter</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Ekstremitas</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Hb</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Ht</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Leukosit</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Trombosit</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Glukosa</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Urine</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Protein</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Keton</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Rontgen</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>USG</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Analisa</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Penatalaksanaan</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Masalah</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Tujuan/Target</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Kode Perawat</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Perawat</b></td>"
                                    +
                                    "</tr>");
                    while (rs.next()) {
                        htmlContent.append(
                                "<tr class='isi'>" +
                                        "<td valign='top'>" + rs.getString("no_rawat") + "</td>" +
                                        "<td valign='top'>" + rs.getString("no_rkm_medis") + "</td>" +
                                        "<td valign='top'>" + rs.getString("nm_pasien") + "</td>" +
                                        "<td valign='top'>" + rs.getString("tgl_lahir") + "</td>" +
                                        "<td valign='top'>" + rs.getString("jk") + "</td>" +
                                        "<td valign='top'>" + rs.getString("kd_dokter") + "</td>" +
                                        "<td valign='top'>" + rs.getString("nm_dokter") + "</td>" +
                                        "<td valign='top'>" + rs.getString("tanggal") + "</td>" +
                                        "<td valign='top'>" + rs.getString("kesadaran") + "</td>" +
                                        "<td valign='top'>" + rs.getString("gcs") + "</td>" +
                                        "<td valign='top'>" + rs.getString("tb") + "</td>" +
                                        "<td valign='top'>" + rs.getString("bb_umum") + "</td>" +
                                        "<td valign='top'>" + rs.getString("suhu") + "</td>" +
                                        "<td valign='top'>" + rs.getString("td") + "</td>" +
                                        "<td valign='top'>" + rs.getString("nadi") + "</td>" +
                                        "<td valign='top'>" + rs.getString("rr") + "</td>" +
                                        "<td valign='top'>" + rs.getString("spo2") + "</td>" +
                                        "<td valign='top'>" + rs.getString("wajah") + "</td>" +
                                        "<td valign='top'>" + rs.getString("mata") + "</td>" +
                                        "<td valign='top'>" + rs.getString("mulut") + "</td>" +
                                        "<td valign='top'>" + rs.getString("payudara") + "</td>" +
                                        "<td valign='top'>" + rs.getString("fundus_uteri") + "</td>" +
                                        "<td valign='top'>" + rs.getString("kontraksi_uterus") + "</td>" +
                                        "<td valign='top'>" + rs.getString("kandung_kemih") + "</td>" +
                                        "<td valign='top'>" + rs.getString("luka_operasi") + "</td>" +
                                        "<td valign='top'>" + rs.getString("lokhea") + "</td>" +
                                        "<td valign='top'>" + rs.getString("luka_perineum") + "</td>" +
                                        "<td valign='top'>" + rs.getString("foley_catheter") + "</td>" +
                                        "<td valign='top'>" + rs.getString("ekstremitas") + "</td>" +
                                        "<td valign='top'>" + rs.getString("hb") + "</td>" +
                                        "<td valign='top'>" + rs.getString("ht") + "</td>" +
                                        "<td valign='top'>" + rs.getString("leukosit") + "</td>" +
                                        "<td valign='top'>" + rs.getString("trombosit") + "</td>" +
                                        "<td valign='top'>" + rs.getString("glukosa") + "</td>" +
                                        "<td valign='top'>" + rs.getString("urine") + "</td>" +
                                        "<td valign='top'>" + rs.getString("protein") + "</td>" +
                                        "<td valign='top'>" + rs.getString("keton") + "</td>" +
                                        "<td valign='top'>" + rs.getString("rontgen") + "</td>" +
                                        "<td valign='top'>" + rs.getString("usg") + "</td>" +
                                        "<td valign='top'>" + rs.getString("analisa") + "</td>" +
                                        "<td valign='top'>" + rs.getString("penatalaksana") + "</td>" +
                                        "<td valign='top'>" + rs.getString("masalah") + "</td>" +
                                        "<td valign='top'>" + rs.getString("tujuan") + "</td>" +
                                        "<td valign='top'>" + rs.getString("nip") + "</td>" +
                                        "<td valign='top'>" + rs.getString("nama") + "</td>" +
                                        "</tr>");

                    }
                    LoadHTML.setText(
                            "<html>" +
                                    "<table width='3500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"
                                    +
                                    htmlContent.toString() +
                                    "</table>" +
                                    "</html>");

                    File g = new File("file2.css");
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                            ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                                    +
                                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                                    +
                                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                                    +
                                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                                    +
                                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
                    bg.close();

                    File f = new File("DataAsesmenKhususKebidananIbuNifas.html");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    bw.write(LoadHTML.getText().replaceAll("<head>", "<head>" +
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />" +
                            "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            +
                            "<tr class='isi2'>" +
                            "<td valign='top' align='center'>" +
                            "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>" +
                            akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                            +
                            akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>" +
                            "<font size='2' face='Tahoma'>DATA ASESMEN KHUSUS KEBIDANAN PADA IBU NIFAS<br><br></font>" +
                            "</td>" +
                            "</tr>" +
                            "</table>"));
                    bw.close();
                    Desktop.getDesktop().browse(f.toURI());
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }

            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
    }// GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }// GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }// GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }// GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }// GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }// GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if ((evt.getClickCount() == 2) && (tbObat.getSelectedColumn() == 0)) {
                TabRawat.setSelectedIndex(0);
            }
        }
    }// GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }// GEN-LAST:event_tbObatKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KdDokterKeyPressed

    }// GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }// GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnDokterKeyPressed
        // Valid.pindah(evt,Monitoring,BtnSimpan);
    }// GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt, TB, TD);
    }// GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt, GCS, BB);
    }// GEN-LAST:event_TBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt, TD, RR);
    }// GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt, RR, SPO);
    }// GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt, BB, Nadi);
    }// GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt, Nadi, Suhu);
    }// GEN-LAST:event_RRKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KeluhanUtamaKeyPressed

    }// GEN-LAST:event_KeluhanUtamaKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt, Kesadaran, TB);
    }// GEN-LAST:event_GCSKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        }
    }// GEN-LAST:event_TabRawatMouseClicked

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_EkstremitasKeyPressed

    }// GEN-LAST:event_EkstremitasKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt, Ekstremitas, GCS);
    }// GEN-LAST:event_KesadaranKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SPOKeyPressed
        Valid.pindah(evt, Suhu, Wajah);
    }// GEN-LAST:event_SPOKeyPressed

    private void WajahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WajahKeyPressed
        Valid.pindah(evt, SPO, Mata);
    }// GEN-LAST:event_WajahKeyPressed

    private void MulutKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MulutKeyPressed
        Valid.pindah(evt, Mata, Payudara);
    }// GEN-LAST:event_MulutKeyPressed

    private void PayudaraKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PayudaraKeyPressed
        Valid.pindah(evt, Mulut, FundusUteri);
    }// GEN-LAST:event_PayudaraKeyPressed

    private void FundusUteriKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_FundusUteriKeyPressed
        Valid.pindah(evt, Payudara, KontraksiUterus);
    }// GEN-LAST:event_FundusUteriKeyPressed

    private void LukaOperasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LukaOperasiKeyPressed
        Valid.pindah(evt, KandungKemih, Lokhea);
    }// GEN-LAST:event_LukaOperasiKeyPressed

    private void LokheaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LokheaKeyPressed
        Valid.pindah(evt, LukaOperasi, LukaPerineum);
    }// GEN-LAST:event_LokheaKeyPressed

    private void LukaPerineumKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LukaPerineumKeyPressed
        Valid.pindah(evt, Lokhea, FoleyCatheter);
    }// GEN-LAST:event_LukaPerineumKeyPressed

    private void FoleyCatheterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_FoleyCatheterKeyPressed

    }// GEN-LAST:event_FoleyCatheterKeyPressed

    private void TujuanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TujuanKeyPressed

    }// GEN-LAST:event_TujuanKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TglAsuhanKeyPressed

    }// GEN-LAST:event_TglAsuhanKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            finger = Sequel.cariIsi(
                    "select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            param.put("finger",
                    "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh "
                            + tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + "\nID "
                            + (finger.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString() : finger)
                            + "\n" + Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()));

            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanapKebidanan.jasper", "report",
                    "::[ Laporan Penilaian Awal Medis Rawat Inap Kebidanan & Kandungan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_kandungan.tanggal,"
                            +
                            "penilaian_medis_ranap_kandungan.kd_dokter,penilaian_medis_ranap_kandungan.anamnesis,penilaian_medis_ranap_kandungan.hubungan,penilaian_medis_ranap_kandungan.keluhan_utama,penilaian_medis_ranap_kandungan.rps,penilaian_medis_ranap_kandungan.rpk,penilaian_medis_ranap_kandungan.rpd,penilaian_medis_ranap_kandungan.rpo,penilaian_medis_ranap_kandungan.alergi,"
                            +
                            "penilaian_medis_ranap_kandungan.keadaan,penilaian_medis_ranap_kandungan.gcs,penilaian_medis_ranap_kandungan.kesadaran,penilaian_medis_ranap_kandungan.td,penilaian_medis_ranap_kandungan.nadi,penilaian_medis_ranap_kandungan.rr,penilaian_medis_ranap_kandungan.suhu,penilaian_medis_ranap_kandungan.spo,penilaian_medis_ranap_kandungan.bb,penilaian_medis_ranap_kandungan.tb,"
                            +
                            "penilaian_medis_ranap_kandungan.kepala,penilaian_medis_ranap_kandungan.mata,penilaian_medis_ranap_kandungan.gigi,penilaian_medis_ranap_kandungan.tht,penilaian_medis_ranap_kandungan.thoraks,penilaian_medis_ranap_kandungan.jantung,penilaian_medis_ranap_kandungan.paru,penilaian_medis_ranap_kandungan.abdomen,penilaian_medis_ranap_kandungan.ekstremitas,"
                            +
                            "penilaian_medis_ranap_kandungan.genital,penilaian_medis_ranap_kandungan.kulit,penilaian_medis_ranap_kandungan.ket_fisik,penilaian_medis_ranap_kandungan.tfu,penilaian_medis_ranap_kandungan.tbj,penilaian_medis_ranap_kandungan.his,penilaian_medis_ranap_kandungan.kontraksi,penilaian_medis_ranap_kandungan.djj,penilaian_medis_ranap_kandungan.inspeksi,"
                            +
                            "penilaian_medis_ranap_kandungan.inspekulo,penilaian_medis_ranap_kandungan.vt,penilaian_medis_ranap_kandungan.rt,penilaian_medis_ranap_kandungan.ultra,penilaian_medis_ranap_kandungan.kardio,penilaian_medis_ranap_kandungan.lab,penilaian_medis_ranap_kandungan.diagnosis,penilaian_medis_ranap_kandungan.tata,penilaian_medis_ranap_kandungan.edukasi,dokter.nm_dokter "
                            +
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                            "inner join penilaian_medis_ranap_kandungan on reg_periksa.no_rawat=penilaian_medis_ranap_kandungan.no_rawat "
                            +
                            "inner join dokter on penilaian_medis_ranap_kandungan.kd_dokter=dokter.kd_dokter where penilaian_medis_ranap_kandungan.no_rawat='"
                            + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'",
                    param);
        }
    }// GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt, Wajah, Mulut);
    }// GEN-LAST:event_MataKeyPressed

    private void KontraksiUterusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KontraksiUterusKeyPressed
        Valid.pindah(evt, FundusUteri, KandungKemih);
    }// GEN-LAST:event_KontraksiUterusKeyPressed

    private void KandungKemihKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KandungKemihKeyPressed
        Valid.pindah(evt, KontraksiUterus, LukaOperasi);
    }// GEN-LAST:event_KandungKemihKeyPressed

    private void JenisPersalinanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JenisPersalinanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JenisPersalinanKeyPressed

    private void JenisKelaminKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JenisKelaminKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JenisKelaminKeyPressed

    private void KetubanPecahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetubanPecahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KetubanPecahKeyPressed

    private void EpisiotomiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_EpisiotomiKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_EpisiotomiKeyPressed

    private void KeadaanAnakKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KeadaanAnakKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KeadaanAnakKeyPressed

    private void PereneumKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PereneumKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PereneumKeyPressed

    private void SCAIKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SCAIKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SCAIKeyPressed

    private void BB1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BB1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BB1KeyPressed

    private void PBKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PBKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PBKeyPressed

    private void witaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_witaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_witaKeyPressed

    private void jam1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_jam1KeyPressed

    private void mnt1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_mnt1KeyPressed

    private void jam2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_jam2KeyPressed

    private void mnt2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_mnt2KeyPressed

    private void jam3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_jam3KeyPressed

    private void mnt3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_mnt3KeyPressed

    private void mnt4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_mnt4KeyPressed

    private void jam4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_jam4KeyPressed

    private void jamtotalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jamtotalKeyPressed

    }// GEN-LAST:event_jamtotalKeyPressed

    private void mnttotalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnttotalKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_mnttotalKeyPressed

    private void darahan1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_darahan1KeyPressed

    private void darahan2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_darahan2KeyPressed

    private void darahan3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_darahan3KeyPressed

    private void darahan4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_darahan4KeyPressed

    private void darahantotalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahantotalKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_darahantotalKeyPressed

    private void USGKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_USGKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_USGKeyPressed

    private void LeukositKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LeukositKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_LeukositKeyPressed

    private void TrombositKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TrombositKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TrombositKeyPressed

    private void GlukosaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_GlukosaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_GlukosaKeyPressed

    private void UrineKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_UrineKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_UrineKeyPressed

    private void ProteinKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ProteinKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_ProteinKeyPressed

    private void KetonKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetonKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KetonKeyPressed

    private void HtKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_HtKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_HtKeyPressed

    private void HbKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_HbKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_HbKeyPressed

    private void RontgenKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_RontgenKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_RontgenKeyPressed

    private void AnalisaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AnalisaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_AnalisaKeyPressed

    private void PenatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PenatalaksanaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PenatalaksanaKeyPressed

    private void MasalahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MasalahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_MasalahKeyPressed

    private void jamtotalKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jamtotalKeyReleased

    }// GEN-LAST:event_jamtotalKeyReleased

    private void jam4KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam4KeyReleased
        int jm1 = Integer.parseInt(jam1.getText());
        int jm2 = Integer.parseInt(jam2.getText());
        int jm3 = Integer.parseInt(jam3.getText());
        int jm4 = Integer.parseInt(jam4.getText());
        int hsl = jm1 + jm2 + jm3 + jm4;
        jamtotal.setText(String.valueOf(hsl));
    }// GEN-LAST:event_jam4KeyReleased

    private void jam1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam1KeyReleased
        int jm1 = Integer.parseInt(jam1.getText());
        int hsl = jm1;
        jamtotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_jam1KeyReleased

    private void jam2KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam2KeyReleased
        int jm1 = Integer.parseInt(jam1.getText());
        int jm2 = Integer.parseInt(jam2.getText());

        int hsl = jm1 + jm2;
        jamtotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_jam2KeyReleased

    private void jam3KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jam3KeyReleased
        int jm1 = Integer.parseInt(jam1.getText());
        int jm2 = Integer.parseInt(jam2.getText());
        int jm3 = Integer.parseInt(jam3.getText());

        int hsl = jm1 + jm2 + jm3;
        jamtotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_jam3KeyReleased

    private void mnt1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt1KeyReleased
        int mt1 = Integer.parseInt(mnt1.getText());

        int hsl = mt1;
        mnttotal.setText(String.valueOf(hsl)); // TODO add your handling code here:
    }// GEN-LAST:event_mnt1KeyReleased

    private void mnt2KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt2KeyReleased
        int mt1 = Integer.parseInt(mnt1.getText());
        int mt2 = Integer.parseInt(mnt2.getText());

        int hsl = mt1 + mt2;
        mnttotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_mnt2KeyReleased

    private void mnt3KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt3KeyReleased
        int mt1 = Integer.parseInt(mnt1.getText());
        int mt2 = Integer.parseInt(mnt2.getText());
        int mt3 = Integer.parseInt(mnt3.getText());

        int hsl = mt1 + mt2 + mt3;
        mnttotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_mnt3KeyReleased

    private void mnt4KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_mnt4KeyReleased
        int mt1 = Integer.parseInt(mnt1.getText());
        int mt2 = Integer.parseInt(mnt2.getText());
        int mt3 = Integer.parseInt(mnt3.getText());
        int mt4 = Integer.parseInt(mnt4.getText());
        int hsl = mt1 + mt2 + mt3 + mt4;
        mnttotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_mnt4KeyReleased

    private void darahan1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan1KeyReleased
        int darah1 = Integer.parseInt(darahan1.getText());

        int hsl = darah1;
        darahantotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_darahan1KeyReleased

    private void darahan2KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan2KeyReleased
        int darah1 = Integer.parseInt(darahan1.getText());
        int darah2 = Integer.parseInt(darahan2.getText());

        int hsl = darah1 + darah2;
        darahantotal.setText(String.valueOf(hsl)); // TODO add your handling code here:
    }// GEN-LAST:event_darahan2KeyReleased

    private void darahan3KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan3KeyReleased
        int darah1 = Integer.parseInt(darahan1.getText());
        int darah2 = Integer.parseInt(darahan2.getText());
        int darah3 = Integer.parseInt(darahan3.getText());

        int hsl = darah1 + darah2 + darah3;
        darahantotal.setText(String.valueOf(hsl));// TODO add your handling code here:
    }// GEN-LAST:event_darahan3KeyReleased

    private void darahan4KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_darahan4KeyReleased
        int darah1 = Integer.parseInt(darahan1.getText());
        int darah2 = Integer.parseInt(darahan2.getText());
        int darah3 = Integer.parseInt(darahan3.getText());
        int darah4 = Integer.parseInt(darahan4.getText());
        int hsl = darah1 + darah2 + darah3 + darah4;
        darahantotal.setText(String.valueOf(hsl)); // TODO add your handling code here:
    }// GEN-LAST:event_darahan4KeyReleased

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KdPetugasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NmPetugas, KdPetugas.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            NmDokter.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        }
    }// GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }// GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnPetugasKeyPressed
        // Valid.pindah(evt,NmDokter,cmbSkor1);
    }// GEN-LAST:event_btnPetugasKeyPressed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt, BtnSimpan, BtnPetugas);
    }// GEN-LAST:event_BtnPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }// GEN-LAST:event_BtnPetugasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            AsesmenKhususKebidananIbuNifas dialog = new AsesmenKhususKebidananIbuNifas(new javax.swing.JFrame(), true);
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
    private widget.TextBox Abortus;
    private widget.TextBox AlergiKet;
    private widget.TextArea Analisa;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAktivitasBergantung;
    private widget.CekBox ChkAktivitasMandiri;
    private widget.CekBox ChkAktivitasPenuh;
    private widget.CekBox ChkAlergiAda;
    private widget.CekBox ChkAlergiTidak;
    private widget.CekBox ChkAnusHemoroid;
    private widget.CekBox ChkAnusTAK;
    private widget.CekBox ChkAsma;
    private widget.CekBox ChkBabDiare;
    private widget.CekBox ChkBabKonstipasi;
    private widget.CekBox ChkBabTAK;
    private widget.CekBox ChkBakKateter;
    private widget.CekBox ChkBakOliguri;
    private widget.CekBox ChkBakRetensi;
    private widget.CekBox ChkBakTAK;
    private widget.CekBox ChkBartholiniMembesar;
    private widget.CekBox ChkBartholiniTidak;
    private widget.CekBox ChkColostrumBelum;
    private widget.CekBox ChkColostrumLancar;
    private widget.CekBox ChkDM;
    private widget.CekBox ChkDPDiet;
    private widget.CekBox ChkDPImunisasi;
    private widget.CekBox ChkDPKonsul;
    private widget.CekBox ChkDPLain;
    private widget.CekBox ChkDPMinumObat;
    private widget.CekBox ChkDPMobilisasi;
    private widget.CekBox ChkEkstOedema;
    private widget.CekBox ChkEkstParalise;
    private widget.CekBox ChkEkstRefleks;
    private widget.CekBox ChkEkstVarises;
    private widget.CekBox ChkGinjal;
    private widget.CekBox ChkHT;
    private widget.CekBox ChkHepar;
    private widget.CekBox ChkHomanTidak;
    private widget.CekBox ChkHomanYa;
    private widget.CekBox ChkIstirahatInsomnia;
    private widget.CekBox ChkIstirahatLain;
    private widget.CekBox ChkIstirahatTidakAda;
    private widget.CekBox ChkKGBMembesar;
    private widget.CekBox ChkKGBTidak;
    private widget.CekBox ChkLain;
    private widget.CekBox ChkLokiaBauNeg;
    private widget.CekBox ChkLokiaBauPos;
    private widget.CekBox ChkMammaeBenjolan;
    private widget.CekBox ChkMammaeKemerahan;
    private widget.CekBox ChkMammaeKeras;
    private widget.CekBox ChkMammaeLain;
    private widget.CekBox ChkMammaeLembek;
    private widget.CekBox ChkMammaePanas;
    private widget.CekBox ChkMataAnemia;
    private widget.CekBox ChkMataIcterus;
    private widget.CekBox ChkMataLain;
    private widget.CekBox ChkMataTAK;
    private widget.CekBox ChkNutrisiMual;
    private widget.CekBox ChkNutrisiMuntah;
    private widget.CekBox ChkNutrisiPuasa;
    private widget.CekBox ChkNutrisiTAK;
    private widget.CekBox ChkNutrisiTidakNafsu;
    private widget.CekBox ChkOperasiPernah;
    private widget.CekBox ChkOperasiTidak;
    private widget.CekBox ChkOpnamePernah;
    private widget.CekBox ChkOpnameTidak;
    private widget.CekBox ChkPengetahuanASI;
    private widget.CekBox ChkPengetahuanKB;
    private widget.CekBox ChkPengetahuanLain;
    private widget.CekBox ChkPengetahuanPijatBayi;
    private widget.CekBox ChkPengetahuanRawatTaliPusat;
    private widget.CekBox ChkPengobatanPernah;
    private widget.CekBox ChkPengobatanTidak;
    private widget.CekBox ChkPenunjangEKG;
    private widget.CekBox ChkPenunjangLab;
    private widget.CekBox ChkPenunjangLain;
    private widget.CekBox ChkPenunjangRontgen;
    private widget.CekBox ChkPenunjangUSG;
    private widget.CekBox ChkPenyuluhanHak;
    private widget.CekBox ChkPenyuluhanPeraturan;
    private widget.CekBox ChkPenyuluhanPerawatan;
    private widget.CekBox ChkPerineumRuptur;
    private widget.CekBox ChkPerineumUtuh;
    private widget.CekBox ChkPsikoCemas;
    private widget.CekBox ChkPsikoGembira;
    private widget.CekBox ChkPsikoLain;
    private widget.CekBox ChkPsikoMenangis;
    private widget.CekBox ChkPsikoTenang;
    private widget.CekBox ChkPutingDatar;
    private widget.CekBox ChkPutingMenonjol;
    private widget.CekBox ChkSupportKeluarga;
    private widget.CekBox ChkSupportLain;
    private widget.CekBox ChkSupportTeman;
    private widget.CekBox ChkTBC;
    private widget.CekBox ChkThyroidMembesar;
    private widget.CekBox ChkThyroidTidak;
    private widget.CekBox ChkVaginaBauNeg;
    private widget.CekBox ChkVaginaBauPos;
    private widget.CekBox ChkVaginaCairan;
    private widget.CekBox ChkVaginaProlapsus;
    private widget.CekBox ChkVaginaTAK;
    private widget.CekBox ChkVulvaCondyloma;
    private widget.CekBox ChkVulvaEdema;
    private widget.CekBox ChkVulvaVarises;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox EkstParaliseKet;
    private widget.ComboBox Ekstremitas;
    private widget.ComboBox FoleyCatheter;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox FundusUteri;
    private widget.TextBox GCS;
    private widget.TextBox Glukosa;
    private widget.TextBox Gravida;
    private widget.TextBox HPHT;
    private widget.TextBox HPL;
    private widget.TextBox Hb;
    private widget.TextBox Ht;
    private widget.TextBox Imunisasi;
    private widget.TextBox Jk;
    private widget.ComboBox KandungKemih;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kesadaran;
    private widget.TextBox Keton;
    private widget.ComboBox KontraksiUterus;
    private widget.Label LCount;
    private widget.TextBox Leukosit;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Lokhea;
    private widget.TextBox LokiaBanyak;
    private widget.TextBox LokiaJenis;
    private widget.TextBox LokiaWarna;
    private widget.ComboBox LukaOperasi;
    private widget.ComboBox LukaPerineum;
    private widget.TextBox MammaeLain;
    private widget.TextArea Masalah;
    private widget.ComboBox Mata;
    private widget.TextBox MataLain;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.ComboBox Mulut;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox OperasiKet;
    private widget.TextBox OpnameRS;
    private widget.TextBox OpnameSakit;
    private widget.TextBox Para;
    private widget.ComboBox Payudara;
    private widget.TextArea Penatalaksana;
    private widget.TextBox PengobatanKet;
    private widget.TextBox Protein;
    private widget.TextBox RR;
    private widget.TextArea RiwayatKeluhan;
    private widget.TextBox RiwayatKesehatanLain;
    private widget.TextBox Rontgen;
    private widget.TextBox SPO;
    private widget.ScrollPane Scroll;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox Trombosit;
    private widget.TextArea Tujuan;
    private widget.TextBox USG;
    private widget.TextBox Urine;
    private widget.TextBox VaginaWarna;
    private widget.ComboBox Wajah;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
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
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel33;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel95;
    private widget.Label jLabel97;
    private widget.Label jLabelFGenitalia;
    private widget.Label jLabelFGenitaliaBanyak;
    private widget.Label jLabelFGenitaliaBartholini;
    private widget.Label jLabelFGenitaliaBau;
    private widget.Label jLabelFGenitaliaJenis;
    private widget.Label jLabelFGenitaliaLokia;
    private widget.Label jLabelFGenitaliaPerineum;
    private widget.Label jLabelFGenitaliaVagina;
    private widget.Label jLabelFGenitaliaVaginaBau;
    private widget.Label jLabelFGenitaliaVaginaWarna;
    private widget.Label jLabelFGenitaliaVulva;
    private widget.Label jLabelFGenitaliaWarna;
    private widget.Label jLabelGAnus;
    private widget.Label jLabelHEkstremitas;
    private widget.Label jLabelIHoman;
    private widget.Label jLabelJPola;
    private widget.Label jLabelJPolaAktivitas;
    private widget.Label jLabelJPolaBab;
    private widget.Label jLabelJPolaBak;
    private widget.Label jLabelJPolaEliminasi;
    private widget.Label jLabelJPolaIstirahat;
    private widget.Label jLabelJPolaNutrisi;
    private widget.Label jLabelJPolaPsikologis;
    private widget.Label jLabelJPolaSupport;
    private widget.Label jLabelMPengetahuan;
    private widget.Label jLabelMPenyuluhan;
    private widget.Label jLabelNDischarge;
    private widget.Label jLabelSubjA;
    private widget.Label jLabelSubjAlasanMasuk;
    private widget.Label jLabelSubjAlergi;
    private widget.Label jLabelSubjColostrum;
    private widget.Label jLabelSubjGPA;
    private widget.Label jLabelSubjHPHT;
    private widget.Label jLabelSubjHPL;
    private widget.Label jLabelSubjImunisasi;
    private widget.Label jLabelSubjKGB;
    private widget.Label jLabelSubjKeluhan;
    private widget.Label jLabelSubjMammae;
    private widget.Label jLabelSubjMata;
    private widget.Label jLabelSubjOperasi;
    private widget.Label jLabelSubjOperasiKet;
    private widget.Label jLabelSubjOpname;
    private widget.Label jLabelSubjOpnameRS;
    private widget.Label jLabelSubjOpnameSakit;
    private widget.Label jLabelSubjP;
    private widget.Label jLabelSubjPengobatan;
    private widget.Label jLabelSubjPengobatanKet;
    private widget.Label jLabelSubjPuting;
    private widget.Label jLabelSubjRiwayatKeluhan;
    private widget.Label jLabelSubjRiwayatKesehatan;
    private widget.Label jLabelSubjThyroid;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label2;
    private widget.Label label3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPaneKeluhan;
    private widget.ScrollPane scrollPaneRiwayatKeluhan;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.tanggal,asesmen_khusus_kebidanan_ibu_nifas.kd_dokter,dokter.nm_dokter,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.keluhan_utama,asesmen_khusus_kebidanan_ibu_nifas.riwayat_keluhan,asesmen_khusus_kebidanan_ibu_nifas.riwayat_kesehatan,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.riwayat_kesehatan_lain,asesmen_khusus_kebidanan_ibu_nifas.g,asesmen_khusus_kebidanan_ibu_nifas.p,asesmen_khusus_kebidanan_ibu_nifas.a,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.hpht,asesmen_khusus_kebidanan_ibu_nifas.hpl,asesmen_khusus_kebidanan_ibu_nifas.imunisasi,asesmen_khusus_kebidanan_ibu_nifas.opname,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.opname_sakit,asesmen_khusus_kebidanan_ibu_nifas.opname_rs,asesmen_khusus_kebidanan_ibu_nifas.operasi,asesmen_khusus_kebidanan_ibu_nifas.operasi_ket,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.pengobatan,asesmen_khusus_kebidanan_ibu_nifas.pengobatan_ket,asesmen_khusus_kebidanan_ibu_nifas.alergi,asesmen_khusus_kebidanan_ibu_nifas.alergi_ket,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.pf_mata,asesmen_khusus_kebidanan_ibu_nifas.pf_mata_lain,asesmen_khusus_kebidanan_ibu_nifas.pf_thyroid,asesmen_khusus_kebidanan_ibu_nifas.pf_kgb,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.pf_mammae,asesmen_khusus_kebidanan_ibu_nifas.pf_mammae_lain,asesmen_khusus_kebidanan_ibu_nifas.pf_puting,asesmen_khusus_kebidanan_ibu_nifas.pf_colostrum,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.kesadaran,asesmen_khusus_kebidanan_ibu_nifas.gcs,asesmen_khusus_kebidanan_ibu_nifas.tb,asesmen_khusus_kebidanan_ibu_nifas.bb_umum,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.suhu,asesmen_khusus_kebidanan_ibu_nifas.td,asesmen_khusus_kebidanan_ibu_nifas.nadi,asesmen_khusus_kebidanan_ibu_nifas.rr,asesmen_khusus_kebidanan_ibu_nifas.spo2,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.wajah,asesmen_khusus_kebidanan_ibu_nifas.mata,asesmen_khusus_kebidanan_ibu_nifas.mulut,asesmen_khusus_kebidanan_ibu_nifas.payudara,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.fundus_uteri,asesmen_khusus_kebidanan_ibu_nifas.kontraksi_uterus,asesmen_khusus_kebidanan_ibu_nifas.kandung_kemih,asesmen_khusus_kebidanan_ibu_nifas.luka_operasi,asesmen_khusus_kebidanan_ibu_nifas.lokhea,asesmen_khusus_kebidanan_ibu_nifas.luka_perineum,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.foley_catheter,asesmen_khusus_kebidanan_ibu_nifas.ekstremitas,asesmen_khusus_kebidanan_ibu_nifas.hb,asesmen_khusus_kebidanan_ibu_nifas.ht,asesmen_khusus_kebidanan_ibu_nifas.leukosit,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.trombosit,asesmen_khusus_kebidanan_ibu_nifas.glukosa,asesmen_khusus_kebidanan_ibu_nifas.urine,asesmen_khusus_kebidanan_ibu_nifas.protein,asesmen_khusus_kebidanan_ibu_nifas.keton,asesmen_khusus_kebidanan_ibu_nifas.rontgen,asesmen_khusus_kebidanan_ibu_nifas.usg,"
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.analisa,asesmen_khusus_kebidanan_ibu_nifas.penatalaksana,asesmen_khusus_kebidanan_ibu_nifas.masalah,asesmen_khusus_kebidanan_ibu_nifas.tujuan,asesmen_khusus_kebidanan_ibu_nifas.nip,petugas.nama "
                            +

                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                            "inner join asesmen_khusus_kebidanan_ibu_nifas on reg_periksa.no_rawat=asesmen_khusus_kebidanan_ibu_nifas.no_rawat "
                            +
                            "inner join petugas on asesmen_khusus_kebidanan_ibu_nifas.nip=petugas.nip " +
                            "inner join dokter on asesmen_khusus_kebidanan_ibu_nifas.kd_dokter=dokter.kd_dokter where "
                            +
                            "asesmen_khusus_kebidanan_ibu_nifas.tanggal between ? and ? " +
                            (TCari.getText().trim().equals("") ? ""
                                    : "and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or asesmen_khusus_kebidanan_ibu_nifas.nip like ? or "
                                            +
                                            "petugas.nama like ? or asesmen_khusus_kebidanan_ibu_nifas.kd_dokter like ? or dokter.nm_dokter like ?)")
                            +
                            " order by asesmen_khusus_kebidanan_ibu_nifas.tanggal");

            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                if (!TCari.getText().equals("")) {
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                    ps.setString(8, "%" + TCari.getText() + "%");
                    ps.setString(9, "%" + TCari.getText() + "%");
                    // if(TCari.getText().trim().equals("")){
                    // ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    // ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    // }else{
                    // ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    // ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    // ps.setString(3,"%"+TCari.getText()+"%");
                    // ps.setString(4,"%"+TCari.getText()+"%");
                    // ps.setString(5,"%"+TCari.getText()+"%");
                    // ps.setString(6,"%"+TCari.getText()+"%");
                    // ps.setString(7,"%"+TCari.getText()+"%");

                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[] {
                            rs.getString("no_rawat"),
                            rs.getString("no_rkm_medis"),
                            rs.getString("nm_pasien"),
                            rs.getString("tgl_lahir"),
                            rs.getString("jk"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("tanggal"),
                            rs.getString("keluhan_utama"),
                            rs.getString("riwayat_keluhan"),
                            rs.getString("riwayat_kesehatan"),
                            rs.getString("riwayat_kesehatan_lain"),
                            rs.getString("g"),
                            rs.getString("p"),
                            rs.getString("a"),
                            rs.getString("hpht"),
                            rs.getString("hpl"),
                            rs.getString("imunisasi"),
                            rs.getString("opname"),
                            rs.getString("opname_sakit"),
                            rs.getString("opname_rs"),
                            rs.getString("operasi"),
                            rs.getString("operasi_ket"),
                            rs.getString("pengobatan"),
                            rs.getString("pengobatan_ket"),
                            rs.getString("alergi"),
                            rs.getString("alergi_ket"),
                            rs.getString("pf_mata"),
                            rs.getString("pf_mata_lain"),
                            rs.getString("pf_thyroid"),
                            rs.getString("pf_kgb"),
                            rs.getString("pf_mammae"),
                            rs.getString("pf_mammae_lain"),
                            rs.getString("pf_puting"),
                            rs.getString("pf_colostrum"),
                            rs.getString("kesadaran"),
                            rs.getString("gcs"),
                            rs.getString("tb"),
                            rs.getString("bb_umum"),
                            rs.getString("suhu"),
                            rs.getString("td"),
                            rs.getString("nadi"),
                            rs.getString("rr"),
                            rs.getString("spo2"),
                            rs.getString("wajah"),
                            rs.getString("mata"),
                            rs.getString("mulut"),
                            rs.getString("payudara"),
                            rs.getString("fundus_uteri"),
                            rs.getString("kontraksi_uterus"),
                            rs.getString("kandung_kemih"),
                            rs.getString("luka_operasi"),
                            rs.getString("lokhea"),
                            rs.getString("luka_perineum"),
                            rs.getString("foley_catheter"),
                            rs.getString("ekstremitas"),
                            rs.getString("hb"),
                            rs.getString("ht"),
                            rs.getString("leukosit"),
                            rs.getString("trombosit"),
                            rs.getString("glukosa"),
                            rs.getString("urine"),
                            rs.getString("protein"),
                            rs.getString("keton"),
                            rs.getString("rontgen"),
                            rs.getString("usg"),
                            rs.getString("analisa"),
                            rs.getString("penatalaksana"),
                            rs.getString("masalah"),
                            rs.getString("tujuan"),
                            rs.getString("nip"),
                            rs.getString("nama")

                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        Analisa.setText("");
        KeluhanUtama.setText("");
        RiwayatKeluhan.setText("");
        RiwayatKesehatanLain.setText("");
        Gravida.setText("");
        Para.setText("");
        Abortus.setText("");
        HPHT.setText("");
        HPL.setText("");
        Imunisasi.setText("");
        OpnameSakit.setText("");
        OpnameRS.setText("");
        OperasiKet.setText("");
        PengobatanKet.setText("");
        AlergiKet.setText("");
        MataLain.setText("");
        MammaeLain.setText("");
        ChkDM.setSelected(false);
        ChkHT.setSelected(false);
        ChkTBC.setSelected(false);
        ChkAsma.setSelected(false);
        ChkGinjal.setSelected(false);
        ChkHepar.setSelected(false);
        ChkLain.setSelected(false);
        ChkOpnameTidak.setSelected(false);
        ChkOpnamePernah.setSelected(false);
        ChkOperasiTidak.setSelected(false);
        ChkOperasiPernah.setSelected(false);
        ChkPengobatanTidak.setSelected(false);
        ChkPengobatanPernah.setSelected(false);
        ChkAlergiTidak.setSelected(false);
        ChkAlergiAda.setSelected(false);
        ChkMataAnemia.setSelected(false);
        ChkMataIcterus.setSelected(false);
        ChkMataTAK.setSelected(false);
        ChkMataLain.setSelected(false);
        ChkThyroidMembesar.setSelected(false);
        ChkThyroidTidak.setSelected(false);
        ChkKGBMembesar.setSelected(false);
        ChkKGBTidak.setSelected(false);
        ChkMammaeLembek.setSelected(false);
        ChkMammaeKeras.setSelected(false);
        ChkMammaePanas.setSelected(false);
        ChkMammaeKemerahan.setSelected(false);
        ChkMammaeBenjolan.setSelected(false);
        ChkMammaeLain.setSelected(false);
        ChkPutingMenonjol.setSelected(false);
        ChkPutingDatar.setSelected(false);
        ChkColostrumLancar.setSelected(false);
        ChkColostrumBelum.setSelected(false);
        LokiaJenis.setText("");
        LokiaWarna.setText("");
        LokiaBanyak.setText("");
        VaginaWarna.setText("");
        EkstParaliseKet.setText("");
        ChkLokiaBauPos.setSelected(false);
        ChkLokiaBauNeg.setSelected(false);
        ChkPerineumUtuh.setSelected(false);
        ChkPerineumRuptur.setSelected(false);
        ChkVulvaVarises.setSelected(false);
        ChkVulvaEdema.setSelected(false);
        ChkVulvaCondyloma.setSelected(false);
        ChkBartholiniMembesar.setSelected(false);
        ChkBartholiniTidak.setSelected(false);
        ChkVaginaCairan.setSelected(false);
        ChkVaginaBauPos.setSelected(false);
        ChkVaginaBauNeg.setSelected(false);
        ChkVaginaProlapsus.setSelected(false);
        ChkVaginaTAK.setSelected(false);
        ChkAnusHemoroid.setSelected(false);
        ChkAnusTAK.setSelected(false);
        ChkEkstOedema.setSelected(false);
        ChkEkstVarises.setSelected(false);
        ChkEkstRefleks.setSelected(false);
        ChkEkstParalise.setSelected(false);
        ChkHomanYa.setSelected(false);
        ChkHomanTidak.setSelected(false);
        ChkNutrisiMual.setSelected(false);
        ChkNutrisiMuntah.setSelected(false);
        ChkNutrisiTidakNafsu.setSelected(false);
        ChkNutrisiPuasa.setSelected(false);
        ChkNutrisiTAK.setSelected(false);
        ChkBabKonstipasi.setSelected(false);
        ChkBabDiare.setSelected(false);
        ChkBabTAK.setSelected(false);
        ChkBakRetensi.setSelected(false);
        ChkBakKateter.setSelected(false);
        ChkBakOliguri.setSelected(false);
        ChkBakTAK.setSelected(false);
        ChkIstirahatInsomnia.setSelected(false);
        ChkIstirahatTidakAda.setSelected(false);
        ChkIstirahatLain.setSelected(false);
        ChkAktivitasMandiri.setSelected(false);
        ChkAktivitasBergantung.setSelected(false);
        ChkAktivitasPenuh.setSelected(false);
        ChkPsikoGembira.setSelected(false);
        ChkPsikoTenang.setSelected(false);
        ChkPsikoCemas.setSelected(false);
        ChkPsikoMenangis.setSelected(false);
        ChkPsikoLain.setSelected(false);
        ChkSupportKeluarga.setSelected(false);
        ChkSupportTeman.setSelected(false);
        ChkSupportLain.setSelected(false);
        ChkPenunjangLab.setSelected(false);
        ChkPenunjangRontgen.setSelected(false);
        ChkPenunjangUSG.setSelected(false);
        ChkPenunjangEKG.setSelected(false);
        ChkPenunjangLain.setSelected(false);
        ChkPenyuluhanPeraturan.setSelected(false);
        ChkPenyuluhanHak.setSelected(false);
        ChkPenyuluhanPerawatan.setSelected(false);
        ChkPengetahuanASI.setSelected(false);
        ChkPengetahuanKB.setSelected(false);
        ChkPengetahuanRawatTaliPusat.setSelected(false);
        ChkPengetahuanPijatBayi.setSelected(false);
        ChkPengetahuanLain.setSelected(false);
        ChkDPMinumObat.setSelected(false);
        ChkDPKonsul.setSelected(false);
        ChkDPImunisasi.setSelected(false);
        ChkDPMobilisasi.setSelected(false);
        ChkDPDiet.setSelected(false);
        ChkDPLain.setSelected(false);
        Masalah.setText("");
        Tujuan.setText("");
        Rontgen.setText("");
        USG.setText("");
        Kesadaran.setSelectedIndex(0);
        GCS.setText("");
        TB.setText("");
        BB.setText("");
        Suhu.setText("");
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        SPO.setText("");
        Wajah.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Mulut.setSelectedIndex(0);
        Payudara.setSelectedIndex(0);
        FundusUteri.setSelectedIndex(0);
        KontraksiUterus.setSelectedIndex(0);
        KandungKemih.setSelectedIndex(0);
        LukaOperasi.setSelectedIndex(0);
        Lokhea.setSelectedIndex(0);
        LukaPerineum.setSelectedIndex(0);
        FoleyCatheter.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Hb.setText("");
        Ht.setText("");
        Leukosit.setText("");
        Trombosit.setText("");
        Glukosa.setText("");
        Urine.setText("");
        Protein.setText("");
        Keton.setText("");
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TglLahir.setText("");
        Jk.setText("");
        NmDokter.setText("");
        KdDokter.setText("");
        NmPetugas.setText("");
        KdPetugas.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
    }

    private void hideSubjektifSection() {
        jLabel33.setVisible(false);
        jLabel43.setVisible(false);
        JenisPersalinan.setVisible(false);
        jLabel30.setVisible(false);
        SCAI.setVisible(false);
        jLabel56.setVisible(false);
        KeadaanAnak.setVisible(false);
        jLabel48.setVisible(false);
        JenisKelamin.setVisible(false);
        BB1.setVisible(false);
        jLabel27.setVisible(false);
        jLabel31.setVisible(false);
        PB.setVisible(false);
        jLabel32.setVisible(false);
        jLabel54.setVisible(false);
        KetubanPecah.setVisible(false);
        jLabel36.setVisible(false);
        wita.setVisible(false);
        jLabel37.setVisible(false);
        jLabel55.setVisible(false);
        Episiotomi.setVisible(false);
        jLabel64.setVisible(false);
        Pereneum.setVisible(false);
        jLabel60.setVisible(false);
        jLabel61.setVisible(false);
        jLabel62.setVisible(false);
        jLabel63.setVisible(false);
        jLabel65.setVisible(false);
        jLabel66.setVisible(false);
        jLabel67.setVisible(false);
        jLabel68.setVisible(false);
        jLabel69.setVisible(false);
        jLabel70.setVisible(false);
        jLabel71.setVisible(false);
        jLabel72.setVisible(false);
        jLabel73.setVisible(false);
        jLabel74.setVisible(false);
        jam1.setVisible(false);
        mnt1.setVisible(false);
        jam2.setVisible(false);
        mnt2.setVisible(false);
        jam3.setVisible(false);
        mnt3.setVisible(false);
        jam4.setVisible(false);
        mnt4.setVisible(false);
        jamtotal.setVisible(false);
        mnttotal.setVisible(false);
        darahan1.setVisible(false);
        darahan2.setVisible(false);
        darahan3.setVisible(false);
        darahan4.setVisible(false);
        darahantotal.setVisible(false);
        jLabel75.setVisible(false);
        jLabel76.setVisible(false);
        jLabel77.setVisible(false);
        jLabel78.setVisible(false);
        jLabel79.setVisible(false);
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            Valid.SetTgl2(TglAsuhan, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            RiwayatKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            loadRiwayatKesehatan(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            RiwayatKesehatanLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            Gravida.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            Para.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            Abortus.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            HPHT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            HPL.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Imunisasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString(), ChkOpnamePernah, "Pernah",
                    ChkOpnameTidak, "Tidak Pernah");
            OpnameSakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            OpnameRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString(), ChkOperasiPernah, "Pernah",
                    ChkOperasiTidak, "Tidak Pernah");
            OperasiKet.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString(), ChkPengobatanPernah, "Pernah",
                    ChkPengobatanTidak, "Tidak");
            PengobatanKet.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString(), ChkAlergiAda, "Ada", ChkAlergiTidak,
                    "Tidak Ada");
            AlergiKet.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            loadPemeriksaanMata(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            MataLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString(), ChkThyroidMembesar, "Membesar",
                    ChkThyroidTidak, "Tidak");
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString(), ChkKGBMembesar, "Membesar",
                    ChkKGBTidak, "Tidak");
            loadPemeriksaanMammae(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
            MammaeLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString(), ChkPutingMenonjol, "Menonjol",
                    ChkPutingDatar, "Datar");
            setPilihan(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString(), ChkColostrumLancar, "Lancar",
                    ChkColostrumBelum, "Belum Ada");
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 38).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 39).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 40).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 41).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString());
            SPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString());
            Wajah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 44).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 45).toString());
            Mulut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 46).toString());
            Payudara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 47).toString());
            FundusUteri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 48).toString());
            KontraksiUterus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 49).toString());
            KandungKemih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 50).toString());
            LukaOperasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString());
            Lokhea.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 52).toString());
            LukaPerineum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString());
            FoleyCatheter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 54).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 55).toString());
            Hb.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 56).toString());
            Ht.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 57).toString());
            Leukosit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 58).toString());
            Trombosit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 59).toString());
            Glukosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 60).toString());
            Urine.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 61).toString());
            Protein.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 62).toString());
            Keton.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 63).toString());
            Rontgen.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 64).toString());
            USG.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 65).toString());
            Analisa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 66).toString());
            Penatalaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 67).toString());
            Masalah.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 68).toString());
            Tujuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 69).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 70).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 71).toString());

        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "
                            +
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                            "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        BtnPrint.setEnabled(akses.gettindakan_ranap());
        if (akses.getjml2() >= 1) {
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NmPetugas, KdPetugas.getText());
            if (NmPetugas.getText().equals("")) {
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }

    }

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
        tampil();
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from asesmen_khusus_kebidanan_ibu_nifas where no_rawat=?", 1, new String[] {
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
        }) == true) {
            tampil();
            TabRawat.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if (Sequel.mengedittf("asesmen_khusus_kebidanan_ibu_nifas", "no_rawat=?",
                "tanggal=?,kd_dokter=?,keluhan_utama=?,riwayat_keluhan=?,riwayat_kesehatan=?,riwayat_kesehatan_lain=?,g=?,p=?,a=?,hpht=?,hpl=?,imunisasi=?,"
                        +
                        "opname=?,opname_sakit=?,opname_rs=?,operasi=?,operasi_ket=?,pengobatan=?,pengobatan_ket=?,alergi=?,alergi_ket=?,pf_mata=?,pf_mata_lain=?,pf_thyroid=?,pf_kgb=?,"
                        +
                        "pf_mammae=?,pf_mammae_lain=?,pf_puting=?,pf_colostrum=?,kesadaran=?,gcs=?,tb=?,bb_umum=?,suhu=?,td=?,nadi=?,rr=?,spo2=?,wajah=?,mata=?,mulut=?,payudara=?,"
                        +
                        "fundus_uteri=?,kontraksi_uterus=?,kandung_kemih=?,luka_operasi=?,lokhea=?,luka_perineum=?,foley_catheter=?,ekstremitas=?,hb=?,ht=?,leukosit=?,trombosit=?,"
                        +
                        "glukosa=?,urine=?,protein=?,keton=?,rontgen=?,usg=?,analisa=?,penatalaksana=?,masalah=?,tujuan=?,nip=?",
                66, new String[] {
                        Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " "
                                + TglAsuhan.getSelectedItem().toString().substring(11, 19),
                        KdDokter.getText(),
                        KeluhanUtama.getText(), RiwayatKeluhan.getText(), buildRiwayatKesehatan(),
                        RiwayatKesehatanLain.getText(), Gravida.getText(), Para.getText(), Abortus.getText(),
                        HPHT.getText(), HPL.getText(), Imunisasi.getText(),
                        getPilihan(ChkOpnamePernah, "Pernah", ChkOpnameTidak, "Tidak Pernah"), OpnameSakit.getText(),
                        OpnameRS.getText(),
                        getPilihan(ChkOperasiPernah, "Pernah", ChkOperasiTidak, "Tidak Pernah"), OperasiKet.getText(),
                        getPilihan(ChkPengobatanPernah, "Pernah", ChkPengobatanTidak, "Tidak"), PengobatanKet.getText(),
                        getPilihan(ChkAlergiAda, "Ada", ChkAlergiTidak, "Tidak Ada"), AlergiKet.getText(),
                        buildPemeriksaanMata(), MataLain.getText(),
                        getPilihan(ChkThyroidMembesar, "Membesar", ChkThyroidTidak, "Tidak"),
                        getPilihan(ChkKGBMembesar, "Membesar", ChkKGBTidak, "Tidak"),
                        buildPemeriksaanMammae(), MammaeLain.getText(),
                        getPilihan(ChkPutingMenonjol, "Menonjol", ChkPutingDatar, "Datar"),
                        getPilihan(ChkColostrumLancar, "Lancar", ChkColostrumBelum, "Belum Ada"),
                        Kesadaran.getSelectedItem().toString(), GCS.getText(), TB.getText(), BB.getText(),
                        Suhu.getText(), TD.getText(), Nadi.getText(), RR.getText(), SPO.getText(),
                        Wajah.getSelectedItem().toString(), Mata.getSelectedItem().toString(),
                        Mulut.getSelectedItem().toString(),
                        Payudara.getSelectedItem().toString(), FundusUteri.getSelectedItem().toString(),
                        KontraksiUterus.getSelectedItem().toString(), KandungKemih.getSelectedItem().toString(),
                        LukaOperasi.getSelectedItem().toString(), Lokhea.getSelectedItem().toString(),
                        LukaPerineum.getSelectedItem().toString(), FoleyCatheter.getSelectedItem().toString(),
                        Ekstremitas.getSelectedItem().toString(), Hb.getText(), Ht.getText(), Leukosit.getText(),
                        Trombosit.getText(), Glukosa.getText(), Urine.getText(), Protein.getText(), Keton.getText(),
                        Rontgen.getText(), USG.getText(), Analisa.getText(), Penatalaksana.getText(), Masalah.getText(),
                        Tujuan.getText(), KdPetugas.getText(),
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                }) == true) {
            tampil();
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private String buildRiwayatKesehatan() {
        return buildTokenList(
                new String[] { "DM", "HT", "TBC", "ASMA", "GINJAL", "HEPAR", "LAIN" },
                new widget.CekBox[] { ChkDM, ChkHT, ChkTBC, ChkAsma, ChkGinjal, ChkHepar, ChkLain });
    }

    private void loadRiwayatKesehatan(String data) {
        applyTokenList(
                data,
                new String[] { "DM", "HT", "TBC", "ASMA", "GINJAL", "HEPAR", "LAIN" },
                new widget.CekBox[] { ChkDM, ChkHT, ChkTBC, ChkAsma, ChkGinjal, ChkHepar, ChkLain });
    }

    private String buildPemeriksaanMata() {
        return buildTokenList(
                new String[] { "ANEMIA", "ICTERUS", "TAK", "LAIN" },
                new widget.CekBox[] { ChkMataAnemia, ChkMataIcterus, ChkMataTAK, ChkMataLain });
    }

    private void loadPemeriksaanMata(String data) {
        applyTokenList(
                data,
                new String[] { "ANEMIA", "ICTERUS", "TAK", "LAIN" },
                new widget.CekBox[] { ChkMataAnemia, ChkMataIcterus, ChkMataTAK, ChkMataLain });
    }

    private String buildPemeriksaanMammae() {
        return buildTokenList(
                new String[] { "LEMBEK", "KERAS", "PANAS", "KEMERAHAN", "BENJOLAN", "LAIN" },
                new widget.CekBox[] { ChkMammaeLembek, ChkMammaeKeras, ChkMammaePanas, ChkMammaeKemerahan,
                        ChkMammaeBenjolan, ChkMammaeLain });
    }

    private void loadPemeriksaanMammae(String data) {
        applyTokenList(
                data,
                new String[] { "LEMBEK", "KERAS", "PANAS", "KEMERAHAN", "BENJOLAN", "LAIN" },
                new widget.CekBox[] { ChkMammaeLembek, ChkMammaeKeras, ChkMammaePanas, ChkMammaeKemerahan,
                        ChkMammaeBenjolan, ChkMammaeLain });
    }

    private String buildTokenList(String[] tokens, widget.CekBox[] checks) {
        StringBuilder hasil = new StringBuilder();
        for (int idx = 0; idx < tokens.length; idx++) {
            if (checks[idx].isSelected()) {
                if (hasil.length() > 0) {
                    hasil.append(";");
                }
                hasil.append(tokens[idx]);
            }
        }
        return hasil.toString();
    }

    private void applyTokenList(String data, String[] tokens, widget.CekBox[] checks) {
        for (int idx = 0; idx < tokens.length; idx++) {
            checks[idx].setSelected(hasToken(data, tokens[idx]));
        }
    }

    private boolean hasToken(String data, String token) {
        if (data == null) {
            return false;
        }
        for (String bagian : data.split(";")) {
            if (bagian.trim().equalsIgnoreCase(token)) {
                return true;
            }
        }
        return false;
    }

    private String getPilihan(widget.CekBox utama, String nilaiUtama, widget.CekBox alternatif,
            String nilaiAlternatif) {
        if (utama.isSelected()) {
            return nilaiUtama;
        }
        if (alternatif.isSelected()) {
            return nilaiAlternatif;
        }
        // Default to alternatif to satisfy NOT NULL enum columns.
        return nilaiAlternatif;
    }

    private void setPilihan(String value, widget.CekBox utama, String nilaiUtama, widget.CekBox alternatif,
            String nilaiAlternatif) {
        utama.setSelected(nilaiUtama.equalsIgnoreCase(value));
        alternatif.setSelected(nilaiAlternatif.equalsIgnoreCase(value));
    }

    // @SuppressWarnings("empty-statement")
    // private void isTotal(){
    // jamtotal.setText((Integer.parseInt(jam1.getText())+(Integer.parseInt(jam2.getText())+(Integer.parseInt(jam3.getText())+(Integer.parseInt(jam4.getText()))))+i)+"");
    // }
}
