package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Client;
import kg.nais.models.ClientSurvey;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class ClientSurveyFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(ClientSurvey object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(ClientSurvey object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(ClientSurvey object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }

    public List<ClientSurvey> findAll(){
        List<ClientSurvey> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("ClientSurvey.findAll",ClientSurvey.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }
    public List<ClientSurvey> findByClient(Client client){
        List<ClientSurvey> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager()
                    .createNamedQuery("ClientSurvey.findByClient",ClientSurvey.class)
                    .setParameter("client",client).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public ClientSurvey findById(Integer id) {
        ClientSurvey object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(ClientSurvey.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }


}
