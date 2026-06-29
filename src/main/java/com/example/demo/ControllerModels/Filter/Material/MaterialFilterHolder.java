package com.example.demo.ControllerModels.Filter.Material;

import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialFilterHolder {


    private MaterialType materialTypeChoice = MaterialType.ALL;
    private ActiveInactive activeInactive = ActiveInactive.ACTIVE;
    private Long stockAmountChoice = 0L;
    private Long minThresholdChoice = 0L;
    private Double unitPriceChoice = 0.0;
    private LocalDate fromDateChoice = LocalDate.of(1000,12,12);
    private LocalDate todDateChoice = LocalDate.of(1000,12,12);
    private Stock stockChoice = Stock.ALL;
    private String promtChoice = "ALL";
    private int page = 0;
    private int pageCount = 5;


}
