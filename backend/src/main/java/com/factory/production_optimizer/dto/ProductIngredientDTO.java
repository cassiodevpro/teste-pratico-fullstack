package com.factory.production_optimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredientDTO {
    private Long id;
    private RawMaterialDTO rawMaterial;
    private Double requiredQuantity;
}
