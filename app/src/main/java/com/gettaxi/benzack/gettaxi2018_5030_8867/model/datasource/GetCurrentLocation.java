package com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetCurrentLocation extends Activity {

    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    // Define a listener that responds to location updates
    LocationListener locationListener;
    String loc;

    public String listenForLocation() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                //         showSunTimes(location.getLatitude(), location.getLongitude()); /// ...
                // Called when a new location is found by the network location provider.
                //    Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                //TODO- get the tet and jus put in variable
                loc = getPlace(location);
                //      locationTextView.setText(getPlace(location));////location.toString());

                // Remove the listener you previously added
                //todo- if only want a one time check i think remove
                locationManager.removeUpdates(locationListener);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        return loc;
    }

    //turns Location into string
    public String getPlace(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                return stateName + "\n" + cityName + "\n" + countryName;
            }
            return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    private void getLocation() {

        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);


//where its happening. i think calls onLocationChanged
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

    }
}
