package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Client;
import kg.nais.models.Feed;
import kg.nais.models.OrdersHistory;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class OrdersHistoryFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(OrdersHistory object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(OrdersHistory object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(OrdersHistory object) {
        objectDao.beginTransaction();
        OrdersHistory oh = objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object);
        if(oh != null)
            objectDao.getEntityManager().remove(oh);
        objectDao.commitAndCloseTransaction();
    }

    public List<OrdersHistory> findAll(){
        List<OrdersHistory> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("OrdersHistory.findAll",OrdersHistory.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public OrdersHistory findById(Integer id) {
        OrdersHistory object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(OrdersHistory.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }

    public List<OrdersHistory> findByClient(Client client){
        List<OrdersHistory> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("OrdersHistory.findByClient",OrdersHistory.class)
                    .setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<OrdersHistory> findByFeed(Feed feed){
        List<OrdersHistory> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("OrdersHistory.findByFeed",OrdersHistory.class)
                    .setParameter("feed",feed).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<OrdersHistory> findByClientAndFeed(Client client,Feed feed){
        List<OrdersHistory> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("OrdersHistory.findByClientAndFeed",OrdersHistory.class)
                    .setParameter("client",client).setParameter("feed",feed).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }
}
