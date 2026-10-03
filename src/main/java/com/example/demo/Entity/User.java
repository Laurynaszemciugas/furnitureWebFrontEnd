package com.example.demo.Entity;

import com.example.demo.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    private String googleId;

    private DateFormat dateFormat;
    private TimeZone timeZone;
    private Language language;
    private Verification verification;

    private LocalDateTime lastLogin;


    private String ip;


    private String fullName;
    private String imageUrl;
    private byte[] imageData;

    private UserSettings userSettingsList;


}
