package kg.nais.models;

import javax.persistence.*;

/**
 * Created by aziret on 7/11/17.
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "ChickFeedConsume.findAll",
        query = "SELECT c FROM ChickFeedConsume c")
})
public class ChickFeedConsume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chickFeedConsumeId;

    @Column(unique = true,nullable = false)
    private int age;

    @Column
    private double consume;

    public ChickFeedConsume() {}

    public ChickFeedConsume(int age, int consume) {
        this.age = age;
        this.consume = consume;
    }

    public int getChickFeedConsumeId() {
        return chickFeedConsumeId;
    }

    public void setChickFeedConsumeId(int chickFeedConsumeId) {
        this.chickFeedConsumeId = chickFeedConsumeId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getConsume() {
        return consume;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }
}
