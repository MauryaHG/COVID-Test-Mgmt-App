package com.example.CovidGov.AdminBooking;


import com.example.CovidGov.User;
import com.example.CovidGov.apiTools;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;

public class editBookingsView extends FormLayout {

    private Booking currentBooking;
    private final apiTools api = new apiTools();
    private final bookingsView bookingView;

    VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of VaadinSession to use its key-value collection of attributes.
    User currentUser = session.getAttribute(User.class);

    TextField customerId = new TextField("Customer Id");

    TextField testingSiteId = new TextField("Testing SiteId");
    TextField startTime = new TextField("Date");
    TextField notes = new TextField("Notes");

    Button save = new Button("Save");
    Button delete = new Button("Delete");


    public editBookingsView(bookingsView bookingsView, List<Booking> booking) {
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
        System.out.println(booking.getTestingSite());
        customerId.setValue(booking.getCustomer().getId());
        testingSiteId.setValue(currentUser.getAdditionalInfo().getWorkingSite());
        startTime.setValue(booking.getStartTime());
        notes.setValue(booking.getNotes());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> validateAndSave(currentBooking, customerId.getValue(), testingSiteId.getValue(), startTime.getValue(), notes.getValue()));
        delete.addClickListener(event -> deleteEvent(currentBooking));

        return new HorizontalLayout(save, delete);
    }

    private void validateAndSave(Booking currentBooking, String customerId, String testingSiteId, String startTime, String notes) {
        api.saveBooking(currentBooking, customerId, testingSiteId, startTime, notes);
        Notification.show(("Booking updated."));
    }



    public void deleteEvent(Booking currentBooking) {
        api.deleteBooking(currentBooking);
        bookingView.updateList();
    }


}
