package com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities;

import com.google.firebase.database.Exclude;

public class ClientPlus extends Client {

    String id;

    public ClientPlus(String clientName, String clientPhoneNumber, String clientEmailAddress, String id) {
        super(clientName, clientPhoneNumber, clientEmailAddress);
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
