package controller;
import okhttp3.*;

import org.json.*;

import java.io.IOException;
public class RegisterTransport {
    public String registerTransport(int districtId, int wardId){
        OkHttpClient client = new OkHttpClient();
        String result = "";
        // Đăng nhập và lấy Bearer token
        String json = "{ \"email\": \"20130471@st.hcmuaf.edu.vn\", \"password\": \"12345678\" }";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url("http://140.238.54.136/api/auth/login")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            String token = jsonObject.getString("access_token");

            // Sử dụng Bearer token để gửi yêu cầu đăng ký vận chuyển
            JSONObject jsonData = new JSONObject();
            jsonData.put("from_district_id", "3695");
            jsonData.put("from_ward_id", "90737");
            jsonData.put("to_district_id", String.valueOf(districtId));
            jsonData.put("to_ward_id", String.valueOf(wardId));
            jsonData.put("height", "100");
            jsonData.put("length", "100");
            jsonData.put("width", "100");
            jsonData.put("weight", "100");
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonData.toString());

            Request request2 = new Request.Builder()
                    .url("http://140.238.54.136/api/registerTransport")
                    .header("Authorization", "Bearer " + token)
                    .post(requestBody)
                    .build();

            try (Response response2 = client.newCall(request2).execute()) {
                String responseBody2 = response2.body().string();
                System.out.println(responseBody2);
                JSONObject jsonObject1 = new JSONObject(responseBody2);
                result = jsonObject1.getJSONObject("Transport").getString("id");
                System.out.println(result);
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return result;
    }

    public static void main(String[] args) {
//        new RegisterTransport().registerTransport(2270, 231013);
    }
}
