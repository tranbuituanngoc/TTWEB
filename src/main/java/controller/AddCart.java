package controller;

import model.*;

import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCart", value = "/addCart")
public class AddCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("product_id");
        if (id == null){
            id = request.getParameter("productID");
        }
        Product p = ProductService.getById(id);
        String colorId_raw = request.getParameter("color_id");
        User user = (User) request.getSession().getAttribute("user");

        String quantity_raw = request.getParameter("quantity_value");

        int quantity  =1;
        if (quantity_raw != null){
            quantity = Integer.parseInt(quantity_raw);
        }
        int colorId = p.getColor().get(0).getId_color();
        if (colorId_raw != null) {
           colorId = Integer.parseInt(colorId_raw);
        }
        String sizeId_raw = request.getParameter("size");
        int sizeId = p.getSize().get(0).getIdSize();
        if (sizeId_raw != null) {
           sizeId = Integer.parseInt(sizeId_raw);
        }

        int price = ProductImportedService.getPrice(id, sizeId, colorId);
        Cart cart = new Cart();
        Color color = ProductColorService.getColorById(colorId);
        Size size = ProductSizeService.getSizeById(sizeId);
        p.setPrice(price);
        cart.setColor(color);
        cart.setSize(size);
        cart.setProduct(p);
        HttpSession session = request.getSession();
        CartUser cartUser = (CartUser) session.getAttribute( "cartUser");
        if (cartUser.getIdUser() == null) {
            cartUser = new CartUser();
            cartUser.setIdUser(user.getId_User());
        }
        cartUser.addCart(cart, quantity);
        session.setAttribute("cartUser", cartUser);
        String url = request.getHeader("referer");
        response.sendRedirect(url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
