package com.aqueel.project.EmbeddedServer;

import com.aqueel.project.Dao.*;
import com.aqueel.project.Exc.DaoException;
import com.google.gson.Gson;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import static com.aqueel.project.ServerClass.*;
import static spark.Spark.*;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Server {

    public static void main(String[] args) throws DaoException {

        port(4567);
        String dataSource = "jdbc:h2:~/app306.db";

        if(args.length > 0) {
            port(Integer.parseInt(args[0]));
            dataSource = args[1];
        }

        String conString = dataSource + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString, "", "");
        Gson gson = new Gson();

        FoodDao foodDao = new Sql2oFoodDao(sql2o);
        CategoryDao categoryDao = new Sql2oCategoryDao(sql2o);
        ExtrasDao extrasDao = new Sql2oExtrasDao(sql2o);
        OrderDao orderDao = new Sql2oOrderDao(sql2o);
        ItemDao itemDao = new Sql2oItemDao(sql2o);
        CustomerDao customerDao = new Sql2oCustomerDao(sql2o);

        Connection con = sql2o.open();

        /*
        *
        *   MENU
        *
        */

        //GET MENU (ALL) works
        getMenu(gson, foodDao, categoryDao);

        //GET MENU (ID) works
        getMenuId(gson, foodDao, categoryDao);

        //Add food item works
        putMenu(gson, foodDao, categoryDao);

        //Update Food
        updateMenu(gson, foodDao);

        /*
        *
        *   Surcharge
        *
        */

        //GET Surcharge
        getSurcharge(gson, extrasDao);

        //Update Surcharge
        setSurcharge(gson, extrasDao);


        /*
        *
        *   Customers
        *
        */


        //Get Customers
        getCustomers(gson, customerDao);

        //Get Customer By ID
        getCustomerId(gson, orderDao, customerDao);


        /*
        *
        *   Orders
        *
        */


        //Get Order
        getOrder(gson, orderDao, customerDao);

        //GEt Specific Order
        GetOrderId(gson, orderDao, itemDao, customerDao);


        //Insert a new order
        putOrder(gson, foodDao, extrasDao, orderDao, itemDao, customerDao);

        //Deliver order
        deliverOrder(gson, orderDao);

        //Cancel order
        cancelOrder(gson, orderDao);


        /*
        *
        *   Reports
        *
        */

        //GET Reports
        getReports(gson);

        //Get Specific Report
        get("/report/:rid", "application/json", (req, res) -> {

            return switchReport(orderDao, itemDao, customerDao, req, res);

        }, gson::toJson);

        after((req, res) -> {
            res.type("application/json");
        });

    }


}
