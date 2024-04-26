package edu.uiuc.cs427app;

// Special output object for FetchWeatherTask1, with all info needed
public class WeatherOutput {

    // Latitutde/ longitude also used by Map
    private double latitude;
    private double longitude;

    // city name, though this is also inputted
    private String cityName ;

    // weather info, a custom string concateninating all info
    private String weatherInfo ;

    // contructor : object instantiation to create a new version of this object
    public WeatherOutput(double latitude, double longitude, String cityName, String weatherInfo) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName ;
        this.weatherInfo = weatherInfo ;
    }

    // getting latitude
    public double getLatitude() {
        return latitude;
    }

    // getting longitude
    public double getLongitude() {
        return longitude;
    }

    // getting name of city
    public String getCityName() {
        return cityName;
    }

    // getting all weather data in a formatted string
    public String getWeatherInfo() {
        return weatherInfo;
    }
}
