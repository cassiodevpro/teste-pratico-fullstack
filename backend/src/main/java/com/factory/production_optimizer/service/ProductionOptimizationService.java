package com.factory.production_optimizer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.factory.production_optimizer.dto.ProductionSuggestionDTO;
import com.factory.production_optimizer.model.Product;
import com.factory.production_optimizer.model.ProductIngredient;
import com.factory.production_optimizer.model.RawMaterial;
import com.factory.production_optimizer.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductionOptimizationService {
    private final ProductRepository productRepository;

    public ProductionSuggestionDTO suggestProduction() {
        List<Product> products = productRepository.findAll();
        Map<Long, Double> stockMap = new HashMap<>();
        // Inicializa o estoque simulado
        for (Product p : products) {
            for (ProductIngredient pi : p.getIngredients()) {
                RawMaterial rm = pi.getRawMaterial();
                stockMap.put(rm.getId(), rm.getStockQuantity());
            }
        }

        // Calcula o máximo de cada produto e valor potencial
        List<ProductPotential> potentials = new ArrayList<>();
        for (Product p : products) {
            int maxUnits = maxUnitsPossible(p, stockMap);
            BigDecimal totalValue = p.getPrice().multiply(BigDecimal.valueOf(maxUnits));
            potentials.add(new ProductPotential(p, maxUnits, totalValue));
        }
        // Ordena por maior valor potencial
        potentials.sort(Comparator.comparing(ProductPotential::getTotalValue).reversed());

        // Simula produção greedy
        Map<Long, Double> simulatedStock = new HashMap<>(stockMap);
        List<ProductionSuggestionDTO.SuggestedProduct> suggestions = new ArrayList<>();
        BigDecimal totalExpectedRevenue = BigDecimal.ZERO;
        for (ProductPotential pot : potentials) {
            int possible = maxUnitsPossible(pot.product, simulatedStock);
            if (possible > 0) {
                // Abate estoque simulado
                for (ProductIngredient pi : pot.product.getIngredients()) {
                    Long rmId = pi.getRawMaterial().getId();
                    double used = pi.getRequiredQuantity() * possible;
                    simulatedStock.put(rmId, simulatedStock.get(rmId) - used);
                }
                BigDecimal revenue = pot.product.getPrice().multiply(BigDecimal.valueOf(possible));
                suggestions.add(new ProductionSuggestionDTO.SuggestedProduct(
                        pot.product.getName(), possible, revenue
                ));
                totalExpectedRevenue = totalExpectedRevenue.add(revenue);
            }
        }
        return new ProductionSuggestionDTO(suggestions, totalExpectedRevenue);
    }

    private int maxUnitsPossible(Product product, Map<Long, Double> stock) {
        int max = Integer.MAX_VALUE;
        for (ProductIngredient pi : product.getIngredients()) {
            Long rmId = pi.getRawMaterial().getId();
            double available = stock.getOrDefault(rmId, 0.0);
            int possible = (int) (available / pi.getRequiredQuantity());
            if (possible < max) max = possible;
        }
        return max > 0 ? max : 0;
    }

    private static class ProductPotential {
        Product product;
        int maxUnits;
        BigDecimal totalValue;
        ProductPotential(Product product, int maxUnits, BigDecimal totalValue) {
            this.product = product;
            this.maxUnits = maxUnits;
            this.totalValue = totalValue;
        }
        public BigDecimal getTotalValue() { return totalValue; }
    }
}
