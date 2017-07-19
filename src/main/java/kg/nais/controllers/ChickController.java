package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.ClientFacade;
import kg.nais.facade.FeedFacade;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.models.Feed;
import kg.nais.tools.Tools;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

import static kg.nais.tools.ViewPath.REDIRECT;
import static kg.nais.tools.ViewPath.SHOW_CLIENTS;

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

    public List<Chick> findChickListByClient(Client client){
        List<Chick> chickList;
        chickList = new ChickFacade().findByClient(client);
        return chickList;
    }

    /**
     * calculate amount of chick of client consuming specific chick
     * @param feedName
     * @param client
     * @return
     */
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
            if(c.isModFeed() && c.getFeed().getName().equals(feedName)){
                amount += c.getAmount();
                continue;
            }
            if(!c.isModFeed() && c.getAge() >= feed.getAgeFrom() && c.getAge() <= feed.getAgeTo()){
                amount += c.getAmount();
            }
        }
        return amount;
    }

    public int calculateFeedForChick(int feedId, Client client){
        List<Chick> chicks = new ChickFacade().findByClient(client);
        List<Feed> feeds = new FeedController().getFeedList();
        Feed feed = null;
        for (Feed f : feeds) {
            if (f.getFeedId() == feedId) {
                feed = f;
            }
        }

        int amount = 0;
        for(Chick c : chicks){
            if(c.isModFeed() && c.getFeed().getFeedId() == feedId){
                amount += c.getAmount();
                continue;
            }
            if(!c.isModFeed() && c.getAge() >= feed.getAgeFrom() && c.getAge() <= feed.getAgeTo()){
                amount += c.getAmount();
            }
        }
        return amount;
    }

    public List<Chick> getChickListByClientAndFeed(Client client, Feed feed){
        return new ChickFacade().findByClientAndFeed(client,feed);
    }

    public List<Chick> getChicksForFeedBelow(Feed feed){
        return new ChickFacade().findForFeedBelow(feed);
    }

    public List<Chick> getChicksByClientForFeedBelow(Client client,Feed feed){
        return new ChickFacade().findChicksByClientForFeedBelow(client,feed);
    }

    public void increaseChicksAgeByDay(Chick chick){
        chick.increaseAgeByDay();
    }

    public List<Chick> getChicksForFeed(List<Chick> chicks,Feed feed){
        List<Chick> resultList = new ArrayList<Chick>();
        for (Chick c : chicks){
            if(c.getFeed().getFeedId() == feed.getFeedId())
                resultList.add(c);
        }
        return  resultList;
    }

    public List<Chick> removeChicksAboveFeed(List<Chick> list,Feed feed){
        List<Integer> toRm = new ArrayList<>();
        for(int i = 0; i< list.size();i++){
            if (list.get(i).getAge() > feed.getAgeTo()){
                toRm.add(i);
            }
        }
        for(int i = 0;i < toRm.size();i++){
//            System.out.printf("REMOVING: CHICK-%d | AGE-%d\n",list.get(i).getChickId(),list.get(i).getAge());
            list.remove(i);
        }

        return list;
    }

    public void deleteChicks(Client client){
        List<Chick> list = new ChickFacade().findByClient(client);
        ChickFacade cf = new ChickFacade();
        list.forEach(cf::delete);
    }
}
