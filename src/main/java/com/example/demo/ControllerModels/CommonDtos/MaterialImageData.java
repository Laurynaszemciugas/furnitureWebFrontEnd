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
public class MaterialImageData {

    private Long id;
    private String uuId;
    private String imageName;
    private String imageUrl;
    private ImageLogic imageLogic;

    private byte[] imageData;

    private Materials materials;

    private User user;

    private LocalDateTime created;

}
