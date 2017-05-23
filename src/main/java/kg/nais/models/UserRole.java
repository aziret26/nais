package kg.nais.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timur on 13-Apr-17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="UserRole.findAll",
        query = "SELECT ur FROM UserRole ur"),
        @NamedQuery(name = "UserRole.findByRole",
        query = "SELECT ur FROM UserRole ur WHERE ur.userRole = :userRole")
})
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userRoleId;
    @Column
    private String userRole;

    @OneToMany(mappedBy = "userRole")
    private List<User> userList=new ArrayList<User>();

    public UserRole(){}

    public UserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return userRole;
    }

}
