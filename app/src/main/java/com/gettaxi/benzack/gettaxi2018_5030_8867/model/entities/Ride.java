package com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities;

import android.location.Location;

import java.sql.Time;

//todo name,email,phone, destination
public class Ride {
    Client theClient;
    ClientRequestStatus status;
    Location startRideLocation;
    Location endRideLocation;
    Time startRideTime;
    Time endRideTime;


    @Override
    public String toString() {
        return "Ride{" +
                theClient.toString() +
                ", status=" + status +
                ", startRideLocation=" + startRideLocation +
                ", endRideLocation=" + endRideLocation +
                ", startRideTime=" + startRideTime +
                ", endRideTime=" + endRideTime +
                '}';
    }

    public Ride() {
        Location n = null;
        Time t = null;
        t.setTime(4);
        n.setAltitude(4);
        n.setLatitude(4);
        Client c = new Client("zack", "050", "z@jct");
        this.status = ClientRequestStatus.WAITING;
        this.startRideLocation = n;
        this.endRideLocation = n;
        this.startRideTime = t;
        this.endRideTime = t;
        this.theClient = c;
    }

    //constrcutor
    public Ride(Location startRideLocation, Location endRideLocation,
                Time startRideTime, Time endRideTime, Client theClient) {

        this.status = ClientRequestStatus.WAITING;
        this.startRideLocation = startRideLocation;
        this.endRideLocation = endRideLocation;
        this.startRideTime = null;  ////////////////////TODO- GET CURRENT TIME OF BEGINNING OF RIDE
        this.endRideTime = null;    //////////////////// TODO- WHENEVER FINISH RIDE- SHOULD PUT IN END TIME.
        this.theClient = theClient;
    }

    public Client getTheClient() {
        return theClient;
    }

    public ClientRequestStatus getStatus() {
        return status;
    }

    public Location getStartRideLocation() {
        return startRideLocation;
    }

    public Location getEndRideLocation() {
        return endRideLocation;
    }

    public Time getStartRideTime() {
        return startRideTime;
    }

    public Time getEndRideTime() {
        return endRideTime;
    }


    public void setTheClient(Client theClient) {
        this.theClient = theClient;
    }

    public void setStatus(ClientRequestStatus status) {
        this.status = status;
    }

    public void setStartRideLocation(Location startRideLocation) {
        this.startRideLocation = startRideLocation;
    }

    public void setEndRideLocation(Location endRideLocation) {
        this.endRideLocation = endRideLocation;
    }

    public void setStartRideTime(Time startRideTime) {
        this.startRideTime = startRideTime;
    }

    public void setEndRideTime(Time endRideTime) {
        this.endRideTime = endRideTime;
    }


}

