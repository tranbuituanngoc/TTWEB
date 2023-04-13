package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.Product;
import model.ProductCategory;
import model.ProductSize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductCategoryService {
    public static List<ProductCategory> getCategoryProduct(String id){
        List<ProductCategory> categoryProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select pc.id, pc.id_category, descrip from product_category pc join categories c on pc.id_category = c.id_category where id_product=?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            categoryProduct = new LinkedList<>();
            while (rs.next()) {
                categoryProduct.add(
                    new ProductCategory(
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
        return categoryProduct;
    }

    public static List<ProductCategory> getAll(){
        List<ProductCategory> categoryProduct;
        try {
            PreparedStatement pState = null;
//            String sql = "select pc.id, pc.id_category, descrip from product_category pc join categories c on pc.id_category = c.id_category";
            String sql = "select id_category, descrip from categories ";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            categoryProduct = new LinkedList<>();
            while (rs.next()) {
                categoryProduct.add(
                        new ProductCategory(
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
        return categoryProduct;
    }
    public static ProductCategory selectByDescrip(String d) {
        ProductCategory res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT id_category, descrip FROM categories WHERE descrip=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_category = resultSet.getString("id_category");
                String descrip = resultSet.getString("descrip");

                res = new ProductCategory();
                res.setId_Category(Integer.parseInt(id_category));
                res.setDescription(descrip);
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static void main(String[] args) {
//        List<ProductCategory> list =ProductCategoryService.getCategoryProduct("sp094");
//        System.out.println(list.toString());
//        List<ProductCategory> list2 = ProductCategoryService.getAll();
//        System.out.println(list2);
    }
}
