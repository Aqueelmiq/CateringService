package com.aqueel.project;


import com.aqueel.project.Adapters.FoodAdapter;
import com.aqueel.project.Dao.FoodDao;
import com.aqueel.project.Dao.Sql2oFoodDao;
import com.aqueel.project.Models.Food;
import com.google.gson.Gson;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.servlet.SparkApplication;


import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Main implements SparkApplication {

    @Override
    public void init() {

        get("/hello", (request, response) -> {
            return "Hello World!";
        });

        String dataSource = "jdbc:h2:~/app124.db";

        String conString = dataSource + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(conString, "", "");
        FoodDao foodDao = new Sql2oFoodDao(sql2o);
        Connection con = sql2o.open();
        Gson gson = new Gson();


        /*
        *
        *   Food & Menu
        *
        */


        //GET MENU
        get("/menu", "application/json", (req, res) -> {
            res.status(200);
            return foodDao.findAll();
        }, gson::toJson);

        //GET MENU (ID) works
        get("/menu/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            if(foodDao.findById(id) == null)
                res.status(404);
            else
                res.status(200);
            return foodDao.findById(id);
        }, gson::toJson);


        after((req, res) -> {
            res.type("application/json");
        });


        //Add food item works
        put("/admin/menu", "application/json", (req, res) -> {
            try {
                Food food = gson.fromJson(req.body(), Food.class);
                foodDao.add(food);
                Map<String, Integer> idMap = new HashMap();
                idMap.put("id", food.getId());
                res.status(200);
                return idMap;

            }catch (IllegalStateException ex) {
                res.status(400);
                return 0;
            } catch (java.lang.NullPointerException ec) {
                res.status(404);
                return 0;
            }


        }, gson::toJson);



        /*
        *
        *   Surcharge
        *
        */



        /*
        *
        *   Customers
        *
        */



        /*
        *
        *   Orders
        *
        */



        /*
        *
        *   Reports
        *
        */


    }
}

