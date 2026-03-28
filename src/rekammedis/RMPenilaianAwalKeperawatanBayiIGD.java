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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * Form baru berbasis PDF RM.2/UGD/BY.
 *
 * File ini disiapkan sebagai pondasi UI dan logika skor utama
 * sebelum dihubungkan ke tabel/database dan menu utama aplikasi.
 */
public final class RMPenilaianAwalKeperawatanBayiIGD extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;
    private static final String TABLE_PENILAIAN = "penilaian_awal_keperawatan_bayi_igd";
    private static final Color CANVAS_BACKGROUND = new Color(244, 247, 250);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color CARD_BORDER = new Color(210, 221, 232);
    private static final Color TITLE_COLOR = new Color(52, 92, 138);
    private static final Color LABEL_COLOR = new Color(63, 74, 89);
    private static final Color HINT_COLOR = new Color(106, 118, 133);
    private static final Color BANNER_BACKGROUND = new Color(234, 243, 252);
    private static final Color BANNER_BORDER = new Color(188, 208, 229);
    private static final Color READ_ONLY_BACKGROUND = new Color(246, 249, 252);
    private static final String[] KOLOM_PENILAIAN = new String[]{
        "no_rawat", "tanggal", "kategori_triage", "keluhan_saat_ini", "alergi_tidak_ada", "alergi_keterangan",
        "airway_bebas", "airway_gargling", "airway_stridor", "airway_wheezing", "airway_ronchi", "airway_terintubasi",
        "breathing_spontan", "breathing_dispneu", "breathing_ventilasi_mekanik", "breathing_tachipneu", "breathing_apneu", "breathing_memakai_ventilator",
        "nadi_kualitas", "crt", "warna_kulit", "perdarahan", "turgor_kulit", "respirasi", "tekanan_darah", "respon", "refleks", "gcs", "nadi_neurologi", "suhu",
        "skrining_gizi1", "nilai_gizi1", "skrining_gizi2", "nilai_gizi2", "total_skor_gizi", "keterangan_gizi", "dietisien_diberitahu", "jam_lapor_dietisien",
        "fungsional_tidak", "fungsional_tongkat", "fungsional_kursi_roda", "fungsional_lain", "fungsional_lain_keterangan", "cacat_tubuh",
        "humpty_skala1", "humpty_nilai1", "humpty_skala2", "humpty_nilai2", "humpty_skala3", "humpty_nilai3", "humpty_skala4", "humpty_nilai4",
        "humpty_skala5", "humpty_nilai5", "humpty_skala6", "humpty_nilai6", "humpty_skala7", "humpty_nilai7", "humpty_total", "humpty_keterangan",
        "nips_skala1", "nips_nilai1", "nips_skala2", "nips_nilai2", "nips_skala3", "nips_nilai3", "nips_skala4", "nips_nilai4", "nips_skala5", "nips_nilai5",
        "nips_total", "nips_keterangan", "penilaian_kondisi", "discharge_rawat_gabung", "discharge_diselimuti_hangat", "discharge_kirim_ke_ruang_bayi",
        "discharge_vitamin_k", "discharge_observasi_suhu", "discharge_pemantauan_glukosa", "discharge_edukasi_keluarga", "discharge_lainnya",
        "diagnosis_bersihan_jalan_napas_tidak_efektif", "diagnosis_pola_napas_tidak_efektif", "diagnosis_gangguan_pertukaran_gas", "diagnosis_termoregulasi_tidak_efektif",
        "diagnosis_ketidakseimbangan_nutrisi", "diagnosis_ikterik_berhubungan_menyusui", "diagnosis_kurang_volume_cairan", "diagnosis_diare",
        "diagnosis_perfusi_jaringan_gastrointestinal", "diagnosis_kesiapan_peningkatan_nutrisi", "diagnosis_menyusui_tidak_efektif", "diagnosis_risiko_infeksi",
        "diagnosis_risiko_aspirasi", "diagnosis_risiko_kerusakan_integritas_kulit", "psikologis_cemas", "psikologis_takut", "psikologis_marah", "psikologis_sedih",
        "mental_orientasi_baik", "mental_masalah_perilaku", "mental_masalah_perilaku_keterangan", "mental_perilaku_kekerasan", "status_sosial", "tempat_tinggal",
        "hubungan_keluarga", "orang_tua_wali_bayi", "petugas_display", "nip"
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
    private TextBox RefleksField;
    private TextBox GcsField;
    private TextBox NadiNeuroField;
    private TextBox SuhuField;

    private ComboBox SkriningGizi1;
    private ComboBox SkriningGizi2;
    private TextBox SkorGizi1;
    private TextBox SkorGizi2;
    private TextBox TotalSkorGizi;
    private TextBox KeteranganGizi;
    private CekBox DietisienDiberitahu;
    private TextBox JamLaporDietisien;

    private CekBox FungsionalTidakAda;
    private CekBox FungsionalTongkat;
    private CekBox FungsionalKursiRoda;
    private CekBox FungsionalLain;
    private TextBox FungsionalLainText;
    private TextBox CatatTubuhField;

    private ComboBox[] HumptyCombo;
    private TextBox[] HumptyNilai;
    private TextBox TotalHumpty;
    private TextBox KeteranganHumpty;

    private ComboBox[] NipsCombo;
    private TextBox[] NipsNilai;
    private TextBox TotalNips;
    private TextBox KeteranganNips;

    private TextArea PenilaianKondisi;
    private CekBox[] dischargeChecks;
    private TextArea DischargeLainnya;

    private CekBox[] diagnosisChecks;
    private CekBox[] emosiChecks;
    private CekBox MentalOrientasiBaik;
    private CekBox MentalMasalahPerilaku;
    private TextBox MentalMasalahText;
    private CekBox MentalPerilakuKekerasan;
    private TextBox StatusSosialField;
    private TextBox TempatTinggalField;
    private TextBox HubunganKeluargaField;
    private TextBox OrangTuaWaliField;
    private TextBox PetugasField;

    public RMPenilaianAwalKeperawatanBayiIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initToolbarButtons();
        buildDynamicForm();
        initDataListTab();
        initDefaultValues();
        setSize(1280, 860);
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
        formBody.add(createHumptySection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createNipsSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createDischargeSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createDiagnosisSection());
        formBody.add(Box.createVerticalStrut(12));
        formBody.add(createSosialPsikologiSection());
        formBody.add(Box.createVerticalGlue());
    }

    private JPanel createIdentitasSection() {
        JPanel section = createSectionPanel("A. Identitas Dan Triage");
        section.add(createSectionHint("Lengkapi identitas pasien, waktu asesmen, kategori triage, dan riwayat alergi sebelum melanjutkan ke survey primer."));

        TNoRw = createTextBox(120);
        TNoRM = createTextBox(100);
        TPasien = createTextBox(240);
        TglLahir = createTextBox(110);
        JK = createTextBox(60);
        TglAsesmen = new Tanggal();
        TglAsesmen.setPreferredSize(new Dimension(140, 23));
        TglAsesmen.setMaximumSize(TglAsesmen.getPreferredSize());
        TglAsesmen.setBackground(Color.WHITE);
        JamAsesmen = createTextBox(80);
        KategoriTriage = createCombo(new String[]{
            "Pilih kategori triage",
            "P1",
            "P2",
            "P3",
            "P4",
            "P0"
        }, 180);
        KeluhanSaatIni = createTextArea(3, 90);
        AlergiTidakAda = createCheck("Tidak ada alergi");
        AlergiSebutkan = createTextBox(340);

        section.add(createRow(
            field("No. Rawat", TNoRw),
            field("No. RM", TNoRM),
            field("Nama", TPasien),
            field("L/P", JK),
            field("Tgl. Lahir", TglLahir)
        ));
        section.add(createRow(
            field("Tanggal Asesmen", TglAsesmen),
            field("Jam", JamAsesmen),
            field("Kategori Triage", KategoriTriage)
        ));
        section.add(createTextAreaRow("Keluhan saat ini / mekanisme kejadian", KeluhanSaatIni));
        section.add(createRow(
            field("Status alergi", AlergiTidakAda),
            field("Riwayat alergi / sebutkan", AlergiSebutkan)
        ));

        return section;
    }

    private JPanel createPrimarySurveySection() {
        JPanel section = createSectionPanel("B. Keadaan Umum / Primary Survey");
        section.add(createSectionHint("Centang temuan klinis awal pada airway dan breathing, lalu lengkapi parameter sirkulasi serta neurologis."));

        airwayChecks = new CekBox[]{
            createCheck("Bebas"),
            createCheck("Gargling"),
            createCheck("Stridor"),
            createCheck("Wheezing"),
            createCheck("Ronchi"),
            createCheck("Terintubasi")
        };
        breathingChecks = new CekBox[]{
            createCheck("Spontan"),
            createCheck("Dispneu"),
            createCheck("Ventilasi mekanik"),
            createCheck("Tachipneu"),
            createCheck("Apneu"),
            createCheck("Memakai ventilator")
        };

        NadiKualitas = createCombo(new String[]{"Pilih", "Kuat", "Lemah"}, 150);
        CrtCombo = createCombo(new String[]{"Pilih", "< 2 detik", ">= 2 detik"}, 150);
        WarnaKulitCombo = createCombo(new String[]{"Pilih", "Normal", "Pucat", "Kuning"}, 150);
        PerdarahanCombo = createCombo(new String[]{"Pilih", "Tidak ada", "Terkontrol", "Tidak terkontrol"}, 170);
        TurgorCombo = createCombo(new String[]{"Pilih", "Baik", "Buruk"}, 130);
        RespirasiField = createTextBox(120);
        TekananDarahField = createTextBox(120);

        ResponCombo = createCombo(new String[]{"Pilih", "Alert", "Pain", "Verbal", "Unrespons"}, 150);
        RefleksField = createTextBox(180);
        GcsField = createTextBox(120);
        NadiNeuroField = createTextBox(120);
        SuhuField = createTextBox(120);

        section.add(createChecklistRow("Airway", airwayChecks, 3));
        section.add(createChecklistRow("Breathing", breathingChecks, 3));
        section.add(createRow(
            field("Nadi", NadiKualitas),
            field("CRT", CrtCombo),
            field("Warna kulit", WarnaKulitCombo)
        ));
        section.add(createRow(
            field("Perdarahan", PerdarahanCombo),
            field("Turgor kulit", TurgorCombo),
            field("Respirasi", RespirasiField),
            field("Tekanan darah", TekananDarahField)
        ));
        section.add(createRow(
            field("Respon", ResponCombo),
            field("Refleks", RefleksField),
            field("GCS (E+V+M)", GcsField)
        ));
        section.add(createRow(
            field("Nadi", NadiNeuroField),
            field("Suhu (C)", SuhuField)
        ));

        return section;
    }

    private JPanel createSkriningGiziSection() {
        JPanel section = createSectionPanel("C. Skrining Gizi Awal");
        section.add(createSectionHint("Skor gizi dihitung otomatis berdasarkan pilihan yang Anda isi. Lengkapi tindak lanjut bila skor memerlukan perhatian."));
        ItemListener giziListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateGiziScore();
                }
            }
        };

        SkriningGizi1 = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih penilaian penurunan berat badan", 0),
            new ScoredChoice("Tidak ada penurunan berat badan", 0),
            new ScoredChoice("Tidak yakin / tidak tahu", 2),
            new ScoredChoice("Penurunan 1 sampai 5 kg", 1),
            new ScoredChoice("Penurunan 6 sampai 10 kg", 2),
            new ScoredChoice("Penurunan 11 sampai 15 kg", 3),
            new ScoredChoice("Penurunan >= 15 kg", 4)
        }, giziListener, 430);
        SkriningGizi2 = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih status asupan makan", 0),
            new ScoredChoice("Tidak", 0),
            new ScoredChoice("Ya", 1)
        }, giziListener, 430);
        SkorGizi1 = createReadOnlyBox(50);
        SkorGizi2 = createReadOnlyBox(50);
        TotalSkorGizi = createReadOnlyBox(60);
        KeteranganGizi = createReadOnlyBox(360);
        DietisienDiberitahu = createCheck("Dietisien / dokter sudah diberitahu");
        JamLaporDietisien = createTextBox(100);

        section.add(createRow(
            field("1. Penurunan BB tidak diinginkan", SkriningGizi1),
            field("Skor", SkorGizi1)
        ));
        section.add(createRow(
            field("2. Asupan makan berkurang", SkriningGizi2),
            field("Skor", SkorGizi2)
        ));
        section.add(createRow(
            field("Total skor", TotalSkorGizi),
            field("Keterangan", KeteranganGizi)
        ));
        section.add(createRow(
            field("Tindak lanjut", DietisienDiberitahu),
            field("Jam lapor", JamLaporDietisien)
        ));

        return section;
    }

    private JPanel createStatusFungsionalSection() {
        JPanel section = createSectionPanel("D. Status Fungsional");
        section.add(createSectionHint("Catat alat bantu yang digunakan pasien serta keterangan cacat tubuh bila ada."));

        FungsionalTidakAda = createCheck("Tidak");
        FungsionalTongkat = createCheck("Tongkat");
        FungsionalKursiRoda = createCheck("Kursi roda");
        FungsionalLain = createCheck("Lain-lain");
        FungsionalLainText = createTextBox(240);
        CatatTubuhField = createTextBox(260);

        section.add(createRow(
            field("Penggunaan alat bantu", createInlinePanel(
                FungsionalTidakAda,
                FungsionalTongkat,
                FungsionalKursiRoda,
                FungsionalLain
            )),
            field("Keterangan lain", FungsionalLainText)
        ));
        section.add(createRow(field("Cacat tubuh", CatatTubuhField)));

        return section;
    }

    private JPanel createHumptySection() {
        JPanel section = createSectionPanel("E. Pengkajian Risiko Jatuh Anak (Humpty Dumpty)");
        section.add(createSectionHint("Pilih kategori yang paling sesuai untuk setiap parameter. Nilai dan interpretasi akan terisi otomatis."));

        HumptyCombo = new ComboBox[7];
        HumptyNilai = new TextBox[7];
        ItemListener humptyListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateHumptyScore();
                }
            }
        };

        HumptyCombo[0] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih usia", 0),
            new ScoredChoice("Di bawah 3 tahun", 4),
            new ScoredChoice("3 sampai 7 tahun", 3),
            new ScoredChoice("7 sampai 13 tahun", 2),
            new ScoredChoice("Lebih dari 13 tahun", 1)
        }, humptyListener, 320);
        HumptyCombo[1] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih jenis kelamin", 0),
            new ScoredChoice("Laki-laki", 2),
            new ScoredChoice("Perempuan", 1)
        }, humptyListener, 320);
        HumptyCombo[2] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih diagnosis", 0),
            new ScoredChoice("Kelainan neurologi", 4),
            new ScoredChoice("Perubahan dalam oksigen", 3),
            new ScoredChoice("Kelainan psikis / perilaku", 2),
            new ScoredChoice("Diagnosis lain", 1)
        }, humptyListener, 320);
        HumptyCombo[3] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih gangguan kognitif", 0),
            new ScoredChoice("Tidak sadar keterbatasan", 3),
            new ScoredChoice("Lupa keterbatasan", 2),
            new ScoredChoice("Mengetahui kemampuan diri", 1)
        }, humptyListener, 320);
        HumptyCombo[4] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih faktor lingkungan", 0),
            new ScoredChoice("Riwayat jatuh dari tempat tidur bayi / anak", 4),
            new ScoredChoice("Menggunakan alat bantu / box / mebel", 3),
            new ScoredChoice("Berada di tempat tidur", 2),
            new ScoredChoice("Di luar ruang rawat", 1)
        }, humptyListener, 320);
        HumptyCombo[5] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih respon operasi / sedasi", 0),
            new ScoredChoice("Dalam 24 jam", 3),
            new ScoredChoice("Dalam 48 jam", 2),
            new ScoredChoice("Lebih dari 48 jam", 1)
        }, humptyListener, 320);
        HumptyCombo[6] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih penggunaan obat", 0),
            new ScoredChoice("Obat multipel / sedasi / hipnotik / narkotik", 3),
            new ScoredChoice("Salah satu terapi di atas", 2),
            new ScoredChoice("Pengobatan lain", 1)
        }, humptyListener, 320);

        for (int idx = 0; idx < HumptyNilai.length; idx++) {
            HumptyNilai[idx] = createReadOnlyBox(45);
        }

        TotalHumpty = createReadOnlyBox(60);
        KeteranganHumpty = createReadOnlyBox(380);

        section.add(createRow(field("1. Umur", HumptyCombo[0]), field("Skor", HumptyNilai[0])));
        section.add(createRow(field("2. Jenis kelamin", HumptyCombo[1]), field("Skor", HumptyNilai[1])));
        section.add(createRow(field("3. Diagnosa", HumptyCombo[2]), field("Skor", HumptyNilai[2])));
        section.add(createRow(field("4. Gangguan kognitif", HumptyCombo[3]), field("Skor", HumptyNilai[3])));
        section.add(createRow(field("5. Faktor lingkungan", HumptyCombo[4]), field("Skor", HumptyNilai[4])));
        section.add(createRow(field("6. Respon operasi / obat / anestesi", HumptyCombo[5]), field("Skor", HumptyNilai[5])));
        section.add(createRow(field("7. Penggunaan obat", HumptyCombo[6]), field("Skor", HumptyNilai[6])));
        section.add(createRow(field("Skor total", TotalHumpty), field("Keterangan", KeteranganHumpty)));

        return section;
    }

    private JPanel createNipsSection() {
        JPanel section = createSectionPanel("F. Pengkajian Skala Nyeri NIPS");
        section.add(createSectionHint("Gunakan skala NIPS sesuai observasi bayi. Nilai total dan interpretasi akan diperbarui otomatis."));

        NipsCombo = new ComboBox[5];
        NipsNilai = new TextBox[5];
        ItemListener nipsListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateNipsScore();
                }
            }
        };

        NipsCombo[0] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih ekspresi wajah", 0),
            new ScoredChoice("Otot relaks / wajah tenang / ekspresi netral", 0),
            new ScoredChoice("Meringis / alis berkerut / ekspresi negatif", 1)
        }, nipsListener, 420);
        NipsCombo[1] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih tangisan", 0),
            new ScoredChoice("Tenang / tidak menangis", 0),
            new ScoredChoice("Merengek / mengerang lemah intermiten", 1),
            new ScoredChoice("Menangis keras / melengking terus menerus", 2)
        }, nipsListener, 420);
        NipsCombo[2] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih pola napas", 0),
            new ScoredChoice("Bernapas biasa", 0),
            new ScoredChoice("Perubahan napas / tarikan ireguler / menahan napas", 1)
        }, nipsListener, 420);
        NipsCombo[3] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih gerakan tungkai", 0),
            new ScoredChoice("Relaks / gerakan tungkai biasa", 0),
            new ScoredChoice("Fleksi / ekstensi tegang kaku", 1)
        }, nipsListener, 420);
        NipsCombo[4] = createScoredCombo(new ScoredChoice[]{
            new ScoredChoice("Pilih tingkat kesadaran", 0),
            new ScoredChoice("Tenang / tidur lelap / bangun", 0),
            new ScoredChoice("Gelisah", 1)
        }, nipsListener, 420);

        for (int idx = 0; idx < NipsNilai.length; idx++) {
            NipsNilai[idx] = createReadOnlyBox(45);
        }

        TotalNips = createReadOnlyBox(60);
        KeteranganNips = createReadOnlyBox(380);

        section.add(createRow(field("1. Ekspresi wajah", NipsCombo[0]), field("Nilai", NipsNilai[0])));
        section.add(createRow(field("2. Tangisan", NipsCombo[1]), field("Nilai", NipsNilai[1])));
        section.add(createRow(field("3. Pola napas", NipsCombo[2]), field("Nilai", NipsNilai[2])));
        section.add(createRow(field("4. Tungkai", NipsCombo[3]), field("Nilai", NipsNilai[3])));
        section.add(createRow(field("5. Tingkat kesadaran", NipsCombo[4]), field("Nilai", NipsNilai[4])));
        section.add(createRow(field("Jumlah", TotalNips), field("Interpretasi", KeteranganNips)));

        return section;
    }

    private JPanel createDischargeSection() {
        JPanel section = createSectionPanel("G. Penilaian Dan Discharge Planning");
        section.add(createSectionHint("Tuliskan kondisi klinis utama dan centang rencana pulang / observasi yang perlu dilakukan."));

        PenilaianKondisi = createTextArea(4, 90);
        dischargeChecks = new CekBox[]{
            createCheck("Bayi tetap bersama ibu (rawat gabung)"),
            createCheck("Diselimuti dan hangat"),
            createCheck("Kirim ke ruang bayi segera"),
            createCheck("Vitamin K 0,5 - 1 mg IM diberikan"),
            createCheck("Observasi suhu 2-3 jam"),
            createCheck("Pemantauan glukosa darah"),
            createCheck("Edukasi keluarga")
        };
        DischargeLainnya = createTextArea(3, 90);

        section.add(createTextAreaRow("Penilaian klinis / masalah utama", PenilaianKondisi));
        section.add(createChecklistRow("Discharge planning", dischargeChecks, 2));
        section.add(createTextAreaRow("Rencana / catatan discharge planning lainnya", DischargeLainnya));

        return section;
    }

    private JPanel createDiagnosisSection() {
        JPanel section = createSectionPanel("H. Diagnosis Keperawatan");
        section.add(createSectionHint("Pilih diagnosis keperawatan yang paling relevan dengan kondisi pasien saat asesmen."));

        diagnosisChecks = new CekBox[]{
            createCheck("Bersihan jalan napas tidak efektif"),
            createCheck("Pola napas tidak efektif"),
            createCheck("Gangguan pertukaran gas"),
            createCheck("Termoregulasi tidak efektif"),
            createCheck("Ketidakseimbangan nutrisi"),
            createCheck("Ikterik berhubungan minum / menyusui"),
            createCheck("Kurang volume cairan"),
            createCheck("Diare"),
            createCheck("Perfusi jaringan gastrointestinal tidak efektif"),
            createCheck("Kesiapan peningkatan kebutuhan nutrisi"),
            createCheck("Menyusui tidak efektif"),
            createCheck("Risiko infeksi"),
            createCheck("Risiko aspirasi"),
            createCheck("Risiko kerusakan integritas kulit")
        };

        section.add(createChecklistRow(null, diagnosisChecks, 2));
        return section;
    }

    private JPanel createSosialPsikologiSection() {
        JPanel section = createSectionPanel("I. Hubungan Status Sosial Psikologi");
        section.add(createSectionHint("Lengkapi kondisi psikologis, keadaan mental, dan dukungan keluarga untuk membantu kesinambungan pelayanan."));

        emosiChecks = new CekBox[]{
            createCheck("Cemas"),
            createCheck("Takut"),
            createCheck("Marah"),
            createCheck("Sedih")
        };
        MentalOrientasiBaik = createCheck("Sadar dan orientasi baik");
        MentalMasalahPerilaku = createCheck("Ada masalah perilaku");
        MentalMasalahText = createTextBox(240);
        MentalPerilakuKekerasan = createCheck("Perilaku kekerasan sebelumnya");
        StatusSosialField = createTextBox(220);
        TempatTinggalField = createTextBox(220);
        HubunganKeluargaField = createTextBox(260);
        OrangTuaWaliField = createTextBox(220);
        PetugasField = createTextBox(220);

        section.add(createChecklistRow("Status psikologis", emosiChecks, 4));
        section.add(createRow(
            field("Keadaan mental", createInlinePanel(
                MentalOrientasiBaik,
                MentalMasalahPerilaku,
                MentalPerilakuKekerasan
            )),
            field("Masalah perilaku / sebutkan", MentalMasalahText)
        ));
        section.add(createRow(
            field("Status sosial", StatusSosialField),
            field("Tempat tinggal", TempatTinggalField)
        ));
        section.add(createRow(field("Hubungan pasien dengan anggota keluarga", HubunganKeluargaField)));
        section.add(createRow(
            field("Orang tua / wali bayi", OrangTuaWaliField),
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
        return BorderFactory.createCompoundBorder(
            titledBorder,
            new EmptyBorder(12, 12, 12, 12)
        );
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

        JLabel title = new JLabel("Panduan Pengisian Asesmen Bayi IGD");
        title.setFont(new Font("Tahoma", Font.BOLD, 13));
        title.setForeground(TITLE_COLOR);

        JLabel description = new JLabel("<html>Lengkapi data dari identitas, primary survey, skrining gizi, hingga sosial psikologi. "
                + "Skor gizi, Humpty Dumpty, dan NIPS dihitung otomatis setelah pilihan diisi.</html>");
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
        int height = Math.max(72, area.getRows() * 24);
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
            "Tgl.Asesmen", "Triage", "Keluhan", "Humpty", "NIPS", "Petugas"
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

        setDataTableColumnWidths();

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

    private void setDataTableColumnWidths() {
        int[] widths = new int[]{120, 90, 220, 80, 90, 130, 70, 300, 60, 60, 160};
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
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
        RefleksField.setText("");
        GcsField.setText("");
        NadiNeuroField.setText("");
        SuhuField.setText("");

        SkriningGizi1.setSelectedIndex(0);
        SkriningGizi2.setSelectedIndex(0);
        DietisienDiberitahu.setSelected(false);
        JamLaporDietisien.setText("");
        updateGiziScore();

        FungsionalTidakAda.setSelected(true);
        FungsionalTongkat.setSelected(false);
        FungsionalKursiRoda.setSelected(false);
        FungsionalLain.setSelected(false);
        FungsionalLainText.setText("");
        CatatTubuhField.setText("");

        resetComboArray(HumptyCombo);
        updateHumptyScore();

        resetComboArray(NipsCombo);
        updateNipsScore();

        PenilaianKondisi.setText("");
        resetChecks(dischargeChecks);
        DischargeLainnya.setText("");

        resetChecks(diagnosisChecks);

        resetChecks(emosiChecks);
        MentalOrientasiBaik.setSelected(false);
        MentalMasalahPerilaku.setSelected(false);
        MentalMasalahText.setText("");
        MentalPerilakuKekerasan.setSelected(false);
        StatusSosialField.setText("");
        TempatTinggalField.setText("");
        HubunganKeluargaField.setText("");
        OrangTuaWaliField.setText("");
        if (PetugasField.getText().trim().equals("") && akses.getkode() != null) {
            PetugasField.setText(akses.getkode());
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

    private void updateNipsScore() {
        int total = 0;
        boolean lengkap = true;
        for (int i = 0; i < NipsCombo.length; i++) {
            int skor = getChoiceScore(NipsCombo[i]);
            NipsNilai[i].setText(String.valueOf(skor));
            total += skor;
            if (NipsCombo[i].getSelectedIndex() == 0) {
                lengkap = false;
            }
        }
        TotalNips.setText(String.valueOf(total));
        if (!lengkap && total == 0) {
            KeteranganNips.setText("Belum dinilai");
        } else if (!lengkap) {
            KeteranganNips.setText("Isian belum lengkap");
        } else if (total == 0) {
            KeteranganNips.setText("Tidak perlu intervensi");
        } else if (total <= 3) {
            KeteranganNips.setText("Intervensi non-farmakologis");
        } else if (total <= 5) {
            KeteranganNips.setText("Terapi analgetik non-opioid");
        } else {
            KeteranganNips.setText("Terapi opioid");
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
        html.append("<h2>Penilaian Awal Keperawatan UGD Bayi</h2>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "No. Rawat", safeText(TNoRw));
        appendRow(html, "No. RM", safeText(TNoRM));
        appendRow(html, "Nama", safeText(TPasien));
        appendRow(html, "L/P", safeText(JK));
        appendRow(html, "Tgl. Lahir", safeText(TglLahir));
        appendRow(html, "Tanggal Asesmen", formatTanggal(TglAsesmen));
        appendRow(html, "Jam Asesmen", safeText(JamAsesmen));
        appendRow(html, "Kategori Triage", selectedText(KategoriTriage));
        appendRow(html, "Keluhan / mekanisme kejadian", safeText(KeluhanSaatIni));
        appendRow(html, "Riwayat alergi", AlergiTidakAda.isSelected() ? "Tidak ada" : safeText(AlergiSebutkan));
        html.append("</table>");

        html.append("<h3>Primary Survey</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Airway", joinSelected(airwayChecks));
        appendRow(html, "Breathing", joinSelected(breathingChecks));
        appendRow(html, "Sirkulasi", buildCirculationSummary());
        appendRow(html, "Neurologi", buildNeurologiSummary());
        html.append("</table>");

        html.append("<h3>Skrining Gizi</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Penurunan BB", selectedText(SkriningGizi1));
        appendRow(html, "Asupan makan", selectedText(SkriningGizi2));
        appendRow(html, "Skor total", safeText(TotalSkorGizi) + " - " + safeText(KeteranganGizi));
        appendRow(html, "Dietisien / dokter diberitahu", DietisienDiberitahu.isSelected() ? "Ya, jam " + safeText(JamLaporDietisien) : "Tidak");
        html.append("</table>");

        html.append("<h3>Status Fungsional</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Penggunaan alat bantu", buildFungsionalSummary());
        appendRow(html, "Cacat tubuh", safeText(CatatTubuhField));
        html.append("</table>");

        html.append("<h3>Humpty Dumpty</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        for (int i = 0; i < HumptyCombo.length; i++) {
            appendRow(html, "Parameter " + (i + 1), selectedText(HumptyCombo[i]) + " (Skor " + safeText(HumptyNilai[i]) + ")");
        }
        appendRow(html, "Total", safeText(TotalHumpty) + " - " + safeText(KeteranganHumpty));
        html.append("</table>");

        html.append("<h3>NIPS</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        for (int i = 0; i < NipsCombo.length; i++) {
            appendRow(html, "Parameter " + (i + 1), selectedText(NipsCombo[i]) + " (Nilai " + safeText(NipsNilai[i]) + ")");
        }
        appendRow(html, "Total", safeText(TotalNips) + " - " + safeText(KeteranganNips));
        html.append("</table>");

        html.append("<h3>Discharge Planning</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Penilaian klinis", safeText(PenilaianKondisi));
        appendRow(html, "Rencana discharge", joinSelected(dischargeChecks));
        appendRow(html, "Catatan lain", safeText(DischargeLainnya));
        html.append("</table>");

        html.append("<h3>Diagnosis Keperawatan</h3>");
        html.append("<p>").append(escapeHtml(joinSelected(diagnosisChecks))).append("</p>");

        html.append("<h3>Sosial Psikologi</h3>");
        html.append("<table cellpadding='4' cellspacing='0' style='border-collapse:collapse;width:100%;'>");
        appendRow(html, "Status psikologis", joinSelected(emosiChecks));
        appendRow(html, "Keadaan mental", buildMentalSummary());
        appendRow(html, "Status sosial", safeText(StatusSosialField));
        appendRow(html, "Tempat tinggal", safeText(TempatTinggalField));
        appendRow(html, "Hubungan pasien dengan keluarga", safeText(HubunganKeluargaField));
        appendRow(html, "Orang tua / wali bayi", safeText(OrangTuaWaliField));
        appendRow(html, "Petugas", safeText(PetugasField));
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
        if (values.isEmpty()) {
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
            + ", Refleks: " + safeText(RefleksField)
            + ", GCS: " + safeText(GcsField)
            + ", Nadi: " + safeText(NadiNeuroField)
            + ", Suhu: " + safeText(SuhuField);
    }

    private String buildFungsionalSummary() {
        List<String> values = new ArrayList<String>();
        if (FungsionalTidakAda.isSelected()) {
            values.add("Tidak");
        }
        if (FungsionalTongkat.isSelected()) {
            values.add("Tongkat");
        }
        if (FungsionalKursiRoda.isSelected()) {
            values.add("Kursi roda");
        }
        if (FungsionalLain.isSelected()) {
            values.add("Lain-lain: " + safeText(FungsionalLainText));
        }
        if (values.isEmpty()) {
            return "-";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(values.get(i));
        }
        return result.toString();
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
            values.add("Perilaku kekerasan sebelumnya");
        }
        if (values.isEmpty()) {
            return "-";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(values.get(i));
        }
        return result.toString();
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

    public Map<String, String> collectStructuredData() {
        LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
        data.put("no_rawat", safeText(TNoRw));
        data.put("no_rm", safeText(TNoRM));
        data.put("nama", safeText(TPasien));
        data.put("jk", safeText(JK));
        data.put("tgl_lahir", safeText(TglLahir));
        data.put("tgl_asesmen", formatTanggal(TglAsesmen));
        data.put("jam_asesmen", safeText(JamAsesmen));
        data.put("triage", selectedText(KategoriTriage));
        data.put("keluhan", safeText(KeluhanSaatIni));
        data.put("alergi", AlergiTidakAda.isSelected() ? "Tidak ada" : safeText(AlergiSebutkan));
        data.put("airway", joinSelected(airwayChecks));
        data.put("breathing", joinSelected(breathingChecks));
        data.put("humpty_total", safeText(TotalHumpty));
        data.put("humpty_keterangan", safeText(KeteranganHumpty));
        data.put("nips_total", safeText(TotalNips));
        data.put("nips_keterangan", safeText(KeteranganNips));
        data.put("diagnosis_keperawatan", joinSelected(diagnosisChecks));
        data.put("status_psikologis", joinSelected(emosiChecks));
        data.put("orang_tua_wali", safeText(OrangTuaWaliField));
        data.put("petugas", safeText(PetugasField));
        return data;
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
        data.add(safeText(RefleksField));
        data.add(safeText(GcsField));
        data.add(safeText(NadiNeuroField));
        data.add(safeText(SuhuField));

        data.add(comboDbValue(SkriningGizi1));
        data.add(nilaiAtauNol(SkorGizi1));
        data.add(comboDbValue(SkriningGizi2));
        data.add(nilaiAtauNol(SkorGizi2));
        data.add(nilaiAtauNol(TotalSkorGizi));
        data.add(safeText(KeteranganGizi));
        data.add(yaTidak(DietisienDiberitahu));
        data.add(safeText(JamLaporDietisien));

        data.add(yaTidak(FungsionalTidakAda));
        data.add(yaTidak(FungsionalTongkat));
        data.add(yaTidak(FungsionalKursiRoda));
        data.add(yaTidak(FungsionalLain));
        data.add(safeText(FungsionalLainText));
        data.add(safeText(CatatTubuhField));

        data.add(comboDbValue(HumptyCombo[0]));
        data.add(nilaiAtauNol(HumptyNilai[0]));
        data.add(comboDbValue(HumptyCombo[1]));
        data.add(nilaiAtauNol(HumptyNilai[1]));
        data.add(comboDbValue(HumptyCombo[2]));
        data.add(nilaiAtauNol(HumptyNilai[2]));
        data.add(comboDbValue(HumptyCombo[3]));
        data.add(nilaiAtauNol(HumptyNilai[3]));
        data.add(comboDbValue(HumptyCombo[4]));
        data.add(nilaiAtauNol(HumptyNilai[4]));
        data.add(comboDbValue(HumptyCombo[5]));
        data.add(nilaiAtauNol(HumptyNilai[5]));
        data.add(comboDbValue(HumptyCombo[6]));
        data.add(nilaiAtauNol(HumptyNilai[6]));
        data.add(nilaiAtauNol(TotalHumpty));
        data.add(safeText(KeteranganHumpty));

        data.add(comboDbValue(NipsCombo[0]));
        data.add(nilaiAtauNol(NipsNilai[0]));
        data.add(comboDbValue(NipsCombo[1]));
        data.add(nilaiAtauNol(NipsNilai[1]));
        data.add(comboDbValue(NipsCombo[2]));
        data.add(nilaiAtauNol(NipsNilai[2]));
        data.add(comboDbValue(NipsCombo[3]));
        data.add(nilaiAtauNol(NipsNilai[3]));
        data.add(comboDbValue(NipsCombo[4]));
        data.add(nilaiAtauNol(NipsNilai[4]));
        data.add(nilaiAtauNol(TotalNips));
        data.add(safeText(KeteranganNips));

        data.add(safeText(PenilaianKondisi));
        data.add(yaTidak(dischargeChecks[0]));
        data.add(yaTidak(dischargeChecks[1]));
        data.add(yaTidak(dischargeChecks[2]));
        data.add(yaTidak(dischargeChecks[3]));
        data.add(yaTidak(dischargeChecks[4]));
        data.add(yaTidak(dischargeChecks[5]));
        data.add(yaTidak(dischargeChecks[6]));
        data.add(safeText(DischargeLainnya));

        data.add(yaTidak(diagnosisChecks[0]));
        data.add(yaTidak(diagnosisChecks[1]));
        data.add(yaTidak(diagnosisChecks[2]));
        data.add(yaTidak(diagnosisChecks[3]));
        data.add(yaTidak(diagnosisChecks[4]));
        data.add(yaTidak(diagnosisChecks[5]));
        data.add(yaTidak(diagnosisChecks[6]));
        data.add(yaTidak(diagnosisChecks[7]));
        data.add(yaTidak(diagnosisChecks[8]));
        data.add(yaTidak(diagnosisChecks[9]));
        data.add(yaTidak(diagnosisChecks[10]));
        data.add(yaTidak(diagnosisChecks[11]));
        data.add(yaTidak(diagnosisChecks[12]));
        data.add(yaTidak(diagnosisChecks[13]));

        data.add(yaTidak(emosiChecks[0]));
        data.add(yaTidak(emosiChecks[1]));
        data.add(yaTidak(emosiChecks[2]));
        data.add(yaTidak(emosiChecks[3]));
        data.add(yaTidak(MentalOrientasiBaik));
        data.add(yaTidak(MentalMasalahPerilaku));
        data.add(safeText(MentalMasalahText));
        data.add(yaTidak(MentalPerilakuKekerasan));
        data.add(safeText(StatusSosialField));
        data.add(safeText(TempatTinggalField));
        data.add(safeText(HubunganKeluargaField));
        data.add(safeText(OrangTuaWaliField));
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
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
            return false;
        } else if (KategoriTriage.getSelectedIndex() == 0) {
            Valid.textKosong(KategoriTriage, "Kategori triage");
            return false;
        } else if (KeluhanSaatIni.getText().trim().equals("")) {
            Valid.textKosong(KeluhanSaatIni, "Keluhan saat ini");
            return false;
        } else if (PetugasField.getText().trim().equals("")) {
            Valid.textKosong(PetugasField, "Petugas");
            return false;
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
                "select ifnull(nullif(p.nama,''),ifnull(nullif(pa.petugas_display,''),'')) " +
                "from " + TABLE_PENILAIAN + " pa left join petugas p on p.nip=pa.nip where pa.no_rawat=?",
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
                    "select sha1(sidikjari.sidikjari) from sidikjari " +
                    "inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    nipPetugas
            ).trim();
        }
        String identitasTtd = finger.equals("") ? (nipPetugas.equals("") ? namaPetugas : nipPetugas) : finger;
        String tanggalTtd = tanggalAsesmen.equals("") ? "-" : Valid.SetTgl3(tanggalAsesmen);
        param.put(
                "finger",
                "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() +
                "\nDitandatangani secara elektronik oleh " + (namaPetugas.equals("") ? "-" : namaPetugas) +
                "\nID " + (identitasTtd.equals("") ? "-" : identitasTtd) +
                "\n" + tanggalTtd
        );
        return param;
    }

    private String sqlEscape(String value) {
        return value == null ? "" : value.replace("'", "''");
    }

    private String buildReportQuery(String noRawat) {
        String rawat = sqlEscape(noRawat);
        return "select rp.no_rawat,ps.no_rkm_medis,ps.nm_pasien," +
                "if(ps.jk='L','Laki-Laki','Perempuan') as jk," +
                "date_format(ps.tgl_lahir,'%d-%m-%Y') as tgl_lahir_text," +
                "date_format(pa.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal_text," +
                "ifnull(nullif(p.nama,''),ifnull(nullif(pa.petugas_display,''),'-')) as nama_petugas," +
                "ifnull(nullif(pa.kategori_triage,''),'-') as kategori_triage," +
                "ifnull(nullif(pa.keluhan_saat_ini,''),'-') as keluhan_saat_ini," +
                "if(pa.alergi_tidak_ada='Ya','Tidak ada alergi',ifnull(nullif(pa.alergi_keterangan,''),'-')) as alergi_info," +
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
                "', Turgor: ',ifnull(nullif(pa.turgor_kulit,''),'-'),', Respirasi: ',ifnull(nullif(pa.respirasi,''),'-')," +
                "', TD: ',ifnull(nullif(pa.tekanan_darah,''),'-')) as circulation_summary," +
                "concat('Respon: ',ifnull(nullif(pa.respon,''),'-'),', Refleks: ',ifnull(nullif(pa.refleks,''),'-')," +
                "', GCS: ',ifnull(nullif(pa.gcs,''),'-'),', Nadi: ',ifnull(nullif(pa.nadi_neurologi,''),'-')," +
                "', Suhu: ',ifnull(nullif(pa.suhu,''),'-')) as neurologi_summary," +
                "concat('1) ',ifnull(nullif(pa.skrining_gizi1,''),'-'),' = ',pa.nilai_gizi1," +
                "'; 2) ',ifnull(nullif(pa.skrining_gizi2,''),'-'),' = ',pa.nilai_gizi2," +
                "'; Total: ',pa.total_skor_gizi,' (',ifnull(nullif(pa.keterangan_gizi,''),'-'),')'," +
                "'; Dietisien: ',if(pa.dietisien_diberitahu='Ya',concat('Ya ',ifnull(nullif(pa.jam_lapor_dietisien,''),'')),'Tidak')) as gizi_summary," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.fungsional_tidak='Ya','Tidak',null)," +
                "if(pa.fungsional_tongkat='Ya','Tongkat',null)," +
                "if(pa.fungsional_kursi_roda='Ya','Kursi roda',null)," +
                "if(pa.fungsional_lain='Ya',concat('Lain-lain: ',ifnull(nullif(pa.fungsional_lain_keterangan,''),'-')),null)),''),'-') as fungsional_summary," +
                "ifnull(nullif(pa.cacat_tubuh,''),'-') as cacat_tubuh," +
                "concat('1) ',ifnull(nullif(pa.humpty_skala1,''),'-'),' [',pa.humpty_nilai1,']; '," +
                "'2) ',ifnull(nullif(pa.humpty_skala2,''),'-'),' [',pa.humpty_nilai2,']; '," +
                "'3) ',ifnull(nullif(pa.humpty_skala3,''),'-'),' [',pa.humpty_nilai3,']; '," +
                "'4) ',ifnull(nullif(pa.humpty_skala4,''),'-'),' [',pa.humpty_nilai4,']; '," +
                "'5) ',ifnull(nullif(pa.humpty_skala5,''),'-'),' [',pa.humpty_nilai5,']; '," +
                "'6) ',ifnull(nullif(pa.humpty_skala6,''),'-'),' [',pa.humpty_nilai6,']; '," +
                "'7) ',ifnull(nullif(pa.humpty_skala7,''),'-'),' [',pa.humpty_nilai7,']; '," +
                "'Total: ',pa.humpty_total,' (',ifnull(nullif(pa.humpty_keterangan,''),'-'),')') as humpty_summary," +
                "concat('1) ',ifnull(nullif(pa.nips_skala1,''),'-'),' [',pa.nips_nilai1,']; '," +
                "'2) ',ifnull(nullif(pa.nips_skala2,''),'-'),' [',pa.nips_nilai2,']; '," +
                "'3) ',ifnull(nullif(pa.nips_skala3,''),'-'),' [',pa.nips_nilai3,']; '," +
                "'4) ',ifnull(nullif(pa.nips_skala4,''),'-'),' [',pa.nips_nilai4,']; '," +
                "'5) ',ifnull(nullif(pa.nips_skala5,''),'-'),' [',pa.nips_nilai5,']; '," +
                "'Total: ',pa.nips_total,' (',ifnull(nullif(pa.nips_keterangan,''),'-'),')') as nips_summary," +
                "ifnull(nullif(pa.penilaian_kondisi,''),'-') as penilaian_kondisi," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.discharge_rawat_gabung='Ya','Rawat gabung',null)," +
                "if(pa.discharge_diselimuti_hangat='Ya','Diselimuti hangat',null)," +
                "if(pa.discharge_kirim_ke_ruang_bayi='Ya','Kirim ke ruang bayi',null)," +
                "if(pa.discharge_vitamin_k='Ya','Vitamin K diberikan',null)," +
                "if(pa.discharge_observasi_suhu='Ya','Observasi suhu 2-3 jam',null)," +
                "if(pa.discharge_pemantauan_glukosa='Ya','Pemantauan glukosa',null)," +
                "if(pa.discharge_edukasi_keluarga='Ya','Edukasi keluarga',null)),''),'-') as discharge_summary," +
                "ifnull(nullif(pa.discharge_lainnya,''),'-') as discharge_lainnya," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.diagnosis_bersihan_jalan_napas_tidak_efektif='Ya','Bersihan jalan napas tidak efektif',null)," +
                "if(pa.diagnosis_pola_napas_tidak_efektif='Ya','Pola napas tidak efektif',null)," +
                "if(pa.diagnosis_gangguan_pertukaran_gas='Ya','Gangguan pertukaran gas',null)," +
                "if(pa.diagnosis_termoregulasi_tidak_efektif='Ya','Termoregulasi tidak efektif',null)," +
                "if(pa.diagnosis_ketidakseimbangan_nutrisi='Ya','Ketidakseimbangan nutrisi',null)," +
                "if(pa.diagnosis_ikterik_berhubungan_menyusui='Ya','Ikterik berhubungan menyusui',null)," +
                "if(pa.diagnosis_kurang_volume_cairan='Ya','Kurang volume cairan',null)," +
                "if(pa.diagnosis_diare='Ya','Diare',null)," +
                "if(pa.diagnosis_perfusi_jaringan_gastrointestinal='Ya','Perfusi jaringan gastrointestinal tidak efektif',null)," +
                "if(pa.diagnosis_kesiapan_peningkatan_nutrisi='Ya','Kesiapan peningkatan nutrisi',null)," +
                "if(pa.diagnosis_menyusui_tidak_efektif='Ya','Menyusui tidak efektif',null)," +
                "if(pa.diagnosis_risiko_infeksi='Ya','Risiko infeksi',null)," +
                "if(pa.diagnosis_risiko_aspirasi='Ya','Risiko aspirasi',null)," +
                "if(pa.diagnosis_risiko_kerusakan_integritas_kulit='Ya','Risiko kerusakan integritas kulit',null)),''),'-') as diagnosis_summary," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.psikologis_cemas='Ya','Cemas',null)," +
                "if(pa.psikologis_takut='Ya','Takut',null)," +
                "if(pa.psikologis_marah='Ya','Marah',null)," +
                "if(pa.psikologis_sedih='Ya','Sedih',null)),''),'-') as psikologis_summary," +
                "ifnull(nullif(concat_ws(', '," +
                "if(pa.mental_orientasi_baik='Ya','Sadar dan orientasi baik',null)," +
                "if(pa.mental_masalah_perilaku='Ya',concat('Ada masalah perilaku: ',ifnull(nullif(pa.mental_masalah_perilaku_keterangan,''),'-')),null)," +
                "if(pa.mental_perilaku_kekerasan='Ya','Perilaku kekerasan sebelumnya',null)),''),'-') as mental_summary," +
                "concat('Status sosial: ',ifnull(nullif(pa.status_sosial,''),'-'),'; Tempat tinggal: ',ifnull(nullif(pa.tempat_tinggal,''),'-')," +
                "'; Hubungan keluarga: ',ifnull(nullif(pa.hubungan_keluarga,''),'-'),'; Orang tua/Wali: ',ifnull(nullif(pa.orang_tua_wali_bayi,''),'-')) as sosial_summary " +
                "from penilaian_awal_keperawatan_bayi_igd pa " +
                "inner join reg_periksa rp on rp.no_rawat=pa.no_rawat " +
                "inner join pasien ps on ps.no_rkm_medis=rp.no_rkm_medis " +
                "left join petugas p on p.nip=pa.nip " +
                "where pa.no_rawat='" + rawat + "'";
    }

    private void pilihBarisData(String noRawat) {
        if (tbData == null || noRawat.trim().equals("")) {
            return;
        }
        for (int i = 0; i < tbData.getRowCount(); i++) {
            if (noRawat.equals(tbData.getValueAt(i, 0).toString())) {
                tbData.setRowSelectionInterval(i, i);
                tbData.scrollRectToVisible(tbData.getCellRect(i, 0, true));
                return;
            }
        }
    }

    private void tampilData() {
        if (tabMode == null) {
            return;
        }

        while (tabMode.getRowCount() > 0) {
            tabMode.removeRow(0);
        }

        String cari = "";
        if (TCariData != null) {
            cari = TCariData.getText().trim();
        }

        try {
            ps = koneksi.prepareStatement(
                "select penilaian_awal_keperawatan_bayi_igd.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien," +
                "if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_awal_keperawatan_bayi_igd.tanggal," +
                "penilaian_awal_keperawatan_bayi_igd.kategori_triage,penilaian_awal_keperawatan_bayi_igd.keluhan_saat_ini," +
                "penilaian_awal_keperawatan_bayi_igd.humpty_total,penilaian_awal_keperawatan_bayi_igd.nips_total," +
                "ifnull(petugas.nama,penilaian_awal_keperawatan_bayi_igd.petugas_display) as nama_petugas " +
                "from penilaian_awal_keperawatan_bayi_igd " +
                "inner join reg_periksa on reg_periksa.no_rawat=penilaian_awal_keperawatan_bayi_igd.no_rawat " +
                "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis " +
                "left join petugas on petugas.nip=penilaian_awal_keperawatan_bayi_igd.nip " +
                "where penilaian_awal_keperawatan_bayi_igd.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? " +
                "order by penilaian_awal_keperawatan_bayi_igd.tanggal desc"
            );
            String keyword = "%" + cari + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("no_rawat"),
                    rs.getString("no_rkm_medis"),
                    rs.getString("nm_pasien"),
                    rs.getString("jk"),
                    rs.getString("tgl_lahir"),
                    rs.getString("tanggal"),
                    rs.getString("kategori_triage"),
                    rs.getString("keluhan_saat_ini"),
                    rs.getString("humpty_total"),
                    rs.getString("nips_total"),
                    rs.getString("nama_petugas")
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
                "select penilaian_awal_keperawatan_bayi_igd.*,reg_periksa.no_rkm_medis,pasien.nm_pasien," +
                "if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir," +
                "ifnull(petugas.nama,penilaian_awal_keperawatan_bayi_igd.petugas_display) as nama_petugas " +
                "from penilaian_awal_keperawatan_bayi_igd " +
                "inner join reg_periksa on reg_periksa.no_rawat=penilaian_awal_keperawatan_bayi_igd.no_rawat " +
                "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis " +
                "left join petugas on petugas.nip=penilaian_awal_keperawatan_bayi_igd.nip " +
                "where penilaian_awal_keperawatan_bayi_igd.no_rawat=?"
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
                RefleksField.setText(rs.getString("refleks"));
                GcsField.setText(rs.getString("gcs"));
                NadiNeuroField.setText(rs.getString("nadi_neurologi"));
                SuhuField.setText(rs.getString("suhu"));

                setComboByText(SkriningGizi1, rs.getString("skrining_gizi1"));
                setComboByText(SkriningGizi2, rs.getString("skrining_gizi2"));
                DietisienDiberitahu.setSelected("Ya".equals(rs.getString("dietisien_diberitahu")));
                JamLaporDietisien.setText(rs.getString("jam_lapor_dietisien"));

                FungsionalTidakAda.setSelected("Ya".equals(rs.getString("fungsional_tidak")));
                FungsionalTongkat.setSelected("Ya".equals(rs.getString("fungsional_tongkat")));
                FungsionalKursiRoda.setSelected("Ya".equals(rs.getString("fungsional_kursi_roda")));
                FungsionalLain.setSelected("Ya".equals(rs.getString("fungsional_lain")));
                FungsionalLainText.setText(rs.getString("fungsional_lain_keterangan"));
                CatatTubuhField.setText(rs.getString("cacat_tubuh"));

                setComboByText(HumptyCombo[0], rs.getString("humpty_skala1"));
                setComboByText(HumptyCombo[1], rs.getString("humpty_skala2"));
                setComboByText(HumptyCombo[2], rs.getString("humpty_skala3"));
                setComboByText(HumptyCombo[3], rs.getString("humpty_skala4"));
                setComboByText(HumptyCombo[4], rs.getString("humpty_skala5"));
                setComboByText(HumptyCombo[5], rs.getString("humpty_skala6"));
                setComboByText(HumptyCombo[6], rs.getString("humpty_skala7"));

                setComboByText(NipsCombo[0], rs.getString("nips_skala1"));
                setComboByText(NipsCombo[1], rs.getString("nips_skala2"));
                setComboByText(NipsCombo[2], rs.getString("nips_skala3"));
                setComboByText(NipsCombo[3], rs.getString("nips_skala4"));
                setComboByText(NipsCombo[4], rs.getString("nips_skala5"));

                PenilaianKondisi.setText(rs.getString("penilaian_kondisi"));
                dischargeChecks[0].setSelected("Ya".equals(rs.getString("discharge_rawat_gabung")));
                dischargeChecks[1].setSelected("Ya".equals(rs.getString("discharge_diselimuti_hangat")));
                dischargeChecks[2].setSelected("Ya".equals(rs.getString("discharge_kirim_ke_ruang_bayi")));
                dischargeChecks[3].setSelected("Ya".equals(rs.getString("discharge_vitamin_k")));
                dischargeChecks[4].setSelected("Ya".equals(rs.getString("discharge_observasi_suhu")));
                dischargeChecks[5].setSelected("Ya".equals(rs.getString("discharge_pemantauan_glukosa")));
                dischargeChecks[6].setSelected("Ya".equals(rs.getString("discharge_edukasi_keluarga")));
                DischargeLainnya.setText(rs.getString("discharge_lainnya"));

                diagnosisChecks[0].setSelected("Ya".equals(rs.getString("diagnosis_bersihan_jalan_napas_tidak_efektif")));
                diagnosisChecks[1].setSelected("Ya".equals(rs.getString("diagnosis_pola_napas_tidak_efektif")));
                diagnosisChecks[2].setSelected("Ya".equals(rs.getString("diagnosis_gangguan_pertukaran_gas")));
                diagnosisChecks[3].setSelected("Ya".equals(rs.getString("diagnosis_termoregulasi_tidak_efektif")));
                diagnosisChecks[4].setSelected("Ya".equals(rs.getString("diagnosis_ketidakseimbangan_nutrisi")));
                diagnosisChecks[5].setSelected("Ya".equals(rs.getString("diagnosis_ikterik_berhubungan_menyusui")));
                diagnosisChecks[6].setSelected("Ya".equals(rs.getString("diagnosis_kurang_volume_cairan")));
                diagnosisChecks[7].setSelected("Ya".equals(rs.getString("diagnosis_diare")));
                diagnosisChecks[8].setSelected("Ya".equals(rs.getString("diagnosis_perfusi_jaringan_gastrointestinal")));
                diagnosisChecks[9].setSelected("Ya".equals(rs.getString("diagnosis_kesiapan_peningkatan_nutrisi")));
                diagnosisChecks[10].setSelected("Ya".equals(rs.getString("diagnosis_menyusui_tidak_efektif")));
                diagnosisChecks[11].setSelected("Ya".equals(rs.getString("diagnosis_risiko_infeksi")));
                diagnosisChecks[12].setSelected("Ya".equals(rs.getString("diagnosis_risiko_aspirasi")));
                diagnosisChecks[13].setSelected("Ya".equals(rs.getString("diagnosis_risiko_kerusakan_integritas_kulit")));

                emosiChecks[0].setSelected("Ya".equals(rs.getString("psikologis_cemas")));
                emosiChecks[1].setSelected("Ya".equals(rs.getString("psikologis_takut")));
                emosiChecks[2].setSelected("Ya".equals(rs.getString("psikologis_marah")));
                emosiChecks[3].setSelected("Ya".equals(rs.getString("psikologis_sedih")));
                MentalOrientasiBaik.setSelected("Ya".equals(rs.getString("mental_orientasi_baik")));
                MentalMasalahPerilaku.setSelected("Ya".equals(rs.getString("mental_masalah_perilaku")));
                MentalMasalahText.setText(rs.getString("mental_masalah_perilaku_keterangan"));
                MentalPerilakuKekerasan.setSelected("Ya".equals(rs.getString("mental_perilaku_kekerasan")));
                StatusSosialField.setText(rs.getString("status_sosial"));
                TempatTinggalField.setText(rs.getString("tempat_tinggal"));
                HubunganKeluargaField.setText(rs.getString("hubungan_keluarga"));
                OrangTuaWaliField.setText(rs.getString("orang_tua_wali_bayi"));
                PetugasField.setText(rs.getString("nama_petugas"));

                updateGiziScore();
                updateHumptyScore();
                updateNipsScore();
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
                "from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis " +
                "where reg_periksa.no_rawat=?"
            );
            try {
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
        updateNipsScore();
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
        updateNipsScore();
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
        final String reportBaseName = "rptFormulirPenilaianAwalKeperawatanBayiIGD";
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
                "::[ Formulir Penilaian Awal Keperawatan Bayi IGD ]::",
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
                "Hapus data penilaian bayi IGD untuk No. Rawat " + TNoRw.getText() + " ?",
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
        updateNipsScore();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Bayi IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
