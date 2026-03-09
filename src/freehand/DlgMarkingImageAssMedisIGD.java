/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package freehand;

import fungsi.koneksiDB;
import fungsi.sekuel;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.SwingUtilities;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author perpustakaan
 */
public class DlgMarkingImageAssMedisIGD extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private String urlImage = "";
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    // ==== Tambahan untuk drawing & export yang stabil ====
    private java.awt.image.BufferedImage overlay;     // kanvas coretan (transparan)
    private java.awt.image.BufferedImage lastBgImage; // background lokalis terakhir
    private javax.swing.JComponent overlayComp;       // komponen transparan untuk melukis overlay
    private java.awt.Point lastPt;                    // posisi drag terakhir
    private javax.swing.JLabel bgLabel;              // background image holder
    


    /**
     * Creates new form DlgPemberianObat
     *
     * @param parent
     * @param modal
     */
    public DlgMarkingImageAssMedisIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        PanelWall.setLayout(null);

        bgLabel = new javax.swing.JLabel();
        bgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bgLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        PanelWall.add(bgLabel);

        overlayComp = new javax.swing.JComponent() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (overlay != null) {
                    g.drawImage(overlay, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        overlayComp.setOpaque(false);
        PanelWall.add(overlayComp);
        PanelWall.setComponentZOrder(overlayComp, 0);
        PanelWall.setComponentZOrder(bgLabel, 1);

        syncOverlayBounds(); // initial sizing

        PanelWall.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                syncOverlayBounds();
            }
        });

        overlayComp.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                lastPt = e.getPoint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                lastPt = null;
            }
        });
        overlayComp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                ensureOverlay();
                if (overlay == null) {
                    return;
                }
                if (lastPt == null) {
                    lastPt = e.getPoint();
                    return;
                }

                java.awt.Graphics2D g2 = overlay.createGraphics();
                try {
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setStroke(new java.awt.BasicStroke(3f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));
                    g2.setColor(java.awt.Color.RED);
                    g2.drawLine(lastPt.x, lastPt.y, e.getX(), e.getY());
                } finally {
                    g2.dispose();
                }
                lastPt = e.getPoint();
                overlayComp.repaint();
            }
        });

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width, screenSize.height);
        //setSize(800, 400);
        setResizable(false);
        this.setLocation(0, 0);
