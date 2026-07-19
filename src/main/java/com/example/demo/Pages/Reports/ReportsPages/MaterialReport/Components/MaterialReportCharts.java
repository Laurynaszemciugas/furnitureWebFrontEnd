package com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.GraphDataDateValue;
import com.example.demo.Enums.Widths;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.DTO.MaterialReportPieChart;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.html.Div;

import java.time.LocalDate;
import java.util.List;

public class MaterialReportCharts {

    CommonComponents commonComponents;
    Common common;
    MaterialService materialService;

    public MaterialReportCharts(CommonComponents commonComponents, Common common, MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.materialService = materialService;
    }

    public Div ProductByStatusChart(

            LocalDate fromDate, LocalDate toDate, Widths widths
    ) {

        MaterialReportPieChart stuff = materialService.getReportPagePieChart(fromDate,toDate);

        long inStock = stuff.getInStockCount();
        long lowStock = stuff.getLowStockCount();
        long outOfStock = stuff.getOutOfStockCount();



        Div chartDiv = new Div();

        chartDiv.setWidth(widths.getWidth());

        chartDiv.setMinHeight("480px");

        chartDiv.getStyle()
                .set("background-color", "white")
                .set("border", "1px solid #e5e7eb")
                .set("border-radius", "16px")
                .set("padding", "22px")
                .set("box-sizing", "border-box");

        chartDiv.getElement().executeJs("""
        const host = this;

        const values = [$0, $1, $2, $3, $4, $5];

        const labels = [
            'In Stock',
            'Low stock',
            'No stock',
        ];

        const colors = [
            '#6366F1',
            '#F97316',
            '#1677FF',
        ];

        const total = values.reduce(
            (sum, value) => sum + value,
            0
        );

        host.innerHTML = `
            <div style="
                font-size: 18px;
                font-weight: 700;
                color: #172033;
                margin-bottom: 20px;
            ">
                Orders by Status
            </div>

            <div style="
                display: flex;
                align-items: center;
                gap: 55px;
                flex-wrap: wrap;
            ">
                <div style="
                    width: 260px;
                    height: 260px;
                    min-width: 260px;
                    position: relative;
                    margin: auto;
                ">
                    <canvas></canvas>
                </div>

                <div
                    class="orders-status-rows"
                    style="
                        flex: 1;
                        min-width: 300px;
                        display: flex;
                        flex-direction: column;
                        gap: 18px;
                    ">
                </div>
            </div>
        `;

        const rowsHolder =
            host.querySelector('.orders-status-rows');

        const rowsHtml = labels.map((label, index) => {
            const value = values[index];

            const percentage = total === 0
                ? 0
                : value / total * 100;

            return `
                <div style="
                    display: grid;
                    grid-template-columns: minmax(130px, 1fr) 75px 65px;
                    align-items: center;
                    column-gap: 15px;
                    font-size: 14px;
                ">
                    <div style="
                        display: flex;
                        align-items: center;
                        gap: 12px;
                        color: #526078;
                    ">
                        <span style="
                            width: 10px;
                            height: 10px;
                            min-width: 10px;
                            border-radius: 50%;
                            background-color: ${colors[index]};
                        "></span>

                        <span>${label}</span>
                    </div>

                    <span style="
                        text-align: right;
                        font-weight: 600;
                        color: #263754;
                    ">
                        ${value.toLocaleString()}
                    </span>

                    <span style="
                        text-align: right;
                        color: #526078;
                    ">
                        ${percentage.toFixed(1)}%
                    </span>
                </div>
            `;
        }).join('');

        rowsHolder.innerHTML = `
            ${rowsHtml}

            <div style="
                border-top: 1px solid #e5e7eb;
                margin-top: 2px;
                padding-top: 17px;
                display: grid;
                grid-template-columns: minmax(130px, 1fr) 75px 65px;
                column-gap: 15px;
                align-items: center;
            ">
                <span style="
                    font-size: 15px;
                    font-weight: 600;
                    color: #263754;
                ">
                    Total
                </span>

                <span style="
                    text-align: right;
                    font-size: 15px;
                    font-weight: 700;
                    color: #263754;
                ">
                    ${total.toLocaleString()}
                </span>

                <span></span>
            </div>
        `;

        const canvas = host.querySelector('canvas');
        const ctx = canvas.getContext('2d');

        if (host.ordersStatusChart) {
            host.ordersStatusChart.destroy();
        }

        const centerTextPlugin = {
            id: 'ordersStatusCenterText',

            afterDraw(chart) {
                const chartArea = chart.chartArea;

                if (!chartArea) {
                    return;
                }



                const context = chart.ctx;

                const centerX =
                    (chartArea.left + chartArea.right) / 2;

                const centerY =
                    (chartArea.top + chartArea.bottom) / 2;

                context.save();

                context.textAlign = 'center';
                context.textBaseline = 'middle';

                context.fillStyle = '#172033';
                context.font = '700 25px Arial';

                context.fillText(
                    total.toLocaleString(),
                    centerX,
                    centerY - 10
                );

                context.fillStyle = '#526078';
                context.font = '500 13px Arial';

                context.fillText(
                    'Total Orders',
                    centerX,
                    centerY + 19
                );

                context.restore();
            }
        };

        host.ordersStatusChart = new Chart(ctx, {
            type: 'doughnut',

            data: {
                labels: labels,

                datasets: [{
                    data: values,
                    backgroundColor: colors,

                    borderColor: '#ffffff',
                    borderWidth: 3,

                    hoverBorderWidth: 3,
                    hoverOffset: 7
                }]
            },

            options: {
                responsive: true,
                maintainAspectRatio: false,

                cutout: '68%',

                animation: {
                    duration: 1200,
                    easing: 'easeOutQuart'
                },

                plugins: {
                    legend: {
                        display: false
                    },

                    tooltip: {
                        backgroundColor: '#ffffff',
                        titleColor: '#172033',
                        bodyColor: '#526078',

                        borderColor: '#e2e8f0',
                        borderWidth: 1,

                        padding: 11,
                        cornerRadius: 8,

                        callbacks: {
                            label(context) {
                                const value = context.raw;

                                const percentage = total === 0
                                    ? 0
                                    : value / total * 100;

                                return context.label
                                    + ': '
                                    + value.toLocaleString()
                                    + ' ('
                                    + percentage.toFixed(1)
                                    + '%)';
                            }
                        }
                    }
                }
            },

            plugins: [
                centerTextPlugin
            ]
        });
    """,
                inStock,
                lowStock,
                outOfStock

        );




        return chartDiv;
    }


