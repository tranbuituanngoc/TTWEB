package model;

public class ProductSize {
    private int id ;
    private int id_Size;
    private String description;
    private  String id_product;


    public ProductSize(int id, int id_Size, String description) {
        this.id = id;
        this.id_Size = id_Size;
        this.description = description;
    }

    public ProductSize(int id, int id_Size, String description, String id_product) {
        this.id = id;
        this.id_Size = id_Size;
        this.description = description;
        this.id_product = id_product;
    }

    public ProductSize() {
    }

    public ProductSize(int id_Size, String description) {
        this.id_Size = id_Size;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Size() {
        return id_Size;
    }

    public void setId_Size(int id_Size) {
        this.id_Size = id_Size;
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "id=" + id +
                ", id_Size=" + id_Size +
                ", description='" + description + '\'' +
                '}';
    }
}
