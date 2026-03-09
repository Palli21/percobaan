package rekammedis;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import fungsi.WarnaRiwayatSoap;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen (Modernized by Gemini with FlatLaf)
 */
public final class RMCari5SOAPTerakhir extends JDialog {
    private final DefaultTableModel tabMode;
    private final validasi Valid = new validasi();
    private final Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String norm = "", nip = "";
    
    // UI Components
    private JTable tbKamar;
    private JComboBox<String> Status;
    private JTextField TCari;
    private JButton BtnCari, BtnAll, BtnKeluar;
    private JLabel LCount;

    public RMCari5SOAPTerakhir(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row = {"Tanggal", "Jam", "Subjek", "Objek", "Asesmen", "Plan", "Instruksi", "Evaluasi", "Kode PPA", "Nama PPA"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKamar.setModel(tabMode);
        aturTabel();
    }
    
    private void aturTabel() {
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int z = 0; z < 10; z++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(z);
            if (z == 0) column.setPreferredWidth(75);
            else if (z == 1) column.setPreferredWidth(60);
            else if (z == 2) column.setPreferredWidth(250);
            else if (z == 3) column.setPreferredWidth(250);
            else if (z == 4) column.setPreferredWidth(250);
            else if (z == 5) column.setPreferredWidth(250);
            else if (z == 6) column.setPreferredWidth(250);
            else if (z == 7) column.setPreferredWidth(250);
            else if (z == 8) { column.setMinWidth(0); column.setMaxWidth(0); } // Sembunyikan Kode PPA
            else if (z == 9) column.setPreferredWidth(180);
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaRiwayatSoap());
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Riwayat 15 SOAP Terakhir Pasien");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Panel Atas untuk Instruksi dan Filter
        JPanel panelAtas = new JPanel(new BorderLayout(10, 0));
        
        // Panel Instruksi
        JPanel panelInstruksi = new JPanel(new BorderLayout());
        panelInstruksi.setBorder(new TitledBorder("Petunjuk Penggunaan"));
        JLabel labelInstruksi = new JLabel(
            "<html>Klik pada baris SOAP yang ingin digunakan, lalu tekan <b>Spasi</b> pada keyboard untuk memilih.</html>"
        );
        labelInstruksi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelInstruksi.setBorder(new EmptyBorder(5, 10, 10, 10));
        panelInstruksi.add(labelInstruksi);
        
        // Panel Filter
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelFilter.setBorder(new TitledBorder("Filter Data SOAP"));
        
        panelFilter.add(new JLabel("Status Perawatan:"));
        Status = new JComboBox<>(new String[]{"Rawat Jalan", "Rawat Inap"});
        Status.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        Status.setPreferredSize(new Dimension(130, 28));
        panelFilter.add(Status);
        
        panelFilter.add(new JLabel("   Kata Kunci:"));
        TCari = new JTextField();
        TCari.setPreferredSize(new Dimension(250, 28));
        TCari.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        addPlaceholder(TCari, "Cari Subjek atau Objek...");
        panelFilter.add(TCari);
        
        BtnCari = new JButton("Cari");
        BtnCari.setFont(new Font("Segoe UI", Font.BOLD, 13));
        BtnCari.putClientProperty("JButton.buttonType", "roundRect");
        BtnCari.putClientProperty("JButton.style", "primary"); // Tombol utama
        
        BtnAll = new JButton("Tampilkan Semua");
        BtnAll.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        BtnAll.putClientProperty("JButton.buttonType", "roundRect");
        
        panelFilter.add(BtnCari);
        panelFilter.add(BtnAll);
        
        panelAtas.add(panelInstruksi, BorderLayout.CENTER);
        panelAtas.add(panelFilter, BorderLayout.EAST);
        contentPane.add(panelAtas, BorderLayout.NORTH);

        // Panel Tengah untuk Tabel
        JScrollPane scrollPane = new JScrollPane();
        tbKamar = new JTable();
        tbKamar.setRowHeight(30);
        tbKamar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tbKamar.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tbKamar.setShowGrid(true);
        tbKamar.setGridColor(new Color(230, 230, 230));
        scrollPane.setViewportView(tbKamar);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel Bawah untuk Info dan Tombol Keluar
        JPanel panelBawah = new JPanel(new BorderLayout());
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelInfo.add(new JLabel("Jumlah Record:"));
        LCount = new JLabel("0");
        LCount.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelInfo.add(LCount);
        
        JPanel panelKeluar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        BtnKeluar = new JButton("Tutup");
        BtnKeluar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        BtnKeluar.putClientProperty("JButton.buttonType", "roundRect");
        panelKeluar.add(BtnKeluar);
        
        panelBawah.add(panelInfo, BorderLayout.WEST);
        panelBawah.add(panelKeluar, BorderLayout.EAST);
        contentPane.add(panelBawah, BorderLayout.SOUTH);

        // Event Listeners
        setupEventListeners();
    }
    
