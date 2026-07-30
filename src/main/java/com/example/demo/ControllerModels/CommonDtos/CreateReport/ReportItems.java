package com.example.demo.ControllerModels.CommonDtos.CreateReport;

import com.example.demo.Enums.Widget;
import com.example.demo.Enums.Widths;
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

    private Widths Width;

    private Report report;

}
