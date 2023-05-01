package model;

public class Color {
    private int id;
    private int id_color;
    private int id_product;

    public Color() {
    }

    public Color(int id, int id_color, int id_product) {
        this.id = id;
        this.id_color = id_color;
        this.id_product = id_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_color() {
        return id_color;
    }

    public void setId_color(int id_color) {
        this.id_color = id_color;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
}
