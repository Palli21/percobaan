package fungsi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class WhatsappReminderKontrolService {
    private static final String DEFAULT_SEND_TIME = "08:00";
    private static final String DEFAULT_ZONE = "Asia/Makassar";
    private static final String AKTIVITAS = "Reminder Kontrol";
    private static final DateTimeFormatter DATE_ONLY = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Connection koneksi;
    private final ZoneId zoneId;
    private final LocalTime sendTime;
    private final String testPhone;
    private final Object runLock = new Object();
    private Timer timer;

    public WhatsappReminderKontrolService(String timeText, String zoneText, String testPhone) {
        this.zoneId = resolveZone(zoneText);
        this.sendTime = resolveSendTime(timeText);
        this.testPhone = safeTrim(testPhone);
        this.koneksi = koneksiDB.condb();
        this.timer = new Timer(true);

        long delayMs = calculateInitialDelayMs();
        long intervalMs = Duration.ofDays(1).toMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                kirimReminderKontrol();
            }
        }, delayMs, intervalMs);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            System.out.println("Reminder Kontrol WA dihentikan.");
        }
    }

    private void kirimReminderKontrol() {
        synchronized (runLock) {
            String waGateway = koneksiDB.WAGATEWAY();
            if (!"golang".equalsIgnoreCase(waGateway) && !"wagolang".equalsIgnoreCase(waGateway)) {
                return;
            }

            LocalDate targetDate = LocalDate.now(zoneId).plusDays(1);
            String todayStr = LocalDate.now(zoneId).format(DATE_ONLY);
            String overridePhone = normalizePhone(testPhone);
            boolean useOverride = !overridePhone.isEmpty();

            String sql = "select p.no_rkm_medis, p.nm_pasien, p.no_tlp, "
                    + "bsk.no_surat, bsk.tgl_rencana, bsk.nm_poli_bpjs, bsk.nm_dokter_bpjs "
                    + "from bridging_surat_kontrol_bpjs bsk "
                    + "inner join bridging_sep bs on bsk.no_sep = bs.no_sep "
                    + "inner join reg_periksa rp on rp.no_rawat = bs.no_rawat "
                    + "inner join pasien p on p.no_rkm_medis = rp.no_rkm_medis "
                    + "where bsk.tgl_rencana = ? and p.no_tlp is not null and p.no_tlp <> ''";

            Connection conn = getConnection();
            if (conn == null) {
                System.out.println("Gagal mengirim reminder kontrol: koneksi database null.");
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, Date.valueOf(targetDate));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String noRm = safeTrim(rs.getString("no_rkm_medis"));
                        String noWa = useOverride ? overridePhone : normalizePhone(rs.getString("no_tlp"));
                        String nama = safeTrim(rs.getString("nm_pasien"));
                        String noSurat = safeTrim(rs.getString("no_surat"));
                        String tglKontrol = safeTrim(rs.getString("tgl_rencana"));
                        String nmPoli = safeTrim(rs.getString("nm_poli_bpjs"));
                        String nmDokter = safeTrim(rs.getString("nm_dokter_bpjs"));

                        if (noWa.isEmpty() || noRm.isEmpty()) {
                            continue;
                        }
                        if (!useOverride && sudahDikirim(noRm, noSurat, todayStr)) {
                            continue;
                        }

                        String pesan = buildReminderMessage(nama, noRm, noSurat, tglKontrol, nmPoli, nmDokter);
                        WagolangClient.Result result = WagolangClient.sendText(noWa, pesan);
                        if (result.ok) {
                            logWaReport(result.messageId, noRm, noWa, result.chatId, pesan);
                            if (useOverride) {
                                break;
                            }
                        } else {
                            System.out.println("Gagal kirim reminder kontrol: " + result.error);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Gagal mengirim reminder kontrol: " + e.getMessage());
            }
        }
    }

    public void triggerNow() {
        Thread worker = new Thread(this::kirimReminderKontrol, "ReminderKontrolTest");
        worker.setDaemon(true);
        worker.start();
    }

    private boolean sudahDikirim(String noRm, String noSurat, String todayStr) {
        String sql = "select count(id) from wa_report "
                + "where device = ? and no_rkm_medis = ? and left(tglkirim,10) = ? and message like ?";
        String needle = "%NO. SURAT : " + safeDash(noSurat) + "%";
        Connection conn = getConnection();
        if (conn == null) {
            return false;
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, AKTIVITAS);
            ps.setString(2, noRm);
            ps.setString(3, todayStr);
            ps.setString(4, needle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal cek reminder kontrol: " + e.getMessage());
        }
        return false;
    }

    private void logWaReport(String messageId, String noRm, String noWa, String chatId, String pesan) {
        String now = ZonedDateTime.now(zoneId).format(DATE_TIME);
        String sql = "insert into wa_report values(?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("Gagal log reminder kontrol: koneksi database null.");
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizeMessageId(messageId));
            ps.setString(2, noRm);
            ps.setString(3, noWa);
            ps.setString(4, "SYSTEM");
            ps.setString(5, AKTIVITAS);
            ps.setString(6, chatId == null ? "" : chatId);
            ps.setString(7, pesan == null ? "" : pesan);
            ps.setString(8, "");
            ps.setString(9, "true");
            ps.setString(10, "");
            ps.setString(11, now);
            ps.setString(12, now);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Gagal log reminder kontrol: " + e.getMessage());
        }
    }

    private String buildReminderMessage(String nama, String noRm, String noSurat, String tglKontrol, String nmPoli, String nmDokter) {
        String namaRs = safeTrim(akses.getnamars());
        String kabupaten = safeTrim(akses.getkabupatenrs());
        String alamatRs = safeTrim(akses.getalamatrs());
        String header = namaRs;
        if (!kabupaten.isEmpty()) {
            String headerUpper = header.toUpperCase();
            String kabUpper = kabupaten.toUpperCase();
            if (!headerUpper.contains(kabUpper)) {
                header = header.isEmpty() ? "KAB. " + kabupaten : header + " KAB. " + kabupaten;
            }
        }
        if (header.isEmpty()) {
            header = "RS";
        }

        StringBuilder pesan = new StringBuilder();
        pesan.append("\uD83C\uDFE5 ").append(header);
        if (!alamatRs.isEmpty()) {
            pesan.append("\n\uD83D\uDCCD ").append(alamatRs);
        }
        pesan.append("\n\n\uD83D\uDD14 Pengingat Kontrol BPJS");
        pesan.append("\nYth. Bapak/Ibu ").append(safeDash(nama)).append(",");
        pesan.append("\nBerikut jadwal kontrol Anda:");
        pesan.append("\n\uD83C\uDD94 No. RM : ").append(safeDash(noRm));
        pesan.append("\n\uD83D\uDCDD NO. SURAT : ").append(safeDash(noSurat));
        pesan.append("\n\uD83D\uDCC5 Tgl Kontrol : ").append(safeDash(tglKontrol));
        pesan.append("\n\uD83C\uDFE5 Poli : ").append(safeDash(nmPoli));
        pesan.append("\n\uD83E\uDE7A Dokter : ").append(safeDash(nmDokter));
        pesan.append("\n\n\uD83D\uDCF1 Silakan ambil antrian melalui Aplikasi Mobile JKN.");
        pesan.append("\n\uD83D\uDE4F Terima kasih.");
        return pesan.toString();
    }

    private long calculateInitialDelayMs() {
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime next = now.withHour(sendTime.getHour())
                .withMinute(sendTime.getMinute())
                .withSecond(0)
                .withNano(0);
        if (!next.isAfter(now)) {
            next = next.plusDays(1);
        }
        return Duration.between(now, next).toMillis();
    }

    private ZoneId resolveZone(String zoneText) {
        String zoneValue = safeTrim(zoneText);
        if (zoneValue.isEmpty()) {
            zoneValue = DEFAULT_ZONE;
        }
        try {
            return ZoneId.of(zoneValue);
        } catch (Exception e) {
            return ZoneId.of(DEFAULT_ZONE);
        }
    }

    private LocalTime resolveSendTime(String timeText) {
        String value = safeTrim(timeText);
        if (value.isEmpty()) {
            value = DEFAULT_SEND_TIME;
        }
        try {
            return LocalTime.parse(value);
        } catch (Exception e) {
            return LocalTime.parse(DEFAULT_SEND_TIME);
        }
    }

    private String safeDash(String value) {
        String trimmed = safeTrim(value);
        return trimmed.isEmpty() ? "-" : trimmed;
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private String normalizeMessageId(String messageId) {
        String trimmed = safeTrim(messageId);
        if (trimmed.matches("\\d+")) {
            return trimmed;
        }
        return String.valueOf(System.currentTimeMillis());
    }

    private Connection getConnection() {
        try {
            if (koneksi == null || koneksi.isClosed()) {
                koneksi = koneksiDB.condb();
            }
        } catch (Exception e) {
            koneksi = koneksiDB.condb();
        }
        return koneksi;
    }

    private String normalizePhone(String raw) {
        String trimmed = safeTrim(raw).replace("|", "").replace(" ", "");
        if (trimmed.isEmpty()) {
            return "";
        }
        if (trimmed.contains("@")) {
            return trimmed;
        }
        return trimmed.replaceAll("[^0-9]", "");
    }
}
