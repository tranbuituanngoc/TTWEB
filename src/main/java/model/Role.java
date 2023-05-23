package model;

public class Role {
    private int role_id;
    private String role_name;
    private int product_permission;
    private int order_permission;
    private int role_permission;
    private int shipping_address_permission;
    private int user_permission;
    private int cart_permission;


    private String name;
    private String email;

    private String user_id;
    public Role() {
    }

    public  int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getProduct_permission() {
        return product_permission;
    }

    public void setProduct_permission(int product_permission) {
        this.product_permission = product_permission;
    }

    public int getOrder_permission() {
        return order_permission;
    }

    public void setOrder_permission(int order_permission) {
        this.order_permission = order_permission;
    }

    public int getRole_permission() {
        return role_permission;
    }

    public void setRole_permission(int role_permission) {
        this.role_permission = role_permission;
    }

    public int getShipping_address_permission() {
        return shipping_address_permission;
    }

    public void setShipping_address_permission(int shipping_address_permission) {
        this.shipping_address_permission = shipping_address_permission;
    }

    public int getUser_permission() {
        return user_permission;
    }

    public void setUser_permission(int user_permission) {
        this.user_permission = user_permission;
    }

    public int getCart_permission() {
        return cart_permission;
    }

    public void setCart_permission(int cart_permission) {
        this.cart_permission = cart_permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                ", product_permission=" + product_permission +
                ", order_permission=" + order_permission +
                ", role_permission=" + role_permission +
                ", shipping_address_permission=" + shipping_address_permission +
                ", user_permission=" + user_permission +
                ", cart_permission=" + cart_permission +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}' + "\n";
    }
}
