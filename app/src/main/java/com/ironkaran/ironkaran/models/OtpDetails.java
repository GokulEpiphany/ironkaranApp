package com.ironkaran.ironkaran.models;

public class OtpDetails {

    private int currentOtp;


    public OtpDetails(int currentOtp) {
        super();
        this.currentOtp = currentOtp;
    }

    public int getCurrentOtp() {
        return currentOtp;
    }

    public void setCurrentOtp(int currentOtp) {
        this.currentOtp = currentOtp;
    }

}