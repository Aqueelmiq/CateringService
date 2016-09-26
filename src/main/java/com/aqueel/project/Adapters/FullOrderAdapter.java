package com.aqueel.project.Adapters;

import com.aqueel.project.Models.Customer;

import java.util.Date;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class FullOrderAdapter {

    private int id;
    private double amount;
    private double surcharge;
    private String status;
    private String note;
    private Date order_date;
    private Date delivery_date;
    private Customer ordered_by;
    private String delivery_address;
    private ItemAdapter order_detail;
}
