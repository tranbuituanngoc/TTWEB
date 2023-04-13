package service;

import database.JDBCUtil;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsService {
    public static int getQuantityDetail(String idProduct, int idSize, int idCate, int idColor) {
        int res =0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT quantityDetails FROM productdetails WHERE id_product=? AND id_size=? AND id_category=? AND id_color=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2,idSize);
            statement.setInt(3,idCate);
            statement.setInt(4,idColor);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getInt("quantityDetails");

                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int insert(String idProduct, int idSize, int idCate, int idColor, int quantity) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO productdetails (id_product,id_size,id_category,id_color,quantityDetails) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2, idSize);
            statement.setInt(3,idCate);
            statement.setInt(4, idColor);
            statement.setInt(5, quantity);
            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
