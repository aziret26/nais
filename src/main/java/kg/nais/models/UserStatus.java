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
        @NamedQuery(name="UserStatus.findAll",
                query = "SELECT us FROM UserStatus us"),
        @NamedQuery(name = "UserStatus.findByStatus",
                query = "SELECT us FROM UserStatus us WHERE us.userStatus = :userStatus")
})
public class UserStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userStatusId;
    @Column
    private String userStatus;

    @OneToMany(mappedBy = "userStatus")
    private List<User> userList=new ArrayList<User>();

    public UserStatus() {
    }

    public UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(int userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
