package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Order;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class Sql2oOrderDao implements OrderDao{

    private final Sql2o sql2o;

    public Sql2oOrderDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM ORDERS";
            return con.createQuery(sql)
                    .executeAndFetch(Order.class);
        }

    }

    @Override
    public int add(Order o) throws DaoException {

        String sql = "INSERT INTO ORDERS (customer_id, note, surcharge, status, order_date, delivery_date, delivery_address, amount) VALUES (:customer_id, :note, :surcharge, :status, :order_date, :delivery_date, :delivery_address, :amount)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(o)
                    .executeUpdate()
                    .getKey();
            o.setId(id);

        }
        return 0;
    }

    @Override
    public Order find(int oid) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM ORDERS WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", oid)
                    .executeAndFetchFirst(Order.class);
        }
    }

    @Override
    public List<Order> findByDate(String date) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM ORDERS WHERE delivery_date = :date AND status = 'open'";
            return con.createQuery(sql)
                    .addParameter("date", date)
                    .executeAndFetch(Order.class);
        }
    }

    @Override
    public List<Order> findBetween(String start, String end) throws DaoException {

        String sql = "SELECT * FROM ORDERS WHERE delivery_date >= :start AND delivery_date <= :end";
        if(start == null && end == null) {
            return findAll();
        }

        try(Connection con = sql2o.open()) {


            if(end == null) {
                sql = "SELECT * FROM ORDERS WHERE delivery_date >= :start";
                return con.createQuery(sql)
                        .addParameter("start", start)
                        .executeAndFetch(Order.class);
            }
            else if(start == null) {
                sql = "SELECT * FROM ORDERS WHERE delivery_date <= :end";
                return con.createQuery(sql)
                        .addParameter("end", end)
                        .executeAndFetch(Order.class);

            }
            else {
                return con.createQuery(sql)
                        .addParameter("start", start)
                        .addParameter("end", end)
                        .executeAndFetch(Order.class);

            }


        }
    }

    @Override
    public List<Order> findByCustomer(int customer_id) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM ORDERS WHERE customer_id = :id";
            return con.createQuery(sql)
                    .addParameter("id", customer_id)
                    .executeAndFetch(Order.class);
        }
    }

    @Override
    public int update(int oid, int cid) throws DaoException {
        try (Connection con = sql2o.open()) {

            String sql = "UPDATE ORDERS SET customer_id = :cid WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", oid)
                    .addParameter("cid", cid)
                    .executeUpdate()
                    .getResult();
        }
    }

    @Override
    public int update(int oid, double price) throws DaoException {
        try (Connection con = sql2o.open()) {

            String sql = "UPDATE ORDERS SET amount = :amount WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", oid)
                    .addParameter("amount", price)
                    .executeUpdate()
                    .getResult();
        }
    }

    @Override
    public int deliver(int oid) throws DaoException {
        try (Connection con = sql2o.open()) {

            String sql = "UPDATE ORDERS SET status = :status WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", oid)
                    .addParameter("status", "delivered")
                    .executeUpdate()
                    .getResult();
        }
    }

    @Override
    public int cancel(int oid) throws DaoException {
        try (Connection con = sql2o.open()) {

            String sql = "UPDATE ORDERS SET status = :status WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", oid)
                    .addParameter("status", "cancelled")
                    .executeUpdate()
                    .getResult();
        }
    }

}
