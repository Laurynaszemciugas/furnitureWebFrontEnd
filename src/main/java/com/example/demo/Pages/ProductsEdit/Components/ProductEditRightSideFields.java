package com.example.demo.Pages.ProductsEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.*;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Tags;
import com.example.demo.Enums.Visibility;
import com.example.demo.Pages.CommonComponents.ProductAddEditGrids;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ProductEditRightSideFields {

    CommonComponents commonComponents;
    Common common;
    ProductAddEditGrids productAddEditGrids;

    ProductEditImage productEditImage;

    List<String> items = new ArrayList<>();


    private Binder<ProductData> binder =
            new Binder<>(ProductData.class);

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


    // send message that info is ready
    Consumer<ProductData> consumer;

    HorizontalLayout tagsSelected = new HorizontalLayout();

    public void setConsumer (Consumer<ProductData> consumer){
        this.consumer = consumer;
    }


    // set consumer so it could react this is due to because in controller new is used
    public void setProductEditImage(ProductEditImage productEditImage) {
        this.productEditImage = productEditImage;
        if (this.productEditImage != null) {
            this.productEditImage.setListConsumer(list -> {
                System.out.println("1234");
            });
        }
    }


    public ProductEditRightSideFields(CommonComponents commonComponents,
                        Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productAddEditGrids = new ProductAddEditGrids(commonComponents,common);

        bindFields();
        items.add("Wood");
        items.add("Nails");



    }



    public void loadData(ProductData productEditDto){


        // SET ITEMS (These are usually safe as Enums are static)
        category.setItems(Category.values());
        tags.setItems(Tags.values());
        status.setItems(Status.values());
        visibility.setItems(Visibility.values());

// TEXT FIELDS (Require "" instead of null)
        productName.setValue(productEditDto.getProductName() == null ? "" : productEditDto.getProductName());
        sku.setValue(productEditDto.getSku() == null ? "" : productEditDto.getSku());
        description.setValue(productEditDto.getDescription() == null ? "" : productEditDto.getDescription());

// NUMBER FIELDS (Can take null, but safer to check)
        price.setValue(productEditDto.getPrice());
        discount.setValue(productEditDto.getDiscount());
        materialCost.setValue(productEditDto.getMaterialCost());

// CASTING NUMBERS (Crucial to check null before casting to double/long)
        stockQuantity.setValue(productEditDto.getStockQuantity() != null ? (double) productEditDto.getStockQuantity() : 0.0);
        lowThreshold.setValue(productEditDto.getLowStockThreshold() != null ? (double) productEditDto.getLowStockThreshold() : 0.0);

// COMBOBOX / ENUMS (Can take null)
        category.setValue(productEditDto.getCategory());
        status.setValue(productEditDto.getStatus());
        visibility.setValue(productEditDto.getVisibility());

// TAGS (Special case: check if the list is null or empty before getting index 0)
        if (productEditDto.getTags() != null && !productEditDto.getTags().isEmpty()) {
            tags.setValue(productEditDto.getTags().get(0));
            tagss.addAll(productEditDto.getTags());
        } else {
            tags.setValue(null); // Or tags.setValue(null);
        }


        if(productEditDto.getExtraDetails() != null && !productEditDto.getExtraDetails().isEmpty()) {
            for (var s : productEditDto.getExtraDetails()) {
                listExtraDetailsGrids.add(new ListExtraDetailsGrid(s.getId(),specName(s.getSpecName()), specDescription(s.getSpecDescription())));
            }
            upgradeExtraDetailsGrid();
        }

        if(productEditDto.getMaterials() != null &&  !productEditDto.getMaterials().isEmpty()) {
            for(var s : productEditDto.getMaterials())
                listMaterialGrids.add(new ListMaterialGrid(s.getId(),s.getMaterialName(),comboBoxMaterial(s.getMaterialName()),quantityField(s.getAmountUsed()),unitField(s.getUnit()),s.getUnitPrice()));
            upgradeMaterialGrid();
        }

        updateSelectedTags(productEditDto);




    }


    public VerticalLayout rightSide(ProductData productEditDtos){

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
        productStatus.add(status,visibility);


        Button addNewMaterial = commonComponents.normalThemeButtonNoNavigate("Add Material", ButtonVariant.LUMO_PRIMARY);

        addNewMaterial.addClickListener(e->{

            listMaterialGrids.add(new ListMaterialGrid(null,"",comboBoxMaterial(""),quantityField(0l),unitField(""),0));
            System.out.println(listMaterialGrids);
            upgradeMaterialGrid();

        });


        Button addNewDetail = commonComponents.normalThemeButtonNoNavigate("Add Detail", ButtonVariant.LUMO_PRIMARY);

        addNewDetail.addClickListener(e->{

            listExtraDetailsGrids.add(new ListExtraDetailsGrid(null,specName(""),specDescription("")));
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
                extraDetailsGridCrafter(),
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
                productAddEditGrids.materialGridCrafter(productFeedModelGrid,listMaterialGrids),
                addNewMaterial


        );



        saveDate(save,productEditDtos);
        clearData(clear);



        return v;
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

    public void saveDate(Button save, ProductData productDataEditAddDto){



        save.addClickListener(e -> {



            productDataEditAddDto.setProductName(productName.getValue());
            productDataEditAddDto.setSku(sku.getValue());
            productDataEditAddDto.setDescription(description.getValue());
            productDataEditAddDto.setPrice(price.getValue());
            productDataEditAddDto.setDiscount(discount.getValue());
            productDataEditAddDto.setMaterialCost(materialCost.getValue());
            productDataEditAddDto.setStockQuantity(stockQuantity.getValue().longValue());
            productDataEditAddDto.setLowStockThreshold(lowThreshold.getValue().longValue());
            productDataEditAddDto.setCategory(category.getValue());
            productDataEditAddDto.setTags(tagss);
            productDataEditAddDto.setStatus(status.getValue());
            productDataEditAddDto.setVisibility(visibility.getValue());




            List<Materials> materials = new ArrayList<>();
            for(var s : listMaterialGrids){
                String materialName = s.getMaterial().getValue();
                Long amountUsed = Long.valueOf(s.getAmountOfMaterial().getValue());
                String materialUnit = s.getUnit().getValue();


                materials.add(new Materials(s.getId(),materialName,amountUsed,s.getUnitPrice(),materialUnit));
            }



            List<ExtraDetails> extraDetails = new ArrayList<>();
            for(var s : listExtraDetailsGrids){
                String specName = "";
                String specDescrption = "";
                if(s.getSpecName() instanceof TextField tf){
                    specName = tf.getValue();
                }
                if(s.getSpecDescription() instanceof TextArea ta){
                    specDescrption = ta.getValue();
                }

                extraDetails.add(new ExtraDetails(s.getId(),specName,specDescrption));
            }

            productDataEditAddDto.setMaterials(materials);

            productDataEditAddDto.setExtraDetails(extraDetails);


            System.out.println(productDataEditAddDto.getMaterials());



            if (binder.writeBeanIfValid(productDataEditAddDto)) {

                System.out.println(productDataEditAddDto.getProductName());
                System.out.println(productDataEditAddDto.getPrice());

                System.out.println(productDataEditAddDto.getExtraDetails());

                consumer.accept(productDataEditAddDto);

            } else {
                System.out.println("Validation failed");
            }
        });
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


    // grid crafters

    public Grid<ListMaterialGrid> materialGridCrafter(){


        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getMaterial).setHeader("Material").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getAmountOfMaterial).setHeader("Amount of material").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getUnit).setHeader("Unit").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.PRIMARY,VaadinIcon.TRASH,"White");

            HorizontalLayout h = new HorizontalLayout();
            h.setWidthFull();
            h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            h.add(remove);


            remove.addClickListener(e->{
                listMaterialGrids.remove(row);
                upgradeMaterialGrid();
            });


            return h;

        }).setHeader("Actions").setAutoWidth(true);

        return productFeedModelGrid;


    }

    public Grid<ListExtraDetailsGrid> extraDetailsGridCrafter(){

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecName)
                .setHeader("Specficiation name").setAutoWidth(true);

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecDescription)
                .setHeader("Specification description").setAutoWidth(true);

        extraDetailsGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.PRIMARY,VaadinIcon.TRASH,"White");

            HorizontalLayout h = new HorizontalLayout();
            h.setWidthFull();
            h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            h.add(remove);


            remove.addClickListener(e->{
                listExtraDetailsGrids.remove(row);
                upgradeExtraDetailsGrid();
            });


            return h;

        }).setHeader("Actions").setAutoWidth(true);

        return extraDetailsGrid;

    }


    // update

    public void upgradeMaterialGrid(){
        productFeedModelGrid.setItems(listMaterialGrids);
    }

    public void upgradeExtraDetailsGrid(){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }

    public void updateSelectedTags(ProductData productEditDtos){

        tagsSelected.removeAll();

        if( productEditDtos.getTags() != null && !productEditDtos.getTags().isEmpty()) {
            for (var s : productEditDtos.getTags()) {
                tagsSelected.add(tagCrafter(s));
            }
        }
    }

    public void removeTags(){

        tagsSelected.removeAll();
        tags.clear();
        tagss.clear();


    }

    // material stuff components

    public ComboBox<String> comboBoxMaterial(String chosenValue) {



        ComboBox<String> materials = new ComboBox<>();
        materials.addClassName("no-gray-disabled");
        materials.setItems(items);

        if(!chosenValue.equalsIgnoreCase("")) {
            materials.setValue(chosenValue);
            materials.setEnabled(false);
        }

        materials.addValueChangeListener(e->{

            boolean exits = listMaterialGrids.stream().anyMatch(item -> item.getNameForCompare().equalsIgnoreCase(e.getValue()));
            boolean fromClient = e.isFromClient();

            if(exits && fromClient){
                commonComponents.showNotification(String.format("%s - '%s' %s","Material",e.getValue(),"exists dublicates are not allowed"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_WARNING);

                materials.clear();
            }
            if(!exits && fromClient) {

                commonComponents.showNotification(String.format("%s - '%s' %s", "Material", e.getValue(), "added succesfully"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_SUCCESS);
                materials.setValue(e.getValue());

                System.out.println("here");
                for(var s : listMaterialGrids){
                    if(s.getMaterial() instanceof ComboBox<?> cb){
                        System.out.println(cb.getValue());

                        s.setNameForCompare((String) cb.getValue());
                    }
                }
                materials.setEnabled(false);


            }





        });
        return materials;
    }

    public IntegerField quantityField(Long chosenValue) {
        IntegerField quantity = new IntegerField();

        quantity.setStepButtonsVisible(true);
        quantity.setValue(1);
        quantity.setMin(0);
        quantity.setMax(100);
        quantity.setStep(1);
        quantity.setValue(chosenValue.intValue());

        return quantity;
    }

    public ComboBox<String> unitField(String chosenValue) {
        ComboBox<String> unit = new ComboBox<>();

        unit.setItems("Planks", "Pieces");
        unit.setValue(chosenValue);
        unit.setEnabled(false);



        return unit;
    }


    // components for extra details grid
    public TextField specName(String name){
        TextField textField = new TextField();
        textField.setValue(name);
        textField.setPlaceholder("Material");
        return textField;
    }

    public TextArea specDescription(String name){
        TextArea textArea = new TextArea();
        textArea.setValue(name);
        textArea.setPlaceholder("Wool");
        textArea.setWidthFull();
        return textArea;
    }



    // binder for textfield checks
    private void bindFields() {

        // PRODUCT NAME
        binder.forField(productName)
                .asRequired("Product name required")
                .withValidator(
                        value -> value.length() >= 3,
                        "Minimum 3 characters"
                )
                .bind(ProductData::getProductName,
                        ProductData::setProductName);


        // SKU
        binder.forField(sku)
                .asRequired("SKU required")
                .withValidator(
                        value -> value.length() >= 2,
                        "SKU too short"
                )
                .bind(ProductData::getSku,
                        ProductData::setSku);


        // DESCRIPTION
        binder.forField(description)
                .asRequired("Description required")
                .withValidator(
                        value -> value.length() >= 10,
                        "Description too short"
                )
                .bind(ProductData::getDescription,
                        ProductData::setDescription);


        // PRICE
        binder.forField(price)
                .asRequired("Price required")
                .withValidator(
                        value -> value >= 0,
                        "Price must be positive"
                )
                .bind(ProductData::getPrice,
                        ProductData::setPrice);


        // DISCOUNT
        binder.forField(discount)
                .withValidator(
                        value -> value == null || (value >= 0 && value <= 100),
                        "Discount must be between 0 and 100"
                )
                .bind(ProductData::getDiscount,
                        ProductData::setDiscount);


        // MATERIAL COST
        binder.forField(materialCost)
                .withValidator(value -> value == null || value >= 0,
                        "Must be positive")
                .bind(ProductData::getMaterialCost,
                        ProductData::setMaterialCost);


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
                .bind(ProductData::getStockQuantity,
                        ProductData::setStockQuantity);


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
                .bind(ProductData::getLowStockThreshold,
                        ProductData::setLowStockThreshold);


        // CATEGORY
        binder.forField(category)
                .asRequired("Category required")
                .bind(ProductData::getCategory,
                        ProductData::setCategory);


        // TAGS
        binder.forField(tags)
                .asRequired("Tag required")
                .withConverter(
                        tag -> List.of(tag),
                        list -> list != null && !list.isEmpty() ? list.get(0) : null
                )
                .bind(ProductData::getTags,
                        ProductData::setTags);


        // STATUS
        binder.forField(status)
                .asRequired("Status required")
                .bind(ProductData::getStatus,
                        ProductData::setStatus);


        // VISIBILITY
        binder.forField(visibility)
                .asRequired("Visibility required")
                .bind(ProductData::getVisibility,
                        ProductData::setVisibility);
    }


}
