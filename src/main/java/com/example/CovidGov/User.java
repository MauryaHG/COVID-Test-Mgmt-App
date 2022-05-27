package com.example.CovidGov;


import com.example.CovidGov.AdminBooking.Booking;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

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
    public List<Booking> bookings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isReceptionist() {
        return isReceptionist;
    }

    public void setReceptionist(boolean receptionist) {
        isReceptionist = receptionist;
    }

    public boolean isHealthcareWorker() {
        return isHealthcareWorker;
    }

    public void setHealthcareWorker(boolean healthcareWorker) {
        isHealthcareWorker = healthcareWorker;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<Object> getTestsTaken() {
        return testsTaken;
    }

    public void setTestsTaken(ArrayList<Object> testsTaken) {
        this.testsTaken = testsTaken;
    }

    public ArrayList<Object> getTestsAdministered() {
        return testsAdministered;
    }

    public void setTestsAdministered(ArrayList<Object> testsAdministered) {
        this.testsAdministered = testsAdministered;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public ArrayList<Object> testsTaken;
    public ArrayList<Object> testsAdministered;
    public AdditionalInfo additionalInfo;

    public class AdditionalInfo{
        private String workingSite;

        public String getWorkingSite() {
            return workingSite;
        }

        public void setWorkingSite(String workingSite) {
            this.workingSite = workingSite;
        }
    }


}
