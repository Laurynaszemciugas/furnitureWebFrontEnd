package com.example.demo;

import com.example.demo.Pages.Login.LoginPage;
import com.example.demo.Services.LoginService.LoginService;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.juli.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;

@SpringBootApplication
@StyleSheet(Lumo.STYLESHEET)
@Push
public class Demo1Application implements AppShellConfigurator {

    public static void main(String[] args) {




        SpringApplication.run(Demo1Application.class, args);
    }
}