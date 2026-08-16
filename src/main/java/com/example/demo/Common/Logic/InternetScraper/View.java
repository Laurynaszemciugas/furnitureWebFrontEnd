package com.example.demo.Common.Logic.InternetScraper;

import com.example.demo.Common.AiQuestion;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Services.InternetScraping.ScraperService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class View  {


    Scraper scraper;

    TextField searchBar = new TextField("Search...");
    NumberField price = new NumberField("Price");
    ComboBox<String> vendors = new ComboBox<>("Vendors");

    Component component;

    CommonComponents commonComponents;
    Common common;

    ScraperService scraperService;

    SessionCrafter sessionCrafter;

    public View(Scraper scraper,CommonComponents commonComponents,Common common,ScraperService scraperService) {

        this.scraper = scraper;
        this.commonComponents = commonComponents;
        this.common = common;
        this.scraperService = scraperService;

        this.sessionCrafter = new SessionCrafter();

    }


    public void layout(String name){



        VerticalLayout itemsContainer = new VerticalLayout();
        itemsContainer.setPadding(false);

        UI ui = UI.getCurrent();

        itemsContainer.removeAll();
        itemsContainer.add(common.loadingOverlay("Searching for offers",ui));

        String jwt = sessionCrafter.extractSession("JWT", String.class);

        CompletableFuture.runAsync(() -> {

            try {

                component = items(scraperService.getDataFromInternetScraping(name,jwt));



                ui.access(() -> {

                    itemsContainer.removeAll();
                    itemsContainer.add(component);


                });


            } catch (Exception ex) {

                System.out.println(ex);



            }

        });





        //TextField searchCustom = new TextField("Search new material");
//        searchCustom.setWidth("400px");
//
//        searchCustom.addValueChangeListener(e->{
//            try {
//                itemsContainer.removeAll();
//                component = items(scraper.scraper(e.getValue()));
//            itemsContainer.add(component);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            } catch (InterruptedException ex) {
//                throw new RuntimeException(ex);
//            }
//        });

        Dialog dialog = new Dialog();
        dialog.setWidth("1100px");
        dialog.setHeight("820px");


        HorizontalLayout filters = new HorizontalLayout();
        filters.setWidthFull();
        filters.getStyle().set("gap","10px").set("margin-bottom","20px");

        searchBar.setWidth("250px");


        filters.add(
                searchBar,
                price

        );






        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setPadding(false);
        firstLayer.setWidthFull();


        firstLayer.add(
                commonComponents.iconCrafter(VaadinIcon.GLOBE_WIRE,"25px","Blue"),
                commonComponents.spanCrafter("Online found orders for - (" + name+")","activityFeed-name")

        );




        dialog.add(
                firstLayer,
                filters,
                itemsContainer

        );

        Button close = new Button("Back");
        close.addClickListener(e->{
           dialog.close();
        });

        dialog.getFooter().add(close);


        dialog.open();


    }



    public VerticalLayout items(List<PriceResult> list){

//        vendors.setItems(
//                list.stream()
//                        .flatMap(e -> e.getMultipleVendors() != null
//                                ? e.getMultipleVendors().stream()
//                                : java.util.stream.Stream.empty())
//                        .map(MultipleVendors::getVendorName)
//                        .filter(v -> v != null && !v.isBlank())
//                        .distinct()
//                        .sorted()
//                        .toList()
//        );



       // System.out.println(list);

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        Grid<PriceResult> grid = new Grid<>(PriceResult.class,false);
        ListDataProvider<PriceResult> dataProvider =
                new ListDataProvider<>(list);
        grid.setWidthFull();
        grid.setPageSize(2);
        grid.setHeight("580px");
        grid.setDataProvider(dataProvider);

        dataProvider.setFilter(e -> {

            // Product search
            String search = searchBar.getValue();

            boolean matchesSearch =
                    search == null ||
                            search.isBlank() ||
                            (e.getProductName() != null &&
                                    e.getProductName()
                                            .toLowerCase()
                                            .contains(search.toLowerCase()));

            // Maximum price
            Double maxPrice = price.getValue();

            boolean matchesPrice =
                    maxPrice == null ||
                            e.getPrice() <= maxPrice;

            String selectedVendor = vendors.getValue();

            boolean matchesVendor =
                    selectedVendor == null ||
                            selectedVendor.isBlank() ||
                            (
                                    e.getMultipleVendors() != null &&
                                            e.getMultipleVendors().stream()
                                                    .anyMatch(vv ->
                                                            selectedVendor.equals(vv.getVendorName())
                                                    )
                            );


            return matchesSearch &&
                    matchesPrice &&
                    matchesVendor;
        });

        searchBar.addValueChangeListener(e ->
                dataProvider.refreshAll()
        );

        price.addValueChangeListener(e ->
                dataProvider.refreshAll()
        );

        vendors.addValueChangeListener(e ->
                dataProvider.refreshAll()
        );


        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);


            Image image = new Image(e.getImageUrl(),e.getProductName());

            image.setHeight("120px");
            image.setWidth("120px");

            Span span = new Span(e.getProductName());

            span.getStyle()
                    .set("max-width", "300px")
                    .set("white-space", "normal")
                    .set("overflow-wrap", "break-word");

            h.add(
                  image,
                  span
            );






            return h;

        }).setAutoWidth(true).setHeader("Material");


        grid.addComponentColumn(e->{
            Span span = commonComponents.spanCrafter(e.getPrice() + " Eur","stat-example");

            return span;

        }).setAutoWidth(true).setHeader("Price");




        grid.addComponentColumn(e->{

            VerticalLayout horizontalLayout = new VerticalLayout();
            horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            if(e.getSellerCount() == 1){
                Image image = new Image();
                image.setWidth("80px"); image.setHeight("40px");

                image.setSrc(e.getVendorImageUrl());


                Button button = new Button("Go to source");
                button.setSuffixComponent(VaadinIcon.EXTERNAL_LINK.create());
                button.addClickListener(s ->{
                    UI.getCurrent().getPage().open(
                            e.getVendorUrl(),
                            "_blank");

                });

                horizontalLayout.add(
                        image,
                        button
                );


            }

            else{

                Button moreItems = new Button(e.getSellerCount() + " Offers found");

                Button button = new Button("Go to source");
                button.setSuffixComponent(VaadinIcon.EXTERNAL_LINK.create());

                button.addClickListener(s ->{
                    UI.getCurrent().getPage().open(
                            e.getVendorUrl(),
                            "_blank");

                });

                popOverData(moreItems,e.getMultipleVendors(),e.getImageUrl(),e.getProductName());

                horizontalLayout.add(
                        moreItems,
                        button
                );

            }


            return horizontalLayout;

        }).setAutoWidth(true).setHeader("Seller/Source");








