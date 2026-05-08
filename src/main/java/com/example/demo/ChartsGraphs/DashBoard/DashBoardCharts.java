package com.example.demo.ChartsGraphs.DashBoard;

import com.example.demo.ControllerModels.DashBoard.DashBoardGraphData;
import com.vaadin.flow.component.html.Div;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardCharts {

    public Div ChartTest(List<DashBoardGraphData> list) {

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


}
