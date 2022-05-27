package com.example.CovidGov.AdminBooking;


import com.example.CovidGov.apiTools;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class editBoookingView extends FormLayout {

    private Booking currentBooking;
    private final apiTools api = new apiTools();
    private final bookingsView bookingView;
    TextField customerId = new TextField("Customer Id");

    TextField testingSiteId = new TextField("Testing SiteId");
    TextField startTime = new TextField("Date");
    TextField notes = new TextField("Notes");

    Button save = new Button("Save");
    Button delete = new Button("Delete");


    public editBoookingView(bookingsView bookingsView, List<Booking> booking) {
        bookingView = bookingsView;

        addClassName("booking-form");
        customerId.setReadOnly(true);
        add(customerId,
                testingSiteId,
                startTime,
                notes,
                createButtonsLayout());
    }

    public void setBooking(Booking booking) {
        this.currentBooking = booking;
        System.out.println("sdfdsfdsfdsfdsfdsfdsfsfsd");
        System.out.println(booking.getTestingSite());
        customerId.setValue(booking.getCustomer().getId());
        testingSiteId.setValue("7fbd25ee-5b64-4720-b1f6-4f6d4731260e");
        startTime.setValue(booking.getStartTime());
        notes.setValue(booking.getNotes());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> validateAndSave(currentBooking));
        delete.addClickListener(event -> deleteEvent(currentBooking));

        return new HorizontalLayout(save, delete);
    }

    private void validateAndSave(Booking currentBooking) {
        System.out.println("currentBooking.getTestingSite()");
        System.out.println(currentBooking.getTestingSite().getId());
        apiTools.saveBooking(currentBooking);
        //binder.writeBean(this.currentBooking);
    }

    public void deleteEvent(Booking currentBooking) {
        apiTools.deleteBooking(currentBooking);
        bookingView.updateList();
    }


}
