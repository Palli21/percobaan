package widget;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.border.LineBorder;

/**
 * Komponen JList kustom dengan gaya visual yang telah disesuaikan.
 *
 * @author Dosen
 */
public class List extends JList {

    /**
     * Konstruktor default untuk membuat komponen List.
     */
    public List() {
        super();
        // Mengatur font default untuk komponen
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        
        // Mengatur warna latar belakang menjadi putih
        setBackground(new Color(255, 255, 255));
        
        // Mengatur warna teks menjadi abu-abu gelap
        setForeground(new Color(50, 50, 50));

        // Mengatur warna latar belakang untuk item yang dipilih
        setSelectionBackground(new Color(204, 255, 204));
        
        // Mengatur warna teks untuk item yang dipilih
        setSelectionForeground(new Color(50, 50, 50));

        // Mengatur border/garis tepi komponen
        setBorder(new LineBorder(new Color(204, 204, 204)));
    }
}
