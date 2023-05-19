package controller;

import model.CartUser;
import model.Voucher;
import service.VoucherService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "form_discount_add", value = "/form_discount_add")
public class form_discount_add extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String voucher_code = request.getParameter("discount.code");
        Voucher voucher = VoucherService.getVoucher(voucher_code);
        CartUser cartUser = (CartUser) request.getSession().getAttribute("cartUser");
        int value = cartUser.getTotalValue();
        System.out.println(value);
        System.out.println(voucher);
        if (voucher.getCondition() > value) {
            request.setAttribute("messageResponseVoucher", "fail");
        }
        else if (voucher.getCondition() < value)  {
            request.setAttribute("messageResponseVoucher", "success");
            request.setAttribute("voucher",voucher);
//            request.setAttribute("voucher",voucher);
        }

        request.getRequestDispatcher("checkout2.jsp").forward(request, response);

    }
}
