package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.ApplicationInjector;
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
        req.getRequestDispatcher("/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        final boolean isLogin = userService.login(email, password);

        if (isLogin) {
            User user = userService.findUserByEmail(email);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            req.getRequestDispatcher("/catalog.html").forward(req, resp);
        } else {
            req.getRequestDispatcher("/login.html").forward(req, resp);
        }
    }
}
