package com.aqueel.project.Models;

import com.aqueel.project.Adapters.FullOrderAdapter;
import com.aqueel.project.Adapters.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class DateReport extends Report {

    ArrayList<FullOrderAdapter> orderAdapters;

    public DateReport(int id, String name) {
        super(id, name);
        orderAdapters = new ArrayList<>();
    }

    public DateReport(int id, String name, ArrayList<FullOrderAdapter> o) {
        super(id, name);
        orderAdapters = o;
    }

    public ArrayList<FullOrderAdapter> getOrderAdapters() {
        return orderAdapters;
    }

    public void setOrderAdapters(ArrayList<FullOrderAdapter> orderAdapters) {
        this.orderAdapters = orderAdapters;
    }
}
