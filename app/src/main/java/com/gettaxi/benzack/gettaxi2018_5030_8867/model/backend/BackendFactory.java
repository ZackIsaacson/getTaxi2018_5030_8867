package com.gettaxi.benzack.gettaxi2018_5030_8867.model.backend;

import com.gettaxi.benzack.gettaxi2018_5030_8867.model.datasource.Firebase_DBManager;

public class BackendFactory {
    private static /*final*/ Firebase_DBManager ourInstance = new Firebase_DBManager();

    public static Firebase_DBManager getInstance() {
        return ourInstance;
    }

    private BackendFactory() {
        if(ourInstance==null)
            ourInstance=new Firebase_DBManager();


    }
}
