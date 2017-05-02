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


}
