package service;

import database.JDBCUtil;

import java.sql.*;

public class ProductImportedService {
    public static int getQuantityDetail(String idProduct, int idSize, int idColor) {
        int res =0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT productimported FROM productdetails WHERE id_product=? AND id_size=?  AND id_color=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2,idSize);
            statement.setInt(3,idColor);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = resultSet.getInt("inventoryQuantity");

                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int insert(String idProduct, int idSize, int idCate, int idColor, int quantity, Timestamp importDate) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO productimported (id_product,id_size,id_color,inventoryQuantity,inputQuantity,importDate) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idProduct);
            statement.setInt(2, idSize);
            statement.setInt(3, idColor);
            statement.setInt(4, quantity);
            statement.setInt(5, quantity);
            statement.setTimestamp(6,importDate);
            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
