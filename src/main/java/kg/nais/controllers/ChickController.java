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

    //    public Chick findChickByClientFeed(Feed feed){
//        return new ChickFacade().findByClientFeed(new ClientFacade().findById(clientId),feed);
//    }

//    public Chick findChickByClientFeed(Client client,Feed feed){
//        return new ChickFacade().findByClientFeed(client,feed);
//    }

    public List<Chick> findChickListByClient(Client client){
        List<Chick> chickList;
        chickList = new ChickFacade().findByClient(client);
        return chickList;
    }

    public int calculateFeed(String feedName, Client client){
        List<Chick> chicks = new ChickFacade().findByClient(client);
        List<Feed> feeds = new FeedController().getFeedList();
        Feed feed = null;
        for (Feed f : feeds) {
            if (f.getName().equals(feedName)) {
                feed = f;
            }
        }

        int amount = 0;
        for(Chick c : chicks){
            if(c.getAge() >= feed.getAgeFrom() && c.getAge() <= feed.getAgeTo()){
                amount += c.getAmount();
            }
        }
        return amount;
    }

}
