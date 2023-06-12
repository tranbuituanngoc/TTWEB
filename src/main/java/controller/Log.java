package controller;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServlet;
import model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class Log extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Log.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action.equals("Login")) {
            // Lấy tên đăng nhập và mật khẩu của người dùng từ request
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Kiểm tra tên đăng nhập và mật khẩu của người dùng
            if (username.equals("admin") && password.equals("admin")) {
                // Tạo đối tượng User để sử dụng trong log message
                User user = new User(username);

                // Lấy địa chỉ IP của người dùng
                String ipAddress = request.getRemoteAddr();

                // Ghi log cho sự kiện đăng nhập của người dùng
                logger.info("User " + user.getUserName() + " logged in at " + new Date() + " from IP address " + ipAddress);

                // Lưu thông tin người dùng vào session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect đến trang chủ
                response.sendRedirect("Home");
            } else {
                // Ghi log cho sự kiện đăng nhập thất bại của người dùng
                logger.warn("Failed login attempt for user " + username);

                // Hiển thị thông báo lỗi đăng nhập
                response.sendRedirect("Login?error=true");
            }
        } else if (action.equals("Register")) {
            // Lấy thông tin người dùng từ request
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Tạo đối tượng User để sử dụng trong log message
            User user = new User(username);

            // Ghi log cho sự kiện đăng ký của người dùng
            logger.info("User " + user.getUserName() + " registered at " + new Date());

            // Lưu thông tin người dùng vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect đến trang chủ
            response.sendRedirect("Home");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Ghi log cho sự kiện đăng xuất của người dùng
            logger.info("User " + user.getUserName() + " logged out at " + new Date());

            // Xóa thông tin người dùng khỏi session
            session.removeAttribute("user");
        }

        // Redirect đến trang đăng nhập
        response.sendRedirect("Login");
    }
}
