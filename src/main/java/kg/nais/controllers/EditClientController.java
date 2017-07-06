package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientStatusFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.models.ClientStatus;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kg.nais.tools.ViewPath.*;

/**
 * Created by B-207 on 5/28/2017.
 */

@ManagedBean
@SessionScoped
public class EditClientController extends GeneralController{

    private Client client = new Client();

    private List<Chick> chickList = new ArrayList<Chick>(Arrays.asList(
            new Chick()
    ));

    private boolean frozen = false;

    public Client getClient() {
        if(clientId != 0 && clientId != client.getClientId()){
            client = new ClientFacade().findById(clientId);
            chickList = new ChickFacade().findByClient(new ClientFacade().findById(client.getClientId()));
        }
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Chick> getChickList() {
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }

    public boolean isFrozen() {
        if(client.getClientStatus().getClientStatusId() == 1)
            return false;
        if(client.getClientStatus().getClientStatusId() == 2)
            return true;

        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String addChick(){
        Chick chick = new Chick();
        chick.setEditable(true);
        chickList.add(chick);
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String editClient(int clientId) {
        //chickList = new ChickFacade().findByClient(new ClientFacade().findById(client.getClientId()));
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String removeChik(Chick chick) {
        chickList.remove(chick);
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String saveClient(){
        client.setChickList(chickList);
        new ClientFacade().update(client);
        ChickFacade cf = new ChickFacade();
        for(Chick c : chickList){
            c.setClient(client);
            cf.update(c);
        }
        clientId = 0;
        return SHOW_CLIENTS + REDIRECT;
    }

    public String saveChick() {
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
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String deleteClient(Client client){
        ChickFacade cf = new ChickFacade();
        for(Chick ch : chickList){
            cf.delete(ch);
        }
        new ClientFacade().delete(client);
        return SHOW_CLIENTS+REDIRECT;
    }

    public void freeze(Client client) {
        ClientStatus sc = new ClientStatusFacade().findById(2);
        client.setClientStatus(sc);
        new ClientFacade().update(client);
        frozen = true;
    }

    public void active(Client client) {
        ClientStatus sc = new ClientStatusFacade().findById(1);
        client.setClientStatus(sc);
        new ClientFacade().update(client);
        frozen = false;
    }

}
