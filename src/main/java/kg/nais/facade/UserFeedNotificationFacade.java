package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Client;
import kg.nais.models.Feed;
import kg.nais.models.User;
import kg.nais.models.notification.UserFeedNotification;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class UserFeedNotificationFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(UserFeedNotification object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(UserFeedNotification object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(UserFeedNotification object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }

    public UserFeedNotification findById(Integer id) {
        UserFeedNotification object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(UserFeedNotification.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }

    public List<UserFeedNotification> findAll(){
        return getList("UserFeedNotification.findAll");
    }

    public List<UserFeedNotification> findNotResolved(){

        return getList("UserFeedNotification.findNotResolved");
    }

    public List<UserFeedNotification> findResolved(){
        return getList("UserFeedNotification.findResolved");
    }
    
    public UserFeedNotification findByClientAndFeed(Client client, Feed feed){
        UserFeedNotification objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().
                    createNamedQuery("UserFeedNotification.findByClientAndFeed",UserFeedNotification.class).
                    setParameter("client",client).setParameter("feed",feed).getSingleResult();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<UserFeedNotification> findByClient(Client client){
        List<UserFeedNotification> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().
                    createNamedQuery("UserFeedNotification.findByClient",UserFeedNotification.class).
                    setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    private List<UserFeedNotification> getList(String queryName){
        List<UserFeedNotification> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery(queryName,UserFeedNotification.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

}
