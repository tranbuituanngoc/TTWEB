package controller;

import model.Category;
import model.Product;
import service.CategoryService;

import service.ProductImportedService;

import service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetail", value = "/ProductDetail")
public class ProductDetail extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                response.setContentType("Text/html;charset=UTF-8");
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

                ProductService service = new ProductService();
                List<Product> products  = service.getProducts();

                List<Product> listHintForYou = products.subList(0, Math.min(10, products.size()));
                List<Category> listCategory = CategoryService.getAllCategory();
                int quantity = ProductImportedService.getQuantityDetail(productID, sizeId, colorId);

                request.setAttribute("product", p);
                request.setAttribute("listHintForYou", listHintForYou);
                request.setAttribute("listCategory", listCategory);
                request.setAttribute("color_id", colorId);

                request.setAttribute("size_id", sizeId);
                request.setAttribute("price", price);
                request.setAttribute("quantity", quantity);

                request.getRequestDispatcher("product-detail1.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {

        }
}
