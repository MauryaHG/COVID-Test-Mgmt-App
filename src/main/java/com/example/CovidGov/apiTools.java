package com.example.CovidGov;

import com.example.CovidGov.AdminBooking.Booking;
import com.example.CovidGov.TestingSite.TestingSite;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class apiTools {

    //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v2";

    public static void saveContact(Booking contact) {

    }

    public static void deleteBooking(Booking booking) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://fit3077.com/api/v2/booking/" + booking.getId()))
                .setHeader("Authorization", "96DGTkJgdRHMwrP9NH7z76DTQJMpCL")
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

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(siteIdUrl + "?fields=bookings"))
                .setHeader("Authorization", "96DGTkJgdRHMwrP9NH7z76DTQJMpCL")
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
        //objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        TestingSite site =  objectMapper.readValue(response.body(), TestingSite.class);

        System.out.println(site);
        return site.getBookings();
    }
}
