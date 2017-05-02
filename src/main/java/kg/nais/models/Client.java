package kg.nais.models;

import javax.persistence.*;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Client.findAll",
                query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.findByClientStatus",
                query = "SELECT c FROM Client c WHERE c.clientStatus = :clientStatus")
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Calendar regDate;

    @OneToMany(mappedBy = "client")
    private List<Chick> chickList = new ArrayList<Chick>();

    @ManyToOne
    @JoinColumn(name="clientStatusId")
    private ClientStatus clientStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getRegDate() {
        return regDate;
    }

    public void setRegDate(Calendar regDate) {
        this.regDate = regDate;
    }

    public List<Chick> getChickList() {
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }
}
