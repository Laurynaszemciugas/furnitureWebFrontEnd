package com.example.demo.Services.LoginService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.ControllerModels.CommonDtos.UserSettings;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@Setter
public class LoginService {

    HttpCallLogic httpCallLogic;
    SessionCrafter sessionCrafter;

    Consumer<Boolean> success;

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

    public void createSettings(){


        UserSettings userSettings = httpCallLogic.HttpCall("user/getUserSettings", HttpMethod.GET, null, UserSettings.class, false);

        sessionCrafter.createSession("settings",userSettings);





    }






    public void googleLogin(String sub) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("auth/google", HttpMethod.POST, sub, ErrorResponse.class,false),null,success,true);

    }

}
