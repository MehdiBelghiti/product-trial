package com.project.ecommerce.service.impl;


import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.WishList;
import com.project.ecommerce.model.WishListItem;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishListServiceImplTest {

  @InjectMocks
  private WishListServiceImpl wishListService;

  @Mock
  private WishListRepository wishListRepository;

  @Mock
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddProductToWishList() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    WishList wishList = new WishList();
    Product product = Product.builder().build();
    product.setId(productId);
    product.setQuantity(10);

    when(wishListRepository.findByUserId(userId)).thenReturn(Optional.of(wishList));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    wishListService.addProductToWishList(userId, productId, quantity);

    verify(wishListRepository, times(1)).save(wishList);
    assertEquals(1, wishList.getItems().size());
    assertEquals(quantity, wishList.getItems().get(0).getQuantity());
  }

  @Test
  void testRemoveProductFromWishList() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    WishList wishList = new WishList();
    WishListItem wishListItem = new WishListItem();
    wishListItem.setProduct(Product.builder().build());
    wishListItem.getProduct().setId(productId);
    wishListItem.setQuantity(quantity);
    wishList.getItems().add(wishListItem);

    when(wishListRepository.findByUserId(userId)).thenReturn(Optional.of(wishList));

    wishListService.removeProductFromWishList(userId, productId, quantity);

    verify(wishListRepository, times(1)).save(wishList);
    assertTrue(wishList.getItems().isEmpty());
  }
}
