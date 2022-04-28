package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Route(value = "Browse")
public class BrowseSitesView extends VerticalLayout{
    List<TestingSite> sites;
    Grid<TestSiteTable> grid = new Grid<>(TestSiteTable.class);
    TextField filterText = new TextField();

    //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";

    public BrowseSitesView() throws Exception{
        String testingSiteUrl = rootUrl + "/testing-site";

        //Performing GET requests that return an array of objects.
        // This request is authenticated (API key attached in the Authorization header), and will return the JSON array object containing all users.
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(testingSiteUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // The GET /user endpoint returns a JSON array, so we can loop through the response as we could with a normal array/list.
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        ObjectMapper mapper = new ObjectMapper();
        List<TestSiteTable> siteList = new ArrayList<TestSiteTable>();
        for (ObjectNode node: jsonNodes) {
            TestingSite site = mapper.readValue(node.toString(), TestingSite.class);
            TestSiteTable informativeSite = new TestSiteTable(site.getName(), site.getAddress().getSuburb(), site.getAdditionalInfo().getFacilityType(), site.getAdditionalInfo().isProvidesOnsiteTesting(), site.getAdditionalInfo().isProvidesOnsiteBooking(), site.getAdditionalInfo().getOpeningTimes(), site.getAdditionalInfo().getEstimatedWaitTime());
            siteList.add(informativeSite);
        }

        grid.setItems(siteList);

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
        grid.setColumns("name", "suburb", "facilityType", "providesOnsiteTesting", "providesOnsiteBooking", "openingHours", "estimatedWaitTime");
    }

}


