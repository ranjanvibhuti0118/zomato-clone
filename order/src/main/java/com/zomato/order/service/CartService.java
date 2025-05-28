package com.zomato.order.service;

import com.zomato.order.dto.AddToCartRequest;
import com.zomato.order.dto.UpdateCartItemRequest;
import com.zomato.order.entity.Cart;
import com.zomato.order.entity.CartItem;
import com.zomato.order.exception.CartNotFoundException;
import com.zomato.order.exception.ItemNotFoundException;
import com.zomato.order.repository.CartItemRepository;
import com.zomato.order.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Cart addToCart(AddToCartRequest request) {
        Cart cart = cartRepository.findByUserId(getCurrentUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(getCurrentUserId());
                    newCart.setRestaurantId(request.getRestaurantId());
                    return newCart;
                });

        CartItem cartItem = new CartItem();
        cartItem.setMenuItemId(request.getMenuItemId());
        cartItem.setMenuItemName(request.getMenuItemName());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setUnitPrice(request.getUnitPrice());
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        cart.setTotalAmount(calculateTotalAmount(cart));

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartItem(UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByUserId(getCurrentUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(request.getCartItemId()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item not found in cart"));

        item.setQuantity(request.getQuantity());
        cart.setTotalAmount(calculateTotalAmount(cart));

        return cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(Long itemId) {
        Cart cart = cartRepository.findByUserId(getCurrentUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new ItemNotFoundException("Item not found in cart");
        }

        cart.setTotalAmount(calculateTotalAmount(cart));
        cartRepository.save(cart);
    }

    public Cart getCurrentCart() {
        return cartRepository.findByUserId(getCurrentUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    @Transactional
    public void clearCart() {
        Cart cart = cartRepository.findByUserId(getCurrentUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private Long getCurrentUserId() {
        // TODO: Implement user context retrieval
        return 1L; // Temporary implementation
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
