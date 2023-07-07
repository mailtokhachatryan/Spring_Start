package am.myOffice.shopJDBC.controller;

import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.repository.user.UserRepository;
import am.myOffice.shopJDBC.repository.user.impl.UserRepositoryJdbcImpl;
import am.myOffice.shopJDBC.service.user.UserService;
import am.myOffice.shopJDBC.service.user.impl.UserServiceImpl;
import am.myOffice.shopJDBC.util.DatabaseConnection;
import am.myOffice.shopJDBC.util.constants.Parameter;
import am.myOffice.shopJDBC.util.constants.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRepository userRepository = new UserRepositoryJdbcImpl(DatabaseConnection.getInstance());
        UserService userService = new UserServiceImpl();
        User user = new User();



        try {
            user.setName(req.getParameter(Parameter.NAME_PARAMETER));
            user.setLastname(req.getParameter(Parameter.LASTNAME_PARAMETER));
            user.setBalance(Double.parseDouble(req.getParameter(Parameter.BALANCE_PARAMETER)));
            user.setEmail(req.getParameter(Parameter.EMAIL_PARAMETER));
            user.setPassword(req.getParameter(Parameter.PASSWORD_PARAMETER));
            user.setAge(Integer.parseInt(req.getParameter(Parameter.AGE_PARAMETER)));

            userRepository.isUserExists(req.getParameter(Parameter.EMAIL_PARAMETER));
            userService.register(user);
            req.setAttribute(Parameter.EMAIL_PARAMETER,req.getParameter(Parameter.EMAIL_PARAMETER));
            req.getRequestDispatcher(Path.INDEX_PATH).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Parameter.MESSAGE_ATTRIBUTE, e.getMessage());
            req.getRequestDispatcher(Path.REGISTER_PATH).forward(req,resp);
        }
    }
}
