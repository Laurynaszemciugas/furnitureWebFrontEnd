package com.example.demo.DTOS.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetWithCode {

    private String code;
    private String password;
    private String reEnterPassword;
    private String gmail;


}
