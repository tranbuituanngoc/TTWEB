package bean;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.jdbi.v3.core.Jdbi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Log extends AbLog implements Serializable {
    private long id_log;

    private int id_level;
    private String user_id;
    private String src;
    private String content;
    private String ip_address;
    private String web_browser;
    private Timestamp create_at;
    private String status;

    @Override
    public String toString() {
        return "Log{" +
                "id_log=" + id_log +
                ", id_level=" + id_level +
                ", user_id=" + user_id +
                ", src='" + src + '\'' +
                ", content='" + content + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", web_browser='" + web_browser + '\'' +
                ", create_at=" + create_at +
                ", status='" + status + '\'' +
                '}';
    }

    public Log(int id_level, String user_id, String src, String content, String ip_address, String web_browser, String status) {
        this.id_level = id_level;
        this.user_id = user_id;
        this.src = src;
        this.content = content;
        this.ip_address = ip_address;
        this.web_browser = web_browser;
        this.status = status;
    }

    public Log(int id_level, String user_id, String src, String content, String status) {
        this.id_level = id_level;
        this.user_id = user_id;
        this.src = src;
        this.content = content;
        this.status = status;
    }

    public static final Map<Integer, String> levelMapping = new HashMap<>();

    static {
        levelMapping.put(1, "INFO");
        levelMapping.put(2, "ALERT");
        levelMapping.put(3, "WARNING");
        levelMapping.put(4, "DANGER");
    }

    public static int INFO = 1;
    public static int ALERT = 2;
    public static int WARNING = 3;
    public static int DANGER = 4;

    public long getId_log() {
        return id_log;
    }

    public void setId_log(long id_log) {
        this.id_log = id_log;
    }

    public int getId_level() {
        return id_level;
    }

    public void setId_level(int id_level) {
        this.id_level = id_level;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getWeb_browser() {
        return web_browser;
    }

    public void setWeb_browser(String web_browser) {
        this.web_browser = web_browser;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Log() {
    }

    public String getLevelWithName() {
        return levelMapping.get(levelMapping.containsKey(this.id_level) ? this.id_level : 0);
    }


    public boolean insert(Jdbi db) {
        // Tạo một đối tượng Random
        Random random = new Random();
        String ipAddress = "";
        String webBrowser = "";
        try {
            // Lấy địa chỉ IP
            URL ipApiUrl = new URL("http://ip-api.com/json");
            HttpURLConnection ipConnection = (HttpURLConnection) ipApiUrl.openConnection();
            ipConnection.setRequestMethod("GET");
            int ipResponseCode = ipConnection.getResponseCode();

            if (ipResponseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader ipReader = new BufferedReader(new InputStreamReader(ipConnection.getInputStream()));
                StringBuilder ipResponse = new StringBuilder();
                String ipLine;
                while ((ipLine = ipReader.readLine()) != null) {
                    ipResponse.append(ipLine);
                }
                ipReader.close();

                JSONObject ipJson = new JSONObject(ipResponse.toString());
                ipAddress = ipJson.getString("query");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userAgentHeader = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36";
        webBrowser = getBrowserFromUserAgent(userAgentHeader);

        long idLog = random.nextLong();

        String finalIpAddress = ipAddress;
        String finalWebBrowser = webBrowser;
        Integer i = db.withHandle(handle ->
                handle.execute("INSERT INTO logs(`id_log`, `id_level`, `user_id`, src, content, ip_address, web_browser, `status`)  VALUES(?,?,?,?,?,?,?,?)",
                        idLog, this.id_level, getUser_id().equals("-1") ? null : getUser_id(), this.src, this.content, finalIpAddress
                        , finalWebBrowser, this.status)
        );
        return i == 1;
    }

    private static String getBrowserFromUserAgent(String userAgentHeader) {
        String browserName = "";

        if (userAgentHeader != null) {
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
            Browser browser = userAgent.getBrowser();
            if (browser != null) {
                browserName = browser.getName();
            }
        }
        return browserName;
    }


    @Override
    public boolean delete(Jdbi db) {
        return false;
    }

    @Override
    public boolean update(Jdbi db) {
        return false;
    }


}
