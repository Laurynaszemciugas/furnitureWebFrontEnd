package com.example.demo.ControllerModels.CommonDtos;


import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Materials {

    private Long id;
    private String materialName;
    private Long inStock;
    private Long minThresHold;
    private Stock stock;
    private ActiveInactive enabled;
    private double materialWeight;
    private double unitPrice;

    private String unit;
    private String description;
    private MaterialType materialType;

    @JsonIgnore
    private User user;


    private LocalDateTime created;
    private List<MaterialImageData> images = new ArrayList<>();

}
