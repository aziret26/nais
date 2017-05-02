package kg.nais.facade;


import kg.nais.dao.ObjectDao;
import kg.nais.models.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class UserStatusFacade {
    private ObjectDao objectDao = new ObjectDao();

    public UserStatusFacade(){
        if(findAll().size() == 0){
            initializeUserStatuses();
        }
    }

    public void createUserStatus(UserStatus userStatus) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(userStatus);
        objectDao.commitAndCloseTransaction();
    }

    public void updateUserStatus(UserStatus userStatus) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(userStatus);
        objectDao.commitAndCloseTransaction();
    }

    public void deleteUserStatus(UserStatus userStatus) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(userStatus) ? userStatus : objectDao.getEntityManager().merge(userStatus));
        objectDao.commitAndCloseTransaction();
    }

    public List<UserStatus> findAll(){
        List<UserStatus> userStatusesList;
        try {
            objectDao.beginTransaction();
            userStatusesList = objectDao.getEntityManager().
                    createNamedQuery("UserStatus.findAll", UserStatus.class).getResultList();
        }catch (Exception ex){
            userStatusesList = new ArrayList<UserStatus>();
        }finally {
            objectDao.closeTransaction();
        }
        return userStatusesList;
    }
    public UserStatus findById(Integer id) {
        UserStatus userStatus;
        try {
            objectDao.beginTransaction();
            userStatus = objectDao.getEntityManager().find(UserStatus.class, id);
        }catch (Exception ex){
            userStatus = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return userStatus;
    }
    public UserStatus findByStatus(String status) {
        UserStatus userStatus;
        try {
            objectDao.beginTransaction();
            userStatus = objectDao.getEntityManager().createNamedQuery("UserStatus.findByStatus",UserStatus.class)
                    .setParameter("userStatus",status).getSingleResult();
        }catch (Exception ex){
            userStatus = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return userStatus;
    }

    private void initializeUserStatuses(){
        UserStatus us;
        us = new UserStatus("active");
        createUserStatus(us);
        us = new UserStatus("banned");
        createUserStatus(us);
        us = new UserStatus("frozen");
        createUserStatus(us);
        us = new UserStatus("deleted");
        createUserStatus(us);
    }
}
