package controller;

import model.*;
import service.ProductColorService;
import service.ProductService;
import service.ProductSizeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteCart", value = "/DeleteCart")
public class DeleteCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("product_id");
        Product p = ProductService.getById(id);
        String colorId_raw = request.getParameter("id_color");
        int colorId = p.getColor().get(0).getId_color();
        if (colorId_raw != null) {
           colorId = Integer.parseInt(colorId_raw);
        }
        String sizeId_raw = request.getParameter("id_size");
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
//        System.out.println(id);
//        System.out.println(sizeId);
//        System.out.println(colorId);

        HttpSession session = request.getSession();
        CartUser cartUser = (CartUser) session.getAttribute("cartUser");
        cartUser.removeItem(cart);
        session.removeAttribute("cartUser");
        session.setAttribute("cartUser", cartUser);
        response.sendRedirect("SaveCart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
