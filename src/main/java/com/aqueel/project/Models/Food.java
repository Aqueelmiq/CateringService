package com.aqueel.project.Models;

import com.aqueel.project.Adapters.FoodAdapter;

/**
 * Created by aqueelmiqdad on 9/23/16.
 */
public class Food {

    private int id;
    private String name;
    private double price;
    private int min_qty;
    private String create_date;
    private String last_modified_date;

    public Food(FoodAdapter adp) {
        this.name = adp.getName();
        this.price = adp.getPrice_per_person();
        this.min_qty = adp.getMinimum_order();
    }

    public Food(String name, double price, int min_qty) {
        this.name = name;
        this.price = price;
        this.min_qty = min_qty;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(String last_modified_date) {
        this.last_modified_date = last_modified_date;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMin_qty() {
        return min_qty;
    }

    public void setMin_qty(int min_qty) {
        this.min_qty = min_qty;
    }

}
