package service;

import Util.SaltString;
import database.ConnectDB;
import database.JDBCUtil;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UserService {
    public static UserService getInstance() {
        return new UserService();
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> res = new ArrayList<User>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_user = resultSet.getString("id_user");
                String userName = resultSet.getString("username");
                String fullname = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String password = resultSet.getString("password");
                int role = resultSet.getInt("role");
                boolean status = resultSet.getBoolean("status");

                User user = new User(id_user, userName, fullname, email, phone, address, password, role, status);
                res.add(user);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public User selectById(User o) {
        User res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM users WHERE id_user=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, o.getId_User());
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_user = resultSet.getString("id_user");
                String userName = resultSet.getString("userName");
                String fullName = resultSet.getString("fullName");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String password = resultSet.getString("password");
                String verifyCode = resultSet.getString("verification_code");
                int role = resultSet.getInt("role");
                boolean status = resultSet.getBoolean("status");
                boolean verified = resultSet.getBoolean("verified");
                Timestamp timeValid = resultSet.getTimestamp("time_valid");

                res = new User(id_user, userName, fullName, email, phone, address, password, role, status);
                res.setVerified(verified);
                res.setVerificationCode(verifyCode);
                res.setTimeValid(timeValid);
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static String getNameUser(String id) {
        String res = "";
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT fullName FROM users WHERE id_user=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString(1);
                res = name;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public User selectByUnameNEmail(User o) {
        User res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND email=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, o.getUserName());
            statement.setString(2, o.getEmail());
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_user = resultSet.getString("id_user");
                String userName = resultSet.getString("userName");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String password = resultSet.getString("password");
                String verificationCode = resultSet.getString("verification_code");
                Timestamp timeValid = resultSet.getTimestamp("time_valid");
                boolean verified = resultSet.getBoolean("verified");
                int role = resultSet.getInt("role");

                res = new User(id_user, userName, email, phone, address, password, verificationCode, timeValid, verified, role);
                System.out.println(res.toString());
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public User selectByUserNameAndpassword(User o) {
        User res = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, o.getUserName());
            statement.setString(2, o.getPass());
            System.out.println(o.getUserName());
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_user = resultSet.getString("id_user");
                String userName = resultSet.getString("userName");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String password = resultSet.getString("password");
                String verificationCode = resultSet.getString("verification_code");
                Timestamp timeValid = resultSet.getTimestamp("time_valid");
                boolean verified = resultSet.getBoolean("verified");
                int role = resultSet.getInt("role");

                res = new User(id_user, userName, email, phone, address, password, verificationCode, timeValid, verified, role);
                res.setFullname(resultSet.getString("fullname"));
                System.out.println(res.toString());
                break;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static boolean existUserName(String uname) {
        PreparedStatement s = null;
        try {
            String sql = "select * FROM users where username = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, uname);
            ResultSet rs = s.executeQuery();
            if (rs.next()) return true;
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insert(User o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO users (id_user,username,fullname,email,phone,address,password,role,status) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, o.getId_User());
            statement.setString(2, o.getUserName());
            statement.setString(3, o.getFullname());
            statement.setString(4, o.getEmail());
            statement.setString(5, o.getPhone());
            statement.setString(6, o.getAddress());
            statement.setString(7, o.getPass());
            statement.setInt(8, o.getRole());
            statement.setBoolean(9, o.getStatus());
            System.out.println(sql);
            res = statement.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int update(User o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE Users " +
                    " SET " +
                    " email=?" +
                    ", phone=?" +
                    ", fullname =?" +
                    ", role=?" +
                    ", status=?" +
                    " WHERE id_user=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, o.getEmail());
            st.setString(2, o.getPhone());
            st.setString(3, o.getFullname());
            st.setInt(4, o.getRole());
            st.setBoolean(5, o.getStatus());
            st.setString(6, o.getId_User());
            System.out.println(sql);
            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int updateVerifyInfo(User o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE Users " +
                    " SET " +
                    " verification_code=?" +
                    ", time_valid=?" +
                    ", verified=?" +
                    " WHERE id_user=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, o.getVerificationCode());
            st.setTimestamp(2, o.getTimeValid());
            st.setBoolean(3, o.isVerified());
            st.setString(4, o.getId_User());
            System.out.println(sql);
            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public boolean changepassword(User o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE Users " +
                    " SET " +
                    " password=?" +
                    " WHERE id_user=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, o.getPass());
            st.setString(2, o.getId_User());
            System.out.println(sql);
            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res > 0;
    }

    public int updateAll(ArrayList<User> arrayList) {
        int temp = 0;
        for (User u : arrayList) {
            temp += this.update(u);


        }
        return temp;
    }

    public int insertAll(ArrayList<User> arrayList) {
        int temp = 0;
        for (User u : arrayList) {
            temp += this.insert(u);
        }
        return temp;
    }

    public boolean checkUserName(String s) {
        boolean res = false;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, s);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res = true;
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static void deleteUser(String idUser) {
        PreparedStatement s = null;
        try {
            String sql = "DELETE  FROM users where id_user = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, idUser);
            int rs = s.executeUpdate();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void lockUser(String idUser) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE Users set status = 0 where id_user = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, idUser);
            int rs = s.executeUpdate();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean changePass(User o) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE Users " +
                    " SET " +
                    " password=?" +
                    " WHERE id_user=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, o.getPass());
            st.setString(2, o.getId_User());
            System.out.println(sql);
            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res > 0;
    }

    public static void unlockUser(String idUser) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE Users set status = 1 where id_user = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, idUser);
            int rs = s.executeUpdate();
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean existEmail(String email) {
        PreparedStatement s = null;
        try {
            String sql = "select * FROM users where email = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, email);
            ResultSet rs = s.executeQuery();
            if (rs.next()) return true;
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean checkUsernameEmail(String userName, String email) {
        PreparedStatement preSta = null;
        try {
            String sql = "select username,email FROM users where username=? and email =?";
            preSta = ConnectDB.connect(sql);
            preSta.setString(1, userName);
            preSta.setString(2, email);
            ResultSet rs = preSta.executeQuery();
            if (rs.next()) {
                rs.getString(2);
                rs.getString(3);
                return true;
            }
            rs.close();
            preSta.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int countUser() {
        PreparedStatement pre = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM users";
            pre = ConnectDB.connect(sql);
            ResultSet rs = pre.executeQuery();
            rs.last();
            count = rs.getRow();
            return count;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public static void updatepassword(String email, String password) {
        PreparedStatement preSta = null;
        try {
            String sql = "UPDATE Users SET password=? WHERE email=? ";
            preSta = ConnectDB.connect(sql);
            preSta.setString(1, password);
            preSta.setString(2, email);
            preSta.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    //
//    public static User checkUser(String username, String password) {
//        PreparedStatement preSta = null;
//        try {
//            String sql = "select * FROM users where username = ? and password = ?";
//            preSta = ConnectDB.connect(sql);
//            preSta.setString(1, username);
//            preSta.setString(2, password);
//            ResultSet rs = preSta.executeQuery();
//            User user = null;
//            if (rs.next()) {
//                user = new User(
//                        rs.getString(1), rs.getString(2), rs.getString(3)
//                        , rs.getString(4),
//                        rs.getInt(5),
//                        rs.getString(6),
//                        rs.getString(7),
//                        rs.getInt(8),
//                        rs.getString(9)
//                );
//                return user;
//            }
//            rs.close();
//            preSta.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static int getStatus(String username) {
//        int status = 0;
//        PreparedStatement pre = null;
//        try {
//            String sql = "select status FROM users where username=? ";
//            pre = ConnectDB.connect(sql);
//            pre.setString(1, username);
//            ResultSet rs = pre.executeQuery();
//            if (rs.next()) {
//                return status = rs.getInt(1);
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return status;
//    }
//
//    public static User getUser(String username) {
//        PreparedStatement preSta = null;
//        try {
//            String sql = "select * FROM users where username = ? ";
//            preSta = ConnectDB.connect(sql);
//            preSta.setString(1, username);
//            ResultSet rs = preSta.executeQuery();
//            while (rs.next()) {
//                User user = new User(
//                        rs.getString(1), rs.getString(2), rs.getString(3)
//                        , rs.getString(4),
//                        rs.getInt(5),
//                        rs.getString(6),
//                        rs.getString(7),
//                        rs.getInt(8),
//                        rs.getString(9)
//                );
//                return user;
//            }
//            rs.close();
//            preSta.close();
//            return null;
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static int getRoleDB(String username) {
//        int res = 0;
//        PreparedStatement pre = null;
//        try {
//            String sql = "select role FROM users where username=? ";
//            pre = ConnectDB.connect(sql);
//            pre.setString(1, username);
//            ResultSet rs = pre.executeQuery();
//            if (rs.next()) {
//                return res = rs.getInt(1);
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
//

    private static ArrayList<String> getValueFromToken(String token) {
        // Phương thức này để trích xuất email từ token, tùy thuộc vào cách bạn tạo và lưu trữ token
        // Trong ví dụ này, tôi giả định token chứa email được phân tách bằng dấu gạch dưới (_)
        ArrayList<String> res = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(token, "+");
        while (tokenizer.hasMoreTokens()) {
            res.add(tokenizer.nextToken());
        }
        return res;
    }

    public static void main(String[] args) {
        UserService service = new UserService();
        String saltString = SaltString.getSaltString();
        System.out.println(getNameUser("kh02094306"));
        String s = ("kh02094306".substring(2) + "+" + saltString);
        System.out.println(getValueFromToken(s).get(0));
        for(String c: getValueFromToken(s)){
            System.out.println(c);
        }
    }
}