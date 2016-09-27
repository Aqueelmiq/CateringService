package com.aqueel.project.Models;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class RevenueReport extends Report {

    String start_date;
    String end_date;
    int orders_placed;
    int orders_cancelled;
    int orders_open;
    double food_revenue;
    double surcharge_revenue;

    public RevenueReport() {}

    public RevenueReport(int id, String name) {
        super(id, name);
    }

    public RevenueReport(int id, String name, String start_date, String end_date, int orders_placed, int orders_cancelled, int orders_open, double food_revenue, double surcharge_revenue) {
        super(id, name);
        this.start_date = start_date;
        this.end_date = end_date;
        this.orders_placed = orders_placed;
        this.orders_cancelled = orders_cancelled;
        this.orders_open = orders_open;
        this.food_revenue = food_revenue;
        this.surcharge_revenue = surcharge_revenue;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getOrders_placed() {
        return orders_placed;
    }

    public void setOrders_placed(int orders_placed) {
        this.orders_placed = orders_placed;
    }

    public int getOrders_cancelled() {
        return orders_cancelled;
    }

    public void setOrders_cancelled(int orders_cancelled) {
        this.orders_cancelled = orders_cancelled;
    }

    public int getOrders_open() {
        return orders_open;
    }

    public void setOrders_open(int orders_open) {
        this.orders_open = orders_open;
    }

    public double getFood_revenue() {
        return food_revenue;
    }

    public void setFood_revenue(double food_revenue) {
        this.food_revenue = food_revenue;
    }

    public double getSurcharge_revenue() {
        return surcharge_revenue;
    }

    public void setSurcharge_revenue(double surcharge_revenue) {
        this.surcharge_revenue = surcharge_revenue;
    }
}
