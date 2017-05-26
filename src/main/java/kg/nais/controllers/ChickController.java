package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.models.Feed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by B-207 on 5/26/2017.
 */
@ManagedBean
@ViewScoped
public class ChickController extends GeneralController{

    private Chick chick;
    private List<Chick> chickList;
    private int chickId;

    public Chick getChick() {
        if(chickId != 0){
            chick = new ChickFacade().findById(chickId);
        }
        return chick;
    }

    public void setChick(Chick chick) {
        this.chick = chick;
    }

    public List<Chick> getChickList() {
        return chickList;
    }

    public void setChickList(List<Chick> chickList) {
        this.chickList = chickList;
    }

    public int getChickId() {
        return chickId;
    }

    public void setChickId(int chickId) {
        this.chickId = chickId;
    }

    public Chick findChickByClientFeed(Feed feed){
        return new ChickFacade().findByClientFeed(new ClientFacade().findById(clientId),feed);
    }


    public Chick findChickByClientFeed(Client client,Feed feed){
        return new ChickFacade().findByClientFeed(client,feed);
    }

}
