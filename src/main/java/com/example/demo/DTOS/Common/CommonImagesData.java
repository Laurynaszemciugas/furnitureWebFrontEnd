package com.example.demo.DTOS.Common;

import com.example.demo.Enums.ImageLogic;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonImagesData {

    private Long id;
    private String uuId;
    private String imageName;

    private String imageUrl;
    private String imageType;
    private ImageLogic imageLogic;
    @ToString.Exclude
    private byte[] imageData;


}
