package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.ImageLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImageData {

    private Long id;
    private String uuId;
    private String imageName;
    private String imageUrl;
    private String imageType;
    private ImageLogic imageLogic;

    private byte[] imageData;

    @JsonIgnore
    private Product product;

    private User user;

    private LocalDateTime created;

}
