package service;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/oauth2callback")
public class OAuth2CallbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = request.getParameter("error");

        if (error != null) {
            request.setAttribute("error", "Authentication failed: " + error);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        String provider = (String) request.getSession().getAttribute("provider");

        if (provider.equals("Facebook")) {
            User user = new User();
            user.setUserName(user.getUserName());
            user.setEmail(user.getEmail());
            user.setPass("");
            user.setRole(0);
            user.setFullname("");
            user.setStatus(true);
            UserService userService = new UserService();
            userService.saveUser(user);
            request.getSession().setAttribute("user", user);
            request.getRequestDispatcher("/account-info.jsp").forward(request, response);
        } else if (provider.equals("Google")) {
            User user = new User();
            user.setUserName(user.getUserName());
            user.setEmail(user.getEmail());
            user.setPass("");
            user.setRole(0);
            user.setFullname("");
            user.setStatus(true);
            UserService userService = new UserService();
            userService.saveUser(user);
            request.getSession().setAttribute("user", user);
            request.getRequestDispatcher("/account-info.jsp").forward(request, response);
        }
    }
}