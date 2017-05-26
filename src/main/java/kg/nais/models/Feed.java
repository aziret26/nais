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
    private List<Chick> chick = new ArrayList<Chick>();

    public Feed() {
    }

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

    public List<Chick> getChick() {
        return chick;
    }

    public void setChick(List<Chick> chick) {
        this.chick = chick;
    }
}
