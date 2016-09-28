package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Customer;
import com.aqueel.project.Models.Item;
import com.aqueel.project.Models.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by aqueelmiqdad on 9/27/16.
 */
public class Sql2oOrderDaoTest {

    private Connection con;
    private OrderDao oDao;

    @Before
    public void setUp() throws Exception {
        String conString = "jdbc:h2:mem:aTesting;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString,"","");
        oDao = new Sql2oOrderDao(sql2o);
        //Keep connection open for aTesting
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void findAll() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findAll().size());

    }

    @Test
    public void add() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, order.getId());

    }

    @Test
    public void find() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.find(order.getId()).getId());

    }

    @Test
    public void findByDate() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findByDate("20160929").size());

    }

    @Test
    public void findBetween() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findBetween("20160929", "20160929").size());

    }

    @Test
    public void findBetweenOneOption() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findBetween("20160929", null).size());

    }

    @Test
    public void findBetweenZeroOption() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findBetween(null, null).size());

    }

    @Test
    public void findBetweenLastOption() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findBetween(null, "20160929").size());

    }

    @Test
    public void findByCustomer() throws Exception {

        Customer c = sampleCustomer();
        c.setId(1);
        Order order = sampleOrder(c);
        oDao.add(order);
        assertEquals(1, oDao.findByCustomer(1).size());

    }

    @Test
    public void update() throws Exception {

        Order order = sampleOrder(sampleCustomer());
        oDao.add(order);
        oDao.update(order.getId(), 45);
        assertEquals(oDao.find(order.getId()).getCustomer_id(), 45, 0.0001);

    }

    @Test
    public void deliver() throws Exception {

        Order order = sampleOrder(sampleCustomer());
        oDao.add(order);
        oDao.deliver(order.getId());
        assertEquals(oDao.find(order.getId()).getStatus(), "delivered");

    }

    @Test
    public void cancel() throws Exception {

        Order order = sampleOrder(sampleCustomer());
        oDao.add(order);
        oDao.cancel(order.getId());
        assertEquals(oDao.find(order.getId()).getStatus(), "cancelled");

    }

    private Order sampleOrder(Customer cust) {
        return new Order(cust, 2, 3, 4, "20160929", "Helo St 2140");
    }

    private Item sampleItem() {
        return new Item(1, "Hello", 1, 23);
    }

    private Customer sampleCustomer() {
        return new Customer("Jordan", "3215676780", "mike.jordan@gmail.com");
    }



}