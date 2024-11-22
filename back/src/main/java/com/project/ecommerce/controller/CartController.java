package com.project.ecommerce.controller;

import com.project.ecommerce.model.Cart;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @GetMapping("/{userId}")
  public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
    return ResponseEntity.ok(cartService.getCartByUser(userId));
  }

  @PostMapping("/add/{userId}/{productId}")
  public ResponseEntity<GenericResponse> addToCart(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
    cartService.addProductToCart(userId, productId, quantity);
    return ResponseEntity.ok(new GenericResponse("Product added to cart successfully"));
  }

  @PostMapping("/reduce/{userId}/{productId}")
  public ResponseEntity<GenericResponse> removeFromCart(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
    cartService.removeProductFromCart(userId, productId, quantity);
    return ResponseEntity.ok(new GenericResponse("Product removed from cart successfully"));
  }
}
