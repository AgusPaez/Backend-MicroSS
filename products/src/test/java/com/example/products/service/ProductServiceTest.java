package com.example.products.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.products.model.Product;
import com.example.products.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProducts() {
        Product product = new Product(1L, "Sku03", "PCGAMERTEST", "Desc1Test", 100.0, true);
        List<Product> products = Collections.singletonList(product);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getProducts();
        assertEquals(1, result.size());
        assertEquals("Sku03", result.get(0).getSku());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        Product product = new Product(productId, "Sku03", "PCGAMERTEST", "Desc1Test", 100.0, true);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Object> response = productService.deleteProductById(productId);

        verify(productRepository, times(1)).deleteById(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());
    }
}
