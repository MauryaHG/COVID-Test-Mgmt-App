package com.example.demo.TestingSite;

public class AdditionalInfo{
    public void setProvidesOnsiteTesting(boolean providesOnsiteTesting) {
        this.providesOnsiteTesting = providesOnsiteTesting;
    }

    public void setProvidesOnsiteBooking(boolean providesOnsiteBooking) {
        this.providesOnsiteBooking = providesOnsiteBooking;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }

    public void setEstimatedWaitTime(String estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public boolean isProvidesOnsiteTesting() {
        return providesOnsiteTesting;
    }

    public boolean isProvidesOnsiteBooking() {
        return providesOnsiteBooking;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public String getOpeningTimes() {
        return openingTimes;
    }

    public String getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    private boolean providesOnsiteTesting;
    private boolean providesOnsiteBooking;
    private String facilityType;
    private String openingTimes;
    private String estimatedWaitTime;
}