package kg.nais.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Chick.findAll",
                query = "SELECT c FROM Chick c"),
        @NamedQuery(name = "Chick.findByAgeRange",
                query = "SELECT c FROM Chick c WHERE c.age >= :ageFrom AND c.age <= :ageTo"),
//        @NamedQuery(name = "Chick.findByFeed",
//                query = "SELECT c FROM Chick c WHERE c.feed = :feed"),
//        @NamedQuery(name = "Chick.findByClientFeed",
//                query = "SELECT c FROM Chick c WHERE c.client = :client AND c.feed = :feed"),
        @NamedQuery(name="Chick.findByClient",
                query = "SELECT c FROM Chick c where c.client = :client")
})
public class Chick implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chickId;

    @Column
    private int age;

    @Column
    private int amount;

    @Column
    private Calendar dob;

    @ManyToOne
    @JoinColumn(name="clienId")
    private Client client;

    @Transient
    private boolean editable = true;

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
        dob = calculateDob(age);
        this.age = age;
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

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    private Calendar calculateDob(int age){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR,-(age/52));
        now.add(Calendar.WEEK_OF_YEAR,-(age%52));
        return now;
    }
}
