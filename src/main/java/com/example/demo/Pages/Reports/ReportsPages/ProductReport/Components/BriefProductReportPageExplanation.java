package com.example.demo.Pages.Reports.ReportsPages.ProductReport.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Export;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class BriefProductReportPageExplanation {

    CommonComponents commonComponents;
    Common common;


    boolean firstLoad = true;

    public BriefProductReportPageExplanation(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout briefExplanation(){

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
                commonComponents.biefPageExplanation("Order Reports"),
                rightSideHolder
        );
        return v;
    }

}
