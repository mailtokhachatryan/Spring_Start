package am.myOffice.shopJDBC.service.user;

import am.myOffice.shopJDBC.model.User;

public interface UserService {

    void register(User user) ;
    void login(String email, String password) throws Exception;

    void changePassword(String email,String newPassword,String repeatPassword);

    void deleteAccount(String email);
}
