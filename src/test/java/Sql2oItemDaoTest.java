import com.aqueel.project.Dao.OrderDao;
import com.aqueel.project.Dao.Sql2oItemDao;
import com.aqueel.project.Dao.Sql2oOrderDao;
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
public class Sql2oItemDaoTest {

    private Sql2oItemDao dao;
    private Connection con;
    private OrderDao oDao;

    @Before
    public void setUp() throws Exception {
        String conString = "jdbc:h2:mem:aTesting;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString,"","");
        dao = new Sql2oItemDao(sql2o);
        oDao = new Sql2oOrderDao(sql2o);
        //Keep connection open for aTesting
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void find() throws Exception {

        oDao.add(sampleOrder(sampleCustomer()));
        Item item = sampleItem();
        dao.add(item);
        assertEquals(item.getId(), dao.find(item.getOrder_id()).get(0).getId());

    }

    @Test
    public void findEmpty() throws Exception {
        assertEquals(0, dao.find(1).size());
    }

    @Test
    public void add() throws Exception {
        oDao.add(sampleOrder(sampleCustomer()));
        Item item = sampleItem();
        dao.add(item);
        assertEquals(1, item.getId());
    }

    @Test(expected = DaoException.class)
    public void addNotWorks() throws Exception {
        oDao.add(sampleOrder(sampleCustomer()));
        Item item = sampleItem();
        item.setOrder_id(400);
        dao.add(item);
        assertNotEquals(1, item.getId());
    }

    @Test
    public void findall() throws Exception {
        oDao.add(sampleOrder(sampleCustomer()));
        Item item = sampleItem();
        dao.add(item);
        assertEquals(1, dao.findAll().size());
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