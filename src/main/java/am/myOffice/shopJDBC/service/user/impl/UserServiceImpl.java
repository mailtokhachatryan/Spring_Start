package am.myOffice.shopJDBC.service.user.impl;

import am.myOffice.shopJDBC.exceptions.UserNotFoundException;
import am.myOffice.shopJDBC.exceptions.ValidationException;
import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.repository.user.UserRepository;
import am.myOffice.shopJDBC.service.user.UserService;
import am.myOffice.shopJDBC.util.constants.Message;
import am.myOffice.shopJDBC.util.encoder.MD5Encoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED)
    public void register(User user) {
        try {
            validationForRegistration(user);
            user.setPassword(MD5Encoder.encode(user.getPassword()));

            userRepository.create(user);

            throw new RuntimeException();

            //sending email
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String email, String password) throws Exception {
        validationForLogin(email, password);
        User loginedUser = userRepository.findUsersByEmail(email);
        if (!loginedUser.getPassword().equals(MD5Encoder.encode(password))) {
            throw new ValidationException(Message.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    @Override
    public void changePassword(String email, String newPassword, String repeatPassword) {
        if (!newPassword.equals(repeatPassword)) {
            throw new ValidationException(Message.PASSWORDS_NOT_MATCHES);
        }
        var user = userRepository.findUsersByEmail(email);
        if (user == null)
            throw new UserNotFoundException(Message.USER_NOT_FOUNT);
        passwordValidation(newPassword);
        user.setPassword(MD5Encoder.encode(newPassword));
        try {
            userRepository.update(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(String email) {
        try {
            Long id = userRepository.findUsersByEmail(email).getId();
            userRepository.delete(id);
        } catch (Exception e) {
            throw new UserNotFoundException(Message.USER_NOT_FOUNT);
        }
    }

    private void validationForRegistration(User user) {
        if (user.getEmail() == null ||
                user.getEmail().length() == 0 ||
                !user.getEmail().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
                        + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            throw new ValidationException(Message.EMAIL_OR_PASSWORD_IS_NULL);
        if (user.getPassword() == null ||
                user.getPassword().length() < 8
        )
            throw new ValidationException(Message.PASSWORD_VALIDATION_IS_WRONG);
    }

    private void passwordValidation(String password) {
        if (password.length() < 8)
            throw new ValidationException(Message.PASSWORD_LENGTH_ISSUE);
    }

    private void validationForLogin(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty())
            throw new ValidationException(Message.EMAIL_OR_PASSWORD_IS_NULL);
    }
}
