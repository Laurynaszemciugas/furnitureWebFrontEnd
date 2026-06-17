package com.example.demo.ControllerModels.Material;

import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
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
    ActiveInactive activeInactive;
    Long stockAmountChoice;
    Long minThresholdChoice;
    Double unitPriceChoice;
    LocalDate fromDateChoice;
    LocalDate todDateChoice;
    Stock stockChoice;
    String promtChoice;


}
