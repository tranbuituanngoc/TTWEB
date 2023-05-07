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
    private String fullname;
    private boolean status;
    private String oldPass;

    public User() {
    }

    public User(String id_User, String userName, String email, String phone, String address, String pass, String verificationCode, Timestamp timeValid, boolean verified, int role, String oldPass) {
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
        this.oldPass = oldPass;
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

    public User(String id_User, String userName, String fullname, String email, String phone, String address, String pass, int role, boolean status) {
        this.id_User = id_User;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.role = role;
        this.fullname = fullname;
        this.status = status;
    }

    public User(String id_User, String userName,String fullName, String email, String phone, String address, String pass, String verificationCode, Timestamp timeValid, boolean verified) {
        this.id_User = id_User;
        this.fullname = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.verificationCode = verificationCode;
        this.timeValid = timeValid;
        this.verified = verified;
    }

    public User(String id_User, String userName, String email, String phone, String address, String pass, int role, String fullname, boolean status) {
        this.id_User = id_User;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.role = role;
        this.fullname = fullname;
        this.status = status;
    }

    public User(String userName, String pass, String email) {
        this.userName = userName;
        this.pass = pass;
        this.email = email;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public boolean isStatus() {
        return status;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
