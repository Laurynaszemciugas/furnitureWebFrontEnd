package com.example.demo.ControllerModels.CommonDtos.ProductJoin;

import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMaterials {

    private Long id;
    private String nameForRefrence;
    private boolean newMaterial;
    private Long amountUsed;
    private double unitPrice;
    private Materials materials;
    @JsonIgnore
    private Product product;

    private User user;

    private LocalDateTime created;


}
