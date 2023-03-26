package bean;

import model.ImageProduct;
import model.ProductColor;
import model.ProductSize;

import java.util.List;

public class Product {
    private String productID;
    private String productName;
    private String description;
    private List<ProductSize> size;
    private List<ProductCategory> category;
    private List<ProductColor> color;

    private int price;
    private int salePrice;
    private List<ImageProduct> image;
    private int quantity;
    private int isNew;
    private int status;
    private int cost;

    private int quantityCart;

    public Product() {
    }

    public Product(String productID, String productName, String description, List<ProductSize> size, List<ProductCategory> category, List<ProductColor> color, int price, int salePrice, List<ImageProduct> image, int quantity, int isNew, int status, int cost, int quantityCart) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.size = size;
        this.category = category;
        this.color = color;
        this.price = price;
        this.salePrice = salePrice;
        this.image = image;
        this.quantity = quantity;
        this.isNew = isNew;
        this.status = status;
        this.cost = cost;
        this.quantityCart = quantityCart;
    }

    public String getProductID() {
        return productID;
    }

    public String getKey() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductSize> getSize() {
        return size;
    }

    public void setSize(List<ProductSize> size) {
        this.size = size;
    }

    public List<ProductCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ProductCategory> category) {
        this.category = category;
    }

    public List<ProductColor> getColor() {
        return color;
    }

    public void setColor(List<ProductColor> color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public List<ImageProduct> getImage() {
        return image;
    }

    public void setImage(List<ImageProduct> image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }
    public double getPriceAfterSale(){
        return price-(price*(salePrice/100.0));
    }
    @Override
    public String toString() {
        return "Product{" + "productID='" + productID + '\'' + ", productName='" + productName + '\'' + ", description='" + description + '\'' + ", size=" + size + ", category=" + category + ", color=" + color + ", price=" + price + ", salePrice=" + salePrice + ", image=" + image + ", quantity=" + quantity + ", isNew=" + isNew + ", status=" + status + ", cost=" + cost + ", quantityCart=" + quantityCart + '}';
    }
}
