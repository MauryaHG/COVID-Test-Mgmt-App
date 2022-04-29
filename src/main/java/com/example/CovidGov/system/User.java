package com.example.CovidGov.system;



import com.vaadin.flow.router.Route;

import java.util.ArrayList;

/**
 * user class to be created
 */
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
