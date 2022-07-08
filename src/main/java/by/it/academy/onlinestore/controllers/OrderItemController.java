package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.OrderItemDto;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.mappers.OrderItemMapper;
import by.it.academy.onlinestore.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public OrderItemDto saveOrderItem(@RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemService.addOrderItem(OrderItemMapper.INSTANCE.convertToOrderItem(orderItemDto));
        return OrderItemMapper.INSTANCE.convertToOrderItemDto(orderItem);
    }

    @PostMapping("/{id}/order")
    @PreAuthorize("hasAuthority('USER')")
    public void saveOrderItemToCart(@RequestParam("id") Integer orderItemId, @PathVariable("id") Integer cartId) {
        orderItemService.addOrderItemToCart(orderItemId, cartId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteOrderItem(@PathVariable(value = "id") Integer orderItemId) {
        orderItemService.removeOrderItem(orderItemId);
    }

    @GetMapping("/{id}/order")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<OrderItemDto> showAllOrderItemInCart(@PathVariable(value = "id") Integer cartId) {
        List<OrderItem> orderItems = orderItemService.findAllOrderItemsByCartId(cartId);
        return orderItems.stream()
                .map(OrderItemMapper.INSTANCE::convertToOrderItemDto)
                .collect(Collectors.toList());
    }
}
