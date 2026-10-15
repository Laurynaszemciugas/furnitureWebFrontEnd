package com.example.demo.Entity;

import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
public class ProductComments {

    private Long id;
    private String commenter;
    private String comment;
    private Long review;

    private Product product;


    private User user;

    private LocalDateTime created;

}
