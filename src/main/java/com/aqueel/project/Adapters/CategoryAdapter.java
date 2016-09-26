package com.aqueel.project.Adapters;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class CategoryAdapter {
    private String name;

    public CategoryAdapter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryAdapter{" +
                "name='" + name + '\'' +
                '}';
    }
}
