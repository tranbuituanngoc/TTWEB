package service;

import database.JDBCUtil;
import model.Quantity;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuantityService {
    public static int insert(Quantity q) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO quantities (id_product,inventoryQuantity,inputQuantity,importDate) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, q.getProduct().getProductID());
            statement.setInt(2, q.getProduct().getQuantity());
            statement.setInt(3, q.getProduct().getQuantity());
            statement.setTimestamp(4, q.getImportDate());
            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int update(Quantity q) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE quantities " +
                    " SET " +
                    " inventoryQuantity=?" +
                    ", inputQuantity=?" +
                    ", importDate=?" +
                    " WHERE id_product=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, q.getProduct().getQuantity());
            statement.setInt(2, q.getProduct().getQuantity());
            statement.setTimestamp(3, q.getImportDate());
            statement.setString(4, q.getProduct().getProductID());
            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
