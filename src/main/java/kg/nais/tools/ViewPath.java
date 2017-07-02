package kg.nais.tools;

/**
 * Created by B-207 on 5/24/2017.
 */
public class ViewPath {
    public static final String INDEX = "/index",
    PAGES_FOLDER = "/pages/",
    AUTH_FOOLDER = PAGES_FOLDER + "auth/",
    SIGN_IN = AUTH_FOOLDER + "signin",
    ADMIN_FOLDER = PAGES_FOLDER + "admin/",
    PM_FOLDER =  PAGES_FOLDER + "pm/",
    SDO_FOLDER = PAGES_FOLDER + "sdo/",
    CLIENT_FOLDER = SDO_FOLDER + "client/",
    ADD_CLIENT = CLIENT_FOLDER + "addClient",
    EDIT_CLIENT = CLIENT_FOLDER + "editClient",
    SHOW_CLIENTS = CLIENT_FOLDER + "showClients",
    USER_FOLDER = PAGES_FOLDER + "user/",
    USER_CREATE = USER_FOLDER + "userCreate",
    SHOW_USER_LIST = USER_FOLDER + "showUserList",
    FEED_FOLDER = ADMIN_FOLDER + "feed/",
    ADD_FEED = FEED_FOLDER + "addFeed",
    EDIT_FEED = FEED_FOLDER + "editFeed",
    SHOW_FEED = FEED_FOLDER + "showFeed";


    public static final String REDIRECT = "?faces-redirect=true";
}
