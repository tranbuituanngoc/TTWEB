package service;

import database.ConnectDB;
import model.ImageProduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductImageService {
    public static List<ImageProduct> getImageProduct(String id) {
        List<ImageProduct> imageProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select id_image, image from imagesproduct where id_product=?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            imageProduct = new LinkedList<>();
            while (rs.next()) {
                imageProduct.add(
                        new ImageProduct(
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
        return imageProduct;
    }

    public static List<ImageProduct> getAll() {
        List<ImageProduct> imageProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select id_image, image from imagesproduct ";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            imageProduct = new LinkedList<>();
            while (rs.next()) {
                imageProduct.add(
                        new ImageProduct(
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
        return imageProduct;
    }

    public static void main(String[] args) {
        List<ImageProduct> list = ProductImageService.getImageProduct("sp094");
        System.out.println(list.toString());
        List<ImageProduct> list2 = ProductImageService.getAll();
        System.out.println(list2);
    }
}
