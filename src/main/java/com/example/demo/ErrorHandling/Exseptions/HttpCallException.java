package com.example.demo.ErrorHandling.Exseptions;


import com.example.demo.DTOS.Error.FrontEndError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpCallException extends RuntimeException {

    private final FrontEndError error;



}
