package controller;

import model.Role;
import service.RoleService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditRoleUser", value = "/EditRoleUser")
public class EditRoleUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId_raw = request.getParameter("role_id");
        int roleId = Integer.parseInt(roleId_raw);
        String userId = request.getParameter("user_id");
        String userName = request.getParameter("username");
        List<Role> roles = RoleService.getAllRoles();
        request.setAttribute("roles", roles);
        request.setAttribute("r", roleId);
        request.setAttribute("userId", userId);
        request.setAttribute("userName", userName);
        request.getRequestDispatcher("/admin/EditRoleUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId_raw = request.getParameter("role_id");
        int roleId = Integer.parseInt(roleId_raw);
        String userId = request.getParameter("userId");
        RoleService.setRoleUser(roleId, userId);
        response.sendRedirect("ManageRoleUser");
    }
}
