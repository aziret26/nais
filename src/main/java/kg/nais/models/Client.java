package kg.nais.models;

import kg.nais.models.notification.UserFeedNotification;

import javax.persistence.*;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Client.findAll",
                query = "SELECT c FROM Client c ORDER BY c.name ASC"),
        @NamedQuery(name = "Client.findByClientStatus",
                query = "SELECT c FROM Client c WHERE c.clientStatus.id = :clientStatusId ORDER BY c.name ASC"),
        @NamedQuery(name = "Client.findAllActive",
                query = "SELECT c FROM Client c WHERE c.clientStatus.id = 1 ORDER BY c.name ASC"),
        @NamedQuery(name = "Client.findAllFrozen",
                query = "SELECT c FROM Client c WHERE c.clientStatus.id = 2 ORDER BY c.name ASC"),
        @NamedQuery(name = "Client.searchAllActiveByName",
                query = "SELECT c FROM Client c WHERE c.clientStatus.id = 1 AND" +
                        " c.name LIKE :name ORDER BY c.name ASC"),
        @NamedQuery(name = "Client.searchAllFrozenByName",
                query = "SELECT c FROM Client c WHERE c.clientStatus.id = 2 AND" +
                        " c.name LIKE :name ORDER BY c.name ASC")

})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @Column
    private String name;

    @Column
    private Calendar regDate;

    @Column
    private Integer telephone1;

    @Column
    private Integer telephone2;

    @OneToMany(mappedBy = "client")
    private List<Chick> chickList = new ArrayList<Chick>();

    @ManyToOne
    @JoinColumn(name="clientStatusId")
    private ClientStatus clientStatus;

    @OneToMany(mappedBy = "client")
    private List<Orders> orderList;

    @OneToMany(mappedBy = "client")
    private List<UserFeedNotification> userFeedNotificationList;

    @OneToMany(mappedBy = "client")
    private List<OrdersHistory> ordersHistoryList;

    @OneToOne
    @JoinColumn(name="userId")
    private User user;

    @OneToMany(mappedBy = "client")
    private List<ClientSurvey> clientSurveyList = new ArrayList<ClientSurvey>();

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getRegDate() {
        return regDate;
    }

    public void setRegDate(Calendar regDate) {
        this.regDate = regDate;
    }

    public List<Chick> getChickList() {
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }

    public Integer getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(Integer telephone1) {
        this.telephone1 = telephone1;
    }

    public Integer getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(Integer telephone2) {
        this.telephone2 = telephone2;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public List<UserFeedNotification> getUserFeedNotificationList() {
        return userFeedNotificationList;
    }

    public void setUserFeedNotificationList(List<UserFeedNotification> userFeedNotificationList) {
        this.userFeedNotificationList = userFeedNotificationList;
    }

    public List<OrdersHistory> getOrdersHistoryList() {
        return ordersHistoryList;
    }

    public void setOrdersHistoryList(List<OrdersHistory> ordersHistoryList) {
        this.ordersHistoryList = ordersHistoryList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ClientSurvey> getClientSurveyList() {
        return clientSurveyList;
    }

    public void setClientSurveyList(List<ClientSurvey> clientSurveyList) {
        this.clientSurveyList = clientSurveyList;
    }
}
