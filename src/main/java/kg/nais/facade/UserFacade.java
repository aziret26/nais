package kg.nais.facade;


import kg.nais.dao.ObjectDao;
import kg.nais.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFacade {

    private ObjectDao objectDao = new ObjectDao();

    public UserFacade() {
        if(findAll().size() == 0){
            initAdmin();
        }
    }

    public void createUser(User user) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(user);
        objectDao.commitAndCloseTransaction();
    }

    public void updateUser(User user) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(user);
        objectDao.commitAndCloseTransaction();
    }

    public void deleteUser(User user) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(user) ? user : objectDao.getEntityManager().merge(user));
        objectDao.commitAndCloseTransaction();
    }

    public User findById(Integer id) {
        User user;
        try {
            objectDao.beginTransaction();
            user = objectDao.getEntityManager().find(User.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            user = null;
            objectDao.rollbackIfTransactionActive();
        }
        return user;
    }

    public List<User> findAll(){
        List<User> userList;
        try {
            objectDao.beginTransaction();
            userList = objectDao.getEntityManager().createNamedQuery("User.findAll",User.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            userList = new ArrayList<User>();
            objectDao.rollbackIfTransactionActive();
        }
        return userList;
    }

    public User findByEmail(String email){
        User user;
        try {
            objectDao.beginTransaction();
            user = objectDao.getEntityManager().createNamedQuery("User.findByLogin", User.class)
                    .setParameter("login", email).getSingleResult();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            user = null;
            objectDao.rollbackIfTransactionActive();
        }
        return user;
    }

    public User findByEmailPass(String login,String pass){
        User user;
        try {
            objectDao.beginTransaction();
            user =objectDao.getEntityManager().createNamedQuery("User.findByLoginPass", User.class)
                    .setParameter("login", login).setParameter("password", pass).getSingleResult();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            user = null;
            objectDao.rollbackIfTransactionActive();
        }
        return user;
    }

    public List<User> searchByEmailBy5(String login){

        List<User> userList;
        try {
            objectDao.beginTransaction();
            userList = objectDao.getEntityManager().createNamedQuery("User.searchByLogin",User.class).
                    setParameter("login","%"+login+"%").setMaxResults(5).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            userList = new ArrayList<User>();
            objectDao.rollbackIfTransactionActive();
        }
        return userList;
    }

    public List<User> searchByEmail(String login){
        List<User> userList;
        try {
            objectDao.beginTransaction();
            userList = objectDao.getEntityManager().createNamedQuery("User.searchByLogin",User.class).
                    setParameter("login","%"+login+"%").getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            userList = new ArrayList<User>();
            objectDao.rollbackIfTransactionActive();
        }
        return userList;
    }

    private void initAdmin(){
        User user = new User();
        user.setFname("Admin");
        user.setLname("Admin");
        user.setLogin("admin");
        user.setPassword("admin");
        user.setRegDate(new Date());
        user.setUserRole(new UserRoleFacade().findById(1));
        user.setUserStatus(new UserStatusFacade().findById(1));
        createUser(user);
    }
}
