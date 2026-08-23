package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String gmail;
    private String name;
    private String lastName;
    private String password;
    private String recoveryPin;
    private Role role;
    private AccountStatus accountStatus;
    private LocalDateTime bannedTill;
    private LocalDateTime created;

    private String bio;

    private String phoneNumber;

    private DateFormat dateFormat;
    private TimeZone timeZone;
    private Language language;
    private Verification verification;

    private LocalDateTime lastLogin;

    private boolean receiveGmail;

    private String ip;


    private String fullName;
    private String imageUrl;

    private UserSettings userSettingsList;


}
