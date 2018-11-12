package com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend;

import android.os.AsyncTask;

import com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource.Firebase_DBManager;

public class BackendFactory {
    //check if works
    private static /*final*/ Firebase_DBManager ourInstance;

    public static Firebase_DBManager getInstance() {
        return ourInstance;
    }

    public BackendFactory() {
        if (ourInstance == null)
            ourInstance = new Firebase_DBManager();
    }



}
