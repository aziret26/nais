package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.ClientStatus;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class ClientStatusFacade {
    private ObjectDao objectDao = new ObjectDao();

    public ClientStatusFacade() {
        if(findAll().size() == 0){
            initStatuses();
        }
    }

    public void create(ClientStatus clientStatus) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(clientStatus);
        objectDao.commitAndCloseTransaction();
    }

    public void update(ClientStatus clientStatus) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(clientStatus);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(ClientStatus clientStatus) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(clientStatus) ? clientStatus : objectDao.getEntityManager().merge(clientStatus));
        objectDao.commitAndCloseTransaction();
    }

    public List<ClientStatus> findAll(){
        List<ClientStatus> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("ClientStatus.findAll",ClientStatus.class).getResultList();
        }catch (Exception ex){
            objectList = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return objectList;
    }

    public ClientStatus findById(Integer id) {
        ClientStatus clientStatus;
        try {
            objectDao.beginTransaction();
            clientStatus = objectDao.getEntityManager().find(ClientStatus.class, id);
        }catch (Exception ex){
            clientStatus = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return clientStatus;
    }

    public ClientStatus findByStatus(String status){
        ClientStatus ms;
        try {
            objectDao.beginTransaction();
            ms = objectDao.getEntityManager().createNamedQuery("ClientStatus.findByStatus",ClientStatus.class)
                    .setParameter("status",status).getSingleResult();
        }catch (Exception ex){
            ms = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return ms;
    }

    private void initStatuses(){
        ClientStatus status = new ClientStatus("active");
        update(status);
        status = new ClientStatus("frozen");
        update(status);
        status = new ClientStatus("deleted");
        update(status);
    }
}
