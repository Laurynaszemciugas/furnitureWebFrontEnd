package com.example.demo.ControllerModels.CommonDtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductComments {

    private Long id;
    private String commenter;
    private String comment;
    private Long review;

}
