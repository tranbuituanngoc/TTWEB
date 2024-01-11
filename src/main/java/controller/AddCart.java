package controller;

import bean.Log;
import database.DBProperties;
import model.*;

import org.jdbi.v3.core.Jdbi;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddCart", value = "/addCart")
public class AddCart extends HttpServlet {
    String url = "jdbc:mysql://" + DBProperties.getDbHost() + ":" + DBProperties.getDbPort() + "/" + DBProperties.getDbName() + "?useSSL=false";
//    Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/gachmen_shop", "root", "");
    Jdbi jdbi = Jdbi.create(url, DBProperties.getUsername(), DBProperties.getPassword());

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
        System.out.println(quantity_raw);
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
        if (cartUser == null) {
            cartUser = new CartUser();
            if (user != null) cartUser.setIdUser(user.getId_User());
        }
        if (user != null) cartUser.setIdUser(user.getId_User());

        cartUser.addCart(cart, quantity);
        if (user != null) CartService.addCart(cartUser, cart, quantity);
        session.setAttribute("cartUser", cartUser);
        String url = request.getHeader("referer");

        // Ghi log
//        Log log = new Log(Log.INFO, user != null ? user.getId_User() : "-1", "AddCart", "Thêm sản phẩm vào giỏ thành công", "success");
//        log.insert(jdbi);

        response.sendRedirect(url);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("product_id");
        if (id == null){
            id = request.getParameter("productID");
        }
        Product p = ProductService.getById(id);
        String colorId_raw = request.getParameter("color_id");
        User user = (User) request.getSession().getAttribute("user");

        String quantity_raw = request.getParameter("quantity_value");
        System.out.println(quantity_raw);
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
        if (cartUser == null) {
            cartUser = new CartUser();
            if (user != null) cartUser.setIdUser(user.getId_User());
        }
        if (user != null) cartUser.setIdUser(user.getId_User());

        cartUser.addCart(cart, quantity);
        if (user != null) CartService.addCart(cartUser, cart, quantity);
        session.setAttribute("cartUser", cartUser);
        String url = request.getHeader("referer");

        // Ghi log
        Log log = new Log(Log.INFO, user != null ? user.getId_User() : "-1", "AddCart", "Thêm sản phẩm vào giỏ thành công", "success");
        log.insert(jdbi);

        response.sendRedirect(url);
    }
}
