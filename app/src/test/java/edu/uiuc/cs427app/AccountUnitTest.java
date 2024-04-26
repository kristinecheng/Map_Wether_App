package edu.uiuc.cs427app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Local unit test, which will execute on the development machine (host).
 * These unit tests focus on the account object
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AccountUnitTest {
    @Test
    public void newAccount_isCorrect() {
        String defaultUsername = "foo";
        String defaultThemeName = "Default";

        String testName = "John Doe";
        String testThemeName = "AwesomeTheme";
        String testCityName = "Detroit";
        String testFeedbackMessage = "5 stars";

        User testUser = new User(testName);
        //Theme testTheme = new Theme(testThemeName);
        List<City> testCities = new ArrayList<>();
        List<Feedback> testFeedback = new ArrayList<>();

        testCities.add(new City(testCityName));
        testFeedback.add(new Feedback(testFeedbackMessage));

        //test default constructor
        Account newAccount = new Account();
        assertNotNull(newAccount);
        assertNotNull(newAccount.getUser());
        assertNotNull(newAccount.getTheme());
        assertNotNull(newAccount.getCities());
        assertNotNull(newAccount.getFeedback());
        assertTrue(newAccount.getUser().getUsername().equals(defaultUsername));
        //assertTrue(newAccount.getTheme().getName().equals(defaultThemeName));
        assertTrue(newAccount.getCities().isEmpty());
        assertTrue(newAccount.getFeedback().isEmpty());

        //constructor with args: user
        newAccount = new Account(testUser);
        assertNotNull(newAccount);
        assertNotNull(newAccount.getUser());
        assertNotNull(newAccount.getTheme());
        assertNotNull(newAccount.getCities());
        assertNotNull(newAccount.getFeedback());
        assertTrue(newAccount.getUser().getUsername().equals(testName));
       // assertTrue(newAccount.getTheme().getName().equals(defaultThemeName));
        assertTrue(newAccount.getCities().isEmpty());
        assertTrue(newAccount.getFeedback().isEmpty());

        /*
        //constructor with args: user, theme
        newAccount = new Account(testUser, testTheme);
        assertNotNull(newAccount);
        assertNotNull(newAccount.getUser());
        assertNotNull(newAccount.getTheme());
        assertNotNull(newAccount.getCities());
        assertNotNull(newAccount.getFeedback());
        assertTrue(newAccount.getUser().getUsername().equals(testName));
        assertTrue(newAccount.getTheme().getName().equals(testThemeName));
        assertTrue(newAccount.getCities().isEmpty());
        assertTrue(newAccount.getFeedback().isEmpty());
        */

        /*
        //constructor with args: user, theme, citylist
        newAccount = new Account(testUser, testTheme, testCities);
        assertNotNull(newAccount);
        assertNotNull(newAccount.getUser());
        assertNotNull(newAccount.getTheme());
        assertNotNull(newAccount.getCities());
        assertNotNull(newAccount.getFeedback());
        assertTrue(newAccount.getUser().getUsername().equals(testName));
        assertTrue(newAccount.getTheme().getName().equals(testThemeName));
        assertTrue(newAccount.getCities().size() == 1);
        assertTrue(newAccount.getCities().get(0).getCityName().equals(testCityName));
        assertTrue(newAccount.getFeedback().isEmpty());
        */

        /*
        //constructor with args: user, theme, citylist, feedback
        newAccount = new Account(testUser, testTheme, testCities, testFeedback);
        assertNotNull(newAccount);
        assertNotNull(newAccount.getUser());
        assertNotNull(newAccount.getTheme());
        assertNotNull(newAccount.getCities());
        assertNotNull(newAccount.getFeedback());
        assertTrue(newAccount.getUser().getUsername().equals(testName));
        assertTrue(newAccount.getTheme().getName().equals(testThemeName));
        assertTrue(newAccount.getCities().size() == 1);
        assertTrue(newAccount.getCities().get(0).getCityName().equals(testCityName));
        assertTrue(newAccount.getFeedback().size() == 1);
        assertTrue(newAccount.getFeedback().get(0).getFeedback().equals(testFeedbackMessage));


         */

        /*
        //constructor with args: account
        Account freshAccount = new Account(newAccount);
        assertNotNull(freshAccount);
        assertNotNull(freshAccount.getUser());
        assertNotNull(freshAccount.getTheme());
        assertNotNull(freshAccount.getCities());
        assertNotNull(freshAccount.getFeedback());
        assertTrue(freshAccount.getUser().getUsername().equals(testName));
        assertTrue(freshAccount.getTheme().getName().equals(testThemeName));
        assertTrue(freshAccount.getCities().size() == 1);
        assertTrue(freshAccount.getCities().get(0).getCityName().equals(testCityName));
        assertTrue(freshAccount.getFeedback().size() == 1);
        assertTrue(freshAccount.getFeedback().get(0).getFeedback().equals(testFeedbackMessage));
*/
    }

    /*
    @Test
    public void setTheme_isCorrect() {
        String defaultThemeName = "Default";
        String testThemeName = "AwesomeTheme";
        Account newAccount = new Account();

        assertTrue(newAccount.getTheme().getName().equals(defaultThemeName));

        newAccount.setTheme(new Theme(testThemeName));

        assertTrue(newAccount.getTheme().getName().equals(testThemeName));

    }
*/
    @Test
    public void addCity_isCorrect() {
        String testCityName = "Detroit";

        Account newAccount = new Account();
        assertTrue(newAccount.getCities().isEmpty());

        newAccount.addCity(new City(testCityName));
        assertTrue(newAccount.getCities().get(0).getCityName().equals(testCityName));

    }

    @Test
    public void addFeedback_isCorrect() {
        String testFeedbackMessage = "5 stars";
        Account newAccount = new Account();
        assertTrue(newAccount.getFeedback().isEmpty());

        newAccount.addFeedback(new Feedback(testFeedbackMessage));
        assertTrue(newAccount.getFeedback().get(0).getFeedback().equals(testFeedbackMessage));

    }

    @Test
    public void removeCity_isCorrect() {
        String testCityName = "Detroit";
        City testCity = new City(testCityName);

        Account newAccount = new Account();
        assertTrue(newAccount.getCities().isEmpty());

        newAccount.addCity(new City("Fairfax"));
        newAccount.addCity(testCity);
        newAccount.addCity(new City("Boise"));

        assertNotNull(newAccount.getCityByName(testCityName));
        assertTrue(newAccount.getCityByName(testCityName).getCityName().equals(testCityName));


        newAccount.removeCity(testCity);

        assertNull(newAccount.getCityByName(testCityName));

    }

}