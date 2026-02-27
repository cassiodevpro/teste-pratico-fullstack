package com.factory.production_optimizer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.factory.production_optimizer.exception.ResourceNotFoundException;
import com.factory.production_optimizer.model.RawMaterial;
import com.factory.production_optimizer.repository.RawMaterialRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RawMaterialService {
    private final RawMaterialRepository repository;

    public List<RawMaterial> filter(String name, String code) {
        if (name != null && !name.isEmpty() && code != null && !code.isEmpty()) {
            return repository.findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(name, code);
        } else if (name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(name);
        } else if (code != null && !code.isEmpty()) {
            return repository.findByCodeContainingIgnoreCase(code);
        }
        return findAll();
    }

    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    public RawMaterial findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RawMaterial not found: " + id));
    }

    @Transactional
    public RawMaterial create(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    @Transactional
    public RawMaterial update(Long id, RawMaterial updated) {
        RawMaterial existing = findById(id);
        existing.setCode(updated.getCode());
        existing.setName(updated.getName());
        existing.setStockQuantity(updated.getStockQuantity());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        RawMaterial existing = findById(id);
        repository.delete(existing);
    }
}
