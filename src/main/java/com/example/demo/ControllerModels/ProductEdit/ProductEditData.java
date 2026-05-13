package com.example.demo.ControllerModels.ProductEdit;

import com.example.demo.ControllerModels.Common.ProductDataEditAddDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEditData {

    private ProductDataEditAddDto productEditDto;


    LocalDateTime createdAt;

    public boolean isDataStale(){

        if (createdAt == null) {
            return true;
        }


        long minutes = ChronoUnit.MINUTES.between(createdAt, LocalDateTime.now());

        return minutes > 1;
    }

}
