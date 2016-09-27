package com.aqueel.project.EmbeddedServer;

import com.aqueel.project.Adapters.*;
import com.aqueel.project.Builder.ReportBuilder;
import com.aqueel.project.Dao.*;
import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public class Server {

    public static void main(String[] args) throws DaoException {

        port(4567);
        get("/hello", (request, response) -> {
            return "Hello World!";
        });

        String dataSource = "jdbc:h2:~/app295.db";

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

        //GET MENU (ALL) works
        get("/menu", "application/json", (req, res) -> {
            res.status(200);
            List<Food> f = foodDao.findAll();
            List<FoodAdapter> foodAdapters = new ArrayList<FoodAdapter>();
            f.forEach( food -> {
                List<Category> cat = new ArrayList<Category>();
                try {
                    cat = categoryDao.find(food.getId());
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                List<CategoryAdapter> categoryAdapters = new ArrayList<CategoryAdapter>();
                cat.forEach( cats -> {
                    categoryAdapters.add(new CategoryAdapter(cats.getName()));
                });
                foodAdapters.add(new FoodAdapter(food, categoryAdapters));
            });
            return foodAdapters;
        }, gson::toJson);

        //GET MENU (ID) works
        get("/menu/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            if(foodDao.findById(id) == null)
                res.status(404);
            else
                res.status(200);
            Food f = foodDao.findById(id);
            List<Category> categories = categoryDao.find(f.getId());
            List<CategoryAdapter> c = new ArrayList<CategoryAdapter>();
            categories.forEach( cat -> {
                c.add(new CategoryAdapter(cat.getName()));
            });
            FoodAdapter rValue = new FoodAdapter(f, c);
            return rValue;
        }, gson::toJson);


        //Add food item works
        put("/admin/menu", "application/json", (req, res) -> {
            try {
                FoodAdapter adp = gson.fromJson(req.body(), FoodAdapter.class);
                Food food = new Food(adp);
                foodDao.add(food);
                List<CategoryAdapter> categories = adp.getCategories();
                categories.forEach((category) -> {
                    Category c = new Category(category.getName(), food.getId());
                    try {
                        categoryDao.add(c);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                });

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

        //Update Food
        post("/admin/menu/:mid", "application/json", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params("mid"));
                Type type = new TypeToken<Map<String, Double>>(){}.getType();
                Map<String, Double> changes = gson.fromJson(req.body(), type);
                foodDao.update(id, changes.get("price_per_person"));
                res.status(200);
                return foodDao.findById(id);

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

        //GET Surcharge
        get("/admin/surcharge", "application/json", (req, res) -> {
            System.out.print("Hy");
            Map<String, Double> rValue = new HashMap();
            System.out.print("Ho");
            double sur = extrasDao.get("surcharge");
            System.out.print("Hi");
            rValue.put("surcharge", sur);
            res.status(200);
            return rValue;

        }, gson::toJson);

        post("/admin/surcharge", "application/json", (req, res) -> {

            Type type = new TypeToken<Map<String, Double>>(){}.getType();
            Map<String, Double> changes = gson.fromJson(req.body(), type);
            try {
                extrasDao.update("surcharge", changes.get("surcharge"));

            }catch (IllegalStateException ex) {
                res.status(400);
                return 0;
            } catch (java.lang.NullPointerException ec) {
                res.status(404);
                return 0;
            }catch (DaoException ex) {
                res.status(400);
            }
            return changes;

        }, gson::toJson);



        /*
        *
        *   Customers
        *
        */


        //Get Customers
        get("/customer", "application/json", (req, res) -> {

            String query = req.queryParams("key");
            if(query == null) {
                res.status(200);
                return customerDao.findAll();
            }
            else {
                res.status(200);
                return customerDao.find(query);
            }


        }, gson::toJson);

        get("/customer/:cid", "application/json", (req, res) -> {

            int id = Integer.parseInt(req.params("cid"));
            Customer customer = customerDao.find(id);
            ArrayList<BasicOrderAdapter> orderAdapters = new ArrayList<BasicOrderAdapter>();
            orderDao.findByCustomer(customer.getId()).forEach( order -> {
                orderAdapters.add(new BasicOrderAdapter(order));
            });
            CustomerAdapter rValue = new CustomerAdapter(customer, orderAdapters);
            res.status(200);
            return rValue;

        }, gson::toJson);


        /*
        *
        *   Orders
        *
        */


        //Get Order
        get("/order", "application/json", (req, res) -> {
            String query = req.queryParams("date");
            ArrayList<BasicOrderAdapter> rValue = new ArrayList<BasicOrderAdapter>();
            if(query == null) {
                orderDao.findAll().forEach(order -> {
                    try {
                        Customer c = customerDao.find(order.getCustomer_id());
                        rValue.add(new BasicOrderAdapter(order, c.getEmail()));
                    } catch (DaoException e) {
                        res.status(404);
                        e.printStackTrace();
                    }
                });
                res.status(200);
                return rValue;
            }
            else {
                orderDao.findByDate(query).forEach(order -> {
                    try {
                        Customer c = customerDao.find(order.getCustomer_id());
                        rValue.add(new BasicOrderAdapter(order, c.getEmail()));
                    } catch (DaoException e) {
                        res.status(404);
                        e.printStackTrace();
                    }
                });
                res.status(200);
                return rValue;
            }

        }, gson::toJson);

        //GEt Specific Order
        get("/order/:oid", "application/json", (req, res) -> {

            ArrayList<FullOrderAdapter> orderAdapters = new ArrayList<FullOrderAdapter>();
            ArrayList<ItemAdapter> items = new ArrayList<ItemAdapter>();
            int id = Integer.parseInt(req.params("oid"));
            try {
                res.status(200);
                Order order = orderDao.find(id);
                Customer c = customerDao.find(order.getCustomer_id());
                List<Item> parts = itemDao.find(order.getId());
                parts.forEach( part -> {
                    items.add(new ItemAdapter(part));
                });

                orderAdapters.add(new FullOrderAdapter(order, c, items));
                return orderAdapters;

            }catch (IllegalStateException ex) {
                res.status(400);
                return 0;
            } catch (java.lang.NullPointerException ec) {
                res.status(404);
                return 0;
            }catch (DaoException ex) {
                res.status(501);
                return 0;
            }

        }, gson::toJson);


        //Insert a new order
        put("/order", "application/json", (req, res) -> {

            OrderAdapter o = gson.fromJson(req.body(), OrderAdapter.class);
            Customer c = o.getCustomer(), d;
            d = customerDao.findByEmail(c.getEmail());
            if(d == null)
                customerDao.add(c);
            System.out.print("Yolo");
            Order order = new Order(o, 0, extrasDao.get("surcharge"), d.getId());
            orderDao.add(order);
            Map<String, Double> amount = new HashMap<String, Double>();
            amount.put("total", (double) 0);
            o.getOrder_detail().forEach( detail -> {
                Food f = null;
                try {
                    f = foodDao.findById(detail.getId());
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                try {
                    amount.put("total", amount.get("total") + f.getPrice()*detail.getCount());
                    itemDao.add(new Item(detail, f, order.getId()));
                    res.status(200);
                } catch (DaoException e) {
                    res.status(400);
                    e.printStackTrace();
                    return;
                }

            });
            orderDao.update(order.getId(), amount.get("total"));
            Map<String, String> rValue = new HashMap();
            rValue.put("id", "" + order.getId());
            rValue.put("cancel_url", "http://localhost:4567/order/cancel/" + order.getId());

            return rValue;

        }, gson::toJson);

        //Deliver order
        post("/admin/deliver/:oid", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("oid"));
            return orderDao.deliver(id);
        }, gson::toJson);

        //Cancel order
        post("/order/cancel/:oid", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("oid"));
            return orderDao.cancel(id);
        }, gson::toJson);


        /*
        *
        *   Reports
        *
        */

        get("/report", "application/json", (req, res) -> {

            ArrayList<Report> reports = new ArrayList();
            reports.add(new Report(801, "Orders to deliver today"));
            reports.add(new Report(802, "Orders to deliver tomorrow"));
            reports.add(new Report(803, "Revenue report"));
            reports.add(new Report(804, "Orders delivery report"));

            res.status(200);
            return reports;

        }, gson::toJson);

        get("/report/:rid", "application/json", (req, res) -> {

            int id = Integer.parseInt(req.params("rid"));
            ReportBuilder rb = new ReportBuilder();
            Report report = null;
            switch (id) {
                case 801:
                    report = rb.deliveryToday()
                            .onOrders(orderDao,itemDao,customerDao)
                            .get();
                    res.status(200);
                    break;
                case 802:
                    report = rb.deliveryTomorrow()
                            .onOrders(orderDao,itemDao,customerDao)
                            .get();
                    res.status(200);
                    break;
                case 803:
                    report = rb.revenue()
                            .onOrders(orderDao,itemDao,customerDao)
                            .get();
                    res.status(200);
                    break;
                case 804:
                    report = rb.deliveryToday()
                            .onOrders(orderDao,itemDao,customerDao)
                            .get();
                    res.status(200);
                    break;
                default:
                    res.status(404);
            }
            return report;

        }, gson::toJson);

        after((req, res) -> {
            res.type("application/json");
        });

    }
}
