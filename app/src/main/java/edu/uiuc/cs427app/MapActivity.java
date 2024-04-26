package edu.uiuc.cs427app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Special activity page for map, interactive, with Lat/Long and city name displayed
public class MapActivity extends AppCompatActivity {
    // special webview to show the map
    private WebView webView;

    // showing name of city
    private String cityName;

    // name of user
    private String username;

    // when creating this activity, will need API to get map data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Theme color, need to be set before setContentView to avoid layout inflation
        ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Process the Intent payload that has opened this Activity
        double latitude = getIntent().getDoubleExtra("latitude",0.0);
        double longitude = getIntent().getDoubleExtra("longitude",0.0);
        cityName = getIntent().getStringExtra("city").toString();
        username = getIntent().getStringExtra("username");
        webView = findViewById(R.id.webView);

        // getting city name to show with map
        TextView CityNameTextView = findViewById(R.id.CityNameTextView);
        CityNameTextView.setText(cityName);

        // getting Lat/Long to show with map
        TextView LatLongTextView = findViewById(R.id.LatLongTextView);
        LatLongTextView.setText("Latitude: " +Double.toString(latitude) +
                " | Longitude: " + Double.toString(longitude));

        // Enable JavaScript in the WebView
        webView.getSettings().setJavaScriptEnabled(true);

        // Set a WebViewClient to handle page navigation and open links within the WebView
        webView.setWebViewClient(new WebViewClient());

        // Load a map URL into the WebView
        String url = "https://www.google.com/maps/@" + Double.toString(latitude) + "," +
                Double.toString(longitude) + ",11.75z?entry=ttu";

        // showing the chosen URL in webview
        webView.loadUrl(url);

        // back button, to return to Main
        Button buttonBack = findViewById(R.id.buttonBack);

        // setting back button to go back to main
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go back to MainActivity
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish(); // Optional: if you want to close the current activity
            }
        });

    }
    // making sure the general Android back button goes back to Main
    @Override
    public void onBackPressed() {
        // Override the back button to return to the DetailsActivity
        ThemeUtils.onActivityCreateSetTheme(this, ThemeUtils.currentTheme.getName());
        //ThemeUtils.setDarkMode(ThemeUtils.currentTheme.isDark_mode());
        Intent intent;
        //intent = new Intent(this, DetailsActivity.class);
        // Will go back to Main if Back Button pressed
        intent = new Intent(this, MainActivity.class);

        // The DetailsActivity expects username and city as intents
        intent.putExtra("username", username);
        intent.putExtra("city", cityName);
        startActivity(intent);
        finish();
    }

}
