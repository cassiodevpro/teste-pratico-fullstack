package com.factory.production_optimizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factory.production_optimizer.model.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    boolean existsByCode(String code);
    RawMaterial findByCode(String code);
}
