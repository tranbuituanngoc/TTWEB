package controller;

import Util.Email;
import Util.Encode;
import Util.SaltString;
import model.User;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

@WebServlet(name = "UserController", value = "/nguoi-dung")
public class UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("dang-nhap")) {
            Login(request, response);
        } else if (action.equals("dang-xuat")) {
            Logout(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void Login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            pass = Encode.encodeToSHA1(pass);

            request.setAttribute("username", username);

            User user = new User();
            user.setUserName(username);
            user.setPass(pass);

            UserService userDAO = new UserService();
            User u = userDAO.selectByUserNameAndPass(user);
            String url = "";
            if (u != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", u);
                int r=u.getRole();
                if(u.getRole()==1){
                    url = "/admin/index.jsp";
                }else if(u.getRole()==2){
                    System.out.println("2");
                    url = "/index.jsp";
                }
            } else {
                System.out.println("3");
                request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
                url = "/login.jsp";
            }
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            // remove session
            session.invalidate();

            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            response.sendRedirect(url + "/index.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
