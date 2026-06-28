package com.example.demo.ErrorHandling.Exseptions;


import com.example.demo.ControllerModels.Error.FrontEndError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class HttpCallException extends RuntimeException {

    private final FrontEndError error;



}
