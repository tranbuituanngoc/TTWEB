package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.ProductSize;
import model.ProductSize;
import model.Size;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductSizeService {
    public static List<Size> getSizeProduct(String id) {
        List<Size> sizes;
        try {
            PreparedStatement pState = null;
            String sql = "select * from product_size ps join sizes s on ps.id_size = s.id_size where id_product=?";
            pState = ConnectDB.connect(sql);
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
                sizes.add(size);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sizes;
    }
//    public static ProductSize selectByDescrip(String d) {
//        ProductSize res = null;
//        try {
//            Connection connection = JDBCUtil.getConnection();
//            String sql = "SELECT id_size, descrip FROM sizes WHERE descrip=?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, d);
//            System.out.println(sql);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String id_size = resultSet.getString("id_size");
//                String descrip = resultSet.getString("descrip");
//                res = new ProductSize();
//                res.setId_Size(Integer.parseInt(id_size));
//                res.setDescript(descrip);
//                break;
//            }
//            JDBCUtil.disconection(connection);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return res;
//    }
    public static List<Size> getAll() {
        List<Size> sizes;
        try {
            PreparedStatement pState = null;
            String sql = "select * from product_size ps join sizes s on ps.id_size = s.id_size";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            sizes = new LinkedList<>();
            while (rs.next()) {
                Size size = new Size();
                size.setIdSize(rs.getInt("id_size"));
                size.setHeight(rs.getInt("height"));
                size.setWeight(rs.getInt("weight"));
                size.setLength(rs.getInt("length"));
                size.setWidth(rs.getInt("width"));
                sizes.add(size);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sizes;
    }
    public static Size getSizeById(int idSize) {
        Size size = new Size();
        try {
            PreparedStatement pState = null;
            String sql = "select * from sizes where id_size=?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, idSize);
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                size.setIdSize(rs.getInt("id_size"));
                size.setHeight(rs.getInt("height"));
                size.setWeight(rs.getInt("weight"));
                size.setLength(rs.getInt("length"));
                size.setWidth(rs.getInt("width"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return size;
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
//        List<ProductSize> list =ProductSizeService.getSizeProduct("sp094");
//        System.out.println(list.toString());
//        List<ProductSize> list2 = ProductSizeService.getAll();
//        System.out.println(list2);
//        ProductSizeService.removeProductSizeById("sp094");

//        ProductSizeService.addProductSize(6,"sp094");
//        list =ProductSizeService.getSizeProduct("sp094");
//        System.out.println(list.toString());
        System.out.println(ProductSizeService.getSizeById(1));

    }
}
