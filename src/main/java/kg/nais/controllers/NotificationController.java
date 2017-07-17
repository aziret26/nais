package kg.nais.controllers;

import kg.nais.facade.ClientFeedNotificationFacade;
import kg.nais.facade.NotificationSeenFacade;
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
            userFeedNotificationList = new ClientFeedNotificationFacade().findAll();
            /**
             * mark notification as seen
             */
            if(new NotificationSeenFacade().findAll().size() != userFeedNotificationList.size())
                markAsRead(userFeedNotificationList);
        }
        return userFeedNotificationList;
    }

    public void setUserFeedNotificationList(List<UserFeedNotification> userFeedNotificationList) {
        this.userFeedNotificationList = userFeedNotificationList;
    }

    public int notificationCountForCurrentUser(){
        User user = userController.getCurrentUser();
        ClientFeedNotificationFacade cfnf = new ClientFeedNotificationFacade();
        NotificationSeenFacade ncf = new NotificationSeenFacade();
        int notificationCount = cfnf.findAll().size();
        int seenCount = ncf.findByUser(user).size();
        return notificationCount - seenCount;
    }

    public void markAsRead(List<UserFeedNotification> list){
        NotificationSeenFacade nsf = new NotificationSeenFacade();
        User user = userController.getCurrentUser();
        for(UserFeedNotification ufn : list){
            System.out.printf("LOOKING: client - %d | ufn - %d\n",user.getUserId(),ufn.getClientFeedNotificationId());
            NotificationSeen tempList = nsf.findByUserAndNotification(user,ufn);
            if(tempList == null || tempList.getNotificationSeenId() == 0){
                nsf.create(new NotificationSeen(user,ufn));
            }
        }
    }
}
