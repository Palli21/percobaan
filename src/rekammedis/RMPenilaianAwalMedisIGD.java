/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */


package rekammedis;

import freehand.DlgMarkingImageAssMedisIGD;
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
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisIGD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="",urlImage;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Cara Datang","Ket. Cara Datang","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Penyakit Keluarga","Riwayat Penggunakan Obat","Riwayat Alergi","Keadaan Umum","GCS","Kesadaran","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu","SpO2","BB(Kg)","TB(cm)","Kepala",
            "Mata","Gigi & Mulut","Leher","Thoraks","Abdomen","Genital & Anus","Ekstremitas","Ket.Pemeriksaan Fisik","Ket.Status Lokalis","EKG","Radiologi","Laborat","Diagnosis/Asesmen","Tatalaksana"
            ,"Tempat Kecelakaan","Tanggal Kecelakaan","Jam Kecelakaan","Jenis Kecelakaan/Kendaraan","Bagian 1","Diminta Jam 1","Datang Jam 1","Bagian 2","Diminta Jam 2","Datang Jam 2","Bagian 3","Diminta Jam 3","Datang Jam 3",
            "Observasi","Dipulangkan","PAPS","Dirujuk","Dirujuk Ke","Rawat Inap","Ket. Rawat Inap"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < tabMode.getColumnCount(); i++) {
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
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(140);
            }else if(i==11){
                column.setPreferredWidth(180);
            }else if(i==12){
                column.setPreferredWidth(300);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(120);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(50);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(60);
            }else if(i==22){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(67);
            }else if(i==24){
                column.setPreferredWidth(40);
            }else if(i==25){
                column.setPreferredWidth(40);
            }else if(i==26){
                column.setPreferredWidth(40);
            }else if(i==27){
                column.setPreferredWidth(40);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(300);
            }else if(i==37){
                column.setPreferredWidth(200);
            }else if(i==38){
                column.setPreferredWidth(170);
            }else if(i==39){
                column.setPreferredWidth(170);
            }else if(i==40){
                column.setPreferredWidth(170);
            }else if(i==41){
                column.setPreferredWidth(150);
            }else if(i==42){
                column.setPreferredWidth(300);
            }else if(i==43){
                column.setPreferredWidth(170);
            }else if(i==44){
                column.setPreferredWidth(130);
            }else if(i==45){
                column.setPreferredWidth(90);
            }else if(i==46){
                column.setPreferredWidth(200);
            }else if(i==47){
                column.setPreferredWidth(150);
            }else if(i==48){
                column.setPreferredWidth(90);
            }else if(i==49){
                column.setPreferredWidth(90);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(90);
            }else if(i==52){
                column.setPreferredWidth(90);
            }else if(i==53){
                column.setPreferredWidth(150);
            }else if(i==54){
                column.setPreferredWidth(90);
            }else if(i==55){
                column.setPreferredWidth(90);
            }else if(i==56){
                column.setPreferredWidth(90);
            }else if(i==57){
                column.setPreferredWidth(100);
            }else if(i==58){
                column.setPreferredWidth(80);
            }else if(i==59){
                column.setPreferredWidth(100);
            }else if(i==60){
                column.setPreferredWidth(170);
            }else if(i==61){
                column.setPreferredWidth(100);
            }else if(i==62){
                column.setPreferredWidth(170);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KetCaraDatang.setDocument(new batasInput((int)100).getKata(KetCaraDatang));
        TempatKecelakaan.setDocument(new batasInput((int)150).getKata(TempatKecelakaan));
        TglKecelakaan.setDocument(new batasInput((int)50).getKata(TglKecelakaan));
        JamKecelakaan.setDocument(new batasInput((int)20).getKata(JamKecelakaan));
        Bagian1.setDocument(new batasInput((int)100).getKata(Bagian1));
        DimintaJam1.setDocument(new batasInput((int)20).getKata(DimintaJam1));
        DatangJam1.setDocument(new batasInput((int)20).getKata(DatangJam1));
        Bagian2.setDocument(new batasInput((int)100).getKata(Bagian2));
        DimintaJam2.setDocument(new batasInput((int)20).getKata(DimintaJam2));
        DatangJam2.setDocument(new batasInput((int)20).getKata(DatangJam2));
        Bagian3.setDocument(new batasInput((int)100).getKata(Bagian3));
        DimintaJam3.setDocument(new batasInput((int)20).getKata(DimintaJam3));
        DatangJam3.setDocument(new batasInput((int)20).getKata(DatangJam3));
        DirujukKe.setDocument(new batasInput((int)150).getKata(DirujukKe));
        RawatInapKe.setDocument(new batasInput((int)150).getKata(RawatInapKe));
        SembuhTD.setDocument(new batasInput((byte)15).getKata(SembuhTD));
        SembuhNadi.setDocument(new batasInput((byte)15).getKata(SembuhNadi));
        SembuhSuhu.setDocument(new batasInput((byte)15).getKata(SembuhSuhu));
        SembuhRR.setDocument(new batasInput((byte)15).getKata(SembuhRR));
        SembuhKesadaran.setDocument(new batasInput((int)100).getKata(SembuhKesadaran));
        MembaikTD.setDocument(new batasInput((byte)15).getKata(MembaikTD));
        MembaikNadi.setDocument(new batasInput((byte)15).getKata(MembaikNadi));
        MembaikSuhu.setDocument(new batasInput((byte)15).getKata(MembaikSuhu));
        MembaikRR.setDocument(new batasInput((byte)15).getKata(MembaikRR));
        MembaikKesadaran.setDocument(new batasInput((int)100).getKata(MembaikKesadaran));
        LanjutTD.setDocument(new batasInput((byte)15).getKata(LanjutTD));
        LanjutNadi.setDocument(new batasInput((byte)15).getKata(LanjutNadi));
        LanjutSuhu.setDocument(new batasInput((byte)15).getKata(LanjutSuhu));
        LanjutRR.setDocument(new batasInput((byte)15).getKata(LanjutRR));
        LanjutKesadaran.setDocument(new batasInput((int)100).getKata(LanjutKesadaran));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPK.setDocument(new batasInput((int)2000).getKata(RPK));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        RPO.setDocument(new batasInput((int)1000).getKata(RPO));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        GCS.setDocument(new batasInput((byte)10).getKata(GCS));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        SPO.setDocument(new batasInput((byte)5).getKata(SPO));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        KetFisik.setDocument(new batasInput((int)5000).getKata(KetFisik));
        KetLokalis.setDocument(new batasInput((int)3000).getKata(KetLokalis));
        EKG.setDocument(new batasInput((int)3000).getKata(EKG));
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
        Tatalaksana.setDocument(new batasInput((int)5000).getKata(Tatalaksana));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        updateDirujukKeState(true);
        updateRawatInapState(true);
        
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
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
        jLabel9 = new widget.Label();
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
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        scrollPane5 = new widget.ScrollPane();
        KetFisik = new widget.TextArea();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        Keadaan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel41 = new widget.Label();
        jLabel29 = new widget.Label();
        SPO = new widget.TextBox();
        jLabel35 = new widget.Label();
        Kepala = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Gigi = new widget.ComboBox();
        jLabel45 = new widget.Label();
        Leher = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Genital = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel104 = new widget.Label();
        CaraDatang = new widget.ComboBox();
        jLabel105 = new widget.Label();
        KetCaraDatang = new widget.TextBox();
        jLabel106 = new widget.Label();
        TempatKecelakaan = new widget.TextBox();
        jLabel107 = new widget.Label();
        TglKecelakaan = new widget.TextBox();
        jLabel108 = new widget.Label();
        JamKecelakaan = new widget.TextBox();
        jLabel109 = new widget.Label();
        JenisKecelakaan = new widget.ComboBox();
        PanelWall = new usu.widget.glass.PanelGlass();
        scrollPane8 = new widget.ScrollPane();
        KetLokalis = new widget.TextArea();
        jLabel79 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        EKG = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        jLabel103 = new widget.Label();
        jLabel110 = new widget.Label();
        Bagian1 = new widget.TextBox();
        jLabel111 = new widget.Label();
        DimintaJam1 = new widget.TextBox();
        jLabel112 = new widget.Label();
        DatangJam1 = new widget.TextBox();
        jLabel113 = new widget.Label();
        Bagian2 = new widget.TextBox();
        jLabel114 = new widget.Label();
        DimintaJam2 = new widget.TextBox();
        jLabel115 = new widget.Label();
        DatangJam2 = new widget.TextBox();
        jLabel116 = new widget.Label();
        Bagian3 = new widget.TextBox();
        jLabel117 = new widget.Label();
        DimintaJam3 = new widget.TextBox();
        jLabel118 = new widget.Label();
        DatangJam3 = new widget.TextBox();
        ChkObservasi = new widget.CekBox();
        ChkDipulangkan = new widget.CekBox();
        ChkPAPS = new widget.CekBox();
        ChkDirujukKe = new widget.CekBox();
        DirujukKe = new widget.TextBox();
        ChkRawatInap = new widget.CekBox();
        RawatInapKe = new widget.TextBox();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel42 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        hasil = new widget.TextArea();
        jLabel82 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        nilai = new widget.TextArea();
        BtnEdit2 = new widget.Button();
        labelKeadaanPulang = new widget.Label();
        ChkSembuh = new widget.CekBox();
        labelSembuhHasil = new widget.Label();
        SembuhTD = new widget.TextBox();
        labelSembuhNadi = new widget.Label();
        SembuhNadi = new widget.TextBox();
        labelSembuhSuhu = new widget.Label();
        SembuhSuhu = new widget.TextBox();
        labelSembuhSuhuSatuan = new widget.Label();
        labelSembuhRR = new widget.Label();
        SembuhRR = new widget.TextBox();
        labelSembuhKesadaran = new widget.Label();
        SembuhKesadaran = new widget.TextBox();
        ChkMembaik = new widget.CekBox();
        labelMembaikHasil = new widget.Label();
        MembaikTD = new widget.TextBox();
        labelMembaikNadi = new widget.Label();
        MembaikNadi = new widget.TextBox();
        labelMembaikSuhu = new widget.Label();
        MembaikSuhu = new widget.TextBox();
        labelMembaikSuhuSatuan = new widget.Label();
        labelMembaikRR = new widget.Label();
        MembaikRR = new widget.TextBox();
        labelMembaikKesadaran = new widget.Label();
        MembaikKesadaran = new widget.TextBox();
        ChkPerawatanLanjut = new widget.CekBox();
        labelLanjutHasil = new widget.Label();
        LanjutTD = new widget.TextBox();
        labelLanjutNadi = new widget.Label();
        LanjutNadi = new widget.TextBox();
        labelLanjutSuhu = new widget.Label();
        LanjutSuhu = new widget.TextBox();
        labelLanjutSuhuSatuan = new widget.Label();
        labelLanjutRR = new widget.Label();
        LanjutRR = new widget.TextBox();
        labelLanjutKesadaran = new widget.Label();
        LanjutKesadaran = new widget.TextBox();
        ChkMeninggal = new widget.CekBox();
        jLabel119 = new widget.Label();
        jLabel120 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(1417, 1819));
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
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

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
        BtnDokter.setBounds(348, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Penggunaan Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(10, 260, 180, 23);

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
        jLabel12.setBounds(770, 330, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(800, 330, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(850, 330, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(680, 330, 45, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(650, 330, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(380, 360, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(330, 360, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(290, 360, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(610, 360, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(660, 360, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(10, 360, 127, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(140, 360, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(710, 360, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(220, 360, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(730, 330, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(540, 360, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(490, 360, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(440, 360, 40, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(450, 260, 150, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(600, 260, 260, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(150, 210, 310, 43);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(460, 210, 150, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(630, 2140, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(480, 2140, 150, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(220, 2140, 255, 42);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(40, 2140, 180, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(190, 260, 255, 42);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        KetFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetFisik.setColumns(20);
        KetFisik.setRows(8);
        KetFisik.setName("KetFisik"); // NOI18N
        KetFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetFisikKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(KetFisik);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(520, 390, 340, 113);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(500, 330, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(570, 330, 60, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. PEMERIKSAAN FISIK");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(20, 310, 180, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 180, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(20, 210, 125, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(610, 210, 260, 43);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(10, 310, 880, 1);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(270, 330, 70, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(140, 330, 118, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(10, 390, 127, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(350, 330, 130, 23);

        jLabel41.setText("Keadaan Umum :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(10, 330, 127, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(760, 360, 40, 23);

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });
        FormInput.add(SPO);
        SPO.setBounds(800, 360, 45, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(850, 360, 30, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(140, 390, 128, 23);

        jLabel44.setText("Gigi & Mulut :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(10, 450, 127, 23);

        Gigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Gigi.setName("Gigi"); // NOI18N
        Gigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKeyPressed(evt);
            }
        });
        FormInput.add(Gigi);
        Gigi.setBounds(140, 450, 128, 23);

        jLabel45.setText("Leher :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(10, 480, 127, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(140, 480, 128, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(280, 390, 95, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(370, 390, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(280, 420, 95, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(370, 420, 128, 23);

        jLabel50.setText("Genital & Anus :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(280, 450, 95, 23);

        Genital.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Genital.setName("Genital"); // NOI18N
        Genital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitalKeyPressed(evt);
            }
        });
        FormInput.add(Genital);
        Genital.setBounds(370, 450, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(280, 480, 95, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(370, 480, 128, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(10, 520, 880, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(30, 190, 180, 23);

        jLabel104.setText("Cara Datang :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(20, 80, 100, 23);

        CaraDatang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Datang Sendiri", "Dikirim oleh Dokter", "Puskesmas", "di antar oleh dokter/polisi" }));
        CaraDatang.setName("CaraDatang"); // NOI18N
        CaraDatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CaraDatangActionPerformed(evt);
            }
        });
        CaraDatang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraDatangKeyPressed(evt);
            }
        });
        FormInput.add(CaraDatang);
        CaraDatang.setBounds(120, 80, 200, 23);

        jLabel105.setText("Keterangan :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(320, 80, 90, 23);

        KetCaraDatang.setName("KetCaraDatang"); // NOI18N
        KetCaraDatang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetCaraDatangKeyPressed(evt);
            }
        });
        FormInput.add(KetCaraDatang);
        KetCaraDatang.setBounds(410, 80, 230, 23);

        jLabel106.setText("Tempat Kecelakaan :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(-10, 110, 130, 23);

        TempatKecelakaan.setName("TempatKecelakaan"); // NOI18N
        TempatKecelakaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatKecelakaanKeyPressed(evt);
            }
        });
        FormInput.add(TempatKecelakaan);
        TempatKecelakaan.setBounds(120, 110, 210, 23);

        jLabel107.setText("Tgl. Kecelakaan :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(290, 110, 130, 23);

        TglKecelakaan.setName("TglKecelakaan"); // NOI18N
        TglKecelakaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKecelakaanKeyPressed(evt);
            }
        });
        FormInput.add(TglKecelakaan);
        TglKecelakaan.setBounds(420, 110, 110, 23);

        jLabel108.setText("Jam :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(540, 110, 40, 23);

        JamKecelakaan.setName("JamKecelakaan"); // NOI18N
        JamKecelakaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKecelakaanKeyPressed(evt);
            }
        });
        FormInput.add(JamKecelakaan);
        JamKecelakaan.setBounds(580, 110, 80, 23);

        jLabel109.setText("Jenis Kecelakaan/Kendaraan :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(-40, 140, 200, 23);

        JenisKecelakaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bukan KLL", "Kecelakaan Lalu Lintas Darat", "Kecelakaan Lalu Lintas Air/Laut", "Kekerasan Dalam Rumah Tangga", "Lain-lain" }));
        JenisKecelakaan.setName("JenisKecelakaan"); // NOI18N
        JenisKecelakaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKecelakaanKeyPressed(evt);
            }
        });
        FormInput.add(JenisKecelakaan);
        JenisKecelakaan.setBounds(160, 140, 240, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/semua.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(50, 560, 809, 280);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        KetLokalis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetLokalis.setColumns(20);
        KetLokalis.setRows(5);
        KetLokalis.setName("KetLokalis"); // NOI18N
        KetLokalis.setPreferredSize(new java.awt.Dimension(182, 92));
        KetLokalis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLokalisKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(KetLokalis);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(50, 860, 810, 83);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Keterangan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(50, 840, 100, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(10, 950, 880, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("III. STATUS LOKALIS");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(20, 520, 180, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        EKG.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        EKG.setColumns(20);
        EKG.setRows(5);
        EKG.setName("EKG"); // NOI18N
        EKG.setPreferredSize(new java.awt.Dimension(102, 52));
        EKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(EKG);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(50, 990, 260, 63);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(10, 1060, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(20, 950, 190, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Diagnosis);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(50, 1080, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(10, 1130, 880, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("V. DIAGNOSIS/ASESMEN");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(20, 1060, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(20);
        Tatalaksana.setRows(24);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Tatalaksana);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(50, 1150, 810, 130);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("DISPOSISI :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 1420, 190, 23);

        jLabel110.setText("1. Bagian :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 1320, 80, 23);

        Bagian1.setName("Bagian1"); // NOI18N
        Bagian1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bagian1KeyPressed(evt);
            }
        });
        FormInput.add(Bagian1);
        Bagian1.setBounds(90, 1320, 190, 23);

        jLabel111.setText("Diminta Jam :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(290, 1320, 90, 23);

        DimintaJam1.setName("DimintaJam1"); // NOI18N
        DimintaJam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DimintaJam1KeyPressed(evt);
            }
        });
        FormInput.add(DimintaJam1);
        DimintaJam1.setBounds(380, 1320, 80, 23);

        jLabel112.setText("Datang Jam :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(470, 1320, 90, 23);

        DatangJam1.setName("DatangJam1"); // NOI18N
        DatangJam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DatangJam1KeyPressed(evt);
            }
        });
        FormInput.add(DatangJam1);
        DatangJam1.setBounds(560, 1320, 80, 23);

        jLabel113.setText("2. Bagian :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 1350, 80, 23);

        Bagian2.setName("Bagian2"); // NOI18N
        Bagian2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bagian2KeyPressed(evt);
            }
        });
        FormInput.add(Bagian2);
        Bagian2.setBounds(90, 1350, 190, 23);

        jLabel114.setText("Diminta Jam :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(290, 1350, 90, 23);

        DimintaJam2.setName("DimintaJam2"); // NOI18N
        DimintaJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DimintaJam2KeyPressed(evt);
            }
        });
        FormInput.add(DimintaJam2);
        DimintaJam2.setBounds(380, 1350, 80, 23);

        jLabel115.setText("Datang Jam :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(470, 1350, 90, 23);

        DatangJam2.setName("DatangJam2"); // NOI18N
        DatangJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DatangJam2KeyPressed(evt);
            }
        });
        FormInput.add(DatangJam2);
        DatangJam2.setBounds(560, 1350, 80, 23);

        jLabel116.setText("3. Bagian :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(10, 1380, 80, 23);

        Bagian3.setName("Bagian3"); // NOI18N
        Bagian3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bagian3KeyPressed(evt);
            }
        });
        FormInput.add(Bagian3);
        Bagian3.setBounds(90, 1380, 190, 23);

        jLabel117.setText("Diminta Jam :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(290, 1380, 90, 23);

        DimintaJam3.setName("DimintaJam3"); // NOI18N
        DimintaJam3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DimintaJam3KeyPressed(evt);
            }
        });
        FormInput.add(DimintaJam3);
        DimintaJam3.setBounds(380, 1380, 80, 23);

        jLabel118.setText("Datang Jam :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(470, 1380, 90, 23);

        DatangJam3.setName("DatangJam3"); // NOI18N
        DatangJam3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DatangJam3KeyPressed(evt);
            }
        });
        FormInput.add(DatangJam3);
        DatangJam3.setBounds(560, 1380, 80, 23);

        ChkObservasi.setText("Observasi");
        ChkObservasi.setName("ChkObservasi"); // NOI18N
        ChkObservasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkObservasiActionPerformed(evt);
            }
        });
        ChkObservasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChkObservasiKeyPressed(evt);
            }
        });
        FormInput.add(ChkObservasi);
        ChkObservasi.setBounds(30, 1440, 130, 23);

        ChkDipulangkan.setText("Dipulangkan");
        ChkDipulangkan.setName("ChkDipulangkan"); // NOI18N
        ChkDipulangkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkDipulangkanActionPerformed(evt);
            }
        });
        ChkDipulangkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChkDipulangkanKeyPressed(evt);
            }
        });
        FormInput.add(ChkDipulangkan);
        ChkDipulangkan.setBounds(190, 1440, 140, 23);

        ChkPAPS.setText("PAPS");
        ChkPAPS.setName("ChkPAPS"); // NOI18N
        ChkPAPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkPAPSActionPerformed(evt);
            }
        });
        ChkPAPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChkPAPSKeyPressed(evt);
            }
        });
        FormInput.add(ChkPAPS);
        ChkPAPS.setBounds(350, 1440, 80, 23);

        ChkDirujukKe.setText("Dirujuk ke");
        ChkDirujukKe.setName("ChkDirujukKe"); // NOI18N
        ChkDirujukKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkDirujukKeActionPerformed(evt);
            }
        });
        ChkDirujukKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChkDirujukKeKeyPressed(evt);
            }
        });
        FormInput.add(ChkDirujukKe);
        ChkDirujukKe.setBounds(30, 1470, 100, 23);

        DirujukKe.setEnabled(false);
        DirujukKe.setName("DirujukKe"); // NOI18N
        DirujukKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DirujukKeKeyPressed(evt);
            }
        });
        FormInput.add(DirujukKe);
        DirujukKe.setBounds(140, 1470, 240, 23);

        ChkRawatInap.setText("Rawat inap");
        ChkRawatInap.setName("ChkRawatInap"); // NOI18N
        ChkRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRawatInapActionPerformed(evt);
            }
        });
        ChkRawatInap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChkRawatInapKeyPressed(evt);
            }
        });
        FormInput.add(ChkRawatInap);
        ChkRawatInap.setBounds(400, 1470, 120, 23);

        RawatInapKe.setEnabled(false);
        RawatInapKe.setName("RawatInapKe"); // NOI18N
        RawatInapKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RawatInapKeKeyPressed(evt);
            }
        });
        FormInput.add(RawatInapKe);
        RawatInapKe.setBounds(530, 1470, 240, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2025 13:30:18" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(436, 40, 130, 23);

        jLabel42.setText("Mata :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(10, 420, 127, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(140, 420, 128, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("EKG :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(50, 970, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(330, 970, 150, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        hasil.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        hasil.setColumns(20);
        hasil.setRows(5);
        hasil.setName("hasil"); // NOI18N
        hasil.setPreferredSize(new java.awt.Dimension(102, 52));
        hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasilKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(hasil);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(330, 990, 260, 63);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Laborat :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(600, 970, 150, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        nilai.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        nilai.setColumns(20);
        nilai.setRows(5);
        nilai.setName("nilai"); // NOI18N
        nilai.setPreferredSize(new java.awt.Dimension(102, 52));
        nilai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nilaiKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(nilai);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(600, 990, 260, 63);

        BtnEdit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris2.png"))); // NOI18N
        BtnEdit2.setMnemonic('G');
        BtnEdit2.setText("MARKING LOKALIS");
        BtnEdit2.setToolTipText("Alt+G");
        BtnEdit2.setName("BtnEdit2"); // NOI18N
        BtnEdit2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit2ActionPerformed(evt);
            }
        });
        BtnEdit2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit2KeyPressed(evt);
            }
        });
        FormInput.add(BtnEdit2);
        BtnEdit2.setBounds(280, 530, 160, 30);

        labelKeadaanPulang.setText("Keadaan Saat Pulang :");
        labelKeadaanPulang.setName("labelKeadaanPulang"); // NOI18N
        FormInput.add(labelKeadaanPulang);
        labelKeadaanPulang.setBounds(-20, 1500, 160, 23);

        ChkSembuh.setText("Sembuh");
        ChkSembuh.setName("ChkSembuh"); // NOI18N
        FormInput.add(ChkSembuh);
        ChkSembuh.setBounds(20, 1520, 100, 23);

        labelSembuhHasil.setText("Dengan Hasil Pemeriksaan, Tekanan Darah :");
        labelSembuhHasil.setName("labelSembuhHasil"); // NOI18N
        FormInput.add(labelSembuhHasil);
        labelSembuhHasil.setBounds(50, 1540, 280, 23);

        SembuhTD.setName("SembuhTD"); // NOI18N
        FormInput.add(SembuhTD);
        SembuhTD.setBounds(330, 1540, 70, 23);

        labelSembuhNadi.setText("mmHg,  Nadi :");
        labelSembuhNadi.setName("labelSembuhNadi"); // NOI18N
        FormInput.add(labelSembuhNadi);
        labelSembuhNadi.setBounds(410, 1540, 110, 23);

        SembuhNadi.setName("SembuhNadi"); // NOI18N
        FormInput.add(SembuhNadi);
        SembuhNadi.setBounds(520, 1540, 60, 23);

        labelSembuhSuhu.setText("kali/menit,  Suhu :");
        labelSembuhSuhu.setName("labelSembuhSuhu"); // NOI18N
        FormInput.add(labelSembuhSuhu);
        labelSembuhSuhu.setBounds(580, 1540, 140, 23);

        SembuhSuhu.setName("SembuhSuhu"); // NOI18N
        FormInput.add(SembuhSuhu);
        SembuhSuhu.setBounds(730, 1540, 50, 23);

        labelSembuhSuhuSatuan.setText("°C");
        labelSembuhSuhuSatuan.setName("labelSembuhSuhuSatuan"); // NOI18N
        FormInput.add(labelSembuhSuhuSatuan);
        labelSembuhSuhuSatuan.setBounds(780, 1540, 30, 23);

        labelSembuhRR.setText("Respirasi :");
        labelSembuhRR.setName("labelSembuhRR"); // NOI18N
        FormInput.add(labelSembuhRR);
        labelSembuhRR.setBounds(50, 1570, 80, 23);

        SembuhRR.setName("SembuhRR"); // NOI18N
        FormInput.add(SembuhRR);
        SembuhRR.setBounds(130, 1570, 60, 23);

        labelSembuhKesadaran.setText("kali/menit,  Kesadaran :");
        labelSembuhKesadaran.setName("labelSembuhKesadaran"); // NOI18N
        FormInput.add(labelSembuhKesadaran);
        labelSembuhKesadaran.setBounds(200, 1570, 190, 23);

        SembuhKesadaran.setName("SembuhKesadaran"); // NOI18N
        FormInput.add(SembuhKesadaran);
        SembuhKesadaran.setBounds(390, 1570, 340, 23);

        ChkMembaik.setText("Membaik");
        ChkMembaik.setName("ChkMembaik"); // NOI18N
        FormInput.add(ChkMembaik);
        ChkMembaik.setBounds(20, 1600, 100, 23);

        labelMembaikHasil.setText("Dengan Hasil Pemeriksaan, Tekanan Darah :");
        labelMembaikHasil.setName("labelMembaikHasil"); // NOI18N
        FormInput.add(labelMembaikHasil);
        labelMembaikHasil.setBounds(50, 1620, 280, 23);

        MembaikTD.setName("MembaikTD"); // NOI18N
        FormInput.add(MembaikTD);
        MembaikTD.setBounds(330, 1620, 70, 23);

        labelMembaikNadi.setText("mmHg,  Nadi :");
        labelMembaikNadi.setName("labelMembaikNadi"); // NOI18N
        FormInput.add(labelMembaikNadi);
        labelMembaikNadi.setBounds(410, 1620, 110, 23);

        MembaikNadi.setName("MembaikNadi"); // NOI18N
        FormInput.add(MembaikNadi);
        MembaikNadi.setBounds(520, 1620, 60, 23);

        labelMembaikSuhu.setText("kali/menit,  Suhu :");
        labelMembaikSuhu.setName("labelMembaikSuhu"); // NOI18N
        FormInput.add(labelMembaikSuhu);
        labelMembaikSuhu.setBounds(580, 1620, 140, 23);

        MembaikSuhu.setName("MembaikSuhu"); // NOI18N
        FormInput.add(MembaikSuhu);
        MembaikSuhu.setBounds(730, 1620, 50, 23);

        labelMembaikSuhuSatuan.setText("°C");
        labelMembaikSuhuSatuan.setName("labelMembaikSuhuSatuan"); // NOI18N
        FormInput.add(labelMembaikSuhuSatuan);
        labelMembaikSuhuSatuan.setBounds(780, 1620, 30, 23);

        labelMembaikRR.setText("Respirasi :");
        labelMembaikRR.setName("labelMembaikRR"); // NOI18N
        FormInput.add(labelMembaikRR);
        labelMembaikRR.setBounds(50, 1650, 80, 23);

        MembaikRR.setName("MembaikRR"); // NOI18N
        FormInput.add(MembaikRR);
        MembaikRR.setBounds(130, 1650, 60, 23);

        labelMembaikKesadaran.setText("kali/menit,  Kesadaran :");
        labelMembaikKesadaran.setName("labelMembaikKesadaran"); // NOI18N
        FormInput.add(labelMembaikKesadaran);
        labelMembaikKesadaran.setBounds(200, 1650, 190, 23);

        MembaikKesadaran.setName("MembaikKesadaran"); // NOI18N
        FormInput.add(MembaikKesadaran);
        MembaikKesadaran.setBounds(390, 1650, 340, 23);

        ChkPerawatanLanjut.setText("Perlu Perawatan Lebih Lanjut");
        ChkPerawatanLanjut.setName("ChkPerawatanLanjut"); // NOI18N
        FormInput.add(ChkPerawatanLanjut);
        ChkPerawatanLanjut.setBounds(20, 1680, 220, 23);

        labelLanjutHasil.setText("Dengan Hasil Pemeriksaan, Tekanan Darah :");
        labelLanjutHasil.setName("labelLanjutHasil"); // NOI18N
        FormInput.add(labelLanjutHasil);
        labelLanjutHasil.setBounds(50, 1700, 280, 23);

        LanjutTD.setName("LanjutTD"); // NOI18N
        FormInput.add(LanjutTD);
        LanjutTD.setBounds(330, 1700, 70, 23);

        labelLanjutNadi.setText("mmHg,  Nadi :");
        labelLanjutNadi.setName("labelLanjutNadi"); // NOI18N
        FormInput.add(labelLanjutNadi);
        labelLanjutNadi.setBounds(410, 1700, 110, 23);

        LanjutNadi.setName("LanjutNadi"); // NOI18N
        FormInput.add(LanjutNadi);
        LanjutNadi.setBounds(520, 1700, 60, 23);

        labelLanjutSuhu.setText("kali/menit,  Suhu :");
        labelLanjutSuhu.setName("labelLanjutSuhu"); // NOI18N
        FormInput.add(labelLanjutSuhu);
        labelLanjutSuhu.setBounds(580, 1700, 140, 23);

        LanjutSuhu.setName("LanjutSuhu"); // NOI18N
        FormInput.add(LanjutSuhu);
        LanjutSuhu.setBounds(730, 1700, 50, 23);

        labelLanjutSuhuSatuan.setText("°C");
        labelLanjutSuhuSatuan.setName("labelLanjutSuhuSatuan"); // NOI18N
        FormInput.add(labelLanjutSuhuSatuan);
        labelLanjutSuhuSatuan.setBounds(780, 1700, 30, 23);

        labelLanjutRR.setText("Respirasi :");
        labelLanjutRR.setName("labelLanjutRR"); // NOI18N
        FormInput.add(labelLanjutRR);
        labelLanjutRR.setBounds(50, 1730, 80, 23);

        LanjutRR.setName("LanjutRR"); // NOI18N
        FormInput.add(LanjutRR);
        LanjutRR.setBounds(130, 1730, 60, 23);

        labelLanjutKesadaran.setText("kali/menit,  Kesadaran :");
        labelLanjutKesadaran.setName("labelLanjutKesadaran"); // NOI18N
        FormInput.add(labelLanjutKesadaran);
        labelLanjutKesadaran.setBounds(200, 1730, 190, 23);

        LanjutKesadaran.setName("LanjutKesadaran"); // NOI18N
        FormInput.add(LanjutKesadaran);
        LanjutKesadaran.setBounds(390, 1730, 340, 23);

        ChkMeninggal.setText("Meninggal");
        ChkMeninggal.setName("ChkMeninggal"); // NOI18N
        FormInput.add(ChkMeninggal);
        ChkMeninggal.setBounds(20, 1760, 120, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("VI. PLANNING");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(20, 1130, 190, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("KONSULTASI :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(10, 1290, 190, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2025" }));
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

        TabRawat.addTab("Data Pengkajian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
      if(!validasiDokter()){
          return;
      }
      if (Sequel.menyimpantf(
        "penilaian_medis_igd",
        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
        "No.Rawat",
        77,
        new String[]{
            TNoRw.getText(),
            Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
            KdDokter.getText(), Anamnesis.getSelectedItem().toString(), Hubungan.getText(),
            KeluhanUtama.getText(), RPS.getText(), RPD.getText(), RPK.getText(), RPO.getText(),
            Alergi.getText(), Keadaan.getSelectedItem().toString(), GCS.getText(), Kesadaran.getSelectedItem().toString(),
            TD.getText(), Nadi.getText(), RR.getText(), Suhu.getText(), SPO.getText(), BB.getText(), TB.getText(),
            Kepala.getSelectedItem().toString(), Mata.getSelectedItem().toString(), Gigi.getSelectedItem().toString(),
            Leher.getSelectedItem().toString(), Thoraks.getSelectedItem().toString(), Abdomen.getSelectedItem().toString(),
            Genital.getSelectedItem().toString(), Ekstremitas.getSelectedItem().toString(),
            KetFisik.getText(), KetLokalis.getText(), EKG.getText(), hasil.getText(), nilai.getText(),
            Diagnosis.getText(), Tatalaksana.getText(), CaraDatang.getSelectedItem().toString(), KetCaraDatang.getText(),
            TempatKecelakaan.getText(), TglKecelakaan.getText(), JamKecelakaan.getText(), JenisKecelakaan.getSelectedItem().toString(),
            Bagian1.getText(), DimintaJam1.getText(), DatangJam1.getText(), Bagian2.getText(), DimintaJam2.getText(), DatangJam2.getText(),
            Bagian3.getText(), DimintaJam3.getText(), DatangJam3.getText(), ChkObservasi.isSelected()?"Ya":"Tidak", ChkDipulangkan.isSelected()?"Ya":"Tidak",
            ChkPAPS.isSelected()?"Ya":"Tidak", ChkDirujukKe.isSelected()?"Ya":"Tidak", DirujukKe.getText(), ChkRawatInap.isSelected()?"Ya":"Tidak", RawatInapKe.getText(),
            "Tidak", "", "", "", "", "",
            "Tidak", "", "", "", "", "",
            "Tidak", "", "", "", "", "",
            "Tidak"
        }
)) {
    emptTeks();
    JOptionPane.showMessageDialog(null, "Data berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
}

}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KetFisik,BtnBatal);
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
            }else{
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
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
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                ganti();
            }else{
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    ganti();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'><b>Cara Datang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='180px'><b>Ket. Cara Datang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Riwayat Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>GCS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='67px'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>Suhu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>SpO2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>TB(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Gigi & Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Thoraks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Genital & Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Laborat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Tatalaksana</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Tempat Kecelakaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Tanggal Kecelakaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Jam Kecelakaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Jenis Kecelakaan/Kendaraan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Bagian 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Diminta Jam 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Datang Jam 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Bagian 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Diminta Jam 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Datang Jam 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Bagian 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Diminta Jam 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Datang Jam 3</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,48).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,49).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,50).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,51).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,52).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,53).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,54).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,55).toString()+"</td>"+ 
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='6500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianAwalMedisRanap.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='5100px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS IGD<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

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
                getData();
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

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TB,TD);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,GCS,BB);
    }//GEN-LAST:event_TBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SPO);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,BB,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,Keadaan);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,KetCaraDatang,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,RPS,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void KetFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetFisikKeyPressed
        Valid.pindah2(evt,Ekstremitas,KetLokalis);
    }//GEN-LAST:event_KetFisikKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Kesadaran,TB);
    }//GEN-LAST:event_GCSKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Alergi,Kesadaran);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Keadaan,GCS);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOKeyPressed
        Valid.pindah(evt,Suhu,Kepala);
    }//GEN-LAST:event_SPOKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,SPO,Mata);
    }//GEN-LAST:event_KepalaKeyPressed

    private void GigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKeyPressed
        Valid.pindah(evt,Mata,Leher);
    }//GEN-LAST:event_GigiKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        Valid.pindah(evt,Gigi,Thoraks);
    }//GEN-LAST:event_LeherKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt,Leher,Abdomen);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt,Thoraks,Genital);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void GenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitalKeyPressed
        Valid.pindah(evt,Abdomen,Ekstremitas);
    }//GEN-LAST:event_GenitalKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt,Genital,KetFisik);
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KetLokalisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLokalisKeyPressed
        Valid.pindah2(evt,KetFisik,EKG);
    }//GEN-LAST:event_KetLokalisKeyPressed

    private void EKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGKeyPressed
        Valid.pindah2(evt,KetLokalis,hasil);
    }//GEN-LAST:event_EKGKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,nilai,Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        Valid.pindah2(evt,Diagnosis,Bagian1);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Tatalaksana,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,CaraDatang);
    }//GEN-LAST:event_HubunganKeyPressed

    private void CaraDatangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraDatangKeyPressed
        Valid.pindah(evt,Hubungan,KetCaraDatang);
    }//GEN-LAST:event_CaraDatangKeyPressed

    private void KetCaraDatangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetCaraDatangKeyPressed
        Valid.pindah(evt,CaraDatang,TempatKecelakaan);
    }//GEN-LAST:event_KetCaraDatangKeyPressed

    private void TempatKecelakaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatKecelakaanKeyPressed
        Valid.pindah(evt,KetCaraDatang,TglKecelakaan);
    }//GEN-LAST:event_TempatKecelakaanKeyPressed

    private void TglKecelakaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKecelakaanKeyPressed
        Valid.pindah(evt,TempatKecelakaan,JamKecelakaan);
    }//GEN-LAST:event_TglKecelakaanKeyPressed

    private void JamKecelakaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKecelakaanKeyPressed
        Valid.pindah(evt,TglKecelakaan,JenisKecelakaan);
    }//GEN-LAST:event_JamKecelakaanKeyPressed

    private void JenisKecelakaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKecelakaanKeyPressed
        Valid.pindah(evt,JamKecelakaan,KeluhanUtama);
    }//GEN-LAST:event_JenisKecelakaanKeyPressed

    private void Bagian1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bagian1KeyPressed
        Valid.pindah(evt,Tatalaksana,DimintaJam1);
    }//GEN-LAST:event_Bagian1KeyPressed

    private void DimintaJam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DimintaJam1KeyPressed
        Valid.pindah(evt,Bagian1,DatangJam1);
    }//GEN-LAST:event_DimintaJam1KeyPressed

    private void DatangJam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DatangJam1KeyPressed
        Valid.pindah(evt,DimintaJam1,Bagian2);
    }//GEN-LAST:event_DatangJam1KeyPressed

    private void Bagian2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bagian2KeyPressed
        Valid.pindah(evt,DatangJam1,DimintaJam2);
    }//GEN-LAST:event_Bagian2KeyPressed

    private void DimintaJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DimintaJam2KeyPressed
        Valid.pindah(evt,Bagian2,DatangJam2);
    }//GEN-LAST:event_DimintaJam2KeyPressed

    private void DatangJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DatangJam2KeyPressed
        Valid.pindah(evt,DimintaJam2,Bagian3);
    }//GEN-LAST:event_DatangJam2KeyPressed

    private void Bagian3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bagian3KeyPressed
        Valid.pindah(evt,DatangJam2,DimintaJam3);
    }//GEN-LAST:event_Bagian3KeyPressed

    private void DimintaJam3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DimintaJam3KeyPressed
        Valid.pindah(evt,Bagian3,DatangJam3);
    }//GEN-LAST:event_DimintaJam3KeyPressed

    private void DatangJam3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DatangJam3KeyPressed
        Valid.pindah(evt,DimintaJam3,BtnSimpan);
    }//GEN-LAST:event_DatangJam3KeyPressed

    private void ChkObservasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkObservasiActionPerformed
        // no-op
    }//GEN-LAST:event_ChkObservasiActionPerformed

    private void ChkObservasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChkObservasiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            ChkDipulangkan.requestFocus();
        }
    }//GEN-LAST:event_ChkObservasiKeyPressed

    private void ChkDipulangkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkDipulangkanActionPerformed
        // no-op
    }//GEN-LAST:event_ChkDipulangkanActionPerformed

    private void ChkDipulangkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChkDipulangkanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            ChkPAPS.requestFocus();
        }
    }//GEN-LAST:event_ChkDipulangkanKeyPressed

    private void ChkPAPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkPAPSActionPerformed
        // no-op
    }//GEN-LAST:event_ChkPAPSActionPerformed

    private void ChkPAPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChkPAPSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            ChkDirujukKe.requestFocus();
        }
    }//GEN-LAST:event_ChkPAPSKeyPressed

    private void ChkDirujukKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkDirujukKeActionPerformed
        updateDirujukKeState(true);
    }//GEN-LAST:event_ChkDirujukKeActionPerformed

    private void ChkDirujukKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChkDirujukKeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            DirujukKe.requestFocus();
        }
    }//GEN-LAST:event_ChkDirujukKeKeyPressed

    private void DirujukKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DirujukKeKeyPressed
        Valid.pindah(evt,DirujukKe,ChkRawatInap);
    }//GEN-LAST:event_DirujukKeKeyPressed

    private void ChkRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRawatInapActionPerformed
        updateRawatInapState(true);
    }//GEN-LAST:event_ChkRawatInapActionPerformed

    private void ChkRawatInapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChkRawatInapKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            RawatInapKe.requestFocus();
        }
    }//GEN-LAST:event_ChkRawatInapKeyPressed

    private void RawatInapKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RawatInapKeKeyPressed
        Valid.pindah(evt,RawatInapKe,Bagian1);
    }//GEN-LAST:event_RawatInapKeKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            String kolomRadiologiReport = ekspresiKolomReportPenilaianIGD("hasil","rad");
            String kolomLaboratReport = ekspresiKolomReportPenilaianIGD("nilai","lab");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
