package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.ActivityFeedModel;
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
public class ActivityFeed {

    CommonComponents commonComponents;
    Common common;

    public ActivityFeed(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }



    public VerticalLayout activityFeedCrafter(List<ActivityFeedModel> activityFeedModelList){

        boolean empty = activityFeedModelList.isEmpty();

        VerticalLayout activityFeedHolder = new VerticalLayout(commonComponents.spanCrafterWordNoHide("Activity feed","stat-value"));
        activityFeedHolder.addClassName("island");
        activityFeedHolder.setMaxWidth("500px");
        activityFeedHolder.setMaxHeight("600px");


        HorizontalLayout buttonAtTheBottom = new HorizontalLayout(commonComponents.normalThemeButton("View All Logs", "s", ButtonVariant.LUMO_PRIMARY));
        buttonAtTheBottom.setWidthFull();
        buttonAtTheBottom.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);


        //fix add the action logic to add the info

        VerticalLayout actionsOfTheSystems = new VerticalLayout();

        if(!empty) {
            for (var s : activityFeedModelList) {
                actionsOfTheSystems.add(activityLogCrafter(s.getActionDescription(), s.getWhoMadeIt(), s.getHowLongAgoMinutes(), s.getColor()));
            }
        }
        else{
            activityFeedHolder.add(commonComponents.noDataFound());
        }


        Scroller scroller = new Scroller(actionsOfTheSystems);
        scroller.setSizeFull();


        activityFeedHolder.add(scroller,buttonAtTheBottom);

        return  activityFeedHolder;
    }


    // log crafter for "activityFeedCrafter"
    public HorizontalLayout activityLogCrafter(String nameOfTheUpdatedCreatedItem, String whoMadeAction, long howLongAgo, String color){

        long newStatusMin = 15;

        Span span = commonComponents.spanCrafter("New","new-badge");
        HorizontalLayout neww = new HorizontalLayout(span);
        neww.setHeight("15px");
        neww.getStyle().set("position","absolute").set("top","10px").set("right","10px");


        HorizontalLayout iconHolder = new HorizontalLayout(commonComponents.iconCrafter(VaadinIcon.CIRCLE,"20px",color));
        iconHolder.getStyle().set("margin-top","5px");

        // how  long ago is in minutes
        long timePassed = howLongAgo;
        String timeName = timePassed == 1 ? "Minute ago" : "Minutes ago";
        if(howLongAgo >= 60){
            timePassed = howLongAgo / 60;
            timeName =  timePassed == 1 ? "Hour ago" : "Hours ago";
        }
        if(timePassed >= 24){
            timePassed = timePassed /24;
            timeName = timePassed == 1 ? "Day ago" : "Days ago";
        }
        if(timePassed >=30){
            timePassed = timePassed / 30;
            timeName = timePassed == 1 ? "Month ago" : "Months ago";
        }
        if(timePassed >= 48){
            timePassed = timePassed / 48;
            timeName = timePassed == 1 ? "Year ago" : "Years ago";;
        }



        VerticalLayout verticalLayout = new VerticalLayout(commonComponents.spanCrafterWordNoHide(nameOfTheUpdatedCreatedItem,"activityFeed-name"),
                commonComponents.spanCrafter(String.format("%s %s %d %s",whoMadeAction, "●", timePassed,timeName ),"stat-description")
                );
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        HorizontalLayout activityFeedHolder = new HorizontalLayout(iconHolder,verticalLayout);
        activityFeedHolder.addClassName("island");
        activityFeedHolder.setWidthFull();
        activityFeedHolder.getStyle().set("position", "relative");

        if(howLongAgo <= newStatusMin){
            activityFeedHolder.add(neww);
        }


        return  activityFeedHolder;

    }





}
