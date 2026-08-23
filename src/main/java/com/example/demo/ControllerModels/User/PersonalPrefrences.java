package com.example.demo.ControllerModels.User;

import com.example.demo.Enums.DateFormat;
import com.example.demo.Enums.Language;
import com.example.demo.Enums.TimeZone;
import com.example.demo.Enums.Verification;
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
    private boolean activeNotification;


}
