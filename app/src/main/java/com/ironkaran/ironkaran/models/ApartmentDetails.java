package com.ironkaran.ironkaran.models;

/**
 * Created by gokulakrishnanm on 22/02/18.
 */

public class ApartmentDetails {

    private int apartmentId;
    private String apartmentName;

    public ApartmentDetails(int apartmentId, String apartmentName) {
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
    }

    public ApartmentDetails() {
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
}
