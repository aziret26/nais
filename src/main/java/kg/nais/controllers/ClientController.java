package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientStatusFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.tools.Tools;

import javax.faces.bean.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static kg.nais.tools.ViewPath.*;
/**
 * Created by b-207 on 5/1/2017.
 */
@ManagedBean
@ViewScoped
public class ClientController extends GeneralController {
    private Client client = new Client();

    private List<Chick> chickList = new ArrayList<Chick>(Arrays.asList(
            new Chick()
    ));

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
        if(clientId != 0){
            chickList = new ChickFacade().findByClient(new ClientFacade().findById(clientId));
        }
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }

    public List<Client> findAllClients() {
        return new ClientFacade().findAll();
    }
    public List<Client> findAllActiveClients() {
        return new ClientFacade().findByStatus(1);
    }
    public List<Client> findAllFrozenClients() {
        return new ClientFacade().findByStatus(2);
    }
    public Client getClientById(int id){
        return new ClientFacade().findById(id);
    }

    public String createClient(){
        if(client.getName().length() == 0 ||
                client.getName().equals(" ")){
            Tools.faceMessageWarn("Пожалуйста Введите имя клиента","");
            return "";
        }
        client.setRegDate(Calendar.getInstance());
        client.setClientStatus(new ClientStatusFacade().findById(1));
        new ClientFacade().create(client);
        ChickFacade cf = new ChickFacade();
        for(Chick c : chickList){
            c.setClient(client);
            cf.create(c);
        }
        return SHOW_CLIENTS+REDIRECT;
    }

    public void addChick(){
        Chick chick = new Chick();
        printList();
        chick.setEditable(true);
        chickList.add(chick);
    }
    public void removeChik(Chick chick){
        chickList.remove(chick);
    }

    public void saveChick() {
        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        for(int i = 0; i < chickList.size();i++){
            if(chickList.get(i).getAge() == 0 || chickList.get(i).getAmount() == 0){
                toRemove.add(i);
                continue;
            }
            if (!chickList.get(i).isEditable())
                chickList.get(i).setEditable(true);
            else
                chickList.get(i).setEditable(false);
        }
        for(int i = 0;i < toRemove.size();i++){
            chickList.remove(toRemove.get(i)-i);
        }
    }

    private void printList(){
        for(int i = 0; i < chickList.size();i++){
            System.out.println("Id: "+chickList.get(i).getChickId()+
                    " | age: "+chickList.get(i).getAge());
        }
    }
}
