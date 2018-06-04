package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.UserFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.tools.Tools;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.tools.Tool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
public class ProfileController extends GeneralController{
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    private Client client = new Client();

    private ClientFacade clientFacade = new ClientFacade();

    private String oldPswd,newPswd,confirmNewPswd;

    @PostConstruct
    public void init(){
        oldPswd = "";
        newPswd = "";
        confirmNewPswd = "";
        if(sessionController.getUser().isClient()){
            setClientId(sessionController.getUser().getClient().getClientId());
        }
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @Override
    public void setClientId(int clientId) {
        super.setClientId(clientId);
        setClient(clientFacade.findById(clientId));
    }

    public String getOldPswd() {
        return oldPswd;
    }

    public void setOldPswd(String oldPswd) {
        this.oldPswd = oldPswd;
    }

    public String getNewPswd() {
        return newPswd;
    }

    public void setNewPswd(String newPswd) {
        this.newPswd = newPswd;
    }

    public String getConfirmNewPswd() {
        return confirmNewPswd;
    }

    public void setConfirmNewPswd(String confirmNewPswd) {
        this.confirmNewPswd = confirmNewPswd;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientFacade getClientFacade() {
        return clientFacade;
    }

    public void setClientFacade(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    public void saveProfileData(){
        try {
            clientFacade.update(client);
            Tools.faceMessageInfo("All the data successfully updated","");
        }catch (Exception ex){
            ex.printStackTrace();
            Tools.faceMessageError("Oops! Seems like something happened on the server.","");
        }
    }

    public void updatePassword(){
        if(oldPswd.length() < 3 || newPswd.length() < 3){
            Tools.faceMessageError("Please fill in all the fields","");
            return;
        }
        if(oldPswd.equals(client.getUser().getPassword())){
            client.getUser().setPassword(newPswd);
            new UserFacade().updateUser(client.getUser());
            Tools.faceMessageInfo("Password is successfully updated.","");
        }else{
            Tools.faceMessageError("Old password doesn't match.","");
        }

    }

}
