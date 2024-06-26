package com.example.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.products.model.Product;
import com.example.products.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Método para obtener todos los productos
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // Método para obtener un producto por su ID
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Método para crear un producto
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Método para borrar un producto por ID
    public ResponseEntity<Object> deleteProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.deleteById(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    // Método para modificar un producto
    public Product updateProduct(Long productId, Product newProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(productId);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setSku(newProduct.getSku());
            existingProduct.setName(newProduct.getName());
            existingProduct.setDescripcion(newProduct.getDescripcion());
            existingProduct.setPrice(newProduct.getPrice());
            existingProduct.setStatus(newProduct.getStatus());

            return productRepository.save(existingProduct);
        } else {
            // Manejo de error
            throw new IllegalArgumentException("No se encontró el producto con el ID proporcionado");
        }
    }
}
