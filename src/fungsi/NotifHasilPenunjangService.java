package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import simrskhanza.frmUtama;

public class NotifHasilPenunjangService {
    private final frmUtama parentFrame;
    private final Connection koneksi = koneksiDB.condb();
    private Timer timer;
    private final boolean labEnabled;
    private final boolean labPopupEnabled;
    private final boolean labSoundEnabled;
    private final boolean radEnabled;
    private final boolean radPopupEnabled;
    private final boolean radSoundEnabled;
    private final long delayMs;
    private final long intervalMs;
    private boolean initializedLab = false;
    private boolean initializedRad = false;
    private int labPkSebelumnya = 0;
    private int labPaSebelumnya = 0;
    private int labMbSebelumnya = 0;
    private int radSebelumnya = 0;
    private boolean labPaAvailable = true;
    private boolean labMbAvailable = true;

    public NotifHasilPenunjangService(frmUtama parentFrame, boolean labEnabled, boolean labPopupEnabled,
            boolean labSoundEnabled, boolean radEnabled, boolean radPopupEnabled, boolean radSoundEnabled,
            long delayMs, long intervalMs) {
        this.parentFrame = parentFrame;
        this.labEnabled = labEnabled;
        this.labPopupEnabled = labPopupEnabled;
        this.labSoundEnabled = labSoundEnabled;
        this.radEnabled = radEnabled;
        this.radPopupEnabled = radPopupEnabled;
        this.radSoundEnabled = radSoundEnabled;
        this.delayMs = delayMs > 0 ? delayMs : 5000L;
        this.intervalMs = intervalMs > 0 ? intervalMs : 60000L;
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
            System.out.println("Notif Hasil Penunjang Service dihentikan.");
        }
    }

    private void cekNotifikasi() {
        String kodeDokter = akses.getkode();
        if (kodeDokter == null || kodeDokter.isEmpty() || kodeDokter.equals("Admin Utama")) {
            return;
        }

        if (labEnabled) {
            int pk = hitungHasil("permintaan_lab", kodeDokter);
            int pa = labPaAvailable ? hitungHasilLabPa(kodeDokter) : 0;
            int mb = labMbAvailable ? hitungHasilLabMb(kodeDokter) : 0;
            int total = pk + pa + mb;
            int totalSebelumnya = labPkSebelumnya + labPaSebelumnya + labMbSebelumnya;

            SwingUtilities.invokeLater(() -> {
                if (!initializedLab) {
                    initializedLab = true;
                } else if (total > totalSebelumnya) {
                    int deltaPk = Math.max(0, pk - labPkSebelumnya);
                    int deltaPa = Math.max(0, pa - labPaSebelumnya);
                    int deltaMb = Math.max(0, mb - labMbSebelumnya);
                    int deltaTotal = deltaPk + deltaPa + deltaMb;

                    if (labPopupEnabled) {
                        String message = buildPesanLab(deltaTotal, deltaPk, deltaPa, deltaMb);
                        Runnable action = buildLabAction(deltaPk, deltaPa, deltaMb);
                        CustomNotificationDialog dialog =
                                new CustomNotificationDialog(parentFrame, "SIMRS - Hasil Laboratorium", message, action);
                        dialog.showNotification();
                    }

                    if (labSoundEnabled) {
                        System.out.println("Hasil lab baru terdeteksi! Memainkan suara.");
                        AudioPlayer.play("/wa/notif.wav");
                    }
                }

                labPkSebelumnya = pk;
                labPaSebelumnya = pa;
                labMbSebelumnya = mb;
            });
        }

        if (radEnabled) {
            int rad = hitungHasil("permintaan_radiologi", kodeDokter);
            SwingUtilities.invokeLater(() -> {
                if (!initializedRad) {
                    initializedRad = true;
                } else if (rad > radSebelumnya) {
                    int delta = Math.max(0, rad - radSebelumnya);
                    if (radPopupEnabled) {
                        String message = buildPesanRadiologi(delta);
                        Runnable action = buildRadiologiAction();
                        CustomNotificationDialog dialog =
                                new CustomNotificationDialog(parentFrame, "SIMRS - Hasil Radiologi", message, action);
                        dialog.showNotification();
                    }
                    if (radSoundEnabled) {
                        System.out.println("Hasil radiologi baru terdeteksi! Memainkan suara.");
                        AudioPlayer.play("/wa/notif.wav");
                    }
                }
                radSebelumnya = rad;
            });
        }
    }

    private int hitungHasil(String tableName, String kodeDokter) {
        int hasil = 0;
        String query = "SELECT COUNT(noorder) FROM " + tableName +
                       " WHERE dokter_perujuk = ? AND tgl_hasil <> '0000-00-00'";
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setString(1, kodeDokter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hasil = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat mengecek hasil " + tableName + ": " + e.getMessage());
        }
        return hasil;
    }

    private int hitungHasilLabPa(String kodeDokter) {
        try {
            return hitungHasil("permintaan_labpa", kodeDokter);
        } catch (Exception e) {
            labPaAvailable = false;
            return 0;
        }
    }

    private int hitungHasilLabMb(String kodeDokter) {
        try {
            return hitungHasil("permintaan_labmb", kodeDokter);
        } catch (Exception e) {
            labMbAvailable = false;
            return 0;
        }
    }

    private String buildPesanLab(int deltaTotal, int deltaPk, int deltaPa, int deltaMb) {
        if (deltaTotal <= 1) {
            if (deltaPk == 1 && deltaPa == 0 && deltaMb == 0) {
                return "Hasil Lab PK anda sudah selesai.";
            }
            if (deltaPa == 1 && deltaPk == 0 && deltaMb == 0) {
                return "Hasil Lab PA anda sudah selesai.";
            }
            if (deltaMb == 1 && deltaPk == 0 && deltaPa == 0) {
                return "Hasil Lab MB anda sudah selesai.";
            }
            return "Hasil laboratorium anda sudah selesai.";
        }

        String detail = buildDetail(deltaPk, deltaPa, deltaMb);
        if (!detail.isEmpty()) {
            return "Ada " + deltaTotal + " hasil laboratorium baru (" + detail + ").";
        }
        return "Ada " + deltaTotal + " hasil laboratorium baru.";
    }

    private String buildPesanRadiologi(int delta) {
        if (delta <= 1) {
            return "Hasil radiologi anda sudah selesai.";
        }
        return "Ada " + delta + " hasil radiologi baru.";
    }

    private String buildDetail(int deltaPk, int deltaPa, int deltaMb) {
        String detail = "";
        if (deltaPk > 0) {
            detail = "PK " + deltaPk;
        }
        if (deltaPa > 0) {
            detail = detail.isEmpty() ? "PA " + deltaPa : detail + ", PA " + deltaPa;
        }
        if (deltaMb > 0) {
            detail = detail.isEmpty() ? "MB " + deltaMb : detail + ", MB " + deltaMb;
        }
        return detail;
    }

    private Runnable buildLabAction(int deltaPk, int deltaPa, int deltaMb) {
        if (parentFrame == null) {
            return null;
        }
        if (deltaPk > 0) {
            return () -> parentFrame.bukaHasilLabPkDariNotif();
        }
        if (deltaPa > 0) {
            return () -> parentFrame.bukaHasilLabPaDariNotif();
        }
        if (deltaMb > 0) {
            return () -> parentFrame.bukaHasilLabMbDariNotif();
        }
        return null;
    }

    private Runnable buildRadiologiAction() {
        if (parentFrame == null) {
            return null;
        }
        return () -> parentFrame.bukaHasilRadiologiDariNotif();
    }
}
