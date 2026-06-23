package com.example.demo.Pages.Material.MaterialAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.Enums.*;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
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

    // fields to enter data
    TextField materialName = new TextField("Material name");
    ComboBox<MaterialType> materialType = new ComboBox<>("Material type");
    TextField materialUrl = new TextField("Material URL");
    ComboBox<ActiveInactive> materialStatus = new ComboBox<>("Material status");

    TextArea description = new TextArea("Description");
    TextArea careInstuctions = new TextArea("Care instructions");

    TextField materialColor = new TextField("Material color");
    ComboBox<MaterialType> materialFinishType = new ComboBox("Material finish type");
    ComboBox<MaterialTextures> materialTexture = new ComboBox("Material texture");
    ComboBox<MaterialGrainPatterns> materialGrainPattern = new ComboBox("Material grain patern");

    NumberField materialPrice = new NumberField("Material price");
    NumberField materialUnitWeight = new NumberField("Material unit weight 'grams'");
    IntegerField materialMinThreshold = new IntegerField("Material min threshold");
    ComboBox<MaterialUnits> materialUnit = new ComboBox<>("Material unit");






    public MaterialAddPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productEditImage = new ProductEditImage(commonComponents,common);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        add(mainLayout());
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }



    public HorizontalLayout colorSelector(TextField textField) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setAlignItems(Alignment.BASELINE);

        Input colorPicker = new Input();
        colorPicker.addClassName("color-button");
        colorPicker.setHeight("55px");
        colorPicker.setType("color");
        colorPicker.setValue("#1e88e5");


        textField.addValueChangeListener(e->{
           colorPicker.setValue(e.getValue());
        });

        colorPicker.addValueChangeListener(e->{
            textField.setValue(e.getValue());
        });


        horizontalLayout.add(colorPicker,textField);

        return horizontalLayout;
    }



    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");



        verticalLayout.add(
                briefExplanation(),
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

        configueFields();

        VerticalLayout rightSide = new VerticalLayout();
        rightSide.setPadding(false);
        rightSide.setWidthFull();


        rightSide.add(
                basicInfo(),
                appearance(),
                pricingExtendedDetails()
        );

        return rightSide;

    }

    public VerticalLayout basicInfo(){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("island");

        verticalLayout.add(
                commonComponents.spanCrafterWordNoHide("Basic information","activityFeed-name"),
                commonComponents.doubleValueRow(materialName,materialType),
                commonComponents.doubleValueRow(materialUrl,materialStatus),
                description,
                careInstuctions
        );



        return  verticalLayout;
    }

    public VerticalLayout appearance(){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("island");

        verticalLayout.add(
                commonComponents.spanCrafterWordNoHide("Appearance","activityFeed-name"),
                commonComponents.doubleValueRow(colorSelector(materialColor),materialTexture),
                commonComponents.doubleValueRow(materialFinishType,materialGrainPattern)
        );



        return  verticalLayout;
    }

    public VerticalLayout pricingExtendedDetails(){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("island");

        verticalLayout.add(
                commonComponents.spanCrafterWordNoHide("Pricing & expanded details","activityFeed-name"),
                commonComponents.doubleValueRow(materialPrice,materialUnitWeight),
                commonComponents.doubleValueRow(materialMinThreshold,materialUnit)
        );



        return  verticalLayout;
    }


    public void configueFields(){
        // fields
        materialName.setWidthFull();

        materialType.setWidthFull();
        materialType.setItems(MaterialType.values());
        materialType.setItemLabelGenerator(MaterialType::getDisplayName);

        materialUrl.setWidthFull();

        materialStatus.setWidthFull();
        materialStatus.setItems(Arrays.stream(ActiveInactive.values()).filter(e->e != ActiveInactive.ALL).toList());
        materialStatus.setValue(ActiveInactive.ACTIVE);
        materialStatus.setItemLabelGenerator(ActiveInactive::getGetDisplayNames);

        description.setWidthFull();
        description.setHeight("100px");

        careInstuctions.setWidthFull();
        careInstuctions.setHeight("80px");

        materialColor.setWidthFull();

        materialTexture.setWidthFull();
        materialTexture.setItems(MaterialTextures.values());
        materialTexture.setItemLabelGenerator(MaterialTextures::getDisplayName);

        materialFinishType.setWidthFull();
        materialFinishType.setItems(MaterialType.values());
        materialFinishType.setItemLabelGenerator(MaterialType::getDisplayName);

        materialGrainPattern.setWidthFull();
        materialGrainPattern.setItems(MaterialGrainPatterns.values());
        materialGrainPattern.setItemLabelGenerator(MaterialGrainPatterns::getDisplayName);

        materialPrice.setWidthFull();
        materialPrice.setStepButtonsVisible(true);
        materialPrice.setStep(0.5);

        materialUnitWeight.setWidthFull();
        materialUnitWeight.setStepButtonsVisible(true);
        materialUnitWeight.setStep(0.1);

        materialMinThreshold.setWidthFull();
        materialMinThreshold.setStepButtonsVisible(true);

        materialUnit.setWidthFull();
        materialUnit.setItems(MaterialUnits.values());
        materialUnit.setItemLabelGenerator(MaterialUnits::getSymbol);
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







    public HorizontalLayout briefExplanation(){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Materials", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Create Material", ButtonVariant.LUMO_PRIMARY);


        buttonHolder.add(
                cancel,
                createOrder
        );


        h.add(
                commonComponents.biefPageExplanation("Create new material"),
                buttonHolder

        );


        return h;
    }



}
