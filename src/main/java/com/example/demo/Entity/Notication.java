package com.example.demo.Entity;

import com.example.demo.Enums.NotificationTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notication {

    private Long id;

    private String notificationImage;
    private String notificationName;
    private String message;
    private String messageHighLightedText;
    private NotificationTypes notificationType;

    // this can be person material order product ect ect just a refrence point so later could get the full item from rep
    private Long itemId;

    private boolean read;
    private LocalDateTime created;
    private User sender;
    private User receiver;

}
