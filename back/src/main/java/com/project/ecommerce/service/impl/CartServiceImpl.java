package com.project.ecommerce.service.impl;

import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Cart getCartByUser(Long userId) {
    return cartRepository.findByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
  }

  @Override
  public void addProductToCart(Long userId, Long productId, int quantity) {
    Cart cart = cartRepository.findByUserId(userId)
        .orElseGet(() -> createCartForUser(userId));

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

    CartItem existingCartItem = cart.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .orElse(null);

    int totalQuantity = quantity;
    if (existingCartItem != null) {
      totalQuantity += existingCartItem.getQuantity();
    }

    if (product.getQuantity() < totalQuantity) {
      throw new IllegalArgumentException("Insufficient product quantity available");
    }

    if (existingCartItem != null) {
      existingCartItem.setQuantity(totalQuantity);
    } else {
      CartItem cartItem = new CartItem();
      cartItem.setCart(cart);
      cartItem.setProduct(product);
      cartItem.setQuantity(quantity);
      cart.getItems().add(cartItem);
    }

    cartRepository.save(cart);

    // we would probably reduce the product quantity in here
    // i figured it should more reasonably be at a checkout time rather than ATC (add to cart) time
  }

  @Override
  public void removeProductFromCart(Long userId, Long productId, int quantity) {
    Cart cart = getCartByUser(userId);

    CartItem existingCartItem = cart.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart with id: " + productId));

    int remainingQuantity = existingCartItem.getQuantity() - quantity;
      if (remainingQuantity > 0) {
      existingCartItem.setQuantity(remainingQuantity);
    } else {
      cart.getItems().remove(existingCartItem);
    }

    cartRepository.save(cart);

    // we would probably increase the product quantity in here
  }

  private Cart createCartForUser(Long userId) {
    Cart cart = new Cart();
    cart.setUser(new User());
    cart.getUser().setId(userId);
    return cartRepository.save(cart);
  }
}
