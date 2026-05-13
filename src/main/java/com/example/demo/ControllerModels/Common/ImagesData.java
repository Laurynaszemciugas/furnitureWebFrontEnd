package com.example.demo.ControllerModels.Common;

import com.example.demo.Enums.ImageLogic;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImagesData {

    private Long id;
    private String uuId;
    private String imageName;
    private String imageUrl;
    private String imageType;
    private ImageLogic imageLogic;
    private byte[] imageData;

}
