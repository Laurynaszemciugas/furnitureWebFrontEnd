package com.example.demo.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActionTracker {

    private Long id;
    private String actionName;

    private User whoMadeIt;

    private LocalDateTime created;

    private String name;

}
