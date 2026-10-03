package com.example.demo.Entity.ProductJoin;

import com.example.demo.Entity.Materials;
import com.example.demo.Entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductMaterials {

    private Long id;
    private String nameForRefrence;
    private boolean newMaterial;
    private Long amountUsed;
    private double unitPrice;
    private Materials materials;
    @JsonIgnore
    private Product product;
    @JsonIgnore
    private User user;

    private LocalDateTime created;


}
