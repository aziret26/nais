package kg.nais.models.notification;

import kg.nais.models.Client;
import kg.nais.models.Feed;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aziret on 7/14/17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="UserFeedNotification.findAll",
            query = "SELECT c FROM UserFeedNotification c")
})
public class UserFeedNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userFeedNotificationId;

    @Column
    private Calendar notificationDate;

    @ManyToOne
    @JoinColumn(name="clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name="feedId")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name="notificationTypeId")
    private NotificationType notificationType;


    @OneToMany(mappedBy = "userFeedNotification")
    private List<NotificationSeen> notificationSeenList;


    public UserFeedNotification() {}

    public UserFeedNotification(Client client, Feed feed, Calendar notificationDate,
                                NotificationType notificationType) {
        this.notificationDate = notificationDate;
        this.client = client;
        this.feed = feed;
        this.notificationType = notificationType;
    }

    public int getUserFeedNotificationId() {
        return userFeedNotificationId;
    }

    public void setUserFeedNotificationId(int clientFeedNotificationId) {
        this.userFeedNotificationId = clientFeedNotificationId;
    }

    public Calendar getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Calendar notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public List<NotificationSeen> getNotificationSeenList() {
        return notificationSeenList;
    }

    public void setNotificationSeenList(List<NotificationSeen> notificationSeenList) {
        this.notificationSeenList = notificationSeenList;
    }

    public String getDate(){
        String date = "";

        date = notificationDate.get(Calendar.DAY_OF_MONTH) + " - " + getMonth(notificationDate.get(Calendar.MONTH)) +
                " - " + notificationDate.get(Calendar.YEAR);

        return date;
    }

    private String getMonth(int month){
        switch (month){
            case 1 : return "Январь";
            case 2 : return "Февраль";
            case 3 : return "Март";
            case 4 : return "Апрель";
            case 5 : return "Май";
            case 6 : return "Июнь";
            case 7 : return "Июль";
            case 8 : return "Август";
            case 9 : return "Сентябрь";
            case 10 : return "Октябрь";
            case 11 : return "Ноябрь";
            case 12 : return "Декабрь";
        }
        return "";
    }
}
