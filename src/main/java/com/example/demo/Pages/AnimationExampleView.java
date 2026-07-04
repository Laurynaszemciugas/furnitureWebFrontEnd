package com.example.demo.Pages;

import com.example.demo.MainLayout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;

@Route(value = "va", layout = MainLayout.class)
public class AnimationExampleView extends Div {

    public AnimationExampleView() {
        addClassName("animation-page");

        add(
                cardGrid(),
                smoothPanel()
        );
    }

    private Component loadingOverlay() {
        Div overlay = new Div();
        overlay.addClassName("loading-overlay");

        Div loader = new Div();
        loader.addClassName("modern-loader");

        Span text = new Span("Loading dashboard...");
        text.addClassName("loading-text");

        overlay.add(loader, text);

        return overlay;
    }

    private Component header() {
        Div header = new Div();
        header.addClassName("page-header");

        Span badge = new Span("Vaadin animations");
        badge.addClassName("badge");

        H1 title = new H1("Smooth loading and movement");
        Paragraph text = new Paragraph("CSS-only animation examples for a modern admin panel.");

        Button button = new Button("Create order");
        button.setIcon(new Icon(VaadinIcon.PLUS));
        button.addClassName("animated-button");

        header.add(badge, title, text, button);

        return header;
    }

    private Component skeletonSection() {
        Div wrapper = new Div();
        wrapper.addClassName("skeleton-card");

        Div top = new Div();
        top.addClassName("skeleton-top");

        Div avatar = new Div();
        avatar.addClassName("skeleton-avatar");

        Div lines = new Div();
        lines.addClassName("skeleton-lines");

        Div line1 = new Div();
        line1.addClassName("skeleton-line");
        line1.addClassName("w-70");

        Div line2 = new Div();
        line2.addClassName("skeleton-line");
        line2.addClassName("w-40");

        lines.add(line1, line2);
        top.add(avatar, lines);

        Div bigLine = new Div();
        bigLine.addClassName("skeleton-line");
        bigLine.addClassName("w-100");

        Div smallLine = new Div();
        smallLine.addClassName("skeleton-line");
        smallLine.addClassName("w-85");

        wrapper.add(top, bigLine, smallLine);

        return wrapper;
    }

    private Component cardGrid() {
        Div grid = new Div();
        grid.addClassName("animated-grid");

        grid.add(
                metricCard("Orders", "128", VaadinIcon.CART),
                metricCard("Revenue", "€12.4K", VaadinIcon.EURO),
                metricCard("Materials", "43", VaadinIcon.ARCHIVE),
                metricCard("Employees", "18", VaadinIcon.USERS)
        );

        return grid;
    }

    private Component metricCard(String title, String value, VaadinIcon icon) {
        Div card = new Div();
        card.addClassName("animated-card");

        Div iconBox = new Div();
        iconBox.addClassName("card-icon");
        iconBox.add(new Icon(icon));

        Span titleSpan = new Span(title);
        titleSpan.addClassName("card-title");

        H2 valueText = new H2(value);

        card.add(iconBox, titleSpan, valueText);

        return card;
    }

    private Component smoothPanel() {
        Div panel = new Div();
       // panel.addClassName("smooth-panel");

        Div left = new Div();
        left.addClassName("panel-text");

        Span badge = new Span("Hover me");
        badge.addClassName("badge");

        H2 title = new H2("Smooth card movement");
        Paragraph text = new Paragraph(
                "This panel moves using transform, so it feels much smoother than changing margin or position."
        );

        left.add(badge, title, text);

        Div fakeChart = new Div();
        fakeChart.addClassName("fake-chart");

        fakeChart.add(
                bar("40%"),
                bar("70%"),
                bar("55%"),
                bar("85%"),
                bar("65%")
        );

        panel.add(left, fakeChart);

        return panel;
    }

    private Component bar(String height) {
        Div bar = new Div();
        bar.addClassName("bar");
        bar.getStyle().set("--height", height);
        return bar;
    }
}