package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.CartService;
import by.it.academy.onlinestore.services.OrderItemService;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/createOrder")
public class CreateOrderItemServlet extends HttpServlet {
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final CartService cartService;

    public CreateOrderItemServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.orderItemService = injector.getOrderItemService();
        this.productService = injector.getProductService();
        this.cartService = injector.getCartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.URT_TO_DETAIL).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletContent.USER);
        Cart cart = (Cart) session.getAttribute(ServletContent.CART);

        if (user != null) {
            final OrderItem orderItemBuilder = createOrderItem(req);
            OrderItem orderItem = orderItemService.addOrderItem(orderItemBuilder).get();

            if (cart == null) {
                Cart cartBuilder = createCart(user);
                cart = cartService.addCart(cartBuilder).get();
                session.setAttribute(ServletContent.CART, cart);
            }
            orderItemService.addOrderItemToCart(orderItem.getId(), cart.getId());
        }
        resp.sendRedirect(Path.URL_TO_CART);
    }

    private OrderItem createOrderItem(HttpServletRequest req) {
        final int productId = Integer.parseInt(req.getParameter(ServletContent.PRODUCT_ID));
        final int amount = Integer.parseInt(req.getParameter(ServletContent.AMOUNT));

        Product product = productService.findProductById(productId);

            return OrderItem.builder()
                    .withProduct(product)
                    .withAmount(amount)
                    .build();
    }

    private Cart createCart (User user) {
        return Cart.builder()
                .withUser(user)
                .build();
    }
}
