package com.example.demo.Pages.ProductsEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ImagesData;
import com.example.demo.Enums.ImageLogic;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
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

@Service
@UIScope
public class ProductEditImage {

    CommonComponents commonComponents;
    Common common;




    boolean mainFound = false;

    Image mainImage = new Image("No_picture.png", "Product image");


    List<ImagesData> imagesDataList = new ArrayList<>();
    VerticalLayout feedLayout = new VerticalLayout();

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
                feedLayout.add(createImage(s.getImageUrl(),s.getUuId()));
            }
            else{
                feedLayout.add(createImage(s.getImageUrl(),s.getUuId()));
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
                    if(!mainFound && !mainImage.getSrc().isEmpty()) {
                        imagesData.setImageLogic(ImageLogic.Main);
                        mainFound = true;
                    }
                    else{
                        imagesData.setImageLogic(ImageLogic.NonMain);
                    }
                    imagesData.setImageData(data);

                    imagesDataList.add(imagesData);

                });

        Upload upload = new Upload(inMemoryHandler);

        upload.setAcceptedFileTypes("image/png", ".png");

        int maxFileSizeInBytes = 10 * 1024 * 1024;
        upload.setMaxFileSize(maxFileSizeInBytes);



        upload.addAllFinishedListener(e->{
            for(var s : imagesDataList) {

                if(s.getImageLogic() == ImageLogic.Main) {
                    if (s.getImageData() != null) {
                        String base64 = java.util.Base64.getEncoder().encodeToString(s.getImageData());
                        String src = "data:" + s.getImageType() + ";base64," + base64;
                        mainImage.setSrc(src);
                        feedLayout.add(createImageFromBytes(s.getImageData(), s.getImageType(), s.getUuId()));
                    }
                }
                else{
                    feedLayout.add(createImageFromBytes(s.getImageData(),s.getImageType(),s.getUuId()));
                }
            }

            listConsumer.accept(imagesDataList);
        });


        verticalLayout.add(upload);

        return verticalLayout;
    }


    // create images from bytes
    private Image createImageFromBytes(byte[] data, String mimeType, String id) {



        String base64 = java.util.Base64.getEncoder().encodeToString(data);

        String src = "data:" + mimeType + ";base64," + base64;

        Image image = new Image(src, "uploaded image");
        image.setWidthFull();
        image.setHeight("120px");
        image.getStyle()
                .set("border-radius", "10px");


        image.addClickListener(e->{
           String currentImage = src;

           for(var s : imagesDataList){
               if(s.getImageLogic() == ImageLogic.Main){
                   s.setImageLogic(ImageLogic.NonMain);
               }
               if(s.getUuId().equalsIgnoreCase(id)){
                   s.setImageLogic(ImageLogic.Main);
               }

           }

            mainImage.setSrc(currentImage);


        });

        return image;
    }


    private Image createImage(String url,String id) {





        Image image = new Image(url, "uploaded image");
        image.setWidthFull();
        image.setHeight("120px");
        image.getStyle()
                .set("border-radius", "10px");

        image.addClickListener(e -> {
            for(var s : imagesDataList){
                if(s.getImageLogic() == ImageLogic.Main){
                    s.setImageLogic(ImageLogic.NonMain);
                }
                if(s.getUuId().equalsIgnoreCase(id)){
                    s.setImageLogic(ImageLogic.Main);
                }
            }
            mainImage.setSrc(url);
        });


        return image;
    }











}
