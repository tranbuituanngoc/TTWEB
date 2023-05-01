package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Quantity {
    private Product product;
    private Timestamp importDate;

    public Quantity() {
    }

    public Quantity(Product product, Timestamp importDate) {
        this.product = product;
        this.importDate = importDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }
}
