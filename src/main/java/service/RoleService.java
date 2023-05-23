package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RoleService {
    public static List<Role> getAllRoles() {
        List<Role> listRole = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from roles";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setRole_id(rs.getInt("role_id"));
                role.setRole_name(rs.getString("role_name"));
                role.setRole_permission(rs.getInt("role_permission"));
                role.setCart_permission(rs.getInt("cart_permission"));
                role.setOrder_permission(rs.getInt("order_permission"));
                role.setProduct_permission(rs.getInt("product_permission"));
                role.setUser_permission(rs.getInt("user_permission"));
                role.setShipping_address_permission(rs.getInt("shipping_address_permission"));
                listRole.add(role);
            }
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listRole;
    }

    public static List<Role> getAllRolesUser() {
        List<Role> listRole = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from roles r join users u on r.role_id = u.role";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setRole_id(rs.getInt("role_id"));
                role.setRole_name(rs.getString("role_name"));
                role.setName(rs.getString("fullname"));
                role.setEmail(rs.getString("email"));
                role.setRole_permission(rs.getInt("role_permission"));
                role.setCart_permission(rs.getInt("cart_permission"));
                role.setOrder_permission(rs.getInt("order_permission"));
                role.setProduct_permission(rs.getInt("product_permission"));
                role.setUser_permission(rs.getInt("user_permission"));
                role.setShipping_address_permission(rs.getInt("shipping_address_permission"));
                role.setUser_id(rs.getString("id_user"));
                listRole.add(role);
            }
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listRole;
    }

    public static Role getRole(int idRole) {
        Role role = new Role();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from roles where role_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idRole);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                role.setRole_id(rs.getInt("role_id"));
                role.setRole_name(rs.getString("role_name"));
                role.setRole_permission(rs.getInt("role_permission"));
                role.setCart_permission(rs.getInt("cart_permission"));
                role.setOrder_permission(rs.getInt("order_permission"));
                role.setProduct_permission(rs.getInt("product_permission"));
                role.setUser_permission(rs.getInt("user_permission"));
                role.setShipping_address_permission(rs.getInt("shipping_address_permission"));
            }
            JDBCUtil.disconection(connection);
            statement.close();
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int addRole(Role role) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO roles (role_name,product_permission,order_permission,role_permission,shipping_address_permission,user_permission,cart_permission) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, role.getRole_name());
            statement.setInt(2, role.getProduct_permission());
            statement.setInt(3, role.getOrder_permission());
            statement.setInt(4, role.getRole_permission());
            statement.setInt(5, role.getShipping_address_permission());
            statement.setInt(6, role.getUser_permission());
            statement.setInt(7, role.getCart_permission());
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int updateRole(Role role) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE roles \n" +
                    "SET " +
                    "    role_name = ?, \n" +
                    "    product_permission = ?, \n" +
                    "    order_permission = ?, \n" +
                    "    role_permission = ?, \n" +
                    "    shipping_address_permission = ?, \n" +
                    "    user_permission = ?, \n" +
                    "    cart_permission = ? where role_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role.getRole_name());
            statement.setInt(2, role.getProduct_permission());
            statement.setInt(3, role.getOrder_permission());
            statement.setInt(4, role.getRole_permission());
            statement.setInt(5, role.getShipping_address_permission());
            statement.setInt(6, role.getUser_permission());
            statement.setInt(7, role.getCart_permission());
            statement.setInt(8, role.getRole_id());
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int removeRole(Role role) {
        PreparedStatement s = null;
        try {
            setRoleUser(role);
            String sql = "DELETE from `roles` WHERE role_id = ?";
            s = ConnectDB.connect(sql);
            s.setInt(1, role.getRole_id());
            int rs = s.executeUpdate();
            s.close();
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int setRoleUser(Role role) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE `users` SET role = 2 WHERE role = ?";
            s = ConnectDB.connect(sql);
            s.setInt(1, role.getRole_id());
            int rs = s.executeUpdate();
            s.close();
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int setRoleUser(int role, String idUser) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE `users` SET role = ? WHERE id_user = ?";
            s = ConnectDB.connect(sql);
            s.setInt(1, role);
            s.setString(2,idUser);
            int rs = s.executeUpdate();
            s.close();
            return 1;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(RoleService.getRole(4));
    }
}
