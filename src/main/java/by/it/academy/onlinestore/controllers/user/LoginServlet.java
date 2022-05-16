package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    public LoginServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.userService = injector.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.PATH_TO_LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String email = req.getParameter(ServletContent.EMAIL);
        final String password = req.getParameter(ServletContent.PASSWORD);

        final boolean isLogin = userService.login(email, password);

        if (isLogin) {
            User user = userService.findUserByEmail(email);
            HttpSession session = req.getSession();
            session.setAttribute(ServletContent.USER, user);
            String role = user.getRole().getRoleName();
            if (role.equals(ServletContent.USER_ROLE)) {
                resp.sendRedirect(Path.URL_TO_CATALOG);
            } else if (role.equals(ServletContent.ADMIN_ROLE)) {
                resp.sendRedirect(Path.URL_TO_PRODUCT_PANEL);
            }
        } else {
            resp.sendRedirect(Path.URL_TO_LOGIN);
        }
    }
}
