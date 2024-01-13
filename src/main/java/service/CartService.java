package service;

import model.*;
import database.ConnectDB;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class CartService {
    public static List<CartUser> getAllCart() {
        List<CartUser> result = new LinkedList<>();
        try {
            PreparedStatement pState = null;
            String sql = "select * from cart";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                Product product = ProductService.getById(rs.getString("id_product"));
//                Size size = ProductService.getById()
//                cart.set
//                colorProduct.add(color);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static void updateCart(CartUser cartUser) {

    }

    public static CartUser getCartById(String idUser) {
        CartUser cartUser = new CartUser();
        try {
            PreparedStatement pState = null;
            String sql = "select * from cart where id_user =? and id_order is null";
            pState = ConnectDB.connect(sql);
            pState.setString(1, idUser);
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                int id_size = rs.getInt("id_size");
                int id_color = rs.getInt("id_color");
                String id_user = rs.getString("id_user");
                String id_product = rs.getString("id_product");
                int id_cart = rs.getInt("id");
                Product product = ProductService.getById(id_product);
                Color color = ProductColorService.getColorById(id_color);
                Size size = ProductSizeService.getSizeById(id_size);
                Cart cart = new Cart();
                cart.setSize(size);
                cart.setProduct(product);
                cart.setColor(color);
                cart.setIdCart(id_cart);
                cartUser.setId_cart(rs.getInt("id"));
//                cartUser.setIdUser(idUser);
                cartUser.addCart(cart, rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cartUser;
    }

    public static CartUser getCartByIdOrder(String idOrder) {
        CartUser cartUser = new CartUser();
        try {
            PreparedStatement pState = null;
            String sql = "select * from cart where id_order = ?";
            pState = ConnectDB.connect(sql);
            pState.setString(1, idOrder);
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                int id_size = rs.getInt("id_size");
                int id_color = rs.getInt("id_color");
                String id_user = rs.getString("id_user");
                String id_product = rs.getString("id_product");
                Product product = ProductService.getById(id_product);
                Color color = ProductColorService.getColorById(id_color);
                Size size = ProductSizeService.getSizeById(id_size);
                Cart cart = new Cart();
                cart.setSize(size);
                cart.setProduct(product);
                cart.setColor(color);
                cartUser.setId_cart(rs.getInt("id"));
                cartUser.setIdUser(id_user);
                cartUser.addCart(cart, rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cartUser;
    }

    public static void addCart(CartUser cartUser, Cart cart, int quantity) {
//        CartService.deleteCart(cartUser.getIdUser());
            System.out.println(quantity);
            try {
                Product product = cart.getProduct();
                Color color = cart.getColor();
                Size size = cart.getSize();
                PreparedStatement pState = null;
                String sql = "insert into cart(id_user, id_product, id_size, id_color, quantity) values(?, ?, ?, ?, ?)";
                pState = ConnectDB.connect(sql);
                pState.setString(1, cartUser.getIdUser());
                pState.setString(2, product.getProductID());
                pState.setInt(3, size.getIdSize());
                pState.setInt(4, color.getId_color());
                pState.setInt(5, quantity);
                pState.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

    }

    public static void updateQuantityCart(int quantity, int idCart) {
        try {
            PreparedStatement pState = null;
            String sql = "Update cart set quantity =?  WHERE id = ?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, quantity);
            pState.setInt(2, idCart);
            pState.executeUpdate();
            System.out.println("success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateQuantityProductInCart(int quantity, String idOrder, String idProduct, int idSize, int idColor) {
        try {
            PreparedStatement pState = null;
            String sql = "Update cart set quantity =?  WHERE id_order= ? AND id_product=? AND id_size=? AND id_color=?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, quantity);
            pState.setString(2, idOrder);
            pState.setString(3, idProduct);
            pState.setInt(4, idSize);
            pState.setInt(5, idColor);
            int affectedRows = pState.executeUpdate();
            System.out.println("Row Affected: " + affectedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void deleteCart(int idCart){
        try {
                PreparedStatement pState = null;
                String sql = "DELETE FROM cart WHERE id = ?";
                pState = ConnectDB.connect(sql);
                pState.setInt(1, idCart);
                pState.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    public static void setCartOrder(String id_user, String id_order){
        try {
                PreparedStatement pState = null;
                String sql = "Update cart set id_order =?  WHERE id_user = ? AND id_order IS NULL";
                pState = ConnectDB.connect(sql);
                pState.setString(1, id_order);
                pState.setString(2, id_user);
                pState.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    public static void removeCartOrder(String id_order){
        try {
                PreparedStatement pState = null;
                String sql = "Update cart set id_order =null WHERE id_order = ?";
                pState = ConnectDB.connect(sql);
                pState.setString(1, id_order);
                pState.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    public static void main(String[] args) {
//        System.out.println(CartService.getCartById("kh01125729").getIdUser() == null);
    }
}
