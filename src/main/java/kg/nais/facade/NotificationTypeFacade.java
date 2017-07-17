package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.notification.NotificationType;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class NotificationTypeFacade {
    private ObjectDao objectDao = new ObjectDao();

    public NotificationTypeFacade() {
        if(findAll() == null || findAll().size() == 0){
            initData();
        }
    }

    public void create(NotificationType object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(NotificationType object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(NotificationType object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }

    public List<NotificationType> findAll(){
        List<NotificationType> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("NotificationType.findAll",NotificationType.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public NotificationType findById(Integer id) {
        NotificationType object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(NotificationType.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }

    private void initData(){
        NotificationType nt = new NotificationType("info");
        create(nt);
        nt = new NotificationType("warning");
        create(nt);
        nt = new NotificationType("error");
        create(nt);
    }
}
