package widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    private BufferedImage gradientImage;
    private BufferedImage lightImage;
    private final Color light = new Color(1F, 1F, 0.9F, 0.25F);
    private final Color dark = new Color(1F, 1F, 0.9F, 0.25F);
    private final Color black = new Color(0,102,102);
    private final Color warna = new Color(0,102,102);
    private int lastWidth = -1;
    private int lastHeight = -1;

    public MenuBar() {
        super();
        setBorder(BorderFactory.createEmptyBorder(5, 6, 6, 6));
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Hanya update gambar jika ukuran berubah
        if (width != lastWidth || height != lastHeight) {
            setUpGradientImage(height);
            setUpLightImage(height);
            lastWidth = width;
            lastHeight = height;
        }

        if (isOpaque()) {
            g.drawImage(gradientImage, 0, 0, width, height, null);
            g.drawImage(lightImage, 0, 0, width, height / 2, null);
        }
    }

    private void setUpGradientImage(int height) {
        gradientImage = new BufferedImage(1, height, BufferedImage.TYPE_INT_ARGB);
        GradientPaint paint = new GradientPaint(0, 0, warna, 0, height, black);
        Graphics2D g = gradientImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(paint);
        g.fillRect(0, 0, 1, height);
        g.dispose();
    }

    private void setUpLightImage(int height) {
        lightImage = new BufferedImage(1, height / 2, BufferedImage.TYPE_INT_ARGB);
        GradientPaint paint = new GradientPaint(0, 0, light, 0, height / 2, dark);
        Graphics2D g = lightImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(paint);
        g.fillRect(0, 0, 1, height / 2);
        g.dispose();
    }
}
