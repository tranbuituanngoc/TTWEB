package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
    private String orderID;
    private String userID;
    private String fullName;
    private int totalPrice;
    private int status;
    private int transport_status;
    private int shipping_cost;
    private Timestamp shipping_time;
    private Timestamp order_date;
    private int paymentMethodId;
    private String voucher_code;
    private String idTransport;
    private String eSign;

    public Order() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(int shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public Timestamp getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(Timestamp shipping_time) {
        this.shipping_time = shipping_time;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public String getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(String idTransport) {
        this.idTransport = idTransport;
    }

    public int getTransport_status() {
        return transport_status;
    }

    public void setTransport_status(int transport_status) {
        this.transport_status = transport_status;
    }

    public Timestamp getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Timestamp order_date) {
        this.order_date = order_date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName= fullName;
    }

    public String geteSign() {
        return eSign;
    }

    public void seteSign(String eSign) {
        this.eSign = eSign;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", userID='" + userID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", transport_status=" + transport_status +
                ", shipping_cost=" + shipping_cost +
                ", shipping_time=" + shipping_time +
                ", order_date=" + order_date +
                ", paymentMethodId=" + paymentMethodId +
                ", voucher_code='" + voucher_code + '\'' +
                ", idTransport='" + idTransport + '\'' +
                ", eSign='" + eSign + '\'' +
                '}';
    }
}