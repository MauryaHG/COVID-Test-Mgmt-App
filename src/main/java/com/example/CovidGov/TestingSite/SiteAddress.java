package com.example.CovidGov.TestingSite;

public class SiteAddress {
    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getStreet2() {
        return street2;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    private long latitude;
    private long longitude;
    private String unitNumber;
    private String street;
    private String street2;
    private String suburb;
    private String state;
    private String postcode;
    private AdditionalInfo additionalInfo;
}