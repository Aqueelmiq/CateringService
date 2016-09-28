package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Customer;
import com.aqueel.project.Models.Food;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public class Sql2oCustomerDao implements CustomerDao {

    private final Sql2o sql2o;

    public Sql2oCustomerDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Customer> findAll() throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM CUSTOMERS";
            return con.createQuery(sql)
                    .executeAndFetch(Customer.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return All customers Failed");
        }
    }

    @Override
    public List<Customer> find(String query) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM CUSTOMERS WHERE name LIKE :name";
            return con.createQuery(sql)
                    .addParameter("name", "%"+query+"%")
                    .executeAndFetch(Customer.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return All Food Failed");
        }
    }

    @Override
    public Customer findByEmail(String email) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM CUSTOMERS WHERE email = :email";
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Customer.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return All Food Failed");
        }
    }

    @Override
    public Customer find(int cid) throws DaoException {
        try(Connection con = sql2o.open()) {

            String sql = "SELECT * FROM CUSTOMERS WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", cid)
                    .executeAndFetchFirst(Customer.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Return All Food Failed");
        }
    }

    @Override
    public int add(Customer c) throws DaoException {
        String sql = "INSERT INTO CUSTOMERS (name, email, phone) VALUES (:name, :email, :phone)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(c)
                    .executeUpdate()
                    .getKey();
            c.setId(id);

        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Insert screwd up in Food");
        }
        return -1;
    }
}
