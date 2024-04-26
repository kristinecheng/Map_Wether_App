package edu.uiuc.cs427app;


import android.app.Application;

import androidx.room.Room;

// FOr starting the database with city information
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Starting the ROOM database
        CityDatabase database = Room.databaseBuilder(this, CityDatabase.class, "app-database").build();
        //CityDAO cityDAO = database.cityDao();



    }
}

