package login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String provider = request.getParameter("provider");

        if (provider.equals("Facebook")) {
            String facebookLoginUrl = "https://www.facebook.com/v12.0/dialog/oauth?client_id=526446266364446&redirect_uri=http://localhost:8080/TTWEB_war/&scope=public_profile,email";
            response.sendRedirect(facebookLoginUrl);
        } else if (provider.equals("Google")) {
            String googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id=524926861428-2uub0irutasgfqkmutpn37p8sltupgre.apps.googleusercontent.com&redirect_uri=http://localhost:8080/TTWEB_war/&scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email&response_type=code";
            response.sendRedirect(googleLoginUrl);
        } else {
            request.setAttribute("error", "Invalid provider");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}