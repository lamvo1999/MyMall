package com.example.mymall.Model;

public class AddressesModel {
    private String fullnamr;
    private String address;
    private String pincode;
    private Boolean selected;


    public AddressesModel(String fullnamr, String address, String pincode, Boolean selected) {
        this.fullnamr = fullnamr;
        this.address = address;
        this.pincode = pincode;
        this.selected = selected;
    }

    public String getFullnamr() {
        return fullnamr;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setFullnamr(String fullnamr) {
        this.fullnamr = fullnamr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


}
