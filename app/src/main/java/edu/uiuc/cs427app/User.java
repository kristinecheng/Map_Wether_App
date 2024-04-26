package edu.uiuc.cs427app;

//STUB
//TODO: Fully define User class
public class User {

    private String username;
    private String email;
    private String password;

    //default constructor for user object
    public User() {
        username = "foo";
        password = "bar";
        email = "foo@bar.com";

    }

    //constructor for user object, specifying user name
    public User(String newUsername) {
        username = newUsername;
    }

    //constructor for user object, specifying user name and password
    public User(String newUsername, String newPassword) {
        username = newUsername;
        password = newPassword;
    }

    //returns the current user name
    public String getUsername() {
        return username;
    }
}
