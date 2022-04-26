package com.example.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

@Route("")
public class main extends LoginOverlay {

    private final LoginOverlay loginOverlay = new LoginOverlay();

    /*
       NOTE: In order to access the web service, you will need to include your API key in the Authorization header of all requests you make.
       Your personal API key can be obtained here: https://fit3077.com
      */
    Dotenv dotenv = Dotenv.load();
    private String myApiKey = dotenv.get("API_KEY");

    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";



    public main(String[] args) throws Exception {
        // To get a specific resource from the web service, extend the root URL by appending the resource type you are looking for.
        // For example: [root_url]/user will return a JSON array object containing all users.
        String usersUrl = rootUrl + "/user";

        loginOverlay.setTitle("Covid Gov");
        loginOverlay.setDescription("");
        loginOverlay.setOpened(true);
        loginOverlay.setForgotPasswordButtonVisible(false);

        loginOverlay.addLoginListener(event -> {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(usersUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Create the request body. The password for each of the sample user objects that have been created for you are the same as their respective usernames.
            String jsonString = "{" +
                    "\"userName\":\"" + event.getUsername() + "\"," +
                    "\"password\":\"" + event.getPassword() + "\"" +
                    "}";

            // Note the POST() method being used here, and the request body is supplied to it.
            // A request body needs to be supplied to this endpoint, otherwise a 400 Bad Request error will be returned.
            String usersLoginUrl = usersUrl + "/login";
            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder(URI.create(usersLoginUrl + "?jwt=true")) // Return a JWT so we can use it in Part 5 later.
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                    .build();

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Part 4\n----");
            System.out.println(request.uri());
            if (response.statusCode() ==403) {
                loginOverlay.setError(true);
            }else if (response.statusCode() == 200){
                Notification.show("Logged in");
            }else{
                Notification.show("error");
            }
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Full JSON response: " + response.body()); // The JWT token that has just been issued will be returned since we set ?jwt=true.
            System.out.println("----\n\n");
        });

    }

}
