package com.example.demo.ControllerModels.CommonDtos;


import com.example.demo.Enums.DateFormat;
import com.example.demo.Enums.Language;
import com.example.demo.Enums.TimeZone;
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

    private User user;


}
