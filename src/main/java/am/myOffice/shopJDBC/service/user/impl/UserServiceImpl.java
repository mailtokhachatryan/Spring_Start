package am.myOffice.shopJDBC.service.user.impl;

import am.myOffice.shopJDBC.exceptions.UserNotFoundException;
import am.myOffice.shopJDBC.exceptions.ValidationException;
import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.repository.data.UserRepositoryData;
import am.myOffice.shopJDBC.service.user.UserService;
import am.myOffice.shopJDBC.util.constants.Message;
import am.myOffice.shopJDBC.util.encoder.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service  // name = userServiceImpl
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryData userRepository;

    @Override
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED)
    public void register(User user) {
        try {
            validationForRegistration(user);
            user.setPassword(MD5Encoder.encode(user.getPassword()));

            userRepository.save(user);

            throw new RuntimeException();

            //sending email
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        String mail = "test@gmail.com";
        User byEmail = userRepository.findByEmail(mail);

        System.out.println(userRepository.getName(mail));

        return byEmail;
    }

    @Override
    public void login(String email, String password) throws Exception {
        validationForLogin(email, password);
        User loginedUser = userRepository.findByEmail(email);
        if (!loginedUser.getPassword().equals(MD5Encoder.encode(password))) {
            throw new ValidationException(Message.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    @Override
    @Transactional
    public void changePassword(String email, String newPassword, String repeatPassword) {
        if (!newPassword.equals(repeatPassword)) {
            throw new ValidationException(Message.PASSWORDS_NOT_MATCHES);
        }
        var user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(Message.USER_NOT_FOUNT);
        passwordValidation(newPassword);
        user.setPassword(MD5Encoder.encode(newPassword));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteAccount(String email) {
        try {
            Long id = userRepository.findByEmail(email).getId();
            userRepository.findById(id);
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
