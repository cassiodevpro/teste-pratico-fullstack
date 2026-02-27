package com.factory.production_optimizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factory.production_optimizer.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    Product findByCode(String code);
    java.util.List<Product> findByNameContainingIgnoreCase(String name);
    java.util.List<Product> findByCodeContainingIgnoreCase(String code);
    java.util.List<Product> findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(String name, String code);

    default java.util.List<Product> filter(String name, String code) {
        if (name != null && !name.isEmpty() && code != null && !code.isEmpty()) {
            return findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(name, code);
        } else if (name != null && !name.isEmpty()) {
            return findByNameContainingIgnoreCase(name);
        } else if (code != null && !code.isEmpty()) {
            return findByCodeContainingIgnoreCase(code);
        }
        return findAll();
    }
}
