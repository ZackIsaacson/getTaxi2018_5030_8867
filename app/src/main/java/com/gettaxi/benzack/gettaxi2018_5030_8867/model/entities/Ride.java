package com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities;

import android.location.Location;

import java.sql.Time;

//todo name,email,phone, destination
public class Ride {
    String clientName;
    String clientPhoneNumber;
    String clientEmailAddress;
    ClientRequestStatus status;
    Location startRideLocation;
    Location endRideLocation;
    Time startRideTime;
    Time endRideTime;

    public Ride(String clientName, String clientPhoneNumber, String clientEmailAddress, ClientRequestStatus status, Location startRideLocation, Location endRideLocation, Time startRideTime, Time endRideTime) {
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientEmailAddress = clientEmailAddress;
        this.status = status;
        this.startRideLocation = startRideLocation;
        this.endRideLocation = endRideLocation;
        this.startRideTime = startRideTime;
        this.endRideTime = endRideTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public ClientRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ClientRequestStatus status) {
        this.status = status;
    }

    public Location getStartRideLocation() {
        return startRideLocation;
    }

    public void setStartRideLocation(Location startRideLocation) {
        this.startRideLocation = startRideLocation;
    }

    public Location getEndRideLocation() {
        return endRideLocation;
    }

    public void setEndRideLocation(Location endRideLocation) {
        this.endRideLocation = endRideLocation;
    }

    public Time getStartRideTime() {
        return startRideTime;
    }

    public void setStartRideTime(Time startRideTime) {
        this.startRideTime = startRideTime;
    }

    public Time getEndRideTime() {
        return endRideTime;
    }

    public void setEndRideTime(Time endRideTime) {
        this.endRideTime = endRideTime;
    }
}