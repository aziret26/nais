package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kg.nais.tools.ViewPath.EDIT_CLIENT;
import static kg.nais.tools.ViewPath.REDIRECT;
import static kg.nais.tools.ViewPath.SHOW_CLIENTS;

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
                //chickList.remove(chickList.g);
                toRemove.add(i);
                continue;
            }
            chickList.get(i).setEditable(false);
        }
        for(int i = 0;i < toRemove.size();i++){
            chickList.remove(toRemove.get(i)-i);
        }
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

}
