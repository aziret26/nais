package kg.nais.tools;

/**
 * Created by B-207 on 5/24/2017.
 */
public class ViewPath {
    public static final String INDEX = "/index.xhtml?",
            PAGES_FOLDER = "/pages/",
            AUTH_FOOLDER = PAGES_FOLDER + "auth/",
            SIGN_IN = AUTH_FOOLDER + "signin.xhtml?",

    USER_FOLDER = PAGES_FOLDER + "user/",
            USER_CREATE = USER_FOLDER + "userCreate?",
            SHOW_USER_LIST = USER_FOLDER + "showUserList?",

    ADMIN_FOLDER = PAGES_FOLDER + "admin/",

        FEED_FOLDER = ADMIN_FOLDER + "feed/",
            ADD_FEED = FEED_FOLDER + "addFeed?",
            EDIT_FEED = FEED_FOLDER + "editFeed?",
            SHOW_FEED = FEED_FOLDER + "showFeed?",

        ADMIN_CLIENTS_FOLDER = ADMIN_FOLDER + "clients/",
                ADMIN_SHOW_CLIENTS = ADMIN_CLIENTS_FOLDER + "showClients?",

    SDO_FOLDER = PAGES_FOLDER + "sdo/",

        OPERATOR_CLIENT_FOLDER = SDO_FOLDER + "clients/",
            OPERATOR_ADD_CLIENT = OPERATOR_CLIENT_FOLDER + "addClient?",
            OPERATOR_EDIT_CLIENT = OPERATOR_CLIENT_FOLDER + "editClient?",
            OPERATOR_SHOW_CLIENTS = OPERATOR_CLIENT_FOLDER + "showClients?",


    PM_FOLDER =  PAGES_FOLDER + "pm/",

        PM_ORDER_FOLDER = PM_FOLDER + "orders/",
            PM_ADD_ORDER = PM_ORDER_FOLDER + "addOrder?",
            PM_VIEW_ORDERS = PM_ORDER_FOLDER + "viewOrders?",

        PM_DEMAND_FOLDER = PM_FOLDER + "demand/",
            PM_DEMANDS = PM_DEMAND_FOLDER + "demands?",
            PM_CLIENT_DEMANDS = PM_DEMAND_FOLDER + "clientDemands?",

    CLIENT_FOLDER = PAGES_FOLDER + "client/",

        ADD_SURVEY_DATA = CLIENT_FOLDER + "addSurveyData?",
        SHOW_USER_SURVEY_DATA = CLIENT_FOLDER + "showUserSurveyData?",
        SHOW_SURVEY_DATA = CLIENT_FOLDER + "showSurveyData?";
    public static final String REDIRECT = "faces-redirect=true&";
}
