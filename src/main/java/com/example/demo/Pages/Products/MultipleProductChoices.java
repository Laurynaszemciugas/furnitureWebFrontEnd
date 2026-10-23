package com.example.demo.Pages.Products;

import com.example.demo.Common.*;
import com.example.demo.Common.AI.MultiProductAiDto;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.Common.Logic.ProductEditImage;
import com.example.demo.DTOS.Common.CommonImagesData;
import com.example.demo.DTOS.Common.ListExtraDetailsGrid;
import com.example.demo.DTOS.Common.ListStepsToPrepare;
import com.example.demo.DTOS.Material.MaterialInfo;
import com.example.demo.Entity.*;
import com.example.demo.Entity.ProductJoin.ProductFinishSteps;
import com.example.demo.Entity.ProductJoin.ProductMaterials;
import com.example.demo.Enums.*;
import com.example.demo.Pages.Products.Components.Grids;
import com.example.demo.Pages.Products.Components.MaterialAndDetails;
import com.example.demo.Services.AI.AIService;
import com.example.demo.Services.CommonService.CommonService;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.Style;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

//@Route(value = "testtttttt", layout = MainLayout.class)
public class MultipleProductChoices extends VerticalLayout  {



    CommonComponents commonComponents;
    Common common;
    CommonService commonService;
    Grids grids;

    MaterialService materialService;

    ProductService productService;


    ProductEditImage productEditImage;

    MaterialAndDetails materialAndDetails;

    ObjectConverter objectConverter;




    private Binder<Product> binder =
            new Binder<>(Product.class);

    private TextField displayedName = new TextField("Displayed name");

    private TextField productColor = new TextField("Product color");

    private TextField productName = new TextField("Product name");
    private TextField sku = new TextField("SKU");
    private TextArea description = new TextArea("Description");

    private NumberField price = new NumberField("Price");
    private NumberField discount = new NumberField("Discount %");
    private NumberField materialCost = new NumberField("Material Cost");

    private NumberField stockQuantity = new NumberField("Stock Quantity");
    private NumberField lowThreshold = new NumberField("Low Stock Threshold");

    private Checkbox manualStock = new Checkbox("Manual stock 'Setting stock by hand not effecting the materials stock'");

    private ComboBox<Category> category = new ComboBox<>("Category");
    private ComboBox<Tags> tags = new ComboBox<>("Tags");


    private ComboBox<Status> status = new ComboBox<>("Status");
    private ComboBox<Visibility> visibility = new ComboBox<>("Visibility");




    Button save;
    Button goBack;

    Button addNewVariant;

    Button addNewDetail = new Button();

    Button addNewStep = new Button();

    List<ListExtraDetailsGrid> listExtraDetailsGrids = new ArrayList<>();
    Grid<ListExtraDetailsGrid> extraDetailsGrid = new Grid<>(ListExtraDetailsGrid.class,false);

    List<ListStepsToPrepare> listStepsToPrepares = new ArrayList<>();
    Grid<ListStepsToPrepare> listStepsToPrepareGrid = new Grid<>(ListStepsToPrepare.class,false);


    Grid<MaterialInfo> productFeedModelGrid = new Grid<>(MaterialInfo.class,false);

    List<Tags> tagss = new ArrayList<>();


    Button addNewMaterial = new Button();

    List<MaterialInfo> materialInfoList = new ArrayList<>();

    // send message that info is ready
    Consumer<Product> consumer;

    HorizontalLayout tagsSelected = new HorizontalLayout();

    List<CommonImagesData> newImages = null;

    ColorSelector colorSelector;

    // total material cost
    Span totalMaterialCost;

    // memory of the material cost
    Double value;

    private MultiProductAiDto multiProductAiDto = new MultiProductAiDto();

    public void setConsumer (Consumer<Product> consumer){
        this.consumer = consumer;
    }


    AIService aiService;

    VerticalLayout rightSide =new VerticalLayout();
    VerticalLayout multipleProductHolder = new VerticalLayout();
    HorizontalLayout multipleProductHolderButtons = new HorizontalLayout();

    Product product = new Product();


    List<ProductMultiple> multipleProducts = new ArrayList<>();


    // display all prodcut variants
    HorizontalLayout joinImageAndRightSideHolder =new HorizontalLayout();


    // global value that tells if its editing or adding new
    boolean addNew = false;


    public MultipleProductChoices(CommonComponents commonComponents,
                                  Common common,
                                  CommonService commonService,
                                  ObjectConverter objectConverter,
                                  MaterialService materialService,
                                  AIService aiService,
                                  ProductService productService
    ) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonService = commonService;
        this.objectConverter = objectConverter;
        this.materialService = materialService;
        this.grids = new Grids(commonComponents,common,materialService);
        this.materialAndDetails = new MaterialAndDetails(commonComponents,common,commonService,grids);
        this.aiService = aiService;
        this.productService = productService;
        this.colorSelector = new ColorSelector();


