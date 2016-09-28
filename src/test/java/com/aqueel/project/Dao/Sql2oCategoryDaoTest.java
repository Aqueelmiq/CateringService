package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Category;
import com.aqueel.project.Models.Food;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by aqueelmiqdad on 9/26/16.
 */
public class Sql2oCategoryDaoTest {

    private Sql2oCategoryDao dao;
    private Sql2oFoodDao Fdao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String conString = "jdbc:h2:mem:aTesting;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString,"","");
        dao = new Sql2oCategoryDao(sql2o);
        Fdao = new Sql2oFoodDao(sql2o);
        //Keep connection open for aTesting
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void find() throws Exception {
        Food f = new Food("Hi", 2.4, 5);
        Fdao.add(f);
        Category category = new Category("Test Category", 1);
        dao.add(category);
        Category category1 = dao.find(category.getId()).get(0);
        assertEquals(category.getName(), category1.getName());

    }

    @Test
    public void add() throws Exception {
        Food f = new Food("Hi", 2.4, 5);
        Fdao.add(f);
        Category category = new Category("Test Category", 1);
        dao.add(category);
        assertEquals(1, category.getId());
    }

    @Test(expected= DaoException.class)
    public void addNotWorks() throws Exception {

        Category category = new Category("Test Category", 1);
        dao.add(category);
        assertNull(dao.find(1));
    }

}