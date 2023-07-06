package am.myOffice.shopJDBC.repository.user.impl;

import am.myOffice.shopJDBC.model.User;
import org.hibernate.SessionFactory;

public class SpringJpaProxy extends UserRepoSpringJpa {

    private final SessionFactory sessionFactory;

    public SpringJpaProxy(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        try {
            super.create(user);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
        } finally {
            session.clear();
            session.close();
        }
    }
}
