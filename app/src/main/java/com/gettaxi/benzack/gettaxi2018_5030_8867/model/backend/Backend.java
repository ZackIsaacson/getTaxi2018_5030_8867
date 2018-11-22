package com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend;

import android.content.Context;
import android.drm.DrmStore;
import android.location.Location;
import android.os.AsyncTask;

import com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource.Firebase_DBManager;
import com.gettaxi.benzack.gettaxi2018_5030_8867.model.entities.Driver;
import com.google.android.gms.location.places.Place;

import java.util.Date;
import java.util.List;

public interface Backend {

    public void addRide(String currentLocation, String destinationLoaction, String email, String phone,
                        final Backend.Action action);


    public interface Action {
        public void onSuccess(String act);

        public void onProgress(String act);

        public void onFailure(String act);
    }
    public abstract class BackendUploadAsyncTask extends AsyncTask<Void, Void, Void>
    {
        public abstract Void doInBackground(Void... voids);
    }
}
/*
    public List<TaxiDriver> getAllDrivers();
    public void addDriver();
    public List<TaxiDriver> getAllUnTreatedRides();
    public List<TaxiDriver> getFinishedRides();
    public List<TaxiDriver> getUnTreatedRidesPerDriver(TaxiDriver driver);
    public List<TaxiDriver> getUnTreatedRidesPerCity(Place city);   //todo city?
    public List<TaxiDriver> getUnTreatedRidesPerDistanceFromCurrentLocation(long distance);
    public List<TaxiDriver> getRidesPerDate(Date date);  //todo date?
    public List<TaxiDriver> getRidesPerCashCharged(double cashCharged); //todo supposed to be checked automatically)הסכום יחושב אוטמטית ע"פ המרחק בק"מ X תעריף ל- ק"מ, ניתן יהיה לממש גם נוסחה אחרת שלוקחת בחשבון את משך זמן הנסיעה




     קבל את שמות המשתמשים )הנהגים(
 הוסף "נהג".
 קבל את הנסיעות שעדיין לא טופלו.
 קבל את הנסיעות שהסתיימו.
 קבל את הנסיעות שנעשו ע"י נהג מסוים
 קבלת הנסיעות שעדיין לא טופלו שהיעד שלהם בעיר מסוימת.
 קבלת הנסיעות שעדיין לא טופלו שהיעד שלהם במרחק מסוים ממיקום הנהג הנוכחי.
 קבלת נסיעות ע"פ תאריך מסוים.
 קבלת נסיעות ע"פ סכום שנגבה.
)הסכום יחושב אוטמטית ע"פ המרחק בק"מ X תעריף ל- ק"מ, ניתן יהיה לממש גם נוסחה
אחרת שלוקחת בחשבון את משך זמן הנסיעה
     */

