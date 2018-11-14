package com.gettaxi.benzack.gettaxi2018_5030_8867.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gettaxi.benzack.gettaxi2018_5030_8867.R;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend.BackendFactory;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource.Firebase_DBManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import static android.content.ContentValues.TAG;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends Activity {

    EditText editTextDestination;
    EditText editTextEmail;
    EditText editTextPhone;
    Button buttonAddRide;
    BackendFactory b;
    //  BackendFactory b = new BackendFactory(this);
    Firebase_DBManager firebase_dbManager;
    myAsyncTask m = new myAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        findViews();
    }

    public void findViews() {
        editTextDestination = (EditText) findViewById(R.id.editTextDestination);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        buttonAddRide = (Button) findViewById(R.id.buttonAddRide);

        m.execute();
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                BackendFactory b = new BackendFactory(getBaseContext());
            } catch (Exception e) {
               // Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG);
            }
            firebase_dbManager = b.getInstance();
            buttonAddRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        firebase_dbManager.addRide(editTextDestination.getText().toString(), editTextEmail.getText().toString()
                                , editTextPhone.getText().toString());
                        //execute calls doInBackground (asyncTask) of fireBaseDbManager. which makes the next line asynchrony
                        // (meaning will not wait for the line to be completed. the thread will be working in the background. uploading to firebase)
//            firebase_dbManager.execute(editTextDestination.getText().toString(), editTextEmail.getText().toString()
//                    , editTextPhone.getText().toString());
                        Toast t = Toast.makeText(getBaseContext(), "succesfully uploaded to firebase", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                        t.show();
                        editTextDestination.setText("");
                        editTextEmail.setText("");
                        editTextPhone.setText("");
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            return null;
        }
    }
}


