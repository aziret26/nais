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
        objectDao.getEntityManager().remove(client);
        objectDao.commitAndCloseTransaction();
    }

    public List<Client> findAll(){
        List<Client> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Client.findAll",Client.class).getResultList();
        }catch (Exception ex){
            objectList = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return objectList;
    }

    public Client findById(Integer id) {
        Client client;
        try {
            objectDao.beginTransaction();
            client = objectDao.getEntityManager().find(Client.class, id);
        }catch (Exception ex){
            client = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return client;
    }

//    public Client findByStatus(String status){
//        Client ms;
//        try {
//            objectDao.beginTransaction();
//            ms = objectDao.getEntityManager().createNamedQuery("MemberStatus.findByMemberStatus",Client.class)
//                    .setParameter("status",status).getSingleResult();
//        }catch (Exception ex){
//            ms = null;
//        }finally {
//            objectDao.commitAndCloseTransaction();
//        }
//        return ms;
//    }


}
