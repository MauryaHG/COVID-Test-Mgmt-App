package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class main extends VerticalLayout {

    public main(){
        add(new H1(("Welcome to covid gov!")));

        var loginButton = new Button("Login");
        var username = new TextField();
        var password = new TextField();

        add(username);
        add(password);
        add(new HorizontalLayout(loginButton));

        loginButton.addClickListener(event -> {

        });
    }
}
