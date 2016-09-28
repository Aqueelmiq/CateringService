package com.aqueel.project.Models;

import com.aqueel.project.Adapters.FullOrderAdapter;

import java.util.ArrayList;

/**
 * Created by aqueelmiqdad on 9/28/16.
 */
public class PeriodFullReport extends PeriodReport {

    ArrayList<FullOrderAdapter> orders;


    public PeriodFullReport(int id, String name) {
        super(id, name);
    }

    public PeriodFullReport(int id, String name, String start_date, String end_date) {
        super(id, name, start_date, end_date);
    }

    public ArrayList<FullOrderAdapter> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<FullOrderAdapter> orders) {
        this.orders = orders;
    }
}
