package by.it.academy.onlinestore.filters;

import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        final HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if (user.getRole().getRoleName().equals("ADMIN")) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/error/error_403.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