//            param.put("lokalis",Sequel.cariGambar("select lokalis from gambar")); 
            param.put("url",Sequel.cariIsi("select url_image from asesmen_medis_igd_image_marking where no_rawat=?",TNoRw.getText()));                   
            param.put("lokalis","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/");
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisIGD.jasper","report","::[ Laporan Penilaian Awal Medis IGD ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"+
                "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.cara_datang,penilaian_medis_igd.ket_cara_datang,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"+
                "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"+
                "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.gigi,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"+
                "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,"+kolomRadiologiReport+" as rad,"+kolomLaboratReport+" as lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,penilaian_medis_igd.tempat_kecelakaan,penilaian_medis_igd.tgl_kecelakaan,penilaian_medis_igd.jam_kecelakaan,penilaian_medis_igd.jenis_kecelakaan,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "+
                "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where penilaian_medis_igd.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt,Kepala,Gigi);
    }//GEN-LAST:event_MataKeyPressed

    private void hasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilKeyPressed
        Valid.pindah2(evt,EKG,nilai);
    }//GEN-LAST:event_hasilKeyPressed

    private void nilaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nilaiKeyPressed
        Valid.pindah2(evt,hasil,Diagnosis);
    }//GEN-LAST:event_nilaiKeyPressed

    private void BtnEdit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit2ActionPerformed
        DlgMarkingImageAssMedisIGD form=new DlgMarkingImageAssMedisIGD(null,false);
        form.setNoRw(TNoRw.getText());
        form.setVisible(true);
        form.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                refreshMarkingImage();
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
    }//GEN-LAST:event_BtnEdit2ActionPerformed

    private void BtnEdit2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit2KeyPressed

    private void CaraDatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CaraDatangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraDatangActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisIGD dialog = new RMPenilaianAwalMedisIGD(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Abdomen;
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BB;
    private widget.TextBox Bagian1;
    private widget.TextBox Bagian2;
    private widget.TextBox Bagian3;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit2;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CaraDatang;
    private widget.CekBox ChkDipulangkan;
    private widget.CekBox ChkDirujukKe;
    private widget.CekBox ChkMembaik;
    private widget.CekBox ChkMeninggal;
    private widget.CekBox ChkObservasi;
    private widget.CekBox ChkPAPS;
    private widget.CekBox ChkPerawatanLanjut;
    private widget.CekBox ChkRawatInap;
    private widget.CekBox ChkSembuh;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DatangJam1;
    private widget.TextBox DatangJam2;
    private widget.TextBox DatangJam3;
    private widget.TextArea Diagnosis;
    private widget.TextBox DimintaJam1;
    private widget.TextBox DimintaJam2;
    private widget.TextBox DimintaJam3;
    private widget.TextBox DirujukKe;
    private widget.TextArea EKG;
    private widget.ComboBox Ekstremitas;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GCS;
    private widget.ComboBox Genital;
    private widget.ComboBox Gigi;
    private widget.TextBox Hubungan;
    private widget.TextBox JamKecelakaan;
    private widget.ComboBox JenisKecelakaan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Keadaan;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KetCaraDatang;
    private widget.TextArea KetFisik;
    private widget.TextArea KetLokalis;
    private widget.Label LCount;
    private widget.TextBox LanjutKesadaran;
    private widget.TextBox LanjutNadi;
    private widget.TextBox LanjutRR;
    private widget.TextBox LanjutSuhu;
    private widget.TextBox LanjutTD;
    private widget.ComboBox Leher;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Mata;
    private widget.TextBox MembaikKesadaran;
    private widget.TextBox MembaikNadi;
    private widget.TextBox MembaikRR;
    private widget.TextBox MembaikSuhu;
    private widget.TextBox MembaikTD;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextBox RawatInapKe;
    private widget.TextBox SPO;
    private widget.ScrollPane Scroll;
    private widget.TextBox SembuhKesadaran;
    private widget.TextBox SembuhNadi;
    private widget.TextBox SembuhRR;
    private widget.TextBox SembuhSuhu;
    private widget.TextBox SembuhTD;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea Tatalaksana;
    private widget.TextBox TempatKecelakaan;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglKecelakaan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.TextArea hasil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
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
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel35;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label labelKeadaanPulang;
    private widget.Label labelLanjutHasil;
    private widget.Label labelLanjutKesadaran;
    private widget.Label labelLanjutNadi;
    private widget.Label labelLanjutRR;
    private widget.Label labelLanjutSuhu;
    private widget.Label labelLanjutSuhuSatuan;
    private widget.Label labelMembaikHasil;
    private widget.Label labelMembaikKesadaran;
    private widget.Label labelMembaikNadi;
    private widget.Label labelMembaikRR;
    private widget.Label labelMembaikSuhu;
    private widget.Label labelMembaikSuhuSatuan;
    private widget.Label labelSembuhHasil;
    private widget.Label labelSembuhKesadaran;
    private widget.Label labelSembuhNadi;
    private widget.Label labelSembuhRR;
    private widget.Label labelSembuhSuhu;
    private widget.Label labelSembuhSuhuSatuan;
    private widget.TextArea nilai;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane2;
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
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"+
                        "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.cara_datang,penilaian_medis_igd.ket_cara_datang,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"+
                        "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"+
                        "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.gigi,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"+
                        "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.hasil,penilaian_medis_igd.nilai,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,penilaian_medis_igd.tempat_kecelakaan,penilaian_medis_igd.tgl_kecelakaan,penilaian_medis_igd.jam_kecelakaan,penilaian_medis_igd.jenis_kecelakaan,"+
                        "penilaian_medis_igd.bagian1,penilaian_medis_igd.diminta_jam1,penilaian_medis_igd.datang_jam1,penilaian_medis_igd.bagian2,penilaian_medis_igd.diminta_jam2,penilaian_medis_igd.datang_jam2,penilaian_medis_igd.bagian3,penilaian_medis_igd.diminta_jam3,penilaian_medis_igd.datang_jam3,"+
                        "penilaian_medis_igd.observasi,penilaian_medis_igd.dipulangkan,penilaian_medis_igd.paps_dirujuk,penilaian_medis_igd.dirujuk,penilaian_medis_igd.dirujuk_ke,penilaian_medis_igd.rawat_inap,penilaian_medis_igd.rawat_inap_ke,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "+
                        "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_igd.tanggal between ? and ? order by penilaian_medis_igd.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"+
                        "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.cara_datang,penilaian_medis_igd.ket_cara_datang,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"+
                        "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"+
                        "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.gigi,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"+
                        "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.hasil,penilaian_medis_igd.nilai,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,penilaian_medis_igd.tempat_kecelakaan,penilaian_medis_igd.tgl_kecelakaan,penilaian_medis_igd.jam_kecelakaan,penilaian_medis_igd.jenis_kecelakaan,"+
                        "penilaian_medis_igd.bagian1,penilaian_medis_igd.diminta_jam1,penilaian_medis_igd.datang_jam1,penilaian_medis_igd.bagian2,penilaian_medis_igd.diminta_jam2,penilaian_medis_igd.datang_jam2,penilaian_medis_igd.bagian3,penilaian_medis_igd.diminta_jam3,penilaian_medis_igd.datang_jam3,"+
                        "penilaian_medis_igd.observasi,penilaian_medis_igd.dipulangkan,penilaian_medis_igd.paps_dirujuk,penilaian_medis_igd.dirujuk,penilaian_medis_igd.dirujuk_ke,penilaian_medis_igd.rawat_inap,penilaian_medis_igd.rawat_inap_ke,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "+
                        "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_igd.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_igd.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_igd.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("cara_datang"),rs.getString("ket_cara_datang"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),rs.getString("alergi"),
                        rs.getString("keadaan"),rs.getString("gcs"),rs.getString("kesadaran"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("spo"),rs.getString("bb"),
                        rs.getString("tb"),rs.getString("kepala"),rs.getString("mata"),rs.getString("gigi"),rs.getString("leher"),rs.getString("thoraks"),rs.getString("abdomen"),rs.getString("genital"),
                        rs.getString("ekstremitas"),rs.getString("ket_fisik"),rs.getString("ket_lokalis"),rs.getString("ekg"),rs.getString("hasil"),rs.getString("nilai"),rs.getString("diagnosis"),rs.getString("tata"),
                        rs.getString("tempat_kecelakaan"),rs.getString("tgl_kecelakaan"),rs.getString("jam_kecelakaan"),rs.getString("jenis_kecelakaan"),
                        rs.getString("bagian1"),rs.getString("diminta_jam1"),rs.getString("datang_jam1"),rs.getString("bagian2"),rs.getString("diminta_jam2"),rs.getString("datang_jam2"),rs.getString("bagian3"),rs.getString("diminta_jam3"),rs.getString("datang_jam3"),
                        rs.getString("observasi"),rs.getString("dipulangkan"),rs.getString("paps_dirujuk"),rs.getString("dirujuk"),rs.getString("dirujuk_ke"),rs.getString("rawat_inap"),rs.getString("rawat_inap_ke")
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

    private void updateDirujukKeState(boolean clearWhenDisabled) {
        DirujukKe.setEnabled(ChkDirujukKe.isSelected());
        if(clearWhenDisabled && !ChkDirujukKe.isSelected()) {
            DirujukKe.setText("");
        }
    }

    private void updateRawatInapState(boolean clearWhenDisabled) {
        RawatInapKe.setEnabled(ChkRawatInap.isSelected());
        if(clearWhenDisabled && !ChkRawatInap.isSelected()) {
            RawatInapKe.setText("");
        }
    }

    public void emptTeks() {
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        CaraDatang.setSelectedIndex(0);
        KetCaraDatang.setText("");
        TempatKecelakaan.setText("");
        TglKecelakaan.setText("");
        JamKecelakaan.setText("");
        JenisKecelakaan.setSelectedIndex(0);
        Bagian1.setText("");
        DimintaJam1.setText("");
        DatangJam1.setText("");
        Bagian2.setText("");
        DimintaJam2.setText("");
        DatangJam2.setText("");
        Bagian3.setText("");
        DimintaJam3.setText("");
        DatangJam3.setText("");
        ChkObservasi.setSelected(false);
        ChkDipulangkan.setSelected(false);
        ChkPAPS.setSelected(false);
        ChkDirujukKe.setSelected(false);
        DirujukKe.setText("");
        ChkRawatInap.setSelected(false);
        RawatInapKe.setText("");
        ChkSembuh.setSelected(false);
        SembuhTD.setText("");
        SembuhNadi.setText("");
        SembuhSuhu.setText("");
        SembuhRR.setText("");
        SembuhKesadaran.setText("");
        ChkMembaik.setSelected(false);
        MembaikTD.setText("");
        MembaikNadi.setText("");
        MembaikSuhu.setText("");
        MembaikRR.setText("");
        MembaikKesadaran.setText("");
        ChkPerawatanLanjut.setSelected(false);
        LanjutTD.setText("");
        LanjutNadi.setText("");
        LanjutSuhu.setText("");
        LanjutRR.setText("");
        LanjutKesadaran.setText("");
        ChkMeninggal.setSelected(false);
        KeluhanUtama.setText("");
        RPS.setText("");
        RPK.setText("");
        RPD.setText("");
        RPO.setText("");
        Alergi.setText("");
        Keadaan.setSelectedIndex(0);
        GCS.setText("");
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        BB.setText("");
        TB.setText("");
        Kepala.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Gigi.setSelectedIndex(0);
        Leher.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Genital.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        KetFisik.setText("");
        KetLokalis.setText("");
        EKG.setText("");
        hasil.setText("");
        nilai.setText("");
        Diagnosis.setText("");
        Tatalaksana.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();       
        updateDirujukKeState(true);
        updateRawatInapState(true);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            CaraDatang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KetCaraDatang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            SPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Gigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Leher.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Genital.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            KetFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KetLokalis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            EKG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            hasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            nilai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Tatalaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            TempatKecelakaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            TglKecelakaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            JamKecelakaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            JenisKecelakaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Bagian1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            DimintaJam1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            DatangJam1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Bagian2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            DimintaJam2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            DatangJam2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Bagian3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            DimintaJam3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            DatangJam3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            ChkObservasi.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString().equals("Ya"));
            ChkDipulangkan.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString().equals("Ya"));
            ChkPAPS.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString().equals("Ya"));
            ChkDirujukKe.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString().equals("Ya"));
            DirujukKe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            ChkRawatInap.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString().equals("Ya"));
            RawatInapKe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            updateDirujukKeState(false);
            updateRawatInapState(false);
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            refreshMarkingImage();
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y')as tgl_lahir,reg_periksa.tgl_registrasi "+
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
    
    
   public void setNoRm(String norwt, Date tgl2) {
    TNoRw.setText(norwt);
    TCari.setText(norwt);
    DTPCari2.setDate(tgl2);
    isRawat();
    refreshMarkingImage();

    // Mengambil data dari SOAP/pemeriksaan ralan terakhir
    PreparedStatement pssoap = null;
    ResultSet rssoap = null;
    try {
        // Kueri ini mengambil semua data yang dibutuhkan dalam satu kali panggilan
        // dan mengurutkannya berdasarkan tanggal dan jam terbaru, lalu hanya mengambil 1 data teratas.
        String sql = "SELECT suhu_tubuh, tensi, nadi, respirasi, tinggi, berat, spo2, gcs, " +
                     "keluhan, pemeriksaan, penilaian, rtl " +
                     "FROM pemeriksaan_ralan WHERE no_rawat=? " +
                     "ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1";
        pssoap = koneksi.prepareStatement(sql);
        pssoap.setString(1, TNoRw.getText());
        rssoap = pssoap.executeQuery();

        if (rssoap.next()) {
            // Mengisi field dari data SOAP terakhir
            Suhu.setText(rssoap.getString("suhu_tubuh"));
            TD.setText(rssoap.getString("tensi"));
            Nadi.setText(rssoap.getString("nadi"));
            RR.setText(rssoap.getString("respirasi"));
            TB.setText(rssoap.getString("tinggi"));
            BB.setText(rssoap.getString("berat"));
            SPO.setText(rssoap.getString("spo2")); // Mengisi field SpO2
            GCS.setText(rssoap.getString("gcs"));

            // Mengisi field lain yang relevan
            KeluhanUtama.setText(rssoap.getString("keluhan"));
            KetFisik.setText(rssoap.getString("pemeriksaan"));
            Diagnosis.setText(rssoap.getString("penilaian"));
            Tatalaksana.setText(rssoap.getString("rtl"));
        }

       // Kode untuk mengambil data lab & radiologi
try {
    // Kueri untuk mengambil semua hasil lab terakhir untuk no_rawat yang sama
    String sqlLab = "SELECT T.Pemeriksaan, D.nilai, T.satuan " +
                    "FROM detail_periksa_lab D " +
                    "INNER JOIN template_laboratorium T ON D.id_template = T.id_template " +
                    "WHERE D.no_rawat = ? " +
                    "ORDER BY D.tgl_periksa DESC, D.jam DESC, T.urut ASC";
                    
    PreparedStatement psLab = koneksi.prepareStatement(sqlLab);
    psLab.setString(1, TNoRw.getText());
    ResultSet rsLab = psLab.executeQuery();
    
    StringBuilder hasilLab = new StringBuilder();
    while (rsLab.next()) {
        // Gabungkan nama pemeriksaan, hasil, dan satuan
        hasilLab.append(rsLab.getString("Pemeriksaan"))
                .append(": ")
                .append(rsLab.getString("nilai"))
                .append(" ")
                .append(rsLab.getString("satuan"))
                .append("\n"); // Tambahkan baris baru untuk setiap hasil
    }
    
    // Set hasil gabungan ke dalam field 'nilai'
    nilai.setText(hasilLab.toString());
    
    // Baris untuk hasil radiologi tetap sama
    hasil.setText(Sequel.cariIsi("select hasil from hasil_radiologi where no_rawat=?", TNoRw.getText()));
    
} catch (Exception e) {
    System.out.println("Notifikasi Pengambilan Hasil Lab: " + e);
}

    } catch (Exception e) {
        System.out.println("Notifikasi Pengambilan SOAP Terakhir: " + e);
    } finally {
        if (rssoap != null) {
            try {
                rssoap.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pssoap != null) {
            try {
                pssoap.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

   
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_igd());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_igd());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_igd());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_igd());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", NmDokter,KdDokter.getText());
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_igd where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(!validasiDokter()){
            return;
        }
        if(Sequel.mengedittf("penilaian_medis_igd","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,rpk=?,rpo=?,alergi=?,keadaan=?,gcs=?,kesadaran=?,td=?,nadi=?,rr=?,suhu=?,spo=?,bb=?,tb=?,kepala=?,mata=?,gigi=?,leher=?,thoraks=?,abdomen=?,genital=?,ekstremitas=?,ket_fisik=?,ket_lokalis=?,ekg=?,hasil=?,nilai=?,diagnosis=?,tata=?,cara_datang=?,ket_cara_datang=?,tempat_kecelakaan=?,tgl_kecelakaan=?,jam_kecelakaan=?,jenis_kecelakaan=?,bagian1=?,diminta_jam1=?,datang_jam1=?,bagian2=?,diminta_jam2=?,datang_jam2=?,bagian3=?,diminta_jam3=?,datang_jam3=?,observasi=?,dipulangkan=?,paps_dirujuk=?,dirujuk=?,dirujuk_ke=?,rawat_inap=?,rawat_inap_ke=?",59,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),GCS.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BB.getText(),TB.getText(),Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),Leher.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),KetFisik.getText(),KetLokalis.getText(),EKG.getText(),
                    hasil.getText(),nilai.getText(),Diagnosis.getText(),Tatalaksana.getText(),CaraDatang.getSelectedItem().toString(),KetCaraDatang.getText(),TempatKecelakaan.getText(),TglKecelakaan.getText(),JamKecelakaan.getText(),JenisKecelakaan.getSelectedItem().toString(),
                    Bagian1.getText(),DimintaJam1.getText(),DatangJam1.getText(),Bagian2.getText(),DimintaJam2.getText(),DatangJam2.getText(),Bagian3.getText(),DimintaJam3.getText(),DatangJam3.getText(),
                    ChkObservasi.isSelected()?"Ya":"Tidak",ChkDipulangkan.isSelected()?"Ya":"Tidak",ChkPAPS.isSelected()?"Ya":"Tidak",ChkDirujukKe.isSelected()?"Ya":"Tidak",DirujukKe.getText(),ChkRawatInap.isSelected()?"Ya":"Tidak",RawatInapKe.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
               refreshMarkingImage();
        }
    }

    private boolean validasiDokter() {
        if(KdDokter.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Dokter belum dipilih.");
            KdDokter.requestFocus();
            return false;
        }
        if(Sequel.cariIsi("select kd_dokter from dokter where kd_dokter=?",KdDokter.getText()).trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Kode dokter tidak ditemukan di tabel dokter.");
            KdDokter.requestFocus();
            return false;
        }
        return true;
    }

    private String ekspresiKolomReportPenilaianIGD(String kolomBaru, String kolomLama) {
        boolean adaKolomBaru = Sequel.cariIsi(
            "select count(*) from information_schema.columns where table_schema=database() and table_name='penilaian_medis_igd' and column_name='"+kolomBaru+"'"
        ).equals("1");
        boolean adaKolomLama = Sequel.cariIsi(
            "select count(*) from information_schema.columns where table_schema=database() and table_name='penilaian_medis_igd' and column_name='"+kolomLama+"'"
        ).equals("1");

        if(adaKolomBaru && adaKolomLama){
            return "ifnull(nullif(penilaian_medis_igd."+kolomBaru+",''),penilaian_medis_igd."+kolomLama+")";
        }else if(adaKolomBaru){
            return "penilaian_medis_igd."+kolomBaru;
        }else if(adaKolomLama){
            return "penilaian_medis_igd."+kolomLama;
        }else{
            return "''";
        }
    }

    boolean imageAssesment(String url){
        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            PanelWall.setBackgroundImage(new javax.swing.ImageIcon(img));
            return true;
        }
        catch(IOException ex) {
            return false;
        }
    }

    private void refreshMarkingImage() {
        String found = Sequel.cariIsi("select url_image from asesmen_medis_igd_image_marking where no_rawat=?", TNoRw.getText());
        if (found == null || found.trim().isEmpty()) {
            // tampilkan default jika tidak ada marking
            PanelWall.setBackgroundImage(new ImageIcon(getClass().getResource("/picture/semua.png")));
            return;
        }
        urlImage = found.trim();
        String fullUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/" + urlImage;
        if (!imageAssesment(fullUrl)) {
            // fallback to default bila gagal load
            PanelWall.setBackgroundImage(new ImageIcon(getClass().getResource("/picture/semua.png")));
        }
    }
    
}
