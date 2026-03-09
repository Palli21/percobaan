package rekammedis;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 *
 * @author pallicks 
 */
public class DlgRiwayatRadiologi extends JDialog {

    private final Connection koneksi = koneksiDB.condb();
    private final sekuel Sequel = new sekuel();
    private final validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String no_rkm_medis = "";
    private String formatTanggalIndonesia(String tanggalSql) {
    if (tanggalSql == null || tanggalSql.isEmpty()) {
        return "-";
    }
    try {
        // Tentukan Locale ke Indonesia untuk mendapatkan nama bulan dalam Bahasa Indonesia
        Locale localeIndonesia = new Locale("id", "ID");
        // Pola format yang diinginkan (contoh: 2 September 2025)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", localeIndonesia);
        // Parse tanggal dari string format SQL
        LocalDate tanggal = LocalDate.parse(tanggalSql);
        // Kembalikan tanggal yang sudah diformat
        return tanggal.format(formatter);
    } catch (DateTimeParseException e) {
        System.out.println("Error parsing tanggal: " + tanggalSql + " | " + e.getMessage());
        // Jika gagal, kembalikan tanggal aslinya
        return tanggalSql;
    }
}

    // --- Modern UI Look and Feel ---
    private final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_HEADER_BG = new Color(69, 90, 100); // Blue Grey
    private final Color COLOR_HEADER_FG = Color.WHITE;
    private final Color COLOR_PRIMARY_TEXT = new Color(33, 33, 33);
    private final Color COLOR_SECONDARY_TEXT = new Color(117, 117, 117);
    private final Color COLOR_BORDER = new Color(224, 224, 224);
    private final Color COLOR_ACCENT = new Color(33, 150, 243); // Blue

