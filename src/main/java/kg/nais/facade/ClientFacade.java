package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Client;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class ClientFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(Client client) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(client);
        objectDao.commitAndCloseTransaction();
    }

    public void update(Client client) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(client);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(Client client) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(client) ? client : objectDao.getEntityManager().merge(client));
        objectDao.commitAndCloseTransaction();
    }

    public List<Client> findAll(){
        return getList("Client.findAll");
    }

    public Client findById(Integer id) {
        Client client;
        try {
            objectDao.beginTransaction();
            client = objectDao.getEntityManager().find(Client.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            client = null;
            objectDao.rollbackIfTransactionActive();
        }
        return client;
    }

    public List<Client> findByStatus(int statusId){
        List<Client> clientList;
        try {
            objectDao.beginTransaction();
            clientList = objectDao.getEntityManager().createNamedQuery("Client.findByClientStatus",Client.class)
                    .setParameter("clientStatusId",statusId).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            clientList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return clientList;
    }

    public List<Client> findAllActiveClients(){
        return getList("Client.findAllActive");
    }
    public List<Client> findAllFrozenClients(){
        return getList("Client.findAllFrozen");
    }
    private List<Client> getList(String queryName){
        List<Client> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery(queryName,Client.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }
}
