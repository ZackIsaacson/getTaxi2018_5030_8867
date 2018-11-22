package com.gettaxi.benzack.gettaxi2018_5030_8867.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gettaxi.benzack.gettaxi2018_5030_8867.R;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend.Backend;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend.BackendFactory;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource.Firebase_DBManager;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities.Driver;

//todo 1. make id inputtype phone
//todo 2. input check.
public class DriverRegisterActivity extends Activity {
    EditText editTextFirstName, editTextLastName, editTextID, editTextPhone, editTextEmail, editTextCreditCardNumber;
    Button buttonRegister;
    Backend backendImplementation;
    BackendFactory backendFactory = new BackendFactory();
    Backend.BackendUploadAsyncTask backendUploadAsyncTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_driver_register);
            findViews();
            buttonClickRegister();
        } catch (Exception e) {
            Toast.makeText(DriverRegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public void findViews() {
        backendImplementation = backendFactory.getInstance();
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextPhone = (EditText) findViewById(R.id.editTextDriverPhoneNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextCreditCardNumber = (EditText) findViewById(R.id.editTextCreditCardNumber);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    }

    public void buttonClickRegister() {
        backendUploadAsyncTask = new Backend.BackendUploadAsyncTask() {
            @Override
            public Void doInBackground(Void... voids) {
                buttonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Driver newDriver = new Driver(editTextFirstName.getText().toString(),
                                editTextLastName.getText().toString(),
                                editTextID.getText().toString(),
                                editTextPhone.getText().toString(),
                                editTextEmail.getText().toString(),
                                editTextCreditCardNumber.getText().toString());
                        backendImplementation.addDriver(newDriver, new Backend.Action() {
                            @Override
                            public void onSuccess(String act) {
                                Toast.makeText(DriverRegisterActivity.this, act, Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onProgress(String act) {

                            }

                            @Override
                            public void onFailure(String act) {
                                Toast.makeText(DriverRegisterActivity.this, act, Toast.LENGTH_LONG);
                            }
                        });
                        editTextLastName.setText("");
                        String username=getIntent().getStringExtra("Username");
                        String password=getIntent().getStringExtra("Password");
                        SharedPreferences sharedPreferences = getSharedPreferences("Drivers", MODE_PRIVATE);
                        SharedPreferences.Editor edit=sharedPreferences.edit();
                        edit.putString("Username",username);
                        edit.putString("Password",password);
                        edit.commit();
                        Intent intentDriver=new Intent(DriverRegisterActivity.this,DriverActivity.class);
                        startActivity(intentDriver);
                    }
                });

                return null;
            }

            //   SharedPreferences.Editor edit=sharedPreferences.edit();  //to write
//                edit.putString("Username",editTextUserName.getText().toString());
//                edit.putString("Password",editTextPassword.getText().toString());
//                edit.commit();
        };
        backendUploadAsyncTask.execute();

//        Intent intentBackToSignIn=new Intent(DriverRegisterActivity.this,DriverSignInActivty.class);
//        intentBackToSignIn.putExtra("Username",username);
//        intentBackToSignIn.putExtra("Password",password);
//        startActivity(intentBackToSignIn);
    }
}
