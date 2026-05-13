package com.example.demo.Pages.ProductsEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ImagesData;
import com.example.demo.Enums.ImageLogic;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


public class ProductEditImage {

    CommonComponents commonComponents;
    Common common;




    // set mainfound image boolean value to make only one main image
    boolean mainFound;

    // main image component
    Image mainImage = new Image("No_picture.png", "Product image");

    // all images saved here
    List<ImagesData> imagesDataList = new ArrayList<>();

    List<ImagesData> newUploadedImages = new ArrayList<>();

    // mini images nonMain go here
    VerticalLayout feedLayout = new VerticalLayout();


    // just a tripwire that says to controller hey i got updlaoded
    Consumer<List<ImagesData>> listConsumer;





    public ProductEditImage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        mainFound = false;


    }




    public void setListConsumer(Consumer<List<ImagesData>> listConsumer){
        this.listConsumer = listConsumer;
    }

    public HorizontalLayout images(List<ImagesData> list) {



        HorizontalLayout holder = new HorizontalLayout();
        holder.setPadding(false);
        holder.setSpacing(true);
        holder.setAlignItems(FlexComponent.Alignment.START);
        holder.addClassName("layout-flex");


        VerticalLayout imageHolder = new VerticalLayout();
        imageHolder.setPadding(false);
        imageHolder.setSpacing(false);
        imageHolder.setWidth("450px");
        imageHolder.addClassName("island");
        imageHolder.getStyle().set("position","relative");

        Span mainSelected = commonComponents.spanCrafter("Main","stock-badge");
        mainSelected.addClassName("stock-low");
        mainSelected.getStyle().set("position","absolute").set("top","30px").set("right","30px");


        mainImage.setWidthFull();
        mainImage.setHeight("450px");
        mainImage.getStyle()
                .set("border-radius", "10px");


        imageHolder.add(mainImage,mainSelected);


        feedLayout.setPadding(false);
        feedLayout.setSpacing(true);
        feedLayout.setWidthFull();



        Scroller scroller = new Scroller(feedLayout);
        scroller.setWidth("180px");
        scroller.setHeight("484px");
        scroller.addClassName("island");


        if(!list.isEmpty()) {
            mainFound = true;
            imagesDataList.addAll(list);

            addExistingImages();
        }



        holder.add(imageHolder,scroller);


        return holder;
    }

    public void addExistingImages(){
        for(var s : imagesDataList){
            if(s.getImageLogic() == ImageLogic.Main){
                mainImage.setSrc(s.getImageUrl());
                feedLayout.add(createImage(s.getImageData(), s.getImageType(), s.getUuId(),s.getImageUrl()));
            }
            else{
                feedLayout.add(createImage(s.getImageData(), s.getImageType(), s.getUuId(),s.getImageUrl()));
            }
        }
    }



    // handle uploaded images
    public VerticalLayout uploadStuff(){
        VerticalLayout verticalLayout = new VerticalLayout();



        InMemoryUploadHandler inMemoryHandler = UploadHandler
                .inMemory((metadata, data) -> {
                    String fileName = metadata.fileName();
                    String mimeType = metadata.contentType();

                    ImagesData imagesData = new ImagesData();
                    imagesData.setUuId(UUID.randomUUID().toString());
                    imagesData.setImageName(fileName);
                    imagesData.setImageUrl("none");
                    imagesData.setImageType(mimeType);
                    if(!mainFound) {
                        imagesData.setImageLogic(ImageLogic.Main);
                        mainFound = true;
                    }
                    else{
                        imagesData.setImageLogic(ImageLogic.NonMain);
                    }
                    imagesData.setImageData(data);

                    newUploadedImages.add(imagesData);

                });

        Upload upload = new Upload(inMemoryHandler);

        upload.setAcceptedFileTypes("image/png", ".png");

        int maxFileSizeInBytes = 10 * 1024 * 1024;
        upload.setMaxFileSize(maxFileSizeInBytes);



        upload.addAllFinishedListener(e->{
            for(var s : newUploadedImages) {

                if(s.getImageLogic() == ImageLogic.Main) {
                    if (s.getImageData() != null) {
                        String base64 = java.util.Base64.getEncoder().encodeToString(s.getImageData());
                        String src = "data:" + s.getImageType() + ";base64," + base64;
                        mainImage.setSrc(src);
                        feedLayout.add(createImage(s.getImageData(), s.getImageType(), s.getUuId(),s.getImageUrl()));
                    }
                }
                else{
                    feedLayout.add(createImage(s.getImageData(),s.getImageType(),s.getUuId(),s.getImageUrl()));
                }
            }

            // add new data to list
            imagesDataList.addAll(newUploadedImages);
            // clear list to avoid dublicates
            newUploadedImages.clear();

            // send data that user put stuff in the uploads
            listConsumer.accept(imagesDataList);
        });


        verticalLayout.add(upload);

        return verticalLayout;
    }


    // create images from bytes if it doesnt exist it new from user
    private VerticalLayout createImage(byte[] data, String mimeType, String id, String url) {



        Button mainSelected = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.PRIMARY, VaadinIcon.TRASH,"White");
        mainSelected.getStyle().set("position","absolute").set("bottom","0").set("right","2px");
        mainSelected.setVisible(false);


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.setSpacing(false);
        verticalLayout.setPadding(false);
        verticalLayout.getStyle().set("position","relative");


        String src = "";
        String base64 = "";

        if(data !=null) {
            base64 = java.util.Base64.getEncoder().encodeToString(data);
            src = "data:" + mimeType + ";base64," + base64;
        }
        else{
            src = url;
        }

        Image image = new Image(src, "uploaded image");
        image.setWidthFull();
        image.setHeight("120px");
        image.getStyle()
                .set("border-radius", "10px");


        String finalSrc = src;

        image.addClickListener(e->{
           String currentImage = finalSrc;

           for(var s : imagesDataList){
               if(s.getImageLogic() == ImageLogic.Main){
                   s.setImageLogic(ImageLogic.NonMain);
               }
               if(s.getUuId().equalsIgnoreCase(id)){
                   s.setImageLogic(ImageLogic.Main);

               }

           }
            for(var s : imagesDataList){
                System.out.println(s.getImageName() + " " + s.getImageLogic());
            }

            mainImage.setSrc(currentImage);




        });

        verticalLayout.add(mainSelected);

        image.addSingleClickListener(e->{
           mainSelected.setVisible(false);
        });
        image.addDoubleClickListener(e->{
            mainSelected.setVisible(true);
        });


        mainSelected.addClickListener(e->{
            verticalLayout.setVisible(false);

            handleRemoveOfImageView(id);
        });


        verticalLayout.add(image);

        return verticalLayout;
    }

    public void handleRemoveOfImageView(String id){
        imagesDataList.removeIf(imageData -> imageData.getUuId().equalsIgnoreCase(id));

        if(!imagesDataList.isEmpty()) {
            boolean mainExists = false;
            for (var s : imagesDataList) {
                if (s.getImageLogic() == ImageLogic.Main) {
                    mainExists = true;
                }
            }
            System.out.println(mainExists);



            if (mainExists == false) {
                imagesDataList.get(0).setImageLogic(ImageLogic.Main);
                if (imagesDataList.get(0).getImageData() == null) {
                    mainImage.setSrc(imagesDataList.get(0).getImageUrl());
                }
                else {
                    String base64 = java.util.Base64.getEncoder().encodeToString(imagesDataList.get(0).getImageData());
                    String src = "data:" + imagesDataList.get(0).getImageType() + ";base64," + base64;
                    mainImage.setSrc(src);
                }

            }


        }
        else{
            mainImage.setSrc("No_picture.png");
        }


    }











}
