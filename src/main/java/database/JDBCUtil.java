package database;

import com.mysql.jdbc.Driver;
import model.Product;
import service.ProductImageService;
import service.ProductImportedService;

import java.sql.*;
import java.util.LinkedList;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:mysql://" + DBProperties.getDbHost() + ":" + DBProperties.getDbPort() + "/" + DBProperties.getDbName()
                    +"?useSSL=false"
                    ;
            // Tạo kết nối
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, DBProperties.getUsername(), DBProperties.getPassword());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
                System.out.println(metaData.getUserName());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static void handleSQLException(SQLException e) {
        // Log or handle the exception appropriately
        e.printStackTrace();
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Test getConnection
            connection = getConnection();
            System.out.println("Connected to the database.");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                Product product = new Product();
                product.setThumb(ProductImageService.getThumbProduct(resultSet.getString("id_product")));
                product.setProductID(resultSet.getString("id_product"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setSalePrice(resultSet.getInt("sale"));
                product.setIsNew(resultSet.getInt("isNew"));
                product.setStatus(resultSet.getInt("status"));
                System.out.println(product);
            }
            JDBCUtil.disconection(connection);
            // Test printInfo
            printInfo(connection);

            // Test disconnection
            disconection(connection);
            System.out.println("Disconnected from the database.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Ensure connection is closed in case of any exceptions
            disconection(connection);
        }
    }
}
