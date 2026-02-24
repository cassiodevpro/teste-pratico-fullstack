package com.factory.production_optimizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factory.production_optimizer.model.ProductIngredient;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long> {
}
