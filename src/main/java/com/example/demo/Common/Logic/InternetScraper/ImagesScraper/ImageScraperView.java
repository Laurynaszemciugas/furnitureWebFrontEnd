package com.example.demo.Common.Logic.InternetScraper.ImagesScraper;

import com.example.demo.Common.AiQuestion;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.InternetScraper.Scraper;
import com.example.demo.Common.Logic.SessionCrafter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


@Setter
public class ImageScraperView {


    CommonComponents commonComponents;
    Common common;

    ImageScraper scraper;

    int currentImageIndex;

    Consumer<String> imageUrlConsumer;

    VerticalLayout component;


    String itemMemory;

    SessionCrafter sessionCrafter;

    List<Notification> notifications = new ArrayList<>();

    public ImageScraperView(CommonComponents commonComponents, Common common) {

        this.commonComponents = commonComponents;
        this.common = common;

        this.scraper = new ImageScraper();


        this.sessionCrafter = new SessionCrafter();


    }


    @SneakyThrows
    public Dialog layout(){


        Dialog dialog = new Dialog();
        Button close = new Button("Back");

        VerticalLayout itemsContainer = new VerticalLayout();
        itemsContainer.setPadding(false);
        itemsContainer.setSpacing(false);

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setPadding(false);
        firstLayer.setWidthFull();


        firstLayer.add(
                commonComponents.iconCrafter(VaadinIcon.GLOBE_WIRE,"25px","Blue"),
                commonComponents.spanCrafter("Pictures that were found on the internet","activityFeed-name")

        );


        TextField textField = new TextField("Search for images");
        itemsContainer.add(firstLayer,textField);

        textField.setValue(itemMemory == null ? "" : itemMemory);

        textField.addValueChangeListener(e -> {

            itemMemory = textField.getValue();

                UI ui = UI.getCurrent();

                itemsContainer.removeAll();
                itemsContainer.add(loadingOverlay("Getting images from internet",ui));





                CompletableFuture.runAsync(() -> {

                    try {

                        String name = "";

                        if(textField.getValue() != null){
                            name = textField.getValue();
                        }
                        else{
                            name = e.getValue();
                        }


                        VerticalLayout newComponent = items(scraper.imageScraper(name));

                        ui.access(() -> {

                        itemsContainer.removeAll();
                        itemsContainer.add(firstLayer,textField,newComponent);



                        });


                    } catch (Exception ex) {



                        common.reloadPage();



                    }

                });









                itemsContainer.remove(component);
                itemsContainer.add(component);


        });


        component = items(scraper.imageScraper(null));
        itemsContainer.add(firstLayer,textField,component);








        dialog.setWidth("800px");
        dialog.setHeight("800px");




        dialog.add(
                itemsContainer

        );


        close.addClickListener(e->{
            dialog.close();
        });

        dialog.getFooter().add(close);


        dialog.open();

        return dialog;
    }


    private Component loadingOverlay(String loadingText, UI ui) {
        Div overlay = new Div();
        overlay.addClassName("loading-overlay");

        Div loader = new Div();
        loader.addClassName("modern-loader");

        Span text = new Span(loadingText+ "...");
        text.addClassName("loading-text");

        Button stuckWaiting = new Button("Stuck waiting ? ", e-> common.reloadPage());
        stuckWaiting.setVisible(false);
        stuckWaiting.addThemeVariants(ButtonVariant.PRIMARY);

        overlay.add(loader, text,stuckWaiting);

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            ui.access(() -> {
                stuckWaiting.setVisible(true);
            });

            scheduler.shutdown();

        }, 10, TimeUnit.SECONDS);

        return overlay;
    }



    public VerticalLayout items(List<String> list){





        // System.out.println(list);

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        Grid<String> grid = new Grid<>(String.class,false);
        grid.setItems(list);

        grid.setHeight("580px");




        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);


            Image image = new Image(e,"Internet image");

            image.setHeight("120px");
            image.setWidth("120px");



            image.addClickListener(ee->{
                popOver(list,
                        e);
            });








            return image;

        }).setAutoWidth(true).setHeader("Material");


        grid.addComponentColumn(e->{
            Button button = new Button("Select");

            button.addClickListener(ee->{
                imageUrlConsumer.accept(e);
//                popover.close();
//                dialog.close();

                createMentionNotification(e);
                commonComponents.showNotification("Added successfully",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);

            });



            return button;
        }).setAutoWidth(true).setHeader("Actions");











        v.add(grid);



        return v;

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


    public void popOver(List<String> images, String imageUrl){

        Dialog popover = new Dialog();

        currentImageIndex = images.indexOf(imageUrl);


        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);


        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);


        Button select = new Button("Select current");

        select.addClickListener(e->{


            imageUrlConsumer.accept(images.get(currentImageIndex));
            popover.close();
//            dialog.close();

            createMentionNotification(images.get(currentImageIndex));
            commonComponents.showNotification("Selected image",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);

        });

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
                h,
                select
        );

        popover.add(
                v
        );

        popover.open();




    }

}
