package controller;

import model.*;
import service.CartService;
import service.ProductColorService;
import service.ProductService;
import service.ProductSizeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateCart", value = "/UpdateCart")
public class UpdateCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("product_id");
        Product p = ProductService.getById(id);
        String colorId_raw = request.getParameter("id_color");
        String quantity_raw = request.getParameter("quantity_value");
        String sizeId_raw = request.getParameter("id_size");
        System.out.println("quantity ="+quantity_raw);
        int quantity  =1;
        if (quantity_raw != null){
            quantity = Integer.parseInt(quantity_raw);
        }
        int colorId = p.getColor().get(0).getId_color();
        if (colorId_raw != null) {
           colorId = Integer.parseInt(colorId_raw);
        }

        int sizeId = p.getSize().get(0).getIdSize();
        if (sizeId_raw != null) {
           sizeId = Integer.parseInt(sizeId_raw);
        }
        Cart cart = new Cart();
        Color color = ProductColorService.getColorById(colorId);
        Size size = ProductSizeService.getSizeById(sizeId);
        cart.setColor(color);
        cart.setSize(size);
        cart.setProduct(p);

        HttpSession session = request.getSession();
        CartUser cartUser = (CartUser) session.getAttribute("cartUser");
        cartUser.updateQuantity(cart, quantity);
        session.removeAttribute("cartUser");
        session.setAttribute("cartUser", cartUser);
        CartService.addCart(cartUser);
        response.sendRedirect("Cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
