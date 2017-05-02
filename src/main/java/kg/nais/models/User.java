package kg.nais.models;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * Created by timur on 13-Apr-17.
 */

@FacesValidator(value = "passwordValidator")
@Entity
@NamedQueries({
        @NamedQuery(name="User.findAll",
                query="SELECT u FROM User u"),
        @NamedQuery(name="User.findByPrimaryKey",
                query="SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name="User.findByLogin",
                query="SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name="User.findByLoginPass",
                query="SELECT u FROM User u WHERE u.login = :login AND u.password = :password"),
        @NamedQuery(name="User.searchByLogin",
                query = "SELECT u FROM User u WHERE u.login LIKE :login")
})
public class User implements Serializable,Validator{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column
    private String fname;
    @Column
    private String lname;
    @Column
    private String password;
    @Column
    private String login;
    @Column
    private Date regDate;

    @ManyToOne
    @JoinColumn(name = "userRoleId")
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "userStatusId")
    private UserStatus userStatus;

    public int getUserId() {
        return userId;
    }
    public User(){}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void validate(FacesContext facesContext, UIComponent component, Object value)
            throws ValidatorException{
        String password1 = (String) value;

        UIInput uiPasswordConfirm = (UIInput) component.getAttributes().get("confirmPassword");
        String confirmPassword = uiPasswordConfirm.getSubmittedValue().toString();

        if(password1 == null || password1.isEmpty() || confirmPassword == null) {
            return;
        }
        if(!password1.equals(confirmPassword)){
            uiPasswordConfirm.setValid(false);
            //Tools.faceMessageWarn(confirmPassword + " Passwords don't match " + password1);
            FacesMessage msg = new FacesMessage(confirmPassword + " Passwords don't match " + password1);
            throw new ValidatorException(msg);
        }
    }

}
