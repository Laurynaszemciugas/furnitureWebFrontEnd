package com.example.demo.DTOS.Client;

import com.example.demo.Enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientCategorys {


    private Category category;
    private Long count;


}
