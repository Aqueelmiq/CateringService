package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Customer;
import com.aqueel.project.Models.Order;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class OrderAdapter {


    private String delivery_date;
    private Customer personal_info;
    private String delivery_address;
    private ArrayList<Detail> order_detail;
    private String note;

    public OrderAdapter(Order order, Customer customer, ArrayList<Detail> details) {

        this.delivery_date = order.getDelivery_date();
        this.personal_info = customer;
        this.delivery_address = order.getDelivery_address();
        this.order_detail = details;
        this.note = order.getNote();
    }

    public String getNote() {
        return note;
    }

    public ArrayList<Detail> getOrder_detail() {
        return order_detail;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public Customer getCustomer() {
        return personal_info;
    }

    public void setCustomer(Customer customer) {
        this.personal_info = customer;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

}
