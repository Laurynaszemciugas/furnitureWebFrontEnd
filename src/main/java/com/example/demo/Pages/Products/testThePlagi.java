package com.example.demo.Pages.Products;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class testThePlagi {

    List<Button> buttonList = new ArrayList<>();
    int currentPage = 1;
    int totalPages = 15;


    public Button buttonCrafter(int number){



        Button button = new Button(String.valueOf(number));
        button.setVisible(false);
        button.addClassName("pagination-button");

        button.addClickListener(e->{
            currentPage = Integer.parseInt(button.getText());
            paganationButtons();
        });


        buttonList.add(button);








        return  button;

    }

    public HorizontalLayout buttonHolder(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();


        for(int i = 1; i <= totalPages; i++){
            horizontalLayout.add(buttonCrafter(i));

        }


        paganationButtons();



        return  horizontalLayout;


    }


    public void paganationButtons(){

        for(var s : buttonList){

            s.setVisible(false);
            s.removeClassName("pagination-button-active");
        }


        for(int i = 1; i <= totalPages; i++){

            boolean good = i == 1 || i == totalPages || Math.abs(i-currentPage) <=2;

            if(good){

                if(i == currentPage){
                    buttonList.get(i-1).addClassName("pagination-button-active");
                }
                buttonList.get(i-1).setVisible(true);
            }


        }

    }

}
