package controller;

import bean.Log;
import model.Order;
import model.Voucher;
import org.jdbi.v3.core.Jdbi;
import service.VoucherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SuccessOrder", value = "/SuccessOrder")
public class SuccessOrder extends HttpServlet {
    Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/gachmen_shop", "root", "");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = (Order) request.getSession().getAttribute("order");
//        request.setAttribute("order", order);
//        int paymentMethodId = order.getPaymentMethodId();
        Log log = new Log(Log.INFO, order.getUserID(), "SuccessOrder", "Đặt hàng thành công", "success");
        log.insert(jdbi);
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
