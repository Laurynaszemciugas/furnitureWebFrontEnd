package com.example.demo.ControllerModels.Error;

import com.example.demo.Enums.Warnings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FrontEndError {

    private String message;
    private String instructions;
    private Integer status;

}
