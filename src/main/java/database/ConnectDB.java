package database;

import java.sql.*;

public class ConnectDB {
    static Connection con;
    public static PreparedStatement connect(String sql) throws SQLException, ClassNotFoundException {
        if (con == null || con.isClosed()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + DBProperties.getDbHost() + ":" + DBProperties.getDbPort() + "/" + DBProperties.getDbName();
            con = DriverManager.getConnection(url, DBProperties.getUsername(), DBProperties.getPassword());
            return con.prepareStatement(sql);
        } else {
            return con.prepareStatement(sql);
        }
    }

    public static void main(String[] args) {

    }
}
