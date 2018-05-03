package kg.nais.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "ClientSurvey.findAll",
                query = "SELECT cs FROM ClientSurvey cs"),
        @NamedQuery(name = "ClientSurvey.findByClient",
                query = "SELECT cs FROM ClientSurvey  cs where cs.client = :client ORDER BY cs.surveyDate asc")
})
public class ClientSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientSurveyId;

    @Column
    private Calendar surveyDate;

    @Column
    private Double eggLaying;

    @Column
    private Double eggAvgWeight;

    @Column
    private Double chickenAvgWeight;

    @Column
    private Double feedConsumption;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public int getClientSurveyId() {
        return clientSurveyId;
    }

    public void setClientSurveyId(int clientSurveyId) {
        this.clientSurveyId = clientSurveyId;
    }

    public Calendar getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Calendar surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Double getEggLaying() {
        return eggLaying;
    }

    public void setEggLaying(Double eggLaying) {
        this.eggLaying = eggLaying;
    }

    public Double getEggAvgWeight() {
        return eggAvgWeight;
    }

    public void setEggAvgWeight(Double eggAvgWeight) {
        this.eggAvgWeight = eggAvgWeight;
    }

    public Double getChickenAvgWeight() {
        return chickenAvgWeight;
    }

    public void setChickenAvgWeight(Double chickenAvgWeight) {
        this.chickenAvgWeight = chickenAvgWeight;
    }

    public Double getFeedConsumption() {
        return feedConsumption;
    }

    public void setFeedConsumption(Double feedConsumption) {
        this.feedConsumption = feedConsumption;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
