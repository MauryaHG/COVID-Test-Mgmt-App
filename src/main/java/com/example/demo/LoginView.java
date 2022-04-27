package com.example.demo;


import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

@Route("")
public class LoginView extends LoginOverlay {

    private final LoginOverlay loginOverlay = new LoginOverlay();

   //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";



    public LoginView() throws Exception {

        //create login form using vaadin components
        loginOverlay.setTitle("Covid Gov");
        loginOverlay.setDescription("");
        loginOverlay.setOpened(true);
        loginOverlay.setForgotPasswordButtonVisible(false);

        //add onclicklistener to login button to call authenticate method
        loginOverlay.addLoginListener(event -> authenticateLogin(event.getUsername(), event.getPassword()));



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
            loginOverlay.setError(true);
        }else if (response.statusCode() == 200){
            Notification.show("Logged in");
        }else{
            Notification.show("error");
        }

    }

}
