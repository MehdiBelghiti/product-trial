package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateProduct() {
    ProductDTO productDTO = new ProductDTO(1L, "code", "name", "description", "image", "category", 100.0, 10, "internalReference", 1L, null, 5, null, null);
    when(productService.save(productDTO)).thenReturn(Product.builder()
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
        .build()) ;

    ResponseEntity<GenericResponse> response = productController.createProduct(productDTO);

    verify(productService, times(1)).save(productDTO);
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  void testGetAllProducts() {
    when(productService.findAll()).thenReturn(List.of(ProductDTO.builder().build()));

    ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

    verify(productService, times(1)).findAll();
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void testGetProductById() {
    Long productId = 1L;
    when(productService.findById(productId)).thenReturn(ProductDTO.builder().build());

    ResponseEntity<ProductDTO> response = productController.getProductById(productId);

    verify(productService, times(1)).findById(productId);
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  void testDeleteProduct() {
    Long productId = 1L;

    ResponseEntity<GenericResponse> response = productController.deleteProduct(productId);

    verify(productService, times(1)).delete(productId);
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  void testUpdateProduct() {
    Long productId = 1L;
    ProductDTO productDTO = new ProductDTO(productId, "code", "name", "description", "image", "category", 100.0, 10, "internalReference", 1L, null, 5, null, null);
    when(productService.update(productId, productDTO)).thenReturn(Product.builder()
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

    ResponseEntity<GenericResponse> response = productController.updateProduct(productId, productDTO);

    verify(productService, times(1)).update(productId, productDTO);
    assertEquals(200, response.getStatusCodeValue());
  }
}