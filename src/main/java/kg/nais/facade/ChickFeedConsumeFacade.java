package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.ChickFeedConsume;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class ChickFeedConsumeFacade {
    private ObjectDao objectDao = new ObjectDao();

    public ChickFeedConsumeFacade(){
        List<ChickFeedConsume> list = findAll();
        if(list == null || list.size() == 0)
            initData();
    }

    public void create(ChickFeedConsume object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(object);
        objectDao.commitAndCloseTransaction();
    }

    public void update(ChickFeedConsume object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(object);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(ChickFeedConsume object) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(object) ? object : objectDao.getEntityManager().merge(object));
        objectDao.commitAndCloseTransaction();
    }

    public List<ChickFeedConsume> findAll(){
        List<ChickFeedConsume> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("ChickFeedConsume.findAll",ChickFeedConsume.class).getResultList();
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            objectList = null;
            objectDao.rollbackIfTransactionActive();
        }
        return objectList;
    }

    public ChickFeedConsume findById(Integer id) {
        ChickFeedConsume object;
        try {
            objectDao.beginTransaction();
            object = objectDao.getEntityManager().find(ChickFeedConsume.class, id);
            objectDao.commitAndCloseTransaction();
        }catch (Exception ex){
            object = null;
            objectDao.rollbackIfTransactionActive();
        }
        return object;
    }

    public void initData(){
        List<ChickFeedConsume> list = new ArrayList<ChickFeedConsume>();
        list.add(new ChickFeedConsume(1,13));
        list.add(new ChickFeedConsume(2,20));
        list.add(new ChickFeedConsume(3,26));
        list.add(new ChickFeedConsume(4,33));
        list.add(new ChickFeedConsume(5,38));
        list.add(new ChickFeedConsume(6,43));
        list.add(new ChickFeedConsume(7,48));
        list.add(new ChickFeedConsume(8,54));
        list.add(new ChickFeedConsume(9,59));
        list.add(new ChickFeedConsume(10,61));
        list.add(new ChickFeedConsume(11,65));
        list.add(new ChickFeedConsume(12,66));
        list.add(new ChickFeedConsume(13,69));
        list.add(new ChickFeedConsume(14,70));
        list.add(new ChickFeedConsume(15,71));
        list.add(new ChickFeedConsume(16,73));
        list.add(new ChickFeedConsume(17,75));
        list.add(new ChickFeedConsume(18,80));
        list.add(new ChickFeedConsume(19,85));
        list.add(new ChickFeedConsume(20,95));
        list.add(new ChickFeedConsume(21,100));
        list.add(new ChickFeedConsume(22,104));
        list.add(new ChickFeedConsume(23,106));
        list.add(new ChickFeedConsume(24,108));
        list.add(new ChickFeedConsume(25,110));
        list.add(new ChickFeedConsume(26,113));
        list.add(new ChickFeedConsume(27,115));
        list.add(new ChickFeedConsume(28,117));

        for(int i = 29; i < 81;i++){
            if(i < 35){
                list.add(new ChickFeedConsume(i,119));
            }
            if(i > 34 && i < 63){
                list.add(new ChickFeedConsume(i,120));
            }
            if(i > 62){
                list.add(new ChickFeedConsume(i,121));
            }
        }

        list.forEach(this::create);

    }

}
