package edu.uiuc.cs427app;


//import edu.uiuc.cs427app.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Account {
    private User user;
    private List<City> cityList;
    private List<Feedback> feedbackList;
    private Theme activeTheme;

    //default constructor for Account object
    public Account() {
        user = new User();
        activeTheme = new Theme();
        cityList = new ArrayList<>();
        feedbackList = new ArrayList<>();
    }

    //constructor for account object using existing user object
    public Account(User existingUser) {
        this();
        user = existingUser;
    }

    //constructor for account object using existing user and theme objects
    public Account(User existingUser, Theme existingTheme) {
        this();
        user = existingUser;
        activeTheme = existingTheme;
    }

    //constructor for account object using existing user, theme, and list of city objects
    public Account(User existingUser, Theme existingTheme, List<City> existingCityList) {
        this();
        user = existingUser;
        activeTheme = existingTheme;
        cityList = existingCityList;
    }

    //constructor for account object using existing user, theme, list of city, and list of feedback objects
    public Account(User existingUser, Theme existingTheme, List<City> existingCityList, List<Feedback> existingFeedbackList) {
        this();
        user = existingUser;
        activeTheme = existingTheme;
        cityList = existingCityList;
        feedbackList = existingFeedbackList;
    }

    //constructor for account object using existing account object
    public Account(Account newAccount) {
        this();
        user = newAccount.getUser();
        activeTheme = newAccount.getTheme();
        cityList = newAccount.getCities();
        feedbackList = newAccount.getFeedback();

    }

    //setter for theme, accepts Theme object as input
    public void setTheme(Theme theme) {
        activeTheme = theme;
        //setTheme(android.R.style.Theme_Translucent_NoTitleBar);
    }

    //Adds a city object to the city list for account
    public void addCity(City city) {
        cityList.add(city);
    }

    //removes a city object from the city list
    //matches on literal name of city
    public void removeCity(City city) {
        cityList.removeIf(currCity -> currCity.getCityName().equals(city.getCityName()));
    }

    //adds a feedback object to the feedback list
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }

    //returns the current city collection as a list
    public List<City> getCities() {
        return cityList;
    }

    //Find city in cityList by String
    //returns City object of first match
    //returns null if no match
    public City getCityByName(String name) {
        for (City currCity : cityList) {
            if (currCity.getCityName().equals(name)) {
                return currCity;
            }
        }

        return null;
    }

    //returns the current feedback collection as a list
    public List<Feedback> getFeedback() {
        return feedbackList;
    }

    //returns the current user object
    public User getUser() {
        return user;
    }

    //returns the current theme object
    public Theme getTheme() {
        return activeTheme;
    }
}
