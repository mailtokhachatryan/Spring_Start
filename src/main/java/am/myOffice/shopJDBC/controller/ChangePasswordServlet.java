package am.myOffice.shopJDBC.controller;

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

public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRepository userRepository = new UserRepositoryJdbcImpl(DatabaseConnection.getInstance());
        UserService userService = new UserServiceImpl();

        var email = (String)req.getSession().getAttribute(Parameter.EMAIL_PARAMETER);
        var newPassword = req.getParameter(Parameter.NEW_PASSWORD_PARAMETER);
        var repeatPassword = req.getParameter(Parameter.REPEAT_PASSWORD_PARAMETER);
        try {
            userService.changePassword(email,newPassword,repeatPassword);
            req.getRequestDispatcher(Path.HOME_PATH).forward(req,resp);
        }catch (Exception e) {
            req.setAttribute(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            req.getRequestDispatcher(Path.CHANGE_PASSWORD_PATH).forward(req,resp);
        }

    }
}
