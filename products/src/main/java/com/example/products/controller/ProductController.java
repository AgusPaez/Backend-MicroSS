package com.example.products.controller;

import com.example.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.products.model.Product;
import com.example.products.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Métodos

    // Método para traer todos los productos
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }

    // Método para traer un producto por ID
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // Método para crear un producto
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            Product createdProduct = productService.addProduct(product);
            return ResponseEntity.ok(createdProduct);
        }
    }

    // Método para borrar un producto por ID
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long productId) {
        this.productService.deleteProductById(productId);
    }

    // Método para actualizar un producto por ID
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        this.productService.updateProduct(productId, product);
    }
}
