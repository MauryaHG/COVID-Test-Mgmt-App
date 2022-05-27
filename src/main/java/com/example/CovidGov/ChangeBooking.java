package com.example.CovidGov;

import com.example.CovidGov.AdminBooking.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

    private static final String rootUrl = "https://fit3077.com/api/v2";
    private List<com.example.CovidGov.AdminBooking.Booking> bookingList = new ArrayList<Booking>();

    Grid<com.example.CovidGov.AdminBooking.Booking> grid = new Grid<>(Booking.class);
    private FormLayout formLayout = new FormLayout();
    TextField testingSiteId = new TextField("Testing SiteId");
    TextField startTime = new TextField("Date");

    VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of `VaadinSession` to use its key-value collection of attributes.
    User currentUser = session.getAttribute(User.class);
    String UserId = currentUser.getId();

    private Button save = new Button("Save");
    private Booking currentBooking;


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
        grid.asSingleSelect().addValueChangeListener(event ->
                editBooking(event.getValue()));
        add(formLayout);
        formLayout.add(testingSiteId,
                startTime,
                createButtonsLayout());
    }

    private void configureGrid() {
        grid.addClassNames("user-bookings");
        grid.setSizeFull();
        grid.setColumns("id","testingSite", "startTime", "status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    public void editBooking(Booking booking) {
        System.out.println("booking");
        System.out.println(booking.getTestingSite());
        if (booking == null) {
            closeEditor();
        } else {
            currentBooking = booking;
            testingSiteId.setValue("7fbd25ee-5b64-4720-b1f6-4f6d4731260e");
            startTime.setValue(booking.getStartTime());
            formLayout.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        formLayout.setVisible(false);
        removeClassName("editing");
    }


    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        ObjectMapper emptyarray = new ObjectMapper();
        save.addClickListener(event -> saveBooking(currentBooking, UserId, testingSiteId.getValue(), startTime.getValue(), currentBooking.getStatus(), "", emptyarray));

        return new HorizontalLayout(save);
    }

    public void saveBooking(Booking booking, String customerId, String testingSiteId, String startTime, String status, String notes, ObjectMapper additionalInfo){
        String jsonString = "{" +
                "\"customerId\": \"" + customerId + "\"," +
                "\"testingSiteId\": \"" + testingSiteId + "\"," +
                "\"startTime\": \"" + startTime + "\"" +
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
}



