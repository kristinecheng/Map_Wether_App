package edu.uiuc.cs427app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import android.os.Handler;
import android.os.Looper;


import android.view.View.OnClickListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//define city removal function
public class RemoveCityActivity  extends AppCompatActivity implements View.OnClickListener{

    // initialize text input and button and username
    EditText cityNameToDelete ;
    Button buttonDeleteCity ; //, buttonDisplayData ;
    String username ;

    // just initailize, don't define
    CityDAO cityDAO;
    CityDatabase cityDatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Theme color, need to be set before setContentView to avoid layout inflation
        ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removecity);

        // also initialize the city data base -
        cityDatabase = Room.databaseBuilder(this, CityDatabase.class, "cityDB").build();
        // already initialized above because if initialized inside here, it crashes
        cityDAO = cityDatabase.cityDao();

        // username was passed in as an intent from MAIN
        username = getIntent().getStringExtra("username").toString();
        String welcome = "Please type in a city to remove for " +  username ;

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        welcomeMessage.setText(welcome);

        // add city button
        buttonDeleteCity = findViewById(R.id.removeCityButton);
        cityNameToDelete = findViewById(R.id.cityNameToRemove) ;
        //buttonDisplayData= findViewById(R.id.displayDataButton);

        //buttonAddCity.setOnClickListener(this);

        // displays all data in data base, not just for this user
        /*
        buttonDisplayData.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                getFullListInBackground() ;
            }
        });

         */

        // deletes a city
        buttonDeleteCity.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // user input is the city name
                String cityNameToAddStr = cityNameToDelete.getText().toString() ;

                // use the citname with username to create a new ROOM entry
                CityEntry deleteCity = new CityEntry(username, cityNameToAddStr) ;

                // must be running in background, to avoid crashing app
                deleteCityInBackground(deleteCity) ;

                // Go back to MainActivity
                Intent intent;
                //intent = new Intent(this, MainActivity.class);
                intent = new Intent(RemoveCityActivity.this, MainActivity.class);
                intent.putExtra("username", username );
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onClick(View view) {
        // Clicking addCity adds the City and returns to the main screen
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Add City: this only seems to work in background task
    public void deleteCityInBackground(CityEntry mycitytodelete){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler; //multiple packages have Handler, becareful what to import
        handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background deleting of city

                // find the actual record that matches
                CityEntry cityRecordToDelete = cityDatabase.cityDao().findByNameCityPair(
                        mycitytodelete.getUserName(), mycitytodelete.getCityName() );

                // delete the record, if it exists
                if (cityRecordToDelete != null) {
                    cityDatabase.cityDao().delete(cityRecordToDelete);


                    // after delete, display toast method
                    handler.post(new Runnable(){
                        @Override
                        public void run(){
                            Toast.makeText(RemoveCityActivity.this, "Removed "+ mycitytodelete.getCityName()
                                    + " for " + mycitytodelete.getUserName(), Toast.LENGTH_SHORT).show() ;
                        }
                    }) ;
                }


            }
        });
    }





}
