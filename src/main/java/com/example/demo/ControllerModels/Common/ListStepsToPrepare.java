package com.example.demo.ControllerModels.Common;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListStepsToPrepare {

    private Long id;
    private TextField stepName;
    private TextArea stepDescription;

}
