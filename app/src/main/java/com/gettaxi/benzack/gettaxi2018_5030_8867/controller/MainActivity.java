package com.gettaxi.benzack.gettaxi2018_5030_8867.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gettaxi.benzack.gettaxi2018_5030_8867.R;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend.Backend;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend.BackendFactory;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource.Firebase_DBManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

//1.todo singleton ok?
public class MainActivity extends Activity {

    private enum Provider {GPS, NETWORK, NO_Provider}

    ;
    boolean checkCurrLoc = false, checkDestLoc = false;
    int spinnerChoice = 0;
    final int PLACE_PICKER_REQUEST = 1;
    EditText editTextEmail;
    EditText editTextPhone;
    Button buttonAddRide;
    BackendFactory b = new BackendFactory();
    ;
    Backend backendImplemntation;
    //Firebase_DBManager firebase_dbManager;
    LocationManager locationManager;
    LocationListener locationListener;
    String currentLocationString, destinationLocationString;
    myAsyncTask m = new myAsyncTask();
    PlaceAutocompleteFragment autocompleteFragment;
    Location destinationLocation, currentDestination;
    Spinner spinnerStartLocation;
    TextView textViewSpinnerChoice;
    Location locationGPS;
    Location locationNetwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);
        findViews();
        getLastLoc();

        m.execute();
    }

    int a;

    public void findViews() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentDestination = location;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        getCurrentLocation(Provider.GPS);  //because asynchrony getting the lastknownlocation up to date.
        getCurrentLocation(Provider.NETWORK);
        //  editTextDestination = (EditText) findViewById(R.id.editTextDestination);
        textViewSpinnerChoice = (TextView) findViewById(R.id.textViewspinnerChoice);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        buttonAddRide = (Button) findViewById(R.id.buttonAddRide);
        spinnerStartLocation = (Spinner) findViewById(R.id.spinnerStartLocation);


        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        destinationLocation = new Location("A");//= new Location(from);
        autocompleteFragment.setHint("Enter Destination...");
        //   placeAutocompleteFragment1.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                checkDestLoc = true;
                destinationLocation.setLatitude(place.getLatLng().latitude);
                destinationLocation.setLongitude(place.getLatLng().longitude);
                // .getAddress().toString();//get place details here
            }

            @Override
            public void onError(Status status) {
                checkDestLoc = false;
            }
        });

        String[] startLocationOptions = {"Current Location", "Search on map"};
        ArrayAdapter<String> spinnerStartLocationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, startLocationOptions);
        spinnerStartLocationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartLocation.setAdapter(spinnerStartLocationArrayAdapter);
        spinnerStartLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                switch (selectedItem) {
                    case "Current Location": {
                        Location temp=getLastLoc();
                      //todo  Location temp = getCurrentLocation(Provider.GPS);
                        if (temp == null) {
                            checkCurrLoc = false;
                            textViewSpinnerChoice.setText("GPS and network unavailable to find current location");
                        } else {
                            checkCurrLoc = true;
                            textViewSpinnerChoice.setText(getPlace(temp));
                          //todo  textViewSpinnerChoice.setText(getPlace(getCurrentLocation(Provider.GPS)));
                        }
                        spinnerChoice = 1;
                        break;
                    }
//                    case "Choose Location": {
//                        spinnerChoice = 2;
//                        break;
//                    }
                    case "Search on map": {
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        spinnerChoice = 3;
                        checkCurrLoc = false;

                        try {
                            startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                textViewSpinnerChoice.setText(place.getName());
                checkCurrLoc = true;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            backendImplemntation = b.getInstance();

            buttonAddRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        checkInput();
                        Toast showUserUpload;
                        if (spinnerChoice == 1) {
                            //might be more accurate location, thats why checking again. might be null. solving that in checkinput
                            currentLocationString = getPlace(getLastLoc());
                           //todo currentLocationString = getPlace(getCurrentLocation(Provider.GPS));
                        } else if (spinnerChoice == 3) {
                            currentLocationString = textViewSpinnerChoice.getText().toString();
                        }

                        destinationLocationString = getPlace(destinationLocation);
                        String actionUpload = backendImplemntation.addRide(currentLocationString, destinationLocationString, editTextEmail.getText().toString()
                                , editTextPhone.getText().toString());
                        showUserUpload = Toast.makeText(getBaseContext(), actionUpload, Toast.LENGTH_LONG);
                        showUserUpload.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                        showUserUpload.show();

                        autocompleteFragment.setText("");
                        editTextEmail.setText("");
                        editTextPhone.setText("");

                        //currentLocationString = getPlace(getLastKnownLocation());
                        //    curretLocationString=getPlace(locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER));

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            return null;
        }

    }

    //checks if email input is valid.
    public final static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //check if phone input is valid
    public boolean isValidCellPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 7 || phoneNumber.length() > 13)
            return false;
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    //todo check destination and currentlocation if not null.
    public void checkInput() throws Exception {
        if (!checkCurrLoc)  //if false, no current location
            throw new Exception("To order a taxi, needs a start location!");
        if (!checkDestLoc)   //if false, no destination location
            throw new Exception("To order a taxi, needs a destination location!");
        if (!isValidEmail(editTextEmail.getText().toString()))
            throw new Exception("Not a vaild email address! Please enter again.");
        if (!isValidCellPhone(editTextPhone.getText().toString()))
            throw new Exception("Not a valid phone number! Please enter again.");
    }

    //TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    todo        if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//        todo            ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            todo    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            }
    //todo why doesnt  "if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)" not work. returns false.
    //phones api>= 23 (my phone is 21 and thereforealways returns false. doesnt ask for permission) 21>=23
