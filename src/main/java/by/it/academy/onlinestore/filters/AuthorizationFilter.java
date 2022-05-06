package by.it.academy.onlinestore.filters;

import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/productPanel")
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        final HttpSession session = req.getSession();

        User user = (User) session.getAttribute(ServletContent.USER);

        if (user.getRole().getRoleName().equals(ServletContent.ADMIN_ROLE)) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher(Path.PATH_TO_ERROR_PAGE).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
