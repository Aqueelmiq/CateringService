package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Order;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class BasicOrderAdapter {

    private int id;
    private String order_date;
    private String delivery_date;
    private double amount;
    private double surcharge;
    private String status;
    private String ordered_by;

    public BasicOrderAdapter(Order o, String c) {
        this.id = o.getId();
        this.amount = o.getAmount();
        this.delivery_date = o.getDelivery_date();
        this.order_date = o.getOrder_date();
        this.status = o.getStatus();
        this.ordered_by = c;
        this.surcharge = o.getSurcharge();
    }

    public BasicOrderAdapter(Order o) {
        this.id = o.getId();
        this.amount = o.getAmount();
        this.delivery_date = o.getDelivery_date();
        this.order_date = o.getOrder_date();
        this.status = o.getStatus();
        this.surcharge = o.getSurcharge();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOrdered_by() {
        return ordered_by;
    }

    public void setOrdered_by(String ordered_by) {
        this.ordered_by = ordered_by;
    }
}
