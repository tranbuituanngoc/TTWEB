package controller;

import Util.Email;
import model.CartUser;
import model.Order;
import model.ShippingAdress;
import model.User;
import service.CartService;
import service.OrderService;
import service.ShippingAddressService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditOrder", value = "/EditOrder")
public class EditOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idOrder = request.getParameter("orderID");
        Order order = OrderService.getOrder(idOrder);
        CartUser cartUser = CartService.getCartByIdOrder(idOrder);
        User user = new User();
        user.setId_User(order.getUserID());
        ShippingAdress shippingAdress = ShippingAddressService.getShippingAddressUser(user);
        request.setAttribute("order", order);
        request.setAttribute("cartUser", cartUser);
        request.setAttribute("shippingAdress", shippingAdress);

        request.getRequestDispatcher("/admin/EditOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStatus_raw = request.getParameter("id_status");
        String idStatusTransport_raw = request.getParameter("id_status_transport");
        int idStatus = Integer.parseInt(idStatus_raw);
        int idStatusTransport = Integer.parseInt(idStatusTransport_raw);
        String idOrder = request.getParameter("idOrder");
        String msg = OrderService.updateStatus(idStatus, idStatusTransport, idOrder);
        request.getSession().setAttribute("msgOrder", msg);
        if (msg.equals("Cập nhật thành công.")) {
            if (idStatus == 1) {
                Order order = OrderService.getOrder(idOrder);
                ShippingAdress shippingAdress = ShippingAddressService.getShippingAddressOrder(order);
                String idTransport = new RegisterTransport().registerTransport(shippingAdress.getDistrictId(), shippingAdress.getWardId());
                System.out.println(idTransport);
                order.setIdTransport(idTransport);
                Email mail = new Email();
                String content = "\n" +
                        "Track your order\n" + "Order #65000500 has been shipped! \n" + "TRACK YOUR ORDER\n" + "Expected delivery Apr 22 — 26, 2021\n" +
                        "\n" + "\n" +
                        "Polo\n" + "\n" + "Size: M\n" + "Color: White\n" + "\n" + "2 pcs\n" + "\n" + "$20\n" + "\n" + "\n" +
                        "T-shirt\n" + "\n" + "Size: M\n" + "Color: White\n" + "\n" + "1 pcs\n" + "\n" + "$20\n" + "\n" +
                        "Subtotal: $40.00\n" +
                        "Shipping: $0.00\n" +
                        "Tax: $10.00\n" +
                        "Total: $50.00\n" +
                        "\n" + "Customer: sarah_powell@domain.com\n" +
                        "\n" + "Order number: #65000500\n" +
                        "\n" + "Invoice date: Apr 17, 2021\n" +
                        "\n" + "Payment method: PayPal\n" +
                        "\n" + "Currency: USD\n" +
                        "\n" + "Shipping Method: UPS - Ground\n" +
                        "\n" + "Shipping address:\n" +
                        "\n" + "Sarah Powell,\n" +
                        "600 Montgomery St,\n" +
                        "San Francisco, CA 94111\n" +
                        "\n" + "Got a question? Email us at support@stylecasual.com or give us a call at +000 123 456.\n" +
                        "\n" + "Fb\tTw\tInst\tYt\n" + "Style Casual © 2021 Style Casual, Inc. All Rights Reserved.\n" +
                        "\n" + "4562 Hazy Panda Limits, Chair Crossing, Kentucky, US, 607898\n" +
                        "\n" + "Visit Us\n" + "Privacy Policy\n" + "Terms of Use";
                mail.sendMail(shippingAdress.getEmail(), "Đặt hàng thành công", "TrueMart gach men cao cấp đã nhận được đơn đặt hàng của bạn");
            }
            request.getSession().setAttribute("resultOrder", "true");
        } else request.getSession().setAttribute("resultOrder", "false");
        response.sendRedirect("/ListOrder");
    }
}
