package com.aqueel.project.Builder;

import com.aqueel.project.Adapters.FullOrderAdapter;
import com.aqueel.project.Adapters.ItemAdapter;
import com.aqueel.project.Adapters.OrderAdapter;
import com.aqueel.project.Dao.CustomerDao;
import com.aqueel.project.Dao.FoodDao;
import com.aqueel.project.Dao.ItemDao;
import com.aqueel.project.Dao.OrderDao;
import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.*;
import com.aqueel.project.Models.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/26/16.
 */
public class ReportBuilder {

    private Report report;

    public ReportBuilder() {}

    public ReportBuilder deliveryToday() {
        this.report = new DateReport(801, "Orders to deliver today");
        return this;
    }

    public ReportBuilder deliveryTomorrow() {
        this.report = new DateReport(802, "Orders to deliver tomorrow");
        return this;
    }

    public ReportBuilder revenue() {
        this.report = new RevenueReport(803, "Revenue report");
        return this;
    }

    public ReportBuilder delivery() {
        this.report = new RevenueReport(803, "Orders delivery report");
        return this;
    }

    public ReportBuilder withStart(String start) {
        ((RevenueReport)report).setStart_date(start);
        return this;
    }

    public ReportBuilder withEnd(String end) {
        ((RevenueReport)report).setStart_date(end);
        return this;
    }

    public ReportBuilder onOrders(OrderDao oDao, ItemDao iDao, CustomerDao cDao) throws DaoException {

        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        System.out.print(df.format(today));
        List<Order> orders;
        ArrayList<ItemAdapter> items = new ArrayList<>();

        ArrayList<FullOrderAdapter> orderAdapters = new ArrayList<>();

        switch(report.getId()) {
            case 801:
                DateReport dReport = (DateReport) report;
                String sToday = df.format(today);
                orders = oDao.findByDate(sToday);
                extractData(iDao, cDao, orders, items, orderAdapters);
                dReport.setOrders(orderAdapters);
                break;
            case 802:
                DateReport dReport2 = (DateReport) report;
                String sTomorrow = df.format(tomorrow);
                orders = oDao.findByDate(sTomorrow);
                extractData(iDao, cDao, orders, items, orderAdapters);
                dReport2.setOrders(orderAdapters);
                break;
            case 803:
                double total = 0, tSur = 0;
                int cancelled = 0, open = 0, delivered = 0;
                RevenueReport rev = (RevenueReport)report;
                orders = oDao.findBetween(rev.getStart_date(), rev.getEnd_date());
                for(Order order: orders) {
                    tSur += order.getSurcharge();
                    total += order.getAmount();
                    if(order.getStatus().equalsIgnoreCase("open"))
                        open++;
                    else if(order.getStatus().equalsIgnoreCase("delivered"))
                        delivered++;
                    else
                        cancelled++;
                    rev.setFood_revenue(total);
                    rev.setOrders_cancelled(cancelled);
                    rev.setSurcharge_revenue(tSur);
                    rev.setOrders_open(open);
                    rev.setOrders_placed(open + delivered + cancelled);
                }
                break;
            case 804:
                break;
        }

        return this;
    }

    private void extractData(ItemDao iDao, CustomerDao cDao, List<Order> orders, ArrayList<ItemAdapter> items, ArrayList<FullOrderAdapter> orderAdapters) {
        orders.forEach(order -> {
            try {
                Customer c = cDao.find(order.getCustomer_id());
                List<Item> parts = iDao.find(order.getId());
                parts.forEach( part -> {
                    items.add(new ItemAdapter(part));
                });
                orderAdapters.add(new FullOrderAdapter(order, c, items));
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
    }

    public Report get() {
        return this.report;
    }
}
