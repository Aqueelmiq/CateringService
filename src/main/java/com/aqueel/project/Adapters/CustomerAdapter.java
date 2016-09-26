package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class CustomerAdapter {

    private int id;
    private String name;
    private String email;
    private String phone;
    private ArrayList<BasicOrderAdapter> orders;

    public CustomerAdapter(Customer c, ArrayList<BasicOrderAdapter> orderAdapters) {
        this.name = c.getName();
        this.phone = c.getPhone();
        this.email = c.getEmail();
        this.id = c.getId();
        this.orders = orderAdapters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<BasicOrderAdapter> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<BasicOrderAdapter> orders) {
        this.orders = orders;
    }
}
