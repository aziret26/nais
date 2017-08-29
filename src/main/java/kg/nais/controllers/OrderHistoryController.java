package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.OrderFacade;
import kg.nais.facade.OrdersHistoryFacade;
import kg.nais.models.Client;
import kg.nais.models.Orders;
import kg.nais.models.OrdersHistory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziret on 8/21/17.
 */
@ManagedBean
@ViewScoped
public class OrderHistoryController extends GeneralController {
    private OrdersHistory ordersHistory;

    private List<OrdersHistory> ordersHistoryList;

    private OrdersHistoryFacade ohf;

    private Client client;

    public OrderHistoryController() {
        init();
    }

    @PostConstruct
    public void init(){
        ohf = new OrdersHistoryFacade();
    }

    @Override
    public void setClientId(int clientId) {
        if(clientId == 0)
            return;
        if(client == null || client != null && (client.getClientId() != 0 || client.getClientId() != clientId)){
            client = new ClientFacade().findById(clientId);
            setOrdersHistoryList(ohf.findByClient(client));
        }
        super.setClientId(clientId);
    }

    public OrdersHistory getOrdersHistory() {
        return ordersHistory;
    }

    public void setOrdersHistory(OrdersHistory ordersHistory) {
        this.ordersHistory = ordersHistory;
    }

    public List<OrdersHistory> getOrdersHistoryList() {
        return ordersHistoryList;
    }

    public void setOrdersHistoryList(List<OrdersHistory> ordersHistoryList) {
        this.ordersHistoryList = ordersHistoryList;
    }

    public void removeOrderHistory(int orderHistoryId){
        OrdersHistory oh = new OrdersHistoryFacade().findById(orderHistoryId);
        removeOrderHistory(oh);
    }

    public void removeOrderHistory(OrdersHistory orderHistory){
        OrdersHistoryFacade ohf = new OrdersHistoryFacade();

        if(isLastOrder(orderHistory)){
            OrderFacade of = new OrderFacade();
            Orders orders = of.findByClientFeed(orderHistory.getClient(),orderHistory.getFeed());
            of.delete(orders);
        }

        ohf.delete(orderHistory);
    }

    public boolean isLastOrder(OrdersHistory orderHistory){
        List<OrdersHistory> historyList =
                ohf.findByClientAndFeed(orderHistory.getClient(),orderHistory.getFeed());
        if(historyList.size() > 0){
            OrdersHistory lastOrder = historyList.get(0);
            for(OrdersHistory oh : historyList){
                if(oh.getOrderDate().after(lastOrder.getOrderDate())){
                    lastOrder = oh;
                }
            }
            if(lastOrder.getOrdersHistoryId() == orderHistory.getOrdersHistoryId()){
                return true;
            }
            return false;
        }
        return true;
    }
}
