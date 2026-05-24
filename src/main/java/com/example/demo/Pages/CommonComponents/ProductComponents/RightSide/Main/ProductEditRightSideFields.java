package com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Main;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.*;
import com.example.demo.ControllerModels.CommonDtos.*;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Tags;
import com.example.demo.Enums.Visibility;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.Grids;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.MaterialAndDetails;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.ServerDBCall.ProductEdit.ProductEdItCall;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;


public class ProductEditRightSideFields {

    CommonComponents commonComponents;
    Common common;
    CommonCalls commonCalls;
    Grids grids;


    ProductEdItCall productEdItCall;

    ProductEditImage productEditImage;

    MaterialAndDetails materialAndDetails;



    private Binder<Product> binder =
            new Binder<>(Product.class);

    private TextField productName = new TextField("Product name");
    private TextField sku = new TextField("SKU");
    private TextArea description = new TextArea("Description");

    private NumberField price = new NumberField("Price");
    private NumberField discount = new NumberField("Discount %");
    private NumberField materialCost = new NumberField("Material Cost");

    private NumberField stockQuantity = new NumberField("Stock Quantity");
    private NumberField lowThreshold = new NumberField("Low Stock Threshold");

    private ComboBox<Category> category = new ComboBox<>("Category");
    private ComboBox<Tags> tags = new ComboBox<>("Tags");


    private ComboBox<Status> status = new ComboBox<>("Status");
    private ComboBox<Visibility> visibility = new ComboBox<>("Visibility");

    List<ListExtraDetailsGrid> listExtraDetailsGrids = new ArrayList<>();
    Grid<ListExtraDetailsGrid> extraDetailsGrid = new Grid<>(ListExtraDetailsGrid.class,false);


    List<ListMaterialGrid> listMaterialGrids = new ArrayList<>();
    Grid<ListMaterialGrid> productFeedModelGrid = new Grid<>(ListMaterialGrid.class,false);

    List<Tags> tagss = new ArrayList<>();


    Button addNewMaterial;


    // send message that info is ready
    Consumer<Product> consumer;

    HorizontalLayout tagsSelected = new HorizontalLayout();

    List<ImagesData> newImages = null;

    public void setConsumer (Consumer<Product> consumer){
        this.consumer = consumer;
    }






    public ProductEditRightSideFields(CommonComponents commonComponents,
                                      Common common, CommonCalls commonCalls) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonCalls = commonCalls;
        this.grids = new Grids(commonComponents,common);
        this.materialAndDetails = new MaterialAndDetails(commonComponents,common,commonCalls,grids);

