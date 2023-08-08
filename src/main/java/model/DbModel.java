package model;

import model.entity.Toy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DbModel implements iGetModel<Toy>{

    public DbModel() {

    }

    @Override
    public void addData(Toy data) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            session.save(data);
            session.getTransaction().commit();
            session.close();
            HibernateUtil.shutdown();
        } catch (Throwable ex) {
            System.err.println("Adding data failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public List<Toy> getAllData() {
        return null;
    }

    @Override
    public Toy getDataById(int id) {
        return null;
    }

    @Override
    public void setData(Toy data) {

    }
}
