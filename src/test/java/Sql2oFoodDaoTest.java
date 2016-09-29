import com.aqueel.project.Dao.Sql2oFoodDao;
import com.aqueel.project.Models.Food;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by aqueelmiqdad on 9/27/16.
 */
public class Sql2oFoodDaoTest {

    private Sql2oFoodDao dao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String conString = "jdbc:h2:mem:aTesting;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString,"","");
        dao = new Sql2oFoodDao(sql2o);
        //Keep connection open for aTesting
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void findAll() throws Exception {
        Food food = new Food("Hi", 2.4, 45);
        dao.add(food);
        assertEquals(1, dao.findAll().size());

    }

    @Test
    public void findById() throws Exception {
        Food food = new Food("Hi", 2.4, 45);
        dao.add(food);
        Food food1 = dao.findById(food.getId());
        assertEquals(food.getName(), food1.getName());

    }

    @Test
    public void add() throws Exception {
        Food food = new Food("Hi", 2.4, 45);
        dao.add(food);
        assertEquals(1, food.getId());
    }

    @Test
    public void update() throws Exception {
        Food food = new Food("Hi", 2.4, 45);
        dao.add(food);
        dao.update(food.getId(), 2.5);
        assertEquals(2.5, dao.findById(food.getId()).getPrice(), 0.001);

    }

}