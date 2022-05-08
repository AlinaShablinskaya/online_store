package by.it.academy.onlinestore.filters;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private Map<String, String> protectedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls = new HashMap<>();

        protectedUrls.put(Path.URL_TO_PRODUCT_PANEL, ServletContent.ADMIN_ROLE);
        protectedUrls.put(Path.URL_TO_ADD_CATALOG, ServletContent.ADMIN_ROLE);
        protectedUrls.put(Path.URL_TO_ADD_PRODUCT, ServletContent.ADMIN_ROLE);
        protectedUrls.put(Path.URL_TO_DELETE_PRODUCT, ServletContent.ADMIN_ROLE);
        protectedUrls.put(Path.URL_TO_UPDATE_PRODUCT, ServletContent.ADMIN_ROLE);

        protectedUrls.put(Path.URL_TO_CREATE_ORDER_ITEM, ServletContent.USER_ROLE);
        protectedUrls.put(Path.URL_TO_DELETE_ORDER_ITEM, ServletContent.USER_ROLE);
        protectedUrls.put(Path.URL_TO_PRODUCT_DETAIL, ServletContent.USER_ROLE);
        protectedUrls.put(Path.URL_TO_CART, ServletContent.USER_ROLE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        final HttpSession session = req.getSession();

        String url = req.getServletPath();
        User user = (User) session.getAttribute(ServletContent.USER);

        if (!protectedUrls.containsKey(url) || isAuthorized(user, protectedUrls.get(url))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher(Path.PATH_TO_ERROR_PAGE).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized (User user, String roleName) {
        String role = user.getRole().getRoleName();
            return role.equals(roleName);
    }
}
