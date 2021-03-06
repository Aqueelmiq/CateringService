package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Category;
import com.aqueel.project.Models.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class FoodAdapter {

    private int id;
    private String name;
    private double price_per_person;
    private int minimum_order;
    private List<CategoryAdapter> categories;

    public FoodAdapter(Food f, List<CategoryAdapter> c) {
        this.id = f.getId();
        this.name = f.getName();
        this.price_per_person = f.getPrice();
        this.minimum_order = f.getMin_qty();
        this.categories = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice_per_person() {
        return price_per_person;
    }

    public int getMinimum_order() {
        return minimum_order;
    }

    public List<CategoryAdapter> getCategories() {
        return categories;
    }

}




