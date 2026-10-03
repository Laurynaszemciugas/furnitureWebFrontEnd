package com.example.demo.Entity;


import com.example.demo.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {


    private Long id;

    private DateFormat dateFormat;

    private TimeZone timeZone;

    private Language language;

    private boolean receiveGmail;

    private String theme;

    private String accent;

    private String sidebarSize;

    private OrderProcessing orderProcessing;

    private PageStart pageStart;

    private User user;


}