    private final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 20);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 12);

    // UI Components
    private final JPanel panelContent = new JPanel();
    private JScrollPane scrollPane;
    private final JLabel lblNamaPasien = new JLabel("Nama Pasien: - | No. RM: -");
    private final JButton btnKeluar = new JButton("Keluar");

    /**
     * Creates new form DlgRiwayatRadiologi
     * @param parent
     * @param modal
     */
    public DlgRiwayatRadiologi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        // Basic dialog settings
        setTitle("Riwayat Pemeriksaan Radiologi Pasien");
        setSize(950, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BACKGROUND);

        // Header Panel
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_HEADER_BG);
        panelHeader.setBorder(new EmptyBorder(15, 20, 15, 20));
        lblNamaPasien.setFont(FONT_HEADER);
        lblNamaPasien.setForeground(COLOR_HEADER_FG);
        panelHeader.add(lblNamaPasien, BorderLayout.CENTER);

        // Content Panel
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        panelContent.setBackground(COLOR_BACKGROUND);
        panelContent.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        scrollPane = new JScrollPane(panelContent);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(COLOR_BACKGROUND);

        // Footer Panel
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFooter.setBackground(COLOR_BACKGROUND);
        panelFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        styleModernButton(btnKeluar);
        panelFooter.add(btnKeluar);

        // Add panels to dialog
        add(panelHeader, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);

        // Action Listeners
        btnKeluar.addActionListener(e -> dispose());
    }

    /**
     * Styles a JButton to give it a modern, flat look.
     * @param button The JButton to style.
     */
    private void styleModernButton(JButton button) {
        button.setFont(FONT_TITLE);
        button.setBackground(COLOR_ACCENT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(COLOR_ACCENT.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(COLOR_ACCENT);
            }
        });
    }

    /**
     * Method to set patient data and trigger history display.
     * @param no_rkm_medis Medical record number of the patient.
     */
    public void setNoRm(String no_rkm_medis) {
        this.no_rkm_medis = no_rkm_medis;
        String namaPasien = Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", no_rkm_medis);
        lblNamaPasien.setText("Nama Pasien: " + namaPasien + " | No. RM: " + no_rkm_medis);
        tampilRiwayat();
    }

    /**
     * Fetches and displays the last 5 radiology histories.
     */
    private void tampilRiwayat() {
        panelContent.removeAll();
        panelContent.repaint();
        panelContent.revalidate();
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            String sql = "SELECT pr.tgl_periksa, pr.jam, d.nm_dokter AS dokter_perujuk, hr.hasil, " +
                         "(SELECT GROUP_CONCAT(gr.lokasi_gambar SEPARATOR ';') " +
                         " FROM gambar_radiologi gr WHERE gr.no_rawat = pr.no_rawat " +
                         " AND gr.tgl_periksa = pr.tgl_periksa AND gr.jam = pr.jam) AS daftar_gambar " +
                         "FROM periksa_radiologi pr " +
                         "INNER JOIN reg_periksa rp ON pr.no_rawat = rp.no_rawat " +
                         "INNER JOIN dokter d ON pr.dokter_perujuk = d.kd_dokter " +
                         "LEFT JOIN hasil_radiologi hr ON pr.no_rawat = hr.no_rawat AND pr.tgl_periksa = hr.tgl_periksa AND pr.jam = hr.jam " +
                         "WHERE rp.no_rkm_medis = ? " +
                         "GROUP BY pr.no_rawat, pr.tgl_periksa, pr.jam " +
                         "ORDER BY pr.tgl_periksa DESC, pr.jam DESC LIMIT 5";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, no_rkm_medis);
            rs = ps.executeQuery();

            int count = 1;
            while (rs.next()) {
                JPanel panelRiwayat = createRiwayatPanel(
                    rs.getString("tgl_periksa"),
                    rs.getString("jam"),
                    rs.getString("dokter_perujuk"),
                    rs.getString("hasil"),
                    rs.getString("daftar_gambar"),
                    count
                );
                panelContent.add(panelRiwayat);
                panelContent.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer between cards
                count++;
            }
            
            if (count == 1) {
                JLabel noDataLabel = new JLabel("Tidak ada riwayat pemeriksaan radiologi ditemukan.");
                noDataLabel.setFont(FONT_TITLE);
                noDataLabel.setForeground(COLOR_SECONDARY_TEXT);
                noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                noDataLabel.setBorder(new EmptyBorder(50, 0, 50, 0));
                panelContent.add(noDataLabel);
            }

        } catch (Exception e) {
            System.out.println("Notifikasi Error : " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        
        setCursor(Cursor.getDefaultCursor());
        panelContent.revalidate();
        panelContent.repaint();
    }

    /**
     * Creates a modern "card" panel for a single radiology history entry.
     */
    private JPanel createRiwayatPanel(String tanggal, String jam, String dokterPerujuk, String hasilText, String gambarPaths, int counter) {
        // Main card panel with a subtle border and shadow effect
        RoundedPanel mainPanel = new RoundedPanel(15, COLOR_CARD);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        
        // Card Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_CARD);
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("Pemeriksaan Ke-" + counter + " (" + formatTanggalIndonesia(tanggal)+ " " + jam + ")");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_PRIMARY_TEXT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JLabel doctorLabel = new JLabel("Dokter Perujuk: " + dokterPerujuk);
        doctorLabel.setFont(FONT_LABEL);
        doctorLabel.setForeground(COLOR_SECONDARY_TEXT);
        headerPanel.add(doctorLabel, BorderLayout.EAST);
        
        // Card Body
        JPanel bodyPanel = new JPanel(new BorderLayout(20, 0));
        bodyPanel.setBackground(COLOR_CARD);
        bodyPanel.setBorder(new EmptyBorder(0, 15, 15, 15));

        // Panel for Hasil Pemeriksaan (Expertise)
        JPanel hasilPanel = new JPanel(new BorderLayout());
        hasilPanel.setBackground(COLOR_CARD);
        hasilPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            "Hasil Pemeriksaan (Ekspertise)",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            FONT_BODY, COLOR_SECONDARY_TEXT));
            
        JTextArea areaHasil = new JTextArea(hasilText != null ? hasilText : "Hasil belum diinput.");
        areaHasil.setWrapStyleWord(true);
        areaHasil.setLineWrap(true);
        areaHasil.setEditable(false);
        areaHasil.setFont(FONT_BODY);
        areaHasil.setForeground(COLOR_PRIMARY_TEXT);
        areaHasil.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane scrollHasil = new JScrollPane(areaHasil);
        scrollHasil.setBorder(BorderFactory.createEmptyBorder());
        scrollHasil.setPreferredSize(new Dimension(300, 150));
        hasilPanel.add(scrollHasil, BorderLayout.CENTER);

        // Panel for Photos
        JPanel fotoContainerPanel = new JPanel(new BorderLayout());
        fotoContainerPanel.setBackground(COLOR_CARD);
        fotoContainerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            "Hasil Foto Radiologi (Klik untuk Zoom)",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            FONT_BODY, COLOR_SECONDARY_TEXT));
            
        JPanel panelFoto = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFoto.setBackground(Color.WHITE);
        
        JScrollPane scrollFoto = new JScrollPane(panelFoto);
        scrollFoto.setBorder(BorderFactory.createEmptyBorder());
        scrollFoto.setPreferredSize(new Dimension(100, 280));
        fotoContainerPanel.add(scrollFoto, BorderLayout.CENTER);
        
        // Load images in the background
        if (gambarPaths != null && !gambarPaths.isEmpty()) {
            loadImages(gambarPaths.split(";"), panelFoto);
        } else {
            JLabel noFotoLabel = new JLabel("Tidak ada foto ditemukan.");
            noFotoLabel.setFont(FONT_LABEL);
            noFotoLabel.setForeground(COLOR_SECONDARY_TEXT);
            panelFoto.add(noFotoLabel);
        }
        
        bodyPanel.add(hasilPanel, BorderLayout.CENTER);
        bodyPanel.add(fotoContainerPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        return mainPanel;
    }
    
    /**
     * Loads images from URLs in a background thread to avoid freezing the UI.
     */
    private void loadImages(String[] paths, JPanel containerPanel) {
        SwingWorker<List<RadiologiImage>, Void> worker = new SwingWorker<List<RadiologiImage>, Void>() {
            @Override
            protected List<RadiologiImage> doInBackground() throws Exception {
                List<RadiologiImage> images = new ArrayList<>();
                for (String path : paths) {
                    String trimmedPath = path == null ? "" : path.trim();
                    if (trimmedPath.isEmpty()) {
                        continue;
                    }
                    try {
                        String imageUrl = buildRadiologiImageUrl(trimmedPath);
                        URL url = new URL(imageUrl);
                        BufferedImage originalImage = ImageIO.read(url);
                        if (originalImage != null) {
                            Image thumbnail = createThumbnail(originalImage, 250, 250);
                            images.add(new RadiologiImage(originalImage, new ImageIcon(thumbnail), trimmedPath));
                        }
                    } catch (IOException e) {
                        System.out.println("Gagal memuat gambar: " + trimmedPath + " | Error: " + e);
                    }
                }
                return images;
            }

            @Override
            protected void done() {
                try {
                    List<RadiologiImage> imageItems = get();
                    if (imageItems.isEmpty()) {
                        containerPanel.add(new JLabel("Gagal memuat foto."));
                    } else {
                        for (RadiologiImage imageItem : imageItems) {
                            JLabel imageLabel = new JLabel(imageItem.thumbnail);
                            imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            imageLabel.setToolTipText("Klik untuk memperbesar gambar");
                            imageLabel.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
                            
                            imageLabel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    displayZoomedImage(imageItem);
                                }
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    imageLabel.setBorder(BorderFactory.createLineBorder(COLOR_ACCENT, 2));
                                }
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    imageLabel.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
                                }
                            });
                            containerPanel.add(imageLabel);
                        }
                    }
                    containerPanel.revalidate();
                    containerPanel.repaint();
                } catch (Exception e) {
                    containerPanel.add(new JLabel("Terjadi kesalahan saat menampilkan foto."));
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private String buildRadiologiImageUrl(String path) {
        return "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/radiologi/" + path;
    }

    private Image createThumbnail(BufferedImage image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width <= 0 || height <= 0) {
            return image;
        }
        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
        int newWidth = Math.max(1, (int) Math.round(width * scale));
        int newHeight = Math.max(1, (int) Math.round(height * scale));
        BufferedImage thumbnail = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return thumbnail;
    }

    /**
     * Displays a single image in a new resizable dialog for zooming.
     */
    private void displayZoomedImage(RadiologiImage imageItem) {
        if (imageItem == null || imageItem.originalImage == null) {
            JOptionPane.showMessageDialog(this, "Gambar tidak tersedia.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog zoomDialog = new JDialog(this, "Tampilan Foto", true);
        zoomDialog.setSize(1000, 720);
        zoomDialog.setLocationRelativeTo(this);
        zoomDialog.setLayout(new BorderLayout(10, 10));

        ImageZoomPanel imagePanel = new ImageZoomPanel(imageItem.originalImage);
        JScrollPane imageScrollPane = new JScrollPane(imagePanel);
        imageScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        imageScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        zoomDialog.add(imageScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JLabel zoomLabel = new JLabel("100%");
        JSlider zoomSlider = new JSlider(10, 400, 100);
        zoomSlider.setPreferredSize(new Dimension(200, zoomSlider.getPreferredSize().height));
        JButton btnZoomOut = new JButton("-");
        JButton btnZoomIn = new JButton("+");
        JButton btnZoomReset = new JButton("100%");
        JButton btnZoomFit = new JButton("Fit");
        JButton btnDownload = new JButton("Download");

        controlPanel.add(new JLabel("Zoom"));
        controlPanel.add(btnZoomOut);
        controlPanel.add(btnZoomIn);
        controlPanel.add(zoomSlider);
        controlPanel.add(zoomLabel);
        controlPanel.add(btnZoomReset);
        controlPanel.add(btnZoomFit);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(btnDownload);
        zoomDialog.add(controlPanel, BorderLayout.NORTH);

        zoomSlider.addChangeListener(e -> {
            int zoomValue = zoomSlider.getValue();
            imagePanel.setZoom(zoomValue / 100.0);
            zoomLabel.setText(zoomValue + "%");
        });
        btnZoomOut.addActionListener(e -> zoomSlider.setValue(Math.max(zoomSlider.getMinimum(), zoomSlider.getValue() - 10)));
        btnZoomIn.addActionListener(e -> zoomSlider.setValue(Math.min(zoomSlider.getMaximum(), zoomSlider.getValue() + 10)));
        btnZoomReset.addActionListener(e -> zoomSlider.setValue(100));
        btnZoomFit.addActionListener(e -> zoomSlider.setValue(calculateFitZoomPercent(imageItem.originalImage, imageScrollPane)));
        btnDownload.addActionListener(e -> saveImageToFile(imageItem.originalImage, getFileNameFromPath(imageItem.sourcePath)));

        zoomDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                zoomSlider.setValue(calculateFitZoomPercent(imageItem.originalImage, imageScrollPane));
            }
        });

        zoomDialog.setVisible(true);
    }

    private int calculateFitZoomPercent(BufferedImage image, JScrollPane scrollPane) {
        if (image == null || scrollPane == null) {
            return 100;
        }
        Dimension viewport = scrollPane.getViewport().getExtentSize();
        if (viewport.width <= 0 || viewport.height <= 0) {
            return 100;
        }
        double scaleX = (double) viewport.width / image.getWidth();
        double scaleY = (double) viewport.height / image.getHeight();
        double scale = Math.min(scaleX, scaleY);
        int percent = (int) Math.round(scale * 100);
        return Math.max(10, Math.min(400, percent));
    }

    private void saveImageToFile(BufferedImage image, String suggestedFileName) {
        if (image == null) {
            JOptionPane.showMessageDialog(this, "Gambar tidak tersedia.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan Gambar Radiologi");
        if (suggestedFileName != null && !suggestedFileName.trim().isEmpty()) {
            chooser.setSelectedFile(new File(suggestedFileName));
        }
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        String format = getImageFormatFromName(file.getName());
        if (!file.getName().toLowerCase(Locale.ROOT).endsWith("." + format)) {
            file = new File(file.getParentFile(), file.getName() + "." + format);
        }
        try {
            ImageIO.write(image, format, file);
            JOptionPane.showMessageDialog(this, "Gambar tersimpan di:\n" + file.getAbsolutePath(), "Simpan Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan gambar:\n" + e.getMessage(), "Simpan Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getFileNameFromPath(String path) {
        if (path == null) {
            return "radiologi";
        }
        String normalized = path.trim().replace("\\", "/");
        if (normalized.isEmpty()) {
            return "radiologi";
        }
        int slashIndex = normalized.lastIndexOf('/');
        String fileName = slashIndex >= 0 ? normalized.substring(slashIndex + 1) : normalized;
        int queryIndex = fileName.indexOf('?');
        if (queryIndex >= 0) {
            fileName = fileName.substring(0, queryIndex);
        }
        int hashIndex = fileName.indexOf('#');
        if (hashIndex >= 0) {
            fileName = fileName.substring(0, hashIndex);
        }
        return fileName.isEmpty() ? "radiologi" : fileName;
    }

    private String getImageFormatFromName(String fileName) {
        if (fileName == null) {
            return "png";
        }
        String lower = fileName.toLowerCase(Locale.ROOT);
        int dotIndex = lower.lastIndexOf('.');
        if (dotIndex < 0) {
            return "png";
        }
        String ext = lower.substring(dotIndex + 1);
        if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("bmp") || ext.equals("gif")) {
            return ext;
        }
        return "png";
    }

    private static class RadiologiImage {
        private final BufferedImage originalImage;
        private final ImageIcon thumbnail;
        private final String sourcePath;

        private RadiologiImage(BufferedImage originalImage, ImageIcon thumbnail, String sourcePath) {
            this.originalImage = originalImage;
            this.thumbnail = thumbnail;
            this.sourcePath = sourcePath;
        }
    }

    private static class ImageZoomPanel extends JPanel {
        private final BufferedImage image;
        private double zoom = 1.0;

        private ImageZoomPanel(BufferedImage image) {
            this.image = image;
            setBackground(Color.WHITE);
        }

        private void setZoom(double zoom) {
            this.zoom = Math.max(0.05, zoom);
            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            if (image == null) {
                return new Dimension(1, 1);
            }
            int width = (int) Math.round(image.getWidth() * zoom);
            int height = (int) Math.round(image.getHeight() * zoom);
            return new Dimension(Math.max(1, width), Math.max(1, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image == null) {
                return;
            }
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int width = (int) Math.round(image.getWidth() * zoom);
            int height = (int) Math.round(image.getHeight() * zoom);
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
        }
    }
    
    /**
     * A custom JPanel with rounded corners.
     */
    class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius = 15;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draws the rounded panel with borders.
            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); //paint background
            graphics.setColor(getForeground());
            //graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); //paint border
        }
    }
     
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Flatlaf Light".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DlgRiwayatRadiologi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            DlgRiwayatRadiologi dialog = new DlgRiwayatRadiologi(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            // Example usage:
            dialog.setNoRm("000001"); // Replace with a valid Medical Record number from your DB for testing
            dialog.setVisible(true);
        });
    }
}
