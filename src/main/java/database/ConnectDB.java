package database;

import java.sql.*;

public class ConnectDB {
    private static Connection con;

    public static PreparedStatement connect(String sql) throws SQLException, ClassNotFoundException {
        if (con == null || con.isClosed()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + DBProperties.getDbHost() + ":" + DBProperties.getDbPort() + "/" + DBProperties.getDbName()
                    + "?useSSL=false"
                    ;
            con = DriverManager.getConnection(url, DBProperties.getUsername(), DBProperties.getPassword());
        }
        return con.prepareStatement(sql);
    }

    // Close the PreparedStatement and Connection
    public static void closeResources(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Log or handle the exception
        }
    }

    public static void main(String[] args) {

    }
}
