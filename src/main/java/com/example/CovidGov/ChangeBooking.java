package com.example.CovidGov;

import com.example.CovidGov.AdminBooking.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Route(value = " ChangeBooking")
public class ChangeBooking extends VerticalLayout {
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    private static final String rootUrl = "https://fit3077.com/api/v1";
    private List<com.example.CovidGov.AdminBooking.Booking> bookingList = new ArrayList<Booking>();

    Grid<com.example.CovidGov.AdminBooking.Booking> grid = new Grid<>(Booking.class);
    TextField filterText = new TextField();

    VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of `VaadinSession` to use its key-value collection of attributes.
    User currentUser = session.getAttribute(User.class);
    String UserId = currentUser.getId();


    public ChangeBooking() throws Exception{
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
        bookingList = jsonNode.getBookings();



        System.out.println(currentUser);
        System.out.println(currentUser.getId());
        System.out.println(jsonNode);
        System.out.println(bookingList);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(grid);
        grid.setItems(bookingList);
    }

    private void configureGrid() {
        grid.addClassNames("user-bookings");
        grid.setSizeFull();
        grid.setColumns("id","testingSite", "startTime", "status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }
}



