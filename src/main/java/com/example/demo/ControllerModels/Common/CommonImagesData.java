package com.example.demo.ControllerModels.Common;

import com.example.demo.Enums.ImageLogic;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonImagesData {

    private Long id;
    private String uuId;
    private String imageName;
    @ToString.Exclude
    private String imageUrl;
    private String imageType;
    private ImageLogic imageLogic;
    @ToString.Exclude
    private byte[] imageData;
    private LocalDateTime created;


}
