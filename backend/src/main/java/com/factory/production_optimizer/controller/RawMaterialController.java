package com.factory.production_optimizer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factory.production_optimizer.model.RawMaterial;
import com.factory.production_optimizer.service.RawMaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/raw-materials")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RawMaterialController {
    private final RawMaterialService service;

    @GetMapping
    public List<RawMaterial> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RawMaterial findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial rawMaterial) {
        return service.create(rawMaterial);
    }

    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id, @RequestBody RawMaterial rawMaterial) {
        return service.update(id, rawMaterial);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
