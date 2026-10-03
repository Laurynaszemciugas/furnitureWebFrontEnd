package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
