package com.example.demo.Pages.ClientPages;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.DTOS.BreadCrums.BreadCrumsDto;
import com.example.demo.DTOS.Client.ClientCategorys;
import com.example.demo.Enums.Category;
import com.example.demo.FilterDTO.Client.ClientProductsFilter;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.slider.IntegerRangeSlider;
import com.vaadin.flow.component.slider.IntegerRangeSliderValue;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;


import java.util.List;
import java.util.Set;

@Route(value = "ClientProducts" , layout = ClientMainLayout.class)
public class ClientProducts extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductUI productUI;

    ProductService productService;

    Paganation paganation;

    // client pages filters

    ClientProductsFilter clientProductsFilter = new ClientProductsFilter();


    public ClientProducts(CommonComponents commonComponents, Common common, ProductUI productUI, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productUI = productUI;
        this.productService = productService;
        this.paganation = new Paganation();


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");

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

        left.add(
                filteringLayout()
        );


        VerticalLayout right = new VerticalLayout();
        right.setPadding(false);
        right.setWidth("1000px");

        right.add(
                productUI.productDisplay(productService.getProductsClients(10), "250px","250px","250px"),
                paganation.buttonHolder(10)
        );


        holder.add(left, right);
        holder.expand(right);

        return holder;

    }

    public VerticalLayout filteringLayout(){

        VerticalLayout holder = new VerticalLayout();
        holder.setSpacing(false);
        holder.setPadding(false);
        holder.setPadding(false);



        // =====================================================
        // first part filter name and reset button
        // =====================================================

        HorizontalLayout resetHolder = new HorizontalLayout();
        resetHolder.setJustifyContentMode(JustifyContentMode.BETWEEN);
        resetHolder.setAlignItems(Alignment.CENTER);
        resetHolder.setWidthFull();

        Button resetFilters = new Button("Reset all");

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

        Accordion accordion2 = new Accordion();
        accordion2.setWidthFull();

        AccordionPanel pricePanel = accordion2.add("Price", RangeSliderBasic());
        pricePanel.addThemeVariants(DetailsVariant.REVERSE);


        // Apply filters


        Button applyFilters = new Button("Apply filters");
        applyFilters.setWidthFull();
        applyFilters.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        applyFilters.addClickListener(e->{

            System.out.println(clientProductsFilter);

        });

        holder.add(
                resetHolder,
                commonComponents.lineCrafter("100%"),
                accordion,
                commonComponents.lineCrafter("100%"),
                accordion2,
                commonComponents.lineCrafter("100%"),
                applyFilters

        );


        return holder;
    }


    public VerticalLayout categoryFilterBuilder(){

        VerticalLayout categoryHolder =  new VerticalLayout();
        categoryHolder.setPadding(false);


        List<ClientCategorys> clientCategorys = productService.getClientCategorysWithCount();

        CheckboxGroup<Category> checkboxGroup = new CheckboxGroup<>();

        List<Category> categoryList = clientCategorys.stream().map(s -> s.getCategory()).toList();

        checkboxGroup.setItems(categoryList);

        checkboxGroup.setRenderer(new ComponentRenderer<>(category ->{

            HorizontalLayout h = new HorizontalLayout();


            Long count  = clientCategorys.stream().filter(s -> s.getCategory().equals(category)).map(s -> s.getCount()).findFirst().orElse(0L);

            h.add(
                new Span(category.getDisplayName()),
                    commonComponents.spanCrafterWordNoHide(
                            String.format("(%s)",count),
                            "stat-description"
                    )


            );

            return h;

        }));

        checkboxGroup.addValueChangeListener(e->{

            Set<Category> selectedCategories = e.getValue();

            clientProductsFilter.setCategories(selectedCategories.stream().toList());

        });


        categoryHolder.add(checkboxGroup);




        return categoryHolder;
    }


        public VerticalLayout RangeSliderBasic() {

            VerticalLayout v = new VerticalLayout();
            v.setSpacing(false);
            v.setPadding(false);
            v.setWidthFull();

            IntegerRangeSlider rangeSlider = new IntegerRangeSlider(
                    0, 1000);
            rangeSlider.setWidth("100%");
            rangeSlider.setValueChangeMode(ValueChangeMode.EAGER);
            rangeSlider.setValue(new IntegerRangeSliderValue(200, 800));
            rangeSlider.setAccessibleNameStart("Minimum price");
            rangeSlider.setAccessibleNameEnd("Maximum price");
            add(rangeSlider);


            HorizontalLayout fieldHolder = new HorizontalLayout();
            fieldHolder.setWidthFull();
            fieldHolder.setJustifyContentMode(JustifyContentMode.BETWEEN);
            fieldHolder.setAlignItems(Alignment.CENTER);

            IntegerField fromPrice = new IntegerField();
            fromPrice.setSuffixComponent(new Span("€"));
            fromPrice.setWidth("100px");

            IntegerField toPrice = new IntegerField();
            toPrice.setSuffixComponent(new Span("€"));
            toPrice.setWidth("100px");

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




}
