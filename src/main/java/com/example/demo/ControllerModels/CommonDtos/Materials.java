package com.example.demo.ControllerModels.CommonDtos;


import com.example.demo.Enums.Enabled;
import com.example.demo.Enums.EnabledDisabled;
import com.example.demo.Enums.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

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
    private EnabledDisabled enabledDisabled;
    @JsonIgnore
    private User user;

    private LocalDateTime created;

}
