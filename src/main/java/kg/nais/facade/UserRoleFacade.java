package kg.nais.facade;


import kg.nais.dao.ObjectDao;
import kg.nais.models.UserRole;

import java.util.List;

public class UserRoleFacade {
    private ObjectDao objectDao = new ObjectDao();

    public UserRoleFacade(){
        if(findAll().size() == 0){
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
        objectDao.beginTransaction();
        List<UserRole> userRoleList = objectDao.getEntityManager().
                createNamedQuery("UserRole.findAll",UserRole.class).getResultList();
        objectDao.closeTransaction();
        return userRoleList;
    }
    public UserRole findById(Integer id) {
        objectDao.beginTransaction();
        UserRole userRole = objectDao.getEntityManager().find(UserRole.class, id);
        objectDao.commitAndCloseTransaction();
        return userRole;
    }

    private void initializeUserRoles(){
        UserRole ur = new UserRole("administrator");
        createUserRole(ur);
        ur = new UserRole("production manager");
        createUserRole(ur);
        ur = new UserRole("service department operator");
        createUserRole(ur);
    }

}
