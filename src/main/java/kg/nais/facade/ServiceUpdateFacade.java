package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.ServiceUpdate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class ServiceUpdateFacade {
    private ObjectDao objectDao = new ObjectDao();

    public ServiceUpdateFacade(){
        if(findById(1) == null){
            initFirstRow();
        }
    }

    public void create(ServiceUpdate object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(ServiceUpdate object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(ServiceUpdate object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }

    public List<ServiceUpdate> findAll(){
        List<ServiceUpdate> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("ServiceUpdate.findAll",ServiceUpdate.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public ServiceUpdate findById(Integer id) {
        ServiceUpdate object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(ServiceUpdate.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }

    public ServiceUpdate findValid(){
        List<ServiceUpdate> list = findAll();
        return list.get(list.size()-1);
    }

    private void initFirstRow(){
        ServiceUpdate su = new ServiceUpdate();
        su.setUpdateTime("8:00");
        create(su);
    }
}
