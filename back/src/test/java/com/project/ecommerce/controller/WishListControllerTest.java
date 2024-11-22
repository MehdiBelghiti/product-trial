package com.project.ecommerce.controller;

import com.project.ecommerce.model.WishList;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishListControllerTest {

  @InjectMocks
  private WishListController wishListController;

  @Mock
  private WishListService wishListService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetWishListByUser() {
    Long userId = 1L;
    WishList wishList = new WishList();
    when(wishListService.getWishListByUser(userId)).thenReturn(wishList);

    ResponseEntity<?> response = wishListController.getWishList(userId);

    verify(wishListService, times(1)).getWishListByUser(userId);
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
  }

  @Test
  void testAddProductToWishList() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    ResponseEntity<GenericResponse> response = wishListController.addToWishList(userId, productId, quantity);

    verify(wishListService, times(1)).addProductToWishList(userId, productId, quantity);
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  void testRemoveProductFromWishList() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    ResponseEntity<GenericResponse> response = wishListController.removeFromWishList(userId, productId, quantity);

    verify(wishListService, times(1)).removeProductFromWishList(userId, productId, quantity);
    assertEquals(200, response.getStatusCodeValue());
  }
}
