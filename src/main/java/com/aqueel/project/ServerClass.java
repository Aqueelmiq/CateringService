package com.aqueel.project;

import com.aqueel.project.Adapters.*;
import com.aqueel.project.Builder.ReportBuilder;
import com.aqueel.project.Dao.*;
import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Created by aqueelmiqdad on 9/27/16.
 */
public class ServerClass {

    public static void getMenu(Gson gson, FoodDao foodDao, CategoryDao categoryDao) {
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
    }

    public static void getMenuId(Gson gson, FoodDao foodDao, CategoryDao categoryDao) {
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
    }

    public static void getReports(Gson gson) {
        get("/report", "application/json", (req, res) -> {

            ArrayList<Report> reports = new ArrayList();
            reports.add(new Report(801, "Orders to deliver today"));
            reports.add(new Report(802, "Orders to deliver tomorrow"));
            reports.add(new Report(803, "Revenue report"));
            reports.add(new Report(804, "Orders delivery report"));

            res.status(200);
            return reports;

        }, gson::toJson);
    }

    public static void cancelOrder(Gson gson, OrderDao orderDao) {
        post("/order/cancel/:oid", "application/json", (req, res) -> {

            int id = Integer.parseInt(req.params("oid"));
            Order order = orderDao.find(id);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            if(df.format(new Date()).equalsIgnoreCase(order.getDelivery_date())) {
                return "Cannot cancel today's order!";
            }
            orderDao.cancel(id);
            return "Cancelled";
        }, gson::toJson);
    }

