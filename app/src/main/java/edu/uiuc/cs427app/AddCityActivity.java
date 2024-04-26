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

public class AddCityActivity  extends AppCompatActivity implements View.OnClickListener{

    // initialize text input and button and username
    EditText cityNameToAdd ;
    Button buttonAddCity, buttonDisplayData ;
    String username ;

    // also innitialize the city data base - COMMENTED OUT BECAUSE APP KEPT CRASHING
    //CityDatabase cityDatabase = Room.databaseBuilder(this, CityDatabase.class, "cityDB").build();
    //CityDAO cityDAO = cityDatabase.cityDao();

    // just initailzie, don't define
    CityDAO cityDAO;
    CityDatabase cityDatabase ;

    //CityDatabase cityDB ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Theme color, need to be set before setContentView to avoid layout inflation
        ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcity);

        // also initialize the city data base -
        cityDatabase = Room.databaseBuilder(this, CityDatabase.class, "cityDB").build();
        // already initialized above because if initailized inside here, it crashes
        cityDAO = cityDatabase.cityDao();

        // username was passed in as an intent from MAIN
        username = getIntent().getStringExtra("username").toString();
        String welcome = "Please add a city for " +  username ;

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        welcomeMessage.setText(welcome);

        // add city button
        buttonAddCity = findViewById(R.id.addCityButton);
        cityNameToAdd = findViewById(R.id.cityNameToAdd) ;

        // adds a city
        buttonAddCity.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // user input is the city name
                String cityNameToAddStr = cityNameToAdd.getText().toString() ;

                // use the cityname with username to create a new ROOM entry
                CityEntry newCity = new CityEntry(username, cityNameToAddStr) ;

                // tester code with fake username
                //CityEntry newCity = new CityEntry("any_user", cityNameToAddStr) ;

                // must be running in background, to avoid crashing app
                // only add if string has more than 0 chars
                if (cityNameToAddStr.length() > 0) {
                    addCityInBackground(newCity);
                }

                // Go back to MainActivity
                Intent intent;
                //intent = new Intent(this, MainActivity.class);
                intent = new Intent(AddCityActivity.this, MainActivity.class);
                intent.putExtra("username", username );
                startActivity(intent);
                finish();

            }
        });

    }


    //click activity function
    @Override
    public void onClick(View view) {
        // Clicking addCity adds the City and returns to the main screen
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Add City: this only seems to work in background task
    public void addCityInBackground(CityEntry mycitytoadd){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler; //multiple packages have Handler, be careful what to import
        handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background adding of city
                cityDatabase.cityDao().insert(mycitytoadd) ;

                // after add, display toast method
                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(AddCityActivity.this, "Added "+ mycitytoadd.getCityName()
                            + " for " + mycitytoadd.getUserName(), Toast.LENGTH_SHORT).show() ;
                    }
                }) ;

            }
        });
    }
}
