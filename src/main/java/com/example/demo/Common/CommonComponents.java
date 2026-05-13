package com.example.demo.Common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CommonComponents {



    // components like icons images buttonns spans ect

    public Image imageCrafter(String imageUrl, String width, String height, String borderRadius){
        Image image = new Image(imageUrl, "image error");
        image.setWidth(width);
        image.setHeight(height);
        image.getStyle().set("border-radius",borderRadius);
        return image;
    }

    public Div spaceFiller(){
        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

        return spacer;
    }



    public TextField textFieldCrafter(String placeHolder, String topText){

        TextField textField = new TextField(topText);
        textField.setPlaceholder(placeHolder);

        return  textField;

    }


    public Icon iconCrafter(VaadinIcon chosenIcon, String size, String color){

        Icon icon = chosenIcon.create();
        icon.setSize(size);
        icon.setColor(color);

        return icon;

    }

    public Span spanCrafter(String text, String addClassName){
        Span span = new Span(text);
        span.addClassName(addClassName);
        span.getStyle()
                .set("white-space", "nowrap")
                .set("overflow", "hidden")
                .set("text-overflow", "ellipsis")
                .set("display", "block")
                .set("max-width", "100%");
        return span;
    }

    public Span spanCrafterWordNoHide(String text, String addClassName){
        Span span = new Span(text);
        span.addClassName(addClassName);

        span.getStyle()
                .set("white-space", "normal")
                .set("word-break", "break-word")
                .set("display", "block")
                .set("max-width", "100%");

        return span;
    }

    public Button buttonThemeAndIcon(String text, String navigate , ButtonVariant buttonVariant, VaadinIcon icon, String iconColor){
        Button button = new Button(text, e-> UI.getCurrent().navigate(navigate));
        button.addThemeVariants(buttonVariant);
        button.setIcon(iconCrafter(icon,"25px",iconColor));

        return button;
    }

    public Button buttonThemeAndIconNoNavigate(String text , ButtonVariant buttonVariant, VaadinIcon icon, String iconColor){
        Button button = new Button(text);
        button.addThemeVariants(buttonVariant);
        button.setIcon(iconCrafter(icon,"25px",iconColor));

        return button;
    }

    public Button normalThemeButton(String text, String navigate , ButtonVariant buttonVariant){
        Button button = new Button(text, e-> UI.getCurrent().navigate(navigate));
        button.addThemeVariants(buttonVariant);

        return button;
    }

    public Button normalThemeButtonNoNavigate(String text , ButtonVariant buttonVariant){
        Button button = new Button(text);
        button.addThemeVariants(buttonVariant);

        return button;
    }

    public Button normalButtonNoNavigate(String text , String addclass){
        Button button = new Button(text);
        button.addClassName(addclass);

        return button;
    }

    public Button normalThemeButtonWithSize(String text, String navigate , ButtonVariant buttonVariant, String width, String height){
        Button button = new Button(text, e-> UI.getCurrent().navigate(navigate));
        button.addThemeVariants(buttonVariant);


        button.setWidth(width);
        button.setHeight(height);

        return button;
    }

    public Button normalButtons(String navigate, String text, VaadinIcon icon){
        Button button = new Button(text);
        button.addClassName("clean-btn");
        button.setIcon(iconCrafter(icon,"30px","white"));

        button.addClickListener(e->{
            UI.getCurrent().navigate(navigate);
        });



        return button;

    }

    public Button smallIconButtons(String navigate, VaadinIcon icon, String color){
        Button button = new Button();
        button.addClassName("clean-btn");
        button.setIcon(iconCrafter(icon,"40px",color));
        return button;

    }

    public HorizontalLayout biefPageExplanation(String title){
        HorizontalLayout explanation = new HorizontalLayout(
                spanCrafterWordNoHide(title,"stat-value")

        );

        return  explanation;
    }





// layouts for components


    public HorizontalLayout doubleValueRow(Component component1, Component component2){
        HorizontalLayout valueRow = new HorizontalLayout(component1, component2);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");

        valueRow.setWidthFull();

        return valueRow;
    }

    public HorizontalLayout tripleValueRow(Component component1, Component component2, Component component3){
        HorizontalLayout valueRow = new HorizontalLayout(component1, component2, component3);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");

        valueRow.setWidthFull();

        return valueRow;
    }





    // simple no data layout

    public VerticalLayout noDataFound(){

        Image image = new Image("dataNotFound.png","s");
        image.setWidth("150px");
        image.setHeight("200px");

        VerticalLayout emptyView = new VerticalLayout(spanCrafterWordNoHide("No data found","stat-value"),image);
        emptyView.setAlignItems(FlexComponent.Alignment.CENTER);
        emptyView.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        emptyView.setSizeFull();


        return  emptyView;
    }







}
