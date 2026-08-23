package com.example.demo.ControllerModels.User;

import com.example.demo.Enums.Verification;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountOverview {


    private LocalDateTime created;
    private String gmail;
    private Verification verification;
    private LocalDateTime bannedTill;
    private LocalDateTime lastLogin;
    private String ip;


}
