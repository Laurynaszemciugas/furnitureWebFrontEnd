package com.example.demo;

import com.vaadin.flow.component.html.Div;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class chart {

    public Div ChartTest() {

        Div chartDiv = new Div();
        chartDiv.setWidthFull();
        chartDiv.setMaxHeight("480px");
        chartDiv.getStyle()
                .set("border-radius", "16px");

        chartDiv.getElement().executeJs("""
        this.innerHTML = '';

        const canvas = document.createElement('canvas');
        this.appendChild(canvas);

        const ctx = canvas.getContext('2d');

        // Gradient
        const gradient = ctx.createLinearGradient(0, 0, 0, 300);
        gradient.addColorStop(0, 'rgba(99, 102, 241, 0.9)');
        gradient.addColorStop(1, 'rgba(99, 102, 241, 0.2)');

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                datasets: [{
                    label: 'Monthly Gains (€)',
                    data: [1200, 1900, 3000, 2500, 2200, 3200],
                    backgroundColor: gradient,
                    borderColor: '#6366F1',
                    borderWidth: 2,
                    borderRadius: 10,
                    hoverBackgroundColor: '#818CF8'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,

                animation: {
                    duration: 1200,
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
                            color: '#000',
                            font: {
                                size: 12
                            }
                        }
                    },
                    y: {
                        grid: {
                            color: 'rgba(148, 163, 184, 0.1)'
                        },
                        ticks: {
                            color: '#000'
                        }
                    }
                }
            }
        });
    """);

        return chartDiv;
    }


}
