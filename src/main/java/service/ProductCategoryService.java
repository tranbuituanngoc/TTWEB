package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.Category;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductCategoryService {
    public static List<Category> getCategoryProduct(String id) {
        List<Category> categoryProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select pc.id_category, c.descrip from products pc join categories c on pc.id_category = c.id_category where id_product=?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, id);
            ResultSet rs = pState.executeQuery();
            categoryProduct = new LinkedList<>();
            while (rs.next()) {
                categoryProduct.add(
                        new Category(
                                rs.getInt("id_category"),
                                rs.getString("descrip")
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

    public static List<Category> getAll() {
        List<Category> categoryProduct;
        try {
            PreparedStatement pState = null;
            String sql = "select id_category, descrip from categories ";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            categoryProduct = new LinkedList<>();
            while (rs.next()) {
                categoryProduct.add(
                        new Category(
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

    public static Category selectByDescrip(String d) {
        Category res = null;
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

                res = new Category();
                res.setId(Integer.parseInt(id_category));
                res.setDescription(descrip);
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static String selectByID(String d) {
        String res = "";
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT descrip FROM categories WHERE id_category=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getString("descrip");

                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static void main(String[] args) {
//        List<Category> list =CategoryService.getCategoryProduct("sp094");
//        System.out.println(list.toString());
//        List<Category> list2 = CategoryService.getAll();
        System.out.println(selectByDescrip("gạch giả gỗ"));
    }
}
