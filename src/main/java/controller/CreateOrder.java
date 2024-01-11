package controller;

import Util.ElectronicSignature;
import Util.Email;
import database.DBProperties;
import model.CartUser;
import model.Order;
import model.ShippingAdress;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

@WebServlet(name = "CreateOrder", value = "/CreateOrder")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String newId = "DH" + String.format("%05d", randomNumber);
        Order order = new Order();
        // sửa đoạn này
        Date todaysDate = new Date(new java.util.Date().getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(todaysDate);
        //set expiry date is 10 minute
        c.add(Calendar.DATE, 3);
        Timestamp expiry_date = new Timestamp(c.getTimeInMillis());

//        String leadTime_raw = request.getParameter("lead-time");
//        String fee_raw = request.getParameter("service-fee");
        String voucherCode = request.getParameter("voucher");
        CartUser cartUser = (CartUser) request.getSession().getAttribute("cartUser");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("userInvalid", true);
            response.sendRedirect("/CheckOut");
        }
        String paymentMethod_raw = request.getParameter("payment-method");
        Timestamp leadTime = null;
        int fee = 0;
        int discount = 0;
//        if (leadTime_raw != null) leadTime = new Timestamp(Long.parseLong(leadTime_raw) * 1000L);
        leadTime = expiry_date;
