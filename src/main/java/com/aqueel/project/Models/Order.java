package com.aqueel.project.Models;

import com.aqueel.project.Adapters.OrderAdapter;

import java.security.PolicySpi;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Order {

    private int id;
    private int customer_id;
    private String note;
    private double surcharge;
    private String status;
    private double amount;
    private String order_date;
    private String delivery_date;
    private String delivery_address;


    public Order(OrderAdapter o, double amount, double surcharge, int cid) {
        this.amount = amount;
        this.surcharge = surcharge;
        this.status = "open";
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
        this.order_date = s.format(new Date());
        delivery_date = o.getDelivery_date();
        this.note = o.getNote();
        this.customer_id = cid;
        this.delivery_address = o.getDelivery_address();
    }

    public Order(Customer customer, double amount, double surcharge, int cid, String delivery, String addr) {
        this.amount = amount;
        this.surcharge = surcharge;
        this.status = "open";
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
        this.order_date = s.format(new Date());
        delivery_date = delivery;
        this.note = "none";
        this.customer_id = customer.getId();
        this.delivery_address = addr;

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer_id=" + customer_id +
                ", note='" + note + '\'' +
                ", surcharge=" + surcharge +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", order_date='" + order_date + '\'' +
                ", delivery_date='" + delivery_date + '\'' +
                ", delivery_address='" + delivery_address + '\'' +
                '}';
    }
}
