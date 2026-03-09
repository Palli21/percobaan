
package fungsi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class Toast extends JWindow {
    private final JLabel label = new JLabel();
    private Timer hideTimer;
    private Runnable onClick;

    public Toast(Window parent, String message, int millis) {
        super(parent);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10,16,10,16));
        content.setBackground(new Color(40,40,40,220));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setText(message);
        content.add(label, BorderLayout.CENTER);
        setContentPane(content);
        pack();

        content.addMouseListener(new MouseAdapter(){
            @Override public void mouseClicked(MouseEvent e){
                if(onClick!=null) onClick.run();
                dismiss();
            }
        });

        hideTimer = new Timer(millis, e -> dismiss());
        hideTimer.setRepeats(false);
    }

    public void setOnClick(Runnable r){ this.onClick = r; }

    public void showBottomRight() {
        Point p = computeBottomRightLocation();
        setLocation(p);
        setVisible(true);
        hideTimer.start();
        playSoundIfExists("/sound/notify.wav");
    }

    public void dismiss(){
        if(hideTimer!=null) hideTimer.stop();
        setVisible(false);
        dispose();
    }

    private Point computeBottomRightLocation(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int x = bounds.x + bounds.width - getWidth() - 16 - scnMax.right;
        int y = bounds.y + bounds.height - getHeight() - 40 - scnMax.bottom;
        return new Point(x,y);
    }

    private void playSoundIfExists(String resourcePath){
        try(InputStream in = getClass().getResourceAsStream(resourcePath)){
            if(in==null) return;
            BufferedInputStream bis = new BufferedInputStream(in);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bis);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }catch(Exception ex){
            // ignore audio errors to avoid bothering users
        }
    }
}
