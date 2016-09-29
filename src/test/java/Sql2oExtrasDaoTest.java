import com.aqueel.project.Dao.Sql2oExtrasDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by aqueelmiqdad on 9/27/16.
 */
public class Sql2oExtrasDaoTest {

    private Sql2oExtrasDao dao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String conString = "jdbc:h2:mem:aTesting;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString,"","");
        dao = new Sql2oExtrasDao(sql2o);
        //Keep connection open for aTesting
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void update() throws Exception {
        dao.update("surcharge", 3);
        assertEquals(dao.get("surcharge"), 3, 0.0001);
    }

    @Test
    public void get() throws Exception {
        assertEquals(dao.get("surcharge"), 0, 0.001);
    }

}