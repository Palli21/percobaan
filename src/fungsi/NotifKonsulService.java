package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import simrskhanza.frmUtama;

/**
 * Layanan notifikasi yang berjalan di latar belakang.
 * FUNGSI: Memperbarui angka ikon dan memainkan suara HANYA JIKA ada konsultasi BARU.
 */
public class NotifKonsulService {

    private final frmUtama parentFrame;
    private final sekuel Sequel = new sekuel();
    private Connection koneksi = koneksiDB.condb();
    private Timer timer;
    private final boolean popupEnabled;
    private final boolean soundEnabled;
    private final boolean jawabanEnabled;
    private final boolean jawabanPopupEnabled;
    private final boolean jawabanSoundEnabled;
    private final long delayMs;
    private final long intervalMs;
    private boolean initialized = false;
    private boolean initializedJawaban = false;

    // KUNCI 1: Variabel untuk menyimpan jumlah konsultasi dari pengecekan sebelumnya.
    private int jumlahKonsulSebelumnya = 0;
    private int jumlahJawabanSebelumnya = 0;

    public NotifKonsulService(frmUtama parentFrame) {
        this(parentFrame, true, true, true, true, true, 5000L, 1800000L);
    }

    public NotifKonsulService(frmUtama parentFrame, boolean popupEnabled, boolean soundEnabled, long delayMs, long intervalMs) {
        this(parentFrame, popupEnabled, soundEnabled, true, true, true, delayMs, intervalMs);
    }

