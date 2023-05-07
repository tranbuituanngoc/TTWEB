package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import model.Cart;
@WebServlet(name = "CheckOut", value = "/CheckOut")
public class CheckOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart c = (Cart) request.getSession().getAttribute("cart");
        if(c==null){
            response.sendRedirect("ProductLists");
        }else{
            request.getSession().setAttribute("cart",c);
            request.getRequestDispatcher("checkout2.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String provinceId = request.getParameter("")
    }
}
