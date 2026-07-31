package com.example.demo.Pages.Reports.ReportsPages.CreatorPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.ReportCategory;
import com.example.demo.Enums.Widget;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Material.MaterialAddEdit.Components.ColorSelector;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.CustomReportPageBuilder;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.MaterialReportPage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.*;


@Route(value = "reportCreator", layout = MainLayout.class)
public class ReportCreationPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    ColorSelector colorSelector;
    Report report = new Report();

    HorizontalLayout rightSide = new HorizontalLayout();

    CustomReportPageBuilder customReportPageBuilder;
    Grid<ReportItems> reportGrid = new Grid<>(ReportItems.class,false);

    public ReportCreationPage(CommonComponents commonComponents, Common common,CustomReportPageBuilder customReportPageBuilder) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.customReportPageBuilder = customReportPageBuilder;
        this.colorSelector = new ColorSelector();

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

    public HorizontalLayout mainLayout() {

        HorizontalLayout layout = new HorizontalLayout();

        layout.setWidthFull();

        layout.setFlexGrow(1);
        //layout.setWidth("1650px");
        layout.setPadding(true);
        layout.getStyle().set("margin-top", "5px");

        layout.addClassName("layout-flex");


        layout.add(
                briefPageExplanation(),
                leftRightJoin()
        );

        return layout;
    }

    public VerticalLayout leftSide(){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.addClassName("fromLeftToRight");

        v.add(
                leftSideCrafter()
        );



        return v;
    }

    public VerticalLayout leftSideCrafter(){

        VerticalLayout v = new VerticalLayout();

        v.setWidthFull();
        v.setPadding(false);

        TextField reportName = new TextField("Report name");
        reportName.setWidthFull();

        TextField colorPicker = new TextField("Global color");

        ComboBox<ReportCategory> reportCategory = new ComboBox<>("Report category");
        reportCategory.setItems(ReportCategory.values());
        reportCategory.setItemLabelGenerator(ReportCategory::name);


        TextArea reportDescription = new TextArea("Report description");
        reportDescription.setWidthFull();
        reportDescription.setHeight("80px");


        ComboBox<Widget> widgets = new ComboBox<>("Widget");
        widgets.setItems(Widget.values());


        widgets.setItemLabelGenerator(Widget::getTitle);

        widgets.setRenderer(new ComponentRenderer<>(WID -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);

            Icon vaadinIcon = WID.getIcon().create();
            vaadinIcon.setColor(WID.getColor());
            Span name = new Span(WID.getTitle());

            layout.add(vaadinIcon, name);
            return layout;
        }));


        ComboBox<Widths> widths = new ComboBox<>("Width");
        widths.setItems(Widths.values());
        Button addWidget = commonComponents.buttonThemeAndIcon("Add widget",null, ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White");

        addWidget.addClickListener(e->{

            if(widgets.getValue() == null){
                commonComponents.showNotification("Widget is not selected",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
                widgets.setInvalid(true);
                widgets.setErrorMessage("Please fill this field");
            }
            else if(widths.getValue() == null){
                commonComponents.showNotification("Width is not selected",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
                widths.setInvalid(true);
                widths.setErrorMessage("Please fill this field");
            }

            else {


                report.setReportCategory(reportCategory.getValue());
                report.setReportColor(colorPicker.getValue());
                report.setReportName(reportName.getValue());
                report.setDescription(reportDescription.getValue());

                List<ReportItems> reportItems = report.getReportItemsList();
                reportItems.add(new ReportItems(null, randomId(widgets.getValue().toString()), widgets.getValue(), widths.getValue(), report));

                updateGrid();

                customReportPageBuilder.updateScene(rightSide, colorPicker.getValue(), report.getReportItemsList());
            }
            });


        addWidget.setWidthFull();

        FormLayout firstLayer = new FormLayout();
        firstLayer.add(
                colorSelector.colorSelector(colorPicker),
                reportCategory
        );
        firstLayer.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),       // 1 column on screens smaller than 500px
                new FormLayout.ResponsiveStep("500px", 2)    // 2 columns on screens 500px or wider
        );

        FormLayout secondLayer = new FormLayout();
        secondLayer.add(
                widgets,
                widths
        );

        secondLayer.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );



        reportGrid.addComponentColumn(e->{

            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);

            Icon vaadinIcon = e.getWidget().getIcon().create();
            vaadinIcon.setColor(e.getWidget().getColor());
            Span name = commonComponents.spanCrafterWordNoHide(e.getWidget().getTitle(),"stat-example");

            layout.add(vaadinIcon, name);

            return layout;

        }).setAutoWidth(true).setHeader("Widget");


        reportGrid.addComponentColumn(e->{


            HorizontalLayout h = new HorizontalLayout();

            ComboBox<Widths> widthsComboBox = new ComboBox<>();
            widthsComboBox.setItems(Widths.values());

            widthsComboBox.setValue(e.getWidth());

            widthsComboBox.addValueChangeListener(sa->{

                e.setWidth(sa.getValue());
                updateGrid();

                customReportPageBuilder.updateScene(rightSide,colorPicker.getValue(),report.getReportItemsList());

            });



            Button increaseIndex = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.LUMO_ICON,VaadinIcon.ANGLE_UP,"Black");
            increaseIndex.addClickListener(ee->{
                increaseIndexList(e.getCustomId());
                updateGrid();

                customReportPageBuilder.updateScene(rightSide,colorPicker.getValue(),report.getReportItemsList());

            });


            Button decreaseIndex = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.LUMO_ICON,VaadinIcon.ANGLE_DOWN,"Black");
            decreaseIndex.addClickListener(eee->{
                decreaseIndexList(e.getCustomId());
                updateGrid();
                customReportPageBuilder.updateScene(rightSide,colorPicker.getValue(),report.getReportItemsList());

            });


            Button removeItem = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.LUMO_ERROR,VaadinIcon.TRASH,"Red");

            removeItem.addClickListener(item->{
                report.getReportItemsList().remove(e);
                updateGrid();

                customReportPageBuilder.updateScene(rightSide,colorPicker.getValue(),report.getReportItemsList());

            });









            h.add(
                    widthsComboBox,
                    increaseIndex,
                    decreaseIndex,
                    removeItem
            );

            int index = report.getReportItemsList().stream()
                    .map(ReportItems::getCustomId)
                    .toList()
                    .indexOf(e.getCustomId());

            increaseIndex.setVisible(index > 0);

            decreaseIndex.setVisible(index < report.getReportItemsList().size() - 1);

            return h;

        }).setWidth("400px").setFlexGrow(1).setHeader("Size & actions");






        v.add(
                commonComponents.spanCrafter("Report settings","activityFeed-name"),
                reportName,
                firstLayer,
                reportDescription,
                commonComponents.spanCrafter("Add widget","activityFeed-name"),
                secondLayer,
                addWidget,
                commonComponents.spanCrafter("Selected widgets","activityFeed-name"),
                reportGrid

        );


        return v;

    }


    public HorizontalLayout rightSide() {

        rightSide.addClassName("fromRightToLeft");
        rightSide.addClassName("island");

        rightSide.setWidthFull();
        rightSide.setPadding(false);
        rightSide.setSpacing(false);

        rightSide.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("align-content", "flex-start")
                .set("align-items", "flex-start")
                .set("gap", "16px")
                .set("box-sizing", "border-box");

        return rightSide;
    }
    public SplitLayout leftRightJoin() {
        VerticalLayout leftSide = leftSide();
        HorizontalLayout rightSide = rightSide();

        SplitLayout splitLayout = new SplitLayout(leftSide, rightSide);
        splitLayout.addClassName("smooth-panel");


        splitLayout.setSplitterPosition(35);
        splitLayout.setWidthFull();
        splitLayout.setHeightFull();



        return splitLayout;
    }

    public String randomId(String item){
        Random random = new Random();

        int number = random.nextInt(100);

        return item + "-" + number;

    }

    public int findIndex(String customId){


        for(int i = 0; i < report.getReportItemsList().size() ; i++ ){
            if(report.getReportItemsList().get(i).getCustomId().equals(customId)){
                return i;
            }
        }

        return -1;
    }

    public void increaseIndexList(String customId){

        int index = findIndex(customId);
        if(index <= 0){
            return;
        }



        Collections.swap(
                report.getReportItemsList(),
                index,
                index - 1
        );


    }

    public void decreaseIndexList(String customId){

        int index = findIndex(customId);
        if(index == -1 || index >= report.getReportItemsList().size() - 1){
            return;
        }


        Collections.swap(
                report.getReportItemsList(),
                index,
                index + 1
        );


    }

    public void loadData(){
        List<ReportItems> reportItems = new ArrayList<>();
        reportItems.add(new ReportItems(null,"123",Widget.ORDER_MINI_STATS,Widths.FULL_WIDTH,null) );
        reportItems.add(new ReportItems(null,"123",Widget.ORDER_RECENT_ORDERS,Widths.FULL_WIDTH,null) );

        report.setReportItemsList(reportItems);



        updateGrid();

    }

    public void updateGrid(){
        reportGrid.setItems(report.getReportItemsList());
    }

    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout h = new HorizontalLayout();

        h.addClassName("smooth-panel");

        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Reports", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Create report", ButtonVariant.LUMO_PRIMARY);

        createOrder.addClickListener(e->{


        });

        buttonHolder.add(
                cancel,
                createOrder
        );
        h.add(
                commonComponents.biefPageExplanation("Create custom report"),
                buttonHolder

        );
        return h;
    }


}
