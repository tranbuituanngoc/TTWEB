package service;

import database.ConnectDB;
import database.JDBCUtil;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductService {
    public static List<Product> getAllProduct() {
        List<Product> listProducts;
        // List<Color> listColor;
        // List<Size> listSize;
        List<ImageProduct> listImage;
        try {
            PreparedStatement pState = null;
            String sql = "select * from products where status=?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            ResultSet rs = pState.executeQuery();
            listProducts = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                // listColor =
                // ProductImportedService.getColorProduct(rs.getString("id_product"));
                // listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                // listImage =
                // ProductImageService.getAllImageProduct(rs.getString("id_product"));
                // int price = ProductImportedService.getPrice(rs.getString("id_product"),
                // listSize.get(0).getIdSize(), listColor.get(0).getId_color());
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                //// product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                // product.setImage(listImage);
                listProducts.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listProducts;
    }

    public static List<Product> getAll() {
        List<Product> listProducts;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;

        try {
            PreparedStatement pState = null;
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from products";
            pState = connection.prepareStatement(sql);
            ResultSet rs = pState.executeQuery();
            listProducts = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listProducts.add(product);
            }
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listProducts;
    }

    public static Product getByIdAd(String idProduct) {
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        Product product = new Product();
        try {
            PreparedStatement pState = null;
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products WHERE id_product=?";
            pState = connection.prepareStatement(sql);

            pState.setString(1, idProduct);

            ResultSet rs = pState.executeQuery();
            rs.first();

            listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
            listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
            listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

            product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
            product.setProductID(rs.getString("id_product"));
            product.setProductName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setSalePrice(rs.getInt("sale"));
            product.setIsNew(rs.getInt("isNew"));
            product.setPriceList(ProductImportedService.getPriceListProduct(rs.getString("id_product")));
            product.setCostList(ProductImportedService.getCostListProduct(rs.getString("id_product")));
            product.setQuantityList(ProductImportedService.getQuantityListProduct(rs.getString("id_product")));
            product.setStatus(rs.getInt("status"));
            product.setColor(listColor);
            product.setSize(listSize);
            product.setCategory(rs.getString("id_category"));
            product.setImage(listImage);
        JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static Product getByName(String name) {
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        Product product = new Product();
        try {
            PreparedStatement pState = null;
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products WHERE name=?";
            pState = connection.prepareStatement(sql);

            pState.setString(1, name);

            ResultSet rs = pState.executeQuery();
            rs.first();

            listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
            listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
            listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

            product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
            product.setProductID(rs.getString("id_product"));
            product.setProductName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setSalePrice(rs.getInt("sale"));
            product.setIsNew(rs.getInt("isNew"));
            product.setPriceList(ProductImportedService.getPriceListProduct(rs.getString("id_product")));
            product.setCostList(ProductImportedService.getCostListProduct(rs.getString("id_product")));
            product.setQuantityList(ProductImportedService.getQuantityListProduct(rs.getString("id_product")));
            product.setStatus(rs.getInt("status"));
            product.setColor(listColor);
            product.setSize(listSize);
            product.setCategory(rs.getString("id_category"));
            product.setImage(listImage);
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static Product getProductDetail(String idProduct) {
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        Product product = new Product();
        try {
            PreparedStatement pState = null;
            String sql = "SELECT * FROM products WHERE id_product=?";
            pState = ConnectDB.connect(sql);

            pState.setString(1, idProduct);

            ResultSet rs = pState.executeQuery();
            rs.first();

            listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
            listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
            int price = ProductImportedService.getPrice(rs.getString("id_product"), listSize.get(0).getIdSize(),
                    listColor.get(0).getId_color());
            // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
            listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
            product.setProductID(rs.getString("id_product"));
            product.setProductName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setSalePrice(rs.getInt("sale"));
            product.setIsNew(rs.getInt("isNew"));
            product.setPrice(price);
            // product.setCost(rs.getInt("cost"));
            product.setStatus(rs.getInt("status"));
            product.setColor(listColor);
            product.setSize(listSize);
            product.setCategory(rs.getString("id_category"));
            product.setImage(listImage);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static List<Product> getByPage(List<Product> list, int start, int end) {
        List<Product> listP = new ArrayList<>();
        for (int i = start; i < end; i++) {
            listP.add(list.get(i));
        }
        return listP;
    }

    public static Product getById(String idProduct) {
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        Product product = new Product();
        try {
            PreparedStatement pState = null;
            String sql = "SELECT * FROM products WHERE id_product=?";
            pState = ConnectDB.connect(sql);

            pState.setString(1, idProduct);

            ResultSet rs = pState.executeQuery();
            rs.first();
            listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
            listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
            int price = ProductImportedService.getPrice(rs.getString("id_product"), listSize.get(0).getIdSize(),
                    listColor.get(0).getId_color());
            // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
            listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
            product.setProductID(rs.getString("id_product"));
            product.setProductName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setSalePrice(rs.getInt("sale"));
            product.setIsNew(rs.getInt("isNew"));
            product.setPrice(price);
            // product.setCost(rs.getInt("cost"));
            product.setStatus(rs.getInt("status"));
            product.setColor(listColor);
            product.setSize(listSize);
            product.setCategory(rs.getString("id_category"));
            product.setImage(listImage);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static List<Product> listNewProduct() {
        List<Product> listNewProduct;
        // List<Color> listColor;
        // List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products where isNew=? and status=?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            listNewProduct = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                // listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                // listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listNewProduct.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listNewProduct;

    }

    public static List<Product> getByType(int type) {
        List<Product> list;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=?";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, type);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getByType8(int type) {
        List<Product> list;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, type);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void deleteProduct(String idProduct) {
        PreparedStatement pState = null;
        String sql = "Delete from products where id_product=?";
        try {
            Connection connection = JDBCUtil.getConnection();
            pState = connection.prepareStatement(sql);
            pState.setString(1, idProduct);
            pState.executeUpdate();
            pState.close();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void hideProduct(String id) {
        PreparedStatement s = null;
        try {

            String sql = "UPDATE products set status = ? where id_product = ?";
            Connection connection = JDBCUtil.getConnection();
            s = connection.prepareStatement(sql);
            s.setInt(1, 0);
            s.setString(2, id);
            s.executeUpdate();
            s.close();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void nothideProduct(String id) {
        PreparedStatement s = null;
        try {
            String sql = "UPDATE products set status = ? where id_product = ?";
            Connection connection = JDBCUtil.getConnection();
            s = connection.prepareStatement(sql);
            s.setInt(1, 1);
            s.setString(2, id);
            s.executeUpdate();
            s.close();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean existProductName(String name) {
        PreparedStatement s = null;
        try {
            String sql = "select * from products where name = ?";
            s = ConnectDB.connect(sql);
            s.setString(1, name);
            ResultSet rs = s.executeQuery();
            if (rs.next())
                return true;
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Product> listBestSeller() {
        List<Product> listBestSeller;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "SELECT * FROM products where status =? limit 20;";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            ResultSet rs = pState.executeQuery();
            listBestSeller = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                int price = ProductImportedService.getPrice(rs.getString("id_product"), listSize.get(0).getIdSize(),
                        listColor.get(0).getId_color());
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listBestSeller.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listBestSeller;
    }

    public static List<Product> list10NewProduct() {
        List<Product> listNewProduct;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {
            PreparedStatement pState = null;
            String sql = "select * from products where isNew=? and status=? limit 10";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            listNewProduct = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
//                product.setColor(listColor);
//                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listNewProduct.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listNewProduct;

    }

    public static List<Product> list10BestSeller() {
        List<Product> listBestSeller;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {
            PreparedStatement pState = null;
            String sql = "SELECT * FROM products where status =? limit 10;";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            ResultSet rs = pState.executeQuery();
            listBestSeller = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
//                product.setColor(listColor);
//                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listBestSeller.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listBestSeller;
    }

    public static List<Product> list6NewProductByType() {
        List<Product> listNewProduct;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products where isNew=? and status=? where id_category=? limit 6";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            listNewProduct = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));

               

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
//                product.setColor(listColor);
//                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listNewProduct.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listNewProduct;

    }

    public static List<Product> listHintForYou() {
        List<Product> listHintForYou;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "SELECT * FROM products where status =? limit 10;";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            ResultSet rs = pState.executeQuery();
            listHintForYou = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

               int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));
//                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listHintForYou.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listHintForYou;
    }

    public static int updateProduct(String id, Product product) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE products SET name=? ,description=?,sale=?,isNew=?,status=?,id_category=? WHERE  id_product=?";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1, product.getProductName());
            s.setString(2, product.getDescription());
            s.setInt(3, product.getSalePrice());
            s.setInt(4, product.getIsNew());
            s.setInt(5, product.getStatus());
            s.setInt(6, Integer.parseInt(product.getCategory()));
            s.setString(7, id);
            System.out.println(sql);
            res = s.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int insert(Product product) {
        int res = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO products (id_product,name,description,sale,isNew,status,id_category) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1, product.getProductID());
            s.setString(2, product.getProductName());
            s.setString(3, product.getDescription());
            s.setInt(4, product.getSalePrice());
            s.setInt(5, product.getIsNew());
            s.setInt(6, product.getStatus());
            s.setInt(7, Integer.parseInt(product.getCategory()));
            System.out.println(sql);
            res = s.executeUpdate();
            JDBCUtil.disconection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<Product> searchByName(String txtSearch) {
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        PreparedStatement pre = null;
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?;";
        try {
            pre = ConnectDB.connect(sql);
            pre.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    public static List<Product> listProductA_Z() {
        List<Product> listProductA_Z;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products order by name asc";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            listProductA_Z = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listProductA_Z.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listProductA_Z;
    }

    public static List<Product> listProductZ_A() {
        List<Product> listProductZ_A;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products order by name desc";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            listProductZ_A = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listProductZ_A.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listProductZ_A;
    }

    public static List<Product> listPriceHighToLow() {
        List<Product> listPriceHighToLow;
        List<Color> listColor;

        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products ORDER BY price-(price*sale/100) desc";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            listPriceHighToLow = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listPriceHighToLow.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listPriceHighToLow;
    }

    public static List<Product> listPriceLowToHigh() {
        List<Product> listPriceLowToHigh;
        List<Color> listColor;
        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products ORDER BY price-(price*sale/100) asc";
            pState = ConnectDB.connect(sql);
            ResultSet rs = pState.executeQuery();
            listPriceLowToHigh = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
                listColor = ProductColorService.getColorProduct(rs.getString("id_product"));
                listSize = ProductSizeService.getSizeProduct(rs.getString("id_product"));
                // product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(rs.getInt("price"));
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                product.setColor(listColor);
                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                listPriceLowToHigh.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listPriceLowToHigh;
    }

    public static List<Product> getCategory1() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));//                
                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getCategory2() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 2);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();

//               listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));//                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getCategory3() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {
            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 3);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();

//               listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));//                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getCategory4() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 4);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();

//                 listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));//               
              product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getSeller1() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 1);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();

//               listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));
                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));//                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getSeller2() {

         List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;

        List<ImageProduct> listImage;
        try {
            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 2);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));
                //                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
                // product.setColor(listColor);
                // product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getSeller3() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 3);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));
                //                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
//                product.setColor(listColor);
//                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> getSeller4() {
        List<Product> list;
//        List<Color> listColor;
//        List<Size> listSize;
        List<ImageProduct> listImage;
        try {

            PreparedStatement pState = null;
            String sql = "select * from products" +
                    " where id_category=? and status=? and isNew = 1 limit 8";
            pState = ConnectDB.connect(sql);
            pState.setInt(1, 4);
            pState.setInt(2, 1);
            ResultSet rs = pState.executeQuery();
            list = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product();
//                listColor = ProductImportedService.getColorProduct(rs.getString("id_product"));
//                listSize = ProductImportedService.getSizeProduct(rs.getString("id_product"));
                listImage = ProductImageService.getAllImageProduct(rs.getString("id_product"));

                int price = ProductImportedService.getFirstPrice(rs.getString("id_product"));
                //                product.setThumb(ProductImageService.getThumbProduct(rs.getString("id_product")));

                product.setProductID(rs.getString("id_product"));
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSalePrice(rs.getInt("sale"));
                product.setIsNew(rs.getInt("isNew"));
                product.setPrice(price);
                // product.setCost(rs.getInt("cost"));
                product.setStatus(rs.getInt("status"));
//                product.setColor(listColor);
//                product.setSize(listSize);
                product.setCategory(rs.getString("id_category"));
                product.setImage(listImage);
                list.add(product);
            }
            pState.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main(String[] args) {
        // System.out.println(ProductService.getAllProduct());
        System.out.println(getByIdAd("sp002"));
    }

}
