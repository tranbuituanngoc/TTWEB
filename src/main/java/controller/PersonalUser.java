package controller;

import bean.OrderDetail;
import bean.User;
import model.UserSession;
import service.OrderDetailService;
import service.OrderService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PersonalUser", value = "/personalUser")
public class PersonalUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserSession u = UserSession.getUS(session);
        String idUser = u.getUserId();
        User us = UserService.getByIdUser(idUser);
        int total = 0;
        List<OrderDetail> listH = OrderDetailService.historyBuy(idUser);
        List<OrderDetail> listNot = OrderService.getOrderNotDeliver(idUser);
        for (OrderDetail o : listNot) {
            total += o.getTotalPrice();
        }
        request.setAttribute("user", us);
        request.setAttribute("listHis", listH);
        request.setAttribute("listNot", listNot);
        request.setAttribute("total", total);
        request.getRequestDispatcher("account-info.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
