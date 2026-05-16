package com.example.demo.ControllerModels.CommonDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMaterials {

    private Long id;
    private String materialName;
    private Long amountUsed;
    private double unitPrice;
    private Materials materials;
    private Product product;


}
