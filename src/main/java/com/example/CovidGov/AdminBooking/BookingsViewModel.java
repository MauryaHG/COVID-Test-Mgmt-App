package com.example.CovidGov.AdminBooking;

import com.example.CovidGov.TestingSite.TestingSite;
import com.example.CovidGov.User;
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

/**
 * Gives data between model and view
 */
public class BookingsViewModel {

    private static String testSiteId;

    //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");
    VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of VaadinSession to use its key-value collection of attributes.
    User currentUser = session.getAttribute(User.class);
    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v2";

    /**
     * get user input and create objects to save to api using PATCH request
     * @param booking booking object
     * @param customerId id of the booking customer
     * @param testingSiteId id of the testing site the booking needs to be added to
     * @param startTime start time of test
     * @param notes extra notes
     */
    public void saveBooking(BookingModel booking, String customerId, String testingSiteId, String startTime, String notes) {
        // create jsonString for new booking
        String jsonString = "{" +
                "\"customerId\": \"" + customerId + "\"," +
                "\"testingSiteId\": \"" + testingSiteId + "\"," +
                "\"startTime\": \"" + startTime + "\"," +
                "\"status\": \"" + booking.getStatus() + "\"," +
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
    /**
     * delete a booking from api using booking id and DELETE request
     * @param booking booking to be deleted
     */
    public  void deleteBooking(BookingModel booking) {
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

    /**
     * send GET request to web service to get all bookings of relevant testing site
     * then data is sent back so new booking objects can be created.
     * @param testingSiteId testing site id of admin
     */
    public List<BookingModel> getBookings(String testingSiteId) throws JsonProcessingException {
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

        //create testing site objects
        ObjectMapper objectMapper = new ObjectMapper();
        TestingSite site =  objectMapper.readValue(response.body(), TestingSite.class);

        //add testing site objects to the bookings object
        for (BookingModel element : site.getBookings()) {
            element.setTestingSite(site);
        }
        return site.getBookings();
    }
    /**
     * send GET request to web service to get all bookings of relevant testing site
     * then data is sent back so new booking objects can be created.
     * @param UserId User id of admin
     */
    public List<BookingModel> getBookingsWithUserId(String UserId) throws JsonProcessingException {
        String UserIdUrl = rootUrl + "/user/"+ UserId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(UserIdUrl + "?fields=bookings"))
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

        User jsonNode = new ObjectMapper().readValue(response.body(), User.class);
        List<BookingModel> bookingList = jsonNode.getBookings();

        return bookingList;
    }
}
