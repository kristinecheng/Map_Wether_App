package edu.uiuc.cs427app;

// https://developer.android.com/training/data-storage/room
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// creating entries for the city table, by username + city
@Entity
public class CityEntry {


    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_nameDB")
    public String userNameDB;

    @ColumnInfo(name = "city_nameDB")
    public String cityNameDB;

    // ignore is for anything that won't be a column
    @Ignore
    public CityEntry() {
    }

    //constructor for cityentry accepting a username database name and cityname database
    public CityEntry(String userNameDB, String cityNameDB) {
        this.userNameDB = userNameDB;
        this.cityNameDB = cityNameDB;
        this.uid = 0 ;
    }

    //returns name of username database
    public String getUserName() {
        return userNameDB;
    }

    //sets a new username database name
    public void setUserName(String userNameDB) {
        this.userNameDB = userNameDB;
    }

    //returns name of cityname database
    public String getCityName() {
        return cityNameDB;
    }
}
