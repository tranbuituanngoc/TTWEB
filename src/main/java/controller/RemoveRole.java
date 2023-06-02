package controller;

import model.Role;
import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RemoveRole", value = "/RemoveRole")
public class RemoveRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role_id_raw = request.getParameter("role_id");
        int role_id = Integer.parseInt(role_id_raw);
        Role role = RoleService.getRole(role_id);
        int result = RoleService.removeRole(role);
        request.setAttribute("removeRoleResult" , result);
        response.sendRedirect("/ManageRole");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
