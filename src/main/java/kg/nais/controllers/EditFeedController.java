package kg.nais.controllers;

import kg.nais.facade.FeedFacade;
import kg.nais.facade.UserFacade;
import kg.nais.models.Feed;
import kg.nais.tools.Tools;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

import static kg.nais.tools.ViewPath.INDEX;
import static kg.nais.tools.ViewPath.REDIRECT;
import static kg.nais.tools.ViewPath.SHOW_FEED;

/**
 * Created by Iskyan on 24.06.2017.
 */

@ManagedBean
@SessionScoped
public class EditFeedController extends GeneralController {

    private Feed feed;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    public Feed getFeed() {
        if (feed == null || feedId != 0 && feedId != feed.getFeedId()) {
            feed = new FeedFacade().findById(feedId);
        }
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public String deleteFeed() {
        if(sessionController.getUser() == null){
            Tools.faceMessageWarn("Пожалуйста, авторизуйтесь.","");
            return INDEX+REDIRECT;
        }
        if(new UserFacade().findById(sessionController.getUser().getUserId()).getUserRole().getUserRoleId() != 1){
            Tools.faceMessageWarn("У вас нету прав, на данное действие.","");
            return INDEX+REDIRECT;
        }
        if(feed != null) {
            new FeedFacade().delete(feed);
        }
        return SHOW_FEED+REDIRECT;
    }

    public String saveFeedChanges() {
        new FeedFacade().update(feed);
        feed.setFeedId(0);
        return SHOW_FEED + REDIRECT;
    }

}
