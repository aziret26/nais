package kg.nais.controllers;

/**
 * Created by B-207 on 5/26/2017.
 */
public class GeneralController {
    protected int clientId = 0,feedId=0,chickId=0,userId=0,ordersHistoryId=0;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
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

    public int getOrdersHistoryId() {
        return ordersHistoryId;
    }

    public void setOrdersHistoryId(int ordersHistoryId) {
        this.ordersHistoryId = ordersHistoryId;
    }
}
