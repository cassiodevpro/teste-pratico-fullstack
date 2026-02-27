package com.factory.production_optimizer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.factory.production_optimizer.dto.ProductDTO;
import com.factory.production_optimizer.dto.ProductIngredientDTO;
import com.factory.production_optimizer.dto.RawMaterialDTO;
import com.factory.production_optimizer.exception.ResourceNotFoundException;
import com.factory.production_optimizer.model.Product;
import com.factory.production_optimizer.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<ProductDTO> filterDTO(String name, String code) {
        return repository.filter(name, code).stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public List<ProductDTO> findAllDTO() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        if (product.getIngredients() != null) {
            dto.setIngredients(product.getIngredients().stream().map(this::toIngredientDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    private ProductIngredientDTO toIngredientDTO(com.factory.production_optimizer.model.ProductIngredient ingredient) {
        ProductIngredientDTO dto = new ProductIngredientDTO();
        dto.setId(ingredient.getId());
        dto.setRequiredQuantity(ingredient.getRequiredQuantity());
        if (ingredient.getRawMaterial() != null) {
            RawMaterialDTO rm = new RawMaterialDTO();
            rm.setId(ingredient.getRawMaterial().getId());
            rm.setCode(ingredient.getRawMaterial().getCode());
            rm.setName(ingredient.getRawMaterial().getName());
            rm.setStockQuantity(ingredient.getRawMaterial().getStockQuantity());
            dto.setRawMaterial(rm);
        }
        return dto;
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

        // Atualiza ingredientes manualmente
        if (existing.getIngredients() != null) {
            existing.getIngredients().clear();
        }
        if (updated.getIngredients() != null) {
            for (var ing : updated.getIngredients()) {
                ing.setProduct(existing); // garante vínculo
                existing.getIngredients().add(ing);
            }
        }
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Product existing = findById(id);
        repository.delete(existing);
    }
}
