package kg.nais.controllers;

import kg.nais.facade.FeedFacade;
import kg.nais.models.Feed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by B-207 on 5/26/2017.
 */
@ManagedBean
@ViewScoped
public class FeedController extends GeneralController{
    private Feed feed;

    public List<Feed> getFeedList(){
        return new FeedFacade().findAll();
    }
}
