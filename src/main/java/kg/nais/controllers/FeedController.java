package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.FeedFacade;
import kg.nais.facade.UserFacade;
import kg.nais.facade.UserRoleFacade;
import kg.nais.models.Chick;
import kg.nais.models.Feed;
import kg.nais.models.UserRole;
import kg.nais.tools.Tools;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

import static kg.nais.tools.ViewPath.*;

/**
 * Created by B-207 on 5/26/2017.
 */
@ManagedBean
@ViewScoped
public class FeedController extends GeneralController {

    private Feed feed = new Feed(),nullFeed = null;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Feed> getFeedList(){
        return new FeedFacade().findAll();
    }

    public Feed getNullFeed() {
        return nullFeed;
    }

    public void setNullFeed(Feed nullFeed) {
        this.nullFeed = nullFeed;
    }

    public String createFeed() {
        if(feed.getName().length() == 0 ||
                feed.getName().equals(" ")){
            Tools.faceMessageWarn("Пожалуйста Введите имя клиента","");
            return "";
        }
        new FeedFacade().create(feed);
        ChickFacade cf = new ChickFacade();
        return SHOW_FEED+REDIRECT;
    }

    public String editFeed(int feedId) {
        return EDIT_FEED+REDIRECT + "feedId=" + feedId;
    }

    public Feed getFeedForAge(int age){
        List<Feed> feedList = new FeedFacade().findAll();
        if(age < 0) return null;
        for (Feed f : feedList){
            if(f.getAgeFrom() <= age && f.getAgeTo() >= age){
                return f;
            }
        }
        return null;
    }
}
