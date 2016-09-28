package com.aqueel.project.Adapters;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class Detail {

    private int id;
    private int count;

    public Detail(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
