package com.example.demo;


import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Setter
public class ClientUIUpdater {

    private Consumer<Boolean> updateCart;
    private Consumer<Boolean> updateWhitelist;



    public void updateCart(Boolean value) {
        updateCart.accept(value);
    }

    public void updateWhitelist(Boolean value) {
        updateWhitelist.accept(value);
    }
}