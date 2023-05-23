package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "HomeFilter")
public class HomeFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Kiểm tra nếu truy cập trống "/"
        if (requestURI.equals("/")) {
            // Thực hiện chuyển hướng đến trang khác
            httpResponse.sendRedirect("/Home"); // Thay "/new-page" bằng URL muốn chuyển hướng

            return;
        }
        chain.doFilter(request, response);

    }
}
