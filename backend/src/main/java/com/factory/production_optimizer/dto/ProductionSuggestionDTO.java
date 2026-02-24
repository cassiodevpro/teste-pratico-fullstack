package com.factory.production_optimizer.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionSuggestionDTO {
    private List<SuggestedProduct> suggestedProducts;
    private BigDecimal totalExpectedRevenue;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestedProduct {
        private String productName;
        private int quantityToProduce;
        private BigDecimal totalRevenue;
    }
}
