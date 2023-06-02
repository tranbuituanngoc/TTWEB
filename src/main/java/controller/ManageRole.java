package controller;

import model.Role;
import service.RoleService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageRole", value = "/ManageRole")
public class ManageRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = RoleService.getAllRoles();
        request.setAttribute("r", roles);
        String updateRoleResult = (String) request.getAttribute("updateRoleResult");
        request.setAttribute("updateRoleResult", updateRoleResult);
        request.getRequestDispatcher("admin/ManageRole.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
