package controller;

import model.CartUser;
import model.ShippingAdress;
import model.User;
import service.ShippingAddressService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "CheckOut", value = "/CheckOut")
public class CheckOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartUser c = (CartUser) request.getSession().getAttribute("cartUser");
        User user = (User) request.getSession().getAttribute("user");
        ShippingAdress shippingAdress = ShippingAddressService.getShippingAddressUser(user);
        if(c==null){
            response.sendRedirect("ProductLists");
        }else{
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("cartUser",c);
            request.getSession().setAttribute("shippingAdress", shippingAdress);
            request.getRequestDispatcher("checkout2.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String provinceId = request.getParameter("")
    }
}
