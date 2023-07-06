package am.myOffice.shopJDBC.repository.order.impl;

import am.myOffice.shopJDBC.model.Order;
import am.myOffice.shopJDBC.repository.order.OrderRepository;
import am.myOffice.shopJDBC.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class OrderRepositoryJpaImpl implements OrderRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            session.save(order);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            session.update(order);
        transaction.commit();
        session.close();
    }

    @Override
    public Order getOrderById(Long id) {
        Session session = sessionFactory.openSession();
        Order order = session.get(Order.class, id);
        session.close();
        return order;
    }

    @Override
    public List<Order> getAll() {
        Session session = sessionFactory.openSession();
        NativeQuery<Order> nativeQuery = session.createNativeQuery("SELECT * FROM orders", Order.class);
        session.close();
        return nativeQuery.getResultList();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            session.delete(id);
        transaction.commit();
        session.close();
    }
}
