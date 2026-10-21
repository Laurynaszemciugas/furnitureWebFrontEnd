package com.example.demo.Pages.Products;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.Common.Logic.ProductEditImage;
import com.example.demo.Common.ReviewCrafter;
import com.example.demo.DTOS.BreadCrums.BreadCrumsDto;
import com.example.demo.DTOS.Common.CommonImagesData;
import com.example.demo.Entity.Product;
import com.example.demo.MainLayouts.Admin.MainLayout;
import com.example.demo.Pages.Products.Components.ProductEditRightSideFields;
import com.example.demo.Services.AI.AIService;
import com.example.demo.Services.CommonService.CommonService;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.ProductEditService.ProductEditService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "ProductsAddMulti", layout = MainLayout.class)
public class AddNewMultiProducts extends VerticalLayout implements BeforeEnterObserver {



    CommonComponents commonComponents;
    Common common;
    ProductEditService productEditService;
    CommonService commonService;
    MaterialService materialService;

    ObjectConverter objectConverter;

    ProductEditRightSideFields productEditRightSideFields;
    ProductEditImage productEditImage;
    ReviewCrafter reviewCrafter;

    ProductService productService;




    AIService aiService;

    MultipleProductChoices multipleProductChoices;


    public AddNewMultiProducts(CommonComponents commonComponents,
                               Common common,
                               CommonService commonService,
                               ProductEditService productEditService,
                               ObjectConverter objectConverter,
                               MaterialService materialService,
                               AIService aiService,
                               ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonService = commonService;
        this.productEditService = productEditService;
        this.objectConverter = objectConverter;
        this.materialService = materialService;
        this.aiService = aiService;
        this.productService = productService;
        this.productEditRightSideFields = new ProductEditRightSideFields(commonComponents,common,commonService,objectConverter,materialService,aiService,productService);
        this.productEditImage = new ProductEditImage(commonComponents,common);
        this.reviewCrafter = new ReviewCrafter(commonComponents,common);

        this.multipleProductChoices = new MultipleProductChoices(commonComponents,common,commonService,objectConverter,materialService,aiService,productService);

        this.productEditRightSideFields.setProductEditImage(this.productEditImage);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);




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


        productEditRightSideFields.setConsumer(e->{


            productEditService.updateProductEdit(e);

        });



        verticalLayout.add(joinImagesInfo());

        return verticalLayout;
    }


    public HorizontalLayout joinImagesInfo(){
        HorizontalLayout h =new HorizontalLayout();
        h.setWidthFull();
        h.getStyle().set("flex-wrap","wrap");




            removeAll();

            h.add(
                    multipleProductChoices.mainLayout(new Product(),true)
            );




        return h;
    }




    public VerticalLayout briefPageExplanation(){
        VerticalLayout left = new VerticalLayout();

        left.add(
                commonComponents.breadCrums(new BreadCrumsDto("Products", "Products/1"),new BreadCrumsDto("Edit existing product", null)),
                productEditRightSideFields.briefPageExplanation("Edit existing product")
        );
        left.setWidthFull();

        return left;
    }













}
