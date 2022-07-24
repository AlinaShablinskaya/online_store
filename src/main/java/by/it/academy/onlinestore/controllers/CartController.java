package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.cart.CartDto;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.mappers.CartMapper;
import by.it.academy.onlinestore.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing cart.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    /**
     * POST : create a new cart
     * @param cartDto the cartDto to create
     * @return the new cart in body
     */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public CartDto saveCart(@RequestBody CartDto cartDto) {
        Cart cart = cartService.addCart(CartMapper.INSTANCE.convertToCart(cartDto));
        return CartMapper.INSTANCE.convertToCartDto(cart);
    }

    /**
     * GET /{id} : get cart by specified id
     * @param cartId the id of the cart to retrieve
     * @return the cart with defined id in body
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public CartDto findCartById(@PathVariable(value = "id") Integer cartId) {
        Cart cart = cartService.findCartById(cartId);
        return CartMapper.INSTANCE.convertToCartDto(cart);
    }

    /**
     * DELETE /{id} : delete cart by specified id
     * @param cartId the id of the cart to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteCartById(@PathVariable(value = "id") Integer cartId) {
        cartService.deleteCart(cartId);
    }

    /**
     * PUT : update an existing cart
     * @param cartDto the CartDto to update cart in database
     * @return updates cart in body
     */
    @PutMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public CartDto updateCart(@RequestBody CartDto cartDto) {
        Cart cart = cartService.updateCart(CartMapper.INSTANCE.convertToCart(cartDto));
        return CartMapper.INSTANCE.convertToCartDto(cart);
    }
}
