package controller;

import com.google.gson.Gson;
import model.Order;
import model.Product;
import service.DashboardService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DashboardController", value = "/thong-ke")
public class DashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int revalue= DashboardService.selectRevenueInMonth();
        int sales= DashboardService.selectSalesInMonth();
        int regisInMonth= DashboardService.selectRegisterInMonth();
        int orderInMonth= DashboardService.selectOrderInMonth();

        Map<String, Integer> sales6M= DashboardService.selectSalesInSixMonth();
        Map<String, Integer> sales10D= DashboardService.selectSalesInTenDay();
        Map<String, Integer> bestCate= DashboardService.selectBestSellerCategory();
        List<Order> recentOrder= DashboardService.getRecentOrder();

        Gson gson = new Gson();
        String jsonSales6M = gson.toJson(sales6M);
        String jsonSales10D = gson.toJson(sales10D);
        String jsonBestCate = gson.toJson(bestCate);

        Map<Product, Integer> bestProduct= DashboardService.selectBestSellerProduct();

        request.setAttribute("revalue", revalue);
        request.setAttribute("sales", sales);
        request.setAttribute("regisInMonth", regisInMonth);
        request.setAttribute("orderInMonth", orderInMonth);

        request.setAttribute("jsonSales6M", jsonSales6M);
        request.setAttribute("jsonSales10D", jsonSales10D);
        request.setAttribute("jsonBestCate", jsonBestCate);
        request.setAttribute("bestProduct", bestProduct);
        request.setAttribute("recentOrder", recentOrder);

        request.getRequestDispatcher("admin/index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
