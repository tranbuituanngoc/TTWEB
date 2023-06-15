package controller;

import model.CartUser;
import service.CartService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SaveCart", value = "/SaveCart")
public class SaveCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        CartUser cartUser = (CartUser) session.getAttribute( "cartUser");
//        if (cartUser.getIdUser() != null) {
//             CartService.addCart(cartUser);
//        }
//        response.sendRedirect("Cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