        this.productEditImage = new ProductEditImage(commonComponents,common);

        loadNewData();
        bindFields();


        manualStock.addClassName("stat-example");
        totalMaterialCost = commonComponents.spanCrafter("Total material cost - 0.0 Eur","stat-example");

        joinImagesInfoProgramSide(null);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        addClassName("animation-page");
        //getAiData();


        addNewMaterial = commonComponents.buttonThemeAndIcon("Add Material",null, ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White");



        category.addValueChangeListener(ee -> {
            if (ee.isFromClient()) {
                product.setCategory(ee.getValue());
            }
        });


        displayedName.addValueChangeListener(ee -> {
            if (ee.isFromClient()) {
                product.setProductName(displayedName.getValue());
            }
        });






        save = commonComponents.normalThemeButtonNoNavigate("Save", ButtonVariant.LUMO_PRIMARY);

        goBack = new Button("Cancel",e-> common.customNavigate("Products/1"));

        addNewVariant = commonComponents.normalThemeButtonNoNavigate("Add new variant", ButtonVariant.LUMO_PRIMARY);

        saveDate(addNewVariant);


        productEditImage.setMainChange(list -> {
            newImages = new ArrayList<>();
            newImages.addAll(list);
        });


        productEditImage.setListConsumer(list -> {
            newImages = new ArrayList<>();
            newImages.addAll(list);
        });



        status.setItems(Status.values());

        List<Visibility> list = new ArrayList<>();

        for (var v : Visibility.values()) {
            if (v != Visibility.ALL) {
                list.add(v);
            }
        }

        visibility.setItems(list);

        multipleProductHolder.setPadding(false);
        multipleProductHolder.addClassName("island");




        addTheButtonsOfDisplayAllProducts();

    }



    public void reset(){

        materialInfoList.clear();
        productName.clear();
        sku.clear();
        description.clear();
        price.clear();
        discount.clear();
        materialCost.clear();
        lowThreshold.setValue(Double.valueOf(0));
        manualStock.clear();
        status.clear();
        visibility.clear();
        productColor.clear();
        stockQuantity.setValue(Double.valueOf(0));

    }




//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//
//        removeAll();
//
//
//
//
//        add(mainLayout());
//
//    }


    public VerticalLayout mainLayout(Product product, boolean addNewBool) {

        String briefExplanation = "";
        if(addNewBool){
            briefExplanation = "Add multiple product variants";
            addNew = true;
        }
        else{
            briefExplanation = "Edit multiple product variants";
            addNew = false;
        }


        // load the display stuff the variants
        loadData(null);


        // load date tot the product global and the loadcommon data
        this.product = product;
        loadCommonData();


        displayAllProducts();




        Dialog commonData = new Dialog();
        commonData.setWidth("1000px");

        commonData.add(

        );








        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");

        verticalLayout.getStyle().set("margin-top", "5px");

//        productEditRightSideFields.setConsumer(e->{
//            productAddService.addNewOrder(e);
//        });







        verticalLayout.add(
//                commonComponents.breadCrums(new BreadCrumsDto("Products", "Products/1"),new BreadCrumsDto("Add new product", "ProductsAdd")),
                briefPageExplanation(briefExplanation),
                displayCommonData(),
                multipleProductHolder);

        return verticalLayout;
    }




    public HorizontalLayout joinImagesInfoProgramSide(ProductMultiple productMultiple){


        productEditImage.reset();

        joinImageAndRightSideHolder.setWidthFull();

        joinImageAndRightSideHolder.getStyle().set("flex-wrap","wrap");
        joinImageAndRightSideHolder.getStyle().set("overflow","hidden");


        //make an empty field
        List<CommonImagesData> commonImagesData = new ArrayList<>();

        if(productMultiple != null) {
            if (productMultiple.getImages() != null || productMultiple.getImages().isEmpty()) {
                for (var s : productMultiple.getImages()) {
                    commonImagesData.add(new CommonImagesData(s.getId(), s.getUuId(), s.getImageName(), s.getImageUrl(), s.getImageType(), s.getImageLogic(), s.getImageData()));
                }
            }
        }

        HorizontalLayout images = productEditImage.images(commonImagesData);


        Div holder = new Div();
        holder.add(images,
                productEditImage.uploadStuff()
//                reviewCrafter.commentsHolder(sharedProductInstance.getComments())
        );
        VerticalLayout fields = rightSide(productMultiple);
        fields.addClassName("fromRightToLeft");

        holder.addClassName("fromLeftToRight");

        joinImageAndRightSideHolder.add(holder,fields);
        joinImageAndRightSideHolder.expand(fields);


        return joinImageAndRightSideHolder;
    }




