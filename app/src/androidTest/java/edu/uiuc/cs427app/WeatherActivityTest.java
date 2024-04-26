package edu.uiuc.cs427app;

import android.content.Intent;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.WeatherActivity;
import edu.uiuc.cs427app.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class WeatherActivityTest {

    @Rule
    public ActivityTestRule<WeatherActivity> activityRule =
            new ActivityTestRule<WeatherActivity>(WeatherActivity.class, true, false);

    @Test
    public void checkWelcomeMessageDisplaysCorrectly() {
        // Mock Intent with extras
        Intent intent = new Intent();
        String testCityName = "City1";
        intent.putExtra("city", testCityName);
        intent.putExtra("weatherInfo", "Sunny with a high of 25°C");

        // Launch activity with intent
        activityRule.launchActivity(intent);

        // Check if the TextView for the welcome message displays the correct city name
        String expectedWelcomeText = "Welcome to " + testCityName;
        onView(withId(R.id.welcomeText)).check(matches(withText(expectedWelcomeText)));
    }

    @Test
    public void checkWeatherInfoDisplaysCorrectly() {
        // Mock Intent with extras
        Intent intent = new Intent();
        String testWeatherInfo = "Sunny with a high of 25°C";
        intent.putExtra("city", "City1");
        intent.putExtra("weatherInfo", testWeatherInfo);

        // Launch activity with intent
        activityRule.launchActivity(intent);

        // Check if the TextView for the weather info is displaying the correct information
        onView(withId(R.id.cityInfo)).check(matches(withText(testWeatherInfo)));
    }

    @Test
    public void testTwoExampleCities() {
        // Test for the first city
        Intent intentCity1 = new Intent();
        String cityName1 = "Springfield";
        String weatherInfo1 = "Cloudy with a chance of meatballs";
        intentCity1.putExtra("city", cityName1);
        intentCity1.putExtra("weatherInfo", weatherInfo1);

        // Launch activity for the first city
        activityRule.launchActivity(intentCity1);

        // Check if the TextView for the city name and weather info displays correctly for city 1
        onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to " + cityName1)));
        onView(withId(R.id.cityInfo)).check(matches(withText(weatherInfo1)));

        // Close the activity before starting the next test
        activityRule.finishActivity();

        // Test for the second city
        Intent intentCity2 = new Intent();
        String cityName2 = "Champaign";
        String weatherInfo2 = "Sunny with a high of 75°F";
        intentCity2.putExtra("city", cityName2);
        intentCity2.putExtra("weatherInfo", weatherInfo2);

        // Launch activity for the second city
        activityRule.launchActivity(intentCity2);

        // Check if the TextView for the city name and weather info displays correctly for city 2
        onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to " + cityName2)));
        onView(withId(R.id.cityInfo)).check(matches(withText(weatherInfo2)));
    }
}
