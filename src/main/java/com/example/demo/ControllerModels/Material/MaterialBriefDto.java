package com.example.demo.ControllerModels.Material;

import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialBriefDto {

    private String imageUrl;
    private String name;
    private String description;
    private ActiveInactive activeInactive;
    private MaterialType materialType;
    private Stock stock;
    private LocalDateTime created;
}
