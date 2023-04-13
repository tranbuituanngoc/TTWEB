package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.ProductSize;
import model.ProductSize;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductSizeService {
    public static List<ProductSize> getSizeProduct(String id) {
        List<ProductSize> sizeProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select pc.id, pc.id_size, descrip from product_size pc join sizes c on pc.id_size = c.id_size where id_product=?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            sizeProduct = new LinkedList<>();
            while (rs.next()) {
                sizeProduct.add(
                        new ProductSize(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getString(3)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sizeProduct;
    }

    public static ProductSize selectByDescrip(String d) {
        ProductSize res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT id_size, descrip FROM sizes WHERE descrip=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_size = resultSet.getString("id_size");
                String descrip = resultSet.getString("descrip");

                res = new ProductSize();
                res.setId_Size(Integer.parseInt(id_size));
                res.setDescription(descrip);
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<ProductSize> getAll() {
        List<ProductSize> sizeProduct;
        try {
            PreparedStatement pState = null;
//            String sql = "select pc.id, pc.id_size, descrip from product_size pc join sizes c on pc.id_size = c.id_size";
            String sql = "select id_size, descrip from sizes ";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            sizeProduct = new LinkedList<>();
            while (rs.next()) {
                sizeProduct.add(
                        new ProductSize(
                                rs.getInt(1),
                                rs.getString(2)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sizeProduct;
    }

    public static void removeProductSizeById(String id_product) {
        PreparedStatement s = null;
        String sql = "delete from product_size where id_product = ?";
        try {
            s = ConnectDB.connect(sql);
            s.setString(1, id_product);
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProductSize(int idProductSize, String id_product) {
        PreparedStatement s = null;
        try {
            String sql = "insert into product_size(id_size, id_product) values(?, ?)";
            s = ConnectDB.connect(sql);
            s.setInt(1, idProductSize);
            s.setString(2, id_product);
            s.executeUpdate();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<ProductSize> list = ProductSizeService.getSizeProduct("sp094");
        System.out.println(list.toString());
        List<ProductSize> list2 = ProductSizeService.getAll();
        System.out.println(list2);
    }
}
