package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.Color;
import model.ProductColor
;
import model.ProductSize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductColorService {
    public static List<Color> getColorProduct(String id){
        List<Color> colorProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select * from product_color pc join colors c on pc.id_color = c.id_color where id_product=?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            colorProduct = new LinkedList<>();
            while (rs.next()) {
                Color color = new Color();
                color.setId_color(rs.getInt("id_color"));
                color.setDescrip(rs.getString("descrip"));
                colorProduct.add(color);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return colorProduct;
    }

    public static Color selectByDescrip(String d) {
        Color res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT id_color, descrip FROM colors WHERE descrip=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_color = resultSet.getString("id_color");
                String descrip = resultSet.getString("descrip");

                res = new Color();
                res.setId_color(Integer.parseInt(id_color));
                res.setDescrip(descrip);
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Color> getAll(){
        List<Color> colorProduct;
        try {
            PreparedStatement pState = null;
//            String sql = "select pc.id, pc.id_color, descrip from product_color pc join colors c on pc.id_color = c.id_color";
            String sql = "select id_color, descrip from colors ";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            colorProduct = new LinkedList<>();
            while (rs.next()) {
                colorProduct.add(
                        new Color(
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
        return colorProduct;
    }


    public static void removeProductColorById(String id_product){
        PreparedStatement s = null;
        String sql = "delete from product_color where id_product = ?";
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
    public static void addProductColor(int idProductColor, String id_product){
        PreparedStatement s = null;
        try {
            String sql = "insert into product_color(id_color, id_product) values(?, ?)";
            s = ConnectDB.connect(sql);
            s.setInt(1, idProductColor);
            s.setString(2, id_product);
            s.executeUpdate();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static Color getColorById(int idColor){
        Color result = new Color();
        try {
            PreparedStatement pState = null;
            String sql = "select * from colors where id_color = ?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, idColor);
            ResultSet rs = pState.executeQuery();
            while (rs.next()){
                 result.setId_color(rs.getInt("id_color"));
                 result.setDescrip(rs.getString("descrip"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(ProductColorService.getColorById(1));
//        List<ProductColor> list =ProductColorService.getColorProduct("sp094");
//        System.out.println(list.toString());
//        ProductColorService.removeProductColorById("sp094");
//        list =ProductColorService.getColorProduct("sp094");
//        System.out.println(list.toString());
//        ProductColorService.addProductColor(6, "sp094");
//        list =ProductColorService.getColorProduct("sp094");
//        System.out.println(list.toString());
//        List<ProductColor> list2 = ProductColorService.getAll();
//        System.out.println(list2);

    }
}
