package com.example.CovidGov.AdminBooking;


import com.example.CovidGov.apiTools;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;

public class BookingFormv2 extends FormLayout {

    private Booking currentBooking;
    private apiTools api = new apiTools();
    TextField customerId = new TextField("Customer Id");

    TextField testingSiteId = new TextField("Testing SiteId");
    TextField startTime = new TextField("Date");
    TextField notes = new TextField("Notes");
    ComboBox<Booking> bookings = new ComboBox<>("testing Site");
    Binder<Booking> binder = new BeanValidationBinder<>(Booking.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public BookingFormv2(List<Booking> booking) {
        addClassName("booking-form");
        customerId.setReadOnly(true);
        binder.bindInstanceFields(this);

        bookings.setItems(booking);
        bookings.setItemLabelGenerator(Booking::getId);
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
        testingSiteId.setValue("7fbd25ee-5b64-4720-b1f6-4f6d4731260e");
        startTime.setValue(booking.getStartTime());
        notes.setValue(booking.getNotes());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(deleteEvent(currentBooking)));
        close.addClickListener(event -> fireEvent(CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private ComponentEvent<?> CloseEvent(BookingFormv2 bookingFormv2) {
        return null;
    }


    private void validateAndSave() {
        try {
            binder.writeBean(currentBooking);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public ComponentEvent<?> deleteEvent(Booking currentBooking) {
        apiTools.deleteBooking(currentBooking);
        return null;
    }


}
