package am.myOffice.shopJDBC.repository.user.impl;

import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.repository.user.UserRepository;
import am.myOffice.shopJDBC.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserRepositoryJpaImpl implements UserRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void init() {
        System.out.println("UserRepositoryJpaImpl init method executed");
    }

    public void createdAt() {
        System.out.println("createdAt method");
    }

    @Override
    public void create(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User get(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        Query namedQuery = session.createNativeQuery("SELECT * FROM users", User.class);
        List resultList = namedQuery.getResultList();
        return resultList;
    }

    @Override
    public List<User> findUsersByName(String name) {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery("SELECT * FROM users WHERE lower(name) LIKE lower(concat('%',:name,'%'))",
                User.class);
        nativeQuery.setParameter("name", name);
        return nativeQuery.getResultList();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(get(id));
        transaction.commit();
        session.close();
    }

    @Override
    public User findUsersByEmail(String email) {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery("SELECT * FROM users WHERE email=:email", User.class);
        User user = nativeQuery.setParameter("email", email).getSingleResult();
        session.close();
        return user;
    }

    @Override
    public void isUserExists(String email) {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery("SELECT * FROM users WHERE email=:email", User.class);
        nativeQuery.setParameter("email", email).getSingleResult();
        session.close();
    }
}
