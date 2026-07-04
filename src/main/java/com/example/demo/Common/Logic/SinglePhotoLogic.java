package com.example.demo.Common.Logic;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;


@Getter
@Setter
public class SinglePhotoLogic {


    String imageData = "No_picture.png";

    CommonComponents commonComponents;
    Common common;

    Consumer<String> newImage;

    public SinglePhotoLogic(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public VerticalLayout imageGetterShower(){


        VerticalLayout imageHolder = new VerticalLayout();
        imageHolder.setPadding(false);
        imageHolder.setSpacing(false);
        imageHolder.setSizeFull();
        imageHolder.getStyle().set("margin-top", "10px");

        InMemoryUploadHandler inMemoryHandler = UploadHandler
                .inMemory((metadata, data) -> {
                    String mimeType = metadata.contentType();

                    imageData = common.imageMaker(data,mimeType);

                });
        Upload upload = new Upload(inMemoryHandler);
        upload.setMaxFiles(1);
        upload.setAcceptedFileTypes(".PNG");
        upload.getStyle().set("margin-top", "5px");
        upload.setWidthFull();

        Image profilePic = commonComponents.imageCrafter(imageData,"50%", "130px", "5px");
        Image tableExample = commonComponents.imageCrafter(imageData,"90px", "90px", "50%");
        Image addEmployeeExample = commonComponents.imageCrafter(imageData,"80px", "80px", "50px");

        upload.addAllFinishedListener(e->{
            profilePic.setSrc(imageData);
            tableExample.setSrc(imageData);
            addEmployeeExample.setSrc(imageData);

            newImage.accept(imageData);

        });


        HorizontalLayout profilePhotoHolder = new HorizontalLayout();
        profilePhotoHolder.setWidthFull();
        profilePhotoHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        profilePhotoHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        profilePhotoHolder.add(
                profilePic,
                tableExample,
                addEmployeeExample
        );


        Span profilePicSpan = commonComponents.spanCrafterWordNoHide("Profile photo","stat-title");


        imageHolder.add(
                profilePicSpan,
                profilePhotoHolder,
                upload
        );


        return imageHolder;
    }




}
