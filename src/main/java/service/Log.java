package service;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServlet;
import model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class Log extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Log.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy tên đăng nhập của người dùng từ request
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        // Kiểm tra tên đăng nhập và mật khẩu của người dùng
        if (username.equals("admin") && pass.equals("admin")) {
            // Tạo đối tượng User để sử dụng trong log message
            User user = new User();

            // Lấy địa chỉ IP của người dùng
            String ipAddress = request.getRemoteAddr();

            // Ghi log cho sự kiện đăng nhập của người dùng
            logger.info("User " + user.getUserName() + " logged in at " + new Date() + " from IP address " + ipAddress);

            // Redirect đến trang chủ
            response.sendRedirect("Home");
        } else {
            // Ghi log cho sự kiện đăng nhập thất bại của người dùng
            logger.warn("Failed login attempt for user " + username);

            // Hiển thị thông báo lỗi
            response.sendRedirect("login.jsp?error=1");
        }
    }
}