    public void getAiData() {




        aiService.setProductFinishStepsConsumer(e -> {


            listStepsToPrepares.clear();

            for (var s : e) {


                ListStepsToPrepare listStepsToPrepare =
                        new ListStepsToPrepare(
                                null,
                                1L,
                                materialAndDetails.specName(s.getStepName()),
                                materialAndDetails.specDescription(s.getStepDescription())
                        );

                listStepsToPrepares.add(listStepsToPrepare);
            }

            grids.updateStepGrid(listStepsToPrepares,
                    listStepsToPrepareGrid);
        });

        aiService.setProductExtraDetailsConsumer(e -> {

            listExtraDetailsGrids.clear();

            for (var s : e) {


                ListExtraDetailsGrid extraDetails =
                        new ListExtraDetailsGrid(
                                null,
                                materialAndDetails.specName(s.getSpecName()),
                                materialAndDetails.specDescription(s.getSpecDescription())
                        );

                listExtraDetailsGrids.add(extraDetails);
            }

            grids.upgradeExtraDetailsGrid(listExtraDetailsGrids,
                    extraDetailsGrid);
        });

    }

    // set consumer so it could react this is due to because in controller new not used
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


    public VerticalLayout rightSide( ProductMultiple productMultiple){




        loadData(productMultiple);

        rightSide.removeAll();


        manualStock.addClickListener(e->{
            if(!manualStock.getValue()){
                materialCost.setValue(value);
            }
        });


        // load data here of the common used stuff and  the display stuff




        rightSide.setWidth("700px");
        rightSide.setPadding(false);








        tagsSelected.setWidthFull();
        tagsSelected.addClassName("island");
        tagsSelected.setSpacing(false);
        tagsSelected.setPadding(false);

        if( visibility.getValue() != null && visibility.getValue().equals(Visibility.NonVisible)) {
            status.setEnabled(false);
        }

        if(product.isStockCalculatedManually()) {
            materialCost.setReadOnly(false);
            stockQuantity.setReadOnly(false);
        }
        else{
            materialCost.setReadOnly(true);
            stockQuantity.setReadOnly(true);
        }

        manualStock.addValueChangeListener(e->{

            if(e.getValue()){
                materialCost.setReadOnly(false);
                stockQuantity.setReadOnly(false);
            }
            else{
                materialCost.setReadOnly(true);
                stockQuantity.setReadOnly(true);
            }

        });



        tags.addValueChangeListener(e->{

            if (!e.isFromClient()) {
                return;
            }

            if(!tagss.contains(e.getValue()) && e.getValue() != null ) {
                tagss.add(e.getValue());
                tagsSelected.add(tagCrafter(e.getValue()));

                commonComponents.showNotification(String.format("%s - '%s' %s","Tag",e.getValue(),"added"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_SUCCESS);


                List<ProductTags> productTags = new ArrayList<>();

                for (Tags tag : tagss) {
                    ProductTags productTag = new ProductTags();
                    productTag.setProduct(product);
                    productTag.setTags(tag);
                    productTag.setUser(null);
                    productTags.add(productTag);
                }

                product.setTags(productTags);

            }
            else{
                commonComponents.showNotification(String.format("%s - '%s' %s","Tag",e.getValue(),"already exists"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_WARNING);
            }

        });












        productFeedModelGrid.removeAllColumns();


        rightSide.add(
                basicInfo(),
                pricingInv(),
                productStatus(),
                requiredMaterials()


        );






        return rightSide;
    }




    public HorizontalLayout briefPageExplanation(String name){
        HorizontalLayout left = commonComponents.biefPageExplanation(name);
        left.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout buttonSave = new HorizontalLayout();
        buttonSave.add(goBack,save);

        left.add(buttonSave);

        left.setWidthFull();

        return left;
    }

    public void loadData(ProductMultiple products){





        if(products != null) {

            Double sumPrice = 0.0;

            manualStock.setValue(products.isStockCalculatedManually());




            productName.setValue(products.getProductName() == null ? "" : products.getProductName());
            sku.setValue(products.getSku() == null ? "" : products.getSku());
            description.setValue(products.getDescription() == null ? "" : products.getDescription());

            price.setValue(products.getPrice());
            discount.setValue(products.getDiscount());

            materialCost.setValue(sumPrice);

            productColor.setValue(products.getColor()  == null ? "" : products.getColor());


            stockQuantity.setValue(products.getStockQuantity() != null ? (double) products.getStockQuantity() : 0.0);
            lowThreshold.setValue(products.getLowStockThreshold() != null ? (double) products.getLowStockThreshold() : 0.0);



            status.setValue(products.getStatus());
            visibility.setValue(products.getVisibility());

            materialCost.setValue(sumPrice);

        }
        else{
            productName.setValue(this.product.getProductName() == null ? "" : this.product.getProductName());
        }


        tags.setItems(Tags.values());

        category.setItems(Category.values());
        category.setValue(product.getCategory());


        tagss.clear();
        listStepsToPrepares.clear();
        listExtraDetailsGrids.clear();

        if (product.getTags() != null && !product.getTags().isEmpty()) {
            tags.setValue(product.getTags().get(0).getTags());
            for(var s : product.getTags()) {
                tagss.addAll(Collections.singleton(s.getTags()));
            }
        } else {
            tags.setValue(null);
        }


        if(product.getExtraDetails() != null && !product.getExtraDetails().isEmpty()) {
            for (var s : product.getExtraDetails()) {
                listExtraDetailsGrids.add(new ListExtraDetailsGrid(
                        s.getId(),
                        materialAndDetails.specName(s.getSpecName()),
                        materialAndDetails.specDescription(s.getSpecDescription())));
            }
            upgradeExtraDetailsGrid();
        }


        if(product.getSteps() != null &&  !product.getSteps().isEmpty()) {
            for(var s : product.getSteps()) {

                listStepsToPrepares.add(new ListStepsToPrepare(
                        s.getId(),
                        s.getStepId(),
                        materialAndDetails.specName(s.getStepName()),
                        materialAndDetails.specDescription(s.getStepDescription())));

            }
            updateStepGrid(listStepsToPrepares,listStepsToPrepareGrid);
        }




        updateSelectedTags(product);





    }

    public void loadCommonData(){
        tags.setItems(Tags.values());

        category.setItems(Category.values());
        category.setValue(product.getCategory());

        displayedName.setValue(product.getProductName() == null ? "" : product.getProductName());

        tagss.clear();
        listStepsToPrepares.clear();
        listExtraDetailsGrids.clear();

        if (product.getTags() != null && !product.getTags().isEmpty()) {
            tags.setValue(product.getTags().get(0).getTags());
            for(var s : product.getTags()) {
                tagss.addAll(Collections.singleton(s.getTags()));
            }
        } else {
            tags.setValue(null);
        }


        if(product.getExtraDetails() != null && !product.getExtraDetails().isEmpty()) {
            for (var s : product.getExtraDetails()) {
                listExtraDetailsGrids.add(new ListExtraDetailsGrid(
                        s.getId(),
                        materialAndDetails.specName(s.getSpecName()),
                        materialAndDetails.specDescription(s.getSpecDescription())));
            }
            upgradeExtraDetailsGrid();
        }


        if(product.getSteps() != null &&  !product.getSteps().isEmpty()) {
            for(var s : product.getSteps()) {

                listStepsToPrepares.add(new ListStepsToPrepare(
                        s.getId(),
                        s.getStepId(),
                        materialAndDetails.specName(s.getStepName()),
                        materialAndDetails.specDescription(s.getStepDescription())));

            }
            updateStepGrid(listStepsToPrepares,listStepsToPrepareGrid);
        }




        updateSelectedTags(product);
    }


    public void saveDate(Button save){



        // save all stuff
        this.save.addClickListener(e->{





            List<ExtraDetails> extraDetails = new ArrayList<>();
            for(var s : listExtraDetailsGrids){
                ExtraDetails details = new ExtraDetails();
                details.setProduct(product);
                details.setSpecName(s.getSpecName().getValue());
                details.setSpecDescription(s.getSpecDescription().getValue());
                details.setUser(null);
                extraDetails.add(details);
            }
            product.setExtraDetails(extraDetails);


            List<ProductFinishSteps> stepsToPrepares = new ArrayList<>();
            for(var s : listStepsToPrepares){
                ProductFinishSteps steps = new ProductFinishSteps();
                steps.setProduct(product);
                steps.setStepId(s.getStep());
                steps.setStepName(s.getStepName().getValue());
                steps.setStepDescription(s.getStepDescription().getValue());

                stepsToPrepares.add(steps);
            }
            product.setSteps(stepsToPrepares);


            System.out.println("TAGS");
            System.out.println(product.getTags());
            System.out.println("SPEFICATIONS");
            System.out.println(product.getExtraDetails());
            System.out.println("STEPS TO FINISH");
            System.out.println(product.getSteps());




            if(addNew){
                productService.saveMultipleProducts(product);

            }
            else{
                productService.saveEditedMultipleProduct(product);

            }


        });



        // SAVE NEW VARIANT
        save.addClickListener(e -> {
            if(e.isFromClient()){
                System.out.println("======================================================");
                System.out.println("SAVE CLICKED: " + System.nanoTime());
//                if (binder.validate().isOk()) {

                ProductMultiple multipleProduct = new ProductMultiple();

                product.setMultipleItems(true);


                //================================================================

                multipleProduct.setProductName(productName.getValue());
                multipleProduct.setSku(sku.getValue());
                multipleProduct.setDescription(description.getValue());
                multipleProduct.setPrice(price.getValue());
                multipleProduct.setDiscount(discount.getValue());
                multipleProduct.setMaterialCost(materialCost.getValue());
                multipleProduct.setStockQuantity(stockQuantity.getValue().longValue());
                multipleProduct.setLowStockThreshold(lowThreshold.getValue().longValue());

                multipleProduct.setStatus(status.getValue());
                multipleProduct.setVisibility(visibility.getValue());




                //get images

                if(newImages !=null){
                    System.out.println("changing pictures");
                    for(var s : newImages){

                        if (s.getImageType().equals("Internet") || s.getImageData() == null) {
                            continue;
                        } else {
                            s.setImageUrl(common.imageMaker(s.getImageData(), s.getImageType()));
                        }




                    }
                    multipleProduct.setImages(objectConverter.convert(newImages, ProductImageData.class));
                }


                // get materials
                List<ProductMaterials> materials = new ArrayList<>();
                for(var s : materialInfoList){

                    ProductMaterials productMaterials = new ProductMaterials();
                    productMaterials.setProduct(product);
                    productMaterials.setUser(null);
                    productMaterials.setMaterials(materialService.getMaterial(s.getId()));
                    productMaterials.setId(s.getId());
                    productMaterials.setNameForRefrence(s.getMaterialName());
                    productMaterials.setAmountUsed(Long.valueOf(s.getAmountTaken()));

                    materials.add(productMaterials);
                }
                multipleProduct.setMaterials(materials);




                multipleProduct.setStockCalculatedManually(manualStock.getValue());





                product.getProductMultiples().add(multipleProduct);



                displayAllProducts();
                newImages = new ArrayList<>();

            }
        });
    }


    //display the common product data shared over all the multi products
    public VerticalLayout displayCommonData(){

        VerticalLayout v = new VerticalLayout();
        v.getStyle().setPosition(Style.Position.RELATIVE);
        v.addClassName("island");

        VerticalLayout commonHolder = new VerticalLayout();
        commonHolder.addClassName("removetop");


        Icon downIcon = commonComponents.iconCrafter(
                VaadinIcon.ANGLE_DOWN, "40px", "royalblue"
        );

        Icon upIcon = commonComponents.iconCrafter(
                VaadinIcon.ANGLE_UP, "40px", "royalblue"
        );


        Button button = new Button("Expand",downIcon);
        button.getStyle().setBackgroundColor("Transparent");
        button.getStyle().setPosition(Style.Position.ABSOLUTE);
        button.getStyle().set("top","10px").set("right","20px");



        button.addClickListener(e->{

            if(commonHolder.hasClassName("removetop")){
                commonHolder.removeClassName("removetop");
                commonHolder.addClassName("addtop");

                button.setIcon(upIcon);
                button.setText("Minimize");

            }
            else{
                commonHolder.removeClassName("addtop");
                commonHolder.addClassName("removetop");
                button.setText("Expand");


                button.setIcon(downIcon);

            }

        });



        commonHolder.setPadding(false);
        commonHolder.add(
                categoriesTags(),
                specs(),
                steps()
        );

        v.add(
                button,
                commonComponents.spanCrafterWordNoHide("Common settings used for products","activityFeed-name"),
                commonHolder
        );


        return v;

    }


    public void addTheButtonsOfDisplayAllProducts(){


        Dialog addNewProductVariant = new Dialog();
        addNewProductVariant.getFooter().add(addNewVariant);


        Button openNewProductVariant = new Button("Add new variant");
        openNewProductVariant.addThemeVariants(ButtonVariant.PRIMARY);
        openNewProductVariant.addClickListener(e->{


            reset();

            // remove all image prepate for a another variant
            addNewProductVariant.removeAll();
            joinImageAndRightSideHolder.removeAll();

            addNewProductVariant.add(
                    joinImagesInfoProgramSide(null)
            );

            addNewProductVariant.open();
        });

        multipleProductHolderButtons.add(
                openNewProductVariant
        );

    }

    // display the prodcut variants
    public void displayAllProducts(){

        multipleProductHolder.removeAll();

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        for(var s :  product.getProductMultiples()){

            VerticalLayout v = new VerticalLayout();
            v.getStyle().set("flex", "1 1 252px");
            v.getStyle().set("max-width", "320px");
            v.getStyle().set("min-width", "252px");
            v.addClassName("island-hover");

            // find the main image default it to no image png
            String mainImageUrl = "No_picture.png";
            if(!s.getImages().isEmpty()) {
                for (var imagesOfTheProduct : s.getImages()) {
                    if (imagesOfTheProduct.getImageLogic().equals(ImageLogic.Main)) {
                        mainImageUrl = imagesOfTheProduct.getImageUrl();
                    }
                }
            }

            Image image = commonComponents.imageCrafter(mainImageUrl,"100%","150px","5px");


            Span span = commonComponents.spanCrafter(s.getStock() == null ? "Unknown" : String.valueOf(s.getStock().getDisplayName()),"activityFeed-name");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");


            if(s.getStock() != null) {
                switch (s.getStock()) {
                    case In_Stock -> span.addClassName("stock-in");
                    case No_Stock -> span.addClassName("stock-out");
                    case Low_Stock -> span.addClassName("stock-low");
                }
            }


            v.add(
                    image,
                    commonComponents.spanCrafterWordNoHide(s.getProductName(),"stat-example"),
                    commonComponents.spanCrafterWordNoHide(s.getPrice() + " Eur","stat-example"),

                    commonComponents.doubleValueRow(span,commonComponents.spanCrafterWordNoHide(s.getStockQuantity() + " units","stat-description"))
            );
            Dialog addNewProductVariant = new Dialog();

            Button save = new Button("Save");
            addNewProductVariant.getFooter().add(save);

            save.addClickListener(e->{




                // update the images

                if(newImages !=null) {
                    System.out.println("changing pictures");
                    for (var ss : newImages) {

                        if (ss.getImageType().equals("Internet") || ss.getImageData() == null) {
                            continue;
                        } else {
                            ss.setImageUrl(common.imageMaker(ss.getImageData(), ss.getImageType()));
                        }


                    }

                    s.setImages(objectConverter.convert(newImages, ProductImageData.class));
                }



                // save material make new one or find the link and update it to not lose info
                List<ProductMaterials> updatedMaterials = new ArrayList<>();

                for (MaterialInfo info : materialInfoList) {

                    // First: look for an existing link by ProductMaterials ID
                    ProductMaterials existing = s.getMaterials()
                            .stream()
                            .filter(m -> m.getMaterials().getId().equals(info.getId()))
                            .findFirst()
                            .orElse(null);

                    if (existing != null) {
                        existing.setAmountUsed(info.getAmountTaken());
                        existing.setNameForRefrence(info.getMaterialName());

                        updatedMaterials.add(existing);

                    } else {
                        ProductMaterials newLink = new ProductMaterials();

                        newLink.setProduct(product);
                        newLink.setUser(null);
                        newLink.setNameForRefrence(info.getMaterialName());
                        newLink.setAmountUsed(info.getAmountTaken());

                        Materials material = materialService.getMaterialAccordingToId(info.getId());

                        newLink.setMaterials(material);

                        updatedMaterials.add(newLink);
                    }
                }

                s.setMaterials(updatedMaterials);

                // save new data
                s.setProductName(productName.getValue());
                s.setSku(sku.getValue());
                s.setDescription(description.getValue());
                s.setPrice(price.getValue());
                s.setDiscount(discount.getValue());
                s.setMaterialCost(materialCost.getValue());
                s.setStockQuantity(stockQuantity.getValue().longValue());
                s.setLowStockThreshold(lowThreshold.getValue().longValue());
                s.setCategory(category.getValue());
                s.setStatus(status.getValue());
                s.setVisibility(visibility.getValue());
                s.setColor(productColor.getValue());


                displayAllProducts();

                materialInfoList.clear();

            });

            v.addClickListener(e-> {


                addNewProductVariant.removeAll();
                joinImageAndRightSideHolder.removeAll();


                addNewProductVariant.add(
                        joinImagesInfoProgramSide(s)
                );


                materialInfoList.clear();
                for (var mat : s.getMaterials()) {


                    String url = "No_picture.png";

                    for(var img : mat.getMaterials().getImages()){
                        if(img.getImageLogic().equals(ImageLogic.Main)){
                            url = img.getImageUrl();
                        }
                    }


                    materialInfoList.add(new MaterialInfo(

                            mat.getMaterials().getId(),
                            url,
                            mat.getNameForRefrence(),
                            mat.getUnitPrice(),
                            mat.getMaterials().getInStock(),
                            mat.getAmountUsed()

                    ));
                }

                upgradeMaterialGrid();
                grids.calculateTotal();


                addNewProductVariant.open();






            });

            h.add(
                    v
            );




        }

        HorizontalLayout firstLayerHolder = new HorizontalLayout();
        firstLayerHolder.setWidthFull();
        firstLayerHolder.add(
                commonComponents.spanCrafterWordNoHide(String.format("%s product variants",product.getProductName() == null ? "Unknown" : product.getProductName()),"activityFeed-name"),
                multipleProductHolderButtons
        );
        firstLayerHolder.setJustifyContentMode(JustifyContentMode.BETWEEN);


        multipleProductHolder.add(
                firstLayerHolder,
                h);

    }











    public void loadNewData(){
        grids.setConsumer(e->{
            MaterialInfo materials = materialService.getMaterialInfoAccordingToId(e,product.getId());

            materials.setAmountTaken(1L);

            materialInfoList.add(
                    materials
            );

            upgradeMaterialGrid();

            grids.calculateTotal();

        });
    }



    public VerticalLayout basicInfo(){

        VerticalLayout v = new VerticalLayout();






        v.addClassName("island");
        v.getStyle().set("position","relative");
        FormLayout basicInfo = new FormLayout();

        description.setHeight("150px");

        basicInfo.add(productName, sku,colorSelector.colorSelector(productColor), description);
        basicInfo.setColspan(productName,2);
        basicInfo.setColspan(description,2);

        Button aiButton = commonComponents.buttonThemeAndIconNoNavigate("AI",ButtonVariant.LUMO_PRIMARY, VaadinIcon.MAGIC,"WHITE");
        aiButton.setTooltipText("Generate information with a prompt");
        aiButton.getStyle().set("position","absolute").set("right","10px").set("top","10px");




        aiButton.addClickListener(e -> {

            VerticalLayout component = new VerticalLayout();

            component.setPadding(false);
            component.add(
                    basicInfo(),
//                    specs(),
//                    steps(),
                    pricingInv(),
//                    categoriesTags(),
                    productStatus(),
                    requiredMaterials()
            );


            aiService.dialogTest(multiProductAiDto, MultiProductAiDto.class,rightSide,component,this,"MultiProducts");



        });




        v.add(
                aiButton,
                commonComponents.spanCrafterWordNoHide("Basic information","activityFeed-name"),
                basicInfo
        );

        return v;
    }

    public VerticalLayout specs(){

        addNewDetail = commonComponents.buttonThemeAndIcon("Add Detail",null, ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White");

        addNewDetail.addClickListener(e->{

            listExtraDetailsGrids.add(new ListExtraDetailsGrid(null,
                    materialAndDetails.specName(""),
                    materialAndDetails.specDescription("")));
            upgradeExtraDetailsGrid();

        });





        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.add(
                commonComponents.spanCrafterWordNoHide("Specification","activityFeed-name"),
                grids.extraDetailsGridCrafter(listExtraDetailsGrids,extraDetailsGrid),
                addNewDetail
        );

        return v;
    }

    public VerticalLayout steps(){


        addNewStep = commonComponents.buttonThemeAndIcon("Add Step",null, ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White");

        addNewStep.addClickListener(e->{

            listStepsToPrepares.add(new ListStepsToPrepare(
                    null,
                    Long.valueOf(listStepsToPrepares.size()+1),
                    materialAndDetails.specName(""),
                    materialAndDetails.specDescription("")));
            updateStepGrid(listStepsToPrepares,listStepsToPrepareGrid);

        });

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.add(
                commonComponents.spanCrafterWordNoHide("Steps to finish","activityFeed-name"),
                grids.preparationSteps(listStepsToPrepares,listStepsToPrepareGrid),
                addNewStep
        );

        return v;
    }

    public VerticalLayout pricingInv(){


        FormLayout pricingInventoryOne = new FormLayout();

        pricingInventoryOne.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 3)
        );



        pricingInventoryOne.add(price, discount, materialCost);


        FormLayout pricingInventoryTwo = new FormLayout();
        pricingInventoryTwo.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );

        pricingInventoryTwo.add(stockQuantity, lowThreshold);

        FormLayout pricingInventoryThree = new FormLayout();
        pricingInventoryTwo.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );

        pricingInventoryThree.add(manualStock);


        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.add(
                commonComponents.spanCrafterWordNoHide("Pricing & Inventory","activityFeed-name"),
                pricingInventoryOne,
                pricingInventoryTwo,
                pricingInventoryThree
        );

        return v;
    }

