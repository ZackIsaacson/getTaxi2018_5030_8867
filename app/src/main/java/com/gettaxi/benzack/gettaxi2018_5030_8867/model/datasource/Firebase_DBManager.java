package com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource;

import android.location.Location;
import android.os.AsyncTask;

import com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend.Backend;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Firebase_DBManager extends AsyncTask<String, Void, Void> implements Backend {

    @Override
    public void addRide(String destinationLoaction, String email, String phone) {
        DatabaseReference fireBaseRoot = FirebaseDatabase.getInstance().getReference("Rides");
        //todo-- calculate current location in string! (get locatiob and convert to string)
        HashMap<String, String> hm = new HashMap<String, String>();
        //todo-- change method of implemantion. also add current location to firebase
        hm.put(destinationLoaction, email);
        fireBaseRoot.child(phone).setValue(hm);
    }


    @Override//this function makes it asynchrony. (thread working in background)
    protected Void doInBackground(String... strings) {
        addRide(strings[0], strings[1], strings[2]);
        return null;
    }
}
