package com.example.ecommerce.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Authentication {

    private static final String PREFS_NAME = "AUTH_PREFS";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_ID = "ID";
    private static final String KEY_LOGGED_IN = "LOGGED_IN";

    private SharedPreferences mPrefs;
    private User currentUser;

    public Authentication(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setCurrentUser(User user) {
        currentUser = user;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_ID, user.getId());
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            String username = mPrefs.getString(KEY_USERNAME, "dummy");
            String password = mPrefs.getString(KEY_PASSWORD, "dummy");
            String id = mPrefs.getString(KEY_ID, null);
            if (username != null && password != null) {
                currentUser = new User(username, password,id);
            }
        }
        return currentUser;
    }

    public boolean isUserLoggedIn() {
        return mPrefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public void logout() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_ID);
        editor.putBoolean(KEY_LOGGED_IN, false);
        editor.apply();
        currentUser = null;
    }
}
