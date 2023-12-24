package service;

import database.JDBCUtil;
import model.KeyUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyUserService {
    public static int insert(KeyUser o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO key_user (public_key,is_active,id_user) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, o.publicKeyToString());
            statement.setBoolean(2, o.isActive());
            statement.setString(3, o.getIdUser());
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int updateKeyUser(String idUser) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE key_user " +
                    " SET " +
                    " is_active=?" +
                    " WHERE id_user=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBoolean(1, false);
            st.setString(2, idUser);
            System.out.println(sql);
            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static String getPublicKeyWithUserID(String id) {
        String res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT public_key FROM key_user WHERE id_user=? AND is_active=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setBoolean(2, true);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getString("public_key");
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
