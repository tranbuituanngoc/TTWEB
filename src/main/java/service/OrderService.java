package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderService {
     public static boolean validOrder(Order order){
        String id = order.getOrderID();
        PreparedStatement s = null;
        try {
            String sql = "SELECT * from orders where id_order = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, id);
            ResultSet rs = s.executeQuery();
            if(rs.next()){
                rs.close();
                s.close();
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int addOrder(Order order){
        if (order.getOrderID() != null){
            int res = 0;
            try {
                Connection connection = JDBCUtil.getConnection();
                String sql = "INSERT INTO orders (id_order, id_user, payment_id, totalPrice, status_id, shipping_cost, shipping_time, voucher_code, idTransport) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, order.getOrderID());
                if(order.getOrderID()!= null) statement.setString(2, order.getUserID());
                statement.setInt(3, order.getPaymentMethodId());
                statement.setInt(4, order.getTotalPrice());
                statement.setInt(5, order.getStatus());
                statement.setInt(6, order.getShipping_cost());
                statement.setTimestamp(7, order.getShipping_time());
                statement.setString(8, order.getVoucher_code());
                statement.setString(9, order.getIdTransport());
                res = statement.executeUpdate();
                JDBCUtil.disconection(connection);
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return res;
        }
        return 0;
    }



    public static int updateQuantity(Order order){
            int res = 0;
            try {
                Connection connection = JDBCUtil.getConnection();
                String sql = "UPDATE productimported\n" +
                        "SET inventoryQuantity = inventoryQuantity - (\n" +
                        "  SELECT SUM(quantity)\n" +
                        "  FROM cart\n" +
                        "  WHERE cart.id_size = productimported.id_size\n" +
                        "    AND cart.id_color = productimported.id_color\n" +
                        "    AND cart.id_product = productimported.id_product\n" +
                        "    AND cart.id_order = ?\n" +
                        ")\n" +
                        "WHERE EXISTS (\n" +
                        "  SELECT 1\n" +
                        "  FROM cart\n" +
                        "  WHERE cart.id_size = productimported.id_size\n" +
                        "    AND cart.id_color = productimported.id_color\n" +
                        "    AND cart.id_product = productimported.id_product\n" +
                        "    AND cart.id_order = ?\n" +
                        ")\n" +
                        "AND (inventoryQuantity - (\n" +
                        "  SELECT SUM(quantity)\n" +
                        "  FROM cart\n" +
                        "  WHERE cart.id_size = productimported.id_size\n" +
                        "    AND cart.id_color = productimported.id_color\n" +
                        "    AND cart.id_product = productimported.id_product\n" +
                        "    AND cart.id_order = ?\n" +
                        ")) >= 0;";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, order.getOrderID());
                statement.setString(2, order.getOrderID());
                statement.setString(3, order.getOrderID()); // add another parameter for the third occurrence of orderID
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    res = 1;
                } else {
                    res = -1; // set result to -1 if no rows were updated
                }
                JDBCUtil.disconection(connection);
                statement.close();
            } catch (SQLException e) {
                res =-1;
                throw new RuntimeException(e);
            }
            return res;
    }

    public static List<Order> getAllOrder() {
        List<Order> listOrder = new LinkedList<>();

        return listOrder;
    }

    public static void updateStatus(String id, int status){
        PreparedStatement s = null;
        try {
            String sql = "update `order` set status = ? where id = ?";
            s = ConnectDB.connect(sql);
            s.setInt(1,status);
            s.setString(1,id);
            int rs = s.executeUpdate();
            s.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteOrder(String id){
        PreparedStatement s = null;
        try {
            String sql = "DELETE from `order` WHERE id = ?";
            s = ConnectDB.connect(sql);
            s.setString(1,id);
            int rs = s.executeUpdate();
            s.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        deleteOrder("1324653113234");
        Order order = new Order();
        order.setShipping_cost(3242);
//        order.setShipping_time(234);
        order.setTotalPrice(234324);
        order.setUserID("123213");
        order.setOrderID("21313");
        order.setPaymentMethodId(12313);
        OrderService.addOrder(order);
    }
}
