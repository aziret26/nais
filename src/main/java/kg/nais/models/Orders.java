package kg.nais.models;

import javax.persistence.*;

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
            query = "SELECT o FROM Orders o WHERE o.feed = :feed")
})
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column
    private int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
