package controller;

import model.Role;
import service.RoleService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageRoleUser", value = "/ManageRoleUser")
public class ManageRoleUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = RoleService.getAllRolesUser();
        request.setAttribute("r", roles);
        request.getRequestDispatcher("admin/ManageRoleUser.jsp").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
