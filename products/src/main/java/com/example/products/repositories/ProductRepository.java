package com.example.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.products.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
