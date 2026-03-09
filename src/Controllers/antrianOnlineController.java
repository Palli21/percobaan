package Controllers;

import bridging.ApiMobileJKN;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class antrianOnlineController {
    
    private final Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private final ApiMobileJKN api=new ApiMobileJKN();
    private String URL="",link="",utc="",requestJson="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private final ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private RestTemplate rest = new RestTemplate();
    private Date parsedDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String datajam = "";
    
    public Map<String, Map<String, Integer>> getDataAntrean(String tglAwal, String tglAkhir, String mode, String sumberCari) {
        try {
            link = koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : " + e);
        }

        Map<String, Map<String, Integer>> dataAntrean = new LinkedHashMap<>();

        try {
            ps = koneksi.prepareStatement(
                "SELECT reg_periksa.tgl_registrasi FROM reg_periksa " +
                "WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? GROUP BY reg_periksa.tgl_registrasi"
            );
            ps.setString(1, tglAwal);
            ps.setString(2, tglAkhir);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tgl = rs.getString("tgl_registrasi");
                int belum = 0, sudah = 0, sedang = 0,batal = 0,total = 0;

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
                    utc = String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("x-timestamp", utc);
                    headers.add("x-signature", api.getHmac(utc));
                    headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());

                    requestEntity = new HttpEntity(headers);
                    URL = link + "/antrean/pendaftaran/tanggal/" + tgl;
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("metadata");

                    if (nameNode.path("code").asText().equals("200")) {
                        JsonNode response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                        if (response.isArray()) {
                            for (JsonNode list : response) {
                                String sumber = list.path("sumberdata").asText();
                                String status = list.path("status").asText();
                                boolean isPeserta = list.path("ispeserta").asBoolean();

                                boolean cocok = false;
                                if ("Mobile JKN".equalsIgnoreCase(sumberCari)) {
                                    cocok = isPeserta && "Mobile JKN".equals(sumber);
                                } else if ("Bridging Antrean".equalsIgnoreCase(sumberCari)) {
                                    cocok = !isPeserta && "Bridging Antrean".equals(sumber);
                                } else if ("Semua".equalsIgnoreCase(sumberCari)) {
                                    cocok = true; // ambil semua data
                                }

                                if (cocok) {
                                    switch (status) {
                                        case "Belum dilayani": belum++; break;
                                        case "Sedang dilayani": sedang++; break;
                                        case "Selesai dilayani": sudah++; break;
                                        case "Batal": batal++; break;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi: " + e.getMessage());
                }

                String label;
                if (mode.equals("Tahun")) {
                    int bln = Integer.parseInt(tgl.substring(5, 7));
                    String[] bulanIndo = {
                        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
                    };
                    label = bulanIndo[bln - 1];
                } else {
                    label = String.valueOf(Integer.parseInt(tgl.substring(8, 10)));
                }

                Map<String, Integer> row = dataAntrean.getOrDefault(label, new HashMap<>());
                row.put("Belum dilayani", row.getOrDefault("Belum dilayani", 0) + belum);
                row.put("Sedang dilayani", row.getOrDefault("Sedang dilayani", 0) + sedang);
                row.put("Selesai dilayani", row.getOrDefault("Selesai dilayani", 0) + sudah);
                row.put("Batal", row.getOrDefault("Batal", 0) + batal);
                
                total = belum + sedang + sudah + batal;
                row.put("Total", row.getOrDefault("Total", 0) + total);
                dataAntrean.put(label, row);
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }

        return dataAntrean;
    }
    
    public Map<String, Map<String, Integer>> getDataSumber(String tglAwal, String tglAkhir, String mode) {
         try {
            link = koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : " + e);
        }
        Map<String, Map<String, Integer>> data = new LinkedHashMap<>();

        try {
            ps2 = koneksi.prepareStatement(
                "SELECT reg_periksa.tgl_registrasi FROM reg_periksa " +
                "WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? GROUP BY reg_periksa.tgl_registrasi"
            );
            ps2.setString(1, tglAwal);
            ps2.setString(2, tglAkhir);
            rs2 = ps2.executeQuery();

            while (rs2.next()) {
                String tgl = rs2.getString("tgl_registrasi");
                int mjkn = 0, bridging = 0;

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
                    utc = String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("x-timestamp", utc);
                    headers.add("x-signature", api.getHmac(utc));
                    headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());

                    requestEntity = new HttpEntity(headers);
                    URL = link + "/antrean/pendaftaran/tanggal/" + tgl;
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("metadata");

                    if (nameNode.path("code").asText().equals("200")) {
                        JsonNode response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                        if (response.isArray()) {
                            for (JsonNode list : response) {
                                String sumber = list.path("sumberdata").asText();
                                String status = list.path("status").asText();
                                boolean isPeserta = list.path("ispeserta").asBoolean();

                                if (!"Batal".equalsIgnoreCase(status)) {
                                    if ("Mobile JKN".equalsIgnoreCase(sumber)) {
                                        mjkn++;
                                    }else {
                                        bridging++;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("API Error: " + e.getMessage());
                }

                String label;
                if (mode.equals("Tahun")) {
                    int bln = Integer.parseInt(tgl.substring(5, 7));
                    String[] bulanIndo = {
                        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
                    };
                    label = bulanIndo[bln - 1];
                } else {
                    label = String.valueOf(Integer.parseInt(tgl.substring(8, 10)));
                }
                
                Map<String, Integer> row = data.getOrDefault(label, new HashMap<>());
                row.put("Mobile JKN", row.getOrDefault("Mobile JKN", 0) + mjkn);
                row.put("Bridging Antrean", row.getOrDefault("Bridging Antrean", 0) + bridging);
                data.put(label, row);
            }

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }

        return data;
    }
    
    public AntrianResult getDataRefAntrian(String tglAwal, String tglAkhir, String orderby, String sumberData, String keyword) {
        String statusCheckin = "";
        String statusPeriksa = "";
        String statusSoap = "";
        try {
            link = koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : " + e);
        }
        AntrianResult result = new AntrianResult();
        Map<String, Map<String, Integer>> data = new LinkedHashMap<>();

        try {
            ps2 = koneksi.prepareStatement(
                "SELECT reg_periksa.tgl_registrasi FROM reg_periksa " +
                "WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? GROUP BY reg_periksa.tgl_registrasi"
            );
            ps2.setString(1, tglAwal);
            ps2.setString(2, tglAkhir);
            rs2 = ps2.executeQuery();

            while (rs2.next()) {
                String tgl = rs2.getString("tgl_registrasi");
                int mjkn = 0, bridging = 0;

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
                    utc = String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("x-timestamp", utc);
                    headers.add("x-signature", api.getHmac(utc));
                    headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());

                    requestEntity = new HttpEntity(headers);
                    URL = link + "/antrean/pendaftaran/tanggal/" + tgl;
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("metadata");

                    if (nameNode.path("code").asText().equals("200")) {
                        JsonNode response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                        if (response.isArray()) {
                            for (JsonNode list : response) {
                                String status = list.path("status").asText();
                                String sumberApi = list.path("sumberdata").asText();
                                String kodebooking = list.path("kodebooking").asText().toLowerCase();
                                String norekammedis = list.path("norekammedis").asText().toLowerCase();

                                statusCheckin = "";
                                statusPeriksa = "";
                                statusSoap = "";

                                String noRawatJKN = Sequel.cariIsi(
                                    "SELECT no_rawat FROM referensi_mobilejkn_bpjs WHERE nobooking='" + kodebooking + "'"
                                );
                                boolean isMobileJkn = "Mobile JKN".equalsIgnoreCase(sumberApi) || !noRawatJKN.isEmpty();
                                String sumberEfektif = isMobileJkn ? "Mobile JKN" : sumberApi;
                                if (isMobileJkn) {
                                    ensureRegPeriksaForMobileJkn(kodebooking);
                                }

                                String noRawatNonJKN = resolveNoRawatNonJkn(list, kodebooking);
                                
                                if (!keyword.trim().isEmpty()) {
                                    String keywordLower = keyword.toLowerCase();
                                    if (!kodebooking.contains(keywordLower) && !norekammedis.contains(keywordLower)) {
                                        continue;
                                    }
                                }
                                
                                if (!sumberEfektif.equals(sumberData)) continue;
                                if ("Mobile JKN".equalsIgnoreCase(sumberEfektif)) {
                                    if (!noRawatJKN.isEmpty()) {
                                        statusCheckin = Sequel.cariIsi(
                                            "SELECT status FROM referensi_mobilejkn_bpjs WHERE nobooking='" + kodebooking + "'"
                                        );
                                        statusPeriksa = Sequel.cariIsi(
                                            "SELECT stts FROM reg_periksa WHERE no_rawat='" + noRawatJKN + "'"
                                        );
                                        String soap = Sequel.cariIsi(
                                            "SELECT no_rawat FROM pemeriksaan_ralan WHERE no_rawat='" + noRawatJKN + "'"
                                        );
                                        statusSoap = soap.isEmpty() ? "" : "Ada";
                                    }
                                } else if ("Bridging Antrean".equalsIgnoreCase(sumberEfektif)) {
                                    if (!noRawatNonJKN.isEmpty()) {
                                        statusPeriksa = Sequel.cariIsi(
                                            "SELECT stts FROM reg_periksa WHERE no_rawat='" + noRawatNonJKN + "'"
                                        );
                                        String soap = Sequel.cariIsi(
                                            "SELECT no_rawat FROM pemeriksaan_ralan WHERE no_rawat='" + noRawatNonJKN + "'"
                                        );
                                        statusSoap = soap.isEmpty() ? "" : "Ada";
                                    }
                                } 
                                if (orderby.equals("Semua") || orderby.equals(status)) {
                                    result.data.add(new Object[]{
                                        false,
                                        list.path("kodebooking").asText(),
                                        list.path("tanggal").asText(),
                                        list.path("kodepoli").asText(),
                                        list.path("kodedokter").asText(),
                                        list.path("jampraktek").asText(),
                                        list.path("nik").asText(),
                                        list.path("nokapst").asText(),
                                        list.path("nohp").asText(),
                                        list.path("norekammedis").asText(),
                                        list.path("jeniskunjungan").asText(),
                                        list.path("nomorreferensi").asText(),
                                        sumberEfektif,
                                        list.path("ispeserta").asText().equals("true") ? "Ya" : "Tidak",
                                        list.path("noantrean").asText(),
                                        list.path("estimasidilayani").asText(),
                                        list.path("createdtime").asText(),
                                        status,
                                        statusCheckin,  // kolom baru status check-in
                                        statusPeriksa,  // kolom baru status periksa
                                        statusSoap
                                    });
                                }
                                if (status.equals("Belum dilayani")) result.tot_belum++;
                                if (status.equals("Selesai dilayani")) result.tot_selesai++;
                                if (status.equals("Batal")) result.tot_batal++;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("API Error: " + e.getMessage());
                }
            }
            int sep1 = Sequel.cariInteger(
                    "SELECT COUNT(bridging_sep.no_rawat) FROM bridging_sep " +
                    "WHERE bridging_sep.tglsep BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "' " +
                    "AND bridging_sep.jnspelayanan = '2' AND bridging_sep.kdpolitujuan <> 'IGD'");

            int sep2 = Sequel.cariInteger(
                    "SELECT COUNT(bridging_sep_internal.no_rawat) FROM bridging_sep_internal " +
                    "WHERE bridging_sep_internal.tglsep BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "' " +
                    "AND bridging_sep_internal.jnspelayanan = '2' AND bridging_sep_internal.kdpolitujuan <> 'IGD'");

            result.sep = sep1 + sep2;
            if (result.sep > 0) {
                result.jkn_capaian = (result.jkn_selesai * 100) / result.sep;
                result.mjkn_capaian = (result.mjkn_selesai * 100) / result.sep;
            }

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }

        return result;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private void ensureRegPeriksaForMobileJkn(String kodebooking) {
        String noRawat = "";
        String norm = "";
        String kdPoliBpjs = "";
        String kdDokterBpjs = "";
        String tglRegistrasi = "";
        String kdPoli = "";
        String kdDokter = "";
        String pJawab = "";
        String almtPj = "";
        String hubunganPj = "";
        String kdPj = "";
        String statusDaftar = "Baru";
        String statusKunjungan = "Baru";
        String biayaReg = "0";
        String umur = "0";
        String sttsUmur = "Th";
        String jamReg = new SimpleDateFormat("HH:mm:ss").format(new Date());

        try {
            ps3 = koneksi.prepareStatement(
                "select no_rawat,norm,kodepoli,kodedokter,tanggalperiksa " +
                "from referensi_mobilejkn_bpjs where nobooking=?"
            );
            ps3.setString(1, kodebooking);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                noRawat = safe(rs3.getString("no_rawat"));
                norm = safe(rs3.getString("norm"));
                kdPoliBpjs = safe(rs3.getString("kodepoli"));
                kdDokterBpjs = safe(rs3.getString("kodedokter"));
                tglRegistrasi = safe(rs3.getString("tanggalperiksa"));
            }
        } catch (Exception e) {
            System.out.println("Auto reg_periksa: " + e);
        } finally {
            try {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            } catch (Exception e) {
                System.out.println("Auto reg_periksa: " + e);
            }
        }

        if (noRawat.isEmpty() || norm.isEmpty() || tglRegistrasi.isEmpty()) {
            return;
        }

        if (Sequel.cariInteger(
                "select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.no_rawat=?",
                noRawat
            ) > 0) {
            return;
        }

        kdPoli = Sequel.cariIsi(
            "select maping_poli_bpjs.kd_poli_rs from maping_poli_bpjs where maping_poli_bpjs.kd_poli_bpjs=?",
            kdPoliBpjs
        );
        if (kdPoli.isEmpty()) {
            kdPoli = kdPoliBpjs;
        }

        kdDokter = Sequel.cariIsi(
            "select maping_dokter_dpjpvclaim.kd_dokter from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter_bpjs=?",
            kdDokterBpjs
        );
        if (kdDokter.isEmpty()) {
            kdDokter = kdDokterBpjs;
        }

        if (kdPoli.isEmpty() || kdDokter.isEmpty()) {
            return;
        }

        try {
            ps3 = koneksi.prepareStatement(
                "select pasien.namakeluarga,pasien.keluarga,pasien.kd_pj,pasien.alamat," +
                "kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab, " +
                "if(pasien.tgl_daftar=?,'Baru','Lama') as daftar " +
                "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                "where pasien.no_rkm_medis=?"
            );
            ps3.setString(1, tglRegistrasi);
            ps3.setString(2, norm);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                pJawab = safe(rs3.getString("namakeluarga"));
                hubunganPj = safe(rs3.getString("keluarga"));
                kdPj = safe(rs3.getString("kd_pj"));
                almtPj = safe(rs3.getString("alamat"));
                String nmKel = safe(rs3.getString("nm_kel"));
                String nmKec = safe(rs3.getString("nm_kec"));
                String nmKab = safe(rs3.getString("nm_kab"));
                statusDaftar = safe(rs3.getString("daftar"));
                if (!nmKel.isEmpty() || !nmKec.isEmpty() || !nmKab.isEmpty()) {
                    almtPj = almtPj + ", " + nmKel + ", " + nmKec + ", " + nmKab;
                }
            } else {
                return;
            }
        } catch (Exception e) {
            System.out.println("Auto reg_periksa: " + e);
            return;
        } finally {
            try {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            } catch (Exception e) {
                System.out.println("Auto reg_periksa: " + e);
            }
        }

        int tahun = Sequel.cariInteger(
            "select TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, ?) from pasien where pasien.no_rkm_medis=?",
            tglRegistrasi, norm
        );
        if (tahun > 0) {
            umur = String.valueOf(tahun);
            sttsUmur = "Th";
        } else {
            int bulan = Sequel.cariInteger(
                "select TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, ?) from pasien where pasien.no_rkm_medis=?",
                tglRegistrasi, norm
            );
            if (bulan > 0) {
                umur = String.valueOf(bulan);
                sttsUmur = "Bl";
            } else {
                int hari = Sequel.cariInteger(
                    "select DATEDIFF(?, pasien.tgl_lahir) from pasien where pasien.no_rkm_medis=?",
                    tglRegistrasi, norm
                );
                umur = String.valueOf(hari);
                sttsUmur = "Hr";
            }
        }

        if (Sequel.cariInteger(
                "select count(reg_periksa.no_rkm_medis) from reg_periksa where reg_periksa.no_rkm_medis=? and reg_periksa.kd_poli=?",
                norm, kdPoli
            ) > 0) {
            statusKunjungan = "Lama";
        }

        if ("Lama".equals(statusDaftar)) {
            biayaReg = Sequel.cariIsi(
                "select poliklinik.registrasilama from poliklinik where poliklinik.kd_poli=?",
                kdPoli
            );
        } else {
            biayaReg = Sequel.cariIsi(
                "select poliklinik.registrasi from poliklinik where poliklinik.kd_poli=?",
                kdPoli
            );
        }

        int maxNoReg = Sequel.cariInteger(
            "select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli=? and reg_periksa.tgl_registrasi=?",
            kdPoli, tglRegistrasi
        );
        String noReg = String.format("%03d", maxNoReg + 1);

        Sequel.menyimpantf2(
            "reg_periksa",
            "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
            "No.Rawat",
            19,
            new String[]{
                noReg, noRawat, tglRegistrasi, jamReg,
                kdDokter, norm, kdPoli, pJawab, almtPj, hubunganPj, biayaReg, "Belum",
                statusDaftar, "Ralan", kdPj, umur, sttsUmur, "Belum Bayar", statusKunjungan
            }
        );
    }

    private String resolveNoRawatNonJkn(JsonNode list, String kodebooking) {
        String noRawatByBooking = Sequel.cariIsi("SELECT no_rawat FROM reg_periksa WHERE no_rawat='" + kodebooking + "'");
        if (!noRawatByBooking.isEmpty()) {
            return noRawatByBooking;
        }
        String noRm = list.path("norekammedis").asText();
        String tanggal = list.path("tanggal").asText();
        if (noRm.isEmpty() || tanggal.isEmpty()) {
            return "";
        }
        String kdPoliBpjs = list.path("kodepoli").asText();
        String kdDokterBpjs = list.path("kodedokter").asText();
        String kdPoli = Sequel.cariIsi("select kd_poli_rs from maping_poli_bpjs where kd_poli_bpjs='" + kdPoliBpjs + "'");
        String kdDokter = Sequel.cariIsi("select kd_dokter from maping_dokter_dpjpvclaim where kd_dokter_bpjs='" + kdDokterBpjs + "'");
        String baseSql = "select no_rawat from reg_periksa where no_rkm_medis='" + noRm + "' and tgl_registrasi='" + tanggal + "'";
        String noRawat = "";
        if (!kdPoli.isEmpty() && !kdDokter.isEmpty()) {
            noRawat = Sequel.cariIsi(baseSql + " and kd_poli='" + kdPoli + "' and kd_dokter='" + kdDokter + "' order by jam_reg desc limit 1");
        }
        if (noRawat.isEmpty() && !kdPoli.isEmpty()) {
            noRawat = Sequel.cariIsi(baseSql + " and kd_poli='" + kdPoli + "' order by jam_reg desc limit 1");
        }
        if (noRawat.isEmpty()) {
            noRawat = Sequel.cariIsi(baseSql + " order by jam_reg desc limit 1");
        }
        return noRawat;
    }
    
    public class AntrianResult {
        public List<Object[]> data = new ArrayList<>();
        public int tot_belum = 0, tot_selesai = 0, tot_batal = 0;
        public int jkn_belum = 0, jkn_selesai = 0;
        public int mjkn_belum = 0, mjkn_selesai = 0;
        public int umum_belum = 0, umum_selesai = 0;
        public int sep = 0;
        public int jkn_capaian = 0, mjkn_capaian = 0;
    }

}
