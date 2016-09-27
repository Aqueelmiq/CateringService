package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Customer;
import com.aqueel.project.Models.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class FullOrderAdapter {

    private int id;
    private double amount;
    private double surcharge;
    private String status;
    private String note;
    private String order_date;
    private String delivery_date;
    private Customer ordered_by;
    private String delivery_address;
    private List<ItemAdapter> order_detail;

    public FullOrderAdapter(Order o, Customer c, List<ItemAdapter> order_detail) {
        this.amount = o.getAmount();
        this.surcharge = o.getSurcharge();
        this.status = o.getStatus();
        this.delivery_address = o.getDelivery_address();
        this.delivery_date = o.getDelivery_date();
        this.order_date = o.getOrder_date();
        this.note = o.getNote();
        this.ordered_by = c;
        this.order_detail = order_detail;
        this.id = o.getId();

    }
}
