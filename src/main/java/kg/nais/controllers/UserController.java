package kg.nais.controllers;

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
    private User currentUser;
    private User user;
    private String activationKey;
    private int userRoleId;
    private int userId;
    @PostConstruct
    void init(){
        user = new User();
        if(userId != 0){
            user = new UserFacade().findById(userId);
        }else {
            user.setStaff(true);
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

    public User getCurrentUser() {
        if(sessionController != null && sessionController.isLogged())
            currentUser = new UserFacade().findById(sessionController.getUser().getUserId());
        return currentUser;
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

    public List<User> getAllStaffUsers(){
        return new UserFacade().findAllStaffUsers();
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
        UserFacade uf = new UserFacade();
        user.setRegDate(new Date());
        User tempUser = uf.findByEmail(user.getLogin());
        if(sessionController.getUser().getUserRole().getUserRoleId() != 1){
            Tools.faceMessageWarn("У вас нет привелгии на данную операцию.","");
            return "";
        }else
        if(userRoleId == 0){
            Tools.faceMessageWarn("Неправильно выбрана роль пользователя.","");
            return "";
        }else
        if(tempUser != null){
            Tools.faceMessageWarn("Пользователь с таким логином существует.","");
            return "";
        }else
        if(user.getPassword().length() == 0){
            Tools.faceMessageWarn("Пожалуйста введите пароль.","");
            return "";
        }else{
            user.setUserRole(new UserRoleFacade().findById(userRoleId));
        }


        if(user.getUserStatus() == null){
            user.setUserStatus(new UserStatusFacade().findById(1));
        }
        uf.createUser(user);

        return SHOW_USER_LIST + REDIRECT;
    }

    public boolean createUser(User user){
        UserFacade uf = new UserFacade();
        user.setRegDate(new Date());
        User tempUser = uf.findByEmail(user.getLogin());
        String out = "The user is created";
        if(user.isStaff()){
            if(sessionController.getUser().getUserRole().getUserRoleId() != 1){
                out = "У вас нет привелгии на данную операцию.";
                Tools.faceMessageWarn(out,"");
                System.out.println(out);
                return false;
            }else
            if(userRoleId == 0){
                out = "Неправильно выбрана роль пользователя.";
                Tools.faceMessageWarn(out, "");
                System.out.println(out);
                return false;
            }
        }else{
            if(sessionController.getUser().getUserRole().getUserRoleId() == 1 ||
                    sessionController.getUser().getUserRole().getUserRoleId() == 2){

            }else{
                out = "У вас нет привелгии на данную операцию.";
                Tools.faceMessageWarn(out,"");
                System.out.println(out);
                return false;
            }
        }
        if(tempUser != null){
            out = "Пользователь с таким логином существует.";
            Tools.faceMessageWarn("Пользователь с таким логином существует.","");
            System.out.println(out);
            return false;
        }else
        if(user.getPassword().length() == 0){
            out = "Пожалуйста введите пароль.";
            Tools.faceMessageWarn("Пожалуйста введите пароль.","");
            System.out.println(out);
            return false;
        }
        System.out.println(out);
        uf.createUser(user);
        return true;
    }

    public String logout(){
        sessionController.signout();
        return SIGN_IN + ".xhtml" + REDIRECT;
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
