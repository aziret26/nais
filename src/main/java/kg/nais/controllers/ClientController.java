package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientStatusFacade;
import kg.nais.models.Client;
import kg.nais.models.ClientStatus;
import kg.nais.tools.ViewPath;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static kg.nais.tools.ViewPath.*;
/**
 * Created by b-207 on 5/1/2017.
 */
@ManagedBean
@ViewScoped
public class ClientController {
    private Client client = new Client();
    private int clientId;

    @ManagedProperty(value="#{sessionController}")
    private SessionController userSession;

    public Client getClient() {
        if(clientId != 0){
            client = new ClientFacade().findById(clientId);
        }
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public SessionController getUserSession() {
        return userSession;
    }

    public void setUserSession(kg.nais.controllers.SessionController userSession) {
        this.userSession = userSession;
    }


    public String createClient(){
        client.setRegDate(Calendar.getInstance());
        client.setClientStatus(new ClientStatusFacade().findById(1));
        new ClientFacade().create(client);
        return SHOW_CLIENTS+REDIRECT;
    }

    public List<Client> findAllClients(){
        List<Client> clientList = new ClientFacade().findAll();
        for(Client c : clientList){
            System.out.println(c.getClientId());
        }
        return clientList;
    }

    public String deleteClient(Client client){
        //clientSession.setCleint(client);
        new ClientFacade().delete(client);
        return "";
    }
    public Client getClientById(int id){
        return new ClientFacade().findById(id);
    }
    public String editClient(int clientId){
        return EDIT_CLIENT+REDIRECT+"clientId="+clientId;
    }

    public String deleteClient(int clientId){

        return SHOW_CLIENTS+REDIRECT;
    }
}
