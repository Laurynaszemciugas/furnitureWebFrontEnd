package com.example.demo.Services.LoginService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.DTOS.Auth.PasswordResetWithCode;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserSettings;
import com.example.demo.DTOS.Error.ErrorResponse;
import com.example.demo.Enums.Role;
import com.example.demo.FilterDTO.Material.MaterialFilterHolder;
import com.example.demo.Pages.EmployeePage.Page.EmployeePageDashboard;
import com.example.demo.Pages.Login.LoginPage;
import com.vaadin.flow.component.UI;
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
                    httpCallLogic.HttpCall("auth/signin", HttpMethod.POST, user, ErrorResponse.class, false),null);


            if(jwt != null){
                sessionCrafter.createSession("JWT",jwt);
            }
            else{
                sessionCrafter.createSession("JWT",null);
            }




    }

    public void createSettings(){


        UserSettings userSettings = httpCallLogic.HttpCall("user/getUserSettings", HttpMethod.GET, null, UserSettings.class, false);
        Role userRole = httpCallLogic.HttpCall("user/getUserRole", HttpMethod.GET, null, Role.class, false);
        sessionCrafter.createSession("settings",userSettings);
        sessionCrafter.createSession("user_role",userRole);


        if(userSettings != null && userRole.equals(Role.ADMIN)){


            switch (userSettings.getPageStart()){
                case ORDERS -> UI.getCurrent().navigate("Orders");
                case REPORTS -> UI.getCurrent().navigate("Reports");
                case PRODUCTS -> UI.getCurrent().navigate("Products/1");
                case SETTINGS -> UI.getCurrent().navigate("Settings");
                case DASHBOARD -> UI.getCurrent().navigate("DashBoard");
                case EMPLOYEES -> UI.getCurrent().navigate("Employees");
                case MATERIALS -> UI.getCurrent().navigate("Materials");
                case ACTION_LOGS -> UI.getCurrent().navigate("Actions");
                case null -> UI.getCurrent().navigate("DashBoard");

            }

        }
        else if(userRole.equals(Role.EMPLOYEE)){
            UI.getCurrent().navigate(EmployeePageDashboard.class);
        }




    }






    public void googleLogin(String sub) {






               // httpCallLogic.HttpCall("auth/google", HttpMethod.POST, sub, ErrorResponse.class,false);


        String jwt  = httpCallLogic.checkResponseNoGetValue(
                httpCallLogic.HttpCall("auth/google", HttpMethod.POST, sub, ErrorResponse.class, false),"DashBoard");




        if(jwt != null){
            sessionCrafter.createSession("JWT",jwt);
        }
        else{
            sessionCrafter.createSession("JWT",null);
        }







    }

    @SneakyThrows
    public void createPasswordResetGmailVerificationCode(String gmail) {

        httpCallLogic.checkResponse(httpCallLogic.HttpCall("auth/createPasswordResetGmailVerificationCode", HttpMethod.GET,gmail, ErrorResponse.class,true),null,success,true);

    }

    @SneakyThrows
    public void resetPasswordViaGmail(PasswordResetWithCode code) {

        httpCallLogic.checkResponse(httpCallLogic.HttpCall("auth/resetPasswordViaGmail", HttpMethod.POST,code, ErrorResponse.class,false), "Login",success,true);

    }




}
