package com.factory.production_optimizer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.factory.production_optimizer.exception.ResourceNotFoundException;
import com.factory.production_optimizer.model.Product;
import com.factory.production_optimizer.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Transactional
    public Product create(Product product) {
        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, Product updated) {
        Product existing = findById(id);
        existing.setCode(updated.getCode());
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setIngredients(updated.getIngredients());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Product existing = findById(id);
        repository.delete(existing);
    }
}
