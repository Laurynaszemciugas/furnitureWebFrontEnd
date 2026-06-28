package com.example.demo.Pages.Material.MaterialAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.Enums.*;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.example.demo.Pages.Material.MaterialAdd.Components.ColorSelector;
import com.example.demo.Pages.Material.MaterialAdd.Components.MaterialAddBriefExplanation;
import com.example.demo.Pages.Material.MaterialAdd.Components.RightSideAddMaterials;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Input;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "MaterialAdd", layout = MainLayout.class)
public class MaterialAddPage extends VerticalLayout implements BeforeEnterObserver {

    // pages required stuff
    CommonComponents commonComponents;
    Common common;
    ProductEditImage productEditImage;
    ObjectConverter objectConverter;



    // classes for the page
    MaterialAddBriefExplanation materialAddBriefExplanation;
    RightSideAddMaterials rightSideAddMaterials;




    public MaterialAddPage(CommonComponents commonComponents, Common common, ObjectConverter objectConverter) {
        this.commonComponents = commonComponents;
        this.common = common;


        this.productEditImage = new ProductEditImage(commonComponents,common);
        this.materialAddBriefExplanation = new MaterialAddBriefExplanation(commonComponents,common);
        this.rightSideAddMaterials = new RightSideAddMaterials(commonComponents,common,objectConverter);

        this.rightSideAddMaterials.setProductEditImage(productEditImage);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);




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
        verticalLayout.addClassName("main-island");



        verticalLayout.add(
                rightSideAddMaterials.briefExplanation(),
                leftRighJoin()
        );

        return verticalLayout;
    }

    public VerticalLayout leftSide(){

        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setWidthFull();
        leftSide.addClassName("island");
        List<CommonImagesData> s = new ArrayList<>();
        leftSide.add(

                productEditImage.images(s),
                productEditImage.uploadStuff()
        );



        return leftSide;

    }

    public VerticalLayout rightSide(){

        rightSideAddMaterials.configueFields();

        VerticalLayout rightSide = new VerticalLayout();
        rightSide.setPadding(false);
        rightSide.setWidthFull();


        rightSide.add(
                rightSideAddMaterials.basicInfo(),
                rightSideAddMaterials.appearance(),
                rightSideAddMaterials.pricingExtendedDetails()
        );

        return rightSide;

    }



    public HorizontalLayout leftRighJoin(){

        HorizontalLayout leftRighJoin = new HorizontalLayout();
        leftRighJoin.addClassName("layout-flex");
        leftRighJoin.setWidthFull();

        VerticalLayout leftSide = leftSide();
        leftSide.setWidth("500px");
        VerticalLayout rightSide = rightSide();
        rightSide.setWidth("910px");

        leftRighJoin.add(
                leftSide,
                rightSide
        );
        leftRighJoin.expand(leftSide);



        return leftRighJoin;

    }











}
