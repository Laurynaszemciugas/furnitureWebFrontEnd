package com.example.demo.Pages.ProductsEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ExtraDetails;
import com.example.demo.ControllerModels.Common.ListExtraDetailsGrid;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.example.demo.ControllerModels.Common.ProductDataEditAddDto;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Tags;
import com.example.demo.Enums.Visibility;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
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



    private Binder<ProductDataEditAddDto> binder =
            new Binder<>(ProductDataEditAddDto.class);

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
    Consumer<ProductDataEditAddDto> consumer;


    public void setConsumer (Consumer<ProductDataEditAddDto> consumer){
        this.consumer = consumer;
    }


    public ProductEditRightSideFields(CommonComponents commonComponents,
                        Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        bindFields();

    }

    public void loadData(ProductDataEditAddDto productEditDto){


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
        } else {
            tags.setValue(null); // Or tags.setValue(null);
        }


        if(productEditDto.getExtraDetails() != null && !productEditDto.getExtraDetails().isEmpty()) {
            for (var s : productEditDto.getExtraDetails()) {
                listExtraDetailsGrids.add(new ListExtraDetailsGrid(specName(s.getSpecName()), specDescription(s.getSpecDescription())));
            }
            upgradeExtraDetailsGrid();
        }

        if(productEditDto.getMaterials() != null &&  !productEditDto.getMaterials().isEmpty()) {
            for (var s : productEditDto.getMaterials()) {
                listMaterialGrids.add(new ListMaterialGrid(comboBoxMaterial(s.getMaterialName()), quantityField(Math.toIntExact(s.getAmountUsed())), unitField(s.getUnit())));
            }
            upgradeMaterialGrid();
        }




    }


    public VerticalLayout rightSide(ProductDataEditAddDto productEditDtos){

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


        HorizontalLayout tagsSelected = new HorizontalLayout();
        tagsSelected.setWidthFull();
        tagsSelected.addClassName("island");
        tagsSelected.setSpacing(false);
        tagsSelected.setPadding(false);

        if( productEditDtos.getTags() != null && !productEditDtos.getTags().isEmpty()) {
            for (var s : productEditDtos.getTags()) {
                tagsSelected.add(tagCrafter(s));
            }
        }


        tags.addValueChangeListener(e->{
            if(!tagss.contains(e.getValue()) && !tagss.isEmpty()) {
                tagss.add(e.getValue());
                tagsSelected.add(tagCrafter(e.getValue()));
            }
            else{
                System.out.println("that exists");
            }
            System.out.println(tagss);
            });

        FormLayout categoryTags = new FormLayout();
        categoryTags.add(category,tags);




        FormLayout productStatus = new FormLayout();
        productStatus.add(status,visibility);


        Button addNewMaterial = commonComponents.normalThemeButtonNoNavigate("Add Material", ButtonVariant.LUMO_PRIMARY);

        addNewMaterial.addClickListener(e->{

            listMaterialGrids.add(new ListMaterialGrid(comboBoxMaterial(""),quantityField(0),unitField("")));
            System.out.println(listMaterialGrids);
            upgradeMaterialGrid();

        });


        Button addNewDetail = commonComponents.normalThemeButtonNoNavigate("Add Detail", ButtonVariant.LUMO_PRIMARY);

        addNewDetail.addClickListener(e->{

            listExtraDetailsGrids.add(new ListExtraDetailsGrid(specName(""),specDescription("")));
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
                materialGridCrafter(),
                addNewMaterial


        );



        saveDate(save);



        return v;
    }

    public void saveDate(Button save){
        save.addClickListener(e -> {

            ProductDataEditAddDto dto = new ProductDataEditAddDto();

            if (binder.writeBeanIfValid(dto)) {

                System.out.println(dto.getProductName());
                System.out.println(dto.getPrice());

                consumer.accept(dto);

            } else {
                System.out.println("Validation failed");
            }
        });
    }


    public Span tagCrafter(Tags tags){

        Span span = commonComponents.spanCrafterWordNoHide(tags.toString(),"tag-badge");

        return span;
    }


    // gird crafters

    public Grid<ListMaterialGrid> materialGridCrafter(){

        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getMaterial)
                .setHeader("Material").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getAmountOfMaterial)
                .setHeader("Amount of material").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getUnit)
                .setHeader("Unit").setAutoWidth(true);

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


    // update grids

    public void upgradeMaterialGrid(){
        productFeedModelGrid.setItems(listMaterialGrids);
    }

    public void upgradeExtraDetailsGrid(){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }


    // material stuff components

    public ComboBox<String> comboBoxMaterial(String chosenValue) {
        ComboBox<String> materials = new ComboBox<>();
        materials.setItems("yes");
        materials.setValue(chosenValue);
        return materials;
    }

    public IntegerField quantityField(int chosenValue) {
        IntegerField quantity = new IntegerField();

        quantity.setStepButtonsVisible(true);
        quantity.setValue(1);
        quantity.setMin(0);
        quantity.setMax(100);
        quantity.setStep(1);
        quantity.setValue(chosenValue);

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
                .bind(ProductDataEditAddDto::getProductName,
                        ProductDataEditAddDto::setProductName);


        // SKU
        binder.forField(sku)
                .asRequired("SKU required")
                .withValidator(
                        value -> value.length() >= 2,
                        "SKU too short"
                )
                .bind(ProductDataEditAddDto::getSku,
                        ProductDataEditAddDto::setSku);


        // DESCRIPTION
        binder.forField(description)
                .asRequired("Description required")
                .withValidator(
                        value -> value.length() >= 10,
                        "Description too short"
                )
                .bind(ProductDataEditAddDto::getDescription,
                        ProductDataEditAddDto::setDescription);


        // PRICE
        binder.forField(price)
                .asRequired("Price required")
                .withValidator(
                        value -> value >= 0,
                        "Price must be positive"
                )
                .bind(ProductDataEditAddDto::getPrice,
                        ProductDataEditAddDto::setPrice);


        // DISCOUNT
        binder.forField(discount)
                .withValidator(
                        value -> value == null || (value >= 0 && value <= 100),
                        "Discount must be between 0 and 100"
                )
                .bind(ProductDataEditAddDto::getDiscount,
                        ProductDataEditAddDto::setDiscount);


        // MATERIAL COST
        binder.forField(materialCost)
                .withValidator(value -> value == null || value >= 0,
                        "Must be positive")
                .bind(ProductDataEditAddDto::getMaterialCost,
                        ProductDataEditAddDto::setMaterialCost);


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
                .bind(ProductDataEditAddDto::getStockQuantity,
                        ProductDataEditAddDto::setStockQuantity);


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
                .bind(ProductDataEditAddDto::getLowStockThreshold,
                        ProductDataEditAddDto::setLowStockThreshold);


        // CATEGORY
        binder.forField(category)
                .asRequired("Category required")
                .bind(ProductDataEditAddDto::getCategory,
                        ProductDataEditAddDto::setCategory);


        // TAGS
        binder.forField(tags)
                .asRequired("Tag required")
                .withConverter(
                        tag -> List.of(tag),
                        list -> list != null && !list.isEmpty() ? list.get(0) : null
                )
                .bind(ProductDataEditAddDto::getTags,
                        ProductDataEditAddDto::setTags);


        // STATUS
        binder.forField(status)
                .asRequired("Status required")
                .bind(ProductDataEditAddDto::getStatus,
                        ProductDataEditAddDto::setStatus);


        // VISIBILITY
        binder.forField(visibility)
                .asRequired("Visibility required")
                .bind(ProductDataEditAddDto::getVisibility,
                        ProductDataEditAddDto::setVisibility);
    }


}
