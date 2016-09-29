package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Food;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/29/16.
 */
public class FullFoodAdapter {

    private  int id;
    private String name;
    private double price_per_person;
    private int minimum_order;
    private List<CategoryAdapter> categories;
    private String create_date;
    private String last_modified_date;

    public FullFoodAdapter(int id, String name, double price_per_person, int minimum_order, List<CategoryAdapter> categories, String create_date, String last_modified_date) {
        this.id = id;
        this.name = name;
        this.price_per_person = price_per_person;
        this.minimum_order = minimum_order;
        this.categories = categories;
        this.create_date = create_date;
        this.last_modified_date = last_modified_date;
    }

    public FullFoodAdapter(Food f, List<CategoryAdapter> c) {
        this.id = f.getId();
        this.name = f.getName();
        this.price_per_person = f.getPrice();
        this.minimum_order = f.getMin_qty();
        this.categories = c;
        this.create_date = f.getCreate_date();
        this.last_modified_date = f.getLast_modified_date();
    }
}
