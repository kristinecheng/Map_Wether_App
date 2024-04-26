package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class AddCityTest {

    public static final String CITY_NAME = "Dublin, CA";

    /**
     * Use {@link ActivityScenarioRule} to create and launch the activity under test.
     */
    @Rule
    public ActivityTestRule<MainActivity>mainRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    @Test
    public void testAddNewCity() {
        try {
            Intent mainIntent = new Intent();
            mainIntent.putExtra("username", "kkadi2");
            mainRule.launchActivity(mainIntent);
            Thread.sleep(3000);
            onView(withId(R.id.buttonAddLocation)).perform(click());
            // Type text and then press the button.
            onView(withId(R.id.cityNameToAdd)).perform(typeText(CITY_NAME),
                    closeSoftKeyboard());
            onView(withId(R.id.addCityButton
            )).perform(click());

            // Verify that City name is added in the main view
            //onView(allOf(withId(R.id.textView14), withText(CITY_NAME)))
            onView(withText(CITY_NAME)).check(matches(isDisplayed()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
