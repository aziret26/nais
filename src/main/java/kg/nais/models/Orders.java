package kg.nais.models;

import kg.nais.tools.BasicFunctions;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by aziret on 7/10/17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Orders.findAll",
            query = "SELECT o FROM Orders o"),
        @NamedQuery(name = "Orders.findByClient",
            query = "SELECT o FROM Orders o WHERE o.client = :client"),
        @NamedQuery(name = "Orders.findByFeed",
            query = "SELECT o FROM Orders o WHERE o.feed = :feed"),
        @NamedQuery(name = "Orders.findByClientFeed",
                query = "SELECT o FROM Orders o WHERE o.client = :client AND o.feed = :feed")
})
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column
    private double amount;

    @Column
    private Calendar dueDate;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "feedId")
    private Feed feed;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount < 0)
            amount = 0;
        this.amount = amount;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        if(dueDate == null || !BasicFunctions.isSameDate(this.dueDate,dueDate));
            this.dueDate = dueDate;
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
}
