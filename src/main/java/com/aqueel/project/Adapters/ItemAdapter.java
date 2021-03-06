package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Item;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class ItemAdapter {

    private int id;
    private String name;
    private int count;

    public ItemAdapter(Item item, int id) {
        this.id = item.getFood_id();
        this.count = item.getQty();
        this.name = item.getFood();
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


}
