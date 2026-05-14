package com.example.demo.ControllerModels.Common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListExtraDetailsGrid {

    private Long id;
    private TextField specName;
    private TextArea specDescription;

}
