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

    private ChickController chickController = new ChickController();

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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


    public List<Client> findAllActiveClients() {
        return new ClientFacade().findByStatus(1);
    }

    public List<Client> findAllFrozenClients() {
        return new ClientFacade().findByStatus(2);
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
        saveChick();
        return SHOW_CLIENTS+REDIRECT;
    }

    public void addChick(){
        Chick chick = new Chick();
        chick.setEditable(true);
        chickList.add(chick);
    }

    public void removeChick(Chick chick){
        chickList.remove(chick);
    }

    public void saveChick() {
        /**
         * list where will be stored chicks to delete
         */
        ArrayList<Chick> toRemove = new ArrayList<Chick>();

        /**
         * fill toRemove list with chicks with 0 values
         * to be able to delete them later
         */
        for (Chick chick : chickList) {
            if (chick.getAge() == 0 || chick.getAmount() == 0) {
                toRemove.add(chick);
            }
        }

        /**
         * removing chicks from database
         * as they're useless info however
         */

        ChickFacade cf = new ChickFacade();
        for (Chick chick : toRemove) {
            cf.delete(chick);
            chickList.remove(chick);
        }

        /**
         * storing chicks in database
         */
        for(Chick chick: chickList){
            chick.setClient(client);
            chickController.updateChickFeed(chick);
            cf.create(chick);
        }
    }
}
