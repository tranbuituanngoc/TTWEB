package service;

import database.JDBCUtil;
import model.Product;
import model.Wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishlistService {
    public ArrayList<String> selectByUserId(String id) {
        ArrayList<String> res = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT id_product FROM wishlist WHERE id_user=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_product = resultSet.getString("id_product");
                res.add(id_product);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static ArrayList<Product> selectPByUserId(String id) {
        ArrayList<Product> res = new ArrayList<>();
        ArrayList<String> listID = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT id_product FROM wishlist WHERE id_user=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_product = resultSet.getString("id_product");
                listID.add(id_product);
            }
            for (String id_product : listID) {
                Product product = ProductService.getByIdAd(id_product);

                res.add(product);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int insert(Wishlist o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO wishlist (id_user,id_product) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, o.getId_user());
            statement.setString(2, o.getId_product());
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int delete(String id_product) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE from wishlist " +
                    " WHERE id_product=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id_product);
            ketQua = st.executeUpdate();

            JDBCUtil.disconection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }
}
