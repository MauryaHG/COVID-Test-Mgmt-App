package com.example.demo.TestingSite;

public class TestSiteTable{
    private String name;
    private String suburb;
    private String facilityType;
    private boolean providesOnsiteTesting;
    private boolean providesOnsiteBooking;
    private String openingHours;
    private String estimatedWaitTime;

    public TestSiteTable(String name, String suburb, String facilityType, boolean providesOnsiteTesting, boolean providesOnsiteBooking, String openingHours, String estimatedWaitTime) {
        this.name = name;
        this.suburb = suburb;
        this.facilityType = facilityType;
        this.providesOnsiteTesting = providesOnsiteTesting;
        this.providesOnsiteBooking = providesOnsiteBooking;
        this.openingHours = openingHours;
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public boolean isProvidesOnsiteTesting() {
        return providesOnsiteTesting;
    }

    public void setProvidesOnsiteTesting(boolean providesOnsiteTesting) {
        this.providesOnsiteTesting = providesOnsiteTesting;
    }

    public boolean isProvidesOnsiteBooking() {
        return providesOnsiteBooking;
    }

    public void setProvidesOnsiteBooking(boolean providesOnsiteBooking) {
        this.providesOnsiteBooking = providesOnsiteBooking;
    }



    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public void setEstimatedWaitTime(String estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

}