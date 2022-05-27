package com.example.CovidGov.TestingSite;

import com.example.CovidGov.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Route("Status")
public class Status extends VerticalLayout {
    NumberField pinCode = new NumberField();
    Button checkButton = new Button("Check Status");

    public Status() {
        add(pinCode, checkButton);
        checkButton.addClickListener(event -> {
            checkStatus(pinCode.getValue());
        });

    }

    private void checkStatus(Double pinCode) {

        VaadinSession session = VaadinSession.getCurrent();
        User currentUser = session.getAttribute(User.class);
        String usersUrl = "https://fit3077.com/api/v2/booking/";
        Dotenv dotenv = Dotenv.load();
        String myApiKey = dotenv.get("API_KEY");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());


        ObjectNode[] jsonNodes = new ObjectNode[0];
        try {
            jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonNodes.length; i++) {
            ObjectNode bookingObj = jsonNodes[i];
            System.out.println(bookingObj);
            System.out.println(pinCode);
            System.out.println(bookingObj.get("smsPin"));
            Double checkPin = bookingObj.get("smsPin").asDouble();
            System.out.println(checkPin);
            if(checkPin.equals(pinCode)){
                Notification.show(String.valueOf(bookingObj.get("status")));
            }
        }
    }
}

