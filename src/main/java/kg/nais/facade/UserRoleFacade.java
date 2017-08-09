package kg.nais.facade;


import kg.nais.dao.ObjectDao;
import kg.nais.models.User;
import kg.nais.models.UserRole;

import java.util.List;

public class UserRoleFacade {
    private ObjectDao objectDao = new ObjectDao();

    public UserRoleFacade(){
        List<UserRole> userRoleList = findAll();
        if(userRoleList == null || userRoleList.size() == 0){
            initializeUserRoles();
        }
    }

    public void createUserRole(UserRole userRole) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(userRole);
        objectDao.commitAndCloseTransaction();
    }

    public void updateUserRole(UserRole userRole) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(userRole);
        objectDao.commitAndCloseTransaction();
    }

    public void deleteUserRole(UserRole userRole) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(userRole) ? userRole : objectDao.getEntityManager().merge(userRole));
        objectDao.commitAndCloseTransaction();
    }

    public List<UserRole> findAll(){
        List<UserRole> userRoleList;
        try {
            objectDao.beginTransaction();
            userRoleList = objectDao.getEntityManager().createNamedQuery("UserRole.findAll",UserRole.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            userRoleList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return userRoleList;
    }

    public List<UserRole> findAllSimpleUsers(){
        List<UserRole> userRoleList;
        try {
            objectDao.beginTransaction();
            userRoleList = objectDao.getEntityManager().
                    createNamedQuery("UserRole.findAllSimpleUsers", UserRole.class).getResultList();
            objectDao.closeTransaction();
        }catch (Exception ex){
            userRoleList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return userRoleList;
    }

    public UserRole findById(Integer id) {
        UserRole userRole;
        try {
            objectDao.beginTransaction();
            userRole = objectDao.getEntityManager().find(UserRole.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            userRole = null;
            objectDao.rollbackIfTransactionActive();
        }
        return userRole;
    }

    private void initializeUserRoles(){
        UserRole ur = new UserRole("administrator");
        createUserRole(ur);
        ur = new UserRole("production manager");
        createUserRole(ur);
        ur = new UserRole("operator");
        createUserRole(ur);
    }

}
