package com.example.demo.Entity.Authenfication;

import com.example.demo.Entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GmailAuth {

    private Long id;

    private String oneTimeCode;

    private User user;

    private LocalDateTime created;

    private LocalDateTime expiration;


}
