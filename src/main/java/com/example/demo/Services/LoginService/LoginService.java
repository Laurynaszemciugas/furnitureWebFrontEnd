package com.example.demo.Services.LoginService;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.ServerDBCall.LoginCalls.LoginCalls;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    LoginCalls loginCalls;
    SessionCrafter sessionCrafter;

    public LoginService(LoginCalls loginCalls) {
        this.loginCalls = loginCalls;
        this.sessionCrafter = new SessionCrafter();
    }

    @SneakyThrows
    public void getJWTToken(User user){
        Object answer = loginCalls.getJWT(user);

        if(answer instanceof String value){
            sessionCrafter.createSession("JWT", value);
        }
        else{
            System.out.println("Password or gmail is incorrect");
        }

    }

}
