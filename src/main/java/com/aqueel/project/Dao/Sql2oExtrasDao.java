package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class Sql2oExtrasDao implements ExtrasDao{

    private final Sql2o sql2o;

    public Sql2oExtrasDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int update(String name, double amt) throws DaoException {

        try(Connection con = sql2o.open()) {

            String sql = "UPDATE EXTRAS SET amount = :amount WHERE id = :id";

            int x = con.createQuery(sql)
                    .addParameter("id", 1)
                    .addParameter("amount", amt)
                    .executeUpdate()
                    .getResult();

            return x;

        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Update extras failed");
        }
    }

    @Override
    public double get(String name) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT amount FROM EXTRAS WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", 1)
                    .executeAndFetchFirst(Double.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return Extras Failed");
        }
    }
}
