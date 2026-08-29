package com.example.demo.Common.Logic.InternetScraper.ImagesScraper;

import com.example.demo.Common.Logic.InternetScraper.Scraper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.List;
import java.util.function.Consumer;


@Setter
public class ImageScraperView {

    ImageScraper scraper;

    int currentImageIndex;

    Consumer<String> imageUrlConsumer;

    TextField searchBar = new TextField("Search...");
    NumberField price = new NumberField("Price");
    ComboBox<String> vendors = new ComboBox<>("Vendors");

    Component component;

    Dialog popover = new Dialog();
    Dialog dialog = new Dialog();


    public ImageScraperView() {

        this.scraper = new ImageScraper();




    }


    @SneakyThrows
    public Dialog layout(String itemToSearch){


        VerticalLayout itemsContainer = new VerticalLayout();
        itemsContainer.setPadding(false);

        component = items(scraper.imageScraper(itemToSearch.replace(" ","")));
        itemsContainer.add(component);







        dialog.setWidth("800px");
        dialog.setHeight("800px");


        HorizontalLayout filters = new HorizontalLayout();
        filters.setWidthFull();
        filters.getStyle().set("gap","10px").set("margin-bottom","20px");

        searchBar.setWidth("250px");


        filters.add(
                searchBar,
                price,
                vendors
        );






        dialog.add(
                itemsContainer

        );

        Button close = new Button("Back");
        close.addClickListener(e->{
            dialog.close();
        });

        dialog.getFooter().add(close);


        dialog.open();

        return dialog;
    }



    public VerticalLayout items(List<String> list){





        // System.out.println(list);

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        Grid<String> grid = new Grid<>(String.class,false);
        grid.setItems(list);

        grid.setHeight("650px");




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
                popover.close();
                dialog.close();

            });

            return button;

        }).setAutoWidth(true).setHeader("Actions");



        v.add(new Span("Images from the internet"),
                grid);



        return v;

    }


    public void popOver(List<String> images, String imageUrl){

        currentImageIndex = images.indexOf(imageUrl);


        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);


        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);


        Button select = new Button("Select current");

        select.addClickListener(e->{


            imageUrlConsumer.accept(images.get(currentImageIndex));
            popover.close();
            dialog.close();

        });

        Image image = new Image();
        image.setSrc(images.get(currentImageIndex));
        image.getStyle()
                .set("width", "500px")
                .set("height", "500px")
                .set("object-fit", "contain")
                .set("border-radius", "12px");



        Button left = new Button("L");
        left.addClickListener(e->{
            if(currentImageIndex == 0){
                currentImageIndex = images.size()-1;
            }
            else{
                currentImageIndex--;

            }
            image.setSrc(images.get(currentImageIndex));
        });

        Button right = new Button("R");
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