        bindFields();



    }

    // set consumer so it could react this is due to because in controller new not used
    public void setProductEditImage(ProductEditImage productEditImage) {
        this.productEditImage = productEditImage;
        if (this.productEditImage != null) {
            productEditImage.setListConsumer(list -> {
                newImages = new ArrayList<>();
                newImages.addAll(list);
            });
        }
    }


    public VerticalLayout rightSide(Product productEditDtos){

        loadData(productEditDtos);

        VerticalLayout v =new VerticalLayout();
        v.setWidth("700px");
        v.addClassName("island");


        FormLayout basicInfo = new FormLayout();

        basicInfo.add(productName, sku, description);
        basicInfo.setColspan(description,2);

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



        tagsSelected.setWidthFull();
        tagsSelected.addClassName("island");
        tagsSelected.setSpacing(false);
        tagsSelected.setPadding(false);




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
            }
            else{
                commonComponents.showNotification(String.format("%s - '%s' %s","Tag",e.getValue(),"already exists"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_WARNING);
            }

            });

        FormLayout categoryTags = new FormLayout();
        categoryTags.add(category,tags);




        FormLayout productStatus = new FormLayout();
        visibility.addValueChangeListener(e->{
           if(e.getValue().equals(Visibility.NonVisible)){
               status.setValue(Status.Disabled);
               status.setEnabled(false);
           }
           else{
               status.setEnabled(true);
           }
        });


        productStatus.add(status,visibility);


        addNewMaterial = commonComponents.normalThemeButtonNoNavigate("Add Material", ButtonVariant.LUMO_PRIMARY);

        addNewMaterial.addClickListener(e->{
            addNewMaterial.setEnabled(false);
            listMaterialGrids.add(new ListMaterialGrid(null,
                    "",
                    materialAndDetails.comboBoxMaterial("",listMaterialGrids,productFeedModelGrid,addNewMaterial),
                    materialAndDetails.quantityField(0l,listMaterialGrids,productFeedModelGrid,materialCost),
                    materialAndDetails.unitField(""),
                    0,
                    0l));
            upgradeMaterialGrid();


        });


        Button addNewDetail = commonComponents.normalThemeButtonNoNavigate("Add Detail", ButtonVariant.LUMO_PRIMARY);

        addNewDetail.addClickListener(e->{

            listExtraDetailsGrids.add(new ListExtraDetailsGrid(null,
                            materialAndDetails.specName(""),
                            materialAndDetails.specDescription("")));
            upgradeExtraDetailsGrid();

        });

        HorizontalLayout buttonSave = new HorizontalLayout();
        buttonSave.setWidthFull();
        Button save = commonComponents.normalThemeButtonNoNavigate("Save",ButtonVariant.LUMO_PRIMARY);
        Button clear = commonComponents.normalThemeButtonNoNavigate("Clear",ButtonVariant.LUMO_PRIMARY);
        clear.addClassName("clear-button");
        buttonSave.add(save,clear);

        productFeedModelGrid.removeAllColumns();

        v.add(
                commonComponents.spanCrafterWordNoHide("Actions","activityFeed-name"),
                buttonSave,
                commonComponents.spanCrafterWordNoHide("Basic information","activityFeed-name"),
                basicInfo,
                commonComponents.spanCrafterWordNoHide("Spefication","activityFeed-name"),
                grids.extraDetailsGridCrafter(listExtraDetailsGrids,extraDetailsGrid),
                addNewDetail,
                commonComponents.spanCrafterWordNoHide("Pricing & Inventory","activityFeed-name"),
                pricingInventoryOne,
                pricingInventoryTwo,
                commonComponents.spanCrafterWordNoHide("Categories & Tags","activityFeed-name"),
                categoryTags,
                commonComponents.spanCrafterWordNoHide("Selected Tags","stat-title"),
                tagsSelected,
                commonComponents.spanCrafterWordNoHide("Product Status","activityFeed-name"),
                productStatus,
                commonComponents.spanCrafterWordNoHide("Required Materials","activityFeed-name"),
                grids.materialGridCrafter(productFeedModelGrid,listMaterialGrids,addNewMaterial),
                addNewMaterial


        );



        saveDate(save,productEditDtos);
        clearData(clear);



        return v;
    }




    public void loadData(Product productEditDto){



        category.setItems(Category.values());
        tags.setItems(Tags.values());
        status.setItems(Status.values());
        List<Visibility> list = new ArrayList<>();

        for (var v : Visibility.values()) {
            if (v != Visibility.ALL) {
                list.add(v);
            }
        }

        visibility.setItems(list);


        productName.setValue(productEditDto.getProductName() == null ? "" : productEditDto.getProductName());
        sku.setValue(productEditDto.getSku() == null ? "" : productEditDto.getSku());
        description.setValue(productEditDto.getDescription() == null ? "" : productEditDto.getDescription());

        price.setValue(productEditDto.getPrice());
        discount.setValue(productEditDto.getDiscount());
        materialCost.setValue(productEditDto.getMaterialCost());

        stockQuantity.setValue(productEditDto.getStockQuantity() != null ? (double) productEditDto.getStockQuantity() : 0.0);
        lowThreshold.setValue(productEditDto.getLowStockThreshold() != null ? (double) productEditDto.getLowStockThreshold() : 0.0);

        category.setValue(productEditDto.getCategory());
        status.setValue(productEditDto.getStatus());
        visibility.setValue(productEditDto.getVisibility());

        if (productEditDto.getTags() != null && !productEditDto.getTags().isEmpty()) {
            tags.setValue(productEditDto.getTags().get(0).getTags());
            for(var s : productEditDto.getTags()) {
                tagss.addAll(Collections.singleton(s.getTags()));
            }
        } else {
            tags.setValue(null);
        }


        if(productEditDto.getExtraDetails() != null && !productEditDto.getExtraDetails().isEmpty()) {
            for (var s : productEditDto.getExtraDetails()) {
                listExtraDetailsGrids.add(new ListExtraDetailsGrid(
                        s.getId(),
                        materialAndDetails.specName(s.getSpecName()),
                        materialAndDetails.specDescription(s.getSpecDescription())));
            }
            upgradeExtraDetailsGrid();
        }

        if(productEditDto.getMaterials() != null &&  !productEditDto.getMaterials().isEmpty()) {
            for(var s : productEditDto.getMaterials()) {
                listMaterialGrids.add(new ListMaterialGrid(
                        s.getMaterials().getId(),
                        s.getNameForRefrence(),
                        materialAndDetails.comboBoxMaterial(s.getNameForRefrence(),listMaterialGrids,productFeedModelGrid,addNewMaterial),
                        materialAndDetails.quantityField(s.getAmountUsed(),listMaterialGrids,productFeedModelGrid,materialCost),
                        materialAndDetails.unitField(s.getMaterials().getUnit()),
                        s.getMaterials().getUnitPrice(),
                        s.getMaterials().getInStock()));
            }
            upgradeMaterialGrid();
        }

        updateSelectedTags(productEditDto);




    }

    public void clearData(Button clear){

        clear.addClickListener(e->{
            productName.clear();
            sku.clear();
            description.clear();
            status.clear();
            price.clear();
            materialCost.clear();
            stockQuantity.clear();
            lowThreshold.clear();
            category.clear();
            tags.clear();
            visibility.clear();

            listMaterialGrids.clear();
            listExtraDetailsGrids.clear();

            upgradeExtraDetailsGrid();
            upgradeMaterialGrid();

            removeTags();

        });


    }

    public void saveDate(Button save, Product product){

        save.addClickListener(e -> {


            if (binder.validate().isOk()) {

            product.setProductName(productName.getValue());
            product.setSku(sku.getValue());
            product.setDescription(description.getValue());
            product.setPrice(price.getValue());
            product.setDiscount(discount.getValue());
            product.setMaterialCost(materialCost.getValue());
            product.setStockQuantity(stockQuantity.getValue().longValue());
            product.setLowStockThreshold(lowThreshold.getValue().longValue());
            product.setCategory(category.getValue());
            product.setStatus(status.getValue());
            product.setVisibility(visibility.getValue());

            // get tags
            List<ProductTags> productTags = new ArrayList<>();
            for(var s : tagss) {
                ProductTags allTags = new ProductTags();
                allTags.setProduct(product);
                allTags.setUser(null);
                allTags.setTags(s);
                productTags.add(allTags);
            }
            product.setTags(productTags);


            //get images

            if(newImages !=null){
                System.out.println("changing pictures");
                for(var s : newImages){
                    s.setImageUrl(common.imageMaker(s.getImageData(),s.getImageType()));
                }
                product.setImages(newImages);

            }

            // get materials
            List<ProductMaterials> materials = new ArrayList<>();
            for(var s : listMaterialGrids){

                ProductMaterials productMaterials = new ProductMaterials();
                productMaterials.setId(s.getMaterial().getValue().getId());
                productMaterials.setProduct(product);
                productMaterials.setUser(null);
                productMaterials.setMaterials(null);
                productMaterials.setNameForRefrence(s.getMaterial().getValue().getMaterialName());
                productMaterials.setAmountUsed(Long.valueOf(s.getAmountOfMaterial().getValue()));

                materials.add(productMaterials);
            }
            product.setMaterials(materials);




                // get extra details
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


            checkIfDataCorrect(materials,product);


            } else {
                System.out.println("Validation failed");
            }
        });
    }


    public void checkIfDataCorrect(List<ProductMaterials> materials,Product product){

        int errorCount = 0;

        boolean dataCorrect;
        String errorMessage;

        try {
            errorMessage = commonCalls.checkIfMaterialsAreInStock(materials);
            dataCorrect = errorMessage.equalsIgnoreCase("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        boolean imageError = (newImages == null || newImages.isEmpty());

        if(!dataCorrect){
            errorCount++;
        }
        if(imageError){
            errorCount++;
        }

        System.out.println(errorCount + " erors");
        if(!dataCorrect || imageError) {
            if(!dataCorrect) {
                    showDialog(errorMessage,product,errorCount);
            }
            if(imageError){
                    showNoImageError(product,errorCount);
            }
        }
        else {
            consumer.accept(product);
            UI.getCurrent().navigate("Products/1");
        }
    }



    public void showDialog(String error,Product product, int errorCount){
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Warning");

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span line = new Span("• " + error);
        line.getStyle().set("color", "red");
        content.add(line);

        dialog.setCancelable(true);   // gives "Cancel"
        dialog.setConfirmText("Continue");
        dialog.setCancelText("Go back");


        dialog.addConfirmListener(event -> {
            if(errorCount == 2) {
                consumer.accept(product);
                UI.getCurrent().navigate("Products/1");
            }
        });

        dialog.addCancelListener(event -> {
        });

        dialog.add(content);

        dialog.open();
    }


    public void showNoImageError(Product product, int errorCount){
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("No images error");

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span line = new Span("• " + "No images are selected please add some images to your product");
        line.getStyle().set("color", "red");
        content.add(line);

        dialog.setCancelable(true);   // gives "Cancel"
        dialog.setConfirmText("Continue");
        dialog.setCancelText("Go back");


        dialog.addConfirmListener(event -> {
            if(errorCount == 1) {

                consumer.accept(product);
                UI.getCurrent().navigate("Products/1");
            }
        });

        dialog.addCancelListener(event -> {
        });

        dialog.add(content);

        dialog.open();
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
        productFeedModelGrid.setItems(listMaterialGrids);
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
                .asRequired("Stock quantity required")
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
                .asRequired("Low stock threshold required")
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
