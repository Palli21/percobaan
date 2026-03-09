/*
 * Kontribusi Mega RS Simpangan Depok
 */


package rekammedis;
import java.awt.Color;
import fungsi.WarnaTablePEWS;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPemantauanPEWS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPemantauanPEWS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tanggal","Skor Perilaku","Skor 1","Skor CRT / Warna Kulit","Skor 2",
            "Skor Perespirasi","Skor 3","Ttl.Skor","Kesimpulan","NIP","Petugas","Tgl.Lahir"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable;
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                 column.setPreferredWidth(105);
            }else if(i==1){
                 column.setPreferredWidth(65);
            }else if(i==2){
                 column.setPreferredWidth(160);
            }else if(i==3){
                 column.setPreferredWidth(35);
            }else if(i==4){
                 column.setPreferredWidth(20);
            }else if(i==5){
                 column.setPreferredWidth(120);
            }else if(i==6){
                 column.setPreferredWidth(240);
            }else if(i==7){
                 column.setPreferredWidth(39);
            }else if(i==8){
                 column.setPreferredWidth(122);
            }else if(i==9){
                 column.setPreferredWidth(39);
            }else if(i==10){
                 column.setPreferredWidth(145);
            }else if(i==11){
                 column.setPreferredWidth(39);
            }else if(i==12){
                 column.setPreferredWidth(45);
            }else if(i==13){
                 column.setPreferredWidth(450);
            }else if(i==14){
                 column.setPreferredWidth(80);
            }else if(i==15){
                 column.setPreferredWidth(150);
            }else{
                 column.setMinWidth(0);
                 column.setMaxWidth(0);
            } 
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTablePEWS());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
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
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                KdPetugas.requestFocus();
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
        
        
        ChkInput.setSelected(false);
        isForm();
        jam();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPemantauanPEWS = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NmPetugas = new widget.TextBox();
        KdPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        TglLahir = new widget.TextBox();
        jLabel14 = new widget.Label();
        cmbSkor1 = new widget.ComboBox();
        jLabel17 = new widget.Label();
        cmbSkor2 = new widget.ComboBox();
        Skor1 = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        Skor2 = new widget.TextBox();
        jLabel23 = new widget.Label();
        cmbSkor3 = new widget.ComboBox();
        jLabel26 = new widget.Label();
        Skor3 = new widget.TextBox();
        jLabel27 = new widget.Label();
        TotalSkor = new widget.TextBox();
        ParameterSkor = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel9 = new widget.Label();
        textBox21 = new widget.TextBox2();
        jLabel15 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        textBox22 = new widget.TextBox2();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        textBox23 = new widget.TextBox2();
        FormInput1 = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        Tanggal1 = new widget.Tanggal();
        TNoRM1 = new widget.TextBox();
        jLabel36 = new widget.Label();
        Jam1 = new widget.ComboBox();
        Menit1 = new widget.ComboBox();
        Detik1 = new widget.ComboBox();
        ChkKejadian1 = new widget.CekBox();
        jLabel56 = new widget.Label();
        NmPetugas1 = new widget.TextBox();
        KdPetugas1 = new widget.TextBox();
        btnPetugas1 = new widget.Button();
        TglLahir1 = new widget.TextBox();
        jLabel57 = new widget.Label();
        cmbSkor4 = new widget.ComboBox();
        jLabel61 = new widget.Label();
        cmbSkor5 = new widget.ComboBox();
        Skor4 = new widget.TextBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Skor5 = new widget.TextBox();
        jLabel72 = new widget.Label();
        cmbSkor6 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        Skor6 = new widget.TextBox();
        jLabel77 = new widget.Label();
        TotalSkor1 = new widget.TextBox();
        ParameterSkor1 = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel10 = new widget.Label();
        textBox24 = new widget.TextBox2();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel109 = new widget.Label();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        textBox25 = new widget.TextBox2();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        jLabel131 = new widget.Label();
        jLabel132 = new widget.Label();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel137 = new widget.Label();
        jLabel138 = new widget.Label();
        jLabel139 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel140 = new widget.Label();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        jLabel144 = new widget.Label();
        jLabel145 = new widget.Label();
        jLabel146 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        textBox26 = new widget.TextBox2();
        textBox27 = new widget.TextBox2();
        textBox28 = new widget.TextBox2();
        textBox29 = new widget.TextBox2();
        textBox30 = new widget.TextBox2();
        jLabel147 = new widget.Label();
        textBox31 = new widget.TextBox2();
        jLabel148 = new widget.Label();
        jLabel149 = new widget.Label();
        textBox32 = new widget.TextBox2();
        jLabel150 = new widget.Label();
        jLabel151 = new widget.Label();
        textBox33 = new widget.TextBox2();
        jLabel152 = new widget.Label();
        jLabel153 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPemantauanPEWS.setBackground(new java.awt.Color(255, 255, 254));
        MnPemantauanPEWS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemantauanPEWS.setForeground(new java.awt.Color(50, 50, 50));
        MnPemantauanPEWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemantauanPEWS.setText("Lembar Pemantauan PEWS");
        MnPemantauanPEWS.setName("MnPemantauanPEWS"); // NOI18N
        MnPemantauanPEWS.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPemantauanPEWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemantauanPEWSActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemantauanPEWS);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemantauan PEWS Pasien Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-11-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-11-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(315, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 224));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-11-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(571, 40, 187, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

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
        btnPetugas.setBounds(761, 40, 28, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Respon Terhadap");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(670, 260, 90, 20);

        cmbSkor1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sadar / Bermain", "Tidur / Perubahan Perilaku", "Gelisah", "Tidak Merespon Terhadap Nyeri Penurunan Kesadaran" }));
        cmbSkor1.setName("cmbSkor1"); // NOI18N
        cmbSkor1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor1ItemStateChanged(evt);
            }
        });
        cmbSkor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor1KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor1);
        cmbSkor1.setBounds(270, 80, 380, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("2.  Skor CRT / Warna Kulit");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(80, 110, 160, 23);

        cmbSkor2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 - 2 dtk / Pink", "3 dtk / Pucat", "4 dtk / Sianosis", ">=5 dtk / Mottle" }));
        cmbSkor2.setName("cmbSkor2"); // NOI18N
        cmbSkor2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor2ItemStateChanged(evt);
            }
        });
        cmbSkor2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor2KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor2);
        cmbSkor2.setBounds(270, 110, 380, 23);

        Skor1.setEditable(false);
        Skor1.setBackground(new java.awt.Color(0, 255, 0));
        Skor1.setForeground(new java.awt.Color(255, 255, 255));
        Skor1.setText("0");
        Skor1.setFocusTraversalPolicyProvider(true);
        Skor1.setName("Skor1"); // NOI18N
        Skor1.setOpaque(true);
        FormInput.add(Skor1);
        Skor1.setBounds(745, 80, 44, 23);

        jLabel20.setText("Skor :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(681, 80, 60, 23);

        jLabel22.setText("Skor :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(681, 110, 60, 23);

        Skor2.setEditable(false);
        Skor2.setBackground(new java.awt.Color(0, 255, 0));
        Skor2.setForeground(new java.awt.Color(255, 255, 255));
        Skor2.setText("0");
        Skor2.setFocusTraversalPolicyProvider(true);
        Skor2.setName("Skor2"); // NOI18N
        Skor2.setOpaque(true);
        FormInput.add(Skor2);
        Skor2.setBounds(745, 110, 44, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("3.  Skor Perespirasi");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(80, 140, 160, 23);

        cmbSkor3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Retraksi", "Cuping Hidung / O2 1-3 Lpm", "Retraksi Dada / O2 4-6 Lpm", "Stridor / O2 7-8 Lpm" }));
        cmbSkor3.setName("cmbSkor3"); // NOI18N
        cmbSkor3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor3ItemStateChanged(evt);
            }
        });
        cmbSkor3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor3KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor3);
        cmbSkor3.setBounds(270, 140, 380, 23);

        jLabel26.setText("Skor :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(681, 140, 60, 23);

        Skor3.setEditable(false);
        Skor3.setBackground(new java.awt.Color(0, 255, 0));
        Skor3.setForeground(new java.awt.Color(255, 255, 255));
        Skor3.setText("0");
        Skor3.setFocusTraversalPolicyProvider(true);
        Skor3.setName("Skor3"); // NOI18N
        Skor3.setOpaque(true);
        FormInput.add(Skor3);
        Skor3.setBounds(745, 140, 44, 23);

        jLabel27.setText("Total Skor :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 170, 75, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setText("0");
        TotalSkor.setFocusTraversalPolicyProvider(true);
        TotalSkor.setName("TotalSkor"); // NOI18N
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(79, 170, 40, 23);

        ParameterSkor.setEditable(false);
        ParameterSkor.setFocusTraversalPolicyProvider(true);
        ParameterSkor.setName("ParameterSkor"); // NOI18N
        FormInput.add(ParameterSkor);
        ParameterSkor.setBounds(121, 170, 668, 23);

        jLabel28.setText("Parameter :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 70, 75, 23);

        jLabel9.setText("Tgl.Lahir :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(625, 10, 60, 23);

        textBox21.setBackground(new java.awt.Color(153, 204, 255));
        textBox21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox21.setText("NEWSS PASIEN ANAK");
        textBox21.setName("textBox21"); // NOI18N
        FormInput.add(textBox21);
        textBox21.setBounds(10, 210, 820, 20);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("1.  Skor Perilaku");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(80, 80, 160, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("3");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(670, 230, 80, 20);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("sesuai");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(150, 260, 80, 20);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Cenderung Murung/Diam");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(280, 250, 120, 20);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Sensitif");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(490, 250, 80, 20);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Letargik/Bingung/Penurunan");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(650, 250, 140, 20);

        jLabel33.setBackground(new java.awt.Color(204, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("0");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(120, 230, 80, 20);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("1");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(290, 230, 80, 20);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("2");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(470, 230, 80, 20);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Perilaku");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(10, 250, 80, 20);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Respirasi");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(10, 320, 80, 20);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("terdapat retraksi dada");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(460, 330, 170, 20);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Pucat atau CRT 3 Detik. Tekanan");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(250, 280, 170, 20);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Darah Sistolik 10 mmHg diatas");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(270, 290, 150, 20);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("atau dibawah nilai Normal");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(260, 300, 130, 20);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("gunting (mendengkur)");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(630, 330, 130, 20);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Takikardia. Nadi Lebih Tinggi/");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(450, 290, 160, 20);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Rendah 10x Menit");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(470, 300, 130, 20);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Abu-abu\\Bitu CRT 4 Detik ");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(460, 280, 130, 20);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Abu-abu\\Bitu CRT > 5 Detik ");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(650, 280, 150, 20);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("atau Takkardia. Nadi Lebih Tinggi/Rendah");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(630, 300, 210, 20);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("dan memberikan instruksi tata laksana pada pasien tersebut. Perawat pelaksana harus monitor tanda- tanda vital setiap jam.");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(590, 510, 840, 20);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Kardio Vaskuler");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(10, 290, 80, 20);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Pimk atau CRT 1-2 Detik");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(110, 290, 130, 20);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Normal/Tidak ada Retreaksi");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(100, 320, 150, 20);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("RR > 20 di atas Normal");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(460, 320, 130, 20);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("RR > 10 di atas Normal");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(260, 320, 130, 20);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Menggunakan otot-otot aksesoris");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(250, 330, 170, 20);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(10, 320, 810, 3);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 70, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(10, 350, 810, 3);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(10, 280, 810, 3);

        textBox22.setBackground(new java.awt.Color(255, 51, 51));
        textBox22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox22.setName("textBox22"); // NOI18N
        FormInput.add(textBox22);
        textBox22.setBounds(490, 540, 80, 20);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("(x/menit)");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(110, 410, 80, 20);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Sistolik (mmHg)");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(200, 410, 80, 20);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("(x/menit)");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(340, 410, 80, 20);

        jLabel62.setBackground(new java.awt.Color(204, 255, 255));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Usia");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 390, 80, 20);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Tekanan Darah");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(200, 390, 80, 20);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Frekuensi Nafas");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(330, 390, 80, 20);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText(">12 Tahun");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(20, 510, 80, 20);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("30");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(350, 510, 20, 20);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("70");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(220, 470, 20, 20);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("60");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(220, 450, 20, 20);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("56");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(220, 430, 20, 20);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("40");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(350, 470, 50, 20);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("50");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(350, 450, 20, 20);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("60");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(350, 430, 20, 20);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("0-3 Bulan");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(20, 430, 80, 20);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("100 - 180");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(110, 430, 50, 20);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("60 - 130");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(110, 510, 60, 20);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("30");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(350, 490, 20, 20);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("80");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(220, 490, 20, 20);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("90");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(220, 510, 20, 20);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(20, 530, 420, 3);

        jLabel85.setBackground(new java.awt.Color(204, 255, 255));
        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("Frekuensi Nadi");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(100, 390, 80, 20);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("4-12 Bulan");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(20, 450, 80, 20);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("1-4 Tahun");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(20, 470, 80, 20);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("5-12 Tahun");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(20, 490, 80, 20);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("100 - 180");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(110, 450, 60, 20);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("90 - 160");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(110, 470, 60, 20);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("80 - 140");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(110, 490, 60, 20);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(20, 430, 420, 3);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(20, 450, 420, 3);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(20, 470, 420, 3);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(20, 490, 420, 3);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(20, 510, 420, 3);

        textBox23.setBackground(new java.awt.Color(153, 204, 255));
        textBox23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox23.setText("Nilai Normal Sesuai Usia");
        textBox23.setName("textBox23"); // NOI18N
        FormInput.add(textBox23);
        textBox23.setBounds(20, 370, 420, 20);

        FormInput1.setBackground(new java.awt.Color(250, 255, 245));
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput1.setLayout(null);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput1.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw1KeyPressed(evt);
            }
        });
        FormInput1.add(TNoRw1);
        TNoRw1.setBounds(79, 10, 141, 23);

        TPasien1.setEditable(false);
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasien1KeyPressed(evt);
            }
        });
        FormInput1.add(TPasien1);
        TPasien1.setBounds(336, 10, 285, 23);

        Tanggal1.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-11-2024" }));
        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.setOpaque(false);
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        FormInput1.add(Tanggal1);
        Tanggal1.setBounds(79, 40, 90, 23);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRM1KeyPressed(evt);
            }
        });
        FormInput1.add(TNoRM1);
        TNoRM1.setBounds(222, 10, 112, 23);

        jLabel36.setText("Tanggal :");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setVerifyInputWhenFocusTarget(false);
        FormInput1.add(jLabel36);
        jLabel36.setBounds(0, 40, 75, 23);

        Jam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam1.setName("Jam1"); // NOI18N
        Jam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jam1KeyPressed(evt);
            }
        });
        FormInput1.add(Jam1);
        Jam1.setBounds(173, 40, 62, 23);

        Menit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit1.setName("Menit1"); // NOI18N
        Menit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Menit1KeyPressed(evt);
            }
        });
        FormInput1.add(Menit1);
        Menit1.setBounds(238, 40, 62, 23);

        Detik1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik1.setName("Detik1"); // NOI18N
        Detik1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Detik1KeyPressed(evt);
            }
        });
        FormInput1.add(Detik1);
        Detik1.setBounds(303, 40, 62, 23);

        ChkKejadian1.setBorder(null);
        ChkKejadian1.setSelected(true);
        ChkKejadian1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian1.setName("ChkKejadian1"); // NOI18N
        FormInput1.add(ChkKejadian1);
        ChkKejadian1.setBounds(368, 40, 23, 23);

        jLabel56.setText("Petugas :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput1.add(jLabel56);
        jLabel56.setBounds(400, 40, 70, 23);

        NmPetugas1.setEditable(false);
        NmPetugas1.setName("NmPetugas1"); // NOI18N
        FormInput1.add(NmPetugas1);
        NmPetugas1.setBounds(571, 40, 187, 23);

        KdPetugas1.setEditable(false);
        KdPetugas1.setHighlighter(null);
        KdPetugas1.setName("KdPetugas1"); // NOI18N
        KdPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas1KeyPressed(evt);
            }
        });
        FormInput1.add(KdPetugas1);
        KdPetugas1.setBounds(474, 40, 94, 23);

        btnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas1.setMnemonic('2');
        btnPetugas1.setToolTipText("ALt+2");
        btnPetugas1.setName("btnPetugas1"); // NOI18N
        btnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugas1ActionPerformed(evt);
            }
        });
        btnPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugas1KeyPressed(evt);
            }
        });
        FormInput1.add(btnPetugas1);
        btnPetugas1.setBounds(761, 40, 28, 23);

        TglLahir1.setHighlighter(null);
        TglLahir1.setName("TglLahir1"); // NOI18N
        FormInput1.add(TglLahir1);
        TglLahir1.setBounds(689, 10, 100, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Respon Terhadap");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput1.add(jLabel57);
        jLabel57.setBounds(670, 260, 90, 20);

        cmbSkor4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sadar / Bermain", "Tidur / Perubahan Perilaku", "Gelisah", "Tidak Merespon Terhadap Nyeri Penurunan Kesadaran" }));
        cmbSkor4.setName("cmbSkor4"); // NOI18N
        cmbSkor4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor4ItemStateChanged(evt);
            }
        });
        cmbSkor4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor4KeyPressed(evt);
            }
        });
        FormInput1.add(cmbSkor4);
        cmbSkor4.setBounds(270, 80, 380, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("2.  Skor CRT / Warna Kulit");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput1.add(jLabel61);
        jLabel61.setBounds(80, 110, 160, 23);

        cmbSkor5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 - 2 dtk / Pink", "3 dtk / Pucat", "4 dtk / Sianosis", ">=5 dtk / Mottle" }));
        cmbSkor5.setName("cmbSkor5"); // NOI18N
        cmbSkor5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor5ItemStateChanged(evt);
            }
        });
        cmbSkor5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor5KeyPressed(evt);
            }
        });
        FormInput1.add(cmbSkor5);
        cmbSkor5.setBounds(270, 110, 380, 23);

        Skor4.setEditable(false);
        Skor4.setBackground(new java.awt.Color(0, 255, 0));
        Skor4.setForeground(new java.awt.Color(255, 255, 255));
        Skor4.setText("0");
        Skor4.setFocusTraversalPolicyProvider(true);
        Skor4.setName("Skor4"); // NOI18N
        Skor4.setOpaque(true);
        FormInput1.add(Skor4);
        Skor4.setBounds(745, 80, 44, 23);

        jLabel65.setText("Skor :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput1.add(jLabel65);
        jLabel65.setBounds(681, 80, 60, 23);

        jLabel66.setText("Skor :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput1.add(jLabel66);
        jLabel66.setBounds(681, 110, 60, 23);

        Skor5.setEditable(false);
        Skor5.setBackground(new java.awt.Color(0, 255, 0));
        Skor5.setForeground(new java.awt.Color(255, 255, 255));
        Skor5.setText("0");
        Skor5.setFocusTraversalPolicyProvider(true);
        Skor5.setName("Skor5"); // NOI18N
        Skor5.setOpaque(true);
        FormInput1.add(Skor5);
        Skor5.setBounds(745, 110, 44, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("3.  Skor Perespirasi");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput1.add(jLabel72);
        jLabel72.setBounds(80, 140, 160, 23);

        cmbSkor6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Retraksi", "Cuping Hidung / O2 1-3 Lpm", "Retraksi Dada / O2 4-6 Lpm", "Stridor / O2 7-8 Lpm" }));
        cmbSkor6.setName("cmbSkor6"); // NOI18N
        cmbSkor6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor6ItemStateChanged(evt);
            }
        });
        cmbSkor6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor6KeyPressed(evt);
            }
        });
        FormInput1.add(cmbSkor6);
        cmbSkor6.setBounds(270, 140, 380, 23);

        jLabel76.setText("Skor :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput1.add(jLabel76);
        jLabel76.setBounds(681, 140, 60, 23);

        Skor6.setEditable(false);
        Skor6.setBackground(new java.awt.Color(0, 255, 0));
        Skor6.setForeground(new java.awt.Color(255, 255, 255));
        Skor6.setText("0");
        Skor6.setFocusTraversalPolicyProvider(true);
        Skor6.setName("Skor6"); // NOI18N
        Skor6.setOpaque(true);
        FormInput1.add(Skor6);
        Skor6.setBounds(745, 140, 44, 23);

        jLabel77.setText("Total Skor :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput1.add(jLabel77);
        jLabel77.setBounds(0, 170, 75, 23);

        TotalSkor1.setEditable(false);
        TotalSkor1.setText("0");
        TotalSkor1.setFocusTraversalPolicyProvider(true);
        TotalSkor1.setName("TotalSkor1"); // NOI18N
        FormInput1.add(TotalSkor1);
        TotalSkor1.setBounds(79, 170, 40, 23);

        ParameterSkor1.setEditable(false);
        ParameterSkor1.setFocusTraversalPolicyProvider(true);
        ParameterSkor1.setName("ParameterSkor1"); // NOI18N
        FormInput1.add(ParameterSkor1);
        ParameterSkor1.setBounds(121, 170, 668, 23);

        jLabel78.setText("Parameter :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput1.add(jLabel78);
        jLabel78.setBounds(0, 70, 75, 23);

        jLabel10.setText("Tgl.Lahir :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput1.add(jLabel10);
        jLabel10.setBounds(625, 10, 60, 23);

        textBox24.setBackground(new java.awt.Color(153, 204, 255));
        textBox24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox24.setText("NEWSS PASIEN ANAK");
        textBox24.setName("textBox24"); // NOI18N
        FormInput1.add(textBox24);
        textBox24.setBounds(10, 210, 820, 20);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("1.  Skor Perilaku");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput1.add(jLabel92);
        jLabel92.setBounds(80, 80, 160, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("3");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput1.add(jLabel93);
        jLabel93.setBounds(670, 230, 80, 20);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("sesuai");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput1.add(jLabel94);
        jLabel94.setBounds(150, 260, 80, 20);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Cenderung Murung/Diam");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput1.add(jLabel95);
        jLabel95.setBounds(280, 250, 120, 20);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Sensitif");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput1.add(jLabel96);
        jLabel96.setBounds(490, 250, 80, 20);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("Letargik/Bingung/Penurunan");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput1.add(jLabel97);
        jLabel97.setBounds(650, 250, 140, 20);

        jLabel98.setBackground(new java.awt.Color(204, 255, 255));
        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel98.setText("0");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput1.add(jLabel98);
        jLabel98.setBounds(120, 230, 80, 20);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setText("1");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput1.add(jLabel99);
        jLabel99.setBounds(290, 230, 80, 20);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setText("2");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput1.add(jLabel100);
        jLabel100.setBounds(470, 230, 80, 20);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("Perilaku");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput1.add(jLabel101);
        jLabel101.setBounds(10, 250, 80, 20);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("Respirasi");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput1.add(jLabel102);
        jLabel102.setBounds(10, 320, 80, 20);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("terdapat retraksi dada");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput1.add(jLabel103);
        jLabel103.setBounds(460, 330, 170, 20);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Pucat atau CRT 3 Detik. Tekanan");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput1.add(jLabel104);
        jLabel104.setBounds(250, 280, 170, 20);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Darah Sistolik 10 mmHg diatas");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput1.add(jLabel105);
        jLabel105.setBounds(270, 290, 150, 20);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("atau dibawah nilai Normal");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput1.add(jLabel106);
        jLabel106.setBounds(260, 300, 130, 20);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("gunting (mendengkur)");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput1.add(jLabel107);
        jLabel107.setBounds(630, 330, 130, 20);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Takikardia. Nadi Lebih Tinggi/");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput1.add(jLabel108);
        jLabel108.setBounds(450, 290, 160, 20);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Rendah 10x Menit");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput1.add(jLabel109);
        jLabel109.setBounds(470, 300, 130, 20);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Abu-abu\\Bitu CRT 4 Detik ");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput1.add(jLabel110);
        jLabel110.setBounds(460, 280, 130, 20);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Abu-abu\\Bitu CRT > 5 Detik ");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput1.add(jLabel111);
        jLabel111.setBounds(650, 280, 150, 20);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("atau Takkardia. Nadi Lebih Tinggi/Rendah");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput1.add(jLabel112);
        jLabel112.setBounds(630, 300, 210, 20);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("5Dibawah Normal dengan retreaksi ");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput1.add(jLabel113);
        jLabel113.setBounds(630, 320, 170, 20);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Kardio Vaskuler");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput1.add(jLabel114);
        jLabel114.setBounds(10, 290, 80, 20);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Pimk atau CRT 1-2 Detik");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput1.add(jLabel115);
        jLabel115.setBounds(110, 290, 130, 20);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Normal/Tidak ada Retreaksi");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput1.add(jLabel116);
        jLabel116.setBounds(100, 320, 150, 20);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("RR > 20 di atas Normal");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput1.add(jLabel117);
        jLabel117.setBounds(460, 320, 130, 20);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("RR > 10 di atas Normal");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput1.add(jLabel118);
        jLabel118.setBounds(260, 320, 130, 20);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("Menggunakan otot-otot aksesoris");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput1.add(jLabel119);
        jLabel119.setBounds(250, 330, 170, 20);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput1.add(jSeparator7);
        jSeparator7.setBounds(10, 320, 810, 3);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput1.add(jSeparator8);
        jSeparator8.setBounds(0, 70, 880, 1);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput1.add(jSeparator15);
        jSeparator15.setBounds(10, 350, 810, 3);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput1.add(jSeparator16);
        jSeparator16.setBounds(10, 280, 810, 3);

        textBox25.setBackground(new java.awt.Color(153, 204, 255));
        textBox25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox25.setText("HIJAU 0-2");
        textBox25.setName("textBox25"); // NOI18N
        FormInput1.add(textBox25);
        textBox25.setBounds(490, 370, 80, 20);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("(x/menit)");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput1.add(jLabel120);
        jLabel120.setBounds(110, 410, 80, 20);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Sistolik (mmHg)");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput1.add(jLabel121);
        jLabel121.setBounds(200, 410, 80, 20);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("(x/menit)");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput1.add(jLabel122);
        jLabel122.setBounds(340, 410, 80, 20);

        jLabel123.setBackground(new java.awt.Color(204, 255, 255));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Usia");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput1.add(jLabel123);
        jLabel123.setBounds(0, 390, 80, 20);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Tekanan Darah");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput1.add(jLabel124);
        jLabel124.setBounds(200, 390, 80, 20);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("Frekuensi Nafas");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput1.add(jLabel125);
        jLabel125.setBounds(330, 390, 80, 20);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText(">12 Tahun");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput1.add(jLabel126);
        jLabel126.setBounds(20, 510, 80, 20);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("30");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput1.add(jLabel127);
        jLabel127.setBounds(350, 510, 20, 20);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("70");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput1.add(jLabel128);
        jLabel128.setBounds(220, 470, 20, 20);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("60");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput1.add(jLabel129);
        jLabel129.setBounds(220, 450, 20, 20);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("56");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput1.add(jLabel130);
        jLabel130.setBounds(220, 430, 20, 20);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("40");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput1.add(jLabel131);
        jLabel131.setBounds(350, 470, 50, 20);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("50");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput1.add(jLabel132);
        jLabel132.setBounds(350, 450, 20, 20);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("60");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput1.add(jLabel133);
        jLabel133.setBounds(350, 430, 20, 20);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("0-3 Bulan");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput1.add(jLabel134);
        jLabel134.setBounds(20, 430, 80, 20);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("100 - 180");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput1.add(jLabel135);
        jLabel135.setBounds(110, 430, 50, 20);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("60 - 130");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput1.add(jLabel136);
        jLabel136.setBounds(110, 510, 60, 20);

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText("30");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput1.add(jLabel137);
        jLabel137.setBounds(350, 490, 20, 20);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("80");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput1.add(jLabel138);
        jLabel138.setBounds(220, 490, 20, 20);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("90");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput1.add(jLabel139);
        jLabel139.setBounds(220, 510, 20, 20);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput1.add(jSeparator17);
        jSeparator17.setBounds(20, 530, 420, 3);

        jLabel140.setBackground(new java.awt.Color(204, 255, 255));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setText("Frekuensi Nadi");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput1.add(jLabel140);
        jLabel140.setBounds(100, 390, 80, 20);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("4-12 Bulan");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput1.add(jLabel141);
        jLabel141.setBounds(20, 450, 80, 20);

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setText("1-4 Tahun");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput1.add(jLabel142);
        jLabel142.setBounds(20, 470, 80, 20);

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel143.setText("5-12 Tahun");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput1.add(jLabel143);
        jLabel143.setBounds(20, 490, 80, 20);

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setText("100 - 180");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput1.add(jLabel144);
        jLabel144.setBounds(110, 450, 60, 20);

        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel145.setText("90 - 160");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput1.add(jLabel145);
        jLabel145.setBounds(110, 470, 60, 20);

        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel146.setText("80 - 140");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput1.add(jLabel146);
        jLabel146.setBounds(110, 490, 60, 20);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput1.add(jSeparator18);
        jSeparator18.setBounds(20, 430, 420, 3);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput1.add(jSeparator19);
        jSeparator19.setBounds(20, 450, 420, 3);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput1.add(jSeparator20);
        jSeparator20.setBounds(20, 470, 420, 3);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput1.add(jSeparator21);
        jSeparator21.setBounds(20, 490, 420, 3);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput1.add(jSeparator22);
        jSeparator22.setBounds(20, 510, 420, 3);

        textBox26.setBackground(new java.awt.Color(153, 204, 255));
        textBox26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox26.setText("Nilai Normal Sesuai Usia");
        textBox26.setName("textBox26"); // NOI18N
        FormInput1.add(textBox26);
        textBox26.setBounds(20, 370, 420, 20);

        FormInput.add(FormInput1);
        FormInput1.setBounds(0, 0, 100, 225);

        textBox27.setBackground(new java.awt.Color(0, 255, 51));
        textBox27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox27.setName("textBox27"); // NOI18N
        FormInput.add(textBox27);
        textBox27.setBounds(490, 410, 80, 20);

        textBox28.setBackground(new java.awt.Color(255, 255, 102));
        textBox28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox28.setName("textBox28"); // NOI18N
        FormInput.add(textBox28);
        textBox28.setBounds(490, 440, 80, 20);

        textBox29.setBackground(new java.awt.Color(255, 153, 0));
        textBox29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox29.setName("textBox29"); // NOI18N
        FormInput.add(textBox29);
        textBox29.setBounds(490, 490, 80, 20);

        textBox30.setBackground(new java.awt.Color(0, 255, 51));
        textBox30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox30.setText("Hijau 0-2");
        textBox30.setName("textBox30"); // NOI18N
        FormInput.add(textBox30);
        textBox30.setBounds(490, 370, 80, 20);

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("5Dibawah Normal dengan retreaksi ");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(630, 320, 170, 20);

        textBox31.setBackground(new java.awt.Color(255, 255, 102));
        textBox31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox31.setText("Kuning 3");
        textBox31.setName("textBox31"); // NOI18N
        FormInput.add(textBox31);
        textBox31.setBounds(570, 370, 80, 20);

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Hijau : Pasien dalam kondisi Stabil, monitor tanda-tanda vital setiap pergantian jaga / shfit");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(590, 410, 460, 20);

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel149.setText("Kuning : Pengkajian ulang harus dilakukan oleh perawat primer /PJ Shift. JIka score pasien akurat maka perawat primer atau PP harus menentukan");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(590, 440, 710, 20);

        textBox32.setBackground(new java.awt.Color(255, 153, 0));
        textBox32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox32.setText("Orange 4");
        textBox32.setName("textBox32"); // NOI18N
        FormInput.add(textBox32);
        textBox32.setBounds(650, 370, 80, 20);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("tindakan terhadap kondisi pasien dan melakukan pengkajian ulang setiap 2 jam oleh Perawat pelaksana. Pastikan kondisi pasien tercatat di catatan perkembangan pasien");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(590, 460, 840, 20);

        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel151.setText("rencana perawatan pasien selanjutnya. Perawat Pelaksana harus memonitor tanda tanda vital setiap jam");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(590, 560, 840, 20);

        textBox33.setBackground(new java.awt.Color(255, 51, 51));
        textBox33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textBox33.setText("Merah>5");
        textBox33.setName("textBox33"); // NOI18N
        FormInput.add(textBox33);
        textBox33.setBounds(730, 370, 80, 20);

        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel152.setText("Orange : Pengkajian ulang harus dilakukan oleh Perawat Primer/PJ Shift dan diketahui oleh dokter jaga Residen. DOkter jaga residen harus melaporkan ke DPJP");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(590, 490, 840, 20);

        jLabel153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel153.setText("Merah : TMRC melakukan tatalaksana kegawatan pada pasien. Dokter jaga residen hadir disamping pasien (dalam pemantuan/lapor DPJP) dan berkolaborasi untuk menentukan rencana ");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(590, 540, 840, 20);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(Skor1.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 1");
        }else if(Skor2.getText().trim().equals("")){
            Valid.textKosong(Skor2,"Skor 2");
        }else if(Skor3.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 3");
        }else{
            isCombo1();
            isCombo2();
            isCombo3();
            isjml();
            isHitung();
            if(Sequel.menyimpantf("pemantauan_pews_anak","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                cmbSkor1.getSelectedItem().toString(),Skor1.getText(),cmbSkor2.getSelectedItem().toString(),Skor2.getText(),
                cmbSkor3.getSelectedItem().toString(),Skor3.getText(),TotalSkor.getText(),ParameterSkor.getText(),KdPetugas.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    cmbSkor1.getSelectedItem().toString(),Skor1.getText(),cmbSkor2.getSelectedItem().toString(),Skor2.getText(),cmbSkor3.getSelectedItem().toString(),Skor3.getText(),TotalSkor.getText(),ParameterSkor.getText(),
                    KdPetugas.getText(),NmPetugas.getText(),TglLahir.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }   
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,cmbSkor3,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
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
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            if(TCari.getText().trim().equals("")){
              Valid.MyReportqry("rptDataPemantauanPEWS.jasper","report","::[ Data Pemantauan PEWS ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_anak.tanggal,pemantauan_pews_anak.parameter_perilaku,pemantauan_pews_anak.skor_perilaku,pemantauan_pews_anak.parameter_crt_atau_warna_kulit,pemantauan_pews_anak.skor_crt_atau_warna_kulit,pemantauan_pews_anak.parameter_perespirasi,pemantauan_pews_anak.skor_perespirasi,"+
                    "pemantauan_pews_anak.skor_total,pemantauan_pews_anak.parameter_total,pemantauan_pews_anak.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_anak inner join reg_periksa on pemantauan_pews_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_anak.nip=petugas.nip where "+
                    "pemantauan_pews_anak.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by pemantauan_pews_anak.tanggal ",param);
            }else{
                Valid.MyReportqry("rptDataPemantauanPEWS.jasper","report","::[ Data Pemantauan PEWS ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_anak.tanggal,pemantauan_pews_anak.parameter_perilaku,pemantauan_pews_anak.skor_perilaku,pemantauan_pews_anak.parameter_crt_atau_warna_kulit,pemantauan_pews_anak.skor_crt_atau_warna_kulit,pemantauan_pews_anak.parameter_perespirasi,pemantauan_pews_anak.skor_perespirasi,"+
                    "pemantauan_pews_anak.skor_total,pemantauan_pews_anak.parameter_total,pemantauan_pews_anak.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_anak inner join reg_periksa on pemantauan_pews_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_anak.nip=petugas.nip "+
                    "where pemantauan_pews_anak.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%'  or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                    "or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                    "or pemantauan_pews_anak.parameter_total like '%"+TCari.getText().trim()+"%' or pemantauan_pews_anak.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like ?) "+
                    "order by pemantauan_pews_anak.tanggal ",param);
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
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void MnPemantauanPEWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemantauanPEWSActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("diagnosa",Sequel.cariIsi("select kamar_inap.diagnosa_awal from kamar_inap where kamar_inap.diagnosa_awal<>'' and kamar_inap.no_rawat=? ",TNoRw.getText()));
            Valid.MyReportqry("rptFormulirPemantauanPEWS.jasper","report","::[ Pemantauan PEWS ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur, "+
                    "pasien.jk,pasien.tgl_lahir,pemantauan_pews_anak.tanggal,pemantauan_pews_anak.parameter_perilaku,pemantauan_pews_anak.skor_perilaku,pemantauan_pews_anak.parameter_crt_atau_warna_kulit,pemantauan_pews_anak.skor_crt_atau_warna_kulit, "+
                    "pemantauan_pews_anak.parameter_perespirasi,pemantauan_pews_anak.skor_perespirasi,pemantauan_pews_anak.skor_total,pemantauan_pews_anak.parameter_total, "+
                    "pemantauan_pews_anak.nip,petugas.nama "+
                    "from pemantauan_pews_anak inner join reg_periksa on pemantauan_pews_anak.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_anak.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPemantauanPEWSActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(Skor1.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 1");
        }else if(Skor2.getText().trim().equals("")){
            Valid.textKosong(Skor2,"Skor 2");
        }else if(Skor3.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 3");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            } 
        } 
    }//GEN-LAST:event_BtnEditActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void cmbSkor3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor3KeyPressed
        Valid.pindah(evt, cmbSkor2,BtnSimpan);
    }//GEN-LAST:event_cmbSkor3KeyPressed

    private void cmbSkor3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor3ItemStateChanged
        isCombo3();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor3ItemStateChanged

    private void cmbSkor2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor2KeyPressed
        Valid.pindah(evt, cmbSkor1, cmbSkor3);
    }//GEN-LAST:event_cmbSkor2KeyPressed

    private void cmbSkor2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor2ItemStateChanged
        isCombo2();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor2ItemStateChanged

    private void cmbSkor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor1KeyPressed
        Valid.pindah(evt,btnPetugas,cmbSkor2);
    }//GEN-LAST:event_cmbSkor1KeyPressed

    private void cmbSkor1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor1ItemStateChanged
        isCombo1();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor1ItemStateChanged

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,cmbSkor1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TNoRw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRw1KeyPressed

    private void TPasien1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasien1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasien1KeyPressed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void TNoRM1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRM1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRM1KeyPressed

    private void Jam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jam1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jam1KeyPressed

    private void Menit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Menit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Menit1KeyPressed

    private void Detik1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Detik1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Detik1KeyPressed

    private void KdPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas1KeyPressed

    private void btnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPetugas1ActionPerformed

    private void btnPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPetugas1KeyPressed

    private void cmbSkor4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor4ItemStateChanged

    private void cmbSkor4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor4KeyPressed

    private void cmbSkor5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor5ItemStateChanged

    private void cmbSkor5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor5KeyPressed

    private void cmbSkor6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor6ItemStateChanged

    private void cmbSkor6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor6KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPemantauanPEWS dialog = new RMPemantauanPEWS(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.CekBox ChkKejadian1;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.ComboBox Detik1;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.ComboBox Jam1;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas1;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private widget.ComboBox Menit1;
    private javax.swing.JMenuItem MnPemantauanPEWS;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas1;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox ParameterSkor;
    private widget.TextBox ParameterSkor1;
    private widget.ScrollPane Scroll;
    private widget.TextBox Skor1;
    private widget.TextBox Skor2;
    private widget.TextBox Skor3;
    private widget.TextBox Skor4;
    private widget.TextBox Skor5;
    private widget.TextBox Skor6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tanggal1;
    private widget.TextBox TglLahir;
    private widget.TextBox TglLahir1;
    private widget.TextBox TotalSkor;
    private widget.TextBox TotalSkor1;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private widget.Button btnPetugas1;
    private widget.ComboBox cmbSkor1;
    private widget.ComboBox cmbSkor2;
    private widget.ComboBox cmbSkor3;
    private widget.ComboBox cmbSkor4;
    private widget.ComboBox cmbSkor5;
    private widget.ComboBox cmbSkor6;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel14;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel15;
    private widget.Label jLabel150;
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
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
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
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
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    private widget.TextBox2 textBox21;
    private widget.TextBox2 textBox22;
    private widget.TextBox2 textBox23;
    private widget.TextBox2 textBox24;
    private widget.TextBox2 textBox25;
    private widget.TextBox2 textBox26;
    private widget.TextBox2 textBox27;
    private widget.TextBox2 textBox28;
    private widget.TextBox2 textBox29;
    private widget.TextBox2 textBox30;
    private widget.TextBox2 textBox31;
    private widget.TextBox2 textBox32;
    private widget.TextBox2 textBox33;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_anak.tanggal,pemantauan_pews_anak.parameter_perilaku,pemantauan_pews_anak.skor_perilaku,pemantauan_pews_anak.parameter_crt_atau_warna_kulit,pemantauan_pews_anak.skor_crt_atau_warna_kulit,pemantauan_pews_anak.parameter_perespirasi,pemantauan_pews_anak.skor_perespirasi,"+
                    "pemantauan_pews_anak.skor_total,pemantauan_pews_anak.parameter_total,pemantauan_pews_anak.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_anak inner join reg_periksa on pemantauan_pews_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_anak.nip=petugas.nip where "+
                    "pemantauan_pews_anak.tanggal between ? and ? order by pemantauan_pews_anak.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_anak.tanggal,pemantauan_pews_anak.parameter_perilaku,pemantauan_pews_anak.skor_perilaku,pemantauan_pews_anak.parameter_crt_atau_warna_kulit,pemantauan_pews_anak.skor_crt_atau_warna_kulit,pemantauan_pews_anak.parameter_perespirasi,pemantauan_pews_anak.skor_perespirasi,"+
                    "pemantauan_pews_anak.skor_total,pemantauan_pews_anak.parameter_total,pemantauan_pews_anak.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_anak inner join reg_periksa on pemantauan_pews_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_anak.nip=petugas.nip "+
                    "where pemantauan_pews_anak.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or pemantauan_pews_anak.parameter_total like ? or pemantauan_pews_anak.nip like ? or petugas.nama like ?) "+
                    "order by pemantauan_pews_anak.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("parameter_perilaku"),rs.getString("skor_perilaku"),
                        rs.getString("parameter_crt_atau_warna_kulit"),rs.getString("skor_crt_atau_warna_kulit"),rs.getString("parameter_perespirasi"),
                        rs.getString("skor_perespirasi"),rs.getString("skor_total"),rs.getString("parameter_total"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("lahir")
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
    
    private void isCombo1(){
        if(cmbSkor1.getSelectedItem().equals("Tidur / Perubahan Perilaku")){
            Skor1.setBackground(Color.YELLOW);
            Skor1.setForeground(Color.GREEN);
            Skor1.setText("1");
        }else if(cmbSkor1.getSelectedItem().equals("Gelisah")){
            Skor1.setBackground(Color.ORANGE);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("2");
        }else if(cmbSkor1.getSelectedItem().equals("Tidak Merespon Terhadap Nyeri Penurunan Kesadaran")){
            Skor1.setBackground(Color.RED);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("3");
        }else{
            Skor1.setBackground(Color.GREEN);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("0");
        }
    }
    
    private void isCombo2(){
        if(cmbSkor2.getSelectedItem().equals("3 dtk / Pucat")){
            Skor2.setBackground(Color.YELLOW);
            Skor2.setForeground(Color.GREEN);
            Skor2.setText("1");
        }else if(cmbSkor2.getSelectedItem().equals("4 dtk / Sianosis")){
            Skor2.setBackground(Color.ORANGE);
            Skor2.setForeground(Color.WHITE);
            Skor2.setText("2");
        }else if(cmbSkor2.getSelectedItem().equals(">=5 dtk / Mottle")){
            Skor2.setBackground(Color.RED);
            Skor2.setForeground(Color.WHITE); 
            Skor2.setText("3");
        }else {
            Skor2.setBackground(Color.GREEN);
            Skor2.setForeground(Color.WHITE);
            Skor2.setText("0");
        }
    }
    
    private void isCombo3(){
        if(cmbSkor3.getSelectedItem().equals("Cuping Hidung / O2 1-3 Lpm")){
            Skor3.setBackground(Color.YELLOW);
            Skor3.setForeground(Color.GREEN);
            Skor3.setText("1");
        }else if(cmbSkor3.getSelectedItem().equals("Retraksi Dada / O2 4-6 Lpm")){
            Skor3.setBackground(Color.ORANGE);
            Skor3.setForeground(Color.WHITE);
            Skor3.setText("2");
        }else if(cmbSkor3.getSelectedItem().equals("Stridor / O2 7-8 Lpm")){
            Skor3.setBackground(Color.RED);
            Skor3.setForeground(Color.WHITE);
            Skor3.setText("3");
        }else{
            Skor3.setBackground(Color.GREEN);
            Skor3.setForeground(Color.WHITE);
            Skor3.setText("0");
        }
    } 
    
    private void isjml(){
        if((!Skor1.getText().equals(""))&&(!Skor2.getText().equals(""))&&(!Skor3.getText().equals(""))){
            TotalSkor.setText(Valid.SetAngka2(
                    Double.parseDouble(Skor1.getText().trim())+
                    Double.parseDouble(Skor2.getText().trim())+
                    Double.parseDouble(Skor3.getText().trim())
            ));
        }
    }
    
    private void isHitung(){
        if(Integer.parseInt(TotalSkor.getText())>4){
            ParameterSkor.setText("Laporkan perubahan klinis ke perawat ketua tim, dokter jaga dan DPJP. Kolaborasikan langkah selanjutnya dengan seluruh tim perawatan");
        }else if(Integer.parseInt(TotalSkor.getText())>3){
            ParameterSkor.setText("Monitor per 1 jam. Laporkan ke dokter jaga dan kemudian tindak lanjut lapor ke DPJP untuk advis selanjutnya. Kolaborasi langkah selanjutnya dengan seluruh tim perawatan. Jika masih di perlukan lapor ulang keperawat ketua tim dan DPJP");
        }else if(Integer.parseInt(TotalSkor.getText())>2){
            ParameterSkor.setText("Monitoring 1 sampai 2 jam. Pengkajian ulang dilakukan oleh PJ sift dan laporkan ke dokter jaga");
        }else if(Integer.parseInt(TotalSkor.getText())>0){
            ParameterSkor.setText("Monitoring setiap 4 jam oleh perawat pelaksana dan di lanjutkan observasi atau monitoring secara rutin");
        }else if(Integer.parseInt(TotalSkor.getText())==0){
            ParameterSkor.setText("Beresiko rendah, ulangi setiap 7 jam");
        }
    }
    
    public void emptTeks() {
        Tanggal.setDate(new Date());
        cmbSkor1.setSelectedIndex(0);
        Skor1.setText("0");
        Skor1.setBackground(Color.GREEN);
        Skor1.setForeground(Color.WHITE);
        cmbSkor2.setSelectedIndex(0);
        Skor2.setText("0");
        Skor2.setBackground(Color.GREEN);
        Skor2.setForeground(Color.WHITE);
        cmbSkor3.setSelectedIndex(0);
        Skor3.setText("0");
        Skor3.setBackground(Color.GREEN);
        Skor3.setForeground(Color.WHITE);
        TotalSkor.setText("0");
        ParameterSkor.setText("Beresiko rendah, ulangi setiap 7 jam");
        cmbSkor1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            cmbSkor1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Skor1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
            cmbSkor2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Skor2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());  
            cmbSkor3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Skor3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());  
            TotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());  
            ParameterSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());  
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,600));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpemantauan_pews_anak());
        BtnHapus.setEnabled(akses.getpemantauan_pews_anak());
        BtnEdit.setEnabled(akses.getpemantauan_pews_anak());
        BtnPrint.setEnabled(akses.getpemantauan_pews_anak()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    
    }
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    

    private void ganti() {
        isCombo1();
        isCombo2();
        isCombo3();
        isjml();
        isHitung();
        if(Sequel.mengedittf("pemantauan_pews_anak","tanggal=? and no_rawat=?","no_rawat=?,tanggal=?,parameter_perilaku=?,skor_perilaku=?,"+
            "parameter_crt_atau_warna_kulit=?,skor_crt_atau_warna_kulit=?,parameter_perespirasi=?,skor_perespirasi=?,skor_total=?,parameter_total=?,nip=?",13,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            cmbSkor1.getSelectedItem().toString(),Skor1.getText(),cmbSkor2.getSelectedItem().toString(),Skor2.getText(),
            cmbSkor3.getSelectedItem().toString(),Skor3.getText(),TotalSkor.getText(),ParameterSkor.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(cmbSkor1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Skor1.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(cmbSkor2.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Skor2.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(cmbSkor3.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Skor3.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(TotalSkor.getText(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(ParameterSkor.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),16);
            emptTeks();
        }
    }
    
    private void hapus() {
        if(Sequel.queryu2tf("delete from pemantauan_pews_anak where tanggal=? and no_rawat=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
}

