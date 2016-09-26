package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Customer;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public interface CustomerDao {

    List<Customer> findAll() throws DaoException;
    List<Customer> find(String query) throws DaoException;
    Customer find(int cid) throws DaoException;
    int add(Customer c) throws DaoException;

}
