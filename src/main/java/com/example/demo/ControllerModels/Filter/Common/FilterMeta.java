package com.example.demo.ControllerModels.Filter.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class  FilterMeta {

    String value;
    String fieldName;
    Object ifNull;


}
