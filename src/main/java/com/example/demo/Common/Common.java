package com.example.demo.Common;

import com.example.demo.Common.Logic.ErrorDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Enums.Warnings;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

@Service
@Setter
public class Common {

    CommonComponents commonComponents;
    ErrorDisplay errorDisplay;
    SessionCrafter sessionCrafter;

    Consumer<Boolean> booleanConsumer;


    public Common(CommonComponents commonComponents) {
        this.commonComponents = commonComponents;
        this.errorDisplay = new ErrorDisplay(commonComponents);
        this.sessionCrafter = new SessionCrafter();
    }

    public String dateFormatter(LocalDate date, String yyyymmdd) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(yyyymmdd);
        String formattedDate = date.format(formatter);

        return formattedDate;
    }

    public LocalDate dateCrafter(int minusMonth, int plusMonth, int minusDays, int plusDays, boolean startOfMonth) {

        LocalDate date = LocalDate.now();

        // start with this because this makes the date always to be 01 day
        if (startOfMonth) {
            date = date.withDayOfMonth(1);
        }

        if (plusDays > 0) {
            date = date.plusDays(plusDays);
        }

        if (minusDays > 0) {
            date = date.minusDays(minusDays);
        }

        if (minusMonth > 0) {
            date = date.minusMonths(minusMonth);
        }

        if (plusMonth > 0) {
            date = date.plusMonths(plusMonth);
        }


        return date;

    }


    public boolean checkIfUserConnected() {
        boolean loggedIn = false; // replace with real auth check

        return loggedIn;
    }


    public double diffrenceCalculator(double currentValue, double oldValue) {


        double value = 0;

        if (oldValue == 0 || currentValue == 0) {
            value = 0.0;
        } else {
            value = ((currentValue - oldValue) / oldValue) * 100;
        }

        if (currentValue == 0.0 && oldValue == 0.0) {
            value = 0.0;
        }


        return value;
    }


    public void trendColoring(
            String moreThanZeroColor,
            String lessThanZeroColor,
            Object now,
            Object was,
            double changePercent,
            Span trend
    ) {

        double nowValue = toDouble(now);
        double wasValue = toDouble(was);


        if (changePercent > 0 && nowValue > wasValue) {
            trend.getStyle().set("color", moreThanZeroColor);
        } else if (changePercent < 0 && nowValue < wasValue) {
            trend.getStyle().set("color", lessThanZeroColor);
        } else {
            trend.getStyle().set("color", "gray");
        }
    }


    private double toDouble(Object value) {
        if (value == null) return 0.0;

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        return 0.0;
    }

    public String imageMaker(byte[] data, String mimeType) {

        String base64 = java.util.Base64.getEncoder().encodeToString(data);
        String source = "data:" + mimeType + ";base64," + base64;

        return source;
    }

    public String dateFormatter(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter;
        if (localDateTime != null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        } else {
            return "null";
        }
        return localDateTime.format(dateTimeFormatter);
    }

    public void customNavigate(String navigateTo) {
        // if null user is not navigated to nowhere

        if(navigateTo != null) {
            sessionCrafter.createSession("lastSeen",navigateTo);
            UI.getCurrent().navigate(navigateTo);
        }
    }

    public void reNavigateUsingSession(){
        UI.getCurrent().navigate(sessionCrafter.extractSession("lastSeen",String.class));
    }

    public void customActionsForNotification(String message, Warnings warning, String navigateInCaseOfSuccess, boolean showCorrectAnswer) {
        boolean canNavigate = errorDisplay.customActionsForNotification(message, warning, showCorrectAnswer);

        if (canNavigate) {
            customNavigate(navigateInCaseOfSuccess);
        }


    }


    public void deleteConfirmation(String productName) {
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Warning (DELETE ACTION)");

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span line = new Span(String.format("%s '%s' %s", "Please enter ", productName.toUpperCase(), "to remove a selected item"));
        line.getStyle().set("color", "red");

        Span lilWarning = commonComponents.spanCrafterWordNoHide("Deleting not always delete an item if it's been used, the item will be set to 'DISABLED' 'INACTIVE' depending on item","stat-title");

        TextField confirmName = new TextField("Enter product name");
        confirmName.setWidthFull();



        content.add(line,
                confirmName,
                lilWarning
        );


        dialog.setCancelable(true);
        dialog.setConfirmText("Remove");
        dialog.setCancelText("Go back");


        dialog.addConfirmListener(event -> {
            if (confirmName.getValue().equals(productName.toUpperCase())) {
                booleanConsumer.accept(true);

            } else {
                commonComponents.showNotification("Selected item was not removed verification failed ", 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
                booleanConsumer.accept(false);
            }
        });

        dialog.addCancelListener(event -> {
        });

        dialog.add(content);

        dialog.open();

    }

    public void timer(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public void reloadPage(){
        UI.getCurrent().getPage().reload();
    }

}
