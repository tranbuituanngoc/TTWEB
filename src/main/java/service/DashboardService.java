package service;

import database.JDBCUtil;
import model.Order;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardService {
    public static int selectRegisterInMonth() {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(id_user)\n" +
                    "FROM `users`\n" +
                    "WHERE users.time_valid BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW()\n";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                res = count;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int selectOrderInMonth() {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT count(id_order)\n" +
                    "FROM `orders`\n" +
                    "WHERE orderDate BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW()";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                res = count;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int selectRevenueInMonth() {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT SUM(o.totalPrice - o.shipping_cost) AS revenue\n" +
                    "FROM `orders` AS o\n" +
                    "WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "  AND o.orderDate BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW();";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                res = count;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int selectSalesInMonth() {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT SUM((o.totalPrice - o.shipping_cost) - total_cost) AS pre_tax_profit\n" +
                    "FROM (\n" +
                    "    SELECT o.id_order, SUM(c.quantity * pi.cost) AS total_cost\n" +
                    "    FROM `orders` AS o\n" +
                    "    JOIN `cart` AS c ON o.id_order = c.id_order\n" +
                    "    JOIN `productimported` AS pi ON c.id_product = pi.id_product\n" +
                    "    AND c.id_color = pi.id_color\n" +
                    "    AND c.id_size = pi.id_size\n" +
                    "    WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "        AND o.orderDate BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW()\n" +
                    "    GROUP BY o.id_order\n" +
                    ") AS subquery\n" +
                    "JOIN `orders` AS o ON subquery.id_order = o.id_order\n" +
                    "WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "  AND o.orderDate BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW();";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                res = count;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static Map<String, Integer> selectSalesInSixMonth() {
        Map<String, Integer> res= new HashMap<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT\n" +
                    "    CONCAT(MONTHNAME(o.orderDate), ' ', YEAR(o.orderDate)) AS order_month,\n" +
                    "    SUM((o.totalPrice - o.shipping_cost) - subquery.total_cost) AS pre_tax_profit\n" +
                    "FROM (\n" +
                    "    SELECT o.id_order, SUM(c.quantity * pi.cost) AS total_cost\n" +
                    "    FROM `orders` AS o\n" +
                    "    JOIN `cart` AS c ON o.id_order = c.id_order\n" +
                    "    JOIN `productimported` AS pi ON c.id_product = pi.id_product\n" +
                    "        AND c.id_color = pi.id_color\n" +
                    "        AND c.id_size = pi.id_size\n" +
                    "    WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "        AND o.orderDate BETWEEN DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-01'), INTERVAL 5 MONTH) AND NOW()\n" +
                    "    GROUP BY o.id_order\n" +
                    ") AS subquery\n" +
                    "JOIN `orders` AS o ON subquery.id_order = o.id_order\n" +
                    "WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "GROUP BY order_month, o.orderDate\n" +
                    "ORDER BY MAX(o.orderDate) DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String date= resultSet.getString(1);
                int count = resultSet.getInt(2);


                res.put(date,count);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static Map<String, Integer> selectSalesInTenDay() {
        Map<String, Integer> res= new HashMap<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT\n" +
                    "    CONCAT(DAY(o.orderDate),\n" +
                    "           CASE\n" +
                    "               WHEN DAY(o.orderDate) BETWEEN 11 AND 13 THEN 'th'\n" +
                    "               WHEN DAY(o.orderDate) % 10 = 1 THEN 'st'\n" +
                    "               WHEN DAY(o.orderDate) % 10 = 2 THEN 'nd'\n" +
                    "               WHEN DAY(o.orderDate) % 10 = 3 THEN 'rd'\n" +
                    "               ELSE 'th'\n" +
                    "           END,\n" +
                    "           ' ', MONTHNAME(o.orderDate)) AS order_date,\n" +
                    "    SUM((o.totalPrice - o.shipping_cost) - subquery.total_cost) AS pre_tax_profit\n" +
                    "FROM (\n" +
                    "    SELECT o.id_order, SUM(c.quantity * pi.cost) AS total_cost\n" +
                    "    FROM `orders` AS o\n" +
                    "    JOIN `cart` AS c ON o.id_order = c.id_order\n" +
                    "    JOIN `productimported` AS pi ON c.id_product = pi.id_product\n" +
                    "        AND c.id_color = pi.id_color\n" +
                    "        AND c.id_size = pi.id_size\n" +
                    "    WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "        AND o.orderDate BETWEEN DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL 9 DAY) AND NOW()\n" +
                    "    GROUP BY o.id_order\n" +
                    ") AS subquery\n" +
                    "JOIN `orders` AS o ON subquery.id_order = o.id_order\n" +
                    "WHERE o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "GROUP BY order_date, o.orderDate\n" +
                    "ORDER BY MAX(o.orderDate) DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String date= resultSet.getString(1);
                int count = resultSet.getInt(2);


                res.put(date,count);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static Map<Product, Integer> selectBestSellerProduct() {
        Map<Product, Integer> res= new HashMap<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT p.name, SUM(c.quantity) AS total_quantity\n" +
                    "FROM `cart` AS c\n" +
                    "JOIN `orders` AS o ON c.id_order = o.id_order\n" +
                    "JOIN `products` AS p ON c.id_product = p.id_product\n" +
                    "WHERE o.orderDate BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW() AND o.status_id = 1 AND o.transport_status_id = 2\n" +
                    "GROUP BY p.id_product\n" +
                    "ORDER BY total_quantity DESC\n" +
                    "LIMIT 5;";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Product p= ProductService.getByName(resultSet.getString(1));
                int count = resultSet.getInt(2);


                res.put(p,count);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static Map<String, Integer> selectBestSellerCategory() {
        Map<String, Integer> res= new HashMap<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT cat.descrip, SUM(c.quantity) AS total_quantity\n" +
                    "FROM `cart` AS c\n" +
                    "JOIN `orders` AS o ON c.id_order = o.id_order\n" +
                    "JOIN `products` AS p ON c.id_product = p.id_product\n" +
                    "JOIN `categories` AS cat ON p.id_category = cat.id_category\n" +
                    "WHERE o.orderDate BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND NOW()\n" +
                    "GROUP BY cat.descrip\n" +
                    "ORDER BY total_quantity DESC\n" +
                    "LIMIT 3;\n";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String category= resultSet.getString(1);
                int count = resultSet.getInt(2);

                res.put(category,count);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Order> getRecentOrder() {
        List<Order> res= new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT id_order, u.fullname , orderDate,totalPrice, status_id\n" +
                    "FROM orders o\n" +
                    "JOIN users u ON u.id_user=o.id_user\n" +
                    "ORDER BY orderDate DESC LIMIT 6 ";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order= new Order();
                String id_order= resultSet.getString("id_order");
                String fullname= resultSet.getString("fullname");
                Timestamp orderDate= resultSet.getTimestamp("orderDate");
                int totalPrice= resultSet.getInt("totalPrice");
                int status= resultSet.getInt("status_id");

                order.setOrderID(id_order);
                order.setFullName(fullname);
                order.setOrder_date(orderDate);
                order.setTotalPrice(totalPrice);
                order.setStatus(status);

                res.add(order);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static void main(String[] args) {
//        System.out.println(selectRegisterInMonth());
//        System.out.println(selectOrderInMonth());
//        System.out.println(selectRevenueInMonth());
//        System.out.println(selectSalesInMonth());
//        System.out.println(selectSalesInSixMonth());
//        System.out.println(selectSalesInTenDay());
//        System.out.println(selectBestSellerProduct());
//        System.out.println(selectBestSellerCategory());
//        System.out.println(getRecentOrder());
    }
}
