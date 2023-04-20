package controller;

import model.CartUser;
import model.Category;
import model.Product;
import model.User;
import service.CartService;
import service.CategoryService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexProduct", value = "/Home")
public class IndexProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Product> list = ListProducts.getAllProduct();
//        List<Product>listNewProduct= ProductService.listNewProduct();
//        List<Product>listBestSeller=ProductService.listBestSeller();
        List<Product>listNewProduct= ProductService.list10NewProduct();
        List<Product>listBestSeller=ProductService.list10BestSeller();
        List<Product>listHintForYou=ProductService.listHintForYou();
        List<Category> listCategory = CategoryService.getAllCategory();
        List<Product> listCategoryProduct1 = ProductService.getCategory1();
        List<Product> listCategoryProduct2 = ProductService.getCategory2();
        List<Product> listCategoryProduct3 = ProductService.getCategory3();
        List<Product> listCategoryProduct4 = ProductService.getCategory4();
        List<Product> listSellerProduct1 = ProductService.getSeller1();
        List<Product> listSellerProduct2 = ProductService.getSeller2();
        List<Product> listSellerProduct3 = ProductService.getSeller3();
        List<Product> listSellerProduct4 = ProductService.getSeller4();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.print(user);
        if (user != null ){
            CartUser c = CartService.getCartById(user.getId_User());
            if (c == null) c = new CartUser();
            request.getSession().setAttribute("cartUser",c);
            request.getSession().setAttribute("user", user);
        }

        request.setAttribute("listNewProduct", listNewProduct);
        request.setAttribute("listBestSeller", listBestSeller);
        request.setAttribute("listHintForYou", listHintForYou);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("listCategoryProduct1", listCategoryProduct1);
        request.setAttribute("listCategoryProduct2", listCategoryProduct2);
        request.setAttribute("listCategoryProduct3", listCategoryProduct3);
        request.setAttribute("listCategoryProduct4", listCategoryProduct4);
        request.setAttribute("listSellerProduct1", listSellerProduct1);
        request.setAttribute("listSellerProduct2", listSellerProduct2);
        request.setAttribute("listSellerProduct3", listSellerProduct3);
        request.setAttribute("listSellerProduct4", listSellerProduct4);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
