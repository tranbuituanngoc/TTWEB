package controller;

import Util.Email;
import Util.Encode;
import Util.SaltString;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;
import okhttp3.*;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

@WebServlet(name = "UserController", value = "/nguoi-dung")
public class UserController extends HttpServlet {
    private static final int MAX_LOGIN_ATTEMPTS = 3; // Số lần đăng nhập sai tối đa
    private static final long LOCK_TIME = 5 * 60 * 1000; // Thời gian khóa tài khoản (5 phút)

    private static final String SITE_KEY = "6LfaJM4lAAAAAIZJo4uMpLgyFwkQDp2x4hUguTwY"; // site key reCAPTCHA
    private static final String SECRET_KEY = "6LfaJM4lAAAAACfjZqz0xFBME4uulzf79Wzi8xdJ"; // secret key reCAPTCHA

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("dang-nhap")) {
            Login(request, response);
        } else if (action.equals("dang-xuat")) {
            Logout(request, response);
        } else if (action.equals("dang-ki")) {
            Register(request, response);
        } else if (action.equals("xac-thuc")) {
            verifyAccount(request, response);
        } else if (action.equals("doi-mat-khau")) {
            ChangePass(request, response);
        } else if (action.equals("quen-mat-khau")) {
            forgetPass(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void Login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            boolean valid = verifyRecaptcha(gRecaptchaResponse);

            String messageResponse = "";

            if (valid) {
                pass = Encode.encodeToSHA1(pass);

                request.setAttribute("username", username);

                User user = new User();
                user.setUserName(username);
                user.setPass(pass);

                UserService us = new UserService();
                User u = us.selectByUserNameAndpassword(user);
                HttpSession session = request.getSession(true);

                // Lấy số lần đăng nhập sai đã nhập trong session hoặc tạo session mới nếu chưa có
                Integer loginAttempts = (Integer) session.getAttribute("loginAttempts");

                if (loginAttempts == null) {
                    loginAttempts = 0;
                }

                // Kiểm tra xem tài khoản đã bị khóa hay chưa
                Long unlockTime = (Long) session.getAttribute("unlockTime");

                if (unlockTime != null && unlockTime > System.currentTimeMillis()) {
                    // Nếu tài khoản đã bị khóa, hiển thị thông báo và không xử lý việc đăng nhập
                    long timeLeft = (unlockTime - System.currentTimeMillis()) / 1000;
                    request.setAttribute("error", "Tài khoản của bạn đã bị khóa. Vui lòng thử lại sau " + timeLeft + " giây.");
                    messageResponse = "error";
                    request.setAttribute("messageResponse", messageResponse);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                if (u != null) {
                    session.setAttribute("user", u);
                    if (u.getRole() == 1 || u.getRole() == 0) {
                        response.sendRedirect("ListProductAd");
                    } else if (u.getRole() == 2) {
                        response.sendRedirect("Home");
                    }
                } else {
                    loginAttempts++;
                    session.setAttribute("loginAttempts", loginAttempts);
                    if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                        // Nếu đã đạt đến số lần đăng nhập sai tối đa, khóa chức năng đăng nhập trong 5 phút
                        long unlockTimeSet = System.currentTimeMillis() + LOCK_TIME;
                        session.setAttribute("unlockTime", unlockTimeSet);
                        messageResponse = "error";
                        request.setAttribute("messageResponse", messageResponse);
                        request.setAttribute("error", "Số lần đăng nhập sai của bạn đã vượt quá giới hạn. Vui lòng thử lại sau " + (LOCK_TIME / 1000) + " giây.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                    } else {
                        // Nếu số lần đăng nhập sai chưa đạt tối đa, hiển thị thông báo lỗi và cho phép đăng nhập lại
                        int remainingAttempts = MAX_LOGIN_ATTEMPTS - loginAttempts;
                        messageResponse = "error";
                        request.setAttribute("messageResponse", messageResponse);
                        request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng. Bạn còn " + remainingAttempts + " lần thử.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                    }
                }
            }else {
                messageResponse = "error";
                request.setAttribute("messageResponse", messageResponse);
                request.setAttribute("error", "Vui lòng xác thực mã Captcha");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            // remove session
            session.invalidate();

            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            response.sendRedirect(url + "/Home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Register(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String com_pass = request.getParameter("confirm-password");
            String address = "";
            int role = 2;
            boolean status = true;

            PrintWriter out = response.getWriter();

            request.setAttribute("name", name);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);

            String errorUName = "";
            String errorCPass = "";
            String messageResponse = "";
            int error = 0;
            UserService UserService = new UserService();
            if (UserService.checkUserName(username)) {
                errorUName = "Tên đăng nhập đã tồn tại</br>";
                error++;
            }
            if (!password.equals(com_pass)) {
                errorCPass = "Mật khẩu nhập lại không khớp</br>";
                error++;
            } else {
                password = Encode.encodeToSHA1(password);
            }
            if (error != 0) {
                request.setAttribute("errorUName", errorUName);
                request.setAttribute("errorCPass", errorCPass);
                messageResponse = "error";
                request.setAttribute("messageResponse", messageResponse);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                Random rd = new Random();
                String random = System.currentTimeMillis() + rd.nextInt(100) + "";
                String id_user = "kh" + random.substring(random.length() - 8);
                User user = new User(id_user, username, name, email, phone, address, password, role, status);
                if (UserService.insert(user) > 0) {
                    //Create salt string(random string)
                    String saltString = SaltString.getSaltString();

                    //Set timevalid
                    Date todaysDate = new Date(new java.util.Date().getTime());
                    Calendar c = Calendar.getInstance();
                    c.setTime(todaysDate);
                    //set time valid is 5 minute
                    c.add(Calendar.MINUTE, 5);
                    Timestamp timeValid = new Timestamp(c.getTimeInMillis());

                    //set verified
                    boolean verified = false;

                    user.setVerificationCode(saltString);
                    user.setTimeValid(timeValid);
                    user.setVerified(verified);

                    if (UserService.updateVerifyInfo(user) > 0) {
                        Email.sendMail(user.getEmail(), getContentEmailVerify(user), "Xác Thực Tài Khoản Tại TileMarket");
                    }
                }
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                messageResponse = "success";
                request.setAttribute("messageResponse", messageResponse);
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void verifyAccount(HttpServletRequest request, HttpServletResponse response) {
        String idUser = request.getParameter("id_User");
        String verificationCode = request.getParameter("verificationCode");

        UserService userService = new UserService();

        User user = new User();
        user.setId_User(idUser);
        User us = userService.selectById(user);

        String msg = "";
        if (us != null) {
            Calendar c = Calendar.getInstance();
            Date todaysDate = new Date(new java.util.Date().getTime());
            c.setTime(todaysDate);
            Timestamp timestamp = new Timestamp(c.getTimeInMillis());

            if (us.getVerificationCode().equals(verificationCode)) {
                if (timestamp.before(us.getTimeValid())) {
                    //success
                    us.setVerified(true);
                    userService.updateVerifyInfo(us);
                    msg = "Xác thực tài khoản thành công";
                } else {
                    //error time not valid
                    msg = "Xác thực tài khoản không thành công";
                }
            } else {
                //error verification code
                msg = "Xác thực tài khoản thành công";
            }
        } else {
            msg = "Tài khoản không tồn tại!";
        }
        try {
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            response.sendRedirect(url + "/Home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ChangePass(HttpServletRequest request, HttpServletResponse response) {
        try {
            String oldPass = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String comNewPass = request.getParameter("comNewPass");

            String oldPass_Encode = Encode.encodeToSHA1(oldPass);
            String error = "";
            String url = "/changePass.jsp";

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("/404.jsp");
            } else {
                if (!oldPass_Encode.equals(user.getPass())) {
                    error = "Mật khẩu hiện tại không chính xác!";
                } else {
                    if (!newPassword.equals(comNewPass)) {
                        error = "Mật khẩu nhập lại không khớp!";
                    } else {
                        String newPass_Encode = Encode.encodeToSHA1(newPassword);
                        if (newPass_Encode.equals(oldPass_Encode)) {
                            error = "Mật khẩu mới không được trùng với mật khẩu cũ!";
                        } else {
                            user.setPass(newPass_Encode);
                            user.setOldPass(oldPass_Encode);
                            UserService userDAO = new UserService();
                            if (userDAO.changePass(user)) {
                                error = "Thay đổi mật khẩu thành công!";
                            } else error = "Thay đổi mật khẩu thất bại!";
                        }
                    }
                }
            }
            request.setAttribute("error", error);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private void forgetPass(HttpServletRequest request, HttpServletResponse response) {
        try {
            String uname = request.getParameter("username");
            String email = request.getParameter("email");
            String oldPass = request.getParameter("oldPass");

            UserService userDAO = new UserService();

            User u = new User();
            u.setUserName(uname);
            u.setEmail(email);
            String error = "";
            String url = "";

            String oldPass_Encode = "0";

            User user = null;
            if (oldPass == "") {
                user = userDAO.selectByUnameNEmail(u);
            } else {
                oldPass_Encode = Encode.encodeToSHA1(oldPass);
                u.setOldPass(oldPass_Encode);
                user = userDAO.selectByUnameNEmailNOldPass(u);
            }


            String saltString = SaltString.getSaltString();
            String newPass_Encode = Encode.encodeToSHA1(saltString);
            if (user != null) {
                user.setPass(newPass_Encode);
                user.setOldPass(oldPass_Encode);
                url = "/login.jsp";
                userDAO.changePass(user);
                Email.sendMail(user.getEmail(), getContentEmailFogetPass(user, saltString), "Mật Khẩu Mới Của Bạn Là " + saltString);
            } else {
                error = "Tài khoản không tồn tại";
                url = "/forgot-password.jsp";
            }
            request.setAttribute("error", error);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getContentEmailVerify(User user) {
        String link = "http://localhost:8080/nguoi-dung?action=xac-thuc&id_User=" + user.getId_User() + "&verificationCode=" + user.getVerificationCode();
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "<meta name=\"x-apple-disable-message-reformatting\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "<meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "<title>New Template</title><!--[if (mso 16)]>\n" +
                "<style type=\"text/css\">\n" +
                "a {text-decoration: none;}\n" +
                "</style>\n" +
                "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "<o:OfficeDocumentSettings>\n" +
                "<o:AllowPNG></o:AllowPNG>\n" +
                "<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "</o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "<style type=\"text/css\">\n" +
                "#outlook a {\n" +
                "padding:0;\n" +
                "}\n" +
                ".es-button {\n" +
                "mso-style-priority:100!important;\n" +
                "text-decoration:none!important;\n" +
                "}\n" +
                "a[x-apple-data-detectors] {\n" +
                "color:inherit!important;\n" +
                "text-decoration:none!important;\n" +
                "font-size:inherit!important;\n" +
                "font-family:inherit!important;\n" +
                "font-weight:inherit!important;\n" +
                "line-height:inherit!important;\n" +
                "}\n" +
                ".es-desk-hidden {\n" +
                "display:none;\n" +
                "float:left;\n" +
                "overflow:hidden;\n" +
                "width:0;\n" +
                "max-height:0;\n" +
                "line-height:0;\n" +
                "mso-hide:all;\n" +
                "}\n" +
                "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120% } h1 { font-size:36px!important; text-align:left } h2 { font-size:26px!important; text-align:left } h3 { font-size:20px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:36px!important; text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important; text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:12px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:20px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0!important } .es-m-p0r { padding-right:0!important } .es-m-p0l { padding-left:0!important } .es-m-p0t { padding-top:0!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-m-p5 { padding:5px!important } .es-m-p5t { padding-top:5px!important } .es-m-p5b { padding-bottom:5px!important } .es-m-p5r { padding-right:5px!important } .es-m-p5l { padding-left:5px!important } .es-m-p10 { padding:10px!important } .es-m-p10t { padding-top:10px!important } .es-m-p10b { padding-bottom:10px!important } .es-m-p10r { padding-right:10px!important } .es-m-p10l { padding-left:10px!important } .es-m-p15 { padding:15px!important } .es-m-p15t { padding-top:15px!important } .es-m-p15b { padding-bottom:15px!important } .es-m-p15r { padding-right:15px!important } .es-m-p15l { padding-left:15px!important } .es-m-p20 { padding:20px!important } .es-m-p20t { padding-top:20px!important } .es-m-p20r { padding-right:20px!important } .es-m-p20l { padding-left:20px!important } .es-m-p25 { padding:25px!important } .es-m-p25t { padding-top:25px!important } .es-m-p25b { padding-bottom:25px!important } .es-m-p25r { padding-right:25px!important } .es-m-p25l { padding-left:25px!important } .es-m-p30 { padding:30px!important } .es-m-p30t { padding-top:30px!important } .es-m-p30b { padding-bottom:30px!important } .es-m-p30r { padding-right:30px!important } .es-m-p30l { padding-left:30px!important } .es-m-p35 { padding:35px!important } .es-m-p35t { padding-top:35px!important } .es-m-p35b { padding-bottom:35px!important } .es-m-p35r { padding-right:35px!important } .es-m-p35l { padding-left:35px!important } .es-m-p40 { padding:40px!important } .es-m-p40t { padding-top:40px!important } .es-m-p40b { padding-bottom:40px!important } .es-m-p40r { padding-right:40px!important } .es-m-p40l { padding-left:40px!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } .h-auto { height:auto!important } }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body data-new-gr-c-s-loaded=\"14.1026.0\" style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n" +
                "<div class=\"es-wrapper-color\" style=\"background-color:#FAFAFA\"><!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"#fafafa\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FAFAFA\">\n" +
                "<tr>\n" +
                "<td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "<table bgcolor=\"#ffffff\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"http://localhost:8080/index.jsp\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:14px\"><img src=\"https://atiech.stripocdn.email/content/guids/CABINET_5e6436a83c38621a4bc4e7bbfea401c5/images/logotransparentpng.png\" alt=\"Logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" title=\"Logo\" width=\"199\"></a></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "<table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"Margin:0;padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:30px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0px\"><img src=\"https://atiech.stripocdn.email/content/guids/CABINET_5e6436a83c38621a4bc4e7bbfea401c5/images/protection.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"100\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<h1 style=\"Margin:0;line-height:46px;font-family:arial,'helvetica neue',helvetica,sans-serif;font-size:46px;text-align: center;font-style:normal;font-weight:bold;color:#333333\">" + user.getUserName() + "</h1>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"center\" class=\"es-m-p0r es-m-p0l\" style=\"Margin:0;padding-top:5px;padding-bottom:5px;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">Hãy nhấp vào đường link bên dưới để xác thực tài khoản của bạn.&nbsp;</p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-left:2px dashed #cccccc;border-right:2px dashed #cccccc;border-top:2px dashed #cccccc;border-bottom:2px dashed #cccccc;border-radius:5px\" role=\"presentation\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"><!--[if mso]><a href=\"http://localhost:8080/nguoi-dung/xac-thuc?id_User=\"+user.getId_User()+\"&verificationCode=\"+user.getVerificationCode()\" target=\"_blank\" hidden>\n" +
                "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" esdevVmlButton href=\"http://localhost:8080/nguoi-dung/xac-thuc?id_User=\"+user.getId_User()+\"&verificationCode=\"+user.getVerificationCode()\"\n" +
                "style=\"height:63px; v-text-anchor:middle; width:389px\" arcsize=\"10%\" stroke=\"f\" fillcolor=\"#ef5350\">\n" +
                "<w:anchorlock></w:anchorlock>\n" +
                "<center style='color:#ffffff; font-family:arial, \"helvetica neue\", helvetica, sans-serif; font-size:27px; font-weight:400; line-height:27px; mso-text-raise:1px'>Xác Thực Tài Khoản</center>\n" +
                "</v:roundrect></a>\n" +
                "<![endif]--><!--[if !mso]><!-- --><span class=\"msohide es-button-border\" style=\"border-style:solid;border-color:#2CB543;background:#ef5350;border-width:0px;display:inline-block;border-radius:6px;width:auto;mso-border-alt:10px;mso-hide:all\"><a href=" + link + "class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#FFFFFF;font-size:36px;display:inline-block;background:#ef5350;border-radius:6px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:normal;font-style:normal;line-height:43px;width:auto;text-align:center;padding:10px 30px 10px 30px;padding-left:30px;padding-right:30px;border-color:#ef5350\">Xác Thực Tài Khoản</a></span><!--<![endif]--></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-bottom:30px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-radius:5px\" role=\"presentation\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#999999;font-size:14px\">Nếu không hãy thay đổi mật khẩu tài khoảng GOOGLE của bạn để bảo về thông tin của mình.</p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "<table class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px\">\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"padding:0;Margin:0;width:600px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Facebook\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/facebook-circle-gray.png\" alt=\"Fb\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Twitter\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/twitter-circle-gray.png\" alt=\"Tw\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Instagram\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/instagram-circle-gray.png\" alt=\"Inst\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><img title=\"Youtube\" src=\"https://atiech.stripocdn.email/content/assets/img/social-icons/circle-gray/youtube-circle-gray.png\" alt=\"Yt\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:35px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#333333;font-size:12px\">TileMarket © 2022, Inc.&nbsp;All Rights Reserved .</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#333333;font-size:12px\">Khu Phố 6, Phường Linh Trung, Thành Phố Thủ Đức.</p></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"es-menu\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr class=\"links\">\n" +
                "<td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#999999;font-size:12px\">Visit Us </a></td>\n" +
                "<td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0;border-left:1px solid #cccccc\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#999999;font-size:12px\">Privacy Policy</a></td>\n" +
                "<td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0;border-left:1px solid #cccccc\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#999999;font-size:12px\">Terms of Use</a></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "<tr>\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "<table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" bgcolor=\"#FFFFFF\">\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"padding:20px;Margin:0\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "<tr>\n" +
                "<td align=\"center\" class=\"es-infoblock\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"></a>No longer want to receive these emails?&nbsp;<a href=\"\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\">Unsubscribe</a>.<a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"></a></p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return content;
    }

    private static String getContentEmailFogetPass(User user, String saltStirng) {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"\">\n" +
                "        <div class=\"aHl\"></div>\n" +
                "        <div id=\":p9\" tabindex=\"-1\"></div>\n" +
                "        <div id=\":oy\" class=\"ii gt\" jslog=\"20277; u014N:xr6bB; 4:W251bGwsbnVsbCxbXV0.\">\n" +
                "            <div id=\":ox\" class=\"a3s aiL msg5521148090717455275\"><u></u>\n" +
                "                <div style=\"width:100%;font-family:arial,'helvetica neue',helvetica,sans-serif;padding:0;Margin:0\">\n" +
                "                    <div style=\"background-color:#fafafa\">\n" +
                "                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#fafafa\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"m_5521148090717455275es-header\" align=\"center\" style=\"border-collapse:collapse;border-spacing:0px;table-layout:fixed!important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                                        <table bgcolor=\"#ffffff\" class=\"m_5521148090717455275es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"m_5521148090717455275es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px;font-size:0px\">\n" +
                "                                                                                                        <a href=\"http://localhost:8080/index.jsp\" style=\"text-decoration:underline;color:#666666;font-size:14px\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=http://localhost:8080/index.jsp&amp;source=gmail&amp;ust=1672500692710000&amp;usg=AOvVaw1-Zucl5X8q4yh_1eRzU_T4\"><img src=\"https://atiech.stripocdn.email/content/guids/CABINET_5e6436a83c38621a4bc4e7bbfea401c5/images/logotransparentpng.png\"\n" +
                "                                                                                                                alt=\"Logo\" style=\"display:block;border:0;outline:none;text-decoration:none\" width=\"200\" title=\"Logo\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"m_5521148090717455275es-content\" align=\"center\" style=\"border-collapse:collapse;border-spacing:0px;table-layout:fixed!important;width:100%\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                                        <table bgcolor=\"#ffffff\" class=\"m_5521148090717455275es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td align=\"left\" style=\"Margin:0;padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:30px\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0px\"><img src=\"https://atiech.stripocdn.email/content/guids/CABINET_5e6436a83c38621a4bc4e7bbfea401c5/images/protection.png\"\n" +
                "                                                                                                            alt=\"\" style=\"display:block;border:0;outline:none;text-decoration:none\" width=\"100\" class=\"CToWUd\" data-bit=\"iit\"></td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" class=\"m_5521148090717455275es-m-txt-c m_5521148090717455275h-auto\" height=\"120\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px\">\n" +
                "                                                                                                        <h1 style=\"Margin:0;line-height:46px;font-family:arial,'helvetica neue',helvetica,sans-serif;font-size:23px;font-style:normal;font-weight:bold;color:#333333\">Xin Chào</h1>\n" +
                "                                                                                                        <h1 style=\"Margin:0;line-height:46px;font-family:arial,'helvetica neue',helvetica,sans-serif;text-align: center;font-size:46px;font-style:normal;font-weight:bold;color:#333333\">" + user.getUserName() + "</h1>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" class=\"m_5521148090717455275es-m-p0r m_5521148090717455275es-m-p0l\" style=\"Margin:0;padding-top:5px;padding-bottom:5px;padding-left:40px;padding-right:40px\">\n" +
                "                                                                                                        <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333333;font-size:14px\">Đây là mật khẩu mới của bạn vui lòng đăng nhập lại với mật khẩu này và đổi lại thành một mật khẩu khác.&nbsp;</p>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:separate;border-spacing:0px;border-left:2px dashed #cccccc;border-right:2px dashed #cccccc;border-top:2px dashed #cccccc;border-bottom:2px dashed #cccccc;border-radius:5px\" role=\"presentation\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"><span class=\"m_5521148090717455275es-button-border\" style=\"border-style:solid;border-color:#2cb543;background:#004aad;border-width:0px;display:inline-block;border-radius:6px;width:auto\"><a style=\"text-decoration:none;color:#ffffff;font-size:36px;border-style:solid;border-color:#004aad;border-width:10px 30px 10px 30px;display:inline-block;background:#004aad;border-radius:6px;font-family:arial,'helvetica neue',helvetica,sans-serif;font-weight:normal;font-style:normal;line-height:43px;width:auto;text-align:center;border-left-width:30px;border-right-width:30px\" target=\"_blank\">" + saltStirng + "</a></span></td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-bottom:30px\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:separate;border-spacing:0px;border-radius:5px\" role=\"presentation\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\">\n" +
                "                                                                                                        <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#999999;font-size:14px\">Nếu không phải là bạn, hãy thay đổi mật khẩu tài khoản GOOGLE của bạn để bảo về thông tin của mình.</p>\n" +
                "                                                                                                        <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#999999;font-size:14px\">Email này có thông báo đến bạn khi mật khẩu của bạn đã được thay đổi.</p>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"m_5521148090717455275es-footer\" align=\"center\" style=\"border-collapse:collapse;border-spacing:0px;table-layout:fixed!important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                                        <table class=\"m_5521148090717455275es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:600px\">\n" +
                "                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0\">\n" +
                "                                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"m_5521148090717455275es-table-not-adapt m_5521148090717455275es-social\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                                                            <tbody>\n" +
                "                                                                                                                <tr>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Facebook\" src=\"https://ci5.googleusercontent.com/proxy/qSVLPyWLS6cnB-FBCQqpzD2y4K0bHdNo6NLTNfdTXul_pfPlPvwiM-tpNeGe_Yw761aXV4_sKflFndZtRwBTIExZvHDbyfLGiP2fQou5Qyk9bmaRBJDlIEWCBunPDWUvCJd3lKreHXdl59Db49UmjVPhAr4=s0-d-e1-ft#https://atiech.stripocdn.email/content/assets/img/social-icons/logo-black/facebook-logo-black.png\"\n" +
                "                                                                                                                            alt=\"Fb\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Twitter\" src=\"https://ci5.googleusercontent.com/proxy/_g52Yt8frRsk8f3Es1wT5LNX7aTFLKaxEu1lE1zu4Q3p9wrxApgF93QFhexy40_s7KZMK8gHPbGKjKjBn_LMGRfIOtJK4wSQxAGJR5X0YCp4yFqG_ms0BvpqzHhLls1OCXvSuFKeYIrO2WOq-mD0duvpFg=s0-d-e1-ft#https://atiech.stripocdn.email/content/assets/img/social-icons/logo-black/twitter-logo-black.png\"\n" +
                "                                                                                                                            alt=\"Tw\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Instagram\" src=\"https://ci3.googleusercontent.com/proxy/FX5UEPqcpfCTCvXCF0xiU4hVBVTFm-ZXqbBxSmbzPj9pJrdRy6C2ADo8iSYoWmifpcBZicRLA7KhmXnbwdYG7w9n-eO_rlRUOAtbSFNCQz2XSnID1ndhBNiYuwHecLbXjEy1om8rTJeTXGEgouoiD5kV8MuT=s0-d-e1-ft#https://atiech.stripocdn.email/content/assets/img/social-icons/logo-black/instagram-logo-black.png\"\n" +
                "                                                                                                                            alt=\"Inst\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><img title=\"Youtube\" src=\"https://ci5.googleusercontent.com/proxy/e3LbYYcF-VV3niBap3PiwA0B47DkjNOnRr6PvfXRco5LPEBdLQBSHyUMyQFtwNbWD6S8OFDzXrhh81qRbMtX8xKxnWSzUk7ALyFNx8qJ55kMZQjTvSEYGFNXtjOEhnZW5E1_7ssJCI15h8XB0-SLoPRD9g=s0-d-e1-ft#https://atiech.stripocdn.email/content/assets/img/social-icons/logo-black/youtube-logo-black.png\"\n" +
                "                                                                                                                            alt=\"Yt\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td>\n" +
                "                                                                                                                </tr>\n" +
                "                                                                                                            </tbody>\n" +
                "                                                                                                        </table>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:35px\">\n" +
                "                                                                                                        <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:18px;color:#333333;font-size:12px\">TileMarket © 2022, Inc.&nbsp;All Rights Reserved .</p>\n" +
                "                                                                                                        <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:18px;color:#333333;font-size:12px\">Khu Phố 6, Phường Linh Trung, Thành Phố Thủ Đức.</p>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td style=\"padding:0;Margin:0\">\n" +
                "                                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"m_5521148090717455275es-menu\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                                                            <tbody>\n" +
                "                                                                                                                <tr>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0\"><a style=\"text-decoration:none;display:block;font-family:arial,'helvetica neue',helvetica,sans-serif;color:#999999;font-size:12px\">Visit Us </a></td>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0;border-left:1px solid #cccccc\"><a style=\"text-decoration:none;display:block;font-family:arial,'helvetica neue',helvetica,sans-serif;color:#999999;font-size:12px\">Privacy Policy</a></td>\n" +
                "                                                                                                                    <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0;border-left:1px solid #cccccc\"><a style=\"text-decoration:none;display:block;font-family:arial,'helvetica neue',helvetica,sans-serif;color:#999999;font-size:12px\">Terms of Use</a></td>\n" +
                "                                                                                                                </tr>\n" +
                "                                                                                                            </tbody>\n" +
                "                                                                                                        </table>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"m_5521148090717455275es-content\" align=\"center\" style=\"border-collapse:collapse;border-spacing:0px;table-layout:fixed!important;width:100%\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                                        <table class=\"m_5521148090717455275es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" bgcolor=\"#FFFFFF\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td align=\"left\" style=\"padding:20px;Margin:0\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" class=\"m_5521148090717455275es-infoblock\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#cccccc\">\n" +
                "                                                                                                        <p style=\"Margin:0;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:14px;color:#cccccc;font-size:12px\">\n" +
                "                                                                                                            <a style=\"text-decoration:underline;color:#cccccc;font-size:12px\"></a>No longer want to receive these emails?&nbsp;<a style=\"text-decoration:underline;color:#cccccc;font-size:12px\">Unsubscribe</a>.\n" +
                "                                                                                                            <a style=\"text-decoration:underline;color:#cccccc;font-size:12px\"></a>\n" +
                "                                                                                                        </p>\n" +
                "                                                                                                    </td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <div class=\"yj6qo\"></div>\n" +
                "                        <div class=\"adL\">\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"adL\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"adL\">\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div id=\":pd\" class=\"ii gt\" style=\"display:none\">\n" +
                "            <div id=\":pe\" class=\"a3s aiL \"></div>\n" +
                "        </div>\n" +
                "        <div class=\"hi\"></div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return content;
    }

    private boolean verifyRecaptcha(String gRecaptchaResponse) throws IOException {

        // Tạo một OkHttpClient để gửi yêu cầu HTTP đến Google Recaptcha API
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("secret", SECRET_KEY)
                .add("response", gRecaptchaResponse)
                .build();

        //Gửi yêu cầu chứa dữ liệu của formBody đến url theo phương thức POST
        Request request = new Request.Builder()
                .url("https://www.google.com/recaptcha/api/siteverify")
                .post(formBody)
                .build();

        //Trả về 1 response để phản hồi kết quả xem người dùng đã check Captcha hay chưa?
        Response response = client.newCall(request).execute();

        //Phân tích và chuyển đổi kết quả được trả về thành một biến boolean
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
        boolean success = jsonObject.get("success").getAsBoolean();

        return success;
    }
}
