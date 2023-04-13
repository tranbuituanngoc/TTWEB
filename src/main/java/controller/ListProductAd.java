package controller;

import model.Product;
import model.ProductCategory;
import model.ProductColor;
import model.ProductSize;
import service.ProductCategoryService;
import service.ProductColorService;
import service.ProductService;
import service.ProductSizeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListProductAd", value = "/ListProductAd")
public class ListProductAd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product>list = ProductService.getAll();
        request.setAttribute("listP",list);
        request.getRequestDispatcher("admin/ManageProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