    public NotifKonsulService(frmUtama parentFrame, boolean popupEnabled, boolean soundEnabled,
            boolean jawabanEnabled, boolean jawabanPopupEnabled, boolean jawabanSoundEnabled,
            long delayMs, long intervalMs) {
        this.parentFrame = parentFrame;
        this.popupEnabled = popupEnabled;
        this.soundEnabled = soundEnabled;
        this.jawabanEnabled = jawabanEnabled;
        this.jawabanPopupEnabled = jawabanPopupEnabled;
        this.jawabanSoundEnabled = jawabanSoundEnabled;
        this.delayMs = delayMs > 0 ? delayMs : 5000L;
        this.intervalMs = intervalMs > 0 ? intervalMs : 1800000L;
        this.timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cekNotifikasi();
            }
        }, this.delayMs, this.intervalMs);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            System.out.println("Notif Konsul Service dihentikan.");
        }
    }

    private void cekNotifikasi() {
        String kodeDokter = akses.getkode();
        if (kodeDokter != null && !kodeDokter.isEmpty() && !kodeDokter.equals("Admin Utama")) {
            try {
                String query = "SELECT COUNT(konsultasi_medik.no_permintaan) FROM konsultasi_medik " +
                               "LEFT JOIN jawaban_konsultasi_medik ON konsultasi_medik.no_permintaan = jawaban_konsultasi_medik.no_permintaan " +
                               "WHERE konsultasi_medik.kd_dokter_dikonsuli = ? AND jawaban_konsultasi_medik.no_permintaan IS NULL";
                
                try (PreparedStatement ps = koneksi.prepareStatement(query)) {
                    ps.setString(1, kodeDokter);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int jumlahSaatIni = rs.getInt(1);
                            
                            SwingUtilities.invokeLater(() -> {
                                // 1. Selalu update angka di ikon lonceng.
                                parentFrame.updateNotifKonsul(jumlahSaatIni);
                                
                                // KUNCI 2: Logika untuk memainkan suara.
                                // Suara hanya berbunyi jika jumlah konsultasi saat ini
                                // LEBIH BESAR dari jumlah sebelumnya.
                                if (!initialized) {
                                    initialized = true;
                                } else if (jumlahSaatIni > this.jumlahKonsulSebelumnya) {
                                    if (popupEnabled) {
                                        String dokterPeminta = ambilDokterPemintaTerbaru(kodeDokter);
                                        String message = buildPesanPermintaan(jumlahSaatIni, dokterPeminta);
                                        CustomNotificationDialog dialog =
                                                new CustomNotificationDialog(parentFrame, "SIMRS - Konsul Dokter", message,
                                                        () -> parentFrame.bukaKonsulDariNotif(true));
                                        dialog.showNotification();
                                    }

                                    if (soundEnabled) {
                                        System.out.println("Notifikasi baru terdeteksi! Memainkan suara.");
                                        AudioPlayer.play("/wa/notif.wav");
                                    }
                                }
                                
                                // KUNCI 3: Update jumlah sebelumnya dengan jumlah saat ini
                                // untuk persiapan pengecekan berikutnya.
                                this.jumlahKonsulSebelumnya = jumlahSaatIni;
                            });
                        }
                    }
                }

                if (jawabanEnabled) {
                    String queryJawaban = "SELECT COUNT(DISTINCT konsultasi_medik.no_permintaan) FROM konsultasi_medik " +
                                          "INNER JOIN jawaban_konsultasi_medik ON konsultasi_medik.no_permintaan = jawaban_konsultasi_medik.no_permintaan " +
                                          "WHERE konsultasi_medik.kd_dokter = ?";
                    try (PreparedStatement psJawab = koneksi.prepareStatement(queryJawaban)) {
                        psJawab.setString(1, kodeDokter);
                        try (ResultSet rsJawab = psJawab.executeQuery()) {
                            if (rsJawab.next()) {
                                int jumlahJawabanSaatIni = rsJawab.getInt(1);
                                SwingUtilities.invokeLater(() -> {
                                    if (!initializedJawaban) {
                                        initializedJawaban = true;
                                    } else if (jumlahJawabanSaatIni > this.jumlahJawabanSebelumnya) {
                                        int tambahan = jumlahJawabanSaatIni - this.jumlahJawabanSebelumnya;
                                        if (jawabanPopupEnabled) {
                                            String dokterPenjawab = ambilDokterPenjawabTerbaru(kodeDokter);
                                            String message = buildPesanJawaban(tambahan, dokterPenjawab);
                                            CustomNotificationDialog dialog =
                                                    new CustomNotificationDialog(parentFrame, "SIMRS - Jawaban Konsul", message,
                                                            () -> parentFrame.bukaJawabanKonsulDariNotif());
                                            dialog.showNotification();
                                        }

                                        if (jawabanSoundEnabled) {
                                            System.out.println("Jawaban konsul baru terdeteksi! Memainkan suara.");
                                            AudioPlayer.play("/wa/notif.wav");
                                        }
                                    }

                                    this.jumlahJawabanSebelumnya = jumlahJawabanSaatIni;
                                });
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error saat mengecek notifikasi konsul: " + e.getMessage());
            }
        }
    }

    private String ambilDokterPemintaTerbaru(String kodeDokterDikonsuli) {
        String nama = "";
        String query = "SELECT dokter.nm_dokter FROM konsultasi_medik " +
                       "INNER JOIN dokter ON konsultasi_medik.kd_dokter = dokter.kd_dokter " +
                       "LEFT JOIN jawaban_konsultasi_medik ON konsultasi_medik.no_permintaan = jawaban_konsultasi_medik.no_permintaan " +
                       "WHERE konsultasi_medik.kd_dokter_dikonsuli = ? AND jawaban_konsultasi_medik.no_permintaan IS NULL " +
                       "ORDER BY konsultasi_medik.tanggal DESC LIMIT 1";
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setString(1, kodeDokterDikonsuli);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nama = rs.getString(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat mengambil dokter peminta konsul: " + e.getMessage());
        }
        return nama == null ? "" : nama.trim();
    }

    private String buildPesanPermintaan(int jumlahSaatIni, String dokterPeminta) {
        boolean adaNama = dokterPeminta != null && !dokterPeminta.trim().isEmpty();
        if (jumlahSaatIni <= 1) {
            if (adaNama) {
                return "Permintaan konsul dari dokter " + dokterPeminta + ".";
            }
            return "Ada 1 permintaan konsul baru.";
        }
        if (adaNama) {
            return "Ada " + jumlahSaatIni + " permintaan konsul baru. Permintaan konsul terbaru dari dokter " + dokterPeminta + ".";
        }
        return "Ada " + jumlahSaatIni + " permintaan konsul baru.";
    }

    private String ambilDokterPenjawabTerbaru(String kodeDokterPeminta) {
        String nama = "";
        String query = "SELECT dokter.nm_dokter FROM konsultasi_medik " +
                       "INNER JOIN jawaban_konsultasi_medik ON konsultasi_medik.no_permintaan = jawaban_konsultasi_medik.no_permintaan " +
                       "INNER JOIN dokter ON konsultasi_medik.kd_dokter_dikonsuli = dokter.kd_dokter " +
                       "WHERE konsultasi_medik.kd_dokter = ? " +
                       "ORDER BY jawaban_konsultasi_medik.tanggal DESC LIMIT 1";
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setString(1, kodeDokterPeminta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nama = rs.getString(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat mengambil dokter penjawab konsul: " + e.getMessage());
        }
        return nama == null ? "" : nama.trim();
    }

    private String buildPesanJawaban(int jumlahBaru, String dokterPenjawab) {
        boolean adaNama = dokterPenjawab != null && !dokterPenjawab.trim().isEmpty();
        if (jumlahBaru <= 1) {
            if (adaNama) {
                return "Konsul anda sudah dijawab oleh " + dokterPenjawab + ".";
            }
            return "Ada 1 jawaban konsul baru.";
        }
        if (adaNama) {
            return "Ada " + jumlahBaru + " jawaban konsul baru. Konsul terbaru sudah dijawab oleh " + dokterPenjawab + ".";
        }
        return "Ada " + jumlahBaru + " jawaban konsul baru.";
    }
}
