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
}
