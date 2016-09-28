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

    public OrderAdapter(String delivery_date, Customer customer, String delivery_address, ArrayList<Detail> order_detail, String note) {
        this.delivery_date = delivery_date;
        this.personal_info = customer;
        this.delivery_address = delivery_address;
        this.order_detail = order_detail;
        this.note = note;
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

    public void setOrder_detail(ArrayList<Detail> order_detail) {
        this.order_detail = order_detail;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public Customer getPersonal_info() {
        return personal_info;
    }

    public void setPersonal_info(Customer personal_info) {
        this.personal_info = personal_info;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public void setNote(String note) {
        this.note = note;
    }



    @Override
    public String toString() {
        return "OrderAdapter{" +
                "delivery_date=" + delivery_date +
                ", customer=" + personal_info +
                ", delivery_address='" + delivery_address + '\'' +
                ", details=" + order_detail +
                ", note=" + note +
                '}';
    }
}
