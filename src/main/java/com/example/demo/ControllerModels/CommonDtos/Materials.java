package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.Enabled;
import com.example.demo.Enums.Stock;
import lombok.*;

import java.time.LocalDate;

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
    private Enabled enabled;
    private double materialWeight;
    private double unitPrice;
    private String unit;
    private LocalDate createdAt;

}
