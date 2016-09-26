package com.aqueel.project.Models;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Category {

    private int id;
    private String name;
    private int food_id;

    public Category(String name, int food_id) {
        this.name = name;
        this.food_id = food_id;
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

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", food_id=" + food_id +
                '}';
    }
}
