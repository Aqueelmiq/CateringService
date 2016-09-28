package com.aqueel.project.Models;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class RevenueReport extends PeriodReport {

    int orders_placed;
    int orders_cancelled;
    int orders_open;
    double food_revenue;
    double surcharge_revenue;

    public RevenueReport(int id, String name) {
        super(id, name);
    }

    public void setOrders_placed(int orders_placed) {
        this.orders_placed = orders_placed;
    }

    public void setOrders_cancelled(int orders_cancelled) {
        this.orders_cancelled = orders_cancelled;
    }

    public void setOrders_open(int orders_open) {
        this.orders_open = orders_open;
    }

    public void setFood_revenue(double food_revenue) {
        this.food_revenue = food_revenue;
    }

    public void setSurcharge_revenue(double surcharge_revenue) {
        this.surcharge_revenue = surcharge_revenue;
    }
}
