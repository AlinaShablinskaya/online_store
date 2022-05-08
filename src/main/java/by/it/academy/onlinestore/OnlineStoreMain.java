package by.it.academy.onlinestore;

import by.it.academy.onlinestore.dao.impl.CustomerAddressDaoImpl;
import by.it.academy.onlinestore.entities.*;
import by.it.academy.onlinestore.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
                .withProduct(productService.findProductById(2)).withAmount(10).withTotalPrice(BigDecimal.valueOf(12.0)).build();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem3);

        BigDecimal sum = cartService.calculateSum(orderItems);

        Cart cart = Cart.builder().withId(10).withUser(userService.findUserById(1)).withTotalSum(sum).build();

        OrderItem orderItem4 = OrderItem.builder()
                .withProduct(productService.findProductById(2)).withAmount(20).withTotalPrice(BigDecimal.valueOf(12.0)).build();

        List<OrderItem> orderItems1 = new ArrayList<>();
        orderItems.add(orderItem4);

        BigDecimal sum2 = cartService.calculateSum(orderItems1);



        System.out.println(cart);

        Cart update = Cart.builder().withId(10).withUser(userService.findUserById(1)).withTotalSum(sum2).build();

        System.out.println(update);

    }
}
