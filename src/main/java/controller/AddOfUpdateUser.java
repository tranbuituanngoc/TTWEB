package controller;

import Util.Encode;
import Util.SaltString;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

@WebServlet(name = "AddOfUpdateUser", value = "/AddOfUpdateUser")
public class AddOfUpdateUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        request.setCharacterEncoding("UTF-8");
        UserService us = new UserService();
        User u = new User();
        u.setId_User(id);
        if (action != null) {
            if (action.equals("getadd")) {
                request.setAttribute("err", "");
                request.getRequestDispatcher("admin/AddUser.jsp").forward(request, response);
            }
            if (action.equals("getupdate")) {
                User user = us.selectById(u);
                String forward = "admin/AddUser.jsp?";
                forward += "username=" + user.getUserName();
                forward += "&email=" + user.getEmail();
                forward += "&phone=" + user.getPhone();
                forward += "&fullname=" + user.getFullname();
                forward += "&permission=" + user.getRole();
                forward += "&fullname=" + user.getFullname();
                forward += "&status=" + user.getStatus();
                request.getRequestDispatcher(forward).forward(request, response);
            }

            if (action.equals("add")) {
                boolean isErr = false;
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                int role = Integer.parseInt(request.getParameter("permission"));
                String fullname = request.getParameter("fullname");
                String phone = request.getParameter("phone");
                boolean status = "1".equals(request.getParameter("status"));

                if (UserService.existUserName(username)) {
                    request.setAttribute("err", "Tên tài khoản đã tồn tại");
                    request.getRequestDispatcher("admin/AddUser.jsp").forward(request, response);
                    isErr = true;

                } else if (password.length() < 8) {
                    request.setAttribute("err", "Vui lòng nhập mật khẩu từ 8 kí tự trở lên");
                    request.getRequestDispatcher("admin/AddUser.jsp").forward(request, response);
                    isErr = true;
                }
                if (!isErr) {
                    Random rd = new Random();
                    String random = System.currentTimeMillis() + rd.nextInt(100) + "";
                    String id_user = "kh" + random.substring(random.length() - 8);
                    //Create salt string(random string)
                    String saltString = SaltString.getSaltString();

                    //Set timevalid
                    java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
                    Calendar c = Calendar.getInstance();
                    c.setTime(todaysDate);
                    //set time valid is 5 minute
                    c.add(Calendar.MINUTE, 5);
                    Timestamp timeValid = new Timestamp(c.getTimeInMillis());

                    //set verified
                    boolean verified = true;

                    password = Encode.encodeToSHA1(password);

                    User user = new User();
                    user.setVerificationCode(saltString);
                    user.setTimeValid(timeValid);
                    user.setVerified(verified);
                    user.setId_User(id_user);
                    user.setPass(password);
                    user.setRole(role);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setFullname(fullname);
                    user.setStatus(status);


                    us.insert(user);
                    us.updateVerifyInfo(user);
                    request.setAttribute("err", "Thêm tài khoản thành công");
                    response.sendRedirect("ListUserAd");
                }
            }

            if (action.equals("update")) {
                boolean isErr = false;
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                int role = Integer.parseInt(request.getParameter("permission"));
                String fullname = request.getParameter("fullname");
                String phone = request.getParameter("phone");
                boolean status = "1".equals(request.getParameter("status"));
                if (UserService.existUserName(username)) {
                    request.setAttribute("err", "Tên tài khoản đã tồn tại");
                    request.getRequestDispatcher("admin/AddUser.jsp").forward(request, response);
                    isErr = true;

                }
                if (!isErr) {

                    User user = new User();
                    user.setId_User(id);
                    user.setRole(role);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setFullname(fullname);
                    user.setStatus(status);
                    user.setUserName(username);

                    us.update( user);
                    request.setAttribute("err", "Chỉnh sửa tài khoản thành công");
                    response.sendRedirect("ListUserAd");
                }
            }
            if (action.equals("delete")) {
                UserService.deleteUser(id);
                response.sendRedirect("ListUserAd");
            }
            if (action.equals("lock")) {
                UserService.lockUser(id);
                response.sendRedirect("ListUserAd");
            }
            if (action.equals("unlock")) {
                UserService.unlockUser(id);
                response.sendRedirect("ListUserAd");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
