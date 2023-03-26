package model;

public class ProductColor {
    private int id ;
    private int id_Color;
    private String description;

    public ProductColor(int id, int id_Color, String description) {
        this.id = id;
        this.id_Color = id_Color;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Color() {
        return id_Color;
    }

    public void setId_Color(int id_Color) {
        this.id_Color = id_Color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductColor{" +
                "id=" + id +
                ", id_Color=" + id_Color +
                ", description='" + description + '\'' +
                '}';
    }
}
