package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.User;
import kg.nais.models.notification.UserFeedNotification;
import kg.nais.models.notification.NotificationSeen;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class NotificationSeenFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(NotificationSeen object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(NotificationSeen object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(NotificationSeen object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }


    public NotificationSeen findById(Integer id) {
        NotificationSeen object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(NotificationSeen.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }


    public List<NotificationSeen> findAll(){
        List<NotificationSeen> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("NotificationSeen.findAll",NotificationSeen.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<NotificationSeen> findByUser(User object){
        List<NotificationSeen> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("NotificationSeen.findByUser",NotificationSeen.class)
                    .setParameter("user", object).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }


    public List<NotificationSeen> findByNotification(UserFeedNotification object){
        List<NotificationSeen> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("NotificationSeen.findByNotification",NotificationSeen.class)
                    .setParameter("clientFeedNotification", object).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public NotificationSeen findByUserAndNotification(User user, UserFeedNotification notification){
        NotificationSeen objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().
                    createNamedQuery("NotificationSeen.findByUserAndNotification",NotificationSeen.class).
                    setParameter("user", user).setParameter("clientFeedNotification",notification).getSingleResult();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }
    public int countSeenByUser(User user){
        List<NotificationSeen> objectList = findByUser(user);
        return objectList != null ? objectList.size() : 0;
    }

}
