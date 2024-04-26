package edu.uiuc.cs427app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

// Data Access Object for ROOM
@Dao
public interface CityDAO {

    //gets a list of city names based on user name
    @Query("SELECT city_nameDB FROM CityEntry WHERE user_nameDB LIKE :currentUser") //  LIMIT 1")
    List<String>  citiesByUserName(String currentUser );

    // find a user city pair for deletion
    @Query("SELECT * FROM CityEntry WHERE user_nameDB LIKE :currentUser and city_nameDB like :currentCity  LIMIT 1")
    CityEntry findByNameCityPair(String currentUser, String currentCity );

    //insert a single cityentry
    @Insert
    void  insert(CityEntry cityEntry) ;

    //delete a specific cityentry
    @Delete
    void delete(CityEntry cityEntry);
}



/* Code Bibliography/Acknowledgements - References used to help with code

** This tutorial was used to help get started with ROOM database
https://www.youtube.com/watch?v=ChD4GkS5Kds
Room Database using Java | Android Tutorial

** This page was used to help find the “.allowMainThreadQueries()” option to allow ROOM queries to be synchronous
https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread

** Android documentation pages also used for ROOM
https://developer.android.com/guide/background/asynchronous/java-threads
https://developer.android.com/reference/java/util/concurrent/ExecutorService
https://developer.android.com/training/run-background-service/create-service
https://developer.android.com/training/data-storage/room
https://developer.android.com/reference/java/lang/Runnable
https://developer.android.com/training/data-storage/room/async-querieshttps://developer.android.com/training/data-storage/room/async-queries

*/
