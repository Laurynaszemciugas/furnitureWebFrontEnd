package com.example.demo.Entity;

import com.example.demo.Enums.SpecTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
public class ExtraDetails {

    private Long id;
    private String specName;
    private String specDescription;

    private SpecTypes specTypes;

    @JsonIgnore
    private Product product;

    @JsonIgnore
    private ProductMultiple productMultiple;

    @JsonIgnore
    private User user;

    private LocalDateTime created;

}
