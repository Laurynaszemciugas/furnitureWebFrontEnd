package com.example.demo.ControllerModels.Filter.Material;

import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialFilterHolder {


    MaterialType materialTypeChoice;
    Status statusChoice;
    Long stockAmountChoice;
    Long minThresholdChoice;
    Double unitPriceChoice;
    LocalDate fromDateChoice;
    LocalDate todDateChoice;
    String promtChoice;


}
