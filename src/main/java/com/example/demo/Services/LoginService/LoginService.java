package com.example.demo.Services.LoginService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


@Service
@Setter
public class LoginService {

    HttpCallLogic httpCallLogic;
    SessionCrafter sessionCrafter;
    public LoginService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
        this.sessionCrafter = new SessionCrafter();
    }

    public void getJWTToken(User user){



            String jwt  = httpCallLogic.checkResponseNoGetValue(
                    httpCallLogic.HttpCall("auth/signin", HttpMethod.POST, user, ErrorResponse.class, false),sessionCrafter.extractSession("lastSeen",String.class));


            if(jwt != null){
                sessionCrafter.createSession("JWT",jwt);
            }
            else{
                sessionCrafter.createSession("JWT",null);
            }



    }

}
