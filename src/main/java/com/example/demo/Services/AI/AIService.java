package com.example.demo.Services.AI;

import com.example.demo.Common.AiQuestion;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.vaadin.copilot.shaded.checkerframework.checker.units.qual.C;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AIService {

    HttpCallLogic httpCallLogic;

    Common common;

    public AIService(HttpCallLogic httpCallLogic, Common common) {
        this.httpCallLogic = httpCallLogic;
        this.common = common;
    }

    @SneakyThrows
    public<T> T getMaterialDataAccordingToId(AiQuestion aiQuestion, String jwt, Class<T> tClass, UI ui) {


        T t = (T) httpCallLogic.HttpCallWithJwt("Ai/getAiFillText", HttpMethod.POST,aiQuestion, tClass,false,jwt);


        if (t instanceof ErrorResponse response) {
            ui.access(()->{

            common.customActionsForNotification(response.getMessage(),response.getWarning(),null,false);
            });

        }

        return t;

    }


}
