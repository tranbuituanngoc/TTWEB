package controller;

import model.Product;
import model.User;
import model.Cart;
import service.OrderDetailService;
import service.OrderService;
import tool.SendToMail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "CreateOrder", value = "/CreateOrder")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        HttpSession session= request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            request.setAttribute("err", "Vui lòng đăng nhập trước khi đặt hàng *");
            request.getRequestDispatcher("Payment").forward(request, response);
        } else {
            String idUser = u.getId_User();
            if (fullname.equals("") || address.equals("") || email.equals("") || phone.equals("")) {
                request.setAttribute("err", "Vui lòng điền đầy đủ vào những mục có đánh *");
                request.getRequestDispatcher("Payment").forward(request, response);

            } else {
                session = request.getSession();
                session.getAttribute("cart");
//                Cart c = Cart.getCart(session);
//                Collection<Product> products = c.getData();
//                List<Product> pro = new ArrayList<Product>(products);
//                OrderService.insertOrder(idUser, fullname, (int) c.total(), address, phone, email);

                request.setAttribute("msg", "Bạn đã đặt hàng thành công");
                response.sendRedirect("ProductLists");
//                OrderDetailService.insertOrderDetail(pro);
                SendToMail mail = new SendToMail();
                mail.sendEmail(email, "TrueMart-Order", "TrueMart gach men cao cấp đã nhận được đơn đặt hàng của bạn");
                Cart newCart = new Cart();
//                newCart.commit(session);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
