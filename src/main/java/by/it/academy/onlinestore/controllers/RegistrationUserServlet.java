package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "registration", urlPatterns = "/registration")
public class RegistrationUserServlet extends HttpServlet {
    private final UserService userService;

    public RegistrationUserServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.userService = injector.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String firstName = req.getParameter("firstName");
        final String lastName = req.getParameter("lastName");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final String repeatPassword = req.getParameter("repeatPassword");

        if (!Objects.equals(password, repeatPassword)) {
            req.getRequestDispatcher("/registration.html").forward(req, resp);
            return;
        }

        final User user = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withLogin(login)
                .withPassword(password)
                .build();

        userService.createUser(user);

        req.getRequestDispatcher("/login.html").forward(req, resp);
        return;
    }
}
