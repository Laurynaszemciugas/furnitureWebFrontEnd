package com.example.demo.ControllerModels.CommonDtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    private Long id;
    private String commenter;
    private String comment;
    private Long review;

    @JsonIgnore
    private Product product;
    @JsonIgnore
    private User user;

    private LocalDateTime created;

}
