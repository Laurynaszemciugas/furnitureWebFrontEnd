package com.example.demo.Pages.ProductsEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.example.demo.ControllerModels.ProductEdit.ProductEditDto;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Tags;
import com.example.demo.Enums.Visibility;
import com.example.demo.Pages.ProductsEdit.Page.ProductsEdit;
import com.example.demo.Services.ProductEditService.ProductEditService;
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
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class ProductEditRightSideFields {

    CommonComponents commonComponents;
    Common common;



    private Binder<ProductEditDto> binder =
            new Binder<>(ProductEditDto.class);

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

    List<ListMaterialGrid> listMaterialGrids = new ArrayList<>();
    Grid<ListMaterialGrid> productFeedModelGrid = new Grid<>(ListMaterialGrid.class,false);

    List<Tags> tagss = new ArrayList<>();


    public ProductEditRightSideFields(CommonComponents commonComponents,
                        Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        bindFields();

    }


    public VerticalLayout rightSide(){
        VerticalLayout v =new VerticalLayout();
        v.setWidth("700px");
        v.addClassName("island");

        tagss.add(Tags.Door);

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

        if(!tagss.isEmpty()) {
            for (var s : tagss) {
                tagsSelected.add(tagCrafter(s));
            }
        }

        tags.setItems(Tags.Door,Tags.Modern,Tags.LivingRoom);

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
            updateGrid();

        });

        HorizontalLayout buttonSave = new HorizontalLayout();
        buttonSave.setWidthFull();
        buttonSave.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        Button save = new Button("Save");
        buttonSave.add(save);

        productFeedModelGrid.removeAllColumns();

        v.add(
                commonComponents.spanCrafterWordNoHide("Actions","activityFeed-name"),
                buttonSave,
                commonComponents.spanCrafterWordNoHide("Basic information","activityFeed-name"),
                basicInfo,
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
                girdCrafter(),
                addNewMaterial


        );



        save.addClickListener(e -> {

            ProductEditDto dto = new ProductEditDto();

            if (binder.writeBeanIfValid(dto)) {

                System.out.println(dto.getProductName());
                System.out.println(dto.getPrice());

                // productEditService.save(dto);

            } else {
                System.out.println("Validation failed");
            }
        });



        return v;
    }


    public Span tagCrafter(Tags tags){

        Span span = commonComponents.spanCrafterWordNoHide(tags.toString(),"tag-badge");

        return span;
    }



    public Grid<ListMaterialGrid> girdCrafter(){

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
                updateGrid();
            });


            return h;

        }).setHeader("Actions").setAutoWidth(true);

        return productFeedModelGrid;

    }


    // update grid

    public void updateGrid(){
        productFeedModelGrid.setItems(listMaterialGrids);
    }


    // material stuff components

    public ComboBox<String> comboBoxMaterial(String chosenValue) {
        ComboBox<String> materials = new ComboBox<>();
        return materials;
    }

    public IntegerField quantityField(int chosenValue) {
        IntegerField quantity = new IntegerField();
        quantity.setStepButtonsVisible(true);
        quantity.setValue(1);
        quantity.setMin(0);
        quantity.setMax(100);
        quantity.setStep(1);

        return quantity;
    }

    public ComboBox<String> unitField(String chosenValue) {
        ComboBox<String> unit = new ComboBox<>();
        unit.setItems("Planks", "Pieces");
        unit.setValue("Auto");
        unit.setEnabled(false);



        return unit;
    }



    private void bindFields() {

        // PRODUCT NAME
        binder.forField(productName)
                .asRequired("Product name required")
                .withValidator(
                        value -> value.length() >= 3,
                        "Minimum 3 characters"
                )
                .bind(ProductEditDto::getProductName,
                        ProductEditDto::setProductName);


        // SKU
        binder.forField(sku)
                .asRequired("SKU required")
                .withValidator(
                        value -> value.length() >= 2,
                        "SKU too short"
                )
                .bind(ProductEditDto::getSku,
                        ProductEditDto::setSku);


        // DESCRIPTION
        binder.forField(description)
                .asRequired("Description required")
                .withValidator(
                        value -> value.length() >= 10,
                        "Description too short"
                )
                .bind(ProductEditDto::getDescription,
                        ProductEditDto::setDescription);


        // PRICE
        binder.forField(price)
                .asRequired("Price required")
                .withValidator(
                        value -> value >= 0,
                        "Price must be positive"
                )
                .bind(ProductEditDto::getPrice,
                        ProductEditDto::setPrice);


        // DISCOUNT
        binder.forField(discount)
                .withValidator(
                        value -> value == null || (value >= 0 && value <= 100),
                        "Discount must be between 0 and 100"
                )
                .bind(ProductEditDto::getDiscount,
                        ProductEditDto::setDiscount);


        // MATERIAL COST
        binder.forField(materialCost)
                .withValidator(value -> value == null || value >= 0,
                        "Must be positive")
                .bind(ProductEditDto::getMaterialCost,
                        ProductEditDto::setMaterialCost);


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
                .bind(ProductEditDto::getStockQuantity,
                        ProductEditDto::setStockQuantity);


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
                .bind(ProductEditDto::getLowStockThreshold,
                        ProductEditDto::setLowStockThreshold);


        // CATEGORY
        binder.forField(category)
                .asRequired("Category required")
                .bind(ProductEditDto::getCategory,
                        ProductEditDto::setCategory);


        // TAGS
        binder.forField(tags)
                .asRequired("Tag required")
                .withConverter(
                        tag -> List.of(tag),
                        list -> list != null && !list.isEmpty()
                                ? list.get(0)
                                : null
                )
                .bind(ProductEditDto::getTags,
                        ProductEditDto::setTags);


        // STATUS
        binder.forField(status)
                .asRequired("Status required")
                .bind(ProductEditDto::getStatus,
                        ProductEditDto::setStatus);


        // VISIBILITY
        binder.forField(visibility)
                .asRequired("Visibility required")
                .bind(ProductEditDto::getVisibility,
                        ProductEditDto::setVisibility);
    }


}
