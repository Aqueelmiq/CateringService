package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Food;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/23/16.
 */

public class Sql2oFoodDao implements FoodDao {

    private final Sql2o sql2o;

    public Sql2oFoodDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Food> findAll() throws DaoException {

        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM MENU";
            return con.createQuery(sql)
                    .executeAndFetch(Food.class);
        }

    }

    @Override
    public Food findById(int id) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM MENU WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Food.class);
        }
    }

    @Override
    public int add(Food f) throws DaoException {

        String sql = "INSERT INTO MENU (name, price, min_qty, create_date, last_modified_date) VALUES (:name, :price, :min_qty, :create_date, :last_modified_date)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(f)
                    .executeUpdate()
                    .getKey();
            f.setId(id);

        }
        return -1;
    }

    @Override
    public int update(int fid, double price) throws DaoException {

        try (Connection con = sql2o.open()) {

            String sql = "UPDATE MENU SET price = :price WHERE id = :id";
            String sql2 = "UPDATE MENU SET last_modified_date = :date";
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

            con.createQuery(sql2).
                    addParameter("date", df.format(new Date()))
                    .executeUpdate();

            return con.createQuery(sql)
                    .addParameter("id", fid)
                    .addParameter("price", price)
                    .executeUpdate()
                    .getResult();
        }

    }

}
