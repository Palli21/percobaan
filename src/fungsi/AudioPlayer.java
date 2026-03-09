package fungsi;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class AudioPlayer {

    /**
     * Memainkan file suara dari path resource di dalam proyek.
     * @param resourcePath Path ke file suara, dimulai dengan '/', contoh: "/sounds/notif.wav"
     */
    public static void play(String resourcePath) {
        // Menggunakan 'synchronized' untuk mencegah beberapa suara diputar bersamaan dan menyebabkan error
        synchronized (AudioPlayer.class) {
            try {
                // Mencari lokasi resource file suara di dalam JAR/proyek
                URL soundURL = AudioPlayer.class.getResource(resourcePath);
                if (soundURL == null) {
                    System.err.println("File suara tidak ditemukan di: " + resourcePath);
                    return;
                }

                // Membuat aliran audio dari file
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                
                // Mendapatkan klip suara yang bisa dimainkan
                Clip clip = AudioSystem.getClip();
                
                // Membuka aliran audio ke klip
                clip.open(audioStream);
                
                // Mulai memainkan suara
                clip.start();
                
                // Catatan: klip akan bermain di thread terpisah,
                // jadi tidak akan menghentikan alur program utama.

            } catch (Exception e) {
                System.err.println("Gagal memainkan suara: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}