package com.example.CovidGov.AdminBooking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "QRcode" ,"test","qrcode"})
public class AdditionalInfo {

    public String getqrcode() {
        return qrcode;
    }

    public void setqrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    private String qrcode;

}
