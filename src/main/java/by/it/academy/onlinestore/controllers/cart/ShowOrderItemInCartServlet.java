package by.it.academy.onlinestore.controllers.cart;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.services.CartService;
import by.it.academy.onlinestore.services.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/cart")
public class ShowOrderItemInCartServlet extends HttpServlet {
    private final OrderItemService orderItemService;
    private final CartService cartService;

    public ShowOrderItemInCartServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.orderItemService = injector.getOrderItemService();
        this.cartService = injector.getCartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(ServletContent.CART);

        List<OrderItem> orderItems = new ArrayList<>(orderItemService.findAllOrderItemsByCartId(cart.getId()));

        req.setAttribute(ServletContent.ORDER_ITEM, orderItems);
        BigDecimal totalSum = cartService.calculateSum(orderItems);
        req.setAttribute(ServletContent.SUM, totalSum);

        Cart cartBuilder = Cart.builder()
                .withId(cart.getId())
                .withTotalSum(totalSum)
                .build();
        cartService.updateCart(cartBuilder);

        req.getRequestDispatcher(Path.PATH_TO_CART_PAGE).forward(req, resp);
    }
}
