package login;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginCallback")
public class CallbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String provider = request.getParameter("provider");
        String code = request.getParameter("code");

        if (provider != null) {
            if (provider.equals("Facebook")) {
                // Thực hiện xác thực Facebook và lấy thông tin người dùng

                // Sau khi xác thực thành công và lấy thông tin người dùng, bạn có thể lưu thông tin người dùng vào session
                User user = new User();
                user.setUserName("Tên đăng nhập của người dùng từ Facebook");

                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                response.sendRedirect("Home"); // Chuyển hướng đến trang tương tự như đăng nhập bình thường
            } else if (provider.equals("Google")) {
                // Thực hiện xác thực Google và lấy thông tin người dùng

                // Sau khi xác thực thành công và lấy thông tin người dùng, bạn có thể lưu thông tin người dùng vào session
                User user = new User();
                user.setUserName("Tên đăng nhập của người dùng từ Google");

                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                response.sendRedirect("Home"); // Chuyển hướng đến trang tương tự như đăng nhập bình thường
            } else {
                request.setAttribute("error", "Invalid provider");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Missing provider");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}