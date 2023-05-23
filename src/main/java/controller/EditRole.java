package controller;

import model.Role;
import service.RoleService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditRole", value = "/EditRole")
public class EditRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId_raw = request.getParameter("role_id");
        int roleId = Integer.parseInt(roleId_raw);
        Role role = RoleService.getRole(roleId);
        request.setAttribute("r", role);
        request.getRequestDispatcher("admin/EditRole.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role_permission  =request.getParameter("role_permission");
        String role_id_raw = request.getParameter("role_id");
        int role_id = Integer.parseInt(role_id_raw);
        String shipping_address_permission  =request.getParameter("shipping_address_permission");
        String product_permission  =request.getParameter("product_permission");
        String cart_permission  =request.getParameter("cart_permission");
        String order_permission  =request.getParameter("order_permission");
        String user_permission  =request.getParameter("user_permission");
        String role_name = request.getParameter("role_name");
        Role role = new Role();
        role.setRole_name(role_name);
        role.setRole_id(role_id);
        if (role_permission != null) role.setRole_permission(1);
        else role.setRole_permission(0);

        if (shipping_address_permission != null) role.setShipping_address_permission(1);
        else role.setShipping_address_permission(0);

        if (product_permission != null) role.setProduct_permission(1);
        else role.setProduct_permission(0);

        if (cart_permission != null) role.setCart_permission(1);
        else role.setCart_permission(0);

        if (order_permission != null) role.setOrder_permission(1);
        else role.setOrder_permission(0);

        if (user_permission != null) role.setUser_permission(1);
        else role.setUser_permission(0);

        int result =RoleService.updateRole(role);
        request.setAttribute("updateRoleResult" , result);
        response.sendRedirect("/ManageRole");
    }
}
