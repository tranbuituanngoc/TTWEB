package service;

import database.JDBCUtil;
import model.ProductColor;
import model.ProductSize;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductImportedService {
    public static List<ProductColor> getColorProduct(String idProduct) {
        List<ProductColor> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT DISTINCT c.id_color, c.descrip FROM colors c JOIN productimported pi ON pi.id_color=c.id_color WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res.add(
                        new ProductColor(
                                resultSet.getInt("id_color"),
                                resultSet.getString("descrip")
                        )
                );
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<ProductSize> getSizeProduct(String idProduct) {
        List<ProductSize> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT DISTINCT c.id_size, c.descrip FROM sizes c JOIN productimported pi ON pi.id_size=c.id_size WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res.add(
                        new ProductSize(
                                resultSet.getInt("id_size"),
                                resultSet.getString("descrip")
                        )
                );
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Integer> getQuantityProduct(String idProduct) {
        List<Integer> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT inventoryQuantity FROM productimported pi WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res.add(
                        resultSet.getInt("inventoryQuantity")
                );
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Integer> getPriceProduct(String idProduct) {
        List<Integer> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT price FROM productimported pi WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res.add(
                        resultSet.getInt("price")
                );
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Integer> getCostProduct(String idProduct) {
        List<Integer> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT cost FROM productimported pi WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res.add(
                        resultSet.getInt("cost")
                );
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public static int getQuantityDetail(String idProduct, int idSize, int idColor) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT inventoryQuantity FROM productimported WHERE id_product=? AND id_size=?  AND id_color=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2, idSize);
            statement.setInt(3, idColor);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getInt("inventoryQuantity");

                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int getMinPriceProduct(String idProduct) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT price FROM productimported WHERE id_product=? ORDER BY price ASC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getInt("price");
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int getMinCostProduct(String idProduct) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT cost FROM productimported WHERE id_product=? ORDER BY cost ASC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getInt("cost");
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int insert(String idProduct, int idSize, int idColor, int quantity, Timestamp importDate, int price, int cost) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO productimported (id_product,id_size,id_color,inventoryQuantity,inputQuantity,importDate,price,cost) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2, idSize);
            statement.setInt(3, idColor);
            statement.setInt(4, quantity);
            statement.setInt(5, quantity);
            statement.setTimestamp(6, importDate);
            statement.setInt(7, price);
            statement.setInt(8, cost);
            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int delete(String idProduct) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "DELETE  FROM productimported WHERE id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);

            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
