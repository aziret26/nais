package kg.nais.models;

import javax.persistence.*;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Feed.findAll",
                query = "SELECT f FROM Feed f"),
        @NamedQuery(name = "Feed.findByName",
                query = "SELECT f FROM Client f WHERE f.name = :name")
})
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedId;

    @Column
    private String name;

    @Column
    private int ageFrom,ageTo;

    @OneToMany(mappedBy = "feed")
    private List<Orders> orderList;

    @OneToMany(mappedBy = "feed")
    private List<Chick> chickList;

    public Feed() {}

    public Feed(String name, int ageFrom, int ageTo) {
        this.name = name;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public List<Chick> getChickList() {
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }
}
