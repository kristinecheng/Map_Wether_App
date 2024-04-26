package edu.uiuc.cs427app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.room.Room;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.TextView;
//import android.widget.LinearLayout;
import android.widget.GridLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

// main page of app after login, from here can view Weather/Map by city
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<Integer> cityButtonIDs = new ArrayList<>();
    private List<String> allUserCities;

    // define username here so it can be used by onclick functions
    private String username ;

    // just initailize Data Access Object, don't define yet
    CityDAO cityDAO;

    // same for ROOM database, initialize to define later
    CityDatabase cityDatabase ;

    // Creating MainActivity, includes main thread call to ROOM, also API call to Map if button clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Theme color, need to be set before setContentView to avoid layout inflation
        ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
        ThemeUtils.setDarkMode(ThemeUtils.currentTheme.isDark_mode());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // also innitialize the city data base -
        cityDatabase = Room.databaseBuilder(this, CityDatabase.class, "cityDB").allowMainThreadQueries().build();
        // already initilzied above because if initailized inside here, it crashes
        cityDAO = cityDatabase.cityDao();

        // Retrieve Username
        Intent intent = getIntent();
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        username = intent.getStringExtra("username");
        Log.d("I", "xxy Username: " + username);
        usernameTextView.setText("Username: " + username);

        // Initializing the UI components
        Button buttonNew = findViewById(R.id.buttonAddLocation);
        Button buttonRemove = findViewById(R.id.buttonRemoveLocation);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        // buttons to add/remove cities, logout
        buttonNew.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        //LinearLayout buttonContainer = findViewById(R.id.cityButtonContainer);
        GridLayout buttonContainer = findViewById(R.id.cityButtonContainer);

        // Use Data Access Object to query ROOM database
        allUserCities =  cityDAO.citiesByUserName( username );

        //list out the cities in the UI
        String city;
        Integer buttonID;
        Integer buttonID2;

        // create a label and two buttons per city
        for (int i = 0; i < allUserCities.size(); i++) {
            city = allUserCities.get(i);

            //  layout special for city
            GridLayout.Spec cityRowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            GridLayout.Spec cityColSpec = GridLayout.spec(0, GridLayout.FILL, 1f);

            // parameters for city
            GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(cityRowSpec, cityColSpec);
            params1.width = 0;
            params1.height = GridLayout.LayoutParams.WRAP_CONTENT;

            // City label, in the first column
            TextView cityTextView = new TextView(this);
            cityTextView.setText(city);
            cityTextView.setLayoutParams(params1);
            buttonContainer.addView(cityTextView);

            // This will be the Weather button
            Button dynamicButton = new Button(this); // "this" refers to the current context (usually the Activity)

            // adjusted this to be an integer, becuase generateViewId() just keeps counting up
            // and it doesn't reset to 1 each time MainActivity loads, so that was a problem
            buttonID = i+1 ; // = View.generateViewId();
            cityButtonIDs.add(buttonID);
            dynamicButton.setId(buttonID);
            //dynamicButton.setText(city); // sets the button text to the name of the city
            dynamicButton.setText("Weather"); // actually, this is weather button
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT; // You can also use a specific height
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // 1f indicates equal distribution
            dynamicButton.setLayoutParams(params);
            buttonContainer.addView(dynamicButton);
            dynamicButton.setOnClickListener(this);

            // for next button - will be MAP
            Button dynamicButton2 = new Button(this); // "this" refers to the current context (usually the Activity)

            // another button, will add it such that ID starts second half of list
            buttonID2 = i+1 +allUserCities.size(); // = View.generateViewId();

            // add MAP button
            cityButtonIDs.add(buttonID2);
            dynamicButton2.setId(buttonID2);
            dynamicButton2.setText("Map"); // sets the button text to the name of the city

            // need new PARAMs for map, but are same as for weather
            GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
            params2.width = 0;
            params2.height = GridLayout.LayoutParams.WRAP_CONTENT; // You can also use a specific height
            params2.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // 1f indicates equal distribution
            dynamicButton2.setLayoutParams(params2);

            // Add the second button for map
            buttonContainer.addView(dynamicButton2);
            dynamicButton2.setOnClickListener(this);


        }

    }

    // special logic for button clicks
    @Override
    public void onClick(View view) {
        //Retrieve current user's theme reference
        SharedPreferences ref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int color = ref.getInt(username + "themecolor", 0);
        ThemeUtils.currentTheme.setName(color);
        boolean darkmode = ref.getBoolean(username + "darkmode", false);
        ThemeUtils.currentTheme.setDark_mode(darkmode);

        Intent intent;
        switch (view.getId()) {
            case R.id.buttonAddLocation:
                // Initial theme for the coming up activity
                ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
                // Will go to the AddCity screen, passes username
                intent = new Intent(this, AddCityActivity.class);
                intent.putExtra("username", username );
                startActivity(intent);
                finish();
                break;
            case R.id.buttonRemoveLocation:
                // Initial theme for the coming up activity
                ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
                // Will go to the RemoveCity screen, passes username
                intent = new Intent(this, RemoveCityActivity.class);
                intent.putExtra("username", username );
                startActivity(intent);
                finish();
                break;
            case R.id.buttonLogout:
                intent = new Intent(this, LoginActivity.class); // Assuming your login activity is named LoginActivity
                startActivity(intent);
                // Set the login page back to default setting
                ThemeUtils.currentTheme.setDark_mode(false);
                ThemeUtils.setDarkMode(ThemeUtils.currentTheme.isDark_mode());
                finish(); // This closes the MainActivity, so pressing back won't return to this screen after logging out
                break;
        }
        // for the city buttons, will go to Map/Weather Activity pages
        if (cityButtonIDs.contains(view.getId())){
            // Initial theme for the coming up activity
            ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
            //ThemeUtils.setDarkMode(ThemeUtils.currentTheme.isDark_mode());

            // find the ID selected to determine city
            Integer ID_selected = view.getId() ;
            String city ;

            // only go to Weather page if it's in first half city list
            if (ID_selected<=allUserCities.size()) {
                city = allUserCities.get(view.getId() - 1);

                // special function to get input for Weather, then go to Weather
                getLatLongForWeather(city) ;
            }
            // go to Map for second half of city list
            else{
                city = allUserCities.get(view.getId() - 1 - allUserCities.size());

                // special function to get input for Map, then go to Map
                getLatLongForMap(city ) ;
            }
        }
    }


    // Getting Latitude/Longitude for map
    private void getLatLongForMap(String city) {

        // method call for the special version of Weather task foro map
        FetchWeatherTask1 fetchWeatherTask = new FetchWeatherTask1() {
            // after execution, will go to next activity
            @Override
            protected void onPostExecute(WeatherOutput result) {
                // get Latitude/Longitude
                double latitude = result.getLatitude();
                double longitude = result.getLongitude();
                String city = result.getCityName();

                // moving to Map, cinlduing extra info for Lat/Long
                //Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                Intent intent = new Intent(MainActivity.this, MapActivity.class);

                // send this to map for display
                intent.putExtra("latitude", latitude );
                intent.putExtra("longitude", longitude);

                // for either buttons, pass city and username
                intent.putExtra("username", username);
                intent.putExtra("city", city);
                startActivity(intent);
                finish();


            }
        };
        // method call to get the Lat/Long from weather, will update once complete
        fetchWeatherTask.execute(city) ;
    }

    // Getting WeatherData for weather
    private void getLatLongForWeather(String city) {

        // method call for the special version of Weather task for map, using it fro weather now
        FetchWeatherTask1 fetchWeatherTask = new FetchWeatherTask1() {
            // after execution, will go to next activity
            @Override
            protected void onPostExecute(WeatherOutput result) {
                // get Latitude/Longitude
                double latitude = result.getLatitude();
                double longitude = result.getLongitude();
                String city = result.getCityName();
                String weatherInfo = result.getWeatherInfo();

                // moving to Map, cinlduing extra info for Lat/Long
                //Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);

                // lat/long, though not needed for weather
                intent.putExtra("latitude", latitude );
                intent.putExtra("longitude", longitude);

                // for either buttons, pass city and username
                intent.putExtra("username", username);
                intent.putExtra("city", city);

                // special string which has all weather info
                intent.putExtra("weatherInfo", weatherInfo);
                startActivity(intent);
                finish();


            }
        };
        // method call to get the Lat/Long from weather, will update once complete
        fetchWeatherTask.execute(city) ;
    }





}