    public VerticalLayout categoriesTags(){
        FormLayout categoryTags = new FormLayout();
        categoryTags.add(displayedName,category,tags);
        categoryTags.setColspan(displayedName,2);

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.add(
                commonComponents.spanCrafterWordNoHide("Basic information","activityFeed-name"),
                categoryTags,
                tagsSelected


        );

        return v;
    }

    public VerticalLayout productStatus(){


        FormLayout productStatus = new FormLayout();
        visibility.addValueChangeListener(e->{
            if(e.isFromClient()) {
                if (e.getValue().equals(Visibility.NonVisible)) {
                    status.setValue(Status.Disabled);
                    status.setEnabled(false);
                } else {
                    status.setEnabled(true);
                }
            }
        });


        productStatus.add(status,visibility);

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.add(
                commonComponents.spanCrafterWordNoHide("Product Status","activityFeed-name"),
                productStatus


        );

        return v;
    }

    public VerticalLayout requiredMaterials(){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        HorizontalLayout totalMaterialCostHolder = new HorizontalLayout();
        totalMaterialCostHolder.setWidthFull();
        totalMaterialCostHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.END);


        totalMaterialCostHolder.add(
                totalMaterialCost
        );
        grids.setPrice(e->{
            totalMaterialCost.setText("Total material cost - " + e + " Eur");
            value = e;
            if(!manualStock.getValue()) {
                materialCost.setValue(e);
            }
        });

