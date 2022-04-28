package com.example.demo;


import com.example.demo.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Route("Login")
public class LoginView extends VerticalLayout {

    private final LoginOverlay loginOverlay = new LoginOverlay();
    private final LoginForm loginForm = new LoginForm();

    //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";

    public LoginView() throws Exception {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);


        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Covid Gov");
        i18nForm.setUsername("Username");
        i18nForm.setPassword("Password");
        i18nForm.setSubmit("Log in");
        i18nForm.setForgotPassword("Browse testing sites");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Wrong username or password");
        i18nErrorMessage.setMessage("Please check that your username and password are correct and try again.");
        i18n.setErrorMessage(i18nErrorMessage);


        loginForm.setI18n(i18n);
        add(loginForm);
        //add onclicklistener to login button to call authenticate method
        loginForm.addLoginListener(event -> authenticateLogin(event.getUsername(), event.getPassword()));

        loginForm.addForgotPasswordListener(event ->UI.getCurrent().getPage().setLocation("Browse"));

    }

    public void authenticateLogin(String username, String password){
        // To get a specific resource from the web service, extend the root URL by appending the resource type you are looking for.
        // For example: [root_url]/user will return a JSON array object containing all users.
        String usersUrl = rootUrl + "/user";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Create the request body. The password for each of the sample user objects that have been created for you are the same as their respective usernames.
        String jsonString = "{" +
                "\"userName\":\"" + username + "\"," +
                "\"password\":\"" + password + "\"" +
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //check response's status code to see if login info is valid or not
        assert response != null;
        if (response.statusCode() ==403) {
            loginForm.setError(true);
        }else if (response.statusCode() == 200){
            try {
                    ObjectMapper om = new ObjectMapper();
                    User currentUser = om.readValue(getUserData(username), User.class);
                VaadinSession session = VaadinSession.getCurrent() ;   // Fetch current instance of `VaadinSession` to use its key-value collection of attributes.
                session.setAttribute( User.class , currentUser ) ;
                System.out.println(currentUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Notification.show("Logged in");
            UI.getCurrent().getPage().setLocation("Booking");

        }else{
            Notification.show("error");
        }

    }
     public String getUserData(String username) throws Exception {

         String usersUrl = rootUrl + "/user";
         ObjectNode userNode = null;

         // This request is authenticated (API key attached in the Authorization header), and will return the JSON array object containing all users.
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


         // Error checking for this sample code. You can check the status code of your request, as part of performing error handling in your assignment.
         if (response.statusCode() != 200) {
             throw new Exception("Error in api call");
         }

         ObjectNode[] jsonNodes = new ObjectNode[0];
         try {
             jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }

         for (int i = 0; i < jsonNodes.length; i++) {
             ObjectNode userObject = jsonNodes[i];
             if (userObject.get("userName").textValue().equals(username)){
                 userNode = jsonNodes[i];

             }
         }
         System.out.println("Part 2\n----");
         System.out.println(request.uri());
         System.out.println("Response code: " + response.statusCode());
         System.out.println("Full JSON response: " + response.body());
         System.out.println(userNode);

         return userNode.toString();
     }

}
