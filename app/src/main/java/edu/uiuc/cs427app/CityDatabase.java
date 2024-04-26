package edu.uiuc.cs427app;


import androidx.room.Database;
import androidx.room.RoomDatabase;

// creating the CityDatabase with CityEntry records
// used exportSchema = false because it kept giving a warning
@Database(entities = {CityEntry.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDAO cityDao();
}
