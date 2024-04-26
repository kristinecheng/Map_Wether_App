package edu.uiuc.cs427app;

import android.content.Intent;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity>loginRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class, true, false);
    @Rule
    public ActivityTestRule<MainActivity>mainRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    @Test
    public void checkLoginInputInfoSuccessful()
    {
        try
        {
            // Temporary user info for testing
            Intent intent = new Intent();
            String username = "cycheng4";
            String password = "123456";

            intent.putExtra("username", username);
            intent.putExtra("password", password);

            loginRule.launchActivity(intent);

            Thread.sleep(3000);
            // Check if login button displayed
            onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
            // Check if the textview of user info matches the correct username and password
            onView(withId(R.id.usernameEditText)).perform(typeText(username), closeSoftKeyboard());
            onView(withId(R.id.passwordEditText)).perform(typeText(password), closeSoftKeyboard());
            // Check if login action perform
            onView(withId(R.id.loginButton)).perform(click());

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void checkLoginActivitySuccessful()
    {
        try
        {
            // New intent for user login info
            Intent login = new Intent();
            Intent main = new Intent();
            String username = "kkadi2";
            String password = "123456";

            login.putExtra("username", username);
            main.putExtra("username", username);
            login.putExtra("password", password);

            loginRule.launchActivity(login);

            Thread.sleep(3000);
            // Check if the textview of user info matches the correct username and password
            onView(withId(R.id.usernameEditText)).perform(typeText(username), closeSoftKeyboard());
            onView(withId(R.id.passwordEditText)).perform(typeText(password), closeSoftKeyboard());
            // Check if login action perform
            onView(withId(R.id.loginButton)).perform(click());

            mainRule.launchActivity(main);
            // Check if the username under main activity is same as the login
            onView(withId(R.id.usernameTextView)).check(matches(withText("Username: " + username)));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
