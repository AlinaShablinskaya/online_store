package by.it.academy.onlinestore;

import by.it.academy.onlinestore.dao.impl.CustomerAddressDaoImpl;
import by.it.academy.onlinestore.entities.*;
import by.it.academy.onlinestore.services.*;

import java.math.BigDecimal;
import java.util.Optional;

public class OnlineStoreMain {
    public static void main(String[] args) {
        CatalogService catalogService;
        ProductService productService;
        UserService userService;
        CartService cartService;
        OrderItemService orderItemService;
        ApplicationInjector injector = ApplicationInjector.getInstance();
        catalogService = injector.getCatalogService();
        productService = injector.getProductService();
        userService = injector.getUserService();
        cartService = injector.getCartService();
        orderItemService = injector.getOrderItemService();


        OrderItem orderItem3 = OrderItem.builder()
               .withProduct(productService.findProductById(2)).withAmount(10).build();

        Cart cart1 = Cart.builder().withUser(userService.findUserById(1)).build();

        Optional<OrderItem> orderItem = orderItemService.addOrderItem(orderItem3);

        Optional<Cart> cart = cartService.addCart(cart1);

        System.out.println(cart.get().getId());
        System.out.println(orderItem.get().getId());

       orderItemService.addOrderItemToCart(orderItem.get().getId(), cart.get().getId());


    }
}
