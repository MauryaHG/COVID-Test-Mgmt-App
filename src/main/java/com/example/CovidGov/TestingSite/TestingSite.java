package com.example.CovidGov.TestingSite;

import com.example.CovidGov.AdminBooking.Booking;

import java.util.List;
public class TestingSite {
    /*
    public TestingSite(String id, String name, String description, String websiteUrl, String phoneNumber, myAddress address, String createdAt, String updatedAt, AdditionalInfo additionalInfo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.additionalInfo = additionalInfo;
    }*/

    private String id;
    private String name;
    private String description;
    private String websiteUrl;
    private String phoneNumber;
    private siteAddress address;
    private String createdAt;
    private String updatedAt;
    private List<Booking> bookings = null;
    private AdditionalInfo additionalInfo;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(siteAddress address) {
        this.address = address;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public siteAddress getAddress() {
        return address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public List<Booking> getBookings() { return bookings; }



}
