package com.factory.production_optimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialDTO {
    private Long id;
    private String code;
    private String name;
    private Double stockQuantity;
}
