package com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities;

import android.provider.ContactsContract;

import com.google.firebase.database.Exclude;

public class Driver {
    /*
    שם משפחה
 שם פרטי
 מספר ת.ז.
 מספר טלפון
 כתובת דוא"ל
 מספר כרטיס אשראי לחיוב
     */
    String firstName;
    String lastName;
    String id;
    String phoneNumber;
    String email;
    String creditCardNumber;

    public Driver(String firstName, String lastName, String id, String phoneNumber, String email, String creditCardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
