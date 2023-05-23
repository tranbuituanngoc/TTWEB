package filter;

import model.User;
import service.RoleService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CartShippingPermission")
public class CartShippingPermission implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute("user");
        if (user != null && RoleService.checkCartPermission(user) && RoleService.checkShippingAddressPermission(user)) chain.doFilter(request, response);
        else httpRequest.getRequestDispatcher("404.jsp").forward(request, response);
    }
}
