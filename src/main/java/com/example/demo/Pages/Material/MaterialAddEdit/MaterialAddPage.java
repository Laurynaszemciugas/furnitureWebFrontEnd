package com.example.demo.Pages.Material.MaterialAddEdit;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Common.Logic.ProductEditImage;
import com.example.demo.Pages.Material.MaterialAddEdit.Components.MaterialAddBriefExplanation;
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
    MaterialAddBriefExplanation materialAddBriefExplanation;
    RightSideMaterials rightSideAddMaterials;




    public MaterialAddPage(CommonComponents commonComponents, Common common, ObjectConverter objectConverter, MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.objectConverter = objectConverter;
        this.materialService = materialService;

        this.productEditImage = new ProductEditImage(commonComponents,common);
        this.materialAddBriefExplanation = new MaterialAddBriefExplanation(commonComponents,common);
        this.rightSideAddMaterials = new RightSideMaterials(commonComponents,common,materialService,objectConverter);

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
