package controller;

import bean.Log;
import model.CartUser;
import model.ShippingAdress;
import model.User;
import org.jdbi.v3.core.Jdbi;
import service.ShippingAddressService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "CheckOut", value = "/CheckOut")
public class CheckOut extends HttpServlet {
    Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/gachmen_shop", "root", "");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartUser c = (CartUser) request.getSession().getAttribute("cartUser");
        User user = (User) request.getSession().getAttribute("user");
        String orderId= (String) request.getAttribute("orderID");

        ShippingAdress shippingAdress = ShippingAddressService.getShippingAddressUser(user,orderId);
        if(c==null){
            response.sendRedirect("ProductLists");
        }else{
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("cartUser",c);
            request.getSession().setAttribute("shippingAdress", shippingAdress);
            request.getRequestDispatcher("checkout2.jsp").forward(request,response);
        }

        // Ghi log
        Log log = new Log(Log.INFO, user != null ? user.getId_User() : "-1", "CheckOut", "Tiến hành thanh toán", "success");
        log.insert(jdbi);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String provinceId = request.getParameter("")
    }
}
