package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.AccountStatus;
import com.example.demo.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate bannedTill;
    private LocalDateTime created;

    private String fullName;
    private String imageUrl;
    private String phoneNumber;
}