//    todo when gps or network not enabled, doesnt work.
//    todo hasAccuaracy isnt working. change function. changed.
//    todo sometimes when uploading to firebase, doesnt show the toast, only empty toast. null.
    public Location getCurrentLocation(Provider provider) {

        //                && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
        if (provider == Provider.GPS) {  //gps usually more accurate.
            //if needs permission and check if has..
            //phones api>= 23 (my phone is 21 and thereforealways returns false. doesnt ask for permission)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //if no permission for gps, ask for permission (gps is more accurate then network)
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
                }
            } else {   //has permission for gps or doesnt need.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationGPS = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                if (locationGPS != null)
                    return locationGPS;
                else  //if has permission for gps but indoors or cant reach signal. has to use network.
                    provider = Provider.NETWORK;
            }
        }

        if (provider == Provider.NETWORK) {

            //check if needs permission and if does need, if has permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 6);
            } else { //if doesnt need or has permission (when gps not available). get location from network
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationNetwork = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                if (locationNetwork != null)  //has permission for both but gps not working network yes working. return network
                    return locationNetwork;
                else
                    provider = Provider.NO_Provider;
            }
        }                //(provider == Provider.NO_Provider)
        return null;
    }
public Location getLastLoc()
{Location loc;
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return null;
    }
    loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
    String s=getPlace(loc);
    return loc;
}

    //if didnt have permission, tried getting permission. coming back from requestPermissions here.
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 5) {  //check if got gps permission
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation(Provider.GPS);
            } else  //didnt grant permission for gps. try using network
                getCurrentLocation(Provider.NETWORK);
        }
        if (requestCode == 6) // check if got network permission
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //if got permission
                getCurrentLocation(Provider.NETWORK);
            } else  //no peermission for network (if here, also no permission for gps. starts with gps).
                Toast.makeText(this, "Cant find location! GPS and network disabled!", Toast.LENGTH_LONG).show();

    }

    //function gets location and turns into a string. location.toString();
    public String getPlace(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                int i = 0;
                String address = "";
                while (addresses.get(0).getAddressLine(i) != null) {
                    address += addresses.get(0).getAddressLine(i);
                    //    String stateName = addresses.get(0).getAddressLine(1);
                    //  String countryName = addresses.get(0).getAddressLine(2);
                    i++;
                }
                return /*stateName + "\n" +*/ address + "\n"/* + countryName*/;
            }
            return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "IOException ...";
    }
}

/*
    public Location getLastKnownLocation() {
        Location l = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            //  l = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            l = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        }
        return l;
    }
    */
//has permission for network but doesnt have for gps. use network
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            {
//                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//                Location locationNetwork=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
//                if(locationNetwork.hasAccuracy())  //network works return loation
//                    return locationNetwork;
//                else  //only has permission for network but doesnt work. try permission for gps only
//            }
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 6);
//
//            either doesnt need permission or needs but has for gps.
//        }// else { could only still be in the function if has permission
//         Android version is lesser than 6.0 or the permission is already granted.
//         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
//        Location locationGPS=locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
//        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        Location locationNetwork=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
//        if(locationGPS.hasAccuracy() && locationNetwork.hasAccuracy()) {
//            if (locationGPS.getAccuracy() < locationNetwork.getAccuracy())
//                return locationGPS;
//            else
//                return locationNetwork;
//        }
//        if(!locationGPS.hasAccuracy() && !locationNetwork.hasAccuracy())
//            Toast.makeText(this,"cant get location from network or gps", Toast.LENGTH_LONG);
//        if(locationGPS.hasAccuracy())
//            return locationGPS;
//        return locationNetwork;
//    }


// Permission is granted
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                Location locationGPS = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
//                if (locationGPS.hasAccuracy())
//                    return locationGPS;                // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

//    }
//
//} else{  //if didnt give permission for gps, try for network
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},6);
//        Toast.makeText(this, "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//                {
//                }
//        }
//        if(requestCode==6)  //has no permission for gps. asked for network
//        }
//
//        }



