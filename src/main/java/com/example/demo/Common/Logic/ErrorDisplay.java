package com.example.demo.Common.Logic;

import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Warnings;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class ErrorDisplay {

    int times = 0;

    CommonComponents commonComponents;

    public ErrorDisplay(CommonComponents commonComponents) {
        this.commonComponents = commonComponents;
    }

    public boolean customActionsForNotification(String message, Warnings warning){
        NotificationVariant notificationVariant = NotificationVariant.ERROR;

        switch (warning){
            case ERROR ->{
                notificationVariant = NotificationVariant.ERROR;
                times = 0;
            }
            case FATAL_ERROR ->{
                notificationVariant = NotificationVariant.ERROR;
                times = 0;
            }
            case OK ->{
                commonComponents.showNotification(message,4000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
                times = 0;
                return true;

            }

            case WARNING ->{
                notificationVariant = NotificationVariant.WARNING;
                times++;
                if(times == 2){
                    times = 0;
                    return true;
                }
            }
        }
        commonComponents.showNotification(message,4000, Notification.Position.BOTTOM_CENTER, notificationVariant);

        return  false;
    }

}
