package controller;

import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetail", value = "/ProductDetail")
public class ProductDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("Text/html;charset=UTF-8");
        String productID = request.getParameter("productID");
        String colorId_raw = request.getParameter("color");
        Product p = ProductService.getProductDetail(productID);
        int colorId = p.getColor().get(0).getId_color();
        if (colorId_raw != null) {
           colorId = Integer.parseInt(colorId_raw);
        }
        List<Product> listHintForYou=ProductService.listHintForYou();
        List<Category> listCategory = CategoryService.getAllCategory();

        request.setAttribute("product",p);
        request.setAttribute("listHintForYou", listHintForYou);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("color_id", colorId);
        request.getRequestDispatcher("product-detail1.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
