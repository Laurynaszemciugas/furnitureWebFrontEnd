package com.example.demo.Entity.ProductJoin;

import com.example.demo.Entity.Materials;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductMultiple;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
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
    private ProductMultiple productMultiple;

    @JsonIgnore
    private User user;

    private LocalDateTime created;


}