    public Div ProductRevenueAccordingToMonth(
            LocalDate fromDate,
            LocalDate toDate,
            Widths widths
    ) {
        List<GraphDataDateValue> list = materialService.getReportPageLineChart(fromDate,toDate);


        Div chartDiv = new Div();

        chartDiv.setWidth(widths.getWidth());
        chartDiv.setHeight("480px");

        chartDiv.getStyle()
                .set("background-color", "white")
                .set("border", "1px solid #e5e7eb")
                .set("border-radius", "16px")
                .set("padding", "22px")
                .set("box-sizing", "border-box");

        String labels = list.stream()
                .map(value -> "'" + value.getLocalDate() + "'")
                .reduce((first, second) -> first + "," + second)
                .orElse("");

        String data = list.stream()
                .map(value -> String.valueOf(value.getValue()))
                .reduce((first, second) -> first + "," + second)
                .orElse("");

        String javascript = """
            const host = this;

            if (host.chartInstance) {
                host.chartInstance.destroy();
            }

            host.innerHTML = `
                <div style="
                    font-size: 18px;
                    font-weight: 700;
                    color: #172033;
                    margin-bottom: 20px;
                    line-height: 22px;
                ">
                    Order Value Over Time
                </div>

                <div class="chart-holder" style="
                    position: relative;
                    width: 100%;
                    height: calc(100% - 42px);
                ">
                    <canvas></canvas>
                </div>
            `;

            const chartHolder =
                host.querySelector('.chart-holder');

            const canvas =
                chartHolder.querySelector('canvas');

            const ctx =
                canvas.getContext('2d');

            const gradient = ctx.createLinearGradient(
                0,
                0,
                0,
                chartHolder.clientHeight
            );

            gradient.addColorStop(
                0,
                'rgba(34, 117, 243, 0.22)'
            );

            gradient.addColorStop(
                1,
                'rgba(34, 117, 243, 0.01)'
            );

            const formatCurrency = value => {
                const numericValue = Number(value);

                if (numericValue >= 1000000) {
                    return '$'
                        + (numericValue / 1000000).toFixed(1)
                        + 'M';
                }

                if (numericValue >= 1000) {
                         return '€'
                             + (numericValue / 1000).toFixed(1)
                             + 'K';
                     }

                return '$' + numericValue;
            };

            const chartPlugins = window.ChartDataLabels
                ? [window.ChartDataLabels]
                : [];

            host.chartInstance = new Chart(ctx, {
                type: 'line',

                data: {
                    labels: [__LABELS__],

                    datasets: [{
                        data: [__DATA__],

                        borderColor: '#2275F3',
                        backgroundColor: gradient,

                        borderWidth: 3,
                        fill: true,

                        tension: 0.25,

                        pointRadius: 5,
                        pointHoverRadius: 8,

                        pointBackgroundColor: '#2275F3',
                        pointBorderColor: '#2275F3',
                        pointBorderWidth: 2
                    }]
                },

                options: {
                    responsive: true,
                    maintainAspectRatio: false,

                    interaction: {
                        mode: 'index',
                        intersect: false
                    },

                    layout: {
                        padding: {
                            top: 35,
                            right: 15,
                            left: 5
                        }
                    },

                    animation: {
                        duration: 1400,
                        easing: 'easeOutQuart'
                    },

                    plugins: {
                        legend: {
                            display: false
                        },

                        datalabels: {
                            display: true,

                            align: 'top',
                            anchor: 'end',
                            offset: 5,

                            color: '#344054',

                            font: {
                                size: 12,
                                weight: '600'
                            },

                            formatter(value) {
                                return formatCurrency(value);
                            }
                        },

                        tooltip: {
                            backgroundColor: '#ffffff',

                            titleColor: '#172033',
                            bodyColor: '#344054',

                            borderColor: '#e2e8f0',
                            borderWidth: 1,

                            padding: 10,
                            cornerRadius: 8,

                            displayColors: false,

                            callbacks: {
                                label(context) {
                                    return formatCurrency(
                                        context.raw
                                    );
                                }
                            }
                        }
                    },

                    scales: {
                        x: {
                            border: {
                                color: '#dbe2ea'
                            },

                            grid: {
                                display: false
                            },

                            ticks: {
                                color: '#526078',

                                font: {
                                    size: 12
                                }
                            }
                        },

                        y: {
                            beginAtZero: true,

                            border: {
                                display: false
                            },

                            grid: {
                                color:
                                    'rgba(148, 163, 184, 0.20)'
                            },

                            ticks: {
                                color: '#526078',
                                padding: 10,

                                callback(value) {
                                    return formatCurrency(value);
                                }
                            }
                        }
                    }
                },

                plugins: chartPlugins
            });
            """;

        javascript = javascript
                .replace("__LABELS__", labels)
                .replace("__DATA__", data);

        chartDiv.getElement().executeJs(javascript);

        return chartDiv;
    }

//
//
//    public VerticalLayout topCustomerOrder(LocalDate from, LocalDate to, Widths widths){
//
//        List<TopCustomerDto> list = ordersService.getOrderTopCustomer(from,to);
//
//        VerticalLayout v = new VerticalLayout();
//        v.addClassName("island");
//
//        v.setWidth(widths.getWidth());
//
//        Span span = commonComponents.spanCrafter("Top customers","activityFeed-name");
//
//        Grid<TopCustomerDto> grid = new Grid<>(TopCustomerDto.class,false);
//        grid.setItems(list);
//        grid.setWidthFull();
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getId().toString());
//
//            return span1;
//
//        }).setHeader("Id").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getName());
//
//            return span1;
//
//        }).setHeader("Customer").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getOrders().toString());
//
//            return span1;
//
//        }).setHeader("Orders").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getRevenue() + " Eur");
//
//            return span1;
//
//        }).setHeader("Revenue").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getAverageRevenue() + " Eur");
//
//            return span1;
//
//        }).setHeader("Avg. Order value").setAutoWidth(true);
//
//
//        HorizontalLayout buttonHolder = new HorizontalLayout();
//        buttonHolder.setWidthFull();
//        buttonHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
//        Button button = new Button("View all customers");
//
//        buttonHolder.add(button);
//
//
//        v.add(
//                span,
//                grid,
//                buttonHolder
//        );
//
//        return  v;
//    }
//
//
//    public VerticalLayout recentOrdersList(LocalDate from, LocalDate to, Widths widths){
//
//        List<RecentOrdersReportPage> list = ordersService.getRecentOrderList(from,to);
//
//        VerticalLayout v = new VerticalLayout();
//        v.addClassName("island");
//
//        v.setWidth(widths.getWidth());
//
//        Span span = commonComponents.spanCrafter("Recent orders summary","activityFeed-name");
//
//        Grid<RecentOrdersReportPage> grid = new Grid<>(RecentOrdersReportPage.class,false);
//        grid.setItems(list);
//        grid.setWidthFull();
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText("ORD-" + e.getId());
//
//            return span1;
//
//        }).setHeader("Id").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getProductCount().toString());
//
//            return span1;
//
//        }).setHeader("Products count").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//            span1.addClassName("stock-badge");
//
//            span1.setText(e.getOrderStatus().getDisplayName());
//
//            switch (e.getOrderStatus()){
//                case NEW -> span1.addClassName("status-new");
//                case CANCELLED -> span1.addClassName("status-cancelled");
//                case Pending -> span1.addClassName("status-pending");
//                case Finished -> span1.addClassName("status-finished");
//                case In_Progress -> span1.addClassName("status-in-progress");
//                case LACK_OF_SUPPLY -> span1.addClassName("status-lack-of-supply");
//                default -> span1.addClassName("status-none");
//
//            }
//
//            return span1;
//
//        }).setHeader("Status").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(e.getValue() + " Eur");
//
//            return span1;
//
//        }).setHeader("Value").setAutoWidth(true);
//
//        grid.addComponentColumn(e->{
//
//            Span span1 = new Span();
//
//            span1.setText(common.dateFormatter(e.getDueDate(),"MMMM dd, yyyy"));
//
//            return span1;
//
//        }).setHeader("Due date").setAutoWidth(true);
//
//
//        HorizontalLayout buttonHolder = new HorizontalLayout();
//        buttonHolder.setWidthFull();
//        buttonHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
//        Button button = new Button("View all orders");
//
//        buttonHolder.add(button);
//
//
//        v.add(
//                span,
//                grid,
//                buttonHolder
//        );
//
//        return  v;
//    }
//

}