        v.add(
                commonComponents.spanCrafterWordNoHide("Required Materials","activityFeed-name"),
                grids.materialGridCrafter(productFeedModelGrid,materialInfoList,addNewMaterial),
                totalMaterialCostHolder,
                addNewMaterial


        );

        return v;
    }



    // tag crafter
    public HorizontalLayout tagCrafter(Tags newTag) {

        HorizontalLayout layout = new HorizontalLayout();



        layout.addClassName("tag-badge");

        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);



        Span span = commonComponents.spanCrafterWordNoHide(
                newTag != null ? newTag.toString() : "",
                "none"
        );

        Button removeButton = new Button(VaadinIcon.CLOSE_SMALL.create());
        removeButton.addClassName("remove-button");

        removeButton.addClickListener(e->{
            tagss.removeIf(tags1 -> tags1.equals(newTag));

            tags.setValue(!tagss.isEmpty() ? tagss.get(0) : null);

            layout.getParent().ifPresent(parent ->{
                ((HasComponents) parent).remove(layout);
            });

            commonComponents.showNotification(String.format("%s - '%s' %s","Tag",newTag.toString(),"removed"),
                    3000,
                    Notification.Position.BOTTOM_CENTER,
                    NotificationVariant.LUMO_SUCCESS);
        });



        removeButton.setMinWidth("30px");
        removeButton.setHeight("30px");

        layout.add(span, removeButton);



        return layout;
    }


    // update

    public void upgradeMaterialGrid(){
        productFeedModelGrid.setItems(materialInfoList);
    }

    public void upgradeExtraDetailsGrid(){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }

    public void updateSelectedTags(Product productEditDtos){

        tagsSelected.removeAll();

        if( productEditDtos.getTags() != null && !productEditDtos.getTags().isEmpty()) {
            for (var s : productEditDtos.getTags()) {
                tagsSelected.add(tagCrafter(s.getTags()));
            }
        }
    }

    public void updateStepGrid(List<ListStepsToPrepare> listExtraDetailsGrids,Grid<ListStepsToPrepare> extraDetailsGrid){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }


    public void removeTags(){

        tagsSelected.removeAll();
        tags.clear();
        tagss.clear();


    }

    // material stuff components

    // binder for textfield checks
    private void bindFields() {

        // PRODUCT NAME
        binder.forField(productName)
                .asRequired("Product name required")
                .withValidator(
                        value -> value.length() >= 3,
                        "Minimum 3 characters"
                )
                .bind(Product::getProductName,
                        Product::setProductName);


        // SKU
        binder.forField(sku)
                .asRequired("SKU required")
                .withValidator(
                        value -> value.length() >= 2,
                        "SKU too short"
                )
                .bind(Product::getSku,
                        Product::setSku);


        // DESCRIPTION
        binder.forField(description)
                .asRequired("Description required")
                .withValidator(
                        value -> value.length() >= 10,
                        "Description too short"
                )
                .bind(Product::getDescription,
                        Product::setDescription);


        // PRICE
        binder.forField(price)
                .asRequired("Price required")
                .withValidator(
                        value -> value >= 0,
                        "Price must be positive"
                )
                .bind(Product::getPrice,
                        Product::setPrice);


        // DISCOUNT
        binder.forField(discount)
                .withValidator(
                        value -> value == null || (value >= 0 && value <= 100),
                        "Discount must be between 0 and 100"
                )
                .bind(Product::getDiscount,
                        Product::setDiscount);


        // MATERIAL COST
        binder.forField(materialCost)
                .withValidator(value -> value == null || value >= 0,
                        "Must be positive")
                .bind(Product::getMaterialCost,
                        Product::setMaterialCost);


        // STOCK QUANTITY
        binder.forField(stockQuantity)
                .withConverter(
                        Double::longValue,
                        Long::doubleValue
                )
                .withValidator(
                        value -> value >= 0,
                        "Stock cannot be negative"
                )
                .bind(Product::getStockQuantity,
                        Product::setStockQuantity);


        // LOW STOCK THRESHOLD
        binder.forField(lowThreshold)
                .withConverter(
                        Double::longValue,
                        Long::doubleValue
                )
                .withValidator(
                        value -> value >= 0,
                        "Threshold cannot be negative"
                )
                .bind(Product::getLowStockThreshold,
                        Product::setLowStockThreshold);


        // CATEGORY
        binder.forField(category)
                .asRequired("Category required")
                .bind(Product::getCategory,
                        Product::setCategory);


//        // TAGS
//        binder.forField(tags)
//                .asRequired("Tag required")
//                .withConverter(
//                        tag -> List.of(tag),
//                        list -> list != null && !list.isEmpty() ? list.get(0) : null
//                )
//                .bind(Product::getTags,
//                        Product::setTags);


        // STATUS
        binder.forField(status)
                .asRequired("Status required")
                .bind(Product::getStatus,
                        Product::setStatus);


        // VISIBILITY
        binder.forField(visibility)
                .asRequired("Visibility required")
                .bind(Product::getVisibility,
                        Product::setVisibility);
    }















}