//        if (fee_raw != null) fee = Integer.parseInt(fee_raw);
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
//        System.out.println(shippingAdress);
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
//            System.out.println(shippingAdress);
            if (user != null) ShippingAddressService.addShippingAddress(shippingAdress);
        }
        if (user != null) {
            CartService.setCartOrder(user.getId_User(), order.getOrderID());
        }
        int updateQuantityStatus = OrderService.updateQuantity(order);
        if (updateQuantityStatus == -1) {
            request.getSession().setAttribute("errorQuantity", true);
            CartService.removeCartOrder(order.getOrderID());

            response.sendRedirect("/CheckOut");
            return;
        }
        if (updateQuantityStatus == 1) {
            OrderService.addOrder(order);
            request.getSession().setAttribute("shippingAdress", shippingAdress);
            request.getSession().setAttribute("order", order);

//            Tạo đối tượng JSON để hash đơn hàng
            JSONObject orderJson = new JSONObject();
            JSONArray productList = new JSONArray();
            JSONObject product;
            for (int i = 0; i < cartUser.getCart().size(); i++) {
                product = new JSONObject();
                product.put("Product Name", cartUser.getCart().get(i).getProduct().getProductName());
                product.put("Quantity", cartUser.getValue().get(i));
                product.put("Price", cartUser.getCart().get(i).getProduct().getPriceAfterSale() * cartUser.getValue().get(i));

                productList.put(product);
            }
            String paymentMethodString;
            switch (order.getPaymentMethodId()) {
                case 1002252882: {
                    paymentMethodString = "Thanh toán tiền mặt khi giao hàng (COD)";
                    break;
                }
                case 1002252884: {
                    paymentMethodString = "Thanh toán chuyển khoản qua ngân hàng";
                    break;
                }
                default:
                    paymentMethodString = "Momo";
            }

            orderJson.put("Order ID", order.getOrderID());
            orderJson.put("Customer Name", shippingAdress.getFullName());
            orderJson.put("Email", shippingAdress.getEmail());
            orderJson.put("Phone", shippingAdress.getPhone());
            orderJson.put("Payment Method", paymentMethodString);
            orderJson.put("Product List", productList);
            orderJson.put("Total Price", order.getTotalPrice());

//            Chuyển chuỗi JSON thành String để hash
            String orderString = orderJson.toString();
            String hashOrder = ElectronicSignature.hashString(orderString);

//            Lấy ra public key của người dùng và tiến hành mã hóa chuỗi Hash bằng giải thuật RSA với public key của người dùng
            String publicKey = KeyUserService.getPublicKeyWithUserID(order.getUserID());
            String electricSignature = "";
            try {
                electricSignature = ElectronicSignature.encryptAsymmetric(hashOrder, publicKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

//            Gửi email cho người dùng và tiến hành update chữ ký lên csdl
            Email.sendMailWithE_Sign(user.getEmail(), "Đặt hàng thành công!", getSuccessEmail(user.getUserName()), electricSignature);
            OrderService.updateE_SignOrder(hashOrder, order.getOrderID());
            System.out.println("success");
            response.sendRedirect("/SuccessOrder");
        }
    }

    public static String getSuccessEmail(String userName) {
        String domain = DBProperties.getMainDomain();
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"vi\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <title>New Template</title>\n" +
                "    <!--[if (mso 16)]><style type=\"text/css\">     a {text-decoration: none;}     </style><![endif]-->\n" +
                "    <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\n" +
                "    <!--[if gte mso 9]><xml> <o:OfficeDocumentSettings> <o:AllowPNG></o:AllowPNG> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml>\n" +
                "    <![endif]-->\n" +
                "    <style type=\"text/css\">\n" +
                "        #outlook a {\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        \n" +
                "        .es-button {\n" +
                "            mso-style-priority: 100!important;\n" +
                "            text-decoration: none!important;\n" +
                "        }\n" +
                "        \n" +
                "        a[x-apple-data-detectors] {\n" +
                "            color: inherit!important;\n" +
                "            text-decoration: none!important;\n" +
                "            font-size: inherit!important;\n" +
                "            font-family: inherit!important;\n" +
                "            font-weight: inherit!important;\n" +
                "            line-height: inherit!important;\n" +
                "        }\n" +
                "        \n" +
                "        .es-desk-hidden {\n" +
                "            display: none;\n" +
                "            float: left;\n" +
                "            overflow: hidden;\n" +
                "            width: 0;\n" +
                "            max-height: 0;\n" +
                "            line-height: 0;\n" +
                "            mso-hide: all;\n" +
                "        }\n" +
                "        \n" +
                "        @media only screen and (max-width:600px) {\n" +
                "            p,\n" +
                "            ul li,\n" +
                "            ol li,\n" +
                "            a {\n" +
                "                line-height: 150%!important\n" +
                "            }\n" +
                "            h1,\n" +
                "            h2,\n" +
                "            h3,\n" +
                "            h1 a,\n" +
                "            h2 a,\n" +
                "            h3 a {\n" +
                "                line-height: 120%\n" +
                "            }\n" +
                "            h1 {\n" +
                "                font-size: 36px!important;\n" +
                "                text-align: left\n" +
                "            }\n" +
                "            h2 {\n" +
                "                font-size: 26px!important;\n" +
                "                text-align: left\n" +
                "            }\n" +
                "            h3 {\n" +
                "                font-size: 20px!important;\n" +
                "                text-align: left\n" +
                "            }\n" +
                "            .es-header-body h1 a,\n" +
                "            .es-content-body h1 a,\n" +
                "            .es-footer-body h1 a {\n" +
                "                font-size: 36px!important;\n" +
                "                text-align: left\n" +
                "            }\n" +
                "            .es-header-body h2 a,\n" +
                "            .es-content-body h2 a,\n" +
                "            .es-footer-body h2 a {\n" +
                "                font-size: 26px!important;\n" +
                "                text-align: left\n" +
                "            }\n" +
                "            .es-header-body h3 a,\n" +
                "            .es-content-body h3 a,\n" +
                "            .es-footer-body h3 a {\n" +
                "                font-size: 20px!important;\n" +
                "                text-align: left\n" +
                "            }\n" +
                "            .es-menu td a {\n" +
                "                font-size: 12px!important\n" +
                "            }\n" +
                "            .es-header-body p,\n" +
                "            .es-header-body ul li,\n" +
                "            .es-header-body ol li,\n" +
                "            .es-header-body a {\n" +
                "                font-size: 14px!important\n" +
                "            }\n" +
                "            .es-content-body p,\n" +
                "            .es-content-body ul li,\n" +
                "            .es-content-body ol li,\n" +
                "            .es-content-body a {\n" +
                "                font-size: 16px!important\n" +
                "            }\n" +
                "            .es-footer-body p,\n" +
                "            .es-footer-body ul li,\n" +
                "            .es-footer-body ol li,\n" +
                "            .es-footer-body a {\n" +
                "                font-size: 14px!important\n" +
                "            }\n" +
                "            .es-infoblock p,\n" +
                "            .es-infoblock ul li,\n" +
                "            .es-infoblock ol li,\n" +
                "            .es-infoblock a {\n" +
                "                font-size: 12px!important\n" +
                "            }\n" +
                "            *[class=\"gmail-fix\"] {\n" +
                "                display: none!important\n" +
                "            }\n" +
                "            .es-m-txt-c,\n" +
                "            .es-m-txt-c h1,\n" +
                "            .es-m-txt-c h2,\n" +
                "            .es-m-txt-c h3 {\n" +
                "                text-align: center!important\n" +
                "            }\n" +
                "            .es-m-txt-r,\n" +
                "            .es-m-txt-r h1,\n" +
                "            .es-m-txt-r h2,\n" +
                "            .es-m-txt-r h3 {\n" +
                "                text-align: right!important\n" +
                "            }\n" +
                "            .es-m-txt-l,\n" +
                "            .es-m-txt-l h1,\n" +
                "            .es-m-txt-l h2,\n" +
                "            .es-m-txt-l h3 {\n" +
                "                text-align: left!important\n" +
                "            }\n" +
                "            .es-m-txt-r img,\n" +
                "            .es-m-txt-c img,\n" +
                "            .es-m-txt-l img {\n" +
                "                display: inline!important\n" +
                "            }\n" +
                "            .es-button-border {\n" +
                "                display: inline-block!important\n" +
                "            }\n" +
                "            a.es-button,\n" +
                "            button.es-button {\n" +
                "                font-size: 20px!important;\n" +
                "                display: inline-block!important\n" +
                "            }\n" +
                "            .es-adaptive table,\n" +
                "            .es-left,\n" +
                "            .es-right {\n" +
                "                width: 100%!important\n" +
                "            }\n" +
                "            .es-content table,\n" +
                "            .es-header table,\n" +
                "            .es-footer table,\n" +
                "            .es-content,\n" +
                "            .es-footer,\n" +
                "            .es-header {\n" +
                "                width: 100%!important;\n" +
                "                max-width: 600px!important\n" +
                "            }\n" +
                "            .es-adapt-td {\n" +
                "                display: block!important;\n" +
                "                width: 100%!important\n" +
                "            }\n" +
                "            .adapt-img {\n" +
                "                width: 100%!important;\n" +
                "                height: auto!important\n" +
                "            }\n" +
                "            .es-m-p0 {\n" +
                "                padding: 0!important\n" +
                "            }\n" +
                "            .es-m-p0r {\n" +
                "                padding-right: 0!important\n" +
                "            }\n" +
                "            .es-m-p0l {\n" +
                "                padding-left: 0!important\n" +
                "            }\n" +
                "            .es-m-p0t {\n" +
                "                padding-top: 0!important\n" +
                "            }\n" +
                "            .es-m-p0b {\n" +
                "                padding-bottom: 0!important\n" +
                "            }\n" +
                "            .es-m-p20b {\n" +
                "                padding-bottom: 20px!important\n" +
                "            }\n" +
                "            .es-mobile-hidden,\n" +
                "            .es-hidden {\n" +
                "                display: none!important\n" +
                "            }\n" +
                "            tr.es-desk-hidden,\n" +
                "            td.es-desk-hidden,\n" +
                "            table.es-desk-hidden {\n" +
                "                width: auto!important;\n" +
                "                overflow: visible!important;\n" +
                "                float: none!important;\n" +
                "                max-height: inherit!important;\n" +
                "                line-height: inherit!important\n" +
                "            }\n" +
                "            tr.es-desk-hidden {\n" +
                "                display: table-row!important\n" +
                "            }\n" +
                "            table.es-desk-hidden {\n" +
                "                display: table!important\n" +
                "            }\n" +
                "            td.es-desk-menu-hidden {\n" +
                "                display: table-cell!important\n" +
                "            }\n" +
                "            .es-menu td {\n" +
                "                width: 1%!important\n" +
                "            }\n" +
                "            table.es-table-not-adapt,\n" +
                "            .esd-block-html table {\n" +
                "                width: auto!important\n" +
                "            }\n" +
                "            table.es-social {\n" +
                "                display: inline-block!important\n" +
                "            }\n" +
                "            table.es-social td {\n" +
                "                display: inline-block!important\n" +
                "            }\n" +
                "            .es-m-p5 {\n" +
                "                padding: 5px!important\n" +
                "            }\n" +
                "            .es-m-p5t {\n" +
                "                padding-top: 5px!important\n" +
                "            }\n" +
                "            .es-m-p5b {\n" +
                "                padding-bottom: 5px!important\n" +
                "            }\n" +
                "            .es-m-p5r {\n" +
                "                padding-right: 5px!important\n" +
                "            }\n" +
                "            .es-m-p5l {\n" +
                "                padding-left: 5px!important\n" +
                "            }\n" +
                "            .es-m-p10 {\n" +
                "                padding: 10px!important\n" +
                "            }\n" +
                "            .es-m-p10t {\n" +
                "                padding-top: 10px!important\n" +
                "            }\n" +
                "            .es-m-p10b {\n" +
                "                padding-bottom: 10px!important\n" +
                "            }\n" +
                "            .es-m-p10r {\n" +
                "                padding-right: 10px!important\n" +
                "            }\n" +
                "            .es-m-p10l {\n" +
                "                padding-left: 10px!important\n" +
                "            }\n" +
                "            .es-m-p15 {\n" +
                "                padding: 15px!important\n" +
                "            }\n" +
                "            .es-m-p15t {\n" +
                "                padding-top: 15px!important\n" +
                "            }\n" +
                "            .es-m-p15b {\n" +
                "                padding-bottom: 15px!important\n" +
                "            }\n" +
                "            .es-m-p15r {\n" +
                "                padding-right: 15px!important\n" +
                "            }\n" +
                "            .es-m-p15l {\n" +
                "                padding-left: 15px!important\n" +
                "            }\n" +
                "            .es-m-p20 {\n" +
                "                padding: 20px!important\n" +
                "            }\n" +
                "            .es-m-p20t {\n" +
                "                padding-top: 20px!important\n" +
                "            }\n" +
                "            .es-m-p20r {\n" +
                "                padding-right: 20px!important\n" +
                "            }\n" +
                "            .es-m-p20l {\n" +
                "                padding-left: 20px!important\n" +
                "            }\n" +
                "            .es-m-p25 {\n" +
                "                padding: 25px!important\n" +
                "            }\n" +
                "            .es-m-p25t {\n" +
                "                padding-top: 25px!important\n" +
                "            }\n" +
                "            .es-m-p25b {\n" +
                "                padding-bottom: 25px!important\n" +
                "            }\n" +
                "            .es-m-p25r {\n" +
                "                padding-right: 25px!important\n" +
                "            }\n" +
                "            .es-m-p25l {\n" +
                "                padding-left: 25px!important\n" +
                "            }\n" +
                "            .es-m-p30 {\n" +
                "                padding: 30px!important\n" +
                "            }\n" +
                "            .es-m-p30t {\n" +
                "                padding-top: 30px!important\n" +
                "            }\n" +
                "            .es-m-p30b {\n" +
                "                padding-bottom: 30px!important\n" +
                "            }\n" +
                "            .es-m-p30r {\n" +
                "                padding-right: 30px!important\n" +
                "            }\n" +
                "            .es-m-p30l {\n" +
                "                padding-left: 30px!important\n" +
                "            }\n" +
                "            .es-m-p35 {\n" +
                "                padding: 35px!important\n" +
                "            }\n" +
                "            .es-m-p35t {\n" +
                "                padding-top: 35px!important\n" +
                "            }\n" +
                "            .es-m-p35b {\n" +
                "                padding-bottom: 35px!important\n" +
                "            }\n" +
                "            .es-m-p35r {\n" +
                "                padding-right: 35px!important\n" +
                "            }\n" +
                "            .es-m-p35l {\n" +
                "                padding-left: 35px!important\n" +
                "            }\n" +
                "            .es-m-p40 {\n" +
                "                padding: 40px!important\n" +
                "            }\n" +
                "            .es-m-p40t {\n" +
                "                padding-top: 40px!important\n" +
                "            }\n" +
                "            .es-m-p40b {\n" +
                "                padding-bottom: 40px!important\n" +
                "            }\n" +
                "            .es-m-p40r {\n" +
                "                padding-right: 40px!important\n" +
                "            }\n" +
                "            .es-m-p40l {\n" +
                "                padding-left: 40px!important\n" +
                "            }\n" +
                "            .es-desk-hidden {\n" +
                "                display: table-row!important;\n" +
                "                width: auto!important;\n" +
                "                overflow: visible!important;\n" +
                "                max-height: inherit!important\n" +
                "            }\n" +
                "            .h-auto {\n" +
                "                height: auto!important\n" +
                "            }\n" +
                "        }\n" +
                "        \n" +
                "        @media screen and (max-width:384px) {\n" +
                "            .mail-message-content {\n" +
                "                width: 414px!important\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body data-new-gr-c-s-loaded=\"14.1047.0\" style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n" +
                "    <div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"vi\" style=\"background-color:#FAFAFA\">\n" +
                "        <!--[if gte mso 9]><v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\"> <v:fill type=\"tile\" color=\"#fafafa\"></v:fill> </v:background><![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FAFAFA\">\n" +
                "            <tr>\n" +
                "                <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table bgcolor=\"#ffffff\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\">\n" +
                "                                                                    <a target=\"_blank\" href=\"https://riustore.shop/index.jsp\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:14px\"><img src=\"https://atiech.stripocdn.email/content/guids/CABINET_5e6436a83c38621a4bc4e7bbfea401c5/images/logotransparentpng.png\" alt=\"Logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"\n" +
                "                                                                            title=\"Logo\" width=\"199\" height=\"53\"></a>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" style=\"Margin:0;padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:30px\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0px\"><img src=\"https://atiech.stripocdn.email/content/guids/CABINET_5e6436a83c38621a4bc4e7bbfea401c5/images/protection.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"\n" +
                "                                                                        width=\"100\" height=\"100\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" class=\"es-m-txt-c h-auto\" height=\"120\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px\">\n" +
                "                                                                    <h1 style=\"Margin:0;line-height:20px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:bold;color:#333333\">Xin chào</h1>\n" +
                "                                                                    <h1 style=\"Margin:0;line-height:46px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:46px;font-style:normal;font-weight:bold;color:#333333\">" + userName + "</h1>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" class=\"es-m-p0r es-m-p0l\" style=\"Margin:0;padding-top:5px;padding-bottom:5px;padding-left:40px;padding-right:40px\">\n" +
                "                                                                    <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:verdana, geneva, sans-serif;line-height:21px;color:#333333;font-size:14px\">Đơn hàng của bạn đã được tạo thành công.</p>\n" +
                "                                                                    <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:verdana, geneva, sans-serif;line-height:21px;color:#333333;font-size:14px\">Cảm ơn bạn vì đã đặt hàng tại Tile Market.<br></p>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-left:2px dashed #cccccc;border-right:2px dashed #cccccc;border-top:2px dashed #cccccc;border-bottom:2px dashed #cccccc;border-radius:5px\"\n" +
                "                                                            role=\"presentation\">\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" class=\"es-m-txt-c\" bgcolor=\"#ef5350\" style=\"padding:15px;Margin:0\">\n" +
                "                                                                    <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:33px;color:#efefef;font-size:22px\">Chữ ký điện tử cho đơn hàng của bạn sẽ được đính kèm vào tệp tin bên dưới.</p>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-bottom:30px\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-radius:5px\" role=\"presentation\">\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\">\n" +
                "                                                                    <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#999999;font-size:14px\">Nếu không phải bạn đã thực hiện hành động trên hãy thay đổi mật khẩu tài khoảng GOOGLE của bạn để bảo về thông tin của mình.</p>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px\" role=\"none\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:600px\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr>\n" +
                "                                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                        <tr>\n" +
                "                                                                            <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Facebook\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/facebook-circle-gray.png\" alt=\"Fb\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "                                                                            <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Twitter\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/twitter-circle-gray.png\" alt=\"Tw\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "                                                                            <td\n" +
                "                                                                                align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Instagram\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/instagram-circle-gray.png\" alt=\"Inst\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "                                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><img title=\"Youtube\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/youtube-circle-gray.png\" alt=\"Yt\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "                                                                </tr>\n" +
                "                                                                </table>\n" +
                "                                                    </td>\n" +
                "                                                    </tr>\n" +
                "                                                    <tr>\n" +
                "                                                        <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:35px\">\n" +
                "                                                            <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#333333;font-size:12px\">TileMarket © 2022, Inc. All Rights Reserved .</p>\n" +
                "                                                            <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#333333;font-size:12px\">Khu Phố 6, Phường Linh Trung, Thành Phố Thủ Đức.</p>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"padding:0;Margin:0\">\n" +
                "                                                            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"es-menu\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                <tr class=\"links\">\n" +
                "                                                                    <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#999999;font-size:12px\">Visit Us </a></td>\n" +
                "                                                                    <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0;border-left:1px solid #cccccc\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#999999;font-size:12px\">Privacy Policy</a></td>\n" +
                "                                                                    <td\n" +
                "                                                                        align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0;border-left:1px solid #cccccc\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#999999;font-size:12px\">Terms of Use</a></td>\n" +
                "                                                        </tr>\n" +
                "                                                        </table>\n" +
                "                                        </td>\n" +
                "                                        </tr>\n" +
                "                                        </table>\n" +
                "                            </td>\n" +
                "                            </tr>\n" +
                "                            </table>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                            <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" bgcolor=\"#FFFFFF\"\n" +
                "                                role=\"none\">\n" +
                "                                <tr>\n" +
                "                                    <td align=\"left\" style=\"padding:20px;Margin:0\">\n" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                            <tr>\n" +
                "                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td align=\"center\" class=\"es-infoblock\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\">\n" +
                "                                                                <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\">\n" +
                "                                                                    <a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"></a>\n" +
                "                                                                    No longer want to receive these emails?&nbsp;<a href=\"\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\">Unsubscribe</a>.\n" +
                "                                                                    <a\n" +
                "                                                                        target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"></a>\n" +
                "                                                                </p>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                    </table>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <div class=\"ddict_btn\" style=\"top:258px;left:558.889px\"><img src=\"chrome-extension://cianljdimgjlpmjllcbahmpdnicglaap/logo/48.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        return content;
    }
}
