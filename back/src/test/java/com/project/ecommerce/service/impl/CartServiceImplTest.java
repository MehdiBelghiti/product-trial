package com.project.ecommerce.service.impl;


import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

  @InjectMocks
  private CartServiceImpl cartService;

  @Mock
  private CartRepository cartRepository;

  @Mock
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddProductToCart() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    Cart cart = new Cart();
    Product product = Product.builder()
        .id(1L)
        .code("code")
        .name("name")
        .description("description")
        .image("image")
        .category("category")
        .price(100.0)
        .quantity(10)
        .internalReference("internalReference")
        .shellId(1L)
        .rating(5)
        .build();
    product.setId(productId);
    product.setQuantity(10);

    when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    cartService.addProductToCart(userId, productId, quantity);

    verify(cartRepository, times(1)).save(cart);
    assertEquals(1, cart.getItems().size());
    assertEquals(quantity, cart.getItems().get(0).getQuantity());
  }

  @Test
  void testRemoveProductFromCart() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    Cart cart = new Cart();
    CartItem cartItem = new CartItem();
    cartItem.setProduct(Product.builder()
        .id(1L)
        .code("code")
        .name("name")
        .description("description")
        .image("image")
        .category("category")
        .price(100.0)
        .quantity(10)
        .internalReference("internalReference")
        .shellId(1L)
        .rating(5)
        .build());
    cartItem.getProduct().setId(productId);
    cartItem.setQuantity(quantity);
    cart.getItems().add(cartItem);

    when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

    cartService.removeProductFromCart(userId, productId, quantity);

    verify(cartRepository, times(1)).save(cart);
    assertTrue(cart.getItems().isEmpty());
  }
}
