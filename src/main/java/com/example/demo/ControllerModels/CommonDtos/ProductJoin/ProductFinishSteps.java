package com.example.demo.ControllerModels.CommonDtos.ProductJoin;


import com.example.demo.ControllerModels.CommonDtos.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFinishSteps {


    private Long id;
    private Long step;
    private String stepName;
    private String stepDescription;
    private Product product;



}
