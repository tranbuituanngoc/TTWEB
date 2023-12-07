package service;

import database.JDBCUtil;
import model.KeyUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
