package controller;

import model.Order;
import model.Voucher;
import service.VoucherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SuccessOrder", value = "/SuccessOrder")
public class SuccessOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = (Order) request.getSession().getAttribute("order");
//        request.setAttribute("order", order);
//        int paymentMethodId = order.getPaymentMethodId();
        String voucher_code = order.getVoucher_code();
        Voucher voucher = VoucherService.getVoucher(voucher_code);
        request.setAttribute("voucher", voucher);
//        request.setAttribute("paymentMethodId", paymentMethodId);
        request.getRequestDispatcher("SuccessOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
