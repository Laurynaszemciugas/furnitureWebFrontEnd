package com.example.demo.Pages.Reports.ReportsPages.EmployeeReport.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.DTOS.Common.GraphDataDateValue;
import com.example.demo.DTOS.Common.GraphDataLongValue;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.html.Div;

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





    }









