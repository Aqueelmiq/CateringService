package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Food;
import com.aqueel.project.Models.Item;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class Sql2oItemDao implements ItemDao {

    private final Sql2o sql2o;

    public Sql2oItemDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Item> find(int oid) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM ITEMS WHERE order_id = :order_id";
            return con.createQuery(sql)
                    .addParameter("order_id", oid)
                    .executeAndFetch(Item.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return Order Items Failed");
        }
    }

    @Override
    public int add(Item i) throws DaoException {
        String sql = "INSERT INTO ITEMS (food, qty, amount, order_id) VALUES (:food, :qty, :amount, :order_id)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(i)
                    .executeUpdate()
                    .getKey();
            i.setId(id);

        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Insert screwd up in Items");
        }
        return -1;
    }

    @Override
    public List<Item> findAll() throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM ITEMS";
            return con.createQuery(sql)
                    .executeAndFetch(Item.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return Order Items Failed");
        }
    }
}
