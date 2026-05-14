package com.example.demo.Common;

import com.vaadin.flow.component.html.Span;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class Common {


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


    public boolean checkIfUserConnected() {
        boolean loggedIn = false; // replace with real auth check

        return loggedIn;
    }


    public double diffrenceCalculator(double currentValue, double oldValue){


        double value = 0 ;

        if(oldValue == 0 || currentValue == 0) {
            value = 0.0;
        }
        else{
            value = ((currentValue - oldValue) / oldValue) * 100;
        }

        if(currentValue == 0.0 && oldValue == 0.0){
            value = 0.0;
        }


        return  value;
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

    public String imageMaker(byte[] data, String mimeType){

        String base64 = java.util.Base64.getEncoder().encodeToString(data);
        String source = "data:" + mimeType + ";base64," + base64;

        return  source;
    }


}
