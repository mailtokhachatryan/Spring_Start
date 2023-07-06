package am.myOffice.shopJDBC.repository.user.impl;

import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.repository.user.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserRepoSpringJpa implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepoSpringJpa(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> findUsersByName(String name) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findUsersByEmail(String email) {
        return null;
    }

    @Override
    public void isUserExists(String email) {

    }
}
