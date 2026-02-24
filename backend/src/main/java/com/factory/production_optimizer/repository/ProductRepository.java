package com.factory.production_optimizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factory.production_optimizer.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    Product findByCode(String code);
}
