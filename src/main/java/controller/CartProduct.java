package controller;

import model.CartUser;
import model.Product;
import model.Cart;
import model.User;
import service.CartService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "CartProduct", value = "/Cart")
public class CartProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        CartUser c = (CartUser) request.getSession().getAttribute("cartUser");
        if (c == null){
            c = CartService.getCartById(user.getId_User());
        }
        if(c==null){
            response.sendRedirect("ProductLists");
        }else{
            request.setAttribute("listCart",c);
//            System.out.println(c);
            request.getSession().setAttribute("cartUser",c);
            request.getRequestDispatcher("cart.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
