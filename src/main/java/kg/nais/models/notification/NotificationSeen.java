package kg.nais.models.notification;

import kg.nais.models.User;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by aziret on 7/16/17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "NotificationSeen.findAll",
            query = "SELECT n FROM NotificationSeen n"),
        @NamedQuery(name = "NotificationSeen.findByUser",
            query = "SELECT n FROM NotificationSeen n WHERE n.user = :user"),
        @NamedQuery(name = "NotificationSeen.findByNotification",
            query = "SELECT n FROM NotificationSeen n WHERE n.userFeedNotification = :clientFeedNotification"),
        @NamedQuery(name = "NotificationSeen.findByUserAndNotification",
            query = "SELECT n FROM NotificationSeen n WHERE n.user = :user AND n.userFeedNotification = :clientFeedNotification")
})
public class NotificationSeen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationSeenId;

    @Column
    private Calendar seenDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "userFeedNotificationId")
    private UserFeedNotification userFeedNotification;

    public NotificationSeen() {
    }

    public NotificationSeen(User user, UserFeedNotification userFeedNotification) {
        this.seenDate = Calendar.getInstance();
        this.user = user;
        this.userFeedNotification = userFeedNotification;
    }

    public int getNotificationSeenId() {
        return notificationSeenId;
    }

    public void setNotificationSeenId(int notificationSeenId) {
        this.notificationSeenId = notificationSeenId;
    }

    public Calendar getSeenDate() {
        return seenDate;
    }

    public void setSeenDate(Calendar seenDate) {
        this.seenDate = seenDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserFeedNotification getUserFeedNotification() {
        return userFeedNotification;
    }

    public void setUserFeedNotification(UserFeedNotification userFeedNotification) {
        this.userFeedNotification = userFeedNotification;
    }
}
