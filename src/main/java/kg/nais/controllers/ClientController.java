package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientStatusFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.models.User;
import kg.nais.tools.Tools;

import javax.annotation.PostConstruct;
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
    private User user;
    private List<Chick> chickList = new ArrayList<Chick>(Arrays.asList(
            new Chick()
    ));

    private ChickController chickController = new ChickController();

    private List<Client> activeClients, frozenClients;

    private ClientFacade clientFacade = new ClientFacade();

    public ClientController() {
        initObject();
    }

    @PostConstruct
    public void initObject(){
        ClientFacade cf = new ClientFacade();

        activeClients = findAllActiveClients();

        frozenClients = findAllFrozenClients() ;
        user = new User();
        user.setClient(true);
    }

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;

    @Override
    public void setClientId(int clientId) {
        if(clientId == 0) return;

        Client tempClient = clientFacade.findById(clientId);

        if(tempClient == null) return;

        this.client = tempClient;

        super.setClientId(clientId);
    }

    public Client getClient () {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Client> getActiveClients() {
        return activeClients;
    }

    public void setActiveClients(List<Client> activeClients) {
        this.activeClients = activeClients;
    }

    public List<Client> getFrozenClients() {
        return frozenClients;
    }

    public void setFrozenClients(List<Client> frozenClients) {
        this.frozenClients = frozenClients;
    }

    public List<Client> findAllActiveClients() {
        return clientFacade.findAllActiveClients();
    }

    public List<Client> findAllFrozenClients() {
        return clientFacade.findAllFrozenClients();
    }

    public List<Client> searchAllActiveByName(){
        activeClients = clientFacade.searchAllActiveByName(searchNameActive);
        searchPostMsg(activeClients,searchNameActive);
        return activeClients;
    }

    public List<Client> searchAllFrozenByName(){
        frozenClients = clientFacade.searchAllFrozenByName(searchNameFrozen);
        searchPostMsg(frozenClients,searchNameFrozen);
        return frozenClients;
    }

    private void searchPostMsg(List list,String text){
        String str = "По запросу '";
        if(list == null || list.size() == 0){
            Tools.faceMessageInfo(str+text+"' ничего не найдено","");
        }else{
            if(!text.equals(""))
                Tools.faceMessageInfo(str+text+"' найдено","");
        }
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

        UserController userController = new UserController();
        userController.setSessionController(sessionController);

        if(userController.getCurrentUser().getUserRole().getUserRoleId() == 1)
            return ADMIN_SHOW_CLIENTS + REDIRECT;
        return OPERATOR_SHOW_CLIENTS +REDIRECT;
    }

    public String registerUser(){
        UserController userController = new UserController();
        userController.setSessionController(sessionController);

        boolean isUserCreated = userController.createUser(user);
        if(isUserCreated){

            client.setRegDate(Calendar.getInstance());
            client.setClientStatus(new ClientStatusFacade().findById(1));
            client.setUser(user);

            new ClientFacade().create(client);

            Tools.faceMessageInfo("User is successfully created","");
            System.out.println("Client is successfully created");
        }
        return OPERATOR_EDIT_CLIENT +REDIRECT + "clientId=" + client.getClientId();
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
            chick.updateDob();
            chick.setClient(client);
            chickController.updateChickFeed(chick);
            cf.create(chick);
        }
    }
}
