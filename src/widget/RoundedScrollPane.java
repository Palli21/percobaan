package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 * JScrollPane dengan sudut melengkung dan border yang berubah warna saat 
 * komponen di dalamnya (misal JTable) mendapatkan fokus.
 * * @author Gemini
 */
public class RoundedScrollPane extends JScrollPane {
    
    private boolean isFocusOwner = false;
    private final int cornerRadius = 15;
    private final Color focusColor = new Color(0, 156, 215);
    private final Color defaultColor = new Color(180, 180, 180);

    public RoundedScrollPane(Component view) {
        super(view);
        
        setOpaque(false); // Membuat ScrollPane transparan
        getViewport().setOpaque(false); // Membuat area view juga transparan
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding internal
        
        // Listener untuk mendeteksi fokus pada komponen di dalam ScrollPane (yaitu JTable)
        view.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocusOwner = true;
                repaint(); // Gambar ulang dengan border fokus
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocusOwner = false;
                repaint(); // Gambar ulang dengan border default
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pilih warna border berdasarkan status fokus
        Color borderColor = isFocusOwner ? focusColor : defaultColor;

        // Gambar background putih dengan sudut melengkung
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
        
        // Gambar border dengan sudut melengkung
        g2.setColor(borderColor);
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        g2.dispose();
        
        // Panggil super.paintComponent agar child component (tabel dan scrollbar) tergambar
        super.paintComponent(g);
    }
}