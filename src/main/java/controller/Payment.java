package controller;

import model.Product;
import model.Cart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "Payment", value = "/Payment")
public class Payment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart c = (Cart) request.getSession().getAttribute("cart");
        if(c==null){
            response.sendRedirect("ProductLists");
        }else{
            request.getSession().setAttribute("cart",c);
            response.sendRedirect("CheckOut");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
