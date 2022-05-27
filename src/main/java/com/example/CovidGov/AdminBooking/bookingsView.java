package com.example.CovidGov.AdminBooking;

import com.example.CovidGov.apiTools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value="book")
@PageTitle("Bookings")
public class bookingsView extends VerticalLayout {

    Grid<Booking> grid = new Grid<>(Booking.class);
    TextField filterText = new TextField();
    editBookingsView form;
    apiTools api = new apiTools();

    public bookingsView() throws JsonProcessingException {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);

        form = new editBookingsView(this, api.getBookings("7fbd25ee-5b64-4720-b1f6-4f6d4731260e"));
        form.setWidth("25em");
        add(form);
        updateList();
        grid.asSingleSelect().addValueChangeListener(event ->
                editBooking(event.getValue()));
    }

    public void updateList()  {
        try{
            grid.setItems(api.getBookings("7fbd25ee-5b64-4720-b1f6-4f6d4731260e"));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    }

    private void configureGrid() {
        grid.addClassNames("booking-grid");
        grid.setSizeFull();
        grid.setColumns("id","testingSite");
        grid.addColumn(booking -> booking.getCustomer().getGivenName()).setHeader("First Name");
        grid.addColumn(booking -> booking.getCustomer().getFamilyName()).setHeader("Second Name");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    public void editBooking(Booking booking) {
        System.out.println("booking");
        System.out.println(booking.getTestingSite());
        if (booking == null) {
            closeEditor();
        } else {
            form.setBooking(booking);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setVisible(false);
        removeClassName("editing");
    }

}