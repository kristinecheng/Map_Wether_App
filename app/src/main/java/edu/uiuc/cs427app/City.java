package edu.uiuc.cs427app;

//STUB
//TODO: Fully define City object

public class City {

    private String name;

    //default constructor for city object
    public City() {
        name = "Default City";
    }

    //constructor for city object tht accepts string to define city name
    public City(String newCity) {
        name = newCity;
    }

    //returns name of city from city object as a string
    public String getCityName() {
        return name;
    }
}
