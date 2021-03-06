package kg.nais.controllers;

import kg.nais.facade.*;
import kg.nais.models.*;
import kg.nais.tools.Tools;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    private ClientFacade clientFacade = new ClientFacade();
    private List<Chick> chickList = new ArrayList<Chick>(Arrays.asList(new Chick())),
                toDeleteList = new ArrayList<Chick>();

    private boolean frozen = false;

    private ChickController chickController = new ChickController();

    private String newPswd = "";

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @PostConstruct
    public void init(){
        if(sessionController.getUser().isClient())
            setClient(clientFacade.findById(sessionController.getUser().getClient().getClientId()));
    }

    private List<Feed> feedList = new FeedFacade().findAll();

    public Client getClient() {
        if(client == null || clientId != 0 && clientId != client.getClientId()){
            client = new ClientFacade().findById(clientId);
            chickList = new ChickFacade().findByClient(new ClientFacade().findById(client.getClientId()));
        }
        if(sessionController.getUser().isClient()){
            setClient(clientFacade.findById(sessionController.getUser().getClient().getClientId()));
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

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public String getNewPswd() {
        return newPswd;
    }

    public void setNewPswd(String newPswd) {
        this.newPswd = newPswd;
    }

    public String addChick(){
        Chick chick = new Chick();
        chick.setEditable(true);
        chick.setClient(client);
        chickList.add(chick);
        return OPERATOR_EDIT_CLIENT +REDIRECT + "clientId=" + clientId;
    }

    public String editClient(int clientId) {
        //chickList = new ChickFacade().findByClient(new ClientFacade().findById(client.getClientId()));
        return OPERATOR_EDIT_CLIENT +REDIRECT + "clientId=" + clientId;
    }

    public String removeChick(Chick chick) {
        toDeleteList.add(chick);
        chickList.remove(chick);
        return OPERATOR_EDIT_CLIENT +REDIRECT + "clientId=" + clientId;
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
        UserController userController = new UserController();
        userController.setSessionController(sessionController);

        if(userController.getCurrentUser().getUserRole().getUserRoleId() == 1)
            return ADMIN_SHOW_CLIENTS + REDIRECT;
        return OPERATOR_SHOW_CLIENTS + REDIRECT;
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
            chick.updateDob();
            chickController.updateChickFeed(chick);
            cf.update(chick);
        }
        new OrdersController().updateClientsOrderDueDate(client);
    }

    public String changeEditableState(){

        for(Chick chick: chickList) {
            if (!chick.isEditable())
                chick.setEditable(true);
            else
                chick.setEditable(false);
        }
        return OPERATOR_EDIT_CLIENT +REDIRECT + "clientId=" + clientId;
    }

    public String deleteClient(Client client){
        new NotificationController().deleteNotifications(client);
        new ChickController().deleteChicks(client);
        new OrdersController().deleteOrders(client);
        new ClientFacade().delete(client);
        return OPERATOR_SHOW_CLIENTS +REDIRECT;
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

    public void saveClientData(){
        User user = client.getUser();
        if(newPswd.length() > 3){
            user.setPassword(newPswd);
        }
        clientFacade.update(client);
        new UserFacade().updateUser(user);
        Tools.faceMessageInfo("All data successfully update","");
    }
}
