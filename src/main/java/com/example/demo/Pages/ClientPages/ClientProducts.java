package com.example.demo.Pages.ClientPages;

import com.example.demo.Common.AI.AiQuestion;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.DTOS.ActionLogs.ActionLogFeed;
import com.example.demo.DTOS.BreadCrums.BreadCrumsDto;
import com.example.demo.DTOS.Client.ClientCategorys;
import com.example.demo.DTOS.Client.ProductsClients;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.FilterDTO.Client.ClientProductsFilter;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.slider.IntegerRangeSlider;
import com.vaadin.flow.component.slider.IntegerRangeSliderValue;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.P;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Route(value = "ClientProducts" , layout = ClientMainLayout.class)
public class ClientProducts extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductUI productUI;
    SessionCrafter sessionCrafter;

    ProductService productService;

    Paganation paganation;

    // client pages filters

    ClientProductsFilter clientProductsFilter = new ClientProductsFilter();


    VerticalLayout productMemory = new VerticalLayout();


    // filter memory
    VerticalLayout filterHolder = new VerticalLayout();


    public ClientProducts(CommonComponents commonComponents, Common common, ProductUI productUI, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productUI = productUI;
        this.productService = productService;
        this.paganation = new Paganation();
        this.sessionCrafter = new SessionCrafter();


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");


        // default settings
        productMemory.setPadding(false);



    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();





        add(mainLayout());


    }



    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");



        verticalLayout.add(
            leftRightHolder()
        );




        return verticalLayout;
    }



    public HorizontalLayout leftRightHolder(){

        HorizontalLayout holder = new HorizontalLayout();
        holder.addClassName("layout-flex");
        holder.setWidthFull();


        VerticalLayout left = new VerticalLayout();
        left.setPadding(false);
        left.addClassName("island");
        left.setWidth("350px");


        // add the filters
        filteringLayout();
        left.add(
                filterHolder
        );


        VerticalLayout right = new VerticalLayout();
        right.setPadding(false);
        right.setWidth("1000px");

        // update products
        updateProducts();

        right.add(
                productMemory
        );


        holder.add(left, right);
        holder.expand(right);

        return holder;

    }

    public void filteringLayout(){

        // clear all to stop dublicates
        filterHolder.removeAll();

        filterHolder.setSpacing(false);
        filterHolder.setPadding(false);



        // =====================================================
        // first part filter name and reset button
        // =====================================================

        HorizontalLayout resetHolder = new HorizontalLayout();
        resetHolder.setJustifyContentMode(JustifyContentMode.BETWEEN);
        resetHolder.setAlignItems(Alignment.CENTER);
        resetHolder.setWidthFull();

        Button resetFilters = new Button("Reset all");
        resetFilters.addClickListener(e->{
            // reset filter layout
            filteringLayout();

            // reset filter
            clientProductsFilter = new ClientProductsFilter();

            // reload data
            updateProducts();

        });

        resetHolder.add(
                new HorizontalLayout(
                        commonComponents.iconCrafter(VaadinIcon.FILTER,"25px","grey"),
                        commonComponents.spanCrafter("Filters","stat-example")),
                resetFilters

        );

        // =====================================================
        // category selection
        // =====================================================

        Accordion accordion = new Accordion();
        accordion.setWidthFull();

        AccordionPanel categoryPanel = accordion.add("Category", categoryFilterBuilder());
        categoryPanel.addThemeVariants(DetailsVariant.REVERSE);


        // =====================================================
        // price selection
        // =====================================================

        Accordion accordion2 = new Accordion();
        accordion2.setWidthFull();

        AccordionPanel pricePanel = accordion2.add("Price", RangeSliderBasic());
        pricePanel.addThemeVariants(DetailsVariant.REVERSE);

        // =====================================================
        // Material selection
        // =====================================================

        Accordion accordion3 = new Accordion();
        accordion3.setWidthFull();

        AccordionPanel materialPanel = accordion3.add("Material", materialFilterBuilder());
        materialPanel.addThemeVariants(DetailsVariant.REVERSE);


        // =====================================================
        // color selection
        // =====================================================

        Accordion accordion4 = new Accordion();
        accordion4.setWidthFull();

        AccordionPanel colorPanel = accordion4.add("Colors", colorsFilterBuilder());
        colorPanel.addThemeVariants(DetailsVariant.REVERSE);



        // =====================================================
        // discount selection
        // =====================================================

        Accordion accordion6 = new Accordion();
        accordion6.setWidthFull();

        AccordionPanel discountPanel = accordion6.add("Discount", discountFilterBuilder());
        discountPanel.addThemeVariants(DetailsVariant.REVERSE);



        // =====================================================
        // availability selection
        // =====================================================

        Accordion accordion5 = new Accordion();
        accordion5.setWidthFull();

        AccordionPanel availabilityPanel = accordion5.add("Availability", stockFilterBuilder());
        availabilityPanel.addThemeVariants(DetailsVariant.REVERSE);







        // Apply filters
        Button applyFilters = new Button("Apply filters");
        applyFilters.setWidthFull();
        applyFilters.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        applyFilters.addClickListener(e->{

            updateProducts();

        });





        filterHolder.add(
                resetHolder,
                commonComponents.lineCrafter("100%"),
                accordion,
                commonComponents.lineCrafter("100%"),
                accordion2,
                commonComponents.lineCrafter("100%"),
                accordion3,
                commonComponents.lineCrafter("100%"),
                accordion4,
                commonComponents.lineCrafter("100%"),
                accordion6,
                commonComponents.lineCrafter("100%"),
                accordion5,
                commonComponents.lineCrafter("100%"),
                applyFilters

        );


    }






    public VerticalLayout materialFilterBuilder(){

        VerticalLayout categoryHolder =  new VerticalLayout();
        categoryHolder.setPadding(false);

        List<String> materialNames = new ArrayList<>(productService.findAllAvailableMaterialNames());
        // add the last one as All always
        materialNames.add("All");

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems(materialNames);



        // default choice
        checkboxGroup.setValue(Collections.singleton("All"));

        checkboxGroup.addValueChangeListener(e->{


            if(!e.isFromClient()) return;

            if (e.getValue().contains("All")) {
                checkboxGroup.clear();
                checkboxGroup.setValue(Collections.singleton("All"));


                List<String> all = new ArrayList<>();
                all.add(
                        Category.ALL.getDisplayName()
                );
                clientProductsFilter.setMaterials(all);

            }
            else {

                Set<String> set = e.getValue();

                clientProductsFilter.setMaterials(set.stream().toList());
            }
        });


        categoryHolder.add(
                checkboxGroup
        );


        return categoryHolder;

    }


    public VerticalLayout stockFilterBuilder(){

        VerticalLayout categoryHolder =  new VerticalLayout();
        categoryHolder.setPadding(false);

        RadioButtonGroup<Stock> checkboxGroup = new RadioButtonGroup<>();

        checkboxGroup.setItems(Stock.values());

        checkboxGroup.setItemLabelGenerator(Stock::getDisplayName);

        // default choice
        checkboxGroup.setValue(Stock.ALL);

        checkboxGroup.addValueChangeListener(e->{


            clientProductsFilter.setStocks(e.getValue().toString());


        });


        categoryHolder.add(
                checkboxGroup
        );


        return categoryHolder;

    }



    public VerticalLayout discountFilterBuilder(){

        VerticalLayout categoryHolder =  new VerticalLayout();
        categoryHolder.setPadding(false);

        RadioButtonGroup<String> checkboxGroup = new RadioButtonGroup<>();

        checkboxGroup.setItems("Discounted","No discount", "All");

        // default choice
        checkboxGroup.setValue("All");

        checkboxGroup.addValueChangeListener(e->{


            if(e.getValue().equals("Discounted")){
                clientProductsFilter.setDiscount(Boolean.TRUE);
            }
            else if(e.getValue().equals("No discount")){
                clientProductsFilter.setDiscount(Boolean.FALSE);
            }
            else{
                clientProductsFilter.setDiscount(null);
            }





        });


        categoryHolder.add(
                checkboxGroup
        );


        return categoryHolder;

    }





    public HorizontalLayout colorsFilterBuilder(){


        String defaultColor = "#00000000";

        Icon icon = commonComponents.iconCrafter(VaadinIcon.CHECK,"25px","White");
        Icon allColors = commonComponents.iconCrafter(VaadinIcon.GLOBE,"25px","RoyalBlue");

        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.setWidthFull();

        List<String> colors = new ArrayList<>(productService.findAllAvailableColors());

        colors.add(
                defaultColor
        );


        for(var s : colors){
            VerticalLayout v = new VerticalLayout();
            v.setJustifyContentMode(JustifyContentMode.CENTER);
            v.setAlignItems(Alignment.CENTER);
            v.setWidth("25px");
            v.setHeight("25px");
            v.getStyle().setBorderRadius("50%");

            v.getStyle().setBackground(s);

            v.addClassName("island-hover");

            // All colors indictor
            if(s.equals(defaultColor)){
                v.add(
                        allColors
                );

                Tooltip.forComponent(v)
                        .withText("All colors");

            }
            else{
                Tooltip.forComponent(v)
                        .withText("Color hex: " + s);
            }

            v.addClickListener(e->{

                if(s.equals(defaultColor)){
                    icon.setVisible(false);
                    v.add(icon);
                    clientProductsFilter.setColor("All");
                    allColors.setColor("RoyalBlue");
                }
                else{
                    icon.setVisible(true);
                    v.add(icon);
                    clientProductsFilter.setColor(s);
                    allColors.setColor("grey");
                }



            });


            h.add(
                    v
            );

        }



        return h;
    }


    public VerticalLayout categoryFilterBuilder(){

        VerticalLayout categoryHolder =  new VerticalLayout();
        categoryHolder.setPadding(false);


        List<ClientCategorys> clientCategorys = productService.getClientCategorysWithCount();

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();

        List<String> categoryList = clientCategorys.stream().map(s -> s.getCategory().getDisplayName()).toList();

        checkboxGroup.setItems(categoryList);

        // default choice
        checkboxGroup.setValue(Collections.singleton("All"));

        checkboxGroup.setRenderer(new ComponentRenderer<>(category ->{

            HorizontalLayout h = new HorizontalLayout();


            Long count  = clientCategorys.stream().filter(s -> s.getCategory().getDisplayName().equals(category)).map(s -> s.getCount()).findFirst().orElse(0L);

            h.add(
                new Span(category),
                    commonComponents.spanCrafterWordNoHide(
                            String.format("(%s)",count),
                            "stat-description"
                    )


            );

            return h;

        }));

        checkboxGroup.addValueChangeListener(e->{



            if(!e.isFromClient()) return;

            if (e.getValue().contains("All")) {
                checkboxGroup.clear();
                checkboxGroup.setValue(Collections.singleton("All"));

                List<String> all = new ArrayList<>();
                all.add(
                        Category.ALL.toString()
                );
                clientProductsFilter.setCategories(all);

            }
            else {

                Set<String> selectedCategories = e.getValue();

                clientProductsFilter.setCategories(selectedCategories.stream().toList());
            }
        });


        categoryHolder.add(checkboxGroup);




        return categoryHolder;
    }


        public VerticalLayout RangeSliderBasic() {

            int minValue = 0;
            int maxValue = 10000;

            VerticalLayout v = new VerticalLayout();
            v.setSpacing(false);
            v.setPadding(false);
            v.setWidthFull();

            IntegerRangeSlider rangeSlider = new IntegerRangeSlider(
                    minValue, maxValue);
            rangeSlider.setWidth("100%");
            rangeSlider.setValueChangeMode(ValueChangeMode.EAGER);
            rangeSlider.setValue(new IntegerRangeSliderValue(minValue, maxValue));
            rangeSlider.setAccessibleNameStart("Minimum price");
            rangeSlider.setAccessibleNameEnd("Maximum price");
            add(rangeSlider);


            HorizontalLayout fieldHolder = new HorizontalLayout();
            fieldHolder.setWidthFull();
            fieldHolder.setJustifyContentMode(JustifyContentMode.BETWEEN);
            fieldHolder.setAlignItems(Alignment.CENTER);

            IntegerField fromPrice = new IntegerField();
            fromPrice.setValueChangeMode(ValueChangeMode.EAGER);
            fromPrice.setSuffixComponent(new Span("€"));
            fromPrice.setWidth("100px");

            IntegerField toPrice = new IntegerField();
            toPrice.setValueChangeMode(ValueChangeMode.EAGER);
            toPrice.setSuffixComponent(new Span("€"));
            toPrice.setWidth("100px");


            // change the value from the fields
            fromPrice.addValueChangeListener(e->{
                if(e.isFromClient()) {
                    try{
                        rangeSlider.setValue(new IntegerRangeSliderValue(e.getValue(), toPrice.getValue()));
                    } catch (IllegalArgumentException ex) {
                        rangeSlider.setValue(new IntegerRangeSliderValue(minValue, toPrice.getValue()));
                    }
                }
            });

            toPrice.addValueChangeListener(e->{
                if(e.isFromClient()) {

                        rangeSlider.setValue(new IntegerRangeSliderValue(fromPrice.getValue(), e.getValue()));
                        if(e.getValue() > maxValue) {
                            rangeSlider.setValue(new IntegerRangeSliderValue(fromPrice.getValue(), maxValue));
                        }

                }
            });

            fieldHolder.add(
                    fromPrice,toPrice
            );

            fromPrice.setValue(rangeSlider.getValue().start());
            toPrice.setValue(rangeSlider.getValue().end());

            rangeSlider.addValueChangeListener(e->{

                fromPrice.setValue(e.getValue().start());
                toPrice.setValue(e.getValue().end());



                clientProductsFilter.setPriceFrom(Double.valueOf(fromPrice.getValue()));
                clientProductsFilter.setPriceTo(Double.valueOf(toPrice.getValue()));


            });

            v.add(
                    fieldHolder,
                    rangeSlider
            );

            return v;

    }


    // update and show loading thingy
    public void updateProducts(){

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        productMemory.removeAll();
        productMemory.add(
                commonComponents.shimmer(5)
        );

        CompletableFuture
                .supplyAsync(()->{

                    List<ProductsClients> items = productService.getProductsAccordingToFilter(clientProductsFilter,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        productMemory.removeAll();

                        productMemory.add(
                                productUI.productDisplay(e, "250px","250px","250px"),
                                paganation.buttonHolder(10)
                        );
                    });
                });



    }




}
