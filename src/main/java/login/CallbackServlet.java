package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String provider = request.getParameter("provider");
        String code = request.getParameter("code");
        if (provider.equals("Facebook")) {
            String accessTokenUrl = "https://graph.facebook.com/v12.0/oauth/access_token?client_id=526446266364446&redirect_uri=http://localhost:8080/TTWEB_war/callback&client_secret=0c016f33615a32a9549aa7c113b18261&code=" + code;
            String accessTokenResponse = sendGetRequest(accessTokenUrl);
            String accessToken = extractAccessTokenFromResponse(accessTokenResponse);

            String userInfoUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
            String userInfoResponse = sendGetRequest(userInfoUrl);
            JSONObject userInfo = new JSONObject(userInfoResponse);

            String id = userInfo.getString("id");
            String name = userInfo.getString("name");
            String email = userInfo.getString("email");

            // Lưu thông tin của người dùng vào cơ sở dữ liệu
            saveUserToDatabase(id, name, email);
        } else if (provider.equals("Google")) {
            String tokenUrl = "https://accounts.google.com/o/oauth2/token";
            String tokenParams = "code=" + code + "&client_id=524926861428-2uub0irutasgfqkmutpn37p8sltupgre.apps.googleusercontent.com&client_secret=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&redirect_uri=http://localhost:8080/TTWEB_war/callback&grant_type=authorization_code";
            String tokenResponse = sendPostRequest(tokenUrl, tokenParams);
            JSONObject tokenInfo = new JSONObject(tokenResponse);
            String accessToken = tokenInfo.getString("access_token");

            String userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
            String userInfoResponse = sendGetRequest(userInfoUrl);
            JSONObject userInfo = new JSONObject(userInfoResponse);

            String id = userInfo.getString("id");
            String name = userInfo.getString("name");
            String email = userInfo.getString("email");

            // Lưu thông tin của người dùng vào cơ sở dữ liệu
            saveUserToDatabase(id, name, email);
        }
    }

    private String sendGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    private String sendPostRequest(String urlString, String params) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(params);
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        writer.close();
        reader.close();
        return response.toString();
    }

    private String extractAccessTokenFromResponse(String response) throws IOException {
        JSONObject jsonObject = new JSONObject(response);
        String accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

    private void saveUserToDatabase(String id, String name, String email) {
        // Kết nối tới cơ sở dữ liệu
        String url = "jdbc:mysql://localhost:3306/gachmen_shop";
        String username = "root";
        String password = "";
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO users (id_user, username, fullname, email, phone, address, password, role, verification_code, time_valid, verified, status, changedPassword) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, name);
            statement.setString(4, email);
            statement.setString(5, "");
            statement.setString(6, "");
            statement.setString(7, "");
            statement.setByte(8, (byte) 1);
            statement.setString(9, "");
            statement.setTimestamp(10, new Timestamp(new Date().getTime()));
            statement.setByte(11, (byte) 1);
            statement.setByte(12, (byte) 1);
            statement.setString(13, "");
            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        saveUserToDatabase("123","Trung","trung@gmail.com");
//    }
}