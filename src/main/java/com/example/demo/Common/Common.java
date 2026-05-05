package com.example.demo.Common;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class Common {

    public Icon iconCrafter(VaadinIcon chosenIcon, String size, String color){

        Icon icon = chosenIcon.create();
        icon.setSize(size);
        icon.setColor(color);

        return icon;

    }

    public Button normalThemeButton(String text, Class className , ButtonVariant buttonVariant){
        Button button = new Button(text, e-> UI.getCurrent().navigate(className));
        button.addThemeVariants(buttonVariant);

        return button;
    }


    public String dateFormatter(LocalDate date, String yyyymmdd){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(yyyymmdd);
        String formattedDate = date.format(formatter);

        return  formattedDate;
    }

    public LocalDate dateCrafter(int minusMonth, int plusMonth, int minusDays, int plusDays, boolean startOfMonth){

        LocalDate date = LocalDate.now();

        // start with this because this makes the date always to be 01 day
        if (startOfMonth) {
            date = date.withDayOfMonth(1);
        }

        if(plusDays > 0){
            date = date.plusDays(plusDays);
        }

        if(minusDays > 0){
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

}
