package kg.nais.models;

import javax.persistence.*;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedId;

    @Column
    private int age;

    @Column
    private Calendar registeredDate;

    @OneToMany(mappedBy = "feed")
    private List<Chick> chick = new ArrayList<Chick>();

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Calendar getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Calendar registeredDate) {
        this.registeredDate = registeredDate;
    }

    public List<Chick> getChick() {
        return chick;
    }

    public void setChick(List<Chick> chick) {
        this.chick = chick;
    }
}
