package com.example.demo.users;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.router.Route;


import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
import java.util.ArrayList;

@Route("User")
public class User {

    public String id;
    public String givenName;
    public String familyName;
    public String userName;
    public String phoneNumber;
    public boolean isCustomer;
    public boolean isReceptionist;
    public boolean isHealthcareWorker;
    public ArrayList<Object> bookings;
    public ArrayList<Object> testsTaken;
    public ArrayList<Object> testsAdministered;
    public AdditionalInfo additionalInfo;

    public class AdditionalInfo{
    }

}
