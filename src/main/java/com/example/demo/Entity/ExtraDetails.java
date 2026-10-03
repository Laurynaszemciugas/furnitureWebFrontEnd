package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtraDetails {

    private Long id;
    private String specName;
    private String specDescription;
    @JsonIgnore
    private Product product;

    @JsonIgnore
    private User user;

    private LocalDateTime created;

}
