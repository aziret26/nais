package kg.nais.service;

import kg.nais.controllers.ChickController;
import kg.nais.controllers.ChickFeedConsumeController;
import kg.nais.facade.*;
import kg.nais.models.*;
import kg.nais.models.notification.UserFeedNotification;
import kg.nais.models.notification.NotificationType;

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
        OrderFacade of = new OrderFacade();
        ChickController cc = new ChickController();
        ChickFeedConsumeController fcController = new ChickFeedConsumeController();

        List<Orders> ordersList = of.findAll();
        for(Orders order : ordersList){
            if(order.getAmount() == 0)
                continue;

            List<Chick> chickList = cc.getChickListByClientAndFeed(order.getClient(),order.getFeed());
            double totalConsume = 0;
            for(Chick c : chickList){
                totalConsume += (fcController.getConsumeForAge(c.getAge())*c.getAmount());
            }
            double res = (order.getAmount()-totalConsume/1000.0);
            if(res < 0.5){
                res = 0;
            }
            order.setAmount(res);
            of.update(order);
        }
        ServiceUpdate su = new ServiceUpdateFacade().findValid();
        su.setOrdersLastUpd(Calendar.getInstance());
        new ServiceUpdateFacade().update(su);
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
        ChickFacade cf = new ChickFacade();
        ChickController chickController = new ChickController();
        List<Chick> chickList = cf.findAll();

        for(Chick chick : chickList){
            chickController.increaseChicksAgeByDay(chick);
            if (chick.isModFeed() &&
                    chick.getAge() > chick.getFeed().getAgeTo()){
                chick.setModFeed(false);
            }
            cf.update(chick);
        }
        ServiceUpdate su = new ServiceUpdateFacade().findValid();
        su.setChicksLastUpd(Calendar.getInstance());
        new ServiceUpdateFacade().update(su);
    }

    public void updateNotificationsList(){
        /**
         * 1. remove old notifications if they have been seen by operator
         */
        UserFeedNotificationFacade cfnf =new UserFeedNotificationFacade();
        List<UserFeedNotification> cfnList = cfnf.findAll();
        Calendar today = Calendar.getInstance();

        if(today.get(Calendar.DAY_OF_WEEK) != 0) {
            List<UserFeedNotification> toRemoveNotificationList = new ArrayList<>();
            for(UserFeedNotification c : cfnList){
                if(Math.abs(c.getNotificationDate().get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR)) >= 1){
                    toRemoveNotificationList.add(c);
                }
            }
            for(UserFeedNotification ufn : toRemoveNotificationList){
                new NotificationSeenFacade().findByNotification(ufn).forEach(new NotificationSeenFacade()::delete);
            }
            toRemoveNotificationList.forEach(cfnf::delete);
        }
        /**
         * 2. Create new notifications
         *  2.1. new notification is created only if client has ran out of some specific feed,
         *      and he still needs it yet
         */
        List<Client> clientList = new ClientFacade().findAll();
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

                //do not notify if amount of feed is enough
                if(order != null && order.getAmount() > 0.5){
                    continue;
                }

                //do not notify if in database exists this notification
                //to avoid duplicates
//                if(cfnf.findByClientAndFeed(client,feed) != null ) continue;

                UserFeedNotification cfn =
                        new UserFeedNotification(client,feed,Calendar.getInstance(),ntInfo);
                cfnf.create(cfn);
            }
        }

        ServiceUpdate su = new ServiceUpdateFacade().findValid();
        su.setNotificationsLastUpd(Calendar.getInstance());
        new ServiceUpdateFacade().update(su);
    }
}
