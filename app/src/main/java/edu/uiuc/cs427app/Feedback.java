package edu.uiuc.cs427app;

//STUB
//TODO: Fully define Feedback class
public class Feedback {

    private String feedback;

    //default constructor for feedback object
    public Feedback() {
        feedback = "N/A";
    }

    //constructor that accepts a blob of text for feedback
    public Feedback(String newFeedback) {
        feedback = newFeedback;
    }

    //returns feedback for this feedback object
    public String getFeedback() {
        return feedback;
    }
}
