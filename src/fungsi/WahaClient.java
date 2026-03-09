package fungsi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class WahaClient {
    private WahaClient() {
    }

    public static final class Result {
        public final boolean ok;
        public final String chatId;
        public final String messageId;
        public final String responseBody;
        public final String error;

        private Result(boolean ok, String chatId, String messageId, String responseBody, String error) {
            this.ok = ok;
            this.chatId = chatId;
            this.messageId = messageId;
            this.responseBody = responseBody;
            this.error = error;
        }
    }

    public static final class HealthResult {
        public final boolean ok;
        public final int statusCode;
        public final long latencyMs;
        public final String responseBody;
        public final String error;

        private HealthResult(boolean ok, int statusCode, long latencyMs, String responseBody, String error) {
            this.ok = ok;
            this.statusCode = statusCode;
            this.latencyMs = latencyMs;
            this.responseBody = responseBody;
            this.error = error;
        }
    }

    public static HealthResult check() {
        String baseUrl = trimOrEmpty(koneksiDB.WAHAURL());
        String deviceId = normalizeDeviceId(koneksiDB.WAHASESSION());
        String auth = normalizeAuthHeader(trimOrEmpty(koneksiDB.WAHAAUTH()));
        if (baseUrl.isEmpty()) {
            return new HealthResult(false, 0, 0, "", "WAHAURL kosong");
        }

        String path = trimOrEmpty(koneksiDB.WAHAHEALTHPATH());
        if (path.isEmpty()) {
            path = "/";
        }

        long start = System.nanoTime();
        try {
            String url = buildUrl(baseUrl, path);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            if (!auth.isEmpty()) {
                con.setRequestProperty("Authorization", auth);
            }
            if (!deviceId.isEmpty()) {
                con.setRequestProperty("X-Device-Id", deviceId);
            }

            int responseCode = con.getResponseCode();
            long latencyMs = (System.nanoTime() - start) / 1_000_000;
            String responseBody = readBody(responseCode >= 200 && responseCode < 300 ? con.getInputStream() : con.getErrorStream());
            boolean ok = responseCode >= 200 && responseCode < 300;
            String error = ok ? "" : "HTTP " + responseCode;
            return new HealthResult(ok, responseCode, latencyMs, responseBody, error);
        } catch (Exception e) {
            long latencyMs = (System.nanoTime() - start) / 1_000_000;
            return new HealthResult(false, 0, latencyMs, "", e.getMessage());
        }
    }

    public static Result sendText(String rawChatIdOrPhone, String text) {
        String baseUrl = trimOrEmpty(koneksiDB.WAHAURL());
        String deviceId = normalizeDeviceId(koneksiDB.WAHASESSION());
        String auth = normalizeAuthHeader(trimOrEmpty(koneksiDB.WAHAAUTH()));
        if (baseUrl.isEmpty()) {
            return new Result(false, "", "", "", "WAHAURL kosong");
        }

        String chatId = normalizeChatId(rawChatIdOrPhone);
        if (chatId.isEmpty()) {
            return new Result(false, "", "", "", "ChatId kosong");
        }

        try {
            String url = buildUrl(baseUrl, "/send/message");
            url = addDeviceIdParam(url, deviceId);
            Map<String, String> payload = new HashMap<>();
            payload.put("phone", chatId);
            payload.put("message", text == null ? "" : text);
            String json = new ObjectMapper().writeValueAsString(payload);

            HttpURLConnection con = openJsonConnection(url, auth, deviceId);

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = con.getResponseCode();
            String responseBody = readBody(responseCode >= 200 && responseCode < 300 ? con.getInputStream() : con.getErrorStream());
            boolean ok = responseCode >= 200 && responseCode < 300;
            String messageId = ok ? extractMessageId(responseBody) : "";
            String error = ok ? "" : "HTTP " + responseCode;
            return new Result(ok, chatId, messageId, responseBody, error);
        } catch (Exception e) {
            return new Result(false, chatId, "", "", e.getMessage());
        }
    }

    public static Result sendFile(String rawChatIdOrPhone, String fileUrl, String caption) {
        return sendFile(rawChatIdOrPhone, fileUrl, "", "", caption);
    }

    public static Result sendFile(String rawChatIdOrPhone, String fileUrl, String fileName, String mimeType, String caption) {
        String baseUrl = trimOrEmpty(koneksiDB.WAHAURL());
        String deviceId = normalizeDeviceId(koneksiDB.WAHASESSION());
        String auth = normalizeAuthHeader(trimOrEmpty(koneksiDB.WAHAAUTH()));
        if (baseUrl.isEmpty()) {
            return new Result(false, "", "", "", "WAHAURL kosong");
        }

        String chatId = normalizeChatId(rawChatIdOrPhone);
        if (chatId.isEmpty()) {
            return new Result(false, "", "", "", "ChatId kosong");
        }
        String cleanedUrl = trimOrEmpty(fileUrl);
        if (cleanedUrl.isEmpty()) {
            return new Result(false, chatId, "", "", "URL file kosong");
        }

        String resolvedFileName = trimOrEmpty(fileName);
        if (resolvedFileName.isEmpty()) {
            resolvedFileName = extractFileNameFromUrl(cleanedUrl);
        }
        String resolvedMimeType = trimOrEmpty(mimeType);
        if (resolvedMimeType.isEmpty()) {
            resolvedMimeType = inferMimeType(resolvedFileName);
        }

        try {
            if ("image/webp".equals(resolvedMimeType)) {
                return sendMediaUrl(baseUrl, auth, deviceId, "/send/sticker", "sticker_url", chatId, cleanedUrl, "");
            }
            if (resolvedMimeType.startsWith("image/")) {
                return sendMediaUrl(baseUrl, auth, deviceId, "/send/image", "image_url", chatId, cleanedUrl, caption);
            }
            if (resolvedMimeType.startsWith("video/")) {
                return sendMediaUrl(baseUrl, auth, deviceId, "/send/video", "video_url", chatId, cleanedUrl, caption);
            }
            if (resolvedMimeType.startsWith("audio/")) {
                return sendMediaUrl(baseUrl, auth, deviceId, "/send/audio", "audio_url", chatId, cleanedUrl, "");
            }

            byte[] fileBytes = downloadFile(cleanedUrl);
            return sendMultipartFile(baseUrl, auth, deviceId, chatId, resolvedFileName, resolvedMimeType, caption, fileBytes);
        } catch (Exception e) {
            return new Result(false, chatId, "", "", e.getMessage());
        }
    }

    public static String normalizeChatId(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        if (trimmed.contains("@")) {
            return trimmed;
        }
        String digits = trimmed.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return "";
        }
        if (digits.startsWith("0")) {
            digits = "62" + digits.substring(1);
        }
        return digits + (digits.length() > 15 ? "@g.us" : "@s.whatsapp.net");
    }

    private static String buildUrl(String baseUrl, String path) {
        String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        return normalizedBase + normalizedPath;
    }

    private static HttpURLConnection openJsonConnection(String url, String auth, String deviceId) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        if (!auth.isEmpty()) {
            con.setRequestProperty("Authorization", auth);
        }
        if (!deviceId.isEmpty()) {
            con.setRequestProperty("X-Device-Id", deviceId);
        }
        con.setDoOutput(true);
        return con;
    }

    private static Result sendMediaUrl(String baseUrl, String auth, String deviceId, String path, String urlField, String chatId, String mediaUrl, String caption) throws Exception {
        String url = buildUrl(baseUrl, path);
        url = addDeviceIdParam(url, deviceId);
        Map<String, String> payload = new HashMap<>();
        payload.put("phone", chatId);
        payload.put(urlField, mediaUrl);
        if (caption != null && !caption.isEmpty()) {
            payload.put("caption", caption);
        }
        String json = new ObjectMapper().writeValueAsString(payload);

        HttpURLConnection con = openJsonConnection(url, auth, deviceId);
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = con.getResponseCode();
        String responseBody = readBody(responseCode >= 200 && responseCode < 300 ? con.getInputStream() : con.getErrorStream());
        boolean ok = responseCode >= 200 && responseCode < 300;
        String messageId = ok ? extractMessageId(responseBody) : "";
        String error = ok ? "" : "HTTP " + responseCode;
        return new Result(ok, chatId, messageId, responseBody, error);
    }

    private static Result sendMultipartFile(String baseUrl, String auth, String deviceId, String chatId, String fileName, String mimeType, String caption, byte[] fileBytes) throws Exception {
        String url = buildUrl(baseUrl, "/send/file");
        url = addDeviceIdParam(url, deviceId);
        String boundary = "----WahaClientBoundary" + UUID.randomUUID().toString().replace("-", "");
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        if (!auth.isEmpty()) {
            con.setRequestProperty("Authorization", auth);
        }
        if (!deviceId.isEmpty()) {
            con.setRequestProperty("X-Device-Id", deviceId);
        }
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            writeMultipartField(os, boundary, "phone", chatId);
            if (caption != null && !caption.isEmpty()) {
                writeMultipartField(os, boundary, "caption", caption);
            }
            writeMultipartFile(os, boundary, "file", fileName, mimeType, fileBytes);
            os.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = con.getResponseCode();
        String responseBody = readBody(responseCode >= 200 && responseCode < 300 ? con.getInputStream() : con.getErrorStream());
        boolean ok = responseCode >= 200 && responseCode < 300;
        String messageId = ok ? extractMessageId(responseBody) : "";
        String error = ok ? "" : "HTTP " + responseCode;
        return new Result(ok, chatId, messageId, responseBody, error);
    }

    private static void writeMultipartField(OutputStream os, String boundary, String name, String value) throws Exception {
        String part = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n"
                + value + "\r\n";
        os.write(part.getBytes(StandardCharsets.UTF_8));
    }

    private static void writeMultipartFile(OutputStream os, String boundary, String name, String fileName, String mimeType, byte[] data) throws Exception {
        String header = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\n"
                + "Content-Type: " + mimeType + "\r\n\r\n";
        os.write(header.getBytes(StandardCharsets.UTF_8));
        os.write(data);
        os.write("\r\n".getBytes(StandardCharsets.UTF_8));
    }

    private static byte[] downloadFile(String fileUrl) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(fileUrl).openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        InputStream stream = responseCode >= 200 && responseCode < 300 ? con.getInputStream() : con.getErrorStream();
        if (stream == null) {
            throw new Exception("Gagal mengambil file, HTTP " + responseCode);
        }
        byte[] data;
        try (InputStream input = stream) {
            data = readBytes(input);
        }
        if (responseCode < 200 || responseCode >= 300) {
            throw new Exception("Gagal mengambil file, HTTP " + responseCode);
        }
        return data;
    }

    private static byte[] readBytes(InputStream stream) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int n;
        while ((n = stream.read(data)) != -1) {
            buffer.write(data, 0, n);
        }
        return buffer.toByteArray();
    }

    private static String readBody(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private static String extractFileNameFromUrl(String url) {
        String cleaned = url;
        int queryIndex = cleaned.indexOf('?');
        if (queryIndex >= 0) {
            cleaned = cleaned.substring(0, queryIndex);
        }
        int slashIndex = cleaned.lastIndexOf('/');
        String name = slashIndex >= 0 ? cleaned.substring(slashIndex + 1) : cleaned;
        return name.isEmpty() ? "file" : name;
    }

    private static String inferMimeType(String fileName) {
        String lower = fileName == null ? "" : fileName.toLowerCase();
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (lower.endsWith(".png")) {
            return "image/png";
        }
        if (lower.endsWith(".gif")) {
            return "image/gif";
        }
        if (lower.endsWith(".pdf")) {
            return "application/pdf";
        }
        if (lower.endsWith(".mp4")) {
            return "video/mp4";
        }
        if (lower.endsWith(".mp3")) {
            return "audio/mpeg";
        }
        return "application/octet-stream";
    }

    private static String extractMessageId(String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseBody);
            JsonNode results = node.get("results");
            if (results != null) {
                if (results.hasNonNull("message_id")) {
                    return results.get("message_id").asText();
                }
                if (results.hasNonNull("messageId")) {
                    return results.get("messageId").asText();
                }
            }
            if (node.hasNonNull("id")) {
                return node.get("id").asText();
            }
            if (node.hasNonNull("messageId")) {
                return node.get("messageId").asText();
            }
            JsonNode key = node.get("key");
            if (key != null && key.hasNonNull("id")) {
                return key.get("id").asText();
            }
        } catch (Exception e) {
            // ignore and fallback
        }
        return UUID.randomUUID().toString();
    }

    private static String normalizeAuthHeader(String auth) {
        String trimmed = trimOrEmpty(auth);
        if (trimmed.isEmpty()) {
            return "";
        }
        String lower = trimmed.toLowerCase();
        if (lower.startsWith("basic ")) {
            return trimmed;
        }
        if (trimmed.contains(":")) {
            String encoded = Base64.getEncoder().encodeToString(trimmed.getBytes(StandardCharsets.UTF_8));
            return "Basic " + encoded;
        }
        return trimmed;
    }

    private static String normalizeDeviceId(String raw) {
        String trimmed = trimOrEmpty(raw);
        if (trimmed.isEmpty() || "default".equalsIgnoreCase(trimmed)) {
            return "";
        }
        if (trimmed.contains("@")) {
            return trimmed;
        }
        String digits = trimmed.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return "";
        }
        if (digits.startsWith("0")) {
            digits = "62" + digits.substring(1);
        }
        return digits + "@s.whatsapp.net";
    }

    private static String addDeviceIdParam(String url, String deviceId) {
        if (trimOrEmpty(deviceId).isEmpty()) {
            return url;
        }
        try {
            String encoded = URLEncoder.encode(deviceId, StandardCharsets.UTF_8.name());
            if (url.contains("?")) {
                return url + "&device_id=" + encoded;
            }
            return url + "?device_id=" + encoded;
        } catch (Exception e) {
            return url;
        }
    }

    private static String trimOrEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
