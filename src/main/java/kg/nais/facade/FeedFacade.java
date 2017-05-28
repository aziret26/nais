package kg.nais.facade;

import kg.nais.dao.ObjectDao;
import kg.nais.models.Feed;

import java.util.List;

/**
 * Created by azire on 4/20/2017.
 */
public class FeedFacade {
    private ObjectDao objectDao = new ObjectDao();

    public FeedFacade() {
        if(findAll().size() == 0){
            initFeedTable();
        }
    }

    public void create(Feed feed) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().persist(feed);
        objectDao.commitAndCloseTransaction();
    }

    public void update(Feed feed) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().merge(feed);
        objectDao.commitAndCloseTransaction();
    }

    public void delete(Feed feed) {
        objectDao.beginTransaction();
        objectDao.getEntityManager().remove(objectDao.getEntityManager().contains(feed) ? feed : objectDao.getEntityManager().merge(feed));
        objectDao.commitAndCloseTransaction();
    }

    public List<Feed> findAll(){
        List<Feed> objectList;
        try {
            objectDao.beginTransaction();
            objectList = objectDao.getEntityManager().createNamedQuery("Feed.findAll",Feed.class).getResultList();
        }catch (Exception ex){
            objectList = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return objectList;
    }

    public Feed findById(Integer id) {
        Feed feed;
        try {
            objectDao.beginTransaction();
            feed = objectDao.getEntityManager().find(Feed.class, id);
        }catch (Exception ex){
            feed = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return feed;
    }

    public Feed findByName(String name){
        Feed ms;
        try {
            objectDao.beginTransaction();
            ms = objectDao.getEntityManager().createNamedQuery("Feed.findByName",Feed.class)
                    .setParameter("name",name).getSingleResult();
        }catch (Exception ex){
            ms = null;
        }finally {
            objectDao.commitAndCloseTransaction();
        }
        return ms;
    }

    private void initFeedTable(){
        Feed f = new Feed("J1",1,3);    create(f);
        f = new Feed("J2",4,8);         create(f);
        f = new Feed("J3",9,17);        create(f);
        f = new Feed("J4",18,35);       create(f);
        f = new Feed("T1",36,49);       create(f);
        f = new Feed("T2",50,64);       create(f);
        f = new Feed("T3",65,70);       create(f);
        f = new Feed("T3+",71,500);     create(f);
    }

}
