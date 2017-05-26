package kg.nais.controllers;

import com.sun.org.apache.regexp.internal.RE;
import kg.nais.facade.UserFacade;
import kg.nais.facade.UserRoleFacade;
import kg.nais.facade.UserStatusFacade;
import kg.nais.models.User;
import kg.nais.models.UserRole;
import kg.nais.tools.Tools;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

import static kg.nais.tools.ViewPath.*;
/**
 * Created by timur on 13-Apr-17.
 */
@ManagedBean
@ViewScoped
public class UserController extends GeneralController{
    private User user;
    private String activationKey;
    private int userRoleId;
    private int userId;
    @PostConstruct
    void init(){
        user = new User();
        if(userId != 0){
            user = new UserFacade().findById(userId);
        }
    }

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public User getUser() {
        if(user.getUserId() != userId && userId != 0){
            user = new UserFacade().findById(userId);
        }
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

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public List<User> getAllUsers(){
        return new UserFacade().findAll();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        sessionController.setUser(tempUser);
        sessionController.signin();
        return "/index?faces-redirect=true";
    }

    public String createUser(){
        user.setRegDate(new Date());
        if(sessionController.getUser().getUserRole().getUserRoleId() != 1){
            Tools.faceMessageWarn("У вас нет привелгии на данную операцию.","");
            return "";
        }
        if(userRoleId == 0){
            Tools.faceMessageWarn("Неправильно выбрана роль пользователя.","");
            return "";

        }else{
            user.setUserRole(new UserRoleFacade().findById(userRoleId));
        }
        if(user.getUserStatus() == null){
            user.setUserStatus(new UserStatusFacade().findById(1));
        }
        new UserFacade().createUser(user);
        return "/index";
    }

    public String signout(){
        sessionController.signout();
        return "/index";
    }

    public String signup(){
        user.setRegDate(new Date());
        if(sessionController.getUser().getUserRole().getUserRoleId() != 1){
            Tools.faceMessageWarn("У вас нет привелгии на данную операцию.","");
            return "";
        }
        if(userRoleId == 0){
            Tools.faceMessageWarn("Неправильно выбрана роль пользователя.","");
            return "";

        }else{
            user.setUserRole(new UserRoleFacade().findById(userRoleId));
        }
        if(user.getUserStatus() == null){
            user.setUserStatus(new UserStatusFacade().findById(1));
        }
        UserFacade uf = new UserFacade();
        uf.createUser(user);

        return INDEX+REDIRECT;
    }

    public String saveUserChanges(){
        System.out.println("save user: "+user.getLogin());
        System.out.println("UserRole: "+user.getUserRole().getUserRole());
        String pswd = new UserFacade().findById(user.getUserId()).getPassword();
        UserRole ur = new UserRoleFacade().findById(user.getUserRole().getUserRoleId());
        if(user.getPassword() == null || user.getPassword().equals(""))
            user.setPassword(pswd);
        if(user.getUserRole().getUserRole() == null)
            user.setUserRole(ur);
        if(userRoleId == 0){
            user.setUserRole(ur);
        }else{
            user.setUserRole(new UserRoleFacade().findById(userRoleId));
        }
        System.out.println("UserRole: "+user.getUserRole().getUserRole());

        new UserFacade().updateUser(user);
        System.out.println(user);
        return SHOW_USER_LIST + REDIRECT;
    }

    public String deleteUser(){
        System.out.println("delete user: "+user.getLogin());
        if(userId == 0){
            Tools.faceMessageWarn("Неправильный ID пользователя","");
            return "?userId="+userId;
        }
        new UserFacade().deleteUser(user);
        return SHOW_USER_LIST+REDIRECT;
    }

    public List<UserRole> findSimpleUserRoleList(){
        System.out.println("getting user roles:");
        List<UserRole> userRoleList= new UserRoleFacade().findAllSimpleUsers();
        for (UserRole ur:userRoleList) {
            System.out.println(ur.getUserRoleId()+" : "+ur);
        }
        return userRoleList;
    }

    public void initializeUser(int userId){
        System.out.println("userID: "+userId);
        user = new UserFacade().findById(userId);
        user.setPassword("");
        System.out.println("User ID: "+user.getUserId());
        System.out.println("Login: "+user.getLogin());
        System.out.println("Last name: "+user.getLname());
        System.out.println("First name: "+user.getFname());
        System.out.println("User Role: "+user.getUserRole().getUserRole());
    }
}
