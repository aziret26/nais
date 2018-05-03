package kg.nais.controllers;

import kg.nais.facade.ClientSurveyFacade;
import kg.nais.models.Client;
import kg.nais.models.ClientSurvey;
import kg.nais.tools.customCalendar.CustomCalendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import kg.nais.tools.ViewPath;

@ManagedBean
@ViewScoped
public class ClientSurveyController extends GeneralController{
    private ClientSurvey clientSurvey;

    private CustomCalendar surveyDate;

    private ClientSurveyFacade clientSurveyFacade;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    public ClientSurveyController(){
        init();
    }

    @PostConstruct
    public void init(){
        clientSurvey = new ClientSurvey();
        surveyDate = new CustomCalendar();
        clientSurveyFacade = new ClientSurveyFacade();
    }

    public ClientSurvey getClientSurvey() {
        return clientSurvey;
    }

    public void setClientSurvey(ClientSurvey clientSurvey) {
        this.clientSurvey = clientSurvey;
    }

    public CustomCalendar getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(CustomCalendar surveyDate) {
        this.surveyDate = surveyDate;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public String addSurvey(){
        String redirect = "";

        if (sessionController.getUser().isClient()){
            redirect = ViewPath.SHOW_USER_SURVEY_DATA +
                    "clientId="+sessionController.getUser().getClient().getClientId() + ViewPath.REDIRECT;
        } else
        if (sessionController.getUser().isStaff()) {
            redirect = ViewPath.SHOW_SURVEY_DATA + ViewPath.REDIRECT;
        } else{
            return ViewPath.INDEX + ViewPath.REDIRECT;
        }

        if(clientId == 0 && sessionController.getUser().isClient()){
            clientId = sessionController.getUser().getClient().getClientId();
        }
        // redirect if no client couldn't be found
        if(clientId == 0 ) return redirect;

        Client client = new Client();
        client.setClientId(clientId);
        clientSurvey.setClient(client);

        clientSurvey.setSurveyDate(surveyDate.createCalendar());

        clientSurveyFacade.create(clientSurvey);

        return redirect;
    }
}
