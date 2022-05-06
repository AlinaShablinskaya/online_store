package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.services.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/cart")
public class ShowOrderItemServlet extends HttpServlet {
    private final OrderItemService orderItemService;

    public ShowOrderItemServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.orderItemService = injector.getOrderItemService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(ServletContent.CART);
        req.setAttribute(ServletContent.ORDER_ITEM, orderItemService.findAllOrderItemsByCartId(cart.getId()));
        req.getRequestDispatcher(Path.PATH_TO_CART_PAGE).forward(req, resp);
    }
}
