package service;

import database.ConnectDB;
import model.ProductSize;
import model.ProductSize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductSizeService {
    public static List<ProductSize> getSizeProduct(String id){
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

    public static List<ProductSize> getAll(){
        List<ProductSize> sizeProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select pc.id, pc.id_size, descrip from product_size pc join sizes c on pc.id_size = c.id_size";
            pState = ConnectDB.connect(sql);
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

    public static void main(String[] args) {
        List<ProductSize> list =ProductSizeService.getSizeProduct("sp094");
        System.out.println(list.toString());
        List<ProductSize> list2 = ProductSizeService.getAll();
        System.out.println(list2);
    }
}
