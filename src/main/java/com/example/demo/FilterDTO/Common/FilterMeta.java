package com.example.demo.FilterDTO.Common;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public  class  FilterMeta {

    String value;
    String fieldName;
    Object ifNull;


}
