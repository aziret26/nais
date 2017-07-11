package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.FeedFacade;
import kg.nais.facade.OrderFacade;
import kg.nais.models.Client;
import kg.nais.models.Feed;
import kg.nais.models.Orders;
import kg.nais.tools.Tools;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
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

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<Orders> getOrderList() {
        if(orderList == null){
            orderList = new OrderFacade().findAll();
        }
        return orderList;
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

    public String addOrder(){
        return ADD_ORDER + REDIRECT + "clientId="+clientId;
    }

    public String addOrder(int id){
        return ADD_ORDER + REDIRECT + "clientId="+id;
    }

    public String createOrder(){
        if(client == null || client.getClientId() == 0 ){
            Tools.faceMessageWarn("Неверный ID клиента.","");
            return INDEX;
        }
        if(selectedFeedId == 0){
            Tools.faceMessageWarn("Выберите вид корма","");
            return ADD_ORDER + REDIRECT + "clientId="+clientId;
        }
        order.setFeed(new FeedFacade().findById(selectedFeedId));
        if(order == null){
            System.out.printf("order: null\n");
        }
        order.setClient(client);
        if(order.getFeed() != null)
            System.out.println("selected feed: "+order.getFeed().getName() );
        else
            System.out.println("selected feed: null");
        OrderFacade of = new OrderFacade();
        of.create(order);
        return VIEW_ORDERS;
    }

    @Override
    public void setClientId(int clientId) {
        if(client == null || client.getClientId() != clientId)
            client = new ClientFacade().findById(clientId);
        super.setClientId(clientId);
    }
}
