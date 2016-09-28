package com.aqueel.project.Dao;

import com.aqueel.project.Models.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by aqueelmiqdad on 9/27/16.
 */
public class Sql2oCustomerDaoTest {

    private Sql2oCustomerDao dao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String conString = "jdbc:h2:mem:aTesting;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString,"","");
        dao = new Sql2oCustomerDao(sql2o);
        //Keep connection open for aTesting
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }
    @Test
    public void findAll() throws Exception {

        Customer customer = new Customer("Hello", "hello@hello.com", "321321321");
        dao.add(customer);
        assertEquals(1, dao.findAll().size());

    }

    @Test
    public void find() throws Exception {

        Customer customer = new Customer("Hello", "hello@hello.com", "321321321");
        dao.add(customer);
        Customer customer1 = dao.find(customer.getId());
        assertEquals(customer.getName(), customer1.getName());

    }

    @Test
    public void findByEmail() throws Exception {
        Customer customer = new Customer("Hello", "hello@hello.com", "321321321");
        dao.add(customer);
        Customer customer1 = dao.findByEmail(customer.getEmail());
        assertEquals(customer.getName(), customer1.getName());

    }


    @Test
    public void add() throws Exception {
        Customer customer = new Customer("Hello", "hello@hello.com", "321321321");
        dao.add(customer);
        assertEquals(1, customer.getId());
    }


    @Test
    public void findQuery() throws Exception {
        Customer customer = new Customer("Hello", "hello@hello.com", "321321321");
        dao.add(customer);
        List<Customer> customer1 = dao.find("ell");
        assertEquals(1, customer1.size());
    }

}