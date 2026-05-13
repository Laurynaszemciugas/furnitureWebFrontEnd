package com.example.demo.ControllerModels.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridMaterials {

    private long id;
    private String materialName;
    private long amountUsed;
    private String unit;

}
