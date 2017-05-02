package kg.nais.controllers;

import kg.nais.facade.UserFacade;
import kg.nais.facade.UserRoleFacade;
import kg.nais.facade.UserStatusFacade;
import kg.nais.models.User;
import kg.nais.models.UserStatus;
import kg.nais.tools.Tools;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by timur on 13-Apr-17.
 */
@ManagedBean
@ViewScoped
public class UserController {
    private User user;
    private String activationKey;

    @PostConstruct
    void init(){
        user = new User();
    }

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public List<User> getAllUsers(){
        return new UserFacade().findAll();
    }

    public String signin(){
        String login = user.getLogin();
        String password=user.getPassword();

        UserFacade uf = new UserFacade();
        User tempUser = uf.findByEmailPass(login,password);

        if(tempUser == null){
            Tools.faceMessageWarn("Wrong email or password.","Please, check if data are correct.");
            return "";
        }
        userSession.setUser(tempUser);
        userSession.signin();
        return "/index?faces-redirect=true";
    }

    public String signout(){
        userSession.signout();
        return "/index";
    }

    public String signup(){
        user.setRegDate(new Date());
        if(user.getUserRole() == null){
            user.setUserRole(new UserRoleFacade().findById(3));
        }
        if(user.getUserStatus() == null){
            user.setUserStatus(new UserStatusFacade().findById(4));
        }
        UserFacade uf=new UserFacade();
        uf.createUser(user);

        return "signin?faces-redirect=true";
    }

    public List<User> searchByEmailTop5(String email){
        if(email.length() > 3){
            return new ArrayList<User>();
        }
        List<User> userList;
        userList = new UserFacade().searchByEmailBy5(email);
        return userList;
    }
}
