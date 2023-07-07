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
import javax.sql.DataSource;
import java.io.IOException;

public class AccountDeleteServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRepository userRepository = new UserRepositoryJdbcImpl(DatabaseConnection.getInstance());
        UserService userService = new UserServiceImpl();

        try {
            String email = (String)req.getSession().getAttribute(Parameter.EMAIL_PARAMETER);
            userService.deleteAccount(email);
            req.getRequestDispatcher(Path.INDEX_PATH).forward(req,resp);
        } catch (Exception e){
            req.setAttribute(Parameter.EMAIL_PARAMETER,e.getMessage());
        }
    }
}
