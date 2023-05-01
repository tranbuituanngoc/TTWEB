package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.ImageProduct;
import model.Product;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductImageService {
    public static List<ImageProduct> getAllImageProduct(String id) {
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

    //    sửa lại csdl thì bỏ cmt cho cái này
//    public static List<ImageProduct> getAllImageProduct(String id) {
//        List<ImageProduct> imageProduct;
//        try {
//            PreparedStatement pState = null;
//            String sql = "select id_image, image from imagesproduct where id_product=? and type='image' ";
//            pState = ConnectDB.connect(sql);
//            pState.setString(1, id);
//            ResultSet rs = pState.executeQuery();
//            imageProduct = new LinkedList<>();
//            while (rs.next()) {
//                imageProduct.add(
//                        new ImageProduct(
//                                rs.getInt(1),
//                                rs.getString(2)
//                        )
//                );
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return imageProduct;
//    }
    public static String getThumbProduct(String id) {
        String res = "";
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM imagesproduct WHERE id_product=? AND type='thumb'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getString("image");

                System.out.println(res.toString());
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<ImageProduct> getAll() {
        List<ImageProduct> imageProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select id_image, image from imagesproduct  and type= 'image'";
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

    public static int updateImageProduct(Product p) {
        int res = 0;
        Connection connection = JDBCUtil.getConnection();
        String sql = "UPDATE imagesproduct " +
                " SET " +
                " image=?" +
                " WHERE id_product=? AND id_image=?  AND type='image'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            for (ImageProduct i : p.getImage()) {
                st.setString(1, i.getImage());
                st.setString(2, p.getProductID());
                st.setInt(3, i.getId());
                System.out.println(sql);
                res += st.executeUpdate();
                JDBCUtil.disconection(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int updateThumbProduct(Product p) {
        int res = 0;
        Connection connection = JDBCUtil.getConnection();
        String sql = "UPDATE imagesproduct " +
                " SET " +
                " image=?" +
                " WHERE id_product=? AND type='thumb'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            for (ImageProduct i : p.getImage()) {
                st.setString(1, i.getImage());
                st.setString(2, p.getProductID());
                System.out.println(sql);
                res += st.executeUpdate();
                JDBCUtil.disconection(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int insertImageProduct(Product p) {
        int res = 0;
        Connection connection = JDBCUtil.getConnection();
        String sql = "INSERT INTO imagesproduct (id_product, image, type) VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (ImageProduct i : p.getImage()) {
                statement.setString(1, p.getProductID());
                statement.setString(2, i.getImage());
                statement.setString(3, "image");
                System.out.println(sql);
                res += statement.executeUpdate();
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int insertThumbProduct(Product p) {
        int res = 0;
        Connection connection = JDBCUtil.getConnection();
        String sql = "INSERT INTO imagesproduct (id_product, image, type) VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, p.getProductID());
            statement.setString(2, p.getThumb());
            statement.setString(3, "thumb");
            System.out.println(sql);
            res += statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int deleteImageProduct(Product p) {
        int res = 0;
        Connection connection = JDBCUtil.getConnection();
        String sql = "DELETE  FROM imagesproduct where id_product = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static void main(String[] args) {
        List<ImageProduct> list = ProductImageService.getAllImageProduct("sp094");
        System.out.println(list.toString());
//        List<ImageProduct> list2 = ProductImageService.getAll();
//        System.out.println(list2);
        Product p = new Product();
        p.setProductID("sp094");
        List<ImageProduct> image = new ArrayList<>();
        image.add(new ImageProduct(191, "https://khatra.com.vn/wp-content/uploads/2020/06/walnut-105-.jpg"));
        image.add(new ImageProduct(92, "https://khatra.com.vn/wp-content/uploads/2022/11/69000.jpg"));
        p.setImage(image);
        updateImageProduct(p);
        List<ImageProduct> list2 = ProductImageService.getAllImageProduct("sp094");
        System.out.println(list2.toString());
    }
}
