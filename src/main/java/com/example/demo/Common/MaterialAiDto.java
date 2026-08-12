package com.example.demo.Common;

import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialGrainPatterns;
import com.example.demo.Enums.MaterialTextures;
import com.example.demo.Enums.MaterialType;
import com.vaadin.flow.component.HasValue;
import lombok.*;

import java.lang.reflect.Field;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialAiDto {

    private String materialName = "None";
    private MaterialType materialType = MaterialType.ALL;
    private String materialUrl = "None";

    private String description = "None";
    private String careInstructions = "None";

    private String materialColor = "None";
    private MaterialType materialFinishType = MaterialType.ALL;
    private MaterialTextures materialTexture = MaterialTextures.GLOSSY;
    private MaterialGrainPatterns materialGrainPattern = MaterialGrainPatterns.COARSE_GRAIN;

    private Double materialPrice = 0.0;
    private Double materialUnitWeight = 0.0;
    private Long materialMinThreshold = 0L;
    private Long materialStock = 0L;
    private String materialUnit = "None";

    private LocalDate deliveryDate = LocalDate.of(1000,12,12);
    private Long defaultRestockPeriod = 0L;



}



