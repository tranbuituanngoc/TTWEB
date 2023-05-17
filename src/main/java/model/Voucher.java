package model;

public class Voucher {
    private String voucherCode;
    private int discount;
    private int condition;

    public Voucher() {
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherCode='" + voucherCode + '\'' +
                ", discount=" + discount +
                ", condition=" + condition +
                '}';
    }
}
