package com.example.demo.ControllerModels.User;

import com.example.demo.Enums.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonalPrefrences {



    private DateFormat dateFormat;
    private TimeZone timeZone;
    private Language language;
    private PageStart pageStart;
    private boolean activeNotification;


}
