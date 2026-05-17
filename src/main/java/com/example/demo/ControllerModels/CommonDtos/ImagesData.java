package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.ImageLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagesData {

    private Long id;
    private String uuId;
    private String imageName;
    private String imageUrl;
    private String imageType;

    private ImageLogic imageLogic;

    private byte[] imageData;
    @JsonIgnore
    private Product product;
    @JsonIgnore
    private User user;

    private LocalDateTime created;

}
