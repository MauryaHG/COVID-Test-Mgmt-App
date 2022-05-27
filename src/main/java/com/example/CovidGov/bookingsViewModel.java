package com.example.CovidGov;

import com.example.CovidGov.AdminBooking.Booking;
import com.example.CovidGov.TestingSite.TestingSite;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.VaadinSession;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class bookingsViewModel {

    private static String testSiteId;

    //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");
    VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of VaadinSession to use its key-value collection of attributes.
    User currentUser = session.getAttribute(User.class);
    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v2";

    public void saveBooking(Booking booking, String customerId, String testingSiteId, String startTime, String notes) {
        System.out.println();
        System.out.println(notes);
        String jsonString = "{" +
                "\"customerId\": \"" + customerId + "\"," +
                "\"testingSiteId\": \"" + testingSiteId + "\"," +
                "\"startTime\": \"" + startTime + "\"," +
                "\"notes\": \"" + notes + "\"" +
                "}";
        System.out.println(jsonString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://fit3077.com/api/v2/booking/" + booking.getId()))
                .header("accept", "application/json")
                .header("Authorization", myApiKey)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void deleteBooking(Booking booking) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://fit3077.com/api/v2/booking/" + booking.getId()))
                .setHeader("Authorization", myApiKey)
                .DELETE()
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Booking> getBookings(String testingSiteId) throws JsonProcessingException {
        String siteIdUrl = rootUrl + "/testing-site/"+ testingSiteId;
        testSiteId = testingSiteId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(siteIdUrl + "?fields=bookings"))
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

        ObjectMapper objectMapper = new ObjectMapper();
        TestingSite site =  objectMapper.readValue(response.body(), TestingSite.class);


        for (Booking element : site.getBookings()) {
            element.setTestingSite(site);
        }
        return site.getBookings();
    }
}
