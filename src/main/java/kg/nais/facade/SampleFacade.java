package kg.nais.facade;

import kg.nais.dao.ObjectDao;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class SampleFacade {
    private ObjectDao objectDao = new ObjectDao();

    public void create(Object object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(Object object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(Object object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }

    public List<Object> findAll(){
        List<Object> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Object.findAll",Object.class).getResultList();
        }catch (Exception ex){
            objectList = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return objectList;
    }

    public Object findById(Integer id) {
        Object object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(Object.class, id);
        }catch (Exception ex){
            object = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return object;
    }

//    public Object findByStatus(String status){
//        Object ms;
//        try {
//            objectDao.beginTransaction();
//            ms = objectDao.getEntityManager().createNamedQuery("MemberStatus.findByMemberStatus",Object.class)
//                    .setParameter("status",status).getSingleResult();
//        }catch (Exception ex){
//            ms = null;
//        }finally {
//            objectDao.commitAndCloseTransaction();
//        }
//        return ms;
//    }


}
