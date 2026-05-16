package com.example.demo.ControllerModels.CommonDtos.ProductJoin;


import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.Tags;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTags {

    private Long id;
    @JsonIgnore
    private Product product;

    private Tags tags;

    private User user;

    private LocalDateTime created;

}
