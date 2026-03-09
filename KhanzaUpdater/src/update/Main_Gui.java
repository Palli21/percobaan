
package update;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Thomas Otero (H3R3T1C)
 */
public class Main_Gui extends JFrame{

    private Thread worker;
    private final String root = "update/";

    private JTextArea outText;
    private JButton cancle;
    private JButton launch;
    private JProgressBar progress;
    private JScrollPane sp;
    private JPanel pan1;
    private JPanel pan2;
    private int totalCopyFiles = 0;
    private int copiedFiles = 0;
private final static  Properties propServ = new Properties();
     static String URLSERVER,DEFAULTAPLIKASIRUN;
     public Main_Gui() {
             
        initComponents();
          setLocationRelativeTo(this);
        appendLog("Contacting Download Server...");
        download();
    }
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

       

        pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());

        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        outText = new JTextArea();
        sp = new JScrollPane();
        sp.setViewportView(outText);

        progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        progress.setValue(0);
        progress.setString("Menunggu...");
        
        launch = new JButton("Jalankan Aplikasi");
        launch.setEnabled(false);
        launch.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                launch();
            }
        });
        pan2.add(launch);

        cancle = new JButton("Exit");
        cancle.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        pan2.add(cancle);
        pan1.add(progress, BorderLayout.NORTH);
        pan1.add(sp,BorderLayout.CENTER);
        pan1.add(pan2,BorderLayout.SOUTH);

        add(pan1);
        pack();
        this.setSize(500, 400);
    }

    private void download()
    {
        setProgressIndeterminate("Menyiapkan update...");
        appendLog("Menyiapkan update...");
        worker = new Thread(
        new Runnable(){
            public void run()
            {
                try {
                    setProgressIndeterminate("Menghubungi server...");
                    appendLog("Menghubungi server update...");
                    String link = getDownloadLinkFromHost();
                    setProgressIndeterminate("Mengunduh...");
                    appendLog("Memulai download update...");
                    downloadFile(link);
                    unzip();
                    File copySource = resolveCopySource(new File(root));
                    prepareCopyProgress(copySource);
                    copyFiles(copySource,new File("").getAbsolutePath());
                    setProgressIndeterminate("Membersihkan...");
                    cleanup();
                    updateProgress(100, "Selesai");
                    launch.setEnabled(true);
                    appendLog("Update Finished!");
                    JOptionPane.showMessageDialog(null, "Update selesai, aplikasi akan dijalankan.");
                    launch();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    updateProgress(0, "Gagal");
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat melakukan pembaruan!");
                }
            }
        });
        worker.start();
    }
    private void launch()
    {
         try {
             propServ.loadFromXML(new FileInputStream("settingupdate/config.xml"));
            DEFAULTAPLIKASIRUN=propServ.getProperty("DEFAULTAPLIKASIRUN");
        } catch (Exception e) {
            System.out.println("Notif Setting : "+e);
        } 
        String[] run = {"java","-jar",DEFAULTAPLIKASIRUN};
        try {
            Runtime.getRuntime().exec(run);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
    private void cleanup()
    {
        appendLog("Performing clean up...");
        File f = new File("dataupdate.zip");
        f.delete();
        remove(new File(root));
        new File(root).delete();
    }
    private void remove(File f)
    {
        File[]files = f.listFiles();
        for(File ff:files)
        {
            if(ff.isDirectory())
            {
                remove(ff);
                ff.delete();
            }
            else
            {
                ff.delete();
            }
        }
    }
    private void copyFiles(File f,String dir) throws IOException
    {
        File[]files = f.listFiles();
        for(File ff:files)
        {
            if(ff.isDirectory()){
                new File(dir+"/"+ff.getName()).mkdir();
                copyFiles(ff,dir+"/"+ff.getName());
            }
            else
            {
                copy(ff.getAbsolutePath(),dir+"/"+ff.getName());
                copiedFiles++;
                updateCopyProgress();
            }

        }
    }
    public void copy(String srFile, String dtFile) throws FileNotFoundException, IOException{

          File f1 = new File(srFile);
          File f2 = new File(dtFile);

          InputStream in = new FileInputStream(f1);

          OutputStream out = new FileOutputStream(f2);

          byte[] buf = new byte[1024];
          int len;
          while ((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
          }
          in.close();
          out.close();
      }
    private void unzip() throws IOException
    {
         int BUFFER = 2048;
         BufferedOutputStream dest = null;
         BufferedInputStream is = null;
         ZipEntry entry;
         ZipFile zipfile = new ZipFile("dataupdate.zip");
         int totalEntries = zipfile.size();
         int processed = 0;
         Enumeration e = zipfile.entries();
         (new File(root)).mkdir();
         if (totalEntries > 0) {
             updateProgress(0, "Ekstrak 0%");
         } else {
             setProgressIndeterminate("Ekstrak...");
         }
         while(e.hasMoreElements()) {
            entry = (ZipEntry) e.nextElement();
            processed++;
            if (totalEntries > 0) {
                int percent = (int)((processed * 100L) / totalEntries);
                updateProgress(percent, "Ekstrak " + percent + "%");
            }
            appendLog("Extracting: " + entry);
            if(entry.isDirectory())
                (new File(root+entry.getName())).mkdir();
            else{
                (new File(root+entry.getName())).createNewFile();
                is = new BufferedInputStream
                  (zipfile.getInputStream(entry));
                int count;
                byte data[] = new byte[BUFFER];
                FileOutputStream fos = new
                  FileOutputStream(root+entry.getName());
                dest = new
                  BufferedOutputStream(fos, BUFFER);
                while ((count = is.read(data, 0, BUFFER))
                  != -1) {
                   dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
                is.close();
            }
         }

    }
    private void downloadFile(String link) throws MalformedURLException, IOException
    {
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        long max = conn.getContentLengthLong();
        appendLog("Downloading file...");
        appendLog("Update Size (compressed): " + max + " Bytes");
        if (max > 0) {
            updateProgress(0, "Download 0%");
        } else {
            setProgressIndeterminate("Download...");
        }
        BufferedOutputStream fOut = new BufferedOutputStream(new FileOutputStream(new File("dataupdate.zip")));
        byte[] buffer = new byte[32 * 1024];
        int bytesRead = 0;
        long in = 0;
        int lastPercent = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            in += bytesRead;
            fOut.write(buffer, 0, bytesRead);
            if (max > 0) {
                int percent = (int)((in * 100L) / max);
                if (percent != lastPercent) {
                    updateProgress(percent, "Download " + percent + "%");
                    lastPercent = percent;
                }
            }
        }
        fOut.flush();
        fOut.close();
        is.close();
        appendLog("Download Complete!");
        updateProgress(100, "Download 100%");

    }
    private String getDownloadLinkFromHost() throws MalformedURLException, IOException
    {
         try  {
            propServ.loadFromXML(new FileInputStream("settingupdate/config.xml"));
            URLSERVER=propServ.getProperty("URLSERVERUPDATE");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        String path = "http://"+URLSERVER+"/url.php";
        URL url = new URL(path);

        InputStream html = null;

        html = url.openStream();

        int c = 0;
        StringBuilder buffer = new StringBuilder("");

        while(c != -1) {
            c = html.read();
        buffer.append((char)c);

        }
        return buffer.substring(buffer.indexOf("[url]")+5,buffer.indexOf("[/url]"));
    }

    private File resolveCopySource(File rootDir) {
        if (rootDir == null || !rootDir.isDirectory()) {
            return rootDir;
        }

        File[] children = rootDir.listFiles();
        if (children == null || children.length == 0) {
            return rootDir;
        }

        File onlyDir = null;
        for (int i = 0; i < children.length; i++) {
            File child = children[i];
            if (child.isDirectory()) {
                if (onlyDir == null) {
                    onlyDir = child;
                } else {
                    // Lebih dari satu folder di root, tidak ada pembungkus tunggal.
                    onlyDir = null;
                    break;
                }
            } else {
                // Ada file di root, tidak ada pembungkus tunggal.
                return rootDir;
            }
        }

        if (onlyDir != null) {
            appendLog("Folder pembungkus terdeteksi: " + onlyDir.getName());
            return onlyDir;
        }

        return rootDir;
    }

    private void prepareCopyProgress(File rootDir) {
        totalCopyFiles = countFiles(rootDir);
        copiedFiles = 0;
        if (totalCopyFiles > 0) {
            updateProgress(0, "Salin 0%");
        } else {
            setProgressIndeterminate("Menyalin file...");
        }
    }

    private int countFiles(File f) {
        if (f == null || !f.exists()) {
            return 0;
        }
        if (f.isFile()) {
            return 1;
        }
        int total = 0;
        File[] files = f.listFiles();
        if (files != null) {
            for (File ff : files) {
                total += countFiles(ff);
            }
        }
        return total;
    }

    private void updateCopyProgress() {
        if (totalCopyFiles > 0) {
            int percent = (int)((copiedFiles * 100L) / totalCopyFiles);
            updateProgress(percent, "Salin " + percent + "%");
        }
    }

    private void setProgressIndeterminate(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progress.setIndeterminate(true);
                if (text != null) {
                    progress.setString(text);
                }
            }
        });
    }

    private void updateProgress(final int value, final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progress.setIndeterminate(false);
                progress.setValue(value);
                if (text != null) {
                    progress.setString(text);
                }
            }
        });
    }

    private void appendLog(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (outText.getText().length() == 0) {
                    outText.setText(text);
                } else {
                    outText.append("\n" + text);
                }
                outText.setCaretPosition(outText.getDocument().getLength());
            }
        });
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Gui().setVisible(true);
            }
        });
    }

}
