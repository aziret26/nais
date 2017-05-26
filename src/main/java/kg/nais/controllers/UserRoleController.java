package kg.nais.controllers;

import kg.nais.facade.UserRoleFacade;
import kg.nais.models.UserRole;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by B-207 on 5/24/2017.
 */
@ManagedBean
@ViewScoped
public class UserRoleController {
    private UserRole userRole;

    private List<UserRole> simpleUserRoleList = new ArrayList<UserRole>(),
            fullUserRoleList = new ArrayList<UserRole>();


    //    public void setRoles(List<SelectItem> roles) {
//        this.roles = roles;
//    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<UserRole> getSimpleUserRoleList() {
        if(simpleUserRoleList.size() == 0){
            simpleUserRoleList = new UserRoleFacade().findAllSimpleUsers();
        }
        return simpleUserRoleList;
    }

    public void setSimpleUserRoleList(List<UserRole> simpleUserRoleList) {
        this.simpleUserRoleList = simpleUserRoleList;
    }

    public List<UserRole> getFullUserRoleList() {
        if(fullUserRoleList.size() == 0){
            fullUserRoleList = new UserRoleFacade().findAll();
        }
        return fullUserRoleList;
    }

    public void setFullUserRoleList(List<UserRole> fullUserRoleList) {
        this.fullUserRoleList = fullUserRoleList;
    }
}
