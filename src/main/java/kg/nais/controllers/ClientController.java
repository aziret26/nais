package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.models.Client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Calendar;
import java.util.List;

/**
 * Created by b-207 on 5/1/2017.
 */
@ManagedBean
@ViewScoped
public class ClientController {
    private Client client = new Client();

    @ManagedProperty(value="#{userSession}")
    private UserSession userSession;

    @ManagedProperty(value = "#{clientSession}")
    private ClientSession clientSession;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(kg.nais.controllers.UserSession userSession) {
        this.userSession = userSession;
    }

    public void setClientSession(ClientSession clientSession) {
        this.clientSession = clientSession;
    }

    public String createClient(){
        client.setRegDate(Calendar.getInstance());
        new ClientFacade().create(client);
        return "/index?jsf-redirect=true";
    }

    public List<Client> findAllClients(){
        return new ClientFacade().findAll();
    }

    public String delete(Client client){
        //clientSession.setCleint(client);
        new ClientFacade().delete(client);
        return "";
    }
}
