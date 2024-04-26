package edu.uiuc.cs427app;

import android.os.AsyncTask;

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

// A new version of FetchWeatherTask, but customized for the Map/Weather screens
// to get Lat/Long for Map display
// also gets weatherInfo for weather
// @param city - will input cityname
// @output WeatherOutput object, which has all info needed for display
public class FetchWeatherTask1 extends AsyncTask<String, Void, WeatherOutput> {

    @Override
    protected WeatherOutput doInBackground(String... cities) {
        final String API_KEY = "f0ac808546e59c6588d9b4b5def1c9be";
        final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
        String city = cities[0];
        String urlQueryString = WEATHER_URL + "?q=" + city + "&appid=" + API_KEY;

        // create vairables
        double latitude;
        double longitude;
        String weatherInfo ;

        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonResponse = new JSONObject(result.toString());
                // You can extract more information here if needed
                String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
                JSONObject coords = jsonResponse.getJSONObject("coord");
                latitude = coords.getDouble("lat");
                longitude = coords.getDouble("lon");

                int dt = jsonResponse.getInt("dt");
                int timeZone = jsonResponse.getInt("timezone");
                int realDT = dt + timeZone;
                // Parse date time information
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                String formattedDT = Instant.ofEpochSecond(realDT).atZone(ZoneId.of("UTC")).format(formatter);

                // Temperature Info
                JSONObject mainObj = jsonResponse.getJSONObject("main");
                double temperature = mainObj.getInt("temp") - 275.15;
                //temperature = temperature - 275.15;
                double minTemp = mainObj.getInt("temp_min") - 275.15;
                double maxTemp = mainObj.getInt("temp_max") - 275.15;
                int humidity = mainObj.getInt("humidity");
                // Wind Info
                JSONObject windObj = jsonResponse.getJSONObject("wind");
                float windSpeed = windObj.getLong("speed");
                // Cloud Info
                JSONObject cloudOnj = jsonResponse.getJSONObject("clouds");
                int cloud = cloudOnj.getInt("all");

                StringBuilder info = new StringBuilder();
                info.append("\nCurrent Date & Time: " + formattedDT);
                info.append("\nCurrent Weather: " + weatherDescription);
                info.append("\nTemperature: " + (int) temperature + "c");
                info.append("\nH: " + (int) maxTemp + "c      L: " + (int) minTemp + "c");
                info.append("\nHumidity: " + humidity + "%");
                info.append("\nWind Speed: " + windSpeed + "m/s");
                info.append("\nCloudiness: " + cloud);

                weatherInfo = info.toString();

                // returning a special WeatherOutput object
                return new WeatherOutput(latitude, longitude, city, weatherInfo);  //weatherInfo;
                //return weatherDescription + "\n" + formattedDT + "\n" + " \n At latitiude: " + latitude + " and at longitude: " + longitude;
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            //return "Error: " + e.getMessage();
            // if failing, just use 0
            return new WeatherOutput(0.0, 0.0, city, ""); //weatherInfo;

        }
    }
}




