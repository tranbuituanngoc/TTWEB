package model;

import java.sql.Timestamp;

public class User {
    private String id_User;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private String pass;
    private String verificationCode;
    private Timestamp timeValid;
    private boolean verified;
    private int role;

    public User() {
    }

    public User(String id_User, String userName, String email, String phone, String address, String pass, String verificationCode, Timestamp timeValid, boolean verified, int role) {
        this.id_User = id_User;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.verificationCode = verificationCode;
        this.timeValid = timeValid;
        this.verified = verified;
        this.role = role;
    }

    public User(String id_User, String userName, String email, String phone, String address, String pass, int role) {
        this.id_User = id_User;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.role = role;
    }

    public User(String id_User, String userName, String email, String phone, String address, String pass, String verificationCode, Timestamp timeValid, boolean verified) {
        this.id_User = id_User;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.verificationCode = verificationCode;
        this.timeValid = timeValid;
        this.verified = verified;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Timestamp getTimeValid() {
        return timeValid;
    }

    public void setTimeValid(Timestamp timeValid) {
        this.timeValid = timeValid;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_User='" + id_User + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", pass='" + pass + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", timeValid=" + timeValid +
                ", verified=" + verified +
                ", role=" + role +
                '}';
    }
}
