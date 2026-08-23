package com.example.demo.Services.UserService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.User.AccountOverview;
import com.example.demo.ControllerModels.User.Appearance;
import com.example.demo.ControllerModels.User.PersonalPrefrences;
import com.example.demo.ControllerModels.User.ProfileInformation;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
public class UserService {


    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public UserService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }



    public ProfileInformation getProfileInfo() {

        return httpCallLogic.HttpCall("user/getProfileInfo", HttpMethod.GET,null, ProfileInformation.class,false);

    }

    public void saveProfileInfo(User user) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("user/saveProfileInfo", HttpMethod.POST, user, ErrorResponse.class,false),"Settings",success,true);

    }

    public AccountOverview getAccountOverview() {

        return httpCallLogic.HttpCall("user/getAccountOverview", HttpMethod.GET,null, AccountOverview.class,false);

    }

    public PersonalPrefrences getPersonalPrefrences() {

        return httpCallLogic.HttpCall("user/getPersonalPrefrences", HttpMethod.GET,null, PersonalPrefrences.class,false);

    }


    public void savePersonalPrefrences(User user) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("user/savePersonalPrefrences", HttpMethod.POST, user, ErrorResponse.class,false),"Settings",success,true);

    }


    public Appearance getAppearance() {

        return httpCallLogic.HttpCall("user/getAppearance", HttpMethod.GET,null, Appearance.class,false);

    }


    public void saveTheme(String value) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("user/saveTheme", HttpMethod.GET, value, ErrorResponse.class,true),null,success,true);

    }

    public void saveAccent(String value) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("user/saveAccent", HttpMethod.GET, value, ErrorResponse.class,true),null,success,true);

    }

    public void saveSidebar(String value) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("user/saveSidebar", HttpMethod.GET, value, ErrorResponse.class,true),null,success,true);

    }


}
