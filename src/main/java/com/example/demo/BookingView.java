package com.example.demo;

import com.example.demo.system.Booking;
import com.example.demo.system.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("Booking")
/**
 * creates booking page UI
 */
public class BookingView extends VerticalLayout {

    //Vaadin UI elements
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField username = new TextField("Username");
    TextField address = new TextField("Home Address");
    NumberField phoneNumber = new NumberField("Phone Number");
    EmailField email = new EmailField("Email");
    DatePicker bookingDate = new DatePicker("Booking Date");
    TextField siteId = new TextField("Site ID");


    //retrieve api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    private String notes = "test book";
    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";

    private final Button bookButton = new Button("Book");

    /**
     * add UI elements to page
     */
    public BookingView(){

        FormLayout formLayout = new FormLayout();
        VerticalLayout layout = new VerticalLayout();

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Select one");
        checkboxGroup.setItems("On site testing", "Home Testing");

        checkboxGroup.addValueChangeListener(event -> notes = event.getValue().toString());

        add(new H1("Booking form"));
        add(layout);
        layout.add(checkboxGroup);
        layout.add(formLayout);
        formLayout.add(firstName, lastName);
        formLayout.add(username);
        formLayout.add(address);
        formLayout.add(phoneNumber, email);
        formLayout.add(bookingDate, siteId);

        TextField taskField = new TextField();

        add(bookButton);
        bookButton.addClickListener(click -> {
        });
        layout.setSizeFull();
        layout.setAlignItems(Alignment.CENTER);

        buildForm();
    }

    /**
     * get user input and bind date to booking class object
     */
    private Component buildForm(){

        Binder<Booking> binder = new Binder<>(Booking.class);
        binder.forField(firstName)
                .asRequired("Name is required")
                .bind(Booking::getAdditionalInfo, Booking::setAdditionalInfo);

        binder.forField(lastName)
                .asRequired("Name is required")
                .bind(Booking::getAdditionalInfo, Booking::setAdditionalInfo);

        binder.forField(bookingDate)
                .asRequired("date is required")
                .bind(Booking::getStartTime, Booking::setStartTime);

        binder.forField(siteId)
                .asRequired("site ID is required")
                .bind(Booking::getTestingSiteId, Booking::setTestingSiteId);

        binder.readBean(new Booking());

        binder.addStatusChangeListener(status -> {
                    boolean emptyFields = Stream.of("firstName", "lastName", "bookingDate")
                            .flatMap(prop -> binder.getBinding(prop).stream())
                            .anyMatch(binding -> binding.getField().isEmpty());
                    bookButton.setEnabled(!emptyFields);
                }

        );

        bookButton.addClickListener(click -> {
            try {

                Booking newBooking = new Booking();
                binder.writeBean(newBooking);
                addBooking(newBooking);
                binder.readBean(new Booking());
                System.out.println(newBooking);
            } catch (ValidationException e) {
                add(String.valueOf(new Html(e.getValidationErrors().stream()
                        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
                        .collect(Collectors.joining("\n")))));
            }

        });
        return null;
    }
    /**
     * sends POST request to webs service to add new booking to the database
     * @param newBooking booking object created
     */
    private void addBooking(Booking newBooking) {
        VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of `VaadinSession` to use its key-value collection of attributes.
        User currentUser = session.getAttribute(User.class);

        String userID = currentUser.id;
        String testingSiteId = newBooking.getTestingSiteId();
        LocalDate startTime = newBooking.getStartTime();




        ObjectMapper mapper = new ObjectMapper();

        // create a JSON object
        ObjectNode booking = mapper.createObjectNode();
        booking.put("customerId", userID);
        booking.put("testingSiteId", testingSiteId);
        booking.put("startTime", startTime.toString());
        booking.put("notes", notes);
        //create JSON object for additional data
        ObjectNode additionalInfo = mapper.createObjectNode();
        additionalInfo.put("QRcode","Dummy QR");
        booking.set("additionalInfo", additionalInfo);

        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //POST request
        String userBookingUrl = rootUrl + "/booking";
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        HttpRequest request = HttpRequest.newBuilder(URI.create(userBookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Notification.show(("Booking complete. PIN/QR sent to phone"));
    }

}
