package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.order.OrderItemDto;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.mappers.OrderItemMapper;
import by.it.academy.onlinestore.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing order item.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderItemController {
    private final OrderItemService orderItemService;

    /**
     * POST : create a new order item
     * @param orderItemDto the orderItemDto to create
     * @return the new order item in body
     */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public OrderItemDto saveOrderItem(@RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemService.addOrderItem(OrderItemMapper.INSTANCE.convertToOrderItem(orderItemDto));
        return OrderItemMapper.INSTANCE.convertToOrderItemDto(orderItem);
    }

    /**
     * POST /{id}/order : create a new relationship between order item and cart
     * @param orderItemId the id of the order item to create
     * @param cartId the id of the cart to create
     */
    @PostMapping("/{id}/order")
    @PreAuthorize("hasAuthority('USER')")
    public void saveOrderItemToCart(@RequestParam("id") Integer orderItemId, @PathVariable("id") Integer cartId) {
        orderItemService.addOrderItemToCart(orderItemId, cartId);
    }

    /**
     * DELETE /{id} : delete order item by specified id
     * @param orderItemId the id of the order item to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteOrderItem(@PathVariable(value = "id") Integer orderItemId) {
        orderItemService.removeOrderItem(orderItemId);
    }

    /**
     * GET : get all order items by specified cartId
     * @param cartId the id of the cart to retrieve list of all order items
     * @return the list of all order items in body
     */
    @GetMapping("/{id}/orders")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<OrderItemDto> showAllOrderItemInCart(@PathVariable(value = "id") Integer cartId) {
        List<OrderItem> orderItems = orderItemService.findAllOrderItemsByCartId(cartId);
        return orderItems.stream()
                .map(OrderItemMapper.INSTANCE::convertToOrderItemDto)
                .collect(Collectors.toList());
    }
}
