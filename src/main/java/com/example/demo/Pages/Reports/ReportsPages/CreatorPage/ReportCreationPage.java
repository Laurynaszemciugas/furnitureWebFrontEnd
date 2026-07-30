package com.example.demo.Pages.Reports.ReportsPages.CreatorPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
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
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
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
                leftRightJoin()
        );

        return layout;
    }

    public VerticalLayout leftSide(){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

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
        TextArea reportDescription = new TextArea("Report description");
        reportDescription.setWidthFull();
        reportDescription.setHeight("80px");

        ComboBox<Widget> widgets = new ComboBox<>("Widget");
        widgets.setItems(Widget.values());
        ComboBox<Widths> widths = new ComboBox<>("Width");
        widths.setItems(Widths.values());
        Button addWidget = commonComponents.buttonThemeAndIcon("Add widget",null, ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White");

        addWidget.addClickListener(e->{

            report.setReportCategory(reportCategory.getValue());
            report.setReportColor(colorPicker.getValue());
            report.setReportName(reportName.getValue());
            report.setDescription(reportDescription.getValue());

            List<ReportItems> reportItems = report.getReportItemsList();
            reportItems.add(new ReportItems(null,randomId(widgets.getValue().toString()),widgets.getValue(),widths.getValue(),report));

            updateGrid();

            customReportPageBuilder.updateScene(rightSide,colorPicker.getValue(),report.getReportItemsList());
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

            Span span = commonComponents.spanCrafter(e.getWidget().toString(),"stat-example");

            return span;

        }).setAutoWidth(true).setHeader("Widget");


        reportGrid.addComponentColumn(e->{


            HorizontalLayout h = new HorizontalLayout();

            ComboBox<Widths> widthsComboBox = new ComboBox<>();
            widthsComboBox.setItems(Widths.values());

            widthsComboBox.setValue(e.getWidth());



            Button increaseIndex = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.LUMO_ICON,VaadinIcon.ANGLE_UP,"Black");
            increaseIndex.addClickListener(ee->{
                increaseIndexList(e.getCustomId());
                updateGrid();

            });


            Button decreaseIndex = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.LUMO_ICON,VaadinIcon.ANGLE_DOWN,"Black");
            decreaseIndex.addClickListener(eee->{
                decreaseIndexList(e.getCustomId());
                updateGrid();
            });


            Button removeItem = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.LUMO_ERROR,VaadinIcon.TRASH,"Red");

            removeItem.addClickListener(item->{
                report.getReportItemsList().remove(e);
                updateGrid();
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

        }).setWidth("400px").setFlexGrow(1).setHeader("Size");






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
        rightSide.addClassName("island");
        rightSide.setWidthFull();

        // 1. Enable wrapping for items
        rightSide.getStyle().set("flex-wrap", "wrap");

        // 2. Prevent flexbox from spreading wrapped rows vertically across the screen
        rightSide.getStyle().set("align-content", "flex-start");

        // 3. Set a clean, uniform gap between rows and columns
        rightSide.getStyle().set("gap", "16px");

        return rightSide;
    }
    public SplitLayout leftRightJoin() {
        VerticalLayout leftSide = leftSide();
        HorizontalLayout rightSide = rightSide();

        SplitLayout splitLayout = new SplitLayout(leftSide, rightSide);

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

}
