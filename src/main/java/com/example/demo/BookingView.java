package com.example.demo;

import com.example.demo.users.Booking;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("Booking")

public class BookingView extends VerticalLayout {

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField username = new TextField("Username");
    TextField address = new TextField("Home Address");
    NumberField phoneNumber = new NumberField("Phone Number");
    EmailField email = new EmailField("Email");
    DatePicker bookingDate = new DatePicker("Booking Date");
    TextField siteId = new TextField("Site ID");

    private Button bookButton = new Button("Book");
    private ArrayNode errorsLayout;

    public BookingView(){

        FormLayout formLayout = new FormLayout();
        VerticalLayout layout = new VerticalLayout();


        add(new H1("Booking form"));
        add(layout);

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
                    boolean emptyFields = Stream.of("name", "quantity", "snack")
                            .flatMap(prop -> binder.getBinding(prop).stream())
                            .anyMatch(binding -> binding.getField().isEmpty());
                    bookButton.setEnabled(!emptyFields);
                }

        );

        bookButton.addClickListener(click -> {
            try {

                Booking newBooking = new Booking();
                binder.writeBean(newBooking);
                //addOrder(newBooking); (3)
                binder.readBean(new Booking());
                System.out.println(newBooking);
            } catch (ValidationException e) {
                errorsLayout.add(String.valueOf(new Html(e.getValidationErrors().stream()
                        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
                        .collect(Collectors.joining("\n")))));
            }
        });
        return null;
    }


}
