package model;

public class ProductSize {
    private int id_Size;
    private int id_product;
    public ProductSize() {
    }

    public ProductSize(int id_Size, int id_product) {
        this.id_Size = id_Size;
        this.id_product = id_product;
    }

    public int getId_Size() {
        return id_Size;
    }

    public void setId_Size(int id_Size) {
        this.id_Size = id_Size;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
}
