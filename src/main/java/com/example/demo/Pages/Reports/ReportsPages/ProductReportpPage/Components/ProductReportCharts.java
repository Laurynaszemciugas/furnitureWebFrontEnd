package com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.GraphDataDateValue;
import com.example.demo.ControllerModels.Common.GraphDataLongValue;
import com.example.demo.ControllerModels.Orders.OrderReportPieChart;
import com.example.demo.Enums.Widths;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.DTOS.RecentOrdersReportPage;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.DTOS.TopCustomerDto;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO.ProductLowStockList;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO.ProductPerformanceReport;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO.ProductReportPieChart;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductReportCharts {

    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    List<GraphDataLongValue> list = new ArrayList<>();

    public ProductReportCharts(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
    }

    public Div productByCategory(
            LocalDate fromDate,
            LocalDate toDate,
            Widths widths

    ) {

        List<ProductReportPieChart> data = productService.getPieChartProductReport(fromDate,toDate);

                Div chartDiv = new Div();



        chartDiv.setWidth(widths.getWidth());

        chartDiv.setMinHeight("480px");

        chartDiv.getStyle()
                .set("background-color", "white")
                .set("border", "1px solid #e5e7eb")
                .set("border-radius", "16px")
                .set("padding", "22px")
                .set("box-sizing", "border-box");


        String[] labels = data.stream()
                .map(x -> x.getCategory().toString())
                .toArray(String[]::new);


        Long[] values = data.stream()
                .map(ProductReportPieChart::getValue)
                .toArray(Long[]::new);



        chartDiv.getElement().executeJs("""
    
    const host = this;

    const labels = $0;
    const values = $1;


const colors = [
    '#16A34A',
    '#22C55E', 
    '#15803D', 
    '#4ADE80',
    '#10B981', 
    '#34D399', 
    '#059669', 
    '#65A30D', 
    '#84CC16', 
    '#047857'  
];


    const total = values.reduce(
        (sum,value)=>sum+value,
        0
    );


    host.innerHTML = `

        <div style="
            font-size:18px;
            font-weight:700;
            color:#172033;
            margin-bottom:20px;
        ">
            Products by Category
        </div>


        <div style="
            display:flex;
            align-items:center;
            gap:55px;
            flex-wrap:wrap;
        ">


            <div style="
                width:260px;
                height:260px;
                min-width:260px;
            ">
                <canvas></canvas>
            </div>


            <div class="categoryRows"
                style="
                    flex:1;
                    min-width:300px;
                    display:flex;
                    flex-direction:column;
                    gap:18px;
                ">
            </div>


        </div>

    `;



    const rowsHolder =
        host.querySelector(".categoryRows");



    rowsHolder.innerHTML = labels.map((label,index)=>{


        const value = values[index];


        const percentage =
            total === 0
            ? 0
            : value / total * 100;


        return `

        <div style="
            display:grid;
            grid-template-columns:minmax(130px,1fr) 75px 65px;
            align-items:center;
            column-gap:15px;
            font-size:14px;
        ">


            <div style="
                display:flex;
                align-items:center;
                gap:12px;
                color:#526078;
            ">


                <span style="
                    width:10px;
                    height:10px;
                    border-radius:50%;
                    background:${colors[index % colors.length]};
                ">
                </span>


                <span>
                    ${label}
                </span>


            </div>


            <span style="
                text-align:right;
                font-weight:600;
                color:#263754;
            ">
                ${value.toLocaleString()}
            </span>


            <span style="
                text-align:right;
                color:#526078;
            ">
                ${percentage.toFixed(1)}%
            </span>


        </div>

        `;

    }).join("");



    const canvas = host.querySelector("canvas");

    const ctx = canvas.getContext("2d");



    if(host.productCategoryChart){
        host.productCategoryChart.destroy();
    }



    const centerTextPlugin = {

        id:"centerText",

        afterDraw(chart){

            const area = chart.chartArea;

            if(!area)
                return;


            const ctx = chart.ctx;


            const x =
                (area.left + area.right) / 2;


            const y =
                (area.top + area.bottom) / 2;


            ctx.save();


            ctx.textAlign="center";
            ctx.textBaseline="middle";


            ctx.fillStyle="#172033";
            ctx.font="700 25px Arial";


            ctx.fillText(
                total.toLocaleString(),
                x,
                y-10
            );


            ctx.fillStyle="#526078";
            ctx.font="500 13px Arial";


            ctx.fillText(
                "Products",
                x,
                y+18
            );


            ctx.restore();

        }

    };



    host.productCategoryChart = new Chart(ctx,{

        type:"doughnut",


        data:{

            labels:labels,

            datasets:[{

                data:values,

                backgroundColor:
                    labels.map((_,index)=>
                        colors[index % colors.length]
                    ),


                borderColor:"#ffffff",

                borderWidth:3,

                hoverOffset:7

            }]

        },


        options:{

            responsive:true,

            maintainAspectRatio:false,

            cutout:"68%",


            plugins:{

                legend:{
                    display:false
                },


                tooltip:{

                    callbacks:{

                        label(context){

                            const value=context.raw;

                            const percentage =
                                total===0
                                ?0
                                :value/total*100;


                            return context.label
                            +" : "
                            +value.toLocaleString()
                            +" ("
                            +percentage.toFixed(1)
                            +"%)";

                        }

                    }

                }

            }

        },


        plugins:[
            centerTextPlugin
        ]

    });


    """,
                labels,
                values
        );


        return chartDiv;
    }


public Div OrderRevenueAccordingToMonth(
        LocalDate fromDate,
        LocalDate toDate,
        String color,
        Widths widths
) {


    List<GraphDataLongValue> list =
            productService.getTopProducts(fromDate, toDate, 5);


    Div chartDiv = new Div();


    chartDiv.setWidth(widths.getWidth());
    chartDiv.setHeight("480px");


    chartDiv.getStyle()
            .set("background-color", "white")
            .set("border", "1px solid #e5e7eb")
            .set("border-radius", "16px")
            .set("padding", "22px")
            .set("box-sizing", "border-box")
            .set("position", "relative");



    ComboBox<Integer> topProductCounts = new ComboBox<>();

    topProductCounts.setItems(
            1,
            2,
            5,
            10
    );

    topProductCounts.setValue(5);


    topProductCounts.getStyle()
            .set("position", "absolute")
            .set("right", "22px")
            .set("top", "22px")
            .set("width", "100px");



    chartDiv.add(topProductCounts);



    String labels = list.stream()
            .map(value -> "'" + value.getName() + "'")
            .reduce((a,b) -> a + "," + b)
            .orElse("");



    String data = list.stream()
            .map(value -> String.valueOf(value.getAmount()))
            .reduce((a,b) -> a + "," + b)
            .orElse("");



    String javascript = """
        const host = this;


        if(host.chartInstance){
            host.chartInstance.destroy();
        }


        host.chartContainer = document.createElement("div");

        host.chartContainer.style.width = "100%";
        host.chartContainer.style.height = "100%";


        host.chartContainer.innerHTML = `
            <div style="
                font-size:18px;
                font-weight:700;
                color:#172033;
                margin-bottom:20px;
            ">
                Top selling products
            </div>

            <div style="
                width:100%;
                height:calc(100% - 40px);
            ">
                <canvas></canvas>
            </div>
        `;


        host.appendChild(host.chartContainer);



        const ctx =
            host.chartContainer
            .querySelector("canvas")
            .getContext("2d");



        host.chartInstance = new Chart(ctx, {

            type:"bar",


            data:{

                labels:[__LABELS__],


                datasets:[{

                    data:[__DATA__],

                    backgroundColor:"__COLOR__",

                    borderRadius:8,

                    barThickness:24
                }]
            },


            options:{

                indexAxis:"y",

                responsive:true,

                maintainAspectRatio:false,


                plugins:{

                    legend:{
                        display:false
                    },


                    datalabels:{

                        display:true,

                        anchor:"end",

                        align:"right",

                        color:"#344054"
                    }
                }
            }
        });

        """;


    javascript = javascript
            .replace("__LABELS__", labels)
            .replace("__DATA__", data)
            .replace("__COLOR__", color);



    chartDiv.getElement()
            .executeJs(javascript);




    topProductCounts.addValueChangeListener(e -> {


        if(e.isFromClient()) {


            List<GraphDataLongValue> newList =
                    productService.getTopProducts(
                            fromDate,
                            toDate,
                            e.getValue()
                    );



            String newLabels = newList.stream()
                    .map(value -> "'" + value.getName() + "'")
                    .reduce((a,b) -> a + "," + b)
                    .orElse("");



            String newData = newList.stream()
                    .map(value -> String.valueOf(value.getAmount()))
                    .reduce((a,b) -> a + "," + b)
                    .orElse("");



            String updateChart = """
                const chart = this.chartInstance;


                if(chart){

                    chart.data.labels = [__LABELS__];

                    chart.data.datasets[0].data = [__DATA__];


                    chart.update();

                }
                """;


            updateChart = updateChart
                    .replace("__LABELS__", newLabels)
                    .replace("__DATA__", newData);



            chartDiv.getElement()
                    .executeJs(updateChart);
        }

    });



    return chartDiv;
}



    public VerticalLayout lowStockAlerts(LocalDate from, LocalDate to, Widths widths){

        List<ProductLowStockList> list = productService.getLowStockAlerts(from,to);

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.setWidth(widths.getWidth());

        Span span = commonComponents.spanCrafter("Top customers","activityFeed-name");

        Grid<ProductLowStockList> grid = new Grid<>(ProductLowStockList.class,false);
        grid.setItems(list);
        grid.setWidthFull();



        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);


            Span span1 = new Span();
            span1.addClassName("stat-example");
            span1.setText(e.getProductName());

            Image image = commonComponents.imageCrafter(e.getImageUrl(),"50px","50px","5px");

            h.add(image,span1);

            return h;

        }).setHeader("Product").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stat-example");

            span1.setText(e.getStockLeft().toString());

            return span1;

        }).setHeader("Stock left").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stat-example");

            span1.setText(e.getLowThreshold().toString());

            return span1;

        }).setHeader("Threshold").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stock-badge");

            if(e.isUserDriven()){
                span1.setText("Manual");
                span1.addClassName("status-new");
            }
            else{
                span1.setText("Automatic");
                span1.addClassName("status-lack-of-supply");
            }




            return span1;

        }).setHeader("Who manages stock").setAutoWidth(true);


        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setWidthFull();
        buttonHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Button button = new Button("Calculated");

        buttonHolder.add(button);


        v.add(
                span,
                grid,
                buttonHolder
        );

        return  v;
    }


    public VerticalLayout productPerformance(LocalDate from, LocalDate to, Widths widths){

        List<ProductPerformanceReport> list = productService.getProductPerformance(from,to);

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.setWidth(widths.getWidth());

        Span span = commonComponents.spanCrafter("Recent orders summary","activityFeed-name");

        Grid<ProductPerformanceReport> grid = new Grid<>(ProductPerformanceReport.class,false);
        grid.setItems(list);
        grid.setWidthFull();

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stat-title");

            span1.setText(e.getId().toString());

            return span1;

        }).setHeader("Id").setAutoWidth(true);

        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);


            Span span1 = new Span();
            span1.addClassName("stat-example");
            span1.setText(e.getProductName());

            Image image = commonComponents.imageCrafter(e.getImageUrl(),"50px","50px","5px");

            h.add(image,span1);

            return h;

        }).setHeader("Product").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stock-badge");

            span1.setText(e.getUnitsSold().toString());


            return span1;

        }).setHeader("Units sold").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stat-example");

            span1.setText(e.getRevenue().toString());

            return span1;

        }).setHeader("Revenue").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stat-example");

            span1.setText(e.getRating().toString());

            return span1;

        }).setHeader("Rating").setAutoWidth(true);


        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setWidthFull();
        buttonHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Button button = new Button("View all orders");

        buttonHolder.add(button);


        v.add(
                span,
                grid,
                buttonHolder
        );

        return  v;
    }


}
