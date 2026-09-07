package com.example.demo.Common.Logic;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Setter
public class ImageViewer {

    CommonComponents commonComponents;
    Common common;



    public ImageViewer(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    int currentImageIndex;

    Consumer<String> imageUrlConsumer;

    List<Notification> notifications = new ArrayList<>();

    public void popOver(List<String> images, String imageUrl){

        Dialog popover = new Dialog();

        currentImageIndex = images.indexOf(imageUrl);


        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);


        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);




        Image image = new Image();
        image.setSrc(images.get(currentImageIndex));
        image.getStyle()
                .set("width", "500px")
                .set("height", "300px")
                .set("object-fit", "contain")
                .set("border-radius", "8px")
                .set("background", "#f5f5f5");


        Button left = new Button(commonComponents.iconCrafter(VaadinIcon.ANGLE_LEFT,"25px","Blue"));
        left.addClickListener(e->{
            if(currentImageIndex == 0){
                currentImageIndex = images.size()-1;
            }
            else{
                currentImageIndex--;

            }
            image.setSrc(images.get(currentImageIndex));
        });

        Button right = new Button(commonComponents.iconCrafter(VaadinIcon.ANGLE_RIGHT,"25px","Blue"));
        right.addClickListener(e->{

            if(currentImageIndex == images.size()-1) {

                currentImageIndex = 0;

            }
            else{
                currentImageIndex++;
            }

            image.setSrc(images.get(currentImageIndex));
        });


        h.add(
                left,
                image,
                right

        );


        v.add(
                h
        );

        popover.add(
                v
        );

        popover.open();




    }


    public void createMentionNotification(String url) {
        Notification notification = new Notification();

        Image image = new Image(url,"Image");
        image.setWidth("100px");
        image.setHeight("100px");
        image.getStyle().set("border-radius","10px");


        HorizontalLayout info = new HorizontalLayout(image, new Text("Image was added"));
        info.setAlignItems(FlexComponent.Alignment.CENTER);
        info.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        HorizontalLayout layout = new HorizontalLayout(info);
        layout.addToEnd(createCloseBtn());
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setMinWidth("350px");

        notification.add(layout);

        notification.open();


        notifications.add(notification);
    }

    public  Button createCloseBtn() {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create());


        closeBtn.addClickListener(e->{
            for(var s : notifications){
                s.close();
            }

            notifications.clear();
        });

        return closeBtn;
    }



}
