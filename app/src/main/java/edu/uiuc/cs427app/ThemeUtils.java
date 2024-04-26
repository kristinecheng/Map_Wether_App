package edu.uiuc.cs427app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.IpSecManager;
import android.preference.PreferenceManager;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatDelegate;

//utility class for managing themes
public class ThemeUtils
{
    /** Enum values refer to the theme color  */
    public final static int DEFAULT = 0;
    public final static int PINK = 1;
    public final static int RED = 2;
    public final static int ORANGE = 3;
    public final static int YELLOW = 4;
    public final static int GREEN = 5;
    public final static int BLUE = 6;
    public final static int PURPLE = 7;

    private static int theme_color;
    private static String text_size;

    public static Theme currentTheme = new Theme();

    /**
     * Helper function to define the action of changing theme color. It will end the current activity and reload it after successfully changing theme color
     * @param activity - refers to the current activity
     * @param color - refers to the current theme color, could be update later if user changed it
     */
    public static void changeToTheme(Activity activity, int color)
    {
        theme_color = color;
        activity.recreate();
        //activity.finish();
        //activity.startActivity(new Intent(activity, activity.getClass()));

    }

    /**
     * Helper function to set the theme color to the user preference
     * @param activity - refers to the current activity
     */
    public static void onActivityCreateSetTheme(Activity activity, int current)
    {
        switch (current)
        {
            case DEFAULT:
                activity.setTheme(R.style.Theme_MyFirstApp);
                currentTheme.setName(DEFAULT);
                break;
            case PINK:
                activity.setTheme(R.style.Theme_MyFirstApp_Pink);
                currentTheme.setName(PINK);
                break;
            case RED:
                activity.setTheme(R.style.Theme_MyFirstApp_Red);
                currentTheme.setName(RED);
                break;
            case ORANGE:
                activity.setTheme(R.style.Theme_MyFirstApp_Orange);
                currentTheme.setName(ORANGE);
                break;
            case YELLOW:
                activity.setTheme(R.style.Theme_MyFirstApp_Yellow);
                currentTheme.setName(YELLOW);
                break;
            case GREEN:
                activity.setTheme(R.style.Theme_MyFirstApp_Green);
                currentTheme.setName(GREEN);
                break;
            case BLUE:
                activity.setTheme(R.style.Theme_MyFirstApp_Blue);
                currentTheme.setName(BLUE);
                break;
            case PURPLE:
                activity.setTheme(R.style.Theme_MyFirstApp_Purple);
                currentTheme.setName(PURPLE);
                break;
        }


    }

    /**
     * Function to set dark mode with user specified
      * @param checked - refer to the user preference for dark mode selection
     */
    public static void setDarkMode(boolean checked)
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (checked)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }
}
