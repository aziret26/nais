package kg.nais.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by B-207 on 5/26/2017.
 */
@ManagedBean
@ViewScoped
public class GeneralController {
    protected int clientId,feedId,chickId,userId;

    public int getClientId() {
        System.out.println("getting clientId: "+clientId);
        return clientId;
    }

    public void setClientId(int clientId) {
        System.out.println("setting clientId: "+clientId);
        this.clientId = clientId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public int getChickId() {
        return chickId;
    }

    public void setChickId(int chickId) {
        this.chickId = chickId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
