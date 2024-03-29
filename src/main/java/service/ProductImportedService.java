package service;

import database.JDBCUtil;
import model.Color;
import model.Size;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductImportedService {
    public static int getQuantityDetail(String idProduct, int idSize, int idColor) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT inventoryQuantity FROM productimported WHERE id_product=? AND id_size=?  AND id_color=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2, idSize);
            statement.setInt(3, idColor);
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
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Size> getSizeProduct(String id) {
        List<Size> sizes;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement pState = null;
            String sql = "select DISTINCT s.id_size, height,weight,length,width,descrip from productimported ps join sizes s on ps.id_size = s.id_size where id_product=?";
            pState = connection.prepareStatement(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            sizes = new LinkedList<>();
            while (rs.next()) {
                Size size = new Size();
                size.setIdSize(rs.getInt("id_size"));
                size.setHeight(rs.getInt("height"));
                size.setWeight(rs.getInt("weight"));
                size.setLength(rs.getInt("length"));
                size.setWidth(rs.getInt("width"));
                size.setDescrip(rs.getString("descrip"));
                sizes.add(size);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sizes;

    }

    public static List<Color> getColorProduct(String id) {
        List<Color> colorProduct;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement pState = null;
            String sql = "select DISTINCT c.id_color, descrip from productimported pc join colors c on pc.id_color = c.id_color where id_product=?";
            pState = connection.prepareStatement(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            colorProduct = new LinkedList<>();
            while (rs.next()) {
                Color color = new Color();
                color.setId_color(rs.getInt("id_color"));
                color.setDescrip(rs.getString("descrip"));
                colorProduct.add(color);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return colorProduct;
    }

    public static int getMinPriceProduct(String idProduct) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT price FROM productimported WHERE id_product=? ORDER BY price ASC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
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


    public static int getPrice(String idProduct, int idSize, int idColor) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT price FROM productimported WHERE id_product=? AND id_size=?  AND id_color=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2, idSize);
            statement.setInt(3, idColor);
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

    public static List<Integer> getPriceListProduct(String idProduct) {
        List<Integer> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT price FROM productimported pi WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
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

    public static List<Integer> getQuantityListProduct(String idProduct) {
        List<Integer> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT inventoryQuantity FROM productimported pi WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
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

    public static List<Integer> getCostListProduct(String idProduct) {
        List<Integer> res = new LinkedList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT cost FROM productimported pi WHERE pi.id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
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


    public static int delete(String idProduct) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "DELETE  FROM productimported WHERE id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);

            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
  
    public static int getFirstPrice(String idProduct){
        int res =0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT price FROM productimported WHERE id_product=? ORDER BY id_productImported ASC\n" +
                    "LIMIT 1;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
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
    public static void main(String[] args) {
        // System.out.println(ProductImportedService.getQuantityDetail("sp404590", 1,
        // 2));
        // System.out.println(ProductImportedService.getColorProduct("sp404590"));
        // System.out.println(ProductImportedService.getSizeProduct("sp404590"));
        // System.out.println(ProductImportedService.getPrice("sp404590", 1, 2));

    }
}
