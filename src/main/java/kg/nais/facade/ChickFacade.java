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
        List<Chick> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Chick.findAll",Chick.class).getResultList();
        }catch (Exception ex){
            objectList = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return objectList;
    }

    public Chick findById(Integer id) {
        Chick chick;
        try {
            objectDao.beginTransaction();
            chick = objectDao.getEntityManager().find(Chick.class, id);
        }catch (Exception ex){
            chick = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return chick;
    }

    public List<Chick> findByAgeRange(int ageFrom,int ageTo){
        List<Chick> ms;
        try {
            objectDao.beginTransaction();
            ms = objectDao.getEntityManager().createNamedQuery("Chick.findByAgeRange",Chick.class)
                    .setParameter("ageFrom",ageFrom).setParameter("ageTo",ageTo).getResultList();
        }catch (Exception ex){
            ms = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return ms;
    }

    public Chick findByFeed(Feed feed){
        Chick ms;
        try {
            objectDao.beginTransaction();
            ms = objectDao.getEntityManager().createNamedQuery("Chick.findByFeed",Chick.class)
                    .setParameter("feed",feed).getSingleResult();
        }catch (Exception ex){
            ms = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return ms;
    }

    public Chick findByClientFeed(Client client, Feed feed){
        Chick ms;
        try {
            objectDao.beginTransaction();
            ms = objectDao.getEntityManager().createNamedQuery("Chick.findByFeed",Chick.class)
                    .setParameter("feed",feed).getSingleResult();
        }catch (Exception ex){
            ms = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return ms;
    }

}
