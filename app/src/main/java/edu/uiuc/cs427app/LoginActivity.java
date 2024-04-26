package edu.uiuc.cs427app;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class LoginActivity extends AppCompatActivity {

    // For password storage & verification
    protected SharedPreferences regularPreferences;
    private SharedPreferences encryptedPreferences;

    private AccountManager mAccountManager;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private EditText mSignUpEmailEditText;
    private EditText mSignUpUsernameEditText;
    private EditText mSignUpPasswordEditText;

    // For storing theme preference value purpose
    public Theme currentTheme = new Theme();
    public static final String ACCOUNT_TYPE = "edu.uiuc.cs427app";
    public static final String AUTH_TOKEN_TYPE = "full_access";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Theme color, need to be set before setContentView to avoid layout inflation
        ThemeUtils.onActivityCreateSetTheme(this, 0);

        setContentView(R.layout.activity_login);

        // Initialization for login
        mAccountManager = AccountManager.get(this);
        mUsernameEditText = findViewById(R.id.usernameEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);


        //assign login handler
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // Initialize for theme selection
        Spinner themeSpin = findViewById(R.id.Theme_Spinner);
        SwitchCompat darkmode = findViewById(R.id.Dark_Mode_Switch);
        // Perform theme changes based on user selection, including color and dark mode changes
        setThemePreference(themeSpin);
        setDarkMode(darkmode);

        // Initialization for sign-up
        mSignUpEmailEditText = findViewById(R.id.signUpEmailEditText);
        mSignUpUsernameEditText = findViewById(R.id.signUpUsernameEditText);
        mSignUpPasswordEditText = findViewById(R.id.signUpPasswordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);

        //assign signup handler
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });

        //initialize preferences
        initPreferences();
    }

    //// Helper functions dealing with password storage & validation
    // Initialize EncryptedSharedPreferences
    private void initPreferences() {
        regularPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            encryptedPreferences = EncryptedSharedPreferences.create(
                    "encrypted_prefs",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the error appropriately.
        }
    }
    // Stores username and password
    private void storeAccountInfo(String username, String password, Theme active) {
        // Store username in regular preferences
        SharedPreferences.Editor editor = regularPreferences.edit();
        editor.putString(username, username);
        editor.apply();
        // Store user ui preference in regular preferences
        editor.putInt(username + "themecolor", active.getName());
        editor.apply();
        editor.putBoolean(username + "darkmode", active.isDark_mode());
        editor.apply();

        // Store password in encrypted preferences
        SharedPreferences.Editor encryptedEditor = encryptedPreferences.edit();
        encryptedEditor.putString(username + "pass", password);
        encryptedEditor.apply();
    }
    // checks login credentials with regularPreferences & encryptedPreferences
    private boolean checkCredentials(String inputUsername, String inputPassword) {
        String storedUsername = regularPreferences.getString(inputUsername, "");
        String storedPassword = encryptedPreferences.getString(inputUsername + "pass", "");

        return inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword);
    }

// validates login & toasts result (success / failure) to user
    private void handleLogin() {
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (validateLogin(username, password)) {

            User loginUser = new User(username, password);
            Account account = new Account(loginUser, currentTheme);

            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            //load theme setting
            loadTheme();
            loadDarkMode();
            ThemeUtils.onActivityCreateSetTheme(this, currentTheme.getName());
            //ThemeUtils.setDarkMode(currentTheme.isDark_mode()); // this will overwrite the theme color

            // Start MainActivity and clear the activity stack
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid login!", Toast.LENGTH_SHORT).show();
        }
    }


    // Ensure username and password are non-empty & in database
    private boolean validateLogin(String username, String password) {
        // TODO: Implement your own authentication logic here.
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        if (!checkCredentials(username, password)) {
            return false;
        }

        return true;
    }

    // Validate user input & perform account creation
    private void handleSignUp() {
        String email = mSignUpEmailEditText.getText().toString();
        String username = mSignUpUsernameEditText.getText().toString();
        String password = mSignUpPasswordEditText.getText().toString();

        if (validateSignUp(email, username, password)) {
            // TODO: Implement your logic to add the user to your database.

            Toast.makeText(this, "Sign Up successful!", Toast.LENGTH_SHORT).show();

            Log.d("I", "signup Username: " + username);
            Log.d("I", "signup Password: " + password);
            Log.d("I", "signup Email: " + email);

            // Clear sign-up fields after successful registration
            mSignUpEmailEditText.setText("");
            mSignUpUsernameEditText.setText("");
            mSignUpPasswordEditText.setText("");

            // Optionally: Automatically log in the user after a successful sign-up
            // handleLogin();


            // 1. Store Username in Shared Preferences
            // 2. Store the encrypted password in 'EncryptedSharedPreferences'

        } else {
            Toast.makeText(this, "Sign Up failed!", Toast.LENGTH_SHORT).show();
        }
    }

    // Ensure email, username, and password are non-empty & initialize account
    private boolean validateSignUp(String email, String username, String password) {
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        //storeAccountInfo(username, password);
        storeAccountInfo(username, password, currentTheme);
        return true;
    }

    /**
     * Allow users to choose theme color from a dropdown menu.
     * The default theme color is set to the original app setting color.
     * System will save user's preference to ensure the theme does not get set back to default after
     * closing the application
     */
    private void setThemePreference(Spinner spinner)
    {
        // Initialize the current spinner selection with current theme color
        spinner.setSelection(ThemeUtils.currentTheme.getName());

        // Set the current selected color into user reference
        currentTheme.setName(spinner.getSelectedItemPosition());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (currentTheme.getName() != i)
                    currentTheme.setName(i); // Update user theme reference

                // Store theme color
                SharedPreferences themePrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = themePrefs.edit();
                editor.putInt("themecolor", currentTheme.getName());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                currentTheme.setName(0);
                SharedPreferences themePrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = themePrefs.edit();
                editor.putInt("themecolor", currentTheme.getName());
                editor.apply();
            }
        });
    }

    /**
     * Allow users to enable dark mode for the application from a switch control.
     * System will save user's preference to ensure the display remains until user changes again.
     */
    private void setDarkMode(SwitchCompat darkMode)
    {

        // Set dark mode switch default as false
        boolean checked = false;
        darkMode.setChecked(false); // set the dark mode switch
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton b, boolean isChecked) {
                // Checked to enable the dark mode
                if (isChecked)
                    currentTheme.setDark_mode(true); // Set dark mode for current active theme
                // Default mode is light mode
                else
                    currentTheme.setDark_mode(false); // Set dark mode for current active theme
                /** if (isChecked)
            {
                currentTheme.setDark_mode(true);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else
            {
                currentTheme.setDark_mode(false);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }*/

                // Set dark mode preference to remain the status after logout
                SharedPreferences themePrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = themePrefs.edit();
                editor.putBoolean("darkmode", currentTheme.isDark_mode());
                editor.apply();

            }
        });
    }

    //load the theme based on preferences associated with current user
    private void loadTheme()
    {
        String username = mUsernameEditText.getText().toString();
        //SharedPreferences themePrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int theme = regularPreferences.getInt(username + "themecolor",0);
        ThemeUtils.currentTheme.setName(theme);
        currentTheme.setName(theme);
        //return theme;
    }

    //sets UI to 'dark mode'
    private void loadDarkMode()
    {
        String username = mUsernameEditText.getText().toString();
        boolean dark = regularPreferences.getBoolean(username + "darkmode", false);
        ThemeUtils.currentTheme.setDark_mode(dark);
        currentTheme.setDark_mode(dark);
    }
}