package com.example.demo.ControllerModels.CommonDtos.CreateReport;

import com.example.demo.Enums.Widget;
import com.example.demo.Enums.Widths;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportItems {


    private Long id;

    private String customId;

    private Widget widget;

    private Widths width;
    @JsonIgnore
    private Report report;

}
