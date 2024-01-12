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
        ProductService service = new ProductService();
        List<Product> products  = service.getProducts();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        System.out.print(user);
        if (user != null ){
            CartUser c = CartService.getCartById(user.getId_User());
            if (c == null) c = new CartUser();
            request.getSession().setAttribute("cartUser",c);
            request.getSession().setAttribute("user", user);
        }
        List<Category> listCategory = CategoryService.getAllCategory();
        request.setAttribute("listNewProduct", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listBestSeller", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listHintForYou", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("listCategoryProduct1", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listCategoryProduct2", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listCategoryProduct3", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listCategoryProduct4", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listSellerProduct1", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listSellerProduct2", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listSellerProduct3", products.subList(0, Math.min(10, products.size())));
        request.setAttribute("listSellerProduct4", products.subList(0, Math.min(10, products.size())));

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
