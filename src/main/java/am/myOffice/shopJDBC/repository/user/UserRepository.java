package am.myOffice.shopJDBC.repository.user;

import am.myOffice.shopJDBC.model.User;

import java.util.List;

public interface UserRepository {

    void create(User user);

    void update(User user);

    User get(Long id);

    List<User> getAll();

    List<User> findUsersByName(String name);

    void delete(Long id);

    User findUsersByEmail(String email);

    void isUserExists(String email);
}