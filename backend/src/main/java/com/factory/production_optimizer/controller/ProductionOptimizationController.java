package com.factory.production_optimizer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factory.production_optimizer.dto.ProductionSuggestionDTO;
import com.factory.production_optimizer.service.ProductionOptimizationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductionOptimizationController {
    private final ProductionOptimizationService service;

    @GetMapping("/optimize")
    public ProductionSuggestionDTO optimize() {
        return service.suggestProduction();
    }
}
