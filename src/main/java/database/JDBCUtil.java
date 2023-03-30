package database;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Driver driver = new Driver();
            // register Driver (notNeed). Từ JDK 9 thì không cần đkí nữa chỉ cần tạo Driver
            DriverManager.registerDriver(driver);

            //link
            String urlString = "jdbc:mysql://localhost:3306/gachmen_shop";
            //tên database
            String usernameString = "root";
            //mật khẩu database
            String passwordString = "";

            // Tạo kết nối
            connection = DriverManager.getConnection(urlString, usernameString, passwordString);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return connection;
    }

    public static void disconection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void printInfo(Connection connection) {
        try {
            if (connection != null) {
                DatabaseMetaData metaData=connection.getMetaData();
                System.out.println(metaData.getDatabaseProductName());
                System.out.println(metaData.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