//        grid.addComponentColumn(e->{
//
//            Button button = new Button("Smth");
//
//            return button;
//
//        }).setAutoWidth(true).setHeader("Actions");


        if(list == null || list.isEmpty()){
            v.setAlignItems(FlexComponent.Alignment.CENTER);
            v.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            v.setHeightFull();
            v.add(commonComponents.noDataFoundImproved("No offers found on the internet",null,null));
        }

        else{
            v.add(grid);

        }




        return v;

    }

    public void popOverData(Button button, List<MultipleVendors> vendor, String imageOfSelected, String  nameOfTheSelected){

        Popover popover = new Popover();
        popover.setWidth("700px");

        popover.setTarget(button);

        Grid<MultipleVendors> grid = new Grid<>(MultipleVendors.class,false);
        grid.setSizeFull();

        if(vendor !=null){
        grid.setItems(vendor);
        }

        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);


            Image image = new Image(e.getVendorImageUrl(),e.getVendorName());
            image.getStyle().set("border-radius","15px");
            image.setWidth("150px");
            image.setHeight("80px");


            h.add(
                    image
            );






            return h;

        }).setAutoWidth(true).setHeader("Vendor/Source");


        grid.addComponentColumn(e->{

            Span span = new Span(e.getPrice() + " Eur");

            return span;

        }).setAutoWidth(true).setHeader("Price");




        grid.addComponentColumn(e->{



                Button goToSource = new Button("Go to source");
                goToSource.setSuffixComponent(VaadinIcon.EXTERNAL_LINK.create());

                     goToSource.addClickListener(s ->{
                    UI.getCurrent().getPage().open(
                            e.getVendorUrl(),
                            "_blank");

                });






            return goToSource;

        }).setAutoWidth(true).setHeader("Source");



        VerticalLayout layout = new VerticalLayout();
        layout.setHeight("500px");


        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setAlignItems(FlexComponent.Alignment.CENTER);
        firstLayer.setWidthFull();
        firstLayer.setPadding(false);
        firstLayer.getStyle().set("margin-bottom","20px");

        Image image = new Image(imageOfSelected,"image");
        image.setWidth("120px");
        image.setHeight("80px");

        firstLayer.add(
                image,
                new Span(nameOfTheSelected)
        );

       layout.add(
               firstLayer,
               grid
       );

        layout.setPadding(false);
        layout.setSpacing(false);





        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.getStyle().set("margin-top","20px");
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);


        Button close = new Button("Back");
        close.addClickListener(e->{
            popover.close();
        });

        footer.add(
                close
        );

        layout.add(
                footer
        );

        popover.add(layout);
        popover.setTarget(button);


    }


}
