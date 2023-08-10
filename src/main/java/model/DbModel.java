package model;

import model.entity.Toy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DbModel implements iGetModel<Toy>{

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public DbModel() {

    }

    @Override
    public void addData(Toy data) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.save(data);
            session.getTransaction().commit();
            session.close();
        } catch (Throwable ex) {
            System.err.println("Adding data failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void deleteData(Toy data) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.delete(data);
            session.getTransaction().commit();
            session.close();
        } catch (Throwable ex) {
            System.err.println("Deleting data failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public List<Toy> getAllData() {
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Toy> criteria = builder.createQuery(Toy.class);
            criteria.from(Toy.class);
            List<Toy> toysList = session.createQuery(criteria).getResultList();
            session.close();
            return toysList;
        } catch (Throwable ex) {
            System.err.println("Selecting all data failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public Toy getDataById(int id) {
        try {
            Session session = sessionFactory.openSession();
            Toy toy = (Toy) session.get(Toy.class, id);
            session.close();
            return toy;
        } catch (Throwable ex) {
            System.err.println("Selecting data by id failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void updateData(Toy data) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.update(data);
            session.getTransaction().commit();
            session.close();
        } catch (Throwable ex) {
            System.err.println("Adding data failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void shutdownSessionFabric() {
        HibernateUtil.shutdown();
    }

}
