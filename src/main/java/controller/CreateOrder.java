package controller;

import Util.Email;
import model.CartUser;
import model.Order;
import model.ShippingAdress;
import model.User;
import service.CartService;
import service.OrderService;
import service.ShippingAddressService;
import service.VoucherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

@WebServlet(name = "CreateOrder", value = "/CreateOrder")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String newId = "DH" + String.format("%05d", randomNumber);
        Order order = new Order();
        String leadTime_raw = request.getParameter("lead-time");
        String fee_raw = request.getParameter("service-fee");
        String voucherCode = request.getParameter("voucher");
        CartUser cartUser = (CartUser) request.getSession().getAttribute("cartUser");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("userInvalid", true);
            response.sendRedirect("CheckOut");
        }
        String paymentMethod_raw = request.getParameter("payment-method");
        Timestamp leadTime = null;
        int fee = 0;
        int discount = 0;
        if (leadTime_raw != null) leadTime = new Timestamp(Long.parseLong(leadTime_raw) * 1000L);
        if (fee_raw != null) fee = Integer.parseInt(fee_raw);
        if (voucherCode != null) discount = VoucherService.getVoucher(voucherCode).getDiscount();
        int paymentMethod = 0;
        if (paymentMethod_raw != null) paymentMethod = Integer.parseInt(paymentMethod_raw);
        order.setShipping_cost(fee);
        order.setShipping_time(leadTime);
        order.setTotalPrice(fee + cartUser.getTotalValue() - discount);
        if (user != null) order.setUserID(user.getId_User());
        order.setOrderID(newId);
        order.setPaymentMethodId(paymentMethod);
        order.setVoucher_code(voucherCode);
        ShippingAdress shippingAdress = (ShippingAdress) request.getSession().getAttribute("shippingAddress");
        System.out.println(shippingAdress);
        if (shippingAdress == null) {
            String fullname = request.getParameter("billing_address[full_name]");
            String address = request.getParameter("billing_address[address1]");
            String email = request.getParameter("checkout_user[email]");
            String phone = request.getParameter("billing_address[phone]");
            String provinceId_raw = request.getParameter("selected_customer_shipping_province");
            String districtId_raw = request.getParameter("selected_customer_shipping_district");
            String wardId_raw = request.getParameter("selected_customer_shipping_ward");
            int ward_id = 0;
            int province_id = 0;
            int district_id = 0;
            if (wardId_raw != null) ward_id = Integer.parseInt(wardId_raw);
            if (provinceId_raw != null) province_id = Integer.parseInt(provinceId_raw);
            if (districtId_raw != null) district_id = Integer.parseInt(districtId_raw);
            shippingAdress = new ShippingAdress();
            shippingAdress.setAddress(address);
            shippingAdress.setEmal(email);
            shippingAdress.setDistrictId(district_id);
            shippingAdress.setPhone(phone);
            shippingAdress.setFullName(fullname);
            shippingAdress.setProvinceId(province_id);
            shippingAdress.setWardId(ward_id);
            if (user != null) shippingAdress.setUserId(user.getId_User());
            System.out.println(shippingAdress);
            if (user != null) ShippingAddressService.addShippingAddress(shippingAdress);
        }
        if (user != null) {
            CartService.setCartOrder(user.getId_User(), order.getOrderID());
        }


        if (OrderService.updateQuantity(order) == -1) {
            request.getSession().setAttribute("errorQuantity", true);
            response.sendRedirect("CheckOut");
            CartService.removeCartOrder(user.getId_User());
        }
        if (OrderService.updateQuantity(order) == 1) {
//            sử dụng api logistic
//            String idTransport = new RegisterTransport().registerTransport(shippingAdress.getDistrictId(), shippingAdress.getWardId());
//            System.out.println(idTransport);
//            order.setIdTransport(idTransport);
            OrderService.addOrder(order);
            request.getSession().setAttribute("shippingAdress", shippingAdress);
            request.getSession().setAttribute("order", order);
            System.out.println("success");
            response.sendRedirect("SuccessOrder");
            Email mail = new Email();
            mail.sendMail(user.getEmail(), "TrueMart-Order", "TrueMart gach men cao cấp đã nhận được đơn đặt hàng của bạn");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
