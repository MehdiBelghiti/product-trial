package com.project.ecommerce.service.impl;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.mapper.ProductMapper;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveProduct() {
    ProductDTO productDTO = new ProductDTO(1L, "code", "name", "description", "image", "category", 100.0, 10, "internalReference", 1L, null, 5, null, null);
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
    when(productMapper.ProductDTOtoProduct(productDTO)).thenReturn(product);
    when(productRepository.save(product)).thenReturn(product);

    Product savedProduct = productService.save(productDTO);

    verify(productRepository, times(1)).save(product);
    assertNotNull(savedProduct);
  }

  @Test
  void testFindAllProducts() {
    List<Product> products = List.of(Product.builder()
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
    when(productRepository.findAll()).thenReturn(products);
    when(productMapper.ProductToProductDTO(any(Product.class))).thenReturn(ProductDTO.builder()
        .build());

    List<ProductDTO> productDTOs = productService.findAll();

    verify(productRepository, times(1)).findAll();
    assertEquals(1, productDTOs.size());
  }

  @Test
  void testFindProductById() {
    Long productId = 1L;
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
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productMapper.ProductToProductDTO(product)).thenReturn(ProductDTO.builder().build());

    ProductDTO productDTO = productService.findById(productId);

    verify(productRepository, times(1)).findById(productId);
    assertNotNull(productDTO);
  }

  @Test
  void testUpdateProduct() {
    Long productId = 1L;
    ProductDTO productDTO = new ProductDTO(productId, "code", "name", "description", "image", "category", 100.0, 10, "internalReference", 1L, null, 5, null, null);
    Product existingProduct = Product.builder().build();
    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productMapper.ProductDTOtoProduct(productDTO)).thenReturn(existingProduct);
    when(productRepository.save(existingProduct)).thenReturn(existingProduct);

    Product updatedProduct = productService.update(productId, productDTO);

    verify(productRepository, times(1)).save(existingProduct);
    assertNotNull(updatedProduct);
  }

  @Test
  void testDeleteProduct() {
    Long productId = 1L;
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
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    productService.delete(productId);

    verify(productRepository, times(1)).delete(product);
  }
}