package service;

import model.*;
import database.ConnectDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoryService {
    public static List<Category> getAllCategory(){
        List<Category> result = new LinkedList<>();
        try {
            PreparedStatement pState = null;
            String sql = "SELECT * FROM categories ;";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("id_category"));
                category.setDescription(rs.getString("descrip"));
                result.add(category);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(CategoryService.getAllCategory().toString());
    }
}