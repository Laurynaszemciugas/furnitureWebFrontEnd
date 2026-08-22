package com.example.demo.Services.UserService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
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


}
