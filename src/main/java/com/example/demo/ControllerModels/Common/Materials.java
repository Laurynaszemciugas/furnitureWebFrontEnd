package com.example.demo.ControllerModels.Common;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Materials {

    private Long id;
    private String materialName;
    private Long amountUsed;
    private double unitPrice;
    private String unit;

}
