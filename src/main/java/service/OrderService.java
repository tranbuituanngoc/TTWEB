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
            // Tạo câu lệnh SELECT để kiểm tra hàng lỗi
            String selectSql = "SELECT COUNT(*) AS count_error " +
                    "FROM productimported pi " +
                    "INNER JOIN cart c ON pi.id_product = c.id_product " +
                    "AND pi.id_size = c.id_size " +
                    "AND pi.id_color = c.id_color " +
                    "WHERE c.id_order = ? " +
                    "AND pi.inventoryQuantity - c.quantity < 0";

            PreparedStatement statement = connection.prepareStatement(selectSql);
            statement.setString(1, order.getOrderID());
            ResultSet resultSet = statement.executeQuery();

            // Kiểm tra kết quả của câu lệnh SELECT
            if (resultSet.next()) {
                int countError = resultSet.getInt("count_error");
                if (countError > 0) {
                    System.out.println("Không thể cập nhật. Có hàng lỗi.");
                    res = -1;
                } else {
                    // Tiếp tục thực hiện câu lệnh UPDATE nếu không có hàng lỗi
                    String updateSql = "UPDATE productimported pi " +
                            "INNER JOIN cart c ON pi.id_product = c.id_product " +
                            "AND pi.id_size = c.id_size " +
                            "AND pi.id_color = c.id_color " +
                            "SET pi.inventoryQuantity = pi.inventoryQuantity - c.quantity " +
                            "WHERE c.id_order = ?";

                    // Tạo prepared statement cho câu lệnh UPDATE
                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);

                    // Thiết lập giá trị tham số
                    updateStatement.setString(1, order.getOrderID());

                    // Thực thi câu lệnh UPDATE
                    int rowsUpdated = updateStatement.executeUpdate();

                    // Kiểm tra số dòng bị ảnh hưởng bởi câu lệnh UPDATE
                    if (rowsUpdated > 0) {
                        System.out.println("Cập nhật thành công. Số dòng bị ảnh hưởng: " + rowsUpdated);
                        res = 1;
                    } else {
                        System.out.println("Không có dòng nào được cập nhật.");
                        res =-1;
                    }

                    // Đóng kết nối và các tài nguyên
                    updateStatement.close();
                }
            }
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Order> getAllOrder() {
        List<Order> listOrder = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select id_order, u.id_user, payment_id, totalPrice,transport_status_id, status_id, shipping_cost, shipping_time, orderDate, voucher_code, idTransport, fullname from orders o join users u on o.id_user = u.id_user ORDER BY orderDate DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getString("id_order"));
                order.setUserID(rs.getString("id_user"));
                order.setPaymentMethodId(rs.getInt("payment_id"));
                order.setTotalPrice(rs.getInt("totalPrice"));
                order.setStatus(rs.getInt("status_id"));
                order.setTransport_status(rs.getInt("transport_status_id"));
                order.setShipping_cost(rs.getInt("shipping_cost"));
                order.setShipping_time(rs.getTimestamp("shipping_time"));
                order.setOrder_date(rs.getTimestamp("orderDate"));
                order.setVoucher_code(rs.getString("voucher_code"));
                order.setIdTransport(rs.getString("idTransport"));
                order.setFullName(rs.getString("fullname"));
                listOrder.add(order);
            }
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOrder;
    }

    public static Order getOrder(String idOrder) {
        Order order = new Order();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select id_order, u.id_user, payment_id, totalPrice,transport_status_id, status_id, shipping_cost, shipping_time, orderDate, voucher_code, idTransport, fullname from orders o join users u on o.id_user = u.id_user where id_order = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idOrder);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                order.setOrderID(rs.getString("id_order"));
                order.setUserID(rs.getString("id_user"));
                order.setPaymentMethodId(rs.getInt("payment_id"));
                order.setTotalPrice(rs.getInt("totalPrice"));
                order.setStatus(rs.getInt("status_id"));
                order.setTransport_status(rs.getInt("transport_status_id"));
                order.setShipping_cost(rs.getInt("shipping_cost"));
                order.setShipping_time(rs.getTimestamp("shipping_time"));
                order.setOrder_date(rs.getTimestamp("orderDate"));
                order.setVoucher_code(rs.getString("voucher_code"));
                order.setIdTransport(rs.getString("idTransport"));
                order.setFullName(rs.getString("fullname"));
            }
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }
    public static void deleteOrder(String orderID){
        PreparedStatement s = null;
        try {
            String sql = "DELETE from `orders` WHERE id_order = ?";
            s = ConnectDB.connect(sql);
            s.setString(1,orderID);
            int rs = s.executeUpdate();
            s.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static String updateStatus(int idStatus, int idStatusTransport, String idOrder) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE `orders` SET status_id = ?, transport_status_id = ? WHERE id_order = ?";
            s = ConnectDB.connect(sql);
            s.setInt(1, idStatus);
            s.setInt(2, idStatusTransport);
            s.setString(3, idOrder);
            int rs = s.executeUpdate();
            s.close();

            if (rs > 0) {
                return "Cập nhật thành công.";
            } else {
                return "Không thể thay đổi trạng thái.";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return "Lỗi xảy ra khi cập nhật.";
        }
    }

    public static String cancelOrder(String idOrder) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE `orders` SET status_id = 2 WHERE id_order = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, idOrder);
            int rs = s.executeUpdate();
            s.close();

            if (rs > 0) {
                return "Cập nhật thành công.";
            } else {
                return "Không thể thay đổi trạng thái.";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return "Lỗi xảy ra khi cập nhật.";
        }
    }

    public static List<Order> getOrderById(String idUser) {
        List<Order> listOrder = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select id_order, u.id_user, payment_id, totalPrice,transport_status_id, status_id, shipping_cost, shipping_time, orderDate, voucher_code, idTransport, fullname from orders o join users u on o.id_user = u.id_user where o.id_user = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getString("id_order"));
                order.setUserID(rs.getString("id_user"));
                order.setPaymentMethodId(rs.getInt("payment_id"));
                order.setTotalPrice(rs.getInt("totalPrice"));
                order.setStatus(rs.getInt("status_id"));
                order.setTransport_status(rs.getInt("transport_status_id"));
                order.setShipping_cost(rs.getInt("shipping_cost"));
                order.setShipping_time(rs.getTimestamp("shipping_time"));
                order.setOrder_date(rs.getTimestamp("orderDate"));
                order.setVoucher_code(rs.getString("voucher_code"));
                order.setIdTransport(rs.getString("idTransport"));
                order.setFullName(rs.getString("fullname"));
                listOrder.add(order);
            }
            JDBCUtil.disconection(connection);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOrder;
    }
    public static void main(String[] args) {
//        System.out.println(OrderService.getAllOrder());
//        System.out.println(OrderService.getOrder("DH89386"));\
//        Order o = new Order();
//        o.setOrderID("DH79730");
//        System.out.println(OrderService.updateQuantity(o));

    }

}
