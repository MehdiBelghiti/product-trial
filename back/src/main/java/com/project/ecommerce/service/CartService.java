package com.project.ecommerce.service;

import com.project.ecommerce.model.Cart;

public interface CartService {
  Cart getCartByUser(Long userId);
  void addProductToCart(Long userId, Long productId, int quantity);
  void removeProductFromCart(Long userId, Long productId, int quantity);
}
