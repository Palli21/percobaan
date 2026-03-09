package fungsi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class CustomNotificationDialog extends JDialog {
    private static final int SHADOW_SIZE = 8;
    private static final int ARC = 18;
    private Timer autoCloseTimer;
    private Timer slideTimer;
    private boolean hasBeenPositioned = false;

    public CustomNotificationDialog(Frame owner, String title, String message, Runnable actionOnClick) {
        super(owner, title, false);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setFocusableWindowState(false);
        setAlwaysOnTop(true);

        ShadowPanel root = new ShadowPanel();
        root.setLayout(new BorderLayout());
        root.setBorder(new EmptyBorder(0, 0, SHADOW_SIZE, SHADOW_SIZE));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GradientPanel accent = new GradientPanel(new Color(37, 211, 102), new Color(18, 140, 126));
        accent.setPreferredSize(new Dimension(10, 4));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(6, 12, 4, 8));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(new Color(35, 45, 55));

        String timeText = new SimpleDateFormat("HH:mm").format(new Date());
        JLabel lblTime = new JLabel(timeText);
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblTime.setForeground(new Color(110, 120, 130));
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton btnClose = new JButton("x");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClose.setForeground(new Color(120, 120, 120));
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setOpaque(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel headerRight = new JPanel();
        headerRight.setOpaque(false);
        headerRight.setLayout(new BoxLayout(headerRight, BoxLayout.X_AXIS));
        headerRight.add(lblTime);
        headerRight.add(new JLabel("  "));
        headerRight.add(btnClose);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);

        JPanel bodyPanel = new JPanel(new BorderLayout(10, 0));
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(6, 12, 10, 12));

        CirclePanel iconCircle = new CirclePanel(new Color(18, 140, 126));
        iconCircle.setPreferredSize(new Dimension(40, 40));
        JLabel lblIcon = new JLabel(loadIcon("/picture/chat2.png", 18, 18));
        iconCircle.add(lblIcon);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        String safeMessage = message == null ? "" : message.trim();
        JLabel lblMessage = new JLabel("<html><div style='width:240px;'>" + escapeHtml(safeMessage) + "</div></html>");
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMessage.setForeground(new Color(45, 55, 65));

        String hintText = actionOnClick == null ? "Klik untuk menutup" : "Klik untuk membuka";
        JLabel lblHint = new JLabel(hintText);
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblHint.setForeground(new Color(120, 120, 120));

        textPanel.add(lblMessage);
        textPanel.add(new JLabel(" "));
        textPanel.add(lblHint);

        bodyPanel.add(iconCircle, BorderLayout.WEST);
        bodyPanel.add(textPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(accent, BorderLayout.NORTH);
        topPanel.add(headerPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        root.add(mainPanel, BorderLayout.CENTER);

        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (actionOnClick != null) {
                    actionOnClick.run();
                }
                dispose();
            }
        };
        root.addMouseListener(clickListener);
        mainPanel.addMouseListener(clickListener);
        topPanel.addMouseListener(clickListener);
        headerPanel.addMouseListener(clickListener);
        bodyPanel.addMouseListener(clickListener);
        textPanel.addMouseListener(clickListener);
        lblMessage.addMouseListener(clickListener);
        lblTitle.addMouseListener(clickListener);
        lblTime.addMouseListener(clickListener);
        iconCircle.addMouseListener(clickListener);
        lblIcon.addMouseListener(clickListener);

        btnClose.addActionListener(e -> dispose());

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(320, getHeight()));
        setupAutoCloseTimer(20000);
    }

    private static ImageIcon loadIcon(String path, int width, int height) {
        try {
            java.net.URL url = CustomNotificationDialog.class.getResource(path);
            if (url == null) {
                return null;
            }
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }

    private static String escapeHtml(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private void setupAutoCloseTimer(int delay) {
        autoCloseTimer = new Timer(delay, e -> dispose());
        autoCloseTimer.setRepeats(false);
    }

    public void showNotification() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenBounds = ge.getMaximumWindowBounds();
        int margin = 15;

        int targetX = screenBounds.x + screenBounds.width - getWidth() - margin;
        int y = screenBounds.y + screenBounds.height - getHeight() - margin;
        int startX = screenBounds.x + screenBounds.width;
        superSetLocation(startX, y);

        setAlwaysOnTop(true);
        setVisible(true);
        toFront();
        startSlideIn(startX, targetX, y);
        autoCloseTimer.start();
    }

    private void startSlideIn(int startX, int targetX, int y) {
        if (slideTimer != null && slideTimer.isRunning()) {
            slideTimer.stop();
        }
        int distance = startX - targetX;
        int steps = 12;
        int step = Math.max(1, distance / steps);
        slideTimer = new Timer(15, new ActionListener() {
            int currentX = startX;
            @Override
            public void actionPerformed(ActionEvent e) {
                currentX -= step;
                if (currentX <= targetX) {
                    currentX = targetX;
                    slideTimer.stop();
                }
                superSetLocation(currentX, y);
            }
        });
        slideTimer.start();
    }

    private void superSetLocation(int x, int y) {
        super.setLocation(x, y);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            this.hasBeenPositioned = true;
            toFront();
        }
    }

    @Override
    public void setLocation(int x, int y) {
        if (!hasBeenPositioned) {
            super.setLocation(x, y);
        }
    }

    @Override
    public void setLocationRelativeTo(Component c) {
        if (!hasBeenPositioned) {
            super.setLocationRelativeTo(c);
        }
    }

    @Override
    public void dispose() {
        if (autoCloseTimer != null && autoCloseTimer.isRunning()) {
            autoCloseTimer.stop();
        }
        if (slideTimer != null && slideTimer.isRunning()) {
            slideTimer.stop();
        }
        super.dispose();
    }

    private static class ShadowPanel extends JPanel {
        ShadowPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            int cardW = w - SHADOW_SIZE;
            int cardH = h - SHADOW_SIZE;

            g2.setColor(new Color(0, 0, 0, 35));
            g2.fillRoundRect(SHADOW_SIZE, SHADOW_SIZE, cardW, cardH, ARC, ARC);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, cardW, cardH, ARC, ARC);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class GradientPanel extends JPanel {
        private final Color start;
        private final Color end;

        GradientPanel(Color start, Color end) {
            this.start = start;
            this.end = end;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint paint = new GradientPaint(0, 0, start, getWidth(), 0, end);
            g2.setPaint(paint);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    private static class CirclePanel extends JPanel {
        private final Color color;

        CirclePanel(Color color) {
            this.color = color;
            setOpaque(false);
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
