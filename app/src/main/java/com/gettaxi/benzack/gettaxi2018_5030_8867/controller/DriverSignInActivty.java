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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Thread.sleep;

//todo write login on button, actually check if driver logged in.(through savedsharedpref) if not, register as new driver. saving to sharedpref and then to firebase.

public class DriverSignInActivty extends Activity {
    final static String finalStaticUsername = "Username", finalStaticPassword = "Password", DEFAULT = "";
    EditText editTextUserName;
    EditText editTextPassword;
    Button buttonLogin;
    static DatabaseReference fireBaseDriverRoot;

    static {
        fireBaseDriverRoot = FirebaseDatabase.getInstance().getReference("Drivers");///todo
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);
        findViews();
    }

    public void findViews() {

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SharedPreferences getSharedPrefrences=
                SharedPreferences sharedPreferences = getSharedPreferences("Drivers", MODE_PRIVATE);  //todo mode_append?
                boolean registered = false;
                //check if in shared prefrences
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                if (username == sharedPreferences.getString(finalStaticUsername, DEFAULT) &&
                        password == sharedPreferences.getString(finalStaticPassword, DEFAULT)) {
                    registered = true;

                    //todo. go to next activity with username and password
                    ;
                }
                //might not be in sharedPrefrence but still registered in fb. the reason not in sharedPref is maybe logging in with different smart phone
                else if (username == fireBaseDriverRoot.orderByChild(finalStaticUsername).equalTo(username).toString()
                        && password == fireBaseDriverRoot.orderByChild(finalStaticPassword).equalTo(password).toString()) {
                    //found in firebase. put to sharedRefrence, and then go to the next activity with username and password
                    registered = true;
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(finalStaticUsername, username);
                    edit.putString(finalStaticPassword, password);
                    edit.commit();
                }
                if (registered) {  //either registerd and was in sharedPrefrences or wasnt but was in fireBase.
                    Intent driverIntent = new Intent(DriverSignInActivty.this, DriverActivity.class);
                    driverIntent.putExtra(finalStaticUsername, username);
                    driverIntent.putExtra(finalStaticPassword, password);
                    startActivity(driverIntent);
                }
                //register driver in seperate Activity// can also make it pop up in same activity if not registered
                else {
                    Intent driverRegisterIntent = new Intent(DriverSignInActivty.this, DriverRegisterActivity.class);
                    driverRegisterIntent.putExtra(finalStaticUsername, username);
                    driverRegisterIntent.putExtra(finalStaticPassword, password);
                    startActivity(driverRegisterIntent);
                }

            }
        });


    }

}
