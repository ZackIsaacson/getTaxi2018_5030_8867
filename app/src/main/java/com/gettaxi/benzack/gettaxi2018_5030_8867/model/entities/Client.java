package com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities;


import com.google.firebase.database.Exclude;

public class Client{
    String clientName;
    String clientPhoneNumber;
    String clientEmailAddress;

    public Client(String clientName, String clientPhoneNumber, String clientEmailAddress) {
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientEmailAddress = clientEmailAddress;

    }

    public String PhoneNumberToString()
    {
        return " " + getClientPhoneNumber() + " ";
    }
    //setters
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }
    //getters
    public String getClientName() {
        return clientName;
    }

    @Exclude
    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientName='" + clientName + '\'' +
                ", clientPhoneNumber=" + clientPhoneNumber +
                ", clientEmailAddress='" + clientEmailAddress + '\'' +
                '}';
    }

    //constructors
}

