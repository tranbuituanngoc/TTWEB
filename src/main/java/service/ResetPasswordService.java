package service;

import database.JDBCUtil;
import model.User;
import org.apache.commons.math3.util.Pair;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ResetPasswordService {

    public static int insert(String id_user, String token, Timestamp expiry_date) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO password_reset_tokens (id_user,token, expiry_date) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,id_user);
            statement.setString(2,token);
            statement.setTimestamp(3, expiry_date);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static Pair<String,Timestamp> selectByUserID(String id) {
        Pair<String,Timestamp> res =null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT token,expiry_date FROM password_reset_tokens WHERE id_user=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String token= resultSet.getString(1);
                Timestamp expiry_date= resultSet.getTimestamp(2);

                res= new Pair<>(token,expiry_date);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int delete(String token) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "DELETE FROM password_reset_tokens WHERE token=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,token);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
