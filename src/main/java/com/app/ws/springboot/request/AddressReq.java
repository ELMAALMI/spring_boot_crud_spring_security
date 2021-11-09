package com.app.ws.springboot.request;

public class AddressReq {
    private String country;
    private String city;
    private String code_postal;
    private String street;
    public AddressReq(){}
    public AddressReq(String country, String city, String code_postal, String street) {
        this.country = country;
        this.city = city;
        this.code_postal = code_postal;
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
