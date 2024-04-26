package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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


import static androidx.test.espresso.assertion.ViewAssertions.matches;

// This will test if user can successfully log off
@RunWith(AndroidJUnit4.class)
public class LogoutTest {

    // to go to the MainActivity, because will logoff from there
    @Rule
    public ActivityTestRule<MainActivity> mainRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    // to test the logoff activity
    @Test
    public void testLogoffAction() {
        try {
            // going to main page, with same username as other tests
            Intent mainIntent = new Intent();
            mainIntent.putExtra("username", "kkadi2");
            mainRule.launchActivity(mainIntent);
            Thread.sleep(3000);

            // attempting to click the logoff button
            onView(withId(R.id.buttonLogout)).perform(click());

            // only the Login screen has "Dark Mode" text
            // so confirm this appears to make sure we're really on the login screen
            onView(withId(R.id.Dark_Mode)).check(matches(withText("Dark Mode")));

            // Need to confirm that something random will fail the test
            // MUST LEAVE COMMENTED OUT TO PASS
            //onView(withId(R.id.Dark_Mode)).check(matches(withText("RANDOM TEXT")));

            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
