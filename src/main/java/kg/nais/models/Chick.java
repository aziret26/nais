package kg.nais.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
public class Chick implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chickId;

    @Column
    private int age;

    @Column
    private Calendar dob;

    @Column
    private Date regDate;

    @ManyToOne
    @JoinColumn(name = "feedId")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name="clienId")
    private Client client;

    public int getChickId() {
        return chickId;
    }

    public void setChickId(int chickId) {
        this.chickId = chickId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}
