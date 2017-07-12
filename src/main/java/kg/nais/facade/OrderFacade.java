package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Client;
import kg.nais.models.Feed;
import kg.nais.models.Orders;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class OrderFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(Orders orders) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(orders);
        objectDao.commitAndCloseTransaction();
    }

    public void update(Orders orders) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(orders);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(Orders orders) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(orders) ? orders : objectDao.getEntityManager().merge(orders));
        objectDao.commitAndCloseTransaction();
    }

    public List<Orders> findAll(){
        List<Orders> ordersList;
        try {
            objectDao.beginTransaction();
            ordersList = objectDao.getEntityManager().createNamedQuery("Orders.findAll",Orders.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            ordersList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return ordersList;
    }

    public Orders findById(Integer id) {
        Orders orders;
        try {
            objectDao.beginTransaction();
            orders = objectDao.getEntityManager().find(Orders.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            orders = null;
            objectDao.rollbackIfTransactionActive();
        }
        return orders;
    }

    public List<Orders> findByClient(Client client){
        List<Orders> ordersList;
        try {
            objectDao.beginTransaction();
            ordersList = objectDao.getEntityManager().createNamedQuery("Orders.findByClient",Orders.class)
                    .setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            ordersList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return ordersList;
    }

    public Orders findByClientFeed(Client client, Feed feed){
        Orders order;
        try {
            objectDao.beginTransaction();
            order = objectDao.getEntityManager().createNamedQuery("Orders.findByClientFeed",Orders.class)
                    .setParameter("client",client).setParameter("feed",feed).getSingleResult();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            order = null;
            objectDao.rollbackIfTransactionActive();
        }
        return order;
    }

    public List<Orders> findByFeed(Feed feed){
        List<Orders> ordersList;
        try {
            objectDao.beginTransaction();
            ordersList = objectDao.getEntityManager().createNamedQuery("Orders.findByFeed",Orders.class)
                    .setParameter("feed",feed).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            ordersList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return ordersList;
    }

}
