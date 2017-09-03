package kg.nais.service;

import kg.nais.controllers.ChickController;
import kg.nais.controllers.ChickFeedConsumeController;
import kg.nais.controllers.NotificationController;
import kg.nais.controllers.OrdersController;
import kg.nais.facade.*;
import kg.nais.models.*;
import kg.nais.models.notification.UserFeedNotification;
import kg.nais.models.notification.NotificationType;
import kg.nais.tools.customCalendar.CustomCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aziret on 7/12/17.
 */

/**
 * Class where all update methods are stored
 * that're called by Job services
 */
public class UpdateMethods {

    /**
     * method that updates Orders data
     * subtracts amount ordered of feed number of consumed feed by client's chicks
     *
     * this method should be called once a day
     */
    public void updateOrdersData(){
        System.out.println("<Updating Orders>");
        OrderFacade of = new OrderFacade();
        OrdersController ordersController = new OrdersController();
        ChickController cc = new ChickController();
        ChickFeedConsumeController fcController = new ChickFeedConsumeController();

        ServiceUpdate su = new ServiceUpdateFacade().findValid();
        CustomCalendar cs = new CustomCalendar();
        Calendar orderLastUpd = su.getOrdersLastUpd();
        int days = orderLastUpd != null ? cs.differenceInDays(orderLastUpd) : 0;

        List<Orders> ordersList = of.findAll();
        for(Orders order : ordersList){
            //continue if nothing left
            if(order == null || order.getAmount() == 0)
                continue;

            int counter = 0;
            List<Chick> chickList = cc.getChickListByClientAndFeed(order.getClient(), order.getFeed());

            for(Chick c : chickList){
                cc.decreaseAgeByDays(c,days);
            }
            do {
                double totalConsume = 0;
                for (Chick c : chickList) {
                    totalConsume += (fcController.getConsumeForAge(c.getAge()) * c.getAmount());
                }
                double res = (order.getAmount() - totalConsume / 1000.0);
                if (res < 0.5) {
                    res = 0;
                }
                order.setAmount(res);
                of.update(order);
                counter++;
            }while (counter <= days);
        }

        su.setOrdersLastUpd(Calendar.getInstance());
        new ServiceUpdateFacade().update(su);
        System.out.println("</Updated Orders>");
    }

    /**
     * method to update chicks data.
     * updates chick age.
     * It check if chick is on modified feed,
     * and if so it checks for age validness to that feed.
     *
     * this method should be called once a day
     */
    public void updateChickData(){
        System.out.println("<Updating Chicks>");
        ChickFacade cf = new ChickFacade();
        ChickController chickController = new ChickController();
        List<Chick> chickList = cf.findAll();
        for(Chick chick : chickList){
            chick.updateAge();
            chickController.updateChickFeed(chick);
            if (chick.isModFeed() &&
                    chick.getAge() > chick.getFeed().getAgeTo()){
                chick.setModFeed(false);
            }
            cf.update(chick);
        }
        ServiceUpdate su = new ServiceUpdateFacade().findValid();
        su.setChicksLastUpd(Calendar.getInstance());
        new ServiceUpdateFacade().update(su);
        System.out.println("</Updated Chicks>");
    }

    public void updateNotificationsList(){
        System.out.println("<Updating Notifications>");
        /**
         * 1. remove old notifications if they have been seen by operator
         *    1.1 do not remove if nobody hasn't seen it yet
         */
        UserFeedNotificationFacade ufnf =new UserFeedNotificationFacade();

        ArrayList<UserFeedNotification> toRemoveNotificationList = new ArrayList<>();
        NotificationController nc = new NotificationController();
        for(UserFeedNotification n : nc.getUserFeedNotificationList()){
            if(nc.isNotificationSeen(n)){
                toRemoveNotificationList.add(n);
            }
        }
        nc.removeNotifications(toRemoveNotificationList);

        /**
         * 2. Create new notifications
         *  2.1. new notification is created only if client has ran out of some specific feed,
         *      and he still needs it yet
         */
        OrdersController ordersController = new OrdersController();
        List<Client> clientList = new ClientFacade().findAllActiveClients();
        List<Feed> feedList = new FeedFacade().findAll();
        ChickController cc = new ChickController();
        OrderFacade of = new OrderFacade();
        NotificationType ntInfo = new NotificationTypeFacade().findById(1);
        for(Client client : clientList){
            for (Feed feed : feedList){
                //do not notify if client doesn't need that feed
                if(cc.getChickListByClientAndFeed(client,feed).size() == 0){
                    continue;
                }

                Orders order = of.findByClientFeed(client,feed);

                if(order == null)
                    continue;
                //do not notify if amount of feed is enough
                if(ordersController.isAvailableOrder(order)){
                    continue;
                }

                //do not notify if in database exists this notification
                //to avoid duplicates
                if(ufnf.findByClientAndFeed(client,feed) != null ) continue;
                Calendar date = order.getDueDate() != null ? order.getDueDate() : Calendar.getInstance();
                UserFeedNotification cfn =
                        new UserFeedNotification(client,feed,date,ntInfo);
                ufnf.create(cfn);
            }
        }

        ServiceUpdate su = new ServiceUpdateFacade().findValid();
        su.setNotificationsLastUpd(Calendar.getInstance());
        new ServiceUpdateFacade().update(su);
        System.out.println("</Updated Notifications>");
    }


}
