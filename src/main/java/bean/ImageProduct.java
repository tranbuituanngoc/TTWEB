package bean;


public class ImageProduct {
    private int id;
    private String image;

    public ImageProduct(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageProduct{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
