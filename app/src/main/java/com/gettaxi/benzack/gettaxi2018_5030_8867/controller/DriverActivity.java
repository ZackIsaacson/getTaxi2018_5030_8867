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


//todo main driver activity. fragments etc.
public class DriverActivity extends Activity {
    EditText editTextUserName;
    EditText editTextPassword;
    Button buttonAddToSharedPrefrences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checksharedprefrences);
        findViews();
    }


    public void findViews() {
        final String DEFAULT = "";
        editTextPassword = (EditText) findViewById(R.id.editTextPassword2);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName2);
        buttonAddToSharedPrefrences = (Button) findViewById(R.id.buttonEnterToSharedPrefrences);
        buttonAddToSharedPrefrences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
                //   SharedPreferences.Editor edit=sharedPreferences.edit();  //to write
//                edit.putString("Username",editTextUserName.getText().toString());
//                edit.putString("Password",editTextPassword.getText().toString());
//                edit.commit();
                editTextUserName.setText(sharedPreferences.getString("Username", DEFAULT));
                editTextPassword.setText(sharedPreferences.getString("Password", DEFAULT));
                Toast.makeText(DriverActivity.this, "Succesful retrivel", Toast.LENGTH_LONG).show();
//                Intent DriverIntent=new Intent(DriverSignInActivty.this,DriverActivity.class);
//                startActivity(DriverIntent);
            }
        });

    }
}
