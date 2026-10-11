package com.example.demo.Pages.Reports.ReportsPages.EmployeeReport.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.DTOS.Common.GraphDataDateValue;
import com.example.demo.DTOS.Common.GraphDataLongValue;
import com.example.demo.DTOS.Employee.EmployeeReportTopPerformers;
import com.example.demo.DTOS.StockMovement.StockMovementGrid;
import com.example.demo.Enums.Type;
import com.example.demo.Pages.Employee.Page.EmployeesPage;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.copilot.shaded.checkerframework.checker.units.qual.C;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.util.List;

public class EmployeeReportCharts {

    CommonComponents commonComponents;
    Common common;
    EmployeeService employeeService;

    SessionCrafter sessionCrafter;


    public EmployeeReportCharts(CommonComponents commonComponents, Common common, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;
        this.sessionCrafter = new SessionCrafter();



    }



    public Div employeeProductsMadeAccordingToCategory(
            LocalDate fromDate,
            LocalDate toDate,
            String widths,
            String jwt
    ) {

        List<GraphDataLongValue> list =
                employeeService.getEmployeeReportBarChart(
                        fromDate,
                        toDate,
                        jwt
                );

        Div chartDiv = new Div();

        chartDiv.setWidth(widths);
        chartDiv.setHeight("480px");

        chartDiv.getStyle()
                .set("background-color", "white")
                .set("border", "1px solid #e5e7eb")
                .set("border-radius", "16px")
                .set("padding", "22px")
                .set("box-sizing", "border-box");

        String labels = list.stream()
                .map(value -> "'" + value.getName() + "'")
                .reduce((first, second) -> first + "," + second)
                .orElse("");

        String data = list.stream()
                .map(value -> String.valueOf(value.getAmount()))
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
                Products made according to employee category
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

        const formatNumber = value => {
            return Number(value).toLocaleString();
        };

        const chartPlugins = window.ChartDataLabels
            ? [window.ChartDataLabels]
            : [];

        host.chartInstance = new Chart(ctx, {
            type: 'bar',

            data: {
                labels: [__LABELS__],

                datasets: [{
                    data: [__DATA__],

                    backgroundColor: '#2275F3',
                    borderColor: '#2275F3',

                    borderWidth: 0,

                    borderRadius: 8,

                    barPercentage: 0.65,
                    categoryPercentage: 0.75
                }]
            },

            options: {
                responsive: true,
                maintainAspectRatio: false,

                animation: {
                    duration: 1200,
                    easing: 'easeOutQuart'
                },

                layout: {
                    padding: {
                        top: 30,
                        right: 15,
                        left: 5
                    }
                },

                plugins: {
                    legend: {
                        display: false
                    },

                    datalabels: {
                        display: true,

                        anchor: 'end',
                        align: 'top',

                        offset: 5,

                        color: '#344054',

                        font: {
                            size: 12,
                            weight: '600'
                        },

                        formatter(value) {
                            return formatNumber(value);
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
                                return formatNumber(context.raw);
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
                                return formatNumber(value);
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






    public Div employeeHoursLineGraph(
            LocalDate fromDate,
            LocalDate toDate,
            String widths,
            String jwt
    ) {

        List<GraphDataDateValue> list =
                employeeService.hoursWorked(
                        fromDate,
                        toDate,
                        jwt
                );

        Div chartDiv = new Div();

        chartDiv.setWidth(widths);
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
            Employee working hours
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

    const formatNumber = value => {
        return Number(value).toLocaleString(undefined, {
            maximumFractionDigits: 2
        });
    };

    const chartPlugins = window.ChartDataLabels
        ? [window.ChartDataLabels]
        : [];

    host.chartInstance = new Chart(ctx, {
        type: 'line',

        data: {
            labels: [__LABELS__],

            datasets: [{
                label: 'Hours worked',
                data: [__DATA__],

                borderColor: '#2275F3',
                backgroundColor: 'rgba(34, 117, 243, 0.12)',

                borderWidth: 3,

                pointBackgroundColor: '#2275F3',
                pointBorderColor: '#ffffff',
                pointBorderWidth: 2,
                pointRadius: 5,
                pointHoverRadius: 7,

                tension: 0.35,

                fill: true
            }]
        },

        options: {
            responsive: true,
            maintainAspectRatio: false,

            animation: {
                duration: 1200,
                easing: 'easeOutQuart'
            },

            layout: {
                padding: {
                    top: 30,
                    right: 15,
                    left: 5
                }
            },

            plugins: {
                legend: {
                    display: false
                },

                datalabels: {
                    display: true,

                    align: 'top',
                    offset: 6,

                    color: '#344054',

                    font: {
                        size: 12,
                        weight: '600'
                    },

                    formatter(value) {
                        return formatNumber(value) + ' h';
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
                            return formatNumber(context.raw) + ' hours';
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

                    title: {
                        display: true,
                        text: 'Hours worked',
                        color: '#526078'
                    },

                    border: {
                        display: false
                    },

                    grid: {
                        color: 'rgba(148, 163, 184, 0.20)'
                    },

                    ticks: {
                        color: '#526078',
                        padding: 10,

                        callback(value) {
                            return formatNumber(value) + ' h';
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





    public VerticalLayout topEmployees(LocalDate from, LocalDate to, String widths, String jwt){

        List<EmployeeReportTopPerformers> list = employeeService.allPerformers(from,to,jwt);

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.setWidth(widths);

        Span span = commonComponents.spanCrafter("Employees","activityFeed-name");

        Grid<EmployeeReportTopPerformers> grid = new Grid<>(EmployeeReportTopPerformers.class,false);
        grid.setItems(list);
        grid.setWidthFull();

        grid.addComponentColumn(e->{

            Span span1 = new Span();

            span1.addClassName("stat-title");

            span1.setText("#" + e.getId());

            return span1;

        }).setHeader("Id").setAutoWidth(true);

        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);
            h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);



            h.add(
                commonComponents.imageCrafter(e.getImageUrl() == null ? "No_picture.png" : e.getImageUrl() ,"50px","50px","50%"),
                commonComponents.spanCrafterWordNoHide(e.getFullName(),"stat-example")
            );

            return h;

        }).setHeader("Employee").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span1 = new Span();
            span1.addClassName("stock-badge");

            span1.setText(e.getEmployeeDepartment().getDisplayName());



            return span1;

        }).setHeader("Department").setAutoWidth(true);

        grid.addComponentColumn(e->{

            return commonComponents.spanCrafterWordNoHide(e.getFinishedOrders().toString(),"stat-example");

        }).setHeader("Units completed").setAutoWidth(true);

        grid.addComponentColumn(e->{

            return commonComponents.spanCrafterWordNoHide(e.getHoursWorked() + "h","stat-example");

        }).setHeader("Hours worked").setAutoWidth(true);


        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setWidthFull();
        buttonHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Button button = new Button("View all employees",e-> UI.getCurrent().navigate(EmployeesPage.class));

        buttonHolder.add(button);


        v.add(
                span,
                grid,
                buttonHolder
        );

        return  v;
    }





    }









