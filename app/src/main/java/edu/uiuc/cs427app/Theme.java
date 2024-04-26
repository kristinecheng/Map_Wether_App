package edu.uiuc.cs427app;
//STUB
//TODO: Fully define theme object
/**
 * This class stores the theme value for the color and dark mode setting
 */
public class Theme {

    private int name; // Refers to the theme color
    private boolean dark_mode;

    //default constructor for theme object
    public Theme()
    {
        name = 0;
        dark_mode = false;
    }

    //initialize theme with theme name (id) and dark mode boolean
    public Theme(int name, boolean dark_mode)
    {
        setName(name);
        setDark_mode(dark_mode);
    }

    //returns id of theme as an integer
    public int getName()
    {
        return name;
    }

    //sets the id of theme as integer
    public void setName(int name)
    {
        this.name = name;
    }

    //returns if theme is dark mode or standard
    public boolean isDark_mode() {
        return dark_mode;
    }

    //sets boolean for dark mode
    public void setDark_mode(boolean dark_mode) {
        this.dark_mode = dark_mode;
    }
}
