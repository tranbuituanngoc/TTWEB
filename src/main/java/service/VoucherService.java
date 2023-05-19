package service;

import database.ConnectDB;
import model.Voucher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class VoucherService {
    public static List<Voucher> getAllVoucher(){
        List<Voucher> result = new LinkedList<>();
        try {
            PreparedStatement pState = null;
            String sql = "select * from vouchers";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherCode(rs.getString("voucher_code"));
                voucher.setCondition(rs.getInt("condition"));
                voucher.setDiscount(rs.getInt("discount"));
                result.add(voucher);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Voucher getVoucher(String vourcherCode){
        Voucher result = new Voucher();
        try {
            PreparedStatement pState = null;
            String sql = "select * from vouchers where voucher_code =?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, vourcherCode);
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherCode(rs.getString("voucher_code"));
                voucher.setCondition(rs.getInt("condition"));
                voucher.setDiscount(rs.getInt("discount"));
                result = voucher;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getAllVoucher());
        System.out.println(getVoucher("TRUEMART500K"));

    }
}
