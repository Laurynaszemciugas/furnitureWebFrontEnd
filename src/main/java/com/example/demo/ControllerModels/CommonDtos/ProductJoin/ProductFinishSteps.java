package com.example.demo.ControllerModels.CommonDtos.ProductJoin;


import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.ProductFinishStepStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductFinishSteps {


    private Long id = null;
    private Long stepId = 0L;
    private String stepName = "None";
    private String stepDescription = "None";
    @JsonIgnore
    private Product product = null;

    private ProductFinishStepStatus productFinishStepStatus;

    private User employee;

    private Long stepsNeeded;

    private Long stepsCompleted;





}
