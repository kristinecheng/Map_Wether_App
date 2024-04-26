package edu.uiuc.cs427app;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// Page to display Weather INfo for selected city
public class WeatherActivity extends AppCompatActivity {
    //public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private double latitude;
    private double longitude;
    // private int dateTime;
    private String cityName;
    private String username;

    private Dialog weatherview;
    private String weatherInfo;

    // setting up the weather display page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Theme color, need to be set before setContentView to avoid layout inflation
        ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Initial weather popup view
        weatherview = new Dialog(this);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        cityName = getIntent().getStringExtra("city").toString();
        weatherInfo = getIntent().getStringExtra("weatherInfo").toString();

        String welcome = "Welcome to "+cityName;
        //String cityWeatherInfo = "Detailed information about the weather of "+cityName;

        // Intent payload with username
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        // Displaying welcome, and the custom weather string
        String cityWeatherInfo = weatherInfo ; //"weather info for this city" ;
        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);

        // back button, to return to Main
        Button buttonBack = findViewById(R.id.buttonBack);

        // setting back button to go back to main
        buttonBack.setOnClickListener(new View.OnClickListener() {

            // When user presses back, go back to Main
            @Override
            public void onClick(View v) {
                // Intent to go back to MainActivity
                Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish(); // Optional: if you want to close the current activity
            }
        });

    }

}

