package am.myOffice.shopJDBC.controller;

import am.myOffice.shopJDBC.util.constants.Parameter;
import am.myOffice.shopJDBC.util.constants.Path;
import am.myOffice.shopJDBC.util.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        Cookie remember = CookieUtil.getCookieByName(req.getCookies(), Parameter.REMEMBER_COOKIE);
        if (remember != null){
            remember.setMaxAge(0);
            resp.addCookie(remember);
        }
        resp.sendRedirect(Path.INDEX_PATH);
    }
}
