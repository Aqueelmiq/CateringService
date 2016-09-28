package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Category;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Sql2oCategoryDao implements CategoryDao {


    private final Sql2o sql2o;

    public Sql2oCategoryDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Category> find(int food_id) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM CATEGORIES WHERE food_id = :food_id";
            return con.createQuery(sql)
                    .addParameter("food_id", food_id)
                    .executeAndFetch(Category.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return Category Failed");
        }
    }



    @Override
    public int add(Category c) throws DaoException {
        String sql = "INSERT INTO CATEGORIES (name, food_id) VALUES (:name, :food_id)";

        try(Connection con = sql2o.open()) {

            int id = (int) con.createQuery(sql)
                    .bind(c)
                    .executeUpdate()
                    .getKey();

            c.setId(id);
            return 1;

        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Insert screwd up in Categories");
        }
    }

}
