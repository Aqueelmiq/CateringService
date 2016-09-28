package com.aqueel.project.Models;

import com.aqueel.project.Adapters.FullOrderAdapter;

import java.util.ArrayList;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class DateReport extends Report {

    ArrayList<FullOrderAdapter> orders;

    public DateReport(int id, String name) {
        super(id, name);
        orders = new ArrayList<>();
    }

    public void setOrders(ArrayList<FullOrderAdapter> orders) {
        this.orders = orders;
    }
}
