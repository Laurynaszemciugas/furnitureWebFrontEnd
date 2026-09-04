package com.example.demo.Services;


import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Setter
public class GmailVerificationService {


    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public GmailVerificationService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }


    @SneakyThrows
    public void sendGmailCode(String code){

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("auth/verifyGmail", HttpMethod.POST,code, ErrorResponse.class,false), null,success,true);

    }


}
