package com.example.demo.ControllerModels.Products;


import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductPageData {




    List<ProductFeedModel> productFeedModelList;


    LocalDateTime createdAt;

    public boolean isDataStale(){

        if (createdAt == null) {
            return true;
        }


        long minutes = ChronoUnit.MINUTES.between(createdAt, LocalDateTime.now());

        return minutes > 1;
    }


}
