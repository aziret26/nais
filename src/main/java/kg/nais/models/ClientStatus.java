package kg.nais.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by B-207 on 5/2/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "ClientStatus.findAll",
                query = "SELECT cs FROM ClientStatus cs"),
        @NamedQuery(name = "ClientStatus.findByStatus",
                query = "SELECT cs FROM ClientStatus cs WHERE cs.status = :status")
})
public class ClientStatus implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientStatusId;

    @Column
    private String status;

    @OneToMany(mappedBy = "clientStatus")
    private List<Client> client = new ArrayList<Client>();

    public ClientStatus() {
    }

    public ClientStatus(String status) {
        this.status = status;
    }

    public int getClientStatusId() {
        return clientStatusId;
    }

    public void setClientStatusId(int clientStatusId) {
        this.clientStatusId = clientStatusId;
    }

    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
