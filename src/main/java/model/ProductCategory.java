package model;

public class ProductCategory {
    private int id;
    private int id_Category;
    private String description;

    public ProductCategory(int id, int id_Category, String description) {
        this.id = id;
        this.id_Category = id_Category;
        this.description = description;
    }

    public ProductCategory() {
    }

    public ProductCategory(int id_Category, String description) {
        this.id_Category = id_Category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Category() {
        return id_Category;
    }

    public void setId_Category(int id_Category) {
        this.id_Category = id_Category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", id_Category=" + id_Category +
                ", description='" + description + '\'' +
                '}';
    }

    public static void main(String[] args) {

    }
}
