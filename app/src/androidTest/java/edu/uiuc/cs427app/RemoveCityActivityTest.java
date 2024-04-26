package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RemoveCityActivityTest {

    @Rule
    public ActivityTestRule<AddCityActivity> activityRule =
            new ActivityTestRule<AddCityActivity>(AddCityActivity.class, true, false);

    //Test to validate that a city can be removed
    //from the list given a username and city name
    @Test
    public void testRemoveCity() {
        try {
            Intent city = new Intent();
            String cityName1 = "FakeCityX001";
            String username1 = "fakeuser001";

            city.putExtra("username", username1);

            //add a city using the button
            activityRule.launchActivity(city);

            Thread.sleep(3000);
            onView(withId(R.id.cityNameToAdd)).perform(typeText(cityName1));

            Thread.sleep(3000);
            onView(withId(R.id.addCityButton)).perform(click());

            // Check if the city name is in the list
            onView(withText(cityName1)).check(matches(isDisplayed()));
            Thread.sleep(3000);

            // Remove the city
            onView(withId(R.id.buttonRemoveLocation)).perform(click());
            onView(withId(R.id.cityNameToRemove)).perform(typeText(cityName1));
            Thread.sleep(3000);
            onView(withId(R.id.removeCityButton)).perform(click());

            // Check if the city name is removed from the list
            onView(withText(cityName1)).check(doesNotExist());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
