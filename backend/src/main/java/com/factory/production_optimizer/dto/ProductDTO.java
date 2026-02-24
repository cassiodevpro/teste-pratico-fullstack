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
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private BigDecimal price;
    private List<ProductIngredientDTO> ingredients;
}
