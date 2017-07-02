package kg.nais.controllers;

import kg.nais.facade.FeedFacade;
import kg.nais.models.Feed;
import kg.nais.tools.Tools;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

import static kg.nais.tools.ViewPath.REDIRECT;
import static kg.nais.tools.ViewPath.SHOW_FEED;

/**
 * Created by Iskyan on 24.06.2017.
 */

@ManagedBean
@SessionScoped
public class EditFeedController extends GeneralController {

    private Feed feed;

    public Feed getFeed() {
        if (feedId != 0 && feedId != feed.getFeedId()) {
            feed = new FeedFacade().findById(feedId);
        }
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String deleteFeed(Feed feed) {
        if(userId == 0){
            Tools.faceMessageWarn("Неправильный ID пользователя","");
            return "?userId="+userId;
        }
        new FeedFacade().delete(feed);
        return SHOW_FEED+REDIRECT;
    }

    public String saveFeedChanges() {
        new FeedFacade().update(feed);
        feedId = 0;
        return SHOW_FEED + REDIRECT;
    }

}
