package model;

public class Size {
    private int idSize;
    private int height;
    private int length;
    private int width;
    private int weight;
    private String descrip;

    public Size() {
    }

    public Size(int idSize, int height, int length, int width, int weight, String descrip) {
        this.idSize = idSize;
        this.height = height;
        this.length = length;
        this.width = width;
        this.weight = weight;
        this.descrip = descrip;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Size{" +
                "idSize=" + idSize +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", weight=" + weight +
                '}';
    }
}
