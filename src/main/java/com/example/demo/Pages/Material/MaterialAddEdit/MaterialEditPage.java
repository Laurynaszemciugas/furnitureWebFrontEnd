package com.example.demo.Pages.Material.MaterialAddEdit;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.Common.Logic.ProductEditImage;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Material.MaterialAddEdit.Components.RightSideMaterials;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "MaterialEdit/:item", layout = MainLayout.class)
public class MaterialEditPage extends VerticalLayout implements BeforeEnterObserver {


    // pages required stuff
    CommonComponents commonComponents;
    Common common;
    ProductEditImage productEditImage;
    ObjectConverter objectConverter;
    MaterialService materialService;



    // classes for the page
    RightSideMaterials rightSideAddMaterials;


    int itemChoice = 0;


    public MaterialEditPage(CommonComponents commonComponents, Common common, ObjectConverter objectConverter, MaterialService materialService) {
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




    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        int page = Math.toIntExact( Integer.parseInt(beforeEnterEvent.getRouteParameters().get("item").orElse(null)));

        this.itemChoice  = page;

        add(mainLayout());


    }









    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");


        Materials mat = new Materials();
        mat.setMaterialName("zaza");


        rightSideAddMaterials.setMaterialsConsumer(e->{
            materialService.editProduct(e);
        });


        verticalLayout.add(
                rightSideAddMaterials.briefExplanation("Edit existing Material"),
                rightSideAddMaterials.leftRighJoin(materialService.getMaterial((long) itemChoice))
        );

        return verticalLayout;
    }



}
