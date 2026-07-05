package com.example.demo.Common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class Paganation {

    List<Button> buttonList = new ArrayList<>();
    int currentPage = 1;
    int totalPages = 15;

    private IntConsumer onPageChange;


    public void setOnPageChange(IntConsumer onPageChange) {
        this.onPageChange = onPageChange;
    }

    public Button buttonCrafter(int number){



        Button button = new Button(String.valueOf(number));
        button.setVisible(false);
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
//        button.addClassName("pagination-button");
        button.addClassName("animated-card");

        button.addClickListener(e->{
            currentPage = Integer.parseInt(button.getText());
            paganationButtons();


            onPageChange.accept(currentPage);

        });


        buttonList.add(button);








        return  button;

    }

    public HorizontalLayout buttonHolder(int size){


        // make this class know the total pages long
        buttonList.clear();
        totalPages = size;

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);


        for(int i = 1; i <= totalPages; i++){
            horizontalLayout.add(buttonCrafter(i));


        }


        paganationButtons();



        return  horizontalLayout;


    }


    public void paganationButtons(){

        for(var s : buttonList){

            s.setVisible(false);
            s.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }


        for(int i = 1; i <= totalPages; i++){

            boolean good = i == 1 || i == totalPages || Math.abs(i-currentPage) <=1;

            if(good){

                if(i == currentPage){
                    buttonList.get(i-1).addThemeVariants(ButtonVariant.PRIMARY);
                }
                buttonList.get(i-1).setVisible(true);
            }


        }

    }

    public void updateUIFromExternal(int page) {
        this.currentPage = page;
        if(!buttonList.isEmpty()){
            paganationButtons();
        }
    }

}
