package com.example.demo.ControllerModels.Common;

import com.example.demo.Enums.ImageLogic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonImagesData {

    private Long id;
    private String uuId;
    private String imageName;
    private String imageUrl;
    private String imageType;
    private ImageLogic imageLogic;
    private byte[] imageData;
    private LocalDateTime created;


}
