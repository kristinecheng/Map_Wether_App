package edu.uiuc.cs427app;

import android.content.Intent;
import android.webkit.WebView;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.uiuc.cs427app.MapActivity;
import edu.uiuc.cs427app.R;

import java.util.Arrays;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


@RunWith(Parameterized.class)
public class LocationFeatureTest {

    // Define the parameters using a static method annotated with @Parameters
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 34.0522, -118.2437, "Los Angeles", "Travis"},
                { 41.85, -87.65, "Chicago", "Travis"}
        });
    }

    private final double mlatitude;
    private final double mlongitude;
    private final String mcityName;
    private final String muserName;

    // Constructor to save parameters for tests
    public LocationFeatureTest(double latitude, double longitude,
                               String cityName, String userName) {

        mlatitude = latitude;
        mlongitude = longitude;
        mcityName = cityName;
        muserName = userName;
    }

    @Rule
    public ActivityTestRule<MapActivity> activityRule =
            new ActivityTestRule<MapActivity>(MapActivity.class, true, false);

    @Test
    public void checkLatLongTextBox() {
        try {
            // Create intent
            Intent intent = new Intent();

            intent.putExtra("latitude", mlatitude);
            intent.putExtra("longitude", mlongitude);
            intent.putExtra("city", mcityName);
            intent.putExtra("username", muserName);

            // Launch activity with intent
            activityRule.launchActivity(intent);

            // Sleep
            Thread.sleep(5000);

            // Check if the TextView for the lat/long message displays correctly
            String expectedLatLongText = "Latitude: " + Double.toString(mlatitude) +
                    " | Longitude: " + Double.toString(mlongitude);
            onView(withId(R.id.LatLongTextView)).check(matches(withText(expectedLatLongText)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkCityNameTextBox() {

        try{
            // Create intent
            Intent intent = new Intent();

            intent.putExtra("latitude", mlatitude );
            intent.putExtra("longitude", mlongitude);
            intent.putExtra("city", mcityName);
            intent.putExtra("username", muserName);

            // Launch activity with intent
            activityRule.launchActivity(intent);

            // Sleep
            Thread.sleep(5000);

            // Check if the TextView for the CityName matches the city name
            onView(withId(R.id.CityNameTextView)).check(matches(withText(mcityName)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