//        setSize(885,674); 

        SwingUtilities.invokeLater(this::syncOverlayBounds);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRawat = new widget.TextBox();
        panelGlass9 = new widget.panelisi();
        PanelWall = new usu.widget.glass.PanelGlass();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnHapus1 = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Marking Lokalis Bedah Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 60));
        FormInput.setLayout(null);

        jLabel3.setText("No. Rawat");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 65, 23);

        TNoRawat.setEditable(false);
        TNoRawat.setHighlighter(null);
        TNoRawat.setName("TNoRawat"); // NOI18N
        FormInput.add(TNoRawat);
        TNoRawat.setBounds(70, 10, 470, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);
        FormInput.getAccessibleContext().setAccessibleName("");
        FormInput.getAccessibleContext().setAccessibleDescription("");

        panelGlass9.setBorder(null);
        panelGlass9.setAlignmentX(0.0F);
        panelGlass9.setAlignmentY(0.0F);
        panelGlass9.setMinimumSize(new java.awt.Dimension(0, 0));
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass9.setLayout(new java.awt.BorderLayout());

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(878, 556));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        panelGlass9.add(PanelWall, java.awt.BorderLayout.CENTER);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(100, 56));
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus Marking");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(150, 30));
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

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnHapus1.setMnemonic('H');
        BtnHapus1.setText("Gambar Baru");
        BtnHapus1.setToolTipText("Alt+H");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        BtnHapus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapus1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus1);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar.setMnemonic('T');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+T");
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        simpanMarkingLokalis(); // jalankan versi aman (tanpa Robot) di background

}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed

}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (overlay != null) {
            java.awt.Graphics2D g2 = overlay.createGraphics();
            try {
                g2.setComposite(java.awt.AlphaComposite.Clear);
                g2.fillRect(0, 0, overlay.getWidth(), overlay.getHeight());
            } finally {
                g2.dispose();
            }
            overlayComp.repaint();
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed

    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapus1ActionPerformed
        clearOverlay();
        imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/masterimage/asesmen_medis_igd.png");
    }//GEN-LAST:event_BtnHapus1ActionPerformed

    private void BtnHapus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapus1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapus1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgMarkingImageAssMedisIGD dialog = new DlgMarkingImageAssMedisIGD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.TextBox TNoRawat;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    // End of variables declaration//GEN-END:variables

    private void isPsien() {
//        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    // Membuat ulang kanvas overlay sesuai ukuran PanelWall
    private void ensureOverlay() {
        int w = Math.max(1, PanelWall.getWidth());
        int h = Math.max(1, PanelWall.getHeight());
        if (overlay == null || overlay.getWidth() != w || overlay.getHeight() != h) {
            java.awt.image.BufferedImage newBuf = new java.awt.image.BufferedImage(
                    w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            if (overlay != null) {
                java.awt.Graphics2D g2 = newBuf.createGraphics();
                try {
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                            java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(overlay, 0, 0, w, h, null);
                } finally {
                    g2.dispose();
                }
            }
            overlay = newBuf;
        }
    }


    public void setNoRw(String norw) {
        TNoRawat.setText(norw);
        clearOverlay();
        urlImage = Sequel.cariIsi("select url_image from asesmen_medis_igd_image_marking where no_rawat=?", norw);

        if (urlImage == null || urlImage.trim().isEmpty()) {
            imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/masterimage/asesmen_medis_igd.png");
        } else {
            imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/" + urlImage.trim());
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(true);

    }

    void uploadImage(String FileName, String docpath) {
        try {
            File file = new File("tmpImageFreehand/" + FileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/upload.php?doc=" + docpath);
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
//        HttpResponse response = (HttpResponse) httpClient.execute(postRequest); 
            deleteFile();

        } catch (Exception e) {
            System.out.println("Upload error" + e);
        }
    }

    void deleteFile() {
        File file = new File("tmpImageFreehand");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            if (myFiles == null) {
                return;
            }
            for (String name : myFiles) {
                File myFile = new File(file, name);
                myFile.delete();
            }
        }
    }

    void imageAssesment(String url) {
        try {
            System.out.println("[imageAssesment] URL = " + url);
            if (url == null || url.trim().isEmpty()) {
                lastBgImage = null;
                bgLabel.setIcon(null);
                bgLabel.setText("Gambar tidak tersedia");
                return;
            }

            java.net.URL u = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(7000);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("User-Agent", "SIMRS-RS2024/1.0");
            try {
                int code = conn.getResponseCode();
                System.out.println("[imageAssesment] HTTP " + code + " " + conn.getResponseMessage());
                if (code != 200) {
                    bgLabel.setIcon(null);
                    bgLabel.setText("HTTP " + code + " - gagal memuat");
                    return;
                }

                try (java.io.InputStream in = conn.getInputStream()) {
                    java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(in);
                    if (img == null) {
                        bgLabel.setIcon(null);
                        bgLabel.setText("ImageIO.read() null");
                        return;
                    }

                    lastBgImage = img;
                    bgLabel.setText(null);
                    syncOverlayBounds(); // jaga ukuran overlay & scale background
                }
            } finally {
                conn.disconnect();
            }

        } catch (Exception ex) {
            System.err.println("[imageAssesment] " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            bgLabel.setIcon(null);
            bgLabel.setText("Gagal memuat gambar");
        }
    }


    private void simpanMarkingLokalis() {
        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        final String noRawat = TNoRawat.getText();
        final String fname = "assMdsIGD" + noRawat.replace("/", "") + ".png";
        final String subdir = "assesmentmedisigd/imagemarking"; // konsisten dengan uploadImage()
        final String urlPath = subdir + "/" + fname;

        new javax.swing.SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // 1) Siapkan kanvas hasil dengan ukuran PanelWall
                int w = Math.max(1, PanelWall.getWidth());
                int h = Math.max(1, PanelWall.getHeight());
                java.awt.image.BufferedImage out = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                java.awt.Graphics2D g2 = out.createGraphics();
                try {
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                    // 2) Gambar background lokalis (kalau ada)
                    if (lastBgImage != null) {
                        g2.drawImage(lastBgImage, 0, 0, w, h, null);
                    } else {
                        // fallback: render PanelWall apa adanya
                        PanelWall.printAll(g2);
                    }

                    // 3) Gambar overlay coretan di atasnya
                    if (overlay != null) {
                        g2.drawImage(overlay, 0, 0, w, h, null);
                    }
                } finally {
                    g2.dispose();
                }

                // 4) Simpan ke tmp
                java.io.File outDir = new java.io.File("tmpImageFreehand");
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                java.io.File outFile = new java.io.File(outDir, fname);
                javax.imageio.ImageIO.write(out, "png", outFile);

                // 5) Upload ke server (pakai fungsi Anda yang sudah ada)
                uploadImage(fname, subdir);

                // 6) Update/insert DB asesmen_medis_igd_image_marking
                String tgl = tanggalNow.format(new java.util.Date());
                String jam = jamNow.format(new java.util.Date());
                int ada = Sequel.cariInteger("select count(no_rawat) from asesmen_medis_igd_image_marking where no_rawat=?", noRawat);
                if (ada > 0) {
                    Sequel.mengedittf("asesmen_medis_igd_image_marking", "no_rawat=?",
                            "tanggal=?,jam=?,url_image=?", 4,
                            new String[]{tgl, jam, urlPath, noRawat});
                } else {
                    Sequel.menyimpantf("asesmen_medis_igd_image_marking", "?,?,?,?", "No.Rawat", 4,
                            new String[]{noRawat, tgl, jam, urlPath});
                }
                return null;
            }

            @Override
            protected void done() {
                setCursor(java.awt.Cursor.getDefaultCursor());
                dispose(); // tutup dialog setelah semua selesai
            }
        }.execute();
    }

    private void clearOverlay() {
        if (overlay != null) {
            java.awt.Graphics2D g2 = overlay.createGraphics();
            g2.setComposite(java.awt.AlphaComposite.Clear);
            g2.fillRect(0, 0, overlay.getWidth(), overlay.getHeight());
            g2.dispose();
            overlayComp.repaint();
        }
    }

    private void syncOverlayBounds() {
        int w = PanelWall.getWidth();
        int h = PanelWall.getHeight();
        if (w <= 0 || h <= 0) {
            w = Math.max(1, PanelWall.getPreferredSize().width);
            h = Math.max(1, PanelWall.getPreferredSize().height);
        }
        bgLabel.setBounds(0, 0, w, h);
        overlayComp.setBounds(0, 0, w, h);
        refreshBackgroundIcon(w, h);
        ensureOverlay();
        overlayComp.revalidate();
        overlayComp.repaint();
    }

    // Scale background image agar memenuhi panel dan tetap center
    private void refreshBackgroundIcon(int w, int h) {
        if (lastBgImage == null || w <= 0 || h <= 0) {
            return;
        }
        java.awt.image.BufferedImage scaled = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = scaled.createGraphics();
        try {
            g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            double sx = (double) w / lastBgImage.getWidth();
            double sy = (double) h / lastBgImage.getHeight();
            double scale = Math.max(sx, sy); // cover area supaya penuh seperti contoh
            int sw = (int) Math.round(lastBgImage.getWidth() * scale);
            int sh = (int) Math.round(lastBgImage.getHeight() * scale);
            int x = (w - sw) / 2;
            int y = (h - sh) / 2;
            g2.drawImage(lastBgImage, x, y, sw, sh, null);
        } finally {
            g2.dispose();
        }
        bgLabel.setIcon(new javax.swing.ImageIcon(scaled));
    }


}
