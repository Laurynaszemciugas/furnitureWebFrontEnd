package com.example.demo.Pages.Material.MaterialAddEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.ControllerModels.CommonDtos.MaterialImageData;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.Enums.*;
import com.example.demo.Common.Logic.ProductEditImage;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class RightSideMaterials {

    CommonComponents commonComponents;
    Common common;
    ColorSelector colorSelector;
    ObjectConverter objectConverter;

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
    IntegerField materialStock = new IntegerField("Material stock");
    TextField materialUnit = new TextField("Material unit");


    Binder<Void> binder = new Binder<>();

    ProductEditImage productEditImage;

    MaterialService materialService;

    Materials mat = new Materials();


    List<CommonImagesData> newImages = null;

    Consumer<Materials> materialsConsumer;

    public RightSideMaterials(CommonComponents commonComponents, Common common, MaterialService materialService, ObjectConverter objectConverter) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.colorSelector = new ColorSelector();
        this.materialService = materialService;
        this.objectConverter = objectConverter;



        configueFields();
        binder();



    }


    public void setProductEditImage(ProductEditImage productEditImage) {
        this.productEditImage = productEditImage;
        if (this.productEditImage != null) {
            productEditImage.setListConsumer(list -> {
                newImages = new ArrayList<>();
                newImages.addAll(list);
            });

            productEditImage.setMainChange(list -> {
                newImages = new ArrayList<>();
                newImages.addAll(list);
            });

        }
    }


    public void loadData(Materials materials){



        if(materials != null){

            mat.setId(materials.getId());

            if(!materials.getImages().isEmpty()) {
                productEditImage.loadData(objectConverter.convert(materials.getImages(), CommonImagesData.class));
            }

            materialName.setValue(materials.getMaterialName() == null ? "" : materials.getMaterialName());

            materialType.setValue(materials.getMaterialType());

            materialUrl.setValue(materials.getMaterialUrl() == null ? "" : materials.getMaterialUrl());

            materialStatus.setValue(materials.getEnabled());

            description.setValue(materials.getDescription() == null ? "" : materials.getDescription());

            careInstuctions.setValue(materials.getCareInstructions() == null ? "" : materials.getCareInstructions());

            materialColor.setValue(materials.getMaterialColor() == null ? "" : materials.getMaterialColor());

            materialFinishType.setValue(materials.getMaterialFinishType());

            materialTexture.setValue(materials.getMaterialTextures());

            materialGrainPattern.setValue(materials.getMaterialGrainPatterns());

            materialPrice.setValue(materials.getUnitPrice());

            materialUnitWeight.setValue(materials.getMaterialWeight());

            materialMinThreshold.setValue(
                    materials.getMinThresHold() == null ? null : materials.getMinThresHold().intValue()
            );

            materialStock.setValue(
                    materials.getInStock() == null ? null : materials.getInStock().intValue()
            );

            materialUnit.setValue(materials.getUnit() == null ? "" : materials.getUnit());


        }






    }




    public HorizontalLayout briefExplanation(String name){


        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Materials", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Save", ButtonVariant.LUMO_PRIMARY);


        createOrder.addClickListener(e->{

            if(binder.validate().isOk()){

                mat.setMaterialName(materialName.getValue());
                mat.setInStock(materialStock.getValue().longValue());
                mat.setMinThresHold(materialMinThreshold.getValue().longValue());
                mat.setEnabled(ActiveInactive.ACTIVE);
                mat.setMaterialWeight(materialUnitWeight.getValue());
                mat.setUnitPrice(materialPrice.getValue());
                mat.setUnit(materialUnit.getValue());
                mat.setDescription(description.getValue());
                mat.setMaterialType(materialType.getValue());
                mat.setMaterialUrl(materialUrl.getValue());
                mat.setCareInstructions(careInstuctions.getValue());
                mat.setMaterialColor(materialColor.getValue());
                mat.setMaterialTextures(materialTexture.getValue());
                mat.setMaterialFinishType(materialFinishType.getValue());
                mat.setMaterialGrainPatterns(materialGrainPattern.getValue());


                if(newImages !=null){
                    System.out.println("changing pictures");
                    for(var s : newImages){
                        s.setImageUrl(common.imageMaker(s.getImageData(),s.getImageType()));
                    }

                    mat.setImages(objectConverter.convert(newImages, MaterialImageData.class));

                }




                materialsConsumer.accept(mat);


            }
            else{
                commonComponents.showNotification("Form is not filled properly",4000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
            }

        });


        buttonHolder.add(
                cancel,
                createOrder
        );


        h.add(
                commonComponents.biefPageExplanation(name),
                buttonHolder

        );


        return h;
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
                commonComponents.doubleValueRow(colorSelector.colorSelector(materialColor),materialTexture),
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
                commonComponents.doubleValueRow(materialMinThreshold,materialStock),
                materialUnit
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

        materialStock.setWidthFull();
        materialStock.setStepButtonsVisible(true);


        materialUnit.setWidthFull();
    }


    public void binder() {

        binder.forField(materialName)
                .asRequired("Material name is required")
                .withValidator(
                        value -> value == null || value.trim().length() >= 2,
                        "Material name must be at least 2 characters"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialType)
                .asRequired("Material type is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialUrl)
                .withValidator(
                        value -> value == null ||
                                value.isBlank() ||
                                value.startsWith("http://") ||
                                value.startsWith("https://"),
                        "URL must start with http:// or https://"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialStatus)
                .asRequired("Material status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(description)
                .asRequired("Description is required")
                .withValidator(
                        value -> value == null || value.trim().length() >= 5,
                        "Description must be at least 5 characters"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(careInstuctions)
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialColor)
                .asRequired("Material color is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialFinishType)
                .asRequired("Material finish type is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialTexture)
                .asRequired("Material texture is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialGrainPattern)
                .asRequired("Material grain pattern is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialPrice)
                .asRequired("Material price is required")
                .withValidator(
                        value -> value == null || value >= 0,
                        "Material price cannot be negative"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialUnitWeight)
                .asRequired("Material unit weight is required")
                .withValidator(
                        value -> value == null || value >= 0,
                        "Material unit weight cannot be negative"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialMinThreshold)
                .asRequired("Material min threshold is required")
                .withValidator(
                        value -> value == null || value >= 0,
                        "Material min threshold cannot be negative"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialStock)
                .asRequired("Material stock is required")
                .withValidator(
                        value -> value == null || value >= 0,
                        "Material stock cannot be negative"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(materialUnit)
                .asRequired("Material unit is required")
                .bind(v -> null, (v, value) -> {});
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



    public HorizontalLayout leftRighJoin(Materials materials){

        loadData(materials);

        HorizontalLayout leftRighJoin = new HorizontalLayout();
        leftRighJoin.addClassName("layout-flex");
        leftRighJoin.setWidthFull();

        VerticalLayout leftSide = leftSide();
        leftSide.setWidth("500px");
        VerticalLayout rightSide = rightSide();
        rightSide.setWidth("900px");

        leftRighJoin.add(
                leftSide,
                rightSide
        );
        leftRighJoin.expand(leftSide);



        return leftRighJoin;

    }


}
