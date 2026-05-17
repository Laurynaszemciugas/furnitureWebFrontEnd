package com.example.demo.ControllerModels.CommonDtos;


import com.example.demo.Enums.Tags;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User user;

    private LocalDateTime created;

}
