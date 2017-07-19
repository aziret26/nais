package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.OrderFacade;
import kg.nais.models.Client;
import kg.nais.models.Orders;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aziret on 7/19/17.
 */
public class OrdersControllerTest {
    OrdersController oc = new OrdersController();
    @Before
    public void setUp() throws Exception {
//        Client c = new ClientFacade().findById(3);
        oc.getOrder().setAmount(25);
        oc.setClientId(2);
        oc.setSelectedFeed(8);
    }

    @Test
    public void createOrder() throws Exception {
        oc.createOrder();
        Orders o = new OrderFacade().findByClientFeed(oc.getOrder().getClient(),oc.getOrder().getFeed());
        int a = (int) o.getAmount();
        assertEquals(2,a);
    }

}