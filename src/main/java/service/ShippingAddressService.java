package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.Order;
import model.ShippingAdress;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingAddressService {
    public static ShippingAdress getShippingAddressUser(User user){
        if (user == null) return null;
        String idUser = user.getId_User();
        PreparedStatement s = null;
        ShippingAdress result = new ShippingAdress();
        try {
            String sql = "SELECT * from shipping_address where user_id = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, idUser);
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                result.setAddress(rs.getString("address"));
                result.setEmal(rs.getString("email"));
                result.setDistrictId(rs.getInt("district_id"));
                result.setPhone(rs.getString("phone"));
                result.setFullName(rs.getString("fullname"));
                result.setProvinceId(rs.getInt("province_id"));
                result.setShippingAddressId(rs.getInt("shipping_address_id"));
                result.setWardId(rs.getInt("ward_id"));
            }
            rs.close();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
          return result;
    }

    public static ShippingAdress getShippingAddressOrder(Order order){
        String idUser = order.getUserID();
        PreparedStatement s = null;
        ShippingAdress result = new ShippingAdress();
        try {
            String sql = "SELECT * from shipping_address where user_id = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, idUser);
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                result.setAddress(rs.getString("address"));
                result.setEmal(rs.getString("email"));
                result.setDistrictId(rs.getInt("district_id"));
                result.setPhone(rs.getString("phone"));
                result.setFullName(rs.getString("fullname"));
                result.setProvinceId(rs.getInt("province_id"));
                result.setShippingAddressId(rs.getInt("shipping_address_id"));
                result.setWardId(rs.getInt("ward_id"));
            }
            rs.close();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int addShippingAddress(ShippingAdress shippingAdress){
            int res = 0;
            try {
                Connection connection = JDBCUtil.getConnection();
                String sql = "INSERT INTO shipping_address (user_id, fullname, phone, email, address, province_id, district_id, ward_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, shippingAdress.getUserId());
                statement.setString(2, shippingAdress.getFullName());
                statement.setString(3, shippingAdress.getPhone());
                statement.setString(4, shippingAdress.getEmal());
                statement.setString(5, shippingAdress.getAddress());
                statement.setInt(6, shippingAdress.getProvinceId());
                statement.setInt(7, shippingAdress.getDistrictId());
                statement.setInt(8, shippingAdress.getWardId());
                res = statement.executeUpdate();
                JDBCUtil.disconection(connection);
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return res;

    }

    public static boolean validShippingAddress(ShippingAdress shippingAdress){
        int id = shippingAdress.getShippingAddressId();
        PreparedStatement s = null;
        try {
            String sql = "SELECT * from shipping_address where shipping_address_id = ?";
            s = ConnectDB.connect(sql);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if(rs.next()){
                rs.close();
                s.close();
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
//        User user = new User();
//        user.setId_User("kh54788800");
//        ShippingAdress shippingAdress = new ShippingAdress();
//        System.out.println(shippingAdress);
//        shippingAdress =ShippingAddressService.getShippingAddressUser(user);
//        System.out.println(shippingAdress);
//        System.out.println(ShippingAddressService.validShippingAddress(shippingAdress));
//        shippingAdress.setShippingAddressId(634);
//        System.out.println(ShippingAddressService.addShippingAddress(shippingAdress));
    }
}
