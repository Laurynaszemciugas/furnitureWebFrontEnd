package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
import com.example.demo.ControllerModels.Orders.OrderReportPieChart;
import com.example.demo.Enums.Widths;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.DTOS.ReportResizedData;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components.ProductReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components.ProductReportMiniStatCrafter;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
@Setter
public class CustomReportPageBuilder {

    // components required to build UI
    CommonComponents commonComponents;
    Common common;

    MaterialReportCharts materialReportCharts;
    MaterialReportMiniStatCrafter materialReportMiniStatCrafter;

    OrderReportCharts orderReportCharts;
    OrderReportMiniStatCrafter orderReportMiniStatCrafter;

    ProductReportCharts productReportCharts;
    ProductReportMiniStatCrafter productReportMiniStatCrafter;

    // main UI creator

    RightSideReportCreate rightSideReportCreate;


    // servies

    MaterialService materialService;
    OrdersService ordersService;
    ProductService productService;
    EmployeeService employeeService;
    SessionCrafter sessionCrafter;


    Consumer<ReportResizedData> customIdConsumer;


    public CustomReportPageBuilder(CommonComponents commonComponents, Common common, MaterialService materialService, OrdersService ordersService, ProductService productService, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.materialService = materialService;
        this.ordersService = ordersService;
        this.productService = productService;
        this.employeeService = employeeService;

        this.materialReportCharts = new MaterialReportCharts(commonComponents,common,materialService);
        this.materialReportMiniStatCrafter = new MaterialReportMiniStatCrafter(commonComponents,common,materialService);

        this.orderReportCharts = new OrderReportCharts(commonComponents,common,ordersService);
        this.orderReportMiniStatCrafter = new OrderReportMiniStatCrafter(commonComponents,common,ordersService);

        this.productReportCharts = new ProductReportCharts(commonComponents,common,productService);
        this.productReportMiniStatCrafter = new ProductReportMiniStatCrafter(commonComponents,common,productService);

        this.sessionCrafter = new SessionCrafter();

        this.rightSideReportCreate = new RightSideReportCreate(commonComponents,common);


    }





    public void updateScene(LocalDate from, LocalDate to, HorizontalLayout layout, String color, List<ReportItems> reportItemsList, boolean creatingEditing){




        HorizontalLayout holder = new HorizontalLayout();
        holder.setWidthFull();
        holder.setPadding(false);
        holder.addClassName("layout-flex");

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        layout.removeAll();
        layout.add(commonComponents.shimmer(5));

        CompletableFuture.runAsync(() -> {



        for(var s : reportItemsList){

            Component component = switch (s.getWidget()) {

                case ORDER_MINI_STATS ->
                        orderReportMiniStatCrafter.miniStatHolder(
                                from,
                                to,
                                color,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case ORDER_BY_STATUS ->
                        orderReportCharts.ordersByStatusChart(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case ORDER_VALUE_OVER_TIME ->
                        orderReportCharts.OrderRevenueAccordingToMonth(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case ORDER_TOP_CUSTOMERS ->
                        orderReportCharts.topCustomerOrder(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case ORDER_RECENT_ORDERS ->
                        orderReportCharts.recentOrdersList(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case PRODUCT_MINI_STATS ->
                        productReportMiniStatCrafter.miniStatHolder(
                                from,
                                to,
                                color,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case PRODUCT_TOP_SELLING_PRODUCTS ->
                        productReportCharts.OrderRevenueAccordingToMonth(
                                from,
                                to,
                                color,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case PRODUCT_BY_CATEGORY ->
                        productReportCharts.productByCategory(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case PRODUCT_LOW_STOCK ->
                        productReportCharts.lowStockAlerts(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case PRODUCT_PERFORMANCE ->
                        productReportCharts.productPerformance(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case MATERIAL_MINI_STATS ->
                        new MaterialReportMiniStatCrafter(commonComponents, common, materialService)
                        .miniStatHolder(
                                from,
                                to,
                                color,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case MATERIAL_BY_STATUS ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .ProductByStatusChart(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case MATERIAL_USAGE_OVERTIME ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                                .ProductRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                        s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                        jwt
                        );

                case MATERIAL_LOW_STOCK ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .topCustomerOrder(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case MATERIAL_RECENT_MOVEMENT ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .materialStockMovement(
                                from,
                                to,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );
            };
            layoutAdd(s.getCustomId(),component, holder , s.getWidth(), s.getUserPreferredWidth(),creatingEditing);

        }

        common.timer(250);

            ui.access(() -> {
                layout.removeAll();

                if(creatingEditing){
                    layout.add(rightSideReportCreate.rightSideTop());
                }

                layout.add(holder);
            });

        });


    }



    public void layoutAdd(String customId,Component component,HorizontalLayout holder, Widths widths, String userWidth, boolean editingEnabled){


        component.getStyle().set("position","relative");
        Span span = new Span(widths == Widths.CUSTOM ? userWidth : widths.getName());

        if(widths.equals(Widths.CUSTOM) && editingEnabled) {

            component.getStyle().set("resize", "horizontal");
            component.getStyle().set("overflow", "hidden");
            component.getStyle().set("padding", "8px");
        }


        // custom check on resize
        component.getElement().executeJs("""
    const element = this;

    let resizeTimer;
    let lastWidth = element.offsetWidth;

    const observer = new ResizeObserver(() => {
        clearTimeout(resizeTimer);

        resizeTimer = setTimeout(() => {
            const newWidth = element.offsetWidth;

            if (newWidth !== lastWidth) {
                element.dispatchEvent(new CustomEvent('resize-finished', {
                    bubbles: false
                }));

                lastWidth = newWidth;
            }
        }, 300);
    });

    observer.observe(element);

    this.__resizeObserver = observer;
""");

        component.getElement().addEventListener("resize-finished", e -> {
            component.getElement().executeJs(
                    "return this.offsetWidth;"
            ).then(Integer.class, width -> {
                customIdConsumer.accept(
                        new ReportResizedData(customId, width + "px")
                );
            });
        });

        span.getStyle()
                .set("position","absolute")
                .set("top","2px")
                .set("right","5px")
                .set("z-index","100");

        span.addClassName("tag-badge");




        String widhth = widths == Widths.CUSTOM ? userWidth : widths.getWidth();


        if(component instanceof HasComponents h){
            if(h instanceof Div d) {
                holder.add(wrapperForDiv(d,span,widhth,editingEnabled));
            }
            else{
                if(editingEnabled) {
                    component.addClassNames("island-layout");
                    h.add(span);
                }

                holder.add(
                        component
                );
            }
        }






    }

    public Div wrapperForDiv(Div div,Span span, String widths, boolean editingEnabled){


        Div v = new Div();
        v.setWidth(widths);
        if(editingEnabled) {
            v.addClassNames("island-layout");
            v.add(
                    span
            );
        }
        v.getStyle().set("position","relative");






        div.setWidthFull();



        v.add(
                div
        );



        return v;
    }











}
