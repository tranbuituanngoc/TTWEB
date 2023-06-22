package controller;

import okhttp3.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "form_update_shipping_method", value = "/form_update_shipping_method")
public class form_update_shipping_method extends HttpServlet {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String wardId = request.getParameter("wardId");
        String districtId = request.getParameter("districtId");

        URL url = new URL("http://140.238.54.136/api/auth/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "20130471@st.hcmuaf.edu.vn");
        loginJson.put("password", "12345678");

        String requestBody = loginJson.toString();

        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(requestBody.getBytes());
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            in.close();
            // Lấy bearer token từ phản hồi của API
            JSONObject tokenJson = new JSONObject(stringBuilder.toString());
//            System.out.println(tokenJson);
            String bearerToken = tokenJson.getString("access_token");
//            System.out.println(bearerToken);
            // Sử dụng bearer token để gọi API khác

            // Tạo URL API và thiết lập kết nối

            OkHttpClient client = new OkHttpClient();
            String urlLeadTime = "http://140.238.54.136/api/leadTime";
            String urlCalculateFee = "http://140.238.54.136/api/calculateFee";
            // Tạo JSON object chứa dữ liệu body
            JSONObject json = new JSONObject();
            json.put("from_district_id", "3695");
            json.put("from_ward_id", "90737");
            json.put("to_district_id", districtId);
            json.put("to_ward_id", wardId);
            json.put("height", "100");
            json.put("length", "100");
            json.put("width", "100");
            json.put("weight", "100");

            RequestBody requestBodyLeadTime = RequestBody.create(MediaType.parse("application/json"), json.toString());
            Request requestLeadTime = new Request.Builder()
                    .url(urlLeadTime)
                    .header("Authorization", "Bearer "+bearerToken) // thay your_token_here bằng mã xác thực
                    .post(requestBodyLeadTime)
                    .build();

            Response responseLeadTime = client.newCall(requestLeadTime).execute();
            String responseLead = responseLeadTime.body().string();
//            System.out.println(responseLead);

            RequestBody requestBodyCalculateFee = RequestBody.create(MediaType.parse("application/json"), json.toString());
            Request requestCalculateFee = new Request.Builder()
                    .url(urlCalculateFee)
                    .header("Authorization", "Bearer "+bearerToken) // thay your_token_here bằng mã xác thực
                    .post(requestBodyCalculateFee)
                    .build();

            Response responseCalculateFee = client.newCall(requestCalculateFee).execute();
            String responseFee = responseCalculateFee.body().string();
//            System.out.println(responseFee);

            JSONObject leadTime = new JSONObject(responseLead);
            JSONObject fee= new JSONObject(responseFee);
//            System.out.println(leadTime.toString());
//            System.out.println(fee.toString());
            System.out.println(leadTime.get("data"));
            System.out.println(fee.get("data"));

            JSONObject result = new JSONObject();
            result.put("status", "success");
            result.put("message", "Đã nhận được dữ liệu");
            result.put("leadTime", leadTime.get("data").toString());
            result.put("fee", fee.get("data").toString());
            System.out.println(result.toString());
            // Gửi dữ liệu trả về cho client
            PrintWriter out = response.getWriter();
            out.write(result.toString());
            out.flush();
        } else {
            // Xử lý lỗi khi gửi yêu cầu đến API
            System.out.println("Lỗi khi gửi yêu cầu: " + responseCode);
        }

    }
}
