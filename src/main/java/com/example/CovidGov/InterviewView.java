package com.example.CovidGov;


import com.example.CovidGov.enums.EnumCovidTest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Route(value = "Interview")
public class InterviewView extends VerticalLayout {

    //load own api key from env variables
    Dotenv dotenv = Dotenv.load();
    private final String myApiKey = dotenv.get("API_KEY");

    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    private static final String rootUrl = "https://fit3077.com/api/v1";

    public InterviewView() throws Exception {
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
        VerticalLayout todosList = new VerticalLayout();
        setSizeFull();
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Symptoms");
        checkboxGroup.setItems("Runny nose", "Coughing", "Sore throat", "Sneezing", "Head ache");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        add(checkboxGroup);

        Button myButton = new Button("Submit");
        myButton.addClickListener(click -> assignTest(String.valueOf(checkboxGroup.getValue()).length()));
        myButton.addClickListener(click -> checkboxGroup.setEnabled(false));
        add(myButton);


    }
    // assign rat for not symptoms, else assign pcr
    public CovidTest assignTest(int markers){
        if (markers <= 2){
            return makeRAT();

        }
        else {
            return makePCR();
        }
    }
    public CovidTest makeRAT(){
        CovidTest covidTest = new CovidTest(EnumCovidTest.RAT);
        Notification.show(EnumCovidTest.RAT + "assigned");
        return covidTest;
    }

    public CovidTest makePCR(){
        CovidTest covidTest = new CovidTest(EnumCovidTest.PCR);
        Notification.show(EnumCovidTest.PCR + "assigned");
        return covidTest;
    }
}







