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

    public static void main(String[] args) {
        List<ProductColor> list =ProductColorService.getColorProduct("sp094");
        System.out.println(list.toString());
        List<ProductColor> list2 = ProductColorService.getAll();
        System.out.println(list2);
    }
}
