package com.example.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "Browse")
public class BrowseSites extends VerticalLayout {
    Grid<TestingSite> grid = new Grid<>(TestingSite.class);
    TextField filterText = new TextField();

    public BrowseSites(){
        addClassName("list-view");
        setSizeFull();

        configureGrid();

        add(
                getToolbar(),
                grid
        );
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button browseSiteButton = new Button("Browse site");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, browseSiteButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("testing-site-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "description");
    }

}
