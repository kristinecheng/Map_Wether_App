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
public class SignupTest {
    @Rule
    public ActivityTestRule<LoginActivity>loginRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class, true, false);
    @Rule
    public ActivityTestRule<MainActivity>mainRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    @Test
    public void testValidInput() {
        try {
            Intent intent = new Intent();
            String email = "test@example.com";
            String username = "testUser";
            String password = "testPassword";

            intent.putExtra("email", email);
            intent.putExtra("username", username);
            intent.putExtra("password", password);

            loginRule.launchActivity(intent);

            Thread.sleep(3000);

            onView(withId(R.id.signUpEmailEditText)).perform(typeText(email), closeSoftKeyboard());
            onView(withId(R.id.signUpUsernameEditText)).perform(typeText(username), closeSoftKeyboard());
            onView(withId(R.id.signUpPasswordEditText)).perform(typeText(password), closeSoftKeyboard());

            onView(withId(R.id.signUpButton)).perform(click());
            // Add assertions for the success scenario, e.g., checking if the Toast for successful sign-up is displayed.
            onView(withText("Sign Up successful!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidInput() {
        try {
            Intent intent = new Intent();
            // Provide invalid input to intentionally fail sign-up
            String email = "";
            String username = "";
            String password = "";

            intent.putExtra("email", email);
            intent.putExtra("username", username);
            intent.putExtra("password", password);

            loginRule.launchActivity(intent);

            Thread.sleep(3000);

            onView(withId(R.id.signUpEmailEditText)).perform(typeText(email), closeSoftKeyboard());
            onView(withId(R.id.signUpUsernameEditText)).perform(typeText(username), closeSoftKeyboard());
            onView(withId(R.id.signUpPasswordEditText)).perform(typeText(password), closeSoftKeyboard());

            onView(withId(R.id.signUpButton)).perform(click());
            // Add assertions for the failure scenario, e.g., checking if the Toast for sign-up failure is displayed.
            onView(withText("Sign Up failed!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
