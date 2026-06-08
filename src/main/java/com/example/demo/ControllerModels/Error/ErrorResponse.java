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
public class ErrorResponse {

    String message;
    Warnings warning;



}
