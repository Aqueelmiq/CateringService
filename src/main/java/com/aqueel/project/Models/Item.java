package com.aqueel.project.Models;

import com.aqueel.project.Adapters.Detail;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Item {

    private int id;
    private int qty;
    private String food;
    private int order_id;
    private int food_id;
    private double amount;

    public Item(int qty, String food, int order_id, double amount) {
        this.qty = qty;
        this.food = food;
        this.order_id = order_id;
        this.amount = amount;
    }

    public Item(Detail d, Food f, int oid) {
        this.qty = d.getCount();
        this.amount = f.getPrice()*d.getCount();
        this.food = f.getName();
        this.order_id = oid;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", qty=" + qty +
                ", food='" + food + '\'' +
                ", order_id=" + order_id +
                ", amount=" + amount +
                '}';
    }
}
