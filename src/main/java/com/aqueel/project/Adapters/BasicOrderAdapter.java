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

}
