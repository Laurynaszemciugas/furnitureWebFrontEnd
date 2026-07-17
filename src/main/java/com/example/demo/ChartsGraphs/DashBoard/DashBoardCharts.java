package com.example.demo.ChartsGraphs.DashBoard;

import com.example.demo.ControllerModels.Common.GraphDataDateValue;
import com.vaadin.flow.component.html.Div;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardCharts {

    public Div ChartTest(List<GraphDataDateValue> list) {

        Div chartDiv = new Div();
        chartDiv.setWidthFull();
        chartDiv.setHeight("480px");
        chartDiv.getStyle()
                .set("border-radius", "16px");

        // Convert Java list → JS arrays
        String labels = list.stream()
                .map(d -> "'" + d.getLocalDate() + "'")
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        String data = list.stream()
                .map(d -> String.valueOf(d.getValue()))
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        chartDiv.getElement().executeJs("""
        this.innerHTML = '';

        const canvas = document.createElement('canvas');
        this.appendChild(canvas);

        const ctx = canvas.getContext('2d');

        // BLUE gradient (your original style)
        const gradient = ctx.createLinearGradient(0, 0, 0, 300);
        gradient.addColorStop(0, 'rgba(99, 102, 241, 0.9)');
        gradient.addColorStop(1, 'rgba(99, 102, 241, 0.2)');

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: [%s],
                datasets: [{
                    label: 'Value',
                    data: [%s],

                    backgroundColor: gradient,
                    borderColor: '#6366F1',
                    borderWidth: 3,
                    fill: true,

                    // smooth line
                    tension: 0.4,

                    // points
                    pointRadius: 4,
                    pointHoverRadius: 8,
                    pointBackgroundColor: '#ffffff',
                    pointBorderColor: '#6366F1'
                }]
            },

            options: {
                responsive: true,
                maintainAspectRatio: false,

                animation: {
                    duration: 1400,
                    easing: 'easeOutQuart'
                },

                plugins: {
                    legend: {
                        labels: {
                            color: '#000',
                            font: {
                                size: 14
                            }
                        }
                    },
                    tooltip: {
                        backgroundColor: '#ffffff',
                        titleColor: '#000',
                        bodyColor: '#000',
                        borderColor: '#e2e8f0',
                        borderWidth: 1,
                        padding: 10,
                        cornerRadius: 8
                    }
                },

                scales: {
                    x: {
                        grid: {
                            display: false
                        },
                        ticks: {
                            color: '#000'
                        }
                    },
                    y: {
                        grid: {
                            color: 'rgba(148, 163, 184, 0.15)'
                        },
                        ticks: {
                            color: '#000'
                        }
                    }
                }
            }
        });
    """.formatted(labels, data));

        return chartDiv;
    }

    public Div ordersByStatusChart(
            long pending,
            long inProgress,
            long completed,
            long cancelled
    ) {
        Div chartDiv = new Div();

        chartDiv.setWidthFull();
        chartDiv.setMinHeight("320px");

        chartDiv.getStyle()
                .set("background-color", "white")
                .set("border", "1px solid #e5e7eb")
                .set("border-radius", "16px")
                .set("padding", "22px")
                .set("box-sizing", "border-box");

        chartDiv.getElement().executeJs("""
        const host = this;

        const values = [$0, $1, $2, $3];

        const labels = [
            'Pending',
            'In Progress',
            'Completed',
            'Cancelled'
        ];

        const colors = [
            '#1677FF',
            '#FFB81C',
            '#3DBB70',
            '#EF4444'
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
                pending,
                inProgress,
                completed,
                cancelled
        );

        return chartDiv;
    }

}
