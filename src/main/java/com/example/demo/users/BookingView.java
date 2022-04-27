package com.example.demo.users;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

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
        Button bookButton = new Button("Book");
        add(bookButton);
        bookButton.addClickListener(click -> {
        });

        layout.setSizeFull();
        layout.setAlignItems(Alignment.CENTER);

    }
}
