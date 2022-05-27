package com.example.CovidGov.AdminBooking;

import com.example.CovidGov.User;
import com.example.CovidGov.BookingsViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@Route(value="book")
@PageTitle("Bookings")
public class BookingsView extends VerticalLayout {

    Grid<BookingModel> grid = new Grid<>(BookingModel.class);
    EditBookingsView form;
    BookingsViewModel api = new BookingsViewModel();

    VaadinSession session = VaadinSession.getCurrent();   // Fetch current instance of VaadinSession to use its key-value collection of attributes.
    User currentUser = session.getAttribute(User.class);
    String siteID = currentUser.getAdditionalInfo().getWorkingSite();
    public BookingsView() throws JsonProcessingException {

        System.out.println(currentUser);
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);

        form = new EditBookingsView(this, api.getBookings(siteID));
        form.setWidth("25em");
        add(form);
        updateList();
        grid.asSingleSelect().addValueChangeListener(event ->
                editBooking(event.getValue()));
    }

    public void updateList()  {
        try{
            grid.setItems(api.getBookings(siteID));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    }

    private void configureGrid() {
        grid.addClassNames("booking-grid");
        grid.setSizeFull();
        grid.setColumns("id", "status");
        grid.addColumn(booking -> booking.getTestingSite().getName()).setHeader("Testing site name");
        grid.addColumn(booking -> booking.getTestingSite().getId()).setHeader("Testing site ID");
        grid.addColumn(booking -> booking.getCustomer().getGivenName()).setHeader("First Name");
        grid.addColumn(booking -> booking.getCustomer().getFamilyName()).setHeader("Second Name");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    public void editBooking(BookingModel booking) {
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