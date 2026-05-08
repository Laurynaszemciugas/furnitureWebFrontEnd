package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.MaterialLowNo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialLowNoStock {

    CommonComponents commonComponents;
    Common common;

    public MaterialLowNoStock(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    public VerticalLayout materialLowNoStock(List<MaterialLowNo> materialLowNoList){

        boolean empty = materialLowNoList.isEmpty();


        VerticalLayout mainLayout = new VerticalLayout(commonComponents.spanCrafterWordNoHide("Material Low/No stock","stat-value"));
        mainLayout.setWidth("600px");
        mainLayout.addClassName("island");


        // add data here from db
        VerticalLayout material = new VerticalLayout();

        if(!empty) {
            for (var s : materialLowNoList) {
                material.add(materialLowNoStockFeed(s.getId(), s.getName(), s.getCurrentStock(), s.getMinTreshold(), s.getPieceCost()));
            }
        }
        else{
            material.add(commonComponents.noDataFound());
        }

        // scroller that takes material as data from materialLowNoStockFeed
        Scroller scroller = new Scroller(material);
        scroller.setSizeFull();
        scroller.setHeight("400px");

        // simple button in the middle
        HorizontalLayout buttonAtTheBottom = new HorizontalLayout(commonComponents.normalThemeButton("View All Material", "s", ButtonVariant.LUMO_PRIMARY));
        buttonAtTheBottom.setWidthFull();
        buttonAtTheBottom.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);



        // add remainin stuff to the main layout of the low no material layout
        mainLayout.add(scroller,buttonAtTheBottom);

        return mainLayout;
    }


    // material feed crafter
    public HorizontalLayout materialLowNoStockFeed(long id, String materialName, long currentStock, long minTresh, double pieceCost){

        double howMuchToReachInStock = howMuchToReachTresh(currentStock, minTresh, pieceCost);

        // icon for nicer view
        HorizontalLayout iconHolder = new HorizontalLayout(commonComponents.iconCrafter(VaadinIcon.CIRCLE,"20px","blue"));
        iconHolder.getStyle().set("margin-top","5px");

        // badge
        Span span = commonComponents.spanCrafter("Null","new-badge");
        HorizontalLayout stockAlert = new HorizontalLayout(span);
        stockAlert.setHeight("15px");
        stockAlert.getStyle().set("position","absolute").set("top","10px").set("right","10px");

        // edit button

        Button editShortCut = commonComponents.smallIconButtons("1", VaadinIcon.PENCIL,"black");
        editShortCut.addClickListener(e->{
            System.out.println(id);
        });

        HorizontalLayout editData = new HorizontalLayout(editShortCut);
        editData.getStyle().set("position","absolute").set("bottom","2px").set("right","2px").set("z-index","10");




        VerticalLayout e = new VerticalLayout(
                commonComponents.tripleValueRow(commonComponents.spanCrafterWordNoHide(materialName,"activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(" - ","activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(String.format("%d %s",currentStock, currentStock == 1 ? "Unit left" : "Units left"),"stat-example")),

                commonComponents.doubleValueRow(
                        commonComponents.spanCrafterWordNoHide(String.format("%s %d",  "Min treshold", minTresh),"stat-title"),
                        commonComponents.spanCrafterWordNoHide(String.format("%s %s %.2f %s","●" , " Peace cost", pieceCost,"Eur"),"stat-title")),


                commonComponents.spanCrafterWordNoHide(String.format("%s %.2f %s", "Aproximetly need to spend - ", howMuchToReachInStock , "Eur"),"stat-description")
        );


        e.setWidthFull();
        e.setPadding(false);
        e.setSpacing(false);


        HorizontalLayout h12 = new HorizontalLayout(iconHolder,e,editData);
        h12.addClassName("island");
        h12.setWidthFull();
        h12.getStyle().set("position","relative");

        if(currentStock == 0){
            span.addClassName("stock-out");
            span.setText("No stock");
            h12.add(stockAlert);
        }
        else{
            span.addClassName("stock-low");
            span.setText("Low stock");
            h12.add(stockAlert);
        }



        return  h12;
    }

    public double howMuchToReachTresh(long currentStock, long minTresh, double pieceCost){

        double toPay = 0;

        long diffrence = Math.abs(currentStock - minTresh);

        toPay = diffrence * pieceCost;


        return  toPay;
    }




}
