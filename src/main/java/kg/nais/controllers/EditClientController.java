package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientStatusFacade;
import kg.nais.facade.FeedFacade;
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

    private List<Chick> chickList = new ArrayList<Chick>(Arrays.asList(new Chick())),
                toDeleteList = new ArrayList<Chick>();

    private boolean frozen = false;

    public Client getClient() {
        if(client == null || clientId != 0 && clientId != client.getClientId()){
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
        chick.setClient(client);
        chickList.add(chick);
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String editClient(int clientId) {
        //chickList = new ChickFacade().findByClient(new ClientFacade().findById(client.getClientId()));
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String removeChick(Chick chick) {
        toDeleteList.add(chick);
        chickList.remove(chick);
        return EDIT_CLIENT+REDIRECT + "clientId = " + clientId;
    }

    public String saveClient(){
        saveChick();
        new ClientFacade().update(client);
        /**
         * reinitializing client data
         */
        toDeleteList = new ArrayList<Chick>();
        chickList = new ArrayList<Chick>(Arrays.asList(new Chick()));
        client = new Client();
        return SHOW_CLIENTS + REDIRECT;
    }

    private void saveChick() {
        /**
         * fill toRemove list with chicks with 0 values
         * to be able to delete them later
         */
        for (Chick chick : chickList) {
            if (chick.getAge() == 0 || chick.getAmount() == 0) {
                toDeleteList.add(chick);
            }
        }

        /**
         * removing chicks from database
         * as they're useless info however
         */
        ChickFacade cf = new ChickFacade();
        for (Chick chick : toDeleteList) {
            cf.delete(chick);
            chickList.remove(chick);
        }

        /**
         * updating rest of the chicks in database
         */
        for(Chick chick: chickList){
            if(chick.isModFeed() && chick.getSelectedFeedId() == 0)
                chick.setModFeed(false);

            if(chick.isModFeed())
                chick.setFeed(new FeedFacade().findById(chick.getSelectedFeedId()));
            else
                chick.setFeed(null);
            cf.update(chick);
        }
    }

    public String changeEditableState(){

        for(Chick chick: chickList) {
            if (!chick.isEditable())
                chick.setEditable(true);
            else
                chick.setEditable(false);
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
