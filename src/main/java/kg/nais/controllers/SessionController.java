package kg.nais.controllers;

import kg.nais.models.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by timur on 14-Apr-17.
 */
@ManagedBean
@SessionScoped
public class SessionController {
    private User user = new User();
    private boolean logged = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void signin(){
        /*
        * called from UserController
        * */
        logged = true;
    }
    public void signout(){
        /*
        * called from UserController
        * */
        user = new User();
        logged = false;
    }
}
