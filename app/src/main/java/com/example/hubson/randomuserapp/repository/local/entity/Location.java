package com.example.hubson.randomuserapp.repository.local.entity;

public class Location {
    private final String street;
    private final String city;
    private final String state;
    private final String postcode;

    public Location(String street, String city, String state, String postcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }
}
