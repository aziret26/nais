package kg.nais.controllers;

import kg.nais.facade.*;
import kg.nais.models.*;
import kg.nais.models.notification.UserFeedNotification;
import kg.nais.tools.customCalendar.CustomCalendar;
import kg.nais.tools.Tools;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static kg.nais.tools.ViewPath.*;

/**
 * Created by aziret on 7/8/17.
 */
@ManagedBean
@ViewScoped
public class OrdersController extends GeneralController{

    private Client client;

    private Orders order = new Orders();

    private int selectedFeedId = 0;

    private List<Orders> orderList = new ArrayList<Orders>();

    private CustomCalendar customCalendar = new CustomCalendar();

    private OrderHistoryController orderHistoryController = new OrderHistoryController();

    public OrdersController() {
        orderList = new OrderFacade().findAll();
    }

    public CustomCalendar getCustomCalendar() {
        return customCalendar;
    }

    public void setCustomCalendar(CustomCalendar customCalendar) {
        this.customCalendar = customCalendar;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<Orders> getOrderList() {
        if(orderList == null || orderList.size() == 0){
            orderList = new OrderFacade().findAll();
        }
        return orderList;
    }

    public Orders getOrderListByClientsFeed(Client client,Feed feed) {
        Orders o = new OrderFacade().findByClientFeed(client, feed);
        return o != null ? o : new Orders();
    }

    public void setOrderList(List<Orders> orderList) {
        if(orderList == null || orderList.size() == 0)
            orderList = new OrderFacade().findAll();
        this.orderList = orderList;
    }

    public Client getClient() {
        if(clientId != 0) {
            client = new ClientFacade().findById(clientId);
        }
        return client;
    }

    public int getSelectedFeed() {
        return selectedFeedId;
    }

    public void setSelectedFeed(int selectedFeedId) {
        this.selectedFeedId = selectedFeedId;
    }

    @Override
    public void setClientId(int clientId) {
        if(client == null || client.getClientId() != clientId)
            client = new ClientFacade().findById(clientId);
        super.setClientId(clientId);
    }

    public String addOrder(){
        return PM_ADD_ORDER + REDIRECT + "clientId="+clientId;
    }

    public String addOrder(int id){
        return PM_ADD_ORDER + REDIRECT + "clientId="+id;
    }

    public String createOrder(){
        if(client == null || client.getClientId() == 0 ){
            Tools.faceMessageWarn("Неверный ID клиента.","");
            return INDEX;
        }
        if(selectedFeedId == 0){
            Tools.faceMessageWarn("Выберите вид корма","");
            return PM_ADD_ORDER + REDIRECT + "clientId="+clientId;
        }
        order.setFeed(new FeedFacade().findById(selectedFeedId));
        order.setClient(client);
        double amountOfFeed = order.getAmount();
        if(customCalendar.after()){
            calculateConsumptionTill(order,customCalendar);
        }

        order.setOrderDate(customCalendar.createCalendar());

        order.setDueDate(calcOrderDueDate(order));

        OrdersHistory ordersHistory = createHistory(order,amountOfFeed);

        if(orderHistoryController.isLastOrder(ordersHistory)) {
            Orders tempOrder = new OrderFacade().findByClientFeed(order.getClient(), order.getFeed());

            if (tempOrder != null) {
                tempOrder.setAmount(order.getAmount());
                tempOrder.setDueDate(calcOrderDueDate(order));
                new OrderFacade().update(tempOrder);
                return PM_VIEW_ORDERS + REDIRECT;
            }
            OrderFacade of = new OrderFacade();
            of.create(order);
            //Remove notification about this cause
            UserFeedNotification ufn = new UserFeedNotificationFacade().findByClientAndFeed(order.getClient(),order.getFeed());
            if(ufn != null)
                new NotificationController().removeNotification(ufn);
        }
        return PM_VIEW_ORDERS + REDIRECT;
    }

    private void calculateConsumptionTill(Orders order,CustomCalendar customCalendar){
        CustomCalendar now = new CustomCalendar();
        int days = customCalendar.differenceInDays(now);
        List<Chick> chickList = new ChickController().findChickListByClient(order.getClient());
        ChickController cc = new ChickController();
        ChickFeedConsumeController consumeController = new ChickFeedConsumeController();
        for(Chick c: chickList){
            cc.decreaseAgeByDays(c,days);
        }
        double consumption = 0;
        for(int i = 0; i < days; i++){
            List<Chick> list = cc.getChicksForFeed(chickList,order.getFeed());
            for(Chick chick : list) {
                consumption += consumeController.getConsumeAmount(chick);
            }
            chickList.forEach(cc::increaseChicksAgeByDay);
        }
        order.setAmount(order.getAmount() - consumption);
    }

    /**
     * calculates the date when feed, that has been ordered, will be consumed
     * @param o
     * @return
     */
    public Calendar calcOrderDueDate(Orders o){
        Calendar date = Calendar.getInstance();
        List<Chick> chickList = new ChickController().getChicksByClientForFeedBelow(o.getClient(),o.getFeed());
        ChickFeedConsumeController consumeController = new ChickFeedConsumeController();
        ChickController cc = new ChickController();
        double amount = o.getAmount();

        if(chickList == null){
            return null;
        }

        CustomCalendar customDate;
        while (amount > 0.49){
            customDate = new CustomCalendar(date);
            List<Chick> list = cc.getChicksForFeed(chickList, o.getFeed());
            if(!customDate.after(order.getOrderDate())) {
                for (Chick c : list) {
                    amount -= consumeController.getConsumeAmount(c);
                    if (amount < 0.5) {
                        return date;
                    }
                }
            }
            chickList.forEach(new ChickController()::increaseChicksAgeByDay);
            if (chickList.size() == 0 && amount > 0) {
                return null;
            }
            date.add(Calendar.DAY_OF_YEAR,1);
        }
        return date;
    }

    public void updateClientsOrderDueDate(Client client){
        OrderFacade of = new OrderFacade();
        List<Orders> ordersList = of.findByClient(client);
        for(Orders o : ordersList){
            o.setDueDate(calcOrderDueDate(o));
            of.update(o);
        }
    }

    public void deleteOrders(Client client){
        OrderFacade of = new OrderFacade();
        List<Orders> ordersList = of.findByClient(client);
        ordersList.forEach(of::delete);
    }

    public Orders getOrderByClientAndFeed(Client client,Feed feed){
        for(Orders order : orderList){
            if(order.getClient().getClientId() == client.getClientId() &&
                    order.getFeed().getFeedId() == feed.getFeedId())
                return order;
        }
        return null;
    }

    public boolean hasResourcesByDate(Client client,Feed feed,Calendar date){
        if( orderList == null || orderList.size() == 0 ){
            return false;
        }
        for( Orders o : orderList ){
            if( o.getClient().getClientId() == client.getClientId() &&
                o.getFeed().getFeedId() == feed.getFeedId() &&
                ( o.getDueDate() == null ||
                    o.getDueDate().compareTo(date) > 0 )){
                return true;
            }
        }
        return false;
    }

    private OrdersHistory createHistory(Orders order,double amountOfFeed){
        OrdersHistoryFacade ohf = new OrdersHistoryFacade();

        List<OrdersHistory> ordersHistoryList = ohf.findByClientAndFeed(order.getClient(),order.getFeed());
        for(OrdersHistory oh : ordersHistoryList){
            if(customCalendar.differenceInDays(oh.getOrderDate()) == 0){
                oh.setDueDate(order.getDueDate());
                oh.setAmount(amountOfFeed);
                ohf.update(oh);
                return oh;
            }
        }
        OrdersHistory ordersHistory = new OrdersHistory();
        ordersHistory.setClient(order.getClient());
        ordersHistory.setAmount(amountOfFeed);
        ordersHistory.setFeed(order.getFeed());
        ordersHistory.setOrderDate(customCalendar.createCalendar());
        ordersHistory.setDueDate(order.getDueDate());
        ohf.create(ordersHistory);
        return ordersHistory;
    }

    public boolean isAvailableOrder(Orders order){
        CustomCalendar cc = new CustomCalendar();
        if( order == null )
            return false;
        if( cc.before(order.getOrderDate()) || cc.after(order.getDueDate()) )
            return  false;
        return true;
    }

    public boolean isAvailableOrder(Orders order, Calendar date){
        if(order == null || date == null)
            return false;
        CustomCalendar cc = new CustomCalendar(date);
        if(cc.after(order.getOrderDate()) || cc.before(order.getDueDate()))
            return  false;
        return true;
    }
}
