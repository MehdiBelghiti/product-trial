package com.project.ecommerce.controller;

import com.project.ecommerce.model.Cart;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

  @InjectMocks
  private CartController cartController;

  @Mock
  private CartService cartService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetCartByUser() {
    Long userId = 1L;
    Cart cart = new Cart();
    when(cartService.getCartByUser(userId)).thenReturn(cart);

    ResponseEntity<Cart> response = cartController.getCart(userId);

    verify(cartService, times(1)).getCartByUser(userId);
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
  }

  @Test
  void testAddProductToCart() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    ResponseEntity<GenericResponse> response = cartController.addToCart(userId, productId, quantity);

    verify(cartService, times(1)).addProductToCart(userId, productId, quantity);
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  void testRemoveProductFromCart() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    ResponseEntity<GenericResponse> response = cartController.removeFromCart(userId, productId, quantity);

    verify(cartService, times(1)).removeProductFromCart(userId, productId, quantity);
    assertEquals(200, response.getStatusCodeValue());
  }
}