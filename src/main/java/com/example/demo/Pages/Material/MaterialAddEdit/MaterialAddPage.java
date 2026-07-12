package com.example.demo.Pages.Material.MaterialAddEdit;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Common.Logic.ProductEditImage;
import com.example.demo.Pages.Material.MaterialAddEdit.Components.RightSideMaterials;
import com.example.demo.Services.Material.MaterialService;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "MaterialAdd", layout = MainLayout.class)
public class MaterialAddPage extends VerticalLayout implements BeforeEnterObserver {

    // pages required stuff
    CommonComponents commonComponents;
    Common common;
    ProductEditImage productEditImage;
    ObjectConverter objectConverter;
    MaterialService materialService;



    // classes for the page
    RightSideMaterials rightSideAddMaterials;




    public MaterialAddPage(CommonComponents commonComponents, Common common, ObjectConverter objectConverter, MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.objectConverter = objectConverter;
        this.materialService = materialService;

        this.productEditImage = new ProductEditImage(commonComponents,common);
        this.rightSideAddMaterials = new RightSideMaterials(commonComponents,common,materialService,objectConverter);

        this.rightSideAddMaterials.setProductEditImage(productEditImage);

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





        rightSideAddMaterials.setMaterialsConsumer(e->{
            materialService.saveNewMaterial(e);



        });


        verticalLayout.add(
                rightSideAddMaterials.briefExplanation("Create new Material","Create new"),
                rightSideAddMaterials.leftRighJoin(null)
        );

        return verticalLayout;
    }













}
