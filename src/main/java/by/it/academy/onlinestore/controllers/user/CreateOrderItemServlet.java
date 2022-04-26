package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
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

@WebServlet(name = "addOrderItem", urlPatterns = "/addOrderItem")
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
        req.getRequestDispatcher("/catalog").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int productId = Integer.parseInt(req.getParameter("product_id"));
        final int amount = Integer.parseInt(req.getParameter("amount"));

        Product findProductById = productService.findProductById(productId);

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");


        final OrderItem orderItem = OrderItem.builder()
                    .withProduct(findProductById)
                    .withAmount(amount)
                    .build();
        orderItemService.createOrderItem(orderItem);

        if (cart == null ) {
            cart = Cart.builder()
                    .withUser(user)
                    .build();
            cartService.addCart(cart);
            session.setAttribute("cart", cart);
        }

        orderItemService.addOrderItemOnCart(orderItem.getId(), cart.getId());
    }
}
