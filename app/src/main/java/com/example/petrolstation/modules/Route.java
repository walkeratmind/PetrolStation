package com.example.petrolstation.modules;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    private Distance distance;
    private Duration duration;

    private String startAddress;
    private String endAddress;

    private LatLng startLocation;
    private LatLng endLocation;

    private List<LatLng> points;

    public Route(Distance distance, Duration duration,
                 String startAddress, String endAddress,
                 LatLng startLocation, LatLng endLocation,
                 List<LatLng> points) {
        this.distance = distance;
        this.duration = duration;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.points = points;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}
