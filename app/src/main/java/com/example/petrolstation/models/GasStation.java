package com.example.petrolstation.models;

public class GasStation {
    private String name;
    private String location;
    private String latitude;
    private String longitude;
    private String petrolPrice;
    private String dieselPrice;

    public GasStation() {}

    public GasStation(String name, String location, String latitude, String longitude, String petrolPrice, String dieselPrice) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(String dieselPrice) {
        this.dieselPrice = dieselPrice;
    }
}
