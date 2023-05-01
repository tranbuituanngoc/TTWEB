package controller;

import model.Product;
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import service.CategoryService;
import service.ProductImportedService;
=======
>>>>>>> 4bddbfb357a0bba29aca122187d53c2bbaf11471
>>>>>>> Stashed changes
import service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProductDetail", value = "/ProductDetail")
public class ProductDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("Text/html;charset=UTF-8");
<<<<<<< Updated upstream

        String productID = request.getParameter("productID");
        Product p = ProductService.getProductDetail(productID);
        request.setAttribute("product",p);
        request.getRequestDispatcher("product-detail.jsp").forward(request,response);

=======
<<<<<<< HEAD
        String productID = request.getParameter("productID");
        String colorId_raw = request.getParameter("color");
        String sizeId_raw = request.getParameter("size");

        Product p = ProductService.getProductDetail(productID);
        int colorId = p.getColor().get(0).getId_color();
        int sizeId = p.getSize().get(0).getIdSize();
        if (colorId_raw != null) {
           colorId = Integer.parseInt(colorId_raw);
        }
        if (sizeId_raw != null) {
           sizeId = Integer.parseInt(sizeId_raw);
        }
        int price = ProductImportedService.getPrice(productID, sizeId, colorId);
        List<Product> listHintForYou=ProductService.listHintForYou();
        List<Category> listCategory = CategoryService.getAllCategory();
        int quantity = ProductImportedService.getQuantityDetail(productID, sizeId, colorId);
        request.setAttribute("product",p);
        request.setAttribute("listHintForYou", listHintForYou);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("color_id", colorId);
        request.setAttribute("size_id", sizeId);
        request.setAttribute("price", price);
         request.setAttribute("quantity", quantity);
        request.getRequestDispatcher("product-detail1.jsp").forward(request,response);
=======

        String productID = request.getParameter("productID");
        Product p = ProductService.getProductDetail(productID);
        request.setAttribute("product",p);
        request.getRequestDispatcher("product-detail.jsp").forward(request,response);

>>>>>>> 4bddbfb357a0bba29aca122187d53c2bbaf11471
>>>>>>> Stashed changes
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
