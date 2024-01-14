package controller;

import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Refund", value = "/hoan-tien")
public class Refund extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStatus_raw = request.getParameter("id_status");
        String idStatusTransport_raw = request.getParameter("id_status_transport");
        String idOrder = request.getParameter("idOrder");

        String msg = OrderService.updateStatus(Integer.parseInt(idStatus_raw), Integer.parseInt(idStatusTransport_raw), idOrder);
        boolean msgR;
        if (msg.equals("Cập nhật thành công.")) {
            msgR = true;
        } else {
            msgR = false;
        }
        System.out.println(msgR);
        System.out.println(idOrder);
        request.setAttribute("msgRefund", msgR);
        request.setAttribute("idOrder",idOrder);
        request.getRequestDispatcher("/refund.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
