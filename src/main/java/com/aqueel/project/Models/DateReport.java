package com.aqueel.project.Models;

import com.aqueel.project.Adapters.FullOrderAdapter;

import java.util.ArrayList;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class DateReport extends Report {

    ArrayList<FullOrderAdapter> orders;

    public DateReport() {
    }

    public DateReport(int id, String name) {
        super(id, name);
        orders = new ArrayList<>();
    }

    public DateReport(int id, String name, ArrayList<FullOrderAdapter> o) {
        super(id, name);
        orders = o;
    }

    public ArrayList<FullOrderAdapter> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<FullOrderAdapter> orders) {
        this.orders = orders;
    }
}
