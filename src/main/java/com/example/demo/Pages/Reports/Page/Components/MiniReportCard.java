package com.example.demo.Pages.Reports.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.DashboardWidget;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.DTOS.CustomReportFeed;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class MiniReportCard {

    CommonComponents commonComponents;
    Common common;


    Consumer<Long> removeItem;

    public MiniReportCard(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;


    }

    public HorizontalLayout reportHolder(){


        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h.addClassName("layout-flex");
        h.setPadding(false);


        h.add(
                reportCardCrafter(
                        VaadinIcon.CART,
                        "View Orders Report",
                        "OrderReport",
                        "Orders Reports",
                        "Analyze order performance and trends.",
                        "#2275F3",
                        "Monthly order trends",
                        "Revenue by period",
                        "Order status breakdown",
                        "Top customers"
                ),

                reportCardCrafter(
                        VaadinIcon.PACKAGE,
                        "View Products Report",
                        "ProductReport",
                        "Products Reports",
                        "Understand product performance and inventory.",
                        "#47B25D",
                        "Best selling products",
                        "Stock level analysis",
                        "Product profitability",
                        "Category performance"
                ),

                reportCardCrafter(
                        VaadinIcon.LINES_LIST,
                        "View Materials Report",
                        "MaterialReport",
                        "Materials Reports",
                        "Track material usage and stock efficiency.",
                        "#9768EF",
                        "Material consumption",
                        "Stock turnover",
                        "Cost analysis",
                        "Supplier performance"
                ),

                reportCardCrafter(
                        VaadinIcon.USERS,
                        "View Employees Report",
                        "Reports/Employees",
                        "Employees Reports",
                        "Monitor team productivity and performance.",
                        "#F2BC26",
                        "Employee performance",
                        "Hours worked",
                        "Task completion",
                        "Productivity trends"
                ),

                reportCardCrafter(
                        VaadinIcon.MONEY,
                        "View Financial Report",
                        "Reports/Financial",
                        "Financial Reports",
                        "Get insights into revenue and expenses.",
                        "#EC6766",
                        "Revenue overview",
                        "Expense breakdown",
                        "Profit analysis",
                        "Cash flow trends"
                ),

                reportCardCrafter(
                        VaadinIcon.NEWSPAPER,
                        "Create Custom Report",
                        "reportCreator",
                        "Custom Report",
                        "Build your own report with selected metrics.",
                        "#3BB8D0",
                        "Choose data sources",
                        "Select date range",
                        "Add custom filters",
                        "Save report templates"
                )
        );

        return h;

    }





    public VerticalLayout reportCardCrafter(VaadinIcon userIcon, String buttonName, String navigateTo, String title, String descriptiont, String color, String lil1, String lil2, String lil3, String lil4){


        VerticalLayout card = new VerticalLayout();

        card.addClassName("animated-card");

        card.addClassName("island");
        card.getStyle().set("flex", "1 1 452px");
        card.getStyle().set("max-width", "620px");
        card.getStyle().set("min-width", "452px");

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setPadding(false);


        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.getStyle().set("position","relative");
        iconHolder.setHeight("90px");
        iconHolder.setWidth("100px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        iconHolder.addClassName("statIconBack");

        iconHolder.getStyle().set("background-color",common.hexToRgba(color,0.15));

        Icon icon = commonComponents.iconCrafter(userIcon,"30px",color);
        iconHolder.add(icon);


        VerticalLayout textHolder = new VerticalLayout();
        textHolder.setWidthFull();
        textHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        textHolder.setPadding(false);
        textHolder.setSpacing(false);

        Span tittle = commonComponents.spanCrafter(title,"activityFeed-name");
        Span description = commonComponents.spanCrafter(descriptiont,"stat-description");
        description.getStyle()
                .set("white-space", "normal")
                .set("overflow-wrap", "anywhere")
                .set("word-break", "break-word")
                .set("display", "block")
                .set("width", "100%")
                .set("min-width", "0");

        textHolder.add(
                tittle,
                description
        );

        firstLayer.add(
                iconHolder,
                textHolder
        );

        card.add(
                firstLayer,
                commonComponents.lineCrafter("100%")
        );



        card.add(
                lilInfo(lil1,color),
                lilInfo(lil2,color),
                lilInfo(lil3,color),
                lilInfo(lil4,color)
        );

        Button enterReport = new Button(buttonName);
        enterReport.setWidthFull();
        enterReport.getStyle().set("background-color",color).set("color","White");

        enterReport.addClickListener(e->{
            UI.getCurrent().navigate(navigateTo);
        });

        card.add(
                enterReport
        );




        return  card;



    }

    public HorizontalLayout lilInfo(String info, String color){

        HorizontalLayout h =new HorizontalLayout();
        h.setPadding(false);
        h.setWidthFull();

        h.add(
                commonComponents.iconCrafter(VaadinIcon.CHECK,"25px",color),
                commonComponents.spanCrafter(info,"s")
        );

        return h;

    }

    public HorizontalLayout loadUserData(List<CustomReportFeed> reportFeeds){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();

        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h.addClassName("layout-flex");
        h.setPadding(false);

        if(!reportFeeds.isEmpty()) {
            for (var s : reportFeeds) {
                h.add(userCreatedReports(s.getId(), s.getReportName(), s.getDashboardWidget(), s.getDescription(), s.getCreated(), s.getReportColor()));
            }
        }
        else{
            h.add(commonComponents.noDataFoundImproved("No custom reports found","Create one","reportCreator"));
        }


        return h;
    }

    public VerticalLayout userCreatedReports(Long id, String title, DashboardWidget dashboardWidget, String description, LocalDateTime created, String color){

        VerticalLayout card = new VerticalLayout();
        card.addClassName("island");
        card.getStyle().set("position","relative");

        card.addClassName("animated-card");

        card.addClassName("island");
        card.getStyle().set("flex", "1 1 452px");
        card.getStyle().set("max-width", "620px");
        card.getStyle().set("min-width", "452px");

        VerticalLayout v =  new VerticalLayout();
        v.setPadding(false);
        v.setSpacing(false);
        v.setWidthFull();

        v.add(
                commonComponents.spanCrafter(title,"activityFeed-name"),
                commonComponents.spanCrafter(description,"stat-example"),
                commonComponents.spanCrafter(created == null ? "" :  "Created - "+ common.dateFormatter(created,"dd MMM yyyy"),"stat-description")
        );

        Button deleteButton = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.ERROR,VaadinIcon.TRASH,"Red");


        deleteButton.addClickListener(e->{
           common.actualDeleteConfirmation(title);

            common.setBooleanConsumer(ee->{
                if(ee == true) {
                    removeItem.accept(id);
                }
            });

        });

        Button editButton = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.ERROR,VaadinIcon.PENCIL,"Blue");


        editButton.addClickListener(e->{
            UI.getCurrent().navigate("editReport/" + id);
        });




        Button button = new Button("View " + title);
        button.setWidthFull();
        button.getStyle().set("background-color",color).set("color","White");

        button.addClickListener(e->{
            UI.getCurrent().navigate("viewCustomReport/" + id);
        });

        Button openMore = new Button(VaadinIcon.OPTIONS.create());
        openMore.getStyle().set("position","absolute").set("right","10px").set("top","10px");
        openMore.addThemeVariants(ButtonVariant.LUMO_ICON);
        openMore.setAriaLabel("Notifications");

        Popover popover = new Popover();
        popover.setTarget(openMore);

        H4 heading = new H4("Actions");
        VerticalLayout layout = new VerticalLayout(heading);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(
                editButton,
                deleteButton
        );

        popover.add(layout);


        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.getStyle().set("position","relative");
        iconHolder.setHeight("90px");
        iconHolder.setWidth("100px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        iconHolder.addClassName("statIconBack");

        iconHolder.getStyle().set("background-color",common.hexToRgba(color,0.15));

        Icon icon = commonComponents.iconCrafter(dashboardWidget == null ? VaadinIcon.ADJUST : dashboardWidget.getIcon(),"30px",color);
        iconHolder.add(icon);

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setPadding(false);
        h.setAlignItems(FlexComponent.Alignment.CENTER);

        h.add(
                iconHolder, v
        );

        card.add(
                h,
                button,
                openMore,
                popover
        );

        return card;

    }

}
