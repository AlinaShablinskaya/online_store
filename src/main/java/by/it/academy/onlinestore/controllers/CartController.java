package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.CartDto;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.mappers.CartMapper;
import by.it.academy.onlinestore.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/")
    public CartDto saveCart(@RequestBody CartDto cartDto) {
        Cart cart = cartService.addCart(CartMapper.INSTANCE.convertToCart(cartDto));
        return CartMapper.INSTANCE.convertToCartDto(cart);
    }

    @GetMapping("/{id}")
    public CartDto findCartById(@PathVariable(value = "id") Integer cartId) {
        Cart cart = cartService.findCartById(cartId);
        return CartMapper.INSTANCE.convertToCartDto(cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCartById(@PathVariable(value = "id") Integer cartId) {
        cartService.deleteCart(cartId);
    }

    @PutMapping("/")
    public CartDto updateCart(@RequestBody CartDto cartDto) {
        Cart cart = cartService.updateCart(CartMapper.INSTANCE.convertToCart(cartDto));
        return CartMapper.INSTANCE.convertToCartDto(cart);
    }
}
