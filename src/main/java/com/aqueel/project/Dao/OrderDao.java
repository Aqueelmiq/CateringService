package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Order;

import java.util.Date;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public interface OrderDao {

    List<Order> findAll() throws DaoException;
    int add(Order o) throws DaoException;
    Order find(int oid) throws DaoException;
    List<Order> findByDate(String date) throws DaoException;
    List<Order> findBetween(String start, String end) throws DaoException;
    List<Order> findByCustomer(int customer_id) throws DaoException;

    int update(int oid, int cid) throws DaoException;
    int update(int oid, double price) throws DaoException;
    int deliver(int oid) throws DaoException;
    int cancel(int oid) throws DaoException;

}
