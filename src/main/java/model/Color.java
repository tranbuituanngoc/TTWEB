package model;

public class Color {
    private int id_color;
    private String descrip;

    public Color() {
    }

    public Color(int id_color, String descrip) {
        this.id_color = id_color;
        this.descrip = descrip;
    }

    public int getId_color() {
        return id_color;
    }

    public void setId_color(int id_color) {
        this.id_color = id_color;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id_color=" + id_color +
                ", descrip='" + descrip + '\'' +
                '}';
    }
}
