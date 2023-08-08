package model;

import model.entity.Toy;
import org.hibernate.Session;
public class Test {
    public static void main(String[] args) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.close();
//        HibernateUtil.shutdown();

        DbModel dbModel = new DbModel();
        Toy toy = new Toy("barbiekkj", new Probability(12.2564));
        dbModel.addData(toy);
    }
}