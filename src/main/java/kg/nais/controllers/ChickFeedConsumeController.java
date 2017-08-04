package kg.nais.controllers;

import kg.nais.facade.ChickFeedConsumeFacade;
import kg.nais.models.Chick;
import kg.nais.models.ChickFeedConsume;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by aziret on 7/11/17.
 */
@ManagedBean
@ViewScoped
public class ChickFeedConsumeController {
    private ChickFeedConsume chickFeedConsume;

    private List<ChickFeedConsume> chickFeedConsumeList;

    public ChickFeedConsumeController() {
        chickFeedConsumeList = new ChickFeedConsumeFacade().findAll();
    }

    public ChickFeedConsume getChickFeedConsume() {
        return chickFeedConsume;
    }

    public void setChickFeedConsume(ChickFeedConsume chickFeedConsume) {
        this.chickFeedConsume = chickFeedConsume;
    }

    public List<ChickFeedConsume> getChickFeedConsumeList() {
        return chickFeedConsumeList;
    }

    public void setChickFeedConsumeList(List<ChickFeedConsume> chickFeedConsumeList) {
        this.chickFeedConsumeList = chickFeedConsumeList;
    }

    public double getConsumeForAge(int age){
        for (ChickFeedConsume cf : chickFeedConsumeList){
            if(cf.getAge() == age)
                return cf.getConsume();
        }
        return 0;
    }

    public double getConsumeAmount(Chick chick){
        double result = 0;
        for(ChickFeedConsume cfc : chickFeedConsumeList){
            if(cfc.getAge() == chick.getAge()){
                result = chick.getAmount()*cfc.getConsume()*0.001;
            }
        }
        return result;
    }
}
