package am.myOffice.shopJDBC.controller;

import am.myOffice.shopJDBC.repository.user.UserRepository;
import am.myOffice.shopJDBC.repository.user.impl.UserRepositoryJdbcImpl;
import am.myOffice.shopJDBC.service.user.UserService;
import am.myOffice.shopJDBC.service.user.impl.UserServiceImpl;
import am.myOffice.shopJDBC.util.DatabaseConnection;
import am.myOffice.shopJDBC.util.constants.Parameter;
import am.myOffice.shopJDBC.util.constants.Path;
import am.myOffice.shopJDBC.util.encoder.AESManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserRepository userRepository = new UserRepositoryJdbcImpl(DatabaseConnection.getInstance());
        UserService userService = new UserServiceImpl(userRepository);
        var email = req.getParameter(Parameter.EMAIL_PARAMETER);
        var password = req.getParameter(Parameter.PASSWORD_PARAMETER);
        var rememberMe = req.getParameter(Parameter.REMEMBER_ME_PARAMETER);
        System.out.println(rememberMe);
        try {
            userService.login(email,password);
            if (rememberMe  != null && rememberMe.equals("on")){
                Cookie cookie = new Cookie(Parameter.REMEMBER_COOKIE, AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                resp.addCookie(cookie);
            }
            req.getSession().setAttribute(Parameter.EMAIL_PARAMETER, email);
            req.getRequestDispatcher(Path.HOME_PATH).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Parameter.MESSAGE_ATTRIBUTE, e.getMessage());
            req.getRequestDispatcher(Path.INDEX_PATH).forward(req,resp);
        }
    }
}
