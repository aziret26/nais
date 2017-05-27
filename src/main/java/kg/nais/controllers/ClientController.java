package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientStatusFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;

import javax.faces.bean.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static kg.nais.tools.ViewPath.*;
/**
 * Created by b-207 on 5/1/2017.
 */
@ManagedBean
@SessionScoped
public class ClientController extends GeneralController{
    private Client client = new Client();

    private List<Chick> chickList = new ArrayList<Chick>();

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;

    public Client getClient() {
        if(clientId != 0){
            client = new ClientFacade().findById(clientId);
        }
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(kg.nais.controllers.SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public List<Chick> getChickList() {
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }

    public List<Client> findAllClients(){
        return new ClientFacade().findAll();
    }
    public Client getClientById(int id){
        return new ClientFacade().findById(id);
    }

    public String createClient(){
        client.setRegDate(Calendar.getInstance());
        client.setClientStatus(new ClientStatusFacade().findById(1));
        new ClientFacade().create(client);
        return SHOW_CLIENTS+REDIRECT;
    }
    public String deleteClient(Client client){
        new ClientFacade().delete(client);
        return "";
    }
    public String editClient(int clientId){
        return EDIT_CLIENT+REDIRECT+"clientId="+clientId;
    }
    public String saveClient(){
        client.setChickList(chickList);
        new ClientFacade().update(client);
        return SHOW_CLIENTS+REDIRECT;
    }
    public String deleteClient(int clientId){

        return SHOW_CLIENTS+REDIRECT;
    }

    public void addChick(){
        printList();
        chickList.add(new Chick());

    }
    public void removeChik(Chick chick){
        chickList.remove(chick);
    }

    private void printList(){
        for(int i = 0; i < chickList.size();i++){
            System.out.println("Id: "+chickList.get(i).getChickId()+
                    " | age: "+chickList.get(i).getAge());
        }
    }
}
