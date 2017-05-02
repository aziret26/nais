package kg.nais.facade;


import kg.nais.dao.UserDao;
import kg.nais.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFacade {

    private UserDao userDao = new UserDao();

    public UserFacade() {
        if(findAll().size() == 0){
            initAdmin();
        }
    }

    public void createUser(User user) {
        userDao.beginTransaction();
        userDao.getEntityManager().persist(user);
        userDao.commitAndCloseTransaction();
    }

    public void updateUser(User user) {
        userDao.beginTransaction();
        userDao.getEntityManager().merge(user);
        userDao.commitAndCloseTransaction();
    }

    public void deleteUser(User user) {
        userDao.beginTransaction();
        userDao.getEntityManager().remove(userDao.getEntityManager().contains(user) ? user : userDao.getEntityManager().merge(user));
        userDao.commitAndCloseTransaction();
    }

    public User findById(Integer id) {
        userDao.beginTransaction();
        User user = userDao.getEntityManager().find(User.class, id);
        userDao.commitAndCloseTransaction();
        return user;
    }

    public List<User> findAll(){
        List<User> userList;
        try {
            userDao.beginTransaction();
            userList = userDao.getEntityManager().createNamedQuery("User.findAll",User.class).getResultList();
        }catch (Exception ex){
            userList = new ArrayList<User>();
        }finally {
            userDao.commitAndCloseTransaction();
        }
        return userList;
    }

    public User findByEmail(String email){
        User user;
        try {
            userDao.beginTransaction();
            user = userDao.getEntityManager().createNamedQuery("User.findByLogin", User.class)
                    .setParameter("login", email).getSingleResult();
        }catch (Exception ex){
            user = null;
        }finally {
            userDao.commitAndCloseTransaction();
        }
        return user;
    }
    public User findByEmailPass(String login,String pass){
        User user;
        try {
            userDao.beginTransaction();
            user =userDao.getEntityManager().createNamedQuery("User.findByLoginPass", User.class)
                    .setParameter("login", login).setParameter("password", pass).getSingleResult();
        }catch (Exception ex){
            user = null;
        }finally {
            userDao.commitAndCloseTransaction();
        }
        return user;
    }
    public List<User> searchByEmailBy5(String login){

        List<User> userList;
        try {
            userDao.beginTransaction();
            userList = userDao.getEntityManager().createNamedQuery("User.searchByLogin",User.class).
                    setParameter("login","%"+login+"%").setMaxResults(5).getResultList();
        }catch (Exception ex){
            userList = new ArrayList<User>();
        }finally {
            userDao.commitAndCloseTransaction();
        }
        return userList;
    }

    public List<User> searchByEmail(String login){
        List<User> userList;
        try {
            userDao.beginTransaction();
            userList = userDao.getEntityManager().createNamedQuery("User.searchByLogin",User.class).
                    setParameter("login","%"+login+"%").getResultList();
        }catch (Exception ex){
            userList = new ArrayList<User>();
        }finally {
            userDao.commitAndCloseTransaction();
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
