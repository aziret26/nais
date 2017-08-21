package kg.nais.models;

import kg.nais.tools.BasicFunctions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by aziret on 8/21/17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "OrdersHistory.findAll",
            query = "SELECT o FROM OrdersHistory o"),
        @NamedQuery(name = "OrdersHistory.findByClient",
                query = "SELECT o FROM OrdersHistory o WHERE o.client = :client"),
        @NamedQuery(name = "OrdersHistory.findByFeed",
                query = "SELECT o FROM OrdersHistory o WHERE o.feed = :feed"),
        @NamedQuery(name = "OrdersHistory.findByClientAndFeed",
                query = "SELECT o FROM OrdersHistory o WHERE o.client = :client AND o.feed = :feed")
})
public class OrdersHistory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ordersHistoryId;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "feedId")
    private Feed feed;

    @Column(precision = 4, scale = 2)
    private double amount;

    @Column
    private Calendar orderDate;

    @Column
    private Calendar dueDate;

    public int getOrdersHistoryId() {
        return ordersHistoryId;
    }

    public void setOrdersHistoryId(int ordersHistoryId) {
        this.ordersHistoryId = ordersHistoryId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public String getOrderDateAsString(){
        return BasicFunctions.calendarToString(orderDate);
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateAsString(){
        return BasicFunctions.calendarToString(dueDate);
    }
}
