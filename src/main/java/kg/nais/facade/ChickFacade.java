package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Chick;
import kg.nais.models.Client;
import kg.nais.models.Feed;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class ChickFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(Chick chick) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(chick);
        objectDao.commitAndCloseTransaction();
    }

    public void update(Chick chick) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(chick);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(Chick chick) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(chick) ? chick : objectDao.getEntityManager().merge(chick));
        objectDao.commitAndCloseTransaction();
    }

    public List<Chick> findAll(){
        List<Chick> chickList;
        try {
            objectDao.beginTransaction();
            chickList = objectDao.getEntityManager().createNamedQuery("Chick.findAll",Chick.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            chickList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return chickList;
    }

    public Chick findById(Integer id) {
        Chick chick;
        try {
            objectDao.beginTransaction();
            chick = objectDao.getEntityManager().find(Chick.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            chick = null;
            objectDao.rollbackIfTransactionActive();
        }
        return chick;
    }

    public List<Chick> findByAgeRange(int ageFrom,int ageTo){
        List<Chick> chickList;
        try {
            objectDao.beginTransaction();
            chickList = objectDao.getEntityManager().createNamedQuery("Chick.findByAgeRange",Chick.class)
                    .setParameter("ageFrom",ageFrom).setParameter("ageTo",ageTo).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            chickList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return chickList;
    }

    public List<Chick> findByFeed(Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findByFeed",Chick.class)
                    .setParameter("feed",feed).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<Chick> findByClientAndFeed(Client client, Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findByClientAndFeed",Chick.class)
                    .setParameter("client",client).setParameter("feed",feed).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<Chick> findByActiveClientAndFeed(Client client, Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findByActiveClientAndAgeBefore",Chick.class)
                    .setParameter("client",client).setParameter("age",feed.getAgeTo()).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<Chick> findByClient(Client client){
        List<Chick> chickList;
        try {
            objectDao.beginTransaction();
            chickList = objectDao.getEntityManager().createNamedQuery("Chick.findByClient",Chick.class)
                    .setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            chickList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return chickList;
    }

    public List<Chick> findChicksForFeedBelow(Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findChicksAgeBefore",Chick.class)
                    .setParameter("age",feed.getAgeTo()).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<Chick> findActiveChicksForFeedBelow(Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findActiveChicksAgeBefore",Chick.class)
                    .setParameter("age",feed.getAgeTo()).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<Chick> findChicksByClientForFeedBelow(Client client,Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findByClientForFeedBelow",Chick.class)
                    .setParameter("ageTo",feed.getAgeTo()).setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public List<Chick> findChicksByActiveClientForFeedBelow(Client client,Feed feed){
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findByActiveClientForFeedBelow",Chick.class)
                    .setParameter("ageTo",feed.getAgeTo()).setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }


}
