package kg.nais.models.notification;

import javax.persistence.*;
import java.util.List;

/**
 * Created by aziret on 7/14/17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="NotificationType.findAll",
            query = "SELECT n FROM NotificationType n")
})
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationTypeId;

    @Column
    private String title;

    @OneToMany(mappedBy = "notificationType")
    private List<UserFeedNotification> userFeedNotificationList;

    public NotificationType() {
    }

    public NotificationType(String title) {
        this.title = title;
    }


    public int getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(int notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserFeedNotification> getUserFeedNotificationList() {
        return userFeedNotificationList;
    }

    public void setUserFeedNotificationList(List<UserFeedNotification> userFeedNotificationList) {
        this.userFeedNotificationList = userFeedNotificationList;
    }
}
