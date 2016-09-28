package com.aqueel.project;

import com.aqueel.project.Adapters.*;
import com.aqueel.project.Dao.Sql2oCustomerDao;
import com.aqueel.project.Dao.Sql2oFoodDao;
import com.aqueel.project.Dao.Sql2oItemDao;
import com.aqueel.project.Dao.Sql2oOrderDao;
import com.aqueel.project.EmbeddedServer.Server;
import com.aqueel.project.Models.*;
import com.aqueel.project.aTesting.ApiClient;
import com.aqueel.project.aTesting.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.lang.reflect.Type;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by aqueelmiqdad on 9/27/16.
 */
public class ServerTest {

    public static final String PORT = "4560";
    public static final String TESTING_SOURCE = "jdbc:h2:mem:aTesting";
    private Sql2o sql2o;
    private Connection conn;
    private Gson gson;
    private ApiClient client;
    private Sql2oOrderDao dao;
    private Sql2oFoodDao daoF;
    private Sql2oItemDao daoI;
    private Sql2oCustomerDao daoC;

    @BeforeClass
    public static void startServer() throws Exception {
        String[] args = {PORT, TESTING_SOURCE};
        Server.main(args);
        sleep(200);
    }

    @Before
    public void setUp() throws Exception {
        sleep(100);
        String conString = TESTING_SOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        sql2o = new Sql2o(conString , "", "");
        client = new ApiClient("http://localhost:4560");
        conn = sql2o.open();
        gson = new Gson();
        dao = new Sql2oOrderDao(sql2o);
        daoF = new Sql2oFoodDao(sql2o);
        daoI = new Sql2oItemDao(sql2o);
        daoC = new Sql2oCustomerDao(sql2o);
        Food food = sampleFood();
        daoF.add(food);

        Customer cust = sampleCustomer();
        daoC.add(cust);

        Order order = sampleOrder(cust);
        dao.add(order);

        Item item = sampleItem(food, order);
        daoI.add(item);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @AfterClass
    public static void stopServer() throws Exception {
        Spark.stop();
    }

    //SURCHARGE

    @Test
    public void putSurchargeWorks() throws Exception {

        Map<String, Double> map = new HashMap<>();
        map.put("surcharge", 3.4);
        ApiResponse response = client.request("POST", "/admin/surcharge", gson.toJson(map));
        Type type = new TypeToken<Map<String, Double>>(){}.getType();
        Map<String, Double> result = gson.fromJson(response.getBody(), type);
        assertEquals(3.4, result.get("surcharge"), 0.001);


    }

    @Test
    public void getSurchargeWorks() throws Exception {

        ApiResponse response = client.request("GET", "/admin/surcharge");
        assertEquals(200, response.getStatus());

    }

    //MENU

    @Test
    public void getMenuWorks() throws Exception {

        Food food = sampleFood();
        daoF.add(food);
        ApiResponse response = client.request("GET", "/menu");
        assertEquals(200, response.getStatus());

    }

    //GET MENU ID
    @Test
    public void getMenuIDWorks() throws Exception {

        Food food = sampleFood();
        daoF.add(food);
        ApiResponse response = client.request("GET", "/menu/1");
        assertEquals(200, response.getStatus());

    }

    //POST MENU

    @Test
    public void postMenuPriceWorks() throws Exception {

        Map foods = new HashMap<>();
        foods.put("price_per_person", 7.3);
        ApiResponse response = client.request("POST", "/admin/menu/1", gson.toJson(foods));
        Food s = daoF.findById(1);
        assertEquals(7.3, s.getPrice(), 0.0001);

    }

    @Test
    public void postMenuPriceWorksWell() throws Exception {

        Map foods = new HashMap<>();
        foods.put("price_per_person", 7.3);
        ApiResponse response = client.request("POST", "/admin/menu/333", gson.toJson(foods));
        assertEquals(404, response.getStatus(), 0.0001);
    }

    @Test
    public void putMenuWorks() throws Exception {


        List<CategoryAdapter> categories = new ArrayList<CategoryAdapter>();
        categories.add(new CategoryAdapter("hi"));
        Map food = new HashMap<>();
        food.put("name", "blah");
        food.put("categories", categories);
        food.put("price_per_person", 2);
        food.put("minimum_order", 100);

        ApiResponse response = client.request("PUT", "/admin/menu", gson.toJson(food) );

        assertEquals(200, response.getStatus());

    }

    //Invalid Menu
    @Test
    public void putIvalidMenuNotWorks() throws Exception {

        ApiResponse response = client.request("PUT", "/admin/menu", "");

        assertEquals(404, response.getStatus());

    }

    //ORDER

    //GET ORDER
    @Test
    public void getOrderWorks() throws Exception {

        daoF.add(sampleFood());
        daoI.add(sampleItem());
        ApiResponse response = client.request("GET", "/order");
        assertEquals(500, response.getStatus());

    }

    @Test
    public void getOrderIDNotFoundWorks() throws Exception {

        ApiResponse response = client.request("GET", "/order/500");
        assertEquals(404, response.getStatus());

    }

    @Test
    public void getOrderIDFoundWorks() throws Exception {

        dao.add(sampleOrder(sampleCustomer()));
        ApiResponse response = client.request("GET", "/order/1");
        assertEquals(200, response.getStatus());

    }


    //PUT ORDER
    @Test
    public void putOrderNotWorks() throws Exception {

        Customer info = new Customer("Aqueel", "a@a.com", "3125369986");
        List<Item> items = new ArrayList();

        Map order = new HashMap();
        order.put("delivery_date", "20160301");
        order.put("delivery_address", "10 West 31st ST, Chicago IL 60616");
        order.put("personal_info", info);
        order.put("note", "Room SB-214");
        order.put("order_detail", items);

        ApiResponse response = client.request("PUT", "/order", gson.toJson(order));

        assertEquals(500, response.getStatus());

    }

    @Test
    public void putOrderWorks() throws Exception {

        Customer c = sampleCustomer();
        Order order = sampleOrder(c);
        order.setNote("Hello Brah");
        ArrayList<Detail> d = new ArrayList();
        d.add(sampleDetail());
        OrderAdapter o = new OrderAdapter(order, c, d);
        ApiResponse response = client.request("PUT", "/order", gson.toJson(o));
        assertEquals(200, response.getStatus());

    }


    //Customer

    @Test
    public void getCustomerWorks() throws Exception {


        ApiResponse response = client.request("GET", "/customer");
        assertEquals(200, response.getStatus());

    }

    @Test
    public void getCustomerIdWorks() throws Exception {


        ApiResponse response = client.request("GET", "/customer/1");
        assertEquals(200, response.getStatus());

    }

    @Test
    public void getCustomerIdNotWorks() throws Exception {


        ApiResponse response = client.request("GET", "/customer/100");
        assertEquals(404, response.getStatus());

    }

    @Test
    public void getReoprtWorks() throws Exception {

        ApiResponse response = client.request("GET", "/report");
        assertEquals(200, response.getStatus());

    }

    @Test
    public void getRevenueReoprtWorks() throws Exception {


        ApiResponse response = client.request("GET", "/report/803");
        assertEquals(200, response.getStatus());

    }

    @Test
    public void getTodayReportWorks() throws Exception {


        ApiResponse response = client.request("GET", "/report/801");
        assertEquals(200, response.getStatus());

    }

    @Test
    public void getTomorrowReportFoundWorks() throws Exception {


        ApiResponse response = client.request("GET", "/report/802");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void getRevReportFoundWorks() throws Exception {


        ApiResponse response = client.request("GET", "/report/803");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void getDateReportFoundWorks() throws Exception {


        ApiResponse response = client.request("GET", "/report/804");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void getTomorrowReportWorks() throws Exception {



        ApiResponse response = client.request("GET", "/report/802");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void getDeliveryReportWorks() throws Exception {



        ApiResponse response = client.request("GET", "/report/804");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void getCustomersWorks() throws Exception {

        ApiResponse response = client.request("GET", "/customer");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void CustomerOrderWorks() throws Exception {


        ApiResponse response = client.request("GET", "/customer/1");

        assertEquals(200, response.getStatus());

    }

    @Test
    public void CustomernameWorks() throws Exception {


        ApiResponse response = client.request("GET", "/customer?key=Jordan");

        assertEquals(200, response.getStatus());

    }

    //Reports
    @Test
    public void Report801Works() throws Exception {

        ApiResponse response = client.request("GET", "/report/801");
        assertEquals(200, response.getStatus());

    }
    @Test
    public void Report802Works() throws Exception {

        ApiResponse response = client.request("GET", "/report/802");
        assertEquals(200, response.getStatus());

    }
    @Test
    public void Report803Works() throws Exception {

        ApiResponse response = client.request("GET", "/report/803");
        assertEquals(200, response.getStatus());

    }
    @Test
    public void Report804Works() throws Exception {

        ApiResponse response = client.request("GET", "/report/804");
        assertEquals(200, response.getStatus());

    }

    private Order sampleOrder(Customer cust) {
        return new Order(cust, 2, 3, 4, "20160929", "Helo St 2140");
    }

    private Item sampleItem(Food food, Order order) {
        return new Item(100, food.getName(), order.getId(), food.getPrice()*100);
    }

    private Customer sampleCustomer() {
        return new Customer("Jordan", "3215676780", "mike.jordan@gmail.com");
    }

    private Food sampleFood() {
        return new Food("Chicken Soup", 1.5, 2);
    }

    private Item sampleItem() {
        return new Item(1, "Hello", 1, 23);
    }

    private Detail sampleDetail() {
        return new Detail(1, 20);
    }

}