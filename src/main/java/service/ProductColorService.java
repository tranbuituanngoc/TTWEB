package service;

import database.ConnectDB;
import model.ProductColor
;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductColorService {
    public static List<ProductColor> getColorProduct(String id){
        List<ProductColor> colorProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select pc.id, pc.id_color, descrip from product_color pc join colors c on pc.id_color = c.id_color where id_product=?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            colorProduct = new LinkedList<>();
            while (rs.next()) {
                colorProduct.add(
                        new ProductColor(
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
        return colorProduct;
    }

    public static List<ProductColor> getAll(){
        List<ProductColor> colorProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select pc.id, pc.id_color, descrip from product_color pc join colors c on pc.id_color = c.id_color";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            colorProduct = new LinkedList<>();
            while (rs.next()) {
                colorProduct.add(
                        new ProductColor(
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

    public static void main(String[] args) {
        List<ProductColor> list =ProductColorService.getColorProduct("sp094");
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
