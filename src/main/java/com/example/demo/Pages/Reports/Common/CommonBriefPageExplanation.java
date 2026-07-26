package com.example.demo.Pages.Reports.Common;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Export;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Setter;

import java.time.LocalDate;
import java.util.function.Consumer;


@Setter
public class CommonBriefPageExplanation {

    CommonComponents commonComponents;
    Common common;

    Consumer<FromToDate> fromToDateConsumer;

    boolean firstLoad = true;

    public CommonBriefPageExplanation(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout briefExplanation(String pageExplanation, String color){

        HorizontalLayout v = new HorizontalLayout();
        v.addClassName("layout-flex");

        if(firstLoad){
            v.addClassName("smooth-panel");
            firstLoad = false;
        }
        else{
            v.removeClassName("smooth-panel");
        }

        v.setWidthFull();
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);


        DatePicker from = new DatePicker();
        from.setPlaceholder("From date");

        DatePicker to = new DatePicker();
        to.setPlaceholder("To date");

        ComboBox<Export> export = new ComboBox<>();
        export.setItems(Export.values());
        export.setPlaceholder("Export");

        Button generateButton = new Button("Generate report");
        generateButton.addThemeVariants(ButtonVariant.PRIMARY);
        generateButton.getStyle().set("background-color",color);

        generateButton.addClickListener(e->{





            if(from.getValue() == null){
                from.setValue(common.currentMonthStart());
            }
            if (to.getValue() == null) {

                LocalDate nextMonthDate = from.getValue().withDayOfMonth(1).plusMonths(1).minusDays(1);
                to.setValue(nextMonthDate);
            }

            if(from.getValue().isAfter(to.getValue())){
                System.out.println("date from cannot be after to");
            }
            else {
                fromToDateConsumer.accept(new FromToDate(from.getValue(), to.getValue()));
            }
        });


        HorizontalLayout rightSideHolder = new HorizontalLayout();
        rightSideHolder.addClassName("layout-flex");
        rightSideHolder.setPadding(false);
        rightSideHolder.add(
                from,
                to,
                export,
                generateButton
        );



        v.add(
                commonComponents.biefPageExplanation(pageExplanation),
                rightSideHolder
        );
        return v;
    }

}
