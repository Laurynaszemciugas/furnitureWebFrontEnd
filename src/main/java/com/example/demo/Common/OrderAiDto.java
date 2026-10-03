package com.example.demo.Common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderAiDto {



    private String orderNote = "None";
    private LocalDateTime estimatedDueDate = LocalDateTime.now();
    private String phoneNumber = "None";
    private String billingAddress = "None";
    private String orderCreatedByName = "None";
    private String orderCreatedByGmail = "None";



    }
