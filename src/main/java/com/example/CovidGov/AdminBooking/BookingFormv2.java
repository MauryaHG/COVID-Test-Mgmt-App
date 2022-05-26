package com.example.CovidGov.AdminBooking;


import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class BookingFormv2 extends FormLayout {

    private Booking currentBooking;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DatePicker date = new DatePicker("Date");
    TextField site = new TextField("Testing-Site");
    ComboBox<Booking> bookings = new ComboBox<>("testing Site");
    Binder<Booking> binder = new BeanValidationBinder<>(Booking.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public BookingFormv2(List<Booking> booking) {
        addClassName("booking-form");
        //binder.bindInstanceFields(this);

        bookings.setItems(booking);
        bookings.setItemLabelGenerator(Booking::getId);
        add(firstName,
                lastName,
                date,
                site,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, currentBooking)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setBooking(Booking booking) {
        this.currentBooking = booking;
        binder.readBean(booking);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(currentBooking);
            fireEvent(new SaveEvent(this, currentBooking));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<BookingFormv2> {
        private Booking booking;

        protected ContactFormEvent(BookingFormv2 source, Booking booking) {
            super(source, false);
            this.booking = booking;
        }

        public Booking getContact() {
            return booking;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(BookingFormv2 source, Booking booking) {
            super(source, booking);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(BookingFormv2 source, Booking booking) {
            super(source, booking);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(BookingFormv2 source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}