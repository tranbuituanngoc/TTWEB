package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CartUser {
    private int id_cart;
    private String idUser;
    private Map<Cart, Integer> cartMap = new HashMap<Cart, Integer>();
    public CartUser() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getId_cart() {
        return id_cart;
    }
    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }


    public Map<Cart, Integer> getCartMap() {
        return cartMap;
    }

    @Override
    public String toString() {
        return "CartUser{" +
                "id_cart=" + id_cart +
                ", idUser='" + idUser + '\'' +
                ", cartMap=" + cartMap +
                '}';
    }

    public void setCartMap(Map<Cart, Integer> cartMap) {
        this.cartMap = cartMap;
    }
    // thêm vào giỏ hàng
    public void addCart(Cart cart, int quantity){
        Cart cartAdd = getSameCart(cart);
        if (cartMap.containsKey(cartAdd)) {
            int currentQuantity = cartMap.get(cartAdd);
            cartMap.put(cartAdd, currentQuantity + quantity);
        } else {
            cartMap.put(cart, quantity);
        }
    }

      // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(Cart cart) {
        Cart cartAdd = getSameCart(cart);
        cartMap.remove(cartAdd);
    }
    // Cập nhật số lượng của sản phẩm trong giỏ hàng
    public void updateQuantity(Cart cart, int quantity) {
        Cart cartAdd = getSameCart(cart);
        if (cartMap.containsKey(cartAdd)) {
            cartMap.put(cartAdd, quantity);
        }
    }
//    tính tổng số lượng sản phẩm của giỏ hàng
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int quantity : cartMap.values()) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }
     // Lấy tổng giá trị của giỏ hàng
    public double getTotalValue() {
        double totalValue = 0;
        for (Map.Entry<Cart, Integer> entry : cartMap.entrySet()) {
            Product product = entry.getKey().getProduct();
            int quantity = entry.getValue();
            totalValue += product.getPriceAfterSale() * quantity;
        }
        return totalValue;
    }

    public int size(){
        return cartMap.size();
    }
    public List<Cart> getCart(){
        List<Cart> result = new LinkedList<>();
        for (Cart cart: cartMap.keySet()){
            result.add(cart);
        }
        return result;
    }
    public List<Integer> getValue(){
        List<Integer> result = new LinkedList<>();
        for (Integer i: cartMap.values()){
            result.add(i);
        }
        return result;
    }
    public Cart getSameCart(Cart cart){
        for (Cart cartItem: cartMap.keySet()){
            if (cartItem.getColor().getId_color() == cart.getColor().getId_color()
                    && cartItem.getSize().getIdSize() == cart.getSize().getIdSize() &&
                    cartItem.getProduct().getProductID().equals(cart.getProduct().getProductID()))
                return cartItem;
        }
        return null;
    }

    public int quantity(Cart key){
        return cartMap.get(key);
    }
    public void printCart() {
        System.out.println("Thông tin giỏ hàng:");
        for (Map.Entry<Cart, Integer> entry : cartMap.entrySet()) {
            Cart cart = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("Sản phẩm: " + cart.getProduct().getProductName() +
                    ", Kích thước: " + cart.getSize().getIdSize() +
                    ", Màu sắc: " + cart.getColor().getId_color() +
                    ", Giá: " + cart.getProduct().getPrice() +
                    ", Số lượng: " + quantity);
        }
        System.out.println("Tổng số lượng: " + getTotalQuantity());
        System.out.println("Tổng giá trị: " + getTotalValue());
    }


    public static void main(String[] args) {
//        HashMap<Cart, Integer> cartMap = new HashMap<Cart, Integer>();
//        Cart cart = new Cart(); // Tạo đối tượng Cart
//        Integer quantity = 1; // Số lượng của Cart
//        Cart cart2 = new Cart();
//        cartMap.put(cart, quantity); // Thêm Cart và quantity vào cartMap
//        CartUser cartUser = new CartUser();
//        cartUser.setCartMap(cartMap);
//        cartUser.addCart(cart2,2);
//        System.out.println(cartUser);
//        System.out.println(cartUser.size());
//        System.out.println(cartUser.getCart());
//        System.out.println(cartUser.quantity(cartUser.getCart().get(0)));
    }
}
