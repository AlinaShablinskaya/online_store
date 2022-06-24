package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.RoleService;
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
    private static final String REPEAT_PASSWORD = "repeat_password";
    private static final String USER_ROLE = "USER";
    private final UserService userService;
    private final RoleService roleService;

    public RegistrationUserServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.userService = injector.getUserService();
        this.roleService = injector.getRoleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.PATH_TO_REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String firstName = req.getParameter(ServletContent.FIRST_NAME);
        final String lastName = req.getParameter(ServletContent.LAST_NAME);
        final String email = req.getParameter(ServletContent.EMAIL);
        final String password = req.getParameter(ServletContent.PASSWORD);
        final String repeatPassword = req.getParameter(REPEAT_PASSWORD);

        if (!Objects.equals(password, repeatPassword)) {
            req.getRequestDispatcher(Path.PATH_TO_REGISTRATION_PAGE).forward(req, resp);
        }

        Role role = roleService.findByRoleName(USER_ROLE);

        final User user = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withRole(role)
                .build();

        userService.createUser(user);

        resp.sendRedirect(Path.URL_TO_LOGIN);
    }
}
