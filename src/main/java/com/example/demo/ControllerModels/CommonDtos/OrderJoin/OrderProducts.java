package com.example.demo.ControllerModels.CommonDtos.OrderJoin;

import com.example.demo.ControllerModels.CommonDtos.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProducts {

    private Long id;
    private Product product;
    private Long amountOfProduct;
    private LocalDateTime created;
    private User user;

}