    public static void deliverOrder(Gson gson, OrderDao orderDao) {
        post("/admin/deliver/:oid", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("oid"));
            orderDao.deliver(id);
            return "Delivered";
        }, gson::toJson);
    }

    public static void putOrder(Gson gson, FoodDao foodDao, ExtrasDao extrasDao, OrderDao orderDao, ItemDao itemDao, CustomerDao customerDao) {
        put("/order", "application/json", (req, res) -> {

            List<Item> items = new ArrayList();
            OrderAdapter o = gson.fromJson(req.body(), OrderAdapter.class);
            if(o.getOrder_detail().isEmpty()) {
                res.status(500);
                return "Invalid order";
            }
            Map<String, Double> amount = new HashMap();
            Customer c = o.getCustomer(), d = customerDao.findByEmail(c.getEmail());
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            calendar.setTime(df.parse(o.getDelivery_date()));
            Order order;
            if(calendar.DAY_OF_WEEK == Calendar.SATURDAY || calendar.DAY_OF_WEEK == Calendar.SUNDAY)
                 order = new Order(o, 0, extrasDao.get("surcharge"), 0);
            else
                order = new Order(o, 0, 0, 0);
            amount.put("total", (double) 0);
            for(Detail detail: o.getOrder_detail()) {
                Food f = null;
                try {
                    f = foodDao.findById(detail.getId());
                    if(f.getMin_qty() > detail.getCount()) {
                        res.status(400);
                        return 0;
                    }
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                amount.put("total", amount.get("total") + f.getPrice()*detail.getCount());
                items.add(new Item(detail, f, -1));
            }
            orderDao.add(order);
            if(d == null) {
                customerDao.add(c);
                d = c;
            }
            order.setCustomer_id(d.getId());
            order.setAmount(amount.get("total"));
            orderDao.update(order.getId(), amount.get("total"));
            orderDao.update(order.getId(), d.getId());
            for (Item item: items) {
                try {
                    item.setOrder_id(order.getId());
                    itemDao.add(item);
                } catch (DaoException e) {
                    e.printStackTrace();
                }
            }
            Map<String, String> rValue = new HashMap();
            rValue.put("id", "" + order.getId());
            rValue.put("cancel_url", "http://localhost:4567/order/cancel/" + order.getId());

            res.status(200);
            return rValue;

        }, gson::toJson);
    }

    public static void GetOrderId(Gson gson, OrderDao orderDao, ItemDao itemDao, CustomerDao customerDao) {
        get("/order/:oid", "application/json", (req, res) -> {

            ArrayList<FullOrderAdapter> orderAdapters = new ArrayList<FullOrderAdapter>();
            ArrayList<ItemAdapter> items = new ArrayList<ItemAdapter>();
            int id = Integer.parseInt(req.params("oid"));
            try {

                Order order = orderDao.find(id);
                if (order == null) {
                    res.status(404);
                    return null;
                }
                Customer c = customerDao.find(order.getCustomer_id());
                List<Item> parts = itemDao.find(order.getId());
                parts.forEach( part -> {
                    items.add(new ItemAdapter(part, 1));
                });
                orderAdapters.add(new FullOrderAdapter(order, c, items));

                res.status(200);
                return orderAdapters;

            } catch (DaoException ex) {
                res.status(501);
                return 0;
            }

        }, gson::toJson);
    }

    public static void getOrder(Gson gson, OrderDao orderDao, CustomerDao customerDao) {
        get("/order", "application/json", (req, res) -> {
            String query = req.queryParams("date");
            ArrayList<BasicOrderAdapter> rValue = new ArrayList();
            if(query == null) {
                for(Order order: orderDao.findAll()){
                    retreiveOrder(customerDao, res, rValue, order);
                }
                res.status(200);
                return rValue;
            }
            else {
                for(Order order: orderDao.findByDate(query)){
                    retreiveOrder(customerDao, res, rValue, order);
                }
                res.status(200);
                return rValue;
            }

        }, gson::toJson);
    }

    public static void getCustomerId(Gson gson, OrderDao orderDao, CustomerDao customerDao) {
        get("/customer/:cid", "application/json", (req, res) -> {

            int id = Integer.parseInt(req.params("cid"));
            Customer customer = customerDao.find(id);
            if(customer == null) {
                res.status(404);
                return null;
            }
            ArrayList<BasicOrderAdapter> orderAdapters = new ArrayList<BasicOrderAdapter>();
            orderDao.findByCustomer(customer.getId()).forEach( order -> {
                orderAdapters.add(new BasicOrderAdapter(order));
            });
            CustomerAdapter rValue = new CustomerAdapter(customer, orderAdapters);
            res.status(200);
            return rValue;

        }, gson::toJson);
    }

    public static void getCustomers(Gson gson, CustomerDao customerDao) {
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
    }

    public static void setSurcharge(Gson gson, ExtrasDao extrasDao) {
        post("/admin/surcharge", "application/json", (req, res) -> {

            Type type = new TypeToken<Map<String, Double>>(){}.getType();
            Map<String, Double> changes = gson.fromJson(req.body(), type);
            extrasDao.update("surcharge", changes.get("surcharge"));
            return changes;

        }, gson::toJson);
    }

    public static void getSurcharge(Gson gson, ExtrasDao extrasDao) {
        get("/admin/surcharge", "application/json", (req, res) -> {
            Map<String, Double> rValue = new HashMap();
            double sur = extrasDao.get("surcharge");
            rValue.put("surcharge", sur);
            res.status(200);
            return rValue;

        }, gson::toJson);
    }

    public static void updateMenu(Gson gson, FoodDao foodDao) {
        post("/admin/menu/:mid", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("mid"));
            Type type = new TypeToken<Map<String, Double>>() {}.getType();
            Map<String, Double> changes = gson.fromJson(req.body(), type);
            int status = foodDao.update(id, changes.get("price_per_person"));
            if(status > 0) {
                res.status(200);
                return foodDao.findById(id);
            }
            else
                res.status(404);
            return 0;
        }, gson::toJson);
    }

    public static void putMenu(Gson gson, FoodDao foodDao, CategoryDao categoryDao) {
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
            } catch (NullPointerException ec) {
                res.status(404);
                return 0;
            }

        }, gson::toJson);
    }

    public static Object switchReport(OrderDao orderDao, ItemDao itemDao, CustomerDao customerDao, Request req, Response res) throws DaoException {
        int id = Integer.parseInt(req.params("rid"));
        String start = req.queryParams("start_date");
        String end = req.queryParams("end_date");
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
                        .withStart(start)
                        .withEnd(end)
                        .onOrders(orderDao,itemDao,customerDao)
                        .get();
                res.status(200);
                break;
            case 804:
                report = rb.delivery()
                        .withStart(start)
                        .withEnd(end)
                        .onOrders(orderDao,itemDao,customerDao)
                        .get();
                res.status(200);
                break;
            default:
                res.status(404);
        }
        return report;
    }

    //Utility Methods

    public static void retreiveOrder(CustomerDao customerDao, Response res, ArrayList<BasicOrderAdapter> rValue, Order order) {
        try {
            Customer c = customerDao.find(order.getCustomer_id());
            rValue.add(new BasicOrderAdapter(order, c.getEmail()));
        } catch (DaoException e) {
            res.status(404);
            e.printStackTrace();
        }
    }

}
