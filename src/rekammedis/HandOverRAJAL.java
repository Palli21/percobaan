/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import laporan.DlgBerkasRawat;
import java.text.SimpleDateFormat;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class HandOverRAJAL extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
//    private int i=0;
    private int i=0,jml=0,index=0;
//    private int x = 0, i = 0, pilihan = 0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");

    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public HandOverRAJAL(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Rawat","Status","No.Rawat","No.RM","Nama Pasien","NIP","Nama Pemeriksa","Tanggal SOAP","Jam SOAP","S (SUBJECTIVE)","O (OBJECTIVE)",
            "A (ASSESSMENT)","P (PLAN)","Shift"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(250);
            }else if(i==10){
                column.setPreferredWidth(250);
            }else if(i==11){
                column.setPreferredWidth(250);
            }else if(i==12){
                column.setPreferredWidth(170);
            }else if(i==13){
                column.setPreferredWidth(170);    
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
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
                    if(i==1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());  
                    }else{
                        KdPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());  
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
        
        ChkInput.setSelected(false);
        isForm();
      
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
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
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        TPenilaian = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
        label15 = new widget.Label();
        KodeNik1 = new widget.TextBox();
        NamaPegawai1 = new widget.TextBox();
        TanggalPemeriksaan = new widget.TextBox();
        label16 = new widget.Label();
        label17 = new widget.Label();
        JamPemeriksaan = new widget.TextBox();
        shift = new widget.ComboBox();
        label18 = new widget.Label();
        label19 = new widget.Label();
        BtnPetugas = new widget.Button();
        NmPetugas = new widget.TextBox();
        KdPetugas = new widget.TextBox();
        label14 = new widget.Label();
        label21 = new widget.Label();
        KdPetugas2 = new widget.TextBox();
        NmPetugas2 = new widget.TextBox();
        BtnPetugas2 = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Validasi HandOver]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/checked.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Diterima");
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bawahkaca.png"))); // NOI18N
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 330));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        scrollInput.setName("scrollInput"); // NOI18N

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 310));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(361, 10, 424, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 10, 112, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("S (SUBJECTIVE)"));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TKeluhan.setEditable(false);
        TKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeluhan.setColumns(20);
        TKeluhan.setRows(5);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKeluhan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(30, 130, 360, 80);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("O (OBJECTIVE)"));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TPemeriksaan.setEditable(false);
        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TPemeriksaan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(30, 220, 360, 80);

        scrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("A (ASSESSMENT)"));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TPenilaian.setEditable(false);
        TPenilaian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPenilaian.setColumns(20);
        TPenilaian.setRows(5);
        TPenilaian.setName("TPenilaian"); // NOI18N
        TPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenilaianKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TPenilaian);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(400, 130, 360, 80);

        scrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("P (PLAN)"));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TindakLanjut.setEditable(false);
        TindakLanjut.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakLanjut.setColumns(20);
        TindakLanjut.setRows(5);
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TindakLanjut);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(400, 220, 360, 80);

        label15.setText("Pemeriksa :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(500, 40, 90, 23);

        KodeNik1.setEditable(false);
        KodeNik1.setName("KodeNik1"); // NOI18N
        KodeNik1.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeNik1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KodeNik1ActionPerformed(evt);
            }
        });
        KodeNik1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeNik1KeyPressed(evt);
            }
        });
        FormInput.add(KodeNik1);
        KodeNik1.setBounds(600, 40, 141, 23);

        NamaPegawai1.setEditable(false);
        NamaPegawai1.setName("NamaPegawai1"); // NOI18N
        NamaPegawai1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaPegawai1);
        NamaPegawai1.setBounds(750, 40, 270, 23);

        TanggalPemeriksaan.setEditable(false);
        TanggalPemeriksaan.setName("TanggalPemeriksaan"); // NOI18N
        TanggalPemeriksaan.setPreferredSize(new java.awt.Dimension(80, 23));
        TanggalPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPemeriksaanActionPerformed(evt);
            }
        });
        TanggalPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPemeriksaan);
        TanggalPemeriksaan.setBounds(150, 100, 141, 23);

        label16.setText("Tanggal Terima:");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(10, 100, 130, 23);

        label17.setText("Shift :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(560, 100, 50, 23);

        JamPemeriksaan.setEditable(false);
        JamPemeriksaan.setName("JamPemeriksaan"); // NOI18N
        JamPemeriksaan.setPreferredSize(new java.awt.Dimension(80, 23));
        JamPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JamPemeriksaanActionPerformed(evt);
            }
        });
        JamPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(JamPemeriksaan);
        JamPemeriksaan.setBounds(410, 100, 141, 23);

        shift.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Malam" }));
        shift.setToolTipText("");
        shift.setName("shift"); // NOI18N
        shift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shiftActionPerformed(evt);
            }
        });
        FormInput.add(shift);
        shift.setBounds(610, 100, 80, 20);

        label18.setText("Jam Terima:");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(310, 100, 100, 23);

        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(0, 40, 100, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/20paper.png"))); // NOI18N
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
        BtnPetugas.setBounds(450, 40, 28, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(240, 40, 210, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(130, 40, 100, 23);

        label14.setText("Yang Menyerahkan : ");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(20, 40, 110, 23);

        label21.setText("Yang Menerima : ");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(30, 70, 100, 23);

        KdPetugas2.setEditable(false);
        KdPetugas2.setName("KdPetugas2"); // NOI18N
        KdPetugas2.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas2);
        KdPetugas2.setBounds(130, 70, 100, 23);

        NmPetugas2.setEditable(false);
        NmPetugas2.setName("NmPetugas2"); // NOI18N
        NmPetugas2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas2);
        NmPetugas2.setBounds(230, 70, 210, 23);

        BtnPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/20paper.png"))); // NOI18N
        BtnPetugas2.setMnemonic('2');
        BtnPetugas2.setToolTipText("Alt+2");
        BtnPetugas2.setName("BtnPetugas2"); // NOI18N
        BtnPetugas2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugas2ActionPerformed(evt);
            }
        });
        BtnPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas2);
        BtnPetugas2.setBounds(450, 70, 28, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,BtnPetugas);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Yang Menyerahkan");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Yang Menerima");
         }else if(KodeNik1.getText().equals("")||NamaPegawai1.getText().equals("")){
//            Valid.textKosong(BtnPegawai,"Nama Pemeriksa");
        }else if(TKeluhan.getText().equals("")){
            Valid.textKosong(TKeluhan,"S (SUBJECTIVE");
        }else if(TPemeriksaan.getText().equals("")){
            Valid.textKosong(TPemeriksaan,"O (OBJECTIVE)");
        }else{
            if(Sequel.menyimpantf("validasi_handover_rajal","?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",14,new String[]{
                    TNoRw.getText(),TanggalPemeriksaan.getText(),JamPemeriksaan.getText(),TKeluhan.getText(),TPemeriksaan.getText(),TPenilaian.getText(),TindakLanjut.getText(), 
                    KodeNik1.getText(),KdPetugas.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),"Validasi",shift.getSelectedItem().toString(),KdPetugas2.getText()
                })==true){
                    tampil();
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TKeluhan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnBatal);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnBatal,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    ChkInput.setSelected(true);
                    isForm(); 
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeluhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKeluhanKeyPressed

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPemeriksaanKeyPressed

    private void TPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenilaianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPenilaianKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void KodeNik1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KodeNik1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeNik1ActionPerformed

    private void KodeNik1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeNik1KeyPressed
        Valid.pindah(evt,TCari,TKeluhan);
    }//GEN-LAST:event_KodeNik1KeyPressed

    private void TanggalPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPemeriksaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPemeriksaanActionPerformed

    private void TanggalPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPemeriksaanKeyPressed

    private void JamPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JamPemeriksaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamPemeriksaanActionPerformed

    private void JamPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamPemeriksaanKeyPressed

    private void shiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shiftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shiftActionPerformed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        i=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnSimpan,BtnPetugas2);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed

    }//GEN-LAST:event_KdPetugasKeyPressed

    private void KdPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas2KeyPressed

    private void BtnPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugas2ActionPerformed
        i=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugas2ActionPerformed

    private void BtnPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugas2KeyPressed
