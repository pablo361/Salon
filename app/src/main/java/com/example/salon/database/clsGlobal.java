package com.example.salon.database;

import android.app.Application;

public class clsGlobal extends Application {
    public static final String BASE_URL = "http://192.168.100.7/idm/";


    private static clsGlobal singleton;
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static clsGlobal getInstance() {
        return singleton;
    }
}
