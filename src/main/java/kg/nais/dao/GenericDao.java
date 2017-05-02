package kg.nais.dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

abstract class GenericDao implements Serializable {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    private EntityManager entityManager;

    public void beginTransaction() {
        setEntityManager(getEntityManagerFactory().createEntityManager());
        getEntityManager().getTransaction().begin();
    }

    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    public void closeTransaction() {
        getEntityManager().close();
    }

    public void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getRawEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