//        Valid.pindah(evt,BtnPetugas,BtnDPJP);
    }//GEN-LAST:event_BtnPetugas2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            HandOverRAJAL dialog = new HandOverRAJAL(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JamPemeriksaan;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas2;
    private widget.TextBox KodeNik1;
    private widget.Label LCount;
    private widget.TextBox NamaPegawai1;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas2;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextArea TKeluhan;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TPemeriksaan;
    private widget.TextArea TPenilaian;
    private widget.TextBox TanggalPemeriksaan;
    private widget.TextArea TindakLanjut;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label21;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ComboBox shift;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.status_lanjut,pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.keluhan, " +
                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.rtl,pemeriksaan_ralan.nip,pegawai.nama from pasien inner join reg_periksa inner join pemeriksaan_ranap "+
                    "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik LEFT JOIN validasi_handover ON validasi_handover.no_rawat = pemeriksaan_ralan.no_rawat AND validasi_handover.tgl_perawatan = pemeriksaan_ralan.tgl_perawatan AND validasi_handover.jam_rawat = pemeriksaan_ralan.jam_rawat where "+
                    "pemeriksaan_ralan.no_rawat like ? and ISNULL(validasi_handover.status_validasi) order by pemeriksaan_ralan.no_rawat desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.status_lanjut,pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.keluhan, " +
                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.rtl,pemeriksaan_ralan.nip,pegawai.nama from pasien inner join reg_periksa inner join pemeriksaan_ralan "+
                    "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik LEFT JOIN validasi_handover ON validasi_handover.no_rawat = pemeriksaan_ralan.no_rawat AND validasi_handover.tgl_perawatan = pemeriksaan_ralan.tgl_perawatan AND validasi_handover.jam_rawat = pemeriksaan_ralan.jam_rawat where "+
                    "pemeriksaan_ralan.no_rawat like ? and ISNULL(validasi_handover.status_validasi) order by pemeriksaan_ralan.no_rawat desc");
            }
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                }else{
                    ps.setString(1,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_registrasi"),rs.getString("status_lanjut"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("keluhan"),rs.getString("pemeriksaan"),rs.getString("penilaian"),rs.getString("rtl")
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        KodeNik1.setText("");
        NamaPegawai1.setText("");
        TanggalPemeriksaan.setText("");
        JamPemeriksaan.setText("");
        TKeluhan.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        TindakLanjut.setText("");
        TNoRw.requestFocus();
        KodeNik1.requestFocus();
        TanggalPemeriksaan.requestFocus();
        JamPemeriksaan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());  
            KodeNik1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
            NamaPegawai1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());  
            TanggalPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
            JamPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            TKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());   
            TPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());  
            TPenilaian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TindakLanjut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
        TNoRw.requestFocus();
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-330));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdata_resume_pasien());

//        if(akses.getjml2()>=1){
//            Nik.setEditable(false);
//            BtnPegawai.setEnabled(false);
//            Nik.setText(akses.getkode());
//            Sequel.cariIsi("select nama from petugas where petugas=?", NamaPegawai,Nik.getText());
//            if(NamaPegawai.getText().equals("")){
//                Nik.setText("");
//                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
            }
        }            
//    }

    
//}
