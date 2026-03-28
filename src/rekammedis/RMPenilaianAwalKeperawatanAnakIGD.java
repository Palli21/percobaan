package rekammedis;

import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.WarnaTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperCompileManager;
import widget.Button;
import widget.CekBox;
import widget.ComboBox;
import widget.InternalFrame;
import widget.ScrollPane;
import widget.Table;
import widget.Tanggal;
import widget.TextArea;
import widget.TextBox;
import widget.panelisi;

/**
 * Form baru berbasis PDF RM.2/UGD/ANK.
 */
public final class RMPenilaianAwalKeperawatanAnakIGD extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_PENILAIAN = "penilaian_awal_keperawatan_anak_igd";
    private static final Color CANVAS_BACKGROUND = new Color(244, 247, 250);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color CARD_BORDER = new Color(210, 221, 232);
    private static final Color TITLE_COLOR = new Color(52, 92, 138);
    private static final Color LABEL_COLOR = new Color(63, 74, 89);
    private static final Color HINT_COLOR = new Color(106, 118, 133);
    private static final Color BANNER_BACKGROUND = new Color(234, 243, 252);
    private static final Color BANNER_BORDER = new Color(188, 208, 229);
    private static final Color READ_ONLY_BACKGROUND = new Color(246, 249, 252);

    private static final String[] MINIMAL_LABELS = new String[]{
        "Kebersihan diri, mandi, ganti pakaian dilakukan sendiri",
        "Makan dan minum dilakukan sendiri",
        "Ambulasi dengan pengawasan",
        "Observasi tanda-tanda vital dilakukan setiap shift",
        "Pengobatan minimal, status psikologis stabil"
    };
    private static final String[] PARSIAL_LABELS = new String[]{
        "Kebersihan diri, makan dan minum dibantu",
        "Observasi tanda-tanda vital dilakukan setiap 4 jam",
        "Ambulasi dibantu, pengobatan lebih dari sekali",
        "Folley catheter, input-output dicatat",
        "Klien terpasang infus, persiapan pengobatan yang memerlukan prosedur"
    };
    private static final String[] TOTAL_LABELS = new String[]{
        "Semua kebutuhan klien dibantu",
        "Perubahan posisi dan observasi tanda-tanda vital tiap 2 jam",
        "Makan melalui NGT, terapi intravena",
        "Pemakaian suction",
        "Gelisah / disorientasi"
    };
    private static final String[] FLACC_LABELS = new String[]{
        "Wajah", "Kaki", "Aktivitas", "Tangis", "Bersuara"
    };

    private static final String[] KOLOM_PENILAIAN = new String[]{
        "no_rawat", "tanggal", "kategori_triage", "keluhan_saat_ini", "alergi_tidak_ada", "alergi_keterangan",
        "airway_bebas", "airway_gargling", "airway_stridor", "airway_wheezing", "airway_ronchi", "airway_terintubasi",
        "breathing_spontan", "breathing_dispneu", "breathing_ventilasi_mekanik", "breathing_tachipneu", "breathing_apneu", "breathing_memakai_ventilator",
        "nadi_kualitas", "crt", "warna_kulit", "perdarahan", "turgor_kulit", "respirasi", "tekanan_darah",
        "respon", "pupil", "refleks", "gcs_e", "gcs_v", "gcs_m", "nadi_neurologi", "suhu",
        "skrining_gizi1", "nilai_gizi1", "skrining_gizi2", "nilai_gizi2", "total_skor_gizi", "keterangan_gizi", "gizi_status_dietisien", "gizi_lapor", "gizi_tanggal", "gizi_jam",
        "alat_bantu_tidak", "alat_bantu_tongkat", "alat_bantu_kruk", "alat_bantu_kursi_roda", "alat_bantu_lain", "alat_bantu_lain_keterangan", "cacat_tubuh_status", "cacat_tubuh_keterangan",
        "perawatan_minimal_1", "perawatan_minimal_2", "perawatan_minimal_3", "perawatan_minimal_4", "perawatan_minimal_5",
        "perawatan_parsial_1", "perawatan_parsial_2", "perawatan_parsial_3", "perawatan_parsial_4", "perawatan_parsial_5",
        "perawatan_total_1", "perawatan_total_2", "perawatan_total_3", "perawatan_total_4", "perawatan_total_5", "klasifikasi_pasien", "aktivitas_pasien",
        "humpty_skala1", "humpty_nilai1", "humpty_skala2", "humpty_nilai2", "humpty_skala3", "humpty_nilai3", "humpty_skala4", "humpty_nilai4",
        "humpty_skala5", "humpty_nilai5", "humpty_skala6", "humpty_nilai6", "humpty_skala7", "humpty_nilai7", "humpty_total", "humpty_keterangan",
        "nrs", "wong_baker",
        "flacc_skala1", "flacc_nilai1", "flacc_skala2", "flacc_nilai2", "flacc_skala3", "flacc_nilai3", "flacc_skala4", "flacc_nilai4", "flacc_skala5", "flacc_nilai5", "flacc_total",
        "nyeri_frekuensi", "nyeri_lama", "nyeri_menjalar", "nyeri_menjalar_ke", "nyeri_kualitas", "nyeri_pencetus", "nyeri_pengurang", "lokasi_nyeri",
        "tindak_lanjut_edukasi", "tindak_lanjut_intervensi", "tindak_lanjut_konsul", "tindak_lanjut_konsul_ke",
        "psikologis_cemas", "psikologis_takut", "psikologis_marah", "psikologis_sedih", "psikologis_lain", "psikologis_lain_keterangan",
        "mental_orientasi_baik", "mental_masalah_perilaku", "mental_masalah_perilaku_keterangan", "mental_perilaku_kekerasan", "mental_perilaku_kekerasan_keterangan",
        "status_sosial", "tempat_tinggal", "tempat_tinggal_keterangan", "hubungan_keluarga", "ibadah_teratur",
        "tempat_ttd", "pasien_keluarga_ttd", "petugas_display", "nip"
    };

    private static final class ScoredChoice {
        private final String label;
        private final int score;

        private ScoredChoice(String label, int score) {
            this.label = label;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private final validasi Valid = new validasi();
    private final sekuel Sequel = new sekuel();
    private final Connection koneksi = koneksiDB.condb();
    private final SimpleDateFormat jamFormat = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat tanggalTampilFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat tanggalDatabaseFormat = new SimpleDateFormat("yyyy-MM-dd");
    private PreparedStatement ps;
    private ResultSet rs;
    private JPanel formBody;
    private DefaultTableModel tabMode;
    private Table tbData;
    private TextBox TCariData;
    private Button BtnRefreshData;
    private Button BtnHapus;
    private Button BtnSimpan;
    private Button BtnEdit;
    private Button BtnPrint;
    private String currentPetugasNip = "";

    private TextBox TNoRw;
    private TextBox TNoRM;
    private TextBox TPasien;
    private TextBox TglLahir;
    private TextBox JK;
    private Tanggal TglAsesmen;
    private TextBox JamAsesmen;
    private ComboBox KategoriTriage;
    private TextArea KeluhanSaatIni;
    private CekBox AlergiTidakAda;
    private TextBox AlergiSebutkan;

    private CekBox[] airwayChecks;
    private CekBox[] breathingChecks;
    private ComboBox NadiKualitas;
    private ComboBox CrtCombo;
    private ComboBox WarnaKulitCombo;
    private ComboBox PerdarahanCombo;
    private ComboBox TurgorCombo;
    private TextBox RespirasiField;
    private TextBox TekananDarahField;
    private ComboBox ResponCombo;
    private ComboBox PupilCombo;
    private TextBox RefleksField;
    private TextBox GcsEField;
    private TextBox GcsVField;
    private TextBox GcsMField;
    private TextBox NadiNeuroField;
    private TextBox SuhuField;

    private ComboBox SkriningGizi1;
    private ComboBox SkriningGizi2;
    private TextBox SkorGizi1;
    private TextBox SkorGizi2;
    private TextBox TotalSkorGizi;
    private TextBox KeteranganGizi;
    private ComboBox GiziStatusCombo;
    private ComboBox GiziLaporCombo;
    private TextBox GiziTanggalField;
    private TextBox GiziJamField;

    private CekBox AlatBantuTidak;
    private CekBox AlatBantuTongkat;
    private CekBox AlatBantuKruk;
    private CekBox AlatBantuKursiRoda;
    private CekBox AlatBantuLain;
    private TextBox AlatBantuLainText;
    private ComboBox CacatTubuhCombo;
    private TextBox CacatTubuhText;

    private ComboBox[] PerawatanMinimalCombo;
    private ComboBox[] PerawatanParsialCombo;
    private ComboBox[] PerawatanTotalCombo;
    private ComboBox KlasifikasiPasienCombo;
    private ComboBox AktivitasPasienCombo;

    private ComboBox[] HumptyCombo;
    private TextBox[] HumptyNilai;
    private TextBox TotalHumpty;
    private TextBox KeteranganHumpty;

    private ComboBox NrsCombo;
    private ComboBox WongBakerCombo;
    private ComboBox[] FlaccCombo;
    private TextBox[] FlaccNilai;
    private TextBox FlaccTotalField;
    private ComboBox NyeriFrekuensiCombo;
    private TextBox NyeriLamaField;
    private ComboBox NyeriMenjalarCombo;
    private TextBox NyeriMenjalarKeField;
    private ComboBox NyeriKualitasCombo;
    private TextArea NyeriPencetusArea;
    private TextArea NyeriPengurangArea;
    private TextArea LokasiNyeriArea;
    private CekBox TindakLanjutEdukasi;
    private CekBox TindakLanjutIntervensi;
    private CekBox TindakLanjutKonsul;
    private TextBox TindakLanjutKonsulKeField;

    private CekBox[] PsikologisChecks;
    private CekBox PsikologisLain;
    private TextBox PsikologisLainText;
    private CekBox MentalOrientasiBaik;
    private CekBox MentalMasalahPerilaku;
    private TextBox MentalMasalahText;
    private CekBox MentalPerilakuKekerasan;
    private TextBox MentalPerilakuKekerasanText;
    private TextBox StatusSosialField;
    private ComboBox TempatTinggalCombo;
    private TextBox TempatTinggalText;
    private TextBox HubunganKeluargaField;
    private ComboBox IbadahCombo;
    private TextBox TempatTtdField;
    private TextBox PasienKeluargaField;
    private TextBox PetugasField;

    public RMPenilaianAwalKeperawatanAnakIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initToolbarButtons();
        buildDynamicForm();
        initDataListTab();
        initDefaultValues();
        setSize(1280, 900);
        setLocationRelativeTo(null);
    }

    private void buildDynamicForm() {
        formBody = new JPanel();
        formBody.setLayout(new BoxLayout(formBody, BoxLayout.Y_AXIS));
        formBody.setBorder(new EmptyBorder(16, 16, 20, 16));
        formBody.setBackground(CANVAS_BACKGROUND);
        ScrollInput.setViewportView(formBody);

        formBody.add(createInfoBanner());
        formBody.add(Box.createVerticalStrut(14));
        formBody.add(createIdentitasSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createPrimarySurveySection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createSkriningGiziSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createStatusFungsionalSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createPerawatanSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createHumptySection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createNyeriSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createSosialPsikologiSection());
        formBody.add(Box.createVerticalGlue());
    }

    private JPanel createIdentitasSection() {
        JPanel section = createSectionPanel("A. Identitas Dan Triage");
        section.add(createSectionHint("Lengkapi identitas pasien, waktu pengkajian, kategori triage, keluhan, dan riwayat alergi sesuai formulir RM.2/UGD/ANK."));

        TNoRw = createTextBox(120);
        TNoRM = createTextBox(100);
        TPasien = createTextBox(240);
        TglLahir = createTextBox(110);
        JK = createTextBox(80);
        TglAsesmen = new Tanggal();
        TglAsesmen.setPreferredSize(new Dimension(140, 23));
        TglAsesmen.setMaximumSize(TglAsesmen.getPreferredSize());
        TglAsesmen.setBackground(Color.WHITE);
        JamAsesmen = createTextBox(80);
        KategoriTriage = createCombo(new String[]{"Pilih kategori triage", "P1", "P2", "P3", "P4", "P0"}, 180);
        KeluhanSaatIni = createTextArea(3, 90);
        AlergiTidakAda = createCheck("Tidak");
        AlergiSebutkan = createTextBox(340);

        section.add(createRow(
            field("No. Rawat", TNoRw),
            field("No. RM", TNoRM),
            field("Nama", TPasien),
            field("L/P", JK),
            field("Tgl. Lahir", TglLahir)
        ));
        section.add(createRow(
            field("Tanggal", TglAsesmen),
            field("Jam Pengkajian", JamAsesmen),
            field("Kategori Triage", KategoriTriage)
        ));
        section.add(createTextAreaRow("Keluhan saat ini / mekanisme kejadian", KeluhanSaatIni));
        section.add(createRow(
            field("Riwayat alergi tidak ada", AlergiTidakAda),
            field("Ya / sebutkan", AlergiSebutkan)
        ));
        return section;
    }

    private JPanel createPrimarySurveySection() {
        JPanel section = createSectionPanel("B. Keadaan Umum / Primary Survey");
        section.add(createSectionHint("Centang temuan pada airway dan breathing, lalu lengkapi sirkulasi serta disability / neurologi sesuai kondisi awal pasien."));

        airwayChecks = new CekBox[]{
            createCheck("Bebas"), createCheck("Gargling"), createCheck("Stridor"),
            createCheck("Wheezing"), createCheck("Ronchi"), createCheck("Terintubasi")
        };
        breathingChecks = new CekBox[]{
            createCheck("Spontan"), createCheck("Dispneu"), createCheck("Ventilasi mekanik"),
            createCheck("Tachipneu"), createCheck("Apneu"), createCheck("Memakai ventilator")
        };

        NadiKualitas = createCombo(new String[]{"Pilih", "Kuat", "Lemah"}, 130);
        CrtCombo = createCombo(new String[]{"Pilih", "Kurang dari 2 detik", "Lebih atau sama dengan 2 detik"}, 180);
        WarnaKulitCombo = createCombo(new String[]{"Pilih", "Normal", "Pucat", "Kuning"}, 140);
        PerdarahanCombo = createCombo(new String[]{"Pilih", "Tidak ada", "Terkontrol", "Tidak terkontrol"}, 170);
        TurgorCombo = createCombo(new String[]{"Pilih", "Baik", "Buruk"}, 120);
        RespirasiField = createTextBox(120);
        TekananDarahField = createTextBox(120);
        ResponCombo = createCombo(new String[]{"Pilih", "Alert", "Pain", "Verbal", "Unrespons"}, 140);
        PupilCombo = createCombo(new String[]{"Pilih", "Isokor", "Anisokor", "Pin Point", "Midriasis"}, 140);
        RefleksField = createTextBox(160);
        GcsEField = createTextBox(45);
        GcsVField = createTextBox(45);
        GcsMField = createTextBox(45);
        NadiNeuroField = createTextBox(120);
        SuhuField = createTextBox(120);

        section.add(createChecklistRow("Airway", airwayChecks, 3));
        section.add(createChecklistRow("Breathing", breathingChecks, 3));
        section.add(createRow(
            field("Nadi", NadiKualitas),
            field("CRT", CrtCombo),
            field("Warna kulit", WarnaKulitCombo),
            field("Perdarahan", PerdarahanCombo)
        ));
        section.add(createRow(
            field("Turgor kulit", TurgorCombo),
            field("Respirasi (kali/menit)", RespirasiField),
            field("Tekanan darah (mmHg)", TekananDarahField)
        ));
        section.add(createRow(
            field("Respon", ResponCombo),
            field("Pupil", PupilCombo),
            field("Refleks", RefleksField)
        ));
        section.add(createRow(
            field("GCS E", GcsEField),
            field("GCS V", GcsVField),
            field("GCS M", GcsMField),
            field("Nadi", NadiNeuroField),
            field("Suhu (C)", SuhuField)
        ));
        return section;
    }

    private JPanel createSkriningGiziSection() {
        JPanel section = createSectionPanel("C. Skrining Gizi Awal");
        section.add(createSectionHint("Skor gizi mengikuti formulir PDF. Total dan keterangannya dihitung otomatis ketika pilihan diisi."));
        ItemListener giziListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateGiziScore();
                }
            }
        };

        SkriningGizi1 = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih penurunan berat badan", 0),
            new ScoredChoice("Tidak ada penurunan berat badan", 0),
            new ScoredChoice("Tidak yakin / tidak tahu", 2),
            new ScoredChoice("Penurunan 1 sampai 5 Kg", 1),
            new ScoredChoice("Penurunan 6 sampai 10 Kg", 2),
            new ScoredChoice("Penurunan 11 sampai 15 Kg", 3),
            new ScoredChoice("Penurunan lebih atau sama dengan 15 Kg", 4)
        }, giziListener, 430);
        SkriningGizi2 = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih status asupan makan", 0),
            new ScoredChoice("Ya, asupan makan berkurang", 1),
            new ScoredChoice("Tidak", 0)
        }, giziListener, 430);
        SkorGizi1 = createReadOnlyBox(50);
        SkorGizi2 = createReadOnlyBox(50);
        TotalSkorGizi = createReadOnlyBox(60);
        KeteranganGizi = createReadOnlyBox(360);
        GiziStatusCombo = createCombo(new String[]{
            "Pilih status dietisien",
            "Sudah dibaca dan diketahui oleh dietisien",
            "Dietisien diberitahukan ke dokter"
        }, 310);
        GiziLaporCombo = createCombo(new String[]{"Pilih", "Ya", "Tidak"}, 100);
        GiziTanggalField = createTextBox(120);
        GiziJamField = createTextBox(80);

        section.add(createRow(
            field("1. Penurunan BB tidak diinginkan dalam 3 bulan terakhir", SkriningGizi1),
            field("Skor", SkorGizi1)
        ));
        section.add(createRow(
            field("2. Asupan makan berkurang karena tidak nafsu makan", SkriningGizi2),
            field("Skor", SkorGizi2)
        ));
        section.add(createRow(
            field("Total skor", TotalSkorGizi),
            field("Keterangan", KeteranganGizi)
        ));
        section.add(createRow(
            field("Diisi oleh dietisien", GiziStatusCombo),
            field("Status", GiziLaporCombo),
            field("Tanggal", GiziTanggalField),
            field("Jam", GiziJamField)
        ));
        return section;
    }

    private JPanel createStatusFungsionalSection() {
        JPanel section = createSectionPanel("D. Status Fungsional");
        section.add(createSectionHint("Isi penggunaan alat bantu dan kondisi cacat tubuh sesuai bagian A pada halaman lanjutan PDF."));

        AlatBantuTidak = createCheck("Tidak");
        AlatBantuTongkat = createCheck("Tongkat");
        AlatBantuKruk = createCheck("Kruk");
        AlatBantuKursiRoda = createCheck("Kursi roda");
        AlatBantuLain = createCheck("Lain-lain");
        AlatBantuLainText = createTextBox(240);
        CacatTubuhCombo = createCombo(new String[]{"Pilih", "Tidak", "Ada"}, 100);
        CacatTubuhText = createTextBox(320);

        section.add(createRow(
            field("Penggunaan alat bantu", createInlinePanel(
                AlatBantuTidak,
                AlatBantuTongkat,
                AlatBantuKruk,
                AlatBantuKursiRoda,
                AlatBantuLain
            )),
            field("Keterangan lain", AlatBantuLainText)
        ));
        section.add(createRow(
            field("Cacat tubuh", CacatTubuhCombo),
            field("Sebutkan", CacatTubuhText)
        ));
        return section;
    }

    private JPanel createPerawatanSection() {
        JPanel section = createSectionPanel("E. Pengkajian Kebutuhan Perawatan Harian");
        section.add(createSectionHint("Isi penilaian perawatan minimal, parsial, atau total sesuai baris kriteria pada formulir."));

        PerawatanMinimalCombo = createCriteriaCombos(MINIMAL_LABELS.length);
        PerawatanParsialCombo = createCriteriaCombos(PARSIAL_LABELS.length);
        PerawatanTotalCombo = createCriteriaCombos(TOTAL_LABELS.length);
        KlasifikasiPasienCombo = createCombo(new String[]{"Pilih klasifikasi", "Minimal", "Parsial", "Total"}, 170);
        AktivitasPasienCombo = createCombo(new String[]{"Pilih aktivitas", "Minimal", "Tergantung sebagian", "Tergantung penuh"}, 190);

        section.add(createCriteriaGroup("Perawatan Minimal", MINIMAL_LABELS, PerawatanMinimalCombo));
        section.add(createCriteriaGroup("Perawatan Parsial", PARSIAL_LABELS, PerawatanParsialCombo));
        section.add(createCriteriaGroup("Perawatan Total", TOTAL_LABELS, PerawatanTotalCombo));
        section.add(createRow(
            field("Klasifikasi pasien", KlasifikasiPasienCombo),
            field("Aktivitas", AktivitasPasienCombo)
        ));
        return section;
    }

    private JPanel createHumptySection() {
        JPanel section = createSectionPanel("F. Pengkajian Risiko Jatuh Pasien Anak (Humpty Dumpty)");
        section.add(createSectionHint("Pilih kategori yang paling sesuai untuk setiap parameter. Nilai per parameter dan total Humpty dihitung otomatis."));
        ItemListener humptyListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateHumptyScore();
                }
            }
        };

        HumptyCombo = new ComboBox[7];
        HumptyNilai = new TextBox[7];
        HumptyCombo[0] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih umur", 0),
            new ScoredChoice("Di bawah 3 tahun", 4),
            new ScoredChoice("3 sampai 7 tahun", 3),
            new ScoredChoice("7 sampai 13 tahun", 2),
            new ScoredChoice("Lebih dari 13 tahun", 1)
        }, humptyListener, 360);
        HumptyCombo[1] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih jenis kelamin", 0),
            new ScoredChoice("Laki-laki", 2),
            new ScoredChoice("Perempuan", 1)
        }, humptyListener, 360);
        HumptyCombo[2] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih diagnosa", 0),
            new ScoredChoice("Kelainan neurologi", 4),
            new ScoredChoice("Perubahan dalam oksigen / gangguan pernapasan", 3),
            new ScoredChoice("Kelainan psikis / perilaku", 2),
            new ScoredChoice("Diagnosis lain", 1)
        }, humptyListener, 360);
        HumptyCombo[3] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih gangguan kognitif", 0),
            new ScoredChoice("Tidak sadar terhadap keterbatasan", 3),
            new ScoredChoice("Lupa keterbatasan", 2),
            new ScoredChoice("Mengetahui kemampuan diri", 1)
        }, humptyListener, 360);
        HumptyCombo[4] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih faktor lingkungan", 0),
            new ScoredChoice("Riwayat jatuh dari tempat tidur bayi / anak", 4),
            new ScoredChoice("Menggunakan alat bantu / box / mebel", 3),
            new ScoredChoice("Berada di tempat tidur", 2),
            new ScoredChoice("Di luar ruang rawat", 1)
        }, humptyListener, 360);
        HumptyCombo[5] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih respon operasi / obat / anestesi", 0),
            new ScoredChoice("Dalam 24 jam", 3),
            new ScoredChoice("Dalam 48 jam dengan riwayat jatuh", 2),
            new ScoredChoice("Lebih dari 48 jam", 1)
        }, humptyListener, 360);
        HumptyCombo[6] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih penggunaan obat", 0),
            new ScoredChoice("Bermacam-macam obat / sedatif / hipnotik / narkotik", 3),
            new ScoredChoice("Salah satu dari pengobatan di atas", 2),
            new ScoredChoice("Pengobatan lain", 1)
        }, humptyListener, 360);

        for (int i = 0; i < HumptyNilai.length; i++) {
            HumptyNilai[i] = createReadOnlyBox(45);
        }
        TotalHumpty = createReadOnlyBox(60);
        KeteranganHumpty = createReadOnlyBox(360);

        section.add(createRow(field("1. Umur", HumptyCombo[0]), field("Skor", HumptyNilai[0])));
        section.add(createRow(field("2. Jenis kelamin", HumptyCombo[1]), field("Skor", HumptyNilai[1])));
        section.add(createRow(field("3. Diagnosa", HumptyCombo[2]), field("Skor", HumptyNilai[2])));
        section.add(createRow(field("4. Gangguan kognitif", HumptyCombo[3]), field("Skor", HumptyNilai[3])));
        section.add(createRow(field("5. Faktor lingkungan", HumptyCombo[4]), field("Skor", HumptyNilai[4])));
        section.add(createRow(field("6. Respon terhadap operasi / obat / anestesi", HumptyCombo[5]), field("Skor", HumptyNilai[5])));
        section.add(createRow(field("7. Penggunaan obat", HumptyCombo[6]), field("Skor", HumptyNilai[6])));
        section.add(createRow(field("Skor total", TotalHumpty), field("Keterangan", KeteranganHumpty)));
        return section;
    }

    private JPanel createNyeriSection() {
        JPanel section = createSectionPanel("G. Pengkajian Skala Nyeri");
        section.add(createSectionHint("Tampilkan kedua blok nyeri sesuai PDF: NRS / Wong Baker untuk anak > 3 tahun dan FLACC untuk anak < 3 tahun. Total FLACC tetap diisi manual sesuai formulir."));
        section.add(createImagePanel("/picture/skala_nyeri.png", 900, 420, "Referensi gambar skala nyeri"));
        ItemListener flaccListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateFlaccScores();
                }
            }
        };

        NrsCombo = createCombo(new String[]{
            "Pilih NRS/VAS", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
        }, 160);
        WongBakerCombo = createCombo(new String[]{
            "Pilih Wong Baker",
            "0 - Tidak nyeri",
            "2 - Sedikit nyeri",
            "4 - Agak mengganggu",
            "6 - Mengganggu aktivitas",
            "8 - Sangat mengganggu",
            "10 - Tak tertahankan"
        }, 220);

        FlaccCombo = new ComboBox[5];
        FlaccNilai = new TextBox[5];
        FlaccCombo[0] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih wajah", 0),
            new ScoredChoice("Tidak ada ekspresi tertentu atau senyum", 0),
            new ScoredChoice("Sesekali meringis atau menarik diri", 1),
            new ScoredChoice("Sering gemetaran dagu atau rahang terkatup", 2)
        }, flaccListener, 420);
        FlaccCombo[1] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih kaki", 0),
            new ScoredChoice("Posisi normal atau santai", 0),
            new ScoredChoice("Gelisah, khawatir, tegang", 1),
            new ScoredChoice("Menendang atau menarik kaki", 2)
        }, flaccListener, 420);
        FlaccCombo[2] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih aktivitas", 0),
            new ScoredChoice("Berbaring tenang, posisi normal, mudah bergerak", 0),
            new ScoredChoice("Menggeliat, mondar-mandir, tegang", 1),
            new ScoredChoice("Melengkung, kaku atau menyentak", 2)
        }, flaccListener, 420);
        FlaccCombo[3] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih tangis", 0),
            new ScoredChoice("Tidak teriak / tidak menangis", 0),
            new ScoredChoice("Mengerang atau merengek, sesekali mengeluh", 1),
            new ScoredChoice("Menangis terus-menerus atau menjerit", 2)
        }, flaccListener, 420);
        FlaccCombo[4] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih bersuara", 0),
            new ScoredChoice("Puas / senang / santai", 0),
            new ScoredChoice("Tenang bila dipeluk / digendong / diajak bicara", 1),
            new ScoredChoice("Sulit untuk dibujuk atau merasa tidak nyaman", 2)
        }, flaccListener, 420);
        for (int i = 0; i < FlaccNilai.length; i++) {
            FlaccNilai[i] = createReadOnlyBox(45);
        }
        FlaccTotalField = createTextBox(80);

        NyeriFrekuensiCombo = createCombo(new String[]{"Pilih frekuensi", "Jarang", "Hilang timbul", "Terus menerus"}, 170);
        NyeriLamaField = createTextBox(180);
        NyeriMenjalarCombo = createCombo(new String[]{"Pilih", "Tidak", "Ya"}, 100);
        NyeriMenjalarKeField = createTextBox(180);
        NyeriKualitasCombo = createCombo(new String[]{"Pilih kualitas", "Nyeri tumpul", "Nyeri tajam", "Panas terbakar"}, 170);
        NyeriPencetusArea = createTextArea(2, 90);
        NyeriPengurangArea = createTextArea(2, 90);
        LokasiNyeriArea = createTextArea(2, 90);
        TindakLanjutEdukasi = createCheck("Edukasi");
        TindakLanjutIntervensi = createCheck("Intervensi");
        TindakLanjutKonsul = createCheck("Konsul ke");
        TindakLanjutKonsulKeField = createTextBox(220);

        section.add(createRow(
            field("NRS / VAS", NrsCombo),
            field("Wong Baker", WongBakerCombo)
        ));
        section.add(createSectionHint("Skala FLACC untuk anak < 3 tahun."));
        for (int i = 0; i < FlaccCombo.length; i++) {
            section.add(createRow(field((i + 1) + ". " + FLACC_LABELS[i], FlaccCombo[i]), field("Nilai", FlaccNilai[i])));
        }
        section.add(createRow(field("Total FLACC", FlaccTotalField)));
        section.add(createRow(
            field("Frekuensi nyeri", NyeriFrekuensiCombo),
            field("Lama nyeri", NyeriLamaField),
            field("Menjalar", NyeriMenjalarCombo),
            field("Ke", NyeriMenjalarKeField)
        ));
        section.add(createRow(field("Kualitas nyeri", NyeriKualitasCombo)));
        section.add(createTextAreaRow("Faktor pencetus / memperberat", NyeriPencetusArea));
        section.add(createTextAreaRow("Faktor yang mengurangi / menghilangkan nyeri", NyeriPengurangArea));
        section.add(createTextAreaRow("Lokasi nyeri", LokasiNyeriArea));
        section.add(createRow(
            field("Tindak lanjut", createInlinePanel(TindakLanjutEdukasi, TindakLanjutIntervensi, TindakLanjutKonsul)),
            field("Konsul ke", TindakLanjutKonsulKeField)
        ));
        return section;
    }

    private JPanel createSosialPsikologiSection() {
        JPanel section = createSectionPanel("H. Hubungan Status Sosial Psikologi Dan Tanda Tangan");
        section.add(createSectionHint("Lengkapi status psikologis, keadaan mental, sosial, kebiasaan ibadah, serta identitas penandatangan formulir."));

        PsikologisChecks = new CekBox[]{
            createCheck("Cemas"), createCheck("Takut"), createCheck("Marah"), createCheck("Sedih")
        };
        PsikologisLain = createCheck("Lain-lain");
        PsikologisLainText = createTextBox(240);
        MentalOrientasiBaik = createCheck("Sadar dan orientasi baik");
        MentalMasalahPerilaku = createCheck("Ada masalah perilaku");
        MentalMasalahText = createTextBox(240);
        MentalPerilakuKekerasan = createCheck("Perilaku kekerasan sebelumnya");
        MentalPerilakuKekerasanText = createTextBox(240);
        StatusSosialField = createTextBox(220);
        TempatTinggalCombo = createCombo(new String[]{"Pilih tempat tinggal", "Rumah", "Panti", "Lainnya"}, 150);
        TempatTinggalText = createTextBox(220);
        HubunganKeluargaField = createTextBox(280);
        IbadahCombo = createCombo(new String[]{"Pilih", "Ya", "Tidak"}, 100);
        TempatTtdField = createTextBox(180);
        PasienKeluargaField = createTextBox(220);
        PetugasField = createTextBox(220);

        JPanel psikologisPanel = createInlinePanel(
            PsikologisChecks[0], PsikologisChecks[1], PsikologisChecks[2], PsikologisChecks[3], PsikologisLain
        );
        section.add(createRow(
            field("Status psikologis", psikologisPanel),
            field("Sebutkan", PsikologisLainText)
        ));
        section.add(createRow(
            field("Keadaan mental", createInlinePanel(MentalOrientasiBaik, MentalMasalahPerilaku)),
            field("Masalah perilaku", MentalMasalahText)
        ));
        section.add(createRow(
            field("Perilaku kekerasan yang dialami sebelumnya", MentalPerilakuKekerasan),
            field("Keterangan", MentalPerilakuKekerasanText)
        ));
        section.add(createRow(
            field("Status sosial", StatusSosialField),
            field("Tempat tinggal", TempatTinggalCombo),
            field("Keterangan tempat tinggal", TempatTinggalText)
        ));
        section.add(createRow(field("Hubungan pasien dengan anggota keluarga", HubunganKeluargaField)));
        section.add(createRow(field("Kebiasaan beribadah teratur (umur 12 tahun ke atas)", IbadahCombo)));
        section.add(createRow(
            field("Tempat", TempatTtdField),
            field("Pasien / Keluarga", PasienKeluargaField),
            field("Petugas", PetugasField)
        ));
        return section;
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BACKGROUND);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(sectionBorder(title));
        return panel;
    }

    private Border sectionBorder(String title) {
        javax.swing.border.TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CARD_BORDER),
            title,
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Tahoma", Font.BOLD, 12),
            TITLE_COLOR
        );
        return BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(12, 12, 12, 12));
    }

    private JPanel createInfoBanner() {
        JPanel banner = new JPanel(new BorderLayout(0, 6));
        banner.setOpaque(true);
        banner.setBackground(BANNER_BACKGROUND);
        banner.setAlignmentX(Component.LEFT_ALIGNMENT);
        banner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BANNER_BORDER),
            new EmptyBorder(12, 14, 12, 14)
        ));

        JLabel title = new JLabel("Panduan Pengisian Asesmen Anak IGD");
        title.setFont(new Font("Tahoma", Font.BOLD, 13));
        title.setForeground(TITLE_COLOR);

        JLabel description = new JLabel("<html>Form ini disusun mengikuti PDF RM.2/UGD/ANK. Total skrining gizi dan Humpty Dumpty dihitung otomatis, sedangkan skala nyeri tetap dapat diisi sesuai formulir.</html>");
        description.setFont(new Font("Tahoma", Font.PLAIN, 11));
        description.setForeground(HINT_COLOR);

        banner.add(title, BorderLayout.NORTH);
        banner.add(description, BorderLayout.CENTER);
        return banner;
    }

    private JLabel createSectionHint(String text) {
        JLabel hint = new JLabel("<html>" + text + "</html>");
        hint.setFont(new Font("Tahoma", Font.PLAIN, 11));
        hint.setForeground(HINT_COLOR);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        hint.setBorder(new EmptyBorder(0, 0, 10, 0));
        return hint;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.BOLD, 11));
        label.setForeground(LABEL_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createRow(Component... components) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 8));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(new EmptyBorder(0, 0, 2, 0));
        for (int i = 0; i < components.length; i++) {
            row.add(components[i]);
        }
        return row;
    }

    private JPanel createTextAreaRow(String label, TextArea area) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(new EmptyBorder(0, 0, 4, 0));
        JLabel lbl = createFieldLabel(label);
        JScrollPane scroll = new JScrollPane(area);
        int height = Math.max(60, area.getRows() * 24);
        scroll.setPreferredSize(new Dimension(980, height));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(lbl);
        row.add(Box.createVerticalStrut(6));
        row.add(scroll);
        return row;
    }

    private JPanel createChecklistRow(String label, CekBox[] boxes, int columns) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.setBorder(new EmptyBorder(0, 0, 4, 0));
        if (label != null) {
            wrapper.add(createFieldLabel(label));
            wrapper.add(Box.createVerticalStrut(6));
        }
        JPanel checkPanel = new JPanel(new GridLayout(0, columns, 12, 8));
        checkPanel.setOpaque(false);
        checkPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (int i = 0; i < boxes.length; i++) {
            checkPanel.add(boxes[i]);
        }
        wrapper.add(checkPanel);
        return wrapper;
    }

    private JPanel field(String labelText, JComponent component) {
        JPanel field = new JPanel();
        field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
        field.setOpaque(false);
        field.setBorder(new EmptyBorder(0, 0, 0, 2));
        JLabel label = createFieldLabel(labelText);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setMaximumSize(component.getPreferredSize());
        field.add(label);
        field.add(Box.createVerticalStrut(4));
        field.add(component);
        return field;
    }

    private JPanel createInlinePanel(Component... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (int i = 0; i < components.length; i++) {
            panel.add(components[i]);
        }
        return panel;
    }

    private JPanel createImagePanel(String resourcePath, int maxWidth, int maxHeight, String title) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel caption = createFieldLabel(title);
        caption.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(caption);
        wrapper.add(Box.createVerticalStrut(6));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(READ_ONLY_BACKGROUND);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER),
            new EmptyBorder(8, 8, 8, 8)
        ));

        JLabel imageLabel = new JLabel("Gambar skala nyeri tidak ditemukan", SwingConstants.CENTER);
        imageLabel.setForeground(HINT_COLOR);
        imageLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        imageLabel.setOpaque(false);

        try {
            java.net.URL imageUrl = getClass().getResource(resourcePath);
            if (imageUrl != null) {
                ImageIcon sourceIcon = new ImageIcon(imageUrl);
                int imageWidth = sourceIcon.getIconWidth();
                int imageHeight = sourceIcon.getIconHeight();
                if ((imageWidth > 0) && (imageHeight > 0)) {
                    double scale = Math.min((double) maxWidth / (double) imageWidth, (double) maxHeight / (double) imageHeight);
                    scale = Math.min(scale, 1.0d);
                    int scaledWidth = Math.max(1, (int) Math.round(imageWidth * scale));
                    int scaledHeight = Math.max(1, (int) Math.round(imageHeight * scale));
                    Image scaledImage = sourceIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    imageLabel.setText("");
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

        card.add(imageLabel, BorderLayout.CENTER);
        wrapper.add(card);
        return wrapper;
    }

    private TextBox createTextBox(int width) {
        TextBox box = new TextBox();
        box.setPreferredSize(new Dimension(width, 23));
        box.setMaximumSize(box.getPreferredSize());
        return box;
    }

    private TextBox createReadOnlyBox(int width) {
        TextBox box = createTextBox(width);
        box.setEditable(false);
        box.setHorizontalAlignment(SwingConstants.CENTER);
        box.setBackground(READ_ONLY_BACKGROUND);
        return box;
    }

    private TextArea createTextArea(int rows, int columns) {
        TextArea area = new TextArea();
        area.setRows(rows);
        area.setColumns(columns);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(4, 4, 4, 4));
        area.setBackground(Color.WHITE);
        return area;
    }

    private ComboBox createCombo(String[] values, int width) {
        ComboBox combo = new ComboBox();
        combo.setModel(new DefaultComboBoxModel(values));
        combo.setPreferredSize(new Dimension(width, 23));
        combo.setMaximumSize(combo.getPreferredSize());
        return combo;
    }

    private ComboBox createScoredCombo(ScoredChoice[] choices, ItemListener listener, int width) {
        ComboBox combo = new ComboBox();
        combo.setModel(new DefaultComboBoxModel(choices));
        combo.setPreferredSize(new Dimension(width, 23));
        combo.setMaximumSize(combo.getPreferredSize());
        combo.addItemListener(listener);
        return combo;
    }

    private ComboBox[] createCriteriaCombos(int size) {
        ComboBox[] combos = new ComboBox[size];
        for (int i = 0; i < size; i++) {
            combos[i] = createCombo(new String[]{"Pilih", "Ya", "Tidak"}, 90);
        }
        return combos;
    }

    private JPanel createCriteriaGroup(String title, String[] labels, ComboBox[] combos) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.setBorder(new EmptyBorder(0, 0, 8, 0));
        JLabel titleLabel = createFieldLabel(title);
        titleLabel.setBorder(new EmptyBorder(0, 0, 6, 0));
        wrapper.add(titleLabel);
        for (int i = 0; i < labels.length; i++) {
            wrapper.add(createRow(field((i + 1) + ". " + labels[i], combos[i])));
        }
        return wrapper;
    }

    private CekBox createCheck(String label) {
        CekBox check = new CekBox();
        check.setText(label);
        check.setOpaque(false);
        check.setFont(new Font("Tahoma", Font.PLAIN, 11));
        return check;
    }

    private void initDefaultValues() {
        TglAsesmen.setDate(new Date());
        JamAsesmen.setText(jamFormat.format(new Date()));
        resetFormForNewEntry();
        tampilData();
    }

    private void initToolbarButtons() {
        BtnSimpan = createToolbarButton("/picture/save-16x16.png", "Simpan", 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BtnSimpanActionPerformed(e);
            }
        });
        BtnEdit = createToolbarButton("/picture/inventaris.png", "Edit", 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BtnEditActionPerformed(e);
            }
        });
        BtnPrint = createToolbarButton("/picture/b_print.png", "Cetak", 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BtnPrintActionPerformed(e);
            }
        });
        BtnHapus = createToolbarButton("/picture/stop_f2.png", "Hapus", 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BtnHapusActionPerformed(e);
            }
        });

        panelButtons.add(BtnPrint, 0);
        panelButtons.add(BtnHapus, 0);
        panelButtons.add(BtnEdit, 0);
        panelButtons.add(BtnSimpan, 0);
    }

    private Button createToolbarButton(String iconPath, String text, int width, ActionListener listener) {
        Button button = new Button();
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        button.setText(text);
        button.setPreferredSize(new Dimension(width, 30));
        button.addActionListener(listener);
        return button;
    }

    private void initDataListTab() {
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "L/P", "Tgl.Lahir",
            "Tgl.Asesmen", "Triage", "Keluhan", "Humpty", "Nyeri", "Petugas"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbData = new Table();
        tbData.setModel(tabMode);
        tbData.setPreferredScrollableViewportSize(new Dimension(500, 300));
        tbData.setAutoResizeMode(Table.AUTO_RESIZE_OFF);
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if ((evt.getClickCount() == 2) && (tbData.getSelectedRow() > -1)) {
                    loadSelectedData();
                }
            }
        });
        tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if ((evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) && (tbData.getSelectedRow() > -1)) {
                    evt.consume();
                    loadSelectedData();
                }
            }
        });

        int[] widths = new int[]{120, 90, 220, 80, 95, 130, 70, 260, 70, 120, 160};
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        TCariData = createTextBox(260);
        TCariData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilData();
            }
        });

        BtnRefreshData = new Button();
        BtnRefreshData.setText("Tampilkan");
        BtnRefreshData.setPreferredSize(new Dimension(110, 30));
        BtnRefreshData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilData();
            }
        });

        JLabel info = new JLabel("Klik 2x data untuk melihat detail pasien");
        info.setForeground(new Color(70, 70, 70));
        info.setFont(new Font("Tahoma", Font.PLAIN, 11));

        topPanel.add(createFieldLabel("Cari No. Rawat / No. RM / Nama"));
        topPanel.add(TCariData);
        topPanel.add(BtnRefreshData);
        topPanel.add(info);

        InternalFrame dataFrame = new InternalFrame();
        dataFrame.setBorder(null);
        dataFrame.setLayout(new BorderLayout());
        dataFrame.add(topPanel, BorderLayout.NORTH);
        dataFrame.add(new JScrollPane(tbData), BorderLayout.CENTER);

        TabRawat.addTab("Data Pasien", dataFrame);
    }

    private void resetFormForNewEntry() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TglLahir.setText("");
        JK.setText("");
        resetAssessmentFields();
    }

    private void resetAssessmentFields() {
        KategoriTriage.setSelectedIndex(0);
        KeluhanSaatIni.setText("");
        AlergiTidakAda.setSelected(true);
        AlergiSebutkan.setText("");

        resetChecks(airwayChecks);
        resetChecks(breathingChecks);
        NadiKualitas.setSelectedIndex(0);
        CrtCombo.setSelectedIndex(0);
        WarnaKulitCombo.setSelectedIndex(0);
        PerdarahanCombo.setSelectedIndex(0);
        TurgorCombo.setSelectedIndex(0);
        RespirasiField.setText("");
        TekananDarahField.setText("");
        ResponCombo.setSelectedIndex(0);
        PupilCombo.setSelectedIndex(0);
        RefleksField.setText("");
        GcsEField.setText("");
        GcsVField.setText("");
        GcsMField.setText("");
        NadiNeuroField.setText("");
        SuhuField.setText("");

        SkriningGizi1.setSelectedIndex(0);
        SkriningGizi2.setSelectedIndex(0);
        GiziStatusCombo.setSelectedIndex(0);
        GiziLaporCombo.setSelectedIndex(0);
        GiziTanggalField.setText("");
        GiziJamField.setText("");
        updateGiziScore();

        AlatBantuTidak.setSelected(false);
        AlatBantuTongkat.setSelected(false);
        AlatBantuKruk.setSelected(false);
        AlatBantuKursiRoda.setSelected(false);
        AlatBantuLain.setSelected(false);
        AlatBantuLainText.setText("");
        CacatTubuhCombo.setSelectedIndex(0);
        CacatTubuhText.setText("");

        resetComboArray(PerawatanMinimalCombo);
        resetComboArray(PerawatanParsialCombo);
        resetComboArray(PerawatanTotalCombo);
        KlasifikasiPasienCombo.setSelectedIndex(0);
        AktivitasPasienCombo.setSelectedIndex(0);

        resetComboArray(HumptyCombo);
        updateHumptyScore();

        NrsCombo.setSelectedIndex(0);
        WongBakerCombo.setSelectedIndex(0);
        resetComboArray(FlaccCombo);
        updateFlaccScores();
        FlaccTotalField.setText("");
        NyeriFrekuensiCombo.setSelectedIndex(0);
        NyeriLamaField.setText("");
        NyeriMenjalarCombo.setSelectedIndex(0);
        NyeriMenjalarKeField.setText("");
        NyeriKualitasCombo.setSelectedIndex(0);
        NyeriPencetusArea.setText("");
        NyeriPengurangArea.setText("");
        LokasiNyeriArea.setText("");
        TindakLanjutEdukasi.setSelected(false);
        TindakLanjutIntervensi.setSelected(false);
        TindakLanjutKonsul.setSelected(false);
        TindakLanjutKonsulKeField.setText("");

        resetChecks(PsikologisChecks);
        PsikologisLain.setSelected(false);
        PsikologisLainText.setText("");
        MentalOrientasiBaik.setSelected(false);
        MentalMasalahPerilaku.setSelected(false);
        MentalMasalahText.setText("");
        MentalPerilakuKekerasan.setSelected(false);
        MentalPerilakuKekerasanText.setText("");
        StatusSosialField.setText("");
        TempatTinggalCombo.setSelectedIndex(0);
        TempatTinggalText.setText("");
        HubunganKeluargaField.setText("");
        IbadahCombo.setSelectedIndex(0);
        TempatTtdField.setText("Bungi");
        PasienKeluargaField.setText("");
        if (akses.getjml2() >= 1) {
            String namaPetugas = Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", akses.getkode());
            if (namaPetugas == null || namaPetugas.trim().equals("")) {
                PetugasField.setText(akses.getkode());
            } else {
                PetugasField.setText(namaPetugas);
            }
        } else {
            PetugasField.setText("");
        }
        renderPreview();
    }

    private void resetChecks(CekBox[] checks) {
        for (int i = 0; i < checks.length; i++) {
            checks[i].setSelected(false);
        }
    }

    private void resetComboArray(ComboBox[] combos) {
        for (int i = 0; i < combos.length; i++) {
            combos[i].setSelectedIndex(0);
        }
    }

    private void updateGiziScore() {
        int skor1 = getChoiceScore(SkriningGizi1);
        int skor2 = getChoiceScore(SkriningGizi2);
        int total = skor1 + skor2;
        SkorGizi1.setText(String.valueOf(skor1));
        SkorGizi2.setText(String.valueOf(skor2));
        TotalSkorGizi.setText(String.valueOf(total));
        if ((SkriningGizi1.getSelectedIndex() == 0) && (SkriningGizi2.getSelectedIndex() == 0)) {
            KeteranganGizi.setText("Belum dinilai");
        } else if (total >= 2) {
            KeteranganGizi.setText("Perlu pengkajian lanjutan oleh ahli gizi");
        } else {
            KeteranganGizi.setText("Belum perlu pengkajian lanjutan");
        }
    }

    private void updateHumptyScore() {
        int total = 0;
        boolean lengkap = true;
        for (int i = 0; i < HumptyCombo.length; i++) {
            int skor = getChoiceScore(HumptyCombo[i]);
            HumptyNilai[i].setText(String.valueOf(skor));
            total += skor;
            if (HumptyCombo[i].getSelectedIndex() == 0) {
                lengkap = false;
            }
        }
        TotalHumpty.setText(String.valueOf(total));
        if (!lengkap && total == 0) {
            KeteranganHumpty.setText("Belum dinilai");
        } else if (!lengkap) {
            KeteranganHumpty.setText("Isian belum lengkap");
        } else if (total >= 12) {
            KeteranganHumpty.setText("Risiko Tinggi (12 - 23)");
        } else if (total >= 7) {
            KeteranganHumpty.setText("Risiko Rendah (7 - 11)");
        } else {
            KeteranganHumpty.setText("Skor di bawah rentang formulir");
        }
    }

    private void updateFlaccScores() {
        for (int i = 0; i < FlaccCombo.length; i++) {
            FlaccNilai[i].setText(String.valueOf(getChoiceScore(FlaccCombo[i])));
        }
    }

    private int getChoiceScore(ComboBox combo) {
        Object selected = combo.getSelectedItem();
        if (selected instanceof ScoredChoice) {
            return ((ScoredChoice) selected).getScore();
        }
        return 0;
    }

    private void renderPreview() {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family:Tahoma;font-size:11px;'>");
        html.append("<h2>Penilaian Awal Keperawatan Anak IGD</h2>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "No. Rawat", safeText(TNoRw));
        appendRow(html, "No. RM", safeText(TNoRM));
        appendRow(html, "Nama", safeText(TPasien));
        appendRow(html, "L/P", safeText(JK));
        appendRow(html, "Tgl. Lahir", safeText(TglLahir));
        appendRow(html, "Tanggal Asesmen", formatTanggal(TglAsesmen));
        appendRow(html, "Jam Pengkajian", safeText(JamAsesmen));
        appendRow(html, "Kategori Triage", selectedText(KategoriTriage));
        appendRow(html, "Keluhan / mekanisme kejadian", safeText(KeluhanSaatIni));
        appendRow(html, "Riwayat alergi", AlergiTidakAda.isSelected() ? "Tidak" : safeText(AlergiSebutkan));
        html.append("</table>");

        html.append("<h3>Keadaan Umum</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Airway", joinSelected(airwayChecks));
        appendRow(html, "Breathing", joinSelected(breathingChecks));
        appendRow(html, "Sirkulasi", buildCirculationSummary());
        appendRow(html, "Neurologis", buildNeurologiSummary());
        html.append("</table>");

        html.append("<h3>Skrining Gizi</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Gizi", buildGiziSummary());
        html.append("</table>");

        html.append("<h3>Status Fungsional Dan Perawatan Harian</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Status fungsional", buildFungsionalSummary());
        appendRow(html, "Perawatan harian", buildPerawatanSummary());
        html.append("</table>");

        html.append("<h3>Humpty Dumpty</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Humpty", buildHumptySummary());
        html.append("</table>");

        html.append("<h3>Nyeri</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "NRS / Wong Baker", buildNyeriAnakSummary());
        appendRow(html, "FLACC", buildFlaccSummary());
        appendRow(html, "Detail nyeri", buildNyeriDetailSummary());
        html.append("</table>");

        html.append("<h3>Sosial Psikologi</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Psikologis", buildPsikologisSummary());
        appendRow(html, "Mental", buildMentalSummary());
        appendRow(html, "Sosial", buildSosialSummary());
        appendRow(html, "Tempat / TTD", buildTandaTanganSummary());
        html.append("</table>");

        html.append("</body></html>");
        PreviewPane.setText(html.toString());
        PreviewPane.setCaretPosition(0);
    }

    private void appendRow(StringBuilder html, String key, String value) {
        html.append("<tr>")
            .append("<td style='width:220px;border:1px solid #d7dfe7;background:#f7fafc;vertical-align:top;'><b>")
            .append(escapeHtml(key))
            .append("</b></td>")
            .append("<td style='border:1px solid #d7dfe7;vertical-align:top;'>")
            .append(escapeHtml(value))
            .append("</td>")
            .append("</tr>");
    }

    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("\n", "<br>");
    }

    private String safeText(JComponent component) {
        if (component == null) {
            return "";
        } else if (component instanceof TextBox) {
            return ((TextBox) component).getText().trim();
        } else if (component instanceof TextArea) {
            return ((TextArea) component).getText().trim();
        }
        return "";
    }

    private String selectedText(ComboBox combo) {
        if (combo == null || combo.getSelectedItem() == null) {
            return "";
        }
        return combo.getSelectedItem().toString();
    }

    private String joinSelected(CekBox[] checks) {
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < checks.length; i++) {
            if (checks[i].isSelected()) {
                values.add(checks[i].getText());
            }
        }
        return joinValues(values);
    }

    private String joinValues(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "-";
        }
        StringBuilder joined = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                joined.append(", ");
            }
            joined.append(values.get(i));
        }
        return joined.toString();
    }

    private String buildCirculationSummary() {
        return "Nadi: " + selectedText(NadiKualitas)
            + ", CRT: " + selectedText(CrtCombo)
            + ", Warna kulit: " + selectedText(WarnaKulitCombo)
            + ", Perdarahan: " + selectedText(PerdarahanCombo)
            + ", Turgor: " + selectedText(TurgorCombo)
            + ", Respirasi: " + safeText(RespirasiField)
            + ", TD: " + safeText(TekananDarahField);
    }

    private String buildNeurologiSummary() {
        return "Respon: " + selectedText(ResponCombo)
            + ", Pupil: " + selectedText(PupilCombo)
            + ", Refleks: " + safeText(RefleksField)
            + ", GCS E/V/M: " + safeText(GcsEField) + "/" + safeText(GcsVField) + "/" + safeText(GcsMField)
            + ", Nadi: " + safeText(NadiNeuroField)
            + ", Suhu: " + safeText(SuhuField);
    }

    private String buildGiziSummary() {
        return "1) " + selectedText(SkriningGizi1) + " = " + safeText(SkorGizi1)
            + "; 2) " + selectedText(SkriningGizi2) + " = " + safeText(SkorGizi2)
            + "; Total: " + safeText(TotalSkorGizi) + " (" + safeText(KeteranganGizi) + ")"
            + "; Dietisien: " + selectedText(GiziStatusCombo)
            + "; Status: " + selectedText(GiziLaporCombo)
            + (safeText(GiziTanggalField).equals("") ? "" : "; Tgl: " + safeText(GiziTanggalField))
            + (safeText(GiziJamField).equals("") ? "" : "; Jam: " + safeText(GiziJamField));
    }

    private String buildFungsionalSummary() {
        List<String> alatBantu = new ArrayList<String>();
        if (AlatBantuTidak.isSelected()) {
            alatBantu.add("Tidak");
        }
        if (AlatBantuTongkat.isSelected()) {
            alatBantu.add("Tongkat");
        }
        if (AlatBantuKruk.isSelected()) {
            alatBantu.add("Kruk");
        }
        if (AlatBantuKursiRoda.isSelected()) {
            alatBantu.add("Kursi roda");
        }
        if (AlatBantuLain.isSelected()) {
            alatBantu.add("Lain-lain: " + safeText(AlatBantuLainText));
        }
        String cacat = selectedText(CacatTubuhCombo);
        if ("Ada".equalsIgnoreCase(cacat) && !safeText(CacatTubuhText).equals("")) {
            cacat = cacat + " (" + safeText(CacatTubuhText) + ")";
        }
        return "Alat bantu: " + joinValues(alatBantu) + "; Cacat tubuh: " + (cacat.equals("") ? "-" : cacat);
    }

    private String buildPerawatanSummary() {
        return "Minimal: " + buildCriteriaSummary(MINIMAL_LABELS, PerawatanMinimalCombo)
            + "; Parsial: " + buildCriteriaSummary(PARSIAL_LABELS, PerawatanParsialCombo)
            + "; Total: " + buildCriteriaSummary(TOTAL_LABELS, PerawatanTotalCombo)
            + "; Klasifikasi: " + selectedText(KlasifikasiPasienCombo)
            + "; Aktivitas: " + selectedText(AktivitasPasienCombo);
    }

    private String buildCriteriaSummary(String[] labels, ComboBox[] combos) {
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < labels.length; i++) {
            String pilihan = comboDbValue(combos[i]);
            if (!pilihan.equals("")) {
                values.add((i + 1) + ". " + labels[i] + " (" + pilihan + ")");
            }
        }
        return joinValues(values);
    }

    private String buildHumptySummary() {
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < HumptyCombo.length; i++) {
            values.add((i + 1) + ") " + selectedText(HumptyCombo[i]) + " [" + safeText(HumptyNilai[i]) + "]");
        }
        return joinValues(values) + "; Total: " + safeText(TotalHumpty) + " (" + safeText(KeteranganHumpty) + ")";
    }

    private String buildNyeriAnakSummary() {
        return "NRS/VAS: " + selectedText(NrsCombo) + "; Wong Baker: " + selectedText(WongBakerCombo);
    }

    private String buildFlaccSummary() {
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < FlaccCombo.length; i++) {
            values.add(FLACC_LABELS[i] + ": " + selectedText(FlaccCombo[i]) + " [" + safeText(FlaccNilai[i]) + "]");
        }
        values.add("Total: " + safeText(FlaccTotalField));
        return joinValues(values);
    }

    private String buildNyeriDetailSummary() {
        List<String> tindakLanjut = new ArrayList<String>();
        if (TindakLanjutEdukasi.isSelected()) {
            tindakLanjut.add("Edukasi");
        }
        if (TindakLanjutIntervensi.isSelected()) {
            tindakLanjut.add("Intervensi");
        }
        if (TindakLanjutKonsul.isSelected()) {
            String konsul = "Konsul";
            if (!safeText(TindakLanjutKonsulKeField).equals("")) {
                konsul = konsul + " ke " + safeText(TindakLanjutKonsulKeField);
            }
            tindakLanjut.add(konsul);
        }
        return "Frekuensi: " + selectedText(NyeriFrekuensiCombo)
            + "; Lama: " + safeText(NyeriLamaField)
            + "; Menjalar: " + selectedText(NyeriMenjalarCombo)
            + (safeText(NyeriMenjalarKeField).equals("") ? "" : " ke " + safeText(NyeriMenjalarKeField))
            + "; Kualitas: " + selectedText(NyeriKualitasCombo)
            + "; Faktor pencetus: " + safeText(NyeriPencetusArea)
            + "; Faktor pengurang: " + safeText(NyeriPengurangArea)
            + "; Lokasi: " + safeText(LokasiNyeriArea)
            + "; Tindak lanjut: " + joinValues(tindakLanjut);
    }

    private String buildPsikologisSummary() {
        List<String> values = new ArrayList<String>();
        values.add(joinSelected(PsikologisChecks));
        if (PsikologisLain.isSelected()) {
            values.add("Lain-lain: " + safeText(PsikologisLainText));
        }
        return joinValues(values);
    }

    private String buildMentalSummary() {
        List<String> values = new ArrayList<String>();
        if (MentalOrientasiBaik.isSelected()) {
            values.add("Sadar dan orientasi baik");
        }
        if (MentalMasalahPerilaku.isSelected()) {
            values.add("Ada masalah perilaku: " + safeText(MentalMasalahText));
        }
        if (MentalPerilakuKekerasan.isSelected()) {
            values.add("Perilaku kekerasan sebelumnya: " + safeText(MentalPerilakuKekerasanText));
        }
        return joinValues(values);
    }

    private String buildSosialSummary() {
        String tempatTinggal = selectedText(TempatTinggalCombo);
        if ("Lainnya".equalsIgnoreCase(tempatTinggal) && !safeText(TempatTinggalText).equals("")) {
            tempatTinggal = tempatTinggal + " (" + safeText(TempatTinggalText) + ")";
        }
        return "Status sosial: " + safeText(StatusSosialField)
            + "; Tempat tinggal: " + tempatTinggal
            + "; Hubungan keluarga: " + safeText(HubunganKeluargaField)
            + "; Ibadah teratur: " + selectedText(IbadahCombo);
    }

    private String buildTandaTanganSummary() {
        return "Tempat: " + safeText(TempatTtdField)
            + "; Pasien/Keluarga: " + safeText(PasienKeluargaField)
            + "; Petugas: " + safeText(PetugasField);
    }

    private String formatTanggal(Tanggal tanggal) {
        try {
            return tanggalTampilFormat.format(tanggal.getDate());
        } catch (Exception e) {
            return "";
        }
    }

    private String formatTanggalDatabase(Tanggal tanggal) {
        try {
            return tanggalDatabaseFormat.format(tanggal.getDate());
        } catch (Exception e) {
            return "";
        }
    }

    private String comboDbValue(ComboBox combo) {
        if (combo == null || combo.getSelectedItem() == null) {
            return "";
        }
        String value = combo.getSelectedItem().toString().trim();
        if ((combo.getSelectedIndex() == 0) && value.toLowerCase().startsWith("pilih")) {
            return "";
        }
        return value;
    }

    private String yaTidakComboDbValue(ComboBox combo) {
        String value = comboDbValue(combo);
        return value.equals("") ? "Tidak" : value;
    }

    private String yaTidak(CekBox check) {
        return (check != null && check.isSelected()) ? "Ya" : "Tidak";
    }

    private String nilaiAtauNol(TextBox box) {
        String value = safeText(box);
        return value.equals("") ? "0" : value;
    }

    private String waktuSimpan() {
        String jam = safeText(JamAsesmen);
        if (jam.equals("")) {
            jam = jamFormat.format(new Date());
            JamAsesmen.setText(jam);
        }
        if (jam.length() == 5) {
            return jam + ":00";
        }
        return jam;
    }

    private String tanggalSimpan() {
        return formatTanggalDatabase(TglAsesmen) + " " + waktuSimpan();
    }

    private String resolvePetugasNip() {
        if (akses.getjml2() >= 1 && akses.getkode() != null) {
            currentPetugasNip = akses.getkode();
        }
        return currentPetugasNip == null ? "" : currentPetugasNip.trim();
    }

    private List<String> kumpulkanDataPenilaian() {
        List<String> data = new ArrayList<String>();
        data.add(safeText(TNoRw));
        data.add(tanggalSimpan());
        data.add(comboDbValue(KategoriTriage));
        data.add(safeText(KeluhanSaatIni));
        data.add(yaTidak(AlergiTidakAda));
        data.add(safeText(AlergiSebutkan));

        data.add(yaTidak(airwayChecks[0]));
        data.add(yaTidak(airwayChecks[1]));
        data.add(yaTidak(airwayChecks[2]));
        data.add(yaTidak(airwayChecks[3]));
        data.add(yaTidak(airwayChecks[4]));
        data.add(yaTidak(airwayChecks[5]));

        data.add(yaTidak(breathingChecks[0]));
        data.add(yaTidak(breathingChecks[1]));
        data.add(yaTidak(breathingChecks[2]));
        data.add(yaTidak(breathingChecks[3]));
        data.add(yaTidak(breathingChecks[4]));
        data.add(yaTidak(breathingChecks[5]));

        data.add(comboDbValue(NadiKualitas));
        data.add(comboDbValue(CrtCombo));
        data.add(comboDbValue(WarnaKulitCombo));
        data.add(comboDbValue(PerdarahanCombo));
        data.add(comboDbValue(TurgorCombo));
        data.add(safeText(RespirasiField));
        data.add(safeText(TekananDarahField));
        data.add(comboDbValue(ResponCombo));
        data.add(comboDbValue(PupilCombo));
        data.add(safeText(RefleksField));
        data.add(safeText(GcsEField));
        data.add(safeText(GcsVField));
        data.add(safeText(GcsMField));
        data.add(safeText(NadiNeuroField));
        data.add(safeText(SuhuField));

        data.add(comboDbValue(SkriningGizi1));
        data.add(nilaiAtauNol(SkorGizi1));
        data.add(comboDbValue(SkriningGizi2));
        data.add(nilaiAtauNol(SkorGizi2));
        data.add(nilaiAtauNol(TotalSkorGizi));
        data.add(safeText(KeteranganGizi));
        data.add(comboDbValue(GiziStatusCombo));
        data.add(comboDbValue(GiziLaporCombo));
        data.add(safeText(GiziTanggalField));
        data.add(safeText(GiziJamField));

        data.add(yaTidak(AlatBantuTidak));
        data.add(yaTidak(AlatBantuTongkat));
        data.add(yaTidak(AlatBantuKruk));
        data.add(yaTidak(AlatBantuKursiRoda));
        data.add(yaTidak(AlatBantuLain));
        data.add(safeText(AlatBantuLainText));
        data.add(comboDbValue(CacatTubuhCombo));
        data.add(safeText(CacatTubuhText));

        for (int i = 0; i < PerawatanMinimalCombo.length; i++) {
            data.add(yaTidakComboDbValue(PerawatanMinimalCombo[i]));
        }
        for (int i = 0; i < PerawatanParsialCombo.length; i++) {
            data.add(yaTidakComboDbValue(PerawatanParsialCombo[i]));
        }
        for (int i = 0; i < PerawatanTotalCombo.length; i++) {
            data.add(yaTidakComboDbValue(PerawatanTotalCombo[i]));
        }
        data.add(comboDbValue(KlasifikasiPasienCombo));
        data.add(comboDbValue(AktivitasPasienCombo));

        for (int i = 0; i < HumptyCombo.length; i++) {
            data.add(comboDbValue(HumptyCombo[i]));
            data.add(nilaiAtauNol(HumptyNilai[i]));
        }
        data.add(nilaiAtauNol(TotalHumpty));
        data.add(safeText(KeteranganHumpty));

        data.add(comboDbValue(NrsCombo));
        data.add(comboDbValue(WongBakerCombo));
        for (int i = 0; i < FlaccCombo.length; i++) {
            data.add(comboDbValue(FlaccCombo[i]));
            data.add(nilaiAtauNol(FlaccNilai[i]));
        }
        data.add(safeText(FlaccTotalField));

        data.add(comboDbValue(NyeriFrekuensiCombo));
        data.add(safeText(NyeriLamaField));
        data.add(comboDbValue(NyeriMenjalarCombo));
        data.add(safeText(NyeriMenjalarKeField));
        data.add(comboDbValue(NyeriKualitasCombo));
        data.add(safeText(NyeriPencetusArea));
        data.add(safeText(NyeriPengurangArea));
        data.add(safeText(LokasiNyeriArea));
        data.add(yaTidak(TindakLanjutEdukasi));
        data.add(yaTidak(TindakLanjutIntervensi));
        data.add(yaTidak(TindakLanjutKonsul));
        data.add(safeText(TindakLanjutKonsulKeField));

        data.add(yaTidak(PsikologisChecks[0]));
        data.add(yaTidak(PsikologisChecks[1]));
        data.add(yaTidak(PsikologisChecks[2]));
        data.add(yaTidak(PsikologisChecks[3]));
        data.add(yaTidak(PsikologisLain));
        data.add(safeText(PsikologisLainText));
        data.add(yaTidak(MentalOrientasiBaik));
        data.add(yaTidak(MentalMasalahPerilaku));
        data.add(safeText(MentalMasalahText));
        data.add(yaTidak(MentalPerilakuKekerasan));
        data.add(safeText(MentalPerilakuKekerasanText));
        data.add(safeText(StatusSosialField));
        data.add(comboDbValue(TempatTinggalCombo));
        data.add(safeText(TempatTinggalText));
        data.add(safeText(HubunganKeluargaField));
        data.add(comboDbValue(IbadahCombo));
        data.add(safeText(TempatTtdField));
        data.add(safeText(PasienKeluargaField));
        data.add(safeText(PetugasField));
        data.add(resolvePetugasNip());
        return data;
    }

    private String buatSqlInsert() {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < KOLOM_PENILAIAN.length; i++) {
            if (i > 0) {
                columns.append(",");
                values.append(",");
            }
            columns.append(KOLOM_PENILAIAN[i]);
            values.append("?");
        }
        return "insert into " + TABLE_PENILAIAN + " (" + columns.toString() + ") values (" + values.toString() + ")";
    }

    private String buatSqlUpdate() {
        StringBuilder update = new StringBuilder();
        for (int i = 1; i < KOLOM_PENILAIAN.length; i++) {
            if (i > 1) {
                update.append(",");
            }
            update.append(KOLOM_PENILAIAN[i]).append("=?");
        }
        return "update " + TABLE_PENILAIAN + " set " + update.toString() + " where no_rawat=?";
    }

    private boolean penilaianSudahAda(String noRawat) {
        PreparedStatement checkPs = null;
        ResultSet checkRs = null;
        try {
            checkPs = koneksi.prepareStatement("select no_rawat from " + TABLE_PENILAIAN + " where no_rawat=?");
            checkPs.setString(1, noRawat);
            checkRs = checkPs.executeQuery();
            return checkRs.next();
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            return false;
        } finally {
            try {
                if (checkRs != null) {
                    checkRs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
            try {
                if (checkPs != null) {
                    checkPs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private boolean validasiForm() {
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No. Rawat");
            return false;
        }
        if (TNoRM.getText().trim().equals("")) {
            isRawat();
        }
        return true;
    }

    private boolean simpanPenilaian() {
        List<String> data = kumpulkanDataPenilaian();
        PreparedStatement simpanPs = null;
        try {
            simpanPs = koneksi.prepareStatement(buatSqlInsert());
            for (int i = 0; i < data.size(); i++) {
                if ("nip".equals(KOLOM_PENILAIAN[i]) && data.get(i).trim().equals("")) {
                    simpanPs.setNull(i + 1, Types.VARCHAR);
                } else {
                    simpanPs.setString(i + 1, data.get(i));
                }
            }
            simpanPs.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(rootPane, "Maaf, data penilaian gagal disimpan.");
            return false;
        } finally {
            try {
                if (simpanPs != null) {
                    simpanPs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private boolean editPenilaian() {
        List<String> data = kumpulkanDataPenilaian();
        PreparedStatement editPs = null;
        try {
            editPs = koneksi.prepareStatement(buatSqlUpdate());
            for (int i = 1; i < data.size(); i++) {
                if ("nip".equals(KOLOM_PENILAIAN[i]) && data.get(i).trim().equals("")) {
                    editPs.setNull(i, Types.VARCHAR);
                } else {
                    editPs.setString(i, data.get(i));
                }
            }
            editPs.setString(data.size(), data.get(0));
            editPs.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(rootPane, "Maaf, data penilaian gagal diubah.");
            return false;
        } finally {
            try {
                if (editPs != null) {
                    editPs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private boolean hapusPenilaian() {
        PreparedStatement hapusPs = null;
        try {
            hapusPs = koneksi.prepareStatement("delete from " + TABLE_PENILAIAN + " where no_rawat=?");
            hapusPs.setString(1, TNoRw.getText());
            return hapusPs.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(rootPane, "Maaf, data penilaian gagal dihapus.");
            return false;
        } finally {
            try {
                if (hapusPs != null) {
                    hapusPs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private boolean siapkanReportJasper(String reportBaseName) {
        try {
            File jrxml = new File("./report/" + reportBaseName + ".jrxml");
            File jasper = new File("./report/" + reportBaseName + ".jasper");
            if (jasper.exists() && jasper.lastModified() >= jrxml.lastModified()) {
                return true;
            }
            if (!jrxml.exists()) {
                JOptionPane.showMessageDialog(rootPane, "File report source tidak ditemukan : " + jrxml.getPath());
                return false;
            }
            System.setProperty("net.sf.jasperreports.compiler.java", "net.sf.jasperreports.engine.design.JRJavacCompiler");
            System.setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJavacCompiler");
            JasperCompileManager.compileReportToFile(jrxml.getPath(), jasper.getPath());
            return true;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(rootPane, "Maaf, report Jasper gagal disiapkan.");
            return false;
        }
    }

    private Map<String, Object> buildReportParameters(String noRawat) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
        String namaPetugas = Sequel.cariIsi(
                "select ifnull(nullif(p.nama,''),ifnull(nullif(pa.petugas_display,''),'')) from " + TABLE_PENILAIAN + " pa left join petugas p on p.nip=pa.nip where pa.no_rawat=?",
                noRawat
        ).trim();
        String nipPetugas = Sequel.cariIsi(
                "select ifnull(nullif(nip,''),'') from " + TABLE_PENILAIAN + " where no_rawat=?",
                noRawat
        ).trim();
        String tanggalAsesmen = Sequel.cariIsi(
                "select ifnull(date_format(tanggal,'%Y-%m-%d %H:%i:%s'),'') from " + TABLE_PENILAIAN + " where no_rawat=?",
                noRawat
        ).trim();
        if (namaPetugas.equals("")) {
            namaPetugas = safeText(PetugasField);
        }
        if (nipPetugas.equals("")) {
            nipPetugas = currentPetugasNip == null ? "" : currentPetugasNip.trim();
        }
        if (tanggalAsesmen.equals("")) {
            tanggalAsesmen = formatTanggalDatabase(TglAsesmen) + " " + waktuSimpan();
        }
        String finger = "";
        if (!nipPetugas.equals("")) {
            finger = Sequel.cariIsi(
                    "select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    nipPetugas
            ).trim();
        }
        String identitasTtd = finger.equals("") ? (nipPetugas.equals("") ? namaPetugas : nipPetugas) : finger;
        String tanggalTtd = tanggalAsesmen.equals("") ? "-" : Valid.SetTgl3(tanggalAsesmen);
        param.put(
                "finger",
                "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                + "\nDitandatangani secara elektronik oleh " + (namaPetugas.equals("") ? "-" : namaPetugas)
                + "\nID " + (identitasTtd.equals("") ? "-" : identitasTtd)
                + "\n" + tanggalTtd
        );
        return param;
    }

    private static String sqlEscapeStatic(String value) {
        return value == null ? "" : value.replace("'", "''");
    }

    public static String buildRingkasanQuery(String noRawat) {
        String rawat = sqlEscapeStatic(noRawat);
        return "select rp.no_rawat,ps.no_rkm_medis,ps.nm_pasien," +
                "if(ps.jk='L','Laki-Laki','Perempuan') as jk," +
                "date_format(ps.tgl_lahir,'%d-%m-%Y') as tgl_lahir_text," +
                "date_format(pa.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal_text," +
                "ifnull(nullif(p.nama,''),ifnull(nullif(pa.petugas_display,''),'-')) as nama_petugas," +
                "ifnull(nullif(pa.kategori_triage,''),'-') as kategori_triage," +
                "ifnull(nullif(pa.keluhan_saat_ini,''),'-') as keluhan_saat_ini," +
                "if(pa.alergi_tidak_ada='Ya','Tidak',ifnull(nullif(pa.alergi_keterangan,''),'-')) as alergi_info," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.airway_bebas='Ya','Bebas',null)," +
                "if(pa.airway_gargling='Ya','Gargling',null)," +
                "if(pa.airway_stridor='Ya','Stridor',null)," +
                "if(pa.airway_wheezing='Ya','Wheezing',null)," +
                "if(pa.airway_ronchi='Ya','Ronchi',null)," +
                "if(pa.airway_terintubasi='Ya','Terintubasi',null)),''),'-') as airway_summary," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.breathing_spontan='Ya','Spontan',null)," +
                "if(pa.breathing_dispneu='Ya','Dispneu',null)," +
                "if(pa.breathing_ventilasi_mekanik='Ya','Ventilasi mekanik',null)," +
                "if(pa.breathing_tachipneu='Ya','Tachipneu',null)," +
                "if(pa.breathing_apneu='Ya','Apneu',null)," +
                "if(pa.breathing_memakai_ventilator='Ya','Memakai ventilator',null)),''),'-') as breathing_summary," +
                "concat('Nadi: ',ifnull(nullif(pa.nadi_kualitas,''),'-'),', CRT: ',ifnull(nullif(pa.crt,''),'-')," +
                "', Warna kulit: ',ifnull(nullif(pa.warna_kulit,''),'-'),', Perdarahan: ',ifnull(nullif(pa.perdarahan,''),'-')," +
                "', Turgor: ',ifnull(nullif(pa.turgor_kulit,''),'-'),', Respirasi: ',ifnull(nullif(pa.respirasi,''),'-'),', TD: ',ifnull(nullif(pa.tekanan_darah,''),'-')) as circulation_summary," +
                "concat('Respon: ',ifnull(nullif(pa.respon,''),'-'),', Pupil: ',ifnull(nullif(pa.pupil,''),'-'),', Refleks: ',ifnull(nullif(pa.refleks,''),'-')," +
                "', GCS E/V/M: ',ifnull(nullif(pa.gcs_e,''),'-'),'/',ifnull(nullif(pa.gcs_v,''),'-'),'/',ifnull(nullif(pa.gcs_m,''),'-')," +
                "', Nadi: ',ifnull(nullif(pa.nadi_neurologi,''),'-'),', Suhu: ',ifnull(nullif(pa.suhu,''),'-')) as neurologi_summary," +
                "concat('1) ',ifnull(nullif(pa.skrining_gizi1,''),'-'),' = ',pa.nilai_gizi1,'; 2) ',ifnull(nullif(pa.skrining_gizi2,''),'-'),' = ',pa.nilai_gizi2," +
                "'; Total: ',pa.total_skor_gizi,' (',ifnull(nullif(pa.keterangan_gizi,''),'-'),')','; Dietisien: ',ifnull(nullif(pa.gizi_status_dietisien,''),'-')," +
                "'; Status: ',ifnull(nullif(pa.gizi_lapor,''),'-'),if(pa.gizi_tanggal='','',concat('; Tgl: ',pa.gizi_tanggal)),if(pa.gizi_jam='','',concat('; Jam: ',pa.gizi_jam))) as gizi_summary," +
                "concat('Alat bantu: ',ifnull(nullif(concat_ws(', '," +
                "if(pa.alat_bantu_tidak='Ya','Tidak',null),if(pa.alat_bantu_tongkat='Ya','Tongkat',null),if(pa.alat_bantu_kruk='Ya','Kruk',null)," +
                "if(pa.alat_bantu_kursi_roda='Ya','Kursi roda',null),if(pa.alat_bantu_lain='Ya',concat('Lain-lain',if(pa.alat_bantu_lain_keterangan='','',concat(' (',pa.alat_bantu_lain_keterangan,')'))),null)),''),'-')," +
                "'; Cacat tubuh: ',if(pa.cacat_tubuh_status='Ada',concat('Ada',if(pa.cacat_tubuh_keterangan='','',concat(' (',pa.cacat_tubuh_keterangan,')'))),ifnull(nullif(pa.cacat_tubuh_status,''),'-'))) as fungsional_summary," +
                "concat('Minimal: ',ifnull(nullif(concat_ws('; '," +
                "if(pa.perawatan_minimal_1='Ya','Kebersihan diri mandiri',null),if(pa.perawatan_minimal_2='Ya','Makan/minum mandiri',null)," +
                "if(pa.perawatan_minimal_3='Ya','Ambulasi dengan pengawasan',null),if(pa.perawatan_minimal_4='Ya','Observasi TTV tiap shift',null)," +
                "if(pa.perawatan_minimal_5='Ya','Pengobatan minimal / psikologis stabil',null)),''),'-')," +
                "'; Parsial: ',ifnull(nullif(concat_ws('; '," +
                "if(pa.perawatan_parsial_1='Ya','Kebersihan diri / makan dibantu',null),if(pa.perawatan_parsial_2='Ya','Observasi TTV tiap 4 jam',null)," +
                "if(pa.perawatan_parsial_3='Ya','Ambulasi dibantu / pengobatan > 1 kali',null),if(pa.perawatan_parsial_4='Ya','Folley catheter / I-O dicatat',null)," +
                "if(pa.perawatan_parsial_5='Ya','Infus / persiapan prosedur',null)),''),'-')," +
                "'; Total: ',ifnull(nullif(concat_ws('; '," +
                "if(pa.perawatan_total_1='Ya','Semua kebutuhan dibantu',null),if(pa.perawatan_total_2='Ya','Perubahan posisi / TTV tiap 2 jam',null)," +
                "if(pa.perawatan_total_3='Ya','Makan via NGT / terapi IV',null),if(pa.perawatan_total_4='Ya','Pemakaian suction',null),if(pa.perawatan_total_5='Ya','Gelisah / disorientasi',null)),''),'-')," +
                "'; Klasifikasi: ',ifnull(nullif(pa.klasifikasi_pasien,''),'-'),'; Aktivitas: ',ifnull(nullif(pa.aktivitas_pasien,''),'-')) as perawatan_summary," +
                "concat('1) ',ifnull(nullif(pa.humpty_skala1,''),'-'),' [',pa.humpty_nilai1,']; ','2) ',ifnull(nullif(pa.humpty_skala2,''),'-'),' [',pa.humpty_nilai2,']; '," +
                "'3) ',ifnull(nullif(pa.humpty_skala3,''),'-'),' [',pa.humpty_nilai3,']; ','4) ',ifnull(nullif(pa.humpty_skala4,''),'-'),' [',pa.humpty_nilai4,']; '," +
                "'5) ',ifnull(nullif(pa.humpty_skala5,''),'-'),' [',pa.humpty_nilai5,']; ','6) ',ifnull(nullif(pa.humpty_skala6,''),'-'),' [',pa.humpty_nilai6,']; '," +
                "'7) ',ifnull(nullif(pa.humpty_skala7,''),'-'),' [',pa.humpty_nilai7,']; Total: ',pa.humpty_total,' (',ifnull(nullif(pa.humpty_keterangan,''),'-'),')') as humpty_summary," +
                "concat('NRS/VAS: ',ifnull(nullif(pa.nrs,''),'-'),'; Wong Baker: ',ifnull(nullif(pa.wong_baker,''),'-')) as nyeri_nrs_summary," +
                "concat('Wajah: ',ifnull(nullif(pa.flacc_skala1,''),'-'),' [',ifnull(nullif(pa.flacc_nilai1,''),'0'),']; '," +
                "'Kaki: ',ifnull(nullif(pa.flacc_skala2,''),'-'),' [',ifnull(nullif(pa.flacc_nilai2,''),'0'),']; '," +
                "'Aktivitas: ',ifnull(nullif(pa.flacc_skala3,''),'-'),' [',ifnull(nullif(pa.flacc_nilai3,''),'0'),']; '," +
                "'Tangis: ',ifnull(nullif(pa.flacc_skala4,''),'-'),' [',ifnull(nullif(pa.flacc_nilai4,''),'0'),']; '," +
                "'Bersuara: ',ifnull(nullif(pa.flacc_skala5,''),'-'),' [',ifnull(nullif(pa.flacc_nilai5,''),'0'),']; Total: ',ifnull(nullif(pa.flacc_total,''),'-')) as flacc_summary," +
                "concat('Frekuensi: ',ifnull(nullif(pa.nyeri_frekuensi,''),'-'),'; Lama: ',ifnull(nullif(pa.nyeri_lama,''),'-'),'; Menjalar: ',ifnull(nullif(pa.nyeri_menjalar,''),'-')," +
                "if(pa.nyeri_menjalar_ke='','',concat(' ke ',pa.nyeri_menjalar_ke)),'; Kualitas: ',ifnull(nullif(pa.nyeri_kualitas,''),'-')," +
                "'; Faktor pencetus: ',ifnull(nullif(pa.nyeri_pencetus,''),'-'),'; Faktor pengurang: ',ifnull(nullif(pa.nyeri_pengurang,''),'-')," +
                "'; Lokasi: ',ifnull(nullif(pa.lokasi_nyeri,''),'-'),'; Tindak lanjut: ',ifnull(nullif(concat_ws(', '," +
                "if(pa.tindak_lanjut_edukasi='Ya','Edukasi',null),if(pa.tindak_lanjut_intervensi='Ya','Intervensi',null)," +
                "if(pa.tindak_lanjut_konsul='Ya',concat('Konsul',if(pa.tindak_lanjut_konsul_ke='','',concat(' ke ',pa.tindak_lanjut_konsul_ke))),null)),''),'-')) as nyeri_detail_summary," +
                "ifnull(nullif(concat_ws(', ',if(pa.psikologis_cemas='Ya','Cemas',null),if(pa.psikologis_takut='Ya','Takut',null),if(pa.psikologis_marah='Ya','Marah',null),if(pa.psikologis_sedih='Ya','Sedih',null),if(pa.psikologis_lain='Ya',concat('Lain-lain: ',ifnull(nullif(pa.psikologis_lain_keterangan,''),'-')),null)),''),'-') as psikologis_summary," +
                "ifnull(nullif(concat_ws(', ',if(pa.mental_orientasi_baik='Ya','Sadar dan orientasi baik',null)," +
                "if(pa.mental_masalah_perilaku='Ya',concat('Ada masalah perilaku: ',ifnull(nullif(pa.mental_masalah_perilaku_keterangan,''),'-')),null)," +
                "if(pa.mental_perilaku_kekerasan='Ya',concat('Perilaku kekerasan sebelumnya: ',ifnull(nullif(pa.mental_perilaku_kekerasan_keterangan,''),'-')),null)),''),'-') as mental_summary," +
                "concat('Status sosial: ',ifnull(nullif(pa.status_sosial,''),'-'),'; Tempat tinggal: ',ifnull(nullif(pa.tempat_tinggal,''),'-')," +
                "if(pa.tempat_tinggal_keterangan='','',concat(' (',pa.tempat_tinggal_keterangan,')')),'; Hubungan keluarga: ',ifnull(nullif(pa.hubungan_keluarga,''),'-')," +
                "'; Ibadah teratur: ',ifnull(nullif(pa.ibadah_teratur,''),'-')) as sosial_summary," +
                "ifnull(nullif(pa.tempat_ttd,''),'-') as tempat_ttd,ifnull(nullif(pa.pasien_keluarga_ttd,''),'-') as pasien_keluarga_ttd " +
                "from " + TABLE_PENILAIAN + " pa inner join reg_periksa rp on rp.no_rawat=pa.no_rawat inner join pasien ps on ps.no_rkm_medis=rp.no_rkm_medis left join petugas p on p.nip=pa.nip where pa.no_rawat='" + rawat + "'";
    }

    private String buildReportQuery(String noRawat) {
        return buildRingkasanQuery(noRawat);
    }

    private void tampilData() {
        if (tabMode == null) {
            return;
        }
        while (tabMode.getRowCount() > 0) {
            tabMode.removeRow(0);
        }
        String cari = TCariData == null ? "" : TCariData.getText().trim();
        try {
            ps = koneksi.prepareStatement(
                "select pa.no_rawat,rp.no_rkm_medis,ps.nm_pasien,if(ps.jk='L','Laki-Laki','Perempuan') as jk,ps.tgl_lahir,pa.tanggal,pa.kategori_triage,pa.keluhan_saat_ini,pa.humpty_total," +
                "concat(ifnull(nullif(pa.nrs,''),'-'),' / ',ifnull(nullif(pa.flacc_total,''),'-')) as nyeri_ringkas,ifnull(p.nama,pa.petugas_display) as nama_petugas " +
                "from " + TABLE_PENILAIAN + " pa inner join reg_periksa rp on rp.no_rawat=pa.no_rawat inner join pasien ps on ps.no_rkm_medis=rp.no_rkm_medis " +
                "left join petugas p on p.nip=pa.nip where pa.no_rawat like ? or rp.no_rkm_medis like ? or ps.nm_pasien like ? order by pa.tanggal desc"
            );
            String keyword = "%" + cari + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("jk"), rs.getString("tgl_lahir"),
                    rs.getString("tanggal"), rs.getString("kategori_triage"), rs.getString("keluhan_saat_ini"), rs.getString("humpty_total"), rs.getString("nyeri_ringkas"), rs.getString("nama_petugas")
                });
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void loadSelectedData() {
        if (tbData.getSelectedRow() > -1) {
            loadDataByNoRawat(tbData.getValueAt(tbData.getSelectedRow(), 0).toString());
        }
    }

    private void loadDataByNoRawat(String noRawat) {
        try {
            ps = koneksi.prepareStatement(
                "select pa.*,rp.no_rkm_medis,ps.nm_pasien,if(ps.jk='L','Laki-Laki','Perempuan') as jk,ps.tgl_lahir,ifnull(p.nama,pa.petugas_display) as nama_petugas " +
                "from " + TABLE_PENILAIAN + " pa inner join reg_periksa rp on rp.no_rawat=pa.no_rawat inner join pasien ps on ps.no_rkm_medis=rp.no_rkm_medis " +
                "left join petugas p on p.nip=pa.nip where pa.no_rawat=?"
            );
            ps.setString(1, noRawat);
            rs = ps.executeQuery();
            if (rs.next()) {
                currentPetugasNip = rs.getString("nip") == null ? "" : rs.getString("nip");
                TNoRw.setText(rs.getString("no_rawat"));
                TNoRM.setText(rs.getString("no_rkm_medis"));
                TPasien.setText(rs.getString("nm_pasien"));
                JK.setText(rs.getString("jk"));
                TglLahir.setText(rs.getString("tgl_lahir"));

                Timestamp ts = rs.getTimestamp("tanggal");
                if (ts != null) {
                    TglAsesmen.setDate(new Date(ts.getTime()));
                    JamAsesmen.setText(jamFormat.format(new Date(ts.getTime())));
                }

                setComboByText(KategoriTriage, rs.getString("kategori_triage"));
                KeluhanSaatIni.setText(rs.getString("keluhan_saat_ini"));
                AlergiTidakAda.setSelected("Ya".equals(rs.getString("alergi_tidak_ada")));
                AlergiSebutkan.setText(rs.getString("alergi_keterangan"));

                airwayChecks[0].setSelected("Ya".equals(rs.getString("airway_bebas")));
                airwayChecks[1].setSelected("Ya".equals(rs.getString("airway_gargling")));
                airwayChecks[2].setSelected("Ya".equals(rs.getString("airway_stridor")));
                airwayChecks[3].setSelected("Ya".equals(rs.getString("airway_wheezing")));
                airwayChecks[4].setSelected("Ya".equals(rs.getString("airway_ronchi")));
                airwayChecks[5].setSelected("Ya".equals(rs.getString("airway_terintubasi")));

                breathingChecks[0].setSelected("Ya".equals(rs.getString("breathing_spontan")));
                breathingChecks[1].setSelected("Ya".equals(rs.getString("breathing_dispneu")));
                breathingChecks[2].setSelected("Ya".equals(rs.getString("breathing_ventilasi_mekanik")));
                breathingChecks[3].setSelected("Ya".equals(rs.getString("breathing_tachipneu")));
                breathingChecks[4].setSelected("Ya".equals(rs.getString("breathing_apneu")));
                breathingChecks[5].setSelected("Ya".equals(rs.getString("breathing_memakai_ventilator")));

                setComboByText(NadiKualitas, rs.getString("nadi_kualitas"));
                setComboByText(CrtCombo, rs.getString("crt"));
                setComboByText(WarnaKulitCombo, rs.getString("warna_kulit"));
                setComboByText(PerdarahanCombo, rs.getString("perdarahan"));
                setComboByText(TurgorCombo, rs.getString("turgor_kulit"));
                RespirasiField.setText(rs.getString("respirasi"));
                TekananDarahField.setText(rs.getString("tekanan_darah"));
                setComboByText(ResponCombo, rs.getString("respon"));
                setComboByText(PupilCombo, rs.getString("pupil"));
                RefleksField.setText(rs.getString("refleks"));
                GcsEField.setText(rs.getString("gcs_e"));
                GcsVField.setText(rs.getString("gcs_v"));
                GcsMField.setText(rs.getString("gcs_m"));
                NadiNeuroField.setText(rs.getString("nadi_neurologi"));
                SuhuField.setText(rs.getString("suhu"));

                setComboByText(SkriningGizi1, rs.getString("skrining_gizi1"));
                setComboByText(SkriningGizi2, rs.getString("skrining_gizi2"));
                setComboByText(GiziStatusCombo, rs.getString("gizi_status_dietisien"));
                setComboByText(GiziLaporCombo, rs.getString("gizi_lapor"));
                GiziTanggalField.setText(rs.getString("gizi_tanggal"));
                GiziJamField.setText(rs.getString("gizi_jam"));

                AlatBantuTidak.setSelected("Ya".equals(rs.getString("alat_bantu_tidak")));
                AlatBantuTongkat.setSelected("Ya".equals(rs.getString("alat_bantu_tongkat")));
                AlatBantuKruk.setSelected("Ya".equals(rs.getString("alat_bantu_kruk")));
                AlatBantuKursiRoda.setSelected("Ya".equals(rs.getString("alat_bantu_kursi_roda")));
                AlatBantuLain.setSelected("Ya".equals(rs.getString("alat_bantu_lain")));
                AlatBantuLainText.setText(rs.getString("alat_bantu_lain_keterangan"));
                setComboByText(CacatTubuhCombo, rs.getString("cacat_tubuh_status"));
                CacatTubuhText.setText(rs.getString("cacat_tubuh_keterangan"));

                setComboByText(PerawatanMinimalCombo[0], rs.getString("perawatan_minimal_1"));
                setComboByText(PerawatanMinimalCombo[1], rs.getString("perawatan_minimal_2"));
                setComboByText(PerawatanMinimalCombo[2], rs.getString("perawatan_minimal_3"));
                setComboByText(PerawatanMinimalCombo[3], rs.getString("perawatan_minimal_4"));
                setComboByText(PerawatanMinimalCombo[4], rs.getString("perawatan_minimal_5"));
                setComboByText(PerawatanParsialCombo[0], rs.getString("perawatan_parsial_1"));
                setComboByText(PerawatanParsialCombo[1], rs.getString("perawatan_parsial_2"));
                setComboByText(PerawatanParsialCombo[2], rs.getString("perawatan_parsial_3"));
                setComboByText(PerawatanParsialCombo[3], rs.getString("perawatan_parsial_4"));
                setComboByText(PerawatanParsialCombo[4], rs.getString("perawatan_parsial_5"));
                setComboByText(PerawatanTotalCombo[0], rs.getString("perawatan_total_1"));
                setComboByText(PerawatanTotalCombo[1], rs.getString("perawatan_total_2"));
                setComboByText(PerawatanTotalCombo[2], rs.getString("perawatan_total_3"));
                setComboByText(PerawatanTotalCombo[3], rs.getString("perawatan_total_4"));
                setComboByText(PerawatanTotalCombo[4], rs.getString("perawatan_total_5"));
                setComboByText(KlasifikasiPasienCombo, rs.getString("klasifikasi_pasien"));
                setComboByText(AktivitasPasienCombo, rs.getString("aktivitas_pasien"));

                setComboByText(HumptyCombo[0], rs.getString("humpty_skala1"));
                setComboByText(HumptyCombo[1], rs.getString("humpty_skala2"));
                setComboByText(HumptyCombo[2], rs.getString("humpty_skala3"));
                setComboByText(HumptyCombo[3], rs.getString("humpty_skala4"));
                setComboByText(HumptyCombo[4], rs.getString("humpty_skala5"));
                setComboByText(HumptyCombo[5], rs.getString("humpty_skala6"));
                setComboByText(HumptyCombo[6], rs.getString("humpty_skala7"));

                setComboByText(NrsCombo, rs.getString("nrs"));
                setComboByText(WongBakerCombo, rs.getString("wong_baker"));
                setComboByText(FlaccCombo[0], rs.getString("flacc_skala1"));
                setComboByText(FlaccCombo[1], rs.getString("flacc_skala2"));
                setComboByText(FlaccCombo[2], rs.getString("flacc_skala3"));
                setComboByText(FlaccCombo[3], rs.getString("flacc_skala4"));
                setComboByText(FlaccCombo[4], rs.getString("flacc_skala5"));
                FlaccTotalField.setText(rs.getString("flacc_total"));
                setComboByText(NyeriFrekuensiCombo, rs.getString("nyeri_frekuensi"));
                NyeriLamaField.setText(rs.getString("nyeri_lama"));
                setComboByText(NyeriMenjalarCombo, rs.getString("nyeri_menjalar"));
                NyeriMenjalarKeField.setText(rs.getString("nyeri_menjalar_ke"));
                setComboByText(NyeriKualitasCombo, rs.getString("nyeri_kualitas"));
                NyeriPencetusArea.setText(rs.getString("nyeri_pencetus"));
                NyeriPengurangArea.setText(rs.getString("nyeri_pengurang"));
                LokasiNyeriArea.setText(rs.getString("lokasi_nyeri"));
                TindakLanjutEdukasi.setSelected("Ya".equals(rs.getString("tindak_lanjut_edukasi")));
                TindakLanjutIntervensi.setSelected("Ya".equals(rs.getString("tindak_lanjut_intervensi")));
                TindakLanjutKonsul.setSelected("Ya".equals(rs.getString("tindak_lanjut_konsul")));
                TindakLanjutKonsulKeField.setText(rs.getString("tindak_lanjut_konsul_ke"));

                PsikologisChecks[0].setSelected("Ya".equals(rs.getString("psikologis_cemas")));
                PsikologisChecks[1].setSelected("Ya".equals(rs.getString("psikologis_takut")));
                PsikologisChecks[2].setSelected("Ya".equals(rs.getString("psikologis_marah")));
                PsikologisChecks[3].setSelected("Ya".equals(rs.getString("psikologis_sedih")));
                PsikologisLain.setSelected("Ya".equals(rs.getString("psikologis_lain")));
                PsikologisLainText.setText(rs.getString("psikologis_lain_keterangan"));
                MentalOrientasiBaik.setSelected("Ya".equals(rs.getString("mental_orientasi_baik")));
                MentalMasalahPerilaku.setSelected("Ya".equals(rs.getString("mental_masalah_perilaku")));
                MentalMasalahText.setText(rs.getString("mental_masalah_perilaku_keterangan"));
                MentalPerilakuKekerasan.setSelected("Ya".equals(rs.getString("mental_perilaku_kekerasan")));
                MentalPerilakuKekerasanText.setText(rs.getString("mental_perilaku_kekerasan_keterangan"));
                StatusSosialField.setText(rs.getString("status_sosial"));
                setComboByText(TempatTinggalCombo, rs.getString("tempat_tinggal"));
                TempatTinggalText.setText(rs.getString("tempat_tinggal_keterangan"));
                HubunganKeluargaField.setText(rs.getString("hubungan_keluarga"));
                setComboByText(IbadahCombo, rs.getString("ibadah_teratur"));
                TempatTtdField.setText(rs.getString("tempat_ttd"));
                PasienKeluargaField.setText(rs.getString("pasien_keluarga_ttd"));
                PetugasField.setText(rs.getString("nama_petugas"));

                updateGiziScore();
                updateHumptyScore();
                updateFlaccScores();
                renderPreview();
                TabRawat.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void setComboByText(ComboBox combo, String value) {
        if ((combo == null) || (value == null)) {
            return;
        }
        for (int i = 0; i < combo.getItemCount(); i++) {
            Object item = combo.getItemAt(i);
            if ((item != null) && value.trim().equalsIgnoreCase(item.toString().trim())) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir " +
                "from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=?"
            );
            ps.setString(1, TNoRw.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                TNoRM.setText(rs.getString("no_rkm_medis"));
                TPasien.setText(rs.getString("nm_pasien"));
                JK.setText(rs.getString("jk"));
                TglLahir.setText(rs.getString("tgl_lahir"));
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void pilihBarisData(String noRawat) {
        if ((tbData == null) || (noRawat == null)) {
            return;
        }
        for (int i = 0; i < tbData.getRowCount(); i++) {
            if (noRawat.equals(tbData.getValueAt(i, 0).toString())) {
                tbData.setRowSelectionInterval(i, i);
                tbData.scrollRectToVisible(tbData.getCellRect(i, 0, true));
                break;
            }
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        if (tgl2 != null) {
            TglAsesmen.setDate(tgl2);
        }
        if (TCariData != null) {
            TCariData.setText(norwt);
        }
        isRawat();
        renderPreview();
        tampilData();
    }

    public void emptTeks() {
        if (TglAsesmen != null) {
            TglAsesmen.setDate(new Date());
        }
        if (!TNoRw.getText().trim().equals("")) {
            resetAssessmentFields();
        } else {
            resetFormForNewEntry();
        }
        TabRawat.setSelectedIndex(0);
    }

    public void isCek() {
        if (akses.getjml2() >= 1) {
            currentPetugasNip = akses.getkode();
            String namaPetugas = Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", akses.getkode());
            if ((namaPetugas != null) && (!namaPetugas.trim().equals(""))) {
                PetugasField.setText(namaPetugas);
            } else {
                PetugasField.setText(akses.getkode());
            }
        }
    }

    public void tampil() {
        renderPreview();
        tampilData();
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        updateGiziScore();
        updateHumptyScore();
        updateFlaccScores();
        renderPreview();
        if (!validasiForm()) {
            return;
        }
        if (penilaianSudahAda(TNoRw.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Data penilaian untuk pasien ini sudah ada. Gunakan tombol Edit.");
            return;
        }
        if (simpanPenilaian()) {
            tampilData();
            pilihBarisData(TNoRw.getText());
            TabRawat.setSelectedIndex(2);
            JOptionPane.showMessageDialog(rootPane, "Data penilaian berhasil disimpan.");
        }
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        updateGiziScore();
        updateHumptyScore();
        updateFlaccScores();
        renderPreview();
        if (!validasiForm()) {
            return;
        }
        if (!penilaianSudahAda(TNoRw.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Data penilaian belum tersimpan. Gunakan tombol Simpan.");
            return;
        }
        if (editPenilaian()) {
            tampilData();
            pilihBarisData(TNoRw.getText());
            TabRawat.setSelectedIndex(2);
            JOptionPane.showMessageDialog(rootPane, "Data penilaian berhasil diubah.");
        }
    }

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {
        final String reportBaseName = "rptFormulirPenilaianAwalKeperawatanAnakIGD";
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No. Rawat");
            return;
        }
        if (!penilaianSudahAda(TNoRw.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Data penilaian belum tersimpan. Simpan data terlebih dahulu sebelum dicetak.");
            return;
        }
        if (!siapkanReportJasper(reportBaseName)) {
            return;
        }
        Valid.MyReportqry(
                reportBaseName + ".jasper",
                "report",
                "::[ Formulir Penilaian Awal Keperawatan Anak IGD ]::",
                buildReportQuery(TNoRw.getText()),
                buildReportParameters(TNoRw.getText())
        );
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No. Rawat");
            return;
        }
        if (!penilaianSudahAda(TNoRw.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Data penilaian belum tersimpan atau sudah dihapus.");
            return;
        }
        if (JOptionPane.showConfirmDialog(rootPane,
                "Hapus data penilaian anak IGD untuk No. Rawat " + TNoRw.getText() + " ?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (hapusPenilaian()) {
                emptTeks();
                tampilData();
                TabRawat.setSelectedIndex(0);
                JOptionPane.showMessageDialog(rootPane, "Data penilaian berhasil dihapus.");
            }
        }
    }

    private void BtnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGenerateActionPerformed
        updateGiziScore();
        updateHumptyScore();
        updateFlaccScores();
        renderPreview();
        TabRawat.setSelectedIndex(1);
    }//GEN-LAST:event_BtnGenerateActionPerformed

    private void BtnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBaruActionPerformed
        emptTeks();
        TabRawat.setSelectedIndex(0);
    }//GEN-LAST:event_BtnBaruActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        renderPreview();
        tampilData();
    }//GEN-LAST:event_formWindowOpened

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new InternalFrame();
        panelButtons = new panelisi();
        BtnGenerate = new Button();
        BtnBaru = new Button();
        BtnKeluar = new Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new InternalFrame();
        ScrollInput = new ScrollPane();
        internalFrame3 = new InternalFrame();
        ScrollPreview = new javax.swing.JScrollPane();
        PreviewPane = new JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout());

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Anak IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelButtons.setPreferredSize(new java.awt.Dimension(44, 54));
        panelButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 10));

        BtnGenerate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnGenerate.setText("Generate");
        BtnGenerate.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGenerateActionPerformed(evt);
            }
        });
        panelButtons.add(BtnGenerate);

        BtnBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBaru.setText("Baru");
        BtnBaru.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBaruActionPerformed(evt);
            }
        });
        panelButtons.add(BtnBaru);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setText("Keluar");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelButtons.add(BtnKeluar);

        internalFrame1.add(panelButtons, java.awt.BorderLayout.PAGE_END);

        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new java.awt.BorderLayout());
        ScrollInput.setBorder(null);
        internalFrame2.add(ScrollInput, java.awt.BorderLayout.CENTER);
        TabRawat.addTab("Input Form", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout());

        PreviewPane.setEditable(false);
        PreviewPane.setContentType("text/html"); // NOI18N
        PreviewPane.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ScrollPreview.setViewportView(PreviewPane);

        internalFrame3.add(ScrollPreview, java.awt.BorderLayout.CENTER);
        TabRawat.addTab("Preview", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Button BtnBaru;
    private Button BtnGenerate;
    private Button BtnKeluar;
    private JEditorPane PreviewPane;
    private ScrollPane ScrollInput;
    private javax.swing.JScrollPane ScrollPreview;
    private javax.swing.JTabbedPane TabRawat;
    private InternalFrame internalFrame1;
    private InternalFrame internalFrame2;
    private InternalFrame internalFrame3;
    private panelisi panelButtons;
    // End of variables declaration//GEN-END:variables
}
