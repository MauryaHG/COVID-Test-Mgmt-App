package com.example.demo.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import elemental.json.impl.JreJsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;


public class User {

    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");
    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";

    /*
    private String id;
    private String givenName;
    private String  familyName;
    private String userName;
    private String phoneNumber;
    private Boolean isCustomer;
    private Boolean isReceptionist;
    private Boolean isHealthcareWorker;
    */

    public User(String username, String password) {

        try {
            this.getUserInfo( username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private String getUserInfo(String username, String password) throws Exception {

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

        return userNode.toString();
    }
}
