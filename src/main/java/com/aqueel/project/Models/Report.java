package com.aqueel.project.Models;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class Report {

    int id;
    String name;

    public Report(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