    private void setupEventListeners() {
        TCari.setDocument(new batasInput(100).getKata(TCari));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
                @Override public void removeUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
                @Override public void changedUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
            });
        }
        
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) tampil();
            }
        });
        
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (tabMode.getRowCount() != 0) {
                    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                        dispose();
                    } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                }
            }
        });
        
        BtnCari.addActionListener(e -> tampil());
        BtnAll.addActionListener(e -> {
            TCari.setText("");
            addPlaceholder(TCari, "Cari Subjek atau Objek..."); // Kembalikan placeholder
            tampil();
        });
        BtnKeluar.addActionListener(e -> dispose());
        Status.addActionListener(e -> tampil());
    }
    
    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        String query;
        String keyword = "%" + (TCari.getText().equals("Cari Subjek atau Objek...") ? "" : TCari.getText().trim()) + "%";
        
        if (Status.getSelectedIndex() == 0) { // Ralan
            query = "SELECT pr.tgl_perawatan, pr.jam_rawat, pr.keluhan, pr.pemeriksaan, " +
                    "pr.penilaian, pr.rtl, pr.instruksi, pr.evaluasi, pr.nip, COALESCE(p.nama, pr.nip) as nama_ppa " +
                    "FROM pemeriksaan_ralan pr " +
                    "INNER JOIN reg_periksa rp ON pr.no_rawat = rp.no_rawat " +
                    "LEFT JOIN pegawai p ON p.nik = pr.nip " +
                    "WHERE rp.no_rkm_medis = ? AND (pr.keluhan LIKE ? OR pr.pemeriksaan LIKE ?) " +
                    "ORDER BY pr.tgl_perawatan DESC, pr.jam_rawat DESC LIMIT 15";
        } else { // Ranap
            query = "SELECT pr.tgl_perawatan, pr.jam_rawat, pr.keluhan, pr.pemeriksaan, " +
                    "pr.penilaian, pr.rtl, pr.instruksi, pr.evaluasi, pr.nip, COALESCE(p.nama, pr.nip) as nama_ppa " +
                    "FROM pemeriksaan_ranap pr " +
                    "INNER JOIN reg_periksa rp ON pr.no_rawat = rp.no_rawat " +
                    "LEFT JOIN pegawai p ON p.nik = pr.nip " +
                    "WHERE rp.no_rkm_medis = ? AND (pr.keluhan LIKE ? OR pr.pemeriksaan LIKE ?) " +
                    "ORDER BY pr.tgl_perawatan DESC, pr.jam_rawat DESC LIMIT 15";
        }

        try {
            ps = koneksi.prepareStatement(query);
            ps.setString(1, norm);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new String[]{
                    rs.getString("tgl_perawatan"), rs.getString("jam_rawat"), rs.getString("keluhan"), 
                    rs.getString("pemeriksaan"), rs.getString("penilaian"), rs.getString("rtl"), 
                    rs.getString("instruksi"), rs.getString("evaluasi"), rs.getString("nip"), 
                    rs.getString("nama_ppa")
                });
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }
        LCount.setText(String.valueOf(tabMode.getRowCount()));
    }

    public void setNoRM(String norm, String nip, String status) {
        this.norm = norm;
        this.nip = nip;
        Status.setSelectedItem(status.equals("Ralan") ? "Rawat Jalan" : "Rawat Inap");
        tampil();
        TCari.requestFocusInWindow();
    }

    public JTable getTable() {
        return tbKamar;
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Gagal menginisialisasi LaF");
        }

        java.awt.EventQueue.invokeLater(() -> {
            RMCari5SOAPTerakhir dialog = new RMCari5SOAPTerakhir(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
}

