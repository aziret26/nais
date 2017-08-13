package kg.nais.controllers;

import kg.nais.facade.UserFacade;
import kg.nais.facade.UserFeedNotificationFacade;
import kg.nais.facade.NotificationSeenFacade;
import kg.nais.models.Client;
import kg.nais.models.User;
import kg.nais.models.notification.NotificationSeen;
import kg.nais.models.notification.UserFeedNotification;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by aziret on 7/15/17.
 */
@ManagedBean
@ViewScoped
public class NotificationController {

    @ManagedProperty(value = "#{userController}")
    private UserController userController;

    private UserFeedNotification userFeedNotification;

    private List<UserFeedNotification> userFeedNotificationList;

    private List<NotificationSeen> notificationSeenList;

    public NotificationController() {
        initObject();
    }

    private void initObject(){
        userFeedNotificationList = new UserFeedNotificationFacade().findAll();
        notificationSeenList = new NotificationSeenFacade().findAll();
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public UserFeedNotification getUserFeedNotification() {
        return userFeedNotification;
    }

    public void setUserFeedNotification(UserFeedNotification userFeedNotification) {
        this.userFeedNotification = userFeedNotification;
    }

    public List<UserFeedNotification> getUserFeedNotificationList() {
        if(userFeedNotificationList == null || userFeedNotificationList.size() == 0) {
            userFeedNotificationList = new UserFeedNotificationFacade().findAll();
        }
        return userFeedNotificationList;
    }
    public List<UserFeedNotification> getUserFeedNotificationListForCurrentUser(){
        User user = userController.getCurrentUser();
        if(user == null){
            return userFeedNotificationList;
        }
        getUserFeedNotificationList();
        List<NotificationSeen> nsList = new NotificationSeenFacade().findByUser(user);
        if(nsList == null || nsList.size() != userFeedNotificationList.size())
            markAsRead(userFeedNotificationList);

        return userFeedNotificationList;
    }

    public void setUserFeedNotificationList(List<UserFeedNotification> userFeedNotificationList) {
        this.userFeedNotificationList = userFeedNotificationList;
    }

    public int notificationCountForCurrentUser(){
        User user = userController.getCurrentUser();
        UserFeedNotificationFacade cfnf = new UserFeedNotificationFacade();
        NotificationSeenFacade ncf = new NotificationSeenFacade();
        int notificationCount = cfnf.findAll().size();
        List<NotificationSeen> ns = ncf.findByUser(user);
        int seenCount = ns != null ? ns.size() : 0;
        return notificationCount - seenCount;
    }

    public void markAsRead(List<UserFeedNotification> list){
        NotificationSeenFacade nsf = new NotificationSeenFacade();
        User user = userController.getCurrentUser();
        for(UserFeedNotification ufn : list){
            NotificationSeen tempList = nsf.findByUserAndNotification(user,ufn);
            if(tempList == null || tempList.getNotificationSeenId() == 0){
                nsf.create(new NotificationSeen(user,ufn));
            }
        }
    }

    public void removeNotification(UserFeedNotification ufn){
        _removeNotification(ufn);
        initObject();
    }
    private void _removeNotification(UserFeedNotification ufn){
        NotificationSeenFacade nsf = new NotificationSeenFacade();
        List<NotificationSeen> nsList = nsf.findByNotification(ufn);
        if(nsList != null){
            nsList.forEach(nsf::delete);
        }
        new UserFeedNotificationFacade().delete(ufn);
    }

    public void deleteNotifications(Client client){
        UserFeedNotificationFacade noteFacade = new UserFeedNotificationFacade();
        List<UserFeedNotification> ufnList = noteFacade.findByClient(client);
        if(ufnList == null)
            return;

        //delete seen data that are related to notification
        NotificationSeenFacade nsf = new NotificationSeenFacade();
        for(UserFeedNotification ufn : ufnList) {
            List<NotificationSeen> seenList = nsf.findByNotification(ufn);
            seenList.forEach(nsf::delete);
        }

        ufnList.forEach(noteFacade::delete);
    }

    public boolean isNotificationSeen(UserFeedNotification ufn){
        if(ufn == null || notificationSeenList == null || notificationSeenList.size() == 0){
            return false;
        }
        for(NotificationSeen ns : notificationSeenList){
            if(ns.getUserFeedNotification().getUserFeedNotificationId() == ufn.getUserFeedNotificationId())
                return true;
        }
        return false;
    }


    /**
     * Removes notifications from UserFeedNotification table
     * and related to it date from NotificationSeen table
     * @param ufnList list of UserFeedNotification to be deleted
     */
    public void removeNotifications(List<UserFeedNotification> ufnList){
        _removeNotifications(ufnList);
        initObject();
    }

    private void _removeNotifications(List<UserFeedNotification> ufnList){
        if(ufnList == null || ufnList.size() == 0)
            return;

        NotificationSeenFacade nsf = new NotificationSeenFacade();
        UserFeedNotificationFacade ufnf = new UserFeedNotificationFacade();

        for(UserFeedNotification ufn : ufnList) {
            nsf.findByNotification(ufn).forEach(new NotificationSeenFacade()::delete);
        }

        ufnList.forEach(ufnf::delete);

    }
